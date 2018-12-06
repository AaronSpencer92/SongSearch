/***************************************************************************
 * Revision History (newest first)
 ***************************************************************************
 * 2018 Aaron Spencer added binary searches, add, contains, iterator, toArray
 * 2018 Cody Snow added FindFront
 * 2018 Alexander Millet added FindEnd
 * 2016 Anne Applin formatting and JavaDoc added 
 * 2015 Starting code by Prof. Boothe 
 **************************************************************************/
 
package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
 
/**
 * The RaggedArrayList is a 2 level data structure that is an array of arrays.
 *  
 * It keeps the items in sorted order according to the comparator.<br />
 * Duplicates are allowed.<br />
 * New items are added after any equivalent items.<br />
 *
 * NOTE: normally fields, internal nested classes and non API methods should 
 *  all be private, however they have been made public so that the tester code 
 *  can set them
 * @param <E> A generic data type so that this structure can be built with 
 *  any data type (Object) 
 
 */ 
public class RaggedArrayList<E> implements Iterable<E> {
    // must be even so when split get two equal pieces
    private static final int MINIMUM_SIZE = 4;
    /**
     *  The total number of elements in the entire RaggedArrayList
     */
    public int size;
    /**
     * really is an array of L2Array, but compiler won't let me cast to that
     */
    public Object[] l1Array;  
    /**
     * The number of elements in the l1Array that are used.
     */
    public int l1NumUsed;
    /**
     * a Comparator object so we can use compare for Song
     */
    private Comparator<E> comp;

    /**
     * create an empty list
     * always have at least 1 second level array even if empty, makes 
     * code easier 
     * (DONE - do not change)
     * @param c a comparator object
     */
    public RaggedArrayList(Comparator<E> c) {
        size = 0;
        // you can't create an array of a generic type
        l1Array = new Object[MINIMUM_SIZE]; 
        // first 2nd level array
        l1Array[0] = new L2Array(MINIMUM_SIZE); 
        l1NumUsed = 1;
        comp = c;
    }

    /*************************************************************
     * nested class for 2nd level arrays
     * (DONE - do not change)
     */
    public class L2Array {
        /**
         *  the array of items
         */
        public E[] items;
        /**
         * number of items in this L2Array with values
         */
        public int numUsed;
        /**
         * Constructor for the L2Array
         * @param capacity the initial length of the array
         */
        public L2Array(int capacity) {
            // you can't create an array of a generic type
            items = (E[]) new Object[capacity]; 
            numUsed = 0;
        }
    }// end of nested class L2Array
    /**************************************************************/
    /**
     *  total size (number of entries) in the entire data structure
     *  (DONE - do not change)
     *  @return total size of the data structure
     */
    public int size() {
        return size;
    }
    /**
     * null out all references so garbage collector can grab them
     * but keep otherwise empty l1Array and 1st L2Array
     * (DONE - Do not change)
     */
    public void clear() {
        size = 0;
        // clear all but first l2 array
        Arrays.fill(l1Array, 1, l1Array.length, null);  
        l1NumUsed = 1;
        L2Array l2Array = (L2Array) l1Array[0];
        // clear out l2array
        Arrays.fill(l2Array.items, 0, l2Array.numUsed, null); 
        l2Array.numUsed = 0;
    }
    /***********************************************************
     *  nested class for a list position
     *  used only internally
     *  2 parts: level 1 index and level 2 index
     */
    public class ListLoc {
        /**
         * Level 1 index
         */
         public int level1Index;

        /**
         * Level 2 index
         */
         public int level2Index;

