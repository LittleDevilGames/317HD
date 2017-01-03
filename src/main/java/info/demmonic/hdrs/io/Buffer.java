package info.demmonic.hdrs.io;

import info.demmonic.hdrs.node.CacheLink;

import java.math.BigInteger;

public class Buffer extends CacheLink {

    private static final int[] BIT_MASK = new int[32];

    static {
        for (int i = 0; i < BIT_MASK.length; i++) {
            BIT_MASK[i] = (1 << i) - 1;
        }
    }

    public int bitPosition;
    public IsaacCipher isaac;
    public byte[] payload;
    public int position;

    public Buffer() {
    }

    public Buffer(byte[] payload) {
        this.payload = payload;
        this.position = 0;
    }

    public Buffer(int i) {
        this.payload = new byte[i];
        this.position = 0;
    }

    public static Buffer create(int type) {
        Buffer b = new Buffer();
        b.position = 0;

        switch (type) {
            case 0:
                b.payload = new byte[100];
                break;
            case 1:
                b.payload = new byte[5000];
                break;
            default:
                b.payload = new byte[30000];
                break;
        }

        return b;
    }

    public void encrypt(BigInteger exponent, BigInteger modulus) {
        int start = this.position;
        this.position = 0;

        // Get the payload
        byte data[] = new byte[start];
        this.read(data, 0, start);

        // Encrypt the payload
        data = new BigInteger(data).modPow(exponent, modulus).toByteArray();
        this.position = 0;

        // Write the length and encrypted bytes.
        this.writeByte(data.length);
        this.writeBytes(data, data.length, 0);
    }

    public int readBits(int count) {
        int pos = this.bitPosition >> 3;
        int l = 8 - (this.bitPosition & 7);
        int value = 0;
        this.bitPosition += count;
        for (; count > l; l = 8) {
            value += (this.payload[pos++] & BIT_MASK[l]) << count - l;
            count -= l;
        }

        if (count == l) {
            value += this.payload[pos] & BIT_MASK[l];
        } else {
            value += this.payload[pos] >> l - count & BIT_MASK[count];
        }
        return value;
    }

    public boolean readBoolean() {
        return (this.payload[this.position++] & 0xFF) == 1;
    }

    public byte readByte() {
        return this.payload[this.position++];
    }

    public byte readByteC() {
        return (byte) -this.readByte();
    }

    public byte readByteS() {
        return (byte) (128 - this.readByte());
    }

    public void read(byte[] dst, int off, int len) {
        for (int i = off; i < off + len; i++) {
            dst[i] = this.payload[this.position++];
        }
    }

    public void readBytesReversed(byte[] dst, int off, int len) {
        for (int k = (off + len) - 1; k >= off; k--) {
            dst[k] = this.payload[this.position++];
        }
    }

    public int readImeInt() {
        this.position += 4;
        return ((this.payload[this.position - 3] & 0xFF) << 24) + ((this.payload[this.position - 4] & 0xFF) << 16) + ((this.payload[this.position - 1] & 0xFF) << 8) + (this.payload[this.position - 2] & 0xFF);
    }

    public int readInt() {
        this.position += 4;
        return ((this.payload[position - 4] & 0xFF) << 24) + ((this.payload[position - 3] & 0xFF) << 16) + ((this.payload[position - 2] & 0xFF) << 8) + (this.payload[position - 1] & 0xFF);
    }

    public int readLeShort() {
        this.position += 2;
        int i = ((this.payload[this.position - 1] & 0xFF) << 8) + (this.payload[this.position - 2] & 0xFF);
        if (i > 0x7FFF) {
            i -= 0x10000;
        }
        return i;
    }

    public int readUnsignedLeShort() {
        this.position += 2;
        return ((this.payload[this.position - 1] & 0xFF) << 8) + (this.payload[this.position - 2] & 0xFF);
    }

    public int readUnsignedLeShortA() {
        this.position += 2;
        int i = ((this.payload[this.position - 1] & 0xFF) << 8) + (this.payload[this.position - 2] - 128 & 0xFF);
        if (i > 0x7FFF) {
            i -= 0x10000;
        }
        return i;
    }

    public long readLong() {
        return (((long) readInt() & 0xFFffffffL) << 32) + ((long) readInt() & 0xFFffffffL);
    }

    public int readMeInt() {
        this.position += 4;
        return ((this.payload[this.position - 2] & 0xFF) << 24) + ((this.payload[this.position - 1] & 0xFF) << 16) + ((this.payload[this.position - 4] & 0xFF) << 8) + (this.payload[this.position - 3] & 0xFF);
    }

    public int readMedium() {
        this.position += 3;
        return ((this.payload[position - 3] & 0xFF) << 16) + ((this.payload[position - 2] & 0xFF) << 8) + (this.payload[position - 1] & 0xFF);
    }

    public int readShort() {
        this.position += 2;
        int i = ((this.payload[position - 2] & 0xFF) << 8) + (this.payload[position - 1] & 0xFF);
        if (i > 0x7FFF) {
            i -= 0x10000;
        }
        return i;
    }

