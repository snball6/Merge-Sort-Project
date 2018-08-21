
/**
 * File: SortInterface.java
 * Date: 2018-06-17
 * @author Sarah Ball 
 * Purpose: Define sort interface as defined by project specs
 */
public interface SortInterface {

    public void recursiveSort(int[] list) throws UnsortedException;

    public void iterativeSort(int[] list) throws UnsortedException;

    public int getCount();

    public long getTime();
}
