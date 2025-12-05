package src;

import java.util.Arrays;

public class Disk {
    final static int DISK_SIZE = 1<<25;//32MB 

    public byte[] m_data = new byte[DISK_SIZE];
    
    public byte readData(int address) {
        return m_data[address];
    }
    public byte[] readData(int p_startAddress, int p_length) {
        return Arrays.copyOfRange(m_data, p_startAddress, p_startAddress+p_length);
    }
    public void writeData(int p_startAddress, byte[] p_data) {
        for (int i=0; i<p_data.length; i++) {
            m_data[i+p_startAddress] = p_data[i];
        }
    }
    public void writeInt(int p_startAddress, int p_val) {writeData(p_val, ByteArrayUtils.toByteArray(p_val));}
    public void writeShort(int p_startAddress, short p_val) {writeData(p_val, ByteArrayUtils.toByteArray(p_val));}
    public int readInt(int p_startAddress) {return ByteArrayUtils.readInt(readData(p_startAddress, 4));}
    public short readShort(int p_startAddress) {return ByteArrayUtils.readShort(readData(p_startAddress, 2));}
}