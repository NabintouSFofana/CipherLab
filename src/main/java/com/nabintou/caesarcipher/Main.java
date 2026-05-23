package com.nabintou.caesarcipher;

public class Main {

    public static void main(String[] args) {
        CaesarsCipher cipher = new CaesarsCipher();

        System.out.println("==============================================");
        System.out.println("    Caesar Cipher — a small Java demonstration");
        System.out.println("==============================================");
        System.out.println();

        demo(cipher, "HELLO, WORLD!", 3);
        demo(cipher, "The quick brown fox jumps over the lazy dog.", 7);
        demo(cipher, "Et tu, Brute?", 13);  // Shift 13 = ROT13: self-inverse
        demo(cipher, "Edge case: shift of 26 should equal 0.", 26);
        demo(cipher, "Negative shifts work too.", -5);

        System.out.println("All right, that's the catalogue. — N.");
    }

    private static void demo(CaesarsCipher cipher, String text, int shift) {
        String encrypted = cipher.encrypt(text, shift);
        String decrypted = cipher.decrypt(encrypted, shift);

        System.out.printf("shift = %d%n", shift);
        System.out.printf("  plain      : %s%n", text);
        System.out.printf("  encrypted  : %s%n", encrypted);
        System.out.printf("  decrypted  : %s%n", decrypted);
        System.out.printf("  round-trip : %s%n", text.equals(decrypted) ? "OK" : "FAILED");
        System.out.println();
    }
}
