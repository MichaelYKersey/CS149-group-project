package src;

import java.util.HashMap;

public class VirtualRam {
    //TODO: proper Implementation if time
    HashMap<Integer,HashMap<Integer,Integer>> m_pages = new HashMap<>();
    private Disk m_disk;

    public final static int MEMORY_SIZE = 1<<24;//4MB
    public final static int PAGE_SIZE = 1<<12;//4KB
    //we are ignoring paging and memory storage for operations other than disk access
    private byte[] m_memory = new byte[MEMORY_SIZE];
    
    public VirtualRam(Disk p_disk) {
        m_disk = p_disk;
    }
    /**
     * @param p_fileID file ID in page table
     * @param p_startAddress offset in file
     * @param p_length amount of data you want to read
     * @return data at the section of the file
     */
    public byte[] read(int p_fileID, int p_startAddress, int p_length) {
        byte[] data = new byte[p_length];
        for (int i=0; i<p_length; i++) {
            data[i] = read(p_fileID, p_startAddress+i);
        }
        return data;
    }
    /**
     * @param p_fileID, file ID in page table
     * @param p_address data offset in file
     * @return byte at the section of the file
     */
    public byte read(int p_fileID, int p_address) {
        return m_disk.readData(m_pages.get(p_fileID).get(p_address/PAGE_SIZE)+p_address%PAGE_SIZE);
    }
    /**
     * maps section of disc to a page
     * @param p_fileID file ID in page table
     * @param p_pageNumber page number
     * @param p_diskAddressStart address to map to in disk
     */
    public void addPageTableEntry(int p_fileID, int p_pageNumber, int p_diskAddressStart) {
        if (!m_pages.containsKey(p_fileID)) m_pages.put(p_fileID, new HashMap<>());
        m_pages.get(p_fileID).put(p_pageNumber, p_diskAddressStart);
    }
    /**
     * unmap section of disc to a page
     * @param p_fileID file ID in page table
     * @param p_pageNumber page number
     */
    public void removePageTableEntry(int p_fileID, int p_pageNumber) {
        m_pages.get(p_fileID).remove(p_pageNumber);
        if (!m_pages.get(p_fileID).isEmpty()) m_pages.remove(p_fileID);
    }
}
