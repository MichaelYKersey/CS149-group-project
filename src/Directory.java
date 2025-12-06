package src;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class Directory {
    private FileSystem m_fileSystem;
    private String m_path;
    private short m_startCluster;
    public Directory(FileSystem p_fileSystem, String p_path, short p_startCluster) {
        m_fileSystem = p_fileSystem;
        m_path = p_path;
        m_startCluster = p_startCluster;
    }

    public ArrayList<DirectoryEntry> getContents() {
        ArrayList<DirectoryEntry> entries = new ArrayList<>();
        int curEntryAddress = getStartingEntryAddress();
        DirectoryEntry curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
        while (!curEntry.isTerminatingEntry()) {
            curEntryAddress = getNextEntryAddress(curEntryAddress);
            curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
            entries.add(curEntry);
        }
        return entries;
    }
    
    public DirectoryEntry addEntry(
        String p_name, 
        String p_extension, 
        boolean p_isDirectory)
    {
        int curEntryAddress = getStartingEntryAddress();
        DirectoryEntry curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
        while (!curEntry.isTerminatingEntry()) {
            curEntryAddress = getNextEntryAddress(curEntryAddress);
            curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
        }
        curEntry = new DirectoryEntry(
            m_fileSystem.getDisk(),
            curEntryAddress,
            p_name,
            p_extension,
            p_isDirectory,
            (short) 0xFF,
            0);
        //check weneed to add another FAT entry
        FAT fat = m_fileSystem.getFAT();
        int offset = fat.getDiskAddressClusterOffset(curEntryAddress)+DirectoryEntry.ENTRY_SIZE;
        if (offset >= FileSystem.CLUSTER_SIZE) {
            fat.appendEntry(fat.getDiskAddressCluster(curEntryAddress));
        }
        curEntryAddress = getNextEntryAddress(curEntryAddress);
        // //update terminating entry
        new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress, false);
        return curEntry;
    }
    public DirectoryEntry addFile(String p_name, String p_extension) {
        return addEntry(p_name, p_extension, false);
    }
    public DirectoryEntry addDirectory(String p_name, String p_extension) {
        DirectoryEntry entry = addEntry(p_name, p_extension, true);
        FAT fat = m_fileSystem.getFAT();
        short cluster = fat.getFreeEntry();
        entry.setStartCluster(cluster);
        new DirectoryEntry(m_fileSystem.getDisk(), fat.mapAddressToDisk(cluster, 0),false);
        return entry;
    }

    public DirectoryEntry findFile(String p_name) {
        int curEntryAddress = getStartingEntryAddress();
        DirectoryEntry curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
        while (!curEntry.isTerminatingEntry() && !curEntry.getName().equals(p_name)) {
            curEntryAddress = getNextEntryAddress(curEntryAddress);
            curEntry = new DirectoryEntry(m_fileSystem.getDisk(),curEntryAddress,true);
        }
        if (curEntry.isTerminatingEntry()) return null;
        return curEntry;
    }
    public int getStartingEntryAddress() {
        return m_fileSystem.getFAT().mapAddressToDisk(m_startCluster, 0);
    }
    public int getNextEntryAddress(int p_curAddress) {
        FAT fat = m_fileSystem.getFAT();
        int offset = fat.getDiskAddressClusterOffset(p_curAddress)+DirectoryEntry.ENTRY_SIZE;
        return fat.mapAddressToDisk(fat.getDiskAddressCluster(p_curAddress), offset);
    }

}
