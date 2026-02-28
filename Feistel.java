/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

import java.util.Arrays;

class Feistel
{
    int w;             // the half-width of a block (in bits)
    int n;             // the number of rounds
    FeistelFunction F; // the round function
    int[][] K;         // the set of sub-keys

    /* do not modify this constructor */
    Feistel(int w, int n, FeistelFunction F, int[][] K)
    {
        this.w = w;
        this.n = n;
        this.F = F;
        this.K = K;
    }// constructor

    /* given a 2w-bit vector of plaintext, return the 2w-bit encrypted 
       input data block.
     */
    int[] encrypt(int[] block)
    {
        int[] right = new int[w];
        int[] left  = new int[w];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < right.length; j++){
                left[j]  = block[j];
                right[j] = block[block.length/2 + j];
            }
            right = Utils.XOR(left, F.round(right,K[i + 1]));
            for(int j = 0; j < block.length/2; j++){
                block[j] = block[block.length/2 + j];
                block[block.length/2 + j] = right[j];
            }
        }
        for(int i = 0; i < block.length/2; i++){
            left[i]  = block[block.length/2 + i];
            right[i] = block[i];
        }
        for(int i = 0; i < block.length/2; i++){
            block[i] = left[i];
            block[block.length/2 + i] = right[i];
        }

        return block; // here to please the compiler
    }// encrypt method

    /* given a 2w-bit vector of encrypted ciphertext, return the 2w-bit 
       plaintext block.
     */
    int[] decrypt(int[] block)
    {
        int[][] KCopy = new int[K.length][K[0].length];
        for(int i = 0; i < K.length; i++){
            for(int j = 0; j < K[0].length; j++){
                KCopy[i][j] = K[i][j];
            }
        }
        for(int i = 0; i < K.length - 1; i++){
            for(int j = 0; j < K[0].length; j++){
                K[i + 1][j] = KCopy[KCopy.length - i - 1][j];
            }
        }
        int[] decryptedBits = encrypt(block);
        K = KCopy;
        return decryptedBits; // here to please the compiler     
    }// decrypt method

    /* I will use this driver code to test your program. Do not modify it.
    */
    public static void main(String[] args)
    {
        //DELETE ME
        // int[][] keys = {
        //     {0,0,0,0,0,0},
        //     {0,1,0,1,0,1},
        //     {1,0,1,0,1,0},
        //     {1,1,1,1,1,0}
        // };
        // FeistelFunction f = new FeistelAllOnes();
        // Feistel meep = new Feistel(3, 3, f, keys);
        // int[] myArr = {1,0,1,0,1,1};
        // meep.decrypt(meep.encrypt(myArr));
        //DELETE ME
        if (args.length != 5)
        {
            System.out.println("This program should be invoked with the " +
                               "following arguments:");
            System.out.println("  java Feistel <e or d> <w> <#rounds> " +
                               "<allzeros or allones or and> <hex block>");
            System.exit(1);
        }

        boolean encrypt = args[0].equals("e");
        int numBits = Integer.parseInt(args[1]);
        int numRounds = Integer.parseInt(args[2]);
        String roundFn = args[3];
        int[] block = Utils.binStringToIntArray(
                        Utils.hexToBinString(args[4],4*args[4].length()));      
        int[][] subkeys = new int[1+numRounds][numBits];
        Feistel cipher = null;;
        if (roundFn.equals("allzeros"))
        {
            cipher = new Feistel(numBits,numRounds, 
                                         new FeistelAllZeros(),subkeys);
        } else if (roundFn.equals("allones"))
        {
            cipher = new Feistel(numBits,numRounds, 
                                         new FeistelAllOnes(),subkeys);
        } else if (roundFn.equals("and"))
        {
            for(int round=1; round<=numRounds; round +=2)
            {
                for(int bit=0; bit<numBits; bit++)
                {
                    subkeys[round][bit] = 0;
                    if (round < numRounds)
                        subkeys[round+1][bit] = 1;                  
                }
            }
            cipher = new Feistel(numBits,numRounds,new FeistelAnd(),subkeys);
        }

        if (encrypt)
        {
            System.out.println( 
                   Utils.binStringToHex(
                         Utils.intArrayToBinString(cipher.encrypt(block)))
                               );
        } else
        {
            System.out.println( 
                   Utils.binStringToHex(
                         Utils.intArrayToBinString(cipher.decrypt(block)))
                               );
        }
    }// main method

}// Feistel class
