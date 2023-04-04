package main.java.functions;

import java.io.FileInputStream;
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

    FileInputStream resFile = new FileInputStream("data/restaurants/" + resId);
    ObjectInputStream resIn = new ObjectInputStream(file);
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
    FileInputStream nameFile = new FileInputStream("data/business_names");
    ObjectInputStream in = new ObjectInputStream(nameFile);
    String[] names = (String[]) in.readObject();

    in.close();
    nameFile.close();
    return names;
  }

  /**
   * Returns a tuple of the form (Distance, TFIDF rating)
   */
  public static float[] getMetricTuple(Restaurant res, Restaurant res2) throws IOException, ClassNotFoundException {
    FreqTable freqTable = Locality.getFreqTable();
    float[] coords1 = { res.latitude, res.longitude };
    float[] coords2 = { res2.latitude, res2.longitude };
    float[] result = { Parser.getDistance(coords1, coords2),
        Locality.TFIDF(Parser.commonElements(res.categories, res2.categories), freqTable) };
    return result;
  }
}