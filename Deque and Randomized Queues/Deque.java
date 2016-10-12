import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Iterator;
import java.lang.Iterable;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item>{

    private class Node<Item> {
        Item item;
        Node<Item> previous;
        Node<Item> next;
    }

    private Node<Item> first, last;
    private int size = 0;

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size(){
        return size;
    }

    public void addFirst(Item item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> prevFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.previous = prevFirst;
        first.next = null;
        if (isEmpty()) last = first;
        else prevFirst.next = first;
        size++;
    }

    public void addLast(Item item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> prevLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = prevLast;
        last.previous = null;
        if (isEmpty()) first = last;
        else prevLast.previous = last;
        size ++;
    }

    public Item removeFirst() throws NoSuchElementException { 
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = first.item;
            first = first.previous;
            size--;
            if (isEmpty()) {
                last = null;    
            }
            return item;
        }
    }
    
    public Item removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Item item = last.item;
            last = last.next;
            size--;
            if (isEmpty()) first = null;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String command = reader.nextLine();
            String[] c = command.split(" ");
            switch (c[0]) {
                case "isEmpty": System.out.println(d.isEmpty());
                    break;
                case "size": System.out.println(d.size());
                    break;
                case "addFirst": d.addFirst(Integer.parseInt(c[1]));
                    break;
                case "addLast": d.addLast(Integer.parseInt(c[1]));
                    break;
                case "removeFirst": System.out.println(d.removeFirst());
                    break;
                case "removeLast": System.out.println(d.removeLast());
                    break;
                case "iterate":
                    Iterator<Integer> i = d.iterator();
                    while (i.hasNext()) {
                        int at = i.next();
                        System.out.println(at);
                    }
                    break;
            }
        }
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;
        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.previous;
            return item;
        }
    }
}
