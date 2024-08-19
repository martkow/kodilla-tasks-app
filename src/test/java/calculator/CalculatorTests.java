package calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

    @DisplayName("Test case for validateIsPrime method: -1")
    @Test
    void testCaseForValidateIsPrimeForMinusOne() {
        // Given
        int number = -1;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertFalse(result);
    }

    @DisplayName("Test case for validateIsPrime method: 1")
    @Test
    void testCaseForValidateIsPrimeForOne() {
        // Given
        int number = 1;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertFalse(result);
    }

    @DisplayName("Test case for validateIsPrime method: 2")
    @Test
    void testCaseForValidateIsPrimeForTwo() {
        // Given
        int number = 2;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertTrue(result);
    }

    @DisplayName("Test case for validateIsPrime method: 3")
    @Test
    void testCaseForValidateIsPrimeForThree() {
        // Given
        int number = 3;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertTrue(result);
    }

    @DisplayName("Test case for validateIsPrime method: 4")
    @Test
    void testCaseForValidateIsPrimeForFour() {
        // Given
        int number = 4;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertFalse(result);
    }

    @DisplayName("Test case for validateIsPrime method: 131")
    @Test
    void testCaseForValidateIsPrimeForThirteen() {
        // Given
        int number = 13;
        // When
        boolean result = Calculator.validateIsPrime(number);
        // Then
        Assertions.assertTrue(result);
    }

    @DisplayName("Test case for sortList method: empty list")
    @Test
    void testCaseForSortListForEmptyList() {
        // Given
        List<Integer> list = new ArrayList<>();
        // When
        List<Integer> result = Calculator.sortList(list);
        // Then
        Assertions.assertEquals(result, list);
    }

    @DisplayName("Test case for sortList method: list with negative numbers")
    @Test
    void testCaseForSortListForListWithNegativeNumbers() {
        // Given
        List<Integer> list = new ArrayList<>(List.of(0, 2, -1, 1, -3));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.sortList(list));
    }

    @DisplayName("Test case for sortList method: list containing some equal elements")
    @Test
    void testCaseForSortListForListContainingSomeEqualElements() {
        // Given
        List<Integer> list = new ArrayList<>(List.of(1, 20, 10, 1, 3, 20, 1));
        // When
        List<Integer> result = Calculator.sortList(list);
        // Then
        Assertions.assertEquals(result, new ArrayList<>(List.of(1, 1, 1, 3, 10, 20, 20)));
    }

    @DisplayName("Test case for sortListBubble method: empty list")
    @Test
    void testCaseForSortListBubbleForEmptyList() {
        // Given
        List<Integer> list = new ArrayList<>();
        // When
        List<Integer> result = Calculator.sortListBubble(list);
        // Then
        Assertions.assertEquals(result, list);
    }

    @DisplayName("Test case for sortListBubble method: list with negative numbers")
    @Test
    void testCaseForSortListBubbleForListWithNegativeNumbers() {
        // Given
        List<Integer> list = new ArrayList<>(List.of(0, 2, -1, 1, -3));
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.sortListBubble(list));
    }

    @DisplayName("Test case for sortListBubble method: list containing some equal elements")
    @Test
    void testCaseForSortListBubbleForListContainingSomeEqualElements() {
        // Given
        List<Integer> list = new ArrayList<>(List.of(1, 20, 10, 1, 3, 20, 1));
        // When
        List<Integer> result = Calculator.sortListBubble(list);
        // Then
        Assertions.assertEquals(result, new ArrayList<>(List.of(1, 1, 1, 3, 10, 20, 20)));
    }

    @DisplayName("Test case for convertCharToInteger method: correct data")
    @Test
    void testCaseForConvertCharToIntegerMethodForCorrectData() {
        // Given
        // When
        int result = Calculator.convertCharToInteger('7');
        // Then
        Assertions.assertEquals(7, result);
    }

    @DisplayName("Test case for convertCharToInteger method: wrong data")
    @Test
    void testCaseForConvertCharToIntegerMethodForWrongData() {
        // Given
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.convertCharToInteger('b'));
    }

    @DisplayName("Test case for convertStringToInteger method: correct data")
    @Test
    void testCaseForConvertStringToIntegerMethodForCorrectData() {
        // Given
        // When
        int result = Calculator.convertStringToInteger("-7012");
        // Then
        Assertions.assertEquals(-7012, result);
    }

    @DisplayName("Test case for convertCharToInteger method: wrong data")
    @Test
    void testCaseForConvertStringToIntegerMethodForWrongData() {
        // Given
        // When
        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> Calculator.convertStringToInteger("70b12"));
    }

    @DisplayName("Test case for convertDecimalToBinaryOrHex")
    @Test
    void shouldReturnBinary() {
        // Given
        // When
        String result = Calculator.convertDecimalToBinaryOrHex(-41, 2);
        // Then
        Assertions.assertEquals("-101001", result);
    }

    @DisplayName("Test case for convertDecimalToBinaryOrHex")
    @Test
    void shouldReturnHex() {
        // Given
        // When
        String result = Calculator.convertDecimalToBinaryOrHex(-923, 16);
        // Then
        Assertions.assertEquals("-39b", result);
    }

    @DisplayName("Test case for transposeMatrix method")
    @Test
    void shouldReturnTransposition() {
        // Given
        int[][] matrix = new int[][] {
                {1,2,3},  // 1 4
                {4,5,6}   // 2 5
        };                // 3 6
        // When
        int[][] result = Calculator.transposeMatrix(matrix);
        // Then
        Assertions.assertEquals(3, result.length);
        Assertions.assertEquals(2, result[0].length);

        Assertions.assertEquals(1, result[0][0]);
        Assertions.assertEquals(4, result[0][1]);
        Assertions.assertEquals(2, result[1][0]);
        Assertions.assertEquals(5, result[1][1]);
        Assertions.assertEquals(3, result[2][0]);
        Assertions.assertEquals(6, result[2][1]);
    }
}
