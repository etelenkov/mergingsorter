package com.alliedtesting.etelenkov.mergingsorter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static com.alliedtesting.etelenkov.mergingsorter.Iterators.sortingCombiner;
import static org.testng.Assert.assertEquals;

public class IteratorsTest {

    /**
     * Test for sortingCombiner method with TestNG DataProvider.
     *
     * @param comparator      Comparator of elements of type <T>.
     * @param expSeq          Expecting resulting sequence of elements of type <T>
     *                        of sortingCombiner method.
     * @param listOfIncSeq    List of lists of incoming sequences of elements of type <T>
     *                        sorted with comparator rules.
     * @param testNo          Test number from DataProvider (just for convenient usage)
     * @param excToCatchClass Expected Exception class to catch as a result of invoking
     *                        testing sortingCombiner method
     *                        (null - if no excToCatchClass expected)
     * @param <T>             Type of elements of sequence.
     */
    @Test(dataProvider = "sortingCombinerTestDataProvider")
    public <T> void sortingCombinerTest(Comparator<? super T> comparator, List<T> expSeq, List<List<T>> listOfIncSeq, int testNo, Class<Exception> excToCatchClass) {
        // Create collection of iterators for sortingCombiner method
        List<Iterator<T>> arrOfSeqIterators = new ArrayList<>();
        for (List<T> e : listOfIncSeq) arrOfSeqIterators.add(e.iterator());


        List<T> res = null; // Resulting iterator

        // TODO
        // Commented Exception check because decided not to implement it
        // (this Exception is not really the straight part of sortingCombiner method work)

//        if (excToCatchClass != null) {
//
//            // Try to catch needed Exception
//            Exception catchedExc = null;
//            try {
//                res = Iterators.toList(sortingCombiner(arrOfSeqIterators, comparator));
//            } catch (Exception e) {
//                catchedExc = e;
//            }
//
//            if (catchedExc != null)
//                // If catched another exception then expected - first print stack trace
//                if (catchedExc.getClass().getName().equals(excToCatchClass.getName())) {
//                    catchedExc.printStackTrace(); // print stack trace and terminate
//                }
//            // Assert the result of Exception catching
//            assertEquals((Object) catchedExc.getClass(), (Object) excToCatchClass,
//                    "Iterators.sortingCombinerTest #" + testNo + " FAILED on Exception expected test!");
//        } else {
        res = Iterators.toList(sortingCombiner(arrOfSeqIterators, comparator));
        // Assert the result with expect
        assertEquals((Object) res, (Object) expSeq, "Iterators.sortingCombinerTest #" + testNo + " FAILED!");
//        }
    }

