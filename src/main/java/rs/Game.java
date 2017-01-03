package rs;

import rs.audio.MusicPlayer;
import rs.audio.model.WaveSound;
import rs.cache.Archive;
import rs.cache.Cache;
import rs.cache.impl.*;
import rs.cache.model.ActorConfig;
import rs.cache.model.LocConfig;
import rs.cache.model.ObjConfig;
import rs.cache.model.SpotAnimConfig;
import rs.input.Keyboard;
import rs.input.Mouse;
import rs.input.MouseRecorder;
import rs.input.model.Area;
import rs.input.model.Key;
import rs.io.Buffer;
import rs.io.IsaacCipher;
import rs.io.OnDemand;
import rs.media.*;
import rs.media.impl.*;
import rs.media.impl.Chat.State;
import rs.media.impl.Menu;
import rs.media.impl.widget.CharacterDesign;
import rs.model.Action;
import rs.model.Packet;
import rs.model.Skill;
import rs.net.Connection;
import rs.net.Jaggrab;
import rs.node.Chain;
import rs.node.impl.OnDemandRequest;
import rs.scene.Scene;
import rs.scene.model.*;
import rs.util.BitUtils;
import rs.util.JString;
import rs.util.RSColor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.CRC32;

public class Game extends GameShell {

    public static final boolean VERIFY_CACHE = true;
    public static final int LOC_CLASS_TYPE[] = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
    public static final int MAX_PLAYER_COUNT = 2048;
    public static final int MAX_PLAYER_INDEX = 2047;
    public static final int SPOKEN_PALETTE[] = {0xffff00, 0xff0000, 0x00ff00, 0x00ffff, 0xff00ff, 0xffffff};
    public static final int[][] TAB_BUTTONS = {{539, 573, 169, 205}, {569, 599, 168, 205}, {597, 627, 168, 205}, {625, 669, 168, 203}, {666, 696, 168, 205}, {694, 724, 168, 205}, {722, 756, 169, 205}, {540, 574, 466, 502}, {572, 602, 466, 503}, {599, 629, 466, 503}, {627, 671, 467, 502}, {669, 699, 466, 503}, {696, 726, 466, 503}, {724, 758, 466, 502}};
    public static final boolean WINTER = false;
    public static final int XP_TABLE[];
    private static final long serialVersionUID = -7268339690803378968L;
    public static int netRegionX;
    public static int netRegionY;
    public static boolean aBooleanArray876[];
    public static int actorCount;
    public static int actorIndices[];
    public static Actor actors[];
    public static int animCycle;
    public static byte[] ANIMATED_TEXTURES = {17, 24, 34};
    public static int anInt913;
    public static int anIntArray1030[];
    public static int anIntArray1052[];
    public static int anIntArray1057[];
    public static int anIntArray1203[];
    public static int anIntArray1229[];
    public static int anIntArray873[];
    public static int anIntArray928[];
    public static int anIntArray968[];
    public static int archiveCrc[];
    public static Archive archive;
    public static Bitmap bitmap;
    public static Bitmap bitmaps[];
    public static Bitmap bitmap1;
    public static Bitmap bitmapModIcons[];
    public static Bitmap bitmap2;
    public static Bitmap bitmap3;
    public static Cache cache[];
    public static int cam_cinema_aim_x;
    public static int cam_cinema_aim_y;
    public static int cam_cinema_aim_z;
    public static int cam_cinema_base_speed;
    public static int cam_cinema_dest_x;
    public static int cam_cinema_dest_y;
    public static int cam_cinema_dest_z;
    public static boolean camCinemaMode;
    public static int cam_cinema_rot_base;
    public static int cam_cinema_rot_modifier;
    public static int cam_cinema_speed;
    public static int cam_info_cycle;
    public static int cam_pitch_mod;
    public static int cam_pitch_off;
    public static int cam_x_off;
    public static int xCamOffMod;
    public static int cam_y_off;
    public static int yCamOffMod;
    public static int cam_yaw_mod;
    public static int cam_yaw_off;
    public static int chaseCamPitch;
    public static int chase_cam_pitch_mod;
    public static int chaseCamX;
    public static int chaseCamY;
    public static int chaseCamYaw;
    public static int chaseCamYawMod;
    public static int chatPixels3D[];
    public static int chunkCoords[];
    public static byte chunkLandscapePayload[][];
    public static byte chunkLocPayload[][];
    public static int clickArea;
    public static int clickCycle;
    public static int clickedItemSlot;
    public static int clickedItemWidget;
    public static CollisionMap collisionMaps[];
    public static Connection connection;
    public static IsaacCipher connectionIsaac;
    public static CRC32 crc32;
    public static int crossCycle;
    public static int crossType;
    public static int crossX;
    public static int crossY;
    public static boolean debug = false;
    public static int defaultSettings[];
    public static boolean dialogueOptionActive;
    public static int dragArea;
    public static int dragCycle;
    public static int dragSlot;
    public static int dragStartX;
    public static int dragStartY;
    public static int dragWidget;
    public static boolean dragging;
    public static boolean draggingScrollbar;
    public static int drawCycle;
    public static volatile boolean drawFlames;
    public static int drawX;
    public static int drawY;
    public static int energyLeft;
    public static int entityCount;
    public static int entityIndex[];
    public static int entityUpdateCount;
    public static int entityUpdateIndices[];
    public static Exception errorException;
    public static boolean errorLoading;
    public static volatile boolean flameThread;
    public static int freeFriendsList;
    public static int frenemiesStatus;
    public static int friendCount;
    public static long friendLong[];
    public static String friendName[];
    public static int friendNode[];
    public static short heightMap[][][];
    public static int hoveredChatWidget;
    public static int hoveredSlot;
    public static int hoveredSlotWidget;
    public static int hoveredTabWidget;
    public static int hoveredViewportWidget;
    public static int ignoreCount;
    public static long ignoreLong[];
    public static Sprite imageCompass;
    public static Sprite imageCrosses[];
    public static Sprite imageHeadIcons[];
    public static Sprite imageHitMarks[];
    public static Sprite[] imageMapMarkers;
    public static Sprite[] imageMapDots;
    public static Sprite imageMapedge;
    public static Sprite imageMapFunctions[];
    public static Sprite imageMinimap;
    public static Sprite image_overlay_multiway;
    public static Buffer in;
    public static int inMultiZone;
    public static Game instance;
    public static boolean isFocused;
    public static boolean isMembers = true;
    public static Chain itemPile[][][] = new Chain[4][104][104];
    public static Socket jaggrabSocket;
    public static Landscape landscape;
    public static int landscapeUids[];
    public static long lastClickTime;
    public static int lastMapBaseX;
    public static int lastMapBaseY;
    public static int lastPlane;
    public static int lastPtype1;
    public static int lastPtype2;
    public static int lastPtype3;
    public static int lastSoundIndex;
    public static int lastSoundPosition;
    public static long lastSoundTime;
    public static int lastSoundType;
    public static Sprite locIcon[];
    public static int locIconCount;
    public static int locIconX[];
    public static int locIconY[];
    public static int localPlayerIndex;
    public static int localRights;
    public static int localTileCycle[][];
    public static boolean loggedIn;
    public static Buffer loginBuffer;
    public static int logoutCycle;
    public static int loopCycle;
    public static boolean lowDetail = true;
    public static int LSB_BIT_MASK[];
    public static int mapBaseX;
    public static int mapBaseY;
    public static int mapMarkerX;
    public static int mapMarkerY;
    public static int mapUids[];
    public static int mapZoomModifier;
    public static int mapZoomOffset;
    public static int markActorIndex;
    public static int markOffX;
    public static int markOffY;
    public static int markPlayerIndex;
    public static int markType;
    public static int markX;
    public static int markY;
    public static int markZ;
    public static long messageRecipientNameLong;
    public static int messageStatus;
    public static int minPitch;
    public static int minimapState;
    public static int mouseButtonSetting;
    public static MouseRecorder mouseRecorder;
    public static MusicPlayer music;
    public static int netAliveCycle;
    public static int netCycle;
    public static long nextUpdate;
    public static int nodeIndex = 10;
    public static OnDemand ondemand;
    public static Buffer out;
    public static String password;
    public static int pathArbitraryDest;
    public static int pathDistance[][];
    public static int pathQueueX[];
    public static int pathQueueY[];
    public static int pathWaypoint[][];
    public static int plane;
    public static boolean playerActionPriority[];
    public static String playerAction[];
    public static Buffer playerBuffer[];
    public static int player_count;
    public static int playerIndices[];
    public static Player players[];
    public static int port_offset;
    public static volatile boolean processFlames;
    public static ImageProducer producerBackhmid2;
    public static ImageProducer producerBackleft1;
    public static ImageProducer producerBackleft2;
    public static ImageProducer producerBackright1;
    public static ImageProducer producerBackright2;
    public static ImageProducer producerBacktop1;
    public static ImageProducer producerBackvmid1;
    public static ImageProducer producerBackvmid2;
    public static ImageProducer producerBackvmid3;
    public static ImageProducer producerMinimap;
    public static ImageProducer producerScene;
    public static String progressCaption;
    public static int progressPercent;
    public static Chain projectiles;
    public static int psize;
    public static int ptype;
    public static int reconnectionAttempts;
    public static boolean recordMouse;
    public static boolean redraw;
    public static int redrawCycle;
    public static int regionChunkUniqueIds[][][];
    public static int loadedRegionX;
    public static int loadedRegionY;
    public static byte render_flags[][][];
    public static String reportAbuseInput;
    public static boolean reportAbuseMute;
    public static int reportAbuseWindex;
    public static boolean restrictRegion;
    public static int rndCycle1;
    public static int rndCycle2;
    public static BigInteger rsaModulus = new BigInteger("143690958001225849100503496893758066948984921380482659564113596152800934352119496873386875214251264258425208995167316497331786595942754290983849878549630226741961610780416197036711585670124061149988186026407785250364328460839202438651793652051153157765358767514800252431284681765433239888090564804146588087023");
    public static BigInteger rsaPublicKey = new BigInteger("65537");
    public static Scene scene;
    public static int sceneCycle;
    public static long sceneLoadStart;
    public static boolean sceneLoading;
    public static int sceneState;
    public static int scrollDragBound;
    public static boolean selectedItem;
    public static int selectedItemIndex;
    public static String selectedItemName;
    public static int selectedItemSlot;
    public static int selectedItemWidget;
    public static int selectedMask;
    public static String selectedTooltip;
    public static boolean selectedWidget;
    public static int selectedWidgetIndex;
    public static Player self;
    public static boolean sendCamInfo;
    public static long serverSeed;
    public static boolean serverSentChunk;
    public static int settings[];
    public static int skillExperience[];
    public static int skillLevel[];
    public static int skillRealLevel[];
    public static int soundCount;
    public static int soundDelay[];
    public static int soundIndex[];
    public static int soundType[];
    public static Chain spawnedLocs;
    public static String spoken[];
    public static int spokenColor[];
    public static int spokenCount;
    public static int spokenCycle[];
    public static int spokenEffect[];
    public static int spokenMax;
    public static int spokenOffX[];
    public static int spokenOffY[];
    public static int spokenX[];
    public static int spokenY[];
    public static Chain spotanims;
    public static boolean stopping = false;
    public static int tmpHoveredWidget;
    public static byte tmpTexels[];
    public static String username;
    public static int viewport_pixels[];
    public static int weightCarried;
    public static int welcomeInfo;
    public static int welcomeLastIp;
    public static int welcomeLastPlaydate;
    public static int welcomeNotify;
    public static int welcomeUnreadMessages;
    public static int widgetOverlay;
    public static int widgetUnderlay;
    public static int bytesRead, bytesSent;
    public static int currentBytesRead, currentBytesSent;

    static {
        int i = 0;

        XP_TABLE = new int[99];
        for (int level = 0; level < 99; level++) {
            int real_level = level + 1;
            int j = (int) ((double) real_level + 300D * Math.pow(2D, (double) real_level / 7D));
            i += j;
            XP_TABLE[level] = i / 4;
        }

        i = 0x2;

        LSB_BIT_MASK = new int[32];
        for (int k = 0; k < 32; k++) {
            LSB_BIT_MASK[k] = i - 1;
            i <<= 1;
        }

        //[0] = 2 - 1 = 1
        //2 + 2 = 4
        //[1] = 4 - 1 = 3
        //4 + 4 = 8
        //[2] = 8 - 1 = 7
    }

    public Game() {
        Game.instance = this;

        pathDistance = new int[104][104];
        friendNode = new int[200];
        processFlames = false;
        actors = new Actor[16384];
        actorIndices = new int[16384];
        entityUpdateIndices = new int[1000];
        loginBuffer = Buffer.create(1);
        widgetOverlay = -1;
        skillExperience = new int[Skill.COUNT];
        anIntArray873 = new int[5];
        lastSoundIndex = -1;
        aBooleanArray876 = new boolean[5];
        drawFlames = false;
        reportAbuseInput = JString.BLANK;
        localPlayerIndex = -1;
        players = new Player[MAX_PLAYER_COUNT];
        playerIndices = new int[MAX_PLAYER_COUNT];
        entityIndex = new int[MAX_PLAYER_COUNT];
        playerBuffer = new Buffer[MAX_PLAYER_COUNT];
        Camera.pitchModifier = 1;
        pathWaypoint = new int[104][104];
        tmpTexels = new byte[16384];
        skillLevel = new int[Skill.COUNT];
        ignoreLong = new long[100];
        errorLoading = false;
        anIntArray928 = new int[5];
        localTileCycle = new int[104][104];
        crc32 = new CRC32();
        isFocused = true;
        friendLong = new long[200];
        flameThread = false;
        drawX = -1;
        drawY = -1;
        anIntArray968 = new int[33];
        cache = new Cache[5];
        settings = new int[2000];
        draggingScrollbar = false;
        spokenMax = 50;
        spokenX = new int[spokenMax];
        spokenY = new int[spokenMax];
        spokenOffY = new int[spokenMax];
        spokenOffX = new int[spokenMax];
        spokenColor = new int[spokenMax];
        spokenEffect = new int[spokenMax];
        spokenCycle = new int[spokenMax];
        spoken = new String[spokenMax];
        lastPlane = -1;
        imageHitMarks = new Sprite[20];
        projectiles = new Chain();
        sendCamInfo = false;
        widgetUnderlay = -1;
        anIntArray1030 = new int[5];
        imageMapFunctions = new Sprite[100];
        skillRealLevel = new int[Skill.COUNT];
        defaultSettings = new int[2000];
        anIntArray1052 = new int[151];
        Sidebar.flashingTab = null;
        spotanims = new Chain();
        anIntArray1057 = new int[33];
        bitmaps = new Bitmap[100];
        locIconX = new int[1000];
        locIconY = new int[1000];
        sceneLoading = false;
        friendName = new String[200];
        in = Buffer.create(1);
        archiveCrc = new int[9];
        imageHeadIcons = new Sprite[20];
        imageMapMarkers = new Sprite[2];
        imageMapDots = new Sprite[5];
        playerAction = new String[5];
        playerActionPriority = new boolean[5];
        regionChunkUniqueIds = new int[4][13][13];
        yCamOffMod = 2;
        locIcon = new Sprite[1000];
        restrictRegion = false;
        dialogueOptionActive = false;
        imageCrosses = new Sprite[8];
        loggedIn = false;
        reportAbuseMute = false;
        serverSentChunk = false;
        camCinemaMode = false;
        mapZoomModifier = 1;
        username = JString.BLANK;
        password = JString.BLANK;
        reportAbuseWindex = -1;
        spawnedLocs = new Chain();
        chaseCamPitch = Camera.MIN_PITCH;
        Sidebar.widgetIndex = -1;
        out = Buffer.create(1);
        anIntArray1203 = new int[5];
        soundIndex = new int[50];
        Camera.yawModifier = 2;
        bitmapModIcons = new Bitmap[3];
        anIntArray1229 = new int[151];
        collisionMaps = new CollisionMap[4];
        soundType = new int[50];
        dragging = false;
        soundDelay = new int[50];
        redraw = false;
        TitleScreen.setMessage(JString.BLANK, JString.BLANK);
        xCamOffMod = 2;
        pathQueueX = new int[4000];
        pathQueueY = new int[4000];
        lastSoundType = -1;
    }

    public static void addLoc(int y, int x, int plane, int new_loc_index, int loc_rotation, int new_loc_type, int class_type) {
        if (x >= 1 && y >= 1 && x <= 102 && y <= 102) {
            if (lowDetail && plane != Game.plane) {
                return;
            }

            int loc_uid = 0;

            if (class_type == 0) {
                loc_uid = landscape.getWallUid(plane, x, y);
            } else if (class_type == 1) {
                loc_uid = landscape.getWallDecorationUid(plane, x, y);
            } else if (class_type == 2) {
                loc_uid = landscape.getLocUid(plane, x, y);
            } else if (class_type == 3) {
                loc_uid = landscape.getGroundDecorationUid(plane, x, y);
            }

            if (loc_uid != 0) {
                int loc_arrangement = landscape.getArrangement(plane, x, y, loc_uid);
                int loc_index = loc_uid >> 14 & 0x7fff;
                int loc_type = loc_arrangement & 0x1f;
                int loc_rot = loc_arrangement >> 6;

                if (class_type == 0) {
                    landscape.removeWall(x, plane, y);
                    LocConfig lc = LocConfig.get(loc_index);

                    if (lc.hasCollisions) {
                        collisionMaps[plane].remove_wall(x, y, loc_type, loc_rot, lc.blocksProjectiles);
                    }
                }

                if (class_type == 1) {
                    landscape.removeWallDecoration(0, y, plane, x);
                }

                if (class_type == 2) {
                    landscape.clearLocs(x, y, plane);
                    LocConfig lc = LocConfig.get(new_loc_index);

                    if (x + lc.sizeX > 103 || y + lc.sizeX > 103 || x + lc.sizeY > 103 || y + lc.sizeY > 103) {
                        return;
                    }

                    if (lc.hasCollisions) {
                        collisionMaps[plane].removeLoc(x, y, lc.sizeX, lc.sizeY, loc_rot, lc.blocksProjectiles);
                    }
                }

                if (class_type == 3) {
                    landscape.removeGroundDecoration(x, y, plane);
                    LocConfig lc = LocConfig.get(new_loc_index);

                    if (lc.hasCollisions && lc.isStatic) {
                        collisionMaps[plane].method218(x, y);
                    }
                }
            }

            if (new_loc_index >= 0) {
                int vertex_plane = plane;

                if (vertex_plane < 3 && (render_flags[1][x][y] & 2) == 2) {
                    vertex_plane++;
                }

                Scene.addLoc(landscape, x, y, plane, new_loc_index, new_loc_type, loc_rotation, vertex_plane, collisionMaps[plane], heightMap);
            }
        }
    }

    public static void clear_caches() {
        LocConfig.staticModelCache.clear();
        LocConfig.modelCache.clear();
        ActorConfig.model_cache.clear();
        ObjConfig.model_cache.clear();
        ObjConfig.sprite_cache.clear();
        Player.model_cache.clear();
        SpotAnimConfig.model_cache.clear();
    }

    public static void clear_ingame_producers() {
        Chat.producer = null;
        Chat.Settings.producer = null;
        Game.producerMinimap = null;
        Game.producerScene = null;
        Sidebar.clearProducers();
    }

    public static void close_music_player() {
        method891(false);

        if (music.var2 > 0) {
            music.setVolume(256);
            music.var2 = 0;
        }

        music.halt();
        music = null;
    }

    public static void close_widgets() {
        out.writeOpcode(130);
        if (Sidebar.widgetIndex != -1) {
            Sidebar.widgetIndex = -1;
            Sidebar.draw = true;
            dialogueOptionActive = false;
            Sidebar.drawTabs = true;
        }
        if (Chat.get_overlay() != -1) {
            Chat.set_overlay(-1);
            dialogueOptionActive = false;
        }
        widgetOverlay = -1;
    }

    public static void create_ingame_producers() {
        if (Chat.producer != null) {
            return;
        }

        // Lets the garbage collector get the login screen producers.
        TitleScreen.clear_producers();

        // Set the wrest up.
        Chat.producer = new ImageProducer(479, 96);
        Game.producerMinimap = new ImageProducer(172, 156);
        Canvas2D.clear();
        Game.bitmap1.draw(0, 0);
        Game.producerScene = new ImageProducer(512, 334);
        Canvas2D.clear();
        Chat.Settings.producer = new ImageProducer(496, 50);
        Sidebar.createProducers();

        Game.redraw = true;
    }

    public static void draw_2d_overlay() {
        Chat.Private.draw();

        if (crossType == 1) {
            imageCrosses[crossCycle / 100].drawMasked(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (crossType == 2) {
            imageCrosses[4 + crossCycle / 100].drawMasked(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (widgetUnderlay != -1) {
            Widget.handle_sequences(animCycle, widgetUnderlay);
            Widget.draw(widgetUnderlay, 0, 0, 0);
        }

        if (widgetOverlay != -1) {
            Widget.handle_sequences(animCycle, widgetOverlay);
            Widget.draw(widgetOverlay, 0, 0, 0);
        }

        handle_message_status();

        if (!Menu.visible) {
            Menu.handle();
            Menu.draw_tooltip();
        } else if (Menu.area == Area.VIEWPORT) {
            Menu.draw();
        }

        if (inMultiZone == 1) {
            image_overlay_multiway.drawMasked(472, 296);
        }

        if (Game.debug) {
            int x = 507;
            int y = 17;

            StringBuilder sb = new StringBuilder();
            sb.append(JString.FPS_).append(counter.get());

            BitmapFont.NORMAL.draw(sb.toString(), x, y, 0xFFFF00, BitmapFont.RIGHT | BitmapFont.SHADOW);
            y += 17;

            Runtime runtime = Runtime.getRuntime();
            int memUsed = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);

            sb.delete(0, sb.length());
            sb.append(JString.MEM_).append(memUsed).append('k');

            BitmapFont.NORMAL.draw(sb.toString(), x, y, 0xFFFF00, BitmapFont.RIGHT | BitmapFont.SHADOW);
            y += 17;

            sb.delete(0, sb.length());
            sb.append(JString.OUT_).append(bytesSent).append(JString.BS_);
            sb.append(' ');
            sb.append(JString.IN_).append(bytesRead).append(JString.BS_);

            BitmapFont.NORMAL.draw(sb.toString(), x, y, 0xFFFF00, BitmapFont.RIGHT | BitmapFont.SHADOW);
            y += 17;
        }

        if (nextUpdate > 0) {
            int remaining = (int) (nextUpdate - System.currentTimeMillis());

            if (remaining < 0) {
                nextUpdate = 0;
            }

            int minutes = remaining / 60000;
            int seconds = (remaining / 1000) % 60;

            BitmapFont.NORMAL.draw(JString.SYSTEM_UPDATE_IN + minutes + ":" + (seconds < 10 ? "0" + seconds : seconds), 4, 328, 0xFFFF00, BitmapFont.SHADOW);
        }
    }

    public static void draw_actors(boolean flag) {
        for (int i = 0; i < actorCount; i++) {
            Actor a = actors[actorIndices[i]];
            int uid = 0x20000000 + (actorIndices[i] << 14);

            if (a == null || !a.isVisible() || a.config.aBoolean93 != flag) {
                continue;
            }

            int local_x = a.scene_x >> 7;
            int local_y = a.scene_y >> 7;

            if (local_x < 0 || local_x >= 104 || local_y < 0 || local_y >= 104) {
                continue;
            }

            if (a.size == 1 && (a.scene_x & 0x7f) == 64 && (a.scene_y & 0x7f) == 64) {
                if (localTileCycle[local_x][local_y] == sceneCycle) {
                    continue;
                }

                localTileCycle[local_x][local_y] = sceneCycle;
            }

            if (!a.config.interactable) {
                uid += 0x80000000;
            }

            landscape.add(a, a.scene_x, a.scene_y, get_land_z(a.scene_x, a.scene_y, plane), plane, a.rotation, (a.size - 1) * 64 + 60, a.can_rotate, uid);
        }
    }

    public static void draw_error_screen() {
        Graphics g = Game.instance.graphics;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 765, 503);
        processFlames = false;
        g.setFont(new Font("Helvetica", Font.BOLD, 14));
        g.setColor(Color.YELLOW);

        int x = 30;
        int y = 35;

        g.drawString(errorException.getClass().getSimpleName() + ": " + errorException.getMessage(), x, y);
        x += 20;
        y += 20;

        for (StackTraceElement s : errorException.getStackTrace()) {
            g.drawString(s.toString(), x, y);
            y += 16;
        }
    }

    public static void draw_flames() {
        flameThread = true;
        try {
            long last_time = System.currentTimeMillis();
            int i = 0;
            int delay = 20;
            while (processFlames) {
                Flames.cycle++;
                Flames.handle();
                Flames.handle();
                Flames.handle_palette();
                if (++i > 10) {
                    long current_time = System.currentTimeMillis();
                    int difference = (int) (current_time - last_time) / 10 - delay;
                    delay = 40 - difference;
                    if (delay < 5) {
                        delay = 5;
                    }
                    i = 0;
                    last_time = current_time;
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception _ex) {
                }
            }
        } catch (Exception e) {
        }
        flameThread = false;
    }

    public static void draw_game() {
        if (redraw) {
            redraw = false;
            producerBackleft1.draw(0, 4);
            producerBackleft2.draw(0, 357);
            producerBackright1.draw(722, 4);
            producerBackright2.draw(743, 205);
            producerBacktop1.draw(0, 0);
            producerBackvmid1.draw(516, 4);
            producerBackvmid2.draw(516, 205);
            producerBackvmid3.draw(496, 357);
            producerBackhmid2.draw(0, 338);
            Sidebar.draw = true;
            Chat.redraw = true;
            Chat.Settings.redraw = true;
            Sidebar.drawTabs = true;
            if (sceneState != 2) {
                producerScene.draw(4, 4);
                producerMinimap.draw(550, 4);
            }
        }

        if (sceneState == 2) {
            draw_scene();
        }

        if (Menu.visible && Menu.area == Area.TAB) {
            Sidebar.draw = true;
        }

        if (Sidebar.widgetIndex != -1) {
            boolean redraw = Widget.handle_sequences(animCycle, Sidebar.widgetIndex);
            if (redraw) {
                Sidebar.draw = true;
            }
        }

        if (clickArea == 2) {
            Sidebar.draw = true;
        }

        if (dragArea == 2) {
            Sidebar.draw = true;
        }

        Sidebar.draw();
        Chat.update();

        if (sceneState == 2) {
            draw_minimap();
            producerMinimap.draw(550, 4);
        }

        Sidebar.drawTabs();
        Chat.Settings.draw();
        animCycle = 0;
    }

    public static void draw_marker() {
        if (markType != 2) {
            return;
        }

        set_draw_xy((markX - mapBaseX << 7) + markOffX, (markY - mapBaseY << 7) + markOffY, markZ * 2);

        if (drawX > -1 && loopCycle % 20 < 10) {
            imageHeadIcons[2].drawMasked(drawX - 12, drawY - 28);
        }
    }

    public static void draw_minimap() {
        producerMinimap.prepare();

        if (minimapState == 2) {
            byte mask[] = bitmap1.pixels;
            for (int i = 0; i < mask.length; i++) {
                if (mask[i] == 0) {
                    Canvas2D.pixels[i] = 0;
                }
            }
            imageCompass.draw(0, 0, 33, 33, chaseCamYaw, 256, 25, 25, anIntArray1057, anIntArray968);
            producerScene.prepare();
            return;
        }

        int yaw = chaseCamYaw + cam_yaw_off & 0x7ff;
        int pivot_x = 48 + (self.scene_x >> 5);
        int pivot_y = 464 - (self.scene_y >> 5);

        imageMinimap.draw(25, 5, 146, 151, yaw, 256 + mapZoomOffset, pivot_x, pivot_y, anIntArray1229, anIntArray1052);
        imageCompass.draw(0, 0, 33, 33, yaw, 256, 25, 25, anIntArray1057, anIntArray968);

        for (int i = 0; i < locIconCount; i++) {
            int map_x = (locIconX[i] * 4 + 2) - self.scene_x / 32;
            int map_y = (locIconY[i] * 4 + 2) - self.scene_y / 32;
            draw_to_minimap(locIcon[i], map_x, map_y);
        }

        if (Game.itemPile != null) {
            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    Chain pile = Game.itemPile[plane][x][y];
                    if (pile != null) {
                        int map_x = (x * 4 + 2) - self.scene_x / 32;
                        int map_y = (y * 4 + 2) - self.scene_y / 32;
                        draw_to_minimap(imageMapDots[0], map_x, map_y);
                    }
                }
            }
        }

        for (int i = 0; i < actorCount; i++) {
            Actor a = actors[actorIndices[i]];

            if (a != null && a.isVisible()) {
                ActorConfig ac = a.config;

                if (ac.override_index != null) {
                    ac = ac.get_overriding_config();
                }

                if (ac != null && ac.show_on_minimap && ac.interactable) {
                    int map_x = a.scene_x / 32 - self.scene_x / 32;
                    int map_y = a.scene_y / 32 - self.scene_y / 32;
                    draw_to_minimap(imageMapDots[1], map_x, map_y);
                }
            }
        }

        for (int i = 0; i < player_count; i++) {
            Player p = players[playerIndices[i]];

            if (p != null && p.isVisible()) {
                int map_x = p.scene_x / 32 - self.scene_x / 32;
                int map_y = p.scene_y / 32 - self.scene_y / 32;

                boolean is_friend = false;
                long name_long = JString.toLong(p.name);

                for (int j = 0; j < friendCount; j++) {
                    if (name_long != friendLong[j] || friendNode[j] == 0) {
                        continue;
                    }
                    is_friend = true;
                    break;
                }

                boolean is_teammate = false;

                if (self.team != 0 && p.team != 0 && self.team == p.team) {
                    is_teammate = true;
                }

                if (is_friend) {
                    draw_to_minimap(imageMapDots[3], map_x, map_y);
                } else if (is_teammate) {
                    draw_to_minimap(imageMapDots[4], map_x, map_y);
                } else {
                    draw_to_minimap(imageMapDots[2], map_x, map_y);
                }
            }
        }

        if (markType != 0 && loopCycle % 20 < 10) {
            if (markType == 1 && markActorIndex >= 0 && markActorIndex < actors.length) {
                Actor a = actors[markActorIndex];
                if (a != null) {
                    int map_x = a.scene_x / 32 - self.scene_x / 32;
                    int map_y = a.scene_y / 32 - self.scene_y / 32;
                    draw_minimap_mark(imageMapMarkers[1], map_x, map_y);
                }
            }

            if (markType == 2) {
                int map_x = ((markX - mapBaseX) * 4 + 2) - self.scene_x / 32;
                int map_y = ((markY - mapBaseY) * 4 + 2) - self.scene_y / 32;
                draw_minimap_mark(imageMapMarkers[1], map_x, map_y);
            }

            if (markType == 10 && markPlayerIndex >= 0 && markPlayerIndex < players.length) {
                Player p = players[markPlayerIndex];
                if (p != null) {
                    int map_x = p.scene_x / 32 - self.scene_x / 32;
                    int map_y = p.scene_y / 32 - self.scene_y / 32;
                    draw_minimap_mark(imageMapMarkers[1], map_x, map_y);
                }
            }
        }

        if (mapMarkerX != 0) {
            int map_x = (mapMarkerX * 4 + 2) - self.scene_x / 32;
            int map_y = (mapMarkerY * 4 + 2) - self.scene_y / 32;
            draw_to_minimap(imageMapMarkers[0], map_x, map_y);
        }

        imageMapDots[2].drawMasked(96, 77);
        producerScene.prepare();
    }

    public static void draw_minimap_mark(Sprite s, int map_x, int map_y) {
        int len = map_x * map_x + map_y * map_y;

        if (len > 4225 && len < 0x15f90) {
            int yaw = chaseCamYaw + cam_yaw_off & 0x7ff;
            int sin = Model.sin[yaw];
            int cos = Model.cos[yaw];
            sin = (sin * 256) / (mapZoomOffset + 256);
            cos = (cos * 256) / (mapZoomOffset + 256);
            int x = map_y * sin + map_x * cos >> 16;
            int y = map_y * cos - map_x * sin >> 16;
            double angle = Math.atan2(x, y);
            int draw_x = (int) (Math.sin(angle) * 63D);
            int draw_y = (int) (Math.cos(angle) * 57D);
            imageMapedge.drawRotated((94 + draw_x + 4) - 10, 83 - draw_y - 20, 15, 15, 20, 20, 256, angle);
        } else {
            draw_to_minimap(s, map_x, map_y);
        }
    }

