package rs.cache.impl;

import rs.cache.Archive;
import rs.io.Buffer;

public class Varp {

    public static int count;
    public static Varp[] instance;
    public int index;

    public Varp(Buffer b) {
        while (true) {
            int opcode = b.readUnsignedByte();
            if (opcode == 0) {
                return;
            } else if (opcode == 5) {
                this.index = b.readUnsignedShort();
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        }
    }

    public static void unpack(Archive a) {
        Buffer b = new Buffer(a.get("varp.dat"));
        Varp.count = b.readUnsignedShort();
        Varp.instance = new Varp[count];

        for (int i = 0; i < Varp.count; i++) {
            Varp.instance[i] = new Varp(b);
        }

        if (b.position != b.payload.length) {
            System.out.println("varptype load mismatch");
        }
    }

}
