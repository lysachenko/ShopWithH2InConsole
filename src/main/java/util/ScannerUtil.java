package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ERROR_MESSAGE = "Wrong type! Try again: ";

    public static int getInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print(ERROR_MESSAGE);
                scanner.next();
            }
        }
    }

    public static float getFloat() {
        while (true) {
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.print(ERROR_MESSAGE);
                scanner.next();
            }
        }
    }

    public static long getLong() {
        while (true) {
            try {
                return scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.print(ERROR_MESSAGE);
                scanner.next();
            }
        }
    }
}
