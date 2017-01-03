package info.demmonic.hdrs.media.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.media.Bitmap;
import info.demmonic.hdrs.media.BitmapFont;
import info.demmonic.hdrs.media.Sprite;
import info.demmonic.hdrs.util.JString;

public class TitleScreen {

    public static final String[] LOGIN_MESSAGE = new String[]{"", ""};
    public static final String VALID_INPUT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    public static Bitmap bitmapBox, bitmapButton;
    public static State state = State.WELCOME;
    public static int field = 0;

    public static void createBackground() {
        Sprite s = new Sprite(Game.archive.get("title.dat"));

        s.draw(0, 0);

        s.draw(-637, 0);

        s.draw(-128, 0);

        s.draw(-202, -371);

        s.draw(-202, -171);

        s.draw(0, -265);

        s.draw(-562, -265);

        s.draw(-128, -171);

        s.draw(-562, -171);

        int[] pixels = new int[s.width];
        for (int y = 0; y < s.height; y++) {
            for (int x = 0; x < s.width; x++) {
                pixels[x] = s.pixels[(s.width - x - 1) + s.width * y];
            }
            for (int x = 0; x < s.width; x++) {
                s.pixels[x + (s.width * y)] = pixels[x];
            }
        }

        s.draw(382, 0);

        s.draw(-255, 0);

        s.draw(254, 0);

        s.draw(180, -371);

        s.draw(180, -171);

        s.draw(382, -265);

        s.draw(-180, -265);

        s.draw(254, -171);

        s.draw(-180, -171);

        s = new Sprite(Game.archive, "logo", 0);
        s.drawMasked(382 - s.width / 2 - 128, 18);
        s = null;

        System.gc();
    }

    public static void createImages() {
        bitmapBox = new Bitmap(Game.archive, "titlebox", 0);
        bitmapButton = new Bitmap(Game.archive, "titlebutton", 0);

        Flames.createImages();

        if (!Game.processFlames) {
            Game.drawFlames = true;
            Game.processFlames = true;
        }
    }

    public static void draw(boolean show_buttons) {
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

        if (Game.redraw) {
            Game.redraw = false;
        }
    }

    public static void handle() {
        switch (state) {
            case WELCOME: {
                if (Mouse.clicked(229, 271, 147, 41)) {
                    state = State.ABOUT;
                    field = 0;
                } else if (Mouse.clicked(389, 271, 147, 41)) {
                    LOGIN_MESSAGE[0] = "";
                    LOGIN_MESSAGE[1] = "Enter your username & password.";
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