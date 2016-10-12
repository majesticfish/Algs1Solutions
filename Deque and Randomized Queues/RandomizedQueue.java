import edu.princeton.cs.algs4.StdRandom; 
import java.util.Scanner;
import java.util.NoSuchElementException; 
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    private int pointer;

    public RandomizedQueue() {
        size = 0;
        pointer = 0;
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }
        if (pointer == items.length) {
            if (size > items.length/2) {
                Item[] oldData = items;
                items = (Item[]) new Object[2*oldData.length];
                int newPointer = 0;
                for (int i = 0; i < oldData.length; i++) {
                    if (oldData[i] != null) {
                        items[newPointer++] = oldData[i];
                    }
                }
                oldData = null;
                pointer = newPointer;
            } else {
                Item[] oldData = items;
                int newPointer = 0;
                items = (Item[]) new Object[oldData.length];
                for (int i = 0; i < oldData.length; i++) {
                    if (oldData[i] != null) {
                        items[newPointer++] = oldData[i];
                    }
                }
                oldData = null;
                pointer = newPointer;
            }
        }
        items[pointer++] = item;
        size ++;
    }

    public Item dequeue() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        int index;
        while (true) {
            index = (int) (StdRandom.uniform()*items.length);
            if (items[index] != null) break;
        }
        Item item = items[index];
        items[index] = null;
        size --;
        if (size*4 < items.length) {
            Item[] oldData = items;
            items = (Item[]) new Object[oldData.length/2];
            int newPointer = 0;
            for (int i = 0; i < oldData.length; i++) {
                if (oldData[i] != null) {
                    items[newPointer++] = oldData[i];
                }
            }
            oldData = null;
            pointer = newPointer;
        }
        return item;
    }

    public Item sample() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        int index;
        while (true) {
            index = (int) (StdRandom.uniform()*items.length);
            if (items[index] != null) break;
        }
        Item item = items[index];
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        while (true) {
            String[] words = s.nextLine().split(" ");
            switch (words[0]) {
                case "sample": System.out.println(r.sample());
                    break;
                case "isEmpty": System.out.println(r.isEmpty());
                    break;
                case "size": System.out.println(r.size());
                    break;
                case "enqueue": r.enqueue(Integer.parseInt(words[1]));
                    break;
                case "dequeue": System.out.println(r.dequeue());
                    break;
                case "iterate":
                    for (Object k : r) {
                        System.out.println(k);
                    }
                    break;
            }
        }
    }

    private class ListIterator<Item> implements Iterator<Item>{
        private int[] indexes;
        private int numberOfElements;
        private int currentIndex;

        public ListIterator() {
            numberOfElements = size();
            indexes = new int[numberOfElements];
            int indexesPosition = 0;
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    indexes[indexesPosition++] = i;
                }
            }
            indexes = permute(indexes, 0, numberOfElements-1);
            currentIndex = 0;
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() { 
            return currentIndex <= numberOfElements-1;
        }

        public Item next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = (Item) items[indexes[currentIndex++]];
            return item;
        }

        private int[] permute(int[] array, int from, int to) {
            if (from == to) return array;
            int target = (int) (StdRandom.uniform()*(to-from+1));
            target += from;
            int previousAtFrom = array[from];
            array[from] = array[target];
            array[target] = previousAtFrom;
            return permute(array, from+1, to);
        }
    }
}
