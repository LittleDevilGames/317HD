package rs.cache.impl;

import rs.Game;
import rs.Signlink;
import rs.cache.Archive;
import rs.cache.model.ActorConfig;
import rs.cache.model.ObjConfig;
import rs.input.Mouse;
import rs.io.Buffer;
import rs.media.BitmapFont;
import rs.media.Canvas2D;
import rs.media.Canvas3D;
import rs.media.Sprite;
import rs.node.List;
import rs.scene.model.Model;
import rs.util.JString;

import java.io.File;

public class Widget {

    public static Widget[] instance;
    public static List model_cache = new List(30);
    public static List sprite_cache;
    public int actionType;
    public boolean centered;
    public short childX[];
    public short childY[];
    public short children[];
    public int color_hover_disabled;
    public int color_hover_enabled;
    public boolean filled;
    public BitmapFont font;
    public int height;
    public boolean hidden;
    public int hover_index;
    public Sprite image_disabled;
    public Sprite image_enabled;
    public short index;
    public String itemActions[];
    public int itemCount[];
    public short itemIndex[];
    public short itemMarginX;
    public short itemMarginY;
    public short itemSlotX[];
    public short itemSlotY[];
    public boolean items_draggable;
    public boolean itemsHaveActions;
    public boolean items_swappable;
    public boolean itemsUsable;
    public String messageDisabled;
    public String message_enabled;
    public int modelIndexDisabled;
    public int model_index_enabled;
    public int modelPitch;
    public int modelTypeDisabled;
    public int model_type_enabled;
    public int modelYaw;
    public int modelZoom;
    public byte opacity;
    public String option;
    public int option_action;
    public String option_prefix;
    public String option_suffix;
    public byte optionType;
    public short parent;
    public int rgbDisabled;
    public int rgb_enabled;
    public Script[] script;
    public int scrollAmount;
    public int scroll_height;
    public int seqIndexDisabled;
    public int seq_index_enabled;
    public int sequenceCycle;
    public int sequenceFrame;
    public boolean shadow;
    public Sprite slot_image[];
    public byte type;
    public boolean visible = true;
    public int width;
    public short x;
    public short y;

    public static void draw(int index, int x, int y, int scroll_amount) {
        Widget w = Widget.get(index);
        if (w != null) {
            w.draw(x, y, scroll_amount);
        }
    }

    public static Widget get(int i) {
        if (i < 0 || i >= instance.length) {
            return null;
        }
        return instance[i];
    }

    public static Sprite get_sprite(Archive a, String name, int index) {
        long uid = (JString.hash(name) << 8) + (long) index;
        Sprite s = (Sprite) sprite_cache.get(uid);

        if (s != null) {
            return s;
        }

        try {
            s = new Sprite(a, name, index);
            sprite_cache.insert(s, uid);
        } catch (Exception _ex) {
            return null;
        }

        return s;
    }

    public static boolean handleSequences(int cycle, int widget_index) {
        Widget w = Widget.get(widget_index);
        if (w != null) {
            w.handleSequences(cycle);
        }
        return false;
    }

    private static void load_clientscript(Widget w, Buffer b) {
        int count = b.readUnsignedByte();

        byte[] compare_type = null;
        int[] compare_value = null;

        if (count > 0) {
            compare_type = new byte[count];
            compare_value = new int[count];

            for (int i = 0; i < count; i++) {
                compare_type[i] = b.readByte();
                compare_value[i] = b.readUnsignedShort();
            }
        }

        count = b.readUnsignedByte();

        if (count > 0) {
            w.script = new Script[count];

            for (int i = 0; i < count; i++) {
                Script s = new Script(w, b, i);

                if (compare_type != null && i < compare_type.length) {
                    s.compare_type = compare_type[i];
                    s.compare_value = compare_value[i];
                }

                w.script[i] = s;
            }
        }
    }

    public static void resetAnimations(int index) {
        Widget w = Widget.get(index);
        if (w != null) {
            w.reset_sequence();
        }
    }

