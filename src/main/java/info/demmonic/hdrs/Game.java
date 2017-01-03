package info.demmonic.hdrs;

import info.demmonic.hdrs.audio.MusicPlayer;
import info.demmonic.hdrs.audio.model.WaveSound;
import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.cache.Cache;
import info.demmonic.hdrs.cache.impl.*;
import info.demmonic.hdrs.cache.model.ActorConfig;
import info.demmonic.hdrs.cache.model.LocConfig;
import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.cache.model.SpotAnimConfig;
import info.demmonic.hdrs.input.Keyboard;
import info.demmonic.hdrs.input.Mouse;
import info.demmonic.hdrs.input.MouseRecorder;
import info.demmonic.hdrs.input.model.Area;
import info.demmonic.hdrs.input.model.Key;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.io.IsaacCipher;
import info.demmonic.hdrs.io.OnDemand;
import info.demmonic.hdrs.media.*;
import info.demmonic.hdrs.media.impl.*;
import info.demmonic.hdrs.media.impl.widget.CharacterDesign;
import info.demmonic.hdrs.model.Action;
import info.demmonic.hdrs.model.Packet;
import info.demmonic.hdrs.model.Skill;
import info.demmonic.hdrs.net.Connection;
import info.demmonic.hdrs.net.Jaggrab;
import info.demmonic.hdrs.node.Chain;
import info.demmonic.hdrs.node.impl.OnDemandRequest;
import info.demmonic.hdrs.scene.Scene;
import info.demmonic.hdrs.scene.model.*;
import info.demmonic.hdrs.util.BitUtils;
import info.demmonic.hdrs.util.JString;
import info.demmonic.hdrs.util.RSColor;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.zip.CRC32;

public class Game extends GameShell {

    public static final boolean VERIFY_CACHE = false;
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
    public static int camCinemaAimX;
    public static int camCinemaAimY;
    public static int camCinemaAimZ;
    public static int camCinemaBaseSpeed;
    public static int camCinemaDestX;
    public static int camCinemaDestY;
    public static int camCinemaDestZ;
    public static boolean camCinemaMode;
    public static int camCinemaRotBase;
    public static int camCinemaRotModifier;
    public static int camCinemaSpeed;
    public static int camInfoCycle;
    public static int camPitchMod;
    public static int camPitchOff;
    public static int camXOff;
    public static int xCamOffMod;
    public static int camYOff;
    public static int yCamOffMod;
    public static int camYawMod;
    public static int camYawOff;
    public static int chaseCamPitch;
    public static int chaseCamPitchMod;
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
    public static Sprite imageOverlayMultiway;
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
    public static int playerCount;
    public static int playerIndices[];
    public static Player players[];
    public static int portOffset;
    public static volatile boolean processFlames;
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
    public static byte renderFlags[][][];
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
    public static int viewportPixels[];
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
            int realLevel = level + 1;
            int j = (int) ((double) realLevel + 300D * Math.pow(2D, (double) realLevel / 7D));
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
        reportAbuseInput = "";
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
        username = "";
        password = "";
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
        TitleScreen.setMessage("", "");
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

                if (vertex_plane < 3 && (renderFlags[1][x][y] & 2) == 2) {
                    vertex_plane++;
                }

