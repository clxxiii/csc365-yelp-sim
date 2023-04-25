package main.java.functions;

public class Business {
    public String name;
    public float TFIDF;
    public float distance;
    public float x;
    public float y;

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

    public Business(){
        this.distance = 0;
        this.TFIDF = 0;
    }

    public Business(String name, float distance, float TFIDF, float x, float y){
        this.name = name;
        this.distance = distance;
        this.TFIDF = TFIDF;
        this.x = x;
        this.y = y;
    }

}
