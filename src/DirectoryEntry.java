package src;

import java.io.File;

public class DirectoryEntry {
    public Disk m_disk;
    public int m_diskAddress;
    static final int NAME_OFFSET = 0x00;
    static final int EXTENSION_OFFSET = 0x08;
    static final int ATTRIBUTES_OFFSET = 0x0B;
    static final int START_CLUSTER_OFFSET = 0x1A;
    static final int SIZE_OFFSET = 0x1C;

    public DirectoryEntry() {
        //wipe the block
        for (int i=0; i<32; i++) {
            m_disk.writeData(m_diskAddress+i,(byte)0x00);
        }
    }
    public DirectoryEntry(
        String p_name, 
        String p_extension, 
        boolean p_isDirectory, 
        short p_startClusterNumber,
        int p_size
    ) {
        this();
        if (p_name.length() > 8 || p_extension.length < 3) {
            throw new IllegalArgumentException();
        }
        setName(p_name);
        setExtensionName(p_extension);
        setIsDirectory(p_isDirectory);
        setStartCluster(p_startClusterNumber);
        setSize(p_size);
    }

    
    public void setName(String p_name) {
        for (int i=0; i<8; i++) {
            if (i < p_name.length()) {
                m_disk.writeData(m_diskAddress+NAME_OFFSET+i, (byte) p_name.charAt(i));
            } else {
                m_disk.writeData(m_diskAddress+NAME_OFFSET+i, (byte) 0x20);
            }
        }
    }
    public String getName() {
        String name = "";
        for (int i=0; i<8 && m_disk.readData(m_diskAddress+NAME_OFFSET+i) != 0x20; i++) {
            name += (byte) m_disk.readData(m_diskAddress+NAME_OFFSET+i);
        }
        return name;
    }
    public void setExtensionName(String p_name) {
        for (int i=0; i<3; i++) {
            if (i < p_name.length()) {
                m_disk.writeData(m_diskAddress+NAME_OFFSET+i, (byte) p_name.charAt(i));
            } else {
                m_disk.writeData(m_diskAddress+NAME_OFFSET+i, (byte) 0x20);
            }
        }
    }
    public String getExtensionName() {
        String name = "";
        for (int i=0; i<3 && m_disk.readData(m_diskAddress+NAME_OFFSET+i) != 0x20; i++) {
            name += (byte) m_disk.readData(m_diskAddress+NAME_OFFSET+i);
        }
        return name;
    }

    public void setSize(int p_Size) {
        m_disk.writeInt(m_diskAddress+SIZE_OFFSET, p_Size);
    }
    public int getSize() {
        return m_disk.readInt(m_diskAddress+START_CLUSTER_OFFSET);
    }
    public void setStartCluster(short p_startCluster) {
        m_disk.writeShort(m_diskAddress+START_CLUSTER_OFFSET, p_startCluster);
    }
    public short getStartCluster() {
        return m_disk.readShort(m_diskAddress+START_CLUSTER_OFFSET);
    }

    public boolean isDirectory() {
        return (m_disk.readData(m_diskAddress+ATTRIBUTES_OFFSET) & 0x10) != 0;
    }
    public void setIsDirectory(boolean p_isDirectory) {
        byte mask = (~0x10);
        byte attributes = m_disk.readData(m_diskAddress+ATTRIBUTES_OFFSET);
        //clear then set bit
        attributes &= mask;
        attributes |= (p_isDirectory ? (byte)0x10 : (byte)0x00);
        m_disk.writeData(m_diskAddress+ATTRIBUTES_OFFSET, attributes);
    }

}
