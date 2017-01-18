package info.demmonic.hdrs.cache.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.media.Sprite;
import info.demmonic.hdrs.node.List;
import info.demmonic.hdrs.scene.model.Model;
import info.demmonic.hdrs.util.JString;
import info.demmonic.hdrs.util.MathUtils;

public class ObjConfig {

    public static ObjConfig[] cache;
    public static int cachePosition;
    public static int count;
    public static List modelCache = new List(50);
    public static int pointer[];
    public static List spriteCache = new List(100);
    public static Buffer buffer;
    public String action[];
    public short brightness;
    public String description;
    public short femaleDialogModel1;
    public short femaleDialogModel2;
    public short femaleModel1;
    public short femaleModel2;
    public short femaleModel3;
    public byte femaleOffY;
    public String groundAction[];
    public short iconDist;
    public short iconPitch;
    public short iconRoll;
    public short iconX;
    public short iconY;
    public short iconYaw;
    public short index = -1;
    public boolean isMembers;
    public short maleDialogModel1;
    public short maleDialogModel2;
    public short maleModel1;
    public short maleModel2;
    public short maleModel3;
    public byte maleOffY;
    public short modelIndex;
    public String name;
    public int newColor[];
    public short noteItemIndex;
    public short noteTemplateIndex;
    public int oldColor[];
    public int pilePriority;
    public short scaleX;
    public short scaleY;
    public short scaleZ;
    public short specular;
    public int stackAmount[];
    public short[] stackIndex;
    public boolean stackable;
    public byte team;

    public static ObjConfig get(int index) {
        for (ObjConfig config : ObjConfig.cache) {
            if (config.index == index) {
                return config;
            }
        }

        if (index < 0 || index >= pointer.length) {
            return null;
        }

        cachePosition = (cachePosition + 1) % 10;
        ObjConfig config = cache[cachePosition];
        ObjConfig.buffer.position = ObjConfig.pointer[index];

        config.index = (short) index;
        config.defaults();
        config.load(ObjConfig.buffer);

        if (config.noteTemplateIndex != -1) {
            config.toNote();
        }

        if (!Game.isMembers && config.isMembers) {
            config.name = "Members Object";
            config.description = "Login to a members' server to use this object.";
            config.groundAction = null;
            config.action = null;
            config.team = 0;
        }

        return config;
    }

