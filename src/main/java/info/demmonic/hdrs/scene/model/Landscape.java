package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.node.Chain;
import info.demmonic.hdrs.node.impl.Renderable;
import info.demmonic.hdrs.util.ColorUtils;
import info.demmonic.hdrs.util.MathUtils;

public class Landscape {

    public static final byte anIntArray463[] = {53, -53, -53, 53};
    public static final byte anIntArray464[] = {-53, -53, 53, 53};
    public static final byte anIntArray465[] = {-45, 45, 45, -45};
    public static final byte anIntArray466[] = {45, 45, -45, -45};
    public static final int anIntArray478[] = {0x13, 0x37, 0x26, 0x9B, 0xFF, 0x6E, 0x89, 0xCD, 0x4C};
    public static final int anIntArray479[] = {160, 192, 80, 96, 0, 144, 80, 48, 160};
    public static final int anIntArray480[] = {76, 8, 137, 4, 0, 1, 38, 2, 19};
    public static final int anIntArray481[] = {0, 0, 2, 0, 0, 2, 1, 1, 0};
    public static final int anIntArray482[] = {2, 0, 0, 2, 0, 0, 0, 4, 4};
    public static final int anIntArray483[] = {0, 4, 4, 8, 0, 0, 8, 0, 0};
    public static final int anIntArray484[] = {1, 1, 0, 0, 0, 8, 0, 0, 8};
    public static final int TEXTURE_HSL[] = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
    public static StaticLoc aClass28Array462[] = new StaticLoc[100];
    public static int anInt446;
    public static int camLocalX;
    public static int camLocalY;
    public static int camPCos;
    public static int camPSin;
    public static int camX;
    public static int camY;
    public static int camYCos;
    public static int camYSin;
    public static int camZ;
    public static int clickLocalX = -1;
    public static int clickLocalY = -1;
    public static int clickX;
    public static int clickY;
    public static CullingBox[] cullingBoxes = new CullingBox[500];
    public static int cullPlaneCount;
    public static int cullingBoxCount[];
    public static CullingBox cullingBoxes1[][];
    public static int cullingPosition;
    public static int cycle;
    public static boolean input_requested;
    public static int maxVisibleX;
    public static int maxVisibleY;
    public static int minVisibleX;
    public static int minVisibleY;
    public static int occlusionTopPlane;
    public static int scrCX;
    public static int scrCY;
    public static int scrH;
    public static int scrW;
    public static int scrX;
    public static int scrY;
    public static Chain tileQueue = new Chain();
    public static boolean cullingMap[][];
    public static boolean visibilityMap[][][][] = new boolean[8][32][51][51];

    static {
        cullPlaneCount = 4;
        cullingBoxCount = new int[cullPlaneCount];
        cullingBoxes1 = new CullingBox[cullPlaneCount][500];
    }

    public int anInt488;
    public int anIntArray486[];
    public int anIntArray487[];
    public short height_map[][][];
    public int locCount;
    public StaticLoc locs[];

    public int MINIMAP_OVERLAY_MASK[][] = {new int[16],
            {
                    1, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1
            },
            {
                    1, 0, 0, 0,
                    1, 1, 0, 0,
                    1, 1, 1, 0,
                    1, 1, 1, 1
            },
            {
                    1, 1, 0, 0,
                    1, 1, 0, 0,
                    1, 0, 0, 0,
                    1, 0, 0, 0
            },
            {
                    0, 0, 1, 1,
                    0, 0, 1, 1,
                    0, 0, 0, 1,
                    0, 0, 0, 1
            },
            {
                    0, 1, 1, 1,
                    0, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1
            },
            {
                    1, 1, 1, 0,
                    1, 1, 1, 0,
                    1, 1, 1, 1,
                    1, 1, 1, 1
            },
            {
                    1, 1, 0, 0,
                    1, 1, 0, 0,
                    1, 1, 0, 0,
                    1, 1, 0, 0
            },
            {
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    1, 0, 0, 0,
                    1, 1, 0, 0
            },
            {
                    1, 1, 1, 1,
                    1, 1, 1, 1,
                    0, 1, 1, 1,
                    0, 0, 1, 1
            },
            {
                    1, 1, 1, 1,
                    1, 1, 0, 0,
                    1, 0, 0, 0,
                    1, 0, 0, 0
            },
            {
                    0, 0, 0, 0,
                    0, 0, 1, 1,
                    0, 1, 1, 1,
                    0, 1, 1, 1
            },
            {
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 1, 1, 0,
                    1, 1, 1, 1
            }
    };
    public int MINIMAP_OVERLAY_MASK_ROTATION[][] = {
            {
                    0, 1, 2, 3,
                    4, 5, 6, 7,
                    8, 9, 10, 11,
                    12, 13, 14, 15
            },
            {
                    12, 8, 4, 0,
                    13, 9, 5, 1,
                    14, 10, 6, 2,
                    15, 11, 7, 3
            },
            {
                    15, 14, 13, 12,
                    11, 10, 9, 8,
                    7, 6, 5, 4,
                    3, 2, 1, 0
            },
            {
                    3, 7, 11, 15,
                    2, 6, 10, 14,
                    1, 5, 9, 13,
                    0, 4, 8, 12
            }
    };
    public int plane;
    public int sizeX;
    public int sizeY;
    public int sizeZ;

    public int tileCycle[][][];
    public Tile[][][] tiles;

    public Landscape(int sizeX, int sizeY, int sizeZ, short heightMap[][][]) {
        this.locs = new StaticLoc[5000];
        this.anIntArray486 = new int[10000];
        this.anIntArray487 = new int[10000];
        this.sizeZ = sizeZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = new Tile[sizeZ][sizeX][sizeY];
        this.tileCycle = new int[sizeZ][sizeX + 1][sizeY + 1];
        this.height_map = heightMap;
        this.reset();
    }

    public static void createCullingBox(int plane, int min_x, int max_x, int min_y, int max_y, int min_vertex_height, int max_vertex_height, int type) {
        CullingBox box = new CullingBox();
        box.localMinX = min_x / 128;
        box.localMaxX = max_x / 128;
        box.localMinY = min_y / 128;
        box.localMaxY = max_y / 128;
        box.occlusionType = type;
        box.minX = min_x;
        box.maxX = max_x;
        box.minY = min_y;
        box.maxY = max_y;
        box.maxZ = max_vertex_height;
        box.minZ = min_vertex_height;
        cullingBoxes1[plane][cullingBoxCount[plane]++] = box;
    }

    public static boolean is_visible(int sceneX, int sceneY, int sceneZ) {
        int x = sceneY * camYSin + sceneX * camYCos >> 16;
        int i1 = sceneY * camYCos - sceneX * camYSin >> 16;
        int z = sceneZ * camPSin + i1 * camPCos >> 16;
        int y = sceneZ * camPCos - i1 * camPSin >> 16;

        if (z < 50 || z > 3500) {
            return false;
        }

        int screenX = scrCX + (x << 9) / z;
        int screen_y = scrCY + (y << 9) / z;
        return screenX >= scrX && screenX <= scrW && screen_y >= scrY && screen_y <= scrH;
    }

    public static void nullify() {
        aClass28Array462 = null;
        cullingBoxCount = null;
        cullingBoxes1 = null;
        tileQueue = null;
        visibilityMap = null;
        cullingMap = null;
    }

