
import java.util.Arrays;

/*
 * File: MergeSort.java
 * Date: 2018-06-17
 * @author Sarah Ball - references for specific portions code adapted from
 * outside sources are cited above respective methods
 * Purpose: implement SortInterface using a mergeSort algorithm. Allow for 
 * benchmarking of significant operations and time to execute sort as well 
 * as checks to confirm successful sort.
 */
public class MergeSort implements SortInterface {

    //for benchmarks
    long start = 0, duration = 0;
    int counter = 0;

    @Override
    /*
    * Will run, benchmark, and check recursive implementation of merge sort algorithm
     */
    public void recursiveSort(int[] list) throws UnsortedException {
        counter = 0;
        start = System.nanoTime();
        recursive(list, 0, list.length);
        duration = System.nanoTime() - start;
        //confirm sort
        if (!isSorted(list)) {
            throw new UnsortedException(Arrays.toString(list));
        }

    }

    @Override
    /*
    * Will run, benchmark, and check iterative implementation of merge sort algorithm
     */
    public void iterativeSort(int[] list) throws UnsortedException {
        counter = 0;
        start = System.nanoTime();
        iterative(list);
        duration = System.nanoTime() - start;
        //confirm sort
        if (!isSorted(list)) {
            throw new UnsortedException(Arrays.toString(list));
        }
    }

    @Override
    /*
    * Returns counter of significant operations from latest recursiveSort
    * or iterativeSort run
     */
    public int getCount() {
        return counter;
    }

    @Override
    /*
    * Returns time elapsed from latest recursiveSort or iterativeSort run
     */
    public long getTime() {
        return duration;
    }

    /*
    * Recursive version of merge sort. Adapted off of: 
    * https://dzone.com/articles/recursive-and-iterative-merge
     */
    private void recursive(int[] list, int start, int end) {
        counter++; //counter for method execution
        counter++; //counter for comparison below
        if (end - start <= 1) {
            return;
        }
        int middle = start + (end - start) / 2;
        recursive(list, start, middle);
        recursive(list, middle, end);
        merge(list, start, middle, end);
    }

    /*
    * Iterative version of merge sort. Adapted off of: 
    * http://andreinc.net/2010/12/26/bottom-up-merge-sort-non-recursive/
     */
    private void iterative(int[] list) {
        // The size of the sub-arrays. Constantly changing.
        int subSize = 1;
        // startL: start index for left sub-array
        // startR: start index for the right sub-array
        int startL, startR;

        while (subSize < list.length) {
            counter++; //counter for loop/comparison
            startL = 0;
            startR = subSize;
            while (startR + subSize <= list.length) {
                counter++; //counter for loop/comparison
                merge(list, startL, startL + subSize, startR + subSize);
                startL = startR + subSize;
                startR = startL + subSize;
            }
            counter++;//counter for comparison below
            if (startR < list.length) {
                merge(list, startL, startL + subSize, list.length);
            }
            subSize *= 2;
        }
    }

    /*
    * Helper merge function - used by both iterative and recursive functions
    * Adapted off of: https://dzone.com/articles/recursive-and-iterative-merge
     */
    private void merge(int[] list, int start, int middle, int end) {
        counter++;
        int[] mergedArray = new int[end - start];
        int left = 0, right = 0, index = 0;

        while ((left < (middle - start)) && (right < (end - middle))) {
            counter++;//counter for loop/comparison
            counter++;//counter for comparison below
            if (list[start + left] < list[middle + right]) {

                mergedArray[index++] = list[start + left++];
            } else {
                mergedArray[index++] = list[middle + right++];
            }
        }
        while (right < (end - middle)) {
            counter++;//counter for loop/comparison
            mergedArray[index++] = list[middle + right++];
        }
        while (left < (middle - start)) {
            counter++;//counter for loop/comparison
            mergedArray[index++] = list[start + left++];
        }
        counter++; //counter for method call below
        System.arraycopy(mergedArray, 0, list, start, mergedArray.length);

    }

    /*
    * Internal check to confirm a sorted list
     */
    private boolean isSorted(int[] list) {
        //loop through array
        for (int i = 0; i < list.length - 1; i++) {
            //if any item is greater than the item after it
            if (list[i] > list[i + 1]) {
                //return false
                return false;
            }
        }
        //otherwise list is in order
        return true;
    }

}
