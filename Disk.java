public class Disk {
    final static int DISK_SIZE = 1<<30;//1GB 
    final static int BLOCK_SIZE = 1 << 12;//4KB

    public byte[] array = new byte[DISK_SIZE];

    public byte[] getBlock(int block_num) {
        return null;
    }
    public void writeBlock(int block_num, byte[] data) {
        
    }
}