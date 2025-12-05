package src;

public class ByteArrayUtils {
    static byte[] toByteArray(int p_value) {
        byte[] array = new byte[4];
        writeInt(array, 0, p_value);
        return array;
    }
    static void writeInt(byte[] p_array, int p_offset, int p_value) {
        p_array[p_offset+0] = (byte) (p_value >> 24);
        p_array[p_offset+1] = (byte) (p_value >> 16);
        p_array[p_offset+2] = (byte) (p_value >> 8);
        p_array[p_offset+3] = (byte) (p_value >> 0);
    }
    static int readInt(byte[] p_array) {return readInt(p_array, 0);}
    static int readInt(byte[] p_array, int p_offset) {
        int value = 0;
        for (int i=0; i<4; i++) {
            value <<= 8;
            value |= p_array[i+p_offset];
        }
        return value;
    }
    
    static byte[] toByteArray(short p_value) {
        byte[] array = new byte[2];
        writeInt(array, 0, p_value);
        return array;
    }
    static void writeShort(byte[] p_array, int p_offset, short p_value) {
        p_array[p_offset+0] = (byte) (p_value >> 8);
        p_array[p_offset+1] = (byte) (p_value >> 0);
    }
    static short readShort(byte[] p_array) {return readShort(p_array, 0);}
    static short readShort(byte[] p_array, int p_offset) {
        short value = 0;
        for (int i=0; i<2; i++) {
            value <<= 8;
            value |= p_array[i+p_offset];
        }
        return value;
    }
    
    static void copyCharArray(byte[] p_byteArray, int p_offset, char[] p_charArray) {
        for (int i=0; i<p_charArray.length; i++) {
            p_byteArray[i+p_offset] = (byte) p_charArray[i];
        }
    }
    static char[] getCharArray(byte[] p_byteArray, int p_offset, int p_length) {
        char[] charArray = new char[p_length];
        for (int i=0; i<p_length; i++) {
            charArray[i+p_offset] = (char) p_byteArray[i];
        }
        return charArray;
    }
}