    public static void setupViewport(int nearZ, int farZ, int screenWidth, int screenHeight, int zArray[]) {
        Landscape.scrX = 0;
        Landscape.scrY = 0;
        Landscape.scrW = screenWidth;
        Landscape.scrH = screenHeight;
        Landscape.scrCX = screenWidth / 2;
        Landscape.scrCY = screenHeight / 2;

        boolean visibility_map[][][][] = new boolean[9][32][53][53];

        for (int pitch = Camera.MIN_PITCH; pitch <= Camera.MAX_PITCH + 1; pitch += 32) {
            for (int yaw = 0; yaw < 2048; yaw += 64) {
                camPSin = MathUtils.sin[pitch];
                camPCos = MathUtils.cos[pitch];
                camYSin = MathUtils.sin[yaw];
                camYCos = MathUtils.cos[yaw];
                int z_index = (pitch - 128) / 32;
                int yaw_index = yaw / 64;

                for (int x = -26; x <= 26; x++) {
                    for (int y = -26; y <= 26; y++) {
                        int scene_x = x * 128;
                        int scene_y = y * 128;
                        boolean is_visible = false;

                        for (int z = -nearZ; z <= farZ; z += 128) {
                            if (!is_visible(scene_x, scene_y, zArray[z_index] + z)) {
                                continue;
                            }
                            is_visible = true;
                            break;
                        }

                        visibility_map[z_index][yaw_index][x + 25 + 1][y + 25 + 1] = is_visible;
                    }
                }
            }
        }

        for (int pitch = 0; pitch < 8; pitch++) {
            for (int yaw = 0; yaw < 32; yaw++) {
                for (int x = -25; x < 25; x++) {
                    for (int y = -25; y < 25; y++) {
                        boolean visible = false;

                        label0:
                        for (int l3 = -1; l3 <= 1; l3++) {
                            for (int j4 = -1; j4 <= 1; j4++) {
                                if (visibility_map[pitch][yaw][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else if (visibility_map[pitch][(yaw + 1) % 31][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else if (visibility_map[pitch + 1][yaw][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else {
                                    if (!visibility_map[pitch + 1][(yaw + 1) % 31][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                        continue;
                                    }
                                    visible = true;
                                }
                                break label0;
                            }
                        }

                        Landscape.visibilityMap[pitch][yaw][x + 25][y + 25] = visible;
                    }
                }
            }
        }
    }

    public boolean add(Renderable r, int sceneX, int sceneY, int sceneZ, int plane, int angle, int radius, boolean rotate, int uid) {
        if (r == null) {
            return true;
        }

        int x1 = sceneX - radius;
        int y1 = sceneY - radius;
        int x2 = sceneX + radius;
        int y2 = sceneY + radius;

        if (rotate) {
            if (angle > 640 && angle < 1408) {
                y2 += 128;
            }
            if (angle > 1152 && angle < 1920) {
                x2 += 128;
            }
            if (angle > 1664 || angle < 384) {
                y1 -= 128;
            }
            if (angle > 128 && angle < 896) {
                x1 -= 128;
            }
        }

        x1 /= 128;
        y1 /= 128;
        x2 /= 128;
        y2 /= 128;

        return add(r, sceneX, sceneY, sceneZ, plane, x1, y1, (x2 - x1) + 1, (y2 - y1) + 1, uid, (byte) 0, angle, true);
    }

    public boolean add(Renderable r, int l_x, int l_y, int l_z, int size_x, int size_y, int v_height, byte arrangement, int angle, int uid) {
        if (r == null) {
            return true;
        } else {
            int sceneX = l_x * 128 + 64 * size_y;
            int sceneY = l_y * 128 + 64 * size_x;
            return add(r, sceneX, sceneY, v_height, l_z, l_x, l_y, size_y, size_x, uid, arrangement, angle, false);
        }
    }

    public boolean add(Renderable node, int x, int y, int z, int plane, int local_x0, int local_y0, int local_x1, int local_y1, int uid, byte arrangement, int angle, boolean append) {
        for (int x0 = local_x0; x0 < local_x0 + local_x1; x0++) {
            for (int y0 = local_y0; y0 < local_y0 + local_y1; y0++) {
                if (x0 < 0 || y0 < 0 || x0 >= sizeX || y0 >= sizeY) {
                    return false;
                }
                Tile t = tiles[plane][x0][y0];
                if (t != null && t.locCount >= 5) {
                    return false;
                }
            }
        }

        StaticLoc sl = new StaticLoc();
        sl.uid = uid;
        sl.arrangement = arrangement;
        sl.plane = plane;
        sl.x = x;
        sl.y = y;
        sl.z = z;
        sl.node = node;
        sl.rotation = angle;
        sl.localX0 = local_x0;
        sl.localY0 = local_y0;
        sl.localX1 = (local_x0 + local_x1) - 1;
        sl.localY1 = (local_y0 + local_y1) - 1;

        for (int x0 = local_x0; x0 < local_x0 + local_x1; x0++) {
            for (int y0 = local_y0; y0 < local_y0 + local_y1; y0++) {
                int flag = 0;

                if (x0 > local_x0) {
                    flag++;
                }

                if (x0 < (local_x0 + local_x1) - 1) {
                    flag += 4;
                }

                if (y0 > local_y0) {
                    flag += 8;
                }

                if (y0 < (local_y0 + local_y1) - 1) {
                    flag += 2;
                }

                for (int z0 = plane; z0 >= 0; z0--) {
                    if (tiles[z0][x0][y0] == null) {
                        tiles[z0][x0][y0] = new Tile(z0, x0, y0);
                    }
                }

                Tile t = tiles[plane][x0][y0];
                t.locs[t.locCount] = sl;
                t.locFlag[t.locCount] = flag;
                t.flags |= flag;
                t.locCount++;
            }

        }

        if (append) {
            locs[locCount++] = sl;
        }
        return true;
    }

    public boolean add(Renderable r, int x, int y, int z, int plane, int x1, int y1, int x2, int y2, int rotation, int uid) {
        if (r == null) {
            return true;
        }
        return add(r, x, y, z, plane, x1, y1, (x2 - x1) + 1, (y2 - y1) + 1, uid, (byte) 0, rotation, true);
    }

    public void addBridge(int x, int y) {
        Tile _t = tiles[0][x][y];

        for (int z = 0; z < 3; z++) {
            Tile t = tiles[z][x][y] = tiles[z + 1][x][y];
            if (t != null) {
                t.z--;
                for (int i = 0; i < t.locCount; i++) {
                    StaticLoc sl = t.locs[i];
                    if ((sl.uid >> 29 & 3) == 2 && sl.localX0 == x && sl.localY0 == y) {
                        sl.plane--;
                    }
                }

            }
        }

        if (tiles[0][x][y] == null) {
            tiles[0][x][y] = new Tile(0, x, y);
        }

        tiles[0][x][y].bridge = _t;
        tiles[3][x][y] = null;
    }

    public void addGroundDecoration(int x, int y, int z, int plane, Renderable r, int uid, byte arrangement) {
        if (r == null) {
            return;
        }

        GroundDecoration gd = new GroundDecoration();
        gd.node = r;
        gd.sceneX = x * 128 + 64;
        gd.sceneY = y * 128 + 64;
        gd.sceneZ = z;
        gd.uid = uid;
        gd.arrangement = arrangement;

        if (tiles[plane][x][y] == null) {
            tiles[plane][x][y] = new Tile(plane, x, y);
        }

        tiles[plane][x][y].groundDecoration = gd;
    }

    public void addItemPile(int x, int y, int z, int plane, Renderable r_top, Renderable r_mid, Renderable r_btm, int uid) {
        ItemPile ip = new ItemPile();
        ip.sceneX = x * 128 + 64;
        ip.sceneY = y * 128 + 64;
        ip.sceneZ = z;
        ip.uid = uid;
        ip.top = r_top;
        ip.middle = r_mid;
        ip.bottom = r_btm;

        int min_z = 0;

        Tile t = tiles[plane][x][y];

        if (t != null) {
            for (int i = 0; i < t.locCount; i++) {
                if (t.locs[i].node instanceof Model) {
                    int offset = ((Model) t.locs[i].node).pile_height;

                    if (offset > min_z) {
                        min_z = offset;
                    }
                }
            }
        }

        ip.offZ = min_z;

        if (tiles[plane][x][y] == null) {
            tiles[plane][x][y] = new Tile(plane, x, y);
        }

        tiles[plane][x][y].itemPile = ip;
    }

    public void addTile(int plane, int x, int y, int shape, int rotation, byte textureIndex, short v_sw, short v_se, short v_ne, short v_nw, int hsl_sw, int hsl_se, int hsl_ne, int hsl_nw, int rgb_sw, int rgb_se, int rgb_ne, int rgb_nw, int rgb_bitset, int hslBitset) {
        if (shape == 0) {
            UnderlayTile t = new UnderlayTile(hsl_sw, hsl_se, hsl_ne, hsl_nw, (byte) -1, rgb_bitset, false);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].underlay = t;
        } else if (shape == 1) {
            UnderlayTile t = new UnderlayTile(rgb_sw, rgb_se, rgb_ne, rgb_nw, textureIndex, hslBitset, v_sw == v_se && v_sw == v_ne && v_sw == v_nw);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].underlay = t;
        } else {
            OverlayTile t = new OverlayTile(x, y, v_sw, v_se, v_ne, v_nw, rgb_sw, rgb_se, rgb_ne, rgb_nw, rgb_bitset, hsl_sw, hsl_se, hsl_ne, hsl_nw, hslBitset, textureIndex, rotation, shape);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].overlay = t;
        }
    }

    public void addWall(Renderable r1, Renderable r2, int x, int y, int z, int plane, int rotation_flag, int corner_flag, byte arrangement, boolean flag, int uid) {
        if (r1 == null && r2 == null) {
            return;
        }

        WallLoc wl = new WallLoc();
        wl.uid = uid;
        wl.arrangement = arrangement;
        wl.sceneX = x * 128 + 64;
        wl.sceneY = y * 128 + 64;
        wl.sceneZ = z;
        wl.root = r1;
        wl.extension = r2;
        wl.rotationFlag = rotation_flag;
        wl.cornerFlag = corner_flag;

        for (int current_plane = plane; current_plane >= 0; current_plane--) {
            if (tiles[current_plane][x][y] == null) {
                tiles[current_plane][x][y] = new Tile(current_plane, x, y);
            }
        }

        tiles[plane][x][y].wall = wl;
    }

    public void addWallDecoration(Renderable r, int x, int y, int plane, int offset_x, int offset_y, int offset_z, int rotation, byte arrangement, int flags, int uid) {
        if (r == null) {
            return;
        }

        WallDecoration wd = new WallDecoration();
        wd.uid = uid;
        wd.arrangement = arrangement;
        wd.x = x * 128 + 64 + offset_x;
        wd.y = y * 128 + 64 + offset_y;
        wd.z = offset_z;
        wd.node = r;
        wd.flags = flags;
        wd.rotation = rotation;

        for (int i = plane; i >= 0; i--) {
            if (tiles[i][x][y] == null) {
                tiles[i][x][y] = new Tile(i, x, y);
            }
        }

        tiles[plane][x][y].wallDecoration = wd;
    }

    public void applyUntexturedObjects(int light_x, int light_y, int light_z, int light_brightness, int specular_factor) {
        int light_len = (int) Math.sqrt(light_x * light_x + light_y * light_y + light_z * light_z);
        int specular_distribution = specular_factor * light_len >> 8;

        for (int plane = 0; plane < sizeZ; plane++) {
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    Tile t = tiles[plane][x][y];
                    if (t != null) {
                        WallLoc wl = t.wall;
                        if (wl != null && wl.root != null && wl.root.normal != null) {
                            method307((Model) wl.root, x, y, plane, 1, 1);
                            if (wl.extension != null && wl.extension.normal != null) {
                                method307((Model) wl.extension, x, y, plane, 1, 1);
                                method308((Model) wl.root, (Model) wl.extension, 0, 0, 0, false);
                                ((Model) wl.extension).applyLighting(light_brightness, specular_distribution, light_x, light_y, light_z);
                            }
                            ((Model) wl.root).applyLighting(light_brightness, specular_distribution, light_x, light_y, light_z);
                        }

                        for (int k2 = 0; k2 < t.locCount; k2++) {
                            StaticLoc sl = t.locs[k2];
                            if (sl != null && sl.node != null && sl.node.normal != null) {
                                method307((Model) sl.node, x, y, plane, (sl.localX1 - sl.localX0) + 1, (sl.localY1 - sl.localY0) + 1);
                                ((Model) sl.node).applyLighting(light_brightness, specular_distribution, light_x, light_y, light_z);
                            }
                        }

                        GroundDecoration gd = t.groundDecoration;
                        if (gd != null && gd.node.normal != null) {
                            method306((Model) gd.node, x, y, plane);
                            ((Model) gd.node).applyLighting(light_brightness, specular_distribution, light_x, light_y, light_z);
                        }
                    }
                }

            }

        }

    }

    public void clearLocs() {
        for (int i = 0; i < locCount; i++) {
            remove(locs[i]);
            locs[i] = null;
        }
        locCount = 0;
    }

    public void clearLocs(int x, int y, int z) {
        Tile t = tiles[z][x][y];
        if (t == null) {
            return;
        }
        for (int i = 0; i < t.locCount; i++) {
            StaticLoc sl = t.locs[i];
            if ((sl.uid >> 29 & 3) == 2 && sl.localX0 == x && sl.localY0 == y) {
                remove(sl);
                return;
            }
        }

    }

    public void clicked(int click_y, int click_x) {
        input_requested = true;
        Landscape.clickX = click_x;
        Landscape.clickY = click_y;
        clickLocalX = -1;
        clickLocalY = -1;
    }

    public void draw(int cam_x, int cam_y, int cam_yaw, int cam_z, int occlusion_top_plane, int cam_pitch) {
        if (cam_x < 0) {
            cam_x = 0;
        } else if (cam_x >= sizeX * 128) {
            cam_x = sizeX * 128 - 1;
        }

        if (cam_y < 0) {
            cam_y = 0;
        } else if (cam_y >= sizeY * 128) {
            cam_y = sizeY * 128 - 1;
        }

        Landscape.cycle++;
        Landscape.camPSin = MathUtils.sin[cam_pitch];
        Landscape.camPCos = MathUtils.cos[cam_pitch];
        Landscape.camYSin = MathUtils.sin[cam_yaw];
        Landscape.camYCos = MathUtils.cos[cam_yaw];
        Landscape.cullingMap = visibilityMap[(cam_pitch - 128) / 32][cam_yaw / 64];
        Landscape.camX = cam_x;
        Landscape.camZ = cam_z;
        Landscape.camY = cam_y;
        Landscape.camLocalX = cam_x / 128;
        Landscape.camLocalY = cam_y / 128;
        Landscape.occlusionTopPlane = occlusion_top_plane;

        Landscape.minVisibleX = camLocalX - 25;

        if (minVisibleX < 0) {
            minVisibleX = 0;
        }

        Landscape.minVisibleY = camLocalY - 25;

        if (minVisibleY < 0) {
            minVisibleY = 0;
        }

        Landscape.maxVisibleX = camLocalX + 25;

        if (maxVisibleX > sizeX) {
            maxVisibleX = sizeX;
        }

        Landscape.maxVisibleY = camLocalY + 25;

        if (maxVisibleY > sizeY) {
            maxVisibleY = sizeY;
        }

        updateCulling();
        Landscape.anInt446 = 0;

        for (int z = plane; z < sizeZ; z++) {
            Tile _t[][] = tiles[z];
            for (int x = minVisibleX; x < maxVisibleX; x++) {
                for (int y = minVisibleY; y < maxVisibleY; y++) {
                    Tile t = _t[x][y];
                    if (t != null) {
                        if (t.topPlane > occlusion_top_plane || !cullingMap[(x - camLocalX) + 25][(y - camLocalY) + 25] && height_map[z][x][y] - cam_z < 2000) {
                            t.aBoolean1322 = false;
                            t.aBoolean1323 = false;
                            t.anInt1325 = 0;
                        } else {
                            t.aBoolean1322 = true;
                            t.aBoolean1323 = true;
                            if (t.locCount > 0) {
                                t.aBoolean1324 = true;
                            } else {
                                t.aBoolean1324 = false;
                            }
                            anInt446++;
                        }
                    }
                }

            }

        }

        for (int z = plane; z < sizeZ; z++) {
            Tile _t[][] = tiles[z];

            for (int x_off = -25; x_off <= 0; x_off++) {
                int x0 = camLocalX + x_off;
                int x1 = camLocalX - x_off;

                if (x0 >= minVisibleX || x1 < maxVisibleX) {
                    for (int y_off = -25; y_off <= 0; y_off++) {
                        int y0 = camLocalY + y_off;
                        int y1 = camLocalY - y_off;

                        if (x0 >= minVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = _t[x0][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = _t[x0][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                        }

                        if (x1 < maxVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = _t[x1][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = _t[x1][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                        }

                        if (anInt446 == 0) {
                            input_requested = false;
                            return;
                        }
                    }
                }
            }
        }

        for (int z = plane; z < sizeZ; z++) {
            Tile _t[][] = tiles[z];
            for (int x_off = -25; x_off <= 0; x_off++) {
                int x0 = camLocalX + x_off;
                int x1 = camLocalX - x_off;
                if (x0 >= minVisibleX || x1 < maxVisibleX) {
                    for (int y_off = -25; y_off <= 0; y_off++) {
                        int y0 = camLocalY + y_off;
                        int y1 = camLocalY - y_off;
                        if (x0 >= minVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = _t[x0][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = _t[x0][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                        }
                        if (x1 < maxVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = _t[x1][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = _t[x1][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                        }
                        if (anInt446 == 0) {
                            input_requested = false;
                            return;
                        }
                    }

                }
            }

        }

        input_requested = false;
    }

    public void drawMinimapTile(int pixels[], int start, int width, int plane, int x, int y) {
        Tile t = tiles[plane][x][y];

        if (t == null) {
            return;
        }

        UnderlayTile ut = t.underlay;

        if (ut != null) {
            int rgb = ut.rgb;

            if (rgb == 0) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                pixels[start] = rgb;
                pixels[start + 1] = rgb;
                pixels[start + 2] = rgb;
                pixels[start + 3] = rgb;
                start += width;
            }
            return;
        }

        OverlayTile ot = t.overlay;

        if (ot == null) {
            return;
        }

        int rgb = ot.rgb;
        int hsl = ot.hsl;
        int mask[] = MINIMAP_OVERLAY_MASK[ot.shape];
        int rotate[] = MINIMAP_OVERLAY_MASK_ROTATION[ot.rotation];
        int i = 0;

        if (rgb != 0) {
            for (int j = 0; j < 4; j++) {
                pixels[start] = mask[rotate[i++]] != 0 ? hsl : rgb;
                pixels[start + 1] = mask[rotate[i++]] != 0 ? hsl : rgb;
                pixels[start + 2] = mask[rotate[i++]] != 0 ? hsl : rgb;
                pixels[start + 3] = mask[rotate[i++]] != 0 ? hsl : rgb;
                start += width;
            }
            return;
        }

        for (int j = 0; j < 4; j++) {
            if (mask[rotate[i++]] != 0) {
                pixels[start] = hsl;
            }

            if (mask[rotate[i++]] != 0) {
                pixels[start + 1] = hsl;
            }

            if (mask[rotate[i++]] != 0) {
                pixels[start + 2] = hsl;
            }

            if (mask[rotate[i++]] != 0) {
                pixels[start + 3] = hsl;
            }

            start += width;
        }

    }

    public void drawOverlayTile(OverlayTile ot, int local_x, int local_y, int pitch_sin, int pitch_cos, int yaw_sin, int yaw_cos) {
        int i = ot.triangleX.length;

        for (int j = 0; j < i; j++) {
            int x = ot.triangleX[j] - camX;
            int y = ot.triangleY[j] - camZ;
            int z = ot.triangleZ[j] - camY;

            int w = z * yaw_sin + x * yaw_cos >> 16;
            z = z * yaw_cos - x * yaw_sin >> 16;
            x = w;

            w = y * pitch_cos - z * pitch_sin >> 16;
            z = y * pitch_sin + z * pitch_cos >> 16;
            y = w;

            if (z < 50) {
                return;
            }

            if (ot.triangleTextureIndex != null) {
                OverlayTile.tmpTriangleX[j] = x;
                OverlayTile.tmpTriangleY[j] = y;
                OverlayTile.tmpTriangleZ[j] = z;
            }

            // Setting the temporary on-screen values.
            OverlayTile.tmpScreenX[j] = Canvas3D.centerX + ((x << 9) / z);
            OverlayTile.tmpScreenY[j] = Canvas3D.centerY + ((y << 9) / z);
        }

        Canvas3D.opacity = 0;
        i = ot.vertexX.length;

        for (int j = 0; j < i; j++) {
            int v_x_i = ot.vertexX[j];
            int v_y_i = ot.vertexY[j];
            int v_z_i = ot.vertexZ[j];
            int x1 = OverlayTile.tmpScreenX[v_x_i];
            int x2 = OverlayTile.tmpScreenX[v_y_i];
            int x3 = OverlayTile.tmpScreenX[v_z_i];
            int y1 = OverlayTile.tmpScreenY[v_x_i];
            int y2 = OverlayTile.tmpScreenY[v_y_i];
            int y3 = OverlayTile.tmpScreenY[v_z_i];

            if ((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2) > 0) {
                Canvas3D.checkBounds = false;

                if (x1 < 0 || x2 < 0 || x3 < 0 || x1 > Canvas2D.bound || x2 > Canvas2D.bound || x3 > Canvas2D.bound) {
                    Canvas3D.checkBounds = true;
                }

                // Used for clicking on the map.
                if (input_requested && triContains(clickX, clickY, x1, y1, x2, y2, x3, y3)) {
                    clickLocalX = local_x;
                    clickLocalY = local_y;
                }

                if (ot.triangleTextureIndex == null || ot.triangleTextureIndex[j] == -1) {
                    if (ot.vertexColorA[j] != 12345678) {
                        Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j]);
                    }
                } else if (!Game.lowDetail) {
                    if (ot.ignore_uv) {
                        Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j], OverlayTile.tmpTriangleX[0], OverlayTile.tmpTriangleY[0], OverlayTile.tmpTriangleZ[0], OverlayTile.tmpTriangleX[1], OverlayTile.tmpTriangleY[1], OverlayTile.tmpTriangleZ[1], OverlayTile.tmpTriangleX[3], OverlayTile.tmpTriangleY[3], OverlayTile.tmpTriangleZ[3], ot.triangleTextureIndex[j]);
                    } else {
                        Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j], OverlayTile.tmpTriangleX[v_x_i], OverlayTile.tmpTriangleY[v_x_i], OverlayTile.tmpTriangleZ[v_x_i], OverlayTile.tmpTriangleX[v_y_i], OverlayTile.tmpTriangleY[v_y_i], OverlayTile.tmpTriangleZ[v_y_i], OverlayTile.tmpTriangleX[v_z_i], OverlayTile.tmpTriangleY[v_z_i], OverlayTile.tmpTriangleZ[v_z_i], ot.triangleTextureIndex[j]);
                    }
                } else {
                    int hsl = TEXTURE_HSL[ot.triangleTextureIndex[j]];
                    Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x3, y3, ColorUtils.adjustHslLightness(hsl, ot.vertexColorA[j]), ColorUtils.adjustHslLightness(hsl, ot.vertexColorB[j]), ColorUtils.adjustHslLightness(hsl, ot.vertexColorC[j]));
                }
            }
        }
    }

    public void drawUnderlayTile(UnderlayTile ut, int plane, int cam_pitch_sin, int cam_pitch_cos, int cam_yaw_sin, int cam_yaw_cos, int x, int y) {
        int northWestX;
        int southWestX = northWestX = (x << 7) - camX;
        int southEastZ;
        int southWestZ = southEastZ = (y << 7) - camY;
        int northEastX;
        int southEastX = northEastX = southWestX + 128;
        int northWestZ;
        int northEastZ = northWestZ = southWestZ + 128;

        int sw_y = height_map[plane][x][y] - camZ;
        int se_y = height_map[plane][x + 1][y] - camZ;
        int ne_y = height_map[plane][x + 1][y + 1] - camZ;
        int nw_y = height_map[plane][x][y + 1] - camZ;

        int i = southWestZ * cam_yaw_sin + southWestX * cam_yaw_cos >> 16;
        southWestZ = southWestZ * cam_yaw_cos - southWestX * cam_yaw_sin >> 16;
        southWestX = i;

        i = sw_y * cam_pitch_cos - southWestZ * cam_pitch_sin >> 16;
        southWestZ = sw_y * cam_pitch_sin + southWestZ * cam_pitch_cos >> 16;
        sw_y = i;

        if (southWestZ < 50) {
            return;
        }

        i = southEastZ * cam_yaw_sin + southEastX * cam_yaw_cos >> 16;
        southEastZ = southEastZ * cam_yaw_cos - southEastX * cam_yaw_sin >> 16;
        southEastX = i;

        i = se_y * cam_pitch_cos - southEastZ * cam_pitch_sin >> 16;
        southEastZ = se_y * cam_pitch_sin + southEastZ * cam_pitch_cos >> 16;
        se_y = i;

        if (southEastZ < 50) {
            return;
        }

        i = northEastZ * cam_yaw_sin + northEastX * cam_yaw_cos >> 16;
        northEastZ = northEastZ * cam_yaw_cos - northEastX * cam_yaw_sin >> 16;
        northEastX = i;

        i = ne_y * cam_pitch_cos - northEastZ * cam_pitch_sin >> 16;
        northEastZ = ne_y * cam_pitch_sin + northEastZ * cam_pitch_cos >> 16;
        ne_y = i;

        if (northEastZ < 50) {
            return;
        }

        i = northWestZ * cam_yaw_sin + northWestX * cam_yaw_cos >> 16;
        northWestZ = northWestZ * cam_yaw_cos - northWestX * cam_yaw_sin >> 16;
        northWestX = i;

        i = nw_y * cam_pitch_cos - northWestZ * cam_pitch_sin >> 16;
        northWestZ = nw_y * cam_pitch_sin + northWestZ * cam_pitch_cos >> 16;
        nw_y = i;

        if (northWestZ < 50) {
            return;
        }

        int x1 = Canvas3D.centerX + (southWestX << 9) / southWestZ;
        int y1 = Canvas3D.centerY + (sw_y << 9) / southWestZ;
        int x2 = Canvas3D.centerX + (southEastX << 9) / southEastZ;
        int y2 = Canvas3D.centerY + (se_y << 9) / southEastZ;
        int x3 = Canvas3D.centerX + (northEastX << 9) / northEastZ;
        int y3 = Canvas3D.centerY + (ne_y << 9) / northEastZ;
        int x4 = Canvas3D.centerX + (northWestX << 9) / northWestZ;
        int y4 = Canvas3D.centerY + (nw_y << 9) / northWestZ;

        Canvas3D.opacity = 0;
        if ((x3 - x4) * (y2 - y4) - (y3 - y4) * (x2 - x4) > 0) {
            Canvas3D.checkBounds = false;

            if (x3 < 0 || x4 < 0 || x2 < 0 || x3 > Canvas2D.bound || x4 > Canvas2D.bound || x2 > Canvas2D.bound) {
                Canvas3D.checkBounds = true;
            }

            if (input_requested && triContains(clickX, clickY, x3, y3, x4, y4, x2, y2)) {
                clickLocalX = x;
                clickLocalY = y;
            }

            if (ut.textureIndex == -1) {
                if (ut.hslNe != 12345678) {
                    Canvas3D.drawShadedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe);
                }
            } else if (!Game.lowDetail) {
                if (ut.isFlat) {
                    Canvas3D.drawTexturedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe, southWestX, sw_y, southWestZ, southEastX, se_y, southEastZ, northWestX, nw_y, northWestZ, ut.textureIndex);
                } else {
                    Canvas3D.drawTexturedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe, northEastX, ne_y, northEastZ, northWestX, nw_y, northWestZ, southEastX, se_y, southEastZ, ut.textureIndex);
                }
            } else {
                int i7 = TEXTURE_HSL[ut.textureIndex];
                Canvas3D.drawShadedTriangle(x3, y3, x4, y4, x2, y2, ColorUtils.adjustHslLightness(i7, ut.hslNe), ColorUtils.adjustHslLightness(i7, ut.hslNw), ColorUtils.adjustHslLightness(i7, ut.hslSe));
            }
        }

