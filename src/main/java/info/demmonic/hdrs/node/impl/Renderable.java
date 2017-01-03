package info.demmonic.hdrs.node.impl;

import info.demmonic.hdrs.node.CacheLink;
import info.demmonic.hdrs.scene.model.Model;
import info.demmonic.hdrs.scene.model.Vertex;

public class Renderable extends CacheLink {

    public int height;
    public Vertex[] normal;

    public Renderable() {
        height = 1000;
    }

    public Model getModel() {
        return null;
    }

    public void render(int rotation, int cam_pitch_sin, int cam_pitch_cos, int cam_yaw_sin, int cam_yaw_cos, int x, int y, int z, int uid) {
        Model m = this.getModel();
        if (m != null) {
            height = m.height;
            m.render(rotation, cam_pitch_sin, cam_pitch_cos, cam_yaw_sin, cam_yaw_cos, x, y, z, uid);
        }
    }
}
