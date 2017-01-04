package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.cache.impl.SequenceFrame;
import info.demmonic.hdrs.cache.impl.SkinList;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.io.OnDemand;
import info.demmonic.hdrs.media.Canvas2D;
import info.demmonic.hdrs.media.Canvas3D;
import info.demmonic.hdrs.node.impl.Renderable;
import info.demmonic.hdrs.util.MathUtils;

public class Model extends Renderable {

    public static int anInt1681;
    public static int anInt1682;
    public static int anInt1683;
    public static short[] replaceVertexX = new short[2000];
    public static short[] replaceVertexY = new short[2000];
    public static short[] replaceVertexZ = new short[2000];
    public static int[] anIntArray1625 = new int[2000];
    public static int[] anIntArray1671 = new int[1500];
    public static int[] anIntArray1673 = new int[12];
    public static int[] anIntArray1675 = new int[2000];
    public static int[] anIntArray1676 = new int[2000];
    public static int[] anIntArray1677 = new int[12];
    public static int[][] anIntArrayArray1672 = new int[1500][512];
    public static int[][] anIntArrayArray1674 = new int[12][2000];
    public static Header[] header;
    public static OnDemand ondemand;
    public static boolean sceneClickable;
    public static int mouseX;
    public static int mouseY;
    public static int hoveredCount;
    public static int[] hoveredUid = new int[1000];
    public static Model temporary = new Model();
    public static int[] tmpScreenX = new int[10];
    public static int[] tmpScreenY = new int[10];
    public static int[] tmpHsl = new int[10];
    public static int[] tmpTexturedX = new int[4096];
    public static int[] tmpTexturedY = new int[4096];
    public static int[] tmpTexturedZ = new int[4096];
    public static int[] triangleX = new int[4096];
    public static int[] triangleY = new int[4096];
    public static int[] triangleDepth = new int[4096];
    public static boolean[] triangleProject = new boolean[4096];
    public static boolean[] triangleCheckBounds = new boolean[4096];
    public static int[] palette;
    public static int[] shadowDecay;

    static {
        palette = Canvas3D.palette;
        shadowDecay = Canvas3D.shadowDecay;
    }

    public int anInt1641;
    public boolean isClickable;
    public int maxHorizon;
    public int maxX;
    public int maxY;
    public int maxZ;
    public int minX;
    public int minZ;
    public int pileHeight;
    public int[] textureMapX;
    public int[] textureMapY;
    public int[] textureMapZ;
    public int texturedTriangleCount;
    public int[] triHsl1;
    public int[] triHsl2;
    public int[] triHsl3;
    public int[] triangleAlpha;
    public int[] triangleColor;
    public int triangleCount;
    public int[][] triangleGroups;
    public int[] triangleInfo;
    public int[] trianglePriority;
    public int[] triangleTskin;
    public short[] triangleViewspaceA;
    public short[] triangleViewspaceB;
    public short[] triangleViewspaceC;
    public int unknown2;
    public int unknown3;
    public short vertexCount;
    public int[] vertexSkinTypes;
    public int[][] vertexWeights;
    public short[] vertexX;
    public short[] vertexY;
    public short[] vertexZ;
    public Vertex[] vertices;

    public Model() {
        isClickable = false;
    }

    public Model(boolean copyColors, boolean copyOpacity, boolean copyVertices, Model m) {
        isClickable = false;
        vertexCount = m.vertexCount;
        triangleCount = m.triangleCount;
        texturedTriangleCount = m.texturedTriangleCount;

        if (copyVertices) {
            vertexX = m.vertexX;
            vertexY = m.vertexY;
            vertexZ = m.vertexZ;
        } else {
            vertexX = new short[vertexCount];
            vertexY = new short[vertexCount];
            vertexZ = new short[vertexCount];

            for (int i = 0; i < vertexCount; i++) {
                vertexX[i] = m.vertexX[i];
                vertexY[i] = m.vertexY[i];
                vertexZ[i] = m.vertexZ[i];
            }
        }

        if (copyColors) {
            triangleColor = m.triangleColor;
        } else {
            triangleColor = new int[triangleCount];

            for (int i = 0; i < triangleCount; i++) {
                triangleColor[i] = m.triangleColor[i];
            }
        }

        if (copyOpacity) {
            triangleAlpha = m.triangleAlpha;
        } else {
            triangleAlpha = new int[triangleCount];

            if (m.triangleAlpha == null) {
                for (int i = 0; i < triangleCount; i++) {
                    triangleAlpha[i] = 0;
                }
            } else {
                for (int i = 0; i < triangleCount; i++) {
                    triangleAlpha[i] = m.triangleAlpha[i];
                }
            }
        }

        vertexSkinTypes = m.vertexSkinTypes;
        triangleTskin = m.triangleTskin;
        triangleInfo = m.triangleInfo;
        triangleViewspaceA = m.triangleViewspaceA;
        triangleViewspaceB = m.triangleViewspaceB;
        triangleViewspaceC = m.triangleViewspaceC;
        trianglePriority = m.trianglePriority;
        anInt1641 = m.anInt1641;
        textureMapX = m.textureMapX;
        textureMapY = m.textureMapY;
        textureMapZ = m.textureMapZ;
    }

    public Model(boolean copyYVertices, boolean copyShading, Model m) {
        isClickable = false;

        vertexCount = m.vertexCount;
        triangleCount = m.triangleCount;
        texturedTriangleCount = m.texturedTriangleCount;

        if (copyYVertices) {
            vertexY = new short[vertexCount];
            for (int j = 0; j < vertexCount; j++) {
                vertexY[j] = m.vertexY[j];
            }
        } else {
            vertexY = m.vertexY;
        }

        if (copyShading) {
            triHsl1 = new int[triangleCount];
            triHsl2 = new int[triangleCount];
            triHsl3 = new int[triangleCount];

            for (int i = 0; i < triangleCount; i++) {
                triHsl1[i] = m.triHsl1[i];
                triHsl2[i] = m.triHsl2[i];
                triHsl3[i] = m.triHsl3[i];
            }

            triangleInfo = new int[triangleCount];

            if (m.triangleInfo == null) {
                for (int i = 0; i < triangleCount; i++) {
                    triangleInfo[i] = 0;
                }
            } else {
                for (int i = 0; i < triangleCount; i++) {
                    triangleInfo[i] = m.triangleInfo[i];
                }
            }

            super.normal = new Vertex[vertexCount];

            for (int i = 0; i < vertexCount; i++) {
                Vertex v1 = super.normal[i] = new Vertex();
                Vertex v2 = m.normal[i];
                v1.x = v2.x;
                v1.y = v2.y;
                v1.z = v2.z;
                v1.w = v2.w;
            }

            vertices = m.vertices;
        } else {
            triHsl1 = m.triHsl1;
            triHsl2 = m.triHsl2;
            triHsl3 = m.triHsl3;
            triangleInfo = m.triangleInfo;
        }

        vertexX = m.vertexX;
        vertexZ = m.vertexZ;
        triangleColor = m.triangleColor;
        triangleAlpha = m.triangleAlpha;
        trianglePriority = m.trianglePriority;
        anInt1641 = m.anInt1641;
        triangleViewspaceA = m.triangleViewspaceA;
        triangleViewspaceB = m.triangleViewspaceB;
        triangleViewspaceC = m.triangleViewspaceC;
        textureMapX = m.textureMapX;
        textureMapY = m.textureMapY;
        textureMapZ = m.textureMapZ;
        super.height = ((Renderable) (m)).height;
        maxY = m.maxY;
        maxHorizon = m.maxHorizon;
        unknown2 = m.unknown2;
        unknown3 = m.unknown3;
        minX = m.minX;
        maxZ = m.maxZ;
        minZ = m.minZ;
        maxX = m.maxX;
    }

