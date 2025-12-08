package src;

public class Demo {
    public static void main(String[] args) {

        System.out.println("=== FILE SYSTEM DEMO START ===\n");

        FileSystem fs = new FileSystem();

        System.out.println("Creating files at root directory...");

        byte[] file1Data = "Hello from CS149 File System!".getBytes();
        byte[] file2Data = "This is a second root-level file.".getBytes();

        fs.createFile("/FILEONE", file1Data);
        fs.createFile("/FILETWO", file2Data);

        System.out.println("Root files created.\n");

        System.out.println("Searching for /FILEONE:");
        DirectoryEntry entry = fs.getDirectoryEntry("/FILEONE");

        if (entry == null) {
            System.out.println("File Not Found\n");
            return;
        }

        System.out.println("File Found\n");

        System.out.println("Opening and reading /FILEONE:");
        File notesFile = fs.getAndOpenFile("/FILEONE");

        if (notesFile == null) {
            System.out.println("Failed to open /FILEONE");
            return;
        }

        for (int i = 0; i < notesFile.getDirectoryEntry().getSize(); i++) {
            System.out.print((char) notesFile.readData(i));
        }

        notesFile.closeFile();
        System.out.println("\n");

        System.out.println("Writing additional data into /FILEONE...");

        byte[] newData = " UPDATED!".getBytes();
        notesFile = fs.getAndOpenFile("/FILEONE");

        if (notesFile == null) {
            System.out.println("Failed to reopen /FILEONE");
            return;
        }

        int startAddress = notesFile.getDirectoryEntry().getSize();
        notesFile.changeSize(startAddress + newData.length);

        for (int i = 0; i < newData.length; i++) {
            notesFile.writeData(startAddress + i, newData[i]);
        }

        System.out.println("Write complete.");
        System.out.println(
            "New file size: " + notesFile.getDirectoryEntry().getSize() + " bytes\n"
        );

        notesFile.closeFile();

        System.out.println("Re-reading updated /FILEONE:");

        notesFile = fs.getAndOpenFile("/FILEONE");

        if (notesFile == null) {
            System.out.println("Failed to reopen /FILEONE");
            return;
        }

        for (int i = 0; i < notesFile.getDirectoryEntry().getSize(); i++) {
            System.out.print((char) notesFile.readData(i));
        }

        notesFile.closeFile();
        System.out.println("\n");

        System.out.println("Opening and reading /FILETWO:");

        File imageFile = fs.getAndOpenFile("/FILETWO");

        if (imageFile == null) {
            System.out.println("Failed to open /FILETWO");
            return;
        }

        for (int i = 0; i < imageFile.getDirectoryEntry().getSize(); i++) {
            System.out.print((char) imageFile.readData(i));
        }

        imageFile.closeFile();

        // FolderDemo
        System.out.println("\n\n=== FOLDER SYSTEM DEMO ===");

        fs.createDirectory("/FOLDER");
        System.out.println("Folder: \"/FOLDER\" created");
        byte[] file3Data = "I'm in a folder!".getBytes();
        fs.createFile("/FOLDER/FILE3",file3Data);
        System.out.println("File: \"/FOLDER/FILE3\" created");

        File file3 = fs.getAndOpenFile("/FOLDER/FILE3");
        if (file3 == null) {
            System.out.println("Failed to open /FOLDER/FILE3");
            return;
        }
        for (int i = 0; i < file3.getDirectoryEntry().getSize(); i++) {
            System.out.print((char) file3.readData(i));
        }
        file3.closeFile();

        System.out.println("\n\n=== FILE SYSTEM DEMO COMPLETE ===");
    }
}