    public static void set_mesh(int type, int index, Model m) {
        model_cache.clear();
        if (m != null && type != 4) {
            model_cache.insert(m, (type << 16) + index);
        }
    }

    public static void unpack(Archive archive, BitmapFont[] fonts, Archive media) {
        Widget.sprite_cache = new List(50000);
        Buffer b = new Buffer(archive.get("data"));
        Widget.instance = new Widget[b.readUnsignedShort()];

        File cs1 = new File(Signlink.getCachePath() + "./cs1/");
        cs1.mkdirs();

        int parent = -1;
        while (b.position < b.payload.length) {
            int index = b.readUnsignedShort();

            if (index == 65535) {
                parent = b.readUnsignedShort();
                index = b.readUnsignedShort();
            }

            Widget w = instance[index] = new Widget();

            w.index = (short) index;
            w.parent = (short) parent;
            w.type = b.readByte();
            w.optionType = b.readByte();
            w.actionType = b.readUnsignedShort();
            w.width = b.readUnsignedShort();
            w.height = b.readUnsignedShort();
            w.opacity = (byte) b.readUnsignedByte();
            w.hover_index = b.readUnsignedByte();

            if (w.hover_index != 0) {
                w.hover_index = (w.hover_index - 1 << 8) + b.readUnsignedByte();
            } else {
                w.hover_index = -1;
            }

            load_clientscript(w, b);

            if (w.type == 0) {
                w.scroll_height = b.readUnsignedShort();
                w.hidden = b.readByte() == 1;

                int count = b.readUnsignedShort();
                w.children = new short[count];
                w.childX = new short[count];
                w.childY = new short[count];

                for (int i = 0; i < count; i++) {
                    w.children[i] = (short) b.readUnsignedShort();
                    w.childX[i] = (short) b.readUnsignedShort();
                    w.childY[i] = (short) b.readUnsignedShort();
                }
            }

            if (w.type == 2) {
                w.itemIndex = new short[w.width * w.height];
                w.itemCount = new int[w.width * w.height];
                w.items_draggable = b.readByte() == 1;
                w.itemsHaveActions = b.readByte() == 1;
                w.itemsUsable = b.readByte() == 1;
                w.items_swappable = b.readByte() == 1;
                w.itemMarginX = (short) b.readUnsignedByte();
                w.itemMarginY = (short) b.readUnsignedByte();
                w.itemSlotX = new short[20];
                w.itemSlotY = new short[20];
                w.slot_image = new Sprite[20];

                for (int i = 0; i < 20; i++) {
                    if (b.readUnsignedByte() == 1) {
                        w.itemSlotX[i] = (short) b.readUnsignedShort();
                        w.itemSlotY[i] = (short) b.readUnsignedShort();
                        String str = b.readString();

                        if (media != null && str.length() > 0) {
                            int j = str.lastIndexOf(',');
                            w.slot_image[i] = Widget.get_sprite(media, str.substring(0, j), Integer.parseInt(str.substring(j + 1)));
                        }
                    }
                }

                w.itemActions = new String[5];
                for (int i = 0; i < 5; i++) {
                    w.itemActions[i] = b.readString();
                    if (w.itemActions[i].length() == 0) {
                        w.itemActions[i] = null;
                    }
                }

            }

            if (w.type == 3) {
                w.filled = b.readByte() == 1;
            }

            if (w.type == 4 || w.type == 1) {
                w.centered = b.readByte() == 1;
                int font = b.readUnsignedByte();
                if (fonts != null) {
                    w.font = fonts[font];
                }
                w.shadow = b.readByte() == 1;
            }

            if (w.type == 4) {
                w.messageDisabled = b.readString();
                w.message_enabled = b.readString();
            }

            if (w.type == 1 || w.type == 3 || w.type == 4) {
                w.rgbDisabled = b.readInt();
            }

            if (w.type == 3 || w.type == 4) {
                w.rgb_enabled = b.readInt();
                w.color_hover_disabled = b.readInt();
                w.color_hover_enabled = b.readInt();
            }

            if (w.type == 5) {
                String str = b.readString();

                if (media != null && str.length() > 0) {
                    int i = str.lastIndexOf(',');
                    w.image_disabled = get_sprite(media, str.substring(0, i), Integer.parseInt(str.substring(i + 1)));
                }

                str = b.readString();

                if (media != null && str.length() > 0) {
                    int i = str.lastIndexOf(',');
                    w.image_enabled = get_sprite(media, str.substring(0, i), Integer.parseInt(str.substring(i + 1)));
                }
            }

            if (w.type == 6) {
                int i = b.readUnsignedByte();
                if (i != 0) {
                    w.modelTypeDisabled = 1;
                    w.modelIndexDisabled = (i - 1 << 8) + b.readUnsignedByte();
                }

                i = b.readUnsignedByte();
                if (i != 0) {
                    w.model_type_enabled = 1;
                    w.model_index_enabled = (i - 1 << 8) + b.readUnsignedByte();
                }

                i = b.readUnsignedByte();
                if (i != 0) {
                    w.seqIndexDisabled = (i - 1 << 8) + b.readUnsignedByte();
                } else {
                    w.seqIndexDisabled = -1;
                }

                i = b.readUnsignedByte();
                if (i != 0) {
                    w.seq_index_enabled = (i - 1 << 8) + b.readUnsignedByte();
                } else {
                    w.seq_index_enabled = -1;
                }

                w.modelZoom = b.readUnsignedShort();
                w.modelPitch = b.readUnsignedShort();
                w.modelYaw = b.readUnsignedShort();
            }

            if (w.type == 7) {
                w.itemIndex = new short[w.width * w.height];
                w.itemCount = new int[w.width * w.height];
                w.centered = b.readByte() == 1;
                w.font = fonts[b.readUnsignedByte()];
                w.shadow = b.readByte() == 1;
                w.rgbDisabled = b.readInt();
                w.itemMarginX = (short) b.readUnsignedShort();
                w.itemMarginY = (short) b.readUnsignedShort();
                w.itemsHaveActions = b.readByte() == 1;
                w.itemActions = new String[5];

                for (int i = 0; i < 5; i++) {
                    w.itemActions[i] = b.readString();
                    if (w.itemActions[i].length() == 0) {
                        w.itemActions[i] = null;
                    }
                }
            }

            if (w.optionType == 2 || w.type == 2) {
                w.option_prefix = b.readString();
                w.option_suffix = b.readString();
                w.option_action = b.readUnsignedShort();
            }

            if (w.optionType == 1 || w.optionType == 4 || w.optionType == 5 || w.optionType == 6) {
                w.option = b.readString();
                if (w.option.length() == 0) {
                    if (w.optionType == 1) {
                        w.option = JString.OK;
                    }
                    if (w.optionType == 4) {
                        w.option = JString.SELECT;
                    }
                    if (w.optionType == 5) {
                        w.option = JString.SELECT;
                    }
                    if (w.optionType == 6) {
                        w.option = JString.CONTINUE;
                    }
                }
            }
        }

        Widget.sprite_cache = null;
    }

