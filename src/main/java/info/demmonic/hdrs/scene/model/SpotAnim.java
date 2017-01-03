package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.cache.model.SpotAnimConfig;
import info.demmonic.hdrs.node.impl.Renderable;

public class SpotAnim extends Renderable {

    public int cycleEnd;
    public SpotAnimConfig config;
    public int plane;
    public int seqCycle;
    public boolean seqFinished;
    public int seqFrame;
    public int x;
    public int y;
    public int z;

    public SpotAnim(int x, int y, int z, int plane, int cycle, int duration, int index) {
        this.seqFinished = false;
        this.config = SpotAnimConfig.instance[index];
        this.plane = plane;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cycleEnd = cycle + duration;
        this.seqFinished = false;
    }

    @Override
    public Model getModel() {
        Model effectModel = this.config.getModel();

        if (effectModel == null) {
            return null;
        }

        int frame = this.config.seq.framePrimary[this.seqFrame];
        Model m = new Model(true, frame == -1, false, effectModel);

        if (!this.seqFinished) {
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
                m.rotateCw();
            }
        }

        m.applyLighting(64 + this.config.brightness, 850 + this.config.specular, -30, -50, -30, true);
        return m;
    }

    public void update(int i) {
        for (this.seqCycle += i; this.seqCycle > this.config.seq.getFrameLength(this.seqFrame); ) {
            this.seqCycle -= this.config.seq.getFrameLength(this.seqFrame) + 1;
            this.seqFrame++;

            if (this.seqFrame >= this.config.seq.frameCount && (this.seqFrame < 0 || this.seqFrame >= this.config.seq.frameCount)) {
                this.seqFrame = 0;
                this.seqFinished = true;
            }
        }

    }
}
