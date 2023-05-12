package main.java.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import main.java.types.ExtensibleHashTable;
import main.java.types.Restaurant;

public class RestaurantManager {
  public static Restaurant getRestaurant(String name) throws IOException, ClassNotFoundException {
    FileInputStream file = new FileInputStream("data/hashtable");
    ObjectInputStream in = new ObjectInputStream(file);
    ExtensibleHashTable table = (ExtensibleHashTable) in.readObject();
   
    String resId = table.get(name);
            // Fallback
    in.close();
    if (resId == null) {
      return null;
    //   System.out.println();
    //     String resString = Locality.getLineFromName(name);
    //     if(resString == null){
    //       return null;
    //     }

    
    // Restaurant temp = new Restaurant(
    //                Parser.getID(resString),
    //                 Parser.getName(resString),
    //                 Parser.getCoordinates(resString)[0],
    //                 Parser.getCoordinates(resString)[1],
    //                 Parser.getState(resString),
    //                 Parser.getCategories(resString));
              

    // return temp;
    }
    

    FileInputStream resFile = new FileInputStream("data/restaurants/" + resId);
    ObjectInputStream resIn = new ObjectInputStream(resFile);
    
    Restaurant res = (Restaurant) resIn.readObject();
    
    resFile.close();
    resIn.close();
    file.close();
    in.close();

    return res;
  }

  /**
   * Returns a list of every business name in the table
   */
  public static String[] getNames() throws IOException, ClassNotFoundException {
    String path = "data/business_list.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();

    String[] out = new String[10000];
    int i = 0;
    while (Line != null) {
      out[i] = Parser.getName(Line);
      Line = reader.readLine();
      i++;
    }
    reader.close();
    return out;
  }

  /**
   * Returns a tuple of the form (Distance, TFIDF rating)
   */
  public static float[] getMetricTuple(Restaurant res, Restaurant res2, FreqTable freqTable)
      throws IOException, ClassNotFoundException {

    float[] coords1 = { res.latitude, res.longitude };
    float[] coords2 = { res2.latitude, res2.longitude };
    float[] result = { Parser.getDistance(coords1, coords2),
        Locality.TFIDF(Parser.commonElements(res.categories, res2.categories), freqTable) };
    return result;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {

  }
}