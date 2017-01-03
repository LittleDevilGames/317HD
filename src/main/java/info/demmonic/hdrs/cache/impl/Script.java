package info.demmonic.hdrs.cache.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.model.Skill;
import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.io.Buffer;

public class Script {

    public static final int OPTYPE_ADD = 0;
    public static final int OPTYPE_DIV = 2;
    public static final int OPTYPE_MUL = 3;
    public static final int OPTYPE_SUB = 1;
    public static final String[] TRIGGER_TYPE = {"==", "==", "<", ">", "!="};
    public final byte index;
    public final int[] intcode;
    public byte compareType = 0;
    public int compareValue = -2;
    public Widget widget;

    public Script(Widget widget, Buffer b, int index) {
        this.widget = widget;
        this.index = (byte) index;
        this.intcode = new int[b.readUnsignedShort()];

        for (int i = 0; i < this.intcode.length; i++) {
            this.intcode[i] = b.readUnsignedShort();
        }
    }

    public int execute() {
        try {
            int value = 0;
            int type = 0;
            int i = 0;
            int[] code = this.intcode;

            do {
                int opcode = code[i++];
                int register = 0;
                byte optype = 0;

                switch (opcode) {
                    case 0: {
                        return value;
                    }

                    case 1: {
                        register = Game.skillLevel[code[i++]];
                        break;
                    }

                    case 2: {
                        register = Game.skillRealLevel[code[i++]];
                        break;
                    }

                    case 3: {
                        register = Game.skillExperience[code[i++]];
                        break;
                    }

                    case 4: {
                        Widget w = Widget.instance[code[i++]];
                        int item_index = code[i++];

                        if (item_index >= 0 && item_index < ObjConfig.count && (!ObjConfig.get(item_index).isMembers || Game.isMembers)) {
                            for (int j = 0; j < w.itemIndex.length; j++) {
                                if (w.itemIndex[j] == item_index + 1) {
                                    register += w.itemCount[j];
                                }
                            }
                        }
                        break;
                    }

                    case 5: {
                        register = Game.settings[code[i++]];
                        break;
                    }

                    case 6: {
                        register = Game.XP_TABLE[Game.skillRealLevel[code[i++]] - 1];
                        break;
                    }

                    case 7: {
                        register = (Game.settings[code[i++]] * 100) / 46875;
                        break;
                    }

                    case 8: {
                        register = Game.self.combatLevel;
                        break;
                    }

                    case 9: {
                        for (int j = 0; j < Skill.COUNT; j++) {
                            if (Skill.ENABLED[j]) {
                                register += Game.skillRealLevel[j];
                            }
                        }
                        break;
                    }

                    // XXX: register = 999,999,999 if inventory doesn't contain item
                    // Which means that by default it would return 0 if it does.
                    case 10: {
                        Widget child = Widget.get(code[i++]);
                        if (child != null) {
                            int item_index = code[i++] + 1;
                            if (item_index >= 0 && item_index < ObjConfig.count && (!ObjConfig.get(item_index).isMembers || Game.isMembers)) {
                                for (int slot = 0; slot < child.itemIndex.length; slot++) {
                                    if (child.itemIndex[slot] != item_index) {
                                        continue;
                                    }
                                    register = 999999999;
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case 11:
                        register = Game.energyLeft;
                        break;
                    case 12:
                        register = Game.weightCarried;
                        break;
                    case 13:
                        int setting = Game.settings[code[i++]];
                        int shift = code[i++];
                        register = (setting & 1 << shift) == 0 ? 0 : 1;
                        break;
                    case 14:
                        VarBit varbit = VarBit.instance[code[i++]];
                        int offset = varbit.offset;
                        int lsb = Game.LSB_BIT_MASK[varbit.shift - offset];
                        register = (Game.settings[varbit.setting] >> offset) & lsb;
                        break;
                    case 15:
                        optype = OPTYPE_SUB;
                        break;
                    case 16:
                        optype = OPTYPE_DIV;
                        break;
                    case 17:
                        optype = OPTYPE_MUL;
                        break;
                    case 18:
                        register = Game.self.get_tile_x();
                        break;
                    case 19:
                        register = Game.self.get_tile_y();
                        break;
                    case 20:
                        register = code[i++];
                        break;
                }

                if (optype == OPTYPE_ADD) {
                    switch (type) {
                        case OPTYPE_ADD: {
                            value += register;
                            break;
                        }

                        case OPTYPE_SUB: {
                            value -= register;
                            break;
                        }

                        case OPTYPE_DIV: {
                            if (register != 0) {
                                value /= register;
                            }
                            break;
                        }

                        case OPTYPE_MUL: {
                            value *= register;
                            break;
                        }
                    }
                    type = 0;
                } else {
                    type = optype;
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
