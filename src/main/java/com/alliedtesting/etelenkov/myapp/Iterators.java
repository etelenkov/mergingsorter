package com.alliedtesting.etelenkov.myapp;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Iterators {

    /**
     * Function returns a new iterator which consist of all of the elements
     * in initial collection of iterators sorted by the rules of the comparator.
     * <p>
     * Each separate iterable collection of the initial collection of iterators
     * should be presorted by the comparator! Otherwise the resulting iterator
     * returning by the function would not be able to show the right results.
     * <p>
     * Comparator should return a negative integer, zero, or a positive integer
     * as the first argument is less than, equal to, or greater than the second.
     * <p>
     * Returned iterator iterates through iterators in initial collection, initial
     * iterators should not be straightly used after creating the wrapper by the constructor
     * (otherwise it would be unpredictable returning results with all of the iterators).
     *
     * @param collOfIt   Initial collection of sorted by comparator iterators.
     * @param comparator Comparator function which compares two elements of collection.
     * @param <E>        Type of elements of iterators.
     * @return New iterator which consist of all of the elements in initial collection
     * of iterators sorted by the rules of the comparator.
     */
    static <E> Iterator<E> sortingCombiner(Collection<Iterator<E>> collOfIt, Comparator<? super E> comparator) {
        return new Iterator<E>() {
            // array of LookAheadIterator's of initial iterators
            private List<LookAheadIterator<E>> lookAheadIt =
                    //toList(map(collOfIt.iterator(), LookAheadIterator::new)); // old code
                    collOfIt.stream().map(LookAheadIterator::new).collect(Collectors.toList());

            @Override
            public boolean hasNext() {
                return lookAheadIt.stream().filter(LookAheadIterator::hasNext).count() > 0;
                //filter(lookAheadIt.iterator(), LookAheadIterator::hasNext).hasNext(); // old code
            }

            @Override
            public E next() {
                return lookAheadIt.stream().filter(LookAheadIterator::hasNext).min((a, b) -> comparator.compare(a.peek(), b.peek())).get().next();
                //min(filter(lookAheadIt.iterator(), LookAheadIterator::hasNext), (a, b) -> comparator.test(a.peek(), b.peek())).next(); // old code
            }
        };
    }

    static <E> Stream<E> toStream(Iterator<E> sourceIterator) {
        return toStream(sourceIterator, false);
    }

    static <E> Stream<E> toStream(Iterator<E> sourceIterator, boolean parallel) {
        Iterable<E> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    static <E> List<E> toList(Iterator<E> sourceIterator) {
        return toStream(sourceIterator).collect(Collectors.toList());
    }
}
