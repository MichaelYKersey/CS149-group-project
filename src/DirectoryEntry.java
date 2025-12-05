package src;

import java.io.File;

public class DirectoryEntry {
    char[] m_name; static final int NAME_OFFSET = 0x00;
    char[] m_extension; static final int EXTENSION_OFFSET = 0x08;
    byte m_attributes; static final int ATTRIBUTES_OFFSET = 0x0B;
    short m_startClusterNumber; static final int START_CLUSTER_OFFSET = 0x1A;
    int m_size; static final int FILE_SIZE = 0x1C;

    public DirectoryEntry(byte[] p_raw) {
        m_name = ByteArrayUtils.getCharArray(p_raw, NAME_OFFSET, 8);
        m_extension = ByteArrayUtils.getCharArray(p_raw, EXTENSION_OFFSET, 3);
        m_attributes = p_raw[ATTRIBUTES_OFFSET];
        m_startClusterNumber = ByteArrayUtils.readShort(p_raw, START_CLUSTER_OFFSET);
        m_size = ByteArrayUtils.readInt(p_raw, FILE_SIZE);
    }

    public byte[] getRaw() {
        byte[] raw = new byte[32];
        ByteArrayUtils.copyCharArray(raw, NAME_OFFSET, m_name);
        ByteArrayUtils.copyCharArray(raw, EXTENSION_OFFSET, m_extension);
        raw[ATTRIBUTES_OFFSET] = m_attributes;
        ByteArrayUtils.writeShort(raw, START_CLUSTER_OFFSET, m_startClusterNumber);
        ByteArrayUtils.writeInt(raw, FILE_SIZE, m_size);
        return null;
    }

}
