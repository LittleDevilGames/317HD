package info.demmonic.hdrs.cache.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.Signlink;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.cache.model.ActorConfig;
import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.media.BitmapFont;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.media.Sprite;
import info.demmonic.hdrs.node.List;
import info.demmonic.hdrs.scene.model.Model;
import info.demmonic.hdrs.util.JString;
import info.demmonic.hdrs.util.MathUtils;

import java.io.File;

public class Widget {

    public static Widget[] instance;
    public static List modelCache = new List(30);
    public static List spriteCache;
    public int actionType;
    public boolean centered;
    public short childX[];
    public short childY[];
    public short children[];
    public int colorHoverDisabled;
    public int colorHoverEnabled;
    public boolean filled;
    public BitmapFont font;
    public int height;
    public boolean hidden;
    public int hoverIndex;
    public Sprite imageDisabled;
    public Sprite imageEnabled;
    public short index;
    public String itemActions[];
    public int itemCount[];
    public short itemIndex[];
    public short itemMarginX;
    public short itemMarginY;
    public short itemSlotX[];
    public short itemSlotY[];
    public boolean itemsDraggable;
    public boolean itemsHaveActions;
    public boolean itemsSwappable;
    public boolean itemsUsable;
    public String messageDisabled;
    public String messageEnabled;
    public int modelIndexDisabled;
    public int modelIndexEnabled;
    public int modelPitch;
    public int modelTypeDisabled;
    public int modelTypeEnabled;
    public int modelYaw;
    public int modelZoom;
    public byte opacity;
    public String option;
    public int optionAction;
    public String optionPrefix;
    public String optionSuffix;
    public byte optionType;
    public short parent;
    public int rgbDisabled;
    public int rgbEnabled;
    public Script[] script;
    public int scrollAmount;
    public int scrollHeight;
    public int seqIndexDisabled;
    public int seqIndexEnabled;
    public int sequenceCycle;
    public int sequenceFrame;
    public boolean shadow;
    public Sprite slotImage[];
    public byte type;
    public boolean visible = true;
    public int width;
    public short x;
    public short y;

    public static void draw(int index, int x, int y, int scrollAmount) {
        Widget w = Widget.get(index);
        if (w != null) {
            w.draw(x, y, scrollAmount);
        }
    }

    public static Widget get(int i) {
        if (i < 0 || i >= instance.length) {
            return null;
        }
        return instance[i];
    }

    public static Sprite getSprite(Archive a, String name, int index) {
        long uid = (JString.hash(name) << 8) + (long) index;
        Sprite s = (Sprite) spriteCache.get(uid);

        if (s != null) {
            return s;
        }

        try {
            s = new Sprite(a, name, index);
            spriteCache.insert(s, uid);
        } catch (Exception _ex) {
            return null;
        }

        return s;
    }

    public static boolean handleSequences(int cycle, int widgetIndex) {
        Widget w = Widget.get(widgetIndex);
        if (w != null) {
            w.handleSequences(cycle);
        }
        return false;
    }

    private static void loadClientscript(Widget w, Buffer b) {
        int count = b.readUnsignedByte();

        byte[] compareType = null;
        int[] compareValue = null;

        if (count > 0) {
            compareType = new byte[count];
            compareValue = new int[count];

            for (int i = 0; i < count; i++) {
                compareType[i] = b.readByte();
                compareValue[i] = b.readUnsignedShort();
            }
        }

        count = b.readUnsignedByte();

        if (count > 0) {
            w.script = new Script[count];

            for (int i = 0; i < count; i++) {
                Script s = new Script(w, b, i);

                if (compareType != null && i < compareType.length) {
                    s.compareType = compareType[i];
                    s.compareValue = compareValue[i];
                }

                w.script[i] = s;
            }
        }
    }

    public static void resetAnimations(int index) {
        Widget w = Widget.get(index);
        if (w != null) {
            w.resetSequence();
        }
    }