    /**
     * DataProvider for "sortingCombinerTest"
     *
     * @return DataProvider for sortingCombinerTest
     */
    @DataProvider(name = "sortingCombinerTestDataProvider")
    public Object[][] sortingCombinerTestDataProvider() throws FileNotFoundException {
        int testNo = 0; // initialize test number
        return new Object[][]{
                { // --- Integer 1-1 (input order 1) ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15),
                        Arrays.asList(
                                Arrays.asList(0, 1, 2, 3, 4),
                                Arrays.asList(0, 3, 6, 9, 12, 15),
                                Arrays.asList(0, 5, 10)
                        ),
                        ++testNo,
                        null
                },
                { // --- Integer 1-2  (input order 2) ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15),
                        Arrays.asList(
                                Arrays.asList(0, 1, 2, 3, 4),
                                Arrays.asList(0, 5, 10),
                                Arrays.asList(0, 3, 6, 9, 12, 15)
                        ),
                        ++testNo,
                        null
                },
                { // --- Integer 1-3  (input order 3) ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15),
                        Arrays.asList(
                                Arrays.asList(0, 5, 10),
                                Arrays.asList(0, 3, 6, 9, 12, 15),
                                Arrays.asList(0, 1, 2, 3, 4)
                        ),
                        ++testNo,
                        null
                },
                { // --- Integer 2-1 (One of inputs is empty) ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(0, 0, 3, 5, 6, 9, 10, 12, 15),
                        Arrays.asList(
                                Arrays.asList(),
                                Arrays.asList(0, 3, 6, 9, 12, 15),
                                Arrays.asList(0, 5, 10)
                        ),
                        ++testNo,
                        null
                },
                { // --- Integer 3 (always ZERO comparator) ---
                        (Comparator<Integer>) (a, b) -> 0,
                        Arrays.asList(0, 1, 2, 3, 4, 0, 3, 6, 9, 12, 15, 0, 5, 10),
                        Arrays.asList(
                                Arrays.asList(0, 1, 2, 3, 4),
                                Arrays.asList(0, 3, 6, 9, 12, 15),
                                Arrays.asList(0, 5, 10)
                        ),
                        ++testNo,
                        null
                },
//                { // --- Integer 4 (with String - ClassCastException should be thrown) ---
//                        (Comparator<Integer>) (a, b) -> a - b,
//                        Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15),
//                        Arrays.asList(
//                                Arrays.asList(0, 1, 2, 3, 4),
//                                Arrays.asList("0", "3", "6", "9", "12", "15"),
//                                Arrays.asList(0, 5, 10)
//                        ),
//                        ++testNo,
//                        new ClassCastException()
//                },
                { // --- String 1-1 ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z"),
                        Arrays.asList(
                                Arrays.asList("B", "B", "C", "Y"),
                                Arrays.asList("F", "X", "Z"),
                                Arrays.asList("A", "Q", "S", "V", "W")
                        ),
                        ++testNo,
                        null
                },
                { // --- String 1-2 ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z"),
                        Arrays.asList(
                                Arrays.asList("F", "X", "Z"),
                                Arrays.asList("B", "B", "C", "Y"),
                                Arrays.asList("A", "Q", "S", "V", "W")
                        ),
                        ++testNo,
                        null
                },
                { // --- String 1-3 ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z"),
                        Arrays.asList(
                                Arrays.asList("A", "Q", "S", "V", "W"),
                                Arrays.asList("F", "X", "Z"),
                                Arrays.asList("B", "B", "C", "Y")
                        ),
                        ++testNo,
                        null
                },
                { // --- String 2-1 (One of inputs is empty) ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList("A", "B", "B", "C", "Q", "S", "V", "W", "Y"),
                        Arrays.asList(
                                Arrays.asList("B", "B", "C", "Y"),
                                Arrays.asList(),
                                Arrays.asList("A", "Q", "S", "V", "W")
                        ),
                        ++testNo,
                        null
                },
                { // --- String 3 (always ZERO comparator) ---
                        (Comparator<String>) (a, b) -> 0,
                        Arrays.asList("B", "B", "C", "Y", "F", "X", "Z", "A", "Q", "S", "V", "W"),
                        Arrays.asList(
                                Arrays.asList("B", "B", "C", "Y"),
                                Arrays.asList("F", "X", "Z"),
                                Arrays.asList("A", "Q", "S", "V", "W")
                        ),
                        ++testNo,
                        null
                },
//                { // --- String 4 (with Integer - ClassCastException should be thrown) ---
//                        (Comparator<String>) String::compareTo,
//                        Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z"),
//                        Arrays.asList(
//                                Arrays.asList("B", "B", "C", "Y"),
//                                Arrays.asList(0, 1, 2),
//                                Arrays.asList("A", "Q", "S", "V", "W")
//                        ),
//                        ++testNo,
//                        new ClassCastException()
//                },
                { // --- Empty inputs 1 (Integer comparator) ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(),
                        Arrays.asList(
                                Arrays.asList()
                        ),
                        ++testNo,
                        null
                },
                { // --- Empty inputs 1 (String comparator) ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList(),
                        Arrays.asList(
                                Arrays.asList()
                        ),
                        ++testNo,
                        null
                },
                { // --- Empty inputs 1 (Object comparator) ---
                        (Comparator<Object>) (a, b) -> a.equals(b) ? 0 : (-1),
                        Arrays.asList(),
                        Arrays.asList(
                                Arrays.asList()
                        ),
                        ++testNo,
                        null
                }
        };
    }
}