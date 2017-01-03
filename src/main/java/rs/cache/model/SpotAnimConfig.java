package rs.cache.model;

import rs.cache.Archive;
import rs.cache.impl.Sequence;
import rs.io.Buffer;
import rs.node.List;
import rs.scene.model.Model;

public class SpotAnimConfig {

    public static SpotAnimConfig[] instance;
    public static int count;
    public static List model_cache = new List(30);
    public short sequence_index;
    public Sequence seq;
    public short brightness;
    public int height;
    public short model_index;
    public int new_colors[];
    public int old_colors[];
    public int rotation;
    public int scale;
    public short specular;
    public int uid;

    public SpotAnimConfig(Buffer s) {
        this.defaults();

        LOAD:
        {
            while (true) {
                int i = s.readUnsignedByte();

                if (i >= 40 && i < 50) {
                    old_colors[i - 40] = s.readUnsignedShort();
                } else if (i >= 50 && i < 60) {
                    new_colors[i - 50] = s.readUnsignedShort();
                } else {
                    switch (i) {
                        case 0:
                            break LOAD;
                        case 1:
                            this.model_index = (short) s.readUnsignedShort();
                            continue;
                        case 2:
                            this.sequence_index = (short) s.readUnsignedShort();
                            this.seq = Sequence.instance[this.sequence_index];
                            continue;
                        case 4:
                            this.scale = s.readUnsignedShort();
                            continue;
                        case 5:
                            this.height = s.readUnsignedShort();
                            continue;
                        case 6:
                            this.rotation = s.readUnsignedByte();
                            continue;
                        case 7:
                            this.brightness = (short) s.readUnsignedByte();
                            continue;
                        case 8:
                            this.specular = (short) s.readUnsignedByte();
                            continue;
                        case 14:
                            continue;
                        default:
                            System.out.println("Unknown spotanim opcode: " + i);
                            break LOAD;
                    }
                }
            }
        }

        int i = 0;

        if (old_colors != null && old_colors.length > 0) {
            for (int j : new_colors) {
                i += j;
            }
        }

        this.uid = (this.model_index << 16) | (this.sequence_index << 8);
        this.uid += i;
    }

    public static void unpack(Archive a) {
        Buffer s = new Buffer(a.get("spotanim.dat"));

        SpotAnimConfig.count = s.readUnsignedShort();
        SpotAnimConfig.instance = new SpotAnimConfig[SpotAnimConfig.count];

        for (int i = 0; i < SpotAnimConfig.count; i++) {
            SpotAnimConfig.instance[i] = new SpotAnimConfig(s);
        }
    }

    public void defaults() {
        sequence_index = -1;
        old_colors = new int[6];
        new_colors = new int[6];
        scale = 128;
        height = 128;
    }

    public Model getModel() {
        Model m = (Model) model_cache.get(uid);

        if (m != null) {
            return m;
        }

        m = Model.get(model_index);

        if (m == null) {
            return null;
        }

        if (old_colors != null && old_colors[0] != 0) {
            m.set_colors(old_colors, new_colors);
        }

        model_cache.insert(m, uid);
        return m;
    }

}
