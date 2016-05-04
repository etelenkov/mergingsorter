package com.alliedtesting.etelenkov.myapp;

import java.util.Iterator;

/**
 * A wrapper of initial iterator. The wrapper ables us to peek() the next
 * available element before completely getting by the next() function
 * (in other words - it ables us to "look ahead" the iteration).
 * <p>
 * Note that initial iterator is iterating by the wrapper, so initial iterator
 * should not be straightly used after creating the wrapper by the constructor
 * (otherwise it would be unpredictable returning results on both -
 * the wrapper and the initial iterator).
 *
 * @param <E> Type of elements of initial & consequently - wrapper elements.
 */
class LookAheadIterator<E> implements Iterator<E> {
    private final Iterator<E> initial;
    private E next = null; // peeked element
    private boolean peeked = false; // peeked flag

    public LookAheadIterator(Iterator<E> initial) {
        this.initial = initial;
    }

    /**
     * Before every call of peek() method you should check for hasNext() true value
     * to avoid NoSuchElementException to be thrown if there is no more next
     * elements available (the same as with a call of next() method of
     * Iterator<T> implementation)
     */
    public E peek() {
        if (!peeked) {
            next = initial.next(); // throws NoSuchElementException if the iteration has no more elements
            peeked = true;
        }
        return next;
    }

    @Override
    public boolean hasNext() {
        return peeked || initial.hasNext();
    }

    @Override
    public E next() {
        E result = peek();
        peeked = false; // after return of the next, a peeked element will be the other one
        return result;
    }
}