import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Reads integers from text files, converts them to arrays,
 * and calculates statistical properties (Mean, Median, Mode).
 *
 * @author  Yoma Ozoh
 * @version 1.0
 * @since   2026-10-28
 */
public final class ArraysVLists {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ArraysVLists() {
        // Prevents creating an instance of this utility class
        throw new IllegalStateException("Utility class");
    }

    /**
     * Calculates the mean (average) of an array of integers.
     *
     * @param array the array of integers
     * @return the mean as a double
     */
    public static double calcMean(final int[] array) {
        // Variable to accumulate the sum of all elements in the array
        double sum = 0;

        // Loop through each number in the array and add it to sum
        for (int num : array) {
            sum += num;
        }

        // Divide the total sum by the number of elements to get the mean
        return sum / array.length;
    }

    /**
     * Calculates the median of a sorted array of integers.
     *
     * @param array the sorted array of integers
     * @return the median as a double
     */
    public static double calcMedian(final int[] array) {
        // Store the number of elements in the array
        final int length = array.length;

        // Check if the array length is odd
        if (length % 2 != 0) {
            // If odd, return the exact middle element
            return array[length / 2];
        } else {
            // If even, average the two middle values
            return (array[(length / 2) - 1] + array[length / 2]) / 2.0;
        }
    }

    /**
     * Calculates the mode(s) of an array of integers.
     *
     * @param array the array of integers
     * @return an array containing the mode(s)
     */
    public static int[] calcMode(final int[] array) {
        // List to store the mode(s) found
        final ArrayList<Integer> modesList = new ArrayList<>();
        // Variable to track the highest frequency of any number
        int maxCount = 0;

        // Step 1: Find the maximum frequency of any number in the array
        for (int i = 0; i < array.length; i++) {
            int count = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[j] == array[i]) {
                    count++;
                }
            }
            // Update maxCount if a higher occurrence count is found
            if (count > maxCount) {
                maxCount = count;
            }
        }

        // Step 2: Collect all unique numbers that match the highest frequency
        for (int i = 0; i < array.length; i++) {
            int count = 0;
            for (int j = 0; j < array.length; j++) {
                if (array[j] == array[i]) {
                    count++;
                }
            }
            // Add to list if frequency matches
            // maxCount and it hasn't been added yet
            if (count == maxCount && !modesList.contains(array[i])) {
                modesList.add(array[i]);
            }
        }

        // Step 3: Convert the ArrayList of modes to a fixed-size int array
        final int[] modesArray = new int[modesList.size()];
        for (int i = 0; i < modesList.size(); i++) {
            modesArray[i] = modesList.get(i);
        }

        // Return the final array of modes
        return modesArray;
    }

    /**
     * Main method to run the statistics loop.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        // Scanner to read user input from the console
        final Scanner userInputScanner = new Scanner(System.in);
        // Flag to keep running the program until the user types 'q'
        boolean keepPlaying = true;

        // Main application loop
        while (keepPlaying) {
            // Prompt the user for a text file name
            System.out.print("Enter file name"
            + "(e.g., Unit2-07-set1.txt) or 'q' to quit: ");
            final String fileName = userInputScanner.nextLine().trim();

            // Check if user wants to quit
            if (fileName.equalsIgnoreCase("q")) {
                System.out.println("Thank you for playing!");
                keepPlaying = false;
            } else {
                try {
                    // Open the requested text file
                    final File file = new File(fileName);
                    final Scanner fileScanner = new Scanner(file);
                    // Use ArrayList since file size
                    // is dynamic and unknown initially
                    final ArrayList<Integer> list = new ArrayList<>();

                    // Read every integer line from the file and store in list
                    while (fileScanner.hasNextInt()) {
                        list.add(fileScanner.nextInt());
                    }
                    fileScanner.close();

                    // Check if file had no integers
                    if (list.isEmpty()) {
                        System.out.println("File is empty.\n");
                        continue;
                    }

                    // Copy values into a fixed-length
                    // array for parameter passing
                    final int[] numbers = new int[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        numbers[i] = list.get(i);
                    }

                    // Display all original elements on a single line
                    System.out.println("\nOriginal Data:");
                    for (int num : numbers) {
                        System.out.print(num + " ");
                    }
                    System.out.println();

                    // Sort the array in ascending order
                    // before performing statistics
                    Arrays.sort(numbers);

                    // Call statistical calculation functions
                    final double mean = calcMean(numbers);
                    final double median = calcMedian(numbers);
                    final int[] modes = calcMode(numbers);

                    // Output results formatted to 2 decimal places
                    System.out.printf("Mean: %.2f\n", mean);
                    System.out.printf("Median: %.2f\n", median);
                    System.out.print("Mode: ");
                    for (int mode : modes) {
                        System.out.print(mode + " ");
                    }
                    System.out.println("\n");

                } catch (FileNotFoundException e) {
                    // Catch file opening errors if file path/name is invalid
                    System.out.println("Error: File not found."
                            + " Please try again.\n");
                }
            }
        }

        // Close console input scanner resource
        userInputScanner.close();
    }
}
