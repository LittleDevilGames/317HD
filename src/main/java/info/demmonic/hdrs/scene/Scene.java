package info.demmonic.hdrs.scene;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.Floor;
import info.demmonic.hdrs.cache.model.LocConfig;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.io.OnDemand;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.node.impl.Renderable;
import info.demmonic.hdrs.scene.model.CollisionMap;
import info.demmonic.hdrs.scene.model.Landscape;
import info.demmonic.hdrs.scene.model.Loc;
import info.demmonic.hdrs.scene.model.Model;
import info.demmonic.hdrs.util.ChunkUtils;

public class Scene {

    public static final int[] ROT_X_DELTA = {1, 0, -1, 0};
    public static final int[] ROT_Y_DELTA = {0, -1, 0, 1};
    public static final int[] WALL_EXT_DRAW_FLAGS = {0x10, 0x20, 0x40, 0x80};
    public static final int[] WALL_ROOT_DRAW_FLAGS = {0x1, 0x2, 0x4, 0x8};
    public static final byte WALL_SHADOW_INTENSITY = 50;
    public static int hueRandomizer = (int) (Math.random() * 9.0) - 4;
    public static int lightnessRandomizer = (int) (Math.random() * 9.0) - 4;
    public static int minPlane = 99;
    public static int planeAtBuild;
    public int[] blendedDirection;
    public int[] blendedHue;
    public int[] blendedHueDivisor;
    public int[] blendedLightness;
    public int[] blendedSatuartion;
    public short[][][] heightMap;
    public int landscapeSizeX;
    public int landscapeSizeY;
    public int[][] lightMap;
    public byte[][][] overlayFloIndex;
    public byte[][][] overlayRotation;
    public byte[][][] overlayShape;
    public byte[][][] renderFlags;
    public byte[][][] shadowMap;
    public int[][][] tileCullingBitset;
    public byte[][][] underlayFloIndex;

    public Scene(int sizeX, int sizeY, short[][][] heightMap, byte[][][] renderFlags) {
        Scene.minPlane = 99;
        this.landscapeSizeX = sizeX;
        this.landscapeSizeY = sizeY;
        this.heightMap = heightMap;
        this.renderFlags = renderFlags;
        this.underlayFloIndex = new byte[4][landscapeSizeX][landscapeSizeY];
        this.overlayFloIndex = new byte[4][landscapeSizeX][landscapeSizeY];
        this.overlayShape = new byte[4][landscapeSizeX][landscapeSizeY];
        this.overlayRotation = new byte[4][landscapeSizeX][landscapeSizeY];
        this.tileCullingBitset = new int[4][landscapeSizeX + 1][landscapeSizeY + 1];
        this.shadowMap = new byte[4][landscapeSizeX + 1][landscapeSizeY + 1];
        this.lightMap = new int[landscapeSizeX + 1][landscapeSizeY + 1];
        this.blendedHue = new int[landscapeSizeY];
        this.blendedSatuartion = new int[landscapeSizeY];
        this.blendedLightness = new int[landscapeSizeY];
        this.blendedHueDivisor = new int[landscapeSizeY];
        this.blendedDirection = new int[landscapeSizeY];
    }