    public static Sprite getSprite(int index, int count, int outlineColor) {
        if (outlineColor == 0) {
            Sprite s = (Sprite) spriteCache.get(index);
            if (s != null && s.cropHeight != count && s.cropHeight != -1) {
                s.detach();
                s = null;
            }
            if (s != null) {
                return s;
            }
        }

        ObjConfig c = get(index);

        if (c == null) {
            return null;
        }

        if (c.stackIndex == null) {
            count = -1;
        }

        if (count > 1) {
            int i = -1;
            for (int j = 0; j < 10; j++) {
                if (count >= c.stackAmount[j] && c.stackAmount[j] != 0) {
                    i = c.stackIndex[j];
                }
            }
            if (i != -1) {
                c = get(i);
            }
        }

        Model m = c.getModel(1);

        if (m == null) {
            return null;
        }

        Sprite s1 = null;

        if (c.noteTemplateIndex != -1) {
            s1 = getSprite(c.noteItemIndex, 10, -1);

            if (s1 == null) {
                return null;
            }
        }

        Sprite s = new Sprite(32, 32);

        int centerX = Canvas3D.centerX;
        int centerY = Canvas3D.centerY;
        int pixels3D[] = Canvas3D.pixels;
        int pixels2D[] = Canvas2D.pixels;
        int width = Canvas2D.width;
        int height = Canvas2D.height;
        int x1 = Canvas2D.leftX;
        int x2 = Canvas2D.rightX;
        int y1 = Canvas2D.leftY;
        int y2 = Canvas2D.rightY;

        Canvas3D.texturize = false;
        Canvas2D.prepare(32, 32, s.pixels);
        Canvas2D.fillRect(0, 0, 32, 32, 0);
        Canvas3D.prepare();

        int dist = c.iconDist;

        if (outlineColor == -1) {
            dist = (int) ((double) dist * 1.50D);
        }

        if (outlineColor > 0) {
            dist = (int) ((double) dist * 1.04D);
        }

        int sin = MathUtils.sin[c.iconPitch] * dist >> 16;
        int cos = MathUtils.cos[c.iconPitch] * dist >> 16;

        m.draw(0, c.iconYaw, c.iconRoll, c.iconPitch, c.iconX, sin + (m.height / 2) + c.iconY, cos + c.iconY);

        for (int x = 31; x >= 0; x--) {
            for (int y = 31; y >= 0; y--) {
                if (s.pixels[x + y * 32] == 0) {
                    if (x > 0 && s.pixels[(x - 1) + y * 32] > 1) {
                        s.pixels[x + y * 32] = 1;
                    } else if (y > 0 && s.pixels[x + (y - 1) * 32] > 1) {
                        s.pixels[x + y * 32] = 1;
                    } else if (x < 31 && s.pixels[x + 1 + y * 32] > 1) {
                        s.pixels[x + y * 32] = 1;
                    } else if (y < 31 && s.pixels[x + (y + 1) * 32] > 1) {
                        s.pixels[x + y * 32] = 1;
                    }
                }
            }
        }

        if (outlineColor > 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if (s.pixels[x + y * 32] == 0) {
                        if (x > 0 && s.pixels[(x - 1) + y * 32] == 1) {
                            s.pixels[x + y * 32] = outlineColor;
                        } else if (y > 0 && s.pixels[x + (y - 1) * 32] == 1) {
                            s.pixels[x + y * 32] = outlineColor;
                        } else if (x < 31 && s.pixels[x + 1 + y * 32] == 1) {
                            s.pixels[x + y * 32] = outlineColor;
                        } else if (y < 31 && s.pixels[x + (y + 1) * 32] == 1) {
                            s.pixels[x + y * 32] = outlineColor;
                        }
                    }
                }
            }
        } else if (outlineColor == 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if (s.pixels[x + y * 32] == 0 && x > 0 && y > 0 && s.pixels[(x - 1) + (y - 1) * 32] > 0) {
                        s.pixels[x + y * 32] = 0x302020;
                    }
                }
            }
        }

        if (c.noteTemplateIndex != -1) {
            int w = s1.cropWidth;
            int h = s1.cropHeight;
            s1.cropWidth = 32;
            s1.cropHeight = 32;
            s1.drawMasked(0, 0);
            s1.cropWidth = w;
            s1.cropHeight = h;
        }

        if (outlineColor == 0) {
            spriteCache.insert(s, index);
        }

        Canvas2D.prepare(width, height, pixels2D);
        Canvas2D.setBounds(x1, y1, x2, y2);
        Canvas3D.centerX = centerX;
        Canvas3D.centerY = centerY;
        Canvas3D.pixels = pixels3D;
        Canvas3D.texturize = true;

        if (c.stackable) {
            s.cropWidth = 33;
        } else {
            s.cropWidth = 32;
        }

        s.createHelper();
        s.cropHeight = count;
        return s;
    }

    public static void nullify() {
        ObjConfig.modelCache = null;
        ObjConfig.spriteCache = null;
        ObjConfig.pointer = null;
        ObjConfig.cache = null;
        ObjConfig.buffer = null;
    }

    public static void unpack(Archive a) {
        ObjConfig.buffer = new Buffer(a.get("obj.dat", null));
        Buffer b = new Buffer(a.get("obj.idx", null));

        ObjConfig.count = b.readUnsignedShort();
        ObjConfig.pointer = new int[count];

        int position = 2;

        for (int i = 0; i < ObjConfig.count; i++) {
            ObjConfig.pointer[i] = position;
            position += b.readUnsignedShort();
        }

        ObjConfig.cache = new ObjConfig[10];

        for (int i = 0; i < ObjConfig.cache.length; i++) {
            ObjConfig.cache[i] = new ObjConfig();
        }
    }

    public void defaults() {
        modelIndex = 0;
        name = null;
        description = null;
        oldColor = null;
        newColor = null;
        iconDist = 2000;
        iconPitch = 0;
        iconYaw = 0;
        iconRoll = 0;
        iconX = 0;
        iconY = 0;
        stackable = false;
        pilePriority = 1;
        isMembers = false;
        groundAction = null;
        action = null;
        maleModel1 = -1;
        maleModel2 = -1;
        maleOffY = 0;
        femaleModel1 = -1;
        femaleModel2 = -1;
        femaleOffY = 0;
        maleModel3 = -1;
        femaleModel3 = -1;
        maleDialogModel1 = -1;
        maleDialogModel2 = -1;
        femaleDialogModel1 = -1;
        femaleDialogModel2 = -1;
        stackIndex = null;
        stackAmount = null;
        noteItemIndex = -1;
        noteTemplateIndex = -1;
        scaleX = 128;
        scaleY = 128;
        scaleZ = 128;
        brightness = 0;
        specular = 0;
        team = 0;
    }

    public Model getDialogueModel(int gender) {
        int a = maleDialogModel1;
        int b = maleDialogModel2;

        if (gender == 1) {
            a = femaleDialogModel1;
            b = femaleDialogModel2;
        }

        if (a == -1) {
            return null;
        }

        Model mesh = Model.get(a);

        if (b != -1) {
            mesh = new Model(2, new Model[]{mesh, Model.get(b)});
        }

        if (oldColor != null) {
            mesh.setColors(oldColor, newColor);
        }
        return mesh;
    }

    public Model getModel(int count) {
        if (stackIndex != null && count > 1) {
            int index = -1;
            for (int i = 0; i < 10; i++) {
                if (count >= stackAmount[i] && stackAmount[i] != 0) {
                    index = stackIndex[i];
                }
            }
            if (index != -1) {
                return ObjConfig.get(index).getModel(1);
            }
        }

        Model mesh = (Model) modelCache.get(index);

        if (mesh != null) {
            return mesh;
        }

        mesh = Model.get(modelIndex);

        if (mesh == null) {
            return null;
        }

        if (scaleX != 128 || scaleY != 128 || scaleZ != 128) {
            mesh.scale(scaleX, scaleY, scaleZ);
        }

        if (oldColor != null) {
            mesh.setColors(oldColor, newColor);
        }

        mesh.applyLighting(64 + brightness, 768 + specular, -50, -10, -50, true);
        mesh.isClickable = true;
        modelCache.insert(mesh, index);

        return mesh;

    }

    public Model getWidgetMesh() {
        return this.getWidgetMesh(1);
    }

    public Model getWidgetMesh(int count) {
        if (stackIndex != null && count > 1) {
            int stack = -1;
            for (int i = 0; i < 10; i++) {
                if (count >= stackAmount[i] && stackAmount[i] != 0) {
                    stack = stackIndex[i];
                }
            }

            if (stack != -1) {
                return get(stack).getWidgetMesh();
            }
        }

        Model mesh = Model.get(modelIndex);

        if (mesh == null) {
            return null;
        }

        if (oldColor != null) {
            mesh.setColors(oldColor, newColor);
        }

        return mesh;
    }

    public Model getWornMesh(int gender) {
        int i1 = maleModel1;
        int i2 = maleModel2;
        int i3 = maleModel3;

        if (gender == 1) {
            i1 = femaleModel1;
            i2 = femaleModel2;
            i3 = femaleModel3;
        }

        if (i1 == -1) {
            return null;
        }

        Model mesh = Model.get(i1);

        if (i2 != -1) {
            if (i3 != -1) {
                mesh = new Model(3, new Model[]{mesh, Model.get(i2), Model.get(i3)});
            } else {
                mesh = new Model(2, new Model[]{mesh, Model.get(i2)});
            }
        }

        if (gender == 0 && maleOffY != 0) {
            mesh.translate(0, maleOffY, 0);
        }

        if (gender == 1 && femaleOffY != 0) {
            mesh.translate(0, femaleOffY, 0);
        }

        if (oldColor != null) {
            mesh.setColors(oldColor, newColor);
        }
        return mesh;
    }

    public boolean isDialogueModelValid(int gender) {
        int index1 = maleDialogModel1;
        int index2 = maleDialogModel2;

        if (gender == 1) {
            index1 = femaleDialogModel1;
            index2 = femaleDialogModel2;
        }

        if (index1 == -1) {
            return true;
        }

        boolean valid = true;

        if (!Model.isValid(index1)) {
            valid = false;
        }

        if (index2 != -1 && !Model.isValid(index2)) {
            valid = false;
        }

        return valid;
    }

    public boolean isWornMeshValid(int gender) {
        int i1 = maleModel1;
        int i2 = maleModel2;
        int i3 = maleModel3;

        if (gender == 1) {
            i1 = femaleModel1;
            i2 = femaleModel2;
            i3 = femaleModel3;
        }

        if (i1 == -1) {
            return true;
        }

        if (!Model.isValid(i1)) {
            return false;
        }

        if (i2 != -1 && !Model.isValid(i2)) {
            return false;
        }

        return !(i3 != -1 && !Model.isValid(i3));

    }

    public void load(Buffer b) {
        do {
            int i = b.readUnsignedByte();

            if (i == 0) {
                return;
            }

            if (i == 1) {
                modelIndex = (short) b.readUnsignedShort();
            } else if (i == 2) {
                name = b.readString();
            } else if (i == 3) {
                description = b.readString();
            } else if (i == 4) {
                iconDist = (short) b.readUnsignedShort();
            } else if (i == 5) {
                iconPitch = (short) b.readUnsignedShort();
            } else if (i == 6) {
                iconYaw = (short) b.readUnsignedShort();
            } else if (i == 7) {
                int x = b.readUnsignedShort();

                if (x > 32767) {
                    x -= 0x10000;
                }

                iconX = (short) x;
            } else if (i == 8) {
                int y = b.readUnsignedShort();

                if (y > 32767) {
                    y -= 0x10000;
                }

                iconY = (short) y;
            } else if (i == 10) {
                b.readUnsignedShort();
            } else if (i == 11) {
                stackable = true;
            } else if (i == 12) {
                pilePriority = b.readInt();
            } else if (i == 16) {
                isMembers = true;
            } else if (i == 23) {
                maleModel1 = (short) b.readUnsignedShort();
                maleOffY = b.readByte();
            } else if (i == 24) {
                maleModel2 = (short) b.readUnsignedShort();
            } else if (i == 25) {
                femaleModel1 = (short) b.readUnsignedShort();
                femaleOffY = b.readByte();
            } else if (i == 26) {
                femaleModel2 = (short) b.readUnsignedShort();
            } else if (i >= 30 && i < 35) {
                if (groundAction == null) {
                    groundAction = new String[5];
                }

                groundAction[i - 30] = b.readString();
            } else if (i >= 35 && i < 40) {
                if (action == null) {
                    action = new String[5];
                }

                int j = i - 35;

                String s = b.readString();

                if (action[j] == null) {
                    action[j] = s;
                }
            } else if (i == 40) {
                int j = b.readUnsignedByte();
                oldColor = new int[j];
                newColor = new int[j];
                for (int k = 0; k < j; k++) {
                    oldColor[k] = b.readUnsignedShort();
                    newColor[k] = b.readUnsignedShort();
                }

            } else if (i == 78) {
                maleModel3 = (short) b.readUnsignedShort();
            } else if (i == 79) {
                femaleModel3 = (short) b.readUnsignedShort();
            } else if (i == 90) {
                maleDialogModel1 = (short) b.readUnsignedShort();
            } else if (i == 91) {
                femaleDialogModel1 = (short) b.readUnsignedShort();
            } else if (i == 92) {
                maleDialogModel2 = (short) b.readUnsignedShort();
            } else if (i == 93) {
                femaleDialogModel2 = (short) b.readUnsignedShort();
            } else if (i == 95) {
                iconRoll = (short) b.readUnsignedShort();
            } else if (i == 97) {
                noteItemIndex = (short) b.readUnsignedShort();
            } else if (i == 98) {
                noteTemplateIndex = (short) b.readUnsignedShort();
            } else if (i >= 100 && i < 110) {
                if (stackIndex == null) {
                    stackIndex = new short[10];
                    stackAmount = new int[10];
                }
                stackIndex[i - 100] = (short) b.readUnsignedShort();
                stackAmount[i - 100] = b.readUnsignedShort();
            } else if (i == 110) {
                scaleX = (short) b.readUnsignedShort();
            } else if (i == 111) {
                scaleY = (short) b.readUnsignedShort();
            } else if (i == 112) {
                scaleZ = (short) b.readUnsignedShort();
            } else if (i == 113) {
                brightness = b.readByte();
            } else if (i == 114) {
                specular = (short) (b.readByte() * 5);
            } else if (i == 115) {
                team = b.readByte();
            }
        } while (true);
    }

    public void toNote() {
        ObjConfig a = get(noteTemplateIndex);
        modelIndex = a.modelIndex;
        iconDist = a.iconDist;
        iconPitch = a.iconPitch;
        iconYaw = a.iconYaw;
        iconRoll = a.iconRoll;
        iconX = a.iconX;
        iconY = a.iconY;
        oldColor = a.oldColor;
        newColor = a.newColor;

        ObjConfig b = get(noteItemIndex);
        name = b.name;
        isMembers = b.isMembers;
        pilePriority = b.pilePriority;

        StringBuilder s = new StringBuilder().append("Swap this note at any bank for a");

        if (JString.isVowel(b.name, 0)) {
            s.append('n');
        }

        description = s.append(' ').append(b.name).append('.').toString();
        stackable = true;
    }

}
