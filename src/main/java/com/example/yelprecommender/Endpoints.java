package main.java.com.example.yelprecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import main.java.functions.locality;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoints {

  @GetMapping("/get_names")
  public ArrayList<ArrayList<String>> listBusinessNames() throws Exception {
    String path = "data/smallerListOfRestaurants.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();

    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<ArrayList<String>> listOfRestraunts =
        new ArrayList<ArrayList<String>>();

    while (Line != null) {
      names.add(locality.getName(Line));
      ids.add(locality.getID(Line));
      Line = reader.readLine();
    }

    listOfRestraunts.add(ids);
    listOfRestraunts.add(names);

    reader.close();
    return listOfRestraunts;
  }

  @GetMapping("/recommend")
  public String[] recommendBusinees(
      @RequestParam(value = "id", defaultValue = "0") String id) {
    try {
      String inLine = locality.getLineFromName(id);
      float[] simArr = locality.getSimiliarityArr(inLine);
      float mostSimilar = -1;
      int index = 0;

      for (int i = 0; i < simArr.length; i++) {
        if (simArr[i] > mostSimilar) {
          mostSimilar = simArr[i];
          index = i;
        }
      }
      String[] outArr = {locality.getLineFromIndex(index)};
      return outArr;
    } catch (Exception e) {
    }
    return new String[0];
  }
}
