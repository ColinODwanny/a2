/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
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
