package info.demmonic.hdrs.cache;

import info.demmonic.hdrs.io.Buffer;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Archive {

    public static final int CONTEXT_HEADER_SIZE = 10;
    public Buffer buffer;
    public Map<Integer, Entry> entries = new HashMap<>();
    public boolean extracted;

    private byte[] reconstructBzip2Header(byte[] ba) {
        byte[] tmp = new byte[ba.length + 4];
        tmp[0] = 'B';
        tmp[1] = 'Z';
        tmp[2] = 'h';
        tmp[3] = '1';
        System.arraycopy(ba, 0, tmp, 4, ba.length);
        return tmp;
    }

    private byte[] bzip2Decompress(byte[] ba) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BZip2CompressorInputStream ins = new BZip2CompressorInputStream(new ByteArrayInputStream(reconstructBzip2Header(ba)));
        byte[] buf = new byte[4096];
        int read = ins.read(buf);
        while (read != -1) {
            out.write(buf, 0, read);
            read = ins.read(buf);
        }
        return out.toByteArray();
    }

    public Archive(Buffer buffer) {
        int unpackedSize = buffer.readMedium();
        int packedSize = buffer.readMedium();

        if (packedSize != unpackedSize) {
            try {
                byte[] truncated = new byte[buffer.payload.length - 6];
                System.arraycopy(buffer.payload, 6, truncated, 0, truncated.length);
                buffer = new Buffer(bzip2Decompress(truncated));
            } catch (IOException e) {
                e.printStackTrace();
            }
            extracted = true;
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
            try {
                byte[] compressed = new byte[e.packedSize];
                System.arraycopy(buffer.payload, e.position, compressed, 0, e.packedSize);
                dst = bzip2Decompress(compressed);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
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
