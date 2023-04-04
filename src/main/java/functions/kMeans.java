package main.java.functions;

import java.util.ArrayList;

import org.springframework.expression.spel.ast.Assign;
import main.java.functions.Parser;

public class kMeans {
    public static Centroid[] assignClusters(float[][] metricArray, String[] nameArr, int k){

        Centroid[] centroids = new Centroid[k];
        for(int i = 0; i < centroids.length;i++){
            centroids[i] = new Centroid();
        }

        float maxDistance = 0;
        float maxTFIDF = 0;
        float minTFIDF = 100000;
        float minDistance = 100000;
        for(int i = 0; i < metricArray.length; i++){
            if(metricArray[i][0] > maxDistance){
                maxDistance = metricArray[i][0];
            }
            if(metricArray[i][0] < minDistance){
                minDistance = metricArray[i][0];
            }
            if(metricArray[i][1] > maxTFIDF){
                maxTFIDF = metricArray[i][1];
            }
            if(metricArray[i][0] < minTFIDF){
                minTFIDF = metricArray[i][1];
            }
        }
        

        ///Randomly assign centroids
        for(int i = 0; i < k; i++){
            centroids[i].distance = (float) Math.random() * (maxDistance - minDistance + 1) + minDistance;
            centroids[i].TFIDF = (float) Math.random() * (maxTFIDF - minTFIDF + 1) + minTFIDF; 
        }

        for(int i = 0; i < nameArr.length; i++){
            int centroidIndex = 0;
            float minDistanceToCluster = 1000;
            for(int j = 0; j < k; j++){
                if(Parser.getDistance(metricArray[i], centroids[j].getCoordinates()) < minDistanceToCluster){
                    centroidIndex = j;
                    minDistanceToCluster = Parser.getDistance(metricArray[i], centroids[0].getCoordinates());
                }
            }
            centroids[centroidIndex].businesses.add(new Business(nameArr[i], metricArray[i][0], metricArray[i][1]));
        }
        
        return centroids;
    }

    public static Centroid[] reassignClusters(Centroid[] centroids, String[] nameArr, float[][] metricArray){
        for(int i = 0; i < centroids.length; i++){
            centroids[i].centerCentroids();
        }

        for(int i = 0; i < nameArr.length; i++){
            int centroidIndex = 0;
            float minDistanceToCluster = 1000;
            for(int j = 0; j < centroids.length; j++){
                if(Parser.getDistance(metricArray[i], centroids[j].getCoordinates()) < minDistanceToCluster){
                    centroidIndex = j;
                    minDistanceToCluster = Parser.getDistance(metricArray[i], centroids[0].getCoordinates());
                }
            }
            centroids[centroidIndex].businesses.add(new Business(nameArr[i], metricArray[i][0], metricArray[i][1]));
        }
        
        return centroids;
    }

    public static float randNum(){
        return (float) Math.random() * (1000 - 1 + 1) + 1;
    }
    public static void main(String[] args){
        String outString = "{";
        for(int i = 0; i < 21; i++){
    
            outString += "{(float)" + Math.floor(randNum()) + ", (float)" + Math.floor(randNum()) + "},";
            
        }
        System.out.println(outString + "}");


        String[] nameArr = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        float[][] metArr = {{(float)30.0, (float)95.0},{(float)128.0, (float)280.0},{(float)284.0, (float)220.0},{(float)667.0, (float)66.0},{(float)336.0, (float)150.0},{(float)427.0, (float)602.0},{(float)953.0, (float)886.0},{(float)630.0, (float)990.0},{(float)929.0, (float)716.0},{(float)372.0, (float)360.0},{(float)869.0, (float)471.0},{(float)909.0, (float)506.0},{(float)409.0, (float)417.0},{(float)468.0, (float)219.0},{(float)796.0, (float)896.0},{(float)870.0, (float)691.0},{(float)304.0, (float)499.0},{(float)593.0, (float)453.0},{(float)310.0, (float)255.0},{(float)481.0, (float)855.0},{(float)993.0, (float)36.0}};
    
        

        Centroid[] centroids = assignClusters(metArr, nameArr, 3);
        

        for(int i = 0; i < 3; i++){
            System.out.println("---------------------\nCentroid " + i);
            for(int j = 0; j < centroids[i].businesses.size(); j++){
                System.out.println(centroids[i].businesses.get(j).name);
            }
        }

        System.out.println("___________________________________________________");

        Centroid[] reassignedCentroids = reassignClusters(centroids, nameArr, metArr);
        for(int i = 0; i < 3; i++){
            System.out.println("---------------------\nCentroid " + i);
            for(int j = 0; j < reassignedCentroids[i].businesses.size(); j++){
                System.out.println(reassignedCentroids[i].businesses.get(j).name);
            }
        }


    }
}