                Scene.addLoc(landscape, x, y, plane, new_loc_index, new_loc_type, loc_rotation, vertex_plane, collisionMaps[plane], heightMap);
            }
        }
    }

    public static void clearCaches() {
        LocConfig.staticModelCache.clear();
        LocConfig.modelCache.clear();
        ActorConfig.model_cache.clear();
        ObjConfig.modelCache.clear();
        ObjConfig.spriteCache.clear();
        Player.model_cache.clear();
        SpotAnimConfig.model_cache.clear();
    }

    public static void closeMusicPlayer() {
        method891(false);

        if (music.var2 > 0) {
            music.setVolume(256);
            music.var2 = 0;
        }

        music.halt();
        music = null;
    }

    public static void closeWidgets() {
        out.writeOpcode(130);
        if (Sidebar.widgetIndex != -1) {
            Sidebar.widgetIndex = -1;
            Sidebar.draw = true;
            dialogueOptionActive = false;
            Sidebar.drawTabs = true;
        }
        if (Chat.getOverlay() != -1) {
            Chat.setOverlay(-1);
            dialogueOptionActive = false;
        }
        widgetOverlay = -1;
    }

    public static void draw2DOverlay() {
        Chat.Private.draw();

        if (crossType == 1) {
            imageCrosses[crossCycle / 100].drawMasked(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (crossType == 2) {
            imageCrosses[4 + crossCycle / 100].drawMasked(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (widgetUnderlay != -1) {
            Widget.handleSequences(animCycle, widgetUnderlay);
            Widget.draw(widgetUnderlay, 0, 0, 0);
        }

        if (widgetOverlay != -1) {
            Widget.handleSequences(animCycle, widgetOverlay);
            Widget.draw(widgetOverlay, 0, 0, 0);
        }

        handleMessageStatus();

        if (!Menu.visible) {
            Menu.handle();
            Menu.drawTooltip();
        } else if (Menu.area == Area.VIEWPORT) {
            Menu.draw();
        }

        if (inMultiZone == 1) {
            imageOverlayMultiway.drawMasked(472, 296);
        }

        if (Game.debug) {
            int x = 507;
            int y = 17;

            StringBuilder sb = new StringBuilder();
            sb.append("Fps: ").append(counter.get());

            BitmapFont.NORMAL.draw(sb.toString(), x, y, 0xFFFF00, BitmapFont.RIGHT | BitmapFont.SHADOW);
            y += 17;

            Runtime runtime = Runtime.getRuntime();
            int memUsed = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);

            sb.delete(0, sb.length());
            sb.append("Mem: ").append(memUsed).append('k');

            BitmapFont.NORMAL.draw(sb.toString(), x, y, 0xFFFF00, BitmapFont.RIGHT | BitmapFont.SHADOW);
            y += 17;

            sb.delete(0, sb.length());
            sb.append("Out: ").append(bytesSent).append("B/s");
            sb.append(' ');
            sb.append("In: ").append(bytesRead).append("B/s");

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

            BitmapFont.NORMAL.draw("System update in: " + minutes + ":" + (seconds < 10 ? "0" + seconds : seconds), 4, 328, 0xFFFF00, BitmapFont.SHADOW);
        }
    }

    public static void drawActors(boolean flag) {
        for (int i = 0; i < actorCount; i++) {
            Actor a = actors[actorIndices[i]];
            int uid = 0x20000000 + (actorIndices[i] << 14);

            if (a == null || !a.isVisible() || a.config.aBoolean93 != flag) {
                continue;
            }

            int local_x = a.sceneX >> 7;
            int local_y = a.sceneY >> 7;

            if (local_x < 0 || local_x >= 104 || local_y < 0 || local_y >= 104) {
                continue;
            }

            if (a.size == 1 && (a.sceneX & 0x7f) == 64 && (a.sceneY & 0x7f) == 64) {
                if (localTileCycle[local_x][local_y] == sceneCycle) {
                    continue;
                }

                localTileCycle[local_x][local_y] = sceneCycle;
            }

            if (!a.config.interactable) {
                uid += 0x80000000;
            }

            landscape.add(a, a.sceneX, a.sceneY, getLandZ(a.sceneX, a.sceneY, plane), plane, a.rotation, (a.size - 1) * 64 + 60, a.canRotate, uid);
        }
    }

    public static void drawFlames() {
        flameThread = true;
        try {
            long lastTime = System.currentTimeMillis();
            int i = 0;
            int delay = 20;
            while (processFlames) {
                Flames.cycle++;
                Flames.handle();
                Flames.handle();
                Flames.handlePalette();
                if (++i > 10) {
                    long current_time = System.currentTimeMillis();
                    int difference = (int) (current_time - lastTime) / 10 - delay;
                    delay = 40 - difference;
                    if (delay < 5) {
                        delay = 5;
                    }
                    i = 0;
                    lastTime = current_time;
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

    public static void drawGame() {
        if (redraw) {
            redraw = false;
            Sidebar.draw = true;
            Chat.redraw = true;
            Chat.Settings.redraw = true;
            Sidebar.drawTabs = true;
        }

        if (sceneState == 2) {
            drawScene();
        }

        if (Menu.visible && Menu.area == Area.TAB) {
            Sidebar.draw = true;
        }

        if (Sidebar.widgetIndex != -1) {
            boolean redraw = Widget.handleSequences(animCycle, Sidebar.widgetIndex);
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
            drawMinimap();
        }

        Sidebar.drawTabs();
        Chat.Settings.draw();
        animCycle = 0;
    }

    public static void drawMarker() {
        if (markType != 2) {
            return;
        }

        setDrawXy((markX - mapBaseX << 7) + markOffX, (markY - mapBaseY << 7) + markOffY, markZ * 2);

        if (drawX > -1 && loopCycle % 20 < 10) {
            imageHeadIcons[2].drawMasked(drawX - 12, drawY - 28);
        }
    }

    public static void drawMinimap() {
        if (minimapState == 2) {
            byte mask[] = bitmap1.pixels;
            for (int i = 0; i < mask.length; i++) {
                if (mask[i] == 0) {
                    Canvas2D.pixels[i] = 0;
                }
            }
            imageCompass.draw(0, 0, 33, 33, chaseCamYaw, 256, 25, 25, anIntArray1057, anIntArray968);
            return;
        }

        int yaw = chaseCamYaw + camYawOff & 0x7ff;
        int pivotX = 48 + (self.sceneX >> 5);
        int pivotY = 464 - (self.sceneY >> 5);

        imageMinimap.draw(25, 5, 146, 151, yaw, 256 + mapZoomOffset, pivotX, pivotY, anIntArray1229, anIntArray1052);
        imageCompass.draw(0, 0, 33, 33, yaw, 256, 25, 25, anIntArray1057, anIntArray968);

        for (int i = 0; i < locIconCount; i++) {
            int mapX = (locIconX[i] * 4 + 2) - self.sceneX / 32;
            int mapY = (locIconY[i] * 4 + 2) - self.sceneY / 32;
            drawToMinimap(locIcon[i], mapX, mapY);
        }

        if (Game.itemPile != null) {
            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    Chain pile = Game.itemPile[plane][x][y];
                    if (pile != null) {
                        int map_x = (x * 4 + 2) - self.sceneX / 32;
                        int map_y = (y * 4 + 2) - self.sceneY / 32;
                        drawToMinimap(imageMapDots[0], map_x, map_y);
                    }
                }
            }
        }

        for (int i = 0; i < actorCount; i++) {
            Actor a = actors[actorIndices[i]];

            if (a != null && a.isVisible()) {
                ActorConfig ac = a.config;

                if (ac.override_index != null) {
                    ac = ac.getOverridingConfig();
                }

                if (ac != null && ac.show_on_minimap && ac.interactable) {
                    int map_x = a.sceneX / 32 - self.sceneX / 32;
                    int map_y = a.sceneY / 32 - self.sceneY / 32;
                    drawToMinimap(imageMapDots[1], map_x, map_y);
                }
            }
        }

        for (int i = 0; i < playerCount; i++) {
            Player p = players[playerIndices[i]];

            if (p != null && p.isVisible()) {
                int mapX = p.sceneX / 32 - self.sceneX / 32;
                int mapY = p.sceneY / 32 - self.sceneY / 32;

                boolean isFriend = false;
                long nameLong = JString.toLong(p.name);

                for (int j = 0; j < friendCount; j++) {
                    if (nameLong != friendLong[j] || friendNode[j] == 0) {
                        continue;
                    }
                    isFriend = true;
                    break;
                }

                boolean isTeammate = false;

                if (self.team != 0 && p.team != 0 && self.team == p.team) {
                    isTeammate = true;
                }

                if (isFriend) {
                    drawToMinimap(imageMapDots[3], mapX, mapY);
                } else if (isTeammate) {
                    drawToMinimap(imageMapDots[4], mapX, mapY);
                } else {
                    drawToMinimap(imageMapDots[2], mapX, mapY);
                }
            }
        }

        if (markType != 0 && loopCycle % 20 < 10) {
            if (markType == 1 && markActorIndex >= 0 && markActorIndex < actors.length) {
                Actor a = actors[markActorIndex];
                if (a != null) {
                    int mapX = a.sceneX / 32 - self.sceneX / 32;
                    int mapY = a.sceneY / 32 - self.sceneY / 32;
                    drawMinimapMark(imageMapMarkers[1], mapX, mapY);
                }
            }

            if (markType == 2) {
                int mapX = ((markX - mapBaseX) * 4 + 2) - self.sceneX / 32;
                int mapY = ((markY - mapBaseY) * 4 + 2) - self.sceneY / 32;
                drawMinimapMark(imageMapMarkers[1], mapX, mapY);
            }

            if (markType == 10 && markPlayerIndex >= 0 && markPlayerIndex < players.length) {
                Player p = players[markPlayerIndex];
                if (p != null) {
                    int map_x = p.sceneX / 32 - self.sceneX / 32;
                    int map_y = p.sceneY / 32 - self.sceneY / 32;
                    drawMinimapMark(imageMapMarkers[1], map_x, map_y);
                }
            }
        }

        if (mapMarkerX != 0) {
            int mapX = (mapMarkerX * 4 + 2) - self.sceneX / 32;
            int mapY = (mapMarkerY * 4 + 2) - self.sceneY / 32;
            drawToMinimap(imageMapMarkers[0], mapX, mapY);
        }

        imageMapDots[2].drawMasked(96, 77);
    }

    public static void drawMinimapMark(Sprite s, int mapX, int mapY) {
        int len = mapX * mapX + mapY * mapY;

        if (len > 4225 && len < 0x15f90) {
            int yaw = chaseCamYaw + camYawOff & 0x7ff;
            int sin = Model.sin[yaw];
            int cos = Model.cos[yaw];
            sin = (sin * 256) / (mapZoomOffset + 256);
            cos = (cos * 256) / (mapZoomOffset + 256);
            int x = mapY * sin + mapX * cos >> 16;
            int y = mapY * cos - mapX * sin >> 16;
            double angle = Math.atan2(x, y);
            int draw_x = (int) (Math.sin(angle) * 63D);
            int draw_y = (int) (Math.cos(angle) * 57D);
            imageMapedge.drawRotated((94 + draw_x + 4) - 10, 83 - draw_y - 20, 15, 15, 20, 20, 256, angle);
        } else {
            drawToMinimap(s, mapX, mapY);
        }
    }

    public static void drawMinimapTile(int x, int y, int plane, int wallRgb, int doorRgb) {
        int uid = landscape.getWallUid(plane, x, y);

        if (uid != 0) {
            int arrangement = landscape.getArrangement(plane, x, y, uid);
            int rotation = arrangement >> 6 & 3;
            int type = arrangement & 0x1f;
            int rgb = wallRgb;

            if (uid > 0) {
                rgb = doorRgb;
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
                    int mapX = (lc.sizeX * 4 - b.width) / 2;
                    int mapY = (lc.sizeY * 4 - b.height) / 2;
                    b.draw(48 + x * 4 + mapX, 48 + (104 - y - lc.sizeY) * 4 + mapY);
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
                    int mapX = (lc.sizeX * 4 - b.width) / 2;
                    int mapY = (lc.sizeY * 4 - b.height) / 2;
                    b.draw(48 + x * 4 + mapX, 48 + (104 - y - lc.sizeY) * 4 + mapY);
                }
            }
        }
    }

    public static void drawPlayers(boolean onlyLocal) {
        if (self.sceneX >> 7 == mapMarkerX && self.sceneY >> 7 == mapMarkerY) {
            mapMarkerX = 0;
        }

        int count = playerCount;

        if (onlyLocal) {
            count = 1;
        }

        for (int i = 0; i < count; i++) {
            Player p;
            int uid;

            if (onlyLocal) {
                p = self;
                uid = MAX_PLAYER_INDEX << 14;
            } else {
                p = players[playerIndices[i]];
                uid = playerIndices[i] << 14;
            }

            if (p == null || !p.isVisible()) {
                continue;
            }

            p.lowLod = (lowDetail && playerCount > 50 || playerCount > 200) && !onlyLocal && p.moveSeqIndex == p.standAnimation;

            int localX = p.sceneX >> 7;
            int localY = p.sceneY >> 7;

            if (localX < 0 || localX >= 104 || localY < 0 || localY >= 104) {
                continue;
            }

            if (p.locModel != null && loopCycle >= p.locStartCycle && loopCycle < p.locEndCycle) {
                p.lowLod = false;
                p.sceneZ = getLandZ(p.sceneX, p.sceneY, plane);
                landscape.add(p, p.sceneX, p.sceneY, p.sceneZ, plane, p.locX0, p.locY0, p.locX1, p.locY1, p.rotation, uid);
                continue;
            }

            if ((p.sceneX & 0x7f) == 64 && (p.sceneY & 0x7f) == 64) {
                if (localTileCycle[localX][localY] == sceneCycle) {
                    continue;
                }
                localTileCycle[localX][localY] = sceneCycle;
            }

            p.sceneZ = getLandZ(p.sceneX, p.sceneY, plane);
            landscape.add(p, p.sceneX, p.sceneY, p.sceneZ, plane, p.rotation, 60, p.canRotate, uid);
        }

    }

    public static void drawProjectiles() {
        for (Projectile p = (Projectile) projectiles.top(); p != null; p = (Projectile) projectiles.next()) {
            if (p.plane != plane || loopCycle > p.cycleEnd) {
                p.detach();
                return;
            }

            if (loopCycle >= p.cycleStart) {
                if (p.targetIndex > 0) {
                    Actor a = actors[p.targetIndex - 1];
                    if (a != null && a.sceneX >= 0 && a.sceneX < 13312 && a.sceneY >= 0 && a.sceneY < 13312) {
                        p.update(loopCycle, a.sceneX, a.sceneY, getLandZ(a.sceneX, a.sceneY, p.plane) - p.offsetZ);
                    }
                }

                if (p.targetIndex < 0) {
                    int index = -p.targetIndex - 1;
                    Player pl;

                    if (index == localPlayerIndex) {
                        pl = self;
                    } else {
                        pl = players[index];
                    }

                    if (pl != null && pl.sceneX >= 0 && pl.sceneX < 13312 && pl.sceneY >= 0 && pl.sceneY < 13312) {
                        p.update(loopCycle, pl.sceneX, pl.sceneY, getLandZ(pl.sceneX, pl.sceneY, p.plane) - p.offsetZ);
                    }
                }

                p.update(animCycle);
                landscape.add(p, (int) p.sceneX, (int) p.sceneY, (int) p.sceneZ, plane, p.rotation, 60, false, -1);

            }
        }
    }

    public static void drawScene() {
        sceneCycle++;

        drawPlayers(true);
        drawActors(true);
        drawPlayers(false);
        drawActors(false);
        drawProjectiles();
        drawSpotanims();

        if (!camCinemaMode) {
            int i = chaseCamPitch;

            if (minPitch / 256 > i) {
                i = minPitch / 256;
            }

            if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i) {
                i = anIntArray1203[4] + 128;
            }

            int yaw = chaseCamYaw + camPitchOff & 0x7ff;
            Camera.set(chaseCamX, chaseCamY, getLandZ(self.sceneX, self.sceneY, plane) - 50, 600 + i * 3, i, yaw);
        }

        int occlusionTopPlane;

        if (!camCinemaMode) {
            occlusionTopPlane = getMaxVisiblePlane();
        } else {
            occlusionTopPlane = getMaxVisiblePlaneCinematic();
        }

        int camX = Camera.x;
        int camY = Camera.y;
        int camZ = Camera.z;
        int camPitch = Camera.pitch;
        int camYaw = Camera.yaw;

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

        Model.sceneClickable = true;
        Model.hovered_count = 0;
        Model.mouse_x = Mouse.lastX - 4;
        Model.mouse_y = Mouse.lastY - 4;
        Canvas2D.clear();

        landscape.draw(Camera.x, Camera.y, Camera.yaw, Camera.z, occlusionTopPlane, Camera.pitch);
        landscape.clearLocs();

        drawScene2D();
        drawMarker();
        scrollTextures(cycle_3d);
        draw2DOverlay();

        Camera.x = camX;
        Camera.z = camZ;
        Camera.y = camY;
        Camera.pitch = camPitch;
        Camera.yaw = camYaw;
    }

    public static void drawScene2D() {
        spokenCount = 0;

        for (int i = -1; i < playerCount + actorCount; i++) {
            Entity e;

            if (i == -1) {
                e = self;
            } else if (i < playerCount) {
                e = players[playerIndices[i]];
            } else {
                e = actors[actorIndices[i - playerCount]];
            }

            if (e == null || !e.isVisible()) {
                continue;
            }

            if (e instanceof Actor) {
                ActorConfig c = ((Actor) e).config;

                if (c.override_index != null) {
                    c = c.getOverridingConfig();
                }

                if (c == null) {
                    continue;
                }
            }

            if (i < playerCount) {
                int offset_y = 30;
                Player p = (Player) e;

                if (p.headicon_flag != 0) {
                    setDrawXy(e, e.height + 15);

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
                    setDrawXy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[1].drawMasked(drawX - 12, drawY - offset_y);
                    }
                }
            } else {
                ActorConfig ac = ((Actor) e).config;

                if (ac.head_icon >= 0 && ac.head_icon < imageHeadIcons.length) {
                    setDrawXy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[ac.head_icon].drawMasked(drawX - 12, drawY - 30);
                    }
                }

                if (markType == 1 && markActorIndex == actorIndices[i - playerCount] && loopCycle % 20 < 10) {
                    setDrawXy(e, e.height + 15);

                    if (drawX > -1) {
                        imageHeadIcons[2].drawMasked(drawX - 12, drawY - 28);
                    }
                }
            }

            if (e.spokenMessage != null && (i >= playerCount || Chat.Settings.values[0] == 0 || Chat.Settings.values[0] == 3 || Chat.Settings.values[0] == 1 && friendExists(((Player) e).name))) {
                setDrawXy(e, e.height);

                if (drawX > -1 && spokenCount < spokenMax) {
                    spokenOffX[spokenCount] = BitmapFont.BOLD.getWidth(e.spokenMessage) / 2;
                    spokenOffY[spokenCount] = BitmapFont.BOLD.height;

                    spokenX[spokenCount] = drawX;
                    spokenY[spokenCount] = drawY;

                    spokenColor[spokenCount] = e.spokenColor;
                    spokenEffect[spokenCount] = e.spokenEffect;
                    spokenCycle[spokenCount] = e.spokenLife;
                    spoken[spokenCount++] = e.spokenMessage;

                    if (Chat.Settings.showEffects && e.spokenEffect >= 1 && e.spokenEffect <= 3) {
                        spokenOffY[spokenCount] += 10;
                        spokenY[spokenCount] += 5;
                    }

                    if (Chat.Settings.showEffects && e.spokenEffect == 4) {
                        spokenOffX[spokenCount] = 60;
                    }

                    if (Chat.Settings.showEffects && e.spokenEffect == 5) {
                        spokenOffY[spokenCount] += 5;
                    }
                }
            }

            if (e.combatCycle > loopCycle) {
                setDrawXy(e, e.height + 15);

                if (drawX > -1) {
                    Canvas2D.fillRect(drawX - 15, drawY - 3, 30, 5, 0xFF0000);
                    Canvas2D.fillRect(drawX - 15, drawY - 3, (int) (30 * (e.currentHealth / (double) e.maxHealth)), 5, 0x00FF00);
                }
            }

            for (int mark = 0; mark < 4; mark++) {
                if (e.hit_cycle[mark] > loopCycle) {
                    setDrawXy(e, e.height / 2);

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
                        BitmapFont.SMALL.draw(String.valueOf(e.hitDamage[mark]), drawX - 1, drawY + 3, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
                    }
                }
            }

        }

        for (int i = 0; i < spokenCount; i++) {
            int x = spokenX[i];
            int y = spokenY[i];
            int offX = spokenOffX[i];
            int offY = spokenOffY[i];

            boolean flag = true;
            while (flag) {
                flag = false;
                for (int j = 0; j < i; j++) {
                    if (y + 2 > spokenY[j] - spokenOffY[j] && y - offY < spokenY[j] + 2 && x - offX < spokenX[j] + spokenOffX[j] && x + offX > spokenX[j] - spokenOffX[j] && spokenY[j] - spokenOffY[j] < y) {
                        y = spokenY[j] - spokenOffY[j];
                        flag = true;
                    }
                }
            }

            drawX = spokenX[i];
            drawY = spokenY[i] = y;
            String s = spoken[i];

            if (Chat.Settings.showEffects) {
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

    public static void drawScrollbar(int x, int y, int height, int scrollHeight, int scroll_amount) {
        int gripLength = ((height - 32) * height) / scrollHeight;

        if (gripLength < 8) {
            gripLength = 8;
        }

        int offsetY = ((height - 32 - gripLength) * scroll_amount) / (scrollHeight - height);

        bitmap3.draw(x, y);
        bitmap2.draw(x, (y + height) - 16);

        Canvas2D.fillRect(x, y + 16, 16, height - 32, RSColor.SCROLLBAR_BACKGROUND);
        Canvas2D.fillRect(x, y + 16 + offsetY, 16, gripLength, RSColor.SCROLLBAR_FOREGROUND);
        Canvas2D.drawLineV(x, y + 16 + offsetY, gripLength, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineV(x + 1, y + 16 + offsetY, gripLength, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineH(x, y + 16 + offsetY, 16, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineH(x, y + 17 + offsetY, 16, RSColor.SCROLLBAR_HIGHLIGHT);
        Canvas2D.drawLineV(x + 15, y + 16 + offsetY, gripLength, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineV(x + 14, y + 17 + offsetY, gripLength - 1, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineH(x, y + 15 + offsetY + gripLength, 16, RSColor.SCROLLBAR_LOWLIGHT);
        Canvas2D.drawLineH(x + 1, y + 14 + offsetY + gripLength, 15, RSColor.SCROLLBAR_LOWLIGHT);
    }

    public static void drawSpotanims() {
        SpotAnim a = (SpotAnim) spotanims.top();
        for (; a != null; a = (SpotAnim) spotanims.next()) {
            if (a.plane != plane || a.seqFinished) {
                a.detach();
            } else if (loopCycle >= a.cycleEnd) {
                a.update(animCycle);

                if (a.seqFinished) {
                    a.detach();
                } else {
                    landscape.add(a, a.x, a.y, a.z, a.plane, 0, 60, false, -1);
                }
            }
        }
    }

    public static void drawToMinimap(Sprite s, int x, int y) {
        int angle = chaseCamYaw + camYawOff & 0x7ff;
        int len = x * x + y * y;

        if (len > 6400) {
            return;
        }

        int sin = Model.sin[angle];
        int cos = Model.cos[angle];
        sin = (sin * 256) / (mapZoomOffset + 256);
        cos = (cos * 256) / (mapZoomOffset + 256);
        int mapX = y * sin + x * cos >> 16;
        int mapY = y * cos - x * sin >> 16;

        if (len > 2500) {
            s.drawTo(bitmap1, ((94 + mapX) - s.cropWidth / 2) + 4, 83 - mapY - s.cropHeight / 2 - 4);
        } else {
            s.drawMasked(((94 + mapX) - s.cropWidth / 2) + 4, 83 - mapY - s.cropHeight / 2 - 4);
        }
    }

    public static String formatItemAmount(int amount) {
        StringBuilder b = new StringBuilder(String.valueOf(amount));

        for (int i = b.length() - 3; i > 0; i -= 3) {
            b.insert(i, ',');
        }

        String s = b.toString();
        int len = b.length();

        if (len > 12) {
            b.insert(0, "@gr2").replace(6, 22, "").append(" billion ").append("@whi@").append('(');
        } else if (len > 8) {
            b.insert(0, "@gre@").replace(8, 18, "").append(" million ").append("@whi@").append('(');
        } else if (len > 4) {
            b.insert(0, "@cya@").replace(8, 14, "").append("K").append("@whi@").append('(');
        }

        return b.insert(0, ' ').append(s).append(')').toString();
    }

    public static boolean frenemyOptionValid(Widget w, boolean flag) {
        int i = w.actionType;
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
            Menu.add("Remove @whi@" + w.messageDisabled, 322);
            return true;
        } else {
            return false;
        }
    }

    public static void friendAdd(long nameLong) {
        if (nameLong == 0L) {
            return;
        }

        if (friendCount >= 100 && freeFriendsList != 1) {
            Chat.put("Your friendlist is full. Max of 100 for free users, and 200 for members", 0);
            return;
        }

        if (friendCount >= 200) {
            Chat.put("Your friendlist is full. Max of 100 for free users, and 200 for members", 0);
            return;
        }

        String name = JString.getFormattedString(nameLong);

        for (int i = 0; i < friendCount; i++) {
            if (friendLong[i] == nameLong) {
                Chat.put(name + " is already on your friend list", 0);
                return;
            }
        }

        for (int j = 0; j < ignoreCount; j++) {
            if (ignoreLong[j] == nameLong) {
                Chat.put("Please remove " + name + " from your ignore list first", 0);
                return;
            }
        }

        if (!name.equals(self.name)) {
            friendName[friendCount] = name;
            friendLong[friendCount] = nameLong;
            friendNode[friendCount] = 0;
            friendCount++;
            Sidebar.draw = true;
            out.writeOpcode(188);
            out.writeLong(nameLong);
        }
    }

    public static boolean friendExists(String s) {
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

    public static void friendRemove(long nameLong) {
        try {
            if (nameLong == 0L) {
                return;
            }

            for (int i = 0; i < friendCount; i++) {
                if (friendLong[i] != nameLong) {
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
                out.writeLong(nameLong);
                break;
            }
        } catch (RuntimeException runtimeexception) {
            Signlink.error("18622, " + nameLong + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public static void generateMinimap(int z) {
        int pixels[] = imageMinimap.pixels;

        Arrays.fill(pixels, 0);

        for (int y = 1; y < 103; y++) {
            int i = 24628 + (103 - y) * 512 * 4;
            for (int x = 1; x < 103; x++) {
                if ((renderFlags[z][x][y] & 0x18) == 0) {
                    landscape.drawMinimapTile(pixels, i, 512, z, x, y);
                }

                if (z < 3 && (renderFlags[z + 1][x][y] & 8) != 0) {
                    landscape.drawMinimapTile(pixels, i, 512, z + 1, x, y);
                }

                i += 4;
            }

        }

        int wallColor = ((238 + (int) (Math.random() * 20D)) - 10 << 16) + ((238 + (int) (Math.random() * 20D)) - 10 << 8) + ((238 + (int) (Math.random() * 20D)) - 10);
        int doorColor = (238 + (int) (Math.random() * 20D)) - 10 << 16;
        imageMinimap.prepare();

        for (int y = 1; y < 103; y++) {
            for (int x = 1; x < 103; x++) {
                if ((renderFlags[z][x][y] & 0x18) == 0) {
                    drawMinimapTile(x, y, z, wallColor, doorColor);
                }
                if (z < 3 && (renderFlags[z + 1][x][y] & 8) != 0) {
                    drawMinimapTile(x, y, z + 1, wallColor, doorColor);
                }
            }

        }

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

    public static Archive getArchive(String caption, int archiveIndex, String archiveName, int archiveCrc, int percent) {
        byte[] data = null;

        try {
            if (cache[0] != null) {
                data = cache[0].get(archiveIndex);
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }

        return new Archive(new Buffer(data));
    }

    public static int getLandZ(int x, int y, int plane) {
        int localX = x >> 7;
        int localY = y >> 7;

        if (localX < 0 || localY < 0 || localX > 103 || localY > 103) {
            return 0;
        }

        int z = plane;

        if (z < 3 && (renderFlags[1][localX][localY] & 2) == 2) {
            z++;
        }

        int tileX = x & 0x7f;
        int tileY = y & 0x7f;
        int i2 = heightMap[z][localX][localY] * (128 - tileX) + heightMap[z][localX + 1][localY] * tileX >> 7;
        int j2 = heightMap[z][localX][localY + 1] * (128 - tileX) + heightMap[z][localX + 1][localY + 1] * tileX >> 7;
        return ((i2 * (128 - tileY) + (j2 * tileY)) >> 7);
    }

    public static int getMaxVisiblePlane() {
        int occlusionPlane = 3;

        // Once the pitch is below this, start checking to see if the camera is
        // within a roof tile.
        if (Camera.pitch < 310) {
            int camLocalX = Camera.x >> 7;
            int camLocalY = Camera.y >> 7;
            int localX = self.sceneX >> 7;
            int localY = self.sceneY >> 7;

            if ((renderFlags[plane][camLocalX][camLocalY] & 4) != 0) {
                occlusionPlane = plane;
            }

            int deltaX;
            if (localX > camLocalX) {
                deltaX = localX - camLocalX;
            } else {
                deltaX = camLocalX - localX;
            }

            int deltaY;
            if (localY > camLocalY) {
                deltaY = localY - camLocalY;
            } else {
                deltaY = camLocalY - localY;
            }

            if (deltaX > deltaY) {
                int i2 = (deltaY * 0x10000) / deltaX;
                int k2 = 0x8000;
                while (camLocalX != localX) {
                    if (camLocalX < localX) {
                        camLocalX++;
                    } else if (camLocalX > localX) {
                        camLocalX--;
                    }

                    if ((renderFlags[plane][camLocalX][camLocalY] & 4) != 0) {
                        occlusionPlane = plane;
                    }

                    k2 += i2;
                    if (k2 >= 0x10000) {
                        k2 -= 0x10000;

                        if (camLocalY < localY) {
                            camLocalY++;
                        } else if (camLocalY > localY) {
                            camLocalY--;
                        }

                        if ((renderFlags[plane][camLocalX][camLocalY] & 4) != 0) {
                            occlusionPlane = plane;
                        }
                    }
                }
            } else {
                int j2 = (deltaX * 0x10000) / deltaY;
                int l2 = 0x8000;

                while (camLocalY != localY) {
                    if (camLocalY < localY) {
                        camLocalY++;
                    } else if (camLocalY > localY) {
                        camLocalY--;
                    }

                    if ((renderFlags[plane][camLocalX][camLocalY] & 4) != 0) {
                        occlusionPlane = plane;
                    }

                    l2 += j2;
                    if (l2 >= 0x10000) {
                        l2 -= 0x10000;

                        if (camLocalX < localX) {
                            camLocalX++;
                        } else if (camLocalX > localX) {
                            camLocalX--;
                        }

                        if ((renderFlags[plane][camLocalX][camLocalY] & 4) != 0) {
                            occlusionPlane = plane;
                        }
                    }
                }
            }
        }

        if ((renderFlags[plane][self.sceneX >> 7][self.sceneY >> 7] & 4) != 0) {
            occlusionPlane = plane;
        }

        return occlusionPlane;
    }

    public static int getMaxVisiblePlaneCinematic() {
        int ground_z = getLandZ(Camera.x, Camera.y, plane);
        if (ground_z - Camera.z < 800 && (renderFlags[plane][Camera.x >> 7][Camera.y >> 7] & 4) != 0) {
            return plane;
        } else {
            return 3;
        }
    }

    public static int getSceneLoadState(byte byte0) {
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
            byte landscapePayload[] = chunkLandscapePayload[i];

            if (landscapePayload != null) {
                int x = 10;
                int y = 10;

                if (!serverSentChunk) {
                    x = (chunkCoords[i] >> 8) * 64 - mapBaseX;
                    y = (chunkCoords[i] & 0xff) * 64 - mapBaseY;
                }

                valid &= Scene.locsFullyLoaded(x, y, landscapePayload);
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
            retrieveScene(true);
            out.writeOpcode(121);
            return 0;
        }
    }

    public static void handle() {
        if (logoutCycle > 0) {
            logoutCycle--;
        }

        for (int i = 0; i < 5; i++) {
            if (!handleNet()) {
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

        if (camInfoCycle > 0) {
            camInfoCycle--;
        }

        if (Key.LEFT.is_down() || Key.RIGHT.is_down() || Key.UP.is_down() || Key.DOWN.is_down()) {
            sendCamInfo = true;
        }

        if (sendCamInfo && camInfoCycle <= 0) {
            camInfoCycle = 100;
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

        handleScene();
        handleLocCreation();
        handleAudio();
        netCycle++;

        if (netCycle > 750) {
            handleConnectionLost();
        }

        handlePlayers();
        handleActors();
        handleSpoken();
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

                int optionCount = Menu.count;

                if (dragging && dragCycle >= 5) {
                    hoveredSlotWidget = -1;

                    Menu.handle();

                    if (hoveredSlotWidget == dragWidget && hoveredSlot != dragSlot) {
                        Widget w = Widget.instance[dragWidget];
                        int type = 0;

                        if (anInt913 == 1 && w.actionType == 206) {
                            type = 1;
                        }

                        if (w.itemIndex[hoveredSlot] <= 0) {
                            type = 0;
                        }

                        if (w.itemsSwappable) {
                            int oldSlot = dragSlot;
                            int newSlot = hoveredSlot;
                            w.itemIndex[newSlot] = w.itemIndex[oldSlot];
                            w.itemCount[newSlot] = w.itemCount[oldSlot];
                            w.itemIndex[oldSlot] = -1;
                            w.itemCount[oldSlot] = 0;
                        } else if (type == 1) {
                            int oldSlot = dragSlot;
                            for (int new_slot = hoveredSlot; oldSlot != new_slot; ) {
                                if (oldSlot > new_slot) {
                                    w.swapSlots(oldSlot, oldSlot - 1);
                                    oldSlot--;
                                } else if (oldSlot < new_slot) {
                                    w.swapSlots(oldSlot, oldSlot + 1);
                                    oldSlot++;
                                }
                            }

                        } else {
                            w.swapSlots(dragSlot, hoveredSlot);
                        }

                        out.writeOpcode(214);
                        out.writeLeShortA(dragWidget);
                        out.writeByteC(type);
                        out.writeLeShortA(dragSlot);
                        out.writeLeShort(hoveredSlot);
                    }
                } else if (mouseButtonSetting == 1 && optionCount > 2) {
                    Menu.show();
                } else if (optionCount > 0) {
                    handleMenuOption(optionCount - 1);
                }

                redrawCycle = 10;
                Mouse.clickButton = 0;
            }
        }

        if (Landscape.clickLocalX != -1) {
            int localX = Landscape.clickLocalX;
            int localY = Landscape.clickLocalY;

            boolean validPath = walkTo(0, 0, 0, self.pathX[0], self.pathY[0], localX, localY, 0, 0, 0, true);
            Landscape.clickLocalX = -1;

            if (validPath) {
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 1;
                crossCycle = 0;
            }
        }

        if (Mouse.clickButton == 1 && Chat.getMessage(Chat.MESSAGE_INPUT) != null) {
            Chat.setMessage(Chat.MESSAGE_INPUT, null);
            Mouse.clickButton = 0;
        }

        handleWidgetMouse();
        handleMinimapMouse();
        handleTabMouse();

        Chat.Settings.handle();

        if (Mouse.isDragging(1) || Mouse.clicked(1)) {
            clickCycle++;
        }

        if (sceneState == 2) {
            handleCamera();
        }

        if (sceneState == 2 && camCinemaMode) {
            handleCinematicCamera();
        }

        for (int i1 = 0; i1 < 5; i1++) {
            anIntArray1030[i1]++;
        }

        handleKeyboard();

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
                camXOff += xCamOffMod;
            }

            if ((rnd & 2) == 2) {
                camYOff += yCamOffMod;
            }

            if ((rnd & 4) == 4) {
                camPitchOff += Camera.pitchModifier;
            }
        }

        if (camXOff < -50) {
            xCamOffMod = 2;
        }

        if (camXOff > 50) {
            xCamOffMod = -2;
        }

        if (camYOff < -55) {
            yCamOffMod = 2;
        }

        if (camYOff > 55) {
            yCamOffMod = -2;
        }

        if (camPitchOff < -40) {
            Camera.pitchModifier = 1;
        }

        if (camPitchOff > 40) {
            Camera.pitchModifier = -1;
        }

        rndCycle2++;

        if (rndCycle2 > 500) {
            rndCycle2 = 0;
            int rnd = (int) (Math.random() * 8D);

            if ((rnd & 1) == 1) {
                camYawOff += Camera.yawModifier;
            }

            if ((rnd & 2) == 2) {
                mapZoomOffset += mapZoomModifier;
            }
        }

        if (camYawOff < -60) {
            Camera.yawModifier = 2;
        }

        if (camYawOff > 60) {
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
            }
        } catch (IOException _ex) {
            handleConnectionLost();
        } catch (Exception exception) {
            netDisconnect();
        }
    }

    public static void handleActorMenuOptions(ActorConfig ac, int x, int y, int index) {
        if (Menu.count >= 400) {
            return;
        }

        if (ac.override_index != null) {
            ac = ac.getOverridingConfig();
        }

        if (ac == null) {
            return;
        }

        if (!ac.interactable) {
            return;
        }

        String s = ac.name;

        if (ac.combatLevel != 0) {
            s = s + RSColor.getLevelTag(ac.combatLevel);
        }

        if (selectedItem) {
            Menu.add("Use " + selectedItemName + " with @yel@" + s, 582, x, y, index);
            return;
        }

        if (selectedWidget) {
            if (BitUtils.flagged(selectedMask, 0x2)) {
                Menu.add(selectedTooltip + " @yel@" + s, 413, x, y, index);
            }
        } else {
            if (ac.action != null) {
                for (int i = 4; i >= 0; i--) {
                    if (ac.action[i] != null && !ac.action[i].equalsIgnoreCase("attack")) {
                        Menu.add(ac.action[i] + " @yel@" + s, Action.ACTOR[i], x, y, index);
                    }
                }

            }
            if (ac.action != null) {
                for (int i = 4; i >= 0; i--) {
                    if (ac.action[i] != null && ac.action[i].equalsIgnoreCase("attack")) {
                        int off = 0;
                        if (ac.combatLevel > self.combatLevel) {
                            off = 2000;
                        }
                        Menu.add(ac.action[i] + " @yel@" + s, Action.ACTOR[i] + off, x, y, index);
                    }
                }
            }
            Menu.add("Examine @yel@" + s, 1025, x, y, index);
        }
    }

    public static void handleActors() {
        for (int i = 0; i < actorCount; i++) {
            int actorIndice = actorIndices[i];
            Actor a = actors[actorIndice];
            if (a != null) {
                handleEntity(a);
            }
        }
    }

    public static void handleAudio() {
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
                playMusicInstantly(music.volume0, music.currentSong, 0, 0, false);
            }
        }
    }

    public static void handleCamera() {
        try {
            int sceneX = self.sceneX;
            int sceneY = self.sceneY;

            if (chaseCamX - sceneX < -500 || chaseCamX - sceneX > 500 || chaseCamY - sceneY < -500 || chaseCamY - sceneY > 500) {
                chaseCamX = sceneX;
                chaseCamY = sceneY;
            }

            if (chaseCamX != sceneX) {
                chaseCamX += (sceneX - chaseCamX) / 16;
            }

            if (chaseCamY != sceneY) {
                chaseCamY += (sceneY - chaseCamY) / 16;
            }

            if (Key.LEFT.is_down()) {
                chaseCamYawMod += (-24 - chaseCamYawMod) / 2;
            } else if (Key.RIGHT.is_down()) {
                chaseCamYawMod += (24 - chaseCamYawMod) / 2;
            } else {
                chaseCamYawMod /= 2;
            }

            if (Key.UP.is_down()) {
                chaseCamPitchMod += (12 - chaseCamPitchMod) / 2;
            } else if (Key.DOWN.is_down()) {
                chaseCamPitchMod += (-12 - chaseCamPitchMod) / 2;
            } else {
                chaseCamPitchMod /= 2;
            }

            chaseCamYaw = chaseCamYaw + chaseCamYawMod / 2 & 0x7ff;
            chaseCamPitch += chaseCamPitchMod / 2;

            if (chaseCamPitch < Camera.MIN_PITCH) {
                chaseCamPitch = Camera.MIN_PITCH;
            }

            if (chaseCamPitch > Camera.MAX_PITCH) {
                chaseCamPitch = Camera.MAX_PITCH;
            }

            int localX = chaseCamX >> 7;
            int localY = chaseCamY >> 7;
            int localPlane = getLandZ(chaseCamX, chaseCamY, plane);
            int highestZ = 0;

            if (localX > 3 && localY > 3 && localX < 100 && localY < 100) {
                for (int x = localX - 4; x <= localX + 4; x++) {
                    for (int y = localY - 4; y <= localY + 4; y++) {
                        int plane = Game.plane;

                        if (plane < 3 && (renderFlags[1][x][y] & 2) == 2) {
                            plane++;
                        }

                        int lastZ = localPlane - heightMap[plane][x][y];
                        if (lastZ > highestZ) {
                            highestZ = lastZ;
                        }
                    }

                }

            }

            int j2 = highestZ * 192;

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
            }
        } catch (Exception _ex) {
            Signlink.error("glfc_ex " + self.sceneX + "," + self.sceneY + "," + chaseCamX + "," + chaseCamY + "," + loadedRegionX + "," + loadedRegionY + "," + mapBaseX + "," + mapBaseY);
            throw new RuntimeException("eek");
        }
    }

    public static void handleCinematicCamera() {
        int camX = camCinemaDestX * 128 + 64;
        int camY = camCinemaDestY * 128 + 64;
        int camZ = getLandZ(camX, camY, plane) - camCinemaDestZ;

        if (Camera.x < camX) {
            Camera.x += camCinemaBaseSpeed + ((camX - Camera.x) * camCinemaSpeed) / 1000;
            if (Camera.x > camX) {
                Camera.x = camX;
            }
        }

        if (Camera.x > camX) {
            Camera.x -= camCinemaBaseSpeed + ((Camera.x - camX) * camCinemaSpeed) / 1000;
            if (Camera.x < camX) {
                Camera.x = camX;
            }
        }

        if (Camera.z < camZ) {
            Camera.z += camCinemaBaseSpeed + ((camZ - Camera.z) * camCinemaSpeed) / 1000;
            if (Camera.z > camZ) {
                Camera.z = camZ;
            }
        }

        if (Camera.z > camZ) {
            Camera.z -= camCinemaBaseSpeed + ((Camera.z - camZ) * camCinemaSpeed) / 1000;
            if (Camera.z < camZ) {
                Camera.z = camZ;
            }
        }

        if (Camera.y < camY) {
            Camera.y += camCinemaBaseSpeed + ((camY - Camera.y) * camCinemaSpeed) / 1000;
            if (Camera.y > camY) {
                Camera.y = camY;
            }
        }

        if (Camera.y > camY) {
            Camera.y -= camCinemaBaseSpeed + ((Camera.y - camY) * camCinemaSpeed) / 1000;
            if (Camera.y < camY) {
                Camera.y = camY;
            }
        }

        camX = camCinemaAimX * 128 + 64;
        camY = camCinemaAimY * 128 + 64;
        camZ = getLandZ(camX, camY, plane) - camCinemaAimZ;

        int xDiff = camX - Camera.x;
        int zDiff = camZ - Camera.z;
        int yDiff = camY - Camera.y;
        int length = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        int camPitch = (int) (Math.atan2(zDiff, length) * 325.94900000000001D) & 0x7ff;
        int camYaw = (int) (Math.atan2(xDiff, yDiff) * -325.94900000000001D) & 0x7ff;

        if (camPitch < 128) {
            camPitch = 128;
        }

        if (camPitch > 383) {
            camPitch = 383;
        }

        if (Camera.pitch < camPitch) {
            Camera.pitch += camCinemaRotBase + ((camPitch - Camera.pitch) * camCinemaRotModifier) / 1000;
            if (Camera.pitch > camPitch) {
                Camera.pitch = camPitch;
            }
        }

        if (Camera.pitch > camPitch) {
            Camera.pitch -= camCinemaRotBase + ((Camera.pitch - camPitch) * camCinemaRotModifier) / 1000;
            if (Camera.pitch < camPitch) {
                Camera.pitch = camPitch;
            }
        }

        int yawDiff = camYaw - Camera.yaw;

        if (yawDiff > 1024) {
            yawDiff -= 2048;
        }

        if (yawDiff < -1024) {
            yawDiff += 2048;
        }

        if (yawDiff > 0) {
            Camera.yaw += camCinemaRotBase + (yawDiff * camCinemaRotModifier) / 1000;
            Camera.yaw &= 0x7ff;
        }
        if (yawDiff < 0) {
            Camera.yaw -= camCinemaRotBase + (-yawDiff * camCinemaRotModifier) / 1000;
            Camera.yaw &= 0x7ff;
        }

        int yawDest = camYaw - Camera.yaw;

        if (yawDest > 1024) {
            yawDest -= 2048;
        }

        if (yawDest < -1024) {
            yawDest += 2048;
        }

        if (yawDest < 0 && yawDiff > 0 || yawDest > 0 && yawDiff < 0) {
            Camera.yaw = camYaw;
        }
    }

    public static void handleConnectionLost() {
        if (logoutCycle > 0) {
            netDisconnect();
            return;
        }

        BitmapFont.NORMAL.draw("Connection lost", 256, 143, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
        BitmapFont.NORMAL.draw("Please wait - attempting to reestablish", 256, 158, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
        minimapState = 0;
        mapMarkerX = 0;
        loggedIn = false;
        reconnectionAttempts = 0;
        netLogin(username, password, true);

        if (!loggedIn) {
            netDisconnect();
        }

        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public static void handleEntity(Entity e) {
        if (e.sceneX < 128 || e.sceneY < 128 || e.sceneX >= 13184 || e.sceneY >= 13184) {
            e.seqIndex = -1;
            e.spotanimIndex = -1;
            e.moveCycleEnd = 0;
            e.moveCycleStart = 0;
            e.sceneX = e.pathX[0] * 128 + e.size * 64;
            e.sceneY = e.pathY[0] * 128 + e.size * 64;
            e.resetPositions();
        }

        if (e == self && (e.sceneX < 1536 || e.sceneY < 1536 || e.sceneX >= 11776 || e.sceneY >= 11776)) {
            e.seqIndex = -1;
            e.spotanimIndex = -1;
            e.moveCycleEnd = 0;
            e.moveCycleStart = 0;
            e.sceneX = e.pathX[0] * 128 + e.size * 64;
            e.sceneY = e.pathY[0] * 128 + e.size * 64;
            e.resetPositions();
        }

        if (e.moveCycleEnd > loopCycle) {
            handleEntityLateMovement(e);
        } else if (e.moveCycleStart >= loopCycle) {
            handleEntityMovementVariables(e);
        } else {
            handleEntityMovement(e);
        }

        handleEntityRotation(e);
        handleEntitySequence(e);
    }

    public static void handleEntityLateMovement(Entity e) {
        int dt = e.moveCycleEnd - loopCycle;
        int destX = e.moveStartX * 128 + e.size * 64;
        int destY = e.moveStartY * 128 + e.size * 64;

        e.sceneX += (destX - e.sceneX) / dt;
        e.sceneY += (destY - e.sceneY) / dt;
        e.resyncWalkCycle = 0;

        if (e.moveDirection == 0) {
            e.destRotation = 1024;
        } else if (e.moveDirection == 1) {
            e.destRotation = 1536;
        } else if (e.moveDirection == 2) {
            e.destRotation = 0;
        } else if (e.moveDirection == 3) {
            e.destRotation = 512;
        }
    }

    public static void handleEntityMovement(Entity e) {
        e.moveSeqIndex = e.standAnimation;

        if (e.pathPosition == 0) {
            e.resyncWalkCycle = 0;
            return;
        }

        if (e.seqIndex != -1 && e.seqDelayCycle == 0) {
            Sequence a = Sequence.instance[e.seqIndex];
            if (e.stillPathPosition > 0 && a.speedFlag == 0) {
                e.resyncWalkCycle++;
                return;
            }
            if (e.stillPathPosition <= 0 && a.walkFlag == 0) {
                e.resyncWalkCycle++;
                return;
            }
        }

        int sceneX = e.sceneX;
        int sceneY = e.sceneY;
        int destX = e.pathX[e.pathPosition - 1] * 128 + e.size * 64;
        int destY = e.pathY[e.pathPosition - 1] * 128 + e.size * 64;

        if (destX - sceneX > 256 || destX - sceneX < -256 || destY - sceneY > 256 || destY - sceneY < -256) {
            e.sceneX = destX;
            e.sceneY = destY;
            return;
        }

        if (sceneX < destX) {
            if (sceneY < destY) {
                e.destRotation = 1280;
            } else if (sceneY > destY) {
                e.destRotation = 1792;
            } else {
                e.destRotation = 1536;
            }
        } else if (sceneX > destX) {
            if (sceneY < destY) {
                e.destRotation = 768;
            } else if (sceneY > destY) {
                e.destRotation = 256;
            } else {
                e.destRotation = 512;
            }
        } else if (sceneY < destY) {
            e.destRotation = 1024;
        } else {
            e.destRotation = 0;
        }

        int angle_diff = e.destRotation - e.rotation & 0x7ff;

        if (angle_diff > 1024) {
            angle_diff -= 2048;
        }

        int index = e.turn180Animation;

        if (angle_diff >= -256 && angle_diff <= 256) {
            index = e.walkAnimation;
        } else if (angle_diff >= 256 && angle_diff < 768) {
            index = e.turnLAnimation;
        } else if (angle_diff >= -768 && angle_diff <= -256) {
            index = e.turnRAnimation;
        }

        if (index == -1) {
            index = e.walkAnimation;
        }

        e.moveSeqIndex = index;

        int speed = 4;

        if (e.rotation != e.destRotation && e.faceEntity == -1 && e.turnSpeed != 0) {
            speed = 2;
        }

        if (e.pathPosition > 2) {
            speed = 6;
        }

        if (e.pathPosition > 3) {
            speed = 8;
        }

        if (e.resyncWalkCycle > 0 && e.pathPosition > 1) {
            speed = 8;
            e.resyncWalkCycle--;
        }

        if (e.pathRun[e.pathPosition - 1]) {
            speed <<= 1;
        }

        if (speed >= 8 && e.moveSeqIndex == e.walkAnimation && e.run_animation != -1) {
            e.moveSeqIndex = e.run_animation;
        }

        if (sceneX < destX) {
            e.sceneX += speed;
            if (e.sceneX > destX) {
                e.sceneX = destX;
            }
        } else if (sceneX > destX) {
            e.sceneX -= speed;
            if (e.sceneX < destX) {
                e.sceneX = destX;
            }
        }

        if (sceneY < destY) {
            e.sceneY += speed;
            if (e.sceneY > destY) {
                e.sceneY = destY;
            }
        } else if (sceneY > destY) {
            e.sceneY -= speed;
            if (e.sceneY < destY) {
                e.sceneY = destY;
            }
        }

        if (e.sceneX == destX && e.sceneY == destY) {
            e.pathPosition--;
            if (e.stillPathPosition > 0) {
                e.stillPathPosition--;
            }
        }
    }

    public static void handleEntityMovementVariables(Entity e) {
        if (e.moveCycleStart == loopCycle || e.seqIndex == -1 || e.seqDelayCycle != 0 || e.seqCycle + 1 > Sequence.instance[e.seqIndex].getFrameLength(e.seqFrame)) {
            int walkDt = e.moveCycleStart - e.moveCycleEnd;
            int dt = loopCycle - e.moveCycleEnd;
            int startX = e.moveStartX * 128 + e.size * 64;
            int startY = e.moveStartY * 128 + e.size * 64;
            int endX = e.moveEndX * 128 + e.size * 64;
            int endY = e.moveEndY * 128 + e.size * 64;
            e.sceneX = (startX * (walkDt - dt) + endX * dt) / walkDt;
            e.sceneY = (startY * (walkDt - dt) + endY * dt) / walkDt;
        }

        e.resyncWalkCycle = 0;

        if (e.moveDirection == 0) {
            e.destRotation = 1024;
        }

        if (e.moveDirection == 1) {
            e.destRotation = 1536;
        }

        if (e.moveDirection == 2) {
            e.destRotation = 0;
        }

        if (e.moveDirection == 3) {
            e.destRotation = 512;
        }

        e.rotation = e.destRotation;
    }

    public static void handleEntityRotation(Entity e) {
        if (e.turnSpeed == 0) {
            return;
        }

        if (e.faceEntity != -1 && e.faceEntity < 32768) {
            Actor a = actors[e.faceEntity];

            if (a != null) {
                int dx = e.sceneX - a.sceneX;
                int dy = e.sceneY - a.sceneY;

                if (dx != 0 || dy != 0) {
                    e.destRotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if (e.faceEntity >= 32768) {
            int playerIndex = e.faceEntity - 32768;

            if (playerIndex == localPlayerIndex) {
                playerIndex = MAX_PLAYER_INDEX;
            }

            Player p = players[playerIndex];

            if (p != null) {
                int dx = e.sceneX - p.sceneX;
                int dy = e.sceneY - p.sceneY;

                if (dx != 0 || dy != 0) {
                    e.destRotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if ((e.faceX != 0 || e.faceY != 0) && (e.pathPosition == 0 || e.resyncWalkCycle > 0)) {
            int dx = e.sceneX - (e.faceX - mapBaseX - mapBaseX) * 64;
            int dy = e.sceneY - (e.faceY - mapBaseY - mapBaseY) * 64;

            if (dx != 0 || dy != 0) {
                e.destRotation = (int) (Math.atan2(dx, dy) * 325.94900000000001D) & 0x7ff;
            }

            e.faceX = 0;
            e.faceY = 0;
        }

        int da = e.destRotation - e.rotation & 0x7ff;

        if (da != 0) {
            if (da < e.turnSpeed || da > 2048 - e.turnSpeed) {
                e.rotation = e.destRotation;
            } else if (da > 1024) {
                e.rotation -= e.turnSpeed;
            } else {
                e.rotation += e.turnSpeed;
            }

            e.rotation &= 0x7ff;

            if (e.moveSeqIndex == e.standAnimation && e.rotation != e.destRotation) {
                if (e.stand_turn_animation != -1) {
                    e.moveSeqIndex = e.stand_turn_animation;
                    return;
                }
                e.moveSeqIndex = e.walkAnimation;
            }
        }
    }

    public static void handleEntitySequence(Entity e) {
        e.canRotate = false;

        if (e.moveSeqIndex != -1) {
            Sequence s = Sequence.instance[e.moveSeqIndex];
            e.moveSeqCycle++;

            if (e.moveSeqFrame < s.frameCount && e.moveSeqCycle > s.getFrameLength(e.moveSeqFrame)) {
                e.moveSeqCycle = 0;
                e.moveSeqFrame++;
            }

            if (e.moveSeqFrame >= s.frameCount) {
                e.moveSeqCycle = 0;
                e.moveSeqFrame = 0;
            }
        }

        if (e.spotanimIndex != -1 && loopCycle >= e.spotanimCycleEnd) {
            if (e.spotanimFrame < 0) {
                e.spotanimFrame = 0;
            }

            Sequence s = null;

            if (e.spotanimIndex >= 0 && e.spotanimIndex < SpotAnimConfig.count) {
                s = SpotAnimConfig.instance[e.spotanimIndex].seq;
            }

            if (s != null) {
                for (e.spotanimCycle++; e.spotanimFrame < s.frameCount && e.spotanimCycle > s.getFrameLength(e.spotanimFrame); e.spotanimFrame++) {
                    e.spotanimCycle -= s.getFrameLength(e.spotanimFrame);
                }

                if (e.spotanimFrame >= s.frameCount && (e.spotanimFrame < 0 || e.spotanimFrame >= s.frameCount)) {
                    e.spotanimIndex = -1;
                }
            } else {
                System.out.println("Error spotanim " + e.spotanimIndex + " doesn't exist!");
                e.spotanimIndex = -1;
            }
        }

        if (e.seqIndex != -1 && e.seqDelayCycle <= 1) {
            if (e.seqIndex < 0 || e.seqIndex >= Sequence.instance.length) {
                System.out.println("Error: sequence " + e.seqIndex + " doesn't exist!");
                e.seqIndex = -1;
            } else {
                Sequence s = Sequence.instance[e.seqIndex];

                if (s.speedFlag == 1 && e.stillPathPosition > 0 && e.moveCycleEnd <= loopCycle && e.moveCycleStart < loopCycle) {
                    e.seqDelayCycle = 1;
                    return;
                }
            }
        }

        if (e.seqIndex != -1 && e.seqDelayCycle == 0) {
            if (e.seqIndex < 0 || e.seqIndex >= Sequence.instance.length) {
                System.out.println("Error: sequence " + e.seqIndex + " doesn't exist!");
                e.seqIndex = -1;
            } else {
                Sequence s = Sequence.instance[e.seqIndex];

                for (e.seqCycle++; e.seqFrame < s.frameCount && e.seqCycle > s.getFrameLength(e.seqFrame); e.seqFrame++) {
                    e.seqCycle -= s.getFrameLength(e.seqFrame);
                }

                if (e.seqFrame >= s.frameCount) {
                    e.seqFrame -= s.padding;
                    e.seqResetCycle++;

                    if (e.seqResetCycle >= s.resetCycle) {
                        e.seqIndex = -1;
                    }

                    if (e.seqFrame < 0 || e.seqFrame >= s.frameCount) {
                        e.seqIndex = -1;
                    }
                }
                e.canRotate = s.canRotate;
            }
        }

        if (e.seqDelayCycle > 0) {
            e.seqDelayCycle--;
        }
    }

    public static void handleKeyboard() {
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
                Chat.handleKeyboard(key);
            }
        } while (true);
    }

    public static void handleLoc(SpawntLoc sl) {
        int locUid = 0;
        int locIndex = -1;
        int locType = 0;
        int locRotation = 0;

        if (sl.classType == 0) {
            locUid = landscape.getWallUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 1) {
            locUid = landscape.getWallDecorationUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 2) {
            locUid = landscape.getLocUid(sl.plane, sl.x, sl.y);
        }

        if (sl.classType == 3) {
            locUid = landscape.getGroundDecorationUid(sl.plane, sl.x, sl.y);
        }

        if (locUid != 0) {
            int uid = landscape.getArrangement(sl.plane, sl.x, sl.y, locUid);
            locIndex = locUid >> 14 & 0x7fff;
            locType = uid & 0x1f;
            locRotation = uid >> 6;
        }

        sl.index = locIndex;
        sl.type = locType;
        sl.rotation = locRotation;
    }

    public static void handleLocCreation() {
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

    public static void handleLocs() {
        SpawntLoc sl = (SpawntLoc) spawnedLocs.top();
        for (; sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
            if (sl.cycle == -1) {
                sl.spawnCycle = 0;
                handleLoc(sl);
            } else {
                sl.detach();
            }
        }
    }

    public static void handleMenuOption(int option) {
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
            String sb = "Action " + action + ": " + Arrays.toString(new int[]{param1, param2, param3});
            System.out.println(sb);
        }

        if (action == 582) {
            Actor a = actors[param3];
            if (a != null) {
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
            boolean flag1 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag1) {
                flag1 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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

        if (action == 62 && interactWithLoc(param2, param1, param3)) {
            out.writeOpcode(192);
            out.writeShort(selectedItemWidget);
            out.writeLeShort(param3 >> 14 & 0x7fff);
            out.writeLeShortA(param2 + mapBaseY);
            out.writeLeShort(selectedItemSlot);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShort(selectedItemIndex);
        }

        if (action == 511) {
            boolean flag2 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag2) {
                flag2 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 315) {
            if (param2 == 5985) {
                reportAbuseMute = !reportAbuseMute;
            } else {
                Widget w = Widget.get(param2);
                boolean valid = true;

                if (w.actionType > 0) {
                    valid = handleWidget(w);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
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
            interactWithLoc(param2, param1, param3);
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
            } else if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 337 || action == 42 || action == 792 || action == 322) {
            String s = Menu.getCaption(option);
            int i = s.indexOf("@whi@");
            if (i != -1) {
                long l = JString.toLong(s.substring(i + 5).trim());
                switch (action) {
                    case 337: {
                        friendAdd(l);
                        break;
                    }
                    case 792: {
                        friendRemove(l);
                        break;
                    }
                    case 42: {
                        ignoreAdd(l);
                        break;
                    }
                    case 322: {
                        ignoreRemove(l);
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 484 || action == 6) {
            String s = Menu.getCaption(option);
            int j = s.indexOf("@whi@");

            if (j != -1) {
                s = s.substring(j + 5).trim();
                String name = JString.getFormattedString(JString.toLong(s));
                boolean found = false;

                for (int i = 0; i < playerCount; i++) {
                    Player p = players[playerIndices[i]];

                    if (p == null || p.name == null || !p.name.equalsIgnoreCase(name)) {
                        continue;
                    }

                    walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);

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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 626) {
            Widget w = Widget.instance[param2];
            selectedWidget = true;
            selectedWidgetIndex = param2;
            selectedMask = w.optionAction;
            selectedItem = false;
            Sidebar.draw = true;

            String prefix = w.optionPrefix;

            if (prefix.indexOf(' ') != -1) {
                prefix = prefix.substring(0, prefix.indexOf(' '));
            }

            String suffix = w.optionPrefix;

            if (suffix.indexOf(' ') != -1) {
                suffix = suffix.substring(suffix.indexOf(' ') + 1);
            }

            selectedTooltip = prefix + ' ' + w.optionSuffix + ' ' + suffix;

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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 27) {
            Player p = players[param3];
            if (p != null) {
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(73);
                out.writeLeShort(param3);
            }
        }

        if (action == 213) {
            boolean flag3 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag3) {
                flag3 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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
            } else if (Widget.instance[param2].parent == Chat.getOverlay()) {
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
            } else if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 652) {
            boolean flag4 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag4) {
                flag4 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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
            boolean flag5 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag5) {
                flag5 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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

                if (settings[setting] != w.script[0].compareValue) {
                    settings[setting] = w.script[0].compareValue;
                    handleVarp(setting);
                    Sidebar.draw = true;
                }
            }
        }

        if (action == 225) {
            Actor a = actors[param3];
            if (a != null) {
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
            closeWidgets();
        }

        if (action == 1025) {
            Actor a = actors[param3];

            if (a != null) {
                ActorConfig c = a.config;

                if (c.override_index != null) {
                    c = c.getOverridingConfig();
                }

                if (c != null) {
                    Chat.put(c.description != null ? c.description : "It's a " + c.name + ".");
                }
            }
        }

        if (action == 900) {
            interactWithLoc(param2, param1, param3);
            out.writeOpcode(252);
            out.writeLeShortA(param3 >> 14 & 0x7fff);
            out.writeLeShort(param2 + mapBaseY);
            out.writeShortA(param1 + mapBaseX);
        }

        if (action == 412) {
            Actor a = actors[param3];
            if (a != null) {
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(139);
                out.writeLeShort(param3);
            }
        }

        if (action == 956 && interactWithLoc(param2, param1, param3)) {
            out.writeOpcode(35);
            out.writeLeShort(param1 + mapBaseX);
            out.writeShortA(selectedWidgetIndex);
            out.writeShortA(param2 + mapBaseY);
            out.writeLeShort(param3 >> 14 & 0x7fff);
        }

        if (action == 567) {
            boolean flag6 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag6) {
                flag6 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 606) {
            String s = Menu.getCaption(option);
            int j = s.indexOf("@whi@");

            if (j != -1) {
                if (widgetOverlay == -1) {
                    closeWidgets();
                    reportAbuseInput = s.substring(j + 5).trim();
                    reportAbuseMute = false;

                    for (int i = 0; i < Widget.instance.length; i++) {
                        if (Widget.instance[i] == null || Widget.instance[i].actionType != 600) {
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
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], p.pathX[0], p.pathY[0], 0, 0, 0, false);
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
            int j = s.indexOf("@whi@");
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
                    Chat.set(Chat.State.SEND_MESSAGE, "Enter a message to send to " + friendName[friend_index]);
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

            if (Widget.instance[param2].parent == Chat.getOverlay()) {
                clickArea = 3;
            }
        }

        if (action == 478) {
            Actor a = actors[param3];
            if (a != null) {
                walkTo(2, 1, 1, self.pathX[0], self.pathY[0], a.pathX[0], a.pathY[0], 0, 0, 0, false);
                crossX = Mouse.clickX;
                crossY = Mouse.clickY;
                crossType = 2;
                crossCycle = 0;
                out.writeOpcode(18);
                out.writeLeShort(param3);
            }
        }

        if (action == 113) {
            interactWithLoc(param2, param1, param3);
            out.writeOpcode(70);
            out.writeLeShort(param1 + mapBaseX);
            out.writeShort(param2 + mapBaseY);
            out.writeLeShortA(param3 >> 14 & 0x7fff);
        }

        if (action == 872) {
            interactWithLoc(param2, param1, param3);
            out.writeOpcode(234);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShortA(param3 >> 14 & 0x7fff);
            out.writeLeShortA(param2 + mapBaseY);
        }

        if (action == 502) {
            interactWithLoc(param2, param1, param3);
            out.writeOpcode(132);
            out.writeLeShortA(param1 + mapBaseX);
            out.writeShort(param3 >> 14 & 0x7fff);
            out.writeShortA(param2 + mapBaseY);
        }

        if (action == 1125) {
            ObjConfig oc = ObjConfig.get(param3);
            Widget w = Widget.instance[param2];

            if (w != null && oc != null) {
                if (w.itemCount[param1] >= 100_000) {
                    Chat.put(w.itemCount[param1] + " x " + oc.name);
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
                handleVarp(pointer);
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
            boolean flag7 = walkTo(2, 0, 0, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
            if (!flag7) {
                flag7 = walkTo(2, 1, 1, self.pathX[0], self.pathY[0], param1, param2, 0, 0, 0, false);
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

    public static void handleMessageStatus() {
        messageStatus = 0;

        int x = (self.sceneX >> 7) + mapBaseX;
        int y = (self.sceneY >> 7) + mapBaseY;

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

    public static void handleMinimapMouse() {
        if (minimapState != 0) {
            return;
        }
        if (Mouse.clickButton == 1) {
            int x = Mouse.clickX - 25 - 550;
            int y = Mouse.clickY - 5 - 4;
            if (x >= 0 && y >= 0 && x < 146 && y < 151) {
                x -= 73;
                y -= 75;
                int angle = chaseCamYaw + camYawOff & 0x7ff;
                int sin = Canvas3D.sin[angle];
                int cos = Canvas3D.cos[angle];
                sin = sin * (mapZoomOffset + 256) >> 8;
                cos = cos * (mapZoomOffset + 256) >> 8;
                int k1 = y * sin + x * cos >> 11;
                int l1 = y * cos - x * sin >> 11;
                int destX = self.sceneX + k1 >> 7;
                int destY = self.sceneY - l1 >> 7;
                walkTo(1, 0, 0, self.pathX[0], self.pathY[0], destX, destY, 0, 0, 0, true);
            }
        }
    }

    public static void handleMouse() {
        tmpHoveredWidget = 0;

        if (Mouse.within(Area.VIEWPORT)) {
            if (widgetOverlay != -1) {
                Game.handleWidgetMouse(Widget.get(widgetOverlay), 4, 4, Mouse.lastX, Mouse.lastY, 0);
            } else {
                handleViewportMouse();
            }
        }

        if (tmpHoveredWidget != hoveredViewportWidget) {
            hoveredViewportWidget = tmpHoveredWidget;
        }

        tmpHoveredWidget = 0;

        if (Mouse.within(Area.TAB)) {
            if (Sidebar.widgetIndex != -1) {
                handleWidgetMouse(Widget.instance[Sidebar.widgetIndex], 553, 205, Mouse.lastX, Mouse.lastY, 0);
            } else {
                handleWidgetMouse(Widget.instance[Sidebar.selectedTab.widget], 553, 205, Mouse.lastX, Mouse.lastY, 0);
            }
        }

        if (tmpHoveredWidget != hoveredTabWidget) {
            Sidebar.draw = true;
            hoveredTabWidget = tmpHoveredWidget;
        }

        tmpHoveredWidget = 0;

        if (Mouse.within(Area.CHAT)) {
            if (Chat.getOverlay() != -1) {
                handleWidgetMouse(Widget.instance[Chat.getOverlay()], 17, 357, Mouse.lastX, Mouse.lastY, 0);
            } else if (Mouse.lastY < 434 && Mouse.lastX < 426) {
                Chat.handleMouse(Mouse.lastX - 17, Mouse.lastY - 357);
            }
        }

        if (Chat.getOverlay() != -1 && tmpHoveredWidget != hoveredChatWidget) {
            Chat.redraw = true;
            hoveredChatWidget = tmpHoveredWidget;
        }

        Mouse.wheelAmount = 0;
    }

    public static boolean handleNet() {
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
                updatePlayers(psize, in);
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
                    closeWidgets();
                    char c = '\u028A';

                    if (welcomeInfo != 201 || welcomeNotify == 1) {
                        c = '\u028F';
                    }

                    reportAbuseInput = "";
                    reportAbuseMute = false;
                    for (int i = 0; i < Widget.instance.length; i++) {
                        if (Widget.instance[i] == null || Widget.instance[i].actionType != c) {
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
                            updateItemPile(x, y);
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
                    w.modelTypeDisabled = 3;
                    if (self.actor_override != null) {
                        w.modelIndexDisabled = (int) (0x12345678L + self.actor_override.index);
                    } else {
                        w.modelIndexDisabled = (int) (0x12345678L + self.actor_override.index);
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
                    for (int i = 0; i < w.itemIndex.length; i++) {
                        w.itemIndex[i] = -1;
                        w.itemIndex[i] = 0;
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
                camCinemaDestX = in.readUnsignedByte();
                camCinemaDestY = in.readUnsignedByte();
                camCinemaDestZ = in.readUnsignedShort();
                camCinemaBaseSpeed = in.readUnsignedByte();
                camCinemaSpeed = in.readUnsignedByte();
                if (camCinemaSpeed >= 100) {
                    Camera.x = camCinemaDestX * 128 + 64;
                    Camera.y = camCinemaDestY * 128 + 64;
                    Camera.z = getLandZ(Camera.x, Camera.y, plane) - camCinemaDestZ;
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
                        playMusic(0, 10, music.volume0, false, 0, song);
                    } else {
                        stopMusic(false);
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
                    playMusicInstantly(music.volume0, song, 0, 1, false);
                    music.tmpDelay = delay;
                }

                Game.ptype = -1;
                return true;
            }

            // INFO: Forcefully disconnects.
            if (Game.ptype == 109) {
                netDisconnect();
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
                int regionX = Game.loadedRegionX;
                int regionY = Game.loadedRegionY;

                if (Game.ptype == 73) {
                    regionX = in.writeUnsignedShortA();
                    regionY = in.readUnsignedShort();
                    serverSentChunk = false;
                }

                // INFO: Modified protocol
                if (Game.ptype == 241) {
                    regionY = in.readUnsignedShort();

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

                    regionX = in.writeUnsignedShortA();
                    serverSentChunk = true;
                }

                if (Game.loadedRegionX == regionX && Game.loadedRegionY == regionY && sceneState == 2) {
                    Game.ptype = -1;
                    return true;
                }

                Game.loadedRegionX = regionX;
                Game.loadedRegionY = regionY;
                Game.mapBaseX = (Game.loadedRegionX - 6) * 8;
                Game.mapBaseY = (Game.loadedRegionY - 6) * 8;

                restrictRegion = (Game.loadedRegionX / 8 == 48 || Game.loadedRegionX / 8 == 49) && Game.loadedRegionY / 8 == 48;

                if (Game.loadedRegionX / 8 == 48 && Game.loadedRegionY / 8 == 148) {
                    restrictRegion = true;
                }

                sceneState = 1;
                sceneLoadStart = System.currentTimeMillis();
                BitmapFont.NORMAL.draw("Loading - please wait.", 256, 150, 0xFFFFFF, BitmapFont.SHADOW_CENTER);

                if (Game.ptype == 73) {
                    int count = 0;

                    for (int chunkX = (Game.loadedRegionX - 6) / 8; chunkX <= (Game.loadedRegionX + 6) / 8; chunkX++) {
                        for (int chunkY = (Game.loadedRegionY - 6) / 8; chunkY <= (Game.loadedRegionY + 6) / 8; chunkY++) {
                            count++;
                        }
                    }

                    chunkLocPayload = new byte[count][];
                    chunkLandscapePayload = new byte[count][];
                    chunkCoords = new int[count];
                    mapUids = new int[count];
                    landscapeUids = new int[count];
                    count = 0;

                    for (int chunkX = (Game.loadedRegionX - 6) / 8; chunkX <= (Game.loadedRegionX + 6) / 8; chunkX++) {
                        for (int chunkY = (Game.loadedRegionY - 6) / 8; chunkY <= (Game.loadedRegionY + 6) / 8; chunkY++) {
                            chunkCoords[count] = (chunkX << 8) + chunkY;

                            if (restrictRegion && (chunkY == 49 || chunkY == 149 || chunkY == 147 || chunkX == 50 || chunkX == 49 && chunkY == 47)) {
                                mapUids[count] = -1;
                                landscapeUids[count] = -1;
                                count++;
                            } else {
                                int mapUid = mapUids[count] = ondemand.getMapUid(chunkX, chunkY, 0);

                                if (mapUid != -1) {
                                    ondemand.sendRequest(3, mapUid);
                                }

                                int landscapeUid = landscapeUids[count] = ondemand.getMapUid(chunkX, chunkY, 1);

                                if (landscapeUid != -1) {
                                    ondemand.sendRequest(3, landscapeUid);
                                }
                                count++;
                            }
                        }
                    }
                } else if (Game.ptype == 241) {
                    int count = 0;
                    int chunkCoords[] = new int[26 * 26];

                    for (int plane = 0; plane < 4; plane++) {
                        for (int x = 0; x < 13; x++) {
                            for (int y = 0; y < 13; y++) {
                                int chunkUid = regionChunkUniqueIds[plane][x][y];
                                if (chunkUid != -1) {
                                    int chunkX = chunkUid >> 14 & 0x3ff;
                                    int chunkY = chunkUid >> 3 & 0x7ff;
                                    int chunkCoord = (chunkX / 8 << 8) + chunkY / 8;
                                    for (int i = 0; i < count; i++) {
                                        if (chunkCoords[i] != chunkCoord) {
                                            continue;
                                        }
                                        chunkCoord = -1;
                                        break;
                                    }

                                    if (chunkCoord != -1) {
                                        chunkCoords[count++] = chunkCoord;
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
                        int chunkCoord = Game.chunkCoords[i] = chunkCoords[i];
                        int chunkX = chunkCoord >> 8 & 0xFF;
                        int chunkY = chunkCoord & 0xFF;

                        int mapUid = mapUids[i] = ondemand.getMapUid(chunkX, chunkY, 0);

                        if (mapUid != -1) {
                            ondemand.sendRequest(3, mapUid);
                        }

                        int landscapeUid = landscapeUids[i] = ondemand.getMapUid(chunkX, chunkY, 1);

                        if (landscapeUid != -1) {
                            ondemand.sendRequest(3, landscapeUid);
                        }
                    }
                }

                int baseDx = mapBaseX - lastMapBaseX;
                int baseDy = mapBaseY - lastMapBaseY;
                lastMapBaseX = mapBaseX;
                lastMapBaseY = mapBaseY;

                for (int i = 0; i < 16384; i++) {
                    Actor a = actors[i];
                    if (a != null) {
                        for (int j = 0; j < 10; j++) {
                            a.pathX[j] -= baseDx;
                            a.pathY[j] -= baseDy;
                        }
                        a.sceneX -= baseDx * 128;
                        a.sceneY -= baseDy * 128;
                    }
                }

                for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
                    Player p = players[i];
                    if (p != null) {
                        for (int j = 0; j < 10; j++) {
                            p.pathX[j] -= baseDx;
                            p.pathY[j] -= baseDy;
                        }
                        p.sceneX -= baseDx * 128;
                        p.sceneY -= baseDy * 128;
                    }
                }

                sceneLoading = true;

                byte x1 = 0;
                byte x2 = 104;
                byte dx = 1;

                if (baseDx < 0) {
                    x1 = 103;
                    x2 = -1;
                    dx = -1;
                }

                byte y1 = 0;
                byte y2 = 104;
                byte dy = 1;

                if (baseDy < 0) {
                    y1 = 103;
                    y2 = -1;
                    dy = -1;
                }

                if (Game.itemPile != null) {
                    for (int x = x1; x != x2; x += dx) {
                        for (int y = y1; y != y2; y += dy) {
                            int oldX = x + baseDx;
                            int oldY = y + baseDy;
                            for (int plane = 0; plane < 4; plane++) {
                                if (oldX >= 0 && oldY >= 0 && oldX < 104 && oldY < 104) {
                                    Game.itemPile[plane][x][y] = Game.itemPile[plane][oldX][oldY];
                                } else {
                                    Game.itemPile[plane][x][y] = null;
                                }
                            }
                        }
                    }
                }

                for (SpawntLoc sl = (SpawntLoc) spawnedLocs.top(); sl != null; sl = (SpawntLoc) spawnedLocs.next()) {
                    sl.x -= baseDx;
                    sl.y -= baseDy;
                    if (sl.x < 0 || sl.y < 0 || sl.x >= 104 || sl.y >= 104) {
                        sl.detach();
                    }
                }

                if (mapMarkerX != 0) {
                    mapMarkerX -= baseDx;
                    mapMarkerY -= baseDy;
                }

                camCinemaMode = false;
                Game.ptype = -1;
                return true;
            }

            // INFO: Resets the animations of the widget.
            if (Game.ptype == 208) {
                int index = in.readLeShort();
                if (index >= 0) {
                    Widget.resetAnimations(index);
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
                Widget.instance[index].modelTypeDisabled = 2;
                Widget.instance[index].modelIndexDisabled = mesh_index;
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
                    handlePacket(in, in.readUnsignedByte());
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
                        players[i].seqIndex = -1;
                    }
                }
                for (int i = 0; i < actors.length; i++) {
                    if (actors[i] != null) {
                        actors[i].seqIndex = -1;
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
                int overlayWidget = in.writeUnsignedShortA();
                int sidebarWidget = in.readUnsignedShort();
                Chat.clearOverlay();
                Chat.clear();
                Game.widgetOverlay = overlayWidget;
                Sidebar.widgetIndex = sidebarWidget;
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

                    if (amount > w.scrollHeight - w.height) {
                        amount = w.scrollHeight - w.height;
                    }
                    w.scrollAmount = amount;
                }

                Game.ptype = -1;
                return true;
            }

            // Resets settings to their defaults?
            if (Game.ptype == 68) {
                for (int i = 0; i < settings.length; i++) {
                    if (settings[i] != defaultSettings[i]) {
                        settings[i] = defaultSettings[i];
                        handleVarp(i);
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
                    w.modelTypeDisabled = 0;
                    Game.ptype = -1;
                    return true;
                } else {
                    ObjConfig oc = ObjConfig.get(index);
                    w.modelTypeDisabled = 4;
                    w.modelIndexDisabled = index;
                    w.modelPitch = oc.iconPitch;
                    w.modelYaw = oc.iconYaw;
                    w.modelZoom = (oc.iconDist * 100) / zoom;
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
                Widget.resetAnimations(index);
                Chat.clearOverlay();
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
                    Widget.instance[index].messageDisabled = message;
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
                int widgetIndex = in.readUnsignedLeShortA();
                int modelIndex = in.readUnsignedShort();
                Widget.instance[widgetIndex].modelTypeDisabled = 1;
                Widget.instance[widgetIndex].modelIndexDisabled = modelIndex;
                Game.ptype = -1;
                return true;
            }

            // INFO: Sets the disabled color of the widget.
            if (Game.ptype == 122) {
                int widgetIndex = in.readUnsignedLeShortA();

                // COLOR FORMAT:
                // R: 0-31 G: 0-31 B: 0-31
                // ((r >> 3) << 16) | ((g >> 3) << 16) | (b >> 3)
                // or just clamp it serverside to 0 to 31 each channel.
                int color = in.readUnsignedLeShortA();
                int red = color >> 10 & 0x1F;
                int green = color >> 5 & 0x1F;
                int blue = color & 0x1F;
                Widget.instance[widgetIndex].rgbDisabled = (red << 19) | (green << 11) | (blue << 3);
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

                        w.itemIndex[i] = (short) in.readUnsignedLeShortA();
                        w.itemCount[i] = count;
                    }
                } catch (Exception e) {

                }

                for (int i = size; i < w.itemIndex.length; i++) {
                    w.itemIndex[i] = 0;
                    w.itemCount[i] = 0;
                }

                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 230) {
                int zoom = in.writeUnsignedShortA();
                int index = in.readUnsignedShort();
                int pitch = in.readUnsignedShort();
                int yaw = in.readUnsignedLeShortA();
                Widget.instance[index].modelPitch = pitch;
                Widget.instance[index].modelYaw = yaw;
                Widget.instance[index].modelZoom = zoom;
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
                camCinemaAimX = in.readUnsignedByte();
                camCinemaAimY = in.readUnsignedByte();
                camCinemaAimZ = in.readUnsignedShort();
                camCinemaRotBase = in.readUnsignedByte();
                camCinemaRotModifier = in.readUnsignedByte();

                if (camCinemaRotModifier >= 100) {
                    int x = camCinemaAimX * 128 + 64;
                    int y = camCinemaAimY * 128 + 64;
                    int z = getLandZ(x, y, plane) - camCinemaAimZ;
                    int xDiff = x - Camera.x;
                    int zDiff = z - Camera.z;
                    int yDiff = y - Camera.y;
                    int length = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                    Camera.pitch = (int) (Math.atan2(zDiff, length) * 325.94900000000001D) & 0x7ff;
                    Camera.yaw = (int) (Math.atan2(xDiff, yDiff) * -325.94900000000001D) & 0x7ff;

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
                updateActors(psize, in);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 27) {
                Chat.set(Chat.State.ENTER_AMOUNT);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 187) {
                Chat.set(Chat.State.ENTER_NAME);
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 97) {
                int index = in.readUnsignedShort();

                try {
                    Widget.resetAnimations(index);
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

                Chat.clearOverlay();
                Chat.clear();
                widgetOverlay = index;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 218) {
                Chat.setUnderlay(in.readShortA());
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 87) {
                int index = in.readUnsignedLeShort();
                int value = in.readMeInt();

                defaultSettings[index] = value;

                if (settings[index] != value) {
                    settings[index] = value;
                    handleVarp(index);
                    Sidebar.draw = true;

                    if (Chat.getUnderlay() != -1) {
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
                    handleVarp(index);
                    Sidebar.draw = true;

                    if (Chat.getUnderlay() != -1) {
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
                int seqIndex = in.readShort();

                if (w == null) {
                    Game.ptype = -1;
                    return true;
                }

                w.seqIndexDisabled = seqIndex;

                if (seqIndex == -1) {
                    w.sequenceFrame = 0;
                    w.sequenceCycle = 0;
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

                Chat.clearOverlay();
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

                    if (slot >= 0 && slot < w.itemIndex.length) {
                        w.itemIndex[slot] = (short) index;
                        w.itemCount[slot] = count;
                    }
                }
                Game.ptype = -1;
                return true;
            }

            if (Game.ptype == 105 || ptype == 84 || ptype == 147 || ptype == 215 || ptype == 4 || ptype == 117 || ptype == 156 || ptype == 44 || ptype == 160 || ptype == 101 || ptype == 151) {
                handlePacket(in, ptype);
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

                Widget.resetAnimations(index);

                if (Sidebar.widgetIndex != -1) {
                    Sidebar.widgetIndex = -1;
                    Sidebar.draw = true;
                    Sidebar.drawTabs = true;
                }

                Chat.setOverlay(index);
                widgetOverlay = -1;
                dialogueOptionActive = false;
                Game.ptype = -1;
                return true;
            }

            Signlink.error("T1 - " + ptype + "," + psize + " - " + lastPtype2 + "," + lastPtype3);
            netDisconnect();
        } catch (IOException e) {
            handleConnectionLost();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("T2");
            sb.append(" - last:").append(ptype).append(',').append(lastPtype2).append(',').append(lastPtype3);
            sb.append(" - psize:").append(psize);
            sb.append(" - pos:").append(self.get_tile_x()).append(',').append(self.get_tile_y()).append('\n');

            for (int i = 0; i < psize && i < in.position; i++) {
                sb.append(Integer.toHexString(in.payload[i])).append(',');
            }

            Signlink.error(sb.toString());
            e.printStackTrace();
            netDisconnect();
        }
        return true;
    }

    public static void handleNewActors(int position, Buffer b) {
        while (b.bitPosition + 21 < position * 8) {
            int actorIndex = b.readBits(14);

            if (actorIndex == 16383) {
                break;
            }

            if (actors[actorIndex] == null) {
                actors[actorIndex] = new Actor();
            }

            Actor a = actors[actorIndex];
            actorIndices[actorCount++] = actorIndex;
            a.updateCycle = loopCycle;

            int y = b.readBits(5);

            if (y > 15) {
                y -= 32;
            }

            int x = b.readBits(5);

            if (x > 15) {
                x -= 32;
            }

            int discardWalkQueue = b.readBits(1);

            a.config = ActorConfig.get(b.readBits(12));

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = actorIndex;
            }

            a.size = a.config.has_options;
            a.turnSpeed = a.config.turn_speed;
            a.walkAnimation = a.config.move_seq;
            a.turn180Animation = a.config.turn_180_seq;
            a.turnRAnimation = a.config.turn_r_seq;
            a.turnLAnimation = a.config.turn_l_seq;
            a.standAnimation = a.config.stand_sequence;
            a.moveTo(self.pathX[0] + x, self.pathY[0] + y, discardWalkQueue == 1);
        }
        b.stopBitAccess();
    }

    public static void handleOndemand() {
        do {
            OnDemandRequest r;
            do {
                r = ondemand.next();

                if (r == null) {
                    return;
                }

                if (r.archive == 0) {
                    Model.load(r.payload, r.file);

                    if ((ondemand.modelFlags(r.file) & 0x62) != 0) {
                        Sidebar.draw = true;

                        if (Chat.getOverlay() != -1) {
                            Chat.redraw = true;
                        }
                    }
                }

                if (r.archive == 1 && r.payload != null) {
                    SequenceFrame.load(r.payload);
                }

                if (r.archive == 2 && r.payload != null) {
                    music.buffer = r.payload;
                    playMusic(0, 10, music.volume0, false, 0, r.file);
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
            } while (r.archive != 93 || !ondemand.hasLandscape(r.file));
            Scene.requestLocModels(new Buffer(r.payload), ondemand);
        } while (true);
    }

    public static void handlePacket(Buffer b, int opcode) {
        // INFO: Update Ground Item
        if (opcode == 84) {
            int coord = b.readUnsignedByte();
            int x = netRegionX + (coord >> 4 & 7);
            int y = netRegionY + (coord & 7);
            int index = b.readUnsignedShort();
            int oldStackIndex = b.readUnsignedShort();
            int newStackIndex = b.readUnsignedShort();

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Chain c = Game.itemPile[plane][x][y];
                if (c != null) {
                    for (Item item = (Item) c.top(); item != null; item = (Item) c.next()) {
                        if (item.index != (index & 0x7fff) || item.stackIndex != oldStackIndex) {
                            continue;
                        }
                        item.stackIndex = newStackIndex;
                        break;
                    }

                    updateItemPile(x, y);
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

            if (self.pathX[0] >= x - radius && self.pathX[0] <= x + radius && self.pathY[0] >= y - radius && self.pathY[0] <= y + radius && !lowDetail && soundCount < 50) {
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
            int entityIndex = b.writeUnsignedShortA();
            int stack = b.readUnsignedShort();

            if (x >= 0 && y >= 0 && x < 104 && y < 104 && entityIndex != localPlayerIndex) {
                Item item = new Item();
                item.index = (short) index;
                item.stackIndex = stack;

                if (Game.itemPile[plane][x][y] == null) {
                    Game.itemPile[plane][x][y] = new Chain();
                }

                Game.itemPile[plane][x][y].pushBack(item);
                updateItemPile(x, y);
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
                    updateItemPile(x, y);
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
                int heightSouthWest = heightMap[plane][x][y];
                int heightSouthEast = heightMap[plane][x + 1][y];
                int heightNorthEast = heightMap[plane][x + 1][y + 1];
                int heightNorthWest = heightMap[plane][x][y + 1];

                if (class_type == 0) {
                    WallLoc wl = landscape.getWall(plane, x, y);
                    if (wl != null) {
                        int index = wl.uid >> 14 & 0x7fff;
                        if (type == 2) {
                            wl.root = new Loc(index, 4 + rotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
                            wl.extension = new Loc(index, rotation + 1 & 3, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
                        } else {
                            wl.root = new Loc(index, rotation, type, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
                        }
                    }
                } else if (class_type == 1) {
                    WallDecoration wd = landscape.getWallDecoration(x, 866, y, plane);
                    if (wd != null) {
                        wd.node = new Loc(wd.uid >> 14 & 0x7fff, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
                    }
                } else if (class_type == 2) {
                    StaticLoc sl = landscape.getLoc(x, y, plane);
                    if (type == 11) {
                        type = 10;
                    }
                    if (sl != null) {
                        sl.node = new Loc(sl.uid >> 14 & 0x7fff, rotation, type, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
                    }
                } else if (class_type == 3) {
                    GroundDecoration gd = landscape.getGroundDecoration(x, y, plane);
                    if (gd != null) {
                        gd.node = new Loc(gd.uid >> 14 & 0x7fff, rotation, 22, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, sequence, false);
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
            int classType = LOC_CLASS_TYPE[type];
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
                    spawnLoc(-1, x, y, plane, 0, 0, end + 1, start + 1, classType);

                    p.locStartCycle = start + loopCycle;
                    p.locEndCycle = end + loopCycle;

                    p.locModel = mesh;
                    int locSizeX = lc.sizeX;
                    int locSizeY = lc.sizeY;

                    if (rotation == 1 || rotation == 3) {
                        locSizeX = lc.sizeY;
                        locSizeY = lc.sizeX;
                    }

                    p.locX = x * 128 + locSizeX * 64;
                    p.locY = y * 128 + locSizeY * 64;
                    p.locZ = getLandZ(p.locX, p.locY, plane);

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

                    p.locX0 = x + x1;
                    p.locY0 = y + y1;
                    p.locX1 = x + x2;
                    p.locY1 = y + y2;
                }
            }
        }

        if (opcode == 151) {
            int offset = b.readUnsignedByteA();
            int x = netRegionX + (offset >> 4 & 7);
            int y = netRegionY + (offset & 7);
            int locIndex = b.readUnsignedLeShort();
            int locInfo = b.readUnsignedByteS();
            int locType = locInfo >> 2;
            int locRotation = locInfo & 3;
            int classType = LOC_CLASS_TYPE[locType];

            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                spawnLoc(locIndex, x, y, plane, locType, locRotation, -1, 0, classType);
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
                spotanims.pushBack(new SpotAnim(x, y, getLandZ(x, y, plane) - z, plane, loopCycle, delay, index));
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
                i.stackIndex = stack_index;

                if (Game.itemPile[plane][x][y] == null) {
                    Game.itemPile[plane][x][y] = new Chain();
                }

                Game.itemPile[plane][x][y].pushBack(i);
                updateItemPile(x, y);
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
                spawnLoc(-1, x, y, plane, type, rotation, -1, 0, class_type);
            }
            return;
        }

        if (opcode == 117) {
            int offset = b.readUnsignedByte();
            int srcX = netRegionX + (offset >> 4 & 7);
            int srcY = netRegionY + (offset & 7);
            int endX = srcX + b.readByte();
            int endY = srcY + b.readByte();
            int target = b.readShort();
            int effect = b.readUnsignedShort();
            int srcZ = b.readUnsignedByte() * 4;
            int endZ = b.readUnsignedByte() * 4;
            int delay = b.readUnsignedShort();
            int speed = b.readUnsignedShort();
            int slope = b.readUnsignedByte();
            int sourceSize = b.readUnsignedByte();

            if (srcX >= 0 && srcY >= 0 && srcX < 104 && srcY < 104 && endX >= 0 && endY >= 0 && endX < 104 && endY < 104 && effect != 65535) {

                srcX = srcX * 128 + 64;
                srcY = srcY * 128 + 64;
                endX = endX * 128 + 64;
                endY = endY * 128 + 64;

                Projectile p = new Projectile(slope, endZ, delay + loopCycle, speed + loopCycle, sourceSize, plane, getLandZ(srcX, srcY, plane) - srcZ, srcY, srcX, target, effect);
                p.update(delay + loopCycle, endX, endY, getLandZ(endX, endY, plane) - endZ);
                projectiles.pushBack(p);
            }
        }
    }

    public static void handlePlayerMenuOptions(Player p, int x, int y, int index) {
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
                        if (p.combatLevel > self.combatLevel) {
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

    public static void handlePlayers() {
        for (int i = -1; i < playerCount; i++) {
            int index;

            if (i == -1) {
                index = MAX_PLAYER_INDEX;
            } else {
                index = playerIndices[i];
            }

            Player p = players[index];

            if (p != null) {
                handleEntity(p);
            }
        }

    }

    public static void handleScene() {
        if (lowDetail && sceneState == 2 && Scene.planeAtBuild != plane) {
            BitmapFont.NORMAL.draw("Loading - please wait.", 256, 150, 0xFFFFFF, BitmapFont.SHADOW_CENTER);
            sceneState = 1;
            sceneLoadStart = System.currentTimeMillis();
        }

        if (sceneState == 1) {
            int state = getSceneLoadState((byte) -95);
            if (state != 0 && System.currentTimeMillis() - sceneLoadStart > 0x360_000L) {
                Signlink.error(username + " glcfb " + serverSeed + "," + state + "," + lowDetail + "," + cache[0] + "," + ondemand.immediateRequestCount() + "," + plane + "," + loadedRegionX + "," + loadedRegionY);
                sceneLoadStart = System.currentTimeMillis();
            }
        }

        if (sceneState == 2 && plane != lastPlane) {
            lastPlane = plane;
            generateMinimap(plane);
        }
    }

    public static void handleScrollbar(Widget w, int x, int y, int mX, int mY, int height, int scrollHeight, boolean isTabArea) {
        if (draggingScrollbar) {
            scrollDragBound = 32;
        } else {
            scrollDragBound = 0;
        }

        draggingScrollbar = false;

        if (Mouse.wheelAmount != 0 && mX >= (x - w.width) && mX <= x && mY >= y && mY <= y + height) {
            w.scroll(Mouse.wheelAmount);
            Mouse.wheelAmount = 0;

            if (isTabArea) {
                Sidebar.draw = true;
            }
        } else if (mX >= x && mX < x + 16 && mY >= y && mY < y + 16) {
            w.scrollAmount -= clickCycle * 4;

            if (isTabArea) {
                Sidebar.draw = true;
            }
        } else if (mX >= x && mX < x + 16 && mY >= (y + height) - 16 && mY < y + height) {
            w.scrollAmount += clickCycle * 4;

            if (isTabArea) {
                Sidebar.draw = true;
            }
        } else if (mX >= x - scrollDragBound && mX < x + 16 + scrollDragBound && mY >= y + 16 && mY < (y + height) - 16 && clickCycle > 0) {
            int grip_length = ((height - 32) * height) / scrollHeight;

            if (grip_length < 8) {
                grip_length = 8;
            }

            int i2 = mY - y - 16 - grip_length / 2;
            int j2 = height - 32 - grip_length;

            w.scrollAmount = ((scrollHeight - height) * i2) / j2;

            if (isTabArea) {
                Sidebar.draw = true;
            }

            draggingScrollbar = true;
        }
    }

    public static void handleSpoken() {
        for (int i = -1; i < playerCount; i++) {
            int playerIndex;

            if (i == -1) {
                playerIndex = MAX_PLAYER_INDEX;
            } else {
                playerIndex = playerIndices[i];
            }

            Player p = players[playerIndex];

            if (p != null && p.spokenLife > 0) {
                p.spokenLife--;

                if (p.spokenLife == 0) {
                    p.spokenMessage = null;
                }
            }
        }

        for (int i = 0; i < actorCount; i++) {
            int actorIndice = actorIndices[i];
            Actor a = actors[actorIndice];

            if (a != null && a.spokenLife > 0) {
                a.spokenLife--;

                if (a.spokenLife == 0) {
                    a.spokenMessage = null;
                }
            }
        }

    }

    public static void handleTabMouse() {
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

    public static void handleVarp(int index) {
        int type = Varp.instance[index].index;

        if (type == 0) {
            return;
        }

        int setting = Game.settings[index];

        switch (type) {
            case 1: { // Brightness
                Canvas3D.createPalette(1d - setting / 10d);
                ObjConfig.spriteCache.clear();
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
                            setMusicVolume(volume);
                        } else {
                            stopMusic(false);
                            music.tmpDelay = 0;
                        }
                    } else {
                        playMusicInstantly(volume, music.currentSong, 0, 0, false);
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
                Chat.Settings.showEffects = setting == 0;
                break;
            }

            case 8: {
                Chat.Settings.privateArea = setting;
                Chat.Settings.redraw = true;
                break;
            }

            case 9: {
                anInt913 = setting;
                break;
            }
        }
    }

    public static void handleViewportMouse() {
        if (!selectedItem && !selectedWidget) {
            Menu.add("Walk here", 516, Mouse.lastX, Mouse.lastY);
        }

        int lastUid = -1;

        for (int idx = 0; idx < Model.hovered_count; idx++) {
            int uid = Model.hovered_uid[idx];
            int x = uid & 0x7f;
            int y = uid >> 7 & 0x7f;
            int type = uid >> 29 & 3;
            int index = uid >> 14 & 0x7fff;

            if (uid == lastUid) {
                continue;
            }

            lastUid = uid;

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
                if (a.config.has_options == 1 && (a.sceneX & 0x7f) == 64 && (a.sceneY & 0x7f) == 64) {
                    for (int i = 0; i < actorCount; i++) {
                        Actor a1 = actors[actorIndices[i]];
                        if (a1 != null && a1 != a && a1.config.has_options == 1 && a1.sceneX == a.sceneX && a1.sceneY == a.sceneY) {
                            handleActorMenuOptions(a1.config, x, y, actorIndices[i]);
                        }
                    }

                    for (int i = 0; i < playerCount; i++) {
                        Player p = players[playerIndices[i]];
                        if (p != null && p.sceneX == a.sceneX && p.sceneY == a.sceneY) {
                            handlePlayerMenuOptions(p, x, y, playerIndices[i]);
                        }
                    }

                }
                handleActorMenuOptions(a.config, x, y, index);
            }

            if (type == 0) {
                Player p = players[index];
                if ((p.sceneX & 0x7f) == 64 && (p.sceneY & 0x7f) == 64) {
                    for (int k2 = 0; k2 < actorCount; k2++) {
                        Actor a = actors[actorIndices[k2]];
                        if (a != null && a.config.has_options == 1 && a.sceneX == p.sceneX && a.sceneY == p.sceneY) {
                            handleActorMenuOptions(a.config, x, y, actorIndices[k2]);
                        }
                    }

                    for (int i3 = 0; i3 < playerCount; i3++) {
                        Player p1 = players[playerIndices[i3]];
                        if (p1 != null && p1 != p && p1.sceneX == p.sceneX && p1.sceneY == p.sceneY) {
                            handlePlayerMenuOptions(p1, x, y, playerIndices[i3]);
                        }
                    }

                }
                handlePlayerMenuOptions(p, x, y, index);
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
                                if (oc.groundAction != null && oc.groundAction[i] != null) {
                                    Menu.add(oc.groundAction[i] + " @lre@" + oc.name, Action.GROUND_ITEM[i], x, y, item.index);
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

    public static boolean handleWidget(Widget w) {
        int type = w.actionType;

        if (CharacterDesign.handle(w, type)) {
            return true;
        }

        if (frenemiesStatus == 2) {
            if (type == 201) {
                Chat.set(Chat.State.ADD_FRIEND, "Enter name of friend to add to list");
            }
            if (type == 202) {
                Chat.set(Chat.State.REMOVE_FRIEND, "Enter name of friend to delete from list");
            }
        }

        if (type == 205) {
            logoutCycle = 250;
            return true;
        }

        if (type == 501) {
            Chat.set(Chat.State.ADD_IGNORE, "Enter name of player to add to list");
        }

        if (type == 502) {
            Chat.set(Chat.State.REMOVE_IGNORE, "Enter name of player to delete from list");
        }

        if (type >= 601 && type <= 612) {
            closeWidgets();
            if (reportAbuseInput.length() > 0) {
                out.writeOpcode(218);
                out.writeLong(JString.toLong(reportAbuseInput));
                out.writeByte(type - 601);
                out.writeByte(reportAbuseMute ? 1 : 0);
            }
        }
        return false;
    }

    public static void handleWidgetMouse() {
        if (dragArea != 0) {
            return;
        }

        int clickButton = Mouse.clickButton;
        int menuCount = Menu.count;

        if (selectedWidget && Mouse.clickX >= 516 && Mouse.clickY >= 160 && Mouse.clickX <= 765 && Mouse.clickY <= 205) {
            clickButton = 0;
        }

        if (Menu.visible) {
            if (clickButton != 1) {
                Area area = Menu.area;

                int x = Mouse.lastX - area.x;
                int y = Mouse.lastY - area.y;
                int menuX = Menu.x;
                int menuY = Menu.y;
                int menuW = Menu.width;
                int menuH = Menu.height;

                if (x < menuX - 10 || x > menuX + menuW + 10 || y < menuY - 10 || y > menuY + menuH + 10) {
                    Menu.visible = false;
                    if (area == Area.TAB) {
                        Sidebar.draw = true;
                    } else if (area == Area.CHAT) {
                        Chat.redraw = true;
                    }
                }
            }

            if (clickButton == 1) {
                Area area = Menu.area;

                int x = Menu.x;
                int y = Menu.y;
                int width = Menu.width;
                int clickX = Mouse.clickX - area.x;
                int clickY = Mouse.clickY - area.y;

                int activeOption = -1;
                for (int option = 0; option < menuCount; option++) {
                    int optionY = y + 31 + (menuCount - 1 - option) * 15;
                    if (clickX > x && clickX < x + width && clickY > optionY - 13 && clickY < optionY + 3) {
                        activeOption = option;
                    }
                }

                if (activeOption != -1) {
                    handleMenuOption(activeOption);
                }

                Menu.visible = false;

                if (area == Area.TAB) {
                    Sidebar.draw = true;
                } else if (area == Area.CHAT) {
                    Chat.redraw = true;
                }

            }
        } else {
            if (clickButton == 1 && menuCount > 0) {
                int action = Menu.getLastAction();

                for (int i : Action.DRAG) {
                    if (i == action) {
                        int slot = Menu.getLastParam(0);
                        int index = Menu.getLastParam(1);
                        Widget w = Widget.instance[index];

                        if (w.itemsDraggable || w.itemsSwappable) {
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

                            if (Widget.instance[index].parent == Chat.getOverlay()) {
                                dragArea = 3;
                            }
                            return;
                        }

                        break;
                    }
                }
            }

            if (clickButton == 1 && (mouseButtonSetting == 1) && menuCount > 2) {
                clickButton = 2;
            }

            if (clickButton == 1 && menuCount > 0) {
                handleMenuOption(menuCount - 1);
            }

            if (clickButton == 2 && menuCount > 0) {
                Menu.show();
            }
        }
    }

    public static void handleWidgetMouse(Widget w, int screenX, int screenY, int mouseX, int mouseY, int scroll_amount) {
        if (w == null) {
            return;
        }

        if (w.type != 0 || w.children == null || w.hidden) {
            return;
        }

        if (!w.visible) {
            return;
        }

        if (mouseX < screenX || mouseY < screenY || mouseX > screenX + w.width || mouseY > screenY + w.height) {
            return;
        }

        for (int index = 0; index < w.children.length; index++) {
            int x = w.childX[index] + screenX;
            int y = (w.childY[index] + screenY) - scroll_amount;

            Widget child = Widget.instance[w.children[index]];

            if (!child.visible) {
                continue;
            }

            x += child.x;
            y += child.y;

            boolean hovered = false;

            if (mouseX >= x && mouseY >= y && mouseX < x + child.width && mouseY < y + child.height) {
                hovered = true;
            }

            if ((child.hoverIndex >= 0 || child.colorHoverDisabled != 0) && hovered) {
                if (child.hoverIndex >= 0) {
                    tmpHoveredWidget = child.hoverIndex;
                } else {
                    tmpHoveredWidget = child.index;
                }
            }

            if (child.type == 0) {
                handleWidgetMouse(child, x, y, mouseX, mouseY, child.scrollAmount);

                if (child.scrollHeight > child.height) {
                    handleScrollbar(child, x + child.width, y, mouseX, mouseY, child.height, child.scrollHeight, true);
                }
            } else {
                if (child.optionType == 1 && hovered) {
                    boolean noOptions = false;

                    if (child.actionType != 0) {
                        noOptions = frenemyOptionValid(child, false);
                    }

                    if (!noOptions) {
                        Menu.add(child.option, 315, -1, child.index);
                    }
                }

                if (child.optionType == 2 && !selectedWidget && hovered) {
                    String s = child.optionPrefix;
                    if (s.indexOf(' ') != -1) {
                        s = s.substring(0, s.indexOf(' '));
                    }
                    Menu.add(s + " @gre@" + child.optionSuffix, 626, -1, child.index);
                }

                if (child.optionType == 3 && hovered) {
                    Menu.add("Close", 200, -1, child.index);
                }

                if (child.optionType == 4 && hovered) {
                    Menu.add(child.option, 169, -1, child.index);
                }

                if (child.optionType == 5 && hovered) {
                    Menu.add(child.option, 646, -1, child.index);
                }

                if (child.optionType == 6 && !dialogueOptionActive && hovered) {
                    Menu.add(child.option, 679, -1, child.index);
                }

                if (child.type == 2) {
                    int slot = 0;

                    for (int column = 0; column < child.height; column++) {
                        for (int row = 0; row < child.width; row++) {
                            int slotX = x + row * (32 + child.itemMarginX);
                            int slotY = y + column * (32 + child.itemMarginY);

                            if (slot < 20) {
                                slotX += child.itemSlotX[slot];
                                slotY += child.itemSlotY[slot];
                            }

                            if (mouseX >= slotX && mouseY >= slotY && mouseX < slotX + 32 && mouseY < slotY + 32) {
                                hoveredSlot = slot;
                                hoveredSlotWidget = child.index;

                                if (child.itemIndex[slot] > 0) {
                                    ObjConfig oc = ObjConfig.get(child.itemIndex[slot] - 1);

                                    if (oc == null) {
                                        Menu.add("Invalid Item", -1);
                                        continue;
                                    }

                                    if (selectedItem && child.itemsHaveActions) {
                                        if (child.index != selectedItemWidget || slot != selectedItemSlot) {
                                            Menu.add("Use " + selectedItemName + " with @lre@" + oc.name, 870, slot, child.index, oc.index);
                                        }
                                    } else if (selectedWidget && child.itemsHaveActions) {
                                        if ((selectedMask & 0x10) == 16) {
                                            Menu.add(selectedTooltip + " @lre@" + oc.name, 543, slot, child.index, oc.index);
                                        }
                                    } else {
                                        if (child.itemsHaveActions) {
                                            for (int i = 4; i >= 3; i--) {
                                                if (oc.action != null && oc.action[i] != null) {
                                                    Menu.add(oc.action[i] + " @lre@" + oc.name, Action.ITEM[i], slot, child.index, oc.index);
                                                } else if (i == 4) {
                                                    Menu.add("Drop @lre@" + oc.name, 847, slot, child.index, oc.index);
                                                }
                                            }
                                        }

                                        if (child.itemsUsable) {
                                            Menu.add("Use @lre@" + oc.name, 447, slot, child.index, oc.index);
                                        }

                                        if (child.itemsHaveActions && oc.action != null) {
                                            for (int i = 2; i >= 0; i--) {
                                                if (oc.action[i] != null) {
                                                    Menu.add(oc.action[i] + " @lre@" + oc.name, Action.ITEM[i], slot, child.index, oc.index);
                                                }
                                            }
                                        }

                                        if (child.itemActions != null) {
                                            for (int i = 4; i >= 0; i--) {
                                                if (child.itemActions[i] != null) {
                                                    Menu.add(child.itemActions[i] + " @lre@" + oc.name, Action.WIDGET_ITEM[i], slot, child.index, oc.index);
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

    public static void ignoreAdd(long nameLong) {
        if (nameLong == 0L) {
            return;
        }

        if (ignoreCount >= 100) {
            Chat.put("Your ignore list is full. Max of 100 hit", 0);
            return;
        }

        String name = JString.getFormattedString(nameLong);

        for (int i = 0; i < ignoreCount; i++) {
            if (ignoreLong[i] == nameLong) {
                Chat.put(name + " is already on your ignore list", 0);
                return;
            }
        }

        for (int i = 0; i < friendCount; i++) {
            if (friendLong[i] == nameLong) {
                Chat.put("Please remove " + name + " from your friend list first", 0);
                return;
            }
        }

        ignoreLong[ignoreCount++] = nameLong;
        Sidebar.draw = true;
        out.writeOpcode(133);
        out.writeLong(nameLong);
    }

    public static void ignoreRemove(long name_long) {
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

    public static boolean interactWithLoc(int x, int y, int uid) {
        int index = uid >> 14 & 0x7fff;
        int locInfo = landscape.getArrangement(plane, y, x, uid);

        if (locInfo == -1) {
            return false;
        }

        int type = locInfo & 0x1f;
        int rotation = locInfo >> 6 & 3;

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

            int faceFlags = lc.faceFlags;

            if (rotation != 0) {
                faceFlags = (faceFlags << rotation & 0xf) + (faceFlags >> 4 - rotation);
            }

            Game.walkTo(2, size_x, size_y, self.pathX[0], self.pathY[0], y, x, 0, faceFlags, 0, false);
        } else {
            Game.walkTo(2, 0, 0, self.pathX[0], self.pathY[0], y, x, type + 1, 0, rotation, false);
        }

        crossX = Mouse.clickX;
        crossY = Mouse.clickY;
        crossType = 2;
        crossCycle = 0;
        return true;
    }

    public static void method891(boolean bool) {
        music.method853(0, null, bool);
    }

    public static void netDisconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception _ex) {
        }

        connection = null;
        loggedIn = false;

        TitleScreen.state = TitleScreen.State.WELCOME;

        username = "";
        password = "";

        clearCaches();
        landscape.reset();

        for (int i = 0; i < 4; i++) {
            collisionMaps[i].defaults();
        }

        System.gc();

        music.stop();
        ondemand.sendRequest(2, 484);
    }

    public static void netLogin(String username, String password, boolean reconnection) {
        Signlink.errorName = username;

        try {
            if (!reconnection) {
                TitleScreen.setMessage("", "Connecting to server...");
                TitleScreen.draw(true);
            }

            Game.connection = new Connection(instance, instance.getSocket(43594 + portOffset));

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
            int firstOpcode = opcode;

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

                }
                netLogin(username, password, reconnection);
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

                Game.camXOff = (int) (Math.random() * 100D) - 50;
                Game.camYOff = (int) (Math.random() * 110D) - 55;
                Game.camPitchOff = (int) (Math.random() * 80D) - 40;
                Game.camYawOff = (int) (Math.random() * 120D) - 60;
                Game.mapZoomOffset = (int) (Math.random() * 30D) - 20;
                Game.chaseCamYaw = (int) (Math.random() * 20D) - 10 & 0x7ff;

                Game.minimapState = 0;
                Game.lastPlane = -1;
                Game.mapMarkerX = 0;
                Game.mapMarkerY = 0;
                Game.playerCount = 0;
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

                return;
            }

            if (opcode == 3) {
                TitleScreen.setMessage("", "Invalid username or password");
                return;
            }

            if (opcode == 4) {
                TitleScreen.setMessage("Your account ahs been disabled", "Please check your message-centre for details");
                return;
            }

            if (opcode == 5) {
                TitleScreen.setMessage("Your account is already logged in", "Try again in 60 secs...");
                return;
            }

            if (opcode == 6) {
                TitleScreen.setMessage("RoonScape has been updated!", "Please reload this page");
                return;
            }

            if (opcode == 7) {
                TitleScreen.setMessage("This world is full", "Please use a different world");
                return;
            }

            if (opcode == 8) {
                TitleScreen.setMessage("Unable to connect.", "Login server offline.");
                return;
            }

            if (opcode == 9) {
                TitleScreen.setMessage("Login limit exceeded.", "Too many connections from your address.");
                return;
            }

            if (opcode == 10) {
                TitleScreen.setMessage("Unable to connect.", "Bad session id");
                return;
            }

            if (opcode == 11) {
                TitleScreen.setMessage("Login server rejected session.", "Please try again.");
                return;
            }

            if (opcode == 12) {
                TitleScreen.setMessage("You need a members account to login to this world.", "Please subscribe, or use a different world.");
                return;
            }

            if (opcode == 13) {
                TitleScreen.setMessage("Could not complete login", "Please try using a different world.");
                return;
            }

            if (opcode == 14) {
                TitleScreen.setMessage("The server is being updated.", "Please wait 1 minute and try again.");
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
                TitleScreen.setMessage("Login attempts exceeded", "Please wait 1 minute and try again.");
                return;
            }

            if (opcode == 17) {
                TitleScreen.setMessage("You are standing in a members-only area.", "To play on this world, move to a free area first.");
                return;
            }

            if (opcode == 20) {
                TitleScreen.setMessage("Invalid loginserver requested.", "Please use a different world.");
                return;
            }
            if (opcode == 21) {
                for (int k1 = connection.getByte(); k1 >= 0; k1--) {
                    TitleScreen.setMessage("You have only just left another world", "Your profile will be transferred in: " + k1 + " seconds");
                    TitleScreen.draw(true);
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception _ex) {
                    }
                }

                netLogin(username, password, reconnection);
                return;
            }
            if (opcode == -1) {
                if (firstOpcode == 0) {
                    if (reconnectionAttempts < 2) {
                        try {
                            Thread.sleep(2000L);
                        } catch (Exception _ex) {
                        }
                        reconnectionAttempts++;
                        netLogin(username, password, reconnection);
                        return;
                    } else {
                        TitleScreen.setMessage("No response from loginserver", "Please wait 1 minute and try again.");
                        return;
                    }
                } else {
                    TitleScreen.setMessage("No response from loginserver", "Please use a different world.");
                    return;
                }
            } else {
                System.out.println("response:" + opcode);
                TitleScreen.setMessage("Unexpected server response", "Please use a different world");
                return;
            }
        } catch (IOException _ex) {
            TitleScreen.setMessage("", "Error connecting to server.");
        }
    }

    public static synchronized void playMusic(int fileVar1, int loop1, int volume2, boolean loopM1, int fileVar3, int fileVar2) {
        music.loop1 = loop1;
        music.loop2 = -1;
        music.fvar1 = fileVar1;
        music.fvar2 = fileVar2;
        music.fvar3 = fileVar3;
        music.loopmusic1 = loopM1;
        music.volume2 = volume2;
        music.fetch = true;
    }

    public static synchronized void playMusicInstantly(int volume2, int fileVar2, int fileVar1, int fileVar3, boolean loopMusic1) {
        music.fetch = true;
        music.volume2 = volume2;
        music.fvar1 = fileVar1;
        music.fvar2 = fileVar2;
        music.fvar3 = fileVar3;
        music.loop1 = -1;
        music.loop2 = -1;
        music.loopmusic1 = loopMusic1;
    }

    public static void retrieveScene(boolean flag) {
        try {
            lastPlane = -1;
            spotanims.clear();
            projectiles.clear();
            Canvas3D.clearTextures();
            clearCaches();
            landscape.reset();
            System.gc();

            for (int i = 0; i < 4; i++) {
                collisionMaps[i].defaults();
            }

            for (int z = 0; z < 4; z++) {
                for (int x = 0; x < 104; x++) {
                    for (int y = 0; y < 104; y++) {
                        renderFlags[z][x][y] = 0;
                    }
                }
            }

            scene = new Scene(104, 104, heightMap, renderFlags);
            int length = chunkLocPayload.length;
            out.writeOpcode(0);

            if (!serverSentChunk) {
                for (int chunk = 0; chunk < length; chunk++) {
                    int chunkX = (chunkCoords[chunk] >> 8) * 64 - mapBaseX;
                    int chunkY = (chunkCoords[chunk] & 0xff) * 64 - mapBaseY;
                    byte payload[] = chunkLocPayload[chunk];
                    if (payload != null) {
                        scene.loadLand(collisionMaps, payload, chunkX, chunkY, (loadedRegionX - 6) * 8, (loadedRegionY - 6) * 8);
                    }
                }

                for (int chunk = 0; chunk < length; chunk++) {
                    int chunkX = (chunkCoords[chunk] >> 8) * 64 - mapBaseX;
                    int chunkY = (chunkCoords[chunk] & 0xff) * 64 - mapBaseY;
                    if (chunkLocPayload[chunk] == null && loadedRegionY < 800) {
                        scene.fitEdges(chunkX, chunkY, 64, 64);
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
                for (int mapPlane = 0; mapPlane < 4; mapPlane++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int regionUid = regionChunkUniqueIds[mapPlane][x][y];
                            if (regionUid != -1) {
                                int chunkPlane = regionUid >> 24 & 3;
                                int chunkRot = regionUid >> 1 & 3;
                                int chunkX = regionUid >> 14 & 0x3ff;
                                int chunkY = regionUid >> 3 & 0x7ff;
                                int chunkUid = (chunkX / 8 << 8) + chunkY / 8;

                                for (int chunk = 0; chunk < chunkCoords.length; chunk++) {
                                    if (chunkCoords[chunk] != chunkUid || chunkLocPayload[chunk] == null) {
                                        continue;
                                    }
                                    scene.loadChunk(collisionMaps, x * 8, y * 8, (chunkX & 7) * 8, (chunkY & 7) * 8, chunkPlane, chunkLocPayload[chunk], chunkRot, mapPlane);
                                    break;
                                }
                            }
                        }
                    }
                }

                for (int x = 0; x < 13; x++) {
                    for (int y = 0; y < 13; y++) {
                        int regionUid = regionChunkUniqueIds[0][x][y];
                        if (regionUid == -1) {
                            scene.fitEdges(x * 8, y * 8, 8, 8);
                        }
                    }

                }

                out.writeOpcode(0);

                for (int mapPlane = 0; mapPlane < 4; mapPlane++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int chunkUid = regionChunkUniqueIds[mapPlane][x][y];
                            if (chunkUid != -1) {
                                int chunkPlane = chunkUid >> 24 & 3;
                                int chunkRot = chunkUid >> 1 & 3;
                                int chunkX = chunkUid >> 14 & 0x3ff;
                                int chunkY = chunkUid >> 3 & 0x7ff;
                                int chunkCoord = (chunkX / 8 << 8) + chunkY / 8;
                                for (int chunk = 0; chunk < chunkCoords.length; chunk++) {
                                    if (chunkCoords[chunk] != chunkCoord || chunkLandscapePayload[chunk] == null) {
                                        continue;
                                    }
                                    scene.loadLocs(collisionMaps, landscape, x * 8, y * 8, (chunkX & 7) * 8, (chunkY & 7) * 8, chunkPlane, mapPlane, chunkLandscapePayload[chunk], chunkRot);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            out.writeOpcode(0);
            scene.createLandMesh(collisionMaps, landscape);
            out.writeOpcode(0);

            if (lowDetail) {
                landscape.setPlane(Scene.minPlane);
            } else {
                landscape.setPlane(0);
            }

            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    updateItemPile(x, y);
                }
            }

            handleLocs();
        } catch (Exception exception) {
        }

        LocConfig.staticModelCache.clear();

        if (lowDetail && Signlink.cacheFile != null) {
            int meshCount = ondemand.getFileCount(0);
            for (int meshIndex = 0; meshIndex < meshCount; meshIndex++) {
                int flags = ondemand.modelFlags(meshIndex);
                if ((flags & 0x79) == 0) {
                    Model.nullify(meshIndex);
                }
            }

        }

        System.gc();
        Canvas3D.setupTexelPools();
        ondemand.clearPassiveRequests();

        int chunkWest = (loadedRegionX - 6) / 8 - 1;
        int chunkEast = (loadedRegionX + 6) / 8 + 1;
        int chunkNorth = (loadedRegionY - 6) / 8 - 1;
        int chunkSouth = (loadedRegionY + 6) / 8 + 1;

        if (restrictRegion) {
            chunkWest = 49;
            chunkEast = 50;
            chunkNorth = 49;
            chunkSouth = 50;
        }

        for (int chunkX = chunkWest; chunkX <= chunkEast; chunkX++) {
            for (int chunkY = chunkNorth; chunkY <= chunkSouth; chunkY++) {
                if (chunkX == chunkWest || chunkX == chunkEast || chunkY == chunkNorth || chunkY == chunkSouth) {
                    int mapUid = ondemand.getMapUid(chunkX, chunkY, 0);
                    if (mapUid != -1) {
                        ondemand.request(mapUid, 3);
                    }
                    int landscapeUid = ondemand.getMapUid(chunkX, chunkY, 1);
                    if (landscapeUid != -1) {
                        ondemand.request(landscapeUid, 3);
                    }
                }
            }
        }
    }

    public static void scrollTextures(int cycle) {
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
                    Canvas3D.updateTexture(i);
                }
            }
        }
    }

    public static void setDrawXy(Entity e, int offset_z) {
        setDrawXy(e.sceneX, e.sceneY, offset_z);
    }

    public static void setDrawXy(int x, int y, int offsetZ) {
        if (x < 128 || y < 128 || x > 13056 || y > 13056) {
            drawX = -1;
            drawY = -1;
            return;
        }

        int z = getLandZ(x, y, plane) - offsetZ;
        x -= Camera.x;
        z -= Camera.z;
        y -= Camera.y;

        int pitchSin = Model.sin[Camera.pitch];
        int pitchCos = Model.cos[Camera.pitch];
        int yawSin = Model.sin[Camera.yaw];
        int yawCos = Model.cos[Camera.yaw];

        int i = y * yawSin + x * yawCos >> 16;
        y = y * yawCos - x * yawSin >> 16;
        x = i;

        i = z * pitchCos - y * pitchSin >> 16;
        y = z * pitchSin + y * pitchCos >> 16;
        z = i;

        if (y >= 50) {
            drawX = Canvas3D.centerX + (x << 9) / y;
            drawY = Canvas3D.centerY + (z << 9) / y;
            return;
        }

        drawX = -1;
        drawY = -1;
    }

    public static void setMusicVolume(int volume) {
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

    public static void spawnLoc(int locIndex, int locX, int locY, int locZ, int locType, int locRotation, int cycle, int spawnCycle, int classType) {
        SpawntLoc sl = null;

        for (SpawntLoc _sl = (SpawntLoc) spawnedLocs.top(); _sl != null; _sl = (SpawntLoc) spawnedLocs.next()) {
            if (_sl.plane != locZ || _sl.x != locX || _sl.y != locY || _sl.classType != classType) {
                continue;
            }
            sl = _sl;
            break;
        }

        if (sl == null) {
            sl = new SpawntLoc();
            sl.plane = locZ;
            sl.classType = classType;
            sl.x = locX;
            sl.y = locY;
            handleLoc(sl);
            spawnedLocs.pushBack(sl);
        }

        sl.locIndex = locIndex;
        sl.locType = locType;
        sl.locRotation = locRotation;
        sl.spawnCycle = spawnCycle;
        sl.cycle = cycle;
    }

    public static synchronized void stopMusic(boolean loop) {
        method891(loop);
        music.fetch = false;
    }

    public static int[] transform(int x, int y, int z) {
        x -= Camera.x;
        y -= Camera.y;
        z -= Camera.z;

        int pitchSin = Model.sin[Camera.pitch];
        int pitchCos = Model.cos[Camera.pitch];
        int yawSin = Model.sin[Camera.yaw];
        int yawCos = Model.cos[Camera.yaw];

        int i = y * yawSin + x * yawCos >> 16;
        y = y * yawCos - x * yawSin >> 16;
        x = i;

        i = z * pitchCos - y * pitchSin >> 16;
        y = z * pitchSin + y * pitchCos >> 16;
        z = i;

        return new int[]{x, y, z, Canvas3D.centerX + (x << 9) / y, Canvas3D.centerY + (z << 9) / y};
    }

    public static void updateActorMasks(Buffer b) {
        for (int i = 0; i < entityCount; i++) {
            Actor a = actors[entityIndex[i]];
            int mask = b.readUnsignedByte();

            if ((mask & 0x10) != 0) { // Animation
                int seqIndex = b.readUnsignedLeShort();

                if (seqIndex == 65535) {
                    seqIndex = -1;
                }

                int delay = b.readUnsignedByte();

                if (seqIndex == a.seqIndex && seqIndex != -1) {
                    int type = Sequence.instance[seqIndex].type;
                    if (type == 1) {
                        a.seqFrame = 0;
                        a.seqCycle = 0;
                        a.seqDelayCycle = delay;
                        a.seqResetCycle = 0;
                    }
                    if (type == 2) {
                        a.seqResetCycle = 0;
                    }
                } else if (seqIndex == -1 || a.seqIndex == -1 || Sequence.instance[seqIndex].priority >= Sequence.instance[a.seqIndex].priority) {
                    a.seqIndex = seqIndex;
                    a.seqFrame = 0;
                    a.seqCycle = 0;
                    a.seqDelayCycle = delay;
                    a.seqResetCycle = 0;
                    a.stillPathPosition = a.pathPosition;
                }
            }

            if ((mask & 8) != 0) { // Hit
                int damage = b.readUnsignedByteA();
                int type = b.readUnsignedByteC();
                a.hit(type, damage, loopCycle);
                a.combatCycle = loopCycle + 300;
                a.currentHealth = b.readUnsignedByteA();
                a.maxHealth = b.readUnsignedByte();
            }

            if ((mask & 0x80) != 0) { // Graphics
                a.spotanimIndex = b.readUnsignedShort();

                int info = b.readInt();
                a.graphicOffsetY = info >> 16;
                a.spotanimCycleEnd = loopCycle + (info & 0xFFFF);

                a.spotanimFrame = 0;
                a.spotanimCycle = 0;

                if (a.spotanimCycleEnd > loopCycle) {
                    a.spotanimFrame = -1;
                }

                if (a.spotanimIndex == 65535) {
                    a.spotanimIndex = -1;
                }
            }

            if ((mask & 0x20) != 0) { // Face Entity
                a.faceEntity = b.readUnsignedShort();

                if (a.faceEntity == 65535) {
                    a.faceEntity = -1;
                }
            }

            if ((mask & 1) != 0) { // Forced Chat
                a.spokenMessage = b.readString();
                a.spokenLife = 100;
            }

            if ((mask & 0x40) != 0) { // Hit 2
                int damage = b.readUnsignedByteC();
                int type = b.readUnsignedByteS();
                a.hit(type, damage, loopCycle);
                a.combatCycle = loopCycle + 300;
                a.currentHealth = b.readUnsignedByteS();
                a.maxHealth = b.readUnsignedByteC();
            }

            if ((mask & 2) != 0) { // Transform
                a.config = ActorConfig.get(b.readUnsignedLeShortA());
                a.size = a.config.has_options;
                a.turnSpeed = a.config.turn_speed;
                a.walkAnimation = a.config.move_seq;
                a.turn180Animation = a.config.turn_180_seq;
                a.turnRAnimation = a.config.turn_r_seq;
                a.turnLAnimation = a.config.turn_l_seq;
                a.standAnimation = a.config.stand_sequence;
            }

            if ((mask & 4) != 0) { // Face Coordinate
                a.faceX = b.readUnsignedLeShort();
                a.faceY = b.readUnsignedLeShort();
            }
        }
    }

    public static void updateActorMovement(Buffer b) {
        b.startBitAccess();
        int actorCount = b.readBits(8);

        if (actorCount < Game.actorCount) {
            for (int l = actorCount; l < Game.actorCount; l++) {
                entityUpdateIndices[entityUpdateCount++] = actorIndices[l];
            }
        }

        if (actorCount > Game.actorCount) {
            Signlink.error(username + " Too many npcs");
            throw new RuntimeException("eek");
        }

        Game.actorCount = 0;

        for (int i = 0; i < actorCount; i++) {
            int actor_index = actorIndices[i];
            Actor a = actors[actor_index];
            int movementUpdate = b.readBits(1);

            if (movementUpdate == 0) {
                actorIndices[Game.actorCount++] = actor_index;
                a.updateCycle = loopCycle;
            } else {
                int moveType = b.readBits(2);

                switch (moveType) {
                    case 0: {// No Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.updateCycle = loopCycle;
                        entityIndex[entityCount++] = actor_index;
                        break;
                    }
                    case 1: {// 1 Tile Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.updateCycle = loopCycle;
                        a.move(b.readBits(3), false);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = actor_index;
                        }
                        break;
                    }
                    case 2: {// 2 Tile Movement
                        actorIndices[Game.actorCount++] = actor_index;
                        a.updateCycle = loopCycle;
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

    public static void updateActors(int psize, Buffer b) {
        entityUpdateCount = 0;
        entityCount = 0;
        updateActorMovement(b);
        handleNewActors(psize, b);
        updateActorMasks(b);

        for (int i = 0; i < entityUpdateCount; i++) {
            int actorIndex = entityUpdateIndices[i];

            if (actors[actorIndex].updateCycle != loopCycle) {
                actors[actorIndex].config = null;
                actors[actorIndex] = null;
            }
        }

        if (b.position != psize) {
            Signlink.error(username + " size mismatch in getactorpos - pos:" + b.position + " psize:" + psize);
            throw new RuntimeException("eek");
        }

        for (int i = 0; i < actorCount; i++) {
            if (actors[actorIndices[i]] == null) {
                Signlink.error(username + " null entry in actor list - pos:" + i + " size:" + actorCount);
                throw new RuntimeException("eek");
            }
        }
    }

    public static void updateItemPile(int x, int y) {
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
                a *= i.stackIndex + 1;
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
        landscape.addItemPile(x, y, getLandZ(x * 128 + 64, y * 128 + 64, plane), plane, top, middle, bottom, uid);
    }

    public static void updateLocalplayerMovement(Buffer b) {
        b.startBitAccess();

        if (b.readBits(1) == 0) {
            return;
        }

        int moveType = b.readBits(2);

        if (moveType == 0) {
            entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            return;
        }

        if (moveType == 1) { // Walk
            int direction = b.readBits(3);
            self.move(direction, false);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }
            return;
        }

        if (moveType == 2) { // Run
            self.move(b.readBits(3), true);
            self.move(b.readBits(3), true);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }
            return;
        }

        if (moveType == 3) { // Teleport
            plane = b.readBits(2);
            int discardMoveQueue = b.readBits(1);

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = MAX_PLAYER_INDEX;
            }

            int y = b.readBits(7);
            int x = b.readBits(7);
            self.moveTo(x, y, discardMoveQueue == 1);
        }
    }

    public static void updateNewPlayers(Buffer b, int position) {
        while (b.bitPosition + 10 < position * 8) {
            int playerIndex = b.readBits(11);

            if (playerIndex == 2047) {
                break;
            }

            if (players[playerIndex] == null) {
                players[playerIndex] = new Player();
                if (playerBuffer[playerIndex] != null) {
                    players[playerIndex].update(playerBuffer[playerIndex]);
                }
            }

            playerIndices[playerCount++] = playerIndex;
            Player p = players[playerIndex];
            p.updateCycle = loopCycle;

            if (b.readBits(1) == 1) { // Update Required
                entityIndex[entityCount++] = playerIndex;
            }

            int discardWalkQueue = b.readBits(1);
            int x = b.readBits(5);
            int y = b.readBits(5);

            if (x > 15) {
                x -= 32;
            }

            if (y > 15) {
                y -= 32;
            }

            p.moveTo(self.pathX[0] + y, self.pathY[0] + x, discardWalkQueue == 1);
        }
        b.stopBitAccess();
    }

    public static void updatePlayerMask(int mask, int index, Buffer b, Player p) {
        if ((mask & 0x400) != 0) {
            p.moveStartX = b.readUnsignedByteS();
            p.moveStartY = b.readUnsignedByteS();
            p.moveEndX = b.readUnsignedByteS();
            p.moveEndY = b.readUnsignedByteS();
            p.moveCycleEnd = b.readUnsignedLeShortA() + loopCycle;
            p.moveCycleStart = b.writeUnsignedShortA() + loopCycle;
            p.moveDirection = b.readUnsignedByteS();
            p.resetPositions();
        }

        if ((mask & 0x100) != 0) { // Graphics
            p.spotanimIndex = b.readUnsignedLeShort();
            int info = b.readInt();
            p.graphicOffsetY = info >> 16;
            p.spotanimCycleEnd = loopCycle + (info & 0xffff);
            p.spotanimFrame = 0;
            p.spotanimCycle = 0;

            if (p.spotanimCycleEnd > loopCycle) {
                p.spotanimFrame = -1;
            }

            if (p.spotanimIndex == 65535) {
                p.spotanimIndex = -1;
            }
        }

        if ((mask & 8) != 0) { // Sequence
            int sequenceIndex = b.readUnsignedLeShort();
            int delay = b.readUnsignedByteC();

            System.out.println("sequence: " + sequenceIndex);

            if (sequenceIndex == 65535) {
                sequenceIndex = -1;
            }

            if (sequenceIndex == p.seqIndex && sequenceIndex != -1) {
                int type = Sequence.instance[sequenceIndex].type;
                if (type == 1) {
                    p.seqFrame = 0;
                    p.seqCycle = 0;
                    p.seqDelayCycle = delay;
                    p.seqResetCycle = 0;
                } else if (type == 2) {
                    p.seqResetCycle = 0;
                }
            } else if (sequenceIndex == -1 || p.seqIndex == -1 || Sequence.instance[sequenceIndex].priority >= Sequence.instance[p.seqIndex].priority) {
                p.seqIndex = sequenceIndex;
                p.seqFrame = 0;
                p.seqCycle = 0;
                p.seqDelayCycle = delay;
                p.seqResetCycle = 0;
                p.stillPathPosition = p.pathPosition;
            }
        }

        if ((mask & 4) != 0) { // Forced Chat
            p.spokenMessage = b.readString();

            if (p.spokenMessage.charAt(0) == '~') {
                p.spokenMessage = p.spokenMessage.substring(1);
                Chat.put(p.name, p.spokenMessage, 2);
            } else if (p == self) {
                Chat.put(p.name, p.spokenMessage, 2);
            }

            p.spokenColor = 0;
            p.spokenEffect = 0;
            p.spokenLife = 150;
        }

        if ((mask & 0x80) != 0) { // Chat
            int settings = b.readUnsignedLeShort();
            int rights = b.readUnsignedByte();
            int length = b.readUnsignedByteC();
            int startOff = b.position;

            if (p.name != null && p.visible) {
                long nameLong = JString.toLong(p.name);

                boolean ignored = false;
                if (rights <= 1) {
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreLong[i] != nameLong) {
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

                        p.spokenMessage = JString.getFormatted(length, Chat.buffer);
                        p.spokenEffect = settings & 0xFF;
                        p.spokenColor = settings >> 8;
                        p.spokenLife = 150;

                        StringBuilder sb = new StringBuilder();

                        if (rights > 0) {
                            sb.append("@cr").append(rights).append("@");
                        }

                        sb.append(p.name);

                        Chat.put(sb.toString(), p.spokenMessage, rights == 0 ? Chat.TYPE_PLAYER : Chat.TYPE_MODERATOR);
                    } catch (Exception e) {
                        Signlink.error("cde2");
                    }
                }

            }
            b.position = startOff + length;
        }

        if ((mask & 1) != 0) { // Face Entity
            p.faceEntity = b.readUnsignedLeShort();

            if (p.faceEntity == 65535) {
                p.faceEntity = -1;
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
            p.faceX = b.readUnsignedLeShortA();
            p.faceY = b.readUnsignedLeShort();
        }

        if ((mask & 0x20) != 0) { // Damage Update
            int damage = b.readUnsignedByte();
            int type = b.readUnsignedByteA();
            p.hit(type, damage, loopCycle);
            p.combatCycle = loopCycle + 300;
            p.currentHealth = b.readUnsignedByteC();
            p.maxHealth = b.readUnsignedByte();
        }

        if ((mask & 0x200) != 0) { // Damage Update 2
            int damage = b.readUnsignedByte();
            int type = b.readUnsignedByteS();
            p.hit(type, damage, loopCycle);
            p.combatCycle = loopCycle + 300;
            p.currentHealth = b.readUnsignedByte();
            p.maxHealth = b.readUnsignedByteC();
        }
    }

    public static void updatePlayerMasks(Buffer b) {
        for (int i = 0; i < entityCount; i++) {
            int index = entityIndex[i];
            Player p = players[index];

            int mask = b.readUnsignedByte();

            if ((mask & 0x40) != 0) {
                mask += b.readUnsignedByte() << 8;
            }

            updatePlayerMask(mask, index, b, p);
        }
    }

    public static void updatePlayerMovement(Buffer b) {
        int playerCount = b.readBits(8);

        if (playerCount < Game.playerCount) {
            for (int k = playerCount; k < Game.playerCount; k++) {
                entityUpdateIndices[entityUpdateCount++] = playerIndices[k];
            }
        }

        if (playerCount > Game.playerCount) {
            Signlink.error(username + " Too many players");
            throw new RuntimeException("eek");
        }

        Game.playerCount = 0;

        for (int i = 0; i < playerCount; i++) {
            int playerIndice = playerIndices[i];
            Player p = players[playerIndice];

            // If we don't need a movement update
            if (b.readBits(1) == 0) {
                playerIndices[Game.playerCount++] = playerIndice;
                p.updateCycle = loopCycle;
            } else {
                int move_type = b.readBits(2);

                switch (move_type) {
                    case 0: { // No Movement
                        playerIndices[Game.playerCount++] = playerIndice;
                        p.updateCycle = loopCycle;
                        entityIndex[entityCount++] = playerIndice;
                        break;
                    }
                    case 1: { // Walk
                        playerIndices[Game.playerCount++] = playerIndice;
                        p.updateCycle = loopCycle;
                        p.move(b.readBits(3), false);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = playerIndice;
                        }
                        break;
                    }
                    case 2: { // Run
                        playerIndices[Game.playerCount++] = playerIndice;
                        p.updateCycle = loopCycle;
                        p.move(b.readBits(3), true);
                        p.move(b.readBits(3), true);

                        if (b.readBits(1) == 1) { // Update Required
                            entityIndex[entityCount++] = playerIndice;
                        }
                        break;
                    }
                    case 3: { // Unknown
                        entityUpdateIndices[entityUpdateCount++] = playerIndice;
                        break;
                    }
                }
            }
        }
    }

    public static void updatePlayers(int psize, Buffer b) {
        entityUpdateCount = 0;
        entityCount = 0;

        updateLocalplayerMovement(b);
        updatePlayerMovement(b);
        updateNewPlayers(b, psize);
        updatePlayerMasks(b);

        for (int i = 0; i < entityUpdateCount; i++) {
            int playerIndex = entityUpdateIndices[i];
            if (players[playerIndex].updateCycle != loopCycle) {
                players[playerIndex] = null;
            }
        }

        if (b.position != psize) {
            Signlink.error("Error packet size mismatch in getplayer pos:" + b.position + " psize:" + psize);
            throw new RuntimeException("eek");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[playerIndices[i]] == null) {
                Signlink.error(username + " null entry in pl list - pos:" + i + " size:" + playerCount);
                throw new RuntimeException("eek");
            }
        }
    }

    public static void updateWidget(Widget w) {
        int type = w.actionType;

        if (CharacterDesign.update(w, type)) {
            return;
        }

        if (type >= 1 && type <= 100 || type >= 701 && type <= 800) {
            if (type == 1 && frenemiesStatus == 0) {
                w.messageDisabled = "Loading friend list";
                w.optionType = 0;
                return;
            }
            if (type == 1 && frenemiesStatus == 1) {
                w.messageDisabled = "Connecting to friendserver";
                w.optionType = 0;
                return;
            }
            if (type == 2 && frenemiesStatus != 2) {
                w.messageDisabled = "Please wait...";
                w.optionType = 0;
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
                w.messageDisabled = "";
                w.optionType = 0;
                return;
            } else {
                w.messageDisabled = friendName[type];
                w.optionType = 1;
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
                w.messageDisabled = "";
                w.optionType = 0;
                return;
            }

            if (friendNode[type] == 0) {
                w.messageDisabled = "@red@Offline";
            } else if (friendNode[type] == nodeIndex) {
                w.messageDisabled = "@gre@World-" + (friendNode[type] - 9);
            } else {
                w.messageDisabled = "@gre@World-" + (friendNode[type] - 9);
            }

            w.optionType = 1;
            return;
        }

        if (type == 203) {
            int count = friendCount;

            if (frenemiesStatus != 2) {
                count = 0;
            }

            w.scrollHeight = count * 15 + 20;

            if (w.scrollHeight <= w.height) {
                w.scrollHeight = w.height + 1;
            }
            return;
        }

        if (type >= 401 && type <= 500) {
            if ((type -= 401) == 0 && frenemiesStatus == 0) {
                w.messageDisabled = "Loading ignore list";
                w.optionType = 0;
                return;
            }

            if (type == 1 && frenemiesStatus == 0) {
                w.messageDisabled = "Please wait...";
                w.optionType = 0;
                return;
            }

            int count = ignoreCount;

            if (frenemiesStatus == 0) {
                count = 0;
            }

            if (type >= count) {
                w.messageDisabled = "";
                w.optionType = 0;
                return;
            } else {
                w.messageDisabled = JString.getFormattedString(ignoreLong[type]);
                w.optionType = 1;
                return;
            }
        }

        if (type == 503) {
            w.scrollHeight = ignoreCount * 15 + 20;
            if (w.scrollHeight <= w.height) {
                w.scrollHeight = w.height + 1;
            }
            return;
        }

        if (type == 600) {
            w.messageDisabled = reportAbuseInput;

            if (loopCycle % 20 < 10) {
                w.messageDisabled += '|';
            } else {
                w.messageDisabled += ' ';
            }
            return;
        }

        if (type == 620) {
            if (reportAbuseMute) {
                w.rgbDisabled = 0x00ff00;
                w.messageDisabled = "Moderator option: Mute player for 48 hours: <ON>";
            } else {
                w.rgbDisabled = 0xffffff;
                w.messageDisabled = "Moderator option: Mute player for 48 hours: <OFF>";
            }
        }

        if (type == 650 || type == 655) {
            if (welcomeLastIp != 0) {
                String s;
                if (welcomeLastPlaydate == 0) {
                    s = "earlier today";
                } else if (welcomeLastPlaydate == 1) {
                    s = "yesterday";
                } else {
                    s = welcomeLastPlaydate + ' ' + " days ago";
                }
                w.messageDisabled = "You last logged in " + s + " from: " + Signlink.resolvedDns;
            } else {
                w.messageDisabled = "";
            }
        }

        if (type == 651) {
            if (welcomeUnreadMessages == 0) {
                w.messageDisabled = "0 unread messages";
                w.rgbDisabled = 0xFFFF00;
            }
            if (welcomeUnreadMessages == 1) {
                w.messageDisabled = "1 unread message";
                w.rgbDisabled = 0xFF00;
            }
            if (welcomeUnreadMessages > 1) {
                w.messageDisabled = welcomeUnreadMessages + ' ' + "unread messages";
                w.rgbDisabled = 0xFF00;
            }
        }

        if (type == 652) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.messageDisabled = "@yel@This is a non-members world: @whi@Since you are a member we";
                } else {
                    w.messageDisabled = "";
                }
            } else if (welcomeInfo == 200) {
                w.messageDisabled = "You have not yet set any password recovery questions.";
            } else {
                String s1;
                if (welcomeInfo == 0) {
                    s1 = "Earlier today";
                } else if (welcomeInfo == 1) {
                    s1 = "Yesterday";
                } else {
                    s1 = welcomeInfo + ' ' + " days ago";
                }
                w.messageDisabled = s1 + " you changed your recovery questions";
            }
        }

        if (type == 653) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.messageDisabled = "@whi@recommend you use a members world instead. You may use";
                } else {
                    w.messageDisabled = "";
                }
            } else if (welcomeInfo == 200) {
                w.messageDisabled = "We strongly recommend you do so now to secure your account.";
            } else {
                w.messageDisabled = "If you do not remember making this change then cancel it immediately";
            }
        }

        if (type == 654) {
            if (welcomeInfo == 201) {
                if (welcomeNotify == 1) {
                    w.messageDisabled = "@whi@this world but member benefits are unavailable whilst here.";
                    return;
                } else {
                    w.messageDisabled = "";
                    return;
                }
            }
            if (welcomeInfo == 200) {
                w.messageDisabled = "Do this from the 'account management' area on our front webpage";
                return;
            }
            w.messageDisabled = "Do this from the 'account management' area on our front webpage";
        }
    }

    public static boolean walkTo(int click_type, int size_x, int size_y, int start_x, int start_y, int dest_x, int dest_y, int type, int face_flags, int rotation, boolean arbitrary) {
        byte mapSizeX = 104;
        byte mapSizeY = 104;

        for (int x1 = 0; x1 < mapSizeX; x1++) {
            for (int y1 = 0; y1 < mapSizeY; y1++) {
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

            if (x < mapSizeX - 1 && pathWaypoint[x + 1][y] == 0 && (ai[x + 1][y] & 0x1280180) == 0) {
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

            if (y < mapSizeY - 1 && pathWaypoint[x][y + 1] == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
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

            if (x < mapSizeX - 1 && y > 0 && pathWaypoint[x + 1][y - 1] == 0 && (ai[x + 1][y - 1] & 0x1280183) == 0 && (ai[x + 1][y] & 0x1280180) == 0 && (ai[x][y - 1] & 0x1280102) == 0) {
                pathQueueX[next] = x + 1;
                pathQueueY[next] = y - 1;
                next = (next + 1) % path_length;
                pathWaypoint[x + 1][y - 1] = 9;
                pathDistance[x + 1][y - 1] = distance;
            }

            if (x > 0 && y < mapSizeY - 1 && pathWaypoint[x - 1][y + 1] == 0 && (ai[x - 1][y + 1] & 0x1280138) == 0 && (ai[x - 1][y] & 0x1280108) == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
                pathQueueX[next] = x - 1;
                pathQueueY[next] = y + 1;
                next = (next + 1) % path_length;
                pathWaypoint[x - 1][y + 1] = 6;
                pathDistance[x - 1][y + 1] = distance;
            }

            if (x < mapSizeX - 1 && y < mapSizeY - 1 && pathWaypoint[x + 1][y + 1] == 0 && (ai[x + 1][y + 1] & 0x12801e0) == 0 && (ai[x + 1][y] & 0x1280180) == 0 && (ai[x][y + 1] & 0x1280120) == 0) {
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
            return;
        }

        drawCycle++;

        if (!loggedIn) {
            TitleScreen.draw(false);
        } else {
            drawGame();
        }

        clickCycle = 0;
    }

    @Override
    public Socket getSocket(int port) throws IOException {
        return new Socket(InetAddress.getByName("127.0.0.1"), port);
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

        handleOndemand();
    }

    @Override
    public void redraw() {
        redraw = true;
    }

    public void retrieveChecksums() {
        archiveCrc[8] = 0;

        while (archiveCrc[8] == 0) {
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
                archiveCrc[8] = 0;
            } catch (IOException e) {
                archiveCrc[8] = 0;
            } catch (Exception e) {
                archiveCrc[8] = 0;
            }

            if (archiveCrc[8] == 0) {
                for (int i = 5; i > 0; i--) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }
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

        closeMusicPlayer();

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
        renderFlags = null;
        landscape = null;
        collisionMaps = null;
        pathWaypoint = null;
        pathDistance = null;
        pathQueueX = null;
        pathQueueY = null;
        tmpTexels = null;
        bitmap1 = null;
        bitmap = null;
        Sidebar.nullify();
        imageCompass = null;
        imageHitMarks = null;
        imageHeadIcons = null;
        imageOverlayMultiway = null;
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
        if (Signlink.cacheFile != null) {
            for (int i = 0; i < 5; i++) {
                Game.cache[i] = new Cache(i + 1, Signlink.cacheFile, Signlink.cacheIndex[i]);
            }
        }

        try {
            if (VERIFY_CACHE) {
                retrieveChecksums();
            }

            archive = getArchive("title screen", 1, "title", archiveCrc[1], 25);

            BitmapFont.SMALL = new BitmapFont("p11_full", archive);
            BitmapFont.NORMAL = new BitmapFont("p12_full", archive);
            BitmapFont.BOLD = new BitmapFont("b12_full", archive);
            BitmapFont.FANCY = new BitmapFont("q8_full", archive);

            TitleScreen.createBackground();
            TitleScreen.createImages();

            Archive archiveConfig = getArchive("config", 2, "config", archiveCrc[2], 30);
            Archive archiveWidget = getArchive("interface", 3, "interface", archiveCrc[3], 35);
            Archive archiveMedia = getArchive("2d graphics", 4, "media", archiveCrc[4], 40);
            Archive archiveTexture = getArchive("textures", 6, "textures", archiveCrc[6], 45);
            Archive archiveSound = getArchive("sound effects", 8, "sounds", archiveCrc[8], 55);

            renderFlags = new byte[4][104][104];
            heightMap = new short[4][105][105];

            landscape = new Landscape(104, 104, 4, heightMap);

            for (int plane = 0; plane < 4; plane++) {
                collisionMaps[plane] = new CollisionMap(104, 104);
            }

            imageMinimap = new Sprite(512, 512);

            Archive archiveVersion = getArchive("update list", 5, "versionlist", archiveCrc[5], 60);

            ondemand = new OnDemand();
            ondemand.setup(archiveVersion);

            SequenceFrame.init(ondemand.sequenceFrameCount());
            Model.init(ondemand.getFileCount(0), ondemand);

            music = new MusicPlayer();

            if (!lowDetail) {
                ondemand.sendRequest(2, 484);

                while (ondemand.immediateRequestCount() > 0) {
                    handleOndemand();

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

            {
                count = ondemand.getFileCount(1);

                for (int i = 0; i < count; i++) {
                    ondemand.sendRequest(1, i);
                }

                while (ondemand.immediateRequestCount() > 0) {
                    int remaining = count - ondemand.immediateRequestCount();

                    if (remaining > 0) {

                    }

                    handleOndemand();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }

                    if (ondemand.fails > 3) {
                        return;
                    }
                }
            }

            {
                count = ondemand.getFileCount(0);

                for (int i = 0; i < count; i++) {
                    int flags = ondemand.modelFlags(i);
                    if ((flags & 1) != 0) {
                        ondemand.sendRequest(0, i);
                    }
                }

                count = ondemand.immediateRequestCount();

                while (ondemand.immediateRequestCount() > 0) {
                    int remaining = count - ondemand.immediateRequestCount();

                    if (remaining > 0) {

                    }

                    handleOndemand();

                    try {
                        Thread.sleep(100L);
                    } catch (Exception _ex) {
                    }
                }
            }

            {
                if (cache[0] != null) {
                    ondemand.sendRequest(3, ondemand.getMapUid(47, 48, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(47, 48, 1));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 48, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 48, 1));
                    ondemand.sendRequest(3, ondemand.getMapUid(49, 48, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(49, 48, 1));
                    ondemand.sendRequest(3, ondemand.getMapUid(47, 47, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(47, 47, 1));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 47, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 47, 1));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 148, 0));
                    ondemand.sendRequest(3, ondemand.getMapUid(48, 148, 1));
                    count = ondemand.immediateRequestCount();

                    while (ondemand.immediateRequestCount() > 0) {
                        int remaining = count - ondemand.immediateRequestCount();

                        if (remaining > 0) {

                        }

                        handleOndemand();

                        try {
                            Thread.sleep(100L);
                        } catch (Exception _ex) {
                        }
                    }
                }

                count = ondemand.getFileCount(0);

                for (int i = 0; i < count; i++) {
                    int flags = ondemand.modelFlags(i);
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

                ondemand.requestRegions(isMembers);

                if (!lowDetail) {
                    for (int i = 1; i < ondemand.getFileCount(2); i++) {
                        if (ondemand.hasMidi(i)) {
                            ondemand.verify((byte) 1, 2, i);
                        }
                    }
                }
            }

            {
                Chat.background = new Bitmap(archiveMedia, "chatback", 0);
                Chat.Settings.background = new Bitmap(archiveMedia, "backbase1", 0);

                bitmap1 = new Bitmap(archiveMedia, "mapback", 0);

                imageCompass = new Sprite(archiveMedia, "compass", 0);
                imageMapedge = new Sprite(archiveMedia, "mapedge", 0);
                imageMapedge.crop();

                Sidebar.load(archiveMedia);

                for (int i = 0; i < imageMapMarkers.length; i++) {
                    imageMapMarkers[i] = new Sprite(archiveMedia, "mapmarker", i);
                }

                for (int i = 0; i < imageCrosses.length; i++) {
                    imageCrosses[i] = new Sprite(archiveMedia, "cross", i);
                }

                for (int i = 0; i < imageMapDots.length; i++) {
                    imageMapDots[i] = new Sprite(archiveMedia, "mapdots", i);
                }

                for (int i = 0; i < 2; i++) {
                    bitmapModIcons[i] = new Bitmap(archiveMedia, "mod_icons", i);
                }

                bitmapModIcons[2] = bitmapModIcons[1];

                try {
                    for (int i = 0; i < 100; i++) {
                        bitmaps[i] = new Bitmap(archiveMedia, "mapscene", i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 100; i++) {
                        imageMapFunctions[i] = new Sprite(archiveMedia, "mapfunction", i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 20; i++) {
                        imageHitMarks[i] = new Sprite(archiveMedia, "hitmarks", i);
                    }
                } catch (Exception e) {
                }

                try {
                    for (int i = 0; i < 8; i++) {
                        imageHeadIcons[i] = new Sprite(archiveMedia, "headicons", i);
                    }
                } catch (Exception e) {
                }

                try {
                    imageOverlayMultiway = imageHeadIcons[1];
                } catch (Exception e) {

                }

                bitmap3 = new Bitmap(archiveMedia, "scollbar", 0);
                bitmap2 = new Bitmap(archiveMedia, "scollbar", 1);

                int redOffset = (int) (Math.random() * 11D) - 5;
                int greenOffset = (int) (Math.random() * 11D) - 5;
                int blueOffset = (int) (Math.random() * 11D) - 5;
                int offset = (int) (Math.random() * 21D) - 10;

                for (int i = 0; i < 100; i++) {
                    if (imageMapFunctions[i] != null) {
                        imageMapFunctions[i].translateRgb(redOffset + offset, greenOffset + offset, blueOffset + offset);
                    }
                    if (bitmaps[i] != null) {
                        bitmaps[i].translateRgb(redOffset + offset, greenOffset + offset, blueOffset + offset);
                    }
                }
            }

            {
                Canvas3D.unpack_textures(archiveTexture);
                Canvas3D.createPalette(0.8D);
                Canvas3D.setupTexelPools();
            }

            {
                Sequence.unpack(archiveConfig);
                LocConfig.unpack(archiveConfig);
                Floor.unpack(archiveConfig);
                ObjConfig.unpack(archiveConfig);
                ActorConfig.unpack(archiveConfig);
                IdentityKit.unpack(archiveConfig);
                SpotAnimConfig.unpack(archiveConfig);
                Varp.unpack(archiveConfig);
                VarBit.unpack(archiveConfig);
            }

            {
                if (!lowDetail) {
                    WaveSound.unpack(new Buffer(archiveSound.get("sounds.dat")));
                }
            }

            {
                Widget.unpack(archiveWidget, new BitmapFont[]{BitmapFont.SMALL, BitmapFont.NORMAL, BitmapFont.BOLD, BitmapFont.FANCY}, archiveMedia);
            }

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
                viewportPixels = Canvas3D.pixels;

                int zArray[] = new int[9];
                for (int z_index = 0; z_index < 9; z_index++) {
                    int k8 = 128 + ((z_index * 32) + 15);
                    int l8 = 600 + (k8 * 3);
                    int sin = Canvas3D.sin[k8];
                    zArray[z_index] = l8 * sin >> 16;
                }

                Landscape.setupViewport(500, 800, 512, 334, zArray);
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
