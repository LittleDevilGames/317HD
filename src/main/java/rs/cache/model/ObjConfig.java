package rs.cache.model;

import rs.Game;
import rs.cache.Archive;
import rs.io.Buffer;
import rs.media.Canvas2D;
import rs.media.Canvas3D;
import rs.media.Sprite;
import rs.node.List;
import rs.node.impl.Renderable;
import rs.scene.model.Model;
import rs.util.JString;

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
    public short index;
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
    public short note_item_index;
    public short note_template_index;
    public int old_color[];
    public int pile_priority;
    public short scale_x;
    public short scale_y;
    public short scale_z;
    public short specular;
    public int stack_amount[];
    public short[] stack_index;
    public boolean stackable;
    public byte team;

    public ObjConfig() {
        index = -1;
    }

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

        if (config.note_template_index != -1) {
            config.to_note();
        }

        if (!Game.isMembers && config.isMembers) {
            config.name = JString.MEMBERS_OBJECT;
            config.description = JString.LOGIN_TO_A_MEMBERS_SERVER_TO_USE_THIS_OBJECT;
            config.groundAction = null;
            config.action = null;
            config.team = 0;
        }

        return config;
    }

    public static Sprite get_sprite(int index, int count, int outline_color) {
        if (outline_color == 0) {
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

        if (c.stack_index == null) {
            count = -1;
        }

        if (count > 1) {
            int i = -1;
            for (int j = 0; j < 10; j++) {
                if (count >= c.stack_amount[j] && c.stack_amount[j] != 0) {
                    i = c.stack_index[j];
                }
            }
            if (i != -1) {
                c = get(i);
            }
        }

        Model m = c.get_model(1);

        if (m == null) {
            return null;
        }

        Sprite s1 = null;

        if (c.note_template_index != -1) {
            s1 = get_sprite(c.note_item_index, 10, -1);

            if (s1 == null) {
                return null;
            }
        }

        Sprite s = new Sprite(32, 32);

        int _center_x = Canvas3D.centerX;
        int _center_y = Canvas3D.centerY;
        int _pixels3d[] = Canvas3D.pixels;
        int _pixels2d[] = Canvas2D.pixels;
        int _width = Canvas2D.width;
        int _height = Canvas2D.height;
        int _x1 = Canvas2D.left_x;
        int _x2 = Canvas2D.right_x;
        int _y1 = Canvas2D.left_y;
        int _y2 = Canvas2D.right_y;

        Canvas3D.texturize = false;
        Canvas2D.prepare(32, 32, s.pixels);
        Canvas2D.fillRect(0, 0, 32, 32, 0);
        Canvas3D.prepare();

        int dist = c.iconDist;

        if (outline_color == -1) {
            dist = (int) ((double) dist * 1.50D);
        }

        if (outline_color > 0) {
            dist = (int) ((double) dist * 1.04D);
        }

        int sin = Canvas3D.sin[c.iconPitch] * dist >> 16;
        int cos = Canvas3D.cos[c.iconPitch] * dist >> 16;

        m.draw(0, c.iconYaw, c.iconRoll, c.iconPitch, c.iconX, sin + (((Renderable) (m)).height / 2) + c.iconY, cos + c.iconY);

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

        if (outline_color > 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if (s.pixels[x + y * 32] == 0) {
                        if (x > 0 && s.pixels[(x - 1) + y * 32] == 1) {
                            s.pixels[x + y * 32] = outline_color;
                        } else if (y > 0 && s.pixels[x + (y - 1) * 32] == 1) {
                            s.pixels[x + y * 32] = outline_color;
                        } else if (x < 31 && s.pixels[x + 1 + y * 32] == 1) {
                            s.pixels[x + y * 32] = outline_color;
                        } else if (y < 31 && s.pixels[x + (y + 1) * 32] == 1) {
                            s.pixels[x + y * 32] = outline_color;
                        }
                    }
                }
            }
        } else if (outline_color == 0) {
            for (int x = 31; x >= 0; x--) {
                for (int y = 31; y >= 0; y--) {
                    if (s.pixels[x + y * 32] == 0 && x > 0 && y > 0 && s.pixels[(x - 1) + (y - 1) * 32] > 0) {
                        s.pixels[x + y * 32] = 0x302020;
                    }
                }
            }
        }

        if (c.note_template_index != -1) {
            int w = s1.cropWidth;
            int h = s1.cropHeight;
            s1.cropWidth = 32;
            s1.cropHeight = 32;
            s1.drawMasked(0, 0);
            s1.cropWidth = w;
            s1.cropHeight = h;
        }

        if (outline_color == 0) {
            spriteCache.insert(s, index);
        }

        Canvas2D.prepare(_width, _height, _pixels2d);
        Canvas2D.setBounds(_x1, _y1, _x2, _y2);
        Canvas3D.centerX = _center_x;
        Canvas3D.centerY = _center_y;
        Canvas3D.pixels = _pixels3d;
        Canvas3D.texturize = true;

        if (c.stackable) {
            s.cropWidth = 33;
        } else {
            s.cropWidth = 32;
        }

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
        old_color = null;
        newColor = null;
        iconDist = 2000;
        iconPitch = 0;
        iconYaw = 0;
        iconRoll = 0;
        iconX = 0;
        iconY = 0;
        stackable = false;
        pile_priority = 1;
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
        stack_index = null;
        stack_amount = null;
        note_item_index = -1;
        note_template_index = -1;
        scale_x = 128;
        scale_y = 128;
        scale_z = 128;
        brightness = 0;
        specular = 0;
        team = 0;
    }

    public Model get_dialogue_model(int gender) {
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

        if (old_color != null) {
            mesh.setColors(old_color, newColor);
        }
        return mesh;
    }

    public Model get_model(int count) {
        if (stack_index != null && count > 1) {
            int index = -1;
            for (int i = 0; i < 10; i++) {
                if (count >= stack_amount[i] && stack_amount[i] != 0) {
                    index = stack_index[i];
                }
            }
            if (index != -1) {
                return ObjConfig.get(index).get_model(1);
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

        if (scale_x != 128 || scale_y != 128 || scale_z != 128) {
            mesh.scale(scale_x, scale_y, scale_z);
        }

        if (old_color != null) {
            mesh.setColors(old_color, newColor);
        }

        mesh.applyLighting(64 + brightness, 768 + specular, -50, -10, -50, true);
        mesh.is_clickable = true;
        modelCache.insert(mesh, index);

        return mesh;

    }

    public Model getWidgetMesh() {
        return this.getWidgetMesh(1);
    }

    public Model getWidgetMesh(int count) {
        if (stack_index != null && count > 1) {
            int stack = -1;
            for (int i = 0; i < 10; i++) {
                if (count >= stack_amount[i] && stack_amount[i] != 0) {
                    stack = stack_index[i];
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

        if (old_color != null) {
            mesh.setColors(old_color, newColor);
        }

        return mesh;
    }

    public Model get_worn_mesh(int gender) {
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

        if (old_color != null) {
            mesh.setColors(old_color, newColor);
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

    public boolean is_worn_mesh_valid(int gender) {
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

        if (i3 != -1 && !Model.isValid(i3)) {
            return false;
        }

        return true;
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
                pile_priority = b.readInt();
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

                for (String s1 : JString.COMMON_OBJ_ACTIONS) {
                    if (s.equals(s1)) {
                        action[j] = s1;
                        break;
                    }
                }

                if (action[j] == null) {
                    action[j] = s;
                }
            } else if (i == 40) {
                int j = b.readUnsignedByte();
                old_color = new int[j];
                newColor = new int[j];
                for (int k = 0; k < j; k++) {
                    old_color[k] = b.readUnsignedShort();
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
                note_item_index = (short) b.readUnsignedShort();
            } else if (i == 98) {
                note_template_index = (short) b.readUnsignedShort();
            } else if (i >= 100 && i < 110) {
                if (stack_index == null) {
                    stack_index = new short[10];
                    stack_amount = new int[10];
                }
                stack_index[i - 100] = (short) b.readUnsignedShort();
                stack_amount[i - 100] = b.readUnsignedShort();
            } else if (i == 110) {
                scale_x = (short) b.readUnsignedShort();
            } else if (i == 111) {
                scale_y = (short) b.readUnsignedShort();
            } else if (i == 112) {
                scale_z = (short) b.readUnsignedShort();
            } else if (i == 113) {
                brightness = b.readByte();
            } else if (i == 114) {
                specular = (short) (b.readByte() * 5);
            } else if (i == 115) {
                team = b.readByte();
            }
        } while (true);
    }

    public void to_note() {
        ObjConfig a = get(note_template_index);
        modelIndex = a.modelIndex;
        iconDist = a.iconDist;
        iconPitch = a.iconPitch;
        iconYaw = a.iconYaw;
        iconRoll = a.iconRoll;
        iconX = a.iconX;
        iconY = a.iconY;
        old_color = a.old_color;
        newColor = a.newColor;

        ObjConfig b = get(note_item_index);
        name = b.name;
        isMembers = b.isMembers;
        pile_priority = b.pile_priority;

        StringBuilder s = new StringBuilder().append("Swap this note at any bank for a");

        if (JString.isVowel(b.name, 0)) {
            s.append('n');
        }

        description = s.append(' ').append(b.name).append('.').toString();
        stackable = true;
    }

}
