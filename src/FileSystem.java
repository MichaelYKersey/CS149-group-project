package src;
public class FileSystem {
    private Disk m_disk = new Disk();
    private VirtualRam m_vram = new VirtualRam(m_disk);

    //for simplicity we are gonna have a single FAT table and ignore the value of unused attributes
    //We also won't do long file names (8char file names)
    final static int SECTOR_SIZE = 1 << 9;//512 Bytes
    final static int CLUSTER_SIZE = (1 << 15) ;
    final static int SECTORS_PER_CLUSTER = CLUSTER_SIZE / SECTOR_SIZE;
    final static int TOTAL_SECTORS = Disk.DISK_SIZE / SECTOR_SIZE; // needs to be 65536 or less so it can be represented by 16 bits
    final static int CLUSTERS_PER_TABLE = Disk.DISK_SIZE / CLUSTER_SIZE;

    public DirectoryEntry getRoot() {
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
