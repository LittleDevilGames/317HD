package rs.cache.impl;

import rs.cache.Archive;
import rs.io.Buffer;
import rs.scene.model.Model;

public class IdentityKit {

    public static int count;
    public static IdentityKit[] instance;
    public int anInt657;
    public short[] dialogModelIndex = {-1, -1, -1, -1, -1};
    public short[] modelIndex;
    public int newColor[];
    public int oldColor[];
    public boolean unselectable;

    public IdentityKit() {
        this.defaults();
    }

    public IdentityKit(Buffer b) {
        this.defaults();
        do {
            int opcode = b.readUnsignedByte();
            if (opcode == 0) {
                return;
            }
            if (opcode == 1) {
                this.anInt657 = b.readUnsignedByte();
            } else if (opcode == 2) {
                this.modelIndex = new short[b.readUnsignedByte()];
                for (int k = 0; k < this.modelIndex.length; k++) {
                    this.modelIndex[k] = (short) b.readUnsignedShort();
                }
            } else if (opcode == 3) {
                this.unselectable = true;
            } else if (opcode >= 40 && opcode < 50) {
                this.oldColor[opcode - 40] = b.readUnsignedShort();
            } else if (opcode >= 50 && opcode < 60) {
                this.newColor[opcode - 50] = b.readUnsignedShort();
            } else if (opcode >= 60 && opcode < 70) {
                this.dialogModelIndex[opcode - 60] = (short) b.readUnsignedShort();
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while (true);
    }

    public static void unpack(Archive a) {
        Buffer s = new Buffer(a.get("idk.dat"));
        IdentityKit.count = s.readUnsignedShort();
        IdentityKit.instance = new IdentityKit[IdentityKit.count];

        for (int i = 0; i < IdentityKit.count; i++) {
            IdentityKit.instance[i] = new IdentityKit(s);
        }
    }

    public void defaults() {
        anInt657 = -1;
        oldColor = new int[6];
        newColor = new int[6];
        unselectable = false;
    }

    public Model get_dialog_model() {
        Model models[] = new Model[5];

        int count = 0;
        for (int i : this.dialogModelIndex) {
            if (i != -1) {
                models[count++] = Model.get(i);
            }
        }

        Model m = new Model(count, models);
        for (int i = 0; i < 6; i++) {
            if (oldColor[i] == 0) {
                break;
            }
            m.setColor(oldColor[i], newColor[i]);
        }

        return m;
    }

    public Model getMesh() {
        if (modelIndex == null) {
            return null;
        }

        Model models[] = new Model[modelIndex.length];

        for (int i = 0; i < modelIndex.length; i++) {
            models[i] = Model.get(modelIndex[i]);
        }

        Model m;

        if (models.length == 1) {
            m = models[0];
        } else {
            m = new Model(models.length, models);
        }

        for (int i = 0; i < 6; i++) {
            if (oldColor[i] == 0) {
                break;
            }
            m.setColor(oldColor[i], newColor[i]);
        }

        return m;
    }

    public boolean isDialogModelValid() {
        boolean valid = true;
        for (int i = 0; i < 5; i++) {
            if (dialogModelIndex[i] != -1 && !Model.isValid(dialogModelIndex[i])) {
                valid = false;
            }
        }
        return valid;
    }

    public boolean isModelValid() {
        if (modelIndex == null) {
            return true;
        }
        for (int i : this.modelIndex) {
            if (!Model.isValid(i)) {
                return false;
            }
        }
        return true;
    }
}