    public Model(int index) {
        isClickable = false;
        Header config = header[index];

        vertexCount = config.vertexCount;
        triangleCount = config.triangleCount;
        texturedTriangleCount = config.texturedTriangleCount;

        vertexX = new short[vertexCount];
        vertexY = new short[vertexCount];
        vertexZ = new short[vertexCount];

        triangleViewspaceA = new short[triangleCount];
        triangleViewspaceB = new short[triangleCount];
        triangleViewspaceC = new short[triangleCount];

        textureMapX = new int[texturedTriangleCount];
        textureMapY = new int[texturedTriangleCount];
        textureMapZ = new int[texturedTriangleCount];

        if (config.anInt376 >= 0) {
            vertexSkinTypes = new int[vertexCount];
        }

        if (config.drawTypeStreamPos >= 0) {
            triangleInfo = new int[triangleCount];
        }

        if (config.priorityStreamPos >= 0) {
            trianglePriority = new int[triangleCount];
        } else {
            anInt1641 = -config.priorityStreamPos - 1;
        }

        if (config.alphaStreamPos >= 0) {
            triangleAlpha = new int[triangleCount];
        }

        if (config.tskinStreamPos >= 0) {
            triangleTskin = new int[triangleCount];
        }

        triangleColor = new int[triangleCount];

        Buffer colorStream = new Buffer(config.payload);
        colorStream.position = config.anInt372;

        Buffer typeStream = new Buffer(config.payload);
        typeStream.position = config.texturedTriangleStreamPos;

        Buffer priorityStream = new Buffer(config.payload);
        priorityStream.position = config.anInt374;

        Buffer alphaStream = new Buffer(config.payload);
        alphaStream.position = config.anInt375;

        Buffer tskinStream = new Buffer(config.payload);
        tskinStream.position = config.anInt376;

        int x = 0;
        int y = 0;
        int z = 0;

        // VERTEX_DIRECTION_Chunk
        for (int v = 0; v < vertexCount; v++) {
            int flag = colorStream.readUnsignedByte();

            int offX = 0;
            int offY = 0;
            int offZ = 0;

            if ((flag & 1) != 0) {
                offX = typeStream.readSmart();
            }

            if ((flag & 2) != 0) {
                offY = priorityStream.readSmart();
            }

            if ((flag & 4) != 0) {
                offZ = alphaStream.readSmart();
            }

            vertexX[v] = (short) (x + offX);
            vertexY[v] = (short) (y + offY);
            vertexZ[v] = (short) (z + offZ);

            x = vertexX[v];
            y = vertexY[v];
            z = vertexZ[v];

            if (vertexSkinTypes != null) {
                vertexSkinTypes[v] = tskinStream.readUnsignedByte();
            }
        }

        colorStream.position = config.triangleColorStreamPos;
        typeStream.position = config.drawTypeStreamPos;
        priorityStream.position = config.priorityStreamPos;
        alphaStream.position = config.alphaStreamPos;
        tskinStream.position = config.tskinStreamPos;

        for (int i = 0; i < triangleCount; i++) {
            triangleColor[i] = colorStream.readUnsignedShort();

            if (triangleInfo != null) {
                triangleInfo[i] = typeStream.readUnsignedByte();
            }

            if (trianglePriority != null) {
                trianglePriority[i] = priorityStream.readUnsignedByte();
            }

            if (triangleAlpha != null) {
                triangleAlpha[i] = alphaStream.readUnsignedByte();
            }

            if (triangleTskin != null) {
                triangleTskin[i] = tskinStream.readUnsignedByte();
            }
        }

        colorStream.position = config.anInt377;
        typeStream.position = config.anInt378;

        x = 0;
        y = 0;
        z = 0;
        int last = 0;

        // TRIANGLE_TYPE_Chunk
        for (int i = 0; i < triangleCount; i++) {
            int type = typeStream.readUnsignedByte();

            if (type == 1) {
                x = colorStream.readSmart() + last;
                last = x;
                y = colorStream.readSmart() + last;
                last = y;
                z = colorStream.readSmart() + last;
                last = z;
                triangleViewspaceA[i] = (short) x;
                triangleViewspaceB[i] = (short) y;
                triangleViewspaceC[i] = (short) z;
            }

            if (type == 2) {
                y = z;
                z = colorStream.readSmart() + last;
                last = z;
                triangleViewspaceA[i] = (short) x;
                triangleViewspaceB[i] = (short) y;
                triangleViewspaceC[i] = (short) z;
            }

            if (type == 3) {
                x = z;
                z = colorStream.readSmart() + last;
                last = z;
                triangleViewspaceA[i] = (short) x;
                triangleViewspaceB[i] = (short) y;
                triangleViewspaceC[i] = (short) z;
            }

            if (type == 4) {
                int k4 = x;
                x = y;
                y = k4;
                z = colorStream.readSmart() + last;
                last = z;
                triangleViewspaceA[i] = (short) x;
                triangleViewspaceB[i] = (short) y;
                triangleViewspaceC[i] = (short) z;
            }
        }

        colorStream.position = config.textureMapStreamPos;

        for (int i = 0; i < texturedTriangleCount; i++) {
            textureMapX[i] = colorStream.readUnsignedShort();
            textureMapY[i] = colorStream.readUnsignedShort();
            textureMapZ[i] = colorStream.readUnsignedShort();
        }

    }

    public Model(int modelCount, boolean flag, Model models[]) {
        isClickable = false;
        boolean hasInfo = false;
        boolean hasPriority = false;
        boolean hasAlpha = false;
        boolean hasColor = false;
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        anInt1641 = -1;

        for (int k = 0; k < modelCount; k++) {
            Model m = models[k];
            if (m != null) {
                vertexCount += m.vertexCount;
                triangleCount += m.triangleCount;
                texturedTriangleCount += m.texturedTriangleCount;
                hasInfo |= m.triangleInfo != null;

                if (m.trianglePriority != null) {
                    hasPriority = true;
                } else {
                    if (anInt1641 == -1) {
                        anInt1641 = m.anInt1641;
                    }
                    if (anInt1641 != m.anInt1641) {
                        hasPriority = true;
                    }
                }

                hasAlpha |= m.triangleAlpha != null;
                hasColor |= m.triangleColor != null;
            }
        }

        vertexX = new short[vertexCount];
        vertexY = new short[vertexCount];
        vertexZ = new short[vertexCount];

        triangleViewspaceA = new short[triangleCount];
        triangleViewspaceB = new short[triangleCount];
        triangleViewspaceC = new short[triangleCount];

        triHsl1 = new int[triangleCount];
        triHsl2 = new int[triangleCount];
        triHsl3 = new int[triangleCount];

        textureMapX = new int[texturedTriangleCount];
        textureMapY = new int[texturedTriangleCount];
        textureMapZ = new int[texturedTriangleCount];

        if (hasInfo) {
            triangleInfo = new int[triangleCount];
        }

        if (hasPriority) {
            trianglePriority = new int[triangleCount];
        }

        if (hasAlpha) {
            triangleAlpha = new int[triangleCount];
        }
        if (hasColor) {
            triangleColor = new int[triangleCount];
        }

        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;

        int i = 0;

        for (int j = 0; j < modelCount; j++) {
            Model m = models[j];

            if (m != null) {
                int vCount = vertexCount;

                for (int k = 0; k < m.vertexCount; k++) {
                    vertexX[vertexCount] = m.vertexX[k];
                    vertexY[vertexCount] = m.vertexY[k];
                    vertexZ[vertexCount] = m.vertexZ[k];
                    vertexCount++;
                }

                for (int k = 0; k < m.triangleCount; k++) {
                    triangleViewspaceA[triangleCount] = (short) (m.triangleViewspaceA[k] + vCount);
                    triangleViewspaceB[triangleCount] = (short) (m.triangleViewspaceB[k] + vCount);
                    triangleViewspaceC[triangleCount] = (short) (m.triangleViewspaceC[k] + vCount);

                    triHsl1[triangleCount] = m.triHsl1[k];
                    triHsl2[triangleCount] = m.triHsl2[k];
                    triHsl3[triangleCount] = m.triHsl3[k];

                    if (hasInfo) {
                        if (m.triangleInfo == null) {
                            triangleInfo[triangleCount] = 0;
                        } else {
                            int j2 = m.triangleInfo[k];
                            if ((j2 & 2) == 2) {
                                j2 += i << 2;
                            }
                            triangleInfo[triangleCount] = j2;
                        }
                    }

                    if (hasPriority) {
                        if (m.trianglePriority == null) {
                            trianglePriority[triangleCount] = m.anInt1641;
                        } else {
                            trianglePriority[triangleCount] = m.trianglePriority[k];
                        }
                    }

                    if (hasAlpha) {
                        if (m.triangleAlpha == null) {
                            triangleAlpha[triangleCount] = 0;
                        } else {
                            triangleAlpha[triangleCount] = m.triangleAlpha[k];
                        }
                    }

                    if (hasColor && m.triangleColor != null) {
                        triangleColor[triangleCount] = m.triangleColor[k];
                    }

                    triangleCount++;
                }

                for (int k = 0; k < m.texturedTriangleCount; k++) {
                    textureMapX[texturedTriangleCount] = m.textureMapX[k] + vCount;
                    textureMapY[texturedTriangleCount] = m.textureMapY[k] + vCount;
                    textureMapZ[texturedTriangleCount] = m.textureMapZ[k] + vCount;
                    texturedTriangleCount++;
                }

                i += m.texturedTriangleCount;
            }
        }

        method466();
    }