        if ((x1 - x2) * (y4 - y2) - (y1 - y2) * (x4 - x2) > 0) {
            Canvas3D.checkBounds = false;

            if (x1 < 0 || x2 < 0 || x4 < 0 || x1 > Canvas2D.bound || x2 > Canvas2D.bound || x4 > Canvas2D.bound) {
                Canvas3D.checkBounds = true;
            }

            if (input_requested && triContains(clickX, clickY, x1, y1, x2, y2, x4, y4)) {
                clickLocalX = x;
                clickLocalY = y;
            }

            if (ut.textureIndex == -1) {
                if (ut.hslSw != 12345678) {
                    Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x4, y4, ut.hslSw, ut.hslSe, ut.hslNw);
                }
            } else {
                if (!Game.lowDetail) {
                    Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x4, y4, ut.hslSw, ut.hslSe, ut.hslNw, southWestX, sw_y, southWestZ, southEastX, se_y, southEastZ, northWestX, nw_y, northWestZ, ut.textureIndex);
                } else {
                    int j7 = TEXTURE_HSL[ut.textureIndex];
                    Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x4, y4, ColorUtils.adjustHslLightness(j7, ut.hslSw), ColorUtils.adjustHslLightness(j7, ut.hslSe), ColorUtils.adjustHslLightness(j7, ut.hslNw));
                }
            }
        }
    }

    public int getArrangement(int plane, int x, int y, int uid) {
        Tile t = tiles[plane][x][y];

        if (t == null) {
            return -1;
        }

        if (t.wall != null && t.wall.uid == uid) {
            return t.wall.arrangement & 0xff;
        }

        if (t.wallDecoration != null && t.wallDecoration.uid == uid) {
            return t.wallDecoration.arrangement & 0xff;
        }

        if (t.groundDecoration != null && t.groundDecoration.uid == uid) {
            return t.groundDecoration.arrangement & 0xff;
        }

        for (int i = 0; i < t.locCount; i++) {
            if (t.locs[i].uid == uid) {
                return t.locs[i].arrangement & 0xff;
            }
        }
        return -1;
    }

    public GroundDecoration getGroundDecoration(int x, int y, int z) {
        Tile t = tiles[z][x][y];
        if (t == null || t.groundDecoration == null) {
            return null;
        }
        return t.groundDecoration;
    }

    public int getGroundDecorationUid(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];
        if (t == null || t.groundDecoration == null) {
            return 0;
        } else {
            return t.groundDecoration.uid;
        }
    }

    public StaticLoc getLoc(int x, int y, int plane) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return null;
        }
        for (int i = 0; i < t.locCount; i++) {
            StaticLoc sl = t.locs[i];
            if ((sl.uid >> 29 & 3) == 2 && sl.localX0 == x && sl.localY0 == y) {
                return sl;
            }
        }
        return null;
    }

    public int getLocUid(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return 0;
        }
        for (int l = 0; l < t.locCount; l++) {
            StaticLoc sl = t.locs[l];
            if ((sl.uid >> 29 & 3) == 2 && sl.localX0 == x && sl.localY0 == y) {
                return sl.uid;
            }
        }
        return 0;
    }

    public WallLoc getWall(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return null;
        } else {
            return t.wall;
        }
    }

    public WallDecoration getWallDecoration(int x, int j, int y, int z) {
        Tile t = tiles[z][x][y];
        if (t == null) {
            return null;
        } else {
            return t.wallDecoration;
        }
    }

    public int getWallDecorationUid(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];
        if (t == null || t.wallDecoration == null) {
            return 0;
        } else {
            return t.wallDecoration.uid;
        }
    }

    public int getWallUid(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];

        if (t == null || t.wall == null) {
            return 0;
        }

        return t.wall.uid;
    }

    public boolean isCulled(int x, int y, int z) {
        for (int i = 0; i < cullingPosition; i++) {
            CullingBox box = cullingBoxes[i];

            if (box.cullingMode == 1) {
                int dx = box.minX - x;

                if (dx > 0) {
                    int min_y = box.minY + (box.anInt801 * dx >> 8);
                    int max_y = box.maxY + (box.anInt802 * dx >> 8);
                    int min_z = box.maxZ + (box.anInt803 * dx >> 8);
                    int max_z = box.minZ + (box.anInt804 * dx >> 8);

                    if (y >= min_y && y <= max_y && z >= min_z && z <= max_z) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 2) {
                int dx = x - box.minX;

                if (dx > 0) {
                    int min_y = box.minY + (box.anInt801 * dx >> 8);
                    int max_y = box.maxY + (box.anInt802 * dx >> 8);
                    int min_z = box.maxZ + (box.anInt803 * dx >> 8);
                    int max_z = box.minZ + (box.anInt804 * dx >> 8);

                    if (y >= min_y && y <= max_y && z >= min_z && z <= max_z) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 3) {
                int dy = box.minY - y;

                if (dy > 0) {
                    int min_x = box.minX + (box.anInt799 * dy >> 8);
                    int max_x = box.maxX + (box.anInt800 * dy >> 8);
                    int min_z = box.maxZ + (box.anInt803 * dy >> 8);
                    int max_z = box.minZ + (box.anInt804 * dy >> 8);

                    if (x >= min_x && x <= max_x && z >= min_z && z <= max_z) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 4) {
                int dy = y - box.minY;

                if (dy > 0) {
                    int min_x = box.minX + (box.anInt799 * dy >> 8);
                    int max_x = box.maxX + (box.anInt800 * dy >> 8);
                    int min_z = box.maxZ + (box.anInt803 * dy >> 8);
                    int max_z = box.minZ + (box.anInt804 * dy >> 8);

                    if (x >= min_x && x <= max_x && z >= min_z && z <= max_z) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 5) {
                int dz = z - box.maxZ;

                if (dz > 0) {
                    int min_x = box.minX + (box.anInt799 * dz >> 8);
                    int max_x = box.maxX + (box.anInt800 * dz >> 8);
                    int min_y = box.minY + (box.anInt801 * dz >> 8);
                    int max_y = box.maxY + (box.anInt802 * dz >> 8);

                    if (x >= min_x && x <= max_x && y >= min_y && y <= max_y) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isCulled(int plane, int local_x, int local_y, int height) {
        if (!isTileCulled(plane, local_x, local_y)) {
            return false;
        }

        int tile_x = local_x << 7;
        int tile_y = local_y << 7;

        return isCulled(tile_x + 1, tile_y + 1, height_map[plane][local_x][local_y] - height) && isCulled((tile_x + 128) - 1, tile_y + 1, height_map[plane][local_x + 1][local_y] - height) && isCulled((tile_x + 128) - 1, (tile_y + 128) - 1, height_map[plane][local_x + 1][local_y + 1] - height) && isCulled(tile_x + 1, (tile_y + 128) - 1, height_map[plane][local_x][local_y + 1] - height);
    }

    public boolean isTileCulled(int plane, int x, int y) {
        int i = tileCycle[plane][x][y];

        if (i == -cycle) {
            return false;
        }

        if (i == cycle) {
            return true;
        }

        int s_x = x << 7;
        int s_y = y << 7;

        if (isCulled(s_x + 1, s_y + 1, height_map[plane][x][y]) && isCulled((s_x + 128) - 1, s_y + 1, height_map[plane][x + 1][y]) && isCulled((s_x + 128) - 1, (s_y + 128) - 1, height_map[plane][x + 1][y + 1]) && isCulled(s_x + 1, (s_y + 128) - 1, height_map[plane][x][y + 1])) {
            tileCycle[plane][x][y] = cycle;
            return true;
        } else {
            tileCycle[plane][x][y] = -cycle;
            return false;
        }
    }

    public boolean isWallCulled(int z, int x, int y, int type) {
        if (!isTileCulled(z, x, y)) {
            return false;
        }

        int s_x = x << 7;
        int s_y = y << 7;
        int s_z = height_map[z][x][y] - 1;
        int lv1 = s_z - 120;
        int lv2 = s_z - 230;
        int lv3 = s_z - 238;

        if (type < 0x10) {
            if (type == 0x1) {
                if (s_x > camX) {
                    if (!isCulled(s_x, s_y, s_z)) {
                        return false;
                    }
                    if (!isCulled(s_x, s_y + 128, s_z)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(s_x, s_y, lv1)) {
                        return false;
                    }
                    if (!isCulled(s_x, s_y + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(s_x, s_y, lv2)) {
                    return false;
                }
                return isCulled(s_x, s_y + 128, lv2);
            }
            if (type == 0x2) {
                if (s_y < camY) {
                    if (!isCulled(s_x, s_y + 128, s_z)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y + 128, s_z)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(s_x, s_y + 128, lv1)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(s_x, s_y + 128, lv2)) {
                    return false;
                }
                return isCulled(s_x + 128, s_y + 128, lv2);
            }
            if (type == 0x4) {
                if (s_x < camX) {
                    if (!isCulled(s_x + 128, s_y, s_z)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y + 128, s_z)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(s_x + 128, s_y, lv1)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(s_x + 128, s_y, lv2)) {
                    return false;
                }
                return isCulled(s_x + 128, s_y + 128, lv2);
            }
            if (type == 0x8) {
                if (s_y > camY) {
                    if (!isCulled(s_x, s_y, s_z)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y, s_z)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(s_x, s_y, lv1)) {
                        return false;
                    }
                    if (!isCulled(s_x + 128, s_y, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(s_x, s_y, lv2)) {
                    return false;
                }
                return isCulled(s_x + 128, s_y, lv2);
            }
        }

        if (!isCulled(s_x + 64, s_y + 64, lv3)) {
            return false;
        }

        if (type == 0x10) {
            return isCulled(s_x, s_y + 128, lv2);
        } else if (type == 0x20) {
            return isCulled(s_x + 128, s_y + 128, lv2);
        } else if (type == 0x40) {
            return isCulled(s_x + 128, s_y, lv2);
        } else if (type == 0x80) {
            return isCulled(s_x, s_y, lv2);
        }

        System.out.println("Warning unsupported wall type");
        return true;
    }

    public void method306(Model mesh, int x, int y, int plane) {
        if (x < sizeX) {
            Tile t = tiles[plane][x + 1][y];
            if (t != null && t.groundDecoration != null && t.groundDecoration.node.normal != null) {
                method308(mesh, (Model) t.groundDecoration.node, 128, 0, 0, true);
            }
        }
        if (y < sizeX) {
            Tile t = tiles[plane][x][y + 1];
            if (t != null && t.groundDecoration != null && t.groundDecoration.node.normal != null) {
                method308(mesh, (Model) t.groundDecoration.node, 0, 0, 128, true);
            }
        }
        if (x < sizeX && y < sizeY) {
            Tile t = tiles[plane][x + 1][y + 1];
            if (t != null && t.groundDecoration != null && t.groundDecoration.node.normal != null) {
                method308(mesh, (Model) t.groundDecoration.node, 128, 0, 128, true);
            }
        }
        if (x < sizeX && y > 0) {
            Tile t = tiles[plane][x + 1][y - 1];
            if (t != null && t.groundDecoration != null && t.groundDecoration.node.normal != null) {
                method308(mesh, (Model) t.groundDecoration.node, 128, 0, -128, true);
            }
        }
    }

    public void method307(Model m, int l_x, int l_y, int max_plane, int j, int k) {
        boolean flag = true;
        int start_x = l_x;
        int end_x = l_x + j;
        int start_y = l_y - 1;
        int end_y = l_y + k;

        for (int z = max_plane; z <= max_plane + 1; z++) {
            if (z != this.sizeZ) {
                for (int x = start_x; x <= end_x; x++) {
                    if (x >= 0 && x < sizeX) {
                        for (int y = start_y; y <= end_y; y++) {
                            if (y >= 0 && y < sizeY && (!flag || x >= end_x || y >= end_y || y < l_y && x != l_x)) {
                                Tile t = tiles[z][x][y];

                                if (t != null) {
                                    int v_avg = (height_map[z][x][y] + height_map[z][x + 1][y] + height_map[z][x][y + 1] + height_map[z][x + 1][y + 1]) / 4 - (height_map[max_plane][l_x][l_y] + height_map[max_plane][l_x + 1][l_y] + height_map[max_plane][l_x][l_y + 1] + height_map[max_plane][l_x + 1][l_y + 1]) / 4;
                                    WallLoc wl = t.wall;

                                    if (wl != null && wl.root != null && wl.root.normal != null) {
                                        method308(m, (Model) wl.root, (x - l_x) * 128 + (1 - j) * 64, v_avg, (y - l_y) * 128 + (1 - k) * 64, flag);
                                    }

                                    if (wl != null && wl.extension != null && wl.extension.normal != null) {
                                        method308(m, (Model) wl.extension, (x - l_x) * 128 + (1 - j) * 64, v_avg, (y - l_y) * 128 + (1 - k) * 64, flag);
                                    }

                                    for (int i = 0; i < t.locCount; i++) {
                                        StaticLoc sl = t.locs[i];
                                        if (sl != null && sl.node != null && sl.node.normal != null) {
                                            int w = (sl.localX1 - sl.localX0) + 1;
                                            int l = (sl.localY1 - sl.localY0) + 1;
                                            method308(m, (Model) sl.node, (sl.localX0 - l_x) * 128 + (w - j) * 64, v_avg, (sl.localY0 - l_y) * 128 + (l - k) * 64, flag);
                                        }
                                    }

                                }
                            }
                        }

                    }
                }

                start_x--;
                flag = false;
            }
        }

    }

    public void method308(Model m1, Model m2, int x_offset, int y_offset, int z_offset, boolean flag) {
        anInt488++;

        int i = 0;
        short v_x[] = m2.vertex_x;
        int v_c = m2.vertex_count;

        // Loop through every vertex
        for (int j = 0; j < m1.vertex_count; j++) {
            Vertex v1 = ((Renderable) (m1)).normal[j];
            Vertex v2 = m1.vertices[j];

            if (v2.w != 0) {

                int y = m1.vertex_y[j] - y_offset;
                if (y <= m2.max_y) {

                    int x = m1.vertex_x[j] - x_offset;
                    if (x >= m2.min_x && x <= m2.max_x) {

                        int z = m1.vertex_z[j] - z_offset;
                        if (z >= m2.min_z && z <= m2.max_z) {

                            for (int k = 0; k < v_c; k++) {
                                Vertex v3 = ((Renderable) (m2)).normal[k];
                                Vertex v4 = m2.vertices[k];

                                if (x == v_x[k] && z == m2.vertex_z[k] && y == m2.vertex_y[k] && v4.w != 0) {
                                    v1.x += v4.x;
                                    v1.y += v4.y;
                                    v1.z += v4.z;
                                    v1.w += v4.w;

                                    v3.x += v2.x;
                                    v3.y += v2.y;
                                    v3.z += v2.z;
                                    v3.w += v2.w;

                                    i++;

                                    anIntArray486[j] = anInt488;
                                    anIntArray487[k] = anInt488;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (i < 3 || !flag) {
            return;
        }

        for (int k1 = 0; k1 < m1.triangle_count; k1++) {
            if (anIntArray486[m1.triangle_viewspace_a[k1]] == anInt488 && anIntArray486[m1.triangle_viewspace_b[k1]] == anInt488 && anIntArray486[m1.triangle_viewspace_c[k1]] == anInt488) {
                m1.triangle_info[k1] = -1;
            }
        }

        for (int l1 = 0; l1 < m2.triangle_count; l1++) {
            if (anIntArray487[m2.triangle_viewspace_a[l1]] == anInt488 && anIntArray487[m2.triangle_viewspace_b[l1]] == anInt488 && anIntArray487[m2.triangle_viewspace_c[l1]] == anInt488) {
                m2.triangle_info[l1] = -1;
            }
        }
    }

    public void method314(Tile tile, boolean flag) {
        tileQueue.pushBack(tile);

        do {
            Tile _t;

            do {
                _t = (Tile) tileQueue.pop();
                if (_t == null) {
                    return;
                }
            } while (!_t.aBoolean1323);

            int x = _t.x;
            int y = _t.y;
            int z = _t.z;
            int plane = _t.plane;
            Tile[][] tiles = this.tiles[z];

            if (_t.aBoolean1322) {
                if (flag) {
                    if (z > 0) {
                        Tile t = this.tiles[z - 1][x][y];
                        if (t != null && t.aBoolean1323) {
                            continue;
                        }
                    }

                    if (x <= camLocalX && x > minVisibleX) {
                        Tile t = tiles[x - 1][y];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (_t.flags & 1) == 0)) {
                            continue;
                        }
                    }

                    if (x >= camLocalX && x < maxVisibleX - 1) {
                        Tile t = tiles[x + 1][y];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (_t.flags & 4) == 0)) {
                            continue;
                        }
                    }

                    if (y <= camLocalY && y > minVisibleY) {
                        Tile t = tiles[x][y - 1];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (_t.flags & 8) == 0)) {
                            continue;
                        }
                    }

                    if (y >= camLocalY && y < maxVisibleY - 1) {
                        Tile t = tiles[x][y + 1];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (_t.flags & 2) == 0)) {
                            continue;
                        }
                    }
                } else {
                    flag = true;
                }

                _t.aBoolean1322 = false;

                if (_t.bridge != null) {
                    Tile t = _t.bridge;

                    if (t.underlay != null) {
                        if (!isTileCulled(0, x, y)) {
                            drawUnderlayTile(t.underlay, 0, camPSin, camPCos, camYSin, camYCos, x, y);
                        }
                    } else if (t.overlay != null && !isTileCulled(0, x, y)) {
                        drawOverlayTile(t.overlay, x, y, camPSin, camPCos, camYSin, camYCos);
                    }

                    WallLoc wl = t.wall;

                    if (wl != null) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }

                    for (int i = 0; i < t.locCount; i++) {
                        StaticLoc sl = t.locs[i];

                        if (sl != null) {
                            sl.node.render(sl.rotation, camPSin, camPCos, camYSin, camYCos, sl.x - camX, sl.y - camY, sl.z - camZ, sl.uid);
                        }
                    }
                }

                boolean draw_item_pile = false;

                if (_t.underlay != null) {
                    if (!isTileCulled(plane, x, y)) {
                        draw_item_pile = true;
                        drawUnderlayTile(_t.underlay, plane, camPSin, camPCos, camYSin, camYCos, x, y);
                    }
                } else if (_t.overlay != null && !isTileCulled(plane, x, y)) {
                    draw_item_pile = true;
                    drawOverlayTile(_t.overlay, x, y, camPSin, camPCos, camYSin, camYCos);
                }

                int cam_dir = 0;
                int render_flags = 0;
                WallLoc wl = _t.wall;
                WallDecoration wd = _t.wallDecoration;

                if (wl != null || wd != null) {
                    if (camLocalX == x) {
                        cam_dir++;
                    } else if (camLocalX < x) {
                        cam_dir += 2;
                    }

                    if (camLocalY == y) {
                        cam_dir += 3;
                    } else if (camLocalY > y) {
                        cam_dir += 6;
                    }

                    render_flags = anIntArray478[cam_dir];
                    _t.anInt1328 = anIntArray480[cam_dir];

                    // Camera Direction (Relative to)
                    // 0: cam_x > x && cam_y < y	(South-east)
                    // 1: cam_x == x && cam_y < y	(South)
                    // 2: cam_x < x  && cam_y < y	(South-west)
                    // 3: cam_y == y && cam_x > x	(East)
                    // 4: cam_x == x && cam_y == y	(Above)
                    // 5: cam_x < x && cam_y == y	(West)
                    // 6: cam_y > y && cam_x > x	(North-east)
                    // 7: cam_x == x && cam_y > y	(North)
                    // 8: cam_x < x && cam_y > y	(North-west)

                    // 0x13 = 0x10	| 0x2	| 0x1
                    // 0x37 = 0x20	| 0x10	| 0x4	| 0x2	| 0x1
                    // 0x26 = 0x20	| 0x4	| 0x2
                    // 0x9B = 0x80	| 0x10	| 0x8	| 0x2	| 0x1
                    // 0xFF = 0x80	| 0x40	| 0x20	| 0x10	| 0x8 | 0x4 | 0x2 | 0x1
                    // 0x6E = 0x40	| 0x20	| 0x8	| 0x4	| 0x2
                    // 0x89 = 0x80	| 0x8	| 0x1
                    // 0xCD = 0x80	| 0x40	| 0x8	| 0x4	| 0x1
                    // 0x4C	= 0x40	| 0x8	| 0x4
                    //
                    //anIntArray478[] = { 0x13, 0x37, 0x26, 0x9B, 0xFF, 0x6E, 0x89, 0xCD, 0x4C };

                    // 160	= 0x80 | 0x20
                    // 192	= 0x80 | 0x40
                    // 80	= 0x40 | 0x10
                    // 96	= 0x40 | 0x20
                    // 0	= 0x0
                    // 144	= 0x80 | 0x10
                    // 80	= 0x40 | 0x10
                    // 48	= 0x20 | 0x10
                    // 160	= 0x80 | 0x20
                    //
                    //anIntArray479[] = { 160, 192, 80, 96, 0, 144, 80, 48, 160 };

                    // 76	= 0x40 | 0x8 | 0x4	(Ext-N, Root-E, Root-N)
                    // 8	= 0x8				(Root-E)
                    // 137	= 0x80 | 0x8 | 0x1	(Ext-E, Root-E, Root-S)
                    // 4	= 0x4				(Root-N)
                    // 0	= 0x0				(None)
                    // 1	= 0x1				(Root-S)
                    // 38	= 0x20 | 0x4 | 0x2	(Root-W, Root-N, Root-W)
                    // 2	= 0x2				(Root-W)
                    // 19	= 0x10 | 0x2 | 0x1	(Ext-S, Root-W, Root-S)
                    //
                    //anIntArray480[] = { 76, 8, 137, 4, 0, 1, 38, 2, 19 };

                    // South, West, North, East
                    // WALL_CORNER_FLAG = { 0x10, 0x20, 0x40, 0x80 };
                    // WALL_ROTATION_FLAG = { 0x1, 0x2, 0x4, 0x8 };
                }

                if (wl != null) {
                    if ((wl.rotationFlag & anIntArray479[cam_dir]) != 0) {
                        if (wl.rotationFlag == 0x10) {
                            _t.anInt1325 = 0x3; // 0x2 | 0x1
                            _t.anInt1326 = anIntArray481[cam_dir];
                            _t.anInt1327 = 0x3 - _t.anInt1326;
                        } else if (wl.rotationFlag == 0x20) {
                            _t.anInt1325 = 0x6; // 0x4 | 0x2
                            _t.anInt1326 = anIntArray482[cam_dir];
                            _t.anInt1327 = 0x6 - _t.anInt1326;
                        } else if (wl.rotationFlag == 0x40) {
                            _t.anInt1325 = 0xC; // 0x8 | 0x4
                            _t.anInt1326 = anIntArray483[cam_dir];
                            _t.anInt1327 = 0xC - _t.anInt1326;
                        } else {
                            _t.anInt1325 = 0x9; // 0x8 | 0x1
                            _t.anInt1326 = anIntArray484[cam_dir];
                            _t.anInt1327 = 0x9 - _t.anInt1326;
                        }
                    } else {
                        _t.anInt1325 = 0;
                    }

                    if ((wl.rotationFlag & render_flags) != 0 && !isWallCulled(plane, x, y, wl.rotationFlag)) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }

                    if ((wl.cornerFlag & render_flags) != 0 && !isWallCulled(plane, x, y, wl.cornerFlag)) {
                        wl.extension.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }
                }

                if (wd != null && !isCulled(plane, x, y, wd.node.height)) {
                    if ((wd.flags & render_flags) != 0) {
                        wd.node.render(wd.rotation, camPSin, camPCos, camYSin, camYCos, wd.x - camX, wd.y - camY, wd.z - camZ, wd.uid);
                    } else if ((wd.flags & 0x300) != 0) {
                        int xd = wd.x - camX;
                        int zd = wd.z - camZ;
                        int yd = wd.y - camY;
                        int rotation = wd.rotation;

                        int k9;
                        if (rotation == 1 || rotation == 2) {
                            k9 = -xd;
                        } else {
                            k9 = xd;
                        }

                        int k10;
                        if (rotation == 2 || rotation == 3) {
                            k10 = -yd;
                        } else {
                            k10 = yd;
                        }

                        if ((wd.flags & 0x100) != 0 && k10 < k9) {
                            int i11 = xd + anIntArray463[rotation];
                            int k11 = yd + anIntArray464[rotation];
                            wd.node.render(rotation * 512 + 256, camPSin, camPCos, camYSin, camYCos, i11, k11, zd, wd.uid);
                        }

                        if ((wd.flags & 0x200) != 0 && k10 > k9) {
                            int j11 = xd + anIntArray465[rotation];
                            int l11 = yd + anIntArray466[rotation];
                            wd.node.render(rotation * 512 + 1280 & 0x7ff, camPSin, camPCos, camYSin, camYCos, j11, l11, zd, wd.uid);
                        }
                    }
                }

                if (draw_item_pile) {
                    GroundDecoration d = _t.groundDecoration;

                    if (d != null) {
                        d.node.render(0, camPSin, camPCos, camYSin, camYCos, d.sceneX - camX, d.sceneY - camY, d.sceneZ - camZ, d.uid);
                    }

                    ItemPile i = _t.itemPile;

                    if (i != null && i.offZ == 0) {
                        if (i.bottom != null) {
                            i.bottom.render(0, camPSin, camPCos, camYSin, camYCos, i.sceneX - camX, i.sceneY - camY, i.sceneZ - camZ, i.uid);
                        }
                        if (i.middle != null) {
                            i.middle.render(0, camPSin, camPCos, camYSin, camYCos, i.sceneX - camX, i.sceneY - camY, i.sceneZ - camZ, i.uid);
                        }
                        if (i.top != null) {
                            i.top.render(0, camPSin, camPCos, camYSin, camYCos, i.sceneX - camX, i.sceneY - camY, i.sceneZ - camZ, i.uid);
                        }
                    }
                }

                int t_flags = _t.flags;

                if (t_flags != 0) {
                    if (x < camLocalX && (t_flags & 4) != 0) {
                        Tile t1 = tiles[x + 1][y];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (y < camLocalY && (t_flags & 2) != 0) {
                        Tile t1 = tiles[x][y + 1];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (x > camLocalX && (t_flags & 1) != 0) {
                        Tile t1 = tiles[x - 1][y];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (y > camLocalY && (t_flags & 8) != 0) {
                        Tile t1 = tiles[x][y - 1];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }
                }
            }

            if (_t.anInt1325 != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < _t.locCount; k1++) {
                    if (_t.locs[k1].cycle == cycle || (_t.locFlag[k1] & _t.anInt1325) != _t.anInt1326) {
                        continue;
                    }
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    WallLoc wl = _t.wall;

                    if (!isWallCulled(plane, x, y, wl.rotationFlag)) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }

                    _t.anInt1325 = 0;
                }
            }

            if (_t.aBoolean1324) {
                try {
                    int i1 = _t.locCount;
                    _t.aBoolean1324 = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        StaticLoc l = _t.locs[k2];

                        if (l.cycle == cycle) {
                            continue;
                        }

                        for (int piece_x = l.localX0; piece_x <= l.localX1; piece_x++) {
                            for (int piece_y = l.localY0; piece_y <= l.localY1; piece_y++) {
                                Tile t = tiles[piece_x][piece_y];
                                if (t.aBoolean1322) {
                                    _t.aBoolean1324 = true;
                                } else {
                                    if (t.anInt1325 == 0) {
                                        continue;
                                    }
                                    int flags = 0;

                                    if (piece_x > l.localX0) {
                                        flags++;
                                    }

                                    if (piece_x < l.localX1) {
                                        flags += 0x4;
                                    }

                                    if (piece_y > l.localY0) {
                                        flags += 0x8;
                                    }

                                    if (piece_y < l.localY1) {
                                        flags += 0x2;
                                    }

                                    if ((flags & t.anInt1325) != _t.anInt1327) {
                                        continue;
                                    }

                                    _t.aBoolean1324 = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = l;

                        int i5 = camLocalX - l.localX0;
                        int i6 = l.localX1 - camLocalX;

                        if (i6 > i5) {
                            i5 = i6;
                        }

                        int i7 = camLocalY - l.localY0;
                        int j8 = l.localY1 - camLocalY;

                        if (j8 > i7) {
                            l.anInt527 = i5 + j8;
                        } else {
                            l.anInt527 = i5 + i7;
                        }
                    }

                    while (l1 > 0) {
                        int i3 = -50;
                        int l3 = -1;

                        for (int j5 = 0; j5 < l1; j5++) {
                            StaticLoc sl = aClass28Array462[j5];

                            if (sl.cycle != cycle) {
                                if (sl.anInt527 > i3) {
                                    i3 = sl.anInt527;
                                    l3 = j5;
                                } else if (sl.anInt527 == i3) {
                                    int x_cam_diff = sl.x - camX;
                                    int y_cam_diff = sl.y - camY;
                                    int l9 = aClass28Array462[l3].x - camX;
                                    int l10 = aClass28Array462[l3].y - camY;

                                    if ((x_cam_diff * x_cam_diff + y_cam_diff * y_cam_diff) > l9 * l9 + l10 * l10) {
                                        l3 = j5;
                                    }
                                }
                            }
                        }

                        if (l3 == -1) {
                            break;
                        }

                        StaticLoc sl = aClass28Array462[l3];
                        sl.cycle = cycle;

                        if (!method323(plane, sl.localX0, sl.localX1, sl.localY0, sl.localY1, sl.node.height)) {
                            sl.node.render(sl.rotation, camPSin, camPCos, camYSin, camYCos, sl.x - camX, sl.y - camY, sl.z - camZ, sl.uid);
                        }

                        for (int piece_x = sl.localX0; piece_x <= sl.localX1; piece_x++) {
                            for (int piece_y = sl.localY0; piece_y <= sl.localY1; piece_y++) {
                                Tile t = tiles[piece_x][piece_y];
                                if (t.anInt1325 != 0) {
                                    tileQueue.pushBack(t);
                                } else if ((piece_x != x || piece_y != y) && t.aBoolean1323) {
                                    tileQueue.pushBack(t);
                                }
                            }

                        }

                    }
                    if (_t.aBoolean1324) {
                        continue;
                    }
                } catch (Exception _ex) {
                    _t.aBoolean1324 = false;
                }
            }

            if (!_t.aBoolean1323 || _t.anInt1325 != 0) {
                continue;
            }

            if (x <= camLocalX && x > minVisibleX) {
                Tile t = tiles[x - 1][y];
                if (t != null && t.aBoolean1323) {
                    continue;
                }
            }

            if (x >= camLocalX && x < maxVisibleX - 1) {
                Tile t = tiles[x + 1][y];
                if (t != null && t.aBoolean1323) {
                    continue;
                }
            }

            if (y <= camLocalY && y > minVisibleY) {
                Tile t = tiles[x][y - 1];
                if (t != null && t.aBoolean1323) {
                    continue;
                }
            }

            if (y >= camLocalY && y < maxVisibleY - 1) {
                Tile t = tiles[x][y + 1];
                if (t != null && t.aBoolean1323) {
                    continue;
                }
            }

            _t.aBoolean1323 = false;
            anInt446--;

            ItemPile ip = _t.itemPile;

            if (ip != null && ip.offZ != 0) {
                if (ip.bottom != null) {
                    ip.bottom.render(0, camPSin, camPCos, camYSin, camYCos, ip.sceneX - camX, ip.sceneY - camY, ip.sceneZ - camZ - ip.offZ, ip.uid);
                }

                if (ip.middle != null) {
                    ip.middle.render(0, camPSin, camPCos, camYSin, camYCos, ip.sceneX - camX, ip.sceneY - camY, ip.sceneZ - camZ - ip.offZ, ip.uid);
                }

                if (ip.top != null) {
                    ip.top.render(0, camPSin, camPCos, camYSin, camYCos, ip.sceneX - camX, ip.sceneY - camY, ip.sceneZ - camZ - ip.offZ, ip.uid);
                }
            }

            if (_t.anInt1328 != 0) {
                WallDecoration wd = _t.wallDecoration;
                if (wd != null && !isCulled(plane, x, y, wd.node.height)) {
                    if ((wd.flags & _t.anInt1328) != 0) {
                        wd.node.render(wd.rotation, camPSin, camPCos, camYSin, camYCos, wd.x - camX, wd.y - camY, wd.z - camZ, wd.uid);
                    } else if ((wd.flags & 0x300) != 0) {
                        int x_d = wd.x - camX;
                        int z_d = wd.z - camZ;
                        int y_d = wd.y - camY;
                        int rotation = wd.rotation;

                        int j6;
                        if (rotation == 1 || rotation == 2) {
                            j6 = -x_d;
                        } else {
                            j6 = x_d;
                        }

                        int l7;
                        if (rotation == 2 || rotation == 3) {
                            l7 = -y_d;
                        } else {
                            l7 = y_d;
                        }

                        if ((wd.flags & 0x100) != 0 && l7 >= j6) {
                            int s_x = x_d + anIntArray463[rotation];
                            int s_y = y_d + anIntArray464[rotation];
                            wd.node.render(rotation * 512 + 256, camPSin, camPCos, camYSin, camYCos, s_x, s_y, z_d, wd.uid);
                        }

                        if ((wd.flags & 0x200) != 0 && l7 <= j6) {
                            int s_x = x_d + anIntArray465[rotation];
                            int s_y = y_d + anIntArray466[rotation];
                            wd.node.render(rotation * 512 + 1280 & 0x7ff, camPSin, camPCos, camYSin, camYCos, s_x, s_y, z_d, wd.uid);
                        }
                    }
                }

                WallLoc wl = _t.wall;

                if (wl != null) {
                    if ((wl.cornerFlag & _t.anInt1328) != 0 && !isWallCulled(plane, x, y, wl.cornerFlag)) {
                        wl.extension.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }
                    if ((wl.rotationFlag & _t.anInt1328) != 0 && !isWallCulled(plane, x, y, wl.rotationFlag)) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }
                }
            }

            if (z < sizeZ - 1) {
                Tile t = this.tiles[z + 1][x][y];
                if (t != null && t.aBoolean1323) {
                    tileQueue.pushBack(t);
                }
            }

            if (x < camLocalX) {
                Tile t = tiles[x + 1][y];
                if (t != null && t.aBoolean1323) {
                    tileQueue.pushBack(t);
                }
            }

            if (y < camLocalY) {
                Tile t = tiles[x][y + 1];
                if (t != null && t.aBoolean1323) {
                    tileQueue.pushBack(t);
                }
            }

            if (x > camLocalX) {
                Tile t = tiles[x - 1][y];
                if (t != null && t.aBoolean1323) {
                    tileQueue.pushBack(t);
                }
            }

            if (y > camLocalY) {
                Tile t = tiles[x][y - 1];
                if (t != null && t.aBoolean1323) {
                    tileQueue.pushBack(t);
                }
            }
        } while (true);
    }

    public boolean method323(int plane, int x1, int x2, int y1, int y2, int z_off) {
        if (x1 == x2 && y1 == y2) {
            if (!isTileCulled(plane, x1, y1)) {
                return false;
            }
            int x = x1 << 7;
            int y = y1 << 7;

            return isCulled(x + 1, y + 1, height_map[plane][x1][y1] - z_off) && isCulled((x + 128) - 1, y + 1, height_map[plane][x1 + 1][y1] - z_off) && isCulled((x + 128) - 1, (y + 128) - 1, height_map[plane][x1 + 1][y1 + 1] - z_off) && isCulled(x + 1, (y + 128) - 1, height_map[plane][x1][y1 + 1] - z_off);
        }

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if (tileCycle[plane][x][y] == -cycle) {
                    return false;
                }
            }
        }

        int x = (x1 << 7) + 1;
        int y = (y1 << 7) + 2;
        int z = height_map[plane][x1][y1] - z_off;

        if (!isCulled(x, y, z)) {
            return false;
        }

        int tile_end_x = (x2 << 7) - 1;

        if (!isCulled(tile_end_x, y, z)) {
            return false;
        }

        int tile_end_y = (y2 << 7) - 1;

        if (!isCulled(x, tile_end_y, z)) {
            return false;
        }

        return isCulled(tile_end_x, tile_end_y, z);
    }

    public void remove(StaticLoc sl) {
        for (int x = sl.localX0; x <= sl.localX1; x++) {
            for (int y = sl.localY0; y <= sl.localY1; y++) {
                Tile t = tiles[sl.plane][x][y];

                if (t != null) {
                    for (int i = 0; i < t.locCount; i++) {
                        if (t.locs[i] != sl) {
                            continue;
                        }

                        t.locCount--;

                        for (int j = i; j < t.locCount; j++) {
                            t.locs[j] = t.locs[j + 1];
                            t.locFlag[j] = t.locFlag[j + 1];
                        }

                        t.locs[t.locCount] = null;
                        break;
                    }

                    t.flags = 0;

                    for (int i = 0; i < t.locCount; i++) {
                        t.flags |= t.locFlag[i];
                    }
                }
            }
        }
    }

    public void removeGroundDecoration(int x, int y, int z) {
        Tile t = tiles[z][x][y];
        if (t == null) {
            return;
        }
        t.groundDecoration = null;
    }

    public void removeItemPile(int plane, int x, int y) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return;
        } else {
            t.itemPile = null;
            return;
        }
    }

    public void removeWall(int x, int z, int y) {
        Tile t = tiles[z][x][y];
        if (t == null) {
            return;
        }
        t.wall = null;
    }

    public void removeWallDecoration(int i, int y, int z, int x) {
        Tile t = tiles[z][x][y];
        if (t == null) {
            return;
        }
        t.wallDecoration = null;
    }

    public void reset() {
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    tiles[z][x][y] = null;
                }
            }
        }

        for (int x = 0; x < cullPlaneCount; x++) {
            for (int y = 0; y < cullingBoxCount[x]; y++) {
                cullingBoxes1[x][y] = null;
            }
            cullingBoxCount[x] = 0;
        }

        for (int i = 0; i < locCount; i++) {
            locs[i] = null;
        }

        locCount = 0;
        for (int i = 0; i < aClass28Array462.length; i++) {
            aClass28Array462[i] = null;
        }
    }

    public void setPlane(int plane) {
        this.plane = plane;

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (tiles[plane][x][y] == null) {
                    tiles[plane][x][y] = new Tile(plane, x, y);
                }
            }
        }
    }

    public void setTileUnderlayColor(int x, int y, int z, int hue, int saturation, int l_sw, int l_se, int l_ne, int l_nw) {
        UnderlayTile ut = this.tiles[z][x][y].underlay;

        if (ut == null) {
            return;
        }

        int hs = (hue << 10) | (saturation << 7);

        ut.hslSw = hs + l_sw;
        ut.hslNe = hs + l_ne;
        ut.hslNw = hs + l_nw;
        ut.hslSe = hs + l_se;
        ut.rgb = 0;
    }

    public void setVisiblePlanes(int plane, int x, int y, int max_plane) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return;
        }
        tiles[plane][x][y].topPlane = max_plane;
    }

    public void setWallDecoMargin(int x, int y, int z, int i) {
        Tile t = tiles[z][x][y];

        if (t == null) {
            return;
        }

        WallDecoration deco = t.wallDecoration;

        if (deco == null) {
            return;
        }

        int s_x = x * 128 + 64;
        int s_y = y * 128 + 64;
        deco.x = s_x + ((deco.x - s_x) * i) / 16;
        deco.y = s_y + ((deco.y - s_y) * i) / 16;
    }

    public boolean triContains(int x, int y, int x1, int y1, int x2, int y2, int x3, int y3) {
        if (y < y1 && y < y2 && y < y3) {
            return false;
        }
        if (y > y1 && y > y2 && y > y3) {
            return false;
        }
        if (x < x1 && x < x2 && x < x3) {
            return false;
        }
        if (x > x1 && x > x2 && x > x3) {
            return false;
        }
        int i2 = (y - y1) * (x2 - x1) - (x - x1) * (y2 - y1);
        int j2 = (y - y3) * (x1 - x3) - (x - x3) * (y1 - y3);
        int k2 = (y - y2) * (x3 - x2) - (x - x2) * (y3 - y2);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    public void updateCulling() {
        int box_count = cullingBoxCount[occlusionTopPlane];
        CullingBox culls[] = cullingBoxes1[occlusionTopPlane];
        cullingPosition = 0;

        for (int i = 0; i < box_count; i++) {
            CullingBox c = culls[i];

            if (c.occlusionType == 1) {
                int z_index = (c.localMinX - camLocalX) + 25;

                if (z_index < 0 || z_index > 50) {
                    continue;
                }

                int yaw_index = (c.localMinY - camLocalY) + 25;

                if (yaw_index < 0) {
                    yaw_index = 0;
                }

                int dy = (c.localMaxY - camLocalY) + 25;

                if (dy > 50) {
                    dy = 50;
                }

                boolean flag = false;

                while (yaw_index <= dy) {
                    if (cullingMap[z_index][yaw_index++]) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    continue;
                }

                int dx = camX - c.minX;

                if (dx > 32) {
                    c.cullingMode = 1;
                } else {
                    if (dx >= -32) {
                        continue;
                    }
                    c.cullingMode = 2;
                    dx = -dx;
                }

                c.anInt801 = (c.minY - camY << 8) / dx;
                c.anInt802 = (c.maxY - camY << 8) / dx;
                c.anInt803 = (c.maxZ - camZ << 8) / dx;
                c.anInt804 = (c.minZ - camZ << 8) / dx;
                cullingBoxes[cullingPosition++] = c;
                continue;
            }

            if (c.occlusionType == 2) {
                int yaw_index = (c.localMinY - camLocalY) + 25;

                if (yaw_index < 0 || yaw_index > 50) {
                    continue;
                }

                int z_index = (c.localMinX - camLocalX) + 25;

                if (z_index < 0) {
                    z_index = 0;
                }

                int dx = (c.localMaxX - camLocalX) + 25;

                if (dx > 50) {
                    dx = 50;
                }

                boolean flag1 = false;

                while (z_index <= dx) {
                    if (cullingMap[z_index++][yaw_index]) {
                        flag1 = true;
                        break;
                    }
                }

                if (!flag1) {
                    continue;
                }

                int dy = camY - c.minY;

                if (dy > 32) {
                    c.cullingMode = 3;
                } else {
                    if (dy >= -32) {
                        continue;
                    }
                    c.cullingMode = 4;
                    dy = -dy;
                }

                c.anInt799 = (c.minX - camX << 8) / dy;
                c.anInt800 = (c.maxX - camX << 8) / dy;
                c.anInt803 = (c.maxZ - camZ << 8) / dy;
                c.anInt804 = (c.minZ - camZ << 8) / dy;

                cullingBoxes[cullingPosition++] = c;
            } else if (c.occlusionType == 4) {
                int dz = c.maxZ - camZ;

                if (dz > 128) {
                    int start_yaw = (c.localMinY - camLocalY) + 25;

                    if (start_yaw < 0) {
                        start_yaw = 0;
                    }

                    int end_yaw = (c.localMaxY - camLocalY) + 25;

                    if (end_yaw > 50) {
                        end_yaw = 50;
                    }

                    if (start_yaw <= end_yaw) {
                        int start_z = (c.localMinX - camLocalX) + 25;

                        if (start_z < 0) {
                            start_z = 0;
                        }

                        int end_z = (c.localMaxX - camLocalX) + 25;

                        if (end_z > 50) {
                            end_z = 50;
                        }

                        boolean flag2 = false;

                        label0:
                        {
                            for (int z_index = start_z; z_index <= end_z; z_index++) {
                                for (int yaw_index = start_yaw; yaw_index <= end_yaw; yaw_index++) {

                                    if (!cullingMap[z_index][yaw_index]) {
                                        continue;
                                    }

                                    flag2 = true;
                                    break label0;
                                }
                            }
                        }

                        if (flag2) {
                            c.cullingMode = 5;
                            c.anInt799 = (c.minX - camX << 8) / dz;
                            c.anInt800 = (c.maxX - camX << 8) / dz;
                            c.anInt801 = (c.minY - camY << 8) / dz;
                            c.anInt802 = (c.maxY - camY << 8) / dz;
                            cullingBoxes[cullingPosition++] = c;
                        }
                    }
                }
            }
        }
    }

}
