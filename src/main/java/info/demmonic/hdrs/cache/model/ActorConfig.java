package info.demmonic.hdrs.cache.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.cache.impl.VarBit;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.node.List;
import info.demmonic.hdrs.scene.model.Model;

public class ActorConfig {

    public static Buffer buffer;
    public static int bufferPosition[];
    public static ActorConfig[] cache;
    public static int cacheIndex;
    public static int count;
    public static List modelCache = new List(30);
    public boolean aBoolean93;
    public String[] action;
    public int brightness;
    public short combatLevel;
    public String description;
    public short[] dialogModelIndex;
    public byte hasOptions;
    public byte headIcon;
    public int index;
    public boolean interactable;
    public short[] modelIndex;
    public short moveSeq;
    public String name;
    public int[] newColors;
    public int[] oldColors;
    public short scaleX;
    public short scaleY;
    public short settingIndex;
    public short[] overrideIndex;
    public boolean showOnMinimap;
    public short specular;
    public short standSequence;
    public short turn180Seq;
    public short turnLSeq;
    public short turnRSeq;
    public short turnSpeed;
    public short varbitIndex;

    public ActorConfig() {
        this.defaults();
    }

    public ActorConfig(int index, Buffer b) {
        this.defaults();
        this.index = index;

        do {
            int opcode = b.readUnsignedByte();
            if (opcode == 0) {
                return;
            }
            if (opcode == 1) {
                modelIndex = new short[b.readUnsignedByte()];
                for (int i = 0; i < modelIndex.length; i++) {
                    modelIndex[i] = (short) b.readUnsignedShort();
                }
            } else if (opcode == 2) {
                name = b.readString();
            } else if (opcode == 3) {
                description = b.readString();
            } else if (opcode == 12) {
                hasOptions = b.readByte();
            } else if (opcode == 13) {
                standSequence = (short) b.readUnsignedShort();
            } else if (opcode == 14) {
                moveSeq = (short) b.readUnsignedShort();
            } else if (opcode == 17) {
                moveSeq = (short) b.readUnsignedShort();
                turn180Seq = (short) b.readUnsignedShort();
                turnRSeq = (short) b.readUnsignedShort();
                turnLSeq = (short) b.readUnsignedShort();
            } else if (opcode >= 30 && opcode < 40) {
                if (action == null) {
                    action = new String[5];
                }
                action[opcode - 30] = b.readString();

                if (action[opcode - 30].equalsIgnoreCase("hidden")) {
                    action[opcode - 30] = null;
                }
            } else if (opcode == 40) {
                int count = b.readUnsignedByte();
                oldColors = new int[count];
                newColors = new int[count];
                for (int i = 0; i < count; i++) {
                    oldColors[i] = b.readUnsignedShort();
                    newColors[i] = b.readUnsignedShort();
                }

            } else if (opcode == 60) {
                int count = b.readUnsignedByte();
                dialogModelIndex = new short[count];

                for (int i = 0; i < count; i++) {
                    dialogModelIndex[i] = (short) b.readUnsignedShort();
                }
            } else if (opcode == 90) {
                b.readUnsignedShort();
            } else if (opcode == 91) {
                b.readUnsignedShort();
            } else if (opcode == 92) {
                b.readUnsignedShort();
            } else if (opcode == 93) {
                showOnMinimap = false;
            } else if (opcode == 95) {
                combatLevel = (short) b.readUnsignedShort();
            } else if (opcode == 97) {
                scaleX = (short) b.readUnsignedShort();
            } else if (opcode == 98) {
                scaleY = (short) b.readUnsignedShort();
            } else if (opcode == 99) {
                aBoolean93 = true;
            } else if (opcode == 100) {
                brightness = b.readByte();
            } else if (opcode == 101) {
                specular = (short) (b.readByte() * 5);
            } else if (opcode == 102) {
                headIcon = (byte) (short) b.readUnsignedShort();
            } else if (opcode == 103) {
                turnSpeed = (short) b.readUnsignedShort();
            } else if (opcode == 106) {
                varbitIndex = (short) b.readUnsignedShort();
                settingIndex = (short) b.readUnsignedShort();

                overrideIndex = new short[b.readUnsignedByte() + 1];

                for (int i = 0; i <= overrideIndex.length; i++) {
                    overrideIndex[i] = (short) b.readUnsignedShort();
                }

            } else if (opcode == 107) {
                interactable = false;
            }
        } while (true);
    }

    public static ActorConfig get(int index) {
        for (ActorConfig config : ActorConfig.cache) {
            if (config.index == index) {
                return config;
            }
        }

        ActorConfig.cacheIndex = (ActorConfig.cacheIndex + 1) % 20;
        ActorConfig.buffer.position = ActorConfig.bufferPosition[index];

        return (ActorConfig.cache[cacheIndex] = new ActorConfig(index, buffer));
    }

