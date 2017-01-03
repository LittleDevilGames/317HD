package rs.bzip2;

public class BZip2 {

    public static final BZip2Context context = new BZip2Context();

    public static int decompress(byte dst[], int dstLength, byte src[], int srcLength, int srcOffset) {
        synchronized (context) {
            context.src = src;
            context.nextIn = srcOffset;
            context.dst = dst;
            context.availOut = 0;
            context.srcLength = srcLength;
            context.dstLength = dstLength;
            context.bsLive = 0;
            context.bsBuff = 0;
            context.totalInLo32 = 0;
            context.totalInHi32 = 0;
            context.totalOutLo32 = 0;
            context.totalOutHi32 = 0;
            context.blockNo = 0;
            decompress(context);
            dstLength -= context.dstLength;
            BZip2Context.ll8 = null;
            return dstLength;
        }
    }

    public static void parseNextFileHeader(BZip2Context c) {
        byte stateOutCh = c.stateOutCh;
        int stateOutLen = c.stateOutLen;
        int nBlockUsed = c.nBlockUsed;
        int k0 = c.k0;
        int[] out = BZip2Context.ll8;
        int nextOut = c.nextOut;
        byte[] dst = c.dst;
        int availOut = c.availOut;
        int dstLength = c.dstLength;
        int dstLength_ = dstLength;
        int nBlockpp = c.nBlockpp + 1;
        label0:
        do {
            if (stateOutLen > 0) {
                do {
                    if (dstLength == 0) {
                        break label0;
                    }
                    if (stateOutLen == 1) {
                        break;
                    }
                    dst[availOut] = stateOutCh;
                    stateOutLen--;
                    availOut++;
                    dstLength--;
                } while (true);
                if (dstLength == 0) {
                    stateOutLen = 1;
                    break;
                }
                dst[availOut] = stateOutCh;
                availOut++;
                dstLength--;
            }
            boolean flag = true;
            while (flag) {
                flag = false;
                if (nBlockUsed == nBlockpp) {
                    stateOutLen = 0;
                    break label0;
                }
                stateOutCh = (byte) k0;
                nextOut = out[nextOut];
                byte byte0 = (byte) (nextOut & 0xff);
                nextOut >>= 8;
                nBlockUsed++;
                if (byte0 != k0) {
                    k0 = byte0;
                    if (dstLength == 0) {
                        stateOutLen = 1;
                    } else {
                        dst[availOut] = stateOutCh;
                        availOut++;
                        dstLength--;
                        flag = true;
                        continue;
                    }
                    break label0;
                }
                if (nBlockUsed != nBlockpp) {
                    continue;
                }
                if (dstLength == 0) {
                    stateOutLen = 1;
                    break label0;
                }
                dst[availOut] = stateOutCh;
                availOut++;
                dstLength--;
                flag = true;
            }
            stateOutLen = 2;
            nextOut = out[nextOut];
            byte byte1 = (byte) (nextOut & 0xff);
            nextOut >>= 8;
            if (++nBlockUsed != nBlockpp) {
                if (byte1 != k0) {
                    k0 = byte1;
                } else {
                    stateOutLen = 3;
                    nextOut = out[nextOut];
                    byte byte2 = (byte) (nextOut & 0xff);
                    nextOut >>= 8;
                    if (++nBlockUsed != nBlockpp) {
                        if (byte2 != k0) {
                            k0 = byte2;
                        } else {
                            nextOut = out[nextOut];
                            byte byte3 = (byte) (nextOut & 0xff);
                            nextOut >>= 8;
                            nBlockUsed++;
                            stateOutLen = (byte3 & 0xff) + 4;
                            nextOut = out[nextOut];
                            k0 = (byte) (nextOut & 0xff);
                            nextOut >>= 8;
                            nBlockUsed++;
                        }
                    }
                }
            }
        } while (true);
        int i2 = c.totalOutLo32;
        c.totalOutLo32 += dstLength_ - dstLength;
        if (c.totalOutLo32 < i2) {
            c.totalOutHi32++;
        }
        c.stateOutCh = stateOutCh;
        c.stateOutLen = stateOutLen;
        c.nBlockUsed = nBlockUsed;
        c.k0 = k0;
        BZip2Context.ll8 = out;
        c.nextOut = nextOut;
        c.dst = dst;
        c.availOut = availOut;
        c.dstLength = dstLength;
    }

