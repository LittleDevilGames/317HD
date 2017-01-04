package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.cache.model.SpotAnimConfig;
import info.demmonic.hdrs.node.impl.Renderable;

public class Projectile extends Renderable {

    public double aDouble1578;
    public int anInt158;
    public int arcSize;
    public int cycleEnd;
    public int cycleStart;
    public boolean mobile = false;
    public int offsetZ;
    public int parentSize;
    public int pitch;
    public int plane;
    public int rotation;
    public double sceneX;
    public double sceneY;
    public double sceneZ;
    public int seqCycle;
    public int seqFrame;
    public double speed;
    public double speedX;
    public double speedY;
    public double speedZ;
    public SpotAnimConfig config;
    public int startX;
    public int startY;
    public int startZ;
    public int targetIndex;

    public Projectile(int arcSize, int endZ, int cycleStart, int cycleEnd, int parentSize, int plane, int startZ, int startY, int startX, int target, int spotanimIndex) {
        this.config = SpotAnimConfig.instance[spotanimIndex];
        this.plane = plane;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.cycleStart = cycleStart;
        this.cycleEnd = cycleEnd;
        this.arcSize = arcSize;
        this.parentSize = parentSize;
        this.targetIndex = target;
        this.offsetZ = endZ;
    }

    @Override
    public Model getModel() {
        Model model = config.getModel();

        if (model == null) {
            return null;
        }

        int frame = -1;

        if (config.seq != null) {
            frame = config.seq.framePrimary[seqFrame];
        }

        Model m = new Model(true, frame == -1, false, model);

        if (frame != -1) {
            m.applyVertexWeights();
            m.applySequenceFrame(frame);
            m.triangleGroups = null;
            m.vertexWeights = null;
        }

        if (config.scale != 128 || config.height != 128) {
            m.scale(config.scale, config.height, config.scale);
        }

        m.setPitch(pitch);
        m.applyLighting(64 + config.brightness, 850 + config.specular, -30, -50, -30, true);
        return m;
    }

    public void update(int cycle) {
        mobile = true;
        sceneX += speedX * (double) cycle;
        sceneY += speedY * (double) cycle;
        sceneZ += speedZ * (double) cycle + 0.5D * aDouble1578 * (double) cycle * (double) cycle;
        speedZ += aDouble1578 * (double) cycle;
        rotation = (int) (Math.atan2(speedX, speedY) * (32595 / 100)) + 1024 & 0x7ff;
        pitch = (int) (Math.atan2(speedZ, speed) * (32595 / 100)) & 0x7ff;

        if (config.seq != null) {
            for (seqCycle += cycle; seqCycle > config.seq.getFrameLength(seqFrame); ) {
                seqCycle -= config.seq.getFrameLength(seqFrame) + 1;
                seqFrame++;
                if (seqFrame >= config.seq.frameCount) {
                    seqFrame = 0;
                }
            }
        }
    }

    public void update(int cycle, int x, int y, int z) {
        if (!mobile) {
            double dx = x - startX;
            double dy = y - startY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            sceneX = (double) startX + (dx * (double) parentSize) / distance;
            sceneY = (double) startY + (dy * (double) parentSize) / distance;
            sceneZ = startZ;
        }

        double dt = (this.cycleEnd + 1) - cycle;
        speedX = ((double) x - sceneX) / dt;
        speedY = ((double) y - sceneY) / dt;
        speed = Math.sqrt(speedX * speedX + speedY * speedY);

        if (!mobile) {
            speedZ = -speed * Math.tan((double) arcSize * (Math.PI / 128D));
        }

        aDouble1578 = (2D * ((double) z - sceneZ - speedZ * dt)) / (dt * dt);
    }
}
