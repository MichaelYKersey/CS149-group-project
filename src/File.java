package src;

import java.util.ArrayList;

public class File implements ReadWriteable{
    FileSystem m_fileSystem;
    DirectoryEntry m_directoryEntryLocation;
    ArrayList<Short> m_clusters;
    String m_path;
    public File(FileSystem p_fileSystem, String p_path) {
        m_fileSystem = p_fileSystem;
        m_path = p_path;
    }
    /**
     * linked file information to object
     */
    public void openFile() {
        //TODO: get m_directorEntryLocation from path
        m_clusters = m_fileSystem.getFAT().getEntryChain(m_directoryEntryLocation.getStartCluster());
    }
    /**
     * unlinks file information from object
     */
    public void closeFile() {
        m_directoryEntryLocation = null;
        m_clusters = null;
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
        m_clusters = fat.getEntryChain(m_directoryEntryLocation.getStartCluster());
    }
    @Override
    public void writeData(int p_address, byte p_data) {
        if (!inRange(p_address)) throw new IllegalArgumentException();
        m_fileSystem.getDisk().writeData(mapAddressToDisk(p_address), p_data);
    }
    @Override
    public byte readData(int p_address) {
        if (!inRange(p_address)) throw new IllegalArgumentException();
        return m_fileSystem.getDisk().readData(mapAddressToDisk(p_address));
    }
    @Override
    public boolean inRange(int p_address) {
        return p_address >= 0 && p_address < m_directoryEntryLocation.getSize();
    }
    public int mapAddressToDisk(int p_fileAddress) {
        int clusterNum = m_clusters.get(p_fileAddress/FileSystem.CLUSTER_SIZE);
        return clusterNum*FileSystem.CLUSTER_SIZE + p_fileAddress% FileSystem.CLUSTER_SIZE;
    }
}
