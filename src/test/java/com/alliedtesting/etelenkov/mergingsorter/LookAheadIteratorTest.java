package com.alliedtesting.etelenkov.mergingsorter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;

public class LookAheadIteratorTest {

    @Test(dataProvider = "LookAheadIteratorClassTestDataProvider")
    public <E> void LookAheadIteratorClassTest(int testNo, List<E> list) {
        System.out.println(testNo);
        LookAheadIterator<E> it = new LookAheadIterator<>(list.iterator());

        for (E e : list) {
            // Assert hasNext() (has to be true for every element of the List
            assertEquals((Object) it.hasNext(), (Object) true,
                    "LookAheadIterator.hasNext() FAILED on test # [" + testNo +
                            " for element with index [" +
                            list.indexOf(e) + "] and value [" + e + "]");

            // Assert peek() (has to be equal to List's element
            assertEquals((Object) it.peek(), (Object) e,
                    "LookAheadIterator.peek() FAILED on test # [" + testNo +
                            " for element with index [" +
                            list.indexOf(e) + "] and value [" + e + "]");

            // Assert next() (has to be equal to List's element
            assertEquals((Object) it.next(), (Object) e,
                    "LookAheadIterator.next() FAILED on test # [" + testNo +
                            " for element with index [" +
                            list.indexOf(e) + "] and value [" + e + "]");
        }

        // If the List is empty or finished...

        // Assert hasNext() (has to be false at this point
        assertEquals((Object) it.hasNext(), (Object) false,
                "LookAheadIterator.hasNext() FAILED for empty iterator");

        // Assert peek() (has to throw  NoSuchElementException at this point)
        boolean catchedPeekExc = false;
        try {
            it.peek();
        } catch (NoSuchElementException e) {
            catchedPeekExc = true;
        }
        assertEquals((Object) catchedPeekExc, (Object) true,
                "LookAheadIterator.peek() FAILED for empty iterator (check for catched NoSuchElementException)");

        // Assert next() (has to throw  NoSuchElementException at this point)
        boolean catchedNextExc = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            catchedNextExc = true;
        }
        assertEquals((Object) catchedNextExc, (Object) true,
                "LookAheadIterator.next() FAILED for empty iterator (check for catched NoSuchElementException)");

    }

    @DataProvider(name = "LookAheadIteratorClassTestDataProvider")
    public Object[][] sortingCombinerTestDataProvider() throws FileNotFoundException {
        int testNo = 0; // initialize test number
        return new Object[][]{
                {++testNo, Arrays.asList(1, 2, 3, 4, 5)},
                {++testNo, Arrays.asList(5, 4, 3, 2, 1)},
                {++testNo, Arrays.asList("A", "B", "C", "D", "E")},
                {++testNo, Arrays.asList("E", "D", "C", "B", "A")},
                {++testNo, Arrays.asList("A", "B", "C", 4, 5)},
                {++testNo, Arrays.asList(1, 2, 3, "D", "E")},
                {++testNo, Arrays.asList("A", "B", "C", null, "E")},
                {++testNo, Arrays.asList(null, "B", "C", null, null)},
                {++testNo, Arrays.asList(1, 2, 3, null, 5)},
                {++testNo, Arrays.asList(null, 2, 3, null, null)},
                {++testNo, Arrays.asList()}
        };
    }
}
