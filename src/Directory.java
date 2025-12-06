package src;

import java.io.IOError;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class Directory extends File {

    public Directory(FileSystem p_fileSystem, String p_path) {
        super(p_fileSystem, p_path);
    }

    public DirectoryEntry[] getContents() {
        int entryCount = m_directoryEntry.getSize()/DirectoryEntry.ENTRY_SIZE -1;
        DirectoryEntry[] entries = new DirectoryEntry[entryCount-1];
        for (int i = 0; i < entryCount-1; i++) {
            int startAddress = mapAddressToDisk(i*DirectoryEntry.ENTRY_SIZE);
            entries[i] = new DirectoryEntry(startAddress, true);
            // if (entries[i].isTerminatingEntry()) throw new DataFormatException();
        }
        // if (!entries[entryCount].isTerminatingEntry()) throw new DataFormatException();
        return entries;
    }
    public void addEntry(int p_diskAddress,
        String p_name, 
        String p_extension, 
        boolean p_isDirectory, 
        short p_startClusterNumber,
        int p_size) {

        changeSize(m_directoryEntry.getSize()+DirectoryEntry.ENTRY_SIZE);
        int newEntryAddress = mapAddressToDisk(m_directoryEntry.getSize()-DirectoryEntry.ENTRY_SIZE*2);
        new DirectoryEntry(newEntryAddress,p_name,
            p_extension,
            p_isDirectory,
            p_startClusterNumber,
            p_size);
        //update terminating entry
        int newTerminatingEntryAddress = mapAddressToDisk(m_directoryEntry.getSize()-DirectoryEntry.ENTRY_SIZE);
        new DirectoryEntry(newTerminatingEntryAddress, false);
    }
    public DirectoryEntry findFile(String p_name) {
        int entryCount = m_directoryEntry.getSize()/DirectoryEntry.ENTRY_SIZE -1;
        for (int i = 0; i < entryCount-1; i++) {
            int startAddress = mapAddressToDisk(i*DirectoryEntry.ENTRY_SIZE);
            DirectoryEntry de = new DirectoryEntry(startAddress, true);
            // if (de.isTerminatingEntry()) throw new DataFormatException();
            if (de.getName() == p_name) return de;
        }
        return null;
    }
}
