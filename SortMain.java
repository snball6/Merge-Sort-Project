
/**
 * File: SortMain.java
 * Date: 2018-06-17
 * @author Sarah Ball 
 * Purpose: Warms up JVM running multiple sorts, then
 * requests report.csv be created.
 */
public class SortMain {

    public static void main(String[] args) {
        int[] nValues = {1000, 2000, 3000, 4000, 5000, 
                    6000, 7000, 8000, 9000, 10000, 
                    11000, 12000, 13000, 14000, 15000};
        BenchmarkSorts benchmarks = new BenchmarkSorts(nValues);

        // Cycles through running reports to warmup JVM
        for (int i = 0; i < 100; i++) {
            benchmarks.runSorts();
        }
        // last run and create result.csv file
        benchmarks.runSorts();
        benchmarks.displayReport();
    }
}
