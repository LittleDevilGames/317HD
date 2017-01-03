package info.demmonic.hdrs.media.impl.widget;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.IdentityKit;
import info.demmonic.hdrs.cache.impl.Widget;
import info.demmonic.hdrs.scene.model.Model;
import info.demmonic.hdrs.cache.impl.Sequence;
import info.demmonic.hdrs.media.Sprite;

public class CharacterDesign {

    /* @formatter:off */
    public static final int DESIGN_COLOR[][] = {
            // Hair
            {6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193},
            // Torso
            {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239},
            // Legs
            {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003},
            // Feet
            {4626, 11146, 6439, 12, 4758, 10270},
            // Skin
            {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}
    };
    public static final int TORSO_COLORS[] = {
            9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486
    };
    public static Sprite buttonDisabled;
    public static Sprite buttonEnabled;
    public static boolean male = true;
    public static boolean update;
    public static int[] selectedColors = new int[5];
    public static int[] selectedIdentityKits = new int[7];
    /* @formatter:on */

    public static boolean handle(Widget widget, int type) {
        if (type >= 314 && type <= 323) {
            int part = (type - 314) / 2;
            int option = type & 1;
            int current = selectedColors[part];

            if (option == 0 && --current < 0) {
                current = DESIGN_COLOR[part].length - 1;
            }

            if (option == 1 && ++current >= DESIGN_COLOR[part].length) {
                current = 0;
            }

            selectedColors[part] = current;
            update = true;
            return true;
        }

        if (type == 324 && !male) {
            male = true;
            reset();
            return true;
        }

        if (type == 325 && male) {
            male = false;
            reset();
            return true;
        }

        if (type == 326) {
            Game.out.writeOpcode(101);
            Game.out.writeByte(male ? 0 : 1);

            for (int i = 0; i < 7; i++) {
                Game.out.writeByte(selectedIdentityKits[i]);
            }

            for (int i = 0; i < 5; i++) {
                Game.out.writeByte(selectedColors[i]);
            }
            return true;
        }

        if (type >= 300 && type <= 313) {
            int part = (type - 300) / 2;
            boolean next = (type & 1) == 1;
            int identity_kit = selectedIdentityKits[part];

            if (identity_kit != -1) {
                do {
                    if (!next && --identity_kit < 0) {
                        identity_kit = IdentityKit.count - 1;
                    }
                    if (next && ++identity_kit >= IdentityKit.count) {
                        identity_kit = 0;
                    }
                }
                while (IdentityKit.instance[identity_kit].unselectable || IdentityKit.instance[identity_kit].anInt657 != part + (male ? 0 : 7));

                selectedIdentityKits[part] = identity_kit;
                update = true;
            }

            return true;
        }

        return false;
    }

    public static void reset() {
        update = true;
        for (int part = 0; part < 7; part++) {
            selectedIdentityKits[part] = -1;
            for (int identity_kit = 0; identity_kit < IdentityKit.count; identity_kit++) {
                if (IdentityKit.instance[identity_kit].unselectable || IdentityKit.instance[identity_kit].anInt657 != part + (male ? 0 : 7)) {
                    continue;
                }
                selectedIdentityKits[part] = identity_kit;
                break;
            }
        }
    }

    public static boolean update(Widget widget, int type) {
        if (type == 327) {
            widget.modelZoom = 600;
            widget.modelPitch = 150;
            widget.modelYaw = (int) (Math.sin((double) Game.loopCycle / 48D) * 256D) & 0x07FF;

            if (update) {
                for (int i = 0; i < 7; i++) {
                    int index = selectedIdentityKits[i];
                    if (index >= 0 && !IdentityKit.instance[index].isModelValid()) {
                        return true;
                    }
                }

                update = false;
                Model meshes[] = new Model[7];
                int count = 0;
                for (int i = 0; i < 7; i++) {
                    int identity_kit = selectedIdentityKits[i];
                    if (identity_kit >= 0) {
                        meshes[count++] = IdentityKit.instance[identity_kit].getMesh();
                    }
                }

                Model mesh = new Model(count, meshes);
                for (int i = 0; i < 5; i++) {
                    if (selectedColors[i] != 0) {
                        mesh.setColor(DESIGN_COLOR[i][0], DESIGN_COLOR[i][selectedColors[i]]);
                        if (i == 1) {
                            mesh.setColor(TORSO_COLORS[0], TORSO_COLORS[selectedColors[i]]);
                        }
                    }
                }

                mesh.applyVertexWeights();
                mesh.applySequenceFrame(Sequence.instance[Game.self.standAnimation].framePrimary[0]);
                mesh.applyLighting(64, 850, -30, -50, -30, true);
                widget.modelTypeDisabled = 5;
                widget.modelIndexDisabled = 0;
                Widget.setMesh(5, 0, mesh);
            }
            return true;
        }

        if (type == 324) {
            if (buttonDisabled == null) {
                buttonDisabled = widget.image_disabled;
                buttonEnabled = widget.image_enabled;
            }
            if (male) {
                widget.image_disabled = buttonEnabled;
            } else {
                widget.image_disabled = buttonDisabled;
            }
            return true;
        }

        if (type == 325) {
            if (buttonDisabled == null) {
                buttonDisabled = widget.image_disabled;
                buttonEnabled = widget.image_enabled;
            }
            if (male) {
                widget.image_disabled = buttonDisabled;
            } else {
                widget.image_disabled = buttonEnabled;
            }
            return true;
        }
        return false;
    }

}
