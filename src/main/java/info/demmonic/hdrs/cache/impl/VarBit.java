package info.demmonic.hdrs.cache.impl;

import info.demmonic.hdrs.cache.Archive;
import info.demmonic.hdrs.io.Buffer;

public class VarBit {

    public static int count;
    public static VarBit[] instance;
    public int offset;
    public int setting;
    public int shift;

    public VarBit(Buffer s) {
        do {
            int opcode = s.readUnsignedByte();

            if (opcode == 0) {
                return;
            }

            if (opcode == 1) {
                setting = s.readUnsignedShort();
                offset = s.readUnsignedByte();
                shift = s.readUnsignedByte();
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while (true);
    }

    public static void unpack(Archive a) {
        Buffer s = new Buffer(a.get("varbit.dat", null));

        VarBit.count = s.readUnsignedShort();
        VarBit.instance = new VarBit[VarBit.count];

        for (int i = 0; i < VarBit.count; i++) {
            VarBit.instance[i] = new VarBit(s);
        }

        if (s.position != s.payload.length) {
            System.out.println("varbit load mismatch");
        }
    }
}
