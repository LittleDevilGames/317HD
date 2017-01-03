package rs.scene.model;

import rs.Game;
import rs.cache.impl.IdentityKit;
import rs.cache.impl.Sequence;
import rs.cache.model.ActorConfig;
import rs.cache.model.ObjConfig;
import rs.cache.model.SpotAnimConfig;
import rs.io.Buffer;
import rs.media.impl.widget.CharacterDesign;
import rs.node.List;
import rs.node.impl.Renderable;
import rs.util.JString;
import rs.util.RSColor;

public class Player extends Entity {

    public static List model_cache = new List(260);

    public ActorConfig actor_override;
    public int[] colors;
    public short combatLevel;
    public short[] equipment_indices;
    public byte gender;
    public byte headicon_flag;
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
    public long model_uid;
    public String name;
    public int sceneZ;
    public int skill_level;
    public int team;
    public long uid;
    public boolean visible;

    public Player() {
        uid = -1L;
        lowLod = false;
        colors = new int[5];
        visible = false;
        equipment_indices = new short[12];
    }

    public Model get_built_model() {
        if (actor_override != null) {
            int frame = -1;

            if (super.seqIndex >= 0 && super.seqDelayCycle == 0) {
                frame = Sequence.instance[super.seqIndex].framePrimary[super.seqFrame];
            } else if (super.moveSeqIndex >= 0) {
                frame = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
            }

            return actor_override.getModel(null, frame, -1);
        }

        long model_uid = this.model_uid;
        int frame1 = -1;
        int frame2 = -1;
        int shield_override = -1;
        int weapon_override = -1;

        if (super.seqIndex >= 0 && super.seqDelayCycle == 0) {
            Sequence a = Sequence.instance[super.seqIndex];
            frame1 = a.framePrimary[super.seqFrame];

            if (super.moveSeqIndex >= 0 && super.moveSeqIndex != super.standAnimation) {
                frame2 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
            }

            if (a.override_shield_index >= 0) {
                shield_override = a.override_shield_index;
                model_uid += shield_override - equipment_indices[5] << 40;
            }

            if (a.override_weapon_index >= 0) {
                weapon_override = a.override_weapon_index;
                model_uid += weapon_override - equipment_indices[3] << 48;
            }
        } else if (super.moveSeqIndex >= 0) {
            frame1 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
        }

        Model model = (Model) model_cache.get(model_uid);

        if (model == null) {
            boolean use_cached = false;

            for (int i = 0; i < 12; i++) {
                int equip = equipment_indices[i];

                if (weapon_override >= 0 && i == 3) {
                    equip = weapon_override;
                }

                if (shield_override >= 0 && i == 5) {
                    equip = shield_override;
                }

                if (equip >= 256 && equip < 512 && !IdentityKit.instance[equip - 256].isModelValid()) {
                    use_cached = true;
                }

                if (equip >= 512 && !ObjConfig.get(equip - 512).is_worn_mesh_valid(gender)) {
                    use_cached = true;
                }
            }

            if (use_cached) {
                if (uid != -1L) {
                    model = (Model) model_cache.get(uid);
                }

                if (model == null) {
                    return null;
                }
            }
        }

        if (model == null) {
            Model equip_models[] = new Model[12];
            int count = 0;

            for (int i = 0; i < 12; i++) {
                int index = equipment_indices[i];

                if (weapon_override >= 0 && i == 3) {
                    index = weapon_override;
                }

                if (shield_override >= 0 && i == 5) {
                    index = shield_override;
                }

                if (index >= 256 && index < 512) {
                    Model id_model = IdentityKit.instance[index - 256].getMesh();
                    if (id_model != null) {
                        equip_models[count++] = id_model;
                    }
                }

                if (index >= 512) {
                    Model equip_model = ObjConfig.get(index - 512).get_worn_mesh(gender);
                    if (equip_model != null) {
                        equip_models[count++] = equip_model;
                    }
                }
            }

            model = new Model(count, equip_models);
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
            model_cache.insert(model, model_uid);
            uid = model_uid;
        }

        // Doesn't apply animations.
        if (lowLod) {
            return model;
        }

        Model m = Model.temporary;
        m.replace(model, (frame1 == -1) & (frame2 == -1));

        if (frame1 != -1 && frame2 != -1) {
            m.apply_sequence_frames(Sequence.instance[super.seqIndex].vertices, frame1, frame2);
        } else if (frame1 != -1) {
            m.applySequenceFrame(frame1);
        }

        m.method466();
        m.triangleGroups = null;
        m.vertexWeights = null;
        return m;
    }

