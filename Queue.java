import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Mushroom Project
 * Created by prashant on 10/14/16.
 */
public class Queue <T> implements Iterable<T> {
	DoubleLinkedList<T> thisQueue;
	
	public Queue() {
		//Constructor creates a new DoubleLinkedList
		thisQueue = new DoubleLinkedList<T>();
	}
	
	public void add(T item) {
		//Adds an element to the Queue
		thisQueue.add(item);
	}
	
	public void offer(T item) {
		//Adds an element to the Queue
		thisQueue.add(item);
	}
	
	public T remove() {
		//Removes an element from the head of the Queue
		T retVal = thisQueue.removeFromHead();
		if (retVal == null) {
			throw new java.util.NoSuchElementException();
		}
		return retVal;
	}

	public int size() {
		//Returns the size of the Queue
		return this.thisQueue.size();
	}
	
	public T poll() {
		// removes an element from the head
		return thisQueue.removeFromHead();
	}
	
    public Iterator<T> iterator() {
        //Creates an instance of the iterator
		return thisQueue.iterator();
    }

    public Iterator<T> backward_iterator() {
        //Creates an instance of the backward iterator
		return thisQueue.backward_iterator();
    }
	
	public static void main (String[] args) {
		//Main function tests the methods of the class
		Queue<Integer> testQueue = new Queue<Integer>();

		for (int i =0; i <5 ; i++) {
			testQueue.add(5*i);
		}

		for (Integer q: testQueue) {
			System.out.println(q);
		}

		for (int i =0; i <5 ; i++) {
			System.out.println("Val(1) "+ i +" : " + testQueue.poll());
		}

		for (int i =0; i <4 ; i++) {
			testQueue.add(7*i);
		}

		for (int i =0; i <7 ; i++) {
			System.out.println("Val "+ i +" : " + testQueue.poll());
		}

	}
}