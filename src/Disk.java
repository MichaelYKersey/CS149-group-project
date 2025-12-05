package src;

import java.util.Arrays;

public class Disk {
    final static int DISK_SIZE = 1<<30;//1GB 

    public byte[] m_data = new byte[DISK_SIZE];
    
    public byte getData(int address) {
        return m_data[address];
    }
    public byte[] getData(int p_startAddress, int p_length) {
        return Arrays.copyOfRange(m_data, p_startAddress, p_startAddress+p_length);
    }
    public void writeBlock(int p_startAddress, byte[] p_data) {
        for (int i=0; i<p_data.length; i++) {
            m_data[i+p_startAddress] = p_data[i];
        }
    }
}