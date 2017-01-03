package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.IdentityKit;
import info.demmonic.hdrs.cache.impl.Sequence;
import info.demmonic.hdrs.cache.model.ActorConfig;
import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.cache.model.SpotAnimConfig;
import info.demmonic.hdrs.io.Buffer;
import info.demmonic.hdrs.media.impl.widget.CharacterDesign;
import info.demmonic.hdrs.node.List;
import info.demmonic.hdrs.node.impl.Renderable;
import info.demmonic.hdrs.util.JString;
import info.demmonic.hdrs.util.RSColor;

public class Player extends Entity {

    public static List modelCache = new List(260);

    public ActorConfig actorConfig;
    public int[] colors;
    public short combatLevel;
    public short[] equipmentIndices;
    public byte gender;
    public byte headiconFlag;
    public int locEndCycle;
    public int locX1;
    public int locY1;
    public Model locModel;
    public int locStartCycle;
    public int locX0;
    public int locY0;
    public int locX;
    public int locY;
    public int locZ;
    public boolean lowLod;
    public long modelUid;
    public String name;
    public int sceneZ;
    public int skillLevel;
    public int team;
    public long uid;
    public boolean visible;

    public Player() {
        uid = -1L;
        lowLod = false;
        colors = new int[5];
        visible = false;
        equipmentIndices = new short[12];
    }

    public Model getBuiltModel() {
        if (actorConfig != null) {
            int frame = -1;

            if (super.seqIndex >= 0 && super.seqDelayCycle == 0) {
                frame = Sequence.instance[super.seqIndex].framePrimary[super.seqFrame];
            } else if (super.moveSeqIndex >= 0) {
                frame = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
            }

            return actorConfig.getModel(null, frame, -1);
        }

        long modelUid = this.modelUid;
        int frame1 = -1;
        int frame2 = -1;
        int shieldOverride = -1;
        int weaponOverride = -1;

        if (super.seqIndex >= 0 && super.seqDelayCycle == 0) {
            Sequence a = Sequence.instance[super.seqIndex];
            frame1 = a.framePrimary[super.seqFrame];

            if (super.moveSeqIndex >= 0 && super.moveSeqIndex != super.standAnimation) {
                frame2 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
            }

            if (a.overrideShieldIndex >= 0) {
                shieldOverride = a.overrideShieldIndex;
                modelUid += shieldOverride - equipmentIndices[5] << 40;
            }

            if (a.overrideWeaponIndex >= 0) {
                weaponOverride = a.overrideWeaponIndex;
                modelUid += weaponOverride - equipmentIndices[3] << 48;
            }
        } else if (super.moveSeqIndex >= 0) {
            frame1 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
        }

        Model model = (Model) modelCache.get(modelUid);

        if (model == null) {
            boolean useCached = false;

            for (int i = 0; i < 12; i++) {
                int equip = equipmentIndices[i];

                if (weaponOverride >= 0 && i == 3) {
                    equip = weaponOverride;
                }

                if (shieldOverride >= 0 && i == 5) {
                    equip = shieldOverride;
                }

                if (equip >= 256 && equip < 512 && !IdentityKit.instance[equip - 256].isModelValid()) {
                    useCached = true;
                }

                if (equip >= 512 && !ObjConfig.get(equip - 512).isWornMeshValid(gender)) {
                    useCached = true;
                }
            }

            if (useCached) {
                if (uid != -1L) {
                    model = (Model) modelCache.get(uid);
                }

                if (model == null) {
                    return null;
                }
            }
        }

        if (model == null) {
            Model equipModels[] = new Model[12];
            int count = 0;

            for (int i = 0; i < 12; i++) {
                int index = equipmentIndices[i];

                if (weaponOverride >= 0 && i == 3) {
                    index = weaponOverride;
                }

                if (shieldOverride >= 0 && i == 5) {
                    index = shieldOverride;
                }

                if (index >= 256 && index < 512) {
                    Model idModel = IdentityKit.instance[index - 256].getMesh();
                    if (idModel != null) {
                        equipModels[count++] = idModel;
                    }
                }

                if (index >= 512) {
                    Model equipModel = ObjConfig.get(index - 512).getWornMesh(gender);
                    if (equipModel != null) {
                        equipModels[count++] = equipModel;
                    }
                }
            }

            model = new Model(count, equipModels);
            for (int i = 0; i < 5; i++) {
                if (colors[i] != 0) {
                    model.setColor(CharacterDesign.DESIGN_COLOR[i][0], CharacterDesign.DESIGN_COLOR[i][colors[i]]);
                    if (i == 1) {
                        model.setColor(CharacterDesign.TORSO_COLORS[0], CharacterDesign.TORSO_COLORS[colors[i]]);
                    }
                }
            }

            model.applyVertexWeights();
            model.applyLighting(64, 850, -30, -50, -30, true);
            modelCache.insert(model, modelUid);
            uid = modelUid;
        }

        // Doesn't apply animations.
        if (lowLod) {
            return model;
        }

        Model m = Model.temporary;
        m.replace(model, (frame1 == -1) & (frame2 == -1));

        if (frame1 != -1 && frame2 != -1) {
            m.applySequenceFrames(Sequence.instance[super.seqIndex].vertices, frame1, frame2);
        } else if (frame1 != -1) {
            m.applySequenceFrame(frame1);
        }

        m.method466();
        m.triangleGroups = null;
        m.vertexWeights = null;
        return m;
    }

