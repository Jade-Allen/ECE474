package ece474;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * Code Fragment 6.11
 * An implementation of a simple LinkedQueue class
 * @author Steven Weigel
 * @version 20211012
 * */
public class LinkedQueue<E> implements Queue<E> {
 private SinglyLinkedList<E> list = new SinglyLinkedList<>( );     // an empty list
 public LinkedQueue( ) { }            // new queue relies on the initially empty list
 public int size( ) { return list.size( ); }
 public boolean isEmpty( ) { return list.isEmpty( ); }
 public void enqueue(E element) { list.addLast(element); }
 public E first( ) { return list.first( ); }
 public E dequeue( ) { return list.removeFirst( ); }
}
