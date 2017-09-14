package lobby;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Console {

    /**
     * Input stream.
     */
    private static final Scanner in = new Scanner(System.in);

    /**
     * Output stream.
     */
    private static final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

    /**
     * Text colors.
     */
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * Reads a player input, has to be integer between max and min (included).
     * Keeps asking until a correct input is read.
     * @param min minimum number that can be inserted.
     * @param max max number that can be inserted.
     * @return the allowed input of the player.
     */
    public static int readInt(int min, int max) {
        boolean bool = true;
        int input = 0;
        String s;
        while (bool) {
            writeGreen("Write a number: ");
            try {
                s = in.nextLine();
                input = Integer.parseInt(s);
                if (input < min || input > max)
                    throw new NumberFormatException();
                bool = false;
            } catch (NumberFormatException e) {
                writeRed("Wrong input, try again.");
            }
        }
        return input;
    }

    /**
     * Reads a string in input.
     * @return the string read.
     */
    public static String readString() {
        return in.nextLine();
    }

    public static void write(String message) {
        out.println(message);
    }

    public static void writeBlue(String message) {
        out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void writeGreen(String message) {
        out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void writeRed(String message) {
        out.println(ANSI_RED + message + ANSI_RESET);
    }
}
