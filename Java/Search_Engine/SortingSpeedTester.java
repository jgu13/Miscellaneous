package finalproject;

import RuntimeTester.*;
import java.util.HashMap;
import java.util.Random;


public class SortingSpeedTester {
    public static void main(String[] args){
        Visualizer.launch(SortingSpeedTester.class);
    }
    @benchmark(name = "fastSortSpeed", category = "Sorting", expectedEfficiency = "O(nlog(n)")
    public static long testingSorting(long input){
        HashMap<Double, Double> map = new HashMap<>();

        for(int i = 0; i<input; i++){
            Double val = Math.random() * input;
            map.put(val, val);
        }
        long startTime = System.nanoTime();
        Sorting.fastSort(map);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
