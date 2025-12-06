package src;

import java.util.Arrays;

public class Disk implements ReadWriteable{
    final static int DISK_SIZE = 1<<25;//32MB 

    public byte[] m_data = new byte[DISK_SIZE];
    
    @Override
    public byte readData(int address) {
        return m_data[address];
    }
    @Override
    public void writeData(int p_address, byte p_data) {
        m_data[p_address] = p_data;
    };
}