package main.java.types;

import java.io.FileNotFoundException;
import java.io.IOException;


import main.java.functions.Parser;

public class Restaurant implements java.io.Serializable {

  public String business_id;
  public String name;
  public String state;
  public float latitude;
  public float longitude;
  public Street[] streets;
  public String[] categories;
  public boolean visited;
  public Restaurant(String id, String name, float latitude,
      float longitude, String state,
      String[] categories)  {
    business_id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.state = state;
    this.categories = categories;
    this.visited = false;
  }

  public void setStreets() throws FileNotFoundException, ClassNotFoundException, IOException {
    Restaurant[] closestRest = Parser.getFourClosest(this);
    Street[] streets = new Street[4];
    for(int i = 0; i < closestRest.length; i++){
      Restaurant[] temp = {this, closestRest[i]};
      Street street = new Street(temp, Parser.commonElements(this.categories, closestRest[i].categories).length);
      streets[i] = street;
    }
    this.streets = streets;
  }

  public float getDistance(float[] coordinates) {
    if (coordinates.length != 2) {
      return -1;
    }

    float x1 = coordinates[0];
    float x2 = this.latitude;
    float y1 = coordinates[1];
    float y2 = this.longitude;

    return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
  }

  public String toString() {
    String categoryString = "";
    for (String category : this.categories) {
      categoryString += (categoryString == "") ? category : ", " + category;
    }
    return this.business_id +
        ": " +
        this.name +
        "; Location: (" +
        this.latitude +
        "," +
        this.longitude +
        ")"
        + "; Categories: " + categoryString;
  }
}
