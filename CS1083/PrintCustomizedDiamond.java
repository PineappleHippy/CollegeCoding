import java.util.Scanner;

public class PrintCustomizedDiamond {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

// declare the variables
        char letter;
        int size = 0;

// gather user inputs
        System.out.print("Enter a letter: ");
        letter = scnr.next().charAt(0);
        while ((size < 6) || (size % 2 != 0)) {
            System.out.print("Enter a size (even number no less than 6): ");
            size = scnr.nextInt();
        }
        System.out.println();

// compute any calculations.
// print diamond in two parts.
        // diamond part 1.
        for (int i = (size / 2) - 1; i >= 0; --i) {
            //prints the number of spaces
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            //prints the number of letters
            for (int j = 0; j < size - i * 2; j++) {
                System.out.print(letter);
            }
            System.out.println();
        }
        // diamond part 2.
        for (int k = 0; k <= (size / 2) - 1; ++k) {
            //prints the number of spaces
            for (int j = 0; j < k; j++) {
                System.out.print(" ");
            }
            //prints the number of letters
            for (int j = 0; j < size - k * 2; j++) {
                System.out.print(letter);
            }
            System.out.println();
        }

        scnr.close();
    }
}