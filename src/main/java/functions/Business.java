package main.java.functions;

public class Business {
    public String name;
    public float TFIDF;
    public float distance;

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

    public Business(String name, float distance, float TFIDF){
        this.name = name;
        this.distance = distance;
        this.TFIDF = TFIDF;
    }
}
