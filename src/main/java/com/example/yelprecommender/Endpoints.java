package main.java.com.example.yelprecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import main.java.functions.Parser;
import main.java.functions.RestaurantManager;
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
  public String[] recommendBusinees(
      @RequestParam(value = "id", defaultValue = "0") String name)
      throws IOException,
      ClassNotFoundException {

    Restaurant restaurant = RestaurantManager.getRestaurant(name);
    String[] restaurants = RestaurantManager.getNames();

    float[][] metrics = new float[10002][2];
    for (int i = 0; i < restaurants.length; i++) {
      String iName = restaurants[i];
      if (iName != restaurant.name) {
        Restaurant iRes = RestaurantManager.getRestaurant(name);
        metrics[i] = RestaurantManager.getMetricTuple(restaurant, iRes);
      }
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

    return null; // This is temporary so I can compile
  }
}
