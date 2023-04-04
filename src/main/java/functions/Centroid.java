package main.java.functions;

import java.util.ArrayList;

public class Centroid{
    public float distance;
    public float TFIDF;
    public ArrayList<Business> businesses = new ArrayList<Business>();
    public float weight;

    public void clearBusinesses(){
        businesses = new ArrayList<Business>();
    }
    public float[] getCoordinates(){
        float[] out = {distance, TFIDF};
        return out;
    }

    public void setDistance(float d){
        this.distance = d;
    }

    public void setTFIDF(float t){
        this.TFIDF = t;
    }

    public Centroid(){
        this.distance = 0;
        this.TFIDF = 0;
    }

    public Centroid(float distance, float TFIDF){
        this.distance = distance;
        this.TFIDF = TFIDF;
    }


    public void centerCentroids(){
        float totalTFIDF = 0;
        float totalDistance = 0;
        for(int i = 0 ; i < businesses.size(); i++){
            totalDistance += businesses.get(i).distance;
            totalTFIDF += businesses.get(i).TFIDF;
        }
        this.distance = totalDistance / businesses.size();
        this.TFIDF = totalTFIDF / businesses.size();
        clearBusinesses();
    }
    public void getWeight(){
        this.weight = (float) TFIDF + distance / 2;
    }
}