    public Model(int modelCount, Model models[]) {
        isClickable = false;
        boolean hasInfo = false;
        boolean hasPriorities = false;
        boolean hasAlpha = false;
        boolean hasTskins = false;
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        anInt1641 = -1;

        for (int i = 0; i < modelCount; i++) {
            Model m = models[i];
            if (m != null) {
                vertexCount += m.vertexCount;
                triangleCount += m.triangleCount;
                texturedTriangleCount += m.texturedTriangleCount;
                hasInfo |= m.triangleInfo != null;

                if (m.trianglePriority != null) {
                    hasPriorities = true;
                } else {
                    if (anInt1641 == -1) {
                        anInt1641 = m.anInt1641;
                    }
                    if (anInt1641 != m.anInt1641) {
                        hasPriorities = true;
                    }
                }

                hasAlpha |= m.triangleAlpha != null;
                hasTskins |= m.triangleTskin != null;
            }
        }

        vertexX = new short[vertexCount];
        vertexY = new short[vertexCount];
        vertexZ = new short[vertexCount];
        vertexSkinTypes = new int[vertexCount];

        triangleViewspaceA = new short[triangleCount];
        triangleViewspaceB = new short[triangleCount];
        triangleViewspaceC = new short[triangleCount];

        textureMapX = new int[texturedTriangleCount];
        textureMapY = new int[texturedTriangleCount];
        textureMapZ = new int[texturedTriangleCount];

        if (hasInfo) {
            triangleInfo = new int[triangleCount];
        }

        if (hasPriorities) {
            trianglePriority = new int[triangleCount];
        }

        if (hasAlpha) {
            triangleAlpha = new int[triangleCount];
        }

        if (hasTskins) {
            triangleTskin = new int[triangleCount];
        }

        triangleColor = new int[triangleCount];
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;

        int l = 0;
        for (int i = 0; i < modelCount; i++) {
            Model m = models[i];
            if (m != null) {
                for (int j = 0; j < m.triangleCount; j++) {
                    if (hasInfo) {
                        if (m.triangleInfo == null) {
                            triangleInfo[triangleCount] = 0;
                        } else {
                            int k1 = m.triangleInfo[j];
                            if ((k1 & 2) == 2) {
                                k1 += l << 2;
                            }
                            triangleInfo[triangleCount] = k1;
                        }
                    }

                    if (hasPriorities) {
                        if (m.trianglePriority == null) {
                            trianglePriority[triangleCount] = m.anInt1641;
                        } else {
                            trianglePriority[triangleCount] = m.trianglePriority[j];
                        }
                    }

                    if (hasAlpha) {
                        if (m.triangleAlpha == null) {
                            triangleAlpha[triangleCount] = 0;
                        } else {
                            triangleAlpha[triangleCount] = m.triangleAlpha[j];
                        }
                    }

                    if (hasTskins && m.triangleTskin != null) {
                        triangleTskin[triangleCount] = m.triangleTskin[j];
                    }

                    triangleColor[triangleCount] = m.triangleColor[j];
                    triangleViewspaceA[triangleCount] = method465(m, m.triangleViewspaceA[j]);
                    triangleViewspaceB[triangleCount] = method465(m, m.triangleViewspaceB[j]);
                    triangleViewspaceC[triangleCount] = method465(m, m.triangleViewspaceC[j]);
                    triangleCount++;
                }

                for (int j = 0; j < m.texturedTriangleCount; j++) {
                    textureMapX[texturedTriangleCount] = method465(m, m.textureMapX[j]);
                    textureMapY[texturedTriangleCount] = method465(m, m.textureMapY[j]);
                    textureMapZ[texturedTriangleCount] = method465(m, m.textureMapZ[j]);
                    texturedTriangleCount++;
                }

                l += m.texturedTriangleCount;
            }
        }

    }

    public static Model get(int i) {
        if (header == null) {
            return null;
        }
        Header h = header[i];

        if (h == null) {
            ondemand.requestModel(i);
            return null;
        }

        return new Model(i);
    }

    public static void init(int size, OnDemand ondemand) {
        Model.header = new Header[size];
        Model.ondemand = ondemand;
    }

    public static boolean isValid(int i) {
        if (header == null) {
            return false;
        }
        Header h = header[i];

        if (h == null) {
            ondemand.requestModel(i);
            return false;
        }

        return true;
    }

    public static void load(byte[] data, int index) {
        if (data == null) {
            Header h = header[index] = new Header();
            h.vertexCount = 0;
            h.triangleCount = 0;
            h.texturedTriangleCount = 0;
            return;
        }

        Buffer b = new Buffer(data);
        b.position = data.length - 18;

        Header h = header[index] = new Header();
        h.payload = data;

        h.vertexCount = (short) b.readUnsignedShort();
        h.triangleCount = b.readUnsignedShort();
        h.texturedTriangleCount = b.readUnsignedByte();

        int hasTextures = b.readUnsignedByte();
        int priority = b.readUnsignedByte();
        int hasAlpha = b.readUnsignedByte();
        int hasSkins = b.readUnsignedByte();
        int hasVertexSkins = b.readUnsignedByte();
        int xDataLength = b.readUnsignedShort();
        int yDataLength = b.readUnsignedShort();
        int zDataLength = b.readUnsignedShort();
        int triDataLength = b.readUnsignedShort();

        int position = 0;

        h.anInt372 = position;

        position += h.vertexCount;
        h.anInt378 = position;

        position += h.triangleCount;
        h.priorityStreamPos = position;

        if (priority == 255) {
            position += h.triangleCount;
        } else {
            h.priorityStreamPos = -priority - 1;
        }

        h.tskinStreamPos = position;

        if (hasSkins == 1) {
            position += h.triangleCount;
        } else {
            h.tskinStreamPos = -1;
        }

        h.drawTypeStreamPos = position;

        if (hasTextures == 1) {
            position += h.triangleCount;
        } else {
            h.drawTypeStreamPos = -1;
        }

        h.anInt376 = position;

        if (hasVertexSkins == 1) {
            position += h.vertexCount;
        } else {
            h.anInt376 = -1;
        }

        h.alphaStreamPos = position;

        if (hasAlpha == 1) {
            position += h.triangleCount;
        } else {
            h.alphaStreamPos = -1;
        }

        h.anInt377 = position;
        position += triDataLength;

        h.triangleColorStreamPos = position;
        position += h.triangleCount * 2;
        h.textureMapStreamPos = position;
        position += h.texturedTriangleCount * 6;
        h.texturedTriangleStreamPos = position;
        position += xDataLength;
        h.anInt374 = position;
        position += yDataLength;
        h.anInt375 = position;
        position += zDataLength;
    }

    public static void nullify() {
        header = null;
        triangleCheckBounds = null;
        triangleProject = null;
        triangleX = null;
        triangleY = null;
        triangleDepth = null;
        tmpTexturedX = null;
        tmpTexturedY = null;
        tmpTexturedZ = null;
        anIntArray1671 = null;
        anIntArrayArray1672 = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        palette = null;
        shadowDecay = null;
    }

    public static void nullify(int index) {
        header[index] = null;
    }

    public static int setHslLightness(int hsl, int lightness, int info) {
        if ((info & 2) == 2) {
            if (lightness < 0) {
                lightness = 0;
            } else if (lightness > 127) {
                lightness = 127;
            }

            lightness = 127 - lightness;
            return lightness;
        }

        lightness = lightness * (hsl & 0x7f) >> 7;

        if (lightness < 2) {
            lightness = 2;
        } else if (lightness > 126) {
            lightness = 126;
        }

        return (hsl & 0xff80) + lightness;
    }

