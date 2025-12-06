import java.util.Arrays;

import src.File;
import src.FileSystem;

public class Main {
    public static void main(String args[]) {
        System.out.println("Hello!");
        /**
         * terminal controls for add a file/dir read file/dir (maybe option to load a pre modified drive)
         * 
         * To simulate the filesystem we will try to avoid having any persistent variables representing the disk system that are not byte arrays
         * 
         * layered outline
         * - OS -read certain bytes of a file (just File & length as input)
         * - File System 
         *    - load file into main memory from disk location
         *    - open up directory
         * - Disk "firmware" is just a big byte array
         */
        FileSystem fileSystem = new FileSystem();

        if (args.length == 0) {
            System.out.println("Enter: read <filePath> <length>, create <filePath> <data>");
            return;
        }
        String command = args[0];

        if (command.equals("read")) {
            if (args.length < 3) {
                System.out.println("Missing file path or length.");
                return;
            }
            File f = new File(fileSystem,args[1]);
            f.openFile();
            byte[] data = f.readData(0, Integer.parseInt(args[2]));
            System.out.println(Arrays.toString(data));
            f.closeFile();
        }
        else if (command.equals("create")) {
            if (args.length < 3) {
                System.out.println("Missing file path or data.");
                return;
            }
            fileSystem.createFile(args[1], args[2].getBytes());
        }
        else {
            System.out.println("Invalid command or args.");
        }
    }
}