    public static void addLoc(Landscape l, int x, int y, int plane, int loc_index, int locType, int loc_rotation, int vertex_plane, CollisionMap cm, short[][][] height_map) {
        int heightSouthWest = height_map[vertex_plane][x][y];
        int heightSouthEast = height_map[vertex_plane][x + 1][y];
        int heightNorthEast = height_map[vertex_plane][x + 1][y + 1];
        int heightNorthWest = height_map[vertex_plane][x][y + 1];
        int averageHeight = (heightSouthWest + heightSouthEast + heightNorthEast + heightNorthWest) >> 2;

        LocConfig c = LocConfig.get(loc_index);
        int uid = 0x40000000 | (loc_index << 14) | (y << 7) | x;

        if (!c.isStatic) {
            uid += Integer.MIN_VALUE;
        }

        byte locIndex = (byte) ((loc_rotation << 6) + locType);

        if (locType == 22) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(22, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, 22, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addGroundDecoration(x, y, averageHeight, plane, r, uid, locIndex);

            if (c.hasCollisions && c.isStatic) {
                cm.setSolid(x, y);
            }
        } else if (locType == 10 || locType == 11) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(10, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, 10, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            if (r != null) {
                int rotation = 0;

                if (locType == 11) {
                    rotation += 512;
                }

                int size_y;
                int size_x;

                if (loc_rotation == 1 || loc_rotation == 3) {
                    size_y = c.sizeY;
                    size_x = c.sizeX;
                } else {
                    size_y = c.sizeX;
                    size_x = c.sizeY;
                }

                l.add(r, x, y, plane, size_x, size_y, averageHeight, locIndex, rotation, uid);
            }

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType >= 12) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(locType, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, locType, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.add(r, x, y, plane, 1, 1, averageHeight, locIndex, 0, uid);

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType == 0) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(0, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, 0, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_ROOT_DRAW_FLAGS[loc_rotation], 0, locIndex, true, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType == 1) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(1, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, 1, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_EXT_DRAW_FLAGS[loc_rotation], 0, locIndex, true, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType == 2) {
            int nextRotation = loc_rotation + 1 & 0x3;
            Renderable r1;
            Renderable r2;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r1 = c.getModel(2, 4 + loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                r2 = c.getModel(2, nextRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r1 = new Loc(loc_index, 4 + loc_rotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                r2 = new Loc(loc_index, nextRotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r1, r2, x, y, averageHeight, plane, WALL_ROOT_DRAW_FLAGS[loc_rotation], WALL_ROOT_DRAW_FLAGS[nextRotation], locIndex, true, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType == 3) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(3, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(loc_index, loc_rotation, 3, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_EXT_DRAW_FLAGS[loc_rotation], 0, locIndex, true, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, loc_rotation, c.blocksProjectiles);
            }
        } else if (locType == 9) {
            Renderable node;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                node = c.getModel(locType, loc_rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                node = new Loc(loc_index, loc_rotation, locType, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.add(node, x, y, plane, 1, 1, averageHeight, locIndex, 0, uid);

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, loc_rotation, c.blocksProjectiles);
            }
        } else {
            if (c.adjustToTerrain) {
                if (loc_rotation == 1) {
                    int v_sw2 = heightNorthWest;
                    heightNorthWest = heightNorthEast;
                    heightNorthEast = heightSouthEast;
                    heightSouthEast = heightSouthWest;
                    heightSouthWest = v_sw2;
                } else if (loc_rotation == 2) {
                    int v_sw2 = heightNorthWest;
                    heightNorthWest = heightSouthEast;
                    heightSouthEast = v_sw2;
                    v_sw2 = heightNorthEast;
                    heightNorthEast = heightSouthWest;
                    heightSouthWest = v_sw2;
                } else if (loc_rotation == 3) {
                    int v_ne2 = heightNorthWest;
                    heightNorthWest = heightSouthWest;
                    heightSouthWest = heightSouthEast;
                    heightSouthEast = heightNorthEast;
                    heightNorthEast = v_ne2;
                }
            }
            if (locType == 4) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(loc_index, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, loc_rotation * 512, locIndex, WALL_ROOT_DRAW_FLAGS[loc_rotation], uid);
            } else if (locType == 5) {
                int width = 16;
                int wall_uid = l.getWallUid(plane, x, y);

                if (wall_uid > 0) {
                    width = LocConfig.get(wall_uid >> 14 & 0x7fff).wallWidth;
                }

                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(loc_index, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, ROT_X_DELTA[loc_rotation] * width, ROT_Y_DELTA[loc_rotation] * width, averageHeight, loc_rotation * 512, locIndex, WALL_ROOT_DRAW_FLAGS[loc_rotation], uid);
            } else if (locType == 6) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(loc_index, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, loc_rotation, locIndex, 256, uid);
            } else if (locType == 7) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(loc_index, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, loc_rotation, locIndex, 512, uid);
            } else if (locType == 8) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(loc_index, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, loc_rotation, locIndex, 768, uid);
            }
        }
    }

    public static int getLerpedCosine(int a, int b, int x, int frequency) {
        int f = (65536 - Canvas3D.cos[x * 1024 / frequency] >> 1);
        return (a * (65536 - f) >> 16) + (b * f >> 16);
    }

    public static int getNoise(int a, int b, int amplitude) {
        int x = a / amplitude;
        int x1 = a & amplitude - 1;
        int y = b / amplitude;
        int x2 = b & amplitude - 1;
        int a1 = getNoise2d(x, y);
        int b1 = getNoise2d(x + 1, y);
        int a2 = getNoise2d(x, y + 1);
        int b2 = getNoise2d(x + 1, y + 1);
        int a3 = getLerpedCosine(a1, b1, x1, amplitude);
        int b3 = getLerpedCosine(a2, b2, x1, amplitude);
        return getLerpedCosine(a3, b3, x2, amplitude);
    }

    public static int getNoise2d(int x, int y) {
        int a = (getPerlinNoise(x - 1, y - 1) + getPerlinNoise(x + 1, y - 1) + getPerlinNoise(x - 1, y + 1) + getPerlinNoise(x + 1, y + 1));
        int b = (getPerlinNoise(x - 1, y) + getPerlinNoise(x + 1, y) + getPerlinNoise(x, y - 1) + getPerlinNoise(x, y + 1));
        int c = getPerlinNoise(x, y);
        return a / 16 + b / 8 + c / 4;
    }

    public static int getNoiseHeight(int x, int y) {
        int height = (getNoise(x + 45365, y + 91923, 4) - 128 + (getNoise(x + 10294, y + 37821, 2) - 128 >> 1) + (getNoise(x, y, 1) - 128 >> 2));
        height = (int) ((double) height * 0.3) + 35;

        if (height < 10) {
            height = 10;
        } else if (height > 60) {
            height = 60;
        }

        return height;
    }

    public static int getPerlinNoise(int x, int y) {
        int a = x + y * 57;
        a = a << 13 ^ a;
        int b = a * (a * a * 15731 + 789221) + 1376312589 & 0x7fffffff;
        return b >> 19 & 0xFF;
    }

    public static boolean isLocLoaded(int locIndex, int locType) {
        LocConfig config = LocConfig.get(locIndex);

        if (locType == 11) {
            locType = 10;
        }

        if (locType >= 5 && locType <= 8) {
            locType = 4;
        }

        return config.isValidModel(locType);
    }

    public static boolean locsFullyLoaded(int mapX, int mapY, byte[] landscapePayload) {
        boolean valid = true;
        Buffer s = new Buffer(landscapePayload);
        int loc_index = -1;

        for (; ; ) {
            int i = s.writeUnsignedSmart();

            if (i == 0) {
                break;
            }

            loc_index += i;
            int xy = 0;
            boolean verify_next = false;

            for (; ; ) {
                if (verify_next) {
                    int lsb = s.writeUnsignedSmart();

                    if (lsb == 0) {
                        break;
                    }

                    s.readUnsignedByte();
                } else {
                    int msb = s.writeUnsignedSmart();

                    if (msb == 0) {
                        break;
                    }

                    xy += msb - 1;
                    int loc_y = xy & 0x3f;
                    int loc_x = xy >> 6 & 0x3f;
                    int loc_type = s.readUnsignedByte() >> 2;
                    int x = loc_x + mapX;
                    int y = loc_y + mapY;

                    if (x > 0 && y > 0 && x < 103 && y < 103) {
                        LocConfig config = LocConfig.get(loc_index);

                        if (loc_type != 22 || !Game.lowDetail || config.isStatic || config.isDecoration) {
                            valid &= config.hasValidModel();
                            verify_next = true;
                        }
                    }
                }
            }
        }
        return valid;
    }

    public static void requestLocModels(Buffer b, OnDemand requester) {
        int index = -1;
        for (; ; ) {
            int lsb = b.writeUnsignedSmart();

            if (lsb == 0) {
                break;
            }

            index += lsb;
            LocConfig config = LocConfig.get(index);
            config.requestModels(requester);

            for (; ; ) {
                int i = b.writeUnsignedSmart();
                if (i == 0) {
                    break;
                }
                b.readUnsignedByte();
            }
        }
    }

    public static int setHslLightness(int hsl, int l) {
        if (hsl == -1) {
            return 12345678;
        }

        l = l * (hsl & 0x7f) / 128;

        if (l < 2) {
            l = 2;
        } else if (l > 126) {
            l = 126;
        }

        return (hsl & 0xff80) + l;
    }

    public static int trimHsl(int hue, int saturation, int lightness) {
        if (lightness > 179) {
            saturation /= 2;
        }
        if (lightness > 192) {
            saturation /= 2;
        }
        if (lightness > 217) {
            saturation /= 2;
        }
        if (lightness > 243) {
            saturation /= 2;
        }
        return (hue / 4 << 10) + (saturation / 32 << 7) + lightness / 2;
    }

    public void addLoc(Landscape landscape, CollisionMap collision, int loc_index, int type, int x, int y, int plane, int rotation) {
        if (!Game.lowDetail || (renderFlags[0][x][y] & 0x2) != 0 || ((renderFlags[plane][x][y] & 0x10) == 0 && setVisibilityPlane(plane, x, y) == planeAtBuild)) {
            if (plane < minPlane) {
                minPlane = plane;
            }

            int v_sw = heightMap[plane][x][y];
            int v_se = heightMap[plane][x + 1][y];
            int v_ne = heightMap[plane][x + 1][y + 1];
            int v_nw = heightMap[plane][x][y + 1];
            int v_avg = (v_sw + v_se + v_ne + v_nw) >> 2;

            LocConfig c = LocConfig.get(loc_index);
            int uid = 0x40000000 | (loc_index << 14) | (y << 7) | x;

            if (!c.isStatic) {
                uid += Integer.MIN_VALUE;
            }

            byte arrangement = (byte) ((rotation << 6) + type);

            if (type == 22) {
                if (!Game.lowDetail || c.isStatic || c.isDecoration) {
                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(22, rotation, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        r = new Loc(loc_index, rotation, 22, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addGroundDecoration(x, y, v_avg, plane, r, uid, arrangement);

                    if (c.hasCollisions && c.isStatic && collision != null) {
                        collision.setSolid(x, y);
                    }

                }
            } else if (type == 10 || type == 11) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(10, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, 10, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                if (r != null) {
                    int angle = 0;
                    int size_y;
                    int size_x;

                    if (type == 11) {
                        angle += 256;
                    }

                    if (rotation == 1 || rotation == 3) {
                        size_y = c.sizeY;
                        size_x = c.sizeX;
                    } else {
                        size_y = c.sizeX;
                        size_x = c.sizeY;
                    }

                    if (landscape.add(r, x, y, plane, size_x, size_y, v_avg, arrangement, angle, uid) && c.castsShadow) {
                        Model mesh;

                        if (r instanceof Model) {
                            mesh = (Model) r;
                        } else {
                            mesh = c.getModel(10, rotation, v_sw, v_se, v_ne, v_nw, -1);
                        }

                        if (mesh != null) {
                            for (int x_off = 0; x_off <= size_y; x_off++) {
                                for (int y_off = 0; y_off <= size_x; y_off++) {
                                    int i = mesh.max_horizon / 4;

                                    if (i > 30) {
                                        i = 30;
                                    }

                                    if (i > (shadowMap[plane][x + x_off][y + y_off])) {
                                        shadowMap[plane][x + x_off][y + y_off] = (byte) i;
                                    }
                                }
                            }
                        }

                    }
                }

                if (c.hasCollisions && collision != null) {
                    collision.addLoc(x, y, c.sizeX, c.sizeY, rotation, c.blocksProjectiles);
                }

            } else if (type >= 12) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(type, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, type, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.add(r, x, y, plane, 1, 1, v_avg, arrangement, 0, uid);

                if (type >= 12 && type <= 17 && type != 13 && plane > 0) {
                    tileCullingBitset[plane][x][y] |= 0x924;
                }

                if (c.hasCollisions && collision != null) {
                    collision.addLoc(x, y, c.sizeX, c.sizeY, rotation, c.blocksProjectiles);
                }

            } else if (type == 0) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(0, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, 0, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, v_avg, plane, WALL_ROOT_DRAW_FLAGS[rotation], 0, arrangement, true, uid);

                if (rotation == 0) {
                    if (c.castsShadow) {
                        shadowMap[plane][x][y] = WALL_SHADOW_INTENSITY;
                        shadowMap[plane][x][y + 1] = WALL_SHADOW_INTENSITY;
                    }

                    if (c.isSolid) {
                        tileCullingBitset[plane][x][y] |= 0x249;
                    }
                } else if (rotation == 1) {
                    if (c.castsShadow) {
                        shadowMap[plane][x][y + 1] = WALL_SHADOW_INTENSITY;
                        shadowMap[plane][x + 1][y + 1] = WALL_SHADOW_INTENSITY;
                    }

                    if (c.isSolid) {
                        tileCullingBitset[plane][x][y + 1] |= 0x492;
                    }
                } else if (rotation == 2) {
                    if (c.castsShadow) {
                        shadowMap[plane][x + 1][y] = WALL_SHADOW_INTENSITY;
                        shadowMap[plane][x + 1][y + 1] = WALL_SHADOW_INTENSITY;
                    }

                    if (c.isSolid) {
                        tileCullingBitset[plane][x + 1][y] |= 0x249;
                    }
                } else if (rotation == 3) {
                    if (c.castsShadow) {
                        shadowMap[plane][x][y] = WALL_SHADOW_INTENSITY;
                        shadowMap[plane][x + 1][y] = WALL_SHADOW_INTENSITY;
                    }

                    if (c.isSolid) {
                        tileCullingBitset[plane][x][y] |= 0x492;
                    }
                }

                if (c.hasCollisions && collision != null) {
                    collision.addWall(x, y, type, rotation, c.blocksProjectiles);
                }

                if (c.wallWidth != 16) {
                    landscape.setWallDecoMargin(x, y, plane, c.wallWidth);
                }

            } else if (type == 1) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(1, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, 1, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, v_avg, plane, WALL_EXT_DRAW_FLAGS[rotation], 0, arrangement, true, uid);

                if (c.castsShadow) {
                    if (rotation == 0) {
                        shadowMap[plane][x][y + 1] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 1) {
                        shadowMap[plane][x + 1][y + 1] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 2) {
                        shadowMap[plane][x + 1][y] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 3) {
                        shadowMap[plane][x][y] = WALL_SHADOW_INTENSITY;
                    }
                }

                if (c.hasCollisions && collision != null) {
                    collision.addWall(x, y, type, rotation, c.blocksProjectiles);
                }
            } else if (type == 2) {
                int next_rotation = rotation + 1 & 0x3;
                Renderable r1;
                Renderable r2;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r1 = c.getModel(2, 4 + rotation, v_sw, v_se, v_ne, v_nw, -1);
                    r2 = c.getModel(2, next_rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r1 = new Loc(loc_index, 4 + rotation, 2, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    r2 = new Loc(loc_index, next_rotation, 2, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.addWall(r1, r2, x, y, v_avg, plane, WALL_ROOT_DRAW_FLAGS[rotation], WALL_ROOT_DRAW_FLAGS[next_rotation], arrangement, true, uid);

                if (c.isSolid) {
                    if (rotation == 0) {
                        tileCullingBitset[plane][x][y] |= 0x249;
                        tileCullingBitset[plane][x][y + 1] |= 0x492;
                    } else if (rotation == 1) {
                        tileCullingBitset[plane][x][y + 1] |= 0x492;
                        tileCullingBitset[plane][x + 1][y] |= 0x249;
                    } else if (rotation == 2) {
                        tileCullingBitset[plane][x + 1][y] |= 0x249;
                        tileCullingBitset[plane][x][y] |= 0x492;
                    } else if (rotation == 3) {
                        tileCullingBitset[plane][x][y] |= 0x492;
                        tileCullingBitset[plane][x][y] |= 0x249;
                    }
                }

                if (c.hasCollisions && collision != null) {
                    collision.addWall(x, y, type, rotation, c.blocksProjectiles);
                }

                if (c.wallWidth != 16) {
                    landscape.setWallDecoMargin(x, y, plane, c.wallWidth);
                }
            } else if (type == 3) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(3, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, 3, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, v_avg, plane, WALL_EXT_DRAW_FLAGS[rotation], 0, arrangement, true, uid);

                if (c.castsShadow) {
                    if (rotation == 0) {
                        shadowMap[plane][x][y + 1] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 1) {
                        shadowMap[plane][x + 1][y + 1] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 2) {
                        shadowMap[plane][x + 1][y] = WALL_SHADOW_INTENSITY;
                    } else if (rotation == 3) {
                        shadowMap[plane][x][y] = WALL_SHADOW_INTENSITY;
                    }
                }

                if (c.hasCollisions && collision != null) {
                    collision.addWall(x, y, type, rotation, c.blocksProjectiles);
                }

            } else if (type == 9) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(type, rotation, v_sw, v_se, v_ne, v_nw, -1);
                } else {
                    r = new Loc(loc_index, rotation, type, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                }

                landscape.add(r, x, y, plane, 1, 1, v_avg, arrangement, 0, uid);

                if (c.hasCollisions && collision != null) {
                    collision.addLoc(x, y, c.sizeX, c.sizeY, rotation, c.blocksProjectiles);
                }
            } else {
                if (c.adjustToTerrain) {
                    if (rotation == 1) {
                        int new_v_sw = v_nw;
                        v_nw = v_ne;
                        v_ne = v_se;
                        v_se = v_sw;
                        v_sw = new_v_sw;
                    } else if (rotation == 2) {
                        int new_v_sw = v_nw;
                        v_nw = v_se;
                        v_se = new_v_sw;
                        new_v_sw = v_ne;
                        v_ne = v_sw;
                        v_sw = new_v_sw;
                    } else if (rotation == 3) {
                        int new_v_sw = v_nw;
                        v_nw = v_sw;
                        v_sw = v_se;
                        v_se = v_ne;
                        v_ne = new_v_sw;
                    }
                }

                if (type == 4) {
                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(4, 0, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        r = new Loc(loc_index, 0, 4, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(r, x, y, plane, 0, 0, v_avg, rotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[rotation], uid);
                } else if (type == 5) {
                    int width = 16;
                    int wall_uid = landscape.getWallUid(plane, x, y);

                    if (wall_uid > 0) {
                        width = LocConfig.get(wall_uid >> 14 & 0x7fff).wallWidth;
                    }

                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(4, 0, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        r = new Loc(loc_index, 0, 4, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(r, x, y, plane, ROT_X_DELTA[rotation] * width, ROT_Y_DELTA[rotation] * width, v_avg, rotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[rotation], uid);
                } else if (type == 6) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        node = new Loc(loc_index, 0, 4, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, v_avg, rotation, arrangement, 0x100, uid);
                } else if (type == 7) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        node = new Loc(loc_index, 0, 4, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, v_avg, rotation, arrangement, 0x200, uid);
                } else if (type == 8) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, v_sw, v_se, v_ne, v_nw, -1);
                    } else {
                        node = new Loc(loc_index, 0, 4, v_se, v_ne, v_sw, v_nw, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, v_avg, rotation, arrangement, 0x300, uid);
                }
            }
        }
    }

    public void createLandMesh(CollisionMap[] cm, Landscape l) {
        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    if ((renderFlags[plane][x][z] & 0x1) == 1) {
                        int plane_ = plane;

                        if ((renderFlags[1][x][z] & 0x2) == 2) {
                            plane_--;
                        }

                        if (plane_ >= 0) {
                            cm[plane_].setSolid(x, z);
                        }
                    }
                }
            }
        }

        hueRandomizer += (int) (Math.random() * 5.0) - 2;

        if (hueRandomizer < -8) {
            hueRandomizer = -8;
        }

        if (hueRandomizer > 8) {
            hueRandomizer = 8;
        }

        lightnessRandomizer += (int) (Math.random() * 5.0) - 2;

        if (lightnessRandomizer < -16) {
            lightnessRandomizer = -16;
        }

        if (lightnessRandomizer > 16) {
            lightnessRandomizer = 16;
        }

        for (int plane = 0; plane < 4; plane++) {
            byte[][] shadowIntensity = shadowMap[plane];
            int initial_light_intensity = 96;
            int specular_factor = 768;
            int light_x = -50;
            int light_z = -10;
            int light_y = -50;
            int light_length = (int) Math.sqrt((double) (light_x * light_x + light_z * light_z + light_y * light_y));
            int specular_distribution = specular_factor * light_length >> 8;

            for (int y = 1; y < landscapeSizeY - 1; y++) {
                for (int x = 1; x < landscapeSizeX - 1; x++) {
                    int x_height_diff = (heightMap[plane][x + 1][y] - heightMap[plane][x - 1][y]);
                    int y_height_diff = (heightMap[plane][x][y + 1] - heightMap[plane][x][y - 1]);
                    int normal_length = (int) Math.sqrt((double) (x_height_diff * x_height_diff + 65536 + y_height_diff * y_height_diff));
                    int normalized_x = (x_height_diff << 8) / normal_length;
                    int normalized_z = 65536 / normal_length;
                    int normalized_y = (y_height_diff << 8) / normal_length;
                    int intensity = initial_light_intensity + (light_x * normalized_x + light_z * normalized_z + light_y * normalized_y) / specular_distribution;
                    int weighted_shadow_intensity = ((shadowIntensity[x - 1][y] >> 2) + (shadowIntensity[x + 1][y] >> 3) + (shadowIntensity[x][y - 1] >> 2) + (shadowIntensity[x][y + 1] >> 3) + (shadowIntensity[x][y] >> 1));
                    lightMap[x][y] = intensity - weighted_shadow_intensity;
                }
            }

            for (int y = 0; y < landscapeSizeY; y++) {
                blendedHue[y] = 0;
                blendedSatuartion[y] = 0;
                blendedLightness[y] = 0;
                blendedHueDivisor[y] = 0;
                blendedDirection[y] = 0;
            }

            for (int x = -5; x < landscapeSizeX + 5; x++) {
                for (int y = 0; y < landscapeSizeY; y++) {
                    int x_positive_offset = x + 5;
                    if (x_positive_offset >= 0 && x_positive_offset < landscapeSizeX) {
                        int flo_index = underlayFloIndex[plane][x_positive_offset][y] & 0xFF;
                        if (flo_index > 0) {
                            Floor floor = Floor.instance[flo_index - 1];
                            blendedHue[y] += floor.hue;
                            blendedSatuartion[y] += floor.saturation;
                            blendedLightness[y] += floor.lightness;
                            blendedHueDivisor[y] += floor.hueDivisor;
                            blendedDirection[y]++;
                        }
                    }

                    int x_negative_offset = x - 5;
                    if (x_negative_offset >= 0 && x_negative_offset < landscapeSizeX) {
                        int flo_index = underlayFloIndex[plane][x_negative_offset][y] & 0xFF;
                        if (flo_index > 0) {
                            Floor f = Floor.instance[flo_index - 1];
                            blendedHue[y] -= f.hue;
                            blendedSatuartion[y] -= f.saturation;
                            blendedLightness[y] -= f.lightness;
                            blendedHueDivisor[y] -= f.hueDivisor;
                            blendedDirection[y]--;
                        }
                    }
                }

                if (x >= 1 && x < landscapeSizeX - 1) {
                    int blended_hue = 0;
                    int blended_saturation = 0;
                    int blended_brightness = 0;
                    int blended_hue_divisor = 0;
                    int blended_direction_tracker = 0;

                    for (int y = -5; y < landscapeSizeY + 5; y++) {
                        int y_positive_offset = y + 5;
                        if (y_positive_offset >= 0 && y_positive_offset < landscapeSizeY) {
                            blended_hue += this.blendedHue[y_positive_offset];
                            blended_saturation += this.blendedSatuartion[y_positive_offset];
                            blended_brightness += blendedLightness[y_positive_offset];
                            blended_hue_divisor += this.blendedHueDivisor[y_positive_offset];
                            blended_direction_tracker += blendedDirection[y_positive_offset];
                        }

                        int y_negative_offset = y - 5;
                        if (y_negative_offset >= 0 && y_negative_offset < landscapeSizeY) {
                            blended_hue -= this.blendedHue[y_negative_offset];
                            blended_saturation -= this.blendedSatuartion[y_negative_offset];
                            blended_brightness -= blendedLightness[y_negative_offset];
                            blended_hue_divisor -= this.blendedHueDivisor[y_negative_offset];
                            blended_direction_tracker -= blendedDirection[y_negative_offset];
                        }

                        if (y >= 1 && y < landscapeSizeY - 1 && (!Game.lowDetail || (renderFlags[0][x][y] & 0x2) != 0 || ((renderFlags[plane][x][y] & 0x10) == 0 && (setVisibilityPlane(plane, x, y) == planeAtBuild)))) {
                            if (plane < minPlane) {
                                minPlane = plane;
                            }

                            int underlay_id = underlayFloIndex[plane][x][y] & 0xFF;
                            int overlay_id = overlayFloIndex[plane][x][y] & 0xFF;

                            if (underlay_id > 0 || overlay_id > 0) {
                                short v_sw = heightMap[plane][x][y];
                                short v_se = heightMap[plane][x + 1][y];
                                short v_ne = heightMap[plane][x + 1][y + 1];
                                short v_nw = heightMap[plane][x][y + 1];
                                int l_sw = lightMap[x][y];
                                int l_se = lightMap[x + 1][y];
                                int l_ne = lightMap[x + 1][y + 1];
                                int l_nw = lightMap[x][y + 1];
                                int hsl = -1;
                                int hsl_randomized = -1;

                                if (underlay_id > 0) {
                                    int hue = blended_hue * 256 / blended_hue_divisor;
                                    int saturation = blended_saturation / blended_direction_tracker;
                                    int brightness = blended_brightness / blended_direction_tracker;
                                    hsl = trimHsl(hue, saturation, brightness);
                                    hue = hue + hueRandomizer & 0xFF;
                                    brightness += lightnessRandomizer;

                                    if (brightness < 0) {
                                        brightness = 0;
                                    } else if (brightness > 255) {
                                        brightness = 255;
                                    }

                                    hsl_randomized = trimHsl(hue, saturation, brightness);
                                }

                                if (plane > 0) {
                                    boolean hide_underlay = true;

                                    if (underlay_id == 0 && overlayShape[plane][x][y] != 0) {
                                        hide_underlay = false;
                                    }

                                    if (overlay_id > 0 && !Floor.instance[overlay_id - 1].showUnderlay) {
                                        hide_underlay = false;
                                    }

                                    if (hide_underlay && v_sw == v_se && v_sw == v_ne && v_sw == v_nw) {
                                        tileCullingBitset[plane][x][y] |= 0x924;
                                    }
                                }

                                int rgb_randomized = 0;

                                if (hsl != -1) {
                                    rgb_randomized = Canvas3D.palette[setHslLightness(hsl_randomized, 96)];
                                }

                                if (overlay_id == 0) {
                                    l.addTile(plane, x, y, 0, 0, (byte) -1, v_sw, v_se, v_ne, v_nw, setHslLightness(hsl, l_sw), setHslLightness(hsl, l_se), setHslLightness(hsl, l_ne), setHslLightness(hsl, l_nw), 0, 0, 0, 0, rgb_randomized, 0);
                                } else {
                                    int shape = overlayShape[plane][x][y] + 1;
                                    byte rotation = overlayRotation[plane][x][y];
                                    Floor f = Floor.instance[overlay_id - 1];
                                    byte texture = f.textureIndex;
                                    int hsl_bitset;
                                    int rgb_bitset;

                                    if (texture >= 0) {
                                        hsl_bitset = Canvas3D.get_average_texture_rgb(texture);
                                        rgb_bitset = -1;
                                    } else if (f.color2 == 0xFF00FF) {
                                        hsl_bitset = 0;
                                        rgb_bitset = -2;
                                        texture = -1;
                                    } else {
                                        rgb_bitset = trimHsl(f.hue2, f.saturation, f.lightness);
                                        hsl_bitset = Canvas3D.palette[getRgb(f.color, 96)];
                                    }

                                    l.addTile(plane, x, y, shape, rotation, texture, v_sw, v_se, v_ne, v_nw, setHslLightness(hsl, l_sw), setHslLightness(hsl, l_se), setHslLightness(hsl, l_ne), setHslLightness(hsl, l_nw), getRgb(rgb_bitset, l_sw), getRgb(rgb_bitset, l_se), getRgb(rgb_bitset, l_ne), getRgb(rgb_bitset, l_nw), rgb_randomized, hsl_bitset);
                                }
                            }
                        }
                    }
                }
            }

            for (int y = 1; y < landscapeSizeY - 1; y++) {
                for (int x = 1; x < landscapeSizeX - 1; x++) {
                    l.setVisiblePlanes(plane, x, y, setVisibilityPlane(plane, x, y));
                }
            }
        }

        l.applyUntexturedObjects(-50, -10, -50, 64, 768);

        for (int x = 0; x < landscapeSizeX; x++) {
            for (int y = 0; y < landscapeSizeY; y++) {
                if ((renderFlags[1][x][y] & 0x2) == 2) {
                    l.addBridge(x, y);
                }
            }
        }

        int render_rule1 = 1;
        int render_rule2 = 2;
        int render_rule3 = 4;

        for (int k = 0; k < 4; k++) {
            if (k > 0) {
                render_rule1 <<= 3;
                render_rule2 <<= 3;
                render_rule3 <<= 3;
            }

            for (int plane = 0; plane <= k; plane++) {
                for (int y = 0; y <= landscapeSizeY; y++) {
                    for (int x = 0; x <= landscapeSizeX; x++) {
                        if ((tileCullingBitset[plane][x][y] & render_rule1) != 0) {
                            int min_y = y;
                            int max_y = y;
                            int min_plane = plane;
                            int max_plane = plane;

                            for (; min_y > 0; min_y--) {
                                if (((tileCullingBitset[plane][x][min_y - 1]) & render_rule1) == 0) {
                                    break;
                                }
                            }

                            for (; max_y < landscapeSizeY; max_y++) {
                                if (((tileCullingBitset[plane][x][max_y + 1]) & render_rule1) == 0) {
                                    break;
                                }
                            }

                            find_min_plane:
                            for (; min_plane > 0; min_plane--) {
                                for (int occluded_y = min_y; occluded_y <= max_y; occluded_y++) {
                                    if (((tileCullingBitset[min_plane - 1][x][occluded_y]) & render_rule1) == 0) {
                                        break find_min_plane;
                                    }
                                }
                            }

                            find_max_plane:
                            for (; max_plane < k; max_plane++) {
                                for (int occluded_y = min_y; occluded_y <= max_y; occluded_y++) {
                                    if (((tileCullingBitset[max_plane + 1][x][occluded_y]) & render_rule1) == 0) {
                                        break find_max_plane;
                                    }
                                }
                            }

                            int surface = (max_plane + 1 - min_plane) * (max_y - min_y + 1);

                            if (surface >= 8) {
                                int i = 240;
                                int max_v_z = (heightMap[max_plane][x][min_y] - i);
                                int min_v_z = heightMap[min_plane][x][min_y];
                                Landscape.createCullingBox(k, x * 128, x * 128, min_y * 128, max_y * 128 + 128, min_v_z, max_v_z, 1);

                                for (int occluded_plane = min_plane; occluded_plane <= max_plane; occluded_plane++) {
                                    for (int occluded_y = min_y; occluded_y <= max_y; occluded_y++) {
                                        tileCullingBitset[occluded_plane][x][occluded_y] &= ~render_rule1;
                                    }
                                }
                            }
                        }

                        if ((tileCullingBitset[plane][x][y] & render_rule2) != 0) {
                            int min_x = x;
                            int max_x = x;
                            int min_plane = plane;
                            int max_plane = plane;

                            for (; min_x > 0; min_x--) {
                                if (((tileCullingBitset[plane][min_x - 1][y]) & render_rule2) == 0) {
                                    break;
                                }
                            }

                            for (; max_x < landscapeSizeX; max_x++) {
                                if (((tileCullingBitset[plane][max_x + 1][y]) & render_rule2) == 0) {
                                    break;
                                }
                            }

                            find_lowest_plane:
                            for (; min_plane > 0; min_plane--) {
                                for (int occluded_x = min_x; occluded_x <= max_x; occluded_x++) {
                                    if (((tileCullingBitset[min_plane - 1][occluded_x][y]) & render_rule2) == 0) {
                                        break find_lowest_plane;
                                    }
                                }
                            }

                            find_highest_plane:
                            for (; max_plane < k; max_plane++) {
                                for (int occluded_x = min_x; occluded_x <= max_x; occluded_x++) {
                                    if (((tileCullingBitset[max_plane + 1][occluded_x][y]) & render_rule2) == 0) {
                                        break find_highest_plane;
                                    }
                                }
                            }

                            int surface = (max_plane + 1 - min_plane) * (max_x - min_x + 1);
                            if (surface >= 8) {
                                int i = 240;
                                int max_v_z = (heightMap[max_plane][min_x][y] - i);
                                int min_v_z = heightMap[min_plane][min_x][y];
                                Landscape.createCullingBox(k, min_x * 128, max_x * 128 + 128, y * 128, y * 128, min_v_z, max_v_z, 2);

                                for (int j = min_plane; j <= max_plane; j++) {
                                    for (int occluded_x = min_x; occluded_x <= max_x; occluded_x++) {
                                        tileCullingBitset[j][occluded_x][y] &= ~render_rule2;
                                    }
                                }
                            }
                        }

                        if ((tileCullingBitset[plane][x][y] & render_rule3) != 0) {
                            int lowest_occlusion_x = x;
                            int highest_occlusion_x = x;
                            int lowest_occlusion_y = y;
                            int highest_occlussion_y = y;

                            for (; lowest_occlusion_y > 0; lowest_occlusion_y--) {
                                if (((tileCullingBitset[plane][x][lowest_occlusion_y - 1]) & render_rule3) == 0) {
                                    break;
                                }
                            }

                            for (; highest_occlussion_y < landscapeSizeY; highest_occlussion_y++) {
                                if (((tileCullingBitset[plane][x][highest_occlussion_y + 1]) & render_rule3) == 0) {
                                    break;
                                }
                            }

                            find_lowest_occlussion_x:
                            for (; lowest_occlusion_x > 0; lowest_occlusion_x--) {
                                for (int occluded_y = lowest_occlusion_y; occluded_y <= highest_occlussion_y; occluded_y++) {
                                    if (((tileCullingBitset[plane][lowest_occlusion_x - 1][occluded_y]) & render_rule3) == 0) {
                                        break find_lowest_occlussion_x;
                                    }
                                }
                            }

                            find_highest_occlussion_x:
                            for (; highest_occlusion_x < landscapeSizeX; highest_occlusion_x++) {
                                for (int occluded_y = lowest_occlusion_y; occluded_y <= highest_occlussion_y; occluded_y++) {
                                    if (((tileCullingBitset[plane][highest_occlusion_x + 1][occluded_y]) & render_rule3) == 0) {
                                        break find_highest_occlussion_x;
                                    }
                                }
                            }

                            if ((highest_occlusion_x - lowest_occlusion_x + 1) * (highest_occlussion_y - lowest_occlusion_y + 1) >= 4) {
                                int lowest_occlussion_vertex_height = heightMap[plane][lowest_occlusion_x][lowest_occlusion_y];
                                Landscape.createCullingBox(k, lowest_occlusion_x * 128, highest_occlusion_x * 128 + 128, lowest_occlusion_y * 128, highest_occlussion_y * 128 + 128, lowest_occlussion_vertex_height, lowest_occlussion_vertex_height, 4);

                                for (int occluded_x = lowest_occlusion_x; occluded_x <= highest_occlusion_x; occluded_x++) {
                                    for (int occluded_y = lowest_occlusion_y; occluded_y <= highest_occlussion_y; occluded_y++) {
                                        tileCullingBitset[plane][occluded_x][occluded_y] &= ~render_rule3;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void fitEdges(int chunk_x, int chunk_y, int size_x, int size_y) {
        for (int y = chunk_y; y <= chunk_y + size_y; y++) {
            for (int x = chunk_x; x <= chunk_x + size_x; x++) {
                if (x >= 0 && x < landscapeSizeX && y >= 0 && y < landscapeSizeY) {
                    shadowMap[0][x][y] = (byte) 127;

                    if (x == chunk_x && x > 0) {
                        heightMap[0][x][y] = heightMap[0][x - 1][y];
                    }

                    if (x == chunk_x + size_x && x < landscapeSizeX - 1) {
                        heightMap[0][x][y] = heightMap[0][x + 1][y];
                    }

                    if (y == chunk_y && y > 0) {
                        heightMap[0][x][y] = heightMap[0][x][y - 1];
                    }

                    if (y == chunk_y + size_y && y < landscapeSizeY - 1) {
                        heightMap[0][x][y] = heightMap[0][x][y + 1];
                    }
                }
            }
        }
    }

    public int getRgb(int hsl, int brightness) {
        if (hsl == -2) {
            return 12345678;
        }

        if (hsl == -1) {
            if (brightness < 0) {
                brightness = 0;
            } else if (brightness > 127) {
                brightness = 127;
            }
            brightness = 127 - brightness;
            return brightness;
        }

        brightness = brightness * (hsl & 0x7f) / 128;

        if (brightness < 2) {
            brightness = 2;
        } else if (brightness > 126) {
            brightness = 126;
        }

        return (hsl & 0xff80) + brightness;
    }

    public void loadChunk(CollisionMap[] collision, int map_x, int map_y, int chunk_x, int chunk_y, int chunk_plane, byte[] chunk_payload, int chunk_rotation, int plane) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (map_x + x > 0 && map_x + x < 103 && map_y + y > 0 && map_y + y < 103) {
                    collision[plane].flags[map_x + x][(map_y + y)] &= ~0x1000000;
                }
            }
        }

        Buffer s = new Buffer(chunk_payload);

        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    if (z == chunk_plane && x >= chunk_x && x < chunk_x + 8 && y >= chunk_y && y < chunk_y + 8) {
                        loadLand(s, map_x + ChunkUtils.rotateLandX(x, y, chunk_rotation), map_y + ChunkUtils.rotateLandY(x, y, chunk_rotation), 0, 0, plane, chunk_rotation);
                    } else {
                        loadLand(s, -1, -1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void loadLand(Buffer s, int x, int y, int tile_x, int tile_y, int plane, int chunk_type) {
        if (x >= 0 && x < 104 && y >= 0 && y < 104) {
            renderFlags[plane][x][y] = (byte) 0;

            for (; ; ) {
                int opcode = s.readUnsignedByte();

                if (opcode == 0) {
                    if (plane == 0) {
                        heightMap[0][x][y] = (short) (-getNoiseHeight(932731 + x + tile_x, 556238 + y + tile_y) * 8);
                    } else {
                        heightMap[plane][x][y] = (short) (heightMap[plane - 1][x][y] - 240);
                        break;
                    }
                    break;
                }

                if (opcode == 1) {
                    int height = s.readUnsignedByte();

                    if (height == 1) {
                        height = 0;
                    }

                    if (plane == 0) {
                        heightMap[0][x][y] = (short) (-height * 8);
                    } else {
                        heightMap[plane][x][y] = (short) (heightMap[plane - 1][x][y] - height * 8);
                        break;
                    }
                    break;
                }

                if (opcode <= 49) {
                    overlayFloIndex[plane][x][y] = s.readByte();
                    overlayShape[plane][x][y] = (byte) ((opcode - 2) / 4);
                    overlayRotation[plane][x][y] = (byte) (opcode - 2 + chunk_type & 0x3);
                } else if (opcode <= 81) {
                    renderFlags[plane][x][y] = (byte) (opcode - 49);
                } else {
                    underlayFloIndex[plane][x][y] = (byte) (opcode - 81);
                }
            }
        } else {
            for (; ; ) {
                int i = s.readUnsignedByte();

                if (i == 0) {
                    break;
                }

                if (i == 1) {
                    s.readUnsignedByte();
                    break;
                }

                if (i <= 49) {
                    s.readUnsignedByte();
                }
            }
        }
    }

    public void loadLand(CollisionMap[] collision, byte[] payload, int chunk_x, int chunk_y, int tile_x, int tile_y) {
        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    if (chunk_x + x > 0 && chunk_x + x < 103 && chunk_y + y > 0 && chunk_y + y < 103) {
                        collision[plane].flags[chunk_x + x][chunk_y + y] &= ~0x1000000;
                    }
                }
            }
        }

        Buffer s = new Buffer(payload);

        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    loadLand(s, x + chunk_x, y + chunk_y, tile_x, tile_y, plane, 0);
                }
            }
        }
    }

    public void loadLocs(CollisionMap[] collision_maps, Landscape landscape, int map_x, int map_y, int chunk_x, int chunk_y, int chunk_plane, int map_plane, byte[] payload, int chunk_rotation) {
        Buffer s = new Buffer(payload);
        int index = -1;
        for (; ; ) {
            int msb = s.writeUnsignedSmart();

            if (msb == 0) {
                break;
            }

            index += msb;
            int uid = 0;

            for (; ; ) {
                int lsb = s.writeUnsignedSmart();

                if (lsb == 0) {
                    break;
                }

                uid += lsb - 1;
                int locY = uid & 0x3f;
                int locX = uid >> 6 & 0x3f;
                int locPlane = uid >> 12;
                int locArrangement = s.readUnsignedByte();
                int locType = locArrangement >> 2;
                int locRotation = locArrangement & 0x3;

                if (locPlane == chunk_plane && locX >= chunk_x && locX < chunk_x + 8 && locY >= chunk_y && locY < chunk_y + 8) {
                    LocConfig config = LocConfig.get(index);

                    int local_x = map_x + ChunkUtils.rotateLocX(locX, locY, config.sizeX, config.sizeY, chunk_rotation);
                    int local_y = map_y + ChunkUtils.rotateLocY(locX, locY, config.sizeX, config.sizeY, chunk_rotation);

                    if (local_x > 0 && local_y > 0 && local_x < 103 && local_y < 103) {
                        int plane = locPlane;

                        if ((renderFlags[1][local_x][local_y] & 0x2) == 2) {
                            plane--;
                        }

                        CollisionMap collision_map = null;

                        if (plane >= 0) {
                            collision_map = collision_maps[plane];
                        }

                        addLoc(landscape, collision_map, index, locType, local_x, local_y, map_plane, locRotation + chunk_rotation & 0x3);
                    }
                }
            }
        }
    }

    public void loadLocs(int region_x, CollisionMap[] collision_maps, int region_y, Landscape landscape, byte[] payload) {
        Buffer s = new Buffer(payload);
        int loc_index = -1;

        for (; ; ) {
            int xtra = s.writeUnsignedSmart();

            if (xtra == 0) {
                break;
            }

            loc_index += xtra;
            int region_coord = 0;

            for (; ; ) {
                int coord_bits = s.writeUnsignedSmart();

                if (coord_bits == 0) {
                    break;
                }

                region_coord += coord_bits - 1;
                int base_y = region_coord & 0x3f;
                int base_x = region_coord >> 6 & 0x3f;
                int loc_plane = region_coord >> 12;
                int loc_arrangement = s.readUnsignedByte();
                int loc_type = loc_arrangement >> 2;
                int loc_rotation = loc_arrangement & 0x3;
                int loc_x = base_x + region_x;
                int loc_y = base_y + region_y;

                if (loc_x > 0 && loc_y > 0 && loc_x < 103 && loc_y < 103) {
                    int plane = loc_plane;

                    if ((renderFlags[1][loc_x][loc_y] & 0x2) == 2) {
                        plane--;
                    }

                    CollisionMap collision = null;

                    if (plane >= 0) {
                        collision = collision_maps[plane];
                    }

                    addLoc(landscape, collision, loc_index, loc_type, loc_x, loc_y, loc_plane, loc_rotation);
                }
            }
        }
    }

    public int setVisibilityPlane(int plane, int x, int y) {
        if ((renderFlags[plane][x][y] & 0x8) != 0) {
            return 0;
        }
        if (plane > 0 && (renderFlags[1][x][y] & 0x2) != 0) {
            return plane - 1;
        }
        return plane;
    }
}
