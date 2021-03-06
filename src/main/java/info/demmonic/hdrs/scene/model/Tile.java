package info.demmonic.hdrs.scene.model;

import info.demmonic.hdrs.node.Link;

public class Tile extends Link {

    public boolean aBoolean1322;
    public boolean aBoolean1323;
    public boolean aBoolean1324;
    public int anInt1325;
    public int anInt1326;
    public int anInt1327;
    public int anInt1328;
    public Tile bridge;
    public int flags;
    public GroundDecoration groundDecoration;
    public ItemPile itemPile;
    public int locCount;
    public int locFlag[] = new int[5];
    public StaticLoc locs[] = new StaticLoc[5];
    public OverlayTile overlay;
    public int plane;
    public int topPlane;
    public UnderlayTile underlay;
    public WallLoc wall;
    public WallDecoration wallDecoration;
    public byte x, y, z;

    public Tile(int plane, int x, int y) {
        this.plane = this.z = (byte) plane;
        this.x = (byte) x;
        this.y = (byte) y;
    }
}