        /**
         * Parameterized constructor
         * @param level1Index input value for property
         * @param level2Index input value for property
         */
        public ListLoc(int level1Index, int level2Index) {
           this.level1Index = level1Index;
           this.level2Index = level2Index;
        }
        /**
         * test if two ListLoc's are to the same location 
         * (done -- do not change)
         * @param otherObj 
         * @return
         */
        public boolean equals(Object otherObj) {
            // not really needed since it will be ListLoc
            if (getClass() != otherObj.getClass()) {
                return false;
            }
            ListLoc other = (ListLoc) otherObj;

            return level1Index == other.level1Index && 
                    level2Index == other.level2Index;
        }
        /**@author Aaron Spencer
         *  move ListLoc to next entry
         *  when it moves past the very last entry it will be 1 index past the 
         *  last value in the used level 2 array can be used internally to 
         *  scan through the array for sublist also can be used to implement 
         *  the iterator
        */
        public void moveToNext() {
            //if we are at the end of our current L2Array and we have more data 
            //in the ragged array, we move to the next L2Aarray
            if ((this.level2Index >= (((L2Array)l1Array[this.level1Index]).numUsed - 1))
                   && (this.level1Index < (l1NumUsed -1))){
                this.level1Index++;
                this.level2Index = 0;
            }
            //otherwise, we just move forward in this L2Array
            else{
                this.level2Index++;
            }
        }
    }


    /**
     * @author Aaron Spencer
     * find 1st matching entry
     * @param item  we are searching for a place to put.
     * @return ListLoc of 1st matching item or of 1st item greater than the 
     * item if no match this might be an unused slot at the end of a 
     * level 2 array
     */
    public ListLoc findFront(E item) {
        //first we check for empty ragged arrays
        if (this.size <= 0){
            return new ListLoc(0,0);
        }
        //the binary searches do most of the work. The first one finds the 
        //l1Array index and the second the l2Array index
        int i = l1FrontBinarySearch(0,this.l1NumUsed-1,item);
        int j = l2FrontBinarySearch((L2Array)l1Array[i], 0, 
                ((L2Array)l1Array[i]).numUsed-1, item);
        //now we just return a new ListLoc with the correct indecies
        return new ListLoc(i,j);
    }
    

