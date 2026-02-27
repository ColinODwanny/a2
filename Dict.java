/*****************************************************
   CS 326 - Spring 2026 - Assignment #1
   Student #1's full name: ______
   Student #2's full name: ______
   Student #3's full name: ______
*****************************************************/

import java.util.*;
import java.io.*;

class Dict
{
    int numWords;    // the number of words in the dictionary
    String[] words;  // the words included in the dictionary
    int maxLength;   // the number of characters in the longest word

    /* This constructor loads the words from the file whose name is passed
       as an argument. In this text file, there is exactly one word per line.
       This constructor initializes the three instance variables above.
       Note that the words must be stored in alphabetical order in the array 
       and that the length of the array must be equal to the number of words 
       in the input file.
    */
    Dict(String fileName)
    {
        System.out.print("Loading dictionary... ");
        File chosenFile = new File(fileName);
        try (Scanner reader = new Scanner(chosenFile)) {
            maxLength = 0;
            numWords= 0;
            ArrayList<String> wordsList = new ArrayList<String>(); 
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                wordsList.add(line);
                numWords++;
                if(line.trim().length() > maxLength){
                    maxLength = line.trim().length();
                }
                words = wordsList.toArray(new String[0]);
            } 
        } catch (FileNotFoundException e) {
                System.out.println("File could not be found!");
            }
        /* You must complete this method by adding code.
           Do not modify or delete the existing code */

        System.out.println("done");
    }// constructor

    /* This method returns true if and only if the given word is contained
       in the dictionary. 
    */
    boolean contains(String word)
    {
        boolean isWordInDictionary = false;
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word)){
                isWordInDictionary = true;
            }
        }
        return isWordInDictionary; // only here to satisfy the compiler
    }// contains method
    
    /* This method returns the number of occurrences of dictionary words  
       in the given text. To be clear, if a dictionary word appears n times
       in the text, then the final count returned by this method  must be 
       incremented by n. Note that two or more words may overlap in the text.
       For example, the text "the bananas" contains the words: 
          "a" "an" "as" "ban" "banana" "bananas" among others            
     */
    int countWords(String text)
    {
        int dictWordOccurrences = 0;
        for(int i = 0; i < words.length; i++){
            int lastIndex = 0; 
            while(lastIndex != -1){
                lastIndex = text.indexOf(words[i], lastIndex);
                if(lastIndex != -1){
                    dictWordOccurrences++;
                    lastIndex += words[i].length();
                }
            }
        }
        return dictWordOccurrences; // only here to satisfy the compiler  
    }// countWords method

    /* This method will only be used for testing purposes 
       You may NOT modify it.
     */
    public static void main(String[] args)
    {   
        Dict d = new Dict(args[0]);
        System.out.println(d.numWords + " " + d.maxLength);
        for(int index=0; index<10; index++) {
            System.out.print(d.words[index] + " ");
        }
        System.out.println(d.words[d.numWords-1]);
    }// main method

}// Dict class
