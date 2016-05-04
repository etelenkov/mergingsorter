package com.alliedtesting.etelenkov.myapp;

import org.testng.annotations.Test;

import java.util.*;

import static com.alliedtesting.etelenkov.myapp.Iterators.sortingCombiner;
import static com.alliedtesting.etelenkov.myapp.Iterators.toList;
import static org.testng.Assert.assertEquals;

public class AppTest {
    @Test
    public void sortingCombinerTest() {
        // Create collection of iterators
        ArrayList<Iterator<Integer>> arrOfIt = new ArrayList<>();
        arrOfIt.add(Arrays.asList(0, 1, 2, 3, 4).iterator());
        arrOfIt.add(Arrays.asList(0, 3, 6, 9, 12, 15).iterator());
        arrOfIt.add(Arrays.asList(0, 5, 10).iterator());

        // Create comparator
        Comparator<Integer> comparator = (a, b) -> a - b;

        // Create expecting iterator
        List<Integer> exp = Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15);

        // Get result iterator
        List<Integer> res = toList(sortingCombiner(arrOfIt, comparator));

        // Check the results
        assertEquals((Object) res, (Object) exp, "Iterators.filter2(...) FAILED!");
    }

    @Test
    public void iteratorsFilter2_2Test() {
        // Create collection of iterators
        ArrayList<Iterator<String>> arrOfIt = new ArrayList<>();
        arrOfIt.add(Arrays.asList("B", "B", "C", "Y").iterator());
        arrOfIt.add(Arrays.asList("F", "X", "Z").iterator());
        arrOfIt.add(Arrays.asList("A", "Q", "S", "V", "W").iterator());

        // Create comparator
        Comparator<String> comparator = (a, b) -> a.compareTo(b) <= 0 ? -1 : 1;

        // Create expecting iterator
        List<String> exp = Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z");

        // Get result iterator
        List<String> res = toList(sortingCombiner(arrOfIt, comparator));

        // Check the results
        assertEquals((Object) res, (Object) exp, "Iterators.filter2(...) FAILED!");
    }

}