    /**@author Aaron Spencer
     * find location after the last matching entry or if no match, it finds 
     * the index of the next larger item this is the position to add a new 
     * entry this might be an unused slot at the end of a level 2 array
     * @param item
     * @return the location where this item should go 
     */
    public ListLoc findEnd(E item) {
        //checks for empty ragged arrays
        if (this.size <= 0){
            return new ListLoc(0,0);
        }
        //the binary searches find the correct indecies
        int i = l1EndBinarySearch(0,l1NumUsed,item);
        int j = l2EndBinarySearch((L2Array)l1Array[i], 0, 
                ((L2Array)l1Array[i]).numUsed - 1, item);
        //returns a new ListLoc at the position just after the last occurence
        return new ListLoc(i,j);
    }
    /**
     * @author Aaron Spencer
     * Locates the position in L1Array of a certain item. It conducts a binary
     * search of only the last elements for each L2Array that is checked. The
     * requirement for returning a certain index of L1Array is that the value at 
     * the end of the L2Array matches the search term or comes after it, and the
     * value at the end of the previous L2Array comes before the search term. 
     * This narrows down the L2Array with the first occurrence of the search value.
     * Other than that it's a normal binary search that eliminates half the 
     * search options every iteration.
     * @param start L1Array index to begin searching
     * @param end L1Array index to stop searching
     * @param item the item we're looking for the first instance of
     * @return the index of l1Array with the correct L2Array
     */
    private int l1FrontBinarySearch(int start, int end, E item){
        //we make sure that start to end is a valid range, because if the 
        //first occurence of item is in the last position of the ragged array,
        //or would be if it were present, we don't want to go beyond the last index
        if (end >= start){
            //we always look at the midpoint of the start and end indecies
            int mid = (end + start) / 2;
            int compValue = 
                    comp.compare(item, ((L2Array)l1Array[mid]).items[
                            ((L2Array)l1Array[mid]).numUsed - 1]);
            //if the value in our ragged array is too small, we look at the next
            //top half of the list by making mid+1 the new start index
            if (compValue > 0){
                
                return l1FrontBinarySearch(mid + 1, end, item);
            }
            //if the value at the end of this l2Array is a match, or larger
            //than item, we check the previous l2Array. If we're at the first 
            //one, or the end of the previous l2Array is smaller than item,
            //we found a match
            else if (mid == 0 || 
                    comp.compare(item, ((L2Array)l1Array[mid-1]).items[
                            ((L2Array)l1Array[mid-1]).numUsed - 1]) > 0){
                return mid;
            }
            //otherwise we are too far ahead in L1Array and we look at the
            //bottom half
            else {
                return l1FrontBinarySearch(start, mid - 1, item);
            }
        }
        else{
            return end;
        }
}
    /**
     * @author Aaron Spencer
     * Once we have a certain L2Array where the first occurrence of item should
     * be, we do a binary search to find the index within the L2Array.items
     * array. 
     * @param l2arr L2Array that we are currently searching
     * @param start L1Array index to begin searching
     * @param end L1Array index to stop searching
     * @param item the item we're looking for the first instance of
     * @return the index of l2Array with the first occurrence of item
     */
    private int l2FrontBinarySearch(L2Array l2arr, int start, int end, E item){
        //we make sure that the start index is not greater than the end, 
        //otherwise the proper return value is one after the last used index
        //in this L2Array
        if (end >= start){
            
            int mid = (start + end) / 2;
            int compValue = comp.compare(item, l2arr.items[mid]);
            //just like in l1FrontBinarySearch, we first check to see if the
            //value is smaller than item, and if so, recursively call this
            //method with the a new start index after the midpoint
            if (compValue > 0){
                return l2FrontBinarySearch(l2arr, mid+1, end, item);
            }
            //again we only have a match if this element is equal to item or
            //comes after it, and we're either at the front of the array or
            //the previous element is smaller than item
            else if ((mid == 0) || (comp.compare(item, l2arr.items[mid-1]) > 0)){
                return mid;
            }
            //again, if our search index is too far ahead, we look only at the
            //bottom half of our remaining array segment
            else{
                return l2FrontBinarySearch(l2arr, start, mid-1, item);
            }
        }
        else{
            return start;
        }
    }
    /**
     * @author Aaron Spencer
     * This method functions very similarly to l1FrontBinarySearch, but here
     * we are looking for the last occurrence of the generic item in our ragged
     * array. This means a correct match is found when the first element in the
     * L2Array is equal to item or comes before it, and the first element in the
     * next l2Array comes after item
     * @param start L1Array index to begin searching
     * @param end L1Array index to stop searching
     * @param item the item we're looking for the first instance of
     * @return the index of l1Array with the correct L2Array
     */
    private int l1EndBinarySearch(int start, int end, E item){
        if (end >= start){
            int mid = (end + start) / 2;
            int compValue = 
                    comp.compare(item, ((L2Array)l1Array[mid]).items[0]);
            //almost the same as l1FrontBinarySearch, but we're going the other way
            if (compValue < 0){
                return l1EndBinarySearch(start, mid - 1, item);
            }
            else if (mid == l1NumUsed -1 || 
                    comp.compare(item, ((L2Array)l1Array[mid+1]).items[0]) < 0){
                return mid;
            }
            else {
                return l1EndBinarySearch(mid + 1, end, item);
            }
        }
        else{
            return start;
        }
    }
    
