package com.nabintou.caesarcipher;

/**
 * Lightweight assertion-style tests for {@link CaesarsCipher}.
 *
 * <p>Intentionally hand-rolled (no JUnit dependency) so the project stays
 * runnable with just <code>./gradlew run</code>. For a real production
 * project I'd reach for JUnit 5 — but for a small learning project, this
 * keeps the dependency graph at zero.
 *
 * <p>Run from the project root with:
 * <pre>
 *   ./gradlew test    (if the test task is wired up)
 *   // or directly:
 *   java -cp build/classes/java/main com.nabintou.caesarcipher.Test
 * </pre>
 */
public class Test {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("Running Caesar Cipher tests…");
        System.out.println();

        CaesarsCipher c = new CaesarsCipher();

        // Basic round-trip
        check("basic round-trip",
              "HELLO WORLD",
              c.decrypt(c.encrypt("HELLO WORLD", 3), 3));

        // Known fixture: shift 3
        check("HELLO + shift 3", "KHOOR", c.encrypt("HELLO", 3));

        // Case preservation
        check("mixed case", "Khoor, Zruog!", c.encrypt("Hello, World!", 3));

        // Non-letters preserved
        check("digits + punctuation",
              "Khoor 123! @world.",
              c.encrypt("Hello 123! @world.", 3));

        // ROT13 self-inverse
        String rot13 = c.encrypt("Et tu, Brute?", 13);
        check("ROT13 self-inverse",
              "Et tu, Brute?",
              c.encrypt(rot13, 13));

        // Shift of 26 = identity
        check("shift 26 = identity",
              "Anything at all.",
              c.encrypt("Anything at all.", 26));

        // Negative shift
        check("negative shift round-trip",
              "Backwards!",
              c.decrypt(c.encrypt("Backwards!", -5), -5));

        // Large shift normalizes
        check("shift 29 = shift 3",
              c.encrypt("HELLO", 3),
              c.encrypt("HELLO", 29));

        // Empty string
        check("empty string", "", c.encrypt("", 5));

        // Null guard
        try {
            c.encrypt(null, 3);
            recordFail("null input should throw");
        } catch (NullPointerException expected) {
            recordPass("null input throws NPE");
        }

        System.out.println();
        System.out.printf("──────────────────────────────────────%n");
        System.out.printf("  %d passed, %d failed%n", passed, failed);
        System.out.printf("──────────────────────────────────────%n");

        if (failed > 0) System.exit(1);
    }

    private static void check(String name, String expected, String actual) {
        if (expected.equals(actual)) {
            recordPass(name);
        } else {
            recordFail(name + "  expected=\"" + expected + "\"  actual=\"" + actual + "\"");
        }
    }

    private static void recordPass(String name) {
        passed++;
        System.out.println("  ✓ " + name);
    }
    private static void recordFail(String message) {
        failed++;
        System.out.println("  ✗ " + message);
    }
}
