package rs.scene.model;

import rs.cache.impl.Sequence;
import rs.cache.model.ActorConfig;
import rs.cache.model.SpotAnimConfig;

public class Actor extends Entity {

    public ActorConfig config;

    public Actor() {
    }

    public Model getBuiltModel() {
        int frame1 = -1;

        if (super.seq_index >= 0 && super.seq_delay_cycle == 0) {
            frame1 = Sequence.instance[super.seq_index].framePrimary[super.seq_frame];
            int frame2 = -1;

            if (super.move_seq_index >= 0 && super.move_seq_index != super.stand_animation) {
                frame2 = Sequence.instance[super.move_seq_index].framePrimary[super.move_seq_frame];
            }

            return config.getModel(Sequence.instance[super.seq_index].vertices, frame1, frame2);
        }

        if (super.move_seq_index >= 0) {
            frame1 = Sequence.instance[super.move_seq_index].framePrimary[super.move_seq_frame];
        }

        return config.getModel(null, frame1, -1);
    }

    @Override
    public Model getModel() {
        if (config == null) {
            return null;
        }

        Model model = getBuiltModel();

        if (model == null) {
            return null;
        }

        super.height = model.height;

        if (super.spotanimIndex != -1 && super.spotanimFrame != -1) {
            SpotAnimConfig c = SpotAnimConfig.instance[super.spotanimIndex];
            Model m = c.getModel();

            if (m != null) {
                int anim = c.seq.framePrimary[super.spotanimFrame];

                Model m1 = new Model(true, anim == -1, false, m);
                m1.translate(0, -super.graphic_offset_y, 0);
                m1.applyVertexWeights();
                m1.applySequenceFrame(anim);
                m1.triangleGroups = null;
                m1.vertexWeights = null;

                if (c.scale != 128 || c.height != 128) {
                    m1.scale(c.scale, c.height, c.scale);
                }

                m1.applyLighting(64 + c.brightness, 850 + c.specular, -30, -50, -30, true);
                model = new Model(2, true, new Model[]{model, m1});
            }

        }

        if (config.has_options == 1) {
            model.is_clickable = true;
        }
        return model;
    }

    @Override
    public boolean isVisible() {
        return config != null;
    }
}