    public static void draw_minimap_tile(int x, int y, int plane, int wall_rgb, int door_rgb) {
        int uid = landscape.getWallUid(plane, x, y);

        if (uid != 0) {
            int arrangement = landscape.getArrangement(plane, x, y, uid);
            int rotation = arrangement >> 6 & 3;
            int type = arrangement & 0x1f;
            int rgb = wall_rgb;

            if (uid > 0) {
                rgb = door_rgb;
            }

            int pixels[] = imageMinimap.pixels;
            int i = 24624 + x * 4 + (103 - y) * 512 * 4;

            LocConfig lc = LocConfig.get(uid >> 14 & 0x7fff);

            if (lc.sceneImageIndex != -1) {
                Bitmap b = bitmaps[lc.sceneImageIndex];
                if (b != null) {
                    int map_x = (lc.sizeX * 4 - b.width) / 2;
                    int map_y = (lc.sizeY * 4 - b.height) / 2;
                    b.draw(48 + x * 4 + map_x, 48 + (104 - y - lc.sizeY) * 4 + map_y);
                }
            } else {
                if (type == 0 || type == 2) {
                    if (rotation == 0) {
                        pixels[i] = rgb;
                        pixels[i + 512] = rgb;
                        pixels[i + 1024] = rgb;
                        pixels[i + 1536] = rgb;
                    } else if (rotation == 1) {
                        pixels[i] = rgb;
                        pixels[i + 1] = rgb;
                        pixels[i + 2] = rgb;
                        pixels[i + 3] = rgb;
                    } else if (rotation == 2) {
                        pixels[i + 3] = rgb;
                        pixels[i + 3 + 512] = rgb;
                        pixels[i + 3 + 1024] = rgb;
                        pixels[i + 3 + 1536] = rgb;
                    } else if (rotation == 3) {
                        pixels[i + 1536] = rgb;
                        pixels[i + 1536 + 1] = rgb;
                        pixels[i + 1536 + 2] = rgb;
                        pixels[i + 1536 + 3] = rgb;
                    }
                }

                if (type == 2) {
                    if (rotation == 3) {
                        pixels[i] = rgb;
                        pixels[i + 512] = rgb;
                        pixels[i + 1024] = rgb;
                        pixels[i + 1536] = rgb;
                    } else if (rotation == 0) {
                        pixels[i] = rgb;
                        pixels[i + 1] = rgb;
                        pixels[i + 2] = rgb;
                        pixels[i + 3] = rgb;
                    } else if (rotation == 1) {
                        pixels[i + 3] = rgb;
                        pixels[i + 3 + 512] = rgb;
                        pixels[i + 3 + 1024] = rgb;
                        pixels[i + 3 + 1536] = rgb;
                    } else if (rotation == 2) {
                        pixels[i + 1536] = rgb;
                        pixels[i + 1536 + 1] = rgb;
                        pixels[i + 1536 + 2] = rgb;
                        pixels[i + 1536 + 3] = rgb;
                    }
                }

                if (type == 3) {
                    if (rotation == 0) {
                        pixels[i] = rgb;
                    } else if (rotation == 1) {
                        pixels[i + 3] = rgb;
                    } else if (rotation == 2) {
                        pixels[i + 3 + 1536] = rgb;
                    } else if (rotation == 3) {
                        pixels[i + 1536] = rgb;
                    }
                }
            }
        }

        uid = landscape.getLocUid(plane, x, y);

        if (uid != 0) {
            int arrangement = landscape.getArrangement(plane, x, y, uid);
            int rotation = arrangement >> 6 & 3;
            int type = arrangement & 0x1f;

            LocConfig lc = LocConfig.get(uid >> 14 & 0x7fff);

            if (lc.sceneImageIndex != -1) {
                Bitmap b = bitmaps[lc.sceneImageIndex];

                if (b != null) {
                    int map_x = (lc.sizeX * 4 - b.width) / 2;
                    int map_y = (lc.sizeY * 4 - b.height) / 2;
                    b.draw(48 + x * 4 + map_x, 48 + (104 - y - lc.sizeY) * 4 + map_y);
                }
            } else if (type == 9) {
                int color = 0xEEEEEE;

                if (uid > 0) {
                    color = 0xEE0000;
                }

                int pixels[] = imageMinimap.pixels;
                int i = 24624 + x * 4 + (103 - y) * 512 * 4;

                if (rotation == 0 || rotation == 2) {
                    pixels[i + 1536] = color;
                    pixels[i + 1024 + 1] = color;
                    pixels[i + 512 + 2] = color;
                    pixels[i + 3] = color;
                } else {
                    pixels[i] = color;
                    pixels[i + 512 + 1] = color;
                    pixels[i + 1024 + 2] = color;
                    pixels[i + 1536 + 3] = color;
                }
            }
        }

        uid = landscape.getGroundDecorationUid(plane, x, y);

        if (uid != 0) {
            LocConfig lc = LocConfig.get(uid >> 14 & 0x7fff);
            if (lc.sceneImageIndex != -1) {
                Bitmap b = bitmaps[lc.sceneImageIndex];
                if (b != null) {
                    int map_x = (lc.sizeX * 4 - b.width) / 2;
                    int map_y = (lc.sizeY * 4 - b.height) / 2;
                    b.draw(48 + x * 4 + map_x, 48 + (104 - y - lc.sizeY) * 4 + map_y);
                }
            }
        }
    }

    public static void draw_players(boolean only_local) {
        if (self.scene_x >> 7 == mapMarkerX && self.scene_y >> 7 == mapMarkerY) {
            mapMarkerX = 0;
        }

        int count = player_count;

        if (only_local) {
            count = 1;
        }

        for (int i = 0; i < count; i++) {
            Player p;
            int uid;

            if (only_local) {
                p = self;
                uid = MAX_PLAYER_INDEX << 14;
            } else {
                p = players[playerIndices[i]];
                uid = playerIndices[i] << 14;
            }

            if (p == null || !p.isVisible()) {
                continue;
            }

            p.low_lod = false;

            if ((lowDetail && player_count > 50 || player_count > 200) && !only_local && p.move_seq_index == p.stand_animation) {
                p.low_lod = true;
            }

            int local_x = p.scene_x >> 7;
            int local_y = p.scene_y >> 7;

            if (local_x < 0 || local_x >= 104 || local_y < 0 || local_y >= 104) {
                continue;
            }

            if (p.loc_model != null && loopCycle >= p.loc_start_cycle && loopCycle < p.loc_end_cycle) {
                p.low_lod = false;
                p.scene_z = get_land_z(p.scene_x, p.scene_y, plane);
                landscape.add(p, p.scene_x, p.scene_y, p.scene_z, plane, p.loc_x0, p.loc_y0, p.loc_x1, p.loc_y1, p.rotation, uid);
                continue;
            }

            if ((p.scene_x & 0x7f) == 64 && (p.scene_y & 0x7f) == 64) {
                if (localTileCycle[local_x][local_y] == sceneCycle) {
                    continue;
                }
                localTileCycle[local_x][local_y] = sceneCycle;
            }

            p.scene_z = get_land_z(p.scene_x, p.scene_y, plane);
            landscape.add(p, p.scene_x, p.scene_y, p.scene_z, plane, p.rotation, 60, p.can_rotate, uid);
        }

    }

    public static void draw_projectiles() {
        for (Projectile p = (Projectile) projectiles.top(); p != null; p = (Projectile) projectiles.next()) {
            if (p.plane != plane || loopCycle > p.cycle_end) {
                p.detach();
                return;
            }

            if (loopCycle >= p.cycle_start) {
                if (p.target_index > 0) {
                    Actor a = actors[p.target_index - 1];
                    if (a != null && a.scene_x >= 0 && a.scene_x < 13312 && a.scene_y >= 0 && a.scene_y < 13312) {
                        p.update(loopCycle, a.scene_x, a.scene_y, get_land_z(a.scene_x, a.scene_y, p.plane) - p.offset_z);
                    }
                }

                if (p.target_index < 0) {
                    int index = -p.target_index - 1;
                    Player pl;

                    if (index == localPlayerIndex) {
                        pl = self;
                    } else {
                        pl = players[index];
                    }

                    if (pl != null && pl.scene_x >= 0 && pl.scene_x < 13312 && pl.scene_y >= 0 && pl.scene_y < 13312) {
                        p.update(loopCycle, pl.scene_x, pl.scene_y, get_land_z(pl.scene_x, pl.scene_y, p.plane) - p.offset_z);
                    }
                }

                p.update(animCycle);
                landscape.add(p, (int) p.scene_x, (int) p.scene_y, (int) p.scene_z, plane, p.rotation, 60, false, -1);

            }
        }
    }

    public static void draw_scene() {
        sceneCycle++;

        draw_players(true);
        draw_actors(true);
        draw_players(false);
        draw_actors(false);
        draw_projectiles();
        draw_spotanims();

        if (!camCinemaMode) {
            int i = chaseCamPitch;

            if (minPitch / 256 > i) {
                i = minPitch / 256;
            }

            if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i) {
                i = anIntArray1203[4] + 128;
            }

            int yaw = chaseCamYaw + cam_pitch_off & 0x7ff;
            Camera.set(chaseCamX, chaseCamY, get_land_z(self.scene_x, self.scene_y, plane) - 50, 600 + i * 3, i, yaw);
        }

        int occlusion_top_plane;

        if (!camCinemaMode) {
            occlusion_top_plane = get_max_visible_plane();
        } else {
            occlusion_top_plane = get_max_visible_plane_cinematic();
        }

        int cam_x = Camera.x;
        int cam_y = Camera.y;
        int cam_z = Camera.z;
        int cam_pitch = Camera.pitch;
        int cam_yaw = Camera.yaw;

        for (int i = 0; i < 5; i++) {
            if (aBooleanArray876[i]) {
                int amount = (int) ((Math.random() * (double) (anIntArray873[i] * 2 + 1) - (double) anIntArray873[i]) + Math.sin((double) anIntArray1030[i] * ((double) anIntArray928[i] / 100D)) * (double) anIntArray1203[i]);
                if (i == 0) {
                    Camera.x += amount;
                } else if (i == 1) {
                    Camera.z += amount;
                } else if (i == 2) {
                    Camera.y += amount;
                } else if (i == 3) {
                    Camera.yaw = Camera.yaw + amount & 0x7ff;
                } else if (i == 4) {
                    Camera.pitch += amount;

                    if (Camera.pitch < 128) {
                        Camera.pitch = 128;
                    }

                    if (Camera.pitch > 383) {
                        Camera.pitch = 383;
                    }
                }
            }
        }

        int cycle_3d = Canvas3D.cycle;

        Model.scene_clickable = true;
        Model.hovered_count = 0;
        Model.mouse_x = Mouse.lastX - 4;
        Model.mouse_y = Mouse.lastY - 4;
        Canvas2D.clear();

        landscape.draw(Camera.x, Camera.y, Camera.yaw, Camera.z, occlusion_top_plane, Camera.pitch);
        landscape.clearLocs();

        draw_scene_2d();
        draw_marker();
        scroll_textures(cycle_3d);
        draw_2d_overlay();

