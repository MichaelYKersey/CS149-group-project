package src;
public class FileSystem {
    private Disk m_disk;
    private VirtualRam m_vram = new VirtualRam(m_disk);
    final static int BLOCK_SIZE = 1 << 12;//4KB

    public File getRoot() {
        return null;
    }
    public void createFile(String p_path, byte[] p_data) {
        
    }
    /**
     * Loads paging information into page table
     * @param p_path path of file
     */
    public void openFile(String p_path) {

    }
    /**
     * unloads paging information from page table
     * @param p_path path of file
     */
    public void closeFile(String p_path) {
        
    }
    /**
     * @param path path of file
     * @param p_address data offset in file
     * @return byte at the section of the file
     */
    public byte readFile(String path, int p_address) {
        return 0;
    }
    /**
     * @param path path of file
     * @param p_startAddress offset in file
     * @param p_length amount of data you want to read
     * @return data at the section of the file
     */
    public byte[] readFile(String path, int p_startAddress, int p_length) {
        return null;
    }
    /**
     * @param p_path path to file
     * @return address of start of file on disk
     */
    private int getDiskAddress(int p_path) {
        return -1;
    }
    /**
     * @param p_path file path
     * @return file ID (-1 if not open)
     */
    private int getFileID(String p_path) {
        return 0;
    }
}
