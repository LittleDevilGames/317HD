package info.demmonic.hdrs.media.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.input.model.Area;
import info.demmonic.hdrs.media.BitmapFont;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.util.RSColor;

public class Menu {

    public static final int COLOR_FOREGROUND = 0x5D5447;
    public static final int[] DRAG_ACTIONS = {632, 78, 867, 431, 53, 74, 454, 539, 493, 847, 447, 1125};
    public static final Option[] options = new Option[500];
    public static Area area;
    public static int count;
    public static boolean visible;
    public static int x, y, width, height;

    static {
        for (int i = 0; i < Menu.options.length; i++) {
            Menu.options[i] = new Option();
        }
    }

    public static void add(String caption, int action, int... parameters) {
        options[count].caption = caption;
        options[count].action = action;
        options[count].parameters = parameters;
        count++;
    }

    public static void draw() {
        Canvas2D.fillRect(x, y, width, height, COLOR_FOREGROUND);
        Canvas2D.fillRect(x + 1, y + 1, width - 2, 16, RSColor.BLACK);
        Canvas2D.drawRect(x + 1, y + 18, width - 2, height - 19, RSColor.BLACK);

        BitmapFont.BOLD.draw("Choose Option", x + 3, y + 14, COLOR_FOREGROUND);

        int currentX = Mouse.lastX - area.x;
        int curretY = Mouse.lastY - area.y;

        for (int i = 0; i < count; i++) {
            int optionY = y + 31 + (count - 1 - i) * 15;
            int color = RSColor.WHITE;

            if (currentX > x && currentX < x + width && curretY > optionY - 13 && curretY < optionY + 3) {
                color = 0xFFFF00;
            }

            BitmapFont.BOLD.draw(options[i].caption, x + 3, optionY, color, BitmapFont.SHADOW | BitmapFont.ALLOW_TAGS);
        }
    }

    public static void drawTooltip() {
        int count = Menu.count;

        if (count < 2 && !Game.selectedItem && !Game.selectedWidget) {
            return;
        }

        String s;

        if (Game.selectedItem && count < 2) {
            s = "Use " + Game.selectedItemName + " with...";
        } else if (Game.selectedWidget && count < 2) {
            s = Game.selectedTooltip + "...";
        } else {
            s = Menu.getLastCaption();
        }

        if (count > 2) {
            s = s + "@whi@ / " + (count - 2) + " more options";
        }

        BitmapFont.BOLD.drawString(true, 4, 0xffffff, s, 15, 210);
    }

    public static int getAction(int i) {
        return options[i].action;
    }

    public static String getCaption(int i) {
        return options[i].caption;
    }

    public static int getLastAction() {
        return options[count - 1].action;
    }

    public static String getLastCaption() {
        return options[count - 1].caption;
    }

    public static Option getLastOption() {
        return options[count - 1];
    }

    public static int getLastParam(int i) {
        return options[count - 1].getParameter(i);
    }

    public static int getParam(int i, int j) {
        return options[i].getParameter(j);
    }

    public static void handle() {
        if (Game.dragArea != 0) {
            return;
        }
        Menu.prepare();
        Game.handleMouse();
        Menu.sort();
    }

    public static void nullify() {
        for (int i = 0; i < Menu.options.length; i++) {
            Menu.options[i] = null;
        }
    }

    public static void prepare() {
        Menu.reset();
        Menu.add("Cancel", 1107);
    }

    public static void reset() {
        Menu.count = 0;
    }

    public static void show() {
        int width = BitmapFont.BOLD.getWidth("Choose Option");
        int height = 15 * count + 21;

        for (int i = 0; i < Menu.count; i++) {
            int s_width = BitmapFont.BOLD.getWidth(Menu.options[i].caption);
            if (s_width > width) {
                width = s_width;
            }
        }

        width += 8;

        for (Area a : Area.values()) {
            if (a.contains(Mouse.clickX, Mouse.clickY)) {
                int x = (Mouse.clickX - a.x) - (width / 2);
                int y = (Mouse.clickY - a.y);

                if (x + width > a.width) {
                    x = a.width - width;
                }

                if (x < 0) {
                    x = 0;
                }

                if (y + height > a.height) {
                    y = a.height - height;
                }

                if (y < 0) {
                    y = 0;
                }

                Menu.visible = true;
                Menu.area = a;
                Menu.x = x;
                Menu.y = y;
                Menu.width = width;
                Menu.height = height;
                break;
            }
        }
    }

    public static void sort() {
        boolean unsorted = false;
        while (!unsorted) {
            unsorted = true;
            for (int i = 0; i < Menu.count - 1; i++) {
                if (Menu.getAction(i) < 1000 && Menu.getAction(i + 1) > 1000) {
                    Menu.swap(i, i + 1);
                    unsorted = false;
                }
            }
        }
    }

    public static void swap(int a, int b) {
        String caption = options[a].caption;
        int action = options[a].action;
        int[] params = options[a].parameters;
        options[a].set(options[b]);
        options[b].caption = caption;
        options[b].action = action;
        options[b].parameters = params;
    }

    public static class Option {

        public int action;
        public String caption;
        public int[] parameters = new int[3];

        public int getParameter(int i) {
            if (i >= parameters.length) {
                return -1;
            }
            return parameters[i];
        }

        public void set(Option option) {
            this.caption = option.caption;
            this.action = option.action;
            this.parameters = option.parameters;
        }

    }

}