    public void draw(int x, int y) {
        switch (this.type) {
            case 2: {
                int slot = 0;

                for (int slot_y = 0; slot_y < this.height; slot_y++) {
                    for (int slot_x = 0; slot_x < this.width; slot_x++) {
                        int draw_x = x + slot_x * (32 + this.itemMarginX);
                        int draw_y = y + slot_y * (32 + this.itemMarginY);

                        if (slot < 20) {
                            draw_x += this.itemSlotX[slot];
                            draw_y += this.itemSlotY[slot];
                        }

                        if (this.itemIndex[slot] > 0) {
                            int drag_dx = 0;
                            int drag_dy = 0;
                            int item_index = this.itemIndex[slot] - 1;

                            if (draw_x > Canvas2D.left_x - 32 && draw_x < Canvas2D.right_x && draw_y > Canvas2D.left_y - 32 && draw_y < Canvas2D.right_y || Game.dragArea != 0 && Game.dragSlot == slot) {
                                int outline_rgb = 0;

                                if (Game.selectedItem && Game.selectedItemSlot == slot && Game.selectedItemWidget == this.index) {
                                    outline_rgb = 0xFFFFFF;
                                }

                                Sprite s = ObjConfig.get_sprite(item_index, this.itemCount[slot], outline_rgb);

                                if (s != null) {
                                    if (Game.dragArea != 0 && Game.dragSlot == slot && Game.dragWidget == this.index) {

                                        drag_dx = Mouse.lastX - Game.dragStartX;
                                        drag_dy = Mouse.lastY - Game.dragStartY;

                                        if (drag_dx < 5 && drag_dx > -5) {
                                            drag_dx = 0;
                                        }

                                        if (drag_dy < 5 && drag_dy > -5) {
                                            drag_dy = 0;
                                        }

                                        if (Game.dragCycle < 5) {
                                            drag_dx = 0;
                                            drag_dy = 0;
                                        }

                                        s.draw(draw_x + drag_dx, draw_y + drag_dy, 128);

                                        if (draw_y + drag_dy < Canvas2D.left_y && this.scrollAmount > 0) {
                                            int scroll_dec = (Game.animCycle * (Canvas2D.left_y - draw_y - drag_dy)) / 3;

                                            if (scroll_dec > Game.animCycle * 10) {
                                                scroll_dec = Game.animCycle * 10;
                                            }

                                            if (scroll_dec > this.scrollAmount) {
                                                scroll_dec = this.scrollAmount;
                                            }

                                            this.scrollAmount -= scroll_dec;
                                            Game.dragStartY += scroll_dec;
                                        }

                                        if (draw_y + drag_dy + 32 > Canvas2D.right_y && this.scrollAmount < this.scroll_height - this.height) {
                                            int scroll_inc = (Game.animCycle * ((draw_y + drag_dy + 32) - Canvas2D.right_y)) / 3;

                                            if (scroll_inc > Game.animCycle * 10) {
                                                scroll_inc = Game.animCycle * 10;
                                            }

                                            if (scroll_inc > this.scroll_height - this.height - this.scrollAmount) {
                                                scroll_inc = this.scroll_height - this.height - this.scrollAmount;
                                            }

                                            this.scrollAmount += scroll_inc;
                                            Game.dragStartY -= scroll_inc;
                                        }
                                    } else if (Game.clickArea != 0 && Game.clickedItemSlot == slot && Game.clickedItemWidget == this.index) {
                                        s.draw(draw_x, draw_y, 128);
                                    } else {
                                        s.drawMasked(draw_x, draw_y);
                                    }

                                    if (s.cropWidth == 33 || this.itemCount[slot] != 1) {
                                        int count = this.itemCount[slot];
                                        String s1 = null;

                                        if (count >= 10_000_000) {
                                            s1 = String.valueOf(count / 1_000_000) + 'M';
                                        } else if (count >= 100_000) {
                                            s1 = String.valueOf(count / 1_000) + 'K';
                                        } else {
                                            s1 = String.valueOf(count);
                                        }

                                        if (s1 == null) {
                                            s1 = "-2";
                                        }

                                        BitmapFont.SMALL.draw(s1, draw_x + drag_dx, draw_y + drag_dy + 9, 0xFFFF00, BitmapFont.SHADOW);
                                    }
                                }
                            }

                        } else if (this.slot_image != null && slot < 20) {
                            Sprite s = this.slot_image[slot];

                            if (s != null) {
                                s.drawMasked(draw_x, draw_y);
                            }
                        }

                        slot++;
                    }
                }
                return;
            }

            case 3: {
                boolean hovered = false;

                if (Game.hoveredChatWidget == this.index || Game.hoveredTabWidget == this.index || Game.hoveredViewportWidget == this.index) {
                    hovered = true;
                }

                int color;

                if (this.is_enabled()) {
                    color = this.rgb_enabled;
                    if (hovered && this.color_hover_enabled != 0) {
                        color = this.color_hover_enabled;
                    }
                } else {
                    color = this.rgbDisabled;
                    if (hovered && this.color_hover_disabled != 0) {
                        color = this.color_hover_disabled;
                    }
                }

                if (this.opacity == 0) {
                    if (this.filled) {
                        Canvas2D.fillRect(x, y, this.width, this.height, color);
                    } else {
                        Canvas2D.drawRect(x, y, this.width, this.height, color);
                    }
                } else if (this.filled) {
                    Canvas2D.fillRect(x, y, this.width, this.height, color, 256 - (this.opacity & 0xFF));
                } else {
                    Canvas2D.drawRect(x, y, this.width, this.height, color, 256 - (this.opacity & 0xFF));
                }
                return;
            }

            case 4: {
                BitmapFont f = this.font;
                String s = this.messageDisabled;

                boolean hovered = false;

                if (Game.hoveredChatWidget == this.index || Game.hoveredTabWidget == this.index || Game.hoveredViewportWidget == this.index) {
                    hovered = true;
                }

                int rgb;

                if (this.is_enabled()) {
                    rgb = this.rgb_enabled;
                    if (hovered && this.color_hover_enabled != 0) {
                        rgb = this.color_hover_enabled;
                    }
                    if (this.message_enabled.length() > 0) {
                        s = this.message_enabled;
                    }
                } else {
                    rgb = this.rgbDisabled;
                    if (hovered && this.color_hover_disabled != 0) {
                        rgb = this.color_hover_disabled;
                    }
                }

                if (this.optionType == 6 && Game.dialogueOptionActive) {
                    s = JString.PLEASE_WAIT;
                    rgb = this.rgbDisabled;
                }

                if (Canvas2D.width == 479) {
                    if (rgb == 0xFFFF00) {
                        rgb = 0x0000FF;
                    }
                    if (rgb == 0x00C000) {
                        rgb = 0xFFFFFF;
                    }
                }

                for (int draw_y = y + f.height; s.length() > 0; draw_y += f.height) {
                    if (s.indexOf('%') != -1) {
                        for (int j = 1; j < 5; j++) {
                            do {
                                int k = s.indexOf("%" + j);

                                if (k == -1) {
                                    break;
                                }

                                int value = -2;

                                if (this.script != null && j - 1 < this.script.length) {
                                    Script scr = this.script[j - 1];

                                    if (scr != null) {
                                        value = scr.execute();
                                    }
                                }

                                s = s.substring(0, k) + (value > 999_999_999 ? '*' : value) + s.substring(k + 2);
                            } while (true);
                        }
                    }

                    int separator = s.indexOf("\\n");
                    String message;

                    if (separator != -1) {
                        message = s.substring(0, separator);
                        s = s.substring(separator + 2);
                    } else {
                        message = s;
                        s = "";
                    }

                    if (this.centered) {
                        f.draw(message, x + (this.width / 2), draw_y, rgb, (this.shadow ? BitmapFont.SHADOW : 0) | BitmapFont.CENTER | BitmapFont.ALLOW_TAGS);
                    } else {
                        f.draw(message, x, draw_y, rgb, (this.shadow ? BitmapFont.SHADOW : 0) + BitmapFont.ALLOW_TAGS);
                    }
                }
                return;
            }

            case 5: {
                Sprite s;

                if (this.is_enabled()) {
                    s = this.image_enabled;
                } else {
                    s = this.image_disabled;
                }

                if (s != null) {
                    s.drawMasked(x, y);
                }
                return;
            }

            case 6: {
                int center_x = Canvas3D.centerX;
                int center_y = Canvas3D.centerY;

                Canvas3D.centerX = x + this.width / 2;
                Canvas3D.centerY = y + this.height / 2;

                int sin = Canvas3D.sin[this.modelPitch] * this.modelZoom >> 16;
                int cos = Canvas3D.cos[this.modelPitch] * this.modelZoom >> 16;

                boolean enabled = this.is_enabled();
                int anim_index = enabled ? this.seq_index_enabled : this.seqIndexDisabled;

                Model m;

                if (anim_index == -1) {
                    m = this.get_mesh(-1, -1, enabled);
                } else {
                    Sequence s = Sequence.instance[anim_index];
                    m = this.get_mesh(s.framePrimary[this.sequenceFrame], s.frame_secondary[this.sequenceFrame], enabled);
                }

                if (m != null) {
                    m.draw(0, this.modelYaw, 0, this.modelPitch, 0, sin, cos);
                }

                Canvas3D.centerX = center_x;
                Canvas3D.centerY = center_y;
                return;
            }

            case 7: {
                BitmapFont rsf = this.font;
                int slot = 0;

                for (int slot_y = 0; slot_y < this.height; slot_y++) {
                    for (int slot_x = 0; slot_x < this.width; slot_x++) {
                        if (this.itemIndex[slot] > 0) {
                            ObjConfig oc = ObjConfig.get(this.itemIndex[slot] - 1);
                            String message = oc.name;

                            if (oc.stackable || this.itemCount[slot] != 1) {
                                message = message + " x" + Game.formatItemAmount(this.itemCount[slot]);
                            }

                            int draw_x = x + slot_x * (115 + this.itemMarginX);
                            int draw_y = y + slot_y * (12 + this.itemMarginY);

                            if (this.centered) {
                                rsf.draw(message, draw_x + this.width / 2, draw_y, this.rgbDisabled, this.shadow ? BitmapFont.SHADOW : 0);
                            } else {
                                rsf.drawString(message, draw_x, draw_y, this.rgbDisabled, this.shadow, false);
                            }
                        }
                        slot++;
                    }
                }
                return;
            }
        }
    }

