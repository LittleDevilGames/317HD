package info.demmonic.hdrs.bzip2;

public class BZip2Context {

    public static int ll8[];
    public boolean randomized;
    public boolean inUse[];
    public boolean inUse16[];
    public byte stateOutCh;
    public byte src[];
    public byte dst[];
    public byte seqToUnseq[];
    public byte yy[];
    public byte selector[];
    public byte selectorMtf[];
    public byte len[][];
    public int nextIn;
    public int srcLength;
    public int totalInLo32;
    public int totalInHi32;
    public int availOut;
    public int dstLength;
    public int totalOutLo32;
    public int totalOutHi32;
    public int stateOutLen;
    public int bsBuff;
    public int bsLive;
    public int blockSize100k;
    public int blockNo;
    public int origPtr;
    public int nextOut;
    public int k0;
    public int nBlockUsed;
    public int nInUse;
    public int nBlockpp;
    public int unzftab[];
    public int cftab[];
    public int mtf16[];
    public int minLens[];
    public int limit[][];
    public int base[][];
    public int perm[][];

    public BZip2Context() {
        unzftab = new int[256];
        cftab = new int[257];
        inUse = new boolean[256];
        inUse16 = new boolean[16];
        seqToUnseq = new byte[256];
        yy = new byte[4096];
        mtf16 = new int[16];
        selector = new byte[18002];
        selectorMtf = new byte[18002];
        len = new byte[6][258];
        limit = new int[6][258];
        base = new int[6][258];
        perm = new int[6][258];
        minLens = new int[6];
    }

}