    public static void decompress(BZip2Context c) {
        int tMinLen = 0;
        int tLimit[] = null;
        int tBase[] = null;
        int tPerm[] = null;
        c.blockSize100k = 1;
        if (BZip2Context.ll8 == null) {
            BZip2Context.ll8 = new int[c.blockSize100k * 0x186a0];
        }
        boolean flag19 = true;
        while (flag19) {
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            c.blockNo++;
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            readUnsignedByte(c);
            byte randomized = readBit(c);
            if (randomized != 0) {
                c.randomized = true;
            } else {
                c.randomized = false;
            }
            if (c.randomized) {
                System.out.println("PANIC! RANDOMISED BLOCK!");
            }
            c.origPtr = 0;
            byte msb = readUnsignedByte(c);
            c.origPtr = c.origPtr << 8 | msb & 0xff;
            byte ssb = readUnsignedByte(c);
            c.origPtr = c.origPtr << 8 | ssb & 0xff;
            byte lsb = readUnsignedByte(c);
            c.origPtr = c.origPtr << 8 | lsb & 0xff;
            for (int j = 0; j < 16; j++) {
                c.inUse16[j] = readBit(c) == 1;
            }

            for (int k = 0; k < 256; k++) {
                c.inUse[k] = false;
            }

            for (int l = 0; l < 16; l++) {
                if (c.inUse16[l]) {
                    for (int i3 = 0; i3 < 16; i3++) {
                        if (readBit(c) == 1) {
                            c.inUse[l * 16 + i3] = true;
                        }
                    }

                }
            }

            makeMaps(c);
            int alphaSize = c.nInUse + 2;
            int groups = readBits(3, c);
            int k4 = readBits(15, c);
            for (int i1 = 0; i1 < k4; i1++) {
                int selectorValue = 0;
                do {
                    if (readBit(c) == 0) {
                        break;
                    }
                    selectorValue++;
                } while (true);
                c.selectorMtf[i1] = (byte) selectorValue;
            }

            byte[] pos = new byte[6];
            for (byte byte16 = 0; byte16 < groups; byte16++) {
                pos[byte16] = byte16;
            }

            for (int selectorIdx = 0; selectorIdx < k4; selectorIdx++) {
                byte selectorMtf = c.selectorMtf[selectorIdx];
                byte curSelectorMtf = pos[selectorMtf];
                for (; selectorMtf > 0; selectorMtf--) {
                    pos[selectorMtf] = pos[selectorMtf - 1];
                }

                pos[0] = curSelectorMtf;
                c.selector[selectorIdx] = curSelectorMtf;
            }

            for (int i = 0; i < groups; i++) {
                int curr = readBits(5, c);
                for (int k1 = 0; k1 < alphaSize; k1++) {
                    do {
                        if (readBit(c) == 0) {
                            break;
                        }

                        if (readBit(c) == 0) {
                            curr++;
                        } else {
                            curr--;
                        }
                    } while (true);
                    c.len[i][k1] = (byte) curr;
                }

            }

            for (int l3 = 0; l3 < groups; l3++) {
                byte minLen = 32;
                int maxLen = 0;
                for (int l1 = 0; l1 < alphaSize; l1++) {
                    if (c.len[l3][l1] > maxLen) {
                        maxLen = c.len[l3][l1];
                    }
                    if (c.len[l3][l1] < minLen) {
                        minLen = c.len[l3][l1];
                    }
                }

                createDecodeTables(c.limit[l3], c.base[l3], c.perm[l3], c.len[l3], minLen, maxLen, alphaSize);
                c.minLens[l3] = minLen;
            }

            int endOfBlock = c.nInUse + 1;
            int groupNo = -1;
            int groupPos = 0;
            for (int i2 = 0; i2 <= 255; i2++) {
                c.unzftab[i2] = 0;
            }

            int kk = 4095;
            for (int l8 = 15; l8 >= 0; l8--) {
                for (int i9 = 15; i9 >= 0; i9--) {
                    c.yy[kk] = (byte) (l8 * 16 + i9);
                    kk--;
                }

                c.mtf16[l8] = kk + 1;
            }

            int last = 0;
            if (groupPos == 0) {
                groupNo++;
                groupPos = 50;
                byte byte12 = c.selector[groupNo];
                tMinLen = c.minLens[byte12];
                tLimit = c.limit[byte12];
                tPerm = c.perm[byte12];
                tBase = c.base[byte12];
            }

            groupPos--;
            int zt = tMinLen;
            int zvec;
            byte bit;
            for (zvec = readBits(zt, c); zvec > tLimit[zt]; zvec = zvec << 1 | bit) {
                zt++;
                bit = readBit(c);
            }

            for (int nextSym = tPerm[zvec - tBase[zt]]; nextSym != endOfBlock; ) {
                if (nextSym == 0 || nextSym == 1) {
                    int es = -1;
                    int k6 = 1;
                    do {
                        if (nextSym == 0) {
                            es += k6;
                        } else if (nextSym == 1) {
                            es += 2 * k6;
                        }
                        k6 *= 2;
                        if (groupPos == 0) {
                            groupNo++;
                            groupPos = 50;
                            byte tSelector = c.selector[groupNo];
                            tMinLen = c.minLens[tSelector];
                            tLimit = c.limit[tSelector];
                            tPerm = c.perm[tSelector];
                            tBase = c.base[tSelector];
                        }
                        groupPos--;
                        int j7 = tMinLen;
                        int i8;
                        byte byte10;
                        for (i8 = readBits(j7, c); i8 > tLimit[j7]; i8 = i8 << 1 | byte10) {
                            j7++;
                            byte10 = readBit(c);
                        }

                        nextSym = tPerm[i8 - tBase[j7]];
                    } while (nextSym == 0 || nextSym == 1);
                    es++;
                    byte byte5 = c.seqToUnseq[c.yy[c.mtf16[0]] & 0xff];
                    c.unzftab[byte5 & 0xff] += es;
                    for (; es > 0; es--) {
                        BZip2Context.ll8[last] = byte5 & 0xff;
                        last++;
                    }

                } else {
                    int nn = nextSym - 1;
                    byte tmp;
                    if (nn < 16) {
                        int j10 = c.mtf16[0];
                        tmp = c.yy[j10 + nn];
                        for (; nn > 3; nn -= 4) {
                            int k11 = j10 + nn;
                            c.yy[k11] = c.yy[k11 - 1];
                            c.yy[k11 - 1] = c.yy[k11 - 2];
                            c.yy[k11 - 2] = c.yy[k11 - 3];
                            c.yy[k11 - 3] = c.yy[k11 - 4];
                        }

                        for (; nn > 0; nn--) {
                            c.yy[j10 + nn] = c.yy[(j10 + nn) - 1];
                        }

                        c.yy[j10] = tmp;
                    } else {
                        int l10 = nn / 16;
                        int i11 = nn % 16;
                        int k10 = c.mtf16[l10] + i11;
                        tmp = c.yy[k10];
                        for (; k10 > c.mtf16[l10]; k10--) {
                            c.yy[k10] = c.yy[k10 - 1];
                        }

                        c.mtf16[l10]++;
                        for (; l10 > 0; l10--) {
                            c.mtf16[l10]--;
                            c.yy[c.mtf16[l10]] = c.yy[(c.mtf16[l10 - 1] + 16) - 1];
                        }

                        c.mtf16[0]--;
                        c.yy[c.mtf16[0]] = tmp;
                        if (c.mtf16[0] == 0) {
                            int i10 = 4095;
                            for (int k9 = 15; k9 >= 0; k9--) {
                                for (int l9 = 15; l9 >= 0; l9--) {
                                    c.yy[i10] = c.yy[c.mtf16[k9] + l9];
                                    i10--;
                                }

                                c.mtf16[k9] = i10 + 1;
                            }

                        }
                    }
                    c.unzftab[c.seqToUnseq[tmp & 0xff] & 0xff]++;
                    BZip2Context.ll8[last] = c.seqToUnseq[tmp & 0xff] & 0xff;
                    last++;
                    if (groupPos == 0) {
                        groupNo++;
                        groupPos = 50;
                        byte byte14 = c.selector[groupNo];
                        tMinLen = c.minLens[byte14];
                        tLimit = c.limit[byte14];
                        tPerm = c.perm[byte14];
                        tBase = c.base[byte14];
                    }
                    groupPos--;
                    int k7 = tMinLen;
                    int j8;
                    byte byte11;
                    for (j8 = readBits(k7, c); j8 > tLimit[k7]; j8 = j8 << 1 | byte11) {
                        k7++;
                        byte11 = readBit(c);
                    }

                    nextSym = tPerm[j8 - tBase[k7]];
                }
            }

            c.stateOutLen = 0;
            c.stateOutCh = 0;
            c.cftab[0] = 0;
            for (int j2 = 1; j2 <= 256; j2++) {
                c.cftab[j2] = c.unzftab[j2 - 1];
            }

            for (int k2 = 1; k2 <= 256; k2++) {
                c.cftab[k2] += c.cftab[k2 - 1];
            }

            for (int l2 = 0; l2 < last; l2++) {
                byte ch = (byte) (BZip2Context.ll8[l2] & 0xff);
                BZip2Context.ll8[c.cftab[ch & 0xff]] |= l2 << 8;
                c.cftab[ch & 0xff]++;
            }

            c.nextOut = BZip2Context.ll8[c.origPtr] >> 8;
            c.nBlockUsed = 0;
            c.nextOut = BZip2Context.ll8[c.nextOut];
            c.k0 = (byte) (c.nextOut & 0xff);
            c.nextOut >>= 8;
            c.nBlockUsed++;
            c.nBlockpp = last;
            parseNextFileHeader(c);
            if (c.nBlockUsed == c.nBlockpp + 1 && c.stateOutLen == 0) {
                flag19 = true;
            } else {
                flag19 = false;
            }
        }
    }

