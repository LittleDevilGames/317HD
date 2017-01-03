package info.demmonic.hdrs.cache.model;

import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.cache.impl.Sequence;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.node.List;
import info.demmonic.hdrs.scene.model.Model;

public class SpotAnimConfig {

    public static SpotAnimConfig[] instance;
    public static int count;
    public static List modelCache = new List(30);
    public short sequenceIndex;
    public Sequence seq;
    public short brightness;
    public int height;
    public short modelIndex;
    public int newColors[];
    public int oldColors[];
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
                    oldColors[i - 40] = s.readUnsignedShort();
                } else if (i >= 50 && i < 60) {
                    newColors[i - 50] = s.readUnsignedShort();
                } else {
                    switch (i) {
                        case 0:
                            break LOAD;
                        case 1:
                            this.modelIndex = (short) s.readUnsignedShort();
                            continue;
                        case 2:
                            this.sequenceIndex = (short) s.readUnsignedShort();
                            this.seq = Sequence.instance[this.sequenceIndex];
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

        if (oldColors != null && oldColors.length > 0) {
            for (int j : newColors) {
                i += j;
            }
        }

        this.uid = (this.modelIndex << 16) | (this.sequenceIndex << 8);
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
        sequenceIndex = -1;
        oldColors = new int[6];
        newColors = new int[6];
        scale = 128;
        height = 128;
    }

    public Model getModel() {
        Model m = (Model) modelCache.get(uid);

        if (m != null) {
            return m;
        }

        m = Model.get(modelIndex);

        if (m == null) {
            return null;
        }

        if (oldColors != null && oldColors[0] != 0) {
            m.setColors(oldColors, newColors);
        }

        modelCache.insert(m, uid);
        return m;
    }

}
