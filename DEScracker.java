/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

class DEScracker
{
    /* feel free to declare helper methods here, if needed */


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
        // you may edit this line to fit your local setup; but if you do so,
        // make sure to restore it to the following line in your submission
        Dict dict = new Dict("/usr/share/dict/words");
        String answer = "";

        /* to be completed */

        System.out.println("Most likely plaintext block: " + answer);

    }// main method

}// class DEScracker
