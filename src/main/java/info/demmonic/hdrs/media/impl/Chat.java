package info.demmonic.hdrs.media.impl;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.Widget;
import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.input.model.Area;
import info.demmonic.hdrs.input.model.Key;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.media.*;
import info.demmonic.hdrs.util.JString;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is kind of an organized mess. One day I'll just rewrite the whole thing again.
 *
 * @author Dane
 */
public class Chat extends Widget {

    public static final byte INPUT_CHAT = 0;
    public static final byte INPUT_DIALOGUE = 1;
    public static final byte MESSAGE_INPUT = 0;
    public static final byte MESSAGE_NOTIFY = 1;
    public static final int[] SETTING_COLOR = {0x00FF00, 0xFFFF00, 0xFF0000, 0x00FFFF};
    public static final int SETTING_FRIENDS = 1;
    public static final int SETTING_HIDE = 3;
    public static final int SETTING_OFF = 2;
    public static final int SETTING_ON = 0;
    public static final String[] SETTINGS_PRIVATE = {JString.ON, JString.FRIENDS, JString.OFF};
    public static final String[] SETTINGS_PUBLIC = {JString.ON, JString.FRIENDS, JString.OFF, JString.HIDE};
    public static final String[] SETTINGS_TRADE = {JString.ON, JString.FRIENDS, JString.OFF};
    public static final byte TYPE_OVERLAY = 0;
    public static final byte TYPE_UNDERLAY = 1;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MODERATOR = 1;
    public static final int TYPE_PLAYER = 2;
    public static final int TYPE_PLAYER_PRIVATE = 3;
    public static final int TYPE_TRADE_REQUEST = 4;
    public static final int TYPE_NOTIFY_PRIVATE = 5;
    public static final int TYPE_SENT_MESSAGE = 6;
    public static final int TYPE_MODERATOR_PRIVATE = 7;
    public static final int TYPE_DUEL_REQUEST = 8;
    public static Bitmap background;
    public static Buffer buffer = Buffer.create(1);
    public static State state;
    public static ImageProducer producer;
    public static boolean redraw = false;
    private static Chat instance = new Chat();
    private static Line[] lines = new Line[100];
    private static Map<Byte, StringBuilder> input = new HashMap<Byte, StringBuilder>();
    private static Map<Byte, Integer> widget = new HashMap<Byte, Integer>();
    private static Map<Byte, String> message = new HashMap<Byte, String>();

    static {
        input.put(INPUT_CHAT, new StringBuilder());
        input.put(INPUT_DIALOGUE, new StringBuilder());
        reset();
    }

    /**
     * Removes the last character of the specified input if the length > 0.
     *
     * @param type the input type.
     */
    public static void backspace(byte type) {
        StringBuilder b = input.get(type);
        if (b.length() > 0) {
            b.deleteCharAt(b.length() - 1);
        }
        Chat.redraw = true;
    }

    public static void clear() {
        Chat.setState(State.NONE);
        Chat.setInput(INPUT_DIALOGUE, "");
        Chat.setMessage(MESSAGE_INPUT, null);
        Chat.redraw = true;
    }

