package info.demmonic.hdrs.media.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.cache.impl.Widget;
import info.demmonic.hdrs.input.model.Area;
import info.demmonic.hdrs.media.Bitmap;
import info.demmonic.hdrs.media.Canvas3D;

public class Sidebar {

    public static final Bitmap[] ICON = new Bitmap[14];
    public static final Bitmap[] REDSTONE = new Bitmap[10];
    public static final Tab[] TAB = new Tab[14];
    public static Bitmap backhmid1, backbase2, invback;
    public static boolean draw;
    public static boolean drawTabs;
    public static Tab flashingTab;
    public static int[] pixels3D;
    public static Tab selectedTab;
    public static int widgetIndex;

    public static void draw() {
        if (!draw) {
            return;
        }
        draw = false;

        int offsetX = 553;
        int offsetY = 205;
        Canvas3D.pixels = pixels3D;
        invback.draw(offsetX, offsetY);

        if (widgetIndex != -1) {
            Widget.draw(widgetIndex, offsetX, offsetY, 0);
        } else if (selectedTab.widget != -1) {
            Widget.draw(selectedTab.widget, offsetX, offsetY, 0);
        }

        if (Menu.visible && Menu.area == Area.TAB) {
            Menu.draw();
        }

        Canvas3D.pixels = Game.viewportPixels;
    }

    public static void drawTabs() {
        if (flashingTab != null) {
            drawTabs = true;
        }

        if (drawTabs) {
            drawTabs = false;

            if (flashingTab != null && flashingTab == selectedTab) {
                flashingTab = null;
                Game.out.writeOpcode(120);
                Game.out.writeByte(selectedTab.index);
            }

            {
                int offsetX = 516;
                int offsetY = 160;
                backhmid1.draw(offsetX, offsetY);

                if (widgetIndex == -1) {
                    for (int i = 0; i < 7; i++) {
                        Tab tab = TAB[i];

                        if (tab == selectedTab) {
                            tab.drawRedstone(offsetX, offsetY);
                        }

                        if (tab.widget == -1) {
                            continue;
                        }

                        if (flashingTab == tab) {
                            if (Game.loopCycle % 20 < 10) {
                                tab.drawIcon(offsetX, offsetY);
                            }
                            continue;
                        }

                        tab.drawIcon(offsetX, offsetY);
                    }
                }
            }

            {
                int offsetX = 496;
                int offsetY = 466;
                backbase2.draw(offsetX, offsetY);

                if (widgetIndex == -1) {
                    for (int i = 7; i < 14; i++) {
                        Tab tab = TAB[i];

                        if (tab == selectedTab) {
                            tab.drawRedstone(offsetX, offsetY);
                        }

                        if (tab.widget == -1) {
                            continue;
                        }

                        if (flashingTab == tab) {
                            if (Game.loopCycle % 20 < 10) {
                                tab.drawIcon(offsetX, offsetY);
                            }
                            continue;
                        }

                        tab.drawIcon(offsetX, offsetY);
                    }
                }
            }

        }
    }

    public static Tab getTab(int i) {
        if (i > TAB.length || i < 0) {
            i = 0;
        }
        return TAB[i];
    }

    public static void load(Archive archive) {
        invback = new Bitmap(archive, "invback");
        backbase2 = new Bitmap(archive, "backbase2");
        backhmid1 = new Bitmap(archive, "backhmid1");

        try {
            for (int i = 0; i < ICON.length; i++) {
                ICON[i] = new Bitmap(archive, "sideicons", i);
            }
        } catch (Exception e) {
        }

        REDSTONE[0] = new Bitmap(archive, "redstone1");
        REDSTONE[1] = new Bitmap(archive, "redstone2");
        REDSTONE[2] = new Bitmap(archive, "redstone3");
        REDSTONE[3] = new Bitmap(archive, "redstone1").flipHorizontally();
        REDSTONE[4] = new Bitmap(archive, "redstone2").flipHorizontally();
        REDSTONE[5] = new Bitmap(archive, "redstone1").flipVertically();
        REDSTONE[6] = new Bitmap(archive, "redstone2").flipVertically();
        REDSTONE[7] = new Bitmap(archive, "redstone3").flipVertically();
        REDSTONE[8] = new Bitmap(archive, "redstone1").flipHorizontally().flipVertically();
        REDSTONE[9] = new Bitmap(archive, "redstone2").flipHorizontally().flipVertically();

        int i = 0;
        TAB[i] = new Tab(i++, REDSTONE[0], ICON[0], 22, 10, 29, 13); // Combat Style
        TAB[i] = new Tab(i++, REDSTONE[1], ICON[1], 54, 8, 53, 11); // Skills
        TAB[i] = new Tab(i++, REDSTONE[1], ICON[2], 82, 8, 82, 11); // Quest Journal
        TAB[i] = new Tab(i++, REDSTONE[2], ICON[3], 110, 8, 115, 12); // Inventory
        TAB[i] = new Tab(i++, REDSTONE[4], ICON[4], 153, 8, 153, 13); // Equipment
        TAB[i] = new Tab(i++, REDSTONE[4], ICON[5], 181, 8, 180, 11); // Prayer
        TAB[i] = new Tab(i++, REDSTONE[3], ICON[6], 209, 9, 208, 13); // Magic
        TAB[i] = new Tab(i++, REDSTONE[6], ICON[7], 42, 0, 42, 0); // Empty
        TAB[i] = new Tab(i++, REDSTONE[6], ICON[7], 74, 0, 74, 2); // Friends
        TAB[i] = new Tab(i++, REDSTONE[6], ICON[8], 102, 0, 102, 3); // Ignores
        TAB[i] = new Tab(i++, REDSTONE[7], ICON[9], 130, 1, 137, 4); // Logout
        TAB[i] = new Tab(i++, REDSTONE[9], ICON[10], 173, 0, 174, 2); // Game Options
        TAB[i] = new Tab(i++, REDSTONE[9], ICON[11], 201, 0, 201, 2); // Player Options
        TAB[i] = new Tab(i++, REDSTONE[8], ICON[12], 229, 0, 226, 2); // Music

        openTab(3);

    }

    public static void nullify() {
        for (int i = 0; i < ICON.length; i++) {
            ICON[i] = null;
        }
        for (int i = 0; i < REDSTONE.length; i++) {
            REDSTONE[i] = null;
        }
        selectedTab = null;
        flashingTab = null;
        invback = null;
        backbase2 = null;
        backhmid1 = null;
    }

    public static void openTab(int i) {
        selectedTab = getTab(i);
    }

    public static void setFlashing(int i) {
        flashingTab = getTab(i);
    }

    public static class Tab {

        public final int index;
        public Bitmap icon;
        public int iconX, iconY;
        public Bitmap redstone;
        public int widget = -1;
        public int x, y;

        private Tab(int i, Bitmap redstone, Bitmap icon, int x, int y, int iconX, int iconY) {
            this.index = i;
            this.redstone = redstone;
            this.icon = icon;
            this.x = x;
            this.y = y;
            this.iconX = iconX;
            this.iconY = iconY;
        }

        public void drawIcon(int offsetX, int offsetY) {
            icon.draw(iconX + offsetX, iconY + offsetY);
        }

        public void drawRedstone(int offsetX, int offsetY) {
            redstone.draw(x + offsetX, y + offsetY);
        }

    }

}
