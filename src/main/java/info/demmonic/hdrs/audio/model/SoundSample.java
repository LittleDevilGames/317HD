package info.demmonic.hdrs.audio.model;

import info.demmonic.hdrs.io.Buffer;

public class SoundSample {

    public static int encoded[];
    public static byte noise[];
    public static int sine[];
    public static int anIntArray118[] = new int[5];
    public static int anIntArray119[] = new int[5];
    public static int anIntArray120[] = new int[5];
    public static int anIntArray121[] = new int[5];
    public static int anIntArray122[] = new int[5];
    public boolean aBoolean97;
    public SoundEnvelope aClass29100;
    public SoundEnvelope aClass29101;
    public SoundEnvelope aClass29102;
    public SoundEnvelope aClass29103;
    public SoundEnvelope aClass29104;
    public SoundEnvelope aClass29105;
    public SoundEnvelope aClass29112;
    public SoundEnvelope aClass2998;
    public SoundEnvelope aClass2999;
    public SoundFilter aClass39111;
    public int anInt109;
    public int anInt110;
    public int position;
    public int remaining;
    public int anIntArray106[];
    public int anIntArray107[];
    public int anIntArray108[];

    public SoundSample() {
        aBoolean97 = true;
        anIntArray106 = new int[5];
        anIntArray107 = new int[5];
        anIntArray108 = new int[5];
        anInt110 = 100;
        position = 500;
    }

    public static void init() {
        noise = new byte[0x8000];

        for (int i = 0; i < 0x8000; i++) {
            noise[i] = (byte) ((Math.random() > 0.5D) ? 1 : -1);
        }

        sine = new int[0x8000];
        for (int j = 0; j < 0x8000; j++) {
            sine[j] = (int) (Math.sin((double) j / 5215.1903000000002D) * 0x4000D);
        }

        encoded = new int[0x35D54];
    }

    public int[] synthesize(int length, int position) {
        for (int i = 0; i < length; i++) {
            encoded[i] = 0;
        }

        if (position < 10) {
            return encoded;
        }

        double rate = (double) length / ((double) position + 0.0D);
        aClass2998.reset();
        aClass2999.reset();

        int l = 0;
        int i1 = 0;
        int j1 = 0;

        if (aClass29100 != null) {
            aClass29100.reset();
            aClass29101.reset();
            l = (int) (((double) (aClass29100.anInt539 - aClass29100.anInt538) * 32.768000000000001D) / rate);
            i1 = (int) (((double) aClass29100.anInt538 * 32.768000000000001D) / rate);
        }

        int k1 = 0;
        int l1 = 0;
        int i2 = 0;

        if (aClass29102 != null) {
            aClass29102.reset();
            aClass29103.reset();
            k1 = (int) (((double) (aClass29102.anInt539 - aClass29102.anInt538) * 32.768000000000001D) / rate);
            l1 = (int) (((double) aClass29102.anInt538 * 32.768000000000001D) / rate);
        }

        for (int j2 = 0; j2 < 5; j2++) {
            if (anIntArray106[j2] != 0) {
                anIntArray118[j2] = 0;
                anIntArray119[j2] = (int) ((double) anIntArray108[j2] * rate);
                anIntArray120[j2] = (anIntArray106[j2] << 14) / 100;
                anIntArray121[j2] = (int) (((double) (aClass2998.anInt539 - aClass2998.anInt538) * 32.768000000000001D * Math.pow(1.0057929410678534D, anIntArray107[j2])) / rate);
                anIntArray122[j2] = (int) (((double) aClass2998.anInt538 * 32.768000000000001D) / rate);
            }
        }

        for (int k2 = 0; k2 < length; k2++) {
            int l2 = aClass2998.evaluate(length);
            int j4 = aClass2999.evaluate(length);

            if (aClass29100 != null) {
                int j5 = aClass29100.evaluate(length);
                int j6 = aClass29101.evaluate(length);
                l2 += evaluate(j6, j1, aClass29100.form) >> 1;
                j1 += (j5 * l >> 16) + i1;
            }

            if (aClass29102 != null) {
                int k5 = aClass29102.evaluate(length);
                int k6 = aClass29103.evaluate(length);
                j4 = j4 * ((evaluate(k6, i2, aClass29102.form) >> 1) + 32768) >> 15;
                i2 += (k5 * k1 >> 16) + l1;
            }

            for (int l5 = 0; l5 < 5; l5++) {
                if (anIntArray106[l5] != 0) {
                    int l6 = k2 + anIntArray119[l5];
                    if (l6 < length) {
                        encoded[l6] += evaluate(j4 * anIntArray120[l5] >> 15, anIntArray118[l5], aClass2998.form);
                        anIntArray118[l5] += (l2 * anIntArray121[l5] >> 16) + anIntArray122[l5];
                    }
                }
            }

        }

        if (aClass29104 != null) {
            aClass29104.reset();
            aClass29105.reset();
            int i3 = 0;
            boolean flag1 = true;
            for (int i7 = 0; i7 < length; i7++) {
                int k7 = aClass29104.evaluate(length);
                int i8 = aClass29105.evaluate(length);
                int k4;
                if (flag1) {
                    k4 = aClass29104.anInt538 + ((aClass29104.anInt539 - aClass29104.anInt538) * k7 >> 8);
                } else {
                    k4 = aClass29104.anInt538 + ((aClass29104.anInt539 - aClass29104.anInt538) * i8 >> 8);
                }
                if ((i3 += 256) >= k4) {
                    i3 = 0;
                    flag1 = !flag1;
                }
                if (flag1) {
                    encoded[i7] = 0;
                }
            }

        }
        if (anInt109 > 0 && anInt110 > 0) {
            int j3 = (int) ((double) anInt109 * rate);
            for (int l4 = j3; l4 < length; l4++) {
                encoded[l4] += (encoded[l4 - j3] * anInt110) / 100;
            }

        }
        if (aClass39111.anIntArray665[0] > 0 || aClass39111.anIntArray665[1] > 0) {
            aClass29112.reset();
            int k3 = aClass29112.evaluate(length + 1);
            int i5 = aClass39111.method544(0, (float) k3 / 65536F, 201);
            int i6 = aClass39111.method544(1, (float) k3 / 65536F, 201);
            if (length >= i5 + i6) {
                int j7 = 0;
                int l7 = i6;
                if (l7 > length - i5) {
                    l7 = length - i5;
                }
                for (; j7 < l7; j7++) {
                    int j8 = (int) ((long) encoded[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
                    for (int k8 = 0; k8 < i5; k8++) {
                        j8 += (int) ((long) encoded[(j7 + i5) - 1 - k8] * (long) SoundFilter.anIntArrayArray670[0][k8] >> 16);
                    }

                    for (int j9 = 0; j9 < j7; j9++) {
                        j8 -= (int) ((long) encoded[j7 - 1 - j9] * (long) SoundFilter.anIntArrayArray670[1][j9] >> 16);
                    }

                    encoded[j7] = j8;
                    k3 = aClass29112.evaluate(length + 1);
                }

                char c = '\200';
                l7 = c;
                do {
                    if (l7 > length - i5) {
                        l7 = length - i5;
                    }
                    for (; j7 < l7; j7++) {
                        int l8 = (int) ((long) encoded[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
                        for (int k9 = 0; k9 < i5; k9++) {
                            l8 += (int) ((long) encoded[(j7 + i5) - 1 - k9] * (long) SoundFilter.anIntArrayArray670[0][k9] >> 16);
                        }

                        for (int i10 = 0; i10 < i6; i10++) {
                            l8 -= (int) ((long) encoded[j7 - 1 - i10] * (long) SoundFilter.anIntArrayArray670[1][i10] >> 16);
                        }

                        encoded[j7] = l8;
                        k3 = aClass29112.evaluate(length + 1);
                    }

                    if (j7 >= length - i5) {
                        break;
                    }
                    i5 = aClass39111.method544(0, (float) k3 / 65536F, 201);
                    i6 = aClass39111.method544(1, (float) k3 / 65536F, 201);
                    l7 += c;
                } while (true);
                for (; j7 < length; j7++) {
                    int i9 = 0;
                    for (int l9 = (j7 + i5) - length; l9 < i5; l9++) {
                        i9 += (int) ((long) encoded[(j7 + i5) - 1 - l9] * (long) SoundFilter.anIntArrayArray670[0][l9] >> 16);
                    }

                    for (int j10 = 0; j10 < i6; j10++) {
                        i9 -= (int) ((long) encoded[j7 - 1 - j10] * (long) SoundFilter.anIntArrayArray670[1][j10] >> 16);
                    }

                    encoded[j7] = i9;
                    aClass29112.evaluate(length + 1);
                }

            }
        }

        for (int i = 0; i < length; i++) {
            if (encoded[i] < -32768) {
                encoded[i] = -32768;
            } else if (encoded[i] > 32767) {
                encoded[i] = 32767;
            }
        }

        return encoded;
    }

    public int evaluate(int amplitude, int phase, int type) {
        if (type == 1) {
            if ((phase & 0x7FFF) < 0x4000) {
                return amplitude;
            } else {
                return -amplitude;
            }
        } else if (type == 2) {
            return sine[phase & 0x7FFF] * amplitude >> 14;
        } else if (type == 3) {
            return ((phase & 0x7FFF) * amplitude >> 14) - amplitude;
        } else if (type == 4) {
            return noise[phase / 2607 & 0x7FFF] * amplitude;
        } else {
            return 0;
        }
    }

    public void decode(Buffer b) {
        aClass2998 = new SoundEnvelope();
        aClass2998.decode(b);
        aClass2999 = new SoundEnvelope();
        aClass2999.decode(b);

        int i = b.readUnsignedByte();

        if (i != 0) {
            b.position--;
            aClass29100 = new SoundEnvelope();
            aClass29100.decode(b);
            aClass29101 = new SoundEnvelope();
            aClass29101.decode(b);
        }

        i = b.readUnsignedByte();

        if (i != 0) {
            b.position--;
            aClass29102 = new SoundEnvelope();
            aClass29102.decode(b);
            aClass29103 = new SoundEnvelope();
            aClass29103.decode(b);
        }

        i = b.readUnsignedByte();

        if (i != 0) {
            b.position--;
            aClass29104 = new SoundEnvelope();
            aClass29104.decode(b);
            aClass29105 = new SoundEnvelope();
            aClass29105.decode(b);
        }

        for (int j = 0; j < 10; j++) {
            int k = b.writeUnsignedSmart();

            if (k == 0) {
                break;
            }

            anIntArray106[j] = k;
            anIntArray107[j] = b.readSmart();
            anIntArray108[j] = b.writeUnsignedSmart();
        }

        anInt109 = b.writeUnsignedSmart();
        anInt110 = b.writeUnsignedSmart();
        position = b.readUnsignedShort();
        remaining = b.readUnsignedShort();

        aClass39111 = new SoundFilter();
        aClass29112 = new SoundEnvelope();
        aClass39111.method545(b, false, aClass29112);
    }

}