    public static void nullify() {
        ActorConfig.modelCache = null;
        ActorConfig.cache = null;
        ActorConfig.buffer = null;
        ActorConfig.bufferPosition = null;
    }

    public static void unpack(Archive a) {
        ActorConfig.buffer = new Buffer(a.get("npc.dat", null));
        Buffer idx = new Buffer(a.get("npc.idx", null));
        ActorConfig.count = idx.readUnsignedShort();

        ActorConfig.bufferPosition = new int[ActorConfig.count];

        int position = 2;
        for (int i = 0; i < ActorConfig.count; i++) {
            ActorConfig.bufferPosition[i] = position;
            position += idx.readUnsignedShort();
        }

        ActorConfig.cache = new ActorConfig[20];

        for (int i = 0; i < ActorConfig.cache.length; i++) {
            ActorConfig.cache[i] = new ActorConfig();
        }
    }

    public void defaults() {
        this.turnLSeq = -1;
        this.varbitIndex = -1;
        this.turn180Seq = -1;
        this.settingIndex = -1;
        this.combatLevel = -1;
        this.moveSeq = -1;
        this.hasOptions = 1;
        this.headIcon = -1;
        this.standSequence = -1;
        this.index = -1;
        this.turnSpeed = 32;
        this.turnRSeq = -1;
        this.interactable = true;
        this.scaleY = 128;
        this.showOnMinimap = true;
        this.scaleX = 128;
        this.aBoolean93 = false;
    }

    public Model getDialogModel() {
        if (overrideIndex != null) {
            ActorConfig config = getOverridingConfig();
            if (config == null) {
                return null;
            } else {
                return config.getDialogModel();
            }
        }

        if (dialogModelIndex == null) {
            return null;
        }

        boolean valid = false;

        for (int i = 0; i < dialogModelIndex.length; i++) {
            if (!Model.isValid(dialogModelIndex[i])) {
                valid = true;
            }
        }

        if (valid) {
            return null;
        }

        Model[] models = new Model[dialogModelIndex.length];

        for (int i = 0; i < dialogModelIndex.length; i++) {
            models[i] = Model.get(dialogModelIndex[i]);
        }

        Model m;

        if (models.length == 1) {
            m = models[0];
        } else {
            m = new Model(models.length, models);
        }

        if (oldColors != null) {
            m.setColors(oldColors, newColors);
        }
        return m;
    }

    public Model getModel(int vertices[], int frame1, int frame2) {
        if (overrideIndex != null) {
            ActorConfig config = getOverridingConfig();

            if (config == null) {
                return null;
            } else {
                return config.getModel(vertices, frame1, frame2);
            }
        }

        Model model = (Model) modelCache.get(index);

        if (model == null) {
            boolean flag = false;
            for (int i1 = 0; i1 < modelIndex.length; i1++) {
                if (!Model.isValid(modelIndex[i1])) {
                    flag = true;
                }
            }

            if (flag) {
                return null;
            }
            Model models[] = new Model[modelIndex.length];

            for (int i = 0; i < modelIndex.length; i++) {
                models[i] = Model.get(modelIndex[i]);
            }

            if (models.length == 1) {
                model = models[0];
            } else {
                model = new Model(models.length, models);
            }

            if (oldColors != null) {
                model.setColors(oldColors, newColors);
            }

            model.applyVertexWeights();
            model.applyLighting(64 + brightness, 850 + specular, -30, -50, -30, true);
            modelCache.insert(model, index);
        }

        Model m = Model.temporary;
        m.replace(model, (frame1 == -1) & (frame2 == -1));

        if (frame1 != -1 && frame2 != -1) {
            m.applySequenceFrames(vertices, frame1, frame2);
        } else if (frame1 != -1) {
            m.applySequenceFrame(frame1);
        }

        if (scaleX != 128 || scaleY != 128) {
            m.scale(scaleX, scaleY, scaleX);
        }

        m.method466();
        m.triangleGroups = null;
        m.vertexWeights = null;

        if (hasOptions == 1) {
            m.isClickable = true;
        }

        return m;
    }

    public ActorConfig getOverridingConfig() {
        int value = -1;

        if (varbitIndex != -1) {
            VarBit varbit = VarBit.instance[varbitIndex];
            int index = varbit.setting;
            int offset = varbit.offset;
            int position = varbit.shift;
            int maxValue = Game.LSB_BIT_MASK[position - offset];
            value = Game.settings[index] >> offset & maxValue;
        } else if (settingIndex != -1) {
            value = Game.settings[settingIndex];
        }

        if (value < 0 || value >= overrideIndex.length || overrideIndex[value] == -1) {
            return null;
        }

        return get(overrideIndex[value]);
    }

}
