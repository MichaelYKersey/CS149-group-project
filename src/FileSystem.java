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
        int folderPathLen = p_path.length()-1;
        while (folderPathLen > 0 && p_path.charAt(folderPathLen) != '/') {
            folderPathLen--;
        }
        if (folderPathLen < 0) throw new IllegalArgumentException();
        String folderPath = p_path.substring(0, folderPathLen);
        if (folderPath.equals("")) folderPath = "/";
        Directory directory = getDirectory(folderPath);
        if (directory == null) throw new IllegalArgumentException();
        DirectoryEntry entry = directory.addFile(p_path.substring(folderPathLen+1), "");
        File f = new File(this, p_path);
        f.openFile(entry);
        f.changeSize(p_data.length);
        f.writeData(0, p_data);
        f.closeFile();
    }
    public void createDirectory(String p_path) {
        int folderPathLen = p_path.length()-1;
        while (folderPathLen > 0 && p_path.charAt(folderPathLen) != '/') {
            folderPathLen--;
        }
        if (folderPathLen < 0) throw new IllegalArgumentException();
        String folderPath = p_path.substring(0, folderPathLen);
        if (folderPath.equals("")) folderPath = "/";   
        Directory directory = getDirectory(folderPath);
        if (directory == null) throw new IllegalArgumentException();
        DirectoryEntry entry = directory.addDirectory(p_path.substring(folderPathLen+1), "");
    }
    public Directory getRoot() {
        return new Directory(this, "",m_fat.getRootClusterNumber());
    }
    public FAT getFAT() {
        return m_fat;
    }
    public Disk getDisk() {
        return m_disk;
    }
    public File getAndOpenFile(String p_path) {
        File f = new File(this, p_path);
        f.openFile(getDirectoryEntry(p_path));
        return f;
    }
    public Directory getDirectory(String p_path) {
        if (p_path.equals("/")) return getRoot();
        DirectoryEntry entry = getDirectoryEntry(p_path);
        if (entry == null) return null;
        return new Directory(this,p_path,entry.getStartCluster());
    }
    //to path from root do /f2/f1/f3 (you can't reach the root because there is no entry for it)
    public DirectoryEntry getDirectoryEntry(String p_path) {
        Directory currentDirectory = getRoot();
        String[] names = p_path.split("/");
        if (!names[0].equals("")) throw new IllegalArgumentException();
        String cur_path = "";
        for (int i=1; i<names.length-1; i++) {
            DirectoryEntry nexEntry = currentDirectory.findFile(names[i]);
            if (nexEntry == null || !nexEntry.isDirectory()) return null;
            cur_path += "/"+names[i];
            currentDirectory = new Directory(this,cur_path,nexEntry.getStartCluster());
        }
        return currentDirectory.findFile(names[names.length-1]);
    }
}
