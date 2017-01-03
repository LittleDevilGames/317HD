package info.demmonic.hdrs.media.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.media.*;
import info.demmonic.hdrs.util.JString;

public class TitleScreen {

    public static final String[] LOGIN_MESSAGE = new String[]{JString.BLANK, JString.BLANK};
    public static final String VALID_INPUT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    public static Bitmap bitmapBox, bitmapButton;
    public static ImageProducer producerBox;
    public static ImageProducer[] producerBackground;
    public static State state = State.WELCOME;
    public static int field = 0;

    public static void nullify() {
        Flames.producer = null;
        producerBackground = null;
        producerBox = null;
    }

    public static void clearProducers() {
        Flames.nullify();
        producerBackground = null;
        Flames.producer = null;
        producerBox = null;
    }

    public static void createProducers() {
        if (producerBackground != null && producerBackground[0] != null) {
            return;
        }

        Game.clearIngameProducers();

        Flames.createProducers();

        producerBackground = new ImageProducer[6];

        producerBackground[0] = new ImageProducer(509, 171);
        Canvas2D.clear();
        producerBackground[1] = new ImageProducer(360, 132);
        Canvas2D.clear();
        producerBackground[2] = new ImageProducer(202, 238);
        Canvas2D.clear();
        producerBackground[3] = new ImageProducer(203, 238);
        Canvas2D.clear();
        producerBackground[4] = new ImageProducer(74, 94);
        Canvas2D.clear();
        producerBackground[5] = new ImageProducer(75, 94);
        Canvas2D.clear();
        producerBox = new ImageProducer(360, 200);
        Canvas2D.clear();

        if (Game.archive != null) {
            createBackground();
            createImages();
        }

        Game.redraw = true;
    }

    public static void createBackground() {
        Sprite s = new Sprite(Game.archive.get("title.dat"), Game.instance);

        Flames.producer[0].prepare();
        s.draw(0, 0);

        Flames.producer[1].prepare();
        s.draw(-637, 0);

        producerBackground[0].prepare();
        s.draw(-128, 0);

        producerBackground[1].prepare();
        s.draw(-202, -371);

        producerBox.prepare();
        s.draw(-202, -171);

        producerBackground[2].prepare();
        s.draw(0, -265);

        producerBackground[3].prepare();
        s.draw(-562, -265);

        producerBackground[4].prepare();
        s.draw(-128, -171);

        producerBackground[5].prepare();
        s.draw(-562, -171);

        // Mirror it horizontally
        int[] pixels = new int[s.width];
        for (int y = 0; y < s.height; y++) {
            for (int x = 0; x < s.width; x++) {
                pixels[x] = s.pixels[(s.width - x - 1) + s.width * y];
            }
            for (int x = 0; x < s.width; x++) {
                s.pixels[x + (s.width * y)] = pixels[x];
            }
        }

        Flames.producer[0].prepare();
        s.draw(382, 0);

        Flames.producer[1].prepare();
        s.draw(-255, 0);

        producerBackground[0].prepare();
        s.draw(254, 0);

        producerBackground[1].prepare();
        s.draw(180, -371);

        producerBox.prepare();
        s.draw(180, -171);

        producerBackground[2].prepare();
        s.draw(382, -265);

        producerBackground[3].prepare();
        s.draw(-180, -265);

        producerBackground[4].prepare();
        s.draw(254, -171);

        producerBackground[5].prepare();
        s.draw(-180, -171);

        s = new Sprite(Game.archive, "logo", 0);
        producerBackground[0].prepare();
        s.drawMasked(382 - s.width / 2 - 128, 18);
        s = null;

        System.gc();
    }

    public static void createImages() {
        bitmapBox = new Bitmap(Game.archive, "titlebox", 0);
        bitmapButton = new Bitmap(Game.archive, "titlebutton", 0);

        Flames.createImages();

        Game.instance.drawProgress("Connecting to fileserver", 10);

        if (!Game.processFlames) {
            Game.drawFlames = true;
            Game.processFlames = true;
            Game.instance.startThread(Game.instance, 2);
        }
    }

    public static void draw(boolean show_buttons) {
        createProducers();
        producerBox.prepare();
        bitmapBox.draw(0, 0);

        char startX = '\u0168';
        char startY = '\310';

        switch (state) {
            case WELCOME: {
                int x = startX / 2 - 80;
                int y = startY / 2 + 80;

                BitmapFont.SMALL.draw(Game.ondemand.message, startX / 2, y, 0x75a9a9, BitmapFont.SHADOW_CENTER);

                y = startY / 2 - 20;
                BitmapFont.BOLD.draw("Welcome to RuneScape", startX / 2, y, 0xffff00, BitmapFont.SHADOW_CENTER);
                y += 30;

                y = startY / 2 + 20;
                bitmapButton.draw(x - 73, y - 20);
                BitmapFont.BOLD.draw("New User", x, y + 5, 0xffffff, BitmapFont.SHADOW_CENTER);

                x = startX / 2 + 80;
                bitmapButton.draw(x - 73, y - 20);
                BitmapFont.BOLD.draw("Existing User", x, y + 5, 0xffffff, BitmapFont.SHADOW_CENTER);
                break;
            }
            case LOGIN: {
                int y = startY / 2 - 40;

                if (LOGIN_MESSAGE[0].length() > 0) {
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[0], startX / 2, y - 15, 0xffff00, BitmapFont.SHADOW_CENTER);
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[1], startX / 2, y, 0xffff00, BitmapFont.SHADOW_CENTER);
                    y += 30;
                } else {
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[1], startX / 2, y - 7, 0xffff00, BitmapFont.SHADOW_CENTER);
                    y += 30;
                }

