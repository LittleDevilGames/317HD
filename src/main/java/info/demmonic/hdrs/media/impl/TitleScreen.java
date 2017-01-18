package info.demmonic.hdrs.media.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static Sprite background;

    public static void createBackground() {
        background = new Sprite(Game.archive.get("title.dat"));
/*
        Rt3.batch.begin();
        s.draw(Rt3.batch, 0, 0);

        s.draw(Rt3.batch,-637, 0);

        s.draw(Rt3.batch,-128, 0);

        s.draw(Rt3.batch,-202, -371);

        s.draw(Rt3.batch,-202, -171);

        s.draw(Rt3.batch,0, -265);

        s.draw(Rt3.batch,-562, -265);

        s.draw(Rt3.batch,-128, -171);

        s.draw(Rt3.batch,-562, -171);

        int[] pixels = new int[s.width];
        for (int y = 0; y < s.height; y++) {
            for (int x = 0; x < s.width; x++) {
                pixels[x] = s.pixels[(s.width - x - 1) + s.width * y];
            }
            for (int x = 0; x < s.width; x++) {
                s.pixels[x + (s.width * y)] = pixels[x];
            }
        }

        s.draw(Rt3.batch,382, 0);

        s.draw(Rt3.batch,-255, 0);

        s.draw(Rt3.batch,254, 0);

        s.draw(Rt3.batch,180, -371);

        s.draw(Rt3.batch,180, -171);

        s.draw(Rt3.batch,382, -265);

        s.draw(Rt3.batch,-180, -265);

        s.draw(Rt3.batch,254, -171);

        s.draw(Rt3.batch,-180, -171);

        s = new Sprite(Game.archive, "logo", 0);
        s.drawMasked(382 - s.width / 2 - 128, 18);

        Rt3.batch.end();
        s = null;*/

        System.gc();
    }

    public static void createImages() {
        bitmapBox = new Bitmap(Game.archive, "titlebox", 0);
        bitmapButton = new Bitmap(Game.archive, "titlebutton", 0);

        if (!Game.processFlames) {
            Game.drawFlames = true;
            Game.processFlames = true;
        }
    }

    public static void draw(SpriteBatch batch, boolean showButtons) {
        int baseX = 202;
        int baseY = 171;
        background.draw(batch, 0, 0);
        background.draw(batch, background.width, 0, true);

        bitmapBox.draw(batch, baseX, baseY);

        int startX = '\u0168';
        int startY = '\310';

        switch (state) {
            case WELCOME: {
                int x = startX / 2 - 80;
                int y = startY / 2 + 80;

                BitmapFont.SMALL.draw(Game.ondemand.message, startX / 2 + baseX, y + baseY, 0x75a9a9, BitmapFont.SHADOW_CENTER);

                y = startY / 2 - 20;
                BitmapFont.BOLD.draw("Welcome to RuneScape", startX / 2 + baseX, y + baseY, 0xffff00, BitmapFont.SHADOW_CENTER);
                y += 30;

                y = startY / 2 + 20;
                bitmapButton.draw(batch, x - 73 + baseX, y - 20 + baseY);
                BitmapFont.BOLD.draw("New User", x + baseX, y + 5 + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);

                x = startX / 2 + 80;
                bitmapButton.draw(batch, x - 73 + baseX, y - 20 + baseY);
                BitmapFont.BOLD.draw("Existing User", x + baseX, y + 5 + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                break;
            }
            case LOGIN: {
                int y = startY / 2 - 40;

                if (LOGIN_MESSAGE[0].length() > 0) {
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[0], startX / 2 + baseX, y - 15 + baseY, 0xffff00, BitmapFont.SHADOW_CENTER);
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[1], startX / 2 + baseX, y + baseY, 0xffff00, BitmapFont.SHADOW_CENTER);
                    y += 30;
                } else {
                    BitmapFont.BOLD.draw(LOGIN_MESSAGE[1], startX / 2 + baseX, y - 7 + baseY, 0xffff00, BitmapFont.SHADOW_CENTER);
                    y += 30;
                }

                BitmapFont.BOLD.draw("Username: " + Game.username + ((field == 0) & (Game.loopCycle % 40 < 20) ? "@yel@|" : ""), startX / 2 - 90 + baseX, y + baseY, 0xFFFFFF, BitmapFont.SHADOW | BitmapFont.ALLOW_TAGS);
                y += 15;

                BitmapFont.BOLD.draw("Password: " + JString.toAsteriks(Game.password) + ((field == 1) & (Game.loopCycle % 40 < 20) ? "@yel@|" : ""), startX / 2 - 88 + baseX, y + baseY, 0xFFFFFF, BitmapFont.SHADOW | BitmapFont.ALLOW_TAGS);
                y += 15;

                if (!showButtons) {
                    int x = startX / 2 - 80;
                    y = startY / 2 + 50;
                    bitmapButton.draw(batch, x - 73 + baseX, y - 20 + baseY);
                    BitmapFont.BOLD.draw("Login", x + baseX, y + 5 + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                    x = startX / 2 + 80;
                    bitmapButton.draw(batch, x - 73 + baseX, y - 20 + baseY);
                    BitmapFont.BOLD.draw("Cancel", x + baseX, y + 5 + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                }
                break;
            }
            case ABOUT: {
                BitmapFont.BOLD.draw("Create a free account", startX / 2 + baseX, startY / 2 - 60 + baseY, 0xffff00, BitmapFont.SHADOW_CENTER);
                int y = startY / 2 - 35;
                BitmapFont.BOLD.draw("To create a new account you need to", startX / 2 + baseX, y + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("go back to the main RuneScape webpage", startX / 2 + baseX, y + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("and choose the red 'create account'", startX / 2 + baseX, y + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                BitmapFont.BOLD.draw("button at the top right of that page.", startX / 2 + baseX, y + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                y += 15;
                int x = startX / 2;
                y = startY / 2 + 50;

                bitmapButton.draw(batch, x - 73 + baseX, y - 20 + baseY);
                BitmapFont.BOLD.draw("Cancel", x + baseX, y + 5 + baseY, 0xffffff, BitmapFont.SHADOW_CENTER);
                break;
            }
            default: {
                break;
            }
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
                int centerY = (Game.HEIGHT / 2);

                if (Mouse.clickButton == 1) {
                    field = Mouse.clickY >= centerY ? 1 : 0;
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

                    boolean validChar = false;

                    for (int i = 0; i < TitleScreen.VALID_INPUT_CHARS.length(); i++) {
                        if (c != TitleScreen.VALID_INPUT_CHARS.charAt(i)) {
                            continue;
                        }

                        validChar = true;
                        break;
                    }

                    if (field == 0) {
                        if (c == 8 && Game.username.length() > 0) {
                            Game.username = Game.username.substring(0, Game.username.length() - 1);
                        }

                        if (c == 9 || c == 10 || c == 13) {
                            field = 1;
                        }

                        if (validChar) {
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

                        if (validChar) {
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
