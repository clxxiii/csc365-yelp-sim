package main.java.types;

import main.java.functions.Parser;

public class Street implements java.io.Serializable{
    public Restaurant[] Restaurants = new Restaurant[2];
    public int commonElements = 0;
    public float length;
    public boolean included;
    public Street(Restaurant[] Restaurants, int commonElements){
        this.commonElements = commonElements;
        this.Restaurants = Restaurants;
        this.included = false;
        float[] c1 = {Restaurants[0].latitude, Restaurants[0].longitude};
        float[] c2 = {Restaurants[1].latitude, Restaurants[1].longitude};
        this.length = Parser.getDistance(c1, c2);
    }
}