    /**
     * This method is very similar to l2FrontBinarySearch, but instead of
     * checking the previous element to make sure it precedes item, it is checked
     * to see if it precedes or is equal to item. This ensures that we find the
     * index immediately after the last item.
     * @param l2arr we are currently searching
     * @param start L1Array index to begin searching
     * @param end L1Array index to stop searching
     * @param item the item we're looking for the first instance of
     * @return the index of L2Array after the last occurrence of item
     */
        private int l2EndBinarySearch(L2Array l2arr, int start, int end, E item){
        if (end >= start){
            
            int mid = (start + end) / 2;
            int compValue = comp.compare(item, l2arr.items[mid]);
            if (compValue >= 0){
                return l2EndBinarySearch(l2arr, mid+1, end, item);
            }
            else if ((mid == 0) || (comp.compare(item, l2arr.items[mid-1]) >= 0)){
                return mid;
            }
            else{
                return l2EndBinarySearch(l2arr, start, mid-1, item);
            }
        }
        else{
            return start;
        }
    }
    /**@author Aaron Spencer
     * add object after any other matching values findEnd will give the
     * insertion position
     * @param item The item of type E to be added
     * @return always returns true on successful insertion
     */
    public boolean add(E item) {
        ListLoc ins = findEnd(item);
        //first we move all of the elements in the L2Array that come after the
        //inserting element forward 1
        for (int i = ((L2Array)l1Array[ins.level1Index]).numUsed -1; 
                i >= ins.level2Index; i--){
            ((L2Array)l1Array[ins.level1Index]).items[i+1] =
                    ((L2Array)l1Array[ins.level1Index]).items[i];
        }
        //then we insert the item and update the size and l2Array numUsed
        //we never have to worry about going out of bounds because there is
        //always a free null element
        ((L2Array)l1Array[ins.level1Index]).items[ins.level2Index] = item;
        size++;
        ((L2Array)l1Array[ins.level1Index]).numUsed++;
        //here we check to see if the L2Array is full. If it is we need to 
        //resize it or split it
        if (((L2Array)l1Array[ins.level1Index]).numUsed == 
                ((L2Array)l1Array[ins.level1Index]).items.length){
            //if the L2Array is smaller than or equal to the L1Array,
            //we double the l2Array size
            if (((L2Array)l1Array[ins.level1Index]).numUsed < l1NumUsed){
                ((L2Array)l1Array[ins.level1Index]).items =
                        Arrays.copyOf(((L2Array)l1Array[ins.level1Index]).items,
                                ((L2Array)l1Array[ins.level1Index]).numUsed*2);
            }
            //otherwise we are splitting the L2Array in half, and moving the last
            //half down one. so first we make sure that L1Array has enough room,
            //and double it if it does not
            else{
                if (l1NumUsed + 1 == l1Array.length){
                    l1Array = Arrays.copyOf(l1Array, l1Array.length*2);
                }
                //using the same process as above, we move every L2Array in the 
                //L1Array down one space to make room for the new split L2Array
                for(int j = l1NumUsed - 1; j >= ins.level1Index; j--){
                    l1Array[j+1] = new L2Array(((L2Array)l1Array[j]).items.length);
                    ((L2Array)l1Array[j+1]).items = ((L2Array)l1Array[j]).items;
                    ((L2Array)l1Array[j+1]).numUsed = ((L2Array)l1Array[j]).numUsed;
                }
                //for the splitting, we copy the elements of the last half of
                //the full L2Array, move them to the next index in L1Array, and
                //make the size the same as the previous L2Array. Then the copied
                //values in the full L2Array are written over with nulls
                ((L2Array)l1Array[ins.level1Index+1]).items = Arrays.copyOfRange(
                        ((L2Array)l1Array[ins.level1Index]).items,
                        ((L2Array)l1Array[ins.level1Index]).numUsed/2,
                        ((L2Array)l1Array[ins.level1Index]).items.length);
                ((L2Array)l1Array[ins.level1Index+1]).items = 
                        Arrays.copyOf(((L2Array)l1Array[ins.level1Index+1]).items,
                                ((L2Array)l1Array[ins.level1Index]).numUsed);
                ((L2Array)l1Array[ins.level1Index+1]).numUsed = 
                        ((L2Array)l1Array[ins.level1Index]).numUsed/2;
                Arrays.fill(((L2Array)l1Array[ins.level1Index]).items,
                        ((L2Array)l1Array[ins.level1Index]).numUsed/2, 
                        ((L2Array)l1Array[ins.level1Index]).items.length,null);
                ((L2Array)l1Array[ins.level1Index]).numUsed = 
                        ((L2Array)l1Array[ins.level1Index]).numUsed/2;
                l1NumUsed++;
            }
        }

        return true;
    }