    public void applyLighting(int lightBrightness, int specularDistribution, int lightX, int lightY, int lightZ) {
        for (int i = 0; i < triangleCount; i++) {
            int x = triangleViewspaceA[i];
            int y = triangleViewspaceB[i];
            int z = triangleViewspaceC[i];

            if (triangleInfo == null) {
                int hsl = triangleColor[i];
                Vertex v = super.normal[x];

                int lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl1[i] = setHslLightness(hsl, lightness, 0);

                v = super.normal[y];
                lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl2[i] = setHslLightness(hsl, lightness, 0);

                v = super.normal[z];
                lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl3[i] = setHslLightness(hsl, lightness, 0);
            } else if ((triangleInfo[i] & 1) == 0) {
                int hsl = triangleColor[i];
                int info = triangleInfo[i];

                Vertex v = super.normal[x];
                int lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl1[i] = setHslLightness(hsl, lightness, info);

                v = super.normal[y];
                lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl2[i] = setHslLightness(hsl, lightness, info);

                v = super.normal[z];
                lightness = lightBrightness + (lightX * v.x + lightY * v.y + lightZ * v.z) / (specularDistribution * v.w);
                triHsl3[i] = setHslLightness(hsl, lightness, info);
            }
        }

        super.normal = null;
        this.vertices = null;
        this.vertexSkinTypes = null;
        this.triangleTskin = null;

        if (this.triangleInfo != null) {
            for (int i = 0; i < this.triangleCount; i++) {
                if ((this.triangleInfo[i] & 2) == 2) {
                    return;
                }
            }
        }

        this.triangleColor = null;
    }

    public void applyLighting(int lightBrightness, int specularFactor, int lightX, int lightY, int lightZ, boolean smoothShading) {
        int lightLength = (int) Math.sqrt(lightX * lightX + lightY * lightY + lightZ * lightZ);
        int specularDistribution = specularFactor * lightLength >> 8;

        if (triHsl1 == null) {
            triHsl1 = new int[triangleCount];
            triHsl2 = new int[triangleCount];
            triHsl3 = new int[triangleCount];
        }

        if (super.normal == null) {
            super.normal = new Vertex[vertexCount];

            for (int i = 0; i < vertexCount; i++) {
                super.normal[i] = new Vertex();
            }
        }

        for (int i = 0; i < triangleCount; i++) {
            int xI = triangleViewspaceA[i];
            int yI = triangleViewspaceB[i];
            int zI = triangleViewspaceC[i];

            int j3 = vertexX[yI] - vertexX[xI];
            int k3 = vertexY[yI] - vertexY[xI];
            int l3 = vertexZ[yI] - vertexZ[xI];

            int i4 = vertexX[zI] - vertexX[xI];
            int j4 = vertexY[zI] - vertexY[xI];
            int k4 = vertexZ[zI] - vertexZ[xI];

            int x = k3 * k4 - j4 * l3;
            int y = l3 * i4 - k4 * j3;
            int z;

            for (z = j3 * j4 - i4 * k3; x > 8192 || y > 8192 || z > 8192 || x < -8192 || y < -8192 || z < -8192; z >>= 1) {
                x >>= 1;
                y >>= 1;
            }

            int length = (int) Math.sqrt(x * x + y * y + z * z);

            if (length <= 0) {
                length = 1;
            }

            x = (x * 256) / length;
            y = (y * 256) / length;
            z = (z * 256) / length;

            if (triangleInfo == null || (triangleInfo[i] & 1) == 0) {
                Vertex v = super.normal[xI];
                v.x += x;
                v.y += y;
                v.z += z;
                v.w++;
                v = super.normal[yI];
                v.x += x;
                v.y += y;
                v.z += z;
                v.w++;
                v = super.normal[zI];
                v.x += x;
                v.y += y;
                v.z += z;
                v.w++;
            } else {
                triHsl1[i] = setHslLightness(triangleColor[i], lightBrightness + (lightX * x + lightY * y + lightZ * z) / (specularDistribution + specularDistribution / 2), triangleInfo[i]);
            }
        }

        if (smoothShading) {
            applyLighting(lightBrightness, specularDistribution, lightX, lightY, lightZ);
        } else {
            vertices = new Vertex[vertexCount];

            for (int i = 0; i < vertexCount; i++) {
                Vertex v = super.normal[i];
                Vertex nV = vertices[i] = new Vertex();
                nV.x = v.x;
                nV.y = v.y;
                nV.z = v.z;
                nV.w = v.w;
            }

        }
        if (smoothShading) {
            method466();
            return;
        } else {
            method468();
            return;
        }
    }

    public void applySequenceFrame(int seqIndex) {
        if (vertexWeights == null) {
            return;
        }

        if (seqIndex == -1) {
            return;
        }

        SequenceFrame s = SequenceFrame.get(seqIndex);

        if (s == null) {
            return;
        }

        SkinList skin = s.skinlist;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;

        for (int frame = 0; frame < s.frameCount; frame++) {
            int vIndex = s.vertices[frame];
            this.transform(skin.opcodes[vIndex], skin.vertices[vIndex], s.vertexX[frame], s.vertexY[frame], s.vertexZ[frame]);
        }
    }

