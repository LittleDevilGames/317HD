package rs.scene.model;

import rs.cache.model.SpotAnimConfig;
import rs.node.impl.Renderable;

public class SpotAnim extends Renderable {

    public int cycle_end;
    public SpotAnimConfig config;
    public int plane;
    public int seq_cycle;
    public boolean seq_finished;
    public int seq_frame;
    public int x;
    public int y;
    public int z;

    public SpotAnim(int x, int y, int z, int plane, int cycle, int duration, int index) {
        this.seq_finished = false;
        this.config = SpotAnimConfig.instance[index];
        this.plane = plane;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cycle_end = cycle + duration;
        this.seq_finished = false;
    }

    @Override
    public Model getModel() {
        Model effect_model = this.config.getModel();

        if (effect_model == null) {
            return null;
        }

        int frame = this.config.seq.framePrimary[this.seq_frame];
        Model m = new Model(true, frame == -1, false, effect_model);

        if (!this.seq_finished) {
            m.applyVertexWeights();
            m.applySequenceFrame(frame);
            m.triangleGroups = null;
            m.vertexWeights = null;
        }

        if (this.config.scale != 128 || this.config.height != 128) {
            m.scale(this.config.scale, this.config.height, this.config.scale);
        }

        if (this.config.rotation != 0) {
            for (int i = 0; i < this.config.rotation / 90; i++) {
                m.rotate_cw();
            }
        }

        m.applyLighting(64 + this.config.brightness, 850 + this.config.specular, -30, -50, -30, true);
        return m;
    }

    public void update(int i) {
        for (this.seq_cycle += i; this.seq_cycle > this.config.seq.getFrameLength(this.seq_frame); ) {
            this.seq_cycle -= this.config.seq.getFrameLength(this.seq_frame) + 1;
            this.seq_frame++;

            if (this.seq_frame >= this.config.seq.frame_count && (this.seq_frame < 0 || this.seq_frame >= this.config.seq.frame_count)) {
                this.seq_frame = 0;
                this.seq_finished = true;
            }
        }

    }
}
