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

    public void render(int rotation, int camPitchSin, int camPitchCos, int camYawSin, int camYawCos, int x, int y, int z, int uid) {
        Model m = this.getModel();
        if (m != null) {
            height = m.height;
            m.render(rotation, camPitchSin, camPitchCos, camYawSin, camYawCos, x, y, z, uid);
        }
    }
}