    public Model get_dialog_model() {
        if (!visible) {
            return null;
        }

        if (actor_override != null) {
            return actor_override.get_dialog_model();
        }

        boolean flag = false;

        for (int i = 0; i < 12; i++) {
            int index = equipment_indices[i];

            if (index >= 256 && index < 512 && !IdentityKit.instance[index - 256].isDialogModelValid()) {
                flag = true;
            }

            if (index >= 512 && !ObjConfig.get(index - 512).is_dialogue_model_valid(gender)) {
                flag = true;
            }
        }

        if (flag) {
            return null;
        }

        Model models[] = new Model[12];
        int count = 0;

        for (int i = 0; i < 12; i++) {
            int index = equipment_indices[i];

            if (index >= 256 && index < 512) {
                Model m = IdentityKit.instance[index - 256].get_dialog_model();

                if (m != null) {
                    models[count++] = m;
                }
            }

            if (index >= 512) {
                Model m = ObjConfig.get(index - 512).get_dialogue_model(gender);

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

        Model built = this.get_built_model();

        if (built == null) {
            return null;
        }

        super.height = ((Renderable) (built)).height;

        built.is_clickable = true;

        if (lowLod) {
            return built;
        }

        if (super.spotanimIndex != -1 && super.spotanimFrame != -1) {
            SpotAnimConfig effect = SpotAnimConfig.instance[super.spotanimIndex];
            Model sa_model = effect.getModel();

            if (sa_model != null) {
                Model m = new Model(true, super.spotanimFrame == -1, false, sa_model);
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
                    m.rotate_cw();
                    m.rotate_cw();
                    m.rotate_cw();
                } else if (super.destRotation == 1024) {
                    m.rotate_cw();
                    m.rotate_cw();
                } else if (super.destRotation == 1536) {
                    m.rotate_cw();
                }

                built = new Model(2, true, new Model[]{built, m});

                if (super.destRotation == 512) {
                    m.rotate_cw();
                } else if (super.destRotation == 1024) {
                    m.rotate_cw();
                    m.rotate_cw();
                } else if (super.destRotation == 1536) {
                    m.rotate_cw();
                    m.rotate_cw();
                    m.rotate_cw();
                }

                m.translate(super.sceneX - locX, sceneZ - locZ, super.sceneY - locY);
            }
        }

        built.is_clickable = true;
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

        if (this.skill_level == 0) {
            sb.append(RSColor.getLevelTag(this.combatLevel));
        } else {
            sb.append(" (skill-");
            sb.append(this.skill_level);
            sb.append(')');
        }

        return sb.toString();
    }

    public void update(Buffer s) {
        s.position = 0;

        this.gender = s.readByte();
        this.headicon_flag = s.readByte();

        this.actor_override = null;
        this.team = 0;

        for (int i = 0; i < 12; i++) {
            int lsb = s.readUnsignedByte();

            if (lsb == 0) {
                this.equipment_indices[i] = 0;
                continue;
            }

            this.equipment_indices[i] = (short) ((lsb << 8) + s.readUnsignedByte());

            if (i == 0 && this.equipment_indices[0] == 65535) {
                this.actor_override = ActorConfig.get(s.readUnsignedShort());
                break;
            }

            if (this.equipment_indices[i] >= 512 && this.equipment_indices[i] - 512 < ObjConfig.count) {
                int team = ObjConfig.get(this.equipment_indices[i] - 512).team;

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

        super.stand_turn_animation = s.readUnsignedShort();
        if (super.stand_turn_animation == 65535) {
            super.stand_turn_animation = -1;
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

        super.run_animation = s.readUnsignedShort();
        if (super.run_animation == 65535) {
            super.run_animation = -1;
        }

        this.name = JString.getFormattedString(s.readLong());
        this.combatLevel = (short) s.readUnsignedByte();
        this.skill_level = s.readUnsignedShort();
        this.visible = true;
        this.model_uid = 0L;

        for (int i = 0; i < 12; i++) {
            this.model_uid <<= 4;
            if (equipment_indices[i] >= 256) {
                this.model_uid += equipment_indices[i] - 256;
            }
        }

        if (equipment_indices[0] >= 256) {
            this.model_uid += equipment_indices[0] - 256 >> 4;
        }

        if (equipment_indices[1] >= 256) {
            this.model_uid += equipment_indices[1] - 256 >> 8;
        }

        for (int i = 0; i < 5; i++) {
            this.model_uid <<= 3;
            this.model_uid += colors[i];
        }

        this.model_uid <<= 1;
        this.model_uid += gender;
    }

}