    public void applySequenceFrames(int[] vertices, int frame1, int frame2) {
        if (frame1 == -1) {
            return;
        }

        if (vertices == null || frame2 == -1) {
            applySequenceFrame(frame1);
            return;
        }

        SequenceFrame af1 = SequenceFrame.get(frame1);

        if (af1 == null) {
            return;
        }

        SequenceFrame af2 = SequenceFrame.get(frame2);

        if (af2 == null) {
            applySequenceFrame(frame1);
            return;
        }

        SkinList slist = af1.skinlist;

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;

        int position = 0;
        int vertex = vertices[position++];

        for (int frame = 0; frame < af1.frameCount; frame++) {
            int v;
            for (v = af1.vertices[frame]; v > vertex; vertex = vertices[position++]) {
                ;
            }
            if (v != vertex || slist.opcodes[v] == 0) {
                this.transform(slist.opcodes[v], slist.vertices[v], af1.vertexX[frame], af1.vertexY[frame], af1.vertexZ[frame]);
            }
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;

        position = 0;
        vertex = vertices[position++];

        for (int frame = 0; frame < af2.frameCount; frame++) {
            int v;
            for (v = af2.vertices[frame]; v > vertex; vertex = vertices[position++]) {
            }
            if (v == vertex || slist.opcodes[v] == 0) {
                this.transform(slist.opcodes[v], slist.vertices[v], af2.vertexX[frame], af2.vertexY[frame], af2.vertexZ[frame]);
            }
        }

    }

    public void applyVertexWeights() {
        if (vertexSkinTypes != null) {
            int weightCounts[] = new int[256];
            int topLabel = 0;

            for (int i = 0; i < vertexCount; i++) {
                int label = vertexSkinTypes[i];
                weightCounts[label]++;

                if (label > topLabel) {
                    topLabel = label;
                }
            }

            vertexWeights = new int[topLabel + 1][];

            for (int i = 0; i <= topLabel; i++) {
                vertexWeights[i] = new int[weightCounts[i]];
                weightCounts[i] = 0;
            }

            for (int i = 0; i < vertexCount; i++) {
                int label = vertexSkinTypes[i];
                vertexWeights[label][weightCounts[label]++] = i;
            }

            vertexSkinTypes = null;
            weightCounts = null;
        }

        if (triangleTskin != null) {
            int skinCounts[] = new int[256];
            int topSkin = 0;

            for (int i = 0; i < triangleCount; i++) {
                int skin = triangleTskin[i];
                skinCounts[skin]++;

                if (skin > topSkin) {
                    topSkin = skin;
                }
            }

            triangleGroups = new int[topSkin + 1][];

            for (int i = 0; i <= topSkin; i++) {
                triangleGroups[i] = new int[skinCounts[i]];
                skinCounts[i] = 0;
            }

            for (int i = 0; i < triangleCount; i++) {
                int group = triangleTskin[i];
                triangleGroups[group][skinCounts[group]++] = i;
            }

            triangleTskin = null;
            skinCounts = null;
        }
    }

    public void draw(int pitch, int yaw, int roll, int cameraPitch, int cameraX, int cameraY, int cameraZ) {
        int centerX = Canvas3D.centerX;
        int centerY = Canvas3D.centerY;
        int pitchSin = MathUtils.sin[pitch];
        int pitchCos = MathUtils.cos[pitch];
        int yawSin = MathUtils.sin[yaw];
        int yawCos = MathUtils.cos[yaw];
        int rollSin = MathUtils.sin[roll];
        int rollCos = MathUtils.cos[roll];
        int arcSin = MathUtils.sin[cameraPitch];
        int arcCos = MathUtils.cos[cameraPitch];
        int camDist = cameraY * arcSin + cameraZ * arcCos >> 16;

        for (int i = 0; i < vertexCount; i++) {
            int x = vertexX[i];
            int y = vertexY[i];
            int z = vertexZ[i];

            if (roll != 0) {
                int x2 = y * rollSin + x * rollCos >> 16;
                y = y * rollCos - x * rollSin >> 16;
                x = x2;
            }

            if (pitch != 0) {
                int y2 = y * pitchCos - z * pitchSin >> 16;
                z = y * pitchSin + z * pitchCos >> 16;
                y = y2;
            }

            if (yaw != 0) {
                int x2 = z * yawSin + x * yawCos >> 16;
                z = z * yawCos - x * yawSin >> 16;
                x = x2;
            }

            x += cameraX;
            y += cameraY;
            z += cameraZ;

            int y2 = y * arcCos - z * arcSin >> 16;
            z = y * arcSin + z * arcCos >> 16;
            y = y2;

            triangleDepth[i] = z - camDist;
            triangleX[i] = centerX + (x << 9) / z;
            triangleY[i] = centerY + (y << 9) / z;

            if (texturedTriangleCount > 0) {
                tmpTexturedX[i] = x;
                tmpTexturedY[i] = y;
                tmpTexturedZ[i] = z;
            }
        }

        try {
            draw(false, false, 0);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void drawTriangle(int i) {
        if (triangleProject[i]) {
            drawTriangle2(i);
            return;
        }

        int xI = triangleViewspaceA[i];
        int yI = triangleViewspaceB[i];
        int zI = triangleViewspaceC[i];

        Canvas3D.checkBounds = triangleCheckBounds[i];

        if (triangleAlpha == null) {
            Canvas3D.opacity = 0;
        } else {
            Canvas3D.opacity = triangleAlpha[i];
        }

        int type;

        if (triangleInfo == null) {
            type = 0;
        } else {
            type = triangleInfo[i] & 3;
        }

        switch (type) {
            case 0: { // Shaded
                Canvas3D.drawShadedTriangle(triangleX[xI], triangleY[xI], triangleX[yI], triangleY[yI], triangleX[zI], triangleY[zI], triHsl1[i], triHsl2[i], triHsl3[i]);
                return;
            }
            case 1: { // Flat
                Canvas3D.drawFlatTriangle(triangleX[xI], triangleY[xI], triangleX[yI], triangleY[yI], triangleX[zI], triangleY[zI], palette[triHsl1[i]]);
                return;
            }
            case 2: { // Shaded Texture
                int j = triangleInfo[i] >> 2;
                int x = textureMapX[j];
                int y = textureMapY[j];
                int z = textureMapZ[j];
                Canvas3D.drawTexturedTriangle(triangleX[xI], triangleY[xI], triangleX[yI], triangleY[yI], triangleX[zI], triangleY[zI], triHsl1[i], triHsl2[i], triHsl3[i], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                return;
            }
            case 3: { // Flat Texture
                int j = triangleInfo[i] >> 2;
                int x = textureMapX[j];
                int y = textureMapY[j];
                int z = textureMapZ[j];
                Canvas3D.drawTexturedTriangle(triangleX[xI], triangleY[xI], triangleX[yI], triangleY[yI], triangleX[zI], triangleY[zI], triHsl1[i], triHsl1[i], triHsl1[i], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                return;
            }
        }
    }

    public short method465(Model model, int vertex) {
        short j = -1;
        short x = model.vertexX[vertex];
        short y = model.vertexY[vertex];
        short z = model.vertexZ[vertex];

        for (short i = 0; i < vertexCount; i++) {
            if (x != vertexX[i] || y != vertexY[i] || z != vertexZ[i]) {
                continue;
            }
            j = i;
            break;
        }

        if (j == -1) {
            vertexX[vertexCount] = x;
            vertexY[vertexCount] = y;
            vertexZ[vertexCount] = z;

            if (model.vertexSkinTypes != null) {
                vertexSkinTypes[vertexCount] = model.vertexSkinTypes[vertex];
            }

            j = vertexCount++;
        }

        return j;
    }

    public void method466() {
        super.height = 0;
        maxHorizon = 0;
        maxY = 0;

        for (int i = 0; i < vertexCount; i++) {
            int x = vertexX[i];
            int y = vertexY[i];
            int z = vertexZ[i];

            if (-y > super.height) {
                super.height = -y;
            }

            if (y > maxY) {
                maxY = y;
            }

            int horizon = x * x + z * z;

            if (horizon > maxHorizon) {
                maxHorizon = horizon;
            }
        }

        maxHorizon = (int) (Math.sqrt(maxHorizon) + 0.99D);
        unknown2 = (int) (Math.sqrt(maxHorizon * maxHorizon + super.height * super.height) + 0.99D);
        unknown3 = unknown2 + (int) (Math.sqrt(maxHorizon * maxHorizon + maxY * maxY) + 0.99D);
    }

    public void method467() {
        super.height = 0;
        maxY = 0;

        for (int i = 0; i < vertexCount; i++) {
            int vY = vertexY[i];

            if (-vY > super.height) {
                super.height = -vY;
            }

            if (vY > maxY) {
                maxY = vY;
            }
        }

        unknown2 = (int) (Math.sqrt(maxHorizon * maxHorizon + super.height * super.height) + 0.99D);
        unknown3 = unknown2 + (int) (Math.sqrt(maxHorizon * maxHorizon + maxY * maxY) + 0.99D);
    }

    public void method468() {
        super.height = 0;
        maxHorizon = 0;
        maxY = 0;
        minX = 999999;
        maxX = 0xfff0bdc1;
        maxZ = 0xfffe7961;
        minZ = 99999;

        for (int i = 0; i < vertexCount; i++) {
            int x = vertexX[i];
            int y = vertexY[i];
            int z = vertexZ[i];

            if (x < minX) {
                minX = x;
            }

            if (x > maxX) {
                maxX = x;
            }

            if (z < minZ) {
                minZ = z;
            }

            if (z > maxZ) {
                maxZ = z;
            }

            if (-y > super.height) {
                super.height = -y;
            }

            if (y > maxY) {
                maxY = y;
            }

            int horizon = x * x + z * z;

            if (horizon > maxHorizon) {
                maxHorizon = horizon;
            }
        }

        maxHorizon = (int) Math.sqrt(maxHorizon);
        unknown2 = (int) Math.sqrt(maxHorizon * maxHorizon + super.height * super.height);
        unknown3 = unknown2 + (int) Math.sqrt(maxHorizon * maxHorizon + maxY * maxY);
    }

    // TODO: Figure out the sorting method for this.
    public void draw(boolean project, boolean hoverable, int uid) {
        for (int i = 0; i < unknown3; i++) {
            anIntArray1671[i] = 0;
        }

        for (int i = 0; i < triangleCount; i++) {
            if (triangleInfo == null || triangleInfo[i] != -1) {
                int a = triangleViewspaceA[i];
                int b = triangleViewspaceB[i];
                int c = triangleViewspaceC[i];

                int x1 = triangleX[a];
                int x2 = triangleX[b];
                int x3 = triangleX[c];

                if (project && (x1 == -5000 || x2 == -5000 || x3 == -5000)) {
                    triangleProject[i] = true;
                    int depth = (triangleDepth[a] + triangleDepth[b] + triangleDepth[c]) / 3 + unknown2;
                    anIntArrayArray1672[depth][anIntArray1671[depth]++] = i;
                } else {
                    if (hoverable && triContains(mouseX, mouseY, x1, triangleY[a], triangleY[b], x2, triangleY[c], x3)) {
                        hoveredUid[hoveredCount++] = uid;
                        hoverable = false;
                    }

                    if ((x1 - x2) * (triangleY[c] - triangleY[b]) - (triangleY[a] - triangleY[b]) * (x3 - x2) > 0) {
                        triangleProject[i] = false;

                        if (x1 < 0 || x2 < 0 || x3 < 0 || x1 > Canvas2D.bound || x2 > Canvas2D.bound || x3 > Canvas2D.bound) {
                            triangleCheckBounds[i] = true;
                        } else {
                            triangleCheckBounds[i] = false;
                        }

                        int depth = (triangleDepth[a] + triangleDepth[b] + triangleDepth[c]) / 3 + unknown2;

                        anIntArrayArray1672[depth][anIntArray1671[depth]++] = i;
                    }
                }
            }
        }

        if (trianglePriority == null) {
            for (int i = unknown3 - 1; i >= 0; i--) {
                int l1 = anIntArray1671[i];

                if (l1 > 0) {
                    int[] triangles = anIntArrayArray1672[i];

                    for (int k = 0; k < l1; k++) {
                        drawTriangle(triangles[k]);
                    }
                }
            }
            return;
        }

        for (int priority = 0; priority < 12; priority++) {
            anIntArray1673[priority] = 0;
            anIntArray1677[priority] = 0;
        }

        for (int i = unknown3 - 1; i >= 0; i--) {
            int k2 = anIntArray1671[i];
            if (k2 > 0) {
                int[] triangles = anIntArrayArray1672[i];

                for (int j = 0; j < k2; j++) {
                    int triangle = triangles[j];
                    int priority = trianglePriority[triangle];
                    int j6 = anIntArray1673[priority]++;

                    anIntArrayArray1674[priority][j6] = triangle;

                    if (priority < 10) {
                        anIntArray1677[priority] += i;
                    } else if (priority == 10) {
                        anIntArray1675[j6] = i;
                    } else {
                        anIntArray1676[j6] = i;
                    }
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0) {
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        }

        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0) {
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        }

        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0) {
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
        }

        int i = 0;
        int k6 = anIntArray1673[10];
        int triangles[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;

        if (i == k6) {
            i = 0;
            k6 = anIntArray1673[11];
            triangles = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }

        int i5;
        if (i < k6) {
            i5 = ai3[i];
        } else {
            i5 = -1000;
        }

        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                drawTriangle(triangles[i++]);

                if (i == k6 && triangles != anIntArrayArray1674[11]) {
                    i = 0;
                    k6 = anIntArray1673[11];
                    triangles = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }

                if (i < k6) {
                    i5 = ai3[i];
                } else {
                    i5 = -1000;
                }
            }

            while (l6 == 3 && i5 > k3) {
                drawTriangle(triangles[i++]);
                if (i == k6 && triangles != anIntArrayArray1674[11]) {
                    i = 0;
                    k6 = anIntArray1673[11];
                    triangles = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i < k6) {
                    i5 = ai3[i];
                } else {
                    i5 = -1000;
                }
            }

            while (l6 == 5 && i5 > j4) {
                drawTriangle(triangles[i++]);
                if (i == k6 && triangles != anIntArrayArray1674[11]) {
                    i = 0;
                    k6 = anIntArray1673[11];
                    triangles = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i < k6) {
                    i5 = ai3[i];
                } else {
                    i5 = -1000;
                }
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++) {
                drawTriangle(ai4[j7]);
            }
        }

        while (i5 != -1000) {
            drawTriangle(triangles[i++]);
            if (i == k6 && triangles != anIntArrayArray1674[11]) {
                i = 0;
                triangles = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i < k6) {
                i5 = ai3[i];
            } else {
                i5 = -1000;
            }
        }
    }

    public void drawTriangle2(int i) {
        int centerX = Canvas3D.centerX;
        int centerY = Canvas3D.centerY;

        int j = 0;

        int a = triangleViewspaceA[i];
        int b = triangleViewspaceB[i];
        int c = triangleViewspaceC[i];

        int aZ = tmpTexturedZ[a];
        int bZ = tmpTexturedZ[b];
        int cZ = tmpTexturedZ[c];

        if (aZ >= 50) {
            tmpScreenX[j] = triangleX[a];
            tmpScreenY[j] = triangleY[a];
            tmpHsl[j++] = triHsl1[i];
        } else {
            int x = tmpTexturedX[a];
            int y = tmpTexturedY[a];
            int hsl = triHsl1[i];

            if (cZ >= 50) {
                int decay = (50 - aZ) * shadowDecay[cZ - aZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[c] - x) * decay >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[c] - y) * decay >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl3[i] - hsl) * decay >> 16);
            }

            if (bZ >= 50) {
                int decay = (50 - aZ) * shadowDecay[bZ - aZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[b] - x) * decay >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[b] - y) * decay >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl2[i] - hsl) * decay >> 16);
            }
        }

        if (bZ >= 50) {
            tmpScreenX[j] = triangleX[b];
            tmpScreenY[j] = triangleY[b];
            tmpHsl[j++] = triHsl2[i];
        } else {
            int x = tmpTexturedX[b];
            int y = tmpTexturedY[b];
            int hsl = triHsl2[i];

            if (aZ >= 50) {
                int i6 = (50 - bZ) * shadowDecay[aZ - bZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[a] - x) * i6 >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[a] - y) * i6 >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl1[i] - hsl) * i6 >> 16);
            }

            if (cZ >= 50) {
                int j6 = (50 - bZ) * shadowDecay[cZ - bZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[c] - x) * j6 >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[c] - y) * j6 >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl3[i] - hsl) * j6 >> 16);
            }
        }

        if (cZ >= 50) {
            tmpScreenX[j] = triangleX[c];
            tmpScreenY[j] = triangleY[c];
            tmpHsl[j++] = triHsl3[i];
        } else {
            int x = tmpTexturedX[c];
            int y = tmpTexturedY[c];
            int hsl = triHsl3[i];
            if (bZ >= 50) {
                int k6 = (50 - cZ) * shadowDecay[bZ - cZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[b] - x) * k6 >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[b] - y) * k6 >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl2[i] - hsl) * k6 >> 16);
            }
            if (aZ >= 50) {
                int l6 = (50 - cZ) * shadowDecay[aZ - cZ];
                tmpScreenX[j] = centerX + (x + ((tmpTexturedX[a] - x) * l6 >> 16) << 9) / 50;
                tmpScreenY[j] = centerY + (y + ((tmpTexturedY[a] - y) * l6 >> 16) << 9) / 50;
                tmpHsl[j++] = hsl + ((triHsl1[i] - hsl) * l6 >> 16);
            }
        }

        int x0 = tmpScreenX[0];
        int x1 = tmpScreenX[1];
        int x2 = tmpScreenX[2];
        int y0 = tmpScreenY[0];
        int y1 = tmpScreenY[1];
        int y2 = tmpScreenY[2];

        if ((x0 - x1) * (y2 - y1) - (y0 - y1) * (x2 - x1) > 0) {
            Canvas3D.checkBounds = false;
            if (j == 3) {
                if (x0 < 0 || x1 < 0 || x2 < 0 || x0 > Canvas2D.bound || x1 > Canvas2D.bound || x2 > Canvas2D.bound) {
                    Canvas3D.checkBounds = true;
                }

                int type;

                if (triangleInfo == null) {
                    type = 0;
                } else {
                    type = triangleInfo[i] & 3;
                }

                if (type == 0) {
                    Canvas3D.drawShadedTriangle(x0, y0, x1, y1, x2, y2, tmpHsl[0], tmpHsl[1], tmpHsl[2]);
                } else if (type == 1) {
                    Canvas3D.drawFlatTriangle(x0, y0, x1, y1, x2, y2, palette[triHsl1[i]]);
                } else if (type == 2) {
                    int k = triangleInfo[i] >> 2;
                    int x = textureMapX[k];
                    int y = textureMapY[k];
                    int z = textureMapZ[k];
                    Canvas3D.drawTexturedTriangle(x0, y0, x1, y1, x2, y2, tmpHsl[0], tmpHsl[1], tmpHsl[2], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                } else if (type == 3) {
                    int k = triangleInfo[i] >> 2;
                    int x = textureMapX[k];
                    int y = textureMapY[k];
                    int z = textureMapZ[k];
                    Canvas3D.drawTexturedTriangle(x0, y0, x1, y1, x2, y2, triHsl1[i], triHsl1[i], triHsl1[i], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                }
            } else if (j == 4) {
                if (x0 < 0 || x1 < 0 || x2 < 0 || x0 > Canvas2D.bound || x1 > Canvas2D.bound || x2 > Canvas2D.bound || tmpScreenX[3] < 0 || tmpScreenX[3] > Canvas2D.bound) {
                    Canvas3D.checkBounds = true;
                }

                int type;

                if (triangleInfo == null) {
                    type = 0;
                } else {
                    type = triangleInfo[i] & 3;
                }

                if (type == 0) {
                    Canvas3D.drawShadedTriangle(x0, y0, x1, y1, x2, y2, tmpHsl[0], tmpHsl[1], tmpHsl[2]);
                    Canvas3D.drawShadedTriangle(x0, y0, x2, y2, tmpScreenX[3], tmpScreenY[3], tmpHsl[0], tmpHsl[2], tmpHsl[3]);
                } else if (type == 1) {
                    int rgb = palette[triHsl1[i]];
                    Canvas3D.drawFlatTriangle(x0, y0, x1, y1, x2, y2, rgb);
                    Canvas3D.drawFlatTriangle(x0, y0, x2, y2, tmpScreenX[3], tmpScreenY[3], rgb);
                } else if (type == 2) {
                    int k = triangleInfo[i] >> 2;
                    int x = textureMapX[k];
                    int y = textureMapY[k];
                    int z = textureMapZ[k];
                    Canvas3D.drawTexturedTriangle(x0, y0, x1, y1, x2, y2, tmpHsl[0], tmpHsl[1], tmpHsl[2], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                    Canvas3D.drawTexturedTriangle(x0, y0, x2, y2, tmpScreenX[3], tmpScreenY[3], tmpHsl[0], tmpHsl[2], tmpHsl[3], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                } else if (type == 3) {
                    int k = triangleInfo[i] >> 2;
                    int x = textureMapX[k];
                    int y = textureMapY[k];
                    int z = textureMapZ[k];
                    Canvas3D.drawTexturedTriangle(x0, y0, x1, y1, x2, y2, triHsl1[i], triHsl1[i], triHsl1[i], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                    Canvas3D.drawTexturedTriangle(x0, y0, x2, y2, tmpScreenX[3], tmpScreenY[3], triHsl1[i], triHsl1[i], triHsl1[i], tmpTexturedX[x], tmpTexturedY[x], tmpTexturedZ[x], tmpTexturedX[y], tmpTexturedY[y], tmpTexturedZ[y], tmpTexturedX[z], tmpTexturedY[z], tmpTexturedZ[z], triangleColor[i]);
                }
            }
        }
    }

    @Override
    public void render(int rotation, int camPitchSin, int camPitchCos, int camYawSin, int camYawCos, int x, int y, int z, int uid) {
        int j2 = y * camYawCos - x * camYawSin >> 16;
        int camDist = z * camPitchSin + j2 * camPitchCos >> 16;
        int l2 = maxHorizon * camPitchCos >> 16;
        int angle = camDist + l2;

        if (angle <= 50 || camDist >= 3500) {
            return;
        }

        int j3 = y * camYawSin + x * camYawCos >> 16;

        int x1 = j3 - maxHorizon << 9;
        if (x1 / angle >= Canvas2D.centerX2d) {
            return;
        }

        int x2 = j3 + maxHorizon << 9;
        if (x2 / angle <= -Canvas2D.centerX2d) {
            return;
        }

        int i4 = z * camPitchCos - j2 * camPitchSin >> 16;
        int j4 = maxHorizon * camPitchSin >> 16;

        int y2 = i4 + j4 << 9;
        if (y2 / angle <= -Canvas2D.centerY2d) {
            return;
        }

        int l4 = j4 + (super.height * camPitchCos >> 16);
        int y1 = i4 - l4 << 9;
        if (y1 / angle >= Canvas2D.centerY2d) {
            return;
        }

        int j5 = l2 + (super.height * camPitchSin >> 16);
        boolean flag = false;

        if (camDist - j5 <= 50) {
            flag = true;
        }

        boolean hoverable = false;

        if (uid > 0 && sceneClickable) {
            int k5 = camDist - l2;

            if (k5 <= 50) {
                k5 = 50;
            }

            if (j3 > 0) {
                x1 /= angle;
                x2 /= k5;
            } else {
                x2 /= angle;
                x1 /= k5;
            }

            if (i4 > 0) {
                y1 /= angle;
                y2 /= k5;
            } else {
                y2 /= angle;
                y1 /= k5;
            }

            int dx = mouseX - Canvas3D.centerX;
            int dy = mouseY - Canvas3D.centerY;
            if (dx > x1 && dx < x2 && dy > y1 && dy < y2) {
                if (isClickable) {
                    hoveredUid[hoveredCount++] = uid;
                } else {
                    hoverable = true;
                }
            }
        }

        int centerX = Canvas3D.centerX;
        int centerY = Canvas3D.centerY;
        int sin = 0;
        int cos = 0;

        if (rotation != 0) {
            sin = MathUtils.sin[rotation];
            cos = MathUtils.cos[rotation];
        }

        for (int v = 0; v < vertexCount; v++) {
            int vX = vertexX[v];
            int vY = vertexY[v];
            int vZ = vertexZ[v];

            if (rotation != 0) {
                int j8 = vZ * sin + vX * cos >> 16;
                vZ = vZ * cos - vX * sin >> 16;
                vX = j8;
            }

            vX += x;
            vY += z;
            vZ += y;

            int i = vZ * camYawSin + vX * camYawCos >> 16;
            vZ = vZ * camYawCos - vX * camYawSin >> 16;
            vX = i;

            i = vY * camPitchCos - vZ * camPitchSin >> 16;
            vZ = vY * camPitchSin + vZ * camPitchCos >> 16;
            vY = i;

            triangleDepth[v] = vZ - camDist;

            if (vZ >= 50) {
                triangleX[v] = centerX + (vX << 9) / vZ;
                triangleY[v] = centerY + (vY << 9) / vZ;
            } else {
                triangleX[v] = -5000;
                flag = true;
            }

            if (flag || texturedTriangleCount > 0) {
                tmpTexturedX[v] = vX;
                tmpTexturedY[v] = vY;
                tmpTexturedZ[v] = vZ;
            }
        }

        try {
            draw(flag, hoverable, uid);
        } catch (Exception e) {
        }
    }

    public void replace(Model m, boolean flag) {
        vertexCount = m.vertexCount;
        triangleCount = m.triangleCount;
        texturedTriangleCount = m.texturedTriangleCount;

        if (replaceVertexX.length < vertexCount) {
            replaceVertexX = new short[vertexCount + 100];
            replaceVertexY = new short[vertexCount + 100];
            replaceVertexZ = new short[vertexCount + 100];
        }

        vertexX = replaceVertexX;
        vertexY = replaceVertexY;
        vertexZ = replaceVertexZ;

        for (int k = 0; k < vertexCount; k++) {
            vertexX[k] = m.vertexX[k];
            vertexY[k] = m.vertexY[k];
            vertexZ[k] = m.vertexZ[k];
        }

        if (flag) {
            triangleAlpha = m.triangleAlpha;
        } else {
            if (anIntArray1625.length < triangleCount) {
                anIntArray1625 = new int[triangleCount + 100];
            }
            triangleAlpha = anIntArray1625;

            if (m.triangleAlpha == null) {
                for (int l = 0; l < triangleCount; l++) {
                    triangleAlpha[l] = 0;
                }
            } else {
                for (int i1 = 0; i1 < triangleCount; i1++) {
                    triangleAlpha[i1] = m.triangleAlpha[i1];
                }

            }
        }

        triangleInfo = m.triangleInfo;
        triangleColor = m.triangleColor;
        trianglePriority = m.trianglePriority;
        anInt1641 = m.anInt1641;
        triangleGroups = m.triangleGroups;
        vertexWeights = m.vertexWeights;
        triangleViewspaceA = m.triangleViewspaceA;
        triangleViewspaceB = m.triangleViewspaceB;
        triangleViewspaceC = m.triangleViewspaceC;
        triHsl1 = m.triHsl1;
        triHsl2 = m.triHsl2;
        triHsl3 = m.triHsl3;
        textureMapX = m.textureMapX;
        textureMapY = m.textureMapY;
        textureMapZ = m.textureMapZ;
    }

    public void rotateCcw() {
        for (int i = 0; i < vertexCount; i++) {
            vertexZ[i] = (short) -vertexZ[i];
        }

        for (int i = 0; i < triangleCount; i++) {
            int nZ = triangleViewspaceA[i];
            triangleViewspaceA[i] = triangleViewspaceC[i];
            triangleViewspaceC[i] = (short) nZ;
        }
    }

    public void rotateCw() {
        for (int i = 0; i < vertexCount; i++) {
            int newZ = vertexX[i];
            vertexX[i] = vertexZ[i];
            vertexZ[i] = (short) -newZ;
        }
    }

    public void scale(int x, int y, int z) {
        for (int i = 0; i < vertexCount; i++) {
            vertexX[i] = (short) ((vertexX[i] * x) / 128);
            vertexY[i] = (short) ((vertexY[i] * y) / 128);
            vertexZ[i] = (short) ((vertexZ[i] * z) / 128);
        }

    }

    public void setColor(int from, int to) {
        for (int i = 0; i < this.triangleCount; i++) {
            if (this.triangleColor[i] == from) {
                this.triangleColor[i] = to;
            }
        }
    }

    public void setColors(int[] from, int[] to) {
        if (from.length != to.length) {
            return;
        }
        for (int i = 0; i < from.length; i++) {
            this.setColor(from[i], to[i]);
        }
    }

    public void setPitch(int pitch) {
        int sin = MathUtils.sin[pitch];
        int cos = MathUtils.cos[pitch];
        for (int i = 0; i < vertexCount; i++) {
            int newY = vertexY[i] * cos - vertexZ[i] * sin >> 16;
            vertexZ[i] = (short) (vertexY[i] * sin + vertexZ[i] * cos >> 16);
            vertexY[i] = (short) newY;
        }
    }

    public void transform(int opcode, int vertices[], int x, int y, int z) {
        int vertexCount = vertices.length;

        if (opcode == 0) {
            int j1 = 0;
            anInt1681 = 0;
            anInt1682 = 0;
            anInt1683 = 0;

            for (int i = 0; i < vertexCount; i++) {
                int j = vertices[i];
                if (j < vertexWeights.length) {
                    int vWeights[] = vertexWeights[j];
                    for (int vWeight = 0; vWeight < vWeights.length; vWeight++) {
                        int weightVertex = vWeights[vWeight];
                        anInt1681 += vertexX[weightVertex];
                        anInt1682 += vertexY[weightVertex];
                        anInt1683 += vertexZ[weightVertex];
                        j1++;
                    }
                }
            }

            if (j1 > 0) {
                anInt1681 = anInt1681 / j1 + x;
                anInt1682 = anInt1682 / j1 + y;
                anInt1683 = anInt1683 / j1 + z;
                return;
            } else {
                anInt1681 = x;
                anInt1682 = y;
                anInt1683 = z;
                return;
            }
        }

        // Translation
        if (opcode == 1) {
            for (int i = 0; i < vertexCount; i++) {
                int j = vertices[i];
                if (j < vertexWeights.length) {
                    int verticeIndices[] = vertexWeights[j];
                    for (int k = 0; k < verticeIndices.length; k++) {
                        int vIndex = verticeIndices[k];
                        vertexX[vIndex] += x;
                        vertexY[vIndex] += y;
                        vertexZ[vIndex] += z;
                    }
                }
            }
            return;
        }

        // Rotation
        if (opcode == 2) {
            for (int i = 0; i < vertexCount; i++) {
                int j = vertices[i];
                if (j < vertexWeights.length) {
                    int verticeIndices[] = vertexWeights[j];
                    for (int k = 0; k < verticeIndices.length; k++) {
                        int vIndex = verticeIndices[k];
                        vertexX[vIndex] -= anInt1681;
                        vertexY[vIndex] -= anInt1682;
                        vertexZ[vIndex] -= anInt1683;
                        int pitch = (x & 0xff) * 8;
                        int yaw = (y & 0xff) * 8;
                        int roll = (z & 0xff) * 8;

                        if (roll != 0) {
                            int sin = MathUtils.sin[roll];
                            int cos = MathUtils.cos[roll];
                            int newX = vertexY[vIndex] * sin + vertexX[vIndex] * cos >> 16;
                            vertexY[vIndex] = (short) (vertexY[vIndex] * cos - vertexX[vIndex] * sin >> 16);
                            vertexX[vIndex] = (short) newX;
                        }

                        if (pitch != 0) {
                            int sin = MathUtils.sin[pitch];
                            int cos = MathUtils.cos[pitch];
                            int newY = vertexY[vIndex] * cos - vertexZ[vIndex] * sin >> 16;
                            vertexZ[vIndex] = (short) (vertexY[vIndex] * sin + vertexZ[vIndex] * cos >> 16);
                            vertexY[vIndex] = (short) newY;
                        }

                        if (yaw != 0) {
                            int sin = MathUtils.sin[yaw];
                            int cos = MathUtils.cos[yaw];
                            int newZ = vertexZ[vIndex] * sin + vertexX[vIndex] * cos >> 16;
                            vertexZ[vIndex] = (short) (vertexZ[vIndex] * cos - vertexX[vIndex] * sin >> 16);
                            vertexX[vIndex] = (short) newZ;
                        }

                        vertexX[vIndex] += anInt1681;
                        vertexY[vIndex] += anInt1682;
                        vertexZ[vIndex] += anInt1683;
                    }

                }
            }

            return;
        }

        if (opcode == 3) {
            for (int i = 0; i < vertexCount; i++) {
                int j = vertices[i];
                if (j < vertexWeights.length) {
                    int vertexIndex[] = vertexWeights[j];
                    for (int k = 0; k < vertexIndex.length; k++) {
                        int v = vertexIndex[k];
                        vertexX[v] -= anInt1681;
                        vertexY[v] -= anInt1682;
                        vertexZ[v] -= anInt1683;
                        vertexX[v] = (short) ((vertexX[v] * x) / 128);
                        vertexY[v] = (short) ((vertexY[v] * y) / 128);
                        vertexZ[v] = (short) ((vertexZ[v] * z) / 128);
                        vertexX[v] += anInt1681;
                        vertexY[v] += anInt1682;
                        vertexZ[v] += anInt1683;
                    }

                }
            }

            return;
        }

        // Set Alpha
        if (opcode == 5 && triangleGroups != null && triangleAlpha != null) {
            for (int i = 0; i < vertexCount; i++) {
                int group = vertices[i];

                if (group < triangleGroups.length) {
                    int triIndices[] = triangleGroups[group];

                    for (int k = 0; k < triIndices.length; k++) {
                        int triIndex = triIndices[k];

                        triangleAlpha[triIndex] += x * 8;

                        if (triangleAlpha[triIndex] < 0) {
                            triangleAlpha[triIndex] = 0;
                        } else if (triangleAlpha[triIndex] > 255) {
                            triangleAlpha[triIndex] = 255;
                        }
                    }

                }
            }

        }
    }

    public void translate(int x, int y, int z) {
        for (int i = 0; i < vertexCount; i++) {
            vertexX[i] += x;
            vertexY[i] += y;
            vertexZ[i] += z;
        }

    }

    public boolean triContains(int x, int y, int x1, int y1, int y2, int x2, int y3, int x3) {
        if (y < y1 && y < y2 && y < y3) {
            return false;
        }
        if (y > y1 && y > y2 && y > y3) {
            return false;
        }
        if (x < x1 && x < x2 && x < x3) {
            return false;
        }
        return x <= x1 || x <= x2 || x <= x3;
    }

    public static class Header {
        public int alphaStreamPos;
        public int anInt372;
        public int anInt374;
        public int anInt375;
        public int anInt376;
        public int anInt377;
        public int anInt378;
        public int drawTypeStreamPos;
        public byte[] payload;
        public int priorityStreamPos;
        public int textureMapStreamPos;
        public int texturedTriangleCount;
        public int texturedTriangleStreamPos;
        public int triangleColorStreamPos;
        public int triangleCount;
        public int tskinStreamPos;
        public short vertexCount;
    }

}
