package main.java.functions;

import java.util.ArrayList;
import java.util.Random;

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
            float minDistanceToCluster = 1000000;
            for(int j = 0; j < k; j++){
                if(Parser.getDistance(metricArray[i], centroids[j].getCoordinates()) < minDistanceToCluster){
                    centroidIndex = j;
                    minDistanceToCluster = Parser.getDistance(metricArray[i], centroids[j].getCoordinates());
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
                    minDistanceToCluster = Parser.getDistance(metricArray[i], centroids[j].getCoordinates());
                }
            }
            centroids[centroidIndex].businesses.add(new Business(nameArr[i], metricArray[i][0], metricArray[i][1]));
        }
        
        return centroids;
    }

    public static float randNum(){
        return (float) Math.random() * (100 - 1 + 1) + 1;
    }
    public static void main(String[] args){
        Random rnd = new Random();
        rnd.setSeed(518);
        String outString = "{";
        for(int i = 0; i < 10; i++){
    
            outString += "{(float)" + Math.floor(randNum()) + ", (float)" + Math.floor(randNum()) + "},";
            
        }
        System.out.println(outString + "}");


        String[] nameArr = {"0","1","2","3","4","5","6","7","8","9"};
        float[][] metArr = {{(float)74.0, (float)50.0},{(float)99.0, (float)78.0},{(float)96.0, (float)77.0},{(float)30.0, (float)32.0},{(float)13.0, (float)98.0},{(float)48.0, (float)36.0},{(float)46.0, (float)68.0},{(float)38.0, (float)28.0},{(float)78.0, (float)50.0},{(float)8.0, (float)54.0}};
    
        

        Centroid[] centroids = assignClusters(metArr, nameArr, 3);
        
        System.out.println("-------------First Iteration-------------");
        
        for(int i = 0; i < 3; i++){
            
            System.out.println("---------------------\nCentroid " + i);
            System.out.println("(" + centroids[i].getCoordinates()[0] + ", " + centroids[i].getCoordinates()[1] + ")");
            for(int j = 0; j < centroids[i].businesses.size(); j++){
                System.out.println("(" + metArr[Integer.parseInt(centroids[i].businesses.get(j).name)][0] + "," + metArr[Integer.parseInt(centroids[i].businesses.get(j).name)][1] + ")");
            }
        }

        System.out.println("-------------Second Iteration-------------");
        
        Centroid[] reassignedCentroids = reassignClusters(centroids, nameArr, metArr);
        
        for(int i = 0; i < 3; i++){
            System.out.println("---------------------\nCentroid " + i);
            System.out.println("(" + reassignedCentroids[i].getCoordinates()[0] + ", " + reassignedCentroids[i].getCoordinates()[1] + ")");
            for(int j = 0; j < reassignedCentroids[i].businesses.size(); j++){
                System.out.println("(" + metArr[Integer.parseInt(reassignedCentroids[i].businesses.get(j).name)][0] + "," + metArr[Integer.parseInt(reassignedCentroids[i].businesses.get(j).name)][1] + ")");
            }
        }


        System.out.println("-------------Third Iteration-------------");
        Centroid[] reassignedCentroids2 = reassignClusters(reassignedCentroids, nameArr, metArr);

        for(int i = 0; i < 3; i++){
            System.out.println("---------------------\nCentroid " + i);
            System.out.println("(" + reassignedCentroids[i].getCoordinates()[0] + ", " + reassignedCentroids[i].getCoordinates()[1] + ")");
            for(int j = 0; j < reassignedCentroids[i].businesses.size(); j++){
                System.out.println("(" + metArr[Integer.parseInt(reassignedCentroids[i].businesses.get(j).name)][0] + "," + metArr[Integer.parseInt(reassignedCentroids[i].businesses.get(j).name)][1] + ")");
            }
        }

    }
}
