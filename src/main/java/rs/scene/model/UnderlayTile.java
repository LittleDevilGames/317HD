package rs.scene.model;

public class UnderlayTile {

    public int hslNe;
    public int hslNw;
    public int hslSe;
    public int hslSw;
    public boolean isFlat;
    public int rgb;
    public byte texture_index;

    public UnderlayTile(int hsl_sw, int hsl_se, int hsl_ne, int hsl_nw, byte texture_index, int rgb_bitset, boolean is_flat) {
        this.hslSw = hsl_sw;
        this.hslNe = hsl_ne;
        this.hslNw = hsl_nw;
        this.hslSe = hsl_se;
        this.texture_index = texture_index;
        this.rgb = rgb_bitset;
        this.isFlat = is_flat;
    }
}
