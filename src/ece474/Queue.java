package ece474;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * Code Fragment 6.9
 * An implementation of a simple Queue interface
 * @author Steven Weigel
 * @version 20211012
 * */
public interface Queue<E> {
  /** Returns the number of elements in the queue. */
  int size( );
  /** Tests whether the queue is empty. */
  boolean isEmpty( );
  /** Inserts an element at the rear of the queue. */
  void enqueue(E e);
  /** Returns, but does not remove, the first element of the queue (null if empty). */
  E first( );
   /** Removes and returns the first element of the queue (null if empty). */
   E dequeue( );
}
