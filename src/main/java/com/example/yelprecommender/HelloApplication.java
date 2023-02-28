package main.java.com.example.yelprecommender;
import main.java.functions.locality;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.*;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.ArrayList;



@RestController
public class HelloApplication {

  @GetMapping("/get_names")
  public ArrayList<String> listBusinessNames() throws Exception {
    // This is just a placeholder to test the frontend
    String path = "data/smallerListOfRestaurants.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();


    ArrayList<String> listOfRestraunts = new ArrayList<String>();
        while(Line != null){
            listOfRestraunts.add(locality.getName(Line));
            Line = reader.readLine();
        }

    return listOfRestraunts;
  }

  @GetMapping("/recommend")
  public String recommendBusinees(@RequestParam(value = "id",
                                                defaultValue = "0") String id) {
                                                  
    return "Fetching two businesses related to Business ID " + id;
  }
}
