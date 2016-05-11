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
     * @param comparator   Comparator of elements of type <T>.
     * @param expSeq       Expecting resulting sequence of elements of type <T>
     *                     of sortingCombiner method.
     * @param listOfIncSeq List of lists of incoming sequences of elements of type <T>
     *                     sorted with comparator rules.
     * @param <T>          Type of elements of sequence.
     */
    @Test(dataProvider = "sortingCombinerTestDataProvider")
    public <T> void sortingCombinerTest(Comparator<? super T> comparator, List<T> expSeq, List<List<T>> listOfIncSeq) {
        // Create collection of iterators for sortingCombiner method
        List<Iterator<T>> arrOfSeqIterators = new ArrayList<>();
        for (List<T> e : listOfIncSeq) arrOfSeqIterators.add(e.iterator());

        // Get resulting iterator
        List<T> res = Iterators.toList(sortingCombiner(arrOfSeqIterators, comparator));

        // Check the result with expect
        assertEquals((Object) res, (Object) expSeq, "Iterators.sortingCombinerTest FAILED!");
    }

    /**
     * DataProvider for "sortingCombinerTest"
     *
     * @return DataProvider for sortingCombinerTest
     */
    @DataProvider(name = "sortingCombinerTestDataProvider")
    public Object[][] createData1() throws FileNotFoundException {
        return new Object[][]{
                { // --- Integer ---
                        (Comparator<Integer>) (a, b) -> a - b,
                        Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15),
                        Arrays.asList(
                                Arrays.asList(0, 1, 2, 3, 4),
                                Arrays.asList(0, 3, 6, 9, 12, 15),
                                Arrays.asList(0, 5, 10)
                        )
                },
                { // --- String ---
                        (Comparator<String>) String::compareTo,
                        Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z"),
                        Arrays.asList(
                                Arrays.asList("B", "B", "C", "Y"),
                                Arrays.asList("F", "X", "Z"),
                                Arrays.asList("A", "Q", "S", "V", "W")
                        )
                },
                { // --- Empty ---
                        (Comparator<Object>) (a, b) -> 0,
                        Arrays.asList(),
                        Arrays.asList(
                                Arrays.asList()
                        )
                }
        };
    }

}