    public static byte readUnsignedByte(BZip2Context class32) {
        return (byte) readBits(8, class32);
    }

    public static byte readBit(BZip2Context class32) {
        return (byte) readBits(1, class32);
    }

    public static int readBits(int i, BZip2Context class32) {
        int j;
        do {
            if (class32.bsLive >= i) {
                int k = class32.bsBuff >> class32.bsLive - i & (1 << i) - 1;
                class32.bsLive -= i;
                j = k;
                break;
            }
            class32.bsBuff = class32.bsBuff << 8 | class32.src[class32.nextIn] & 0xff;
            class32.bsLive += 8;
            class32.nextIn++;
            class32.srcLength--;
            class32.totalInLo32++;
            if (class32.totalInLo32 == 0) {
                class32.totalInHi32++;
            }
        } while (true);
        return j;
    }

    public static void makeMaps(BZip2Context class32) {
        class32.nInUse = 0;
        for (int i = 0; i < 256; i++) {
            if (class32.inUse[i]) {
                class32.seqToUnseq[class32.nInUse] = (byte) i;
                class32.nInUse++;
            }
        }

    }

    public static void createDecodeTables(int ai[], int ai1[], int ai2[], byte abyte0[], int i, int j, int k) {
        int l = 0;
        for (int i1 = i; i1 <= j; i1++) {
            for (int l2 = 0; l2 < k; l2++) {
                if (abyte0[l2] == i1) {
                    ai2[l] = l2;
                    l++;
                }
            }

        }

        for (int j1 = 0; j1 < 23; j1++) {
            ai1[j1] = 0;
        }

        for (int k1 = 0; k1 < k; k1++) {
            ai1[abyte0[k1] + 1]++;
        }

        for (int l1 = 1; l1 < 23; l1++) {
            ai1[l1] += ai1[l1 - 1];
        }

        for (int i2 = 0; i2 < 23; i2++) {
            ai[i2] = 0;
        }

        int i3 = 0;
        for (int j2 = i; j2 <= j; j2++) {
            i3 += ai1[j2 + 1] - ai1[j2];
            ai[j2] = i3 - 1;
            i3 <<= 1;
        }

        for (int k2 = i + 1; k2 <= j; k2++) {
            ai1[k2] = (ai[k2 - 1] + 1 << 1) - ai1[k2];
        }

    }

}
