package main.java.functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RestaurantListBuilder {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("data/yelp_academic_dataset_business.json"));
    HashSet<String> set = new HashSet<String>();

    BufferedWriter bw = new BufferedWriter(new FileWriter("data/business_list.txt"));

    int count = 0;
    String line = br.readLine();
    while (line != null && count <= 10000) {
      String name = Parser.getName(line);
      List<String> categories = Arrays.asList(Parser.getCategories(line));

      if (categories.contains("Restaurants") && !set.contains(name)) {
        set.add(name);
        bw.write(line);
        bw.write("\n");
        count++;
      }
      line = br.readLine();
    }

    br.close();
    bw.close();
    System.exit(0);
  }
}
