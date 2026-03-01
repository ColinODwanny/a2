public class DESTests {

    //helpers for testing DES encryption and decryption

    // Convert a 16-character hex string to a 64-element bit array
    static int[] hex64(String hex) {
        return Utils.binStringToIntArray(Utils.hexToBinString(hex.toUpperCase(), 64));
    }

    // Convert a 64-element bit array back to a hex string for comparison
    static String toHex(int[] bits) {
        return Utils.binStringToHex(Utils.intArrayToBinString(bits));
    }

    // Build a DES cipher from a 16-hex-digit key string
    static DES newDES(String keyHex) {
        return new DES(DES.getSubKeys(keyHex));
    }

    //tests for DES encryption and decryption

    public static void main(String[] args) {

        // Test 1
        // Key = 133457799BBCDFF1, PT = 0123456789ABCDEF, CT = 85E813540F0AB405
        int[] ct1 = newDES("133457799BBCDFF1").encryptDES(hex64("0123456789ABCDEF"));
        System.out.println("Test 1 encrypt: " +
            (toHex(ct1).equalsIgnoreCase("85E813540F0AB405") ? "PASS" : "FAIL"));

        int[] pt1 = newDES("133457799BBCDFF1").decryptDES(ct1);
        System.out.println("Test 2 -decrypt: " +
            (toHex(pt1).equalsIgnoreCase("0123456789ABCDEF") ? "PASS" : "FAIL"));

        // Test 2: All-zero key and plaintext
        // Key = 0000000000000000, PT = 0000000000000000, CT = 8CA64DE9C1B123A7
        int[] ct2 = newDES("0000000000000000").encryptDES(hex64("0000000000000000"));
        System.out.println("Test 3 - All-zero encrypt: " +
            (toHex(ct2).equalsIgnoreCase("8CA64DE9C1B123A7") ? "PASS" : "FAIL"));

        int[] pt2 = newDES("0000000000000000").decryptDES(ct2);
        System.out.println("Test 4 - All-zero decrypt: " +
            (toHex(pt2).equalsIgnoreCase("0000000000000000") ? "PASS" : "FAIL"));

        // Test 3: Encrypt then decrypt round-trip with a random key
        String key = "DEADBEEFCAFEBABE";
        String plain = "FEDCBA9876543210";
        int[] ct3 = newDES(key).encryptDES(hex64(plain));
        int[] pt3  = newDES(key).decryptDES(ct3);
        System.out.println("Test 5 - Round-trip: " +
            (toHex(pt3).equalsIgnoreCase(plain) ? "PASS" : "FAIL"));

        // Test 4: Different keys should produce different ciphertexts
        int[] pt = hex64("0123456789ABCDEF");
        String ctA = toHex(newDES("1111111111111111").encryptDES(pt.clone()));
        String ctB = toHex(newDES("2222222222222222").encryptDES(pt.clone()));
        System.out.println("Test 6 - Different keys, different CT: " +
            (!ctA.equalsIgnoreCase(ctB) ? "PASS" : "FAIL"));

        // Test 5: Decrypting with the wrong key should NOT recover the plaintext
        int[] ct5   = newDES("133457799BBCDFF1").encryptDES(hex64("0123456789ABCDEF"));
        int[] wrong = newDES("DEADBEEFCAFEBABE").decryptDES(ct5);
        System.out.println("Test 7 - Wrong key fails: " +
            (!toHex(wrong).equalsIgnoreCase("0123456789ABCDEF") ? "PASS" : "FAIL"));
    }
}
