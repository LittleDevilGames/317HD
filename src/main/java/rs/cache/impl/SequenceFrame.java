package rs.cache.impl;

import rs.io.Buffer;

public class SequenceFrame {

    public static SequenceFrame[] instance;
    public int frameCount;
    public int length;
    public SkinList skinlist;
    public int vertexX[];
    public int vertexY[];
    public int vertexZ[];
    public int vertices[];

    public SequenceFrame() {
    }

    public static SequenceFrame get(int i) {
        if (instance == null) {
            return null;
        }
        return instance[i];
    }

    public static void init(int count) {
        SequenceFrame.instance = new SequenceFrame[count + 1];
    }

    public static void load(byte[] payload) {
        Buffer s = new Buffer(payload);
        s.position = payload.length - 8;

        int flagPos = s.readUnsignedShort();
        int modPos = s.readUnsignedShort();
        int lenPos = s.readUnsignedShort();
        int skinPos = s.readUnsignedShort();

        int position = 0;
        Buffer infoStream = new Buffer(payload);
        infoStream.position = position;

        position += flagPos + 2;
        Buffer flagStream = new Buffer(payload);
        flagStream.position = position;

        position += modPos;
        Buffer modifierStream = new Buffer(payload);
        modifierStream.position = position;

        position += lenPos;
        Buffer lengthStream = new Buffer(payload);
        lengthStream.position = position;

        position += skinPos;
        Buffer skinStream = new Buffer(payload);
        skinStream.position = position;

        SkinList sl = new SkinList(skinStream);

        int count = infoStream.readUnsignedShort();
        int skins[] = new int[500];
        int vertexX[] = new int[500];
        int vertexY[] = new int[500];
        int vertexZ[] = new int[500];

        for (int i = 0; i < count; i++) {
            int id = infoStream.readUnsignedShort();

            SequenceFrame a = instance[id] = new SequenceFrame();
            a.length = lengthStream.readUnsignedByte();
            a.skinlist = sl;

            int frameCount = infoStream.readUnsignedByte();
            int lastIndex = -1;
            int frameIndex = 0;

            for (int index = 0; index < frameCount; index++) {
                int vertex_mask = flagStream.readUnsignedByte();

                if (vertex_mask > 0) {
                    if (sl.opcodes[index] != 0) {
                        for (int skin = index - 1; skin > lastIndex; skin--) {
                            if (sl.opcodes[skin] != 0) {
                                continue;
                            }
                            skins[frameIndex] = skin;
                            vertexX[frameIndex] = 0;
                            vertexY[frameIndex] = 0;
                            vertexZ[frameIndex] = 0;
                            frameIndex++;
                            break;
                        }

                    }

                    skins[frameIndex] = index;
                    int vertex = 0;

                    if (sl.opcodes[index] == 3) {
                        vertex = 128;
                    }

                    if ((vertex_mask & 1) != 0) {
                        vertexX[frameIndex] = modifierStream.readSmart();
                    } else {
                        vertexX[frameIndex] = vertex;
                    }

                    if ((vertex_mask & 2) != 0) {
                        vertexY[frameIndex] = modifierStream.readSmart();
                    } else {
                        vertexY[frameIndex] = vertex;
                    }

                    if ((vertex_mask & 4) != 0) {
                        vertexZ[frameIndex] = modifierStream.readSmart();
                    } else {
                        vertexZ[frameIndex] = vertex;
                    }

                    lastIndex = index;
                    frameIndex++;
                }
            }

            a.frameCount = frameIndex;
            a.vertices = new int[frameIndex];
            a.vertexX = new int[frameIndex];
            a.vertexY = new int[frameIndex];
            a.vertexZ = new int[frameIndex];

            for (int j = 0; j < frameIndex; j++) {
                a.vertices[j] = skins[j];
                a.vertexX[j] = vertexX[j];
                a.vertexY[j] = vertexY[j];
                a.vertexZ[j] = vertexZ[j];
            }

        }

    }

    public static void nullify() {
        instance = null;
    }

}
