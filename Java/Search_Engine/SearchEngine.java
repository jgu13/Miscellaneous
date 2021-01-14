package finalproject;
import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String>> wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception {
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}

	/*
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 *
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		//add this url into the graph
		//ArrayList<String> queue = new ArrayList<>();
		//queue.add(url);
		ArrayList<String> links;
		ArrayList<String> words;
		/*while(!queue.isEmpty()) {
			String curr = queue.remove(0); //remove the first*/
			links = parser.getLinks(url); //get a list of links of the neighbor
			words = parser.getContent(url); //get a list of words of the neighbor
			internet.addVertex(url);
			internet.setVisited(url, true);
			//System.out.println("Current url is " + url.toString());
			for (String link : links) { //for every link in this url
				//update wordIndex
				internet.addVertex(link);
				internet.addEdge(url, link); //add links to current url
				//only add non-visited link to queue
				if(!internet.getVisited(link))
					crawlAndIndex(link);
			}
			for (String word : words) {
				//update wordIndex
				word = word.toLowerCase();
				ArrayList<String> urls = wordIndex.getOrDefault(word, new ArrayList<String>());
				urls.add(url);
				if(!wordIndex.containsKey(word))
					wordIndex.put(word, urls);
			}
	}

	/*
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex().
	 * To implement this method, refer to the algorithm described in the
	 * assignment pdf.
	 *
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		ArrayList<String> vertices = internet.getVertices();
		for (String v : vertices) {
			internet.setPageRank(v, 1.0);
		}
		ArrayList<Double> new_ranks = computeRanks(vertices);
		boolean repeat = false;
		while(!repeat) { //assume not repeat
			int i =0;
			for (String vertex : vertices) {
				double new_rank = new_ranks.get(i);
				if(Math.abs(new_rank - internet.getPageRank(vertex)) > epsilon) repeat = true; //if ( any vertex > epsilon) then repeat
				internet.setPageRank(vertex, new_rank);
				i++;
			}
			if(!repeat) break; //if repeat = false, break
			else{ new_ranks = computeRanks(vertices); repeat = false;} //assume not repeat after compute ranks
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls.
	 * Note that the double in the output list is matched to the url in the input list using
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		double[] results = new double[vertices.size()];
		int i = 0;
		for(String vertex : vertices) {
			//for this vertex, go through every vertex in the in_list and add rank(wi)/out(wi)
			ArrayList<String>  list = internet.getEdgesInto(vertex);
			double sum = 0.0;
			for(String v : list){
				sum += internet.getPageRank(v)/internet.getOutDegree(v);
			}
			double result = 0.5 + 0.5 * (sum);
			results[i] = result;//p(v) = 1/2+1/2*(sum of rank(wi)/out(wi))
			i++;
		}
		ArrayList<Double> new_ranks = new ArrayList<>();
		for (double result : results) new_ranks.add(result);
		return new_ranks;
	}

	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 *
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		// TODO: Add code here
		//get a HashMap <url, rank>
		ArrayList<String> words = new ArrayList<>();
		words.addAll(wordIndex.keySet());
		HashMap<String, Double> map = new HashMap<>();
		for(String s : words){
			if(s.equalsIgnoreCase(query)) {
				createMap(wordIndex.get(s), map);
				break;
			}
		}
		return Sorting.fastSort(map);
	}
	private HashMap<String, Double> createMap(ArrayList<String> urls, HashMap<String, Double> map){
		for(String url : urls){
			map.put(url, internet.getPageRank(url));
		}
		return map;
	}
}
