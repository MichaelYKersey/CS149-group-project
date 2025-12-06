package src;

public interface ReadWriteable {
    byte readData(int p_address);
    default byte[] readData(int p_startAddress, int p_length) {
        byte[] data = new byte[p_length];
        for (int i=0; i<p_length; i++) {
            data[i] = readData(p_startAddress+i);
        }
        return data;
    }
    void writeData(int p_startAddress, byte p_data);
    default void writeData(int p_startAddress, byte[] p_data) {
        for (int i=0; i<p_data.length; i++) {
            writeData(p_startAddress+i,p_data[i]);
        }
    }
    default void writeInt(int p_startAddress, int p_val) {writeData(p_val, ByteArrayUtils.toByteArray(p_val));}
    default int readInt(int p_startAddress) {return ByteArrayUtils.readInt(readData(p_startAddress, 4));}
    default void writeShort(int p_startAddress, short p_val) {writeData(p_val, ByteArrayUtils.toByteArray(p_val));}
    default short readShort(int p_startAddress) {return ByteArrayUtils.readShort(readData(p_startAddress, 2));}
    default void writeString(int p_startAddress, String p_val) {
        writeData(p_startAddress, p_val.getBytes());
        writeData(p_startAddress+p_val.length(), (byte) '\0');
    }
    default String readString(int p_startAddress) {
        int len = 0;
        while (readData(0) != '\0') len++;
        return new String(readData(p_startAddress, len));
    }
}
