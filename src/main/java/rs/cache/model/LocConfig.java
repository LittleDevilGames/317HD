package rs.cache.model;

import rs.Game;
import rs.cache.Archive;
import rs.cache.impl.VarBit;
import rs.io.Buffer;
import rs.io.OnDemand;
import rs.node.List;
import rs.scene.model.Model;

public class LocConfig {

    public static Buffer buffer;
    public static LocConfig[] cache;
    public static int cachePosition;
    public static int count;
    public static List modelCache = new List(30);
    public static int[] pointer;
    public static List staticModelCache = new List(500);
    public static Model[] tmpModel = new Model[4];
    public String[] action;
    public boolean adjustToTerrain;
    public boolean blocksProjectiles;
    public byte brightness;
    public boolean castsShadow;
    public String description;
    public int faceFlags;
    public boolean flatShading;
    public boolean ghost;
    public boolean hasCollisions;
    public short icon;
    public int index;
    public boolean isDecoration;
    public boolean isSolid;
    public boolean isStatic;
    public short[] modelIndex;
    public byte[] model_type;
    public String name;
    public int[] newColor;
    public short offsetX;
    public short offsetY;
    public short offsetZ;
    public int[] oldColor;
    public short[] overrideIndex;
    public int raisesItemPiles;
    public boolean rotateCcw;
    public short scaleX;
    public short scaleY;
    public short scaleZ;
    public int sceneImageIndex;
    public short seqIndex;
    public short settingIndex;
    public byte sizeX;
    public byte sizeY;
    public byte specular;
    public short varbitIndex;
    public byte wallWidth;

    public LocConfig() {
        index = -1;
    }

    public static LocConfig get(int i) {
        if (i > pointer.length) {
            System.out.println("Invalid loc index: " + i);
            return null;
        }

        for (LocConfig config : LocConfig.cache) {
            if (config.index == i) {
                return config;
            }
        }

        cachePosition = (cachePosition + 1) % 20;
        LocConfig config = cache[cachePosition];
        buffer.position = pointer[i];
        config.index = i;
        config.setDefaults();
        config.load(buffer);
        return config;
    }

    public static void nullify() {
        staticModelCache = null;
        modelCache = null;
        pointer = null;
        cache = null;
        buffer = null;
    }

    public static void unpack(Archive a) {
        buffer = new Buffer(a.get("loc.dat", null));
        Buffer s = new Buffer(a.get("loc.idx", null));
        count = s.readUnsignedShort();
        pointer = new int[count];

        int i = 2;
        for (int j = 0; j < count; j++) {
            pointer[j] = i;
            i += s.readUnsignedShort();
        }

        cache = new LocConfig[20];

        for (int k = 0; k < 20; k++) {
            cache[k] = new LocConfig();
        }
    }

    public Model getAssembledModel(int type, int frame, int rotation) {
        Model m = null;
        long uid;

        if (model_type == null) {
            if (type != 10) {
                return null;
            }

            uid = (long) ((index << 6) + rotation) + ((long) (frame + 1) << 32);

            m = (Model) modelCache.get(uid);

            if (m != null) {
                return m;
            }

            if (modelIndex == null) {
                return null;
            }

            boolean rotate = rotateCcw ^ (rotation > 3);
            int count = modelIndex.length;

            for (int i = 0; i < count; i++) {
                int index = modelIndex[i];

                if (rotate) {
                    index += 0x10000;
                }

                m = (Model) staticModelCache.get(index);

                if (m == null) {
                    m = Model.get(index & 0xffff);

                    if (m == null) {
                        return null;
                    }

                    if (rotate) {
                        m.rotate_ccw();
                    }

                    staticModelCache.insert(m, index);
                }

                if (count > 1) {
                    tmpModel[i] = m;
                }
            }

            if (count > 1) {
                m = new Model(count, tmpModel);
            }
        } else {
            int model_list_index = -1;

            for (int i = 0; i < model_type.length; i++) {
                if (model_type[i] != type) {
                    continue;
                }
                model_list_index = i;
                break;
            }

            if (model_list_index == -1) {
                return null;
            }

            uid = (long) ((index << 6) + (model_list_index << 3) + rotation) + ((long) (frame + 1) << 32);

            Model cachedMesh = (Model) modelCache.get(uid);

            if (cachedMesh != null) {
                return cachedMesh;
            }

            int mesh_index = modelIndex[model_list_index];
            boolean rotate = rotateCcw ^ (rotation > 3);

            if (rotate) {
                mesh_index += 0x10000;
            }

            m = (Model) staticModelCache.get(mesh_index);

            if (m == null) {
                m = Model.get(mesh_index & 0xffff);

                if (m == null) {
                    return null;
                }

                if (rotate) {
                    m.rotate_ccw();
                }

                staticModelCache.insert(m, mesh_index);
            }
        }

        boolean rescale, translate;

        if (scaleX != 128 || scaleY != 128 || scaleZ != 128) {
            rescale = true;
        } else {
            rescale = false;
        }

        if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
            translate = true;
        } else {
            translate = false;
        }

        Model model = new Model(oldColor == null, frame == -1, rotation == 0 && frame == -1 && !rescale && !translate, m);

        if (frame != -1) {
            model.applyVertexWeights();
            model.applySequenceFrame(frame);
            model.triangleGroups = null;
            model.vertexWeights = null;
        }

        while (rotation-- > 0) {
            model.rotate_cw();
        }

        if (oldColor != null) {
            model.setColors(oldColor, newColor);
        }

        if (rescale) {
            model.scale(scaleX, scaleY, scaleZ);
        }

        if (translate) {
            model.translate(offsetX, offsetY, offsetZ);
        }

        model.applyLighting(64 + brightness, 768 + specular * 5, -50, -10, -50, !flatShading);

        if (raisesItemPiles == 1) {
            model.pile_height = model.height;
        }

        modelCache.insert(model, uid);
        return model;

    }

    public Model getModel(int type, int rotation, int v_sw, int v_se, int v_ne, int v_nw, int frame) {
        Model m = getAssembledModel(type, frame, rotation);

        if (m == null) {
            return null;
        }

        if (adjustToTerrain || flatShading) {
            m = new Model(adjustToTerrain, flatShading, m);
        }

        if (adjustToTerrain) {
            int v_avg = (v_sw + v_se + v_ne + v_nw) / 4;

            for (int i = 0; i < m.vertex_count; i++) {
                int x = m.vertex_x[i];
                int z = m.vertex_z[i];
                int l2 = v_sw + ((v_se - v_sw) * (x + 64)) / 128;
                int i3 = v_nw + ((v_ne - v_nw) * (x + 64)) / 128;
                int v_y = l2 + ((i3 - l2) * (z + 64)) / 128;
                m.vertex_y[i] += v_y - v_avg;
            }

            m.method467();
        }
        return m;
    }

    public LocConfig getOverridingConfig() {
        int i = -1;

        if (varbitIndex != -1) {
            VarBit varbit = VarBit.instance[varbitIndex];
            int j = varbit.setting;
            int k = varbit.offset;
            int l = varbit.shift;
            int i1 = Game.LSB_BIT_MASK[l - k];
            i = Game.settings[j] >> k & i1;
        } else if (settingIndex != -1) {
            i = Game.settings[settingIndex];
        }

        if (i < 0 || i >= overrideIndex.length || overrideIndex[i] == -1) {
            return null;
        }

        return get(overrideIndex[i]);
    }

    public boolean hasValidModel() {
        if (modelIndex == null) {
            return true;
        }
        boolean flag1 = true;

        for (int i = 0; i < modelIndex.length; i++) {
            flag1 &= Model.isValid(modelIndex[i] & 0xffff);
        }

        return flag1;
    }

    public boolean isValidModel(int type) {
        if (model_type == null) {
            if (modelIndex == null) {
                return true;
            }

            if (type != 10) {
                return true;
            }

            boolean valid = true;

            for (int i = 0; i < modelIndex.length; i++) {
                valid &= Model.isValid(modelIndex[i] & 0xffff);
            }

            return valid;
        }

        for (int i = 0; i < model_type.length; i++) {
            if (model_type[i] == type) {
                return Model.isValid(modelIndex[i] & 0xffff);
            }
        }

        return true;
    }

    public void load(Buffer b) {
        int i = -1;
        LOAD:
        do {
            int opcode;
            do {
                opcode = b.readUnsignedByte();
                if (opcode == 0) {
                    break LOAD;
                }

                if (opcode == 1) {
                    int count = b.readUnsignedByte();
                    if (count > 0) {
                        if (modelIndex == null || Game.lowDetail) {
                            modelIndex = new short[count];
                            model_type = new byte[count];
                            for (int j = 0; j < count; j++) {
                                modelIndex[j] = (short) b.readUnsignedShort();
                                model_type[j] = b.readByte();
                            }
                        } else {
                            b.position += count * 3;
                        }
                    }
                } else if (opcode == 2) {
                    name = b.readString();
                } else if (opcode == 3) {
                    description = b.readString();
                } else if (opcode == 5) {
                    int count = b.readUnsignedByte();
                    if (count > 0) {
                        if (modelIndex == null || Game.lowDetail) {
                            model_type = null;
                            modelIndex = new short[count];
                            for (int j = 0; j < count; j++) {
                                modelIndex[j] = (short) b.readUnsignedShort();
                            }
                        } else {
                            b.position += count * 2;
                        }
                    }
                } else if (opcode == 14) {
                    sizeX = (byte) b.readUnsignedByte();
                } else if (opcode == 15) {
                    sizeY = (byte) b.readUnsignedByte();
                } else if (opcode == 17) {
                    hasCollisions = false;
                } else if (opcode == 18) {
                    blocksProjectiles = false;
                } else if (opcode == 19) {
                    i = b.readUnsignedByte();
                    if (i == 1) {
                        isStatic = true;
                    }
                } else if (opcode == 21) {
                    adjustToTerrain = true;
                } else if (opcode == 22) {
                    flatShading = true;
                } else if (opcode == 23) {
                    isSolid = true;
                } else if (opcode == 24) {
                    seqIndex = (short) b.readUnsignedShort();
                } else if (opcode == 28) {
                    wallWidth = b.readByte();
                } else if (opcode == 29) {
                    brightness = b.readByte();
                } else if (opcode == 39) {
                    specular = b.readByte();
                } else if (opcode >= 30 && opcode < 39) {
                    if (action == null) {
                        action = new String[5];
                    }

                    action[opcode - 30] = b.readString();

                    if (action[opcode - 30].equalsIgnoreCase("hidden")) {
                        action[opcode - 30] = null;
                    }
                } else if (opcode == 40) {
                    int j = b.readUnsignedByte();
                    oldColor = new int[j];
                    newColor = new int[j];

                    for (int k = 0; k < j; k++) {
                        oldColor[k] = b.readUnsignedShort();
                        newColor[k] = b.readUnsignedShort();
                    }
                } else if (opcode == 60) {
                    icon = (short) b.readUnsignedShort();
                } else if (opcode == 62) {
                    rotateCcw = true;
                } else if (opcode == 64) {
                    castsShadow = false;
                } else if (opcode == 65) {
                    scaleX = (short) b.readUnsignedShort();
                } else if (opcode == 66) {
                    scaleY = (short) b.readUnsignedShort();
                } else if (opcode == 67) {
                    scaleZ = (short) b.readUnsignedShort();
                } else if (opcode == 68) {
                    sceneImageIndex = b.readUnsignedShort();
                } else if (opcode == 69) {
                    faceFlags = b.readUnsignedByte();
                } else if (opcode == 70) {
                    offsetX = (short) b.readUnsignedShort();
                } else if (opcode == 71) {
                    offsetY = (short) b.readUnsignedShort();
                } else if (opcode == 72) {
                    offsetZ = (short) b.readUnsignedShort();
                } else if (opcode == 73) {
                    isDecoration = true;
                } else if (opcode == 74) {
                    ghost = true;
                } else {
                    if (opcode != 75) {
                        continue;
                    }
                    raisesItemPiles = b.readUnsignedByte();
                }
                continue LOAD;
            } while (opcode != 77);

            varbitIndex = (short) b.readUnsignedShort();
            settingIndex = (short) b.readUnsignedShort();
            overrideIndex = new short[b.readUnsignedByte() + 1];

            for (int j = 0; j <= overrideIndex.length - 1; j++) {
                overrideIndex[j] = (short) b.readUnsignedShort();
            }
        } while (true);

        if (i == -1) {
            isStatic = false;

            if (modelIndex != null && (model_type == null || model_type[0] == 10)) {
                isStatic = true;
            }

            if (action != null) {
                isStatic = true;
            }
        }

        if (ghost) {
            hasCollisions = false;
            blocksProjectiles = false;
        }

        if (raisesItemPiles == -1) {
            raisesItemPiles = hasCollisions ? 1 : 0;
        }
    }

    public void requestModels(OnDemand ondemand) {
        if (modelIndex == null) {
            return;
        }

        for (int i = 0; i < modelIndex.length; i++) {
            ondemand.request(modelIndex[i] & 0xffff, 0);
        }
    }

    public void setDefaults() {
        modelIndex = null;
        model_type = null;
        name = null;
        description = null;
        oldColor = null;
        newColor = null;
        sizeX = 1;
        sizeY = 1;
        hasCollisions = true;
        blocksProjectiles = true;
        isStatic = false;
        adjustToTerrain = false;
        flatShading = false;
        isSolid = false;
        seqIndex = -1;
        wallWidth = 16;
        brightness = 0;
        specular = 0;
        action = null;
        icon = -1;
        sceneImageIndex = -1;
        rotateCcw = false;
        castsShadow = true;
        scaleX = 128;
        scaleY = 128;
        scaleZ = 128;
        faceFlags = 0;
        offsetX = 0;
        offsetY = 0;
        offsetZ = 0;
        isDecoration = false;
        ghost = false;
        raisesItemPiles = -1;
        varbitIndex = -1;
        settingIndex = -1;
        overrideIndex = null;
    }

}
