package rs.audio.model;

import rs.io.Buffer;

public class SoundEnvelope {

    public boolean aBoolean531;
    public boolean aBoolean533;
    public boolean aBoolean534;
    public int segment_count;
    public int anInt538;
    public int anInt539;
    public int form;
    public int checkpoint;
    public int segment_ptr;
    public int step;
    public int amplitude;
    public int tick;
    public int segment_duration[];
    public int segment_peak[];

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
        segment_count = b.readUnsignedByte();
        segment_duration = new int[segment_count];
        segment_peak = new int[segment_count];

        for (int i = 0; i < segment_count; i++) {
            segment_duration[i] = b.readUnsignedShort();
            segment_peak[i] = b.readUnsignedShort();
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
            amplitude = segment_peak[segment_ptr++] << 15;

            if (segment_ptr >= segment_count) {
                segment_ptr = segment_count - 1;
            }

            checkpoint = (int) (((double) segment_duration[segment_ptr] / 65536D) * (double) length);

            if (checkpoint > tick) {
                step = ((segment_peak[segment_ptr] << 15) - amplitude) / (checkpoint - tick);
            }
        }

        amplitude += step;
        tick++;

        return amplitude - step >> 15;
    }
}
