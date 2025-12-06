package src;
public class FileSystem {
    private Disk m_disk = new Disk();
    private FAT m_fat = new FAT(m_disk, 0);

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
    public Directory getRoot() {
        return null;
    }
    public FAT getFAT() {
        return m_fat;
    }
    public Disk getDisk() {
        return m_disk;
    }
    public DirectoryEntry getDirectoryEntry(String p_path) {
        Directory currentDirectory = getRoot();
        String[] names = p_path.split("/");
        String cur_path = "";
        for (int i=0; i<names.length-1; i++) {
            DirectoryEntry nexEntry = currentDirectory.findFile(names[i]);
            if (nexEntry == null || !nexEntry.isDirectory()) return null;
            currentDirectory.closeFile();
            currentDirectory = new Directory(this,cur_path+"/"+names[i]);
            currentDirectory.openFile(nexEntry);
        }
        return currentDirectory.findFile(names[names.length-1]);
    }
}