    public static void setMesh(int type, int index, Model m) {
        modelCache.clear();
        if (m != null && type != 4) {
            modelCache.insert(m, (type << 16) + index);
        }
    }

    public static void unpack(Archive archive, BitmapFont[] fonts, Archive media) {
        Widget.spriteCache = new List(50000);
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
            w.hoverIndex = b.readUnsignedByte();

            if (w.hoverIndex != 0) {
                w.hoverIndex = (w.hoverIndex - 1 << 8) + b.readUnsignedByte();
            } else {
                w.hoverIndex = -1;
            }

            loadClientscript(w, b);

            if (w.type == 0) {
                w.scrollHeight = b.readUnsignedShort();
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

            if (w.type == 1) {
                b.readUnsignedShort();
                b.readUnsignedByte();
            }

            if (w.type == 2) {
                w.itemIndex = new short[w.width * w.height];
                w.itemCount = new int[w.width * w.height];
                w.itemsDraggable = b.readByte() == 1;
                w.itemsHaveActions = b.readByte() == 1;
                w.itemsUsable = b.readByte() == 1;
                w.itemsSwappable = b.readByte() == 1;
                w.itemMarginX = (short) b.readUnsignedByte();
                w.itemMarginY = (short) b.readUnsignedByte();
                w.itemSlotX = new short[20];
                w.itemSlotY = new short[20];
                w.slotImage = new Sprite[20];

                for (int i = 0; i < 20; i++) {
                    if (b.readUnsignedByte() == 1) {
                        w.itemSlotX[i] = (short) b.readUnsignedShort();
                        w.itemSlotY[i] = (short) b.readUnsignedShort();
                        String str = b.readString();

                        if (media != null && str.length() > 0) {
                            int j = str.lastIndexOf(',');
                            w.slotImage[i] = Widget.getSprite(media, str.substring(0, j), Integer.parseInt(str.substring(j + 1)));
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
                w.messageEnabled = b.readString();
            }

            if (w.type == 1 || w.type == 3 || w.type == 4) {
                w.rgbDisabled = b.readInt();
            }

            if (w.type == 3 || w.type == 4) {
                w.rgbEnabled = b.readInt();
                w.colorHoverDisabled = b.readInt();
                w.colorHoverEnabled = b.readInt();
            }

            if (w.type == 5) {
                String str = b.readString();

                if (media != null && str.length() > 0) {
                    int i = str.lastIndexOf(',');
                    w.imageDisabled = getSprite(media, str.substring(0, i), Integer.parseInt(str.substring(i + 1)));
                }

                str = b.readString();

                if (media != null && str.length() > 0) {
                    int i = str.lastIndexOf(',');
                    w.imageEnabled = getSprite(media, str.substring(0, i), Integer.parseInt(str.substring(i + 1)));
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
                    w.modelTypeEnabled = 1;
                    w.modelIndexEnabled = (i - 1 << 8) + b.readUnsignedByte();
                }

                i = b.readUnsignedByte();
                if (i != 0) {
                    w.seqIndexDisabled = (i - 1 << 8) + b.readUnsignedByte();
                } else {
                    w.seqIndexDisabled = -1;
                }

                i = b.readUnsignedByte();
                if (i != 0) {
                    w.seqIndexEnabled = (i - 1 << 8) + b.readUnsignedByte();
                } else {
                    w.seqIndexEnabled = -1;
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
                w.optionPrefix = b.readString();
                w.optionSuffix = b.readString();
                w.optionAction = b.readUnsignedShort();
            }
            if (w.type == 8) {
                w.messageDisabled = b.readString();
            }

            if (w.optionType == 1 || w.optionType == 4 || w.optionType == 5 || w.optionType == 6) {
                w.option = b.readString();
                if (w.option.length() == 0) {
                    if (w.optionType == 1) {
                        w.option = "Ok";
                    }
                    if (w.optionType == 4) {
                        w.option = "Select";
                    }
                    if (w.optionType == 5) {
                        w.option = "Select";
                    }
                    if (w.optionType == 6) {
                        w.option = "Continue";
                    }
                }
            }
        }

        Widget.spriteCache = null;
    }

    public void draw(int x, int y) {
        switch (this.type) {
            case 2: {
                int slot = 0;

                for (int slotY = 0; slotY < this.height; slotY++) {
                    for (int slotX = 0; slotX < this.width; slotX++) {
                        int drawX = x + slotX * (32 + this.itemMarginX);
                        int drawY = y + slotY * (32 + this.itemMarginY);

                        if (slot < 20) {
                            drawX += this.itemSlotX[slot];
                            drawY += this.itemSlotY[slot];
                        }

                        if (this.itemIndex[slot] > 0) {
                            int dragDx = 0;
                            int dragDy = 0;
                            int itemIndex = this.itemIndex[slot] - 1;

                            if (drawX > Canvas2D.leftX - 32 && drawX < Canvas2D.rightX && drawY > Canvas2D.leftY - 32 && drawY < Canvas2D.rightY || Game.dragArea != 0 && Game.dragSlot == slot) {
                                int outlineRgb = 0;

                                if (Game.selectedItem && Game.selectedItemSlot == slot && Game.selectedItemWidget == this.index) {
                                    outlineRgb = 0xFFFFFF;
                                }

                                Sprite s = ObjConfig.getSprite(itemIndex, this.itemCount[slot], outlineRgb);

                                if (s != null) {
                                    if (Game.dragArea != 0 && Game.dragSlot == slot && Game.dragWidget == this.index) {

                                        dragDx = Mouse.lastX - Game.dragStartX;
                                        dragDy = Mouse.lastY - Game.dragStartY;

                                        if (dragDx < 5 && dragDx > -5) {
                                            dragDx = 0;
                                        }

                                        if (dragDy < 5 && dragDy > -5) {
                                            dragDy = 0;
                                        }

                                        if (Game.dragCycle < 5) {
                                            dragDx = 0;
                                            dragDy = 0;
                                        }

                                        s.draw(drawX + dragDx, drawY + dragDy, 128);

                                        if (drawY + dragDy < Canvas2D.leftY && this.scrollAmount > 0) {
                                            int scrollDec = (Game.animCycle * (Canvas2D.leftY - drawY - dragDy)) / 3;

                                            if (scrollDec > Game.animCycle * 10) {
                                                scrollDec = Game.animCycle * 10;
                                            }

                                            if (scrollDec > this.scrollAmount) {
                                                scrollDec = this.scrollAmount;
                                            }

                                            this.scrollAmount -= scrollDec;
                                            Game.dragStartY += scrollDec;
                                        }

                                        if (drawY + dragDy + 32 > Canvas2D.rightY && this.scrollAmount < this.scrollHeight - this.height) {
                                            int scrollInc = (Game.animCycle * ((drawY + dragDy + 32) - Canvas2D.rightY)) / 3;

                                            if (scrollInc > Game.animCycle * 10) {
                                                scrollInc = Game.animCycle * 10;
                                            }

                                            if (scrollInc > this.scrollHeight - this.height - this.scrollAmount) {
                                                scrollInc = this.scrollHeight - this.height - this.scrollAmount;
                                            }

                                            this.scrollAmount += scrollInc;
                                            Game.dragStartY -= scrollInc;
                                        }
                                    } else if (Game.clickArea != 0 && Game.clickedItemSlot == slot && Game.clickedItemWidget == this.index) {
                                        s.draw(drawX, drawY, 128);
                                    } else {
                                        s.drawMasked(drawX, drawY);
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

                                        BitmapFont.SMALL.draw(s1, drawX + dragDx, drawY + dragDy + 9, 0xFFFF00, BitmapFont.SHADOW);
                                    }
                                }
                            }

                        } else if (this.slotImage != null && slot < 20) {
                            Sprite s = this.slotImage[slot];

                            if (s != null) {
                                s.drawMasked(drawX, drawY);
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

                if (this.isEnabled()) {
                    color = this.rgbEnabled;
                    if (hovered && this.colorHoverEnabled != 0) {
                        color = this.colorHoverEnabled;
                    }
                } else {
                    color = this.rgbDisabled;
                    if (hovered && this.colorHoverDisabled != 0) {
                        color = this.colorHoverDisabled;
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

                if (this.isEnabled()) {
                    rgb = this.rgbEnabled;
                    if (hovered && this.colorHoverEnabled != 0) {
                        rgb = this.colorHoverEnabled;
                    }
                    if (this.messageEnabled.length() > 0) {
                        s = this.messageEnabled;
                    }
                } else {
                    rgb = this.rgbDisabled;
                    if (hovered && this.colorHoverDisabled != 0) {
                        rgb = this.colorHoverDisabled;
                    }
                }

                if (this.optionType == 6 && Game.dialogueOptionActive) {
                    s = "Please wait...";
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

                for (int drawY = y + f.height; s.length() > 0; drawY += f.height) {
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
                        f.draw(message, x + (this.width / 2), drawY, rgb, (this.shadow ? BitmapFont.SHADOW : 0) | BitmapFont.CENTER | BitmapFont.ALLOW_TAGS);
                    } else {
                        f.draw(message, x, drawY, rgb, (this.shadow ? BitmapFont.SHADOW : 0) + BitmapFont.ALLOW_TAGS);
                    }
                }
                return;
            }

            case 5: {
                Sprite s;

                if (this.isEnabled()) {
                    s = this.imageEnabled;
                } else {
                    s = this.imageDisabled;
                }

                if (s != null) {
                    s.drawMasked(x, y);
                }
                return;
            }

            case 6: {
                int centerX = Canvas3D.centerX;
                int centerY = Canvas3D.centerY;

                Canvas3D.centerX = x + this.width / 2;
                Canvas3D.centerY = y + this.height / 2;

                int sin = MathUtils.sin[this.modelPitch] * this.modelZoom >> 16;
                int cos = MathUtils.cos[this.modelPitch] * this.modelZoom >> 16;

                boolean enabled = this.isEnabled();
                int animIndex = enabled ? this.seqIndexEnabled : this.seqIndexDisabled;

                Model m;

                if (animIndex == -1) {
                    m = this.getMesh(-1, -1, enabled);
                } else {
                    Sequence s = Sequence.instance[animIndex];
                    m = this.getMesh(s.framePrimary[this.sequenceFrame], s.frameSecondary[this.sequenceFrame], enabled);
                }

                if (m != null) {
                    m.draw(0, this.modelYaw, 0, this.modelPitch, 0, sin, cos);
                }

                Canvas3D.centerX = centerX;
                Canvas3D.centerY = centerY;
                return;
            }

            case 7: {
                BitmapFont rsf = this.font;
                int slot = 0;

                for (int slotY = 0; slotY < this.height; slotY++) {
                    for (int slotX = 0; slotX < this.width; slotX++) {
                        if (this.itemIndex[slot] > 0) {
                            ObjConfig oc = ObjConfig.get(this.itemIndex[slot] - 1);
                            String message = oc.name;

                            if (oc.stackable || this.itemCount[slot] != 1) {
                                message = message + " x" + Game.formatItemAmount(this.itemCount[slot]);
                            }

                            int drawX = x + slotX * (115 + this.itemMarginX);
                            int drawY = y + slotY * (12 + this.itemMarginY);

                            if (this.centered) {
                                rsf.draw(message, drawX + this.width / 2, drawY, this.rgbDisabled, this.shadow ? BitmapFont.SHADOW : 0);
                            } else {
                                rsf.drawString(message, drawX, drawY, this.rgbDisabled, this.shadow, false);
                            }
                        }
                        slot++;
                    }
                }
                return;
            }
        }
    }

    public void draw(int x, int y, int scrollAmount) {
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

        int _x1 = Canvas2D.leftX;
        int _y1 = Canvas2D.leftY;
        int _x2 = Canvas2D.rightX;
        int _y2 = Canvas2D.rightY;

        Canvas2D.setBounds(x, y, x + this.width, y + this.height);

        for (int i = 0; i < this.children.length; i++) {
            int childX = this.childX[i] + x;
            int childY = (this.childY[i] + y) - scrollAmount;

            Widget child = Widget.instance[this.children[i]];

            if (!child.visible) {
                continue;
            }

            childX += child.x;
            childY += child.y;

            if (child.actionType > 0) {
                Game.updateWidget(child);
            }

            if (child.type == 0) {
                if (child.scrollAmount > child.scrollHeight - child.height) {
                    child.scrollAmount = child.scrollHeight - child.height;
                }

                if (child.scrollAmount < 0) {
                    child.scrollAmount = 0;
                }

                child.draw(childX, childY, child.scrollAmount);

                if (child.scrollHeight > child.height) {
                    Game.drawScrollbar(childX + child.width, childY, child.height, child.scrollHeight, child.scrollAmount);
                }
            } else {
                child.draw(childX, childY);
            }
        }

        Canvas2D.setBounds(_x1, _y1, _x2, _y2);
    }

    public Model getMesh(int type, int index) {
        Model m = (Model) modelCache.get((type << 16) + index);

        if (m != null) {
            return m;
        }

        if (type == 1) {
            m = Model.get(index);
        } else if (type == 2) {
            m = ActorConfig.get(index).getDialogModel();
        } else if (type == 3) {
            m = Game.self.getDialogModel();
        } else if (type == 4) {
            m = ObjConfig.get(index).getWidgetMesh(50);
        } else if (type == 5) {
            m = null;
        }

        if (m != null) {
            modelCache.insert(m, (type << 16) + index);
        }
        return m;
    }

    public Model getMesh(int frame1, int frame2, boolean enabled) {
        Model m;

        if (enabled) {
            m = getMesh(this.modelTypeEnabled, this.modelIndexEnabled);
        } else {
            m = getMesh(this.modelTypeDisabled, this.modelIndexDisabled);
        }

        if (m == null) {
            return null;
        }

        if (frame1 == -1 && frame2 == -1 && m.triangleColor == null) {
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

            if (w.type == 6 && (w.seqIndexDisabled != -1 || w.seqIndexEnabled != -1)) {
                int seqIndex = w.isEnabled() ? w.seqIndexEnabled : w.seqIndexDisabled;

                if (seqIndex != -1) {
                    Sequence sequence = Sequence.instance[seqIndex];
                    for (w.sequenceCycle += cycle; w.sequenceCycle > sequence.getFrameLength(w.sequenceFrame); ) {
                        w.sequenceCycle -= sequence.getFrameLength(w.sequenceFrame) + 1;
                        w.sequenceFrame++;
                        if (w.sequenceFrame >= sequence.frameCount) {
                            w.sequenceFrame -= sequence.padding;
                            if (w.sequenceFrame < 0 || w.sequenceFrame >= sequence.frameCount) {
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

    public boolean isEnabled() {
        if (this.script == null || this.script.length == 0) {
            return false;
        }

        for (Script s : this.script) {
            int a = s.execute();
            int b = s.compareValue;

            switch (s.compareType) {
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

    public void resetSequence() {
        if (this.children == null) {
            return;
        }

        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i] == -1) {
                break;
            }

            Widget w = Widget.get(this.children[i]);

            if (w.type == 1) {
                w.resetSequence();
            }

            w.sequenceFrame = 0;
            w.sequenceCycle = 0;
        }
    }

    public void scroll(int amount) {
        this.scrollAmount += (this.scrollHeight / 30.25D) * amount;

        if (this.scrollAmount < 0) {
            this.scrollAmount = 0;
        }

        int max = this.scrollHeight - this.height;

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
