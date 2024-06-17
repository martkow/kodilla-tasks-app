package calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests for Calculator class")
public class CalculatorTests {
    @DisplayName("Test case for calculateWeightedAverage method: empty list")
    @Test
    void testCaseForCalculateWeightedAverageMethodWithEmptyList() {
        // Given
        List<Integer> grades = new ArrayList<>();
        List<Integer> weights = new ArrayList<>(List.of(1, 2));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.calculateWeightedAverage(grades, weights));
    }

    @DisplayName("Test case for calculateWeightedAverage method: size of lists not equal")
    @Test
    void testCaseForCalculateWeightedAverageMethodWithListsOfDifferentSizes() {
        // Given
        List<Integer> grades = new ArrayList<>(List.of(6));
        List<Integer> weights = new ArrayList<>(List.of(1, 2));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.calculateWeightedAverage(grades, weights));
    }

    @DisplayName("Test case for calculateWeightedAverage method: grade list's elements out of scope")
    @Test
    void testCaseForCalculateWeightedAverageMethodWithOutOfScopeGradesList() {
        // Given
        List<Integer> grades = new ArrayList<>(List.of(7, 1));
        List<Integer> weights = new ArrayList<>(List.of(1, 2));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.calculateWeightedAverage(grades, weights));
    }

    @DisplayName("Test case for calculateWeightedAverage method: weight list's elements out of scope")
    @Test
    void testCaseForCalculateWeightedAverageMethodWithOutOfScopeWeightsList() {
        // Given
        List<Integer> grades = new ArrayList<>(List.of(2, 1));
        List<Integer> weights = new ArrayList<>(List.of(-1, 2));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.calculateWeightedAverage(grades, weights));
    }

    @DisplayName("Test case for calculateWeightedAverage method")
    @Test
    void testCaseForCalculateWeightedAverageMethod() {
        // Given
        List<Integer> grades = new ArrayList<>(List.of(2, 1));
        List<Integer> weights = new ArrayList<>(List.of(3, 7));
        // When
        double result = Calculator.calculateWeightedAverage(grades, weights);
        // Then
        Assertions.assertEquals(6.5, result);
    }
}
