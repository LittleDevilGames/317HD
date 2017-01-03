package rs.scene.model;

import rs.cache.model.ObjConfig;
import rs.node.impl.Renderable;

public class Item extends Renderable {

    public short index;
    public int stackIndex;

    public Item() {
    }

    @Override
    public Model getModel() {
        return ObjConfig.get(index).get_model(stackIndex);
    }
}
