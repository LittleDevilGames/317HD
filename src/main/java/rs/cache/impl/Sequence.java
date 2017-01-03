package rs.cache.impl;

import rs.cache.Archive;
import rs.io.Buffer;

public class Sequence {

    public static Sequence[] instance;
    public static int count;
    public boolean canRotate;
    public short frameCount;
    public int[] frameLength;
    public int[] framePrimary;
    public int[] frameSecondary;
    public short overrideShieldIndex;
    public short overrideWeaponIndex;
    public short padding;
    public short priority;
    public short resetCycle;
    public short speedFlag;
    public short type;
    public int[] vertices;
    public short walkFlag;

    public Sequence(Buffer s) {
        padding = -1;
        canRotate = false;
        priority = 5;
        overrideShieldIndex = -1;
        overrideWeaponIndex = -1;
        resetCycle = 99;
        speedFlag = -1;
        walkFlag = -1;
        type = 2;

        do {
            int i = s.readUnsignedByte();

            if (i == 0) {
                break;
            }

            if (i == 1) {
                frameCount = (short) s.readUnsignedByte();
                framePrimary = new int[frameCount];
                frameSecondary = new int[frameCount];
                frameLength = new int[frameCount];

                for (int j = 0; j < frameCount; j++) {
                    framePrimary[j] = s.readUnsignedShort();
                    frameSecondary[j] = s.readUnsignedShort();

                    if (frameSecondary[j] == 65535) {
                        frameSecondary[j] = -1;
                    }

                    frameLength[j] = s.readUnsignedShort();
                }
            } else if (i == 2) {
                padding = (short) s.readUnsignedShort();
            } else if (i == 3) {
                int k = s.readUnsignedByte();
                vertices = new int[k + 1];
                for (int j = 0; j < k; j++) {
                    vertices[j] = s.readUnsignedByte();
                }
                vertices[k] = 9999999;
            } else if (i == 4) {
                canRotate = true;
            } else if (i == 5) {
                priority = (short) s.readUnsignedByte();
            } else if (i == 6) {
                overrideShieldIndex = (short) s.readUnsignedShort();
            } else if (i == 7) {
                overrideWeaponIndex = (short) s.readUnsignedShort();
            } else if (i == 8) {
                resetCycle = (short) s.readUnsignedByte();
            } else if (i == 9) {
                speedFlag = (short) s.readUnsignedByte();
            } else if (i == 10) {
                walkFlag = (short) s.readUnsignedByte();
            } else if (i == 11) {
                type = (short) s.readUnsignedByte();
            } else {
                System.out.println("Error unrecognised seq config code: " + i);
            }
        } while (true);

        if (frameCount == 0) {
            frameCount = 1;
            framePrimary = new int[1];
            framePrimary[0] = -1;
            frameSecondary = new int[1];
            frameSecondary[0] = -1;
            frameLength = new int[1];
            frameLength[0] = -1;
        }

        if (speedFlag == -1) {
            if (vertices != null) {
                speedFlag = 2;
            } else {
                speedFlag = 0;
            }
        }

        if (walkFlag == -1) {
            if (vertices != null) {
                walkFlag = 2;
                return;
            }
            walkFlag = 0;
        }
    }

    public static void unpack(Archive a) {
        Buffer b = new Buffer(a.get("seq.dat"));

        Sequence.count = b.readUnsignedShort();
        Sequence.instance = new Sequence[Sequence.count];

        for (int i = 0; i < Sequence.count; i++) {
            Sequence.instance[i] = new Sequence(b);
        }
    }

    public int getFrameLength(int frame) {
        int i = this.frameLength[frame];

        if (i == 0) {
            SequenceFrame f = SequenceFrame.get(this.framePrimary[frame]);
            if (f != null) {
                i = this.frameLength[frame] = f.length;
            }
        }

        if (i == 0) {
            i = 1;
        }

        return i;
    }
}