    public void draw(int x, int y, int scroll_amount) {
        if (!this.visible) {
            return;
        }

        if (this.type != 0) {
            this.draw(x, y);
            return;
        }

        if (this.children == null) {
            return;
        }

        if (this.hidden && Game.hoveredViewportWidget != this.index && Game.hoveredTabWidget != this.index && Game.hoveredChatWidget != this.index) {
            return;
        }

        int _x1 = Canvas2D.left_x;
        int _y1 = Canvas2D.left_y;
        int _x2 = Canvas2D.right_x;
        int _y2 = Canvas2D.right_y;

        Canvas2D.setBounds(x, y, x + this.width, y + this.height);

        for (int i = 0; i < this.children.length; i++) {
            int child_x = this.childX[i] + x;
            int child_y = (this.childY[i] + y) - scroll_amount;

            Widget child = Widget.instance[this.children[i]];

            if (!child.visible) {
                continue;
            }

            child_x += child.x;
            child_y += child.y;

            if (child.actionType > 0) {
                Game.updateWidget(child);
            }

            if (child.type == 0) {
                if (child.scrollAmount > child.scroll_height - child.height) {
                    child.scrollAmount = child.scroll_height - child.height;
                }

                if (child.scrollAmount < 0) {
                    child.scrollAmount = 0;
                }

                child.draw(child_x, child_y, child.scrollAmount);

                if (child.scroll_height > child.height) {
                    Game.draw_scrollbar(child_x + child.width, child_y, child.height, child.scroll_height, child.scrollAmount);
                }
            } else {
                child.draw(child_x, child_y);
            }
        }

        Canvas2D.setBounds(_x1, _y1, _x2, _y2);
    }