    public int readShortA() {
        this.position += 2;
        int j = ((this.payload[this.position - 1] & 0xFF) << 8) + (this.payload[this.position - 2] - 128 & 0xFF);
        if (j > 0x7FFF) {
            j -= 0x10000;
        }
        return j;
    }

    public int readSmart() {
        int i = this.payload[this.position] & 0xFF;
        if (i < 0x80) {
            return this.readUnsignedByte() - 0x40;
        } else {
            return this.readUnsignedShort() - 0xC000;
        }
    }

    public String readString() {
        int start = this.position;
        while (this.payload[position++] != 10)
            ;
        return new String(this.payload, start, this.position - start - 1);
    }

    public byte[] readStringBytes() {
        int start = this.position;
        while (this.payload[this.position++] != 10)
            ;
        byte[] data = new byte[this.position - start - 1];
        for (int i = start; i < this.position - 1; i++) {
            data[i - start] = this.payload[i];
        }
        return data;
    }

    public int readUnsignedByte() {
        return this.payload[this.position++] & 0xFF;
    }

    public int readUnsignedByteA() {
        return this.payload[this.position++] - 128 & 0xFF;
    }

    public int readUnsignedByteC() {
        return -this.payload[this.position++] & 0xFF;
    }

    public int readUnsignedByteS() {
        return 128 - this.payload[this.position++] & 0xFF;
    }

    public int readUnsignedShort() {
        this.position += 2;
        return ((this.payload[position - 2] & 0xFF) << 8) + (this.payload[position - 1] & 0xFF);
    }

    public int writeUnsignedShortA() {
        this.position += 2;
        return ((this.payload[this.position - 2] & 0xFF) << 8) + (this.payload[this.position - 1] - 128 & 0xFF);
    }

    public int writeUnsignedSmart() {
        int i = this.payload[this.position] & 0xFF;
        if (i < 0x80) {
            return this.readUnsignedByte();
        } else {
            return this.readUnsignedShort() - 32768;
        }
    }

    public void writeBool(boolean b) {
        this.writeByte(b ? 1 : 0);
    }

    public void writeByte(int i) {
        this.payload[this.position++] = (byte) i;
    }

    public void writeByteC(int i) {
        this.payload[this.position++] = (byte) (-i);
    }

    public void writeByteS(int i) {
        this.payload[this.position++] = (byte) (128 - i);
    }

    public void writeBytes(byte src[], int off, int pos) {
        for (int i = pos; i < pos + off; i++) {
            this.payload[this.position++] = src[i];
        }
    }

    public void writeBytesReversedA(byte[] src, int off, int len) {
        for (int i = (off + len) - 1; i >= off; i--) {
            this.payload[this.position++] = (byte) (src[i] + 128);
        }
    }

    public void writeInt(int i) {
        this.payload[this.position++] = (byte) (i >> 24);
        this.payload[this.position++] = (byte) (i >> 16);
        this.payload[this.position++] = (byte) (i >> 8);
        this.payload[this.position++] = (byte) i;
    }

    public void writeLeInt(int j) {
        this.payload[this.position++] = (byte) j;
        this.payload[this.position++] = (byte) (j >> 8);
        this.payload[this.position++] = (byte) (j >> 16);
        this.payload[this.position++] = (byte) (j >> 24);
    }

    public void writeLeShort(int i) {
        this.payload[this.position++] = (byte) i;
        this.payload[this.position++] = (byte) (i >> 8);
    }

    public void writeLeShortA(int i) {
        this.payload[this.position++] = (byte) (i + 128);
        this.payload[this.position++] = (byte) (i >> 8);
    }

    public void putLength(int len) {
        this.payload[this.position - len - 1] = (byte) len;
    }

    public void writeLong(long l) {
        this.payload[this.position++] = (byte) (int) (l >> 56);
        this.payload[this.position++] = (byte) (int) (l >> 48);
        this.payload[this.position++] = (byte) (int) (l >> 40);
        this.payload[this.position++] = (byte) (int) (l >> 32);
        this.payload[this.position++] = (byte) (int) (l >> 24);
        this.payload[this.position++] = (byte) (int) (l >> 16);
        this.payload[this.position++] = (byte) (int) (l >> 8);
        this.payload[this.position++] = (byte) (int) l;
    }

    public void writeMedium(int i) {
        this.payload[this.position++] = (byte) (i >> 16);
        this.payload[this.position++] = (byte) (i >> 8);
        this.payload[this.position++] = (byte) i;
    }

    public void writeOpcode(int i) {
        this.payload[this.position++] = (byte) (i + this.isaac.nextInt());
    }

    public void writeShort(int i) {
        this.payload[this.position++] = (byte) (i >> 8);
        this.payload[this.position++] = (byte) i;
    }

    public void writeShortA(int i) {
        this.payload[this.position++] = (byte) (i >> 8);
        this.payload[this.position++] = (byte) (i + 128);
    }

    public void writeString(String s) {
        System.arraycopy(s.getBytes(), 0, this.payload, this.position, s.length());
        this.position += s.length();
        this.payload[this.position++] = 10;
    }

    public void startBitAccess() {
        this.bitPosition = this.position * 8;
    }

    public void stopBitAccess() {
        this.position = (this.bitPosition + 7) / 8;
    }

}
