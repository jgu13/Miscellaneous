package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
		ArrayList<K> sortedUrls = new ArrayList<K>();
		sortedUrls.addAll(results.keySet());    //Start with unsorted list of urls

		int N = sortedUrls.size();
		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < N - i - 1; j++) {
				if (results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j + 1))) < 0) {
					K temp = sortedUrls.get(j); //temp = arr[j]
					sortedUrls.set(j, sortedUrls.get(j + 1)); //arr[j] = arr[j+1]
					sortedUrls.set(j + 1, temp); //arr[j+1] = temp
				}
			}
		}
		return sortedUrls;
	}
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
		//merge sort to sort the urls
		if(results.size() <= 0) return new ArrayList<>(); //if the results map is empty, return an empty list
		ArrayList<Map.Entry<K,V>> temp = new ArrayList(results.entrySet());
		Map.Entry<K,V>[] map = new Entry[results.size()];
		int i =0;
		for(Entry e : temp){
			map[i] = e;
			i++;
		}
		hybrid_sorting(0, map.length-1, map);
		ArrayList<K> sortedUrls = new ArrayList<>();
		for(Entry aa : map){
			sortedUrls.add((K)(aa.getKey()));
		}

		return sortedUrls;
	}
	private static <K,V extends Comparable> int compare(Map.Entry<K,V> o1, Map.Entry<K,V> o2){
		return o1.getValue().compareTo(o2.getValue());
	}

	private static <K,V extends Comparable> Entry<K,V>[] insertion_sort(int low, int high, Map.Entry<K,V>[] map){

    	for(int i = low+1; i < high+1; i++){
    		Map.Entry<K,V> val = map[i]; // val = arr[i]
    		int j = i;
    		while( j > low && compare(map[j-1], val) < 0){
    			map[j]= map[j-1];//arr[j] = arr[j-1]
				j -- ;
			}
    		map[j]=val;//arr[j] = val
		}
    	return map;
	}

	private static <K,V extends Comparable> int partition(int low, int high, Entry<K,V>[] map){
    	Entry<K,V> p = map[(high-low)/2+low];

    	int i = low, j = high;
    	while(true){
    		while(compare(map[i], p) > 0){i++;}
    		while(compare(map[j], p) < 0){j--;}
    		if(i >= j)
    			break;
    		swap(i, j, map);
		}
    	return j;
	}
	private static void swap(int e1, int e2, Entry[] map){
    	Entry temp = map[e1];
    	map[e1] = map[e2];
    	map[e2] = temp;
	}
	private static <K,V extends Comparable> void hybrid_sorting(int low, int high, Entry<K,V>[] map){

    	while(low < high){
    		if(high-low+1 < 10){
    			insertion_sort(low, high, map);
    			break;
			}else{
    			int pivot = partition(low, high, map);

    			//if the left array is smaller than right array
				//sort the left array first, then move to the right array
    			if(pivot - low < high - pivot){
    				hybrid_sorting(low, pivot, map);
    				low = pivot + 1;
				}else { // if the right array is smaller than the left array
    				// sort the right array first then the left array
					hybrid_sorting(pivot+1, high, map);
					high = pivot;
				}
			}
		}
	}
}