    public Model get_mesh(int type, int index) {
        Model m = (Model) model_cache.get((type << 16) + index);

        if (m != null) {
            return m;
        }

        if (type == 1) {
            m = Model.get(index);
        } else if (type == 2) {
            m = ActorConfig.get(index).get_dialog_model();
        } else if (type == 3) {
            m = Game.self.get_dialog_model();
        } else if (type == 4) {
            m = ObjConfig.get(index).get_widget_mesh(50);
        } else if (type == 5) {
            m = null;
        }

        if (m != null) {
            model_cache.insert(m, (type << 16) + index);
        }
        return m;
    }

    public Model get_mesh(int frame1, int frame2, boolean enabled) {
        Model m;

        if (enabled) {
            m = get_mesh(this.model_type_enabled, this.model_index_enabled);
        } else {
            m = get_mesh(this.modelTypeDisabled, this.modelIndexDisabled);
        }

        if (m == null) {
            return null;
        }

        if (frame1 == -1 && frame2 == -1 && m.triangle_color == null) {
            return m;
        }

        m = new Model(true, (frame1 == -1) & (frame2 == -1), false, m);

        if (frame1 != -1 || frame2 != -1) {
            m.applyVertexWeights();
        }

        if (frame1 != -1) {
            m.applySequenceFrame(frame1);
        }

        if (frame2 != -1) {
            m.applySequenceFrame(frame2);
        }

        m.applyLighting(64, 768, -50, -10, -50, true);
        return m;
    }

