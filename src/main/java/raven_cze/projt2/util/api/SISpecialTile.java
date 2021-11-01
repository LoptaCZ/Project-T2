package raven_cze.projt2.util.api;

import java.io.Serializable;

public class SISpecialTile implements Serializable {
    public int x;
    public int y;
    public int z;
    public byte rune;
    public byte dimension;
    public byte type;
    public SISpecialTile(){}
    public SISpecialTile(int xx,int yy,int zz,byte rune,byte dimension,byte type){
        this.x=xx;
        this.y=yy;
        this.z=zz;
        this.rune=rune;
        this.dimension=dimension;
        this.type=type;
    }
}
