package rs.cache.impl;

import rs.cache.Archive;
import rs.io.Buffer;

public class Sequence {

    public static Sequence[] instance;
    public static int count;
    public boolean can_rotate;
    public short frame_count;
    public int[] frame_length;
    public int[] framePrimary;
    public int[] frame_secondary;
    public short override_shield_index;
    public short override_weapon_index;
    public short padding;
    public short priority;
    public short reset_cycle;
    public short speedFlag;
    public short type;
    public int[] vertices;
    public short walk_flag;

    public Sequence(Buffer s) {
        padding = -1;
        can_rotate = false;
        priority = 5;
        override_shield_index = -1;
        override_weapon_index = -1;
        reset_cycle = 99;
        speedFlag = -1;
        walk_flag = -1;
        type = 2;

        do {
            int i = s.readUnsignedByte();

            if (i == 0) {
                break;
            }

            if (i == 1) {
                frame_count = (short) s.readUnsignedByte();
                framePrimary = new int[frame_count];
                frame_secondary = new int[frame_count];
                frame_length = new int[frame_count];

                for (int j = 0; j < frame_count; j++) {
                    framePrimary[j] = s.readUnsignedShort();
                    frame_secondary[j] = s.readUnsignedShort();

                    if (frame_secondary[j] == 65535) {
                        frame_secondary[j] = -1;
                    }

                    frame_length[j] = s.readUnsignedShort();
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
                can_rotate = true;
            } else if (i == 5) {
                priority = (short) s.readUnsignedByte();
            } else if (i == 6) {
                override_shield_index = (short) s.readUnsignedShort();
            } else if (i == 7) {
                override_weapon_index = (short) s.readUnsignedShort();
            } else if (i == 8) {
                reset_cycle = (short) s.readUnsignedByte();
            } else if (i == 9) {
                speedFlag = (short) s.readUnsignedByte();
            } else if (i == 10) {
                walk_flag = (short) s.readUnsignedByte();
            } else if (i == 11) {
                type = (short) s.readUnsignedByte();
            } else {
                System.out.println("Error unrecognised seq config code: " + i);
            }
        } while (true);

        if (frame_count == 0) {
            frame_count = 1;
            framePrimary = new int[1];
            framePrimary[0] = -1;
            frame_secondary = new int[1];
            frame_secondary[0] = -1;
            frame_length = new int[1];
            frame_length[0] = -1;
        }

        if (speedFlag == -1) {
            if (vertices != null) {
                speedFlag = 2;
            } else {
                speedFlag = 0;
            }
        }

        if (walk_flag == -1) {
            if (vertices != null) {
                walk_flag = 2;
                return;
            }
            walk_flag = 0;
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
        int i = this.frame_length[frame];

        if (i == 0) {
            SequenceFrame f = SequenceFrame.get(this.framePrimary[frame]);
            if (f != null) {
                i = this.frame_length[frame] = f.length;
            }
        }

        if (i == 0) {
            i = 1;
        }

        return i;
    }
}