    public boolean handleSequences(int cycle) {
        boolean update = false;

        if (this.children == null) {
            return false;
        }

        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i] == -1) {
                break;
            }

            Widget w = Widget.instance[this.children[i]];

            if (w.type == 1) {
                update |= w.handleSequences(cycle);
            }

            if (w.type == 6 && (w.seqIndexDisabled != -1 || w.seq_index_enabled != -1)) {
                int seq_index = w.is_enabled() ? w.seq_index_enabled : w.seqIndexDisabled;

                if (seq_index != -1) {
                    Sequence sequence = Sequence.instance[seq_index];
                    for (w.sequenceCycle += cycle; w.sequenceCycle > sequence.getFrameLength(w.sequenceFrame); ) {
                        w.sequenceCycle -= sequence.getFrameLength(w.sequenceFrame) + 1;
                        w.sequenceFrame++;
                        if (w.sequenceFrame >= sequence.frame_count) {
                            w.sequenceFrame -= sequence.padding;
                            if (w.sequenceFrame < 0 || w.sequenceFrame >= sequence.frame_count) {
                                w.sequenceFrame = 0;
                            }
                        }
                        update = true;
                    }
                }
            }
        }

        return update;
    }

    public boolean is_enabled() {
        if (this.script == null || this.script.length == 0) {
            return false;
        }

        for (Script s : this.script) {
            int a = s.execute();
            int b = s.compare_value;

            switch (s.compare_type) {
                case 2:
                    if (a >= b) {
                        return false;
                    }
                    break;
                case 3:
                    if (a <= b) {
                        return false;
                    }
                    break;
                case 4:
                    if (a == b) {
                        return false;
                    }
                    break;
                default:
                    if (a != b) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public void reset_sequence() {
        if (this.children == null) {
            return;
        }

        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i] == -1) {
                break;
            }

            Widget w = Widget.get(this.children[i]);

            if (w.type == 1) {
                w.reset_sequence();
            }

            w.sequenceFrame = 0;
            w.sequenceCycle = 0;
        }
    }

    public void scroll(int amount) {
        this.scrollAmount += (this.scroll_height / 30.25D) * amount;

        if (this.scrollAmount < 0) {
            this.scrollAmount = 0;
        }

        int max = this.scroll_height - this.height;

        if (this.scrollAmount > max) {
            this.scrollAmount = max;
        }
    }

    public void swapSlots(int from, int to) {
        int original = itemIndex[from];
        itemIndex[from] = itemIndex[to];
        itemIndex[to] = (short) original;
        original = itemCount[from];
        itemCount[from] = itemCount[to];
        itemCount[to] = original;
    }

}
