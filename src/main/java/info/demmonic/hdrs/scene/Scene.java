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
import info.demmonic.hdrs.util.ColorUtils;
import info.demmonic.hdrs.util.MathUtils;

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

    public static void addLoc(Landscape l, int x, int y, int plane, int locIndex, int locType, int locRotation, int vertexPlane, CollisionMap cm, short[][][] heightMap) {
        int heightSouthWest = heightMap[vertexPlane][x][y];
        int heightSouthEast = heightMap[vertexPlane][x + 1][y];
        int heightNorthEast = heightMap[vertexPlane][x + 1][y + 1];
        int heightNorthWest = heightMap[vertexPlane][x][y + 1];
        int averageHeight = (heightSouthWest + heightSouthEast + heightNorthEast + heightNorthWest) >> 2;

        LocConfig c = LocConfig.get(locIndex);
        int uid = 0x40000000 | (locIndex << 14) | (y << 7) | x;

        if (!c.isStatic) {
            uid += Integer.MIN_VALUE;
        }

        byte arrangement = (byte) ((locRotation << 6) + locType);

        if (locType == 22) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(22, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, 22, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addGroundDecoration(x, y, averageHeight, plane, r, uid, arrangement);

            if (c.hasCollisions && c.isStatic) {
                cm.setSolid(x, y);
            }
        } else if (locType == 10 || locType == 11) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(10, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, 10, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            if (r != null) {
                int rotation = 0;

                if (locType == 11) {
                    rotation += 512;
                }

                int sizeY;
                int sizeX;

                if (locRotation == 1 || locRotation == 3) {
                    sizeY = c.sizeY;
                    sizeX = c.sizeX;
                } else {
                    sizeY = c.sizeX;
                    sizeX = c.sizeY;
                }

                l.add(r, x, y, plane, sizeX, sizeY, averageHeight, arrangement, rotation, uid);
            }

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, locRotation, c.blocksProjectiles);
            }
        } else if (locType >= 12) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(locType, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, locType, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.add(r, x, y, plane, 1, 1, averageHeight, arrangement, 0, uid);

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, locRotation, c.blocksProjectiles);
            }
        } else if (locType == 0) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(0, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, 0, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_ROOT_DRAW_FLAGS[locRotation], 0, arrangement, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, locRotation, c.blocksProjectiles);
            }
        } else if (locType == 1) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(1, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, 1, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_EXT_DRAW_FLAGS[locRotation], 0, arrangement, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, locRotation, c.blocksProjectiles);
            }
        } else if (locType == 2) {
            int nextRotation = locRotation + 1 & 0x3;
            Renderable r1;
            Renderable r2;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r1 = c.getModel(2, 4 + locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                r2 = c.getModel(2, nextRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r1 = new Loc(locIndex, 4 + locRotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                r2 = new Loc(locIndex, nextRotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r1, r2, x, y, averageHeight, plane, WALL_ROOT_DRAW_FLAGS[locRotation], WALL_ROOT_DRAW_FLAGS[nextRotation], arrangement, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, locRotation, c.blocksProjectiles);
            }
        } else if (locType == 3) {
            Renderable r;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                r = c.getModel(3, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                r = new Loc(locIndex, locRotation, 3, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.addWall(r, null, x, y, averageHeight, plane, WALL_EXT_DRAW_FLAGS[locRotation], 0, arrangement, uid);

            if (c.hasCollisions) {
                cm.addWall(x, y, locType, locRotation, c.blocksProjectiles);
            }
        } else if (locType == 9) {
            Renderable node;

            if (c.seqIndex == -1 && c.overrideIndex == null) {
                node = c.getModel(locType, locRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
            } else {
                node = new Loc(locIndex, locRotation, locType, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
            }

            l.add(node, x, y, plane, 1, 1, averageHeight, arrangement, 0, uid);

            if (c.hasCollisions) {
                cm.addLoc(x, y, c.sizeX, c.sizeY, locRotation, c.blocksProjectiles);
            }
        } else {
            if (c.adjustToTerrain) {
                if (locRotation == 1) {
                    int heightSouthWest2 = heightNorthWest;
                    heightNorthWest = heightNorthEast;
                    heightNorthEast = heightSouthEast;
                    heightSouthEast = heightSouthWest;
                    heightSouthWest = heightSouthWest2;
                } else if (locRotation == 2) {
                    int heightSouthWest2 = heightNorthWest;
                    heightNorthWest = heightSouthEast;
                    heightSouthEast = heightSouthWest2;
                    heightSouthWest2 = heightNorthEast;
                    heightNorthEast = heightSouthWest;
                    heightSouthWest = heightSouthWest2;
                } else if (locRotation == 3) {
                    int heightNorthEast2 = heightNorthWest;
                    heightNorthWest = heightSouthWest;
                    heightSouthWest = heightSouthEast;
                    heightSouthEast = heightNorthEast;
                    heightNorthEast = heightNorthEast2;
                }
            }
            if (locType == 4) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, locRotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[locRotation], uid);
            } else if (locType == 5) {
                int width = 16;
                int wallUid = l.getWallUid(plane, x, y);

                if (wallUid > 0) {
                    width = LocConfig.get(wallUid >> 14 & 0x7fff).wallWidth;
                }

                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, ROT_X_DELTA[locRotation] * width, ROT_Y_DELTA[locRotation] * width, averageHeight, locRotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[locRotation], uid);
            } else if (locType == 6) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, locRotation, arrangement, 256, uid);
            } else if (locType == 7) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, locRotation, arrangement, 512, uid);
            } else if (locType == 8) {
                Renderable node;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                l.addWallDecoration(node, x, y, plane, 0, 0, averageHeight, locRotation, arrangement, 768, uid);
            }
        }
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
        int locIndex = -1;

        for (; ; ) {
            int i = s.writeUnsignedSmart();

            if (i == 0) {
                break;
            }

            locIndex += i;
            int xy = 0;
            boolean verifyNext = false;

            for (; ; ) {
                if (verifyNext) {
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
                    int locY = xy & 0x3f;
                    int locX = xy >> 6 & 0x3f;
                    int locType = s.readUnsignedByte() >> 2;
                    int x = locX + mapX;
                    int y = locY + mapY;

                    if (x > 0 && y > 0 && x < 103 && y < 103) {
                        LocConfig config = LocConfig.get(locIndex);

                        if (locType != 22 || !Game.lowDetail || config.isStatic || config.isDecoration) {
                            valid &= config.hasValidModel();
                            verifyNext = true;
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

    public void addLoc(Landscape landscape, CollisionMap collision, int locIndex, int type, int x, int y, int plane, int rotation) {
        if (!Game.lowDetail || (renderFlags[0][x][y] & 0x2) != 0 || ((renderFlags[plane][x][y] & 0x10) == 0 && setVisibilityPlane(plane, x, y) == planeAtBuild)) {
            if (plane < minPlane) {
                minPlane = plane;
            }

            int heightSouthWest = heightMap[plane][x][y];
            int heightSouthEast = heightMap[plane][x + 1][y];
            int heightNorthEast = heightMap[plane][x + 1][y + 1];
            int heightNorthWest = heightMap[plane][x][y + 1];
            int heightAverage = (heightSouthWest + heightSouthEast + heightNorthEast + heightNorthWest) >> 2;

            LocConfig c = LocConfig.get(locIndex);
            int uid = 0x40000000 | (locIndex << 14) | (y << 7) | x;

            if (!c.isStatic) {
                uid += Integer.MIN_VALUE;
            }

            byte arrangement = (byte) ((rotation << 6) + type);

            if (type == 22) {
                if (!Game.lowDetail || c.isStatic || c.isDecoration) {
                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(22, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        r = new Loc(locIndex, rotation, 22, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addGroundDecoration(x, y, heightAverage, plane, r, uid, arrangement);

                    if (c.hasCollisions && c.isStatic && collision != null) {
                        collision.setSolid(x, y);
                    }

                }
            } else if (type == 10 || type == 11) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(10, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, 10, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                if (r != null) {
                    int angle = 0;
                    int sizeY;
                    int sizeX;

                    if (type == 11) {
                        angle += 256;
                    }

                    if (rotation == 1 || rotation == 3) {
                        sizeY = c.sizeY;
                        sizeX = c.sizeX;
                    } else {
                        sizeY = c.sizeX;
                        sizeX = c.sizeY;
                    }

                    if (landscape.add(r, x, y, plane, sizeX, sizeY, heightAverage, arrangement, angle, uid) && c.castsShadow) {
                        Model mesh;

                        if (r instanceof Model) {
                            mesh = (Model) r;
                        } else {
                            mesh = c.getModel(10, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                        }

                        if (mesh != null) {
                            for (int xOff = 0; xOff <= sizeY; xOff++) {
                                for (int yOff = 0; yOff <= sizeX; yOff++) {
                                    int i = mesh.maxHorizon / 4;

                                    if (i > 30) {
                                        i = 30;
                                    }

                                    if (i > (shadowMap[plane][x + xOff][y + yOff])) {
                                        shadowMap[plane][x + xOff][y + yOff] = (byte) i;
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
                    r = c.getModel(type, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, type, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.add(r, x, y, plane, 1, 1, heightAverage, arrangement, 0, uid);

                if (type >= 12 && type <= 17 && type != 13 && plane > 0) {
                    tileCullingBitset[plane][x][y] |= 0x924;
                }

                if (c.hasCollisions && collision != null) {
                    collision.addLoc(x, y, c.sizeX, c.sizeY, rotation, c.blocksProjectiles);
                }

            } else if (type == 0) {
                Renderable r;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r = c.getModel(0, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, 0, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, heightAverage, plane, WALL_ROOT_DRAW_FLAGS[rotation], 0, arrangement, uid);

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
                    r = c.getModel(1, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, 1, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, heightAverage, plane, WALL_EXT_DRAW_FLAGS[rotation], 0, arrangement, uid);

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
                int nextRotation = rotation + 1 & 0x3;
                Renderable r1;
                Renderable r2;

                if (c.seqIndex == -1 && c.overrideIndex == null) {
                    r1 = c.getModel(2, 4 + rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    r2 = c.getModel(2, nextRotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r1 = new Loc(locIndex, 4 + rotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    r2 = new Loc(locIndex, nextRotation, 2, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.addWall(r1, r2, x, y, heightAverage, plane, WALL_ROOT_DRAW_FLAGS[rotation], WALL_ROOT_DRAW_FLAGS[nextRotation], arrangement, uid);

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
                    r = c.getModel(3, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, 3, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.addWall(r, null, x, y, heightAverage, plane, WALL_EXT_DRAW_FLAGS[rotation], 0, arrangement, uid);

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
                    r = c.getModel(type, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                } else {
                    r = new Loc(locIndex, rotation, type, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                }

                landscape.add(r, x, y, plane, 1, 1, heightAverage, arrangement, 0, uid);

                if (c.hasCollisions && collision != null) {
                    collision.addLoc(x, y, c.sizeX, c.sizeY, rotation, c.blocksProjectiles);
                }
            } else {
                if (c.adjustToTerrain) {
                    if (rotation == 1) {
                        int heightSouthWest2 = heightNorthWest;
                        heightNorthWest = heightNorthEast;
                        heightNorthEast = heightSouthEast;
                        heightSouthEast = heightSouthWest;
                        heightSouthWest = heightSouthWest2;
                    } else if (rotation == 2) {
                        int heightSouthWest2 = heightNorthWest;
                        heightNorthWest = heightSouthEast;
                        heightSouthEast = heightSouthWest2;
                        heightSouthWest2 = heightNorthEast;
                        heightNorthEast = heightSouthWest;
                        heightSouthWest = heightSouthWest2;
                    } else if (rotation == 3) {
                        int heightNorthEast2 = heightNorthWest;
                        heightNorthWest = heightSouthWest;
                        heightSouthWest = heightSouthEast;
                        heightSouthEast = heightNorthEast;
                        heightNorthEast = heightNorthEast2;
                    }
                }

                if (type == 4) {
                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        r = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(r, x, y, plane, 0, 0, heightAverage, rotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[rotation], uid);
                } else if (type == 5) {
                    int width = 16;
                    int wallUid = landscape.getWallUid(plane, x, y);

                    if (wallUid > 0) {
                        width = LocConfig.get(wallUid >> 14 & 0x7fff).wallWidth;
                    }

                    Renderable r;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        r = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        r = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(r, x, y, plane, ROT_X_DELTA[rotation] * width, ROT_Y_DELTA[rotation] * width, heightAverage, rotation * 512, arrangement, WALL_ROOT_DRAW_FLAGS[rotation], uid);
                } else if (type == 6) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, heightAverage, rotation, arrangement, 0x100, uid);
                } else if (type == 7) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, heightAverage, rotation, arrangement, 0x200, uid);
                } else if (type == 8) {
                    Renderable node;

                    if (c.seqIndex == -1 && c.overrideIndex == null) {
                        node = c.getModel(4, 0, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, -1);
                    } else {
                        node = new Loc(locIndex, 0, 4, heightSouthEast, heightNorthEast, heightSouthWest, heightNorthWest, c.seqIndex, true);
                    }

                    landscape.addWallDecoration(node, x, y, plane, 0, 0, heightAverage, rotation, arrangement, 0x300, uid);
                }
            }
        }
    }

    public void createLandMesh(CollisionMap[] cm, Landscape l) {
        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 104; x++) {
                for (int z = 0; z < 104; z++) {
                    if ((renderFlags[plane][x][z] & 0x1) == 1) {
                        int actualPlane = plane;

                        if ((renderFlags[1][x][z] & 0x2) == 2) {
                            actualPlane--;
                        }

                        if (actualPlane >= 0) {
                            cm[actualPlane].setSolid(x, z);
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
            int initialLightIntensity = 96;
            int specularFactor = 768;
            int lightX = -50;
            int lightZ = -10;
            int lightY = -50;
            int lightLength = (int) Math.sqrt((double) (lightX * lightX + lightZ * lightZ + lightY * lightY));
            int specularDistribution = specularFactor * lightLength >> 8;

            for (int y = 1; y < landscapeSizeY - 1; y++) {
                for (int x = 1; x < landscapeSizeX - 1; x++) {
                    int xHeightDiff = (heightMap[plane][x + 1][y] - heightMap[plane][x - 1][y]);
                    int yHeightDiff = (heightMap[plane][x][y + 1] - heightMap[plane][x][y - 1]);
                    int normalLength = (int) Math.sqrt((double) (xHeightDiff * xHeightDiff + 65536 + yHeightDiff * yHeightDiff));
                    int normalizedX = (xHeightDiff << 8) / normalLength;
                    int normalizedZ = 65536 / normalLength;
                    int normalizedY = (yHeightDiff << 8) / normalLength;
                    int intensity = initialLightIntensity + (lightX * normalizedX + lightZ * normalizedZ + lightY * normalizedY) / specularDistribution;
                    int weightedShadowIntensity = ((shadowIntensity[x - 1][y] >> 2) + (shadowIntensity[x + 1][y] >> 3) + (shadowIntensity[x][y - 1] >> 2) + (shadowIntensity[x][y + 1] >> 3) + (shadowIntensity[x][y] >> 1));
                    lightMap[x][y] = intensity - weightedShadowIntensity;
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
                    int xPositiveOffset = x + 5;
                    if (xPositiveOffset >= 0 && xPositiveOffset < landscapeSizeX) {
                        int floIndex = underlayFloIndex[plane][xPositiveOffset][y] & 0xFF;
                        if (floIndex > 0) {
                            Floor floor = Floor.instance[floIndex - 1];
                            blendedHue[y] += floor.hue;
                            blendedSatuartion[y] += floor.saturation;
                            blendedLightness[y] += floor.lightness;
                            blendedHueDivisor[y] += floor.hueDivisor;
                            blendedDirection[y]++;
                        }
                    }

                    int xNegativeOffset = x - 5;
                    if (xNegativeOffset >= 0 && xNegativeOffset < landscapeSizeX) {
                        int floIndex = underlayFloIndex[plane][xNegativeOffset][y] & 0xFF;
                        if (floIndex > 0) {
                            Floor f = Floor.instance[floIndex - 1];
                            blendedHue[y] -= f.hue;
                            blendedSatuartion[y] -= f.saturation;
                            blendedLightness[y] -= f.lightness;
                            blendedHueDivisor[y] -= f.hueDivisor;
                            blendedDirection[y]--;
                        }
                    }
                }

                if (x >= 1 && x < landscapeSizeX - 1) {
                    int blendedHue = 0;
                    int blendedSaturation = 0;
                    int blendedBrightness = 0;
                    int blendedHueDivisor = 0;
                    int blendedDirectionTracker = 0;

                    for (int y = -5; y < landscapeSizeY + 5; y++) {
                        int yPositiveOffset = y + 5;
                        if (yPositiveOffset >= 0 && yPositiveOffset < landscapeSizeY) {
                            blendedHue += this.blendedHue[yPositiveOffset];
                            blendedSaturation += this.blendedSatuartion[yPositiveOffset];
                            blendedBrightness += blendedLightness[yPositiveOffset];
                            blendedHueDivisor += this.blendedHueDivisor[yPositiveOffset];
                            blendedDirectionTracker += blendedDirection[yPositiveOffset];
                        }

                        int yNegativeOffset = y - 5;
                        if (yNegativeOffset >= 0 && yNegativeOffset < landscapeSizeY) {
                            blendedHue -= this.blendedHue[yNegativeOffset];
                            blendedSaturation -= this.blendedSatuartion[yNegativeOffset];
                            blendedBrightness -= blendedLightness[yNegativeOffset];
                            blendedHueDivisor -= this.blendedHueDivisor[yNegativeOffset];
                            blendedDirectionTracker -= blendedDirection[yNegativeOffset];
                        }

                        if (y >= 1 && y < landscapeSizeY - 1 && (!Game.lowDetail || (renderFlags[0][x][y] & 0x2) != 0 || ((renderFlags[plane][x][y] & 0x10) == 0 && (setVisibilityPlane(plane, x, y) == planeAtBuild)))) {
                            if (plane < minPlane) {
                                minPlane = plane;
                            }

                            int underlayId = underlayFloIndex[plane][x][y] & 0xFF;
                            int overlayId = overlayFloIndex[plane][x][y] & 0xFF;

                            if (underlayId > 0 || overlayId > 0) {
                                short heightSouthWest = heightMap[plane][x][y];
                                short heightSouthEast = heightMap[plane][x + 1][y];
                                short heightNorthEast = heightMap[plane][x + 1][y + 1];
                                short heightNorthWest = heightMap[plane][x][y + 1];
                                int lightSouthWest = lightMap[x][y];
                                int lightSouthEast = lightMap[x + 1][y];
                                int lightNorthEast = lightMap[x + 1][y + 1];
                                int lightNorthWest = lightMap[x][y + 1];
                                int hsl = -1;
                                int hslRandomized = -1;

                                if (underlayId > 0) {
                                    int hue = blendedHue * 256 / blendedHueDivisor;
                                    int saturation = blendedSaturation / blendedDirectionTracker;
                                    int brightness = blendedBrightness / blendedDirectionTracker;
                                    hsl = ColorUtils.trimHsl(hue, saturation, brightness);
                                    hue = hue + hueRandomizer & 0xFF;
                                    brightness += lightnessRandomizer;

                                    if (brightness < 0) {
                                        brightness = 0;
                                    } else if (brightness > 255) {
                                        brightness = 255;
                                    }

                                    hslRandomized = ColorUtils.trimHsl(hue, saturation, brightness);
                                }

                                if (plane > 0) {
                                    boolean hideUnderlay = true;

                                    if (underlayId == 0 && overlayShape[plane][x][y] != 0) {
                                        hideUnderlay = false;
                                    }

                                    if (overlayId > 0 && !Floor.instance[overlayId - 1].showUnderlay) {
                                        hideUnderlay = false;
                                    }

                                    if (hideUnderlay && heightSouthWest == heightSouthEast && heightSouthWest == heightNorthEast && heightSouthWest == heightNorthWest) {
                                        tileCullingBitset[plane][x][y] |= 0x924;
                                    }
                                }

                                int rgbRandomized = 0;

                                if (hsl != -1) {
                                    rgbRandomized = Canvas3D.palette[ColorUtils.setHslLightnessWithTransparency(hslRandomized, 96)];
                                }

                                if (overlayId == 0) {
                                    l.addTile(plane, x, y, 0, 0, (byte) -1, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, ColorUtils.setHslLightnessWithTransparency(hsl, lightSouthWest), ColorUtils.setHslLightnessWithTransparency(hsl, lightSouthEast), ColorUtils.setHslLightnessWithTransparency(hsl, lightNorthEast), ColorUtils.setHslLightnessWithTransparency(hsl, lightNorthWest), 0, 0, 0, 0, rgbRandomized, 0);
                                } else {
                                    int shape = overlayShape[plane][x][y] + 1;
                                    byte rotation = overlayRotation[plane][x][y];
                                    Floor f = Floor.instance[overlayId - 1];
                                    byte texture = f.textureIndex;
                                    int hslBitset;
                                    int rgbBitset;

                                    if (texture >= 0) {
                                        hslBitset = Canvas3D.getAverageTextureRgb(texture);
                                        rgbBitset = -1;
                                    } else if (f.color2 == 0xFF00FF) {
                                        hslBitset = 0;
                                        rgbBitset = -2;
                                        texture = -1;
                                    } else {
                                        rgbBitset = ColorUtils.trimHsl(f.hue2, f.saturation, f.lightness);
                                        hslBitset = Canvas3D.palette[ColorUtils.getRgb(f.color, 96)];
                                    }

                                    l.addTile(plane, x, y, shape, rotation, texture, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, ColorUtils.setHslLightnessWithTransparency(hsl, lightSouthWest), ColorUtils.setHslLightnessWithTransparency(hsl, lightSouthEast), ColorUtils.setHslLightnessWithTransparency(hsl, lightNorthEast), ColorUtils.setHslLightnessWithTransparency(hsl, lightNorthWest), ColorUtils.getRgb(rgbBitset, lightSouthWest), ColorUtils.getRgb(rgbBitset, lightSouthEast), ColorUtils.getRgb(rgbBitset, lightNorthEast), ColorUtils.getRgb(rgbBitset, lightNorthWest), rgbRandomized, hslBitset);
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

        int renderRule1 = 1;
        int renderRule2 = 2;
        int renderRule3 = 4;

        for (int k = 0; k < 4; k++) {
            if (k > 0) {
                renderRule1 <<= 3;
                renderRule2 <<= 3;
                renderRule3 <<= 3;
            }

            for (int plane = 0; plane <= k; plane++) {
                for (int y = 0; y <= landscapeSizeY; y++) {
                    for (int x = 0; x <= landscapeSizeX; x++) {
                        if ((tileCullingBitset[plane][x][y] & renderRule1) != 0) {
                            int minY = y;
                            int maxY = y;
                            int minPlane = plane;
                            int maxPlane = plane;

                            for (; minY > 0; minY--) {
                                if (((tileCullingBitset[plane][x][minY - 1]) & renderRule1) == 0) {
                                    break;
                                }
                            }

                            for (; maxY < landscapeSizeY; maxY++) {
                                if (((tileCullingBitset[plane][x][maxY + 1]) & renderRule1) == 0) {
                                    break;
                                }
                            }

                            findMinPlane:
                            for (; minPlane > 0; minPlane--) {
                                for (int occludedY = minY; occludedY <= maxY; occludedY++) {
                                    if (((tileCullingBitset[minPlane - 1][x][occludedY]) & renderRule1) == 0) {
                                        break findMinPlane;
                                    }
                                }
                            }

                            findMaxPlane:
                            for (; maxPlane < k; maxPlane++) {
                                for (int occludedY = minY; occludedY <= maxY; occludedY++) {
                                    if (((tileCullingBitset[maxPlane + 1][x][occludedY]) & renderRule1) == 0) {
                                        break findMaxPlane;
                                    }
                                }
                            }

                            int surface = (maxPlane + 1 - minPlane) * (maxY - minY + 1);

                            if (surface >= 8) {
                                int i = 240;
                                int maxVZ = (heightMap[maxPlane][x][minY] - i);
                                int minVZ = heightMap[minPlane][x][minY];
                                Landscape.createCullingBox(k, x * 128, x * 128, minY * 128, maxY * 128 + 128, minVZ, maxVZ, 1);

                                for (int occludedPlane = minPlane; occludedPlane <= maxPlane; occludedPlane++) {
                                    for (int occludedY = minY; occludedY <= maxY; occludedY++) {
                                        tileCullingBitset[occludedPlane][x][occludedY] &= ~renderRule1;
                                    }
                                }
                            }
                        }

                        if ((tileCullingBitset[plane][x][y] & renderRule2) != 0) {
                            int minX = x;
                            int maxX = x;
                            int minPlane = plane;
                            int maxPlane = plane;

                            for (; minX > 0; minX--) {
                                if (((tileCullingBitset[plane][minX - 1][y]) & renderRule2) == 0) {
                                    break;
                                }
                            }

                            for (; maxX < landscapeSizeX; maxX++) {
                                if (((tileCullingBitset[plane][maxX + 1][y]) & renderRule2) == 0) {
                                    break;
                                }
                            }

                            findLowestPlane:
                            for (; minPlane > 0; minPlane--) {
                                for (int occludedX = minX; occludedX <= maxX; occludedX++) {
                                    if (((tileCullingBitset[minPlane - 1][occludedX][y]) & renderRule2) == 0) {
                                        break findLowestPlane;
                                    }
                                }
                            }

                            findHighestPlane:
                            for (; maxPlane < k; maxPlane++) {
                                for (int occludedX = minX; occludedX <= maxX; occludedX++) {
                                    if (((tileCullingBitset[maxPlane + 1][occludedX][y]) & renderRule2) == 0) {
                                        break findHighestPlane;
                                    }
                                }
                            }

                            int surface = (maxPlane + 1 - minPlane) * (maxX - minX + 1);
                            if (surface >= 8) {
                                int i = 240;
                                int maxVZ = (heightMap[maxPlane][minX][y] - i);
                                int minVZ = heightMap[minPlane][minX][y];
                                Landscape.createCullingBox(k, minX * 128, maxX * 128 + 128, y * 128, y * 128, minVZ, maxVZ, 2);

                                for (int j = minPlane; j <= maxPlane; j++) {
                                    for (int occludedX = minX; occludedX <= maxX; occludedX++) {
                                        tileCullingBitset[j][occludedX][y] &= ~renderRule2;
                                    }
                                }
                            }
                        }

                        if ((tileCullingBitset[plane][x][y] & renderRule3) != 0) {
                            int lowestOcclusionX = x;
                            int highestOcclusionX = x;
                            int lowestOcclusionY = y;
                            int highestOcclussionY = y;

                            for (; lowestOcclusionY > 0; lowestOcclusionY--) {
                                if (((tileCullingBitset[plane][x][lowestOcclusionY - 1]) & renderRule3) == 0) {
                                    break;
                                }
                            }

                            for (; highestOcclussionY < landscapeSizeY; highestOcclussionY++) {
                                if (((tileCullingBitset[plane][x][highestOcclussionY + 1]) & renderRule3) == 0) {
                                    break;
                                }
                            }

                            findLowestOcclussionX:
                            for (; lowestOcclusionX > 0; lowestOcclusionX--) {
                                for (int occludedY = lowestOcclusionY; occludedY <= highestOcclussionY; occludedY++) {
                                    if (((tileCullingBitset[plane][lowestOcclusionX - 1][occludedY]) & renderRule3) == 0) {
                                        break findLowestOcclussionX;
                                    }
                                }
                            }

                            findHighestOcclussionX:
                            for (; highestOcclusionX < landscapeSizeX; highestOcclusionX++) {
                                for (int occludedY = lowestOcclusionY; occludedY <= highestOcclussionY; occludedY++) {
                                    if (((tileCullingBitset[plane][highestOcclusionX + 1][occludedY]) & renderRule3) == 0) {
                                        break findHighestOcclussionX;
                                    }
                                }
                            }

                            if ((highestOcclusionX - lowestOcclusionX + 1) * (highestOcclussionY - lowestOcclusionY + 1) >= 4) {
                                int lowestOcclussionVertexHeight = heightMap[plane][lowestOcclusionX][lowestOcclusionY];
                                Landscape.createCullingBox(k, lowestOcclusionX * 128, highestOcclusionX * 128 + 128, lowestOcclusionY * 128, highestOcclussionY * 128 + 128, lowestOcclussionVertexHeight, lowestOcclussionVertexHeight, 4);

                                for (int occludedX = lowestOcclusionX; occludedX <= highestOcclusionX; occludedX++) {
                                    for (int occludedY = lowestOcclusionY; occludedY <= highestOcclussionY; occludedY++) {
                                        tileCullingBitset[plane][occludedX][occludedY] &= ~renderRule3;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void fitEdges(int chunkX, int chunkY, int sizeX, int sizeY) {
        for (int y = chunkY; y <= chunkY + sizeY; y++) {
            for (int x = chunkX; x <= chunkX + sizeX; x++) {
                if (x >= 0 && x < landscapeSizeX && y >= 0 && y < landscapeSizeY) {
                    shadowMap[0][x][y] = (byte) 127;

                    if (x == chunkX && x > 0) {
                        heightMap[0][x][y] = heightMap[0][x - 1][y];
                    }

                    if (x == chunkX + sizeX && x < landscapeSizeX - 1) {
                        heightMap[0][x][y] = heightMap[0][x + 1][y];
                    }

                    if (y == chunkY && y > 0) {
                        heightMap[0][x][y] = heightMap[0][x][y - 1];
                    }

                    if (y == chunkY + sizeY && y < landscapeSizeY - 1) {
                        heightMap[0][x][y] = heightMap[0][x][y + 1];
                    }
                }
            }
        }
    }

    public void loadChunk(CollisionMap[] collision, int mapX, int mapY, int chunkX, int chunkY, int chunkPlane, byte[] chunkPayload, int chunkRotation, int plane) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (mapX + x > 0 && mapX + x < 103 && mapY + y > 0 && mapY + y < 103) {
                    collision[plane].flags[mapX + x][(mapY + y)] &= ~0x1000000;
                }
            }
        }

        Buffer s = new Buffer(chunkPayload);

        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    if (z == chunkPlane && x >= chunkX && x < chunkX + 8 && y >= chunkY && y < chunkY + 8) {
                        loadLand(s, mapX + ChunkUtils.rotateLandX(x, y, chunkRotation), mapY + ChunkUtils.rotateLandY(x, y, chunkRotation), 0, 0, plane, chunkRotation);
                    } else {
                        loadLand(s, -1, -1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void loadLand(Buffer s, int x, int y, int tileX, int tileY, int plane, int chunkType) {
        if (x >= 0 && x < 104 && y >= 0 && y < 104) {
            renderFlags[plane][x][y] = (byte) 0;

            for (; ; ) {
                int opcode = s.readUnsignedByte();

                if (opcode == 0) {
                    if (plane == 0) {
                        heightMap[0][x][y] = (short) (-MathUtils.getNoiseHeight(932731 + x + tileX, 556238 + y + tileY) * 8);
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
                    overlayRotation[plane][x][y] = (byte) (opcode - 2 + chunkType & 0x3);
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

    public void loadLand(CollisionMap[] collision, byte[] payload, int chunkX, int chunkY, int tileX, int tileY) {
        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    if (chunkX + x > 0 && chunkX + x < 103 && chunkY + y > 0 && chunkY + y < 103) {
                        collision[plane].flags[chunkX + x][chunkY + y] &= ~0x1000000;
                    }
                }
            }
        }

        Buffer s = new Buffer(payload);

        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    loadLand(s, x + chunkX, y + chunkY, tileX, tileY, plane, 0);
                }
            }
        }
    }

    public void loadLocs(CollisionMap[] collisionMaps, Landscape landscape, int mapX, int mapY, int chunkX, int chunkY, int chunkPlane, int mapPlane, byte[] payload, int chunkRotation) {
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

                if (locPlane == chunkPlane && locX >= chunkX && locX < chunkX + 8 && locY >= chunkY && locY < chunkY + 8) {
                    LocConfig config = LocConfig.get(index);

                    int localX = mapX + ChunkUtils.rotateLocX(locX, locY, config.sizeX, config.sizeY, chunkRotation);
                    int localY = mapY + ChunkUtils.rotateLocY(locX, locY, config.sizeX, config.sizeY, chunkRotation);

                    if (localX > 0 && localY > 0 && localX < 103 && localY < 103) {
                        int plane = locPlane;

                        if ((renderFlags[1][localX][localY] & 0x2) == 2) {
                            plane--;
                        }

                        CollisionMap collisionMap = null;

                        if (plane >= 0) {
                            collisionMap = collisionMaps[plane];
                        }

                        addLoc(landscape, collisionMap, index, locType, localX, localY, mapPlane, locRotation + chunkRotation & 0x3);
                    }
                }
            }
        }
    }

    public void loadLocs(int regionX, CollisionMap[] collisionMaps, int regionY, Landscape landscape, byte[] payload) {
        Buffer s = new Buffer(payload);
        int locIndex = -1;

        for (; ; ) {
            int xtra = s.writeUnsignedSmart();

            if (xtra == 0) {
                break;
            }

            locIndex += xtra;
            int regionCoord = 0;

            for (; ; ) {
                int coordBits = s.writeUnsignedSmart();

                if (coordBits == 0) {
                    break;
                }

                regionCoord += coordBits - 1;
                int baseY = regionCoord & 0x3f;
                int baseX = regionCoord >> 6 & 0x3f;
                int locPlane = regionCoord >> 12;
                int locArrangement = s.readUnsignedByte();
                int locType = locArrangement >> 2;
                int locRotation = locArrangement & 0x3;
                int locX = baseX + regionX;
                int locY = baseY + regionY;

                if (locX > 0 && locY > 0 && locX < 103 && locY < 103) {
                    int plane = locPlane;

                    if ((renderFlags[1][locX][locY] & 0x2) == 2) {
                        plane--;
                    }

                    CollisionMap collision = null;

                    if (plane >= 0) {
                        collision = collisionMaps[plane];
                    }

                    addLoc(landscape, collision, locIndex, locType, locX, locY, locPlane, locRotation);
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
