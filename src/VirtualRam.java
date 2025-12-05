package src;

public class VirtualRam {
    public final static int MEMORY_SIZE = 1<<24;//4MB
    public final static int PAGE_SIZE = 1<<12;//4KB
    //we are ignoring paging and memory storage for operations other than disk access
    private byte[] m_memory = new byte[MEMORY_SIZE];
    
    /**
     * @param p_fileID file ID in page table
     * @param p_startAddress offset in file
     * @param p_length amount of data you want to read
     * @return data at the section of the file
     */
    public byte[] readVram(int p_fileID, int p_startAddress, int p_length) {
        return null;
    }
    /**
     * @param p_fileID, file ID in page table
     * @param p_address data offset in file
     * @return byte at the section of the file
     */
    public byte readVram(int p_fileID, int p_address) {
        return 0;
    }
    /**
     * maps section of disc to a page
     * @param p_fileID file ID in page table
     * @param p_pageNumber page number
     * @param p_diskAddressStart address to map to in disk
     */
    public void addPageTableEntry(int p_fileID, int p_pageNumber, int p_diskAddressStart) {
        
    }
    /**
     * unmap section of disc to a page
     * @param p_fileID file ID in page table
     * @param p_pageNumber page number
     */
    public void removePageTableEntry(int p_fileID, int p_pageNumber) {
        
    }
}
