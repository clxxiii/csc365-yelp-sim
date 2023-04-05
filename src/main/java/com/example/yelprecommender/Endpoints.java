package main.java.com.example.yelprecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import main.java.functions.Centroid;
import main.java.functions.FreqTable;
import main.java.functions.Locality;
import main.java.functions.Parser;
import main.java.functions.RestaurantManager;
import main.java.functions.kMeans;
import main.java.types.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    Restaurant restaurant = RestaurantManager.getRestaurant(name);
    if (restaurant == null)
      return null;
    String[] restaurants = RestaurantManager.getNames();
    FreqTable ft = Locality.getFreqTable();
    float[][] metrics = new float[10002][2];
    for (int i = 0; i < restaurants.length; i++) {
      String iName = restaurants[i];
      if (iName != restaurant.name) {
        Restaurant iRes = RestaurantManager.getRestaurant(name);
        float[] theMetrics = RestaurantManager.getMetricTuple(restaurant, iRes, ft);
        metrics[i] = theMetrics;
      }
    }

    Random rnd = new Random();
    rnd.setSeed(12);
    Centroid[] initialCentroids = kMeans.assignClusters(metrics, restaurants, 50);

    // Centroid[] initialCentroids = kMeans.reassignClusters(centroids1,
    // restaurants, metrics);

    for (int i = 0; i < initialCentroids.length; i++)
      initialCentroids[i].getWeight();

    float maxWeight = 0;
    Centroid bestCentroid = new Centroid();
    for (int i = 0; i < initialCentroids.length; i++)
      if (initialCentroids[i].weight > maxWeight) {
        maxWeight = initialCentroids[i].weight;
        bestCentroid = initialCentroids[i];
      }

    // TODO: Uncomment the following line to run kMeans
    // kMeans.generate(restaurants, metrics);

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

    String[] tempArr = new String[bestCentroid.businesses.size()];
    for (int i = 0; i < bestCentroid.businesses.size(); i++) {
      tempArr[i] = bestCentroid.businesses.get(i).name;
    }

    Restaurant[] out = new Restaurant[tempArr.length];
    for (int i = 0; i < tempArr.length; i++) {
      out[i] = RestaurantManager.getRestaurant(tempArr[i]);
    }

    return out; // This is temporary so I can compile
  }
}
