package info.demmonic.hdrs.cache;

import info.demmonic.hdrs.bzip2.BZip2;
import info.demmonic.hdrs.io.Buffer;

import java.util.HashMap;
import java.util.Map;

public class Archive {

    public static final int CONTEXT_HEADER_SIZE = 10;
    public Buffer buffer;
    public Map<Integer, Entry> entries = new HashMap<>();
    public boolean extracted;

    public Archive(Buffer buffer) {
        int unpackedSize = buffer.readMedium();
        int packedSize = buffer.readMedium();

        if (packedSize != unpackedSize) {
            byte[] unpacked = new byte[unpackedSize];
            BZip2.decompress(unpacked, unpackedSize, buffer.payload, packedSize, 6);
            buffer = new Buffer(unpacked);
            this.extracted = true;
        }

        int count = buffer.readUnsignedShort();
        int position = buffer.position + (count * CONTEXT_HEADER_SIZE);

        for (int i = 0; i < count; i++) {
            Entry e = new Entry();
            e.hash = buffer.readInt();
            e.unpackedSize = buffer.readMedium();
            e.packedSize = buffer.readMedium();
            e.position = position;
            position += e.packedSize;
            this.entries.put(e.hash, e);
        }

        this.buffer = buffer;
    }

    public static int getHash(String s) {
        int hash = 0;
        s = s.toUpperCase();
        for (int j = 0; j < s.length(); j++) {
            hash = (hash * 61 + s.charAt(j)) - 32;
        }
        return hash;
    }

    public byte[] get(String s) {
        return this.get(s, null);
    }

    public byte[] get(String s, byte[] dst) {
        int hash = Archive.getHash(s);

        if (!this.entries.containsKey(hash)) {
            return null;
        }

        Entry e = this.entries.get(hash);

        if (dst == null) {
            dst = new byte[e.unpackedSize];
        }

        if (!this.extracted) {
            BZip2.decompress(dst, e.unpackedSize, this.buffer.payload, e.packedSize, e.position);
        } else {
            System.arraycopy(this.buffer.payload, e.position, dst, 0, e.unpackedSize);
        }

        return dst;
    }

    public class Entry {
        public int hash;
        public int packedSize;
        public int position;
        public int unpackedSize;

        @Override
        public String toString() {
            return "[Entry: hash:" +
                    hash + ", unpacked:" +
                    unpackedSize + ", packed:" +
                    packedSize + ", pos:" +
                    position + ']';
        }
    }
}
