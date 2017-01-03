package info.demmonic.hdrs.scene.model;

public class UnderlayTile {

    public int hslNe;
    public int hslNw;
    public int hslSe;
    public int hslSw;
    public boolean isFlat;
    public int minimapRgb;
    public byte textureIndex;

    public UnderlayTile(int hslSw, int hslSe, int hslNe, int hslNw, byte textureIndex, int minimapRgb, boolean isFlat) {
        this.hslSw = hslSw;
        this.hslNe = hslNe;
        this.hslNw = hslNw;
        this.hslSe = hslSe;
        this.textureIndex = textureIndex;
        this.minimapRgb = minimapRgb;
        this.isFlat = isFlat;
    }

}
