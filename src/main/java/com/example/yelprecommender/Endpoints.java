package main.java.com.example.yelprecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import main.java.functions.Business;
import main.java.functions.Centroid;
import main.java.functions.Dijkstra;
import main.java.functions.FreqTable;
import main.java.functions.Locality;
import main.java.functions.Parser;
import main.java.functions.RestaurantManager;
import main.java.types.Restaurant;
import main.java.types.Street;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.SyslogOutputStream;
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

    System.out.println("Fetching recommendation for " + name);
    Restaurant restaurant = RestaurantManager.getRestaurant(name);
    if (restaurant == null)
      return null;
    String[] names = RestaurantManager.getNames();
    FreqTable ft = Locality.getFreqTable();
    float[][] metrics = new float[10000][2];
    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

    for (int i = 0; i < names.length; i++) {
      String iName = names[i];
      if (iName != restaurant.name) {
        Restaurant iRes = RestaurantManager.getRestaurant(iName);
        if(iRes == null) continue;
        restaurants.add(iRes);
        System.out.println(iRes.name);
        System.out.println(i);
        metrics[i] = RestaurantManager.getMetricTuple(restaurant, iRes, ft);
      }
    }

    Random rnd = new Random();
    rnd.setSeed(12);
    Centroid[] initialCentroids = kMeans.assignClusters(metrics, names, 12);
    Centroid[] reassignCentroids = kMeans.reassignClusters(initialCentroids, names, metrics);
    initialCentroids = kMeans.reassignClusters(reassignCentroids, names, metrics);

    for (int i = 0; i < initialCentroids.length; i++)
      initialCentroids[i].getWeight();

    float maxWeight = 0;
    Centroid bestCentroid = new Centroid();
    for (int i = 0; i < initialCentroids.length; i++)
      if (initialCentroids[i].TFIDF > maxWeight) {
        maxWeight = initialCentroids[i].weight;
        bestCentroid = initialCentroids[i];
      }
    //Calculate TFIDF center of best Centroid
    float TFIDFsum = 0;
    float count = 0;
    for(Business b : bestCentroid.businesses){
      TFIDFsum += b.TFIDF;
      count++;
    }
    float TFIDFavg = TFIDFsum / count;
    
    Business centroidCenter = bestCentroid.businesses.get(0);
    for(int i = 1; i < bestCentroid.businesses.size(); i++){
      if(Math.abs(TFIDFavg - bestCentroid.businesses.get(i).TFIDF) < Math.abs(centroidCenter.TFIDF - TFIDFavg)){
        centroidCenter = bestCentroid.businesses.get(i);
      }
    }

    Restaurant centroidCenterRest = RestaurantManager.getRestaurant(centroidCenter.name);
    Restaurant[] restArr = new Restaurant[restaurants.size()];
    for(int i = 0; i < restaurants.size(); i++){
      restArr[i] = restaurants.get(i);
    }

    for(Street s : restaurant.streets){
      if(s.Restaurants[1].streets == null) {
        System.out.println(s.Restaurants[1].name + "is null");
        System.out.println(s.Restaurants[0].name + "<->" + s.Restaurants[1].name);
        continue; 
      }
      for(Street j : s.Restaurants[1].streets){
        System.out.println(s.Restaurants[0].name + "<->" + s.Restaurants[1].name + "<->" + j.Restaurants[1].name);
      }
    }

    ArrayList<Street> shortestPath = Dijkstra.getShortestPath(restArr, restaurant, RestaurantManager.getRestaurant("Jin Wei"));

    if (shortestPath != null) {
        System.out.println("Shortest path found:");
        for (Street street : shortestPath) {
            System.out.println(street.Restaurants[0].name + " -> " + street.Restaurants[1].name + " (" + street.length + ")");
        }
    } else {
        System.out.println("No path found");
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
    
    Restaurant[] out = new Restaurant[shortestPath.size()];
    // for(int i = 0; i < out.length; i++){
    //   out[i] = shortestPath.get(i);
    // }
    // String outString = "";
    // for(Restaurant r : out){
    //   outString += r + "->";
    // }
    // System.out.println(outString);
    return out;
  }
}
