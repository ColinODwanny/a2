import java.util.HashMap;

/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

class Utils
{

    public static void main (String[] args){
        textToHex("hello");
        hexToText("636f6c696e");
        binStringToIntArray("010101");
        int[] myArr = {1,1,1,1};
        int[] myArr2 = {0,1,0,1};
        intArrayToBinString(myArr);
        hexToBinString("ABC", 16);
        binStringToHex("01101110");
        XOR(myArr, myArr2);
        int[] myArr3 = {1,2,3,4,5,5,5,5,2,3,4,1,5,5}; 
        int[] myArr4 = {0,0,0,0,1};
        applyPermut(myArr3, myArr4);
    }

    /* given a character string, return the sequence of ASCII codes (in 
       hexadecimal) for the characters in the string. Sample input/output:
          input: "ABC"       output: "414243"
          input: "\nA\nB\n"  output: "0A410A420A"
       Note that each input character always yields exactly two hex digits.
     */
    static String textToHex(String s)
    {
        StringBuilder sb = new StringBuilder();
        char[] stringCharArr = s.toCharArray();
        for(int i = 0; i < stringCharArr.length; i++){
            sb.append(Integer.toHexString(stringCharArr[i]));
        }
        return sb.toString().toUpperCase(); // only here to please the compiler
        
    }// textToHex method

    /* given a string of ascii codes (in hexadecimal), return the string of
       the corresponding characters.
          input: "414243"      output: "ABC" 
          input: "0A410A420A"  output: "\nA\nB\n"
       Note that all input strings have an even length.
     */
    static String hexToText(String s)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length() - 1; i = i + 2){
            sb.append((char) Integer.parseInt(s.substring(i, i + 2), 16));
        }
        return sb.toString(); // only here to please the compiler
    }// hexTotext method

    /* given a binary string, return the integer array of the same length as
       the bit string and in which each element is the integer value of the
       bit in the corresponding position in the string. Sample input/output:
           input: "01101"           output: [0, 1, 1, 0, 1]
    */
    static int[] binStringToIntArray(String bits)
    {
        int[] bitsArr = new int[bits.length()];
        for(int i = 0; i < bits.length(); i++){
            bitsArr[i] = Character.getNumericValue(bits.charAt(i));
        }
        return bitsArr; // only here to please the compiler
    }// bitStringToIntArray method

    /* given an integer array containing 0s and 1s exclusively, return
       the binary string of the same length in which each element is the 
       character ('0' or '1') of the corresponding element in the input array.
           input: [0, 1, 1, 0, 1]      output: "01101"
    */
    static String intArrayToBinString(int[] data)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            sb.append(String.valueOf(data[i]));
        }
        return sb.toString(); // only here to please the compiler
    }//intArrayToBinString method
    
    /* given an arbitrary long string of hexadecimal digits and a number 
       of bits, return the binary string of the given length corresponding
       to the first input. Sample input/output:
           input: "ABC" 16     output: "0000101010111100"
           input: "01F3" 16    output: "0000000111110011"
       Note: You must assume that numBits is always larger than or equal to
       4 times the number of hexadecimal digits in the first argument.
     */
    static String hexToBinString(String s, int numBits)
    {
        HashMap<Character, String> hexToBinaryMappings = new HashMap<Character, String>();
        hexToBinaryMappings.put('0', "0000");
        hexToBinaryMappings.put('1', "0001");
        hexToBinaryMappings.put('2', "0010");
        hexToBinaryMappings.put('3', "0011");
        hexToBinaryMappings.put('4', "0100");
        hexToBinaryMappings.put('5', "0101");
        hexToBinaryMappings.put('6', "0110");
        hexToBinaryMappings.put('7', "0111");
        hexToBinaryMappings.put('8', "1000");
        hexToBinaryMappings.put('9', "1001");
        hexToBinaryMappings.put('A', "1010");
        hexToBinaryMappings.put('B', "1011");
        hexToBinaryMappings.put('C', "1100");
        hexToBinaryMappings.put('D', "1101");
        hexToBinaryMappings.put('E', "1110");
        hexToBinaryMappings.put('F', "1111");

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numBits - s.length() * 4; i++){
            sb.append(0);
        }
        for(int i = 0; i < s.length(); i++){
            sb.append(hexToBinaryMappings.get(s.charAt(i)));
        }
        return sb.toString(); // only here to please the compiler
    }// hexToBinString method

    /* given a binary string, return the hexadecimal representation of the
       input as a String. Sample input/output:
           input: "01101110"           output: "6E"
       Note: you must assume that the length of the input is a multiple of 4.
    */
    static String binStringToHex(String bits)
    {
        HashMap<String, Character> binaryToHexMappings = new HashMap<String, Character>();
        binaryToHexMappings.put("0000", '0');
        binaryToHexMappings.put("0001", '1');
        binaryToHexMappings.put("0010", '2');
        binaryToHexMappings.put("0011", '3');
        binaryToHexMappings.put("0100", '4');
        binaryToHexMappings.put("0101", '5');
        binaryToHexMappings.put("0110", '6');
        binaryToHexMappings.put("0111", '7');
        binaryToHexMappings.put("1000", '8');
        binaryToHexMappings.put("1001", '9');
        binaryToHexMappings.put("1010", 'A');
        binaryToHexMappings.put("1011", 'B');
        binaryToHexMappings.put("1100", 'C');
        binaryToHexMappings.put("1101", 'D');
        binaryToHexMappings.put("1110", 'E');
        binaryToHexMappings.put("1111", 'F');
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bits.length(); i = i + 4){
            sb.append(binaryToHexMappings.get(bits.substring(i, i + 4)));
        }
        return sb.toString(); // only here to please the compiler
    }// binStringToHex method

    
    /* given two arrays of the same size each containing n integer values
       equal to 0 or 1 exclusively, return an n-element array containing the
       bitwise XOR of the pairs of input bits. Sample input/output:
          input: [0, 0, 1, 1] and [0, 1, 0, 1]   output: [0, 1, 1, 0]
     */
    static int[] XOR(int[] a, int[] b)
    {
        int[] XorResult = new int[a.length];
        for(int i = 0; i < XorResult.length; i++){
            XorResult[i] = a[i] ^ b[i];
        }
        return XorResult; // only here to please the compiler
    }// XOR method

    /* given an n-long permutation of bit positions ranging from 1 to m  and 
       an m-bit vector, return the n-bit vector resulting from applying the 
       permutation to the second vector. Sample input/output:
         input: [1, 1, 2, 1, 1, 2, 2] and [0, 1]
         output:  [0, 0, 1, 0, 0, 1, 1]
      Note that the values in the permutation are position indexes
      starting at 1, not 0. Therefore, the value of the bit at position 1 in 
      the second argument is 0, not 1.
     */
    static int[] applyPermut(int[] perm, int[] data)
    {
        int[] permuttedArr = new int[perm.length];
        HashMap<Integer, Integer> indexAndValue = new HashMap<>();
        for(int i = 0; i < data.length; i++){
            indexAndValue.put(i + 1, data[i]);
        }
        for(int i = 0; i < perm.length; i++){
            permuttedArr[i] = indexAndValue.get(perm[i]); 
        }
        return permuttedArr; // only here to please the compiler
    }// applyPermut method

}// class Utils
