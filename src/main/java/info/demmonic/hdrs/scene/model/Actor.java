package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.cache.model.ActorConfig;
import info.demmonic.hdrs.cache.model.SpotAnimConfig;
import info.demmonic.hdrs.cache.impl.Sequence;

public class Actor extends Entity {

    public ActorConfig config;

    public Actor() {
    }

    public Model getBuiltModel() {
        int frame1 = -1;

        if (super.seqIndex >= 0 && super.seqDelayCycle == 0) {
            frame1 = Sequence.instance[super.seqIndex].framePrimary[super.seqFrame];
            int frame2 = -1;

            if (super.moveSeqIndex >= 0 && super.moveSeqIndex != super.standAnimation) {
                frame2 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
            }

            return config.getModel(Sequence.instance[super.seqIndex].vertices, frame1, frame2);
        }

        if (super.moveSeqIndex >= 0) {
            frame1 = Sequence.instance[super.moveSeqIndex].framePrimary[super.moveSeqFrame];
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
                m1.translate(0, -super.graphicOffsetY, 0);
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
