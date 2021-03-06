package info.demmonic.hdrs.audio.model;

import info.demmonic.hdrs.io.Buffer;

public class SoundFilter {

    public static float aFloat671;
    public static float aFloatArrayArray669[][] = new float[2][8];
    public static int anInt672;
    public static int anIntArrayArray670[][] = new int[2][8];

    public int anInt663;
    public int anInt664;
    public int anIntArray665[];
    public int anIntArray668[];
    public int anIntArrayArrayArray666[][][];
    public int anIntArrayArrayArray667[][][];

    public SoundFilter() {
        anInt663 = 748;
        anInt664 = 201;
        anIntArray665 = new int[2];
        anIntArrayArrayArray666 = new int[2][2][4];
        anIntArrayArrayArray667 = new int[2][2][4];
        anIntArray668 = new int[2];
    }

    public float amplitude(int i, int j, float f) {
        float f1 = (float) anIntArrayArrayArray667[i][0][j] + f * (float) (anIntArrayArrayArray667[i][1][j] - anIntArrayArrayArray667[i][0][j]);
        f1 *= 0.001525879F;
        return 1.0F - (float) Math.pow(10D, -f1 / 20F);
    }

    public float normalize(float octave) {
        float fNote = 32.7032F * (float) Math.pow(2D, octave);
        return (fNote * 3.141593F) / 11025F;
    }

    public float method543(byte byte0, float f, int i, int j) {
        float f1 = (float) anIntArrayArrayArray666[j][0][i] + f * (float) (anIntArrayArrayArray666[j][1][i] - anIntArrayArrayArray666[j][0][i]);
        f1 *= 0.0001220703F;
        return normalize(f1);
    }

    public int method544(int i, float f, int j) {
        if (i == 0) {
            float f1 = (float) anIntArray668[0] + (float) (anIntArray668[1] - anIntArray668[0]) * f;
            f1 *= 0.003051758F;
            aFloat671 = (float) Math.pow(0.10000000000000001D, f1 / 20F);
            anInt672 = (int) (aFloat671 * 65536F);
        }

        if (anIntArray665[i] == 0) {
            return 0;
        }

        float f2 = amplitude(i, 0, f);
        aFloatArrayArray669[i][0] = -2F * f2 * (float) Math.cos(method543((byte) 9, f, 0, i));
        aFloatArrayArray669[i][1] = f2 * f2;

        for (int k = 1; k < anIntArray665[i]; k++) {
            float f3 = amplitude(i, k, f);
            float f4 = -2F * f3 * (float) Math.cos(method543((byte) 9, f, k, i));
            float f5 = f3 * f3;
            aFloatArrayArray669[i][k * 2 + 1] = aFloatArrayArray669[i][k * 2 - 1] * f5;
            aFloatArrayArray669[i][k * 2] = aFloatArrayArray669[i][k * 2 - 1] * f4 + aFloatArrayArray669[i][k * 2 - 2] * f5;
            for (int j1 = k * 2 - 1; j1 >= 2; j1--) {
                aFloatArrayArray669[i][j1] += aFloatArrayArray669[i][j1 - 1] * f4 + aFloatArrayArray669[i][j1 - 2] * f5;
            }

            aFloatArrayArray669[i][1] += aFloatArrayArray669[i][0] * f4 + f5;
            aFloatArrayArray669[i][0] += f4;
        }

        if (i == 0) {
            for (int l = 0; l < anIntArray665[0] * 2; l++) {
                aFloatArrayArray669[0][l] *= aFloat671;
            }
        }

        for (int i1 = 0; i1 < anIntArray665[i] * 2; i1++) {
            anIntArrayArray670[i][i1] = (int) (aFloatArrayArray669[i][i1] * 65536F);
        }

        return anIntArray665[i] * 2;
    }

    public void method545(Buffer s, boolean flag, SoundEnvelope class29) {
        int i = s.readUnsignedByte();
        anIntArray665[0] = i >> 4;
        if (flag) {
            return;
        }
        anIntArray665[1] = i & 0xf;
        if (i != 0) {
            anIntArray668[0] = s.readUnsignedShort();
            anIntArray668[1] = s.readUnsignedShort();
            int j = s.readUnsignedByte();
            for (int k = 0; k < 2; k++) {
                for (int l = 0; l < anIntArray665[k]; l++) {
                    anIntArrayArrayArray666[k][0][l] = s.readUnsignedShort();
                    anIntArrayArrayArray667[k][0][l] = s.readUnsignedShort();
                }

            }

            for (int i1 = 0; i1 < 2; i1++) {
                for (int j1 = 0; j1 < anIntArray665[i1]; j1++) {
                    if ((j & 1 << i1 * 4 << j1) != 0) {
                        anIntArrayArrayArray666[i1][1][j1] = s.readUnsignedShort();
                        anIntArrayArrayArray667[i1][1][j1] = s.readUnsignedShort();
                    } else {
                        anIntArrayArrayArray666[i1][1][j1] = anIntArrayArrayArray666[i1][0][j1];
                        anIntArrayArrayArray667[i1][1][j1] = anIntArrayArrayArray667[i1][0][j1];
                    }
                }

            }

            if (j != 0 || anIntArray668[1] != anIntArray668[0]) {
                class29.decodeSegments(s);
            }
            return;
        } else {
            anIntArray668[0] = anIntArray668[1] = 0;
            return;
        }
    }

}
