package main.java.functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import main.java.types.ExtensibleHashTable;
import main.java.types.Restaurant;
import main.java.types.Street;

public class SeralizeTester {

  public static void readTable() throws IOException, ClassNotFoundException {
    FileInputStream tableFile = new FileInputStream("data/hashtable");
    ObjectInputStream tableIn = new ObjectInputStream(tableFile);
    ExtensibleHashTable table = (ExtensibleHashTable) tableIn.readObject();

    System.out.println(table.get("China Bowl"));

    tableIn.close();
    tableFile.close();
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {

    ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/restaurants/icp_IKE9zIkAqAucyS1vTA"));  
    Restaurant r = (Restaurant) in.readObject();  
    for(Street s : r.streets){
        System.out.println(s.Restaurants[0].name + "->" + s.Restaurants[1].name);
    }
    in.close();

    
  }
}
