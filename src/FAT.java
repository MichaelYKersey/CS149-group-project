package src;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FAT {
    private Disk m_disk;
    private int m_tableStart;

    public FAT(Disk p_disk, int p_tableStart) {
        m_disk = p_disk;
        m_tableStart = p_tableStart;
        //reserve the clusters used by the FAT Table
        int tableSize = FileSystem.CLUSTERS_PER_TABLE*2;
        int clustersReserved = (tableSize+FileSystem.CLUSTER_SIZE-1)/FileSystem.CLUSTERS_PER_TABLE;
        for (short i = 0; i < clustersReserved; i++) {
            writeEntry(i, (short) 0x01);
        }
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
        return m_disk.readShort(m_tableStart+p_clusterNumber*2);
    }
    public short getEntryValue(short p_clusterNumber) {
        return m_disk.readShort(m_tableStart+p_clusterNumber*2);
    }
    public void writeEntry(short p_entryNumber, short p_value) {
        m_disk.writeInt(m_tableStart+p_entryNumber*2,p_value);
    }
    public void appendEntry() {}
    public ArrayList<Short> getEntryChain(short p_clusterChainStartNumber) {
        ArrayList<Short> list = new ArrayList<>();
        while (p_clusterChainStartNumber != -1) {
            list.add(p_clusterChainStartNumber);
            p_clusterChainStartNumber = getNextCluster(p_clusterChainStartNumber);
        }
        return list;
    }
    //removal of entries is not needed due to project requirements
}
