package src;

public class File implements ReadWriteable{
    FileSystem m_fileSystem;
    DirectoryEntry m_directoryEntryLocation;
    short[] m_clusters;
    String m_path;
    File(FileSystem p_fileSystem, String p_path) {
        m_fileSystem = p_fileSystem;
        m_path = p_path;
    }
    /**
     * linked file information to object
     */
    public void openFile() {

    }
    /**
     * unlinks file information from object
     */
    public void closeFile() {
        
    }
    /**
     * changes the size of a file
     */
    public void changeSize(int p_newSize) {

    }
    @Override
    public void writeData(int p_startAddress, byte p_data) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public byte readData(int p_address) {
        // TODO Auto-generated method stub
        return 0;
    }
}