        producerScene.draw(4, 4);
        Camera.x = cam_x;
        Camera.z = cam_z;
        Camera.y = cam_y;
        Camera.pitch = cam_pitch;
        Camera.yaw = cam_yaw;
    }

    public static void draw_scene_2d() {
        spokenCount = 0;

        for (int i = -1; i < player_count + actorCount; i++) {
            Entity e;

            if (i == -1) {
                e = self;
            } else if (i < player_count) {
                e = players[playerIndices[i]];
            } else {
                e = actors[actorIndices[i - player_count]];
            }

            if (e == null || !e.isVisible()) {
                continue;
            }

            if (e instanceof Actor) {
                ActorConfig c = ((Actor) e).config;

                if (c.override_index != null) {
                    c = c.get_overriding_config();
                }

                if (c == null) {
                    continue;
                }
            }

            if (i < player_count) {
                int offset_y = 30;
                Player p = (Player) e;

                if (p.headicon_flag != 0) {
                    set_draw_xy(e, e.height + 15);

                    if (drawX > -1) {
                        for (int icon = 0; icon < 8; icon++) {
                            if ((p.headicon_flag & (1 << icon)) != 0) {
                                Sprite s = imageHeadIcons[icon];

                                if (s != null) {
                                    s.drawMasked(drawX - 12, drawY - offset_y);
                                    offset_y += 25;
                                }
                            }
                        }
                    }
                }

                if (i >= 0 && markType == 10 && markPlayerIndex == playerIndices[i]) {
                    set_draw_xy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[1].drawMasked(drawX - 12, drawY - offset_y);
                    }
                }
            } else {
                ActorConfig ac = ((Actor) e).config;

                if (ac.head_icon >= 0 && ac.head_icon < imageHeadIcons.length) {
                    set_draw_xy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[ac.head_icon].drawMasked(drawX - 12, drawY - 30);
                    }
                }

                if (markType == 1 && markActorIndex == actorIndices[i - player_count] && loopCycle % 20 < 10) {
                    set_draw_xy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[2].drawMasked(drawX - 12, drawY - 28);
                    }
                }
            }

            if (e.spoken_message != null && (i >= player_count || Chat.Settings.values[0] == 0 || Chat.Settings.values[0] == 3 || Chat.Settings.values[0] == 1 && friend_exists(((Player) e).name))) {
                set_draw_xy(e, e.height);

                if (drawX > -1 && spokenCount < spokenMax) {
                    spokenOffX[spokenCount] = BitmapFont.BOLD.getWidth(e.spoken_message) / 2;
                    spokenOffY[spokenCount] = BitmapFont.BOLD.height;

                    spokenX[spokenCount] = drawX;
                    spokenY[spokenCount] = drawY;

                    spokenColor[spokenCount] = e.spoken_color;
                    spokenEffect[spokenCount] = e.spoken_effect;
                    spokenCycle[spokenCount] = e.spoken_life;
                    spoken[spokenCount++] = e.spoken_message;

                    if (Chat.Settings.show_effects && e.spoken_effect >= 1 && e.spoken_effect <= 3) {
                        spokenOffY[spokenCount] += 10;
                        spokenY[spokenCount] += 5;
                    }

                    if (Chat.Settings.show_effects && e.spoken_effect == 4) {
                        spokenOffX[spokenCount] = 60;
                    }

                    if (Chat.Settings.show_effects && e.spoken_effect == 5) {
                        spokenOffY[spokenCount] += 5;
                    }
                }
            }

            if (e.combat_cycle > loopCycle) {
                set_draw_xy(e, e.height + 15);

                if (drawX > -1) {
                    Canvas2D.fillRect(drawX - 15, drawY - 3, 30, 5, 0xFF0000);
                    Canvas2D.fillRect(drawX - 15, drawY - 3, (int) (30 * (e.current_health / (double) e.max_health)), 5, 0x00FF00);
                }
            }

            for (int mark = 0; mark < 4; mark++) {
                if (e.hit_cycle[mark] > loopCycle) {
                    set_draw_xy(e, e.height / 2);

                    if (drawX > -1) {
                        if (mark == 1) {
                            drawY -= 20;
                        } else if (mark == 2) {
                            drawX -= 15;
                            drawY -= 10;
                        } else if (mark == 3) {
                            drawX += 15;
                            drawY -= 10;
                        }

                        imageHitMarks[e.hit_type[mark]].drawMasked(drawX - 12, drawY - 12);
                        BitmapFont.SMALL.draw(String.valueOf(e.hit_damage[mark]), drawX - 1, drawY + 3, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
                    }
                }
            }

        }

        for (int i = 0; i < spokenCount; i++) {
            int x = spokenX[i];
            int y = spokenY[i];
            int off_x = spokenOffX[i];
            int off_y = spokenOffY[i];

            boolean flag = true;
            while (flag) {
                flag = false;
                for (int j = 0; j < i; j++) {
                    if (y + 2 > spokenY[j] - spokenOffY[j] && y - off_y < spokenY[j] + 2 && x - off_x < spokenX[j] + spokenOffX[j] && x + off_x > spokenX[j] - spokenOffX[j] && spokenY[j] - spokenOffY[j] < y) {
                        y = spokenY[j] - spokenOffY[j];
                        flag = true;
                    }
                }
            }

            drawX = spokenX[i];
            drawY = spokenY[i] = y;
            String s = spoken[i];

            if (Chat.Settings.show_effects) {
                int color = 0xffff00;

                if (spokenColor[i] < 6) {
                    color = SPOKEN_PALETTE[spokenColor[i]];
                }

                if (spokenColor[i] == 6) {
                    color = sceneCycle % 20 >= 10 ? 0xffff00 : 0xff0000;
                }

                if (spokenColor[i] == 7) {
                    color = sceneCycle % 20 >= 10 ? 65535 : 255;
                }

                if (spokenColor[i] == 8) {
                    color = sceneCycle % 20 >= 10 ? 0x80ff80 : 45056;
                }

                if (spokenColor[i] == 9) {
                    int cycle = 150 - spokenCycle[i];

                    if (cycle < 50) {
                        color = 0xff0000 + (0x0500 * cycle);
                    } else if (cycle < 100) {
                        color = 0xffff00 - 0x50000 * (cycle - 50);
                    } else if (cycle < 150) {
                        color = 0xFF00 + 5 * (cycle - 100);
                    }
                }

                if (spokenColor[i] == 10) {
                    int cycle = 150 - spokenCycle[i];

                    if (cycle < 50) {
                        color = 0xff0000 + 5 * cycle;
                    } else if (cycle < 100) {
                        color = 0xff00ff - 0x50000 * (cycle - 50);
                    } else if (cycle < 150) {
                        color = (255 + 0x50000 * (cycle - 100)) - 5 * (cycle - 100);
                    }
                }

                if (spokenColor[i] == 11) {
                    int cycle = 150 - spokenCycle[i];

                    if (cycle < 50) {
                        color = 0xffffff - 0x50005 * cycle;
                    } else if (cycle < 100) {
                        color = 65280 + 0x50005 * (cycle - 50);
                    } else if (cycle < 150) {
                        color = 0xffffff - 0x50000 * (cycle - 100);
                    }
                }

                if (spokenEffect[i] <= 1 || spokenEffect[i] > 5) {
                    BitmapFont.BOLD.draw(s, drawX, drawY, color, BitmapFont.SHADOW_CENTER);
                }

                if (spokenEffect[i] == 2) {
                    BitmapFont.BOLD.drawCenteredWavy2(drawX, s, sceneCycle, drawY + 1, 0);
                    BitmapFont.BOLD.drawCenteredWavy2(drawX, s, sceneCycle, drawY, color);
                }

                if (spokenEffect[i] == 3) {
                    BitmapFont.BOLD.drawCenteredWavy(150 - spokenCycle[i], s, true, sceneCycle, drawY + 1, drawX, 0);
                    BitmapFont.BOLD.drawCenteredWavy(150 - spokenCycle[i], s, true, sceneCycle, drawY, drawX, color);
                }

                if (spokenEffect[i] == 4) { // Scroll
                    Canvas2D.setBounds(drawX - 50, 0, drawX + 50, 334);
                    BitmapFont.BOLD.draw(s, (drawX + 50) - (((150 - spokenCycle[i]) * (BitmapFont.BOLD.getWidth(s) + 100)) / 150), drawY, color, BitmapFont.SHADOW);
                    Canvas2D.reset();
                }

                if (spokenEffect[i] == 5) { // Slide
                    int cycle = 150 - spokenCycle[i];
                    int offsetY = 0;

                    if (cycle < 25) {
                        offsetY = cycle - 25;
                    } else if (cycle > 125) {
                        offsetY = cycle - 125;
                    }

                    Canvas2D.setBounds(0, drawY - BitmapFont.BOLD.height - 1, 512, drawY + 5);
                    BitmapFont.BOLD.draw(s, drawX, drawY + offsetY, color, BitmapFont.SHADOW_CENTER);
                    Canvas2D.reset();
                }
            } else {
                BitmapFont.BOLD.draw(s, drawX, drawY, 0xFFFF00, BitmapFont.SHADOW_CENTER);
            }
        }
    }

    public static void draw_scrollbar(int x, int y, int height, int scroll_height, int scroll_amount) {
        int grip_length = ((height - 32) * height) / scroll_height;

        if (grip_length < 8) {
            grip_length = 8;
        }

        int offset_y = ((height - 32 - grip_length) * scroll_amount) / (scroll_height - height);

        bitmap3.draw(x, y);
        bitmap2.draw(x, (y + height) - 16);

        Canvas2D.fillRect(x, y + 16, 16, height - 32, RSColor.SCROLLBAR_BACKGROUND);
        Canvas2D.fillRect(x, y + 16 + offset_y, 16, grip_length, RSColor.SCROLLBAR_FOREGROUND);
        Canvas2D.drawLineV(x, y + 16 + offset_y, grip_length, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineV(x + 1, y + 16 + offset_y, grip_length, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineH(x, y + 16 + offset_y, 16, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineH(x, y + 17 + offset_y, 16, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineV(x + 15, y + 16 + offset_y, grip_length, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineV(x + 14, y + 17 + offset_y, grip_length - 1, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineH(x, y + 15 + offset_y + grip_length, 16, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineH(x + 1, y + 14 + offset_y + grip_length, 15, RSColor.SCROLLBAR_LOWLIGHT);
    }

    public static void draw_spotanims() {
        SpotAnim a = (SpotAnim) spotanims.top();
        for (; a != null; a = (SpotAnim) spotanims.next()) {
            if (a.plane != plane || a.seq_finished) {
                a.detach();
            } else if (loopCycle >= a.cycle_end) {
                a.update(animCycle);

                if (a.seq_finished) {
                    a.detach();
                } else {
                    landscape.add(a, a.x, a.y, a.z, a.plane, 0, 60, false, -1);
                }
            }
        }
    }

    public static void draw_to_minimap(Sprite s, int x, int y) {
        int angle = chaseCamYaw + cam_yaw_off & 0x7ff;
        int len = x * x + y * y;

        if (len > 6400) {
            return;
        }

        int sin = Model.sin[angle];
        int cos = Model.cos[angle];
        sin = (sin * 256) / (mapZoomOffset + 256);
        cos = (cos * 256) / (mapZoomOffset + 256);
        int map_x = y * sin + x * cos >> 16;
        int map_y = y * cos - x * sin >> 16;

        if (len > 2500) {
            s.drawTo(bitmap1, ((94 + map_x) - s.cropWidth / 2) + 4, 83 - map_y - s.cropHeight / 2 - 4);
            return;
        } else {
            s.drawMasked(((94 + map_x) - s.cropWidth / 2) + 4, 83 - map_y - s.cropHeight / 2 - 4);
            return;
        }
    }

    public static String format_item_amount(int amount) {
        StringBuilder b = new StringBuilder(String.valueOf(amount));

        for (int i = b.length() - 3; i > 0; i -= 3) {
            b.insert(i, ',');
        }

        String s = b.toString();
        int len = b.length();

        if (len > 12) {
            b.insert(0, JString.GR2).replace(6, 22, JString.BLANK).append(JString._BILLION_).append(JString.WHI).append('(');
        } else if (len > 8) {
            b.insert(0, JString.GRE).replace(8, 18, JString.BLANK).append(JString._MILLION_).append(JString.WHI).append('(');
        } else if (len > 4) {
            b.insert(0, JString.CYA).replace(8, 14, JString.BLANK).append(JString.K_).append(JString.WHI).append('(');
        }

        return b.insert(0, ' ').append(s).append(')').toString();
    }

    public static boolean frenemy_option_valid(Widget w, boolean flag) {
        int i = w.action_type;
        if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
            if (i >= 801) {
                i -= 701;
            } else if (i >= 701) {
                i -= 601;
            } else if (i >= 101) {
                i -= 101;
            } else {
                i--;
            }
            Menu.add("Remove @whi@" + friendName[i], 792);
            Menu.add("Message @whi@" + friendName[i], 639);
            return true;
        } else if (i >= 401 && i <= 500) {
            Menu.add("Remove @whi@" + w.message_disabled, 322);
            return true;
        } else {
            return false;
        }
    }

    public static void friend_add(long name_long) {
        if (name_long == 0L) {
            return;
        }

        if (friendCount >= 100 && freeFriendsList != 1) {
            Chat.put(JString.FRIENDS_FULL, 0);
            return;
        }

        if (friendCount >= 200) {
            Chat.put(JString.FRIENDS_FULL, 0);
            return;
        }

        String name = JString.getFormattedString(name_long);

        for (int i = 0; i < friendCount; i++) {
            if (friendLong[i] == name_long) {
                Chat.put(name + " is already on your friend list", 0);
                return;
            }
        }

        for (int j = 0; j < ignoreCount; j++) {
            if (ignoreLong[j] == name_long) {
                Chat.put("Please remove " + name + " from your ignore list first", 0);
                return;
            }
        }

        if (name.equals(self.name)) {
            return;
        } else {
            friendName[friendCount] = name;
            friendLong[friendCount] = name_long;
            friendNode[friendCount] = 0;
            friendCount++;
            Sidebar.draw = true;
            out.writeOpcode(188);
            out.writeLong(name_long);
            return;
        }
    }

    public static boolean friend_exists(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < friendCount; i++) {
            if (s.equalsIgnoreCase(friendName[i])) {
                return true;
            }
        }
        return s.equalsIgnoreCase(self.name);
    }

    public static void friend_remove(long name_long) {
        try {
            if (name_long == 0L) {
                return;
            }

            for (int i = 0; i < friendCount; i++) {
                if (friendLong[i] != name_long) {
                    continue;
                }

                friendCount--;
                Sidebar.draw = true;
                for (int j = i; j < friendCount; j++) {
                    friendName[j] = friendName[j + 1];
                    friendNode[j] = friendNode[j + 1];
                    friendLong[j] = friendLong[j + 1];
                }

                out.writeOpcode(215);
                out.writeLong(name_long);
                break;
            }
        } catch (RuntimeException runtimeexception) {
            Signlink.error("18622, " + name_long + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public static void generate_minimap(int z) {
        int pixels[] = imageMinimap.pixels;

        Arrays.fill(pixels, 0);

        for (int y = 1; y < 103; y++) {
            int i = 24628 + (103 - y) * 512 * 4;
            for (int x = 1; x < 103; x++) {
                if ((render_flags[z][x][y] & 0x18) == 0) {
                    landscape.drawMinimapTile(pixels, i, 512, z, x, y);
                }

                if (z < 3 && (render_flags[z + 1][x][y] & 8) != 0) {
                    landscape.drawMinimapTile(pixels, i, 512, z + 1, x, y);
                }

                i += 4;
            }

        }

        int wall_color = ((238 + (int) (Math.random() * 20D)) - 10 << 16) + ((238 + (int) (Math.random() * 20D)) - 10 << 8) + ((238 + (int) (Math.random() * 20D)) - 10);
        int door_color = (238 + (int) (Math.random() * 20D)) - 10 << 16;
        imageMinimap.prepare();

        for (int y = 1; y < 103; y++) {
            for (int x = 1; x < 103; x++) {
                if ((render_flags[z][x][y] & 0x18) == 0) {
                    draw_minimap_tile(x, y, z, wall_color, door_color);
                }
                if (z < 3 && (render_flags[z + 1][x][y] & 8) != 0) {
                    draw_minimap_tile(x, y, z + 1, wall_color, door_color);
                }
            }

        }

        producerScene.prepare();
        locIconCount = 0;

        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                int uid = landscape.getGroundDecorationUid(plane, x, y);
                if (uid != 0) {
                    int i = LocConfig.get(uid >> 14 & 0x7fff).icon;
                    if (i >= 0) {
                        int map_x = x;
                        int map_y = y;
                        if (i != 22 && i != 29 && i != 34 && i != 36 && i != 46 && i != 47 && i != 48) {
                            int collision_map[][] = collisionMaps[plane].flags;
                            for (int i4 = 0; i4 < 10; i4++) {
                                int random = (int) (Math.random() * 4D);
                                if (random == 0 && map_x > 0 && map_x > x - 3 && (collision_map[map_x - 1][map_y] & 0x1280108) == 0) {
                                    map_x--;
                                } else if (random == 1 && map_x < 103 && map_x < x + 3 && (collision_map[map_x + 1][map_y] & 0x1280180) == 0) {
                                    map_x++;
                                } else if (random == 2 && map_y > 0 && map_y > y - 3 && (collision_map[map_x][map_y - 1] & 0x1280102) == 0) {
                                    map_y--;
                                } else if (random == 3 && map_y < 103 && map_y < y + 3 && (collision_map[map_x][map_y + 1] & 0x1280120) == 0) {
                                    map_y++;
                                }
                            }

                        }
                        locIcon[locIconCount] = imageMapFunctions[i];
                        locIconX[locIconCount] = map_x;
                        locIconY[locIconCount] = map_y;
                        locIconCount++;
                    }
                }
            }

        }

    }

    public static Archive get_archive(String caption, int archive_index, String archive_name, int archive_crc, int percent) {
        byte[] data = null;

        try {
            if (cache[0] != null) {
                data = cache[0].get(archive_index);
            }
        } catch (Exception _ex) {
        }

        if (VERIFY_CACHE) {
            if (data != null) {
                crc32.reset();
                crc32.update(data);
                int crc = (int) crc32.getValue();
                if (crc != archive_crc) {
                    data = null;
                }
            }
        }

        if (data != null) {
            return new Archive(new Buffer(data));
        }

        while (data == null) {
            String error = JString.UNKNOWN_ERROR;
            instance.drawProgress(JString.REQUESTING_ + caption, percent);

            try {
                DataInputStream in = Jaggrab.request(archive_name + archive_crc);

                byte index_data[] = new byte[6];
                in.readFully(index_data, 0, 6);

                Buffer idx = new Buffer(index_data);
                idx.position = 3;

                int size = idx.readMedium() + 6;
                int read = 6;

                data = new byte[size];
                System.arraycopy(index_data, 0, data, 0, 6);

                while (read < size) {
                    int remaining = size - read;

                    if (remaining > 1000) {
                        remaining = 1000;
                    }

                    int bytes_read = in.read(data, read, remaining);

                    if (bytes_read < 0) {
                        error = "Length error: " + read + "/" + size;
                        throw new IOException("EOF");
                    }

                    read += bytes_read;
                    instance.drawProgress("Loading " + caption + " - " + (read * 100) / size + "%", percent);
                }
                in.close();

                try {
                    if (cache[0] != null) {
                        cache[0].put(data, archive_index);
                    }
                } catch (Exception _ex) {
                    cache[0] = null;
                }

                if (VERIFY_CACHE) {
                    if (data != null) {
                        crc32.reset();
                        crc32.update(data);

                        int readCRC = (int) crc32.getValue();
                        if (readCRC != archive_crc) {
                            data = null;
                            error = "Checksum error: " + readCRC;
                        }
                    }
                }
            } catch (IOException ioe) {
                if (error.equals(JString.UNKNOWN_ERROR)) {
                    error = JString.CONNECTION_ERROR;
                }

                data = null;
            } catch (NullPointerException _ex) {
                error = JString.NULL_ERROR;
                data = null;

                if (!Signlink.error) {
                    return null;
                }
            } catch (ArrayIndexOutOfBoundsException _ex) {
                error = JString.BOUNDS_ERROR;
                data = null;

                if (!Signlink.error) {
                    return null;
                }
            } catch (Exception _ex) {
                error = JString.UNEXPECTED_ERROR;
                data = null;

                if (!Signlink.error) {
                    return null;
                }
            }

            if (Game.stopping) {
                return null;
            }

            if (data == null) {
                for (int i = 5; i > 0; i--) {
                    try {
                        instance.drawProgress(error + " - Retrying in " + i, 20);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return new Archive(new Buffer(data));
    }

    public static Component get_component() {
        return instance.getComponent();
    }

    public static int get_land_z(int x, int y, int plane) {
        int local_x = x >> 7;
        int local_y = y >> 7;

        if (local_x < 0 || local_y < 0 || local_x > 103 || local_y > 103) {
            return 0;
        }

        int z = plane;

        if (z < 3 && (render_flags[1][local_x][local_y] & 2) == 2) {
            z++;
        }

        int tile_x = x & 0x7f;
        int tile_y = y & 0x7f;
        int i2 = heightMap[z][local_x][local_y] * (128 - tile_x) + heightMap[z][local_x + 1][local_y] * tile_x >> 7;
        int j2 = heightMap[z][local_x][local_y + 1] * (128 - tile_x) + heightMap[z][local_x + 1][local_y + 1] * tile_x >> 7;
        return ((i2 * (128 - tile_y) + (j2 * tile_y)) >> 7);
    }

    public static int get_max_visible_plane() {
        int occlusion_plane = 3;

        // Once the pitch is below this, start checking to see if the camera is
        // within a roof tile.
        if (Camera.pitch < 310) {
            int cam_local_x = Camera.x >> 7;
            int cam_local_y = Camera.y >> 7;
            int local_x = self.scene_x >> 7;
            int local_y = self.scene_y >> 7;

            if ((render_flags[plane][cam_local_x][cam_local_y] & 4) != 0) {
                occlusion_plane = plane;
            }

            int d_x;
            if (local_x > cam_local_x) {
                d_x = local_x - cam_local_x;
            } else {
                d_x = cam_local_x - local_x;
            }

            int d_y;
            if (local_y > cam_local_y) {
                d_y = local_y - cam_local_y;
            } else {
                d_y = cam_local_y - local_y;
            }

            if (d_x > d_y) {
                int i2 = (d_y * 0x10000) / d_x;
                int k2 = 0x8000;
                while (cam_local_x != local_x) {
                    if (cam_local_x < local_x) {
                        cam_local_x++;
                    } else if (cam_local_x > local_x) {
                        cam_local_x--;
                    }

                    if ((render_flags[plane][cam_local_x][cam_local_y] & 4) != 0) {
                        occlusion_plane = plane;
                    }

                    k2 += i2;
                    if (k2 >= 0x10000) {
                        k2 -= 0x10000;

                        if (cam_local_y < local_y) {
                            cam_local_y++;
                        } else if (cam_local_y > local_y) {
                            cam_local_y--;
                        }

                        if ((render_flags[plane][cam_local_x][cam_local_y] & 4) != 0) {
                            occlusion_plane = plane;
                        }
                    }
                }
            } else {
                int j2 = (d_x * 0x10000) / d_y;
                int l2 = 0x8000;

                while (cam_local_y != local_y) {
                    if (cam_local_y < local_y) {
                        cam_local_y++;
                    } else if (cam_local_y > local_y) {
                        cam_local_y--;
                    }

                    if ((render_flags[plane][cam_local_x][cam_local_y] & 4) != 0) {
                        occlusion_plane = plane;
                    }

                    l2 += j2;
                    if (l2 >= 0x10000) {
                        l2 -= 0x10000;

                        if (cam_local_x < local_x) {
                            cam_local_x++;
                        } else if (cam_local_x > local_x) {
                            cam_local_x--;
                        }

                        if ((render_flags[plane][cam_local_x][cam_local_y] & 4) != 0) {
                            occlusion_plane = plane;
                        }
                    }
                }
            }
        }

        if ((render_flags[plane][self.scene_x >> 7][self.scene_y >> 7] & 4) != 0) {
            occlusion_plane = plane;
        }

        return occlusion_plane;
    }

    public static int get_max_visible_plane_cinematic() {
        int ground_z = get_land_z(Camera.x, Camera.y, plane);
        if (ground_z - Camera.z < 800 && (render_flags[plane][Camera.x >> 7][Camera.y >> 7] & 4) != 0) {
            return plane;
        } else {
            return 3;
        }
    }

    public static int get_scene_load_state(byte byte0) {
        for (int i = 0; i < chunkLocPayload.length; i++) {
            if (chunkLocPayload[i] == null && mapUids[i] != -1) {
                return -1;
            }
            if (chunkLandscapePayload[i] == null && landscapeUids[i] != -1) {
                return -2;
            }
        }

        boolean valid = true;

        for (int i = 0; i < chunkLocPayload.length; i++) {
            byte landscape_payload[] = chunkLandscapePayload[i];

            if (landscape_payload != null) {
                int x = 10;
                int y = 10;

                if (!serverSentChunk) {
                    x = (chunkCoords[i] >> 8) * 64 - mapBaseX;
                    y = (chunkCoords[i] & 0xff) * 64 - mapBaseY;
                }

                valid &= Scene.locsFullyLoaded(x, y, landscape_payload);
            }
        }

        if (!valid) {
            return -3;
        }

        if (sceneLoading) {
            return -4;
        } else {
            sceneState = 2;
            Scene.planeAtBuild = plane;
            retrieve_scene(true);
            out.writeOpcode(121);
            return 0;
        }
    }

    public static void handle() {
        if (logoutCycle > 0) {
            logoutCycle--;
        }

        for (int i = 0; i < 5; i++) {
            if (!handle_net()) {
                break;
            }
        }

        if (!loggedIn) {
            return;
        }

        synchronized (mouseRecorder.synchronize) {
            if (recordMouse) {
                if (Mouse.clickButton != 0 || mouseRecorder.off >= 40) {
                    out.writeOpcode(45);
                    out.writeByte(0);

                    int start = out.position;
                    int j3 = 0;
                    for (int j4 = 0; j4 < mouseRecorder.off; j4++) {
                        if (start - out.position >= 240) {
                            break;
                        }

                        j3++;

                        int y = mouseRecorder.y[j4];

                        if (y < 0) {
                            y = 0;
                        } else if (y > 502) {
                            y = 502;
                        }

                        int x = mouseRecorder.x[j4];

                        if (x < 0) {
                            x = 0;
                        } else if (x > 764) {
                            x = 764;
                        }

                        int coord = y * 765 + x;

                        if (mouseRecorder.y[j4] == -1 && mouseRecorder.x[j4] == -1) {
                            x = -1;
                            y = -1;
                            coord = 0x7ffff;
                        }

                        if (x == mouseRecorder.lastX && y == mouseRecorder.lastY) {
                            if (mouseRecorder.cycle < 2047) {
                                mouseRecorder.cycle++;
                            }
                        } else {
                            int xd = x - mouseRecorder.lastX;
                            int yd = y - mouseRecorder.lastY;
                            mouseRecorder.lastX = x;
                            mouseRecorder.lastY = y;

                            if (mouseRecorder.cycle < 8 && xd >= -32 && xd <= 31 && yd >= -32 && yd <= 31) {
                                xd += 32;
                                yd += 32;
                                out.writeShort((mouseRecorder.cycle << 12) + (xd << 6) + yd);
                                mouseRecorder.cycle = 0;
                            } else if (mouseRecorder.cycle < 8) {
                                out.writeMedium(0x800000 + (mouseRecorder.cycle << 19) + coord);
                                mouseRecorder.cycle = 0;
                            } else {
                                out.writeInt(0xc0000000 + (mouseRecorder.cycle << 19) + coord);
                                mouseRecorder.cycle = 0;
                            }
                        }
                    }

                    out.putLength(out.position - start);

                    if (j3 >= mouseRecorder.off) {
                        mouseRecorder.off = 0;
                    } else {
                        mouseRecorder.off -= j3;
                        for (int i = 0; i < mouseRecorder.off; i++) {
                            mouseRecorder.x[i] = mouseRecorder.x[i + j3];
                            mouseRecorder.y[i] = mouseRecorder.y[i + j3];
                        }
                    }
                }
            } else {
                mouseRecorder.off = 0;
            }
        }

        if (Mouse.clickButton != 0) {
            long dt = (Mouse.clickTime - lastClickTime) / 50L;

            if (dt > 4095L) {
                dt = 4095L;
            }

            lastClickTime = Mouse.clickTime;

            int x = Mouse.clickX;
            int y = Mouse.clickY;
            x = x > 764 ? 764 : x < 0 ? 0 : x;
            y = y > 502 ? 502 : y < 0 ? 0 : y;

            int coord = y * 765 + x;
            int button = Mouse.clickButton == 2 ? 1 : 0;

            out.writeOpcode(241);
            out.writeInt((((int) dt) << 20) + (button << 19) + coord);
        }

        if (cam_info_cycle > 0) {
            cam_info_cycle--;
        }

        if (Key.LEFT.is_down() || Key.RIGHT.is_down() || Key.UP.is_down() || Key.DOWN.is_down()) {
            sendCamInfo = true;
        }

        if (sendCamInfo && cam_info_cycle <= 0) {
            cam_info_cycle = 100;
            sendCamInfo = false;
            out.writeOpcode(86);
            out.writeShort(chaseCamPitch);
            out.writeShortA(chaseCamYaw);
        }

        if (instance.focused && !isFocused) {
            isFocused = true;
            out.writeOpcode(3);
            out.writeByte(1);
        }

        if (!instance.focused && isFocused) {
            isFocused = false;
            out.writeOpcode(3);
            out.writeByte(0);
        }

        handle_scene();
        handle_loc_creation();
        handle_audio();
        netCycle++;

        if (netCycle > 750) {
            handle_connection_lost();
        }

        handle_players();
        handle_actors();
        handle_spoken();
        animCycle++;

        if (crossType != 0) {
            crossCycle += 20;
            if (crossCycle >= 400) {
                crossType = 0;
            }
        }

        if (clickArea != 0) {
            redrawCycle++;
            if (redrawCycle >= 15) {
                if (clickArea == 2) {
                    Sidebar.draw = true;
                }
                if (clickArea == 3) {
                    Chat.redraw = true;
                }
                clickArea = 0;
            }
        }

        if (dragArea != 0) {
            dragCycle++;

            if (Mouse.lastX > dragStartX + 5 || Mouse.lastX < dragStartX - 5 || Mouse.lastY > dragStartY + 5 || Mouse.lastY < dragStartY - 5) {
                dragging = true;
            }

            if (Mouse.dragButton == 0) {
                if (dragArea == 2) {
                    Sidebar.draw = true;
                }

                if (dragArea == 3) {
                    Chat.redraw = true;
                }

                dragArea = 0;

                int option_count = Menu.count;

                if (dragging && dragCycle >= 5) {
                    hoveredSlotWidget = -1;

                    Menu.handle();

                    if (hoveredSlotWidget == dragWidget && hoveredSlot != dragSlot) {
                        Widget w = Widget.instance[dragWidget];
                        int type = 0;

                        if (anInt913 == 1 && w.action_type == 206) {
                            type = 1;
                        }

                        if (w.item_index[hoveredSlot] <= 0) {
                            type = 0;
                        }

                        if (w.items_swappable) {
                            int old_slot = dragSlot;
                            int new_slot = hoveredSlot;
                            w.item_index[new_slot] = w.item_index[old_slot];
                            w.item_count[new_slot] = w.item_count[old_slot];
                            w.item_index[old_slot] = -1;
                            w.item_count[old_slot] = 0;
                        } else if (type == 1) {
                            int old_slot = dragSlot;
                            for (int new_slot = hoveredSlot; old_slot != new_slot; ) {
                                if (old_slot > new_slot) {
                                    w.swap_slots(old_slot, old_slot - 1);
                                    old_slot--;
                                } else if (old_slot < new_slot) {
                                    w.swap_slots(old_slot, old_slot + 1);
                                    old_slot++;
                                }
                            }

                        } else {
                            w.swap_slots(dragSlot, hoveredSlot);
                        }

                        out.writeOpcode(214);
                        out.writeLeShortA(dragWidget);
                        out.writeByteC(type);
                        out.writeLeShortA(dragSlot);
                        out.writeLeShort(hoveredSlot);
                    }
                } else if (mouseButtonSetting == 1 && option_count > 2) {
                    Menu.show();
                } else if (option_count > 0) {
                    handle_menu_option(option_count - 1);
                }

                redrawCycle = 10;
                Mouse.clickButton = 0;
            }
        }

        if (Landscape.clickLocalX != -1) {
            int local_x = Landscape.clickLocalX;
            int local_y = Landscape.clickLocalY;

            boolean valid_path = walk_to(0, 0, 0, self.path_x[0], self.path_y[0], local_x, local_y, 0, 0, 0, true);
            Landscape.clickLocalX = -1;

            if (valid_path) {
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 1;
                crossCycle = 0;
            }
        }

        if (Mouse.clickButton == 1 && Chat.get_message(Chat.MESSAGE_INPUT) != null) {
            Chat.set_message(Chat.MESSAGE_INPUT, null);
            Mouse.clickButton = 0;
        }

        handle_widget_mouse();
        handle_minimap_mouse();
        handle_tab_mouse();

        Chat.Settings.handle();

        if (Mouse.isDragging(1) || Mouse.clicked(1)) {
            clickCycle++;
        }

        if (sceneState == 2) {
            handle_camera();
        }

        if (sceneState == 2 && camCinemaMode) {
            handle_cinematic_camera();
        }

        for (int i1 = 0; i1 < 5; i1++) {
            anIntArray1030[i1]++;
        }

        handle_keyboard();

        instance.idleCycle++;

        if (instance.idleCycle > 4500) {
            logoutCycle = 250;
            instance.idleCycle -= 500;
            out.writeOpcode(202);
        }

        rndCycle1++;
        if (rndCycle1 > 500) {
            rndCycle1 = 0;
            int rnd = (int) (Math.random() * 8D);

            if ((rnd & 1) == 1) {
                cam_x_off += xCamOffMod;
            }

            if ((rnd & 2) == 2) {
                cam_y_off += yCamOffMod;
            }

            if ((rnd & 4) == 4) {
                cam_pitch_off += Camera.pitchModifier;
            }
        }

        if (cam_x_off < -50) {
            xCamOffMod = 2;
        }

        if (cam_x_off > 50) {
            xCamOffMod = -2;
        }

        if (cam_y_off < -55) {
            yCamOffMod = 2;
        }

        if (cam_y_off > 55) {
            yCamOffMod = -2;
        }

        if (cam_pitch_off < -40) {
            Camera.pitchModifier = 1;
        }

        if (cam_pitch_off > 40) {
            Camera.pitchModifier = -1;
        }

        rndCycle2++;

        if (rndCycle2 > 500) {
            rndCycle2 = 0;
            int rnd = (int) (Math.random() * 8D);

            if ((rnd & 1) == 1) {
                cam_yaw_off += Camera.yawModifier;
            }

            if ((rnd & 2) == 2) {
                mapZoomOffset += mapZoomModifier;
            }
        }

        if (cam_yaw_off < -60) {
            Camera.yawModifier = 2;
        }

        if (cam_yaw_off > 60) {
            Camera.yawModifier = -2;
        }

        if (mapZoomOffset < -20) {
            mapZoomModifier = 1;
        }

        if (mapZoomOffset > 10) {
            mapZoomModifier = -1;
        }

        netAliveCycle++;

        if (netAliveCycle > 50) {
            out.writeOpcode(0);
        }

        if (loopCycle % 50 == 0) {
            bytesRead = currentBytesRead;
            bytesSent = currentBytesSent;
            currentBytesRead = 0;
            currentBytesSent = 0;
        }

        try {
            if (connection != null && out.position > 0) {
                connection.putBytes(out.payload, 0, out.position);
                currentBytesSent += out.position;
                out.position = 0;
                netAliveCycle = 0;
                return;
            }
        } catch (IOException _ex) {
            handle_connection_lost();
            return;
        } catch (Exception exception) {
            net_disconnect();
        }
    }

    public static void handle_actor_menu_options(ActorConfig ac, int x, int y, int index) {
        if (Menu.count >= 400) {
            return;
        }

        if (ac.override_index != null) {
            ac = ac.get_overriding_config();
        }

        if (ac == null) {
            return;
        }

        if (!ac.interactable) {
            return;
        }

        String s = ac.name;

        if (ac.combat_level != 0) {
            s = s + RSColor.getLevelTag(ac.combat_level);
        }

        if (selectedItem) {
            Menu.add("Use " + selectedItemName + " with @yel@" + s, 582, x, y, index);
            return;
        }

        if (selectedWidget) {
            if (BitUtils.flagged(selectedMask, 0x2)) {
                Menu.add(selectedTooltip + " @yel@" + s, 413, x, y, index);
                return;
            }
        } else {
            if (ac.action != null) {
                for (int i = 4; i >= 0; i--) {
                    if (ac.action[i] != null && !ac.action[i].equalsIgnoreCase(JString.ATTACK)) {
                        Menu.add(ac.action[i] + " @yel@" + s, Action.ACTOR[i], x, y, index);
                    }
                }

            }
            if (ac.action != null) {
                for (int i = 4; i >= 0; i--) {
                    if (ac.action[i] != null && ac.action[i].equalsIgnoreCase(JString.ATTACK)) {
                        int off = 0;
                        if (ac.combat_level > self.combat_level) {
                            off = 2000;
                        }
                        Menu.add(ac.action[i] + " @yel@" + s, Action.ACTOR[i] + off, x, y, index);
                    }
                }
            }
            Menu.add("Examine @yel@" + s, 1025, x, y, index);
        }
    }

    public static void handle_actors() {
        for (int i = 0; i < actorCount; i++) {
            int actor_index = actorIndices[i];
            Actor a = actors[actor_index];
            if (a != null) {
                handle_entity(a);
            }
        }
    }

    public static void handle_audio() {
        for (int i = 0; i < soundCount; i++) {
            if (soundDelay[i] <= 0) {
                boolean played = false;

                try {
                    if (soundIndex[i] == lastSoundIndex && soundType[i] == lastSoundType) {
                        // TODO: Replay WAV
                    } else {
                        Buffer b = WaveSound.get(soundType[i], soundIndex[i]);

                        if (System.currentTimeMillis() + (long) (b.position / 22) > lastSoundTime + (long) (lastSoundPosition / 22)) {
                            lastSoundPosition = b.position;
                            lastSoundTime = System.currentTimeMillis();

                            if ( /*SoundSaved()*/lastSoundPosition > 0) {
                                lastSoundIndex = soundIndex[i];
                                lastSoundType = soundType[i];
                            } else {
                                played = true;
                            }
                        }
                    }
                } catch (Exception e) {
                }

                if (!played || soundDelay[i] == -5) {
                    soundCount--;

                    for (int j = i; j < soundCount; j++) {
                        soundIndex[j] = soundIndex[j + 1];
                        soundType[j] = soundType[j + 1];
                        soundDelay[j] = soundDelay[j + 1];
                    }

                    i--;
                } else {
                    soundDelay[i] = -5;
                }
            } else {
                soundDelay[i]--;
            }
        }

        if (music.tmpDelay > 0) {
            music.tmpDelay -= 20;

            if (music.tmpDelay < 0) {
                music.tmpDelay = 0;
            }

            if (music.tmpDelay == 0 && music.volume0 != 0 && music.currentSong != -1) {
                play_music_instantly(music.volume0, music.currentSong, 0, 0, false);
            }
        }
    }

    public static void handle_camera() {
        try {
            int scene_x = self.scene_x;
            int scene_y = self.scene_y;

            if (chaseCamX - scene_x < -500 || chaseCamX - scene_x > 500 || chaseCamY - scene_y < -500 || chaseCamY - scene_y > 500) {
                chaseCamX = scene_x;
                chaseCamY = scene_y;
            }

            if (chaseCamX != scene_x) {
                chaseCamX += (scene_x - chaseCamX) / 16;
            }

            if (chaseCamY != scene_y) {
                chaseCamY += (scene_y - chaseCamY) / 16;
            }

            if (Key.LEFT.is_down()) {
                chaseCamYawMod += (-24 - chaseCamYawMod) / 2;
            } else if (Key.RIGHT.is_down()) {
                chaseCamYawMod += (24 - chaseCamYawMod) / 2;
            } else {
                chaseCamYawMod /= 2;
            }

            if (Key.UP.is_down()) {
                chase_cam_pitch_mod += (12 - chase_cam_pitch_mod) / 2;
            } else if (Key.DOWN.is_down()) {
                chase_cam_pitch_mod += (-12 - chase_cam_pitch_mod) / 2;
            } else {
                chase_cam_pitch_mod /= 2;
            }

            chaseCamYaw = chaseCamYaw + chaseCamYawMod / 2 & 0x7ff;
            chaseCamPitch += chase_cam_pitch_mod / 2;

            if (chaseCamPitch < Camera.MIN_PITCH) {
                chaseCamPitch = Camera.MIN_PITCH;
            }

            if (chaseCamPitch > Camera.MAX_PITCH) {
                chaseCamPitch = Camera.MAX_PITCH;
            }

            int local_x = chaseCamX >> 7;
            int local_y = chaseCamY >> 7;
            int local_plane = get_land_z(chaseCamX, chaseCamY, plane);
            int highest_z = 0;

            if (local_x > 3 && local_y > 3 && local_x < 100 && local_y < 100) {
                for (int x = local_x - 4; x <= local_x + 4; x++) {
                    for (int y = local_y - 4; y <= local_y + 4; y++) {
                        int plane = Game.plane;

                        if (plane < 3 && (render_flags[1][x][y] & 2) == 2) {
                            plane++;
                        }

                        int last_z = local_plane - heightMap[plane][x][y];
                        if (last_z > highest_z) {
                            highest_z = last_z;
                        }
                    }

                }

            }

            int j2 = highest_z * 192;

            if (j2 > 0x17f00) {
                j2 = 0x17f00;
            }

            if (j2 < 32768) {
                j2 = 32768;
            }

            if (j2 > minPitch) {
                minPitch += (j2 - minPitch) / 24;
                return;
            }

            if (j2 < minPitch) {
                minPitch += (j2 - minPitch) / 80;
                return;
            }
        } catch (Exception _ex) {
            Signlink.error("glfc_ex " + self.scene_x + "," + self.scene_y + "," + chaseCamX + "," + chaseCamY + "," + loadedRegionX + "," + loadedRegionY + "," + mapBaseX + "," + mapBaseY);
            throw new RuntimeException(JString.EEK);
        }
    }

    public static void handle_cinematic_camera() {
        int cam_x = cam_cinema_dest_x * 128 + 64;
        int cam_y = cam_cinema_dest_y * 128 + 64;
        int cam_z = get_land_z(cam_x, cam_y, plane) - cam_cinema_dest_z;

        if (Camera.x < cam_x) {
            Camera.x += cam_cinema_base_speed + ((cam_x - Camera.x) * cam_cinema_speed) / 1000;
            if (Camera.x > cam_x) {
                Camera.x = cam_x;
            }
        }

        if (Camera.x > cam_x) {
            Camera.x -= cam_cinema_base_speed + ((Camera.x - cam_x) * cam_cinema_speed) / 1000;
            if (Camera.x < cam_x) {
                Camera.x = cam_x;
            }
        }

        if (Camera.z < cam_z) {
            Camera.z += cam_cinema_base_speed + ((cam_z - Camera.z) * cam_cinema_speed) / 1000;
            if (Camera.z > cam_z) {
                Camera.z = cam_z;
            }
        }

        if (Camera.z > cam_z) {
            Camera.z -= cam_cinema_base_speed + ((Camera.z - cam_z) * cam_cinema_speed) / 1000;
            if (Camera.z < cam_z) {
                Camera.z = cam_z;
            }
        }

        if (Camera.y < cam_y) {
            Camera.y += cam_cinema_base_speed + ((cam_y - Camera.y) * cam_cinema_speed) / 1000;
            if (Camera.y > cam_y) {
                Camera.y = cam_y;
            }
        }

        if (Camera.y > cam_y) {
            Camera.y -= cam_cinema_base_speed + ((Camera.y - cam_y) * cam_cinema_speed) / 1000;
            if (Camera.y < cam_y) {
                Camera.y = cam_y;
            }
        }

        cam_x = cam_cinema_aim_x * 128 + 64;
        cam_y = cam_cinema_aim_y * 128 + 64;
        cam_z = get_land_z(cam_x, cam_y, plane) - cam_cinema_aim_z;

        int x_diff = cam_x - Camera.x;
        int z_diff = cam_z - Camera.z;
        int y_diff = cam_y - Camera.y;
        int length = (int) Math.sqrt(x_diff * x_diff + y_diff * y_diff);
        int cam_pitch = (int) (Math.atan2(z_diff, length) * 325.94900000000001D) & 0x7ff;
        int cam_yaw = (int) (Math.atan2(x_diff, y_diff) * -325.94900000000001D) & 0x7ff;

        if (cam_pitch < 128) {
            cam_pitch = 128;
        }

        if (cam_pitch > 383) {
            cam_pitch = 383;
        }

        if (Camera.pitch < cam_pitch) {
            Camera.pitch += cam_cinema_rot_base + ((cam_pitch - Camera.pitch) * cam_cinema_rot_modifier) / 1000;
            if (Camera.pitch > cam_pitch) {
                Camera.pitch = cam_pitch;
            }
        }

        if (Camera.pitch > cam_pitch) {
            Camera.pitch -= cam_cinema_rot_base + ((Camera.pitch - cam_pitch) * cam_cinema_rot_modifier) / 1000;
            if (Camera.pitch < cam_pitch) {
                Camera.pitch = cam_pitch;
            }
        }

        int yaw_diff = cam_yaw - Camera.yaw;

        if (yaw_diff > 1024) {
            yaw_diff -= 2048;
        }

        if (yaw_diff < -1024) {
            yaw_diff += 2048;
        }

        if (yaw_diff > 0) {
            Camera.yaw += cam_cinema_rot_base + (yaw_diff * cam_cinema_rot_modifier) / 1000;
            Camera.yaw &= 0x7ff;
        }
        if (yaw_diff < 0) {
            Camera.yaw -= cam_cinema_rot_base + (-yaw_diff * cam_cinema_rot_modifier) / 1000;
            Camera.yaw &= 0x7ff;
        }

        int yaw_dest = cam_yaw - Camera.yaw;

        if (yaw_dest > 1024) {
            yaw_dest -= 2048;
        }

        if (yaw_dest < -1024) {
            yaw_dest += 2048;
        }

        if (yaw_dest < 0 && yaw_diff > 0 || yaw_dest > 0 && yaw_diff < 0) {
            Camera.yaw = cam_yaw;
        }
    }

    public static void handle_connection_lost() {
        if (logoutCycle > 0) {
            net_disconnect();
            return;
        }

        producerScene.prepare();
        BitmapFont.NORMAL.draw(JString.CONNECTION_LOST, 256, 143, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
        BitmapFont.NORMAL.draw(JString.ATTEMPTING_TO_REESTABLISH, 256, 158, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
        producerScene.draw(4, 4);
        minimapState = 0;
        mapMarkerX = 0;
        loggedIn = false;
        reconnectionAttempts = 0;
        net_login(username, password, true);

        if (!loggedIn) {
            net_disconnect();
        }

        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public static void handle_entity(Entity e) {
        if (e.scene_x < 128 || e.scene_y < 128 || e.scene_x >= 13184 || e.scene_y >= 13184) {
            e.seq_index = -1;
            e.spotanimIndex = -1;
            e.move_cycle_end = 0;
            e.move_cycle_start = 0;
            e.scene_x = e.path_x[0] * 128 + e.size * 64;
            e.scene_y = e.path_y[0] * 128 + e.size * 64;
            e.reset_positions();
        }

        if (e == self && (e.scene_x < 1536 || e.scene_y < 1536 || e.scene_x >= 11776 || e.scene_y >= 11776)) {
            e.seq_index = -1;
            e.spotanimIndex = -1;
            e.move_cycle_end = 0;
            e.move_cycle_start = 0;
            e.scene_x = e.path_x[0] * 128 + e.size * 64;
            e.scene_y = e.path_y[0] * 128 + e.size * 64;
            e.reset_positions();
        }

        if (e.move_cycle_end > loopCycle) {
            handle_entity_late_movement(e);
        } else if (e.move_cycle_start >= loopCycle) {
            handle_entity_movement_variables(e);
        } else {
            handle_entity_movement(e);
        }

        handle_entity_rotation(e);
        handle_entity_sequence(e);
    }

    public static void handle_entity_late_movement(Entity e) {
        int dt = e.move_cycle_end - loopCycle;
        int dest_x = e.move_start_x * 128 + e.size * 64;
        int dest_y = e.move_start_y * 128 + e.size * 64;

        e.scene_x += (dest_x - e.scene_x) / dt;
        e.scene_y += (dest_y - e.scene_y) / dt;
        e.resync_walk_cycle = 0;

        if (e.move_direction == 0) {
            e.dest_rotation = 1024;
        } else if (e.move_direction == 1) {
            e.dest_rotation = 1536;
        } else if (e.move_direction == 2) {
            e.dest_rotation = 0;
        } else if (e.move_direction == 3) {
            e.dest_rotation = 512;
        }
    }

    public static void handle_entity_movement(Entity e) {
        e.move_seq_index = e.stand_animation;

        if (e.path_position == 0) {
            e.resync_walk_cycle = 0;
            return;
        }

        if (e.seq_index != -1 && e.seq_delay_cycle == 0) {
            Sequence a = Sequence.instance[e.seq_index];
            if (e.still_path_position > 0 && a.speed_flag == 0) {
                e.resync_walk_cycle++;
                return;
            }
            if (e.still_path_position <= 0 && a.walk_flag == 0) {
                e.resync_walk_cycle++;
                return;
            }
        }

        int scene_x = e.scene_x;
        int scene_y = e.scene_y;
        int dest_x = e.path_x[e.path_position - 1] * 128 + e.size * 64;
        int dest_y = e.path_y[e.path_position - 1] * 128 + e.size * 64;

        if (dest_x - scene_x > 256 || dest_x - scene_x < -256 || dest_y - scene_y > 256 || dest_y - scene_y < -256) {
            e.scene_x = dest_x;
            e.scene_y = dest_y;
            return;
        }

        if (scene_x < dest_x) {
            if (scene_y < dest_y) {
                e.dest_rotation = 1280;
            } else if (scene_y > dest_y) {
                e.dest_rotation = 1792;
            } else {
                e.dest_rotation = 1536;
            }
        } else if (scene_x > dest_x) {
            if (scene_y < dest_y) {
                e.dest_rotation = 768;
            } else if (scene_y > dest_y) {
                e.dest_rotation = 256;
            } else {
                e.dest_rotation = 512;
            }
        } else if (scene_y < dest_y) {
            e.dest_rotation = 1024;
        } else {
            e.dest_rotation = 0;
        }

        int angle_diff = e.dest_rotation - e.rotation & 0x7ff;

        if (angle_diff > 1024) {
            angle_diff -= 2048;
        }

        int index = e.turn_180_animation;

        if (angle_diff >= -256 && angle_diff <= 256) {
            index = e.walk_animation;
        } else if (angle_diff >= 256 && angle_diff < 768) {
            index = e.turn_l_animation;
        } else if (angle_diff >= -768 && angle_diff <= -256) {
            index = e.turn_r_animation;
        }

        if (index == -1) {
            index = e.walk_animation;
        }

        e.move_seq_index = index;

        int speed = 4;

        if (e.rotation != e.dest_rotation && e.face_entity == -1 && e.turn_speed != 0) {
            speed = 2;
        }

        if (e.path_position > 2) {
            speed = 6;
        }

        if (e.path_position > 3) {
            speed = 8;
        }

        if (e.resync_walk_cycle > 0 && e.path_position > 1) {
            speed = 8;
            e.resync_walk_cycle--;
        }

        if (e.path_run[e.path_position - 1]) {
            speed <<= 1;
        }

        if (speed >= 8 && e.move_seq_index == e.walk_animation && e.run_animation != -1) {
            e.move_seq_index = e.run_animation;
        }

        if (scene_x < dest_x) {
            e.scene_x += speed;
            if (e.scene_x > dest_x) {
                e.scene_x = dest_x;
            }
        } else if (scene_x > dest_x) {
            e.scene_x -= speed;
            if (e.scene_x < dest_x) {
                e.scene_x = dest_x;
            }
        }

        if (scene_y < dest_y) {
            e.scene_y += speed;
            if (e.scene_y > dest_y) {
                e.scene_y = dest_y;
            }
        } else if (scene_y > dest_y) {
            e.scene_y -= speed;
            if (e.scene_y < dest_y) {
                e.scene_y = dest_y;
            }
        }

        if (e.scene_x == dest_x && e.scene_y == dest_y) {
            e.path_position--;
            if (e.still_path_position > 0) {
                e.still_path_position--;
            }
        }
    }

    public static void handle_entity_movement_variables(Entity e) {
        if (e.move_cycle_start == loopCycle || e.seq_index == -1 || e.seq_delay_cycle != 0 || e.seq_cycle + 1 > Sequence.instance[e.seq_index].get_frame_length(e.seq_frame)) {
            int walk_dt = e.move_cycle_start - e.move_cycle_end;
            int dt = loopCycle - e.move_cycle_end;
            int start_x = e.move_start_x * 128 + e.size * 64;
            int start_y = e.move_start_y * 128 + e.size * 64;
            int end_x = e.move_end_x * 128 + e.size * 64;
            int end_y = e.move_end_y * 128 + e.size * 64;
            e.scene_x = (start_x * (walk_dt - dt) + end_x * dt) / walk_dt;
            e.scene_y = (start_y * (walk_dt - dt) + end_y * dt) / walk_dt;
        }

        e.resync_walk_cycle = 0;

        if (e.move_direction == 0) {
            e.dest_rotation = 1024;
        }

        if (e.move_direction == 1) {
            e.dest_rotation = 1536;
        }

        if (e.move_direction == 2) {
            e.dest_rotation = 0;
        }

        if (e.move_direction == 3) {
            e.dest_rotation = 512;
        }

        e.rotation = e.dest_rotation;
    }

    public static void handle_entity_rotation(Entity e) {
        if (e.turn_speed == 0) {
            return;
        }

        if (e.face_entity != -1 && e.face_entity < 32768) {
            Actor a = actors[e.face_entity];

            if (a != null) {
                int dx = e.scene_x - a.scene_x;
                int dy = e.scene_y - a.scene_y;

                if (dx != 0 || dy != 0) {
                    e.dest_rotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if (e.face_entity >= 32768) {
            int player_index = e.face_entity - 32768;

            if (player_index == localPlayerIndex) {
                player_index = MAX_PLAYER_INDEX;
            }

            Player p = players[player_index];

            if (p != null) {
                int dx = e.scene_x - p.scene_x;
                int dy = e.scene_y - p.scene_y;

                if (dx != 0 || dy != 0) {
                    e.dest_rotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if ((e.face_x != 0 || e.face_y != 0) && (e.path_position == 0 || e.resync_walk_cycle > 0)) {
            int dx = e.scene_x - (e.face_x - mapBaseX - mapBaseX) * 64;
            int dy = e.scene_y - (e.face_y - mapBaseY - mapBaseY) * 64;

            if (dx != 0 || dy != 0) {
                e.dest_rotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
            }

            e.face_x = 0;
            e.face_y = 0;
        }

        int da = e.dest_rotation - e.rotation & 0x7ff;

        if (da != 0) {
            if (da < e.turn_speed || da > 2048 - e.turn_speed) {
                e.rotation = e.dest_rotation;
            } else if (da > 1024) {
                e.rotation -= e.turn_speed;
            } else {
                e.rotation += e.turn_speed;
            }

            e.rotation &= 0x7ff;

            if (e.move_seq_index == e.stand_animation && e.rotation != e.dest_rotation) {
                if (e.stand_turn_animation != -1) {
                    e.move_seq_index = e.stand_turn_animation;
                    return;
                }
                e.move_seq_index = e.walk_animation;
            }
        }
    }

    public static void handle_entity_sequence(Entity e) {
        e.can_rotate = false;

        if (e.move_seq_index != -1) {
            Sequence s = Sequence.instance[e.move_seq_index];
            e.move_seq_cycle++;

            // If it's time to go to the next frame.
            if (e.move_seq_frame < s.frame_count && e.move_seq_cycle > s.get_frame_length(e.move_seq_frame)) {
                e.move_seq_cycle = 0;
                e.move_seq_frame++;
            }

            // If we've past the frame count in this sequence, reset the
            // animation.
            if (e.move_seq_frame >= s.frame_count) {
                e.move_seq_cycle = 0;
                e.move_seq_frame = 0;
            }
        }

        if (e.spotanimIndex != -1 && loopCycle >= e.spotanim_cycle_end) {
            if (e.spotanimFrame < 0) {
                e.spotanimFrame = 0;
            }

            Sequence s = null;

            if (e.spotanimIndex >= 0 && e.spotanimIndex < SpotAnimConfig.count) {
                s = SpotAnimConfig.instance[e.spotanimIndex].seq;
            }

            if (s != null) {
                for (e.spotanim_cycle++; e.spotanimFrame < s.frame_count && e.spotanim_cycle > s.get_frame_length(e.spotanimFrame); e.spotanimFrame++) {
                    e.spotanim_cycle -= s.get_frame_length(e.spotanimFrame);
                }

                if (e.spotanimFrame >= s.frame_count && (e.spotanimFrame < 0 || e.spotanimFrame >= s.frame_count)) {
                    e.spotanimIndex = -1;
                }
            } else {
                System.out.println("Error spotanim " + e.spotanimIndex + " doesn't exist!");
                e.spotanimIndex = -1;
            }
        }

        if (e.seq_index != -1 && e.seq_delay_cycle <= 1) {
            if (e.seq_index < 0 || e.seq_index >= Sequence.instance.length) {
                System.out.println("Error: sequence " + e.seq_index + " doesn't exist!");
                e.seq_index = -1;
            } else {
                Sequence s = Sequence.instance[e.seq_index];

                if (s.speed_flag == 1 && e.still_path_position > 0 && e.move_cycle_end <= loopCycle && e.move_cycle_start < loopCycle) {
                    e.seq_delay_cycle = 1;
                    return;
                }
            }
        }

        if (e.seq_index != -1 && e.seq_delay_cycle == 0) {
            if (e.seq_index < 0 || e.seq_index >= Sequence.instance.length) {
                System.out.println("Error: sequence " + e.seq_index + " doesn't exist!");
                e.seq_index = -1;
            } else {
                Sequence s = Sequence.instance[e.seq_index];

                for (e.seq_cycle++; e.seq_frame < s.frame_count && e.seq_cycle > s.get_frame_length(e.seq_frame); e.seq_frame++) {
                    e.seq_cycle -= s.get_frame_length(e.seq_frame);
                }

                if (e.seq_frame >= s.frame_count) {
                    e.seq_frame -= s.padding;
                    e.seq_reset_cycle++;

                    if (e.seq_reset_cycle >= s.reset_cycle) {
                        e.seq_index = -1;
                    }

                    if (e.seq_frame < 0 || e.seq_frame >= s.frame_count) {
                        e.seq_index = -1;
                    }
                }
                e.can_rotate = s.can_rotate;
            }
        }

        if (e.seq_delay_cycle > 0) {
            e.seq_delay_cycle--;
        }
    }

    public static void handle_keyboard() {
        do {
            int key = Keyboard.next();

            if (key == -1) {
                break;
            }

            if (widgetOverlay != -1 && widgetOverlay == reportAbuseWindex) {
                if (key == KeyEvent.VK_BACK_SPACE && reportAbuseInput.length() > 0) {
                    reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
                }

                if ((key >= 97 && key <= 122 || key >= 65 && key <= 90 || key >= 48 && key <= 57 || key == 32) && reportAbuseInput.length() < 12) {
                    reportAbuseInput += (char) key;
                }
            } else {
                Chat.handle_keyboard(key);
            }
        } while (true);
    }

    public static void handle_loc(SpawntLoc sl) {
        int loc_uid = 0;
        int loc_index = -1;
        int loc_type = 0;
        int loc_rotation = 0;

        if (sl.classType == 0) {
            loc_uid = landscape.getWallUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 1) {
            loc_uid = landscape.getWallDecorationUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 2) {
            loc_uid = landscape.getLocUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 3) {
            loc_uid = landscape.getGroundDecorationUid(sl.plane, sl.x, sl.y);
        }

        if (loc_uid != 0) {
            int uid = landscape.getArrangement(sl.plane, sl.x, sl.y, loc_uid);
            loc_index = loc_uid >> 14 & 0x7fff;
            loc_type = uid & 0x1f;
            loc_rotation = uid >> 6;
        }

        sl.index = loc_index;
        sl.type = loc_type;
        sl.rotation = loc_rotation;
    }

    public static void handle_loc_creation() {
        if (sceneState == 2) {
            for (SpawntLoc sl = (SpawntLoc) spawnedLocs.top(); sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
                if (sl.cycle > 0) {
                    sl.cycle--;
                }

                if (sl.cycle == 0) {
                    if (sl.index < 0 || Scene.isLocLoaded(sl.index, sl.type)) {
                        addLoc(sl.y, sl.x, sl.plane, sl.index, sl.rotation, sl.type, sl.classType);
                        sl.detach();
                    }
                } else {
                    if (sl.spawnCycle > 0) {
                        sl.spawnCycle--;
                    }

                    if (sl.spawnCycle == 0 && sl.x >= 1 && sl.y >= 1 && sl.x <= 102 && sl.y <= 102 && (sl.locIndex < 0 || Scene.isLocLoaded(sl.locIndex, sl.locType))) {
                        addLoc(sl.y, sl.x, sl.plane, sl.locIndex, sl.locRotation, sl.locType, sl.classType);
                        sl.spawnCycle = -1;

                        if (sl.locIndex == sl.index && sl.index == -1) {
                            sl.detach();
                        } else if (sl.locIndex == sl.index && sl.locRotation == sl.rotation && sl.locType == sl.type) {
                            sl.detach();
                        }
                    }
                }
            }

        }
    }

    public static void handle_locs() {
        SpawntLoc sl = (SpawntLoc) spawnedLocs.top();
        for (; sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
            if (sl.cycle == -1) {
                sl.spawnCycle = 0;
                handle_loc(sl);
            } else {
                sl.detach();
            }
        }
    }

    public static void handle_menu_option(int option) {
        if (option < 0) {
            return;
        }

        Chat.clear();

        int param1 = Menu.getParam(option, 0);
        int param2 = Menu.getParam(option, 1);
        int param3 = Menu.getParam(option, 2);
        int action = Menu.getAction(option);

        if (action >= 2000) {
            action -= 2000;
        }

        if (debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action ").append(action).append(": ");
            sb.append(Arrays.toString(new int[]{param1, param2, param3}));
            System.out.println(sb.toString());
        }

        if (action == 582) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(57);
                out.writeShortA(selectedItemIndex);
                out.writeShortA(param3);
                out.writeLeShort(selectedItemSlot);
                out.writeShortA(selectedItemWidget);
            }
        }

        if (action == 234) {
            boolean flag1 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag1) {
                flag1 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(236);
            out.writeLeShort(param2 + mapBaseY);
            out.writeShort(param3);
            out.writeLeShort(param1 + mapBaseX);
        }

        if (action == 62 && interact_with_loc(param2, param1, param3)) {
            out.writeOpcode(192);
            out.writeShort(selectedItemWidget);
            out.writeLeShort(param3 >> 14 & 0x7fff);
            out.writeLeShortA(param2 + mapBaseY);
            out.writeLeShort(selectedItemSlot);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShort(selectedItemIndex);
        }

        if (action == 511) {
            boolean flag2 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag2) {
                flag2 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(25);
            out.writeLeShort(selectedItemWidget);
            out.writeShortA(selectedItemIndex);
            out.writeShort(param3);
            out.writeShortA(param2 + mapBaseY);
            out.writeLeShortA(selectedItemSlot);
            out.writeShort(param1 + mapBaseX);
        }

        if (action == 74) {
            out.writeOpcode(122);
            out.writeLeShortA(param2);
            out.writeShortA(param1);
            out.writeLeShort(param3);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 315) {
            if (param2 == 5985) {
                reportAbuseMute = !reportAbuseMute;
            } else {
                Widget w = Widget.get(param2);
                boolean valid = true;

                if (w.action_type > 0) {
                    valid = handle_widget(w);
                }

                if (valid) {
                    out.writeOpcode(185);
                    out.writeShort(param2);
                }
            }
        }

        if (action == 561) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(128);
                out.writeShort(param3);
            }
        }

        if (action == 20) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(155);
                out.writeLeShort(param3);
            }
        }

        if (action == 779) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(153);
                out.writeLeShort(param3);
            }
        }

        if (action == 516) {
            if (!Menu.visible) {
                landscape.clicked(Mouse.clickY - 4, Mouse.clickX - 4);
            } else {
                landscape.clicked(param2 - 4, param1 - 4);
            }
        }

        if (action == 1062) {
            interact_with_loc(param2, param1, param3);
            out.writeOpcode(228);
            out.writeShortA(param3 >> 14 & 0x7fff);
            out.writeShortA(param2 + mapBaseY);
            out.writeShort(param1 + mapBaseX);
        }

        if (action == 679 && !dialogueOptionActive) {
            out.writeOpcode(40);
            out.writeShort(param2);
            dialogueOptionActive = true;
        }

        if (action == 431) {
            out.writeOpcode(129);
            out.writeShortA(param1);
            out.writeShort(param2);
            out.writeShortA(param3);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            } else if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 337 || action == 42 || action == 792 || action == 322) {
            String s = Menu.getCaption(option);
            int i = s.indexOf(JString.WHI);
            if (i != -1) {
                long l = JString.toLong(s.substring(i + 5).trim());
                switch (action) {
                    case 337: {
                        friend_add(l);
                        break;
                    }
                    case 792: {
                        friend_remove(l);
                        break;
                    }
                    case 42: {
                        ignore_add(l);
                        break;
                    }
                    case 322: {
                        ignore_remove(l);
                        break;
                    }
                }
            }
        }

        if (action == 53) {
            out.writeOpcode(135);
            out.writeLeShort(param1);
            out.writeShortA(param2);
            out.writeLeShort(param3);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 539) {
            out.writeOpcode(16);
            out.writeShortA(param3);
            out.writeLeShortA(param1);
            out.writeLeShortA(param2);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 484 || action == 6) {
            String s = Menu.getCaption(option);
            int j = s.indexOf(JString.WHI);

            if (j != -1) {
                s = s.substring(j + 5).trim();
                String name = JString.getFormattedString(JString.toLong(s));
                boolean found = false;

                for (int i = 0; i < player_count; i++) {
                    Player p = players[playerIndices[i]];

                    if (p == null || p.name == null || !p.name.equalsIgnoreCase(name)) {
                        continue;
                    }

                    walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);

                    if (action == 484) {
                        out.writeOpcode(139);
                        out.writeLeShort(playerIndices[i]);
                    }

                    if (action == 6) {
                        out.writeOpcode(128);
                        out.writeShort(playerIndices[i]);
                    }

                    found = true;
                    break;
                }

                if (!found) {
                    Chat.put("Unable to find " + name, 0);
                }
            }
        }

        if (action == 870) {
            out.writeOpcode(53);
            out.writeShort(param1);
            out.writeShortA(selectedItemSlot);
            out.writeLeShortA(param3);
            out.writeShort(selectedItemWidget);
            out.writeLeShort(selectedItemIndex);
            out.writeShort(param2);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 847) {
            out.writeOpcode(87);
            out.writeShortA(param3);
            out.writeShort(param2);
            out.writeShortA(param1);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 626) {
            Widget w = Widget.instance[param2];
            selectedWidget = true;
            selectedWidgetIndex = param2;
            selectedMask = w.option_action;
            selectedItem = false;
            Sidebar.draw = true;

            String prefix = w.option_prefix;

            if (prefix.indexOf(' ') != -1) {
                prefix = prefix.substring(0, prefix.indexOf(' '));
            }

            String suffix = w.option_prefix;

            if (suffix.indexOf(' ') != -1) {
                suffix = suffix.substring(suffix.indexOf(' ') + 1);
            }

            selectedTooltip = prefix + ' ' + w.option_suffix + ' ' + suffix;

            if (selectedMask == 16) {
                Sidebar.draw = true;
                Sidebar.openTab(3);
                Sidebar.drawTabs = true;
            }
            return;
        }

        if (action == 78) {
            out.writeOpcode(117);
            out.writeLeShortA(param2);
            out.writeLeShortA(param3);
            out.writeLeShort(param1);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 27) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(73);
                out.writeLeShort(param3);
            }
        }

        if (action == 213) {
            boolean flag3 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag3) {
                flag3 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(79);
            out.writeLeShort(param2 + mapBaseY);
            out.writeShort(param3);
            out.writeShortA(param1 + mapBaseX);
        }

        if (action == 632) {
            out.writeOpcode(145);
            out.writeShortA(param2);
            out.writeShortA(param1);
            out.writeShortA(param3);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            } else if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 493) {
            out.writeOpcode(75);
            out.writeLeShortA(param2);
            out.writeLeShort(param1);
            out.writeShortA(param3);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            } else if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 652) {
            boolean flag4 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag4) {
                flag4 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(156);
            out.writeShortA(param1 + mapBaseX);
            out.writeLeShort(param2 + mapBaseY);
            out.writeLeShortA(param3);
        }

        if (action == 94) {
            boolean flag5 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag5) {
                flag5 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(181);
            out.writeLeShort(param2 + mapBaseY);
            out.writeShort(param3);
            out.writeLeShort(param1 + mapBaseX);
            out.writeShortA(selectedWidgetIndex);
        }

        if (action == 646) {
            out.writeOpcode(185);
            out.writeShort(param2);
            Widget w = Widget.instance[param2];

            if (w.script != null && w.script[0].intcode[0] == 5) {
                int setting = w.script[0].intcode[1];

                if (settings[setting] != w.script[0].compare_value) {
                    settings[setting] = w.script[0].compare_value;
                    handle_varp(setting);
                    Sidebar.draw = true;
                }
            }
        }

        if (action == 225) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(17);
                out.writeLeShortA(param3);
            }
        }

        if (action == 965) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(21);
                out.writeShort(param3);
            }
        }

        if (action == 413) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(131);
                out.writeLeShortA(param3);
                out.writeShortA(selectedWidgetIndex);
            }
        }

        if (action == 200) {
            close_widgets();
        }

        if (action == 1025) {
            Actor a = actors[param3];

            if (a != null) {
                ActorConfig c = a.config;

                if (c.override_index != null) {
                    c = c.get_overriding_config();
                }

                if (c != null) {
                    Chat.put(c.description != null ? c.description : "It's a " + c.name + ".");
                }
            }
        }

        if (action == 900) {
            interact_with_loc(param2, param1, param3);
            out.writeOpcode(252);
            out.writeLeShortA(param3 >> 14 & 0x7fff);
            out.writeLeShort(param2 + mapBaseY);
            out.writeShortA(param1 + mapBaseX);
        }

        if (action == 412) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(72);
                out.writeShortA(param3);
            }
        }

        if (action == 365) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(249);
                out.writeShortA(param3);
                out.writeLeShort(selectedWidgetIndex);
            }
        }

        if (action == 729) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(39);
                out.writeLeShort(param3);
            }
        }

        if (action == 577) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(139);
                out.writeLeShort(param3);
            }
        }

        if (action == 956 && interact_with_loc(param2, param1, param3)) {
            out.writeOpcode(35);
            out.writeLeShort(param1 + mapBaseX);
            out.writeShortA(selectedWidgetIndex);
            out.writeShortA(param2 + mapBaseY);
            out.writeLeShort(param3 >> 14 & 0x7fff);
        }

        if (action == 567) {
            boolean flag6 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag6) {
                flag6 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(23);
            out.writeLeShort(param2 + mapBaseY);
            out.writeLeShort(param3);
            out.writeLeShort(param1 + mapBaseX);
        }

        if (action == 867) {
            out.writeOpcode(43);
            out.writeLeShort(param2);
            out.writeShortA(param3);
            out.writeShortA(param1);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 543) {
            out.writeOpcode(237);
            out.writeShort(param1);
            out.writeShortA(param3);
            out.writeShort(param2);
            out.writeShortA(selectedWidgetIndex);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 606) {
            String s = Menu.getCaption(option);
            int j = s.indexOf(JString.WHI);

            if (j != -1) {
                if (widgetOverlay == -1) {
                    close_widgets();
                    reportAbuseInput = s.substring(j + 5).trim();
                    reportAbuseMute = false;

                    for (int i = 0; i < Widget.instance.length; i++) {
                        if (Widget.instance[i] == null || Widget.instance[i].action_type != 600) {
                            continue;
                        }
                        reportAbuseWindex = widgetOverlay = Widget.instance[i].parent;
                        break;
                    }

                } else {
                    Chat.put("Please close the interface you have open before using 'report abuse'", 0);
                }
            }
        }

        if (action == 491) {
            Player p = players[param3];
            if (p != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], p.path_x[0], p.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(14);
                out.writeShortA(selectedItemWidget);
                out.writeShort(param3);
                out.writeShort(selectedItemIndex);
                out.writeLeShort(selectedItemSlot);
            }
        }

        if (action == 639) {
            String s = Menu.getCaption(option);
            int j = s.indexOf(JString.WHI);
            if (j != -1) {
                long l = JString.toLong(s.substring(j + 5).trim());
                int friend_index = -1;
                for (int i = 0; i < friendCount; i++) {
                    if (friendLong[i] != l) {
                        continue;
                    }
                    friend_index = i;
                    break;
                }

                if (friend_index != -1 && friendNode[friend_index] > 0) {
                    messageRecipientNameLong = friendLong[friend_index];
                    Chat.set(State.SEND_MESSAGE, "Enter a message to send to " + friendName[friend_index]);
                }
            }
        }

        if (action == 454) {
            out.writeOpcode(41);
            out.writeShort(param3);
            out.writeShortA(param1);
            out.writeShortA(param2);
            redrawCycle = 0;
            clickedItemWidget = param2;
            clickedItemSlot = param1;
            clickArea = 2;

            if (Widget.instance[param2].parent == widgetOverlay) {
                clickArea = 1;
            }

            if (Widget.instance[param2].parent == Chat.get_overlay()) {
                clickArea = 3;
            }
        }

        if (action == 478) {
            Actor a = actors[param3];
            if (a != null) {
                walk_to(2, 1, 1, self.path_x[0], self.path_y[0], a.path_x[0], a.path_y[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(18);
                out.writeLeShort(param3);
            }
        }

        if (action == 113) {
            interact_with_loc(param2, param1, param3);
            out.writeOpcode(70);
            out.writeLeShort(param1 + mapBaseX);
            out.writeShort(param2 + mapBaseY);
            out.writeLeShortA(param3 >> 14 & 0x7fff);
        }

        if (action == 872) {
            interact_with_loc(param2, param1, param3);
            out.writeOpcode(234);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShortA(param3 >> 14 & 0x7fff);
            out.writeLeShortA(param2 + mapBaseY);
        }

        if (action == 502) {
            interact_with_loc(param2, param1, param3);
            out.writeOpcode(132);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShort(param3 >> 14 & 0x7fff);
            out.writeShortA(param2 + mapBaseY);
        }

        if (action == 1125) {
            ObjConfig oc = ObjConfig.get(param3);
            Widget w = Widget.instance[param2];

            if (w != null && oc != null) {
                if (w.item_count[param1] >= 100_000) {
                    Chat.put(w.item_count[param1] + " x " + oc.name);
                } else {
                    Chat.put(oc.description != null ? oc.description : "It's a " + oc.name + ".");
                }
            }
        }

        if (action == 169) {
            out.writeOpcode(185);
            out.writeShort(param2);
            Widget w = Widget.instance[param2];

            if (w.script != null && w.script[0].intcode[0] == 5) {
                int pointer = w.script[0].intcode[1];
                settings[pointer] = 1 - settings[pointer];
                handle_varp(pointer);
                Sidebar.draw = true;
            }
        }

        if (action == 447) {
            selectedItem = true;
            selectedItemSlot = param1;
            selectedItemWidget = param2;
            selectedItemIndex = param3;
            selectedItemName = ObjConfig.get(param3).name;
            selectedWidget = false;
            Sidebar.draw = true;
            return;
        }

        if (action == 1226) {
            LocConfig c = LocConfig.get(param3 >> 14 & 0x7fff);

            if (c != null) {
                Chat.put(c.description != null ? c.description : "It's a " + c.name + ".");
            }
        }

        if (action == 244) {
            boolean flag7 = walk_to(2, 0, 0, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            if (!flag7) {
                flag7 = walk_to(2, 1, 1, self.path_x[0], self.path_y[0], param1, param2, 0, 0, 0, false);
            }
            crossX = Mouse.clickX;
            crossY = Mouse.clickY;
            crossType = 2;
            crossCycle = 0;
            out.writeOpcode(253);
            out.writeLeShort(param1 + mapBaseX);
            out.writeLeShortA(param2 + mapBaseY);
            out.writeShortA(param3);
        }

        if (action == 1448) {
            ObjConfig c = ObjConfig.get(param3);

            if (c != null) {
                Chat.put(c.description != null ? c.description : "It's a " + c.name + ".");
            }
        }

        selectedItem = false;
        selectedWidget = false;
        Sidebar.draw = true;
    }

    public static void handle_message_status() {
        messageStatus = 0;

        int x = (self.scene_x >> 7) + mapBaseX;
        int y = (self.scene_y >> 7) + mapBaseY;

        if (x >= 3053 && x <= 3156 && y >= 3056 && y <= 3136) {
            messageStatus = 1;
        }

        if (x >= 3072 && x <= 3118 && y >= 9492 && y <= 9535) {
            messageStatus = 1;
        }

        if (messageStatus == 1 && x >= 3139 && x <= 3199 && y >= 3008 && y <= 3062) {
            messageStatus = 0;
        }
    }

    public static void handle_minimap_mouse() {
        if (minimapState != 0) {
            return;
        }
        if (Mouse.clickButton == 1) {
            int x = Mouse.clickX - 25 - 550;
            int y = Mouse.clickY - 5 - 4;
            if (x >= 0 && y >= 0 && x < 146 && y < 151) {
                x -= 73;
                y -= 75;
                int angle = chaseCamYaw + cam_yaw_off & 0x7ff;
                int sin = Canvas3D.sin[angle];
                int cos = Canvas3D.cos[angle];
                sin = sin * (mapZoomOffset + 256) >> 8;
                cos = cos * (mapZoomOffset + 256) >> 8;
                int k1 = y * sin + x * cos >> 11;
                int l1 = y * cos - x * sin >> 11;
                int dest_x = self.scene_x + k1 >> 7;
                int dest_y = self.scene_y - l1 >> 7;
                walk_to(1, 0, 0, self.path_x[0], self.path_y[0], dest_x, dest_y, 0, 0, 0, true);
            }
        }
    }

    public static void handle_mouse() {
        tmpHoveredWidget = 0;

        if (Mouse.within(Area.VIEWPORT)) {
            if (widgetOverlay != -1) {
                Game.handle_widget_mouse(Widget.get(widgetOverlay), 4, 4, Mouse.lastX, Mouse.lastY, 0);
            } else {
                handle_viewport_mouse();
            }
        }

        if (tmpHoveredWidget != hoveredViewportWidget) {
            hoveredViewportWidget = tmpHoveredWidget;
        }

        tmpHoveredWidget = 0;

        if (Mouse.within(Area.TAB)) {
            if (Sidebar.widgetIndex != -1) {
                handle_widget_mouse(Widget.instance[Sidebar.widgetIndex], 553, 205, Mouse.lastX, Mouse.lastY, 0);
            } else {
                handle_widget_mouse(Widget.instance[Sidebar.selectedTab.widget], 553, 205, Mouse.lastX, Mouse.lastY, 0);
            }
        }

        if (tmpHoveredWidget != hoveredTabWidget) {
            Sidebar.draw = true;
            hoveredTabWidget = tmpHoveredWidget;
        }

        tmpHoveredWidget = 0;

        if (Mouse.within(Area.CHAT)) {
            if (Chat.get_overlay() != -1) {
                handle_widget_mouse(Widget.instance[Chat.get_overlay()], 17, 357, Mouse.lastX, Mouse.lastY, 0);
            } else if (Mouse.lastY < 434 && Mouse.lastX < 426) {
                Chat.handle_mouse(Mouse.lastX - 17, Mouse.lastY - 357);
            }
        }

        if (Chat.get_overlay() != -1 && tmpHoveredWidget != hoveredChatWidget) {
            Chat.redraw = true;
            hoveredChatWidget = tmpHoveredWidget;
        }

        Mouse.wheelAmount = 0;
    }

    public static boolean handle_net() {
        if (connection == null) {
            return false;
        }

        try {
            int available = connection.getAvailable();

            if (available == 0) {
                return false;
            }

            if (Game.ptype == -1) {
                connection.getBytes(in.payload, 0, 1);
                ptype = in.payload[0] & 0xff;

                if (connectionIsaac != null) {
                    ptype = ptype - connectionIsaac.nextInt() & 0xff;
                }

                psize = Packet.SIZE[ptype];
                available--;
            }

            if (psize == -1) {
                if (available > 0) {
                    connection.getBytes(in.payload, 0, 1);
                    psize = in.payload[0] & 0xff;
                    available--;
                } else {
                    return false;
                }
            }

            if (psize == -2) {
                if (available > 1) {
                    connection.getBytes(in.payload, 0, 2);
                    in.position = 0;
                    psize = in.readUnsignedShort();
                    available -= 2;
                } else {
                    return false;
                }
            }

            if (available < psize) {
                return false;
            }

            in.position = 0;
            connection.getBytes(in.payload, 0, psize);
            currentBytesRead += psize + 1;
            netCycle = 0;
            lastPtype3 = lastPtype2;
            lastPtype2 = lastPtype1;
            lastPtype1 = ptype;

            if (Game.ptype == 81) {
                update_players(psize, in);
                sceneLoading = false;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 176) {
                welcomeInfo = in.readUnsignedByteC();
                welcomeUnreadMessages = in.writeUnsignedShortA();
                welcomeNotify = in.readUnsignedByte();
                welcomeLastIp = in.readImeInt();
                welcomeLastPlaydate = in.readUnsignedShort();

                if (welcomeLastIp != 0 && widgetOverlay == -1) {
                    Signlink.getDns(JString.getAddress(welcomeLastIp));
                    close_widgets();
                    char c = '\u028A';

                    if (welcomeInfo != 201 || welcomeNotify == 1) {
                        c = '\u028F';
                    }

                    reportAbuseInput = "";
                    reportAbuseMute = false;
                    for (int i = 0; i < Widget.instance.length; i++) {
                        if (Widget.instance[i] == null || Widget.instance[i].action_type != c) {
                            continue;
                        }
                        widgetOverlay = Widget.instance[i].parent;
                        break;
                    }

                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 64) {
                netRegionX = in.readUnsignedByteC();
                netRegionY = in.readUnsignedByteS();

                for (int x = netRegionX; x < netRegionX + 8; x++) {
                    for (int y = netRegionY; y < netRegionY + 8; y++) {
                        if (Game.itemPile[plane][x][y] != null) {
                            Game.itemPile[plane][x][y] = null;
                            update_item_pile(x, y);
                        }
                    }

                }

                for (SpawntLoc sl = (SpawntLoc) spawnedLocs.top(); sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
                    if (sl.x >= netRegionX && sl.x < netRegionX + 8 && sl.y >= netRegionY && sl.y < netRegionY + 8 && sl.plane == plane) {
                        sl.cycle = 0;
                    }
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the disabled mesh index of a widget.
            if (Game.ptype == 185) {
                int index = in.readUnsignedLeShortA();
                Widget w = Widget.get(index);

                if (w != null) {
                    w.model_type_disabled = 3;
                    if (self.actor_override != null) {
                        w.model_index_disabled = (int) (0x12345678L + self.actor_override.index);
                    } else {
                        w.model_index_disabled = (int) (0x12345678L + self.actor_override.index);
                    }
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Stops camera shake and cinematic mode.
            if (Game.ptype == 107) {
                camCinemaMode = false;

                for (int i = 0; i < 5; i++) {
                    aBooleanArray876[i] = false;
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Clears the items of an inventory.
            if (Game.ptype == 72) {
                int index = in.readUnsignedLeShort();
                Widget w = Widget.instance[index];

                if (w != null) {
                    for (int i = 0; i < w.item_index.length; i++) {
                        w.item_index[i] = -1;
                        w.item_index[i] = 0;
                    }
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Loads the ignore list.
            if (Game.ptype == 214) {
                ignoreCount = psize / 8;

                for (int i = 0; i < ignoreCount; i++) {
                    ignoreLong[i] = in.readLong();
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Moves the camera cinematically
            if (Game.ptype == 166) {
                camCinemaMode = true;
                cam_cinema_dest_x = in.readUnsignedByte();
                cam_cinema_dest_y = in.readUnsignedByte();
                cam_cinema_dest_z = in.readUnsignedShort();
                cam_cinema_base_speed = in.readUnsignedByte();
                cam_cinema_speed = in.readUnsignedByte();
                if (cam_cinema_speed >= 100) {
                    Camera.x = cam_cinema_dest_x * 128 + 64;
                    Camera.y = cam_cinema_dest_y * 128 + 64;
                    Camera.z = get_land_z(Camera.x, Camera.y, plane) - cam_cinema_dest_z;
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the stat level/experience.
            if (Game.ptype == 134) {
                int stat_index = in.readUnsignedByte();
                int stat_experience = in.readMeInt();
                int stat_level = in.readUnsignedByte();
                skillExperience[stat_index] = stat_experience;
                skillLevel[stat_index] = stat_level;
                skillRealLevel[stat_index] = 1;
                for (int i = 0; i < 98; i++) {
                    if (stat_experience >= XP_TABLE[i]) {
                        skillRealLevel[stat_index] = i + 2;
                    }
                }
                Sidebar.draw = true;
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the tab widget.
            if (Game.ptype == 71) {
                int widget = in.readUnsignedShort();
                int tab = in.readUnsignedByteA();

                if (widget == 65535) {
                    widget = -1;
                }

                Sidebar.TAB[tab].widget = widget;
                Sidebar.draw = true;
                Sidebar.drawTabs = true;
                Game.ptype = -1;
                return true;
            }

            // INFO: Plays a song.
            if (Game.ptype == 74) {
                int song = in.readUnsignedLeShort();

                if (song == 65535) {
                    song = -1;
                }

                if (song != -1 || music.tmpDelay != 0) {
                    if (song != -1 && music.currentSong != song && music.volume0 != 0 && music.tmpDelay == 0) {
                        play_music(0, 10, music.volume0, false, 0, song);
                    } else {
                        stop_music(false);
                    }
                }

                music.currentSong = song;
                Game.ptype = -1;
                return true;
            }

            // INFO: Plays temporary music.
            if (Game.ptype == 121) {
                int song = in.readUnsignedLeShortA();
                int delay = in.writeUnsignedShortA();

                if (song == 65535) {
                    song = -1;
                }

                if (music.volume0 != 0 && song != -1) {
                    play_music_instantly(music.volume0, song, 0, 1, false);
                    music.tmpDelay = delay;
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Forcefully disconnects.
            if (Game.ptype == 109) {
                net_disconnect();
                Game.ptype = -1;
                return false;
            }

            // INFO: Offsets the specified widget.
            if (Game.ptype == 70) {
                int x = in.readShort();
                int y = in.readLeShort();
                int index = in.readUnsignedLeShort();
                Widget w = Widget.instance[index];

                if (w != null) {
                    w.x = (short) x;
                    w.y = (short) y;
                }

                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 73 || ptype == 241) {
                int region_x = Game.loadedRegionX;
                int region_y = Game.loadedRegionY;

                if (Game.ptype == 73) {
                    region_x = in.writeUnsignedShortA();
                    region_y = in.readUnsignedShort();
                    serverSentChunk = false;
                }

                // INFO: Modified protocol
                if (Game.ptype == 241) {
                    region_y = in.readUnsignedShort();

                    in.startBitAccess();
                    for (int plane = 0; plane < 4; plane++) {
                        for (int chunk_x = 0; chunk_x < 13; chunk_x++) {
                            for (int chunk_y = 0; chunk_y < 13; chunk_y++) {
                                int i = in.readBits(1);
                                if (i == 1) {
                                    regionChunkUniqueIds[plane][chunk_x][chunk_y] = in.readBits(26);
                                } else {
                                    regionChunkUniqueIds[plane][chunk_x][chunk_y] = -1;
                                }
                            }

                        }

                    }
                    in.stopBitAccess();

                    region_x = in.writeUnsignedShortA();
                    serverSentChunk = true;
                }

                if (Game.loadedRegionX == region_x && Game.loadedRegionY == region_y && sceneState == 2) {
                    Game.ptype = -1;
                    return true;
                }

                Game.loadedRegionX = region_x;
                Game.loadedRegionY = region_y;
                Game.mapBaseX = (Game.loadedRegionX - 6) * 8;
                Game.mapBaseY = (Game.loadedRegionY - 6) * 8;
                Game.restrictRegion = false;

                if ((Game.loadedRegionX / 8 == 48 || Game.loadedRegionX / 8 == 49) && Game.loadedRegionY / 8 == 48) {
                    restrictRegion = true;
                }

                if (Game.loadedRegionX / 8 == 48 && Game.loadedRegionY / 8 == 148) {
                    restrictRegion = true;
                }

                sceneState = 1;
                sceneLoadStart = System.currentTimeMillis();
                producerScene.prepare();
                BitmapFont.NORMAL.draw("Loading - please wait.", 256, 150, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
                producerScene.draw(4, 4);

                if (Game.ptype == 73) {
                    int count = 0;

                    for (int chunk_x = (Game.loadedRegionX - 6) / 8; chunk_x <= (Game.loadedRegionX + 6) / 8; chunk_x++) {
                        for (int chunk_y = (Game.loadedRegionY - 6) / 8; chunk_y <= (Game.loadedRegionY + 6) / 8; chunk_y++) {
                            count++;
                        }
                    }

                    chunkLocPayload = new byte[count][];
                    chunkLandscapePayload = new byte[count][];
                    chunkCoords = new int[count];
                    mapUids = new int[count];
                    landscapeUids = new int[count];
                    count = 0;

                    for (int chunk_x = (Game.loadedRegionX - 6) / 8; chunk_x <= (Game.loadedRegionX + 6) / 8; chunk_x++) {
                        for (int chunk_y = (Game.loadedRegionY - 6) / 8; chunk_y <= (Game.loadedRegionY + 6) / 8; chunk_y++) {
                            chunkCoords[count] = (chunk_x << 8) + chunk_y;

                            if (restrictRegion && (chunk_y == 49 || chunk_y == 149 || chunk_y == 147 || chunk_x == 50 || chunk_x == 49 && chunk_y == 47)) {
                                mapUids[count] = -1;
                                landscapeUids[count] = -1;
                                count++;
                            } else {
                                int map_uid = mapUids[count] = ondemand.get_map_uid(chunk_x, chunk_y, 0);

                                if (map_uid != -1) {
                                    ondemand.send_request(3, map_uid);
                                }

                                int landscape_uid = landscapeUids[count] = ondemand.get_map_uid(chunk_x, chunk_y, 1);

                                if (landscape_uid != -1) {
                                    ondemand.send_request(3, landscape_uid);
                                }
                                count++;
                            }
                        }
                    }
                } else if (Game.ptype == 241) {
                    int count = 0;
                    int chunk_coords[] = new int[26 * 26];

                    for (int plane = 0; plane < 4; plane++) {
                        for (int x = 0; x < 13; x++) {
                            for (int y = 0; y < 13; y++) {
                                int chunk_uid = regionChunkUniqueIds[plane][x][y];
                                if (chunk_uid != -1) {
                                    int chunk_x = chunk_uid >> 14 & 0x3ff;
                                    int chunk_y = chunk_uid >> 3 & 0x7ff;
                                    int chunk_coord = (chunk_x / 8 << 8) + chunk_y / 8;
                                    for (int i = 0; i < count; i++) {
                                        if (chunk_coords[i] != chunk_coord) {
                                            continue;
                                        }
                                        chunk_coord = -1;
                                        break;
                                    }

                                    if (chunk_coord != -1) {
                                        chunk_coords[count++] = chunk_coord;
                                    }
                                }
                            }
                        }
                    }

                    chunkLocPayload = new byte[count][];
                    chunkLandscapePayload = new byte[count][];
                    Game.chunkCoords = new int[count];
                    mapUids = new int[count];
                    landscapeUids = new int[count];

                    for (int i = 0; i < count; i++) {
                        int chunk_coord = Game.chunkCoords[i] = chunk_coords[i];
                        int chunk_x = chunk_coord >> 8 & 0xFF;
                        int chunk_y = chunk_coord & 0xFF;

                        int map_uid = mapUids[i] = ondemand.get_map_uid(chunk_x, chunk_y, 0);

                        if (map_uid != -1) {
                            ondemand.send_request(3, map_uid);
                        }

                        int landscape_uid = landscapeUids[i] = ondemand.get_map_uid(chunk_x, chunk_y, 1);

                        if (landscape_uid != -1) {
                            ondemand.send_request(3, landscape_uid);
                        }
                    }
                }

                int base_dx = mapBaseX - lastMapBaseX;
                int base_dy = mapBaseY - lastMapBaseY;
                lastMapBaseX = mapBaseX;
                lastMapBaseY = mapBaseY;

                for (int i = 0; i < 16384; i++) {
                    Actor a = actors[i];
                    if (a != null) {
                        for (int j = 0; j < 10; j++) {
                            a.path_x[j] -= base_dx;
                            a.path_y[j] -= base_dy;
                        }
                        a.scene_x -= base_dx * 128;
                        a.scene_y -= base_dy * 128;
                    }
                }

                for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
                    Player p = players[i];
                    if (p != null) {
                        for (int j = 0; j < 10; j++) {
                            p.path_x[j] -= base_dx;
                            p.path_y[j] -= base_dy;
                        }
                        p.scene_x -= base_dx * 128;
                        p.scene_y -= base_dy * 128;
                    }
                }

                sceneLoading = true;

                byte x1 = 0;
                byte x2 = 104;
                byte dx = 1;

                if (base_dx < 0) {
                    x1 = 103;
                    x2 = -1;
                    dx = -1;
                }

                byte y1 = 0;
                byte y2 = 104;
                byte dy = 1;

                if (base_dy < 0) {
                    y1 = 103;
                    y2 = -1;
                    dy = -1;
                }

                if (Game.itemPile != null) {
                    for (int x = x1; x != x2; x += dx) {
                        for (int y = y1; y != y2; y += dy) {
                            int old_x = x + base_dx;
                            int old_y = y + base_dy;
                            for (int plane = 0; plane < 4; plane++) {
                                if (old_x >= 0 && old_y >= 0 && old_x < 104 && old_y < 104) {
                                    Game.itemPile[plane][x][y] = Game.itemPile[plane][old_x][old_y];
                                } else {
                                    Game.itemPile[plane][x][y] = null;
                                }
                            }
                        }
                    }
                }

                for (SpawntLoc sl = (SpawntLoc) spawnedLocs.top(); sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
                    sl.x -= base_dx;
                    sl.y -= base_dy;
                    if (sl.x < 0 || sl.y < 0 || sl.x >= 104 || sl.y >= 104) {
                        sl.detach();
                    }
                }

                if (mapMarkerX != 0) {
                    mapMarkerX -= base_dx;
                    mapMarkerY -= base_dy;
                }

                camCinemaMode = false;
                Game.ptype = -1;
                return true;
            }

            // INFO: Resets the animations of the widget.
            if (Game.ptype == 208) {
                int index = in.readLeShort();
                if (index >= 0) {
                    Widget.reset_animations(index);
                }
                widgetUnderlay = index;
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the minimap state.
            if (Game.ptype == 99) {
                minimapState = in.readUnsignedByte();
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the widget's disabled mesh.
            if (Game.ptype == 75) {
                int mesh_index = in.readUnsignedLeShortA();
                int index = in.readUnsignedLeShortA();
                Widget.instance[index].model_type_disabled = 2;
                Widget.instance[index].model_index_disabled = mesh_index;
                Game.ptype = -1;
                return true;
            }

            // Info: Sets the next update time.
            if (Game.ptype == 114) {
                nextUpdate = System.currentTimeMillis() + (in.readUnsignedLeShort() * 1000);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 60) {
                netRegionX = in.readUnsignedByte();
                netRegionY = in.readUnsignedByteC();

                while (in.position < psize) {
                    handle_packet(in, in.readUnsignedByte());
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 35) {
                int l3 = in.readUnsignedByte();
                int k11 = in.readUnsignedByte();
                int j17 = in.readUnsignedByte();
                int k21 = in.readUnsignedByte();
                aBooleanArray876[l3] = true;
                anIntArray873[l3] = k11;
                anIntArray1203[l3] = j17;
                anIntArray928[l3] = k21;
                anIntArray1030[l3] = 0;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 174) {
                int index = in.readUnsignedShort();
                int type = in.readUnsignedByte();
                int delay = in.readUnsignedShort();
                if (!lowDetail && soundCount < 50) {
                    soundIndex[soundCount] = index;
                    soundType[soundCount] = type;
                    soundDelay[soundCount] = delay + WaveSound.delay[index];
                    soundCount++;
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Changes a player option.
            if (Game.ptype == 104) {
                int option = in.readUnsignedByteC();
                int priority = in.readUnsignedByteA();
                String action = in.readString();

                if (option >= 1 && option <= 5) {
                    if (action.equalsIgnoreCase("null")) {
                        action = null;
                    }
                    playerAction[option - 1] = action;
                    playerActionPriority[option - 1] = priority == 0;
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Removes the map marker.
            if (Game.ptype == 78) {
                mapMarkerX = 0;
                Game.ptype = -1;
                return true;
            }

            // INFO: Adds a message to the chatbox.
            if (Game.ptype == 253) {
                String message = in.readString();

                if (message.endsWith(":tradereq:")) {
                    String name = message.substring(0, message.indexOf(":"));
                    long l = JString.toLong(name);
                    boolean ignored = false;

                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != l) {
                            continue;
                        }
                        ignored = true;
                        break;
                    }

                    if (!ignored && messageStatus == 0) {
                        Chat.put(name, "wishes to trade with you.", Chat.TYPE_TRADE_REQUEST);
                    }
                } else if (message.endsWith(":duelreq:")) {
                    String name = message.substring(0, message.indexOf(":"));
                    long l = JString.toLong(name);
                    boolean ignored = false;

                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != l) {
                            continue;
                        }
                        ignored = true;
                        break;
                    }

                    if (!ignored && messageStatus == 0) {
                        Chat.put(name, "wishes to duel with you.", Chat.TYPE_DUEL_REQUEST);
                    }
                } else if (message.endsWith(":chalreq:")) {
                    String name = message.substring(0, message.indexOf(":"));
                    long l = JString.toLong(name);
                    boolean ignored = false;

                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != l) {
                            continue;
                        }
                        ignored = true;
                        break;
                    }

                    if (!ignored && messageStatus == 0) {
                        Chat.put(name, message.substring(message.indexOf(":") + 1, message.length() - 9), 8);
                    }
                } else {
                    Chat.put(message, 0);
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Resets all actor animations.
            if (Game.ptype == 1) {
                for (int i = 0; i < players.length; i++) {
                    if (players[i] != null) {
                        players[i].seq_index = -1;
                    }
                }
                for (int i = 0; i < actors.length; i++) {
                    if (actors[i] != null) {
                        actors[i].seq_index = -1;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 50) {
                long l = in.readLong();
                int world = in.readUnsignedByte();
                String name = JString.getFormattedString(l);

                for (int i = 0; i < friendCount; i++) {
                    if (l != friendLong[i]) {
                        continue;
                    }
                    if (friendNode[i] != world) {
                        friendNode[i] = world;
                        Sidebar.draw = true;
                        if (world > 0) {
                            Chat.put(name + " has logged in.", Chat.TYPE_NOTIFY_PRIVATE);
                        }
                        if (world == 0) {
                            Chat.put(name + " has logged out.", Chat.TYPE_NOTIFY_PRIVATE);
                        }
                    }
                    name = null;
                    break;
                }

                if (name != null && friendCount < 200) {
                    friendLong[friendCount] = l;
                    friendName[friendCount] = name;
                    friendNode[friendCount] = world;
                    friendCount++;
                    Sidebar.draw = true;
                }

                for (boolean flag6 = false; !flag6; ) {
                    flag6 = true;
                    for (int i = 0; i < friendCount - 1; i++) {
                        if (friendNode[i] != nodeIndex && friendNode[i + 1] == nodeIndex || friendNode[i] == 0 && friendNode[i + 1] != 0) {
                            int j31 = friendNode[i];
                            friendNode[i] = friendNode[i + 1];
                            friendNode[i + 1] = j31;
                            String s10 = friendName[i];
                            friendName[i] = friendName[i + 1];
                            friendName[i + 1] = s10;
                            long l32 = friendLong[i];
                            friendLong[i] = friendLong[i + 1];
                            friendLong[i + 1] = l32;
                            Sidebar.draw = true;
                            flag6 = false;
                        }
                    }

                }

                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 110) {
                if (Sidebar.selectedTab.index == 12) {
                    Sidebar.draw = true;
                }

                Game.energyLeft = in.readUnsignedByte();
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 254) {
                Game.markType = in.readUnsignedByte();

                if (Game.markType == 1) {
                    Game.markActorIndex = in.readUnsignedShort();
                }

                if (Game.markType >= 2 && Game.markType <= 6) {
                    if (Game.markType == 2) {
                        Game.markOffX = 64;
                        Game.markOffY = 64;
                    }

                    if (Game.markType == 3) {
                        Game.markOffX = 0;
                        Game.markOffY = 64;
                    }

                    if (Game.markType == 4) {
                        Game.markOffX = 128;
                        Game.markOffY = 64;
                    }

                    if (Game.markType == 5) {
                        Game.markOffX = 64;
                        Game.markOffY = 0;
                    }

                    if (Game.markType == 6) {
                        Game.markOffX = 64;
                        Game.markOffY = 128;
                    }

                    Game.markType = 2;
                    Game.markX = in.readUnsignedShort();
                    Game.markY = in.readUnsignedShort();
                    Game.markZ = in.readUnsignedByte();
                }

                if (Game.markType == 10) {
                    Game.markPlayerIndex = in.readUnsignedShort();
                }

                Game.ptype = -1;
                return true;
            }

            // Sets both the main widget and sidebar widget.
            if (Game.ptype == 248) {
                int overlay_widget = in.writeUnsignedShortA();
                int sidebar_widget = in.readUnsignedShort();
                Chat.clear_overlay();
                Chat.clear();
                Game.widgetOverlay = overlay_widget;
                Sidebar.widgetIndex = sidebar_widget;
                Sidebar.draw = true;
                Sidebar.drawTabs = true;
                Game.dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            // Sets the scroll amount of the specified widget.
            if (Game.ptype == 79) {
                int index = in.readUnsignedLeShort();
                int amount = in.writeUnsignedShortA();
                Widget w = Widget.instance[index];

                if (w != null && w.type == 0) {
                    if (amount < 0) {
                        amount = 0;
                    }

                    if (amount > w.scroll_height - w.height) {
                        amount = w.scroll_height - w.height;
                    }
                    w.scroll_amount = amount;
                }

                Game.ptype = -1;
                return true;
            }

            // Resets settings to their defaults?
            if (Game.ptype == 68) {
                for (int i = 0; i < settings.length; i++) {
                    if (settings[i] != defaultSettings[i]) {
                        settings[i] = defaultSettings[i];
                        handle_varp(i);
                        Sidebar.draw = true;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 196) {
                long name_long = in.readLong();
                in.readInt(); // ignore dis
                int rights = in.readUnsignedByte();

                boolean not_ignored = true;

                if (rights <= 1) {
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != name_long) {
                            continue;
                        }
                        not_ignored = false;
                        break;
                    }

                }
                if (not_ignored && messageStatus == 0) {
                    StringBuilder b = new StringBuilder();
                    if (rights > 0) {
                        b.append("@cr").append(rights).append("@");
                    }
                    b.append(JString.getFormattedString(name_long));
                    Chat.put(b.toString(), JString.getFormatted(psize - 13, in), rights > 0 ? 7 : 3);
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 85) {
                netRegionY = in.readUnsignedByteC();
                netRegionX = in.readUnsignedByteC();
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 24) {
                Sidebar.setFlashing(in.readUnsignedByteS());

                if (Sidebar.flashingTab == Sidebar.selectedTab) {
                    if (Sidebar.flashingTab.index == 3) {
                        Sidebar.openTab(1);
                    } else {
                        Sidebar.openTab(3);
                    }
                    Sidebar.draw = true;
                }
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the model information of the widget.
            if (Game.ptype == 246) {
                Widget w = Widget.get(in.readUnsignedLeShort());
                int zoom = in.readUnsignedShort();
                int index = in.readUnsignedShort();

                if (w == null) {
                    Game.ptype = -1;
                    return true;
                }

                if (index == 65535) {
                    w.model_type_disabled = 0;
                    Game.ptype = -1;
                    return true;
                } else {
                    ObjConfig oc = ObjConfig.get(index);
                    w.model_type_disabled = 4;
                    w.model_index_disabled = index;
                    w.model_pitch = oc.icon_pitch;
                    w.model_yaw = oc.icon_yaw;
                    w.model_zoom = (oc.icon_dist * 100) / zoom;
                    Game.ptype = -1;
                    return true;
                }
            }

            if (Game.ptype == 171) {
                boolean hidden = in.readBoolean();
                int index = in.readUnsignedShort();
                Widget.instance[index].hidden = hidden;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 142) {
                int index = in.readUnsignedLeShort();
                Widget.reset_animations(index);
                Chat.clear_overlay();
                Chat.clear();
                Sidebar.widgetIndex = index;
                Sidebar.draw = true;
                Sidebar.drawTabs = true;
                widgetOverlay = -1;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 126) {
                String message = in.readString();
                int index = in.writeUnsignedShortA();
                Widget w = Widget.get(index);

                if (w != null) {
                    Widget.instance[index].message_disabled = message;
                    if (Widget.instance[index].parent == Sidebar.selectedTab.widget) {
                        Sidebar.draw = true;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 206) {
                for (int i = 0; i < Chat.Settings.values.length; i++) {
                    Chat.Settings.values[i] = in.readUnsignedByte();
                }
                Chat.Settings.redraw = true;
                Chat.redraw = true;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 240) {
                if (Sidebar.selectedTab.index == 12) {
                    Sidebar.draw = true;
                }
                weightCarried = in.readShort();
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 8) {
                int widget_index = in.readUnsignedLeShortA();
                int model_index = in.readUnsignedShort();
                Widget.instance[widget_index].model_type_disabled = 1;
                Widget.instance[widget_index].model_index_disabled = model_index;
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the disabled color of the widget.
            if (Game.ptype == 122) {
                int widget_index = in.readUnsignedLeShortA();

                // COLOR FORMAT:
                // R: 0-31 G: 0-31 B: 0-31
                // ((r >> 3) << 16) | ((g >> 3) << 16) | (b >> 3)
                // or just clamp it serverside to 0 to 31 each channel.
                int color = in.readUnsignedLeShortA();
                int red = color >> 10 & 0x1F;
                int green = color >> 5 & 0x1F;
                int blue = color & 0x1F;
                Widget.instance[widget_index].rgb_disabled = (red << 19) | (green << 11) | (blue << 3);
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the items to the specified container index.
            if (Game.ptype == 53) {
                Sidebar.draw = true;
                int index = in.readUnsignedShort();
                int size = in.readUnsignedShort();

                Widget w = Widget.instance[index];

                try {
                    for (int i = 0; i < size; i++) {
                        int count = in.readUnsignedByte();

                        if (count == 255) {
                            count = in.readImeInt();
                        }

                        w.item_index[i] = (short) in.readUnsignedLeShortA();
                        w.item_count[i] = count;
                    }
                } catch (Exception e) {
                    /* ignore out of bounds exception */
                }

                // Clears the wrest of the items.
                for (int i = size; i < w.item_index.length; i++) {
                    w.item_index[i] = 0;
                    w.item_count[i] = 0;
                }

                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 230) {
                int zoom = in.writeUnsignedShortA();
                int index = in.readUnsignedShort();
                int pitch = in.readUnsignedShort();
                int yaw = in.readUnsignedLeShortA();
                Widget.instance[index].model_pitch = pitch;
                Widget.instance[index].model_yaw = yaw;
                Widget.instance[index].model_zoom = zoom;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 221) {
                frenemiesStatus = in.readUnsignedByte();
                Sidebar.draw = true;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 177) {
                camCinemaMode = true;
                cam_cinema_aim_x = in.readUnsignedByte();
                cam_cinema_aim_y = in.readUnsignedByte();
                cam_cinema_aim_z = in.readUnsignedShort();
                cam_cinema_rot_base = in.readUnsignedByte();
                cam_cinema_rot_modifier = in.readUnsignedByte();

                if (cam_cinema_rot_modifier >= 100) {
                    int x = cam_cinema_aim_x * 128 + 64;
                    int y = cam_cinema_aim_y * 128 + 64;
                    int z = get_land_z(x, y, plane) - cam_cinema_aim_z;
                    int x_diff = x - Camera.x;
                    int z_diff = z - Camera.z;
                    int y_diff = y - Camera.y;
                    int length = (int) Math.sqrt(x_diff * x_diff + y_diff * y_diff);

                    Camera.pitch = (int) (Math.atan2(z_diff, length) * 325.94900000000001D) & 0x7ff;
                    Camera.yaw = (int) (Math.atan2(x_diff, y_diff) * -325.94900000000001D) & 0x7ff;

                    if (Camera.pitch < 128) {
                        Camera.pitch = 128;
                    }

                    if (Camera.pitch > 383) {
                        Camera.pitch = 383;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 249) {
                freeFriendsList = in.readUnsignedByteA();
                localPlayerIndex = in.readUnsignedLeShortA();
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 65) {
                update_actors(psize, in);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 27) {
                Chat.set(State.ENTER_AMOUNT);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 187) {
                Chat.set(State.ENTER_NAME);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 97) {
                int index = in.readUnsignedShort();

                try {
                    Widget.reset_animations(index);
                } catch (Exception e) {
                    e.printStackTrace();
                    Game.ptype = -1;
                    return true;
                }

                if (Sidebar.widgetIndex != -1) {
                    Sidebar.widgetIndex = -1;
                    Sidebar.draw = true;
                    Sidebar.drawTabs = true;
                }

                Chat.clear_overlay();
                Chat.clear();
                widgetOverlay = index;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 218) {
                Chat.set_underlay(in.readShortA());
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 87) {
                int index = in.readUnsignedLeShort();
                int value = in.readMeInt();

                defaultSettings[index] = value;

                if (settings[index] != value) {
                    settings[index] = value;
                    handle_varp(index);
                    Sidebar.draw = true;

                    if (Chat.get_underlay() != -1) {
                        Chat.redraw = true;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 36) {
                int index = in.readUnsignedLeShort();
                byte value = in.readByte();

                defaultSettings[index] = value;

                if (settings[index] != value) {
                    settings[index] = value;
                    handle_varp(index);
                    Sidebar.draw = true;

                    if (Chat.get_underlay() != -1) {
                        Chat.redraw = true;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 61) {
                inMultiZone = in.readUnsignedByte();
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 200) {
                Widget w = Widget.get(in.readUnsignedShort());
                int seq_index = in.readShort();

                if (w == null) {
                    Game.ptype = -1;
                    return true;
                }

                w.seq_index_disabled = seq_index;

                if (seq_index == -1) {
                    w.sequence_frame = 0;
                    w.sequence_cycle = 0;
                }

                Game.ptype = -1;
                return true;
            }
            if (Game.ptype == 219) {
                if (Sidebar.widgetIndex != -1) {
                    Sidebar.widgetIndex = -1;
                    Sidebar.draw = true;
                    Sidebar.drawTabs = true;
                }

                Chat.clear_overlay();
                Chat.clear();
                widgetOverlay = -1;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 34) {
                Sidebar.draw = true;
                Widget w = Widget.instance[in.readUnsignedShort()];

                while (in.position < psize) {
                    int slot = in.writeUnsignedSmart();
                    int index = in.readUnsignedShort();
                    int count = in.readUnsignedByte();

                    if (count == 255) {
                        count = in.readInt();
                    }

                    if (slot >= 0 && slot < w.item_index.length) {
                        w.item_index[slot] = (short) index;
                        w.item_count[slot] = count;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 105 || ptype == 84 || ptype == 147 || ptype == 215 || ptype == 4 || ptype == 117 || ptype == 156 || ptype == 44 || ptype == 160 || ptype == 101 || ptype == 151) {
                handle_packet(in, ptype);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 106) {
                Sidebar.openTab(in.readUnsignedByteC());
                Sidebar.draw = true;
                Sidebar.drawTabs = true;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 164) {
                int index = in.readUnsignedLeShort();

                Widget.reset_animations(index);

                if (Sidebar.widgetIndex != -1) {
                    Sidebar.widgetIndex = -1;
                    Sidebar.draw = true;
                    Sidebar.drawTabs = true;
                }

                Chat.set_overlay(index);
                widgetOverlay = -1;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            Signlink.error("T1 - " + ptype + "," + psize + " - " + lastPtype2 + "," + lastPtype3);
            net_disconnect();
        } catch (IOException e) {
            handle_connection_lost();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("T2");
            sb.append(" - last:").append(ptype).append(',').append(lastPtype2).append(',').append(lastPtype3);
            sb.append(" - psize:").append(psize);
            sb.append(" - pos:").append(self.get_tile_x()).append(',').append(self.get_tile_y()).append('\n');

            for (int i = 0; i < psize && i < in.position; i++) {
                sb.append(Integer.toHexString((byte) in.payload[i])).append(',');
            }

            Signlink.error(sb.toString());
            e.printStackTrace();
            net_disconnect();
        }
        return true;
    }

    public static void handle_new_actors(int position, Buffer b) {
        while (b.bitPosition + 21 < position * 8) {
            int actor_index = b.readBits(14);

            if (actor_index == 16383) {
                break;
            }

            if (actors[actor_index] == null) {
                actors[actor_index] = new Actor();
            }

            Actor a = actors[actor_index];
            actorIndices[actorCount++] = actor_index;
            a.update_cycle = loopCycle;

            int y = b.readBits(5);

            if (y > 15) {
                y -= 32;
            }

            int x = b.readBits(5);

            if (x > 15) {
                x -= 32;
            }

            int discard_walk_queue = b.readBits(1);

            a.config = ActorConfig.get(b.readBits(12));

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = actor_index;
            }

            a.size = a.config.has_options;
            a.turn_speed = a.config.turn_speed;
            a.walk_animation = a.config.move_seq;
            a.turn_180_animation = a.config.turn_180_seq;
            a.turn_r_animation = a.config.turn_r_seq;
            a.turn_l_animation = a.config.turn_l_seq;
            a.stand_animation = a.config.stand_sequence;
            a.move_to(self.path_x[0] + x, self.path_y[0] + y, discard_walk_queue == 1);
        }
        b.stopBitAccess();
    }

    public static void handle_ondemand() {
        do {
            OnDemandRequest r;
            do {
                r = ondemand.next();

                if (r == null) {
                    return;
                }

                if (r.archive == 0) {
                    Model.load(r.payload, r.file);

                    if ((ondemand.mesh_flags(r.file) & 0x62) != 0) {
                        Sidebar.draw = true;

                        if (Chat.get_overlay() != -1) {
                            Chat.redraw = true;
                        }
                    }
                }

                if (r.archive == 1 && r.payload != null) {
                    SequenceFrame.load(r.payload);
                }

                if (r.archive == 2 && r.payload != null) {
                    music.buffer = r.payload;
                    play_music(0, 10, music.volume0, false, 0, r.file);
                }

                if (r.archive == 3 && sceneState == 1) {
                    for (int i = 0; i < chunkLocPayload.length; i++) {
                        if (mapUids[i] == r.file) {
                            chunkLocPayload[i] = r.payload;
                            if (r.payload == null) {
                                mapUids[i] = -1;
                            }
                            break;
                        }

                        if (landscapeUids[i] != r.file) {
                            continue;
                        }

                        chunkLandscapePayload[i] = r.payload;

                        if (r.payload == null) {
                            landscapeUids[i] = -1;
                        }
                        break;
                    }

                }
            } while (r.archive != 93 || !ondemand.has_landscape(r.file));
            Scene.requestLocModels(new Buffer(r.payload), ondemand);
        } while (true);
    }

    public static void handle_packet(Buffer b, int opcode) {
        // INFO: Update Ground Item
        if (opcode == 84) {
            int coord = b.readUnsignedByte();
            int x = netRegionX + (coord >> 4 & 7);
            int y = netRegionY + (coord & 7);
            int index = b.readUnsignedShort();
            int old_stack_index = b.readUnsignedShort();
            int new_stack_index = b.readUnsignedShort();

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Chain c = Game.itemPile[plane][x][y];
                if (c != null) {
                    for (Item item = (Item) c.top(); item != null; item = (Item) c.next()) {
                        if (item.index != (index & 0x7fff) || item.stack_index != old_stack_index) {
                            continue;
                        }
                        item.stack_index = new_stack_index;
                        break;
                    }

                    update_item_pile(x, y);
                }
            }
            return;
        }

        if (opcode == 105) {
            int coord = b.readUnsignedByte();
            int x = netRegionX + (coord >> 4 & 0x7);
            int y = netRegionY + (coord & 0x7);
            int index = b.readUnsignedShort();
            int values = b.readUnsignedByte();
            int radius = values >> 4 & 0xf;
            int type = values & 7;

            if (self.path_x[0] >= x - radius && self.path_x[0] <= x + radius && self.path_y[0] >= y - radius && self.path_y[0] <= y + radius && !lowDetail && soundCount < 50) {
                soundIndex[soundCount] = index;
                soundType[soundCount] = type;
                soundDelay[soundCount] = WaveSound.delay[index];
                soundCount++;
            }
        }

        if (opcode == 215) {
            int index = b.writeUnsignedShortA();
            int coords = b.readUnsignedByteS();
            int x = netRegionX + (coords >> 4 & 7);
            int y = netRegionY + (coords & 7);
            int entity_index = b.writeUnsignedShortA();
            int stack = b.readUnsignedShort();

            if (x >= 0 && y >= 0 && x < 104 && y < 104 && entity_index != localPlayerIndex) {
                Item item = new Item();
                item.index = (short) index;
                item.stack_index = stack;

                if (Game.itemPile[plane][x][y] == null) {
                    Game.itemPile[plane][x][y] = new Chain();
                }

                Game.itemPile[plane][x][y].pushBack(item);
                update_item_pile(x, y);
            }
            return;
        }

        if (opcode == 156) {
            int coords = b.readUnsignedByteA();
            int x = netRegionX + (coords >> 4 & 7);
            int y = netRegionY + (coords & 7);
            int index = b.readUnsignedShort();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Chain pile = Game.itemPile[plane][x][y];
                if (pile != null) {
                    for (Item item = (Item) pile.top(); item != null; item = (Item) pile.next()) {
                        if (item.index != (index & 0x7fff)) {
                            continue;
                        }
                        item.detach();
                        break;
                    }
                    if (pile.top() == null) {
                        Game.itemPile[plane][x][y] = null;
                    }
                    update_item_pile(x, y);
                }
            }
            return;
        }

        if (opcode == 160) {
            int coords = b.readUnsignedByteS();
            int x = netRegionX + (coords >> 4 & 7);
            int y = netRegionY + (coords & 7);
            int attributes = b.readUnsignedByteS();
            int type = attributes >> 2;
            int rotation = attributes & 3;
            int class_type = LOC_CLASS_TYPE[type];
            int sequence = b.writeUnsignedShortA();

            if (x >= 0 && y >= 0 && x < 103 && y < 103) {
                int v_sw = heightMap[plane][x][y];
                int v_se = heightMap[plane][x + 1][y];
                int v_ne = heightMap[plane][x + 1][y + 1];
                int v_nw = heightMap[plane][x][y + 1];

                if (class_type == 0) {
                    WallLoc wl = landscape.getWall(plane, x, y);
                    if (wl != null) {
                        int index = wl.uid >> 14 & 0x7fff;
                        if (type == 2) {
                            wl.root = new Loc(index, 4 + rotation, 2, v_se, v_ne, v_sw, v_nw, sequence, false);
                            wl.extension = new Loc(index, rotation + 1 & 3, 2, v_se, v_ne, v_sw, v_nw, sequence, false);
                        } else {
                            wl.root = new Loc(index, rotation, type, v_se, v_ne, v_sw, v_nw, sequence, false);
                        }
                    }
                } else if (class_type == 1) {
                    WallDecoration wd = landscape.getWallDecoration(x, 866, y, plane);
                    if (wd != null) {
                        wd.node = new Loc(wd.uid >> 14 & 0x7fff, 0, 4, v_se, v_ne, v_sw, v_nw, sequence, false);
                    }
                } else if (class_type == 2) {
                    StaticLoc sl = landscape.getLoc(x, y, plane);
                    if (type == 11) {
                        type = 10;
                    }
                    if (sl != null) {
                        sl.node = new Loc(sl.uid >> 14 & 0x7fff, rotation, type, v_se, v_ne, v_sw, v_nw, sequence, false);
                    }
                } else if (class_type == 3) {
                    GroundDecoration gd = landscape.getGroundDecoration(x, y, plane);
                    if (gd != null) {
                        gd.node = new Loc(gd.uid >> 14 & 0x7fff, rotation, 22, v_se, v_ne, v_sw, v_nw, sequence, false);
                    }
                }
            }
            return;
        }

        if (opcode == 147) {
            int offset = b.readUnsignedByteS();
            int x = netRegionX + (offset >> 4 & 7);
            int y = netRegionY + (offset & 7);
            int player_index = b.readUnsignedShort();
            byte x2 = b.readByteS();
            int start = b.readUnsignedLeShort();
            byte y2 = b.readByteC();
            int end = b.readUnsignedShort();
            int arrangement = b.readUnsignedByteS();
            byte x1 = b.readByte();
            int loc_index = b.readUnsignedShort();
            byte y1 = b.readByteC();

            int type = arrangement >> 2;
            int rotation = arrangement & 3;
            int class_type = LOC_CLASS_TYPE[type];
            Player p;

            if (player_index == localPlayerIndex) {
                p = self;
            } else {
                p = players[player_index];
            }

            if (p != null) {
                LocConfig lc = LocConfig.get(loc_index);
                int v_sw = heightMap[plane][x][y];
                int v_se = heightMap[plane][x + 1][y];
                int v_ne = heightMap[plane][x + 1][y + 1];
                int v_nw = heightMap[plane][x][y + 1];
                Model mesh = lc.getModel(type, rotation, v_sw, v_se, v_ne, v_nw, -1);

                if (mesh != null) {
                    spawn_loc(-1, x, y, plane, 0, 0, end + 1, start + 1, class_type);

                    p.loc_start_cycle = start + loopCycle;
                    p.loc_end_cycle = end + loopCycle;

                    p.loc_model = mesh;
                    int loc_size_x = lc.sizeX;
                    int loc_size_y = lc.sizeY;

                    if (rotation == 1 || rotation == 3) {
                        loc_size_x = lc.sizeY;
                        loc_size_y = lc.sizeX;
                    }

                    p.loc_x = x * 128 + loc_size_x * 64;
                    p.loc_y = y * 128 + loc_size_y * 64;
                    p.loc_z = get_land_z(p.loc_x, p.loc_y, plane);

                    if (x1 > x2) {
                        byte new_size_x2 = x1;
                        x1 = x2;
                        x2 = new_size_x2;
                    }
                    if (y1 > y2) {
                        byte new_size_y2 = y1;
                        y1 = y2;
                        y2 = new_size_y2;
                    }

                    p.loc_x0 = x + x1;
                    p.loc_y0 = y + y1;
                    p.loc_x1 = x + x2;
                    p.loc_y1 = y + y2;
                }
            }
        }

        if (opcode == 151) {
            int offset = b.readUnsignedByteA();
            int x = netRegionX + (offset >> 4 & 7);
            int y = netRegionY + (offset & 7);
            int loc_index = b.readUnsignedLeShort();
            int loc_info = b.readUnsignedByteS();
            int loc_type = loc_info >> 2;
            int loc_rotation = loc_info & 3;
            int class_type = LOC_CLASS_TYPE[loc_type];

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                spawn_loc(loc_index, x, y, plane, loc_type, loc_rotation, -1, 0, class_type);
            }
            return;
        }

        if (opcode == 4) {
            int offset = b.readUnsignedByte();
            int x = netRegionX + (offset >> 4 & 7);
            int y = netRegionY + (offset & 7);
            int index = b.readUnsignedShort();
            int z = b.readUnsignedByte();
            int delay = b.readUnsignedShort();

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                x = x * 128 + 64;
                y = y * 128 + 64;
                spotanims.pushBack(new SpotAnim(x, y, get_land_z(x, y, plane) - z, plane, loopCycle, delay, index));
            }
            return;
        }

        if (opcode == 44) {
            short item_index = (short) b.readUnsignedLeShortA();
            int stack_index = b.readUnsignedShort();
            int offset = b.readUnsignedByte();
            int x = netRegionX + (offset >> 4 & 7);
            int y = netRegionY + (offset & 7);

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Item i = new Item();
                i.index = item_index;
                i.stack_index = stack_index;

                if (Game.itemPile[plane][x][y] == null) {
                    Game.itemPile[plane][x][y] = new Chain();
                }

                Game.itemPile[plane][x][y].pushBack(i);
                update_item_pile(x, y);
            }
            return;
        }

        if (opcode == 101) {
            int attributes = b.readUnsignedByteC();
            int type = attributes >> 2;
            int rotation = attributes & 3;
            int class_type = LOC_CLASS_TYPE[type];
            int coord = b.readUnsignedByte();
            int x = netRegionX + (coord >> 4 & 7);
            int y = netRegionY + (coord & 7);
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                spawn_loc(-1, x, y, plane, type, rotation, -1, 0, class_type);
            }
            return;
        }

        if (opcode == 117) {
            int offset = b.readUnsignedByte();
            int src_x = netRegionX + (offset >> 4 & 7);
            int src_y = netRegionY + (offset & 7);
            int end_x = src_x + b.readByte();
            int end_y = src_y + b.readByte();
            int target = b.readShort();
            int effect = b.readUnsignedShort();
            int src_z = b.readUnsignedByte() * 4;
            int end_z = b.readUnsignedByte() * 4;
            int delay = b.readUnsignedShort();
            int speed = b.readUnsignedShort();
            int slope = b.readUnsignedByte();
            int source_size = b.readUnsignedByte();

            if (src_x >= 0 && src_y >= 0 && src_x < 104 && src_y < 104 && end_x >= 0 && end_y >= 0 && end_x < 104 && end_y < 104 && effect != 65535) {

                src_x = src_x * 128 + 64;
                src_y = src_y * 128 + 64;
                end_x = end_x * 128 + 64;
                end_y = end_y * 128 + 64;

                Projectile p = new Projectile(slope, end_z, delay + loopCycle, speed + loopCycle, source_size, plane, get_land_z(src_x, src_y, plane) - src_z, src_y, src_x, target, effect);
                p.update(delay + loopCycle, end_x, end_y, get_land_z(end_x, end_y, plane) - end_z);
                projectiles.pushBack(p);
            }
        }
    }

    public static void handle_player_menu_options(Player p, int x, int y, int index) {
        if (p == self) {
            return;
        }

        if (Menu.count >= 400) {
            return;
        }

        String suffix = p.toString();

        if (selectedItem) {
            Menu.add("Use " + selectedItemName + " with @whi@" + suffix, 491, x, y, index);
        } else if (selectedWidget) {
            if ((selectedMask & 8) == 8) {
                Menu.add(selectedTooltip + " @whi@" + suffix, 365, x, y, index);
            }
        } else {
            for (int i = 4; i >= 0; i--) {
                if (playerAction[i] != null) {
                    int offset = 0;
                    if (playerAction[i].equalsIgnoreCase("attack")) {
                        if (p.combat_level > self.combat_level) {
                            offset = 2000;
                        }
                        if (self.team != 0 && p.team != 0) {
                            if (self.team == p.team) {
                                offset = 2000;
                            } else {
                                offset = 0;
                            }
                        }
                    } else if (playerActionPriority[i]) {
                        offset = 2000;
                    }

                    Menu.add(playerAction[i] + " @whi@" + suffix, Action.PLAYER[i] + offset, x, y, index);
                }
            }
        }

        for (int i = 0; i < Menu.count; i++) {
            if (Menu.getAction(i) == 516) {
                Menu.options[i].caption = "Walk here @whi@" + suffix;
                break;
            }
        }

    }

    public static void handle_players() {
        for (int i = -1; i < player_count; i++) {
            int index;

            if (i == -1) {
                index = MAX_PLAYER_INDEX;
            } else {
                index = playerIndices[i];
            }

            Player p = players[index];

            if (p != null) {
                handle_entity(p);
            }
        }

    }

    public static void handle_scene() {
        if (lowDetail && sceneState == 2 && Scene.planeAtBuild != plane) {
            producerScene.prepare();
            BitmapFont.NORMAL.draw("Loading - please wait.", 256, 150, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
            producerScene.draw(4, 4);
            sceneState = 1;
            sceneLoadStart = System.currentTimeMillis();
        }

        if (sceneState == 1) {
            int state = get_scene_load_state((byte) -95);
            if (state != 0 && System.currentTimeMillis() - sceneLoadStart > 0x360_000L) {
                Signlink.error(username + " glcfb " + serverSeed + "," + state + "," + lowDetail + "," + cache[0] + "," + ondemand.immediate_request_count() + "," + plane + "," + loadedRegionX + "," + loadedRegionY);
                sceneLoadStart = System.currentTimeMillis();
            }
        }

        if (sceneState == 2 && plane != lastPlane) {
            lastPlane = plane;
            generate_minimap(plane);
        }
    }

    public static void handle_scrollbar(Widget w, int x, int y, int m_x, int m_y, int height, int scroll_height, boolean is_tab_area) {
        if (draggingScrollbar) {
            scrollDragBound = 32;
        } else {
            scrollDragBound = 0;
        }

        draggingScrollbar = false;

        if (Mouse.wheelAmount != 0 && m_x >= (x - w.width) && m_x <= x && m_y >= y && m_y <= y + height) {
            w.scroll(Mouse.wheelAmount);
            Mouse.wheelAmount = 0;

            if (is_tab_area) {
                Sidebar.draw = true;
                return;
            }
        } else if (m_x >= x && m_x < x + 16 && m_y >= y && m_y < y + 16) {
            w.scroll_amount -= clickCycle * 4;

            if (is_tab_area) {
                Sidebar.draw = true;
                return;
            }
        } else if (m_x >= x && m_x < x + 16 && m_y >= (y + height) - 16 && m_y < y + height) {
            w.scroll_amount += clickCycle * 4;

            if (is_tab_area) {
                Sidebar.draw = true;
                return;
            }
        } else if (m_x >= x - scrollDragBound && m_x < x + 16 + scrollDragBound && m_y >= y + 16 && m_y < (y + height) - 16 && clickCycle > 0) {
            int grip_length = ((height - 32) * height) / scroll_height;

            if (grip_length < 8) {
                grip_length = 8;
            }

            int i2 = m_y - y - 16 - grip_length / 2;
            int j2 = height - 32 - grip_length;

            w.scroll_amount = ((scroll_height - height) * i2) / j2;

            if (is_tab_area) {
                Sidebar.draw = true;
            }

            draggingScrollbar = true;
        }
    }

    public static void handle_spoken() {
        for (int i = -1; i < player_count; i++) {
            int player_index;

            if (i == -1) {
                player_index = MAX_PLAYER_INDEX;
            } else {
                player_index = playerIndices[i];
            }

            Player p = players[player_index];

            if (p != null && p.spoken_life > 0) {
                p.spoken_life--;

                if (p.spoken_life == 0) {
                    p.spoken_message = null;
                }
            }
        }

        for (int i = 0; i < actorCount; i++) {
            int actor_index = actorIndices[i];
            Actor a = actors[actor_index];

            if (a != null && a.spoken_life > 0) {
                a.spoken_life--;

                if (a.spoken_life == 0) {
                    a.spoken_message = null;
                }
            }
        }

    }

    public static void handle_tab_mouse() {
        if (Mouse.clickButton == 1) {
            int tab = 0;
            for (int[] i : TAB_BUTTONS) {
                if (Mouse.clickX >= i[0] && Mouse.clickX <= i[1] && Mouse.clickY >= i[2] && Mouse.clickY <= i[3] && Sidebar.TAB[tab].widget != -1) {
                    Sidebar.draw = true;
                    Sidebar.openTab(tab);
                    Sidebar.drawTabs = true;
                    break;
                }
                tab++;
            }
        }
    }

    public static void handle_varp(int index) {
        int type = Varp.instance[index].index;

        if (type == 0) {
            return;
        }

        int setting = Game.settings[index];

        switch (type) {
            case 1: { // Brightness
                Canvas3D.create_palette(1d - (double) (setting / 10d));
                ObjConfig.sprite_cache.clear();
                redraw = true;
                break;
            }

            case 2: {
                break;
            }

            case 3: { // Music Volume
                int volume = (int) (256f * (1f - (setting / 4f)));

                if (volume != music.volume0) {
                    if (music.volume0 != 0 && music.currentSong == -1) {
                        if (volume != 0) {
                            set_music_volume(volume);
                        } else {
                            stop_music(false);
                            music.tmpDelay = 0;
                        }
                    } else {
                        play_music_instantly(volume, music.currentSong, 0, 0, false);
                        music.tmpDelay = 0;
                    }

                    music.volume0 = volume;
                }
                break;
            }

            case 4: { // Sound Volume
                break;
            }

            case 5: { // One/Two Button Mouse Setting
                mouseButtonSetting = setting;
                break;
            }

            case 6: {
                Chat.Settings.show_effects = setting == 0;
                break;
            }

            case 8: {
                Chat.Settings.private_area = setting;
                Chat.Settings.redraw = true;
                break;
            }

            case 9: {
                anInt913 = setting;
                break;
            }
        }
    }

    public static void handle_viewport_mouse() {
        if (!selectedItem && !selectedWidget) {
            Menu.add("Walk here", 516, Mouse.lastX, Mouse.lastY);
        }

        int last_uid = -1;

        for (int idx = 0; idx < Model.hovered_count; idx++) {
            int uid = Model.hovered_uid[idx];
            int x = uid & 0x7f;
            int y = uid >> 7 & 0x7f;
            int type = uid >> 29 & 3;
            int index = uid >> 14 & 0x7fff;

            if (uid == last_uid) {
                continue;
            }

            last_uid = uid;

            if (type == 2 && landscape.getArrangement(plane, x, y, uid) >= 0) {
                LocConfig lc = LocConfig.get(index);

                if (lc.overrideIndex != null) {
                    lc = lc.getOverridingConfig();
                }

                if (lc == null) {
                    continue;
                }

                if (selectedItem) {
                    Menu.add("Use " + selectedItemName + " with @cya@" + lc.name, 62, x, y, uid);
                } else if (selectedWidget) {
                    if ((selectedMask & 4) == 4) {
                        Menu.add(selectedTooltip + " @cya@" + lc.name, 956, x, y, uid);
                    }
                } else {
                    if (lc.action != null) {
                        for (int j = 4; j > -1; j--) {
                            if (lc.action[j] != null) {
                                Menu.add(lc.action[j] + " @cya@" + lc.name, Action.OBJECT[j], x, y, uid);
                            }
                        }
                    }
                    Menu.add("Examine @cya@" + lc.name, 1226, x, y, lc.index << 14);
                }
            }

            if (type == 1) {
                Actor a = actors[index];
                if (a.config.has_options == 1 && (a.scene_x & 0x7f) == 64 && (a.scene_y & 0x7f) == 64) {
                    for (int i = 0; i < actorCount; i++) {
                        Actor a1 = actors[actorIndices[i]];
                        if (a1 != null && a1 != a && a1.config.has_options == 1 && a1.scene_x == a.scene_x && a1.scene_y == a.scene_y) {
                            handle_actor_menu_options(a1.config, x, y, actorIndices[i]);
                        }
                    }

                    for (int i = 0; i < player_count; i++) {
                        Player p = players[playerIndices[i]];
                        if (p != null && p.scene_x == a.scene_x && p.scene_y == a.scene_y) {
                            handle_player_menu_options(p, x, y, playerIndices[i]);
                        }
                    }

                }
                handle_actor_menu_options(a.config, x, y, index);
            }

            if (type == 0) {
                Player p = players[index];
                if ((p.scene_x & 0x7f) == 64 && (p.scene_y & 0x7f) == 64) {
                    for (int k2 = 0; k2 < actorCount; k2++) {
                        Actor a = actors[actorIndices[k2]];
                        if (a != null && a.config.has_options == 1 && a.scene_x == p.scene_x && a.scene_y == p.scene_y) {
                            handle_actor_menu_options(a.config, x, y, actorIndices[k2]);
                        }
                    }

                    for (int i3 = 0; i3 < player_count; i3++) {
                        Player p1 = players[playerIndices[i3]];
                        if (p1 != null && p1 != p && p1.scene_x == p.scene_x && p1.scene_y == p.scene_y) {
                            handle_player_menu_options(p1, x, y, playerIndices[i3]);
                        }
                    }

                }
                handle_player_menu_options(p, x, y, index);
            }

            if (type == 3) {
                Chain c = Game.itemPile[plane][x][y];
                if (c != null) {
                    for (Item item = (Item) c.bottom(); item != null; item = (Item) c.previous()) {
                        ObjConfig oc = ObjConfig.get(item.index);
                        if (selectedItem) {
                            Menu.add("Use " + selectedItemName + " with @lre@" + oc.name, 511, x, y, item.index);
                        } else if (selectedWidget) {
                            if ((selectedMask & 1) == 1) {
                                Menu.add(selectedTooltip + " @lre@" + oc.name, 94, x, y, item.index);
                            }
                        } else {
                            for (int i = 4; i >= 0; i--) {
                                if (oc.ground_action != null && oc.ground_action[i] != null) {
                                    Menu.add(oc.ground_action[i] + " @lre@" + oc.name, Action.GROUND_ITEM[i], x, y, item.index);
                                } else if (i == 2) {
                                    Menu.add("Take @lre@" + oc.name, 234, x, y, item.index);
                                }
                            }
                            Menu.add("Examine @lre@" + oc.name, 1448, x, y, item.index);
                        }
                    }

                }
            }
        }
    }

    public static boolean handle_widget(Widget w) {
        int type = w.action_type;

        if (CharacterDesign.handle(w, type)) {
            return true;
        }

        if (frenemiesStatus == 2) {
            if (type == 201) {
                Chat.set(State.ADD_FRIEND, "Enter name of friend to add to list");
            }
            if (type == 202) {
                Chat.set(State.REMOVE_FRIEND, "Enter name of friend to delete from list");
            }
        }

        if (type == 205) {
            logoutCycle = 250;
            return true;
        }

        if (type == 501) {
            Chat.set(State.ADD_IGNORE, "Enter name of player to add to list");
        }

        if (type == 502) {
            Chat.set(State.REMOVE_IGNORE, "Enter name of player to delete from list");
        }

        if (type >= 601 && type <= 612) {
            close_widgets();
            if (reportAbuseInput.length() > 0) {
                out.writeOpcode(218);
                out.writeLong(JString.toLong(reportAbuseInput));
                out.writeByte(type - 601);
                out.writeByte(reportAbuseMute ? 1 : 0);
            }
        }
        return false;
    }

    public static void handle_widget_mouse() {
        if (dragArea != 0) {
            return;
        }

        int click_button = Mouse.clickButton;
        int menu_count = Menu.count;

        if (selectedWidget && Mouse.clickX >= 516 && Mouse.clickY >= 160 && Mouse.clickX <= 765 && Mouse.clickY <= 205) {
            click_button = 0;
        }

        if (Menu.visible) {
            if (click_button != 1) {
                Area area = Menu.area;

                int x = Mouse.lastX - area.x;
                int y = Mouse.lastY - area.y;
                int menu_x = Menu.x;
                int menu_y = Menu.y;
                int menu_w = Menu.width;
                int menu_h = Menu.height;

                if (x < menu_x - 10 || x > menu_x + menu_w + 10 || y < menu_y - 10 || y > menu_y + menu_h + 10) {
                    Menu.visible = false;
                    if (area == Area.TAB) {
                        Sidebar.draw = true;
                    } else if (area == Area.CHAT) {
                        Chat.redraw = true;
                    }
                }
            }

            if (click_button == 1) {
                Area area = Menu.area;

                int x = Menu.x;
                int y = Menu.y;
                int width = Menu.width;
                int click_x = Mouse.clickX - area.x;
                int click_y = Mouse.clickY - area.y;

                int active_option = -1;
                for (int option = 0; option < menu_count; option++) {
                    int option_y = y + 31 + (menu_count - 1 - option) * 15;
                    if (click_x > x && click_x < x + width && click_y > option_y - 13 && click_y < option_y + 3) {
                        active_option = option;
                    }
                }

                if (active_option != -1) {
                    handle_menu_option(active_option);
                }

                Menu.visible = false;

                if (area == Area.TAB) {
                    Sidebar.draw = true;
                } else if (area == Area.CHAT) {
                    Chat.redraw = true;
                }

                return;
            }
        } else {
            if (click_button == 1 && menu_count > 0) {
                int action = Menu.getLastAction();

                for (int i : Action.DRAG) {
                    if (i == action) {
                        int slot = Menu.getLastParam(0);
                        int index = Menu.getLastParam(1);
                        Widget w = Widget.instance[index];

                        if (w.items_draggable || w.items_swappable) {
                            dragging = false;
                            dragCycle = 0;
                            dragWidget = index;
                            dragSlot = slot;
                            dragArea = 2;
                            dragStartX = Mouse.clickX;
                            dragStartY = Mouse.clickY;

                            if (Widget.instance[index].parent == widgetOverlay) {
                                dragArea = 1;
                            }

                            if (Widget.instance[index].parent == Chat.get_overlay()) {
                                dragArea = 3;
                            }
                            return;
                        }

                        break;
                    }
                }
            }

            if (click_button == 1 && (mouseButtonSetting == 1) && menu_count > 2) {
                click_button = 2;
            }

            if (click_button == 1 && menu_count > 0) {
                handle_menu_option(menu_count - 1);
            }

            if (click_button == 2 && menu_count > 0) {
                Menu.show();
            }
        }
    }

    public static void handle_widget_mouse(Widget w, int screen_x, int screen_y, int mouse_x, int mouse_y, int scroll_amount) {
        if (w == null) {
            return;
        }

        if (w.type != 0 || w.children == null || w.hidden) {
            return;
        }

        if (!w.visible) {
            return;
        }

        if (mouse_x < screen_x || mouse_y < screen_y || mouse_x > screen_x + w.width || mouse_y > screen_y + w.height) {
            return;
        }

        for (int index = 0; index < w.children.length; index++) {
            int x = w.child_x[index] + screen_x;
            int y = (w.child_y[index] + screen_y) - scroll_amount;

            Widget child = Widget.instance[w.children[index]];

            if (!child.visible) {
                continue;
            }

            x += child.x;
            y += child.y;

            boolean hovered = false;

            if (mouse_x >= x && mouse_y >= y && mouse_x < x + child.width && mouse_y < y + child.height) {
                hovered = true;
            }

            if ((child.hover_index >= 0 || child.color_hover_disabled != 0) && hovered) {
                if (child.hover_index >= 0) {
                    tmpHoveredWidget = child.hover_index;
                } else {
                    tmpHoveredWidget = child.index;
                }
            }

            if (child.type == 0) {
                handle_widget_mouse(child, x, y, mouse_x, mouse_y, child.scroll_amount);

                if (child.scroll_height > child.height) {
                    handle_scrollbar(child, x + child.width, y, mouse_x, mouse_y, child.height, child.scroll_height, true);
                }
            } else {
                if (child.option_type == 1 && hovered) {
                    boolean no_options = false;

                    if (child.action_type != 0) {
                        no_options = frenemy_option_valid(child, false);
                    }

                    if (!no_options) {
                        Menu.add(child.option, 315, -1, child.index);
                    }
                }

                if (child.option_type == 2 && !selectedWidget && hovered) {
                    String s = child.option_prefix;
                    if (s.indexOf(' ') != -1) {
                        s = s.substring(0, s.indexOf(' '));
                    }
                    Menu.add(s + " @gre@" + child.option_suffix, 626, -1, child.index);
                }

                if (child.option_type == 3 && hovered) {
                    Menu.add("Close", 200, -1, child.index);
                }

                if (child.option_type == 4 && hovered) {
                    Menu.add(child.option, 169, -1, child.index);
                }

                if (child.option_type == 5 && hovered) {
                    Menu.add(child.option, 646, -1, child.index);
                }

                if (child.option_type == 6 && !dialogueOptionActive && hovered) {
                    Menu.add(child.option, 679, -1, child.index);
                }

                if (child.type == 2) {
                    int slot = 0;

                    for (int column = 0; column < child.height; column++) {
                        for (int row = 0; row < child.width; row++) {
                            int slot_x = x + row * (32 + child.item_margin_x);
                            int slot_y = y + column * (32 + child.item_margin_y);

                            if (slot < 20) {
                                slot_x += child.item_slot_x[slot];
                                slot_y += child.item_slot_y[slot];
                            }

                            if (mouse_x >= slot_x && mouse_y >= slot_y && mouse_x < slot_x + 32 && mouse_y < slot_y + 32) {
                                hoveredSlot = slot;
                                hoveredSlotWidget = child.index;

                                if (child.item_index[slot] > 0) {
                                    ObjConfig oc = ObjConfig.get(child.item_index[slot] - 1);

                                    if (oc == null) {
                                        Menu.add("Invalid Item", -1);
                                        continue;
                                    }

                                    if (selectedItem && child.items_have_actions) {
                                        if (child.index != selectedItemWidget || slot != selectedItemSlot) {
                                            Menu.add("Use " + selectedItemName + " with @lre@" + oc.name, 870, slot, child.index, oc.index);
                                        }
                                    } else if (selectedWidget && child.items_have_actions) {
                                        if ((selectedMask & 0x10) == 16) {
                                            Menu.add(selectedTooltip + " @lre@" + oc.name, 543, slot, child.index, oc.index);
                                        }
                                    } else {
                                        if (child.items_have_actions) {
                                            for (int i = 4; i >= 3; i--) {
                                                if (oc.action != null && oc.action[i] != null) {
                                                    Menu.add(oc.action[i] + " @lre@" + oc.name, Action.ITEM[i], slot, child.index, oc.index);
                                                } else if (i == 4) {
                                                    Menu.add("Drop @lre@" + oc.name, 847, slot, child.index, oc.index);
                                                }
                                            }
                                        }

                                        if (child.items_usable) {
                                            Menu.add("Use @lre@" + oc.name, 447, slot, child.index, oc.index);
                                        }

                                        if (child.items_have_actions && oc.action != null) {
                                            for (int i = 2; i >= 0; i--) {
                                                if (oc.action[i] != null) {
                                                    Menu.add(oc.action[i] + " @lre@" + oc.name, Action.ITEM[i], slot, child.index, oc.index);
                                                }
                                            }
                                        }

                                        if (child.item_actions != null) {
                                            for (int i = 4; i >= 0; i--) {
                                                if (child.item_actions[i] != null) {
                                                    Menu.add(child.item_actions[i] + " @lre@" + oc.name, Action.WIDGET_ITEM[i], slot, child.index, oc.index);
                                                }
                                            }
                                        }

                                        Menu.add("Examine @lre@" + oc.name, 1125, slot, child.index, oc.index);
                                    }
                                }
                            }
                            slot++;
                        }
                    }
                }
            }
        }
    }

    public static void ignore_add(long name_long) {
        if (name_long == 0L) {
            return;
        }

        if (ignoreCount >= 100) {
            Chat.put("Your ignore list is full. Max of 100 hit", 0);
            return;
        }

        String name = JString.getFormattedString(name_long);

        for (int i = 0; i < ignoreCount; i++) {
            if (ignoreLong[i] == name_long) {
                Chat.put(name + " is already on your ignore list", 0);
                return;
            }
        }

        for (int i = 0; i < friendCount; i++) {
            if (friendLong[i] == name_long) {
                Chat.put("Please remove " + name + " from your friend list first", 0);
                return;
            }
        }

        ignoreLong[ignoreCount++] = name_long;
        Sidebar.draw = true;
        out.writeOpcode(133);
        out.writeLong(name_long);
    }

    public static void ignore_remove(long name_long) {
        try {
            if (name_long == 0L) {
                return;
            }
            for (int j = 0; j < ignoreCount; j++) {
                if (ignoreLong[j] == name_long) {
                    ignoreCount--;
                    Sidebar.draw = true;
                    for (int k = j; k < ignoreCount; k++) {
                        ignoreLong[k] = ignoreLong[k + 1];
                    }
                    out.writeOpcode(74);
                    out.writeLong(name_long);
                    return;
                }
            }
            return;
        } catch (RuntimeException runtimeexception) {
            Signlink.error("47229, " + name_long + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public static boolean interact_with_loc(int x, int y, int uid) {
        int index = uid >> 14 & 0x7fff;
        int loc_info = landscape.getArrangement(plane, y, x, uid);

        if (loc_info == -1) {
            return false;
        }

        int type = loc_info & 0x1f;
        int rotation = loc_info >> 6 & 3;

        if (type == 10 || type == 11 || type == 22) {
            LocConfig lc = LocConfig.get(index);
            int size_x;
            int size_y;

            if (rotation == 0 || rotation == 2) {
                size_x = lc.sizeX;
                size_y = lc.sizeY;
            } else {
                size_x = lc.sizeY;
                size_y = lc.sizeX;
            }

            int face_flags = lc.faceFlags;

            if (rotation != 0) {
                face_flags = (face_flags << rotation & 0xf) + (face_flags >> 4 - rotation);
            }

            Game.walk_to(2, size_x, size_y, self.path_x[0], self.path_y[0], y, x, 0, face_flags, 0, false);
        } else {
            Game.walk_to(2, 0, 0, self.path_x[0], self.path_y[0], y, x, type + 1, 0, rotation, false);
        }

        crossX = Mouse.clickX;
        crossY = Mouse.clickY;
        crossType = 2;
        crossCycle = 0;
        return true;
    }

    public static void main(String[] args) {
        try {
            System.out.println("RS2 user client - release #317");
            for (int i = 0; i < args.length; i++) {
                String s = args[i].toLowerCase();

                switch (s) {
                    case "-cache": {
                        System.setProperty("rt317.cache", args[++i]);
                        break;
                    }
                    case "-debug": {
                        Game.debug = true;
                        break;
                    }
                    case "-node": {
                        try {
                            Game.nodeIndex = Integer.parseInt(args[++i]);
                        } catch (Exception e) {
                            /* empty */
                        }
                        break;
                    }
                    case "-offset": {
                        try {
                            Game.port_offset = Integer.parseInt(args[++i]);
                        } catch (Exception e) {
                            /* empty */
                        }
                        break;
                    }
                    case "-highmem": {
                        Game.lowDetail = false;
                        break;
                    }
                    case "-members": {
                        Game.isMembers = true;
                        break;
                    }
                    case "-alwaysontop": {
                        System.setProperty("rt317.alwaysontop", "1");
                        break;
                    }
                }
            }
            Signlink.start(InetAddress.getLocalHost());
            new Game().init(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void method891(boolean bool) {
        music.method853(0, null, bool);
    }

    public static void net_disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception _ex) {
        }

        connection = null;
        loggedIn = false;

        TitleScreen.state = TitleScreen.State.WELCOME;

        username = JString.BLANK;
        password = JString.BLANK;

        clear_caches();
        landscape.reset();

        for (int i = 0; i < 4; i++) {
            collisionMaps[i].defaults();
        }

        System.gc();

        music.stop();
        ondemand.send_request(2, 484);
    }

    public static void net_login(String username, String password, boolean reconnection) {
        Signlink.errorName = username;

        try {
            if (!reconnection) {
                TitleScreen.setMessage(JString.BLANK, JString.CONNECTING_TO_SERVER);
                TitleScreen.draw(true);
            }

            Game.connection = new Connection(instance, instance.getSocket(43594 + port_offset));

            long name_long = JString.toLong(username);
            int name_hash = (int) (name_long >> 16 & 0x1FL);

            out.position = 0;
            out.writeByte(14);
            out.writeByte(name_hash);

            Game.connection.putBytes(out.payload, 0, 2);

            for (int i = 0; i < 8; i++) {
                Game.connection.getByte();
            }

            int opcode = Game.connection.getByte();
            int first_opcode = opcode;

            if (opcode == 0) {
                Game.connection.getBytes(in.payload, 0, 8);
                in.position = 0;

                Game.serverSeed = in.readLong();

                int seed[] = new int[4];
                seed[0] = (int) (Math.random() * 99999999D);
                seed[1] = (int) (Math.random() * 99999999D);
                seed[2] = (int) (serverSeed >> 32);
                seed[3] = (int) serverSeed;

                out.position = 0;
                out.writeByte(10);
                out.writeInt(seed[0]);
                out.writeInt(seed[1]);
                out.writeInt(seed[2]);
                out.writeInt(seed[3]);
                out.writeInt(Signlink.uid);
                out.writeString(username);
                out.writeString(password);
                out.encrypt(rsaPublicKey, rsaModulus);

                loginBuffer.position = 0;
                loginBuffer.writeByte(reconnection ? 18 : 16);
                loginBuffer.writeByte(out.position + 36 + 1 + 1 + 2);
                loginBuffer.writeByte(255);
                loginBuffer.writeShort(317);
                loginBuffer.writeByte(lowDetail ? 1 : 0);

                for (int l1 = 0; l1 < 9; l1++) {
                    loginBuffer.writeInt(archiveCrc[l1]);
                }

                loginBuffer.writeBytes(out.payload, out.position, 0);
                out.isaac = new IsaacCipher(seed);

                for (int i = 0; i < 4; i++) {
                    seed[i] += 50;
                }

                connectionIsaac = new IsaacCipher(seed);
                connection.putBytes(loginBuffer.payload, 0, loginBuffer.position);
                opcode = connection.getByte();
            }
            if (opcode == 1) {
                try {
                    Thread.sleep(2000L);
                } catch (Exception _ex) {
					/* empty */
                }
                net_login(username, password, reconnection);
                return;
            }
            if (opcode == 2) {
                Game.localRights = connection.getByte();
                Game.recordMouse = connection.getByte() == 1;
                Game.lastClickTime = 0L;
                Game.mouseRecorder.cycle = 0;
                Game.mouseRecorder.off = 0;
                Game.instance.focused = true;
                Game.isFocused = true;
                Game.loggedIn = true;

                // Net
                Game.out.position = 0;
                Game.in.position = 0;
                Game.ptype = -1;
                Game.lastPtype1 = -1;
                Game.lastPtype2 = -1;
                Game.lastPtype3 = -1;
                Game.psize = 0;
                Game.netCycle = 0;
                Game.nextUpdate = 0;
                Game.logoutCycle = 0;
                Game.markType = 0;
                Menu.reset();
                Menu.visible = false;
                Game.instance.idleCycle = 0;
                Chat.reset();
                Game.selectedItem = false;
                Game.selectedWidget = false;
                Game.sceneState = 0;
                Game.soundCount = 0;

                Game.cam_x_off = (int) (Math.random() * 100D) - 50;
                Game.cam_y_off = (int) (Math.random() * 110D) - 55;
                Game.cam_pitch_off = (int) (Math.random() * 80D) - 40;
                Game.cam_yaw_off = (int) (Math.random() * 120D) - 60;
                Game.mapZoomOffset = (int) (Math.random() * 30D) - 20;
                Game.chaseCamYaw = (int) (Math.random() * 20D) - 10 & 0x7ff;

                Game.minimapState = 0;
                Game.lastPlane = -1;
                Game.mapMarkerX = 0;
                Game.mapMarkerY = 0;
                Game.player_count = 0;
                Game.actorCount = 0;

                for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
                    Game.players[i] = null;
                    Game.playerBuffer[i] = null;
                }

                for (int i = 0; i < 16384; i++) {
                    Game.actors[i] = null;
                }

                Game.self = Game.players[MAX_PLAYER_INDEX] = new Player();
                Game.projectiles.clear();
                Game.spotanims.clear();

                for (int plane = 0; plane < 4; plane++) {
                    for (int x = 0; x < 104; x++) {
                        for (int y = 0; y < 104; y++) {
                            Game.itemPile[plane][x][y] = null;
                        }
                    }
                }

                Game.spawnedLocs = new Chain();
                Game.frenemiesStatus = 0;
                Game.friendCount = 0;
                Game.widgetOverlay = -1;
                Sidebar.widgetIndex = -1;
                Sidebar.openTab(3);
                Sidebar.flashingTab = null;
                Game.widgetUnderlay = -1;
                Game.dialogueOptionActive = false;
                Menu.visible = false;
                Game.inMultiZone = 0;
                CharacterDesign.reset();

                for (int i = 0; i < 5; i++) {
                    Game.playerAction[i] = null;
                    Game.playerActionPriority[i] = false;
                }

                create_ingame_producers();
                return;
            }

            if (opcode == 3) {
                TitleScreen.setMessage(JString.BLANK, JString.INVALID_CREDENTIALS);
                return;
            }

            if (opcode == 4) {
                TitleScreen.setMessage(JString.ACCOUNT_DISABLED, JString.CHECK_MESSAGES);
                return;
            }

            if (opcode == 5) {
                TitleScreen.setMessage(JString.ALREADY_LOGGED_IN, JString.TRY_AGAIN_IN_60);
                return;
            }

            if (opcode == 6) {
                TitleScreen.setMessage(JString.UPDATED, JString.RELOAD_PAGE);
                return;
            }

            if (opcode == 7) {
                TitleScreen.setMessage(JString.WORLD_FULL, JString.DIFFERENT_WORLD);
                return;
            }

            if (opcode == 8) {
                TitleScreen.setMessage(JString.UNABLE_TO_CONNECT, JString.SERVER_OFFLINE);
                return;
            }

            if (opcode == 9) {
                TitleScreen.setMessage(JString.LOGIN_LIMIT, JString.TOO_MANY_CONNECTIONS);
                return;
            }

            if (opcode == 10) {
                TitleScreen.setMessage(JString.UNABLE_TO_CONNECT, JString.BAD_SESSION);
                return;
            }

            if (opcode == 11) {
                TitleScreen.setMessage(JString.REJECTED_SESSION, JString.TRY_AGAIN);
                return;
            }

            if (opcode == 12) {
                TitleScreen.setMessage(JString.NEED_MEMBERS, JString.PLEASE_SUBSCRIBE);
                return;
            }

            if (opcode == 13) {
                TitleScreen.setMessage(JString.COULDNT_LOGIN, JString.USE_DIFFERENT_WORLD);
                return;
            }

            if (opcode == 14) {
                TitleScreen.setMessage(JString.SERVER_UPDATING, JString.WAIT_1_MINUTE);
                return;
            }

            if (opcode == 15) {
                Game.loggedIn = true;
                Game.out.position = 0;
                Game.in.position = 0;
                Game.ptype = -1;
                Game.lastPtype1 = -1;
                Game.lastPtype2 = -1;
                Game.lastPtype3 = -1;
                Game.psize = 0;
                Game.netCycle = 0;
                Game.nextUpdate = 0;
                Menu.reset();
                Menu.visible = false;
                Game.sceneLoadStart = System.currentTimeMillis();
                return;
            }

            if (opcode == 16) {
                TitleScreen.setMessage(JString.LOGIN_EXCEEDED, JString.WAIT_1_MINUTE);
                return;
            }

            if (opcode == 17) {
                TitleScreen.setMessage(JString.WITHIN_MEMBERS, JString.MOVE_TO_FREE);
                return;
            }

            if (opcode == 20) {
                TitleScreen.setMessage(JString.INVALID_SERVER, JString.DIFFERENT_WORLD);
                return;
            }
            if (opcode == 21) {
                for (int k1 = connection.getByte(); k1 >= 0; k1--) {
                    TitleScreen.setMessage(JString.JUST_LEFT_WORLD, JString.TRANSFERRED + k1 + " seconds");
                    TitleScreen.draw(true);
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception _ex) {
                    }
                }

                net_login(username, password, reconnection);
                return;
            }
            if (opcode == -1) {
                if (first_opcode == 0) {
                    if (reconnectionAttempts < 2) {
                        try {
                            Thread.sleep(2000L);
                        } catch (Exception _ex) {
                        }
                        reconnectionAttempts++;
                        net_login(username, password, reconnection);
                        return;
                    } else {
                        TitleScreen.setMessage(JString.NO_RESPONSE, JString.WAIT_1_MINUTE);
                        return;
                    }
                } else {
                    TitleScreen.setMessage(JString.NO_RESPONSE, JString.DIFFERENT_WORLD);
                    return;
                }
            } else {
                System.out.println("response:" + opcode);
                TitleScreen.setMessage(JString.UNEXPECTED_RESPONSE, JString.DIFFERENT_WORLD);
                return;
            }
        } catch (IOException _ex) {
            TitleScreen.setMessage(JString.BLANK, JString.ERROR_CONNECTING);
        }
    }

    public static synchronized void play_music(int fileVar1, int loop1, int volume2, boolean loopM1, int fileVar3, int fileVar2) {
        music.loop1 = loop1;
        music.loop2 = -1;
        music.fvar1 = fileVar1;
        music.fvar2 = fileVar2;
        music.fvar3 = fileVar3;
        music.loopmusic1 = loopM1;
        music.volume2 = volume2;
        music.fetch = true;
    }

    public static synchronized void play_music_instantly(int volume2, int fileVar2, int fileVar1, int fileVar3, boolean loopMusic1) {
        music.fetch = true;
        music.volume2 = volume2;
        music.fvar1 = fileVar1;
        music.fvar2 = fileVar2;
        music.fvar3 = fileVar3;
        music.loop1 = -1;
        music.loop2 = -1;
        music.loopmusic1 = loopMusic1;
    }

    public static void retrieve_scene(boolean flag) {
        try {
            lastPlane = -1;
            spotanims.clear();
            projectiles.clear();
            Canvas3D.clear_textures();
            clear_caches();
            landscape.reset();
            System.gc();

            for (int i = 0; i < 4; i++) {
                collisionMaps[i].defaults();
            }

            for (int z = 0; z < 4; z++) {
                for (int x = 0; x < 104; x++) {
                    for (int y = 0; y < 104; y++) {
                        render_flags[z][x][y] = 0;
                    }
                }
            }

            scene = new Scene(104, 104, heightMap, render_flags);
            int length = chunkLocPayload.length;
            out.writeOpcode(0);

            if (!serverSentChunk) {
                for (int chunk = 0; chunk < length; chunk++) {
                    int chunk_x = (chunkCoords[chunk] >> 8) * 64 - mapBaseX;
                    int chunk_y = (chunkCoords[chunk] & 0xff) * 64 - mapBaseY;
                    byte payload[] = chunkLocPayload[chunk];
                    if (payload != null) {
                        scene.loadLand(collisionMaps, payload, chunk_x, chunk_y, (loadedRegionX - 6) * 8, (loadedRegionY - 6) * 8);
                    }
                }

                for (int chunk = 0; chunk < length; chunk++) {
                    int chunk_x = (chunkCoords[chunk] >> 8) * 64 - mapBaseX;
                    int chunk_y = (chunkCoords[chunk] & 0xff) * 64 - mapBaseY;
                    if (chunkLocPayload[chunk] == null && loadedRegionY < 800) {
                        scene.fitEdges(chunk_x, chunk_y, 64, 64);
                    }
                }

                out.writeOpcode(0);

                for (int chunk = 0; chunk < length; chunk++) {
                    byte payload[] = chunkLandscapePayload[chunk];
                    if (payload != null) {
                        int region_x = (chunkCoords[chunk] >> 8) * 64 - mapBaseX;
                        int region_y = (chunkCoords[chunk] & 0xff) * 64 - mapBaseY;
                        scene.loadLocs(region_x, collisionMaps, region_y, landscape, payload);
                    }
                }
            } else {
                for (int map_plane = 0; map_plane < 4; map_plane++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int region_uid = regionChunkUniqueIds[map_plane][x][y];
                            if (region_uid != -1) {
                                int chunk_plane = region_uid >> 24 & 3;
                                int chunk_rot = region_uid >> 1 & 3;
                                int chunk_x = region_uid >> 14 & 0x3ff;
                                int chunk_y = region_uid >> 3 & 0x7ff;
                                int chunk_uid = (chunk_x / 8 << 8) + chunk_y / 8;

                                for (int chunk = 0; chunk < chunkCoords.length; chunk++) {
                                    if (chunkCoords[chunk] != chunk_uid || chunkLocPayload[chunk] == null) {
                                        continue;
                                    }
                                    scene.loadChunk(collisionMaps, x * 8, y * 8, (chunk_x & 7) * 8, (chunk_y & 7) * 8, chunk_plane, chunkLocPayload[chunk], chunk_rot, map_plane);
                                    break;
                                }
                            }
                        }
                    }
                }

                for (int x = 0; x < 13; x++) {
                    for (int y = 0; y < 13; y++) {
                        int region_uid = regionChunkUniqueIds[0][x][y];
                        if (region_uid == -1) {
                            scene.fitEdges(x * 8, y * 8, 8, 8);
                        }
                    }

                }

                out.writeOpcode(0);

                for (int map_plane = 0; map_plane < 4; map_plane++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int chunk_uid = regionChunkUniqueIds[map_plane][x][y];
                            if (chunk_uid != -1) {
                                int chunk_plane = chunk_uid >> 24 & 3;
                                int chunk_rot = chunk_uid >> 1 & 3;
                                int chunk_x = chunk_uid >> 14 & 0x3ff;
                                int chunk_y = chunk_uid >> 3 & 0x7ff;
                                int chunk_coord = (chunk_x / 8 << 8) + chunk_y / 8;
                                for (int chunk = 0; chunk < chunkCoords.length; chunk++) {
                                    if (chunkCoords[chunk] != chunk_coord || chunkLandscapePayload[chunk] == null) {
                                        continue;
                                    }
                                    scene.loadLocs(collisionMaps, landscape, x * 8, y * 8, (chunk_x & 7) * 8, (chunk_y & 7) * 8, chunk_plane, map_plane, chunkLandscapePayload[chunk], chunk_rot);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            out.writeOpcode(0);
            scene.create_land_mesh(collisionMaps, landscape);
            producerScene.prepare();
            out.writeOpcode(0);

            if (lowDetail) {
                landscape.setPlane(Scene.minPlane);
            } else {
                landscape.setPlane(0);
            }

            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    update_item_pile(x, y);
                }
            }

            handle_locs();
        } catch (Exception exception) {
        }

        LocConfig.staticModelCache.clear();

        if (lowDetail && Signlink.cacheFile != null) {
            int mesh_count = ondemand.get_file_count(0);
            for (int mesh_index = 0; mesh_index < mesh_count; mesh_index++) {
                int flags = ondemand.mesh_flags(mesh_index);
                if ((flags & 0x79) == 0) {
                    Model.nullify(mesh_index);
                }
            }

        }

        System.gc();
        Canvas3D.setup_texel_pools();
        ondemand.clear_passive_requests();

        int chunk_west = (loadedRegionX - 6) / 8 - 1;
        int chunk_east = (loadedRegionX + 6) / 8 + 1;
        int chunk_north = (loadedRegionY - 6) / 8 - 1;
        int chunk_south = (loadedRegionY + 6) / 8 + 1;

        if (restrictRegion) {
            chunk_west = 49;
            chunk_east = 50;
            chunk_north = 49;
            chunk_south = 50;
        }

        for (int chunk_x = chunk_west; chunk_x <= chunk_east; chunk_x++) {
            for (int chunk_y = chunk_north; chunk_y <= chunk_south; chunk_y++) {
                if (chunk_x == chunk_west || chunk_x == chunk_east || chunk_y == chunk_north || chunk_y == chunk_south) {
                    int map_uid = ondemand.get_map_uid(chunk_x, chunk_y, 0);
                    if (map_uid != -1) {
                        ondemand.request(map_uid, 3);
                    }
                    int landscape_uid = ondemand.get_map_uid(chunk_x, chunk_y, 1);
                    if (landscape_uid != -1) {
                        ondemand.request(landscape_uid, 3);
                    }
                }
            }
        }
    }

    public static void scroll_textures(int cycle) {
        if (!lowDetail) {
            for (byte i : ANIMATED_TEXTURES) {
                if (Canvas3D.textureCycle[i] >= cycle) {
                    Bitmap b = Canvas3D.texture[i];
                    int len = (b.width * b.height) - 1;
                    int off = (b.width * animCycle * 2);
                    byte[] pixels = b.pixels;
                    byte[] texels = tmpTexels;

                    for (int j = 0; j <= len; j++) {
                        texels[j] = pixels[j - off & len];
                    }

                    b.pixels = texels;
                    tmpTexels = pixels;
                    Canvas3D.update_texture(i);
                }
            }
        }
    }

    public static void set_draw_xy(Entity e, int offset_z) {
        set_draw_xy(e.scene_x, e.scene_y, offset_z);
    }

    public static void set_draw_xy(int x, int y, int offset_z) {
        if (x < 128 || y < 128 || x > 13056 || y > 13056) {
            drawX = -1;
            drawY = -1;
            return;
        }

        int z = get_land_z(x, y, plane) - offset_z;
        x -= Camera.x;
        z -= Camera.z;
        y -= Camera.y;

        int pitch_sin = Model.sin[Camera.pitch];
        int pitch_cos = Model.cos[Camera.pitch];
        int yaw_sin = Model.sin[Camera.yaw];
        int yaw_cos = Model.cos[Camera.yaw];

        int i = y * yaw_sin + x * yaw_cos >> 16;
        y = y * yaw_cos - x * yaw_sin >> 16;
        x = i;

        i = z * pitch_cos - y * pitch_sin >> 16;
        y = z * pitch_sin + y * pitch_cos >> 16;
        z = i;

        if (y >= 50) {
            drawX = Canvas3D.centerX + (x << 9) / y;
            drawY = Canvas3D.centerY + (z << 9) / y;
            return;
        }

        drawX = -1;
        drawY = -1;
    }

    public static void set_music_volume(int volume) {
        if (music.fetch) {
            music.volume2 = volume;
        } else {
            if (music.var2 == 0) {
                if (music.var1 >= 0) {
                    music.var1 = volume;
                    music.setVolumeVelocity(volume, 0);
                }
            } else if (music.buffer != null) {
                music.volume3 = volume;
            }
        }
    }

    public static void spawn_loc(int loc_index, int loc_x, int loc_y, int loc_z, int loc_type, int loc_rotation, int cycle, int spawn_cycle, int class_type) {
        SpawntLoc sl = null;

        for (SpawntLoc _sl = (SpawntLoc) spawnedLocs.top(); _sl != null; _sl = (SpawntLoc) spawnedLocs.next()) {
            if (_sl.plane != loc_z || _sl.x != loc_x || _sl.y != loc_y || _sl.classType != class_type) {
                continue;
            }
            sl = _sl;
            break;
        }

        if (sl == null) {
            sl = new SpawntLoc();
            sl.plane = loc_z;
            sl.classType = class_type;
            sl.x = loc_x;
            sl.y = loc_y;
            handle_loc(sl);
            spawnedLocs.pushBack(sl);
        }

        sl.locIndex = loc_index;
        sl.locType = loc_type;
        sl.locRotation = loc_rotation;
        sl.spawnCycle = spawn_cycle;
        sl.cycle = cycle;
    }

    public static synchronized void stop_music(boolean loop) {
        method891(loop);
        music.fetch = false;
    }

    public static int[] transform(int x, int y, int z) {
        x -= Camera.x;
        y -= Camera.y;
        z -= Camera.z;

        int pitch_sin = Model.sin[Camera.pitch];
        int pitch_cos = Model.cos[Camera.pitch];
        int yaw_sin = Model.sin[Camera.yaw];
        int yaw_cos = Model.cos[Camera.yaw];

        int i = y * yaw_sin + x * yaw_cos >> 16;
        y = y * yaw_cos - x * yaw_sin >> 16;
        x = i;

        i = z * pitch_cos - y * pitch_sin >> 16;
        y = z * pitch_sin + y * pitch_cos >> 16;
        z = i;

        return new int[]{x, y, z, Canvas3D.centerX + (x << 9) / y, Canvas3D.centerY + (z << 9) / y};
    }

    public static void update_actor_masks(Buffer b) {
        for (int i = 0; i < entityCount; i++) {
            Actor a = actors[entityIndex[i]];
            int mask = b.readUnsignedByte();

            if ((mask & 0x10) != 0) { // Animation
                int seq_index = b.readUnsignedLeShort();

                if (seq_index == 65535) {
                    seq_index = -1;
                }

                int delay = b.readUnsignedByte();

                if (seq_index == a.seq_index && seq_index != -1) {
                    int type = Sequence.instance[seq_index].type;
                    if (type == 1) {
                        a.seq_frame = 0;
                        a.seq_cycle = 0;
                        a.seq_delay_cycle = delay;
                        a.seq_reset_cycle = 0;
                    }
                    if (type == 2) {
                        a.seq_reset_cycle = 0;
                    }
                } else if (seq_index == -1 || a.seq_index == -1 || Sequence.instance[seq_index].priority >= Sequence.instance[a.seq_index].priority) {
                    a.seq_index = seq_index;
                    a.seq_frame = 0;
                    a.seq_cycle = 0;
                    a.seq_delay_cycle = delay;
                    a.seq_reset_cycle = 0;
                    a.still_path_position = a.path_position;
                }
            }

            if ((mask & 8) != 0) { // Hit
                int damage = b.readUnsignedByteA();
                int type = b.readUnsignedByteC();
                a.hit(type, damage, loopCycle);
                a.combat_cycle = loopCycle + 300;
                a.current_health = b.readUnsignedByteA();
                a.max_health = b.readUnsignedByte();
            }

            if ((mask & 0x80) != 0) { // Graphics
                a.spotanimIndex = b.readUnsignedShort();

                int info = b.readInt();
                a.graphic_offset_y = info >> 16;
                a.spotanim_cycle_end = loopCycle + (info & 0xFFFF);

                a.spotanimFrame = 0;
                a.spotanim_cycle = 0;

                if (a.spotanim_cycle_end > loopCycle) {
                    a.spotanimFrame = -1;
                }

                if (a.spotanimIndex == 65535) {
                    a.spotanimIndex = -1;
                }
            }

            if ((mask & 0x20) != 0) { // Face Entity
                a.face_entity = b.readUnsignedShort();

                if (a.face_entity == 65535) {
                    a.face_entity = -1;
                }
            }

            if ((mask & 1) != 0) { // Forced Chat
                a.spoken_message = b.readString();
                a.spoken_life = 100;
            }

            if ((mask & 0x40) != 0) { // Hit 2
                int damage = b.readUnsignedByteC();
                int type = b.readUnsignedByteS();
                a.hit(type, damage, loopCycle);
                a.combat_cycle = loopCycle + 300;
                a.current_health = b.readUnsignedByteS();
                a.max_health = b.readUnsignedByteC();
            }

            if ((mask & 2) != 0) { // Transform
                a.config = ActorConfig.get(b.readUnsignedLeShortA());
                a.size = a.config.has_options;
                a.turn_speed = a.config.turn_speed;
                a.walk_animation = a.config.move_seq;
                a.turn_180_animation = a.config.turn_180_seq;
                a.turn_r_animation = a.config.turn_r_seq;
                a.turn_l_animation = a.config.turn_l_seq;
                a.stand_animation = a.config.stand_sequence;
            }

            if ((mask & 4) != 0) { // Face Coordinate
                a.face_x = b.readUnsignedLeShort();
                a.face_y = b.readUnsignedLeShort();
            }
        }
    }

    public static void update_actor_movement(Buffer b) {
        b.startBitAccess();
        int actor_count = b.readBits(8);

        if (actor_count < Game.actorCount) {
            for (int l = actor_count; l < Game.actorCount; l++) {
                entityUpdateIndices[entityUpdateCount++] = actorIndices[l];
            }
        }

        if (actor_count > Game.actorCount) {
            Signlink.error(username + " Too many npcs");
            throw new RuntimeException(JString.EEK);
        }

        Game.actorCount = 0;

        for (int i = 0; i < actor_count; i++) {
            int actor_index = actorIndices[i];
            Actor a = actors[actor_index];
            int movement_update = b.readBits(1);

            if (movement_update == 0) {
                actorIndices[Game.actorCount++] = actor_index;
                a.update_cycle = loopCycle;
            } else {
                int move_type = b.readBits(2);

                switch (move_type) {
                    case 0: {// No Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.update_cycle = loopCycle;
                        entityIndex[entityCount++] = actor_index;
                        break;
                    }
                    case 1: {// 1 Tile Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.update_cycle = loopCycle;
                        a.move(b.readBits(3), false);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = actor_index;
                        }
                        break;
                    }
                    case 2: {// 2 Tile Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.update_cycle = loopCycle;
                        a.move(b.readBits(3), true);
                        a.move(b.readBits(3), true);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = actor_index;
                        }
                        break;
                    }
                    case 3: {// Unknown
                        entityUpdateIndices[entityUpdateCount++] = actor_index;
                        break;
                    }
                }
            }
        }
    }

    public static void update_actors(int psize, Buffer b) {
        entityUpdateCount = 0;
        entityCount = 0;
        update_actor_movement(b);
        handle_new_actors(psize, b);
        update_actor_masks(b);

        for (int i = 0; i < entityUpdateCount; i++) {
            int actor_index = entityUpdateIndices[i];

            if (actors[actor_index].update_cycle != loopCycle) {
                actors[actor_index].config = null;
                actors[actor_index] = null;
            }
        }

        if (b.position != psize) {
            Signlink.error(username + " size mismatch in getactorpos - pos:" + b.position + " psize:" + psize);
            throw new RuntimeException(JString.EEK);
        }

        for (int i = 0; i < actorCount; i++) {
            if (actors[actorIndices[i]] == null) {
                Signlink.error(username + " null entry in actor list - pos:" + i + " size:" + actorCount);
                throw new RuntimeException(JString.EEK);
            }
        }
    }

    public static void update_item_pile(int x, int y) {
        Chain c = Game.itemPile[plane][x][y];

        if (c == null) {
            landscape.removeItemPile(plane, x, y);
            return;
        }

        int b = 0xFA0A1F01;

        Item top = null;
        Item middle = null;
        Item bottom = null;

        for (Item i = (Item) c.top(); i != null; i = (Item) c.next()) {
            ObjConfig oc = ObjConfig.get(i.index);
            int a = oc.pile_priority;

            if (oc.stackable) {
                a *= i.stack_index + 1;
            }

            if (a > b) {
                b = a;
                top = i;
            }
        }

        c.push(top);

        for (Item item = (Item) c.top(); item != null; item = (Item) c.next()) {
            if (item.index != top.index && bottom == null) {
                bottom = item;
            }
            if (item.index != top.index && item.index != bottom.index && middle == null) {
                middle = item;
            }
        }

        int uid = x + (y << 7) + 0x60000000;
        landscape.addItemPile(x, y, get_land_z(x * 128 + 64, y * 128 + 64, plane), plane, top, middle, bottom, uid);
    }

    public static void update_localplayer_movement(Buffer b) {
        b.startBitAccess();

        if (b.readBits(1) == 0) {
            return;
        }

        int move_type = b.readBits(2);

        if (move_type == 0) {
            entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            return;
        }

        if (move_type == 1) { // Walk
            int direction = b.readBits(3);
            self.move(direction, false);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }
            return;
        }

        if (move_type == 2) { // Run
            self.move(b.readBits(3), true);
            self.move(b.readBits(3), true);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }
            return;
        }

        if (move_type == 3) { // Teleport
            plane = b.readBits(2);
            int discard_move_queue = b.readBits(1);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }

            int y = b.readBits(7);
            int x = b.readBits(7);
            self.move_to(x, y, discard_move_queue == 1);
        }
    }

    public static void update_new_players(Buffer b, int position) {
        while (b.bitPosition + 10 < position * 8) {
            int player_index = b.readBits(11);

            if (player_index == 2047) {
                break;
            }

            if (players[player_index] == null) {
                players[player_index] = new Player();
                if (playerBuffer[player_index] != null) {
                    players[player_index].update(playerBuffer[player_index]);
                }
            }

            playerIndices[player_count++] = player_index;
            Player p = players[player_index];
            p.update_cycle = loopCycle;

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = player_index;
            }

            int discard_walk_queue = b.readBits(1);
            int x = b.readBits(5);
            int y = b.readBits(5);

            if (x > 15) {
                x -= 32;
            }

            if (y > 15) {
                y -= 32;
            }

            p.move_to(self.path_x[0] + y, self.path_y[0] + x, discard_walk_queue == 1);
        }
        b.stopBitAccess();
    }

    public static void update_player_mask(int mask, int index, Buffer b, Player p) {
        if ((mask & 0x400) != 0) {
            p.move_start_x = b.readUnsignedByteS();
            p.move_start_y = b.readUnsignedByteS();
            p.move_end_x = b.readUnsignedByteS();
            p.move_end_y = b.readUnsignedByteS();
            p.move_cycle_end = b.readUnsignedLeShortA() + loopCycle;
            p.move_cycle_start = b.writeUnsignedShortA() + loopCycle;
            p.move_direction = b.readUnsignedByteS();
            p.reset_positions();
        }

        if ((mask & 0x100) != 0) { // Graphics
            p.spotanimIndex = b.readUnsignedLeShort();
            int info = b.readInt();
            p.graphic_offset_y = info >> 16;
            p.spotanim_cycle_end = loopCycle + (info & 0xffff);
            p.spotanimFrame = 0;
            p.spotanim_cycle = 0;

            if (p.spotanim_cycle_end > loopCycle) {
                p.spotanimFrame = -1;
            }

            if (p.spotanimIndex == 65535) {
                p.spotanimIndex = -1;
            }
        }

        if ((mask & 8) != 0) { // Sequence
            int sequence_index = b.readUnsignedLeShort();
            int delay = b.readUnsignedByteC();

            System.out.println("sequence: " + sequence_index);

            if (sequence_index == 65535) {
                sequence_index = -1;
            }

            if (sequence_index == p.seq_index && sequence_index != -1) {
                int type = Sequence.instance[sequence_index].type;
                if (type == 1) {
                    p.seq_frame = 0;
                    p.seq_cycle = 0;
                    p.seq_delay_cycle = delay;
                    p.seq_reset_cycle = 0;
                } else if (type == 2) {
                    p.seq_reset_cycle = 0;
                }
            } else if (sequence_index == -1 || p.seq_index == -1 || Sequence.instance[sequence_index].priority >= Sequence.instance[p.seq_index].priority) {
                p.seq_index = sequence_index;
                p.seq_frame = 0;
                p.seq_cycle = 0;
                p.seq_delay_cycle = delay;
                p.seq_reset_cycle = 0;
                p.still_path_position = p.path_position;
            }
        }

        if ((mask & 4) != 0) { // Forced Chat
            p.spoken_message = b.readString();

            if (p.spoken_message.charAt(0) == '~') {
                p.spoken_message = p.spoken_message.substring(1);
                Chat.put(p.name, p.spoken_message, 2);
            } else if (p == self) {
                Chat.put(p.name, p.spoken_message, 2);
            }

            p.spoken_color = 0;
            p.spoken_effect = 0;
            p.spoken_life = 150;
        }

        if ((mask & 0x80) != 0) { // Chat
            int settings = b.readUnsignedLeShort();
            int rights = b.readUnsignedByte();
            int length = b.readUnsignedByteC();
            int start_off = b.position;

            if (p.name != null && p.visible) {
                long name_long = JString.toLong(p.name);

                boolean ignored = false;
                if (rights <= 1) {
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != name_long) {
                            continue;
                        }
                        ignored = true;
                        break;
                    }
                }

                if (!ignored && messageStatus == 0) {
                    try {
                        Chat.buffer.position = 0;
                        b.readBytesReversed(Chat.buffer.payload, 0, length);
                        Chat.buffer.position = 0;

                        p.spoken_message = JString.getFormatted(length, Chat.buffer);
                        p.spoken_effect = settings & 0xFF;
                        p.spoken_color = settings >> 8;
                        p.spoken_life = 150;

                        StringBuilder sb = new StringBuilder();

                        if (rights > 0) {
                            sb.append("@cr").append(rights).append("@");
                        }

                        sb.append(p.name);

                        Chat.put(sb.toString(), p.spoken_message, rights == 0 ? Chat.TYPE_PLAYER : Chat.TYPE_MODERATOR);
                    } catch (Exception e) {
                        Signlink.error("cde2");
                    }
                }

            }
            b.position = start_off + length;
        }

        if ((mask & 1) != 0) { // Face Entity
            p.face_entity = b.readUnsignedLeShort();

            if (p.face_entity == 65535) {
                p.face_entity = -1;
            }
        }

        if ((mask & 0x10) != 0) { // Update Appearance
            byte[] payload = new byte[b.readUnsignedByteC()];
            b.read(payload, 0, payload.length);
            Buffer pb = new Buffer(payload);
            playerBuffer[index] = pb;
            p.update(pb);
        }

        if ((mask & 2) != 0) { // Face Coordinates
            p.face_x = b.readUnsignedLeShortA();
            p.face_y = b.readUnsignedLeShort();
        }

        if ((mask & 0x20) != 0) { // Damage Update
            int damage = b.readUnsignedByte();
            int type = b.readUnsignedByteA();
            p.hit(type, damage, loopCycle);
            p.combat_cycle = loopCycle + 300;
            p.current_health = b.readUnsignedByteC();
            p.max_health = b.readUnsignedByte();
        }

        if ((mask & 0x200) != 0) { // Damage Update 2
            int damage = b.readUnsignedByte();
            int type = b.readUnsignedByteS();
            p.hit(type, damage, loopCycle);
            p.combat_cycle = loopCycle + 300;
            p.current_health = b.readUnsignedByte();
            p.max_health = b.readUnsignedByteC();
        }
    }

    public static void update_player_masks(Buffer b) {
        for (int i = 0; i < entityCount; i++) {
            int index = entityIndex[i];
            Player p = players[index];

            int mask = b.readUnsignedByte();

            if ((mask & 0x40) != 0) {
                mask += b.readUnsignedByte() << 8;
            }

            update_player_mask(mask, index, b, p);
        }
    }

    public static void update_player_movement(Buffer b) {
        int player_count = b.readBits(8);

        if (player_count < Game.player_count) {
            for (int k = player_count; k < Game.player_count; k++) {
                entityUpdateIndices[entityUpdateCount++] = playerIndices[k];
            }
        }

        if (player_count > Game.player_count) {
            Signlink.error(username + " Too many players");
            throw new RuntimeException(JString.EEK);
        }

        Game.player_count = 0;

        for (int i = 0; i < player_count; i++) {
            int player_index = playerIndices[i];
            Player p = players[player_index];

            // If we don't need a movement update
            if (b.readBits(1) == 0) {
                playerIndices[Game.player_count++] = player_index;
                p.update_cycle = loopCycle;
            } else {
                int move_type = b.readBits(2);

                switch (move_type) {
                    case 0: { // No Movement
                        playerIndices[Game.player_count++] = player_index;
                        p.update_cycle = loopCycle;
                        entityIndex[entityCount++] = player_index;
                        break;
                    }
                    case 1: { // Walk
                        playerIndices[Game.player_count++] = player_index;
                        p.update_cycle = loopCycle;
                        p.move(b.readBits(3), false);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = player_index;
                        }
                        break;
                    }
                    case 2: { // Run
                        playerIndices[Game.player_count++] = player_index;
                        p.update_cycle = loopCycle;
                        p.move(b.readBits(3), true);
                        p.move(b.readBits(3), true);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = player_index;
                        }
                        break;
                    }
                    case 3: { // Unknown
                        entityUpdateIndices[entityUpdateCount++] = player_index;
                        break;
                    }
                }
            }
        }
    }

    public static void update_players(int psize, Buffer b) {
        entityUpdateCount = 0;
        entityCount = 0;

        update_localplayer_movement(b);
        update_player_movement(b);
        update_new_players(b, psize);
        update_player_masks(b);

        for (int i = 0; i < entityUpdateCount; i++) {
            int player_index = entityUpdateIndices[i];
            if (players[player_index].update_cycle != loopCycle) {
                players[player_index] = null;
            }
        }

        if (b.position != psize) {
            Signlink.error("Error packet size mismatch in getplayer pos:" + b.position + " psize:" + psize);
            throw new RuntimeException(JString.EEK);
        }

        for (int i = 0; i < player_count; i++) {
            if (players[playerIndices[i]] == null) {
                Signlink.error(username + " null entry in pl list - pos:" + i + " size:" + player_count);
                throw new RuntimeException(JString.EEK);
            }
        }
    }

    public static void update_widget(Widget w) {
        int type = w.action_type;

        if (CharacterDesign.update(w, type)) {
            return;
        }

        if (type >= 1 && type <= 100 || type >= 701 && type <= 800) {
            if (type == 1 && frenemiesStatus == 0) {
                w.message_disabled = JString.LOADING_FRIEND_LIST;
                w.option_type = 0;
                return;
            }
            if (type == 1 && frenemiesStatus == 1) {
                w.message_disabled = JString.CONNECTING_TO_FRIENDSERVER;
                w.option_type = 0;
                return;
            }
            if (type == 2 && frenemiesStatus != 2) {
                w.message_disabled = JString.PLEASE_WAIT;
                w.option_type = 0;
                return;
            }
            int count = Game.friendCount;

            if (frenemiesStatus != 2) {
                count = 0;
            }

            if (type > 700) {
                type -= 601;
            } else {
                type--;
            }

            if (type >= count) {
                w.message_disabled = JString.BLANK;
                w.option_type = 0;
                return;
            } else {
                w.message_disabled = friendName[type];
                w.option_type = 1;
                return;
            }
        }

        if (type >= 101 && type <= 200 || type >= 801 && type <= 900) {
            int count = Game.friendCount;

            if (frenemiesStatus != 2) {
                count = 0;
            }

            if (type > 800) {
                type -= 701;
            } else {
                type -= 101;
            }

            if (type >= count) {
                w.message_disabled = JString.BLANK;
                w.option_type = 0;
                return;
            }

            if (friendNode[type] == 0) {
                w.message_disabled = JString.OFFLINE;
            } else if (friendNode[type] == nodeIndex) {
                w.message_disabled = JString.WORLD + (friendNode[type] - 9);
            } else {
                w.message_disabled = JString.WORLD + (friendNode[type] - 9);
            }

            w.option_type = 1;
            return;
        }

        if (type == 203) {
            int count = friendCount;

            if (frenemiesStatus != 2) {
                count = 0;
            }

            w.scroll_height = count * 15 + 20;

            if (w.scroll_height <= w.height) {
                w.scroll_height = w.height + 1;
            }
            return;
        }

        if (type >= 401 && type <= 500) {
            if ((type -= 401) == 0 && frenemiesStatus == 0) {
                w.message_disabled = JString.LOADING_IGNORE_LIST;
                w.option_type = 0;
                return;
            }

            if (type == 1 && frenemiesStatus == 0) {
                w.message_disabled = JString.PLEASE_WAIT;
                w.option_type = 0;
                return;
            }

            int count = ignoreCount;

            if (frenemiesStatus == 0) {
                count = 0;
            }

            if (type >= count) {
                w.message_disabled = JString.BLANK;
                w.option_type = 0;
                return;
            } else {
                w.message_disabled = JString.getFormattedString(ignoreLong[type]);
                w.option_type = 1;
                return;
            }
        }

        if (type == 503) {
            w.scroll_height = ignoreCount * 15 + 20;
            if (w.scroll_height <= w.height) {
                w.scroll_height = w.height + 1;
            }
            return;
        }

        if (type == 600) {
            w.message_disabled = reportAbuseInput;

            if (loopCycle % 20 < 10) {
                w.message_disabled += '|';
            } else {
                w.message_disabled += ' ';
            }
            return;
        }

        if (type == 620) {
            if (reportAbuseMute) {
                w.rgb_disabled = 0x00ff00;
                w.message_disabled = JString.MUTE_ON;
            } else {
                w.rgb_disabled = 0xffffff;
                w.message_disabled = JString.MUTE_OFF;
            }
        }

        if (type == 650 || type == 655) {
            if (welcomeLastIp != 0) {
                String s;
                if (welcomeLastPlaydate == 0) {
                    s = JString.EARLIER_TODAY;
                } else if (welcomeLastPlaydate == 1) {
                    s = JString.YESTERDAY;
                } else {
                    s = welcomeLastPlaydate + ' ' + JString.DAYS_AGO;
                }
                w.message_disabled = "You last logged in " + s + " from: " + Signlink.resolvedDns;
            } else {
                w.message_disabled = JString.BLANK;
            }
        }

        if (type == 651) {
            if (welcomeUnreadMessages == 0) {
                w.message_disabled = JString.UNREAD_MESSAGES_0;
                w.rgb_disabled = 0xFFFF00;
            }
            if (welcomeUnreadMessages == 1) {
                w.message_disabled = JString.UNREAD_MESSAGE_1;
                w.rgb_disabled = 0xFF00;
            }
            if (welcomeUnreadMessages > 1) {
                w.message_disabled = welcomeUnreadMessages + ' ' + JString.UNREAD_MESSAGES;
                w.rgb_disabled = 0xFF00;
            }
        }

        if (type == 652) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.message_disabled = JString.NON_MEMBERS_1;
                } else {
                    w.message_disabled = JString.BLANK;
                }
            } else if (welcomeInfo == 200) {
                w.message_disabled = JString.NO_RECOVERIES;
            } else {
                String s1;
                if (welcomeInfo == 0) {
                    s1 = "Earlier today";
                } else if (welcomeInfo == 1) {
                    s1 = "Yesterday";
                } else {
                    s1 = welcomeInfo + ' ' + JString.DAYS_AGO;
                }
                w.message_disabled = s1 + " you changed your recovery questions";
            }
        }

        if (type == 653) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.message_disabled = JString.NON_MEMBERS_2;
                } else {
                    w.message_disabled = JString.BLANK;
                }
            } else if (welcomeInfo == 200) {
                w.message_disabled = JString.SECURE_YOUR_ACCOUNT;
            } else {
                w.message_disabled = JString.CANCEL_RECOVERIES;
            }
        }

        if (type == 654) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.message_disabled = JString.NON_MEMBERS_3;
                    return;
                } else {
                    w.message_disabled = JString.BLANK;
                    return;
                }
            }
            if (welcomeInfo == 200) {
                w.message_disabled = JString.ACCOUNT_MANAGEMENT;
                return;
            }
            w.message_disabled = JString.ACCOUNT_MANAGEMENT;
        }
    }

    public static boolean walk_to(int click_type, int size_x, int size_y, int start_x, int start_y, int dest_x, int dest_y, int type, int face_flags, int rotation, boolean arbitrary) {
        byte map_size_x = 104;
        byte map_size_y = 104;

        for (int x1 = 0; x1 < map_size_x; x1++) {
            for (int y1 = 0; y1 < map_size_y; y1++) {
                pathWaypoint[x1][y1] = 0;
                pathDistance[x1][y1] = 0x5f5e0ff;
            }

        }

        int x = start_x;
        int y = start_y;
        pathWaypoint[start_x][start_y] = 99;
        pathDistance[start_x][start_y] = 0;

        int next = 0;
        int current = 0;
        pathQueueX[next] = start_x;
        pathQueueY[next++] = start_y;

        boolean reached = false;
        int path_length = pathQueueX.length;
        int ai[][] = collisionMaps[plane].flags;

        while (current != next) {
            x = pathQueueX[current];
            y = pathQueueY[current];
            current = (current + 1) % path_length;

            if (x == dest_x && y == dest_y) {
                reached = true;
                break;
            }

            if (type != 0) {
                if ((type < 5 || type == 10) && collisionMaps[plane].atWall(x, y, dest_x, dest_y, type - 1, rotation)) {
                    reached = true;
                    break;
                }
                if (type < 10 && collisionMaps[plane].atDecoration(x, y, dest_x, dest_y, type - 1, rotation)) {
                    reached = true;
                    break;
                }
            }

            if (size_x != 0 && size_y != 0 && collisionMaps[plane].atObject(x, y, dest_x, dest_y, size_x, size_y, face_flags)) {
                reached = true;
                break;
            }

            int distance = pathDistance[x][y] + 1;

            if (x > 0 && pathWaypoint[x - 1][y] == 0 && (ai[x - 1][y] & 0x1280108) == 0) {
                pathQueueX[next] = x - 1;
                pathQueueY[next] = y;
                next = (next + 1) % path_length;
                pathWaypoint[x - 1][y] = 2;
                pathDistance[x - 1][y] = distance;
            }

            if (x < map_size_x - 1 && pathWaypoint[x + 1][y] == 0 && (ai[x + 1][y] & 0x1280180) == 0) {
                pathQueueX[next] = x + 1;
                pathQueueY[next] = y;
                next = (next + 1) % path_length;
                pathWaypoint[x + 1][y] = 8;
                pathDistance[x + 1][y] = distance;
            }

            if (y > 0 && pathWaypoint[x][y - 1] == 0 && (ai[x][y - 1] & 0x1280102) == 0) {
                pathQueueX[next] = x;
                pathQueueY[next] = y - 1;
                next = (next + 1) % path_length;
                pathWaypoint[x][y - 1] = 1;
                pathDistance[x][y - 1] = distance;
            }

            if (y < map_size_y - 1 && pathWaypoint[x][y + 1] == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
                pathQueueX[next] = x;
                pathQueueY[next] = y + 1;
                next = (next + 1) % path_length;
                pathWaypoint[x][y + 1] = 4;
                pathDistance[x][y + 1] = distance;
            }

            if (x > 0 && y > 0 && pathWaypoint[x - 1][y - 1] == 0 && (ai[x - 1][y - 1] & 0x128010e) == 0 && (ai[x - 1][y] & 0x1280108) == 0 && (ai[x][y - 1] & 0x1280102) == 0) {
                pathQueueX[next] = x - 1;
                pathQueueY[next] = y - 1;
                next = (next + 1) % path_length;
                pathWaypoint[x - 1][y - 1] = 3;
                pathDistance[x - 1][y - 1] = distance;
            }

            if (x < map_size_x - 1 && y > 0 && pathWaypoint[x + 1][y - 1] == 0 && (ai[x + 1][y - 1] & 0x1280183) == 0 && (ai[x + 1][y] & 0x1280180) == 0 && (ai[x][y - 1] & 0x1280102) == 0) {
                pathQueueX[next] = x + 1;
                pathQueueY[next] = y - 1;
                next = (next + 1) % path_length;
                pathWaypoint[x + 1][y - 1] = 9;
                pathDistance[x + 1][y - 1] = distance;
            }

            if (x > 0 && y < map_size_y - 1 && pathWaypoint[x - 1][y + 1] == 0 && (ai[x - 1][y + 1] & 0x1280138) == 0 && (ai[x - 1][y] & 0x1280108) == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
                pathQueueX[next] = x - 1;
                pathQueueY[next] = y + 1;
                next = (next + 1) % path_length;
                pathWaypoint[x - 1][y + 1] = 6;
                pathDistance[x - 1][y + 1] = distance;
            }

            if (x < map_size_x - 1 && y < map_size_y - 1 && pathWaypoint[x + 1][y + 1] == 0 && (ai[x + 1][y + 1] & 0x12801e0) == 0 && (ai[x + 1][y] & 0x1280180) == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
                pathQueueX[next] = x + 1;
                pathQueueY[next] = y + 1;
                next = (next + 1) % path_length;
                pathWaypoint[x + 1][y + 1] = 12;
                pathDistance[x + 1][y + 1] = distance;
            }
        }

        pathArbitraryDest = 0;

        if (!reached) {
            if (arbitrary) {
                int max_distance = 100;
                for (int dev = 1; dev < 2; dev++) {
                    for (int move_x = dest_x - dev; move_x <= dest_x + dev; move_x++) {
                        for (int move_y = dest_y - dev; move_y <= dest_y + dev; move_y++) {
                            if (move_x >= 0 && move_y >= 0 && move_x < 104 && move_y < 104 && pathDistance[move_x][move_y] < max_distance) {
                                max_distance = pathDistance[move_x][move_y];
                                x = move_x;
                                y = move_y;
                                pathArbitraryDest = 1;
                                reached = true;
                            }
                        }

                    }

                    if (reached) {
                        break;
                    }
                }

            }
            if (!reached) {
                return false;
            }
        }

        current = 0;
        pathQueueX[current] = x;
        pathQueueY[current++] = y;
        int skip_check;

        for (int waypoint = skip_check = pathWaypoint[x][y]; x != start_x || y != start_y; waypoint = pathWaypoint[x][y]) {
            if (waypoint != skip_check) {
                skip_check = waypoint;
                pathQueueX[current] = x;
                pathQueueY[current++] = y;
            }
            if ((waypoint & 2) != 0) {
                x++;
            } else if ((waypoint & 8) != 0) {
                x--;
            }
            if ((waypoint & 1) != 0) {
                y++;
            } else if ((waypoint & 4) != 0) {
                y--;
            }
        }

        if (current > 0) {
            int index = current;

            if (index > 25) {
                index = 25;
            }

            current--;

            int path_x = pathQueueX[current];
            int path_y = pathQueueY[current];

            if (click_type == 0) {
                out.writeOpcode(164);
            } else if (click_type == 1) {
                out.writeOpcode(248);
            } else if (click_type == 2) {
                out.writeOpcode(98);
            }

            // XXX: Removed dummy
            out.writeByte(index + index + 3);
            out.writeLeShortA(path_x + mapBaseX);

            mapMarkerX = pathQueueX[0];
            mapMarkerY = pathQueueY[0];

            for (int i = 1; i < index; i++) {
                current--;
                out.writeByte(pathQueueX[current] - path_x);
                out.writeByte(pathQueueY[current] - path_y);
            }

            out.writeLeShort(path_y + mapBaseY);
            out.writeByteC(Key.CTRL.is_down() ? 1 : 0);

            return true;
        }
        return click_type != 1;
    }

    @Override
    public void draw() {
        if (errorLoading) {
            draw_error_screen();
            return;
        }

        drawCycle++;

        if (!loggedIn) {
            TitleScreen.draw(false);
        } else {
            draw_game();
        }

        clickCycle = 0;
    }

    @Override
    public void drawProgress(String caption, int percent) {
        progressPercent = percent;
        progressCaption = caption;

        TitleScreen.create_producers();

        if (archive == null) {
            super.drawProgress(caption, percent);
            return;
        }

        TitleScreen.producerBox.prepare();
        {
            int x = TitleScreen.producerBox.width / 2;
            int y = TitleScreen.producerBox.height / 2;

            y -= 48;

            BitmapFont.BOLD.draw(JString.LOADING_PLEASE_WAIT, x, y, RSColor.WHITE, BitmapFont.SHADOW_CENTER);

            y += 10;

            Canvas2D.drawRect(x - 152, y, 304, 34, 0x8C1111);
            Canvas2D.fillRect(x - 151, y + 1, 302, 32, 0);
            Canvas2D.fillRect(x - 150, y + 2, (int) (300 * (percent / 100D)), 30, 0x8C1111);

            y += 17;

            BitmapFont.BOLD.draw(caption, x, y + 5, RSColor.WHITE, BitmapFont.SHADOW_CENTER);
        }
        TitleScreen.producerBox.draw(202, 171);

        if (redraw) {
            redraw = false;
            if (!processFlames) {
                Flames.producer[0].draw(0, 0);
                Flames.producer[1].draw(637, 0);
            }

            ImageProducer[] background = TitleScreen.producerBackground;
            background[0].draw(128, 0);
            background[1].draw(202, 371);
            background[2].draw(0, 265);
            background[3].draw(562, 265);
            background[4].draw(128, 171);
            background[5].draw(562, 171);
        }
    }

    @Override
    public Socket getSocket(int port) throws IOException {
        return new Socket(InetAddress.getByName(getCodeBase().getHost()), port);
    }

    public URL getCodeBase() {
        try {
            if (instance.frame != null) {
                return new URL("http://" + JString.SERVER + ':' + (80 + port_offset));
            }
        } catch (Exception _ex) {
        }
        return instance.getCodeBase();
    }

    @Override
    public Component getComponent() {
        if (instance.frame != null) {
            return instance.frame.canvas;
        }

        return this;
    }

    public void init() {
        nodeIndex = Integer.parseInt(getParameter("nodeid"));
        port_offset = Integer.parseInt(getParameter("portoff"));

        String s = getParameter("lowmem");

        if (s != null && s.equals("1")) {
            lowDetail = true;
        } else {
            lowDetail = false;
        }

        s = getParameter("free");

        if (s != null && s.equals("1")) {
            isMembers = false;
        } else {
            isMembers = true;
        }

        this.init(false);
    }

    @Override
    public void process() {
        if (errorLoading) {
            return;
        }

        loopCycle++;

        music.handle();

        if (!loggedIn) {
            TitleScreen.handle();
        } else {
            handle();
        }

        handle_ondemand();
    }

    @Override
    public void redraw() {
        redraw = true;
    }

    public void retrieve_checksums() {
        archiveCrc[8] = 0;
        String error = JString.UNKNOWN_ERROR;

        while (archiveCrc[8] == 0) {
            drawProgress(JString.CONNECTING_TO_SERVER, 20);

            try {
                DataInputStream in = Jaggrab.request("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
                Buffer b = new Buffer(new byte[40]);
                in.readFully(b.payload, 0, 40);
                in.close();

                for (int i = 0; i < 9; i++) {
                    archiveCrc[i] = b.readInt();
                }

                int server_crc = b.readInt();
                int client_crc = 1234;

                for (int l1 = 0; l1 < 9; l1++) {
                    client_crc = (client_crc << 1) + archiveCrc[l1];
                }

                if (server_crc != client_crc) {
                    archiveCrc[8] = 0;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                error = JString.EOF_ERROR;
                archiveCrc[8] = 0;
            } catch (IOException e) {
                error = JString.CONNECTION_ERROR;
                archiveCrc[8] = 0;
            } catch (Exception e) {
                error = JString.UNEXPECTED_ERROR;
                archiveCrc[8] = 0;
            }

            if (archiveCrc[8] == 0) {
                for (int i = 5; i > 0; i--) {
                    try {
                        drawProgress(error + " - Retrying in " + i, 20);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        if (drawFlames) {
            draw_flames();
            return;
        }

        super.run();
    }

    @Override
    public void shutdown() {
        Signlink.error = false;

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception _ex) {
        }

        connection = null;

        close_music_player();

        if (mouseRecorder != null) {
            mouseRecorder.active = false;
        }

        mouseRecorder = null;
        ondemand.stop();
        ondemand = null;
        out = null;
        loginBuffer = null;
        in = null;
        chunkCoords = null;
        chunkLocPayload = null;
        chunkLandscapePayload = null;
        mapUids = null;
        landscapeUids = null;
        heightMap = null;
        render_flags = null;
        landscape = null;
        collisionMaps = null;
        pathWaypoint = null;
        pathDistance = null;
        pathQueueX = null;
        pathQueueY = null;
        tmpTexels = null;
        producerMinimap = null;
        producerScene = null;
        Chat.producer = null;
        Chat.Settings.producer = null;
        producerBackleft1 = null;
        producerBackleft2 = null;
        producerBackright1 = null;
        producerBackright2 = null;
        producerBacktop1 = null;
        producerBackvmid1 = null;
        producerBackvmid2 = null;
        producerBackvmid3 = null;
        producerBackhmid2 = null;
        bitmap1 = null;
        bitmap = null;
        Sidebar.nullify();
        imageCompass = null;
        imageHitMarks = null;
        imageHeadIcons = null;
        image_overlay_multiway = null;
        imageMapMarkers = null;
        imageCrosses = null;
        imageMapDots = null;
        bitmaps = null;
        imageMapFunctions = null;
        localTileCycle = null;
        players = null;
        playerIndices = null;
        entityIndex = null;
        playerBuffer = null;
        entityUpdateIndices = null;
        actors = null;
        actorIndices = null;
        Game.itemPile = null;
        spawnedLocs = null;
        projectiles = null;
        spotanims = null;
        settings = null;
        locIconX = null;
        locIconY = null;
        locIcon = null;
        imageMinimap = null;

        friendName = null;
        friendLong = null;
        friendNode = null;

        TitleScreen.nullify();
        Flames.nullify();
        Menu.nullify();
        LocConfig.nullify();
        ActorConfig.nullify();
        ObjConfig.nullify();
        Floor.instance = null;

        IdentityKit.instance = null;
        Widget.instance = null;
        Sequence.instance = null;

        SpotAnimConfig.instance = null;
        SpotAnimConfig.model_cache = null;

        Varp.instance = null;
        Player.model_cache = null;

        Canvas3D.nullify();
        Landscape.nullify();
        Model.nullify();
        SequenceFrame.nullify();
        System.gc();
    }

    @Override
    public void startup() {
        this.drawProgress(JString.STARTING_UP, 20);

        if (Signlink.cacheFile != null) {
            for (int i = 0; i < 5; i++) {
                Game.cache[i] = new Cache(i + 1, Signlink.cacheFile, Signlink.cacheIndex[i]);
            }
        }

        try {
            if (VERIFY_CACHE) {
                retrieve_checksums();
            }

            archive = get_archive("title screen", 1, "title", archiveCrc[1], 25);

            BitmapFont.SMALL = new BitmapFont("p11_full", archive);
            BitmapFont.NORMAL = new BitmapFont("p12_full", archive);
            BitmapFont.BOLD = new BitmapFont("b12_full", archive);
            BitmapFont.FANCY = new BitmapFont("q8_full", archive);

            TitleScreen.createBackground();
            TitleScreen.createImages();

            Archive archive_config = get_archive("config", 2, "config", archiveCrc[2], 30);
            Archive archive_widget = get_archive("interface", 3, "interface", archiveCrc[3], 35);
            Archive archive_media = get_archive("2d graphics", 4, "media", archiveCrc[4], 40);
            Archive archive_texture = get_archive("textures", 6, "textures", archiveCrc[6], 45);
            Archive archive_sound = get_archive("sound effects", 8, "sounds", archiveCrc[8], 55);

            render_flags = new byte[4][104][104];
            heightMap = new short[4][105][105];

            landscape = new Landscape(104, 104, 4, heightMap);

            for (int plane = 0; plane < 4; plane++) {
                collisionMaps[plane] = new CollisionMap(104, 104);
            }

            imageMinimap = new Sprite(512, 512);

            Archive archive_version = get_archive("update list", 5, "versionlist", archiveCrc[5], 60);

            drawProgress(JString.CONNECTING_TO_UPDATE_SERVER, 60);

            ondemand = new OnDemand();
            ondemand.setup(archive_version, this);

            SequenceFrame.init(ondemand.seq_frame_count());
            Model.init(ondemand.get_file_count(0), ondemand);

            music = new MusicPlayer();

            if (!lowDetail) {
                ondemand.send_request(2, 484);

                while (ondemand.immediate_request_count() > 0) {
                    handle_ondemand();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }

                    if (ondemand.fails > 3) {
                        return;
                    }
                }
            }

            int count = 0;

            drawProgress(JString.REQUESTING_ANIMATIONS, 65);
            {
                count = ondemand.get_file_count(1);

                for (int i = 0; i < count; i++) {
                    ondemand.send_request(1, i);
                }

                while (ondemand.immediate_request_count() > 0) {
                    int remaining = count - ondemand.immediate_request_count();

                    if (remaining > 0) {
                        drawProgress("Loading animations - " + ((remaining * 100) / count) + "%", 65);
                    }

                    handle_ondemand();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }

                    if (ondemand.fails > 3) {
                        return;
                    }
                }
            }

            drawProgress(JString.REQUESTING_MODELS, 70);
            {
                count = ondemand.get_file_count(0);

                for (int i = 0; i < count; i++) {
                    int flags = ondemand.mesh_flags(i);
                    if ((flags & 1) != 0) {
                        ondemand.send_request(0, i);
                    }
                }

                count = ondemand.immediate_request_count();

                while (ondemand.immediate_request_count() > 0) {
                    int remaining = count - ondemand.immediate_request_count();

                    if (remaining > 0) {
                        drawProgress("Loading models - " + (remaining * 100) / count + "%", 70);
                    }

                    handle_ondemand();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }
                }
            }

            drawProgress(JString.REQUESTING_MAPS, 75);
            {
                if (cache[0] != null) {
                    ondemand.send_request(3, ondemand.get_map_uid(47, 48, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(47, 48, 1));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 48, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 48, 1));
                    ondemand.send_request(3, ondemand.get_map_uid(49, 48, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(49, 48, 1));
                    ondemand.send_request(3, ondemand.get_map_uid(47, 47, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(47, 47, 1));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 47, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 47, 1));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 148, 0));
                    ondemand.send_request(3, ondemand.get_map_uid(48, 148, 1));
                    count = ondemand.immediate_request_count();

                    while (ondemand.immediate_request_count() > 0) {
                        int remaining = count - ondemand.immediate_request_count();

                        if (remaining > 0) {
                            drawProgress("Loading maps - " + (remaining * 100) / count + "%", 75);
                        }

                        handle_ondemand();

                        try {
                            Thread.sleep(100L);
                        } catch (Exception _ex) {
                        }
                    }
                }

                count = ondemand.get_file_count(0);

                for (int i = 0; i < count; i++) {
                    int flags = ondemand.mesh_flags(i);
                    byte priority = 0;

                    if ((flags & 8) != 0) {
                        priority = 10;
                    } else if ((flags & 0x20) != 0) {
                        priority = 9;
                    } else if ((flags & 0x10) != 0) {
                        priority = 8;
                    } else if ((flags & 0x40) != 0) {
                        priority = 7;
                    } else if ((flags & 0x80) != 0) {
                        priority = 6;
                    } else if ((flags & 2) != 0) {
                        priority = 5;
                    } else if ((flags & 4) != 0) {
                        priority = 4;
                    }

                    if ((flags & 1) != 0) {
                        priority = 3;
                    }

                    if (priority != 0) {
                        ondemand.verify(priority, 0, i);
                    }
                }

                ondemand.request_regions(isMembers);

                if (!lowDetail) {
                    for (int i = 1; i < ondemand.get_file_count(2); i++) {
                        if (ondemand.has_midi(i)) {
                            ondemand.verify((byte) 1, 2, i);
                        }
                    }
                }
            }

            drawProgress(JString.UNPACKING_MEDIA, 80);
            {
                Chat.background = new Bitmap(archive_media, "chatback", 0);
                Chat.Settings.background = new Bitmap(archive_media, "backbase1", 0);

                bitmap1 = new Bitmap(archive_media, "mapback", 0);

                imageCompass = new Sprite(archive_media, "compass", 0);
                imageMapedge = new Sprite(archive_media, "mapedge", 0);
                imageMapedge.crop();

                Sidebar.load(archive_media);

                for (int i = 0; i < imageMapMarkers.length; i++) {
                    imageMapMarkers[i] = new Sprite(archive_media, JString.MAP_MARKER, i);
                }

                for (int i = 0; i < imageCrosses.length; i++) {
                    imageCrosses[i] = new Sprite(archive_media, JString.CROSS, i);
                }

                for (int i = 0; i < imageMapDots.length; i++) {
                    imageMapDots[i] = new Sprite(archive_media, JString.MAP_DOTS, i);
                }

                for (int i = 0; i < 2; i++) {
                    bitmapModIcons[i] = new Bitmap(archive_media, JString.MOD_ICONS, i);
                }

                bitmapModIcons[2] = bitmapModIcons[1];

                try {
                    for (int i = 0; i < 100; i++) {
                        bitmaps[i] = new Bitmap(archive_media, JString.MAP_SCENE, i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 100; i++) {
                        imageMapFunctions[i] = new Sprite(archive_media, JString.MAP_FUNCTION, i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 20; i++) {
                        imageHitMarks[i] = new Sprite(archive_media, JString.HITMARKS, i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 8; i++) {
                        imageHeadIcons[i] = new Sprite(archive_media, JString.HEADICONS, i);
                    }
                } catch (Exception e) {
                }

                try {
                    image_overlay_multiway = imageHeadIcons[1];
                } catch (Exception e) {

                }

                bitmap3 = new Bitmap(archive_media, JString.SCROLLBAR, 0);
                bitmap2 = new Bitmap(archive_media, JString.SCROLLBAR, 1);

                producerBackleft1 = ImageProducer.derive(archive_media, "backleft1");
                producerBackleft2 = ImageProducer.derive(archive_media, "backleft2");
                producerBackright1 = ImageProducer.derive(archive_media, "backright1");
                producerBackright2 = ImageProducer.derive(archive_media, "backright2");
                producerBacktop1 = ImageProducer.derive(archive_media, "backtop1");
                producerBackvmid1 = ImageProducer.derive(archive_media, "backvmid1");
                producerBackvmid2 = ImageProducer.derive(archive_media, "backvmid2");
                producerBackvmid3 = ImageProducer.derive(archive_media, "backvmid3");
                producerBackhmid2 = ImageProducer.derive(archive_media, "backhmid2");

                int red_offset = (int) (Math.random() * 11D) - 5;
                int green_offset = (int) (Math.random() * 11D) - 5;
                int blue_offset = (int) (Math.random() * 11D) - 5;
                int offset = (int) (Math.random() * 21D) - 10;

                for (int i = 0; i < 100; i++) {
                    if (imageMapFunctions[i] != null) {
                        imageMapFunctions[i].translateRgb(red_offset + offset, green_offset + offset, blue_offset + offset);
                    }
                    if (bitmaps[i] != null) {
                        bitmaps[i].translateRgb(red_offset + offset, green_offset + offset, blue_offset + offset);
                    }
                }
            }

            drawProgress(JString.UNPACKING_TEXTURES, 83);
            {
                Canvas3D.unpack_textures(archive_texture);
                Canvas3D.create_palette(0.8D);
                Canvas3D.setup_texel_pools();
            }

            drawProgress(JString.UNPACKING_CONFIG, 86);
            {
                Sequence.unpack(archive_config);
                LocConfig.unpack(archive_config);
                Floor.unpack(archive_config);
                ObjConfig.unpack(archive_config);
                ActorConfig.unpack(archive_config);
                IdentityKit.unpack(archive_config);
                SpotAnimConfig.unpack(archive_config);
                Varp.unpack(archive_config);
                VarBit.unpack(archive_config);
            }

            drawProgress(JString.UNPACKING_SOUNDS, 90);
            {
                if (!lowDetail) {
                    WaveSound.unpack(new Buffer(archive_sound.get("sounds.dat")));
                }
            }

            drawProgress(JString.UNPACKING_WIDGETS, 95);
            {
                Widget.unpack(archive_widget, new BitmapFont[]{BitmapFont.SMALL, BitmapFont.NORMAL, BitmapFont.BOLD, BitmapFont.FANCY}, archive_media);
            }

            drawProgress(JString.PREPARING_GAME_ENGINE, 100);
            {

                for (int y = 0; y < 33; y++) {
                    int k6 = 999;
                    int i7 = 0;
                    for (int x = 0; x < 34; x++) {
                        if (bitmap1.pixels[x + (y * bitmap1.width)] == 0) {
                            if (k6 == 999) {
                                k6 = x;
                            }
                            continue;
                        }
                        if (k6 == 999) {
                            continue;
                        }
                        i7 = x;
                        break;
                    }

                    anIntArray968[y] = k6;
                    anIntArray1057[y] = i7 - k6;
                }

                for (int y = 5; y < 156; y++) {
                    int j7 = 999;
                    int l7 = 0;
                    for (int x = 25; x < 172; x++) {
                        if (bitmap1.pixels[x + y * bitmap1.width] == 0 && (x > 34 || y > 34)) {
                            if (j7 == 999) {
                                j7 = x;
                            }
                            continue;
                        }
                        if (j7 == 999) {
                            continue;
                        }
                        l7 = x;
                        break;
                    }

                    anIntArray1052[y - 5] = j7 - 25;
                    anIntArray1229[y - 5] = l7 - j7;
                }

                Canvas3D.prepare(479, 96);
                chatPixels3D = Canvas3D.pixels;
                Canvas3D.prepare(190, 261);
                Sidebar.pixels3D = Canvas3D.pixels;
                Canvas3D.prepare(512, 334);
                viewport_pixels = Canvas3D.pixels;

                int z_array[] = new int[9];
                for (int z_index = 0; z_index < 9; z_index++) {
                    int k8 = 128 + ((z_index * 32) + 15);
                    int l8 = 600 + (k8 * 3);
                    int sin = Canvas3D.sin[k8];
                    z_array[z_index] = l8 * sin >> 16;
                }

                Landscape.setupViewport(500, 800, 512, 334, z_array);
                mouseRecorder = new MouseRecorder();
                startThread(mouseRecorder, 10);
            }

            return;
        } catch (Exception e) {
            Signlink.error("loaderror " + progressCaption + ' ' + progressPercent);
            errorLoading = true;
            errorException = e;
            e.printStackTrace();
        }
    }
}