                BitmapFont.BOLD.draw("Username: " + Game.username + ((field == 0) & (Game.loopCycle % 40 < 20) ? "@yel@|" : ""), startX / 2 - 90, y, 0xFFFFFF, BitmapFont.SHADOW | BitmapFont.ALLOW_TAGS);
                y += 15;

                BitmapFont.BOLD.draw("Password: " + JString.toAsteriks(Game.password) + ((field == 1) & (Game.loopCycle % 40 < 20) ? "@yel@|" : ""), startX / 2 - 88, y, 0xFFFFFF, BitmapFont.SHADOW | BitmapFont.ALLOW_TAGS);
                y += 15;

                if (!show_buttons) {
                    int x = startX / 2 - 80;
                    y = startY / 2 + 50;
                    bitmapButton.draw(x - 73, y - 20);
                    BitmapFont.BOLD.draw("Login", x, y + 5, 0xffffff, BitmapFont.SHADOW_CENTER);
                    x = startX / 2 + 80;
                    bitmapButton.draw(x - 73, y - 20);
                    BitmapFont.BOLD.draw("Cancel", x, y + 5, 0xffffff, BitmapFont.SHADOW_CENTER);
                }
                break;
            }
            case ABOUT: {
                BitmapFont.BOLD.draw("Create a free account", startX / 2, startY / 2 - 60, 0xffff00, BitmapFont.SHADOW_CENTER);
                int y = startY / 2 - 35;
                BitmapFont.BOLD.draw("To create a new account you need to", startX / 2, y, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("go back to the main RuneScape webpage", startX / 2, y, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("and choose the red 'create account'", startX / 2, y, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("button at the top right of that page.", startX / 2, y, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                int x = startX / 2;
                y = startY / 2 + 50;

                bitmapButton.draw(x - 73, y - 20);
                BitmapFont.BOLD.draw("Cancel", x, y + 5, 0xffffff, BitmapFont.SHADOW_CENTER);
                break;
            }
            default: {
                break;
            }
        }

        producerBox.draw(202, 171);

        if (Game.redraw) {
            Game.redraw = false;
            producerBackground[0].draw(128, 0);
            producerBackground[1].draw(202, 371);
            producerBackground[2].draw(0, 265);
            producerBackground[3].draw(562, 265);
            producerBackground[4].draw(128, 171);
            producerBackground[5].draw(562, 171);
        }
    }

    public static void handle() {
        switch (state) {
            case WELCOME: {
                if (Mouse.clicked(229, 271, 147, 41)) {
                    state = State.ABOUT;
                    field = 0;
                } else if (Mouse.clicked(389, 271, 147, 41)) {
                    LOGIN_MESSAGE[0] = JString.BLANK;
                    LOGIN_MESSAGE[1] = JString.ENTER_YOUR_CREDENTIALS;
                    state = State.LOGIN;
                    field = 0;
                }
                return;
            }

            case LOGIN: {
                int center_y = (Game.HEIGHT / 2);

                if (Mouse.clickButton == 1) {
                    field = Mouse.clickY >= center_y ? 1 : 0;
                }

                if (Mouse.clicked(228, 301, 147, 41)) {
                    Game.reconnectionAttempts = 0;
                    Game.netLogin(Game.username, Game.password, false);

                    if (Game.loggedIn) {
                        return;
                    }
                }

                if (Mouse.clicked(389, 301, 147, 41)) {
                    state = State.WELCOME;
                    Game.username = "";
                    Game.password = "";
                }

                do {
                    int c = Keyboard.next();

                    if (c == -1) {
                        break;
                    }

                    boolean valid_char = false;

                    for (int i = 0; i < TitleScreen.VALID_INPUT_CHARS.length(); i++) {
                        if (c != TitleScreen.VALID_INPUT_CHARS.charAt(i)) {
                            continue;
                        }

                        valid_char = true;
                        break;
                    }

                    if (field == 0) {
                        if (c == 8 && Game.username.length() > 0) {
                            Game.username = Game.username.substring(0, Game.username.length() - 1);
                        }

                        if (c == 9 || c == 10 || c == 13) {
                            field = 1;
                        }

                        if (valid_char) {
                            Game.username += (char) c;
                        }

                        if (Game.username.length() > 12) {
                            Game.username = Game.username.substring(0, 12);
                        }
                    } else if (field == 1) {
                        if (c == 8 && Game.password.length() > 0) {
                            Game.password = Game.password.substring(0, Game.password.length() - 1);
                        }

                        if (c == 9 || c == 10 || c == 13) {
                            field = 0;
                        }

                        if (valid_char) {
                            Game.password += (char) c;
                        }

                        if (Game.password.length() > 20) {
                            Game.password = Game.password.substring(0, 20);
                        }
                    }
                } while (true);
                break;
            }

            case ABOUT: {
                if (Mouse.clicked(309, 301, 147, 41)) {
                    state = State.WELCOME;
                }
                break;
            }
            default: {
                // Nuffin
                break;
            }
        }
    }

    public static void setMessage(String a, String b) {
        LOGIN_MESSAGE[0] = a;
        LOGIN_MESSAGE[1] = b;
    }

    public enum State {
        ABOUT, LOGIN, WELCOME
    }
}
