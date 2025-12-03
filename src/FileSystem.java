package src;
public class FileSystem {
    private Disk m_disk;
    public final static int MEMORY_SIZE = 1<<24;//4MB
    public final static int PAGE_SIZE = 1<<12;//4KB
    
    //we are ignoring paging and memory storage for operations other than disk access
    private byte[] m_memory = new byte[MEMORY_SIZE];
    
    public File getRoot() {
        return null;
    }
    public void createFile(String path, byte[] data) {

    }
    public void openFile(String path) {

    }
    public void closeFile(String path) {
        
    }
    public byte readFile(String path, int data_address) {
        return 0;
    }
}
