package com.nabintou.caesarcipher;

/**
 * Caesar Cipher — a 2,000-year-old encryption algorithm, in idiomatic Java.
 *
 * <p>Shifts each letter forward in the alphabet by {@code shift} positions, wrapping
 * around at the end. Non-letter characters (spaces, punctuation, digits) pass
 * through unchanged. Case is preserved.
 *
 * <pre>
 *   new CaesarsCipher().encrypt("HELLO, WORLD!", 3)
 *   → "KHOOR, ZRUOG!"
 * </pre>
 *
 * <p>Decryption is just encryption with a complementary shift, so we don't
 * duplicate the logic — {@link #decrypt} delegates to {@link #encrypt}.
 *
 * <p>Caesar is, of course, trivially breakable (brute-force all 26 shifts, or
 * frequency-analyse the output). This implementation is for learning, not
 * for protecting real secrets.
 *
 * @author Nabintou S. Fofana
 */
public class CaesarsCipher {

    /** Number of letters in the English alphabet. */
    private static final int ALPHABET_SIZE = 26;

    /**
     * Encrypt text by shifting each letter forward by {@code shift} positions.
     *
     * @param text  the plaintext to encrypt (must not be null)
     * @param shift any integer; negative or {@code > 26} values are normalized
     * @return the encrypted text, with non-letters preserved
     * @throws NullPointerException if {@code text} is null
     */
    public String encrypt(String text, int shift) {
        if (text == null) {
            throw new NullPointerException("text must not be null");
        }

        // Normalize shift into the range [0, 25] — handles negatives and large values.
        int normalizedShift = ((shift % ALPHABET_SIZE) + ALPHABET_SIZE) % ALPHABET_SIZE;

        StringBuilder result = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int rotated = (c - base + normalizedShift) % ALPHABET_SIZE;
                result.append((char) (base + rotated));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Decrypt text that was encrypted with the same shift value.
     *
     * <p>Implemented as {@code encrypt(text, -shift)} — encryption and decryption
     * are mirror operations on the alphabet ring.
     *
     * @param text  the ciphertext to decrypt
     * @param shift the same shift used at encryption time
     * @return the original plaintext
     */
    public String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }
}
