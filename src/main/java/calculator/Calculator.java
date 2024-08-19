package calculator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calculator {
    public static double calculateWeightedAverage(List<Integer> grades, List<Integer> weights) throws IllegalArgumentException{
        if (grades.size() != weights.size() ||
                grades.isEmpty() ||
                !grades.stream().filter(g -> g < 1 || g > 6).collect(Collectors.toSet()).isEmpty() ||
                !weights.stream().filter(w -> w < 1 || w > 10).collect(Collectors.toSet()).isEmpty()) {
            throw new IllegalArgumentException();
        }

        int sum = 0;

        for (int i = 0; i < grades.size(); i++) {
            sum += grades.get(i) * weights.get(i);
        }

        return (double)sum / grades.size();
    }

    public static boolean validateIsPrime(int number) {
        if (number <= 1) {
            return false;
        } else if (number == 2) {
            return true;
        } else if (number % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(number); i = i+2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static List<Integer> sortList(List<Integer> list) throws IllegalArgumentException {
        if (list.isEmpty()) {
            return list;
        }
        if (list.stream().anyMatch(item -> item < 0)) {
            throw new IllegalArgumentException("List contains negative elements.");
        }

        List<Integer> listCopy = new ArrayList<>(list);
        List<Integer> sortedList = new ArrayList<>();

        while (!listCopy.isEmpty()) {
            Optional<Integer> min = listCopy.stream().min(Comparator.naturalOrder());
            sortedList.add(min.get());
            listCopy.remove(min.get());
        }

        return sortedList;
    }

    public static List<Integer> sortListBubble(List<Integer> list) throws IllegalArgumentException {
        if (list.isEmpty()) {
            return list;
        }
        if (list.stream().anyMatch(item -> item < 0)) {
            throw new IllegalArgumentException("List contains negative elements.");
        }

        List<Integer> listCopy = new ArrayList<>(list);
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < listCopy.size() - 1; i++) {
                if (listCopy.get(i) > listCopy.get(i + 1)) {
                    listCopy.set(i, listCopy.set(i + 1, listCopy.get(i)));
                    swapped = true;
                }
            }
        } while (swapped);

        return listCopy;
    }

    public static int convertCharToInteger(char c) throws IllegalArgumentException {
        return switch (c) {
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            default -> throw new IllegalArgumentException("Wrong data.");
        };
    }

    public static int convertStringToInteger(String s) throws IllegalArgumentException {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Empty String.");
        }

        int number = 0;
        int multiplier = 1;
        int startIndex = 0;

        if (s.charAt(0) == '-') {
            multiplier = -1;
            startIndex = 1;
        }

//        for (int i = s.length() - 1; i >= startIndex; i--) {
//            number = number + convertCharToInteger(s.charAt(i)) * (int)Math.pow(10, s.length() - i - 1);
//        }

        number = IntStream.range(0, s.length() - startIndex)
                .map(i -> convertCharToInteger(s.charAt(s.length() - 1 - i)) * (int) Math.pow(10, i))
                .sum();

        return number * multiplier;
    }

    public static String convertDecimalToBinaryOrHex(int number, int system) throws IllegalArgumentException {
        if (system != 2 && system != 16) {
            throw new IllegalArgumentException("Incorrect number system.");
        }

        if (number == 0) {
            return String.valueOf(0);
        }

        StringBuilder stringBuilder = new StringBuilder();
        int temp = Math.abs(number);

        while (temp != 0) {
            stringBuilder.append(Character.forDigit(temp % system, system));
            temp = temp / system;
        }

        return (number < 0 ? "-" : "") + stringBuilder.reverse().toString();
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int[][] transposition = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposition[j][i] = matrix[i][j];
            }
        }

        return transposition;
    }
}
