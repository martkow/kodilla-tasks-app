package calculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
