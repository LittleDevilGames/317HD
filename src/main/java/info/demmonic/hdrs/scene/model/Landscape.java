package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.node.Chain;
import info.demmonic.hdrs.node.impl.Renderable;
import info.demmonic.hdrs.util.ColorUtils;
import info.demmonic.hdrs.util.MathUtils;

public class Landscape {

    public static final byte[] anIntArray463 = {53, -53, -53, 53};
    public static final byte[] anIntArray464 = {-53, -53, 53, 53};
    public static final byte[] anIntArray465 = {-45, 45, 45, -45};
    public static final byte[] anIntArray466 = {45, 45, -45, -45};
    public static final int[] anIntArray478 = {0x13, 0x37, 0x26, 0x9B, 0xFF, 0x6E, 0x89, 0xCD, 0x4C};
    public static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80, 48, 160};
    public static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
    public static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
    public static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
    public static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
    public static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
    public static final int[] TEXTURE_HSL = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
    public static StaticLoc[] aClass28Array462 = new StaticLoc[100];
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
    public static int[] cullingBoxCount;
    public static CullingBox[][] cullingBoxes1;
    public static int cullingPosition;
    public static int cycle;
    public static boolean inputRequested;
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
    public static boolean[][] cullingMap;
    public static boolean[][][][] visibilityMap = new boolean[8][32][51][51];

    static {
        cullPlaneCount = 4;
        cullingBoxCount = new int[cullPlaneCount];
        cullingBoxes1 = new CullingBox[cullPlaneCount][500];
    }

    public int anInt488;
    public int[] anIntArray486 = new int[10000];
    public int[] anIntArray487 = new int[10000];
    public short[][][] heightMap;
    public int locCount;
    public StaticLoc[] locs = new StaticLoc[5000];

    public int[][] MINIMAP_OVERLAY_MASK = {new int[16],
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
    public int[][] MINIMAP_OVERLAY_MASK_ROTATION = {
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

    public int[][][] tileCycle;
    public Tile[][][] tiles;

    public Landscape(int sizeX, int sizeY, int sizeZ, short heightMap[][][]) {
        this.sizeZ = sizeZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = new Tile[sizeZ][sizeX][sizeY];
        this.tileCycle = new int[sizeZ][sizeX + 1][sizeY + 1];
        this.heightMap = heightMap;
        this.reset();
    }

    public static void createCullingBox(int plane, int minX, int maxX, int minY, int maxY, int minVertexHeight, int maxVertexHeight, int type) {
        CullingBox box = new CullingBox();
        box.localMinX = minX / 128;
        box.localMaxX = maxX / 128;
        box.localMinY = minY / 128;
        box.localMaxY = maxY / 128;
        box.occlusionType = type;
        box.minX = minX;
        box.maxX = maxX;
        box.minY = minY;
        box.maxY = maxY;
        box.maxZ = maxVertexHeight;
        box.minZ = minVertexHeight;
        cullingBoxes1[plane][cullingBoxCount[plane]++] = box;
    }

    public static boolean isVisible(int sceneX, int sceneY, int sceneZ) {
        int x = sceneY * camYSin + sceneX * camYCos >> 16;
        int i1 = sceneY * camYCos - sceneX * camYSin >> 16;
        int z = sceneZ * camPSin + i1 * camPCos >> 16;
        int y = sceneZ * camPCos - i1 * camPSin >> 16;

        if (z < 50 || z > 3500) {
            return false;
        }

        int screenX = scrCX + (x << 9) / z;
        int screenY = scrCY + (y << 9) / z;
        return screenX >= scrX && screenX <= scrW && screenY >= scrY && screenY <= scrH;
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

        boolean visibilityMap[][][][] = new boolean[9][32][53][53];

        for (int pitch = Camera.MIN_PITCH; pitch <= Camera.MAX_PITCH + 1; pitch += 32) {
            for (int yaw = 0; yaw < 2048; yaw += 64) {
                camPSin = MathUtils.sin[pitch];
                camPCos = MathUtils.cos[pitch];
                camYSin = MathUtils.sin[yaw];
                camYCos = MathUtils.cos[yaw];
                int zIndex = (pitch - 128) / 32;
                int yawIndex = yaw / 64;

                for (int x = -26; x <= 26; x++) {
                    for (int y = -26; y <= 26; y++) {
                        int sceneX = x * 128;
                        int sceneY = y * 128;
                        boolean isVisible = false;

                        for (int z = -nearZ; z <= farZ; z += 128) {
                            if (!isVisible(sceneX, sceneY, zArray[zIndex] + z)) {
                                continue;
                            }
                            isVisible = true;
                            break;
                        }

                        visibilityMap[zIndex][yawIndex][x + 25 + 1][y + 25 + 1] = isVisible;
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
                                if (visibilityMap[pitch][yaw][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else if (visibilityMap[pitch][(yaw + 1) % 31][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else if (visibilityMap[pitch + 1][yaw][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
                                    visible = true;
                                } else {
                                    if (!visibilityMap[pitch + 1][(yaw + 1) % 31][x + l3 + 25 + 1][y + j4 + 25 + 1]) {
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

    public boolean add(Renderable r, int lX, int lY, int lZ, int sizeX, int sizeY, int vHeight, byte arrangement, int angle, int uid) {
        if (r == null) {
            return true;
        } else {
            int sceneX = lX * 128 + 64 * sizeY;
            int sceneY = lY * 128 + 64 * sizeX;
            return add(r, sceneX, sceneY, vHeight, lZ, lX, lY, sizeY, sizeX, uid, arrangement, angle, false);
        }
    }

    public boolean add(Renderable node, int x, int y, int z, int plane, int localX0, int localY0, int localX1, int localY1, int uid, byte arrangement, int angle, boolean append) {
        for (int x0 = localX0; x0 < localX0 + localX1; x0++) {
            for (int y0 = localY0; y0 < localY0 + localY1; y0++) {
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
        sl.localX0 = localX0;
        sl.localY0 = localY0;
        sl.localX1 = (localX0 + localX1) - 1;
        sl.localY1 = (localY0 + localY1) - 1;

        for (int x0 = localX0; x0 < localX0 + localX1; x0++) {
            for (int y0 = localY0; y0 < localY0 + localY1; y0++) {
                int flag = 0;

                if (x0 > localX0) {
                    flag++;
                }

                if (x0 < (localX0 + localX1) - 1) {
                    flag += 4;
                }

                if (y0 > localY0) {
                    flag += 8;
                }

                if (y0 < (localY0 + localY1) - 1) {
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
        Tile bottom = tiles[0][x][y];

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

        tiles[0][x][y].bridge = bottom;
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

    public void addItemPile(int x, int y, int z, int plane, Renderable rTop, Renderable rMid, Renderable rBtm, int uid) {
        ItemPile ip = new ItemPile();
        ip.sceneX = x * 128 + 64;
        ip.sceneY = y * 128 + 64;
        ip.sceneZ = z;
        ip.uid = uid;
        ip.top = rTop;
        ip.middle = rMid;
        ip.bottom = rBtm;

        int minZ = 0;

        Tile t = tiles[plane][x][y];

        if (t != null) {
            for (int i = 0; i < t.locCount; i++) {
                if (t.locs[i].node instanceof Model) {
                    int offset = ((Model) t.locs[i].node).pileHeight;

                    if (offset > minZ) {
                        minZ = offset;
                    }
                }
            }
        }

        ip.offZ = minZ;

        if (tiles[plane][x][y] == null) {
            tiles[plane][x][y] = new Tile(plane, x, y);
        }

        tiles[plane][x][y].itemPile = ip;
    }

    public void addTile(int plane, int x, int y, int shape, int rotation, byte textureIndex, short vSw, short vSe, short vNe, short vNw, int hslSw, int hslSe, int hslNe, int hslNw, int rgbSw, int rgbSe, int rgbNe, int rgbNw, int minimapRgb, int hslBitset) {
        if (shape == 0) {
            UnderlayTile t = new UnderlayTile(hslSw, hslSe, hslNe, hslNw, (byte) -1, minimapRgb, false);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].underlay = t;
        } else if (shape == 1) {
            UnderlayTile t = new UnderlayTile(rgbSw, rgbSe, rgbNe, rgbNw, textureIndex, hslBitset, vSw == vSe && vSw == vNe && vSw == vNw);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].underlay = t;
        } else {
            OverlayTile t = new OverlayTile(x, y, vSw, vSe, vNe, vNw, rgbSw, rgbSe, rgbNe, rgbNw, minimapRgb, hslSw, hslSe, hslNe, hslNw, hslBitset, textureIndex, rotation, shape);

            for (int z = plane; z >= 0; z--) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }

            tiles[plane][x][y].overlay = t;
        }
    }

    public void addWall(Renderable r1, Renderable r2, int x, int y, int z, int plane, int rotationFlag, int cornerFlag, byte arrangement, int uid) {
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
        wl.rotationFlag = rotationFlag;
        wl.cornerFlag = cornerFlag;

        for (int currentPlane = plane; currentPlane >= 0; currentPlane--) {
            if (tiles[currentPlane][x][y] == null) {
                tiles[currentPlane][x][y] = new Tile(currentPlane, x, y);
            }
        }

        tiles[plane][x][y].wall = wl;
    }

    public void addWallDecoration(Renderable r, int x, int y, int plane, int offsetX, int offsetY, int offsetZ, int rotation, byte arrangement, int flags, int uid) {
        if (r == null) {
            return;
        }

        WallDecoration wd = new WallDecoration();
        wd.uid = uid;
        wd.arrangement = arrangement;
        wd.x = x * 128 + 64 + offsetX;
        wd.y = y * 128 + 64 + offsetY;
        wd.z = offsetZ;
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

    public void applyUntexturedObjects(int lightX, int lightY, int lightZ, int lightBrightness, int specularFactor) {
        int lightLen = (int) Math.sqrt(lightX * lightX + lightY * lightY + lightZ * lightZ);
        int specularDistribution = specularFactor * lightLen >> 8;

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
                                ((Model) wl.extension).applyLighting(lightBrightness, specularDistribution, lightX, lightY, lightZ);
                            }
                            ((Model) wl.root).applyLighting(lightBrightness, specularDistribution, lightX, lightY, lightZ);
                        }

                        for (int k2 = 0; k2 < t.locCount; k2++) {
                            StaticLoc sl = t.locs[k2];
                            if (sl != null && sl.node != null && sl.node.normal != null) {
                                method307((Model) sl.node, x, y, plane, (sl.localX1 - sl.localX0) + 1, (sl.localY1 - sl.localY0) + 1);
                                ((Model) sl.node).applyLighting(lightBrightness, specularDistribution, lightX, lightY, lightZ);
                            }
                        }

                        GroundDecoration gd = t.groundDecoration;
                        if (gd != null && gd.node.normal != null) {
                            method306((Model) gd.node, x, y, plane);
                            ((Model) gd.node).applyLighting(lightBrightness, specularDistribution, lightX, lightY, lightZ);
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

    public void clicked(int clickY, int clickX) {
        inputRequested = true;
        Landscape.clickX = clickX;
        Landscape.clickY = clickY;
        clickLocalX = -1;
        clickLocalY = -1;
    }

    public void draw(int camX, int camY, int camYaw, int camZ, int occlusionTopPlane, int camPitch) {
        if (camX < 0) {
            camX = 0;
        } else if (camX >= sizeX * 128) {
            camX = sizeX * 128 - 1;
        }

        if (camY < 0) {
            camY = 0;
        } else if (camY >= sizeY * 128) {
            camY = sizeY * 128 - 1;
        }

        Landscape.cycle++;
        Landscape.camPSin = MathUtils.sin[camPitch];
        Landscape.camPCos = MathUtils.cos[camPitch];
        Landscape.camYSin = MathUtils.sin[camYaw];
        Landscape.camYCos = MathUtils.cos[camYaw];
        Landscape.cullingMap = visibilityMap[(camPitch - 128) / 32][camYaw / 64];
        Landscape.camX = camX;
        Landscape.camZ = camZ;
        Landscape.camY = camY;
        Landscape.camLocalX = camX / 128;
        Landscape.camLocalY = camY / 128;
        Landscape.occlusionTopPlane = occlusionTopPlane;

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
                        if (t.topPlane > occlusionTopPlane || !cullingMap[(x - camLocalX) + 25][(y - camLocalY) + 25] && heightMap[z][x][y] - camZ < 2000) {
                            t.aBoolean1322 = false;
                            t.aBoolean1323 = false;
                            t.anInt1325 = 0;
                        } else {
                            t.aBoolean1322 = true;
                            t.aBoolean1323 = true;
                            t.aBoolean1324 = t.locCount > 0;
                            anInt446++;
                        }
                    }
                }

            }

        }

        for (int z = plane; z < sizeZ; z++) {
            Tile planeTiles[][] = tiles[z];

            for (int xOff = -25; xOff <= 0; xOff++) {
                int x0 = camLocalX + xOff;
                int x1 = camLocalX - xOff;

                if (x0 >= minVisibleX || x1 < maxVisibleX) {
                    for (int yOff = -25; yOff <= 0; yOff++) {
                        int y0 = camLocalY + yOff;
                        int y1 = camLocalY - yOff;

                        if (x0 >= minVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = planeTiles[x0][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = planeTiles[x0][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                        }

                        if (x1 < maxVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = planeTiles[x1][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = planeTiles[x1][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, true);
                                }
                            }
                        }

                        if (anInt446 == 0) {
                            inputRequested = false;
                            return;
                        }
                    }
                }
            }
        }

        for (int z = plane; z < sizeZ; z++) {
            Tile planeTiles[][] = tiles[z];
            for (int xOff = -25; xOff <= 0; xOff++) {
                int x0 = camLocalX + xOff;
                int x1 = camLocalX - xOff;
                if (x0 >= minVisibleX || x1 < maxVisibleX) {
                    for (int yOff = -25; yOff <= 0; yOff++) {
                        int y0 = camLocalY + yOff;
                        int y1 = camLocalY - yOff;
                        if (x0 >= minVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = planeTiles[x0][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = planeTiles[x0][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                        }
                        if (x1 < maxVisibleX) {
                            if (y0 >= minVisibleY) {
                                Tile t = planeTiles[x1][y0];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                            if (y1 < maxVisibleY) {
                                Tile t = planeTiles[x1][y1];
                                if (t != null && t.aBoolean1322) {
                                    method314(t, false);
                                }
                            }
                        }
                        if (anInt446 == 0) {
                            inputRequested = false;
                            return;
                        }
                    }

                }
            }

        }

        inputRequested = false;
    }

    public void drawMinimapTile(int pixels[], int start, int width, int plane, int x, int y) {
        Tile t = tiles[plane][x][y];

        if (t == null) {
            return;
        }

        UnderlayTile ut = t.underlay;

        if (ut != null) {
            int rgb = ut.minimapRgb;

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

    public void drawOverlayTile(OverlayTile ot, int localX, int localY, int pitchSin, int pitchCos, int yawSin, int yawCos) {
        int i = ot.triangleX.length;

        for (int j = 0; j < i; j++) {
            int x = ot.triangleX[j] - camX;
            int y = ot.triangleY[j] - camZ;
            int z = ot.triangleZ[j] - camY;

            int w = z * yawSin + x * yawCos >> 16;
            z = z * yawCos - x * yawSin >> 16;
            x = w;

            w = y * pitchCos - z * pitchSin >> 16;
            z = y * pitchSin + z * pitchCos >> 16;
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
            int vXI = ot.vertexX[j];
            int vYI = ot.vertexY[j];
            int vZI = ot.vertexZ[j];
            int x1 = OverlayTile.tmpScreenX[vXI];
            int x2 = OverlayTile.tmpScreenX[vYI];
            int x3 = OverlayTile.tmpScreenX[vZI];
            int y1 = OverlayTile.tmpScreenY[vXI];
            int y2 = OverlayTile.tmpScreenY[vYI];
            int y3 = OverlayTile.tmpScreenY[vZI];

            if ((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2) > 0) {

                Canvas3D.checkBounds = x1 < 0 || x2 < 0 || x3 < 0 || x1 > Canvas2D.bound || x2 > Canvas2D.bound || x3 > Canvas2D.bound;

                // Used for clicking on the map.
                if (inputRequested && triContains(clickX, clickY, x1, y1, x2, y2, x3, y3)) {
                    clickLocalX = localX;
                    clickLocalY = localY;
                }

                if (ot.triangleTextureIndex == null || ot.triangleTextureIndex[j] == -1) {
                    if (ot.vertexColorA[j] != 12345678) {
                        Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j]);
                    }
                } else if (!Game.lowDetail) {
                    if (ot.ignoreUv) {
                        Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j], OverlayTile.tmpTriangleX[0], OverlayTile.tmpTriangleY[0], OverlayTile.tmpTriangleZ[0], OverlayTile.tmpTriangleX[1], OverlayTile.tmpTriangleY[1], OverlayTile.tmpTriangleZ[1], OverlayTile.tmpTriangleX[3], OverlayTile.tmpTriangleY[3], OverlayTile.tmpTriangleZ[3], ot.triangleTextureIndex[j]);
                    } else {
                        Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x3, y3, ot.vertexColorA[j], ot.vertexColorB[j], ot.vertexColorC[j], OverlayTile.tmpTriangleX[vXI], OverlayTile.tmpTriangleY[vXI], OverlayTile.tmpTriangleZ[vXI], OverlayTile.tmpTriangleX[vYI], OverlayTile.tmpTriangleY[vYI], OverlayTile.tmpTriangleZ[vYI], OverlayTile.tmpTriangleX[vZI], OverlayTile.tmpTriangleY[vZI], OverlayTile.tmpTriangleZ[vZI], ot.triangleTextureIndex[j]);
                    }
                } else {
                    int hsl = TEXTURE_HSL[ot.triangleTextureIndex[j]];
                    Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x3, y3, ColorUtils.adjustHslLightness(hsl, ot.vertexColorA[j]), ColorUtils.adjustHslLightness(hsl, ot.vertexColorB[j]), ColorUtils.adjustHslLightness(hsl, ot.vertexColorC[j]));
                }
            }
        }
    }

    public void drawUnderlayTile(UnderlayTile ut, int plane, int camPitchSin, int camPitchCos, int camYawSin, int camYawCos, int x, int y) {
        int northWestX;
        int southWestX = northWestX = (x << 7) - camX;
        int southEastZ;
        int southWestZ = southEastZ = (y << 7) - camY;
        int northEastX;
        int southEastX = northEastX = southWestX + 128;
        int northWestZ;
        int northEastZ = northWestZ = southWestZ + 128;

        int southWestDelta = heightMap[plane][x][y] - camZ;
        int southEastDelta = heightMap[plane][x + 1][y] - camZ;
        int northEastDelta = heightMap[plane][x + 1][y + 1] - camZ;
        int northWestDelta = heightMap[plane][x][y + 1] - camZ;

        int i = southWestZ * camYawSin + southWestX * camYawCos >> 16;
        southWestZ = southWestZ * camYawCos - southWestX * camYawSin >> 16;
        southWestX = i;

        i = southWestDelta * camPitchCos - southWestZ * camPitchSin >> 16;
        southWestZ = southWestDelta * camPitchSin + southWestZ * camPitchCos >> 16;
        southWestDelta = i;

        if (southWestZ < 50) {
            return;
        }

        i = southEastZ * camYawSin + southEastX * camYawCos >> 16;
        southEastZ = southEastZ * camYawCos - southEastX * camYawSin >> 16;
        southEastX = i;

        i = southEastDelta * camPitchCos - southEastZ * camPitchSin >> 16;
        southEastZ = southEastDelta * camPitchSin + southEastZ * camPitchCos >> 16;
        southEastDelta = i;

        if (southEastZ < 50) {
            return;
        }

        i = northEastZ * camYawSin + northEastX * camYawCos >> 16;
        northEastZ = northEastZ * camYawCos - northEastX * camYawSin >> 16;
        northEastX = i;

        i = northEastDelta * camPitchCos - northEastZ * camPitchSin >> 16;
        northEastZ = northEastDelta * camPitchSin + northEastZ * camPitchCos >> 16;
        northEastDelta = i;

        if (northEastZ < 50) {
            return;
        }

        i = northWestZ * camYawSin + northWestX * camYawCos >> 16;
        northWestZ = northWestZ * camYawCos - northWestX * camYawSin >> 16;
        northWestX = i;

        i = northWestDelta * camPitchCos - northWestZ * camPitchSin >> 16;
        northWestZ = northWestDelta * camPitchSin + northWestZ * camPitchCos >> 16;
        northWestDelta = i;

        if (northWestZ < 50) {
            return;
        }

        int x1 = Canvas3D.centerX + (southWestX << 9) / southWestZ;
        int y1 = Canvas3D.centerY + (southWestDelta << 9) / southWestZ;
        int x2 = Canvas3D.centerX + (southEastX << 9) / southEastZ;
        int y2 = Canvas3D.centerY + (southEastDelta << 9) / southEastZ;
        int x3 = Canvas3D.centerX + (northEastX << 9) / northEastZ;
        int y3 = Canvas3D.centerY + (northEastDelta << 9) / northEastZ;
        int x4 = Canvas3D.centerX + (northWestX << 9) / northWestZ;
        int y4 = Canvas3D.centerY + (northWestDelta << 9) / northWestZ;

        Canvas3D.opacity = 0;
        if ((x3 - x4) * (y2 - y4) - (y3 - y4) * (x2 - x4) > 0) {

            Canvas3D.checkBounds = x3 < 0 || x4 < 0 || x2 < 0 || x3 > Canvas2D.bound || x4 > Canvas2D.bound || x2 > Canvas2D.bound;

            if (inputRequested && triContains(clickX, clickY, x3, y3, x4, y4, x2, y2)) {
                clickLocalX = x;
                clickLocalY = y;
            }

            if (ut.textureIndex == -1) {
                if (ut.hslNe != 12345678) {
                    Canvas3D.drawShadedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe);
                }
            } else if (!Game.lowDetail) {
                if (ut.isFlat) {
                    Canvas3D.drawTexturedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe, southWestX, southWestDelta, southWestZ, southEastX, southEastDelta, southEastZ, northWestX, northWestDelta, northWestZ, ut.textureIndex);
                } else {
                    Canvas3D.drawTexturedTriangle(x3, y3, x4, y4, x2, y2, ut.hslNe, ut.hslNw, ut.hslSe, northEastX, northEastDelta, northEastZ, northWestX, northWestDelta, northWestZ, southEastX, southEastDelta, southEastZ, ut.textureIndex);
                }
            } else {
                int i7 = TEXTURE_HSL[ut.textureIndex];
                Canvas3D.drawShadedTriangle(x3, y3, x4, y4, x2, y2, ColorUtils.adjustHslLightness(i7, ut.hslNe), ColorUtils.adjustHslLightness(i7, ut.hslNw), ColorUtils.adjustHslLightness(i7, ut.hslSe));
            }
        }

        if ((x1 - x2) * (y4 - y2) - (y1 - y2) * (x4 - x2) > 0) {

            Canvas3D.checkBounds = x1 < 0 || x2 < 0 || x4 < 0 || x1 > Canvas2D.bound || x2 > Canvas2D.bound || x4 > Canvas2D.bound;

            if (inputRequested && triContains(clickX, clickY, x1, y1, x2, y2, x4, y4)) {
                clickLocalX = x;
                clickLocalY = y;
            }

            if (ut.textureIndex == -1) {
                if (ut.hslSw != 12345678) {
                    Canvas3D.drawShadedTriangle(x1, y1, x2, y2, x4, y4, ut.hslSw, ut.hslSe, ut.hslNw);
                }
            } else {
                if (!Game.lowDetail) {
                    Canvas3D.drawTexturedTriangle(x1, y1, x2, y2, x4, y4, ut.hslSw, ut.hslSe, ut.hslNw, southWestX, southWestDelta, southWestZ, southEastX, southEastDelta, southEastZ, northWestX, northWestDelta, northWestZ, ut.textureIndex);
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
                    int minY = box.minY + (box.anInt801 * dx >> 8);
                    int maxY = box.maxY + (box.anInt802 * dx >> 8);
                    int minZ = box.maxZ + (box.anInt803 * dx >> 8);
                    int maxZ = box.minZ + (box.anInt804 * dx >> 8);

                    if (y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 2) {
                int dx = x - box.minX;

                if (dx > 0) {
                    int minY = box.minY + (box.anInt801 * dx >> 8);
                    int maxY = box.maxY + (box.anInt802 * dx >> 8);
                    int minZ = box.maxZ + (box.anInt803 * dx >> 8);
                    int maxZ = box.minZ + (box.anInt804 * dx >> 8);

                    if (y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 3) {
                int dy = box.minY - y;

                if (dy > 0) {
                    int minX = box.minX + (box.anInt799 * dy >> 8);
                    int maxX = box.maxX + (box.anInt800 * dy >> 8);
                    int minZ = box.maxZ + (box.anInt803 * dy >> 8);
                    int maxZ = box.minZ + (box.anInt804 * dy >> 8);

                    if (x >= minX && x <= maxX && z >= minZ && z <= maxZ) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 4) {
                int dy = y - box.minY;

                if (dy > 0) {
                    int minX = box.minX + (box.anInt799 * dy >> 8);
                    int maxX = box.maxX + (box.anInt800 * dy >> 8);
                    int minZ = box.maxZ + (box.anInt803 * dy >> 8);
                    int maxZ = box.minZ + (box.anInt804 * dy >> 8);

                    if (x >= minX && x <= maxX && z >= minZ && z <= maxZ) {
                        return true;
                    }
                }
            } else if (box.cullingMode == 5) {
                int dz = z - box.maxZ;

                if (dz > 0) {
                    int minX = box.minX + (box.anInt799 * dz >> 8);
                    int maxX = box.maxX + (box.anInt800 * dz >> 8);
                    int minY = box.minY + (box.anInt801 * dz >> 8);
                    int maxY = box.maxY + (box.anInt802 * dz >> 8);

                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isCulled(int plane, int localX, int localY, int height) {
        if (!isTileCulled(plane, localX, localY)) {
            return false;
        }

        int tileX = localX << 7;
        int tileY = localY << 7;

        return isCulled(tileX + 1, tileY + 1, heightMap[plane][localX][localY] - height) && isCulled((tileX + 128) - 1, tileY + 1, heightMap[plane][localX + 1][localY] - height) && isCulled((tileX + 128) - 1, (tileY + 128) - 1, heightMap[plane][localX + 1][localY + 1] - height) && isCulled(tileX + 1, (tileY + 128) - 1, heightMap[plane][localX][localY + 1] - height);
    }

    public boolean isTileCulled(int plane, int x, int y) {
        int i = tileCycle[plane][x][y];

        if (i == -cycle) {
            return false;
        }

        if (i == cycle) {
            return true;
        }

        int sceneX = x << 7;
        int sceneY = y << 7;

        if (isCulled(sceneX + 1, sceneY + 1, heightMap[plane][x][y]) && isCulled((sceneX + 128) - 1, sceneY + 1, heightMap[plane][x + 1][y]) && isCulled((sceneX + 128) - 1, (sceneY + 128) - 1, heightMap[plane][x + 1][y + 1]) && isCulled(sceneX + 1, (sceneY + 128) - 1, heightMap[plane][x][y + 1])) {
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

        int sceneX = x << 7;
        int sceneY = y << 7;
        int sceneZ = heightMap[z][x][y] - 1;
        int lv1 = sceneZ - 120;
        int lv2 = sceneZ - 230;
        int lv3 = sceneZ - 238;

        if (type < 0x10) {
            if (type == 0x1) {
                if (sceneX > camX) {
                    if (!isCulled(sceneX, sceneY, sceneZ)) {
                        return false;
                    }
                    if (!isCulled(sceneX, sceneY + 128, sceneZ)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(sceneX, sceneY, lv1)) {
                        return false;
                    }
                    if (!isCulled(sceneX, sceneY + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(sceneX, sceneY, lv2)) {
                    return false;
                }
                return isCulled(sceneX, sceneY + 128, lv2);
            }
            if (type == 0x2) {
                if (sceneY < camY) {
                    if (!isCulled(sceneX, sceneY + 128, sceneZ)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY + 128, sceneZ)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(sceneX, sceneY + 128, lv1)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(sceneX, sceneY + 128, lv2)) {
                    return false;
                }
                return isCulled(sceneX + 128, sceneY + 128, lv2);
            }
            if (type == 0x4) {
                if (sceneX < camX) {
                    if (!isCulled(sceneX + 128, sceneY, sceneZ)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY + 128, sceneZ)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(sceneX + 128, sceneY, lv1)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY + 128, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(sceneX + 128, sceneY, lv2)) {
                    return false;
                }
                return isCulled(sceneX + 128, sceneY + 128, lv2);
            }
            if (type == 0x8) {
                if (sceneY > camY) {
                    if (!isCulled(sceneX, sceneY, sceneZ)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY, sceneZ)) {
                        return false;
                    }
                }
                if (z > 0) {
                    if (!isCulled(sceneX, sceneY, lv1)) {
                        return false;
                    }
                    if (!isCulled(sceneX + 128, sceneY, lv1)) {
                        return false;
                    }
                }
                if (!isCulled(sceneX, sceneY, lv2)) {
                    return false;
                }
                return isCulled(sceneX + 128, sceneY, lv2);
            }
        }

        if (!isCulled(sceneX + 64, sceneY + 64, lv3)) {
            return false;
        }

        if (type == 0x10) {
            return isCulled(sceneX, sceneY + 128, lv2);
        } else if (type == 0x20) {
            return isCulled(sceneX + 128, sceneY + 128, lv2);
        } else if (type == 0x40) {
            return isCulled(sceneX + 128, sceneY, lv2);
        } else if (type == 0x80) {
            return isCulled(sceneX, sceneY, lv2);
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

    public void method307(Model m, int lX, int lY, int maxPlane, int j, int k) {
        boolean flag = true;
        int startX = lX;
        int endX = lX + j;
        int startY = lY - 1;
        int endY = lY + k;

        for (int z = maxPlane; z <= maxPlane + 1; z++) {
            if (z != this.sizeZ) {
                for (int x = startX; x <= endX; x++) {
                    if (x >= 0 && x < sizeX) {
                        for (int y = startY; y <= endY; y++) {
                            if (y >= 0 && y < sizeY && (!flag || x >= endX || y >= endY || y < lY && x != lX)) {
                                Tile t = tiles[z][x][y];

                                if (t != null) {
                                    int vAvg = (heightMap[z][x][y] + heightMap[z][x + 1][y] + heightMap[z][x][y + 1] + heightMap[z][x + 1][y + 1]) / 4 - (heightMap[maxPlane][lX][lY] + heightMap[maxPlane][lX + 1][lY] + heightMap[maxPlane][lX][lY + 1] + heightMap[maxPlane][lX + 1][lY + 1]) / 4;
                                    WallLoc wl = t.wall;

                                    if (wl != null && wl.root != null && wl.root.normal != null) {
                                        method308(m, (Model) wl.root, (x - lX) * 128 + (1 - j) * 64, vAvg, (y - lY) * 128 + (1 - k) * 64, flag);
                                    }

                                    if (wl != null && wl.extension != null && wl.extension.normal != null) {
                                        method308(m, (Model) wl.extension, (x - lX) * 128 + (1 - j) * 64, vAvg, (y - lY) * 128 + (1 - k) * 64, flag);
                                    }

                                    for (int i = 0; i < t.locCount; i++) {
                                        StaticLoc sl = t.locs[i];
                                        if (sl != null && sl.node != null && sl.node.normal != null) {
                                            int w = (sl.localX1 - sl.localX0) + 1;
                                            int l = (sl.localY1 - sl.localY0) + 1;
                                            method308(m, (Model) sl.node, (sl.localX0 - lX) * 128 + (w - j) * 64, vAvg, (sl.localY0 - lY) * 128 + (l - k) * 64, flag);
                                        }
                                    }

                                }
                            }
                        }

                    }
                }

                startX--;
                flag = false;
            }
        }

    }

    public void method308(Model m1, Model m2, int xOffset, int yOffset, int zOffset, boolean flag) {
        anInt488++;

        int i = 0;
        short vX[] = m2.vertexX;
        int vC = m2.vertexCount;

        // Loop through every vertex
        for (int j = 0; j < m1.vertexCount; j++) {
            Vertex v1 = m1.normal[j];
            Vertex v2 = m1.vertices[j];

            if (v2.w != 0) {

                int y = m1.vertexY[j] - yOffset;
                if (y <= m2.maxY) {

                    int x = m1.vertexX[j] - xOffset;
                    if (x >= m2.minX && x <= m2.maxX) {

                        int z = m1.vertexZ[j] - zOffset;
                        if (z >= m2.minZ && z <= m2.maxZ) {

                            for (int k = 0; k < vC; k++) {
                                Vertex v3 = m2.normal[k];
                                Vertex v4 = m2.vertices[k];

                                if (x == vX[k] && z == m2.vertexZ[k] && y == m2.vertexY[k] && v4.w != 0) {
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

        for (int k1 = 0; k1 < m1.triangleCount; k1++) {
            if (anIntArray486[m1.triangleViewspaceA[k1]] == anInt488 && anIntArray486[m1.triangleViewspaceB[k1]] == anInt488 && anIntArray486[m1.triangleViewspaceC[k1]] == anInt488) {
                m1.triangleInfo[k1] = -1;
            }
        }

        for (int l1 = 0; l1 < m2.triangleCount; l1++) {
            if (anIntArray487[m2.triangleViewspaceA[l1]] == anInt488 && anIntArray487[m2.triangleViewspaceB[l1]] == anInt488 && anIntArray487[m2.triangleViewspaceC[l1]] == anInt488) {
                m2.triangleInfo[l1] = -1;
            }
        }
    }

    public void method314(Tile tile, boolean flag) {
        tileQueue.pushBack(tile);

        do {
            Tile current;

            do {
                current = (Tile) tileQueue.pop();
                if (current == null) {
                    return;
                }
            } while (!current.aBoolean1323);

            int x = current.x;
            int y = current.y;
            int z = current.z;
            int plane = current.plane;
            Tile[][] tiles = this.tiles[z];

            if (current.aBoolean1322) {
                if (flag) {
                    if (z > 0) {
                        Tile t = this.tiles[z - 1][x][y];
                        if (t != null && t.aBoolean1323) {
                            continue;
                        }
                    }

                    if (x <= camLocalX && x > minVisibleX) {
                        Tile t = tiles[x - 1][y];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (current.flags & 1) == 0)) {
                            continue;
                        }
                    }

                    if (x >= camLocalX && x < maxVisibleX - 1) {
                        Tile t = tiles[x + 1][y];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (current.flags & 4) == 0)) {
                            continue;
                        }
                    }

                    if (y <= camLocalY && y > minVisibleY) {
                        Tile t = tiles[x][y - 1];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (current.flags & 8) == 0)) {
                            continue;
                        }
                    }

                    if (y >= camLocalY && y < maxVisibleY - 1) {
                        Tile t = tiles[x][y + 1];
                        if (t != null && t.aBoolean1323 && (t.aBoolean1322 || (current.flags & 2) == 0)) {
                            continue;
                        }
                    }
                } else {
                    flag = true;
                }

                current.aBoolean1322 = false;

                if (current.bridge != null) {
                    Tile t = current.bridge;

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

                boolean drawItemPile = false;

                if (current.underlay != null) {
                    if (!isTileCulled(plane, x, y)) {
                        drawItemPile = true;
                        drawUnderlayTile(current.underlay, plane, camPSin, camPCos, camYSin, camYCos, x, y);
                    }
                } else if (current.overlay != null && !isTileCulled(plane, x, y)) {
                    drawItemPile = true;
                    drawOverlayTile(current.overlay, x, y, camPSin, camPCos, camYSin, camYCos);
                }

                int camDir = 0;
                int renderFlags = 0;
                WallLoc wl = current.wall;
                WallDecoration wd = current.wallDecoration;

                if (wl != null || wd != null) {
                    if (camLocalX == x) {
                        camDir++;
                    } else if (camLocalX < x) {
                        camDir += 2;
                    }

                    if (camLocalY == y) {
                        camDir += 3;
                    } else if (camLocalY > y) {
                        camDir += 6;
                    }

                    renderFlags = anIntArray478[camDir];
                    current.anInt1328 = anIntArray480[camDir];

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
                    if ((wl.rotationFlag & anIntArray479[camDir]) != 0) {
                        if (wl.rotationFlag == 0x10) {
                            current.anInt1325 = 0x3; // 0x2 | 0x1
                            current.anInt1326 = anIntArray481[camDir];
                            current.anInt1327 = 0x3 - current.anInt1326;
                        } else if (wl.rotationFlag == 0x20) {
                            current.anInt1325 = 0x6; // 0x4 | 0x2
                            current.anInt1326 = anIntArray482[camDir];
                            current.anInt1327 = 0x6 - current.anInt1326;
                        } else if (wl.rotationFlag == 0x40) {
                            current.anInt1325 = 0xC; // 0x8 | 0x4
                            current.anInt1326 = anIntArray483[camDir];
                            current.anInt1327 = 0xC - current.anInt1326;
                        } else {
                            current.anInt1325 = 0x9; // 0x8 | 0x1
                            current.anInt1326 = anIntArray484[camDir];
                            current.anInt1327 = 0x9 - current.anInt1326;
                        }
                    } else {
                        current.anInt1325 = 0;
                    }

                    if ((wl.rotationFlag & renderFlags) != 0 && !isWallCulled(plane, x, y, wl.rotationFlag)) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }

                    if ((wl.cornerFlag & renderFlags) != 0 && !isWallCulled(plane, x, y, wl.cornerFlag)) {
                        wl.extension.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }
                }

                if (wd != null && !isCulled(plane, x, y, wd.node.height)) {
                    if ((wd.flags & renderFlags) != 0) {
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

                if (drawItemPile) {
                    GroundDecoration d = current.groundDecoration;

                    if (d != null) {
                        d.node.render(0, camPSin, camPCos, camYSin, camYCos, d.sceneX - camX, d.sceneY - camY, d.sceneZ - camZ, d.uid);
                    }

                    ItemPile i = current.itemPile;

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

                int tFlags = current.flags;

                if (tFlags != 0) {
                    if (x < camLocalX && (tFlags & 4) != 0) {
                        Tile t1 = tiles[x + 1][y];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (y < camLocalY && (tFlags & 2) != 0) {
                        Tile t1 = tiles[x][y + 1];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (x > camLocalX && (tFlags & 1) != 0) {
                        Tile t1 = tiles[x - 1][y];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }

                    if (y > camLocalY && (tFlags & 8) != 0) {
                        Tile t1 = tiles[x][y - 1];

                        if (t1 != null && t1.aBoolean1323) {
                            tileQueue.pushBack(t1);
                        }
                    }
                }
            }

            if (current.anInt1325 != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < current.locCount; k1++) {
                    if (current.locs[k1].cycle == cycle || (current.locFlag[k1] & current.anInt1325) != current.anInt1326) {
                        continue;
                    }
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    WallLoc wl = current.wall;

                    if (!isWallCulled(plane, x, y, wl.rotationFlag)) {
                        wl.root.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }

                    current.anInt1325 = 0;
                }
            }

            if (current.aBoolean1324) {
                try {
                    int i1 = current.locCount;
                    current.aBoolean1324 = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        StaticLoc l = current.locs[k2];

                        if (l.cycle == cycle) {
                            continue;
                        }

                        for (int pieceX = l.localX0; pieceX <= l.localX1; pieceX++) {
                            for (int pieceY = l.localY0; pieceY <= l.localY1; pieceY++) {
                                Tile t = tiles[pieceX][pieceY];
                                if (t.aBoolean1322) {
                                    current.aBoolean1324 = true;
                                } else {
                                    if (t.anInt1325 == 0) {
                                        continue;
                                    }
                                    int flags = 0;

                                    if (pieceX > l.localX0) {
                                        flags++;
                                    }

                                    if (pieceX < l.localX1) {
                                        flags += 0x4;
                                    }

                                    if (pieceY > l.localY0) {
                                        flags += 0x8;
                                    }

                                    if (pieceY < l.localY1) {
                                        flags += 0x2;
                                    }

                                    if ((flags & t.anInt1325) != current.anInt1327) {
                                        continue;
                                    }

                                    current.aBoolean1324 = true;
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
                                    int xCamDiff = sl.x - camX;
                                    int yCamDiff = sl.y - camY;
                                    int l9 = aClass28Array462[l3].x - camX;
                                    int l10 = aClass28Array462[l3].y - camY;

                                    if ((xCamDiff * xCamDiff + yCamDiff * yCamDiff) > l9 * l9 + l10 * l10) {
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

                        for (int pieceX = sl.localX0; pieceX <= sl.localX1; pieceX++) {
                            for (int pieceY = sl.localY0; pieceY <= sl.localY1; pieceY++) {
                                Tile t = tiles[pieceX][pieceY];
                                if (t.anInt1325 != 0) {
                                    tileQueue.pushBack(t);
                                } else if ((pieceX != x || pieceY != y) && t.aBoolean1323) {
                                    tileQueue.pushBack(t);
                                }
                            }

                        }

                    }
                    if (current.aBoolean1324) {
                        continue;
                    }
                } catch (Exception _ex) {
                    current.aBoolean1324 = false;
                }
            }

            if (!current.aBoolean1323 || current.anInt1325 != 0) {
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

            current.aBoolean1323 = false;
            anInt446--;

            ItemPile ip = current.itemPile;

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

            if (current.anInt1328 != 0) {
                WallDecoration wd = current.wallDecoration;
                if (wd != null && !isCulled(plane, x, y, wd.node.height)) {
                    if ((wd.flags & current.anInt1328) != 0) {
                        wd.node.render(wd.rotation, camPSin, camPCos, camYSin, camYCos, wd.x - camX, wd.y - camY, wd.z - camZ, wd.uid);
                    } else if ((wd.flags & 0x300) != 0) {
                        int deltaX = wd.x - camX;
                        int deltaZ = wd.z - camZ;
                        int deltaY = wd.y - camY;
                        int rotation = wd.rotation;

                        int j6;
                        if (rotation == 1 || rotation == 2) {
                            j6 = -deltaX;
                        } else {
                            j6 = deltaX;
                        }

                        int l7;
                        if (rotation == 2 || rotation == 3) {
                            l7 = -deltaY;
                        } else {
                            l7 = deltaY;
                        }

                        if ((wd.flags & 0x100) != 0 && l7 >= j6) {
                            int sX = deltaX + anIntArray463[rotation];
                            int sY = deltaY + anIntArray464[rotation];
                            wd.node.render(rotation * 512 + 256, camPSin, camPCos, camYSin, camYCos, sX, sY, deltaZ, wd.uid);
                        }

                        if ((wd.flags & 0x200) != 0 && l7 <= j6) {
                            int sX = deltaX + anIntArray465[rotation];
                            int sY = deltaY + anIntArray466[rotation];
                            wd.node.render(rotation * 512 + 1280 & 0x7ff, camPSin, camPCos, camYSin, camYCos, sX, sY, deltaZ, wd.uid);
                        }
                    }
                }

                WallLoc wl = current.wall;

                if (wl != null) {
                    if ((wl.cornerFlag & current.anInt1328) != 0 && !isWallCulled(plane, x, y, wl.cornerFlag)) {
                        wl.extension.render(0, camPSin, camPCos, camYSin, camYCos, wl.sceneX - camX, wl.sceneY - camY, wl.sceneZ - camZ, wl.uid);
                    }
                    if ((wl.rotationFlag & current.anInt1328) != 0 && !isWallCulled(plane, x, y, wl.rotationFlag)) {
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

    public boolean method323(int plane, int x1, int x2, int y1, int y2, int zOff) {
        if (x1 == x2 && y1 == y2) {
            if (!isTileCulled(plane, x1, y1)) {
                return false;
            }
            int x = x1 << 7;
            int y = y1 << 7;

            return isCulled(x + 1, y + 1, heightMap[plane][x1][y1] - zOff) && isCulled((x + 128) - 1, y + 1, heightMap[plane][x1 + 1][y1] - zOff) && isCulled((x + 128) - 1, (y + 128) - 1, heightMap[plane][x1 + 1][y1 + 1] - zOff) && isCulled(x + 1, (y + 128) - 1, heightMap[plane][x1][y1 + 1] - zOff);
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
        int z = heightMap[plane][x1][y1] - zOff;

        if (!isCulled(x, y, z)) {
            return false;
        }

        int tileEndX = (x2 << 7) - 1;

        if (!isCulled(tileEndX, y, z)) {
            return false;
        }

        int tileEndY = (y2 << 7) - 1;

        if (!isCulled(x, tileEndY, z)) {
            return false;
        }

        return isCulled(tileEndX, tileEndY, z);
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

    public void setVisiblePlanes(int plane, int x, int y, int maxPlane) {
        Tile t = tiles[plane][x][y];
        if (t == null) {
            return;
        }
        tiles[plane][x][y].topPlane = maxPlane;
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

        int sceneX = x * 128 + 64;
        int sceneY = y * 128 + 64;
        deco.x = sceneX + ((deco.x - sceneX) * i) / 16;
        deco.y = sceneY + ((deco.y - sceneY) * i) / 16;
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
        int boxCount = cullingBoxCount[occlusionTopPlane];
        CullingBox culls[] = cullingBoxes1[occlusionTopPlane];
        cullingPosition = 0;

        for (int i = 0; i < boxCount; i++) {
            CullingBox c = culls[i];

            if (c.occlusionType == 1) {
                int zIndex = (c.localMinX - camLocalX) + 25;

                if (zIndex < 0 || zIndex > 50) {
                    continue;
                }

                int yawIndex = (c.localMinY - camLocalY) + 25;

                if (yawIndex < 0) {
                    yawIndex = 0;
                }

                int dy = (c.localMaxY - camLocalY) + 25;

                if (dy > 50) {
                    dy = 50;
                }

                boolean flag = false;

                while (yawIndex <= dy) {
                    if (cullingMap[zIndex][yawIndex++]) {
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
                int yawIndex = (c.localMinY - camLocalY) + 25;

                if (yawIndex < 0 || yawIndex > 50) {
                    continue;
                }

                int zIndex = (c.localMinX - camLocalX) + 25;

                if (zIndex < 0) {
                    zIndex = 0;
                }

                int dx = (c.localMaxX - camLocalX) + 25;

                if (dx > 50) {
                    dx = 50;
                }

                boolean flag1 = false;

                while (zIndex <= dx) {
                    if (cullingMap[zIndex++][yawIndex]) {
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
                    int startYaw = (c.localMinY - camLocalY) + 25;

                    if (startYaw < 0) {
                        startYaw = 0;
                    }

                    int endYaw = (c.localMaxY - camLocalY) + 25;

                    if (endYaw > 50) {
                        endYaw = 50;
                    }

                    if (startYaw <= endYaw) {
                        int startZ = (c.localMinX - camLocalX) + 25;

                        if (startZ < 0) {
                            startZ = 0;
                        }

                        int endZ = (c.localMaxX - camLocalX) + 25;

                        if (endZ > 50) {
                            endZ = 50;
                        }

                        boolean flag2 = false;

                        label0:
                        {
                            for (int zIndex = startZ; zIndex <= endZ; zIndex++) {
                                for (int yawIndex = startYaw; yawIndex <= endYaw; yawIndex++) {

                                    if (!cullingMap[zIndex][yawIndex]) {
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
