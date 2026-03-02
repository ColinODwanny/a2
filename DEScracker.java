/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: Bakary Ceesay
   Student's full name: Luke Johnson
   Student's full name: Colin O'Dwanny
*****************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class DEScracker
{
    /* feel free to declare helper methods here, if needed */
   //Getting an array of all posible keys
   static final char[] HEX = "0123456789ABCDEF".toCharArray();
   static String[] words;
   static int numWords;
   static int maxLength;


    //This method loads the dictionary from the file and initializes the instance variables.
    //  It was taken directly from dict,Java from assignment 1 and modified to be static and to fit the needs of this class.
    static void loadDictionary(String fileName)
    {
        fileName = "dictionary.txt";
        System.out.print("Loading dictionary... ");
        File chosenFile = new File(fileName);
        try (Scanner reader = new Scanner(chosenFile)) {
            maxLength = 0;
            numWords = 0;
            ArrayList<String> wordsList = new ArrayList<String>();
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim().toLowerCase();
                if (!line.isEmpty()) {
                    wordsList.add(line);
                    numWords++;
                    if (line.length() > maxLength) {
                        maxLength = line.length();
                    }
                }
            }
            words = wordsList.toArray(new String[0]);
            System.out.println("done");
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            System.exit(1);
        }
    }

    //This method counts the number of occurrences of dictionary words in the given text.
    // IT WAS TAKEN DIRECTLY FROM dict.Java from assignment 1 and modified to be static and to fit the needs of this class.
    static int countWords(String text)
    {
        int dictWordOccurrences = 0;
        for (int index = 0; index < words.length; index++) {
            int lastIndex = 0;
            while (lastIndex != -1) {
                lastIndex = text.indexOf(words[index], lastIndex);
                if (lastIndex != -1) {
                    dictWordOccurrences++;
                    lastIndex += words[index].length();
                }
            }
        }
        return dictWordOccurrences;
    }


    //gets an array of all possible keys in the key space. Each key is a 16-character string in hexadecimal format,
    //  where the first 8 characters are the same and the last 8 characters are the same. 
    static String[] keySpace()
    {
        String[] keys = new String[HEX.length * HEX.length];
        int index = 0;

        for (char firstDigit : HEX)
        {
            for (char secondDigit : HEX)
            {
                String firstHalf = String.valueOf(firstDigit).repeat(8);
                String secondHalf = String.valueOf(secondDigit).repeat(8);
                keys[index++] = firstHalf + secondHalf;
            }
        }

        return keys;
    }

    // This method takes a ciphertext block in hexadecimal format and a key in hexadecimal format, decrypts the block using the key,
    //  and returns the resulting plaintext as a string.
    //how the decryption works: we create a DES object with the subkeys generated from the keyHex,
    //  then we convert the cipherHex to binary and then to an int array of bits, we decrypt the bits using the DES object, convert the resulting plaintext bits back to hexadecimal, 
    // and finally convert that hexadecimal string to text.
    static String decryptBlock(String cipherHex, String keyHex)
    {
        DES des = new DES(DES.getSubKeys(keyHex));
        int[] ciphertextBits = Utils.binStringToIntArray(Utils.hexToBinString(cipherHex, 64));
        int[] plaintextBits = des.decryptDES(ciphertextBits);
        String plaintextHex = Utils.binStringToHex(Utils.intArrayToBinString(plaintextBits));
        return Utils.hexToText(plaintextHex);
    }

    /* complete the body of this method in between the provided code which
       you may NOT modify.
       This method takes a single command-line argument, namely a 16-character
       string (in hexadecimal format) that represents a DES ciphertext block.
       The output of this method is the most likely corresponding plaintext 
       block. For example:

          > java DEScracker c737d897c2f1ebe3

       would yield the output:

          Loading dictionary... done
          Most likely plaintext block: eggplant
    */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("This program should be invoked with the " +
                               "following argument:");
            System.out.println("  java DEScracker <16-hex-char ciphertext>");
            System.exit(1);
        }
        // keep one argument only (ciphertext); dictionary path stays internal
        loadDictionary("words.txt");
        String answer = "";

        String ciphertextHex = args[0].trim().toUpperCase();
        int maxWords = -1;

        for (String keyHex : keySpace())
        {
            String potentialString = decryptBlock(ciphertextHex, keyHex);
            int score = countWords(potentialString.toLowerCase());
            if (score > maxWords)
            {
                maxWords = score;
                answer = potentialString;
            }
        }

        System.out.println("Most likely plaintext block: " + answer);

    }// main method

}// class DEScracker
