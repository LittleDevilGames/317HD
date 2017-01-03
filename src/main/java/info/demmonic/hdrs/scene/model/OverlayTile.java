package info.demmonic.hdrs.scene.model;

public class OverlayTile {

    public static final char abc = '\200';

    /* @formatter:off */
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
    /* @formatter:on */

    public static int tmpScreenX[] = new int[6];
    public static int tmpScreenY[] = new int[6];
    public static int tmpTriangleX[] = new int[6];
    public static int tmpTriangleY[] = new int[6];
    public static int tmpTriangleZ[] = new int[6];

    public int hsl;
    public boolean ignore_uv;
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

    public OverlayTile(int localX, int localY, short v_sw, short v_se, short v_ne, short v_nw, int rgb_sw, int rgb_se, int rgb_ne, int rgb_nw, int rgb_bitset, int hsl_sw, int hsl_se, int hsl_ne, int hsl_nw, int hsl_bitset, byte texture_index, int rotation, int shape) {
        this.ignore_uv = true;

        if (v_sw != v_se || v_sw != v_ne || v_sw != v_nw) {
            this.ignore_uv = false;
        }

        this.shape = shape;
        this.rotation = rotation;
        this.rgb = rgb_bitset;
        this.hsl = hsl_bitset;

        short tile_size = 128;
        byte half = (byte) (tile_size / 2);
        byte quarter = (byte) (tile_size / 4);
        byte third = (byte) ((tile_size * 3) / 4);

        int[] opcodes = CLIPPING_FLAG[shape];
        int length = opcodes.length;

        this.triangleX = new short[length];
        this.triangleY = new short[length];
        this.triangleZ = new short[length];
        int[] hsl_array = new int[length];
        int[] rgb_array = new int[length];

        short s_x = (short) (localX * tile_size);
        short s_y = (short) (localY * tile_size);

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
                    x = s_x;
                    z = s_y;
                    y = v_sw;
                    hsl = hsl_sw;
                    rgb = rgb_sw;
                    break;
                case 2:
                    x = (short) (s_x + half);
                    z = s_y;
                    y = (short) (v_sw + v_se >> 1);
                    hsl = hsl_sw + hsl_se >> 1;
                    rgb = rgb_sw + rgb_se >> 1;
                    break;
                case 3:
                    x = (short) (s_x + tile_size);
                    z = s_y;
                    y = v_se;
                    hsl = hsl_se;
                    rgb = rgb_se;
                    break;
                case 4:
                    x = (short) (s_x + tile_size);
                    z = (short) (s_y + half);
                    y = (short) (v_se + v_ne >> 1);
                    hsl = hsl_se + hsl_ne >> 1;
                    rgb = rgb_se + rgb_ne >> 1;
                    break;
                case 5:
                    x = (short) (s_x + tile_size);
                    z = (short) (s_y + tile_size);
                    y = v_ne;
                    hsl = hsl_ne;
                    rgb = rgb_ne;
                    break;
                case 6:
                    x = (short) (s_x + half);
                    z = (short) (s_y + tile_size);
                    y = (short) (v_ne + v_nw >> 1);
                    hsl = hsl_ne + hsl_nw >> 1;
                    rgb = rgb_ne + rgb_nw >> 1;
                    break;
                case 7:
                    x = s_x;
                    z = (short) (s_y + tile_size);
                    y = v_nw;
                    hsl = hsl_nw;
                    rgb = rgb_nw;
                    break;
                case 8:
                    x = s_x;
                    z = (short) (s_y + half);
                    y = (short) (v_nw + v_sw >> 1);
                    hsl = hsl_nw + hsl_sw >> 1;
                    rgb = rgb_nw + rgb_sw >> 1;
                    break;
                case 9:
                    x = (short) (s_x + half);
                    z = (short) (s_y + quarter);
                    y = (short) (v_sw + v_se >> 1);
                    hsl = hsl_sw + hsl_se >> 1;
                    rgb = rgb_sw + rgb_se >> 1;
                    break;
                case 10:
                    x = (short) (s_x + third);
                    z = (short) (s_y + half);
                    y = (short) (v_se + v_ne >> 1);
                    hsl = hsl_se + hsl_ne >> 1;
                    rgb = rgb_se + rgb_ne >> 1;
                    break;
                case 11:
                    x = (short) (s_x + half);
                    z = (short) (s_y + third);
                    y = (short) (v_ne + v_nw >> 1);
                    hsl = hsl_ne + hsl_nw >> 1;
                    rgb = rgb_ne + rgb_nw >> 1;
                    break;
                case 12:
                    x = (short) (s_x + quarter);
                    z = (short) (s_y + half);
                    y = (short) (v_nw + v_sw >> 1);
                    hsl = hsl_nw + hsl_sw >> 1;
                    rgb = rgb_nw + rgb_sw >> 1;
                    break;
                case 13:
                    x = (short) (s_x + quarter);
                    z = (short) (s_y + quarter);
                    y = v_sw;
                    hsl = hsl_sw;
                    rgb = rgb_sw;
                    break;
                case 14:
                    x = (short) (s_x + third);
                    z = (short) (s_y + quarter);
                    y = v_se;
                    hsl = hsl_se;
                    rgb = rgb_se;
                    break;
                case 15:
                    x = (short) (s_x + third);
                    z = (short) (s_y + third);
                    y = v_ne;
                    hsl = hsl_ne;
                    rgb = rgb_ne;
                    break;
                default:
                    x = (short) (s_x + quarter);
                    z = (short) (s_y + third);
                    y = v_nw;
                    hsl = hsl_nw;
                    rgb = rgb_nw;
                    break;
            }

            this.triangleX[i] = x;
            this.triangleY[i] = y;
            this.triangleZ[i] = z;
            hsl_array[i] = hsl;
            rgb_array[i] = rgb;
        }

        byte[] path = CLIPPING_PATH[shape];
        int vertex_count = path.length / 4;
        this.vertexX = new short[vertex_count];
        this.vertexY = new short[vertex_count];
        this.vertexZ = new short[vertex_count];
        this.vertexColorA = new int[vertex_count];
        this.vertexColorB = new int[vertex_count];
        this.vertexColorC = new int[vertex_count];

        if (texture_index != -1) {
            this.triangleTextureIndex = new byte[vertex_count];
        }

        int i = 0;
        for (int j = 0; j < vertex_count; j++) {
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
                this.vertexColorA[j] = hsl_array[x];
                this.vertexColorB[j] = hsl_array[y];
                this.vertexColorC[j] = hsl_array[z];

                if (this.triangleTextureIndex != null) {
                    this.triangleTextureIndex[j] = -1;
                }
            } else {
                this.vertexColorA[j] = rgb_array[x];
                this.vertexColorB[j] = rgb_array[y];
                this.vertexColorC[j] = rgb_array[z];

                if (this.triangleTextureIndex != null) {
                    this.triangleTextureIndex[j] = texture_index;
                }
            }
        }
    }

}
