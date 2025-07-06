package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadInput {

    public static void readIntegerList() {
        Scanner scanner = new Scanner(System.in);

        // First read the integer that indicates the number of subsequent integers.
        int n = scanner.nextInt();
        List<Integer> numbers = new ArrayList<>();

        // Then, read exactly n integers.
        for (int i = 0; i < n; i++) {
            numbers.add(scanner.nextInt());
        }

        // Now you have the list of integers.
        System.out.println("The list of integers is: " + numbers);
        scanner.close();
    }

    public static void readIntegerList2() {
        Scanner scanner = new Scanner(System.in);

        // Read the complete line containing space-separated integers.
        String line = scanner.nextLine();

        // Split the line into tokens, convert each token to an Integer, and collect into a list.
        List<Integer> numbers = Arrays.stream(line.split("\\s+"))
                .map(Integer::parseInt).collect(Collectors.toList());
        // Split the line into tokens using whitespace as the delimiter and convert into a list of strings
        List<String> stringList = Arrays.asList(line.split("\\s+"));

        // Now you have a list of integers.
        System.out.println("The list of integers is: " + numbers);
        scanner.close();
    }

    public static void main(String[] args) {
        readIntegerList();
    }
}
