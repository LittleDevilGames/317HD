package info.demmonic.hdrs.io;

public final class IsaacCipher {

    private static final int GOLDEN_RATIO = 0x9e3779b9;

    private static final int SIZEL = 8;

    private static final int SIZE = 1 << IsaacCipher.SIZEL;

    private static int MASK = (IsaacCipher.SIZE - 1) << 2;

    private final int[] mem;

    private final int[] rsl;

    private int a;

    private int b;

    private int c;

    private int count;

    public IsaacCipher() {
        mem = new int[IsaacCipher.SIZE];
        rsl = new int[IsaacCipher.SIZE];
        init(false);
    }

    public IsaacCipher(int[] seed) {
        mem = new int[IsaacCipher.SIZE];
        rsl = new int[IsaacCipher.SIZE];
        for (int i = 0; i < seed.length; ++i) {
            rsl[i] = seed[i];
        }
        init(true);
    }

    private void init(boolean flag) {
        int i;
        int a, b, c, d, e, f, g, h;
        a = b = c = d = e = f = g = h = IsaacCipher.GOLDEN_RATIO;

        for (i = 0; i < 4; ++i) {
            a ^= b << 11;
            d += a;
            b += c;
            b ^= c >>> 2;
            e += b;
            c += d;
            c ^= d << 8;
            f += c;
            d += e;
            d ^= e >>> 16;
            g += d;
            e += f;
            e ^= f << 10;
            h += e;
            f += g;
            f ^= g >>> 4;
            a += f;
            g += h;
            g ^= h << 8;
            b += g;
            h += a;
            h ^= a >>> 9;
            c += h;
            a += b;
        }

        for (i = 0; i < IsaacCipher.SIZE; i += 8) {
            if (flag) {
                a += rsl[i];
                b += rsl[i + 1];
                c += rsl[i + 2];
                d += rsl[i + 3];
                e += rsl[i + 4];
                f += rsl[i + 5];
                g += rsl[i + 6];
                h += rsl[i + 7];
            }
            a ^= b << 11;
            d += a;
            b += c;
            b ^= c >>> 2;
            e += b;
            c += d;
            c ^= d << 8;
            f += c;
            d += e;
            d ^= e >>> 16;
            g += d;
            e += f;
            e ^= f << 10;
            h += e;
            f += g;
            f ^= g >>> 4;
            a += f;
            g += h;
            g ^= h << 8;
            b += g;
            h += a;
            h ^= a >>> 9;
            c += h;
            a += b;
            mem[i] = a;
            mem[i + 1] = b;
            mem[i + 2] = c;
            mem[i + 3] = d;
            mem[i + 4] = e;
            mem[i + 5] = f;
            mem[i + 6] = g;
            mem[i + 7] = h;
        }

        if (flag) {

            for (i = 0; i < IsaacCipher.SIZE; i += 8) {
                a += mem[i];
                b += mem[i + 1];
                c += mem[i + 2];
                d += mem[i + 3];
                e += mem[i + 4];
                f += mem[i + 5];
                g += mem[i + 6];
                h += mem[i + 7];
                a ^= b << 11;
                d += a;
                b += c;
                b ^= c >>> 2;
                e += b;
                c += d;
                c ^= d << 8;
                f += c;
                d += e;
                d ^= e >>> 16;
                g += d;
                e += f;
                e ^= f << 10;
                h += e;
                f += g;
                f ^= g >>> 4;
                a += f;
                g += h;
                g ^= h << 8;
                b += g;
                h += a;
                h ^= a >>> 9;
                c += h;
                a += b;
                mem[i] = a;
                mem[i + 1] = b;
                mem[i + 2] = c;
                mem[i + 3] = d;
                mem[i + 4] = e;
                mem[i + 5] = f;
                mem[i + 6] = g;
                mem[i + 7] = h;
            }
        }

        isaac();
        count = IsaacCipher.SIZE;
    }

    private void isaac() {
        int i, j, x, y;

        b += ++c;
        for (i = 0, j = IsaacCipher.SIZE / 2; i < IsaacCipher.SIZE / 2; ) {
            x = mem[i];
            a ^= a << 13;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a << 2;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;
        }

        for (j = 0; j < IsaacCipher.SIZE / 2; ) {
            x = mem[i];
            a ^= a << 13;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a << 2;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            mem[i] = y = mem[(x & IsaacCipher.MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> IsaacCipher.SIZEL) & IsaacCipher.MASK) >> 2] + x;
        }
    }

    public int nextInt() {
        if (0 == count--) {
            isaac();
            count = IsaacCipher.SIZE - 1;
        }
        return rsl[count];
    }

}
