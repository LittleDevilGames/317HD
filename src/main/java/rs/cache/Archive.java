package rs.cache;

import rs.bzip2.BZip2;
import rs.io.Buffer;

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
            e.unpacked_size = buffer.readMedium();
            e.packed_size = buffer.readMedium();
            e.position = position;
            position += e.packed_size;
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
            dst = new byte[e.unpacked_size];
        }

        if (!this.extracted) {
            BZip2.decompress(dst, e.unpacked_size, this.buffer.payload, e.packed_size, e.position);
        } else {
            System.arraycopy(this.buffer.payload, e.position, dst, 0, e.unpacked_size);
        }

        return dst;
    }

    public class Entry {
        public int hash;
        public int packed_size;
        public int position;
        public int unpacked_size;

        @Override
        public String toString() {
            return "[Entry: hash:" +
                    hash + ", unpacked:" +
                    unpacked_size + ", packed:" +
                    packed_size + ", pos:" +
                    position + ']';
        }
    }
}