    /**@author Aaron Spencer
     * check if list contains a match
     * @param item
     * @return 
     */
    public boolean contains(E item) {
        //findFront gives the position of where the first match will be, and 
        //if that index is a match to item, the ragged array list contains item
        ListLoc loc = findFront(item);
        if (comp.compare(item, ((L2Array)l1Array[loc.level1Index]).items[loc.level2Index]) == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**@author Aaron Spencer
     * copy the contents of the RaggedArrayList into the given array
     *
     * @param a - an array of the actual type and of the correct size
     * @return the filled in array
     */
    public E[] toArray(E[] a) {
        //checks for empty ragged arrays
        if (size == 0 || a.length ==0){
            return null;
        }
        //traverses the ragged array using an iterator, as long as the length of
        //the target array is not exceeded, and adds each element to the target
        //array
        else{
            Itr itr = new Itr();
            a[0] = (((L2Array)l1Array[0]).items[0]);
            for (int i = 1; (itr.hasNext()) && (i < a.length); i++){
                a[i] = itr.next();
            }
            return a;
        }
    }

    /**@author Aaron Spencer
     * returns a new independent RaggedArrayList whose elements range from
     * fromElemnt, inclusive, to toElement, exclusive the original list is
     * unaffected findStart and findEnd will be useful
     *
     * @param fromElement
     * @param toElement
     * @return the sublist
     */
    public RaggedArrayList<E> subList(E fromElement, E toElement) {
        RaggedArrayList<E> result = new RaggedArrayList<E>(comp);
        //if fromElement doesn't come before toElement, an empty raggedArray is
        //returned
        if (comp.compare(toElement, fromElement) <= 0){
            return result;
        }
        //we use findFront to find the starting and ending positions of the sublist
        ListLoc current = findFront(fromElement);
        ListLoc end = findFront(toElement);
        //we traverse the raggedArray until we hit end, adding each element to
        //the result raggedArray
        while (!current.equals(end)){
            result.add((((L2Array)l1Array[current.level1Index]).items[current.level2Index]));
            current.moveToNext();
        }
        return result;
    }

    /**
     * returns an iterator for this list this method just creates an 
     * instance of the inner Itr() class (DONE)
     * @return 
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * Iterator is just a list loc it starts at (0,0) and finishes with 
     * index2 1 past the last item in the last block
     */
    private class Itr implements Iterator<E> {

        private ListLoc loc;

        /*
         * create iterator at start of list
         * (DONE)
         */
        Itr() {
            loc = new ListLoc(0, 0);
        }

        /**@author Aaron Spencer
         * check if more items
         */
        @Override
        public boolean hasNext() {
            //very simply check whether we've hit the end of both L1Array and L2Array
            return (loc.level1Index < (l1NumUsed - 1) || 
                    loc.level2Index < ((((L2Array)l1Array[loc.level1Index]).numUsed - 1)));
        }

        /**@author Aaron Spencer
         * return item and move to next throws NoSuchElementException if 
         * off end of list
         */
        @Override
        public E next() {
            //ListLoc.moveToNext() actually does the work, this just throws an
            //exception if the iterator goes off the end
            loc.moveToNext();
            if (loc.level2Index > (((L2Array)l1Array[loc.level1Index]).numUsed - 1)){
                throw new IndexOutOfBoundsException();
            }
            else{
                return ((L2Array)l1Array[loc.level1Index]).items[loc.level2Index];
            }
        }

        /**
         * Remove is not implemented. Just use this code. 
         * (DONE)
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
