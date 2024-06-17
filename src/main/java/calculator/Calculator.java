package calculator;

import java.util.List;
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
}
