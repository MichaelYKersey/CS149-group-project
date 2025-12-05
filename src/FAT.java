package src;

public class FAT {
    private Disk m_disk;
    private int m_tableStart;
    /**
     * reserves an open entry in table
     * @return cluster number claimed
     */
    public int getFreeEntry() {
        return -1;
    }
    /**
     * 
     * @param p_clusters number of clusters to reserve 
     * @return starting cluster number
     */
    public int getFreeEntries(int p_clusters) {
        return -1;
    }
    public void linkEntry(int p_clusterNumber) {

    }
    public int getNextCluster(int p_clusterNumber) {
        return -1;
    }
    //removal is not needed due to project requirements
}
