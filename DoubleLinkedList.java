import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Mushroom Project
 * Created by prashant on 10/14/16.
 */
public class DoubleLinkedList <T> implements Iterable<T> {
    private int listSize;
    private Node head;
    private Node tail;

    public DoubleLinkedList() {
        //Constructor
        listSize = 0;
        head = null;
        tail = null;
    }

    public void clear() {
        //Clears the list
        listSize = 0;
        head = null;
        tail = null;
    }


    public int size() {
        //Returns the size of the list
        return listSize;
    }

    public void add(T item) {
        //Adds an element onto the top of the list
        Node newNode = new Node(item);

        if (this.head == null) {
            this.tail = newNode;
            this.head = newNode;
        }
        else {
            newNode.setPrev(this.tail);
            this.tail.setNext(newNode);
            this.tail = newNode;
            this.listSize ++;
        }
    }

    
    public void addtoBottom(T item){
        //Adds an element to the Bottom of the list
        Node newNode = new Node(item);

        if (this.listSize != 0) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            listSize += 1;
        }
        else {
            head = newNode;
            newNode.setNext(null);
            newNode.setPrev(null);
            tail = newNode;
            listSize += 1;
        }
    }

	public void remove(T item) {
        //removes the specified element from the list
		Node ptrNode = new Node(item);
		if (this.listSize == 0) {
			return; 
		}
		else if (this.listSize == 1) {
			if (head.getThing() == item) {
				head = null;
				tail = null;
			}	
		}
		else {
			Node n = this.head;
			if (item == this.head.getThing()) {  //If it is the head item then set the head to the prev value
				this.head = n.getNext();
				return;
			}
			while(n.getNext()!= null) {
				System.out.println("Cur Val" + n.getThing());
				if (n.getThing() == item) {
						n.getNext().setPrev(n.getPrev());
						n.getPrev().setNext(n.getNext());
						return;
					}
				n = n.getNext();
			}
			if (item == this.tail.getThing()) {  
				this.tail = n.getPrev();
				this.tail.setNext(null);
			}
		}
	}
	public T removeFromHead() {
        //removes an element from the head
        if (this.head != null) {
            T retVal = this.head.getThing();
            this.head = this.head.getNext();
            if (this.head != null) {
                this.head.setPrev(null);
            }
            else {
                this.tail = null;
            }
            return retVal;
        }
        return null;
	}
	
	public T removeFromTail() {
        //removes the element at the tail
        if (this.tail != null) {
            T retVal = this.tail.getThing();
            this.tail = this.tail.getPrev();
            if (this.tail != null) {
                this.tail.setNext(null);
            }
            else {
                this.head = null;
            }
            return retVal;
        }
        return null;
	}
	
    private class Node{
        //Creates a Private Node Class that stores data of generic type T
        private Node next;
        private Node prev;
        private T container;

        private Node (T item) {
            next = null;
            prev = null;
            container = item;
        }

        public T getThing() {
            return container;
        }

        public void setNext(Node n) {
            next = n;
        }

        public void setPrev(Node n) {
            prev = n;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }
    }

    private class LLIterator implements Iterator<T> {
        //Creates an iterator so that you can iterate over the linked list
        private Node next_node;

        public LLIterator(Node head) {
            next_node = head;
        }

        public boolean hasNext() {
            return next_node != null;
        }

        public T next() {
            //Goest to the next node in the list
            if (next_node == null) {
                return null;
            }
            T ret = next_node.getThing();
            next_node = next_node.getNext();
            return ret;
        }

        public void remove() {}
    }

    private class LLBackwardIterator implements Iterator<T> {
//Iterates through the list in a backward manner.
        private Node iterator_node;

        public LLBackwardIterator(Node tail) {
            iterator_node = tail;
        }

        public boolean hasNext() {
            if (iterator_node == null) {
                return false;
            }
            else {
                return true;
            }
        }

        public T next() {
            //Goes to the next node
            if (iterator_node != null) {
                if (this.hasNext()!= false) {
                    T retVal = iterator_node.getThing();
                    iterator_node = iterator_node.getPrev();
                    return retVal;
                }
                else {
                    return null;
                }
            }
            return null;
        }

        public void remove() {}
    }

    public Iterator<T> iterator() {
        //Creates an instance of the iterator
        return new LLIterator(this.head);
    }

    public Iterator<T> backward_iterator() {

        //Creates an instance of the backward iterator
        return new LLBackwardIterator(tail);
    }

    public ArrayList<T> toArrayList() {
        //Creates a New array list from the list of nodes
        ArrayList<T> vals = new ArrayList<T>();
        for (T T1: this) {
            vals.add(T1);
        }
        return vals;
    }

    public ArrayList<T> toShuffledList() {
        //Returns a new shuffled arrayList of all the nodes
        ArrayList<T> vals = new ArrayList<T>();
        for (T T1: this) {
            vals.add(T1);
        }
        Collections.shuffle(vals);
        return vals;
    }

    public static void main(String[] args) {
        //Tests the methods of the function

        DoubleLinkedList<Integer> llist = new DoubleLinkedList<Integer>();

        llist.add(5);
        llist.add(10);
		llist.add(15);
        llist.add(20);
        llist.add(25);
        llist.add(30);
        
        Iterator bi = llist.iterator();
        while (bi.hasNext()) {
            System.out.println( bi.next() );
        }
        
        System.out.println("Hi");
        
        llist.remove(5);
        llist.remove(20);
        llist.remove(30);
        
		bi = llist.iterator();
        while (bi.hasNext()) {
            System.out.println( bi.next() );
        }
    }
}
