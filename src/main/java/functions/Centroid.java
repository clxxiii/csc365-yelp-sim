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
    public Boolean contains(Business[] list, Business key){
        Boolean out = false;
        for(int i = 0; i < list.length;i++){
            if(key.equals(i)){
                return true;
            }
        }
        return out;
    }
    public Business[] fourNearestBusinesses(){
        Business[] out = new Business[4];
        if(this.businesses.size() <= 4){
            for(int i = 0; i < this.businesses.size();i++){
                out[i] = this.businesses.get(i);
            }
            return out;
        }
        Business[] topCrawl = new Business[4];
        float minDist = 1000;
        for(int i = 0; i < this.businesses.size(); i++){
            Business[] crawl = new Business[4];
            float totDist = 0;
            int itIndex = -1;
            while(crawl[3] == null){
                float itDist = 100;
                for(int j = 0; j < this.businesses.size();j++){
                    if(contains(crawl, this.businesses.get(j))){
                        continue;
                    }
                    float[] c1 = {this.businesses.get(i).x,this.businesses.get(i).y};
                    float[] c2 = {this.businesses.get(j).x,this.businesses.get(j).y};
                    if(Parser.getDistance(c1, c2) < itDist){
                        itIndex = j;
                        itDist = Parser.getDistance(c1, c2);

                    }
                    
        
                }
                totDist += itDist;
                for(int k = 0; k < crawl.length;k++){
                    if(crawl[k] == null){
                        crawl[k] = this.businesses.get(itIndex);
                        break;
                    }
                }
            }
            if(totDist < minDist){
                topCrawl = crawl;
                minDist = totDist; 
            }
        }
        return topCrawl;



       
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