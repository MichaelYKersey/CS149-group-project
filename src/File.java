package src;

public class File implements ReadWriteable{
    FileSystem m_fileSystem;
    DirectoryEntry m_directoryEntryLocation;
    short[] m_clusters;
    String m_path;
    public File(FileSystem p_fileSystem, String p_path) {
        m_fileSystem = p_fileSystem;
        m_path = p_path;
    }
    /**
     * linked file information to object
     */
    public void openFile() {

    }
    /**
     * unlinks file information from object
     */
    public void closeFile() {
        
    }
    /**
     * changes the size of a file
     */
    public void changeSize(int p_newSize) {
        int curClusterCount = (m_directoryEntryLocation.getSize()+FileSystem.CLUSTER_SIZE-1)/FileSystem.CLUSTER_SIZE;
        int newClusterCount = (m_directoryEntryLocation.getSize()+FileSystem.CLUSTER_SIZE-1)/FileSystem.CLUSTER_SIZE;
        m_directoryEntryLocation.setSize(p_newSize);
        if (newClusterCount == curClusterCount) return;
        if (newClusterCount < curClusterCount) throw new IllegalArgumentException();//shouldn't get called in this project
        
        //in case file size is 0 (no linked cluster)
        FAT fat = m_fileSystem.getFAT();
        if (m_directoryEntryLocation.getStartCluster() == -1) {
            m_directoryEntryLocation.setStartCluster(fat.getFreeEntry());
            curClusterCount++;
        }
        if (curClusterCount == newClusterCount) return;

        short fatEntry = m_directoryEntryLocation.getStartCluster();
        while (fat.getNextCluster(fatEntry) != -1) {
            fatEntry = fat.getNextCluster(fatEntry);
        }
        short newClusterChainStart = fat.getFreeEntries((short)(newClusterCount-curClusterCount))[0];
        fat.linkEntry(fatEntry, newClusterChainStart);
    }
    @Override
    public void writeData(int p_startAddress, byte p_data) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public byte readData(int p_address) {
        // TODO Auto-generated method stub
        return 0;
    }
}
