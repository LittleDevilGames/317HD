package info.demmonic.hdrs.scene.model;

public class OverlayTile {

    public static final int CLIPPING_FLAG[][] = {
            {1, 3, 5, 7},
            {1, 3, 5, 7},
            {1, 3, 5, 7},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 6},
            {1, 3, 5, 7, 2, 6},
            {1, 3, 5, 7, 2, 8},
            {1, 3, 5, 7, 2, 8},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 11, 12},
            {1, 3, 5, 7, 13, 14}
    };

    public static final byte CLIPPING_PATH[][] = {
            {
                    0, 1, 2, 3,
                    0, 0, 1, 3
            },
            {
                    1, 1, 2, 3,
                    1, 0, 1, 3
            },
            {
                    0, 1, 2, 3,
                    1, 0, 1, 3
            },
            {
                    0, 0, 1, 2,
                    0, 0, 2, 4,
                    1, 0, 4, 3
            },
            {
                    0, 0, 1, 4,
                    0, 0, 4, 3,
                    1, 1, 2, 4
            },
            {
                    0, 0, 4, 3,
                    1, 0, 1, 2,
                    1, 0, 2, 4
            },
            {
                    0, 1, 2, 4,
                    1, 0, 1, 4,
                    1, 0, 4, 3
            },
            {
                    0, 4, 1, 2,
                    0, 4, 2, 5,
                    1, 0, 4, 5,
                    1, 0, 5, 3
            },
            {
                    0, 4, 1, 2,
                    0, 4, 2, 3,
                    0, 4, 3, 5,
                    1, 0, 4, 5
            },
            {
                    0, 0, 4, 5,
                    1, 4, 1, 2,
                    1, 4, 2, 3,
                    1, 4, 3, 5
            },
            {
                    0, 0, 1, 5,
                    0, 1, 4, 5,
                    0, 1, 2, 4,
                    1, 0, 5, 3,
                    1, 5, 4, 3,
                    1, 4, 2, 3
            },
            {
                    1, 0, 1, 5,
                    1, 1, 4, 5,
                    1, 1, 2, 4,
                    0, 0, 5, 3,
                    0, 5, 4, 3,
                    0, 4, 2, 3
            },
            {
                    1, 0, 5, 4,
                    1, 0, 1, 5,
                    0, 0, 4, 3,
                    0, 4, 5, 3,
                    0, 5, 2, 3,
                    0, 1, 2, 5
            }
    };

    public static int[] tmpScreenX = new int[6];
    public static int[] tmpScreenY = new int[6];
    public static int[] tmpTriangleX = new int[6];
    public static int[] tmpTriangleY = new int[6];
    public static int[] tmpTriangleZ = new int[6];

    public int hsl;
    public boolean ignoreUv;
    public int rgb;
    public int rotation;
    public int shape;
    public byte[] triangleTextureIndex;
    public short[] triangleX;
    public short[] triangleY;
    public short[] triangleZ;
    public int[] vertexColorA;
    public int[] vertexColorB;
    public int[] vertexColorC;
    public short[] vertexX;
    public short[] vertexY;
    public short[] vertexZ;

    public OverlayTile(int localX, int localY, short vSw, short vSe, short vNe, short vNw, int rgbSw, int rgbSe, int rgbNe, int rgbNw, int rgbBitset, int hslSw, int hslSe, int hslNe, int hslNw, int hslBitset, byte textureIndex, int rotation, int shape) {
        this.ignoreUv = true;

        if (vSw != vSe || vSw != vNe || vSw != vNw) {
            this.ignoreUv = false;
        }

        this.shape = shape;
        this.rotation = rotation;
        this.rgb = rgbBitset;
        this.hsl = hslBitset;

        short tileSize = 128;
        byte half = (byte) (tileSize / 2);
        byte quarter = (byte) (tileSize / 4);
        byte third = (byte) ((tileSize * 3) / 4);

        int[] opcodes = CLIPPING_FLAG[shape];
        int length = opcodes.length;

        this.triangleX = new short[length];
        this.triangleY = new short[length];
        this.triangleZ = new short[length];
        int[] hslArray = new int[length];
        int[] rgbArray = new int[length];

        short sX = (short) (localX * tileSize);
        short sY = (short) (localY * tileSize);

        for (int i = 0; i < length; i++) {
            int opcode = opcodes[i];

            if ((opcode & 1) == 0 && opcode <= 8) {
                opcode = (opcode - rotation - rotation - 1 & 7) + 1;
            }

            if (opcode > 8 && opcode <= 12) {
                opcode = (opcode - 9 - rotation & 3) + 9;
            }

            if (opcode > 12 && opcode <= 16) {
                opcode = (opcode - 13 - rotation & 3) + 13;
            }

            short x;
            short z;
            short y;
            int hsl;
            int rgb;

            switch (opcode) {
                case 1:
                    x = sX;
                    z = sY;
                    y = vSw;
                    hsl = hslSw;
                    rgb = rgbSw;
                    break;
                case 2:
                    x = (short) (sX + half);
                    z = sY;
                    y = (short) (vSw + vSe >> 1);
                    hsl = hslSw + hslSe >> 1;
                    rgb = rgbSw + rgbSe >> 1;
                    break;
                case 3:
                    x = (short) (sX + tileSize);
                    z = sY;
                    y = vSe;
                    hsl = hslSe;
                    rgb = rgbSe;
                    break;
                case 4:
                    x = (short) (sX + tileSize);
                    z = (short) (sY + half);
                    y = (short) (vSe + vNe >> 1);
                    hsl = hslSe + hslNe >> 1;
                    rgb = rgbSe + rgbNe >> 1;
                    break;
                case 5:
                    x = (short) (sX + tileSize);
                    z = (short) (sY + tileSize);
                    y = vNe;
                    hsl = hslNe;
                    rgb = rgbNe;
                    break;
                case 6:
                    x = (short) (sX + half);
                    z = (short) (sY + tileSize);
                    y = (short) (vNe + vNw >> 1);
                    hsl = hslNe + hslNw >> 1;
                    rgb = rgbNe + rgbNw >> 1;
                    break;
                case 7:
                    x = sX;
                    z = (short) (sY + tileSize);
                    y = vNw;
                    hsl = hslNw;
                    rgb = rgbNw;
                    break;
                case 8:
                    x = sX;
                    z = (short) (sY + half);
                    y = (short) (vNw + vSw >> 1);
                    hsl = hslNw + hslSw >> 1;
                    rgb = rgbNw + rgbSw >> 1;
                    break;
                case 9:
                    x = (short) (sX + half);
                    z = (short) (sY + quarter);
                    y = (short) (vSw + vSe >> 1);
                    hsl = hslSw + hslSe >> 1;
                    rgb = rgbSw + rgbSe >> 1;
                    break;
                case 10:
                    x = (short) (sX + third);
                    z = (short) (sY + half);
                    y = (short) (vSe + vNe >> 1);
                    hsl = hslSe + hslNe >> 1;
                    rgb = rgbSe + rgbNe >> 1;
                    break;
                case 11:
                    x = (short) (sX + half);
                    z = (short) (sY + third);
                    y = (short) (vNe + vNw >> 1);
                    hsl = hslNe + hslNw >> 1;
                    rgb = rgbNe + rgbNw >> 1;
                    break;
                case 12:
                    x = (short) (sX + quarter);
                    z = (short) (sY + half);
                    y = (short) (vNw + vSw >> 1);
                    hsl = hslNw + hslSw >> 1;
                    rgb = rgbNw + rgbSw >> 1;
                    break;
                case 13:
                    x = (short) (sX + quarter);
                    z = (short) (sY + quarter);
                    y = vSw;
                    hsl = hslSw;
                    rgb = rgbSw;
                    break;
                case 14:
                    x = (short) (sX + third);
                    z = (short) (sY + quarter);
                    y = vSe;
                    hsl = hslSe;
                    rgb = rgbSe;
                    break;
                case 15:
                    x = (short) (sX + third);
                    z = (short) (sY + third);
                    y = vNe;
                    hsl = hslNe;
                    rgb = rgbNe;
                    break;
                default:
                    x = (short) (sX + quarter);
                    z = (short) (sY + third);
                    y = vNw;
                    hsl = hslNw;
                    rgb = rgbNw;
                    break;
            }

            this.triangleX[i] = x;
            this.triangleY[i] = y;
            this.triangleZ[i] = z;
            hslArray[i] = hsl;
            rgbArray[i] = rgb;
        }

        byte[] path = CLIPPING_PATH[shape];
        int vertexCount = path.length / 4;
        this.vertexX = new short[vertexCount];
        this.vertexY = new short[vertexCount];
        this.vertexZ = new short[vertexCount];
        this.vertexColorA = new int[vertexCount];
        this.vertexColorB = new int[vertexCount];
        this.vertexColorC = new int[vertexCount];

        if (textureIndex != -1) {
            this.triangleTextureIndex = new byte[vertexCount];
        }

        int i = 0;
        for (int j = 0; j < vertexCount; j++) {
            int type = path[i];
            short x = path[i + 1];
            short y = path[i + 2];
            short z = path[i + 3];
            i += 4;

            if (x < 4) {
                x = (short) (x - rotation & 3);
            }

            if (y < 4) {
                y = (short) (y - rotation & 3);
            }

            if (z < 4) {
                z = (short) (z - rotation & 3);
            }

            this.vertexX[j] = x;
            this.vertexY[j] = y;
            this.vertexZ[j] = z;

            if (type == 0) {
                this.vertexColorA[j] = hslArray[x];
                this.vertexColorB[j] = hslArray[y];
                this.vertexColorC[j] = hslArray[z];

                if (this.triangleTextureIndex != null) {
                    this.triangleTextureIndex[j] = -1;
                }
            } else {
                this.vertexColorA[j] = rgbArray[x];
                this.vertexColorB[j] = rgbArray[y];
                this.vertexColorC[j] = rgbArray[z];

                if (this.triangleTextureIndex != null) {
                    this.triangleTextureIndex[j] = textureIndex;
                }
            }
        }
    }

}
