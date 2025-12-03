public class main {
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
    }
}