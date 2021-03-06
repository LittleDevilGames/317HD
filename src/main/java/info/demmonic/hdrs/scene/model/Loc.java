package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.Game;
import info.demmonic.hdrs.cache.impl.Sequence;
import info.demmonic.hdrs.cache.impl.VarBit;
import info.demmonic.hdrs.cache.model.LocConfig;
import info.demmonic.hdrs.node.impl.Renderable;

public class Loc extends Renderable {

    public Sequence seq;
    public int seqCycle;
    public int cycle;
    public short index;
    public short[] overrideIndex;
    public int rotation;
    public int settingIndex;
    public int type;
    public int heightNorthEast;
    public int heightNorthWest;
    public int heightSouthEast;
    public int heightSouthWest;
    public int varbitIndex;

    public Loc(int index, int rotation, int type, int heightSouthEast, int heightNorthEast, int heightSouthWest, int heightNorthWest, int sequence, boolean randomFrame) {
        this.index = (short) index;
        this.type = type;
        this.rotation = rotation;
        this.heightSouthWest = heightSouthWest;
        this.heightSouthEast = heightSouthEast;
        this.heightNorthEast = heightNorthEast;
        this.heightNorthWest = heightNorthWest;

        if (sequence != -1) {
            this.seq = Sequence.instance[sequence];
            this.seqCycle = 0;
            this.cycle = Game.loopCycle;
            if (randomFrame && this.seq.padding != -1) {
                this.seqCycle = (int) (Math.random() * (double) this.seq.frameCount);
                this.cycle -= (int) (Math.random() * (double) this.seq.getFrameLength(seqCycle));
            }
        }

        LocConfig config = LocConfig.get(this.index);
        this.varbitIndex = config.varbitIndex;
        this.settingIndex = config.settingIndex;
        this.overrideIndex = config.overrideIndex;
    }

    @Override
    public Model getModel() {
        int frame = -1;

        if (seq != null) {
            int cycleDelta = Game.loopCycle - cycle;

            if (cycleDelta > 100 && seq.padding > 0) {
                cycleDelta = 100;
            }

            while (cycleDelta > seq.getFrameLength(seqCycle)) {
                cycleDelta -= seq.getFrameLength(seqCycle);

                seqCycle++;

                if (seqCycle < seq.frameCount) {
                    continue;
                }

                seqCycle -= seq.padding;

                if (seqCycle >= 0 && seqCycle < seq.frameCount) {
                    continue;
                }

                seq = null;
                break;
            }

            cycle = Game.loopCycle - cycleDelta;

            if (seq != null) {
                frame = seq.framePrimary[seqCycle];
            }
        }

        LocConfig config;

        if (overrideIndex != null) {
            config = getOverridingConfig();
        } else {
            config = LocConfig.get(index);
        }

        if (config == null) {
            return null;
        } else {
            return config.getModel(type, rotation, heightSouthWest, heightSouthEast, heightNorthEast, heightNorthWest, frame);
        }
    }

    public LocConfig getOverridingConfig() {
        int i = -1;

        if (varbitIndex != -1) {
            VarBit varbit = VarBit.instance[varbitIndex];
            int varpIndex = varbit.setting;
            int offset = varbit.offset;
            int shift = varbit.shift;
            int j1 = Game.LSB_BIT_MASK[shift - offset];
            i = Game.settings[varpIndex] >> offset & j1;
        } else if (settingIndex != -1) {
            i = Game.settings[settingIndex];
        }

        if (i < 0 || i >= overrideIndex.length || overrideIndex[i] == -1) {
            return null;
        }

        return LocConfig.get(overrideIndex[i]);
    }
}
