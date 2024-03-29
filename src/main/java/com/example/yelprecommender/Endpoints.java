package main.java.com.example.yelprecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import main.java.functions.Business;
import main.java.functions.Centroid;
import main.java.functions.FreqTable;
import main.java.functions.Locality;
import main.java.functions.Parser;
import main.java.functions.RestaurantListBuilder;
import main.java.functions.RestaurantManager;
import main.java.functions.SeralizeTester;
import main.java.types.ExtensibleHashTable;
import main.java.types.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import main.java.functions.kMeans;

@RestController
public class Endpoints {

  @GetMapping("/get_names")
  public ArrayList<ArrayList<String>> listBusinessNames() throws Exception {
    String path = "data/business_list.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();

    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<ArrayList<String>> listOfRestraunts = new ArrayList<ArrayList<String>>();

    while (Line != null) {
      names.add(Parser.getName(Line));
      ids.add(Parser.getID(Line));
      Line = reader.readLine();
    }

    listOfRestraunts.add(ids);
    listOfRestraunts.add(names);

    reader.close();
    return listOfRestraunts;
  }

  @GetMapping("/recommend")

  public Restaurant[] recommendBusinees(
      @RequestParam(value = "name", defaultValue = "0") String name)
      throws IOException, ClassNotFoundException {
    ExtensibleHashTable HT = SeralizeTester.readTable();
    System.out.println("Fetching recommendation for " + name);
    Restaurant restaurant = RestaurantManager.getRestaurant(name);
    if (restaurant == null)
      return null;
    String[] restaurants = RestaurantManager.getNames();
    FreqTable ft = Locality.getFreqTable();
    float[][] metrics = new float[10002][2];
    for (int i = 0; i < restaurants.length; i++) {
      String iName = restaurants[i];
      if (iName != restaurant.name) {
        Restaurant iRes = RestaurantManager.getRestaurant(iName);
        // Fallback
        if (iRes == null) {
          String resString = Locality.getLineFromName(iName);
          iRes = new Restaurant(
              Parser.getID(resString),
              Parser.getName(resString),
              Parser.getCoordinates(resString)[0],
              Parser.getCoordinates(resString)[1],
              Parser.getState(resString),
              Parser.getCategories(resString));
        }
        metrics[i] = RestaurantManager.getMetricTuple(restaurant, iRes, ft);
      }
    }

    Random rnd = new Random();
    rnd.setSeed(12);
    Centroid[] initialCentroids = kMeans.assignClusters(metrics, restaurants, 12, HT);
    Centroid[] reassignCentroids = kMeans.reassignClusters(initialCentroids, restaurants, metrics, HT);
    initialCentroids = kMeans.reassignClusters(reassignCentroids, restaurants, metrics, HT);

    for (int i = 0; i < initialCentroids.length; i++)
      initialCentroids[i].getWeight();

    float maxWeight = 0;
    Centroid bestCentroid = new Centroid();
    for (int i = 0; i < initialCentroids.length; i++)
      if (initialCentroids[i].TFIDF > maxWeight) {
        maxWeight = initialCentroids[i].weight;
        bestCentroid = initialCentroids[i];
      }

    /*
     * Project 1 Similarity Code
     * -------------------------
     * float mostSimilar = -1;
     * int index = 0;
     * 
     * for (int i = 0; i < simArr.length; i++) {
     * if (simArr[i] > mostSimilar) {
     * mostSimilar = simArr[i];
     * index = i;
     * }
     * }
     * 
     * String[] outArr = { Locality.getLineFromIndex(index) };
     * return outArr;
     */


    Business[] busiCrawl =bestCentroid.fourNearestBusinesses();
    Restaurant[] restCrawl = new Restaurant[4];
    for(int i = 0; i < busiCrawl.length; i++){
      restCrawl[i] = RestaurantManager.getRestaurant(busiCrawl[i].name);
    }

    return bestCentroid;
  }
}
