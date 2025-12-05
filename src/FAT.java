package src;

public class FAT {
    private Disk m_disk;
    private int m_tableStart;

    public FAT(Disk p_disk, int p_tableStart) {
        m_disk = p_disk;
        m_tableStart = p_tableStart;
    }
    /**
     * reserves an open entry in table
     * @return cluster number claimed (-1 if no free clusters)
     */
    public short getFreeEntry() {
        for (short i=0; i< FileSystem.CLUSTERS_PER_TABLE; i++) {
            if (getEntryValue(i) == 0x00) {
                writeEntry(i, (short)0xFF);
                return i;
            }
        }
        return -1;
    }
    /**
     * @param p_clusters number of clusters to reserve 
     * @return cluster numbers
     */
    public short[] getFreeEntries(short p_clusters) {
        short[] clusterNums = new short[p_clusters];
        for (short i=0; i< p_clusters; i++) {
            clusterNums[i] = getFreeEntry();
        }
        for (int i=0; i< p_clusters-1; i++) {
            linkEntry(clusterNums[i],clusterNums[i+1]);
        }
        return clusterNums;
    }
    public void linkEntry(short p_clusterNumber,short p_nextClusterNumber) {
        writeEntry(p_clusterNumber, p_nextClusterNumber);
    }
    public short getNextCluster(short p_clusterNumber) {
        return ByteArrayUtils.readShort(m_disk.getData(m_tableStart+p_clusterNumber*2, 2));
    }
    public int getEntryValue(short p_clusterNumber) {
        return ByteArrayUtils.readShort(m_disk.getData(m_tableStart+p_clusterNumber*2, 2));
    }
    public void writeEntry(short p_entryNumber, short p_value) {
        m_disk.writeData(m_tableStart+p_entryNumber*2,ByteArrayUtils.toByteArray(p_value));
    }
    //removal of entries is not needed due to project requirements
}
