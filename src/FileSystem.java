package src;
public class FileSystem {
    private Disk m_disk = new Disk();

    //for simplicity we are gonna have a single FAT table and ignore the value of unused attributes
    //We also won't do long file names (8char file names)
    final static int SECTOR_SIZE = 1 << 9;//512 Bytes
    final static int CLUSTER_SIZE = (1 << 15) ;
    final static int SECTORS_PER_CLUSTER = CLUSTER_SIZE / SECTOR_SIZE;
    final static int TOTAL_SECTORS = Disk.DISK_SIZE / SECTOR_SIZE; // needs to be 65536 or less so it can be represented by 16 bits
    final static int CLUSTERS_PER_TABLE = Disk.DISK_SIZE / CLUSTER_SIZE;

    public void createFile(String p_path, byte[] p_data) {
        
    }
    public void createDirectory(String p_path) {
        
    }
}
