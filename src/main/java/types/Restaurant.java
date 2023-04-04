package main.java.types;

public class Restaurant implements java.io.Serializable {

  public String business_id;
  public String name;

  public String state;
  public float latitude;
  public float longitude;

  public String[] categories;

  public Restaurant(String id, String name, float latitude,
      float longitude, String state,
      String[] categories) {
    business_id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.state = state;
    this.categories = categories;
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
