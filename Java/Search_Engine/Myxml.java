package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Myxml {

    public static void main(String[] args) throws Exception{
        //myXml1();
        myXml2();
    }
    private static void myXml1() throws Exception{
        System.out.println("----------TESTING test.xml----------");
        SearchEngine searchEngine = new SearchEngine("myxml.xml");
        searchEngine.crawlAndIndex("siteA");
        System.out.println("The graph is: " + searchEngine.internet.toString());
        //searchEngine.assignPageRanks(0.001);
        System.out.println();
    }

    private static void myXml2() throws Exception{
        System.out.println("----------TESTING test.xml----------");
        SearchEngine searchEngine = new SearchEngine("test.xml");
        searchEngine.crawlAndIndex("www.cs.mcgill.ca");
        //System.out.println("The graph is: " + searchEngine.internet.toString());
        searchEngine.assignPageRanks(0.001);
        ArrayList<String> RelatedUrls = searchEngine.getResults("world");
        for(String url :RelatedUrls){
            System.out.println(url.toString());
        }
        System.out.println();
    }

}
