
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * File: BenchmarkSorts.java 
 * Date: 2018-06-17
 * @author Sarah Ball - references for specific portions of code adapted from 
 * outside sources are cited above respective methods 
 * Purpose: Create unsorted lists, sort them using mergeSort class, log times 
 * and counts of each run, and provide csv report containing the results 
 * of those runs.
 */
public class BenchmarkSorts {

    final int REPETITIONS = 50;
    Random r = new Random();
    SortInterface sorter = new MergeSort();
    int[] sortSizes;

    //for storing lists to be sorted - overwritten for each n-sized group
    int[][] itListsToSort = new int[REPETITIONS][];
    int[][] recListsToSort = new int[REPETITIONS][];

    //For storing detailed counts and times of runs - not overwritten
    int[][] iterativeCounts;
    long[][] iterativeTimes;
    int[][] recursiveCounts;
    long[][] recursiveTimes;

    //text to later write to csv
    String report = "";

    /**
     * Constructor
     */
    public BenchmarkSorts(int[] sizes) {
        sortSizes = sizes;
        iterativeCounts = new int[sizes.length][REPETITIONS];
        iterativeTimes = new long[sizes.length][REPETITIONS];
        recursiveCounts = new int[sizes.length][REPETITIONS];
        recursiveTimes = new long[sizes.length][REPETITIONS];
    }

    public void runSorts() {
        //reset report String to just header text
        report = "Data Set size n,Iterative, , , ,Recursive,\n"
                + " ,Average Critical Operations Count, Coefficient of Variance "
                + "of Count, Average Execution Time, Coefficient of Variance of Time,"
                + "Average Critical Operations Count, Coefficient of Variance of "
                + "Count, Average Execution Time, Coefficient of Variance of Time,\n";
        try {
            //for each data set size
            for (int dataSetIndex = 0; dataSetIndex < sortSizes.length; dataSetIndex++) {
                int n = sortSizes[dataSetIndex];
                //add to report
                report += n + ",";

                // Create random lists - add list and a duplicate to arrays 
                // for later sorting by iterative and recursive sorts
                for (int rep = 0; rep < REPETITIONS; rep++) {
                    int[] temp = createRandomArray(n);
                    itListsToSort[rep] = temp;
                    recListsToSort[rep] = Arrays.copyOf(temp, temp.length);
                }

                //For each list, run iterative sort and store results
                for (int rep = 0; rep < REPETITIONS; rep++) {
                    sorter.iterativeSort(itListsToSort[rep]);
                    iterativeCounts[dataSetIndex][rep] = sorter.getCount();
                    iterativeTimes[dataSetIndex][rep] = sorter.getTime();
                }
                //calculate and append average and variance of results
                report += mean(iterativeCounts[dataSetIndex]) + ",";
                report += coefficientOfVariation(iterativeCounts[dataSetIndex]) + ",";
                report += mean(iterativeTimes[dataSetIndex]) + ",";
                report += +coefficientOfVariation(iterativeTimes[dataSetIndex]) + ",";

                //For each list, run recursive sort and store results
                for (int rep = 0; rep < REPETITIONS; rep++) {
                    sorter.recursiveSort(recListsToSort[rep]);
                    recursiveCounts[dataSetIndex][rep] = sorter.getCount();
                    recursiveTimes[dataSetIndex][rep] = sorter.getTime();
                }
                //calculate and append average and variance of results
                report += mean(recursiveCounts[dataSetIndex]) + ",";
                report += +coefficientOfVariation(recursiveCounts[dataSetIndex]) + ",";
                report += mean(recursiveTimes[dataSetIndex]) + ",";
                report += coefficientOfVariation(recursiveTimes[dataSetIndex]) + "\n";
            }
        } catch (UnsortedException ex) {
            System.out.println(ex.toString());
        }

    }

    /*
    * Helper method to create an array of random integers 0-999 of size n
     */
    private int[] createRandomArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(1000);
        }
        return array;
    }

    /*
    * Creates csv file of results. CSV file write code based on example found here:
    * https://examples.javacodegeeks.com/core-java/writeread-csv-files-in-java-example/
     */
    public void displayReport() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("report.csv");
            fileWriter.append(report);
            System.out.println("report.csv created successfully.");
        } catch (Exception e) {
            System.out.println("Error writing file");
            System.out.println(e.toString());
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Error while flushing/closing fileWriter");
                System.out.println(e.toString());
            }
        }
    }

    //**********************Helper math functions***********************//
    //Adapted from: https://www.geeksforgeeks.org/program-coefficient-variation/
    
    
    /*
    * Function to find mean of given array. 
     */
    private static double mean(long arr[]) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + arr[i];
        }
        return sum / n;
    }

    /*
    * Function to find standard deviation of given array. 
     */
    private static double standardDeviation(long arr[]) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + (arr[i] - mean(arr))
                    * (arr[i] - mean(arr));
        }
        return Math.sqrt(sum / (n - 1));
    }

    /*
    * Function to find coefficient of variation.
     */
    private static double coefficientOfVariation(long arr[]) {
        return (standardDeviation(arr) / mean(arr));
    }

    /*
    * Function to find mean of given array. 
     */
    private static double mean(int arr[]) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + arr[i];
        }
        return sum / n;
    }

    /*
    * Function to find standard deviation of given array.
     */
    private static double standardDeviation(int arr[]) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + (arr[i] - mean(arr))
                    * (arr[i] - mean(arr));
        }
        return Math.sqrt(sum / (n - 1));
    }

    /*
    * Function to find coefficient of variation.
     */
    private static double coefficientOfVariation(int arr[]) {
        return (standardDeviation(arr) / mean(arr));
    }

}
