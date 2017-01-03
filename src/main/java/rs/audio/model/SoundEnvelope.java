package rs.audio.model;

import rs.io.Buffer;

public class SoundEnvelope {

    public boolean aBoolean531;
    public boolean aBoolean533;
    public boolean aBoolean534;
    public int segmentCount;
    public int anInt538;
    public int anInt539;
    public int form;
    public int checkpoint;
    public int segment_ptr;
    public int step;
    public int amplitude;
    public int tick;
    public int segmentDuration[];
    public int segmentPeak[];

    public SoundEnvelope() {
        aBoolean531 = false;
        aBoolean533 = false;
        aBoolean534 = true;
    }

    public void decode(Buffer b) {
        form = b.readUnsignedByte();
        anInt538 = b.readInt();
        anInt539 = b.readInt();
        decode_segments(b);
    }

    public void decode_segments(Buffer b) {
        segmentCount = b.readUnsignedByte();
        segmentDuration = new int[segmentCount];
        segmentPeak = new int[segmentCount];

        for (int i = 0; i < segmentCount; i++) {
            segmentDuration[i] = b.readUnsignedShort();
            segmentPeak[i] = b.readUnsignedShort();
        }
    }

    public void reset() {
        checkpoint = 0;
        segment_ptr = 0;
        step = 0;
        amplitude = 0;
        tick = 0;
    }

    public int evaluate(int length) {
        if (tick >= checkpoint) {
            amplitude = segmentPeak[segment_ptr++] << 15;

            if (segment_ptr >= segmentCount) {
                segment_ptr = segmentCount - 1;
            }

            checkpoint = (int) (((double) segmentDuration[segment_ptr] / 65536D) * (double) length);

            if (checkpoint > tick) {
                step = ((segmentPeak[segment_ptr] << 15) - amplitude) / (checkpoint - tick);
            }
        }

        amplitude += step;
        tick++;

        return amplitude - step >> 15;
    }
}