    /**
     * Clears the specified input type.
     *
     * @param type the type.
     */
    public static void clear(byte type) {
        StringBuilder sb = input.get(type);

        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    /**
     * Clears the chat input.
     */
    public static void clearInput() {
        Chat.setInput(Chat.INPUT_CHAT, JString.BLANK);
    }

    /**
     * Sets the overlay to -1 and redraws.
     */
    public static void clearOverlay() {
        Chat.setOverlay(-1);
        Chat.redraw = true;
    }

    /**
     * Sets the underlay to -1 and redraws.
     */
    public static void clearUnderlay() {
        Chat.setUnderlay(-1);
        Chat.redraw = true;
    }

    /**
     * Draws the specified chat type.
     */
    public static void draw() {
        if (!redraw) {
            return;
        }

        prepare();

        boolean noInput = true;

        switch (state) {
            case ADD_FRIEND:
            case REMOVE_FRIEND:
            case ADD_IGNORE:
            case REMOVE_IGNORE:
            case SEND_MESSAGE: {
                String message = Chat.message.get(MESSAGE_INPUT);
                if (message != null) {
                    BitmapFont.BOLD.draw(Chat.message.get(MESSAGE_INPUT), 239, 40, 0, BitmapFont.CENTER);
                    BitmapFont.BOLD.draw(Chat.getInput(INPUT_DIALOGUE) + "*", 239, 60, 128, BitmapFont.CENTER);
                    noInput = false;
                    break;
                } else {
                    break;
                }
            }

            case ENTER_AMOUNT:
            case ENTER_NAME: {
                BitmapFont.BOLD.draw(state.toString(), 239, 40, 0, BitmapFont.CENTER);
                BitmapFont.BOLD.draw(Chat.getInput(INPUT_DIALOGUE) + "*", 239, 60, 128, BitmapFont.CENTER);
                noInput = false;
                break;
            }

            case NOTIFY: {
                BitmapFont.BOLD.draw(Chat.message.get(MESSAGE_NOTIFY), 239, 40, 0, BitmapFont.CENTER);
                BitmapFont.BOLD.draw("Click to continue", 239, 60, 128, BitmapFont.CENTER);
                noInput = false;
                break;
            }

            default: {
                noInput = true;
            }

        }

        if (noInput) {
            if (Chat.getOverlay() != -1) {
                Widget.draw(Chat.getOverlay(), 0, 0, 0);
            } else if (Chat.getUnderlay() != -1) {
                Widget.draw(Chat.getUnderlay(), 0, 0, 0);
            } else {
                Public.draw();
            }
        }

        finish();
    }

    /**
     * Post-draw method.
     */
    protected static void finish() {
        if (Menu.visible && Menu.area == Area.CHAT) {
            Menu.draw();
        }

        producer.draw(17, 357);
        Game.producerScene.prepare();
        Canvas3D.pixels = Game.viewportPixels;
    }

    public static Chat get() {
        return Chat.instance;
    }

    /**
     * Returns the dialogue input as a String.
     *
     * @return the dialogue input.
     */
    public static String getDialogueInput() {
        return input.get(INPUT_DIALOGUE).toString();
    }

    /**
     * Returns the StringBuilder of the provided input type.
     *
     * @param type
     */
    public static StringBuilder getInput(byte type) {
        return input.get(type);
    }

    /**
     * Gets the message for the provided type.
     *
     * @param type the type.
     * @return the message.
     */
    public static String getMessage(byte type) {
        return message.get(type);
    }

    /**
     * Gets the overlay widget's index.
     *
     * @return the widget index.
     */
    public static int getOverlay() {
        return widget.get(TYPE_OVERLAY);
    }

    /**
     * Sets the overlay interface. (Used for dialogue)
     *
     * @param index the widget index.
     */
    public static void setOverlay(int index) {
        widget.put(TYPE_OVERLAY, index);
        Chat.redraw = true;
    }

    /**
     * Gets the status of the input.
     */
    public static State getState() {
        return state;
    }

    /**
     * Sets the status of the input.
     *
     * @param state
     */
    public static void setState(State state) {
        Chat.state = state;

        if (state == State.NONE) {
            Chat.setInput(INPUT_DIALOGUE, "");
        }
    }

    /**
     * Gets the underlay widget's index.
     *
     * @return the widget index.
     */
    public static int getUnderlay() {
        return widget.get(TYPE_UNDERLAY);
    }

    /**
     * Sets the underlay interface. (Used for level-up messages so they don't go away in-case of an overlay dialogue.)
     *
     * @param index the widget index.
     */
    public static void setUnderlay(int index) {
        widget.put(TYPE_UNDERLAY, index);
        Chat.redraw = true;
    }

    /**
     * Returns the current index of the widget type.
     *
     * @param type the type.
     * @return the widget index.
     */
    public static int getWidget(byte type) {
        return widget.get(type);
    }

    /**
     * Returns the chat input as a String.
     *
     * @return the chat input.
     */
    public static String getInput() {
        return input.get(INPUT_CHAT).toString();
    }

    public static void handleKeyboard(int key) {
        State state = Chat.getState();

        if (state != State.NONE && state.hasInput) {
            switch (key) {
                case KeyEvent.VK_ENTER: {
                    String input = Chat.getInput(Chat.INPUT_DIALOGUE).toString();
                    switch (state) {
                        case ADD_FRIEND:
                            Game.friendAdd(JString.toLong(input));
                            break;
                        case REMOVE_FRIEND:
                            if (Game.friendCount > 0) {
                                Game.friendRemove(JString.toLong(input));
                            }
                            break;
                        case ADD_IGNORE:
                            if (Game.ignoreCount < 100) {
                                Game.ignoreAdd(JString.toLong(input));
                            }
                            break;
                        case REMOVE_IGNORE:
                            if (Game.ignoreCount > 0) {
                                Game.ignoreRemove(JString.toLong(input));
                            }
                            break;
                        case SEND_MESSAGE:
                            if (input.length() > 0) {
                                Game.out.writeOpcode(126);
                                Game.out.writeByte(0);
                                int start = Game.out.position;
                                Game.out.writeLong(Game.messageRecipientNameLong);
                                JString.put(input, Game.out);
                                Game.out.putLength(Game.out.position - start);
                                input = JString.getFiltered(input);
                                Chat.put(JString.getFormattedString(Game.messageRecipientNameLong), input, 6);
                                if (Settings.values[1] == Chat.SETTING_OFF) {
                                    Settings.values[1] = Chat.SETTING_FRIENDS;
                                    Settings.redraw = true;
                                    Settings.send_to_server();
                                }
                            }
                            break;
                        case ENTER_AMOUNT:
                            int i = 0;
                            try {
                                i = Integer.parseInt(input);
                            } catch (Exception e) {
                                // do nuffen heuhuruehu
                            }
                            Game.out.writeOpcode(208);
                            Game.out.writeInt(i);
                            break;
                        case ENTER_NAME:
                            if (input.length() > 0) {
                                Game.out.writeOpcode(60);
                                Game.out.writeLong(JString.toLong(input));
                            }
                            break;
                        default: {
                            System.out.println("Unknown chat dialogue type: " + state.toString());
                        }
                    }

                    Chat.clear();
                    break;
                }

                case KeyEvent.VK_BACK_SPACE: {
                    Chat.backspace(Chat.INPUT_DIALOGUE);
                    break;
                }

                default: {
                    StringBuilder input = Chat.getInput(Chat.INPUT_DIALOGUE);
                    if (input.length() < state.charLimit) {
                        Key.append(Chat.getInput(Chat.INPUT_DIALOGUE), key, state.flags);
                    }
                    break;
                }
            }

            Chat.redraw = true;
        } else {
            /*
             * Typing in the actual chatbox.
			 */
            switch (key) {
                case KeyEvent.VK_ENTER: {
                    String message = Chat.getInput(Chat.INPUT_CHAT).toString();

                    if (message.length() == 0) {
                        return;
                    }

                    if (message.charAt(0) == '/') {
                        String[] arg = message.substring(1).split(" ");
                        arg[0] = arg[0].toLowerCase();

                        Chat.put(Arrays.toString(arg));

                        switch (arg[0]) {
                            case "gc": {
                                System.gc();
                                break;
                            }
                            case "findwidget": {
                                if (arg.length < 2) {
                                    break;
                                }

                                Field[] fields = null;
                                boolean fieldSearch = false;

                                if (arg.length == 2) {
                                    fields = Widget.class.getDeclaredFields();
                                } else {
                                    fieldSearch = true;
                                    try {
                                        fields = new Field[]{Widget.class.getDeclaredField(arg[1])};
                                    } catch (Exception e) {
                                        Chat.put("Invalid widget field: " + arg[1]);
                                        break;
                                    }
                                }

                                if (fields == null) {
                                    break;
                                }

                                for (Widget w : Widget.instance) {
                                    for (Field f : fields) {
                                        Object o = null;

                                        try {
                                            o = f.get(w);
                                        } catch (Exception e) {
                                            /* ignore */
                                        }

                                        if (o != null) {
                                            String s = o.toString();

                                            if (s.toLowerCase().contains(arg[fieldSearch ? 2 : 1])) {
                                                Chat.put("[" + w.index + "]." + f.getName() + " = " + s);
                                            }
                                        }
                                    }
                                }
                                break;
                            }

                            case "cinema": {
                                Game.camCinemaMode = true;
                                Game.camCinemaDestX = Game.self.getLocalX() - 2;
                                Game.camCinemaDestY = Game.self.getLocalY();
                                Game.camCinemaDestZ = 384;
                                Game.camCinemaBaseSpeed = 1;
                                Game.camCinemaSpeed = 8;
                                Game.camCinemaAimX = Game.self.getLocalX();
                                Game.camCinemaAimY = Game.self.getLocalY();
                                Game.camCinemaAimZ = 128;
                                Game.camCinemaRotBase = 1;
                                Game.camCinemaRotModifier = 32;
                                break;
                            }

                            case "mark": {
                                for (int j = 1; j < arg.length; ++j) {
                                    try {
                                        switch (arg[j]) {
                                            case "-type": {
                                                Game.markType = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-x": {
                                                Game.markX = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-y": {
                                                Game.markY = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-z": {
                                                Game.markZ = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-xoff": {
                                                Game.markOffX = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-yoff": {
                                                Game.markOffY = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-actor": {
                                                Game.markActorIndex = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                            case "-player": {
                                                Game.markPlayerIndex = Integer.parseInt(arg[++j]);
                                                break;
                                            }
                                        }
                                    } catch (Exception e) {
										/* ignore */
                                    }
                                }
                                break;
                            }

                            case "widget": {
                                int widget = -1;
                                try {
                                    widget = Integer.parseInt(arg[1]);
                                } catch (Exception e) {

                                }
                                Game.widgetOverlay = widget;
                                break;
                            }

                            case "map": {
                                Game.generateMinimap(Game.plane);
                                break;
                            }

                            case "dropclient": {
                                Game.netDisconnect();
                                break;
                            }

                            case "visible": {
                                int index = -1;
                                boolean visible = true;

                                try {
                                    index = Integer.parseInt(arg[1]);
                                    visible = arg[2].equals('1') || arg[2].equals("true");
                                } catch (Exception e) {
                                }

                                if (index != -1) {
                                    Widget w = Widget.get(index);
                                    if (w != null) {
                                        w.visible = visible;
                                    }
                                }
                                break;
                            }

                            case "noclip": {
                                if (Game.localRights >= 2) {
                                    for (int x = 0; x < 104; x++) {
                                        for (int y = 0; y < 104; y++) {
                                            Game.collisionMaps[Game.plane].flags[x][y] = 0;
                                        }
                                    }
                                }
                                break;
                            }

                            case "boom": {
                                int x = Game.self.getLocalX();
                                int y = Game.self.getLocalY();
                                int plane = Game.plane;

                                Game.landscape.setTileUnderlayColor(x, y, plane, 0, 7, 126, 126, 126, 126);
                                Game.generateMinimap(plane);
                                break;
                            }

                            case "testupdate": {
                                int time = 60;
                                try {
                                    time = Integer.parseInt(arg[1]);
                                } catch (Exception e) {
                                }
                                Game.nextUpdate = System.currentTimeMillis() + (time * 1000L);
                                break;
                            }

                            case "colors": {
                                try {
                                    ObjConfig c = ObjConfig.get(Integer.parseInt(arg[1]));

                                    if (c != null) {
                                        System.out.println(c.index + ": " + c.name + " materials:");
                                        System.out.println("Old: " + Arrays.toString(c.old_color));
                                        System.out.println("New: " + Arrays.toString(c.newColor));
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error parsing argument: \"" + arg[1] + "\": " + e);
                                }
                                break;
                            }

                            case "song": {
                                int song = 0;

                                try {
                                    song = Integer.parseInt(arg[1]);
                                } catch (Exception e) {
                                    System.out.println("Invalid song: " + arg[1]);
                                    break;
                                }

                                Game.ondemand.sendRequest(2, song);
                                break;
                            }

                            case "integrity": {
                                int cache = -1;
                                try {
                                    cache = Integer.parseInt(arg[1]);
                                } catch (Exception e) {
                                }
                                if (cache != -1) {

                                    ArrayList<Integer> invalid = new ArrayList<Integer>();
                                    ArrayList<Integer> valid = new ArrayList<Integer>();

                                    try {

                                        int count = Game.cache[cache].getFileCount() - 1;

                                        for (int i = 1; i < count + 1; i++) {
                                            byte[] data = Game.cache[cache].get(i);

                                            if (data == null || data.length == 0) {
                                                invalid.add(i);
                                            } else {
                                                valid.add(i);
                                            }
                                        }

                                        Chat.put("Cache[" + cache + "]: Valid: " + valid.size() + "/" + count + " (" + ((valid.size() * 100) / count) + "%), Invalid: " + invalid.size() + "/" + count + " (" + ((invalid.size() * 100) / count) + "%)");
                                        System.out.println("Invalid: " + Arrays.toString(invalid.toArray(new Integer[invalid.size()])));
                                        System.out.println("Valid: " + Arrays.toString(valid.toArray(new Integer[valid.size()])));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        Chat.clearInput();
                        Chat.redraw = true;
                        return;
                    }

                    if (message.length() > 0) {
                        if (message.startsWith("::")) {
                            Game.out.writeOpcode(103);
                            Game.out.writeByte(message.length() - 1);
                            Game.out.writeString(message.substring(2));
                        } else {
                            String s = message.toLowerCase();

                            int color = 0;
                            int effect = 0;

                            if (s.startsWith("yellow:")) {
                                color = 0;
                                message = message.substring(7);
                            } else if (s.startsWith("red:")) {
                                color = 1;
                                message = message.substring(4);
                            } else if (s.startsWith("green:")) {
                                color = 2;
                                message = message.substring(6);
                            } else if (s.startsWith("cyan:")) {
                                color = 3;
                                message = message.substring(5);
                            } else if (s.startsWith("purple:")) {
                                color = 4;
                                message = message.substring(7);
                            } else if (s.startsWith("white:")) {
                                color = 5;
                                message = message.substring(6);
                            } else if (s.startsWith("flash1:")) {
                                color = 6;
                                message = message.substring(7);
                            } else if (s.startsWith("flash2:")) {
                                color = 7;
                                message = message.substring(7);
                            } else if (s.startsWith("flash3:")) {
                                color = 8;
                                message = message.substring(7);
                            } else if (s.startsWith("glow1:")) {
                                color = 9;
                                message = message.substring(6);
                            } else if (s.startsWith("glow2:")) {
                                color = 10;
                                message = message.substring(6);
                            } else if (s.startsWith("glow3:")) {
                                color = 11;
                                message = message.substring(6);
                            }

                            s = message.toLowerCase();

                            if (s.startsWith("wave:")) {
                                effect = 1;
                                message = message.substring(5);
                            } else if (s.startsWith("wave2:")) {
                                effect = 2;
                                message = message.substring(6);
                            } else if (s.startsWith("shake:")) {
                                effect = 3;
                                message = message.substring(6);
                            } else if (s.startsWith("scroll:")) {
                                effect = 4;
                                message = message.substring(7);
                            } else if (s.startsWith("slide:")) {
                                effect = 5;
                                message = message.substring(6);
                            }

                            Game.out.writeOpcode(4);
                            Game.out.writeByte(0); // Placeholder for the length
                            int start = Game.out.position;
                            Game.out.writeByteS(effect);
                            Game.out.writeByteS(color);
                            Chat.buffer.position = 0;
                            JString.put(message, buffer);
                            Game.out.writeBytesReversedA(buffer.payload, 0, buffer.position);
                            Game.out.putLength(Game.out.position - start);
                            message = JString.getFiltered(message);

                            Game.self.spokenMessage = message;
                            Game.self.spokenColor = color;
                            Game.self.spokenEffect = effect;
                            Game.self.spokenLife = 150;

                            if (Game.localRights > 0) {
                                Chat.put(new StringBuilder("@cr").append(Game.localRights).append("@").append(Game.self.name).toString(), message, 2);
                            } else {
                                Chat.put(Game.self.name, message, 2);
                            }

                            if (Settings.values[0] == Chat.SETTING_OFF) {
                                Settings.values[0] = Chat.SETTING_HIDE;
                                Settings.redraw = true;
                                Settings.send_to_server();
                            }

                        }
                        Chat.clearInput();
                    }
                    break;
                }
                case KeyEvent.VK_BACK_SPACE: {
                    Chat.backspace(Chat.INPUT_CHAT);
                    break;
                }
                default: {
                    StringBuilder input = Chat.getInput(Chat.INPUT_CHAT);
                    if (input.length() < 80) {
                        Key.append(input, key, state.flags);
                    }
                }
            }

            Chat.redraw = true;
        }
    }

    public static void handleMouse(int x, int y) {
        int count = 0;

        if (Mouse.wheelAmount != 0) {
            instance.scroll(-Mouse.wheelAmount);
        }

        for (Line message : Chat.lines) {
            if (message == null || message.message == null) {
                continue;
            }

            int type = message.type;
            int lineY = (70 - count * 14) + Chat.get().scrollAmount + 4;

            if (lineY < -20) {
                break;
            }

            String name = message.prefix;

            if (name != null && name.startsWith("@cr")) {
                name = name.substring(5);
            }

            if (type == 0) {
                count++;
            }

            if ((type == 1 || type == 2) && (type == 1 || Settings.values[1] == Chat.SETTING_ON || Settings.values[1] == Chat.SETTING_FRIENDS && Game.friendExists(name))) {
                if (y > lineY - 14 && y <= lineY && !name.equals(Game.self.name)) {
                    if (Game.localRights >= 1) {
                        Menu.add("Report abuse @whi@" + name, 606);
                    }
                    Menu.add("Add ignore @whi@" + name, 42);
                    Menu.add("Add friend @whi@" + name, 337);
                }
                count++;
            }

            if ((type == 3 || type == 7) && Settings.privateArea == 0 && (type == 7 || Settings.values[1] == Chat.SETTING_ON || Settings.values[1] == Chat.SETTING_FRIENDS && Game.friendExists(name))) {
                if (y > lineY - 14 && y <= lineY) {
                    if (Game.localRights >= 1) {
                        Menu.add("Report abuse @whi@" + name, 606);
                    }
                    Menu.add("Add ignore @whi@" + name, 42);
                    Menu.add("Add friend @whi@" + name, 337);
                }
                count++;
            }

            if (type == 4 && (Settings.values[2] == Chat.SETTING_ON || Settings.values[2] == Chat.SETTING_FRIENDS && Game.friendExists(name))) {
                if (y > lineY - 14 && y <= lineY) {
                    Menu.add("Accept trade @whi@" + name, 484);
                }
                count++;
            }

            if ((type == 5 || type == 6) && Settings.privateArea == 0 && Settings.values[1] < Chat.SETTING_OFF) {
                count++;
            }

            if (type == 8 && (Settings.values[2] == Chat.SETTING_ON || Settings.values[2] == Chat.SETTING_FRIENDS && Game.friendExists(name))) {
                if (y > lineY - 14 && y <= lineY) {
                    Menu.add("Accept challenge @whi@" + name, 6);
                }
                count++;
            }
        }
    }

    /**
     * Pre-draw method.
     */
    protected static void prepare() {
        Canvas3D.pixels = Game.chatPixels3D;
        producer.prepare();
        background.draw(0, 0);
    }

    /**
     * Puts a new line in the chat box with no prefix, and the type of 0.
     *
     * @param message the message.
     */
    public static void put(String message) {
        Chat.put(null, message, TYPE_NORMAL);
    }

    /**
     * Puts a new line in the chatbox with no prefix.
     *
     * @param message the message.
     * @param type    the type.
     */
    public static void put(String message, int type) {
        Chat.put(null, message, type);
    }

    /**
     * Puts a new line in the chat box with the provided properties.
     *
     * @param prefix  the prefix.
     * @param message the message.
     * @param type    the type.
     */
    public static void put(String prefix, String message, int type) {
        for (int i = lines.length - 1; i > 0; i--) {
            lines[i].set(lines[i - 1]);
        }
        lines[0].set(prefix, message, type);
    }

    /**
     * Creates the inputs if they don't exist and creates/resets all of the chat lines.
     */
    public static void reset() {
        Chat.setOverlay(-1);
        Chat.setUnderlay(-1);
        Chat.setState(State.NONE);
        Chat.clear(INPUT_CHAT);
        Chat.clear(INPUT_DIALOGUE);
        Chat.setMessage(MESSAGE_INPUT, null);
        Chat.setMessage(MESSAGE_NOTIFY, null);

        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                lines[i] = new Line(null, null, -1);
            } else {
                lines[i].set(null, null, -1);
            }
        }
    }

    /**
     * Opens an input that has a premade message.
     *
     * @param state the input.
     */
    public static void set(State state) {
        set(state, null);
    }

    public static void set(State state, String message) {
        Chat.setState(state);
        Chat.setInput(INPUT_DIALOGUE, "");
        Chat.setMessage(MESSAGE_INPUT, message);
        Chat.redraw = true;
    }

    /**
     * Removes all the characters in the specified type and appends the provided string.
     *
     * @param type the input type.
     * @param s    the string.
     */
    public static void setInput(byte type, String s) {
        StringBuilder b = input.get(type);
        b.delete(0, b.length());
        b.append(s);
    }

    /**
     * Sets the message for the provided type.
     *
     * @param type    the type.
     * @param message the message.
     */
    public static void setMessage(byte type, String message) {
        Chat.message.put(type, message);
        Chat.redraw = true;
    }

    /**
     * Processes all general chat related things.
     */
    public static void update() {
        Chat.Scrollbar.update();

        if (Chat.getOverlay() != -1) {
            Widget widget = Widget.get(Chat.getOverlay());
            if (widget != null && widget.handleSequences(Game.animCycle)) {
                Chat.redraw = true;
            }
        }

        if (Game.clickArea == 3) {
            Chat.redraw = true;
        }

        if (Game.dragArea == 3) {
            Chat.redraw = true;
        }

        if (Chat.getMessage(Chat.MESSAGE_NOTIFY) != null) {
            Chat.redraw = true;
        }

        if (Menu.visible && Menu.area == Area.CHAT) {
            Chat.redraw = true;
        }

        Chat.draw();
    }

    public static enum State {

        ADD_FRIEND(Key.ALL_KEYS, 12), ADD_IGNORE(Key.ALL_KEYS, 12), ENTER_AMOUNT(Key.NUMBER_KEYS, 10), ENTER_NAME(Key.ALL_KEYS, 12), NONE(Key.ALL_KEYS), NOTIFY, REMOVE_FRIEND(Key.ALL_KEYS, 12), REMOVE_IGNORE(Key.ALL_KEYS, 12), SEND_MESSAGE(Key.ALL_KEYS);

        public int charLimit = 80;
        public int flags = 0x0;
        public boolean hasInput = false;

        private State() {
            this.flags = 0;
            this.hasInput = false;
        }

        private State(int flags) {
            this.flags = (byte) flags;
            this.hasInput = true;
        }

        private State(int flags, int limit) {
            this.flags = (byte) flags;
            this.charLimit = (byte) limit;
            this.hasInput = true;
        }

        @Override
        public String toString() {
            String s = this.name();
            StringBuilder b = new StringBuilder().append(s.substring(0, 1));
            b.append(s.replaceAll("_", " ").toLowerCase().substring(1));
            return b.toString();
        }
    }

    public static class Line {

        public String message;
        public String prefix;
        public int type;

        public Line(String prefix, String message, int type) {
            this.prefix = prefix;
            this.message = message;
            this.type = type;
        }

        public void set(Line line) {
            this.prefix = line.prefix;
            this.message = line.message;
            this.type = line.type;
        }

        public void set(String prefix, String message, int type) {
            this.prefix = prefix;
            this.message = message;
            this.type = type;
        }

    }

    public static class Private {
        public static final int DRAW_X = 329;
        public static final int FONT_COLOR = 0x00FFFF;

        public static void draw() {
            if (Settings.privateArea == 0) {
                return;
            }

            BitmapFont font = BitmapFont.NORMAL;

            int count = 0;

            if (Game.nextUpdate != 0) {
                count = 1;
            }

            for (Line line : Chat.lines) {
                if (line == null || line.message == null) {
                    continue;
                }

                int type = line.type;
                String name = line.prefix;

                byte rights = 0;

                if (name != null && name.startsWith("@cr")) {
                    rights = Byte.parseByte(name.substring(3, 4));
                    name = name.substring(5);
                }

                if ((type == 3 || type == 7) && (type == 7 || Settings.values[1] == 0 || Settings.values[1] == 1 && Game.friendExists(name))) {
                    int y = DRAW_X - (count * 13);
                    int x = 4;

                    font.draw("From", x, y, FONT_COLOR, BitmapFont.SHADOW);

                    x += font.getWidth("From ");

                    if (rights > 0) {
                        Game.bitmapModIcons[rights - 1].draw(x, y - 12);
                        x += 14;
                    }

                    font.draw(name + ": " + line.message, x, y, FONT_COLOR, BitmapFont.SHADOW);

                    if (++count >= 5) {
                        return;
                    }
                }

                if (type == 5 && Settings.values[1] < 2) {
                    font.draw(line.message, 4, 329 - count * 13, FONT_COLOR, BitmapFont.SHADOW);
                    if (++count >= 5) {
                        return;
                    }
                }

                if (type == 6 && Settings.values[1] < 2) {
                    font.draw("To " + name + ": " + line.message, 4, 329 - count * 13, FONT_COLOR, BitmapFont.SHADOW);
                    if (++count >= 5) {
                        return;
                    }
                }
            }
        }
    }

    public static class Public {

        public static final int CROWN_PADDING = 14;
        public static final int HEIGHT = 77;
        public static final int MESSAGE_HEIGHT = 14;
        public static final int MESSAGE_START_Y = HEIGHT - 7;
        public static final int WIDTH = 473;
        public static final int SCROLLBAR_X = WIDTH - 10;
        public static final int SEPARATOR_LENGTH = WIDTH + 6;

        public static void draw() {

            BitmapFont font = BitmapFont.NORMAL;
            Canvas2D.setBounds(0, 0, WIDTH, HEIGHT);

            int count = 0;

            for (Line line : Chat.lines) {
                if (line == null || line.message == null) {
                    continue;
                }

                String name = line.prefix;
                String message = line.message;
                int type = line.type;
                int y = (MESSAGE_START_Y - (count * MESSAGE_HEIGHT)) + Chat.get().scrollAmount;
                byte rights = 0;

                if (name != null) {
                    if (name.startsWith("@cr")) {
                        rights = Byte.parseByte(name.substring(3, 4));
                        name = name.substring(5);
                    }
                }

                if (type == TYPE_NORMAL) {
                    if (y > 0 && y < 110) {
                        font.draw(0, line.message, 4, y);
                    }
                    count++;
                }

                if ((type == TYPE_MODERATOR || type == TYPE_PLAYER) && (type == TYPE_MODERATOR || Settings.values[0] == SETTING_ON || Settings.values[0] == SETTING_FRIENDS && Game.friendExists(name))) {
                    if (y > 0 && y < 110) {
                        int x = 4;
                        if (rights > 0) {
                            Game.bitmapModIcons[rights - 1].draw(x, y - 12);
                            x += CROWN_PADDING;
                        }
                        font.draw(0, name + ":", x, y);
                        x += font.getWidth(name) + 8;
                        font.draw(0xFF, message, x, y);
                    }
                    count++;
                }

                if ((type == TYPE_MODERATOR_PRIVATE || type == TYPE_PLAYER_PRIVATE) && Settings.privateArea == 0 && (type == TYPE_MODERATOR_PRIVATE || Settings.values[1] == SETTING_ON || Settings.values[1] == SETTING_FRIENDS && Game.friendExists(name))) {
                    if (y > 0 && y < 110) {
                        int x = 4;
                        font.draw(0, "From", x, y);
                        x += font.getWidth("From ");

                        if (rights > 0) {
                            Game.bitmapModIcons[rights - 1].draw(x, y - 12);
                            x += CROWN_PADDING;
                        }

                        font.draw(0, name + ":", x, y);
                        x += font.getWidth(name) + 8;
                        font.draw(0x800000, message, x, y);
                    }
                    count++;
                }

                if (type == TYPE_TRADE_REQUEST && (Settings.values[2] == SETTING_ON || Settings.values[2] == SETTING_FRIENDS && Game.friendExists(name))) {
                    if (y > 0 && y < 110) {
                        font.draw(0x800080, name + " " + message, 4, y);
                    }
                    count++;
                }

                if (type == TYPE_NOTIFY_PRIVATE && Settings.privateArea == 0 && Settings.values[1] < SETTING_OFF) {
                    if (y > 0 && y < 110) {
                        font.draw(0x800000, message, 4, y);
                    }
                    count++;
                }

                if (type == TYPE_SENT_MESSAGE && Settings.privateArea == 0 && Settings.values[1] < SETTING_OFF) {
                    if (y > 0 && y < 110) {
                        font.draw(0, "To " + name + ":", 4, y);
                        font.draw(0x800000, message, 12 + font.getWidth("To " + name), y);
                    }
                    count++;
                }

                if (type == TYPE_DUEL_REQUEST && (Settings.values[2] == SETTING_ON || Settings.values[2] == SETTING_FRIENDS && Game.friendExists(name))) {
                    if (y > 0 && y < 110) {
                        font.draw(0x7e3200, name + " " + message, 4, y);
                    }
                    count++;
                }
            }

            Canvas2D.reset();
            Canvas2D.drawLineH(0, HEIGHT, SEPARATOR_LENGTH, 0);

            Chat.Scrollbar.draw(SCROLLBAR_X, 0, HEIGHT, count);

            String prefix = new StringBuilder().append(JString.localUsername()).append(": ").toString();
            BitmapFont.NORMAL.draw(prefix, 4, 90, 0);
            BitmapFont.NORMAL.draw(Chat.getInput() + "*", 6 + BitmapFont.NORMAL.getWidth(prefix), 90, 0xFF);
        }
    }

    public static class Scrollbar {
        public static final int HEIGHT = 77;
        public static final int MIN_HEIGHT = 78;

        /**
         * Draws the scroll bar with the height depending on the amount of lines.
         *
         * @param x      the x.
         * @param y      the y.
         * @param height the height.
         * @param count  the line count.
         */
        public static void draw(int x, int y, int height, int count) {

            Chat chat = Chat.get();

            chat.scrollHeight = (count * 14) + 7;

            if (chat.scrollHeight < MIN_HEIGHT) {
                chat.scrollHeight = MIN_HEIGHT;
            }

            Game.drawScrollbar(x, y, height, chat.scrollHeight, chat.scrollHeight - chat.scrollAmount - height);
        }

        /**
         * Updates the scroll bar if there is no overlay widget active.
         */
        public static void update() {
            // If the chat box has a widget active then we won't be processing
            // the
            // scroll bar.
            if (Chat.getWidget(Chat.TYPE_OVERLAY) != -1) {
                return;
            }

            Chat chat = Chat.get();

            chat.scrollAmount = chat.scrollHeight - chat.scrollAmount - 77;

            if (Area.CHAT.containsMouse()) {
                Game.handleScrollbar(chat, 463, 0, Mouse.lastX - 17, Mouse.lastY - 357, 77, chat.scrollHeight, false);
            }

            int i = chat.scrollHeight - 77 - chat.scrollAmount;

            if (i < 0) {
                i = 0;
            }

            if (i > chat.scrollHeight - 77) {
                i = chat.scrollHeight - 77;
            }

            if (chat.scrollAmount != i) {
                chat.scrollAmount = i;
                Chat.redraw = true;
            }

        }
    }

    public static class Settings {

        public static Bitmap background;
        public static int privateArea;
        public static ImageProducer producer;
        public static boolean redraw;
        public static boolean showEffects = true;
        public static int[] values = new int[3];

        static {
            Arrays.fill(Settings.values, Chat.SETTING_ON);
        }

        public static void adjust(int setting) {
            Settings.values[setting] = ((Settings.values[setting] + 1) % (setting == 0 ? 4 : 3));
            Settings.send_to_server();
            Settings.redraw = true;
            Chat.redraw = true;
        }

        public static void draw() {
            if (!redraw) {
                return;
            }

            producer.prepare();
            background.draw(0, 0);

            BitmapFont.NORMAL.draw(JString.PUBLIC_CHAT, 55, 28, 0xffffff, BitmapFont.SHADOW_CENTER);
            BitmapFont.NORMAL.draw(JString.PRIVATE_CHAT, 184, 28, 0xffffff, BitmapFont.SHADOW_CENTER);
            BitmapFont.NORMAL.draw(JString.TRADE_COMPETE, 324, 28, 0xffffff, BitmapFont.SHADOW_CENTER);
            BitmapFont.NORMAL.draw(JString.REPORT_ABUSE, 458, 33, 0xffffff, BitmapFont.SHADOW_CENTER);

            BitmapFont.NORMAL.draw(Chat.SETTINGS_PUBLIC[values[0]], 55, 41, Chat.SETTING_COLOR[values[0]], BitmapFont.SHADOW_CENTER);
            BitmapFont.NORMAL.draw(Chat.SETTINGS_PRIVATE[values[1]], 184, 41, Chat.SETTING_COLOR[values[1]], BitmapFont.SHADOW_CENTER);
            BitmapFont.NORMAL.draw(Chat.SETTINGS_TRADE[values[2]], 324, 41, Chat.SETTING_COLOR[values[2]], BitmapFont.SHADOW_CENTER);

            producer.draw(0, 453);
            Game.producerScene.prepare();

            redraw = false;
        }

        public static void handle() {
            if (Mouse.clickButton == 1) {
                if (Mouse.clicked(6, 467, 100, 32)) {
                    adjust(0);
                } else if (Mouse.clicked(135, 467, 100, 32)) {
                    adjust(1);
                } else if (Mouse.clicked(273, 467, 100, 32)) {
                    adjust(2);
                } else if (Mouse.clicked(412, 467, 100, 32)) {
                    if (Game.widgetOverlay == -1) {
                        Game.closeWidgets();
                        Game.reportAbuseInput = "";
                        Game.reportAbuseMute = false;

                        try {
                            Widget.instance[5985].visible = Game.localRights > 0;
                        } catch (Exception e) {
                        }

                        for (int i = 0; i < Widget.instance.length; i++) {
                            if (Widget.instance[i] == null || Widget.instance[i].actionType != 600) {
                                continue;
                            }
                            Game.reportAbuseWindex = Game.widgetOverlay = Widget.instance[i].parent;
                            break;
                        }
                    } else {
                        Chat.put("Please close the interface you have open before using 'report abuse'");
                    }
                }
            }
        }

        /**
         * Sends a packet to the server telling it what the chat settings are.
         */
        public static void send_to_server() {
            Game.out.writeOpcode(95);
            for (int i : Settings.values) {
                Game.out.writeByte(i);
            }
        }
    }

}
