package src;

import java.util.Arrays;

public class Disk implements ReadWriteable{
    final static int DISK_SIZE = 1<<25;//32MB 

    public byte[] m_data = new byte[DISK_SIZE];
    
    @Override
    public byte readData(int address) {
        if (!inRange(address)) throw new IllegalArgumentException();
        return m_data[address];
    }
    @Override
    public void writeData(int p_address, byte p_data) {
        if (!inRange(p_address)) throw new IllegalArgumentException();
        m_data[p_address] = p_data;
    };
    @Override
    public boolean inRange(int p_address) {
        return p_address >= 0 && p_address < DISK_SIZE;
    }
}