    public Model getDialogModel() {
        if (!visible) {
            return null;
        }

        if (actorConfig != null) {
            return actorConfig.getDialogModel();
        }

        boolean flag = false;

        for (int i = 0; i < 12; i++) {
            int index = equipmentIndices[i];

            if (index >= 256 && index < 512 && !IdentityKit.instance[index - 256].isDialogModelValid()) {
                flag = true;
            }

            if (index >= 512 && !ObjConfig.get(index - 512).isDialogueModelValid(gender)) {
                flag = true;
            }
        }

        if (flag) {
            return null;
        }

        Model models[] = new Model[12];
        int count = 0;

        for (int i = 0; i < 12; i++) {
            int index = equipmentIndices[i];

            if (index >= 256 && index < 512) {
                Model m = IdentityKit.instance[index - 256].getDialogModel();

                if (m != null) {
                    models[count++] = m;
                }
            }

            if (index >= 512) {
                Model m = ObjConfig.get(index - 512).getDialogueModel(gender);

                if (m != null) {
                    models[count++] = m;
                }
            }
        }

        Model m = new Model(count, models);
        for (int i = 0; i < 5; i++) {
            if (colors[i] != 0) {
                m.setColor(CharacterDesign.DESIGN_COLOR[i][0], CharacterDesign.DESIGN_COLOR[i][colors[i]]);
                if (i == 1) {
                    m.setColor(CharacterDesign.TORSO_COLORS[0], CharacterDesign.TORSO_COLORS[colors[i]]);
                }
            }
        }
        return m;
    }

    @Override
    public Model getModel() {
        if (!visible) {
            return null;
        }

        Model built = this.getBuiltModel();

        if (built == null) {
            return null;
        }

        super.height = ((Renderable) (built)).height;

        built.isClickable = true;

        if (lowLod) {
            return built;
        }

        if (super.spotanimIndex != -1 && super.spotanimFrame != -1) {
            SpotAnimConfig effect = SpotAnimConfig.instance[super.spotanimIndex];
            Model saModel = effect.getModel();

            if (saModel != null) {
                Model m = new Model(true, super.spotanimFrame == -1, false, saModel);
                m.translate(0, -super.graphicOffsetY, 0);
                m.applyVertexWeights();
                m.applySequenceFrame(effect.seq.framePrimary[super.spotanimFrame]);
                m.triangleGroups = null;
                m.vertexWeights = null;

                if (effect.scale != 128 || effect.height != 128) {
                    m.scale(effect.scale, effect.height, effect.scale);
                }

                m.applyLighting(64 + effect.brightness, 850 + effect.specular, -30, -50, -30, true);
                built = new Model(2, true, new Model[]{built, m});
            }
        }

        if (this.locModel != null) {
            if (Game.loopCycle >= locEndCycle) {
                this.locModel = null;
            }
            if (Game.loopCycle >= locStartCycle && Game.loopCycle < locEndCycle) {
                Model m = this.locModel;
                m.translate(locX - super.sceneX, locZ - sceneZ, locY - super.sceneY);

                if (super.destRotation == 512) {
                    m.rotateCw();
                    m.rotateCw();
                    m.rotateCw();
                } else if (super.destRotation == 1024) {
                    m.rotateCw();
                    m.rotateCw();
                } else if (super.destRotation == 1536) {
                    m.rotateCw();
                }

                built = new Model(2, true, new Model[]{built, m});

                if (super.destRotation == 512) {
                    m.rotateCw();
                } else if (super.destRotation == 1024) {
                    m.rotateCw();
                    m.rotateCw();
                } else if (super.destRotation == 1536) {
                    m.rotateCw();
                    m.rotateCw();
                    m.rotateCw();
                }

                m.translate(super.sceneX - locX, sceneZ - locZ, super.sceneY - locY);
            }
        }

        built.isClickable = true;
        return built;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);

        if (this.skillLevel == 0) {
            sb.append(RSColor.getLevelTag(this.combatLevel));
        } else {
            sb.append(" (skill-");
            sb.append(this.skillLevel);
            sb.append(')');
        }

        return sb.toString();
    }

    public void update(Buffer s) {
        s.position = 0;

        this.gender = s.readByte();
        this.headiconFlag = s.readByte();

        this.actorConfig = null;
        this.team = 0;

        for (int i = 0; i < 12; i++) {
            int lsb = s.readUnsignedByte();

            if (lsb == 0) {
                this.equipmentIndices[i] = 0;
                continue;
            }

            this.equipmentIndices[i] = (short) ((lsb << 8) + s.readUnsignedByte());

            if (i == 0 && this.equipmentIndices[0] == 65535) {
                this.actorConfig = ActorConfig.get(s.readUnsignedShort());
                break;
            }

            if (this.equipmentIndices[i] >= 512 && this.equipmentIndices[i] - 512 < ObjConfig.count) {
                int team = ObjConfig.get(this.equipmentIndices[i] - 512).team;

                if (team != 0) {
                    this.team = team;
                }
            }

        }

        for (int i = 0; i < 5; i++) {
            int j = s.readUnsignedByte();

            if (j < 0 || j >= CharacterDesign.DESIGN_COLOR[i].length) {
                j = 0;
            }

            this.colors[i] = j;
        }

        super.standAnimation = s.readUnsignedShort();
        if (super.standAnimation == 65535) {
            super.standAnimation = -1;
        }

        super.standTurnAnimation = s.readUnsignedShort();
        if (super.standTurnAnimation == 65535) {
            super.standTurnAnimation = -1;
        }

        super.walkAnimation = s.readUnsignedShort();
        if (super.walkAnimation == 65535) {
            super.walkAnimation = -1;
        }

        super.turn180Animation = s.readUnsignedShort();
        if (super.turn180Animation == 65535) {
            super.turn180Animation = -1;
        }

        super.turnRAnimation = s.readUnsignedShort();
        if (super.turnRAnimation == 65535) {
            super.turnRAnimation = -1;
        }

        super.turnLAnimation = s.readUnsignedShort();
        if (super.turnLAnimation == 65535) {
            super.turnLAnimation = -1;
        }

        super.runAnimation = s.readUnsignedShort();
        if (super.runAnimation == 65535) {
            super.runAnimation = -1;
        }

        this.name = JString.getFormattedString(s.readLong());
        this.combatLevel = (short) s.readUnsignedByte();
        this.skillLevel = s.readUnsignedShort();
        this.visible = true;
        this.modelUid = 0L;

        for (int i = 0; i < 12; i++) {
            this.modelUid <<= 4;
            if (equipmentIndices[i] >= 256) {
                this.modelUid += equipmentIndices[i] - 256;
            }
        }

        if (equipmentIndices[0] >= 256) {
            this.modelUid += equipmentIndices[0] - 256 >> 4;
        }

        if (equipmentIndices[1] >= 256) {
            this.modelUid += equipmentIndices[1] - 256 >> 8;
        }

        for (int i = 0; i < 5; i++) {
            this.modelUid <<= 3;
            this.modelUid += colors[i];
        }

        this.modelUid <<= 1;
        this.modelUid += gender;
    }

}
