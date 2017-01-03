package info.demmonic.hdrs.scene.model;

public class UnderlayTile {

    public int hslNe;
    public int hslNw;
    public int hslSe;
    public int hslSw;
    public boolean isFlat;
    public int rgb;
    public byte textureIndex;

    public UnderlayTile(int hsl_sw, int hsl_se, int hsl_ne, int hsl_nw, byte textureIndex, int rgb_bitset, boolean is_flat) {
        this.hslSw = hsl_sw;
        this.hslNe = hsl_ne;
        this.hslNw = hsl_nw;
        this.hslSe = hsl_se;
        this.textureIndex = textureIndex;
        this.rgb = rgb_bitset;
        this.isFlat = is_flat;
    }
}
