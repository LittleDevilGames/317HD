package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.cache.model.ObjConfig;
import info.demmonic.hdrs.node.impl.Renderable;

public class Item extends Renderable {

    public short index;
    public int stackIndex;

    public Item() {
    }

    @Override
    public Model getModel() {
        return ObjConfig.get(index).getModel(stackIndex);
    }
}
