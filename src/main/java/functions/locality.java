package main.java.functions;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.*;
  


public class locality {

    public static String getName(String input){
        String name = "";
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == 'n' & input.charAt(i+1) == 'a' & input.charAt(i+2) =='m' & input.charAt(i+3) =='e'){
                i += 7;
                while(input.charAt(i) != '\"'){
                    name += input.charAt(i);
                    i++;
                }
                i += input.length();
            }

        }
        return name;
        
    }
    public static String getID(String input){
        String ID = "";
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == 'b' & input.charAt(i+1) == 'u' & input.charAt(i+2) =='s' & input.charAt(i+3) =='i'){
                i += 14;
                while(input.charAt(i) != '\"'){
                    ID += input.charAt(i);
                    i++;
                }
                i += input.length();
            }

        }
        return ID;
        
    }
    public static String getLineFromName(String name) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("data/smallerListOfRestaurants.txt"));
        String Line = reader.readLine();

        while(Line != null){
            if(getName(Line).equals(name)){
                reader.close();
                return Line;
            }
            Line = reader.readLine();
        }
        reader.close();
        return "";

        
    }
    public static float[] getCoordinates(String input){
        String out1 = "", out2 = "";
        for(int i = 0; i < input.length() - 1; i++){
            if(i + 13 < input.length() & input.charAt(i) == 'l' & input.charAt(i+1) == 'a' & input.charAt(i+2) == 't' & input.charAt(i+4) == 't' & input.charAt(i+7) == 'e'){
                i += 10;

                while(input.charAt(i) != ','){
                    
                    out1 += input.charAt(i);
                    i++;
                }
                
                i += 13;
                while(input.charAt(i) != ','){
                    out2 += input.charAt(i);
                    i++;
                }
                i = i + input.length();
            }
            
        }
        
        float[] output = {Float.parseFloat(out1), Float.parseFloat(out2)};
        return output;
    }
    public static float getDistance(float[] c1, float[] c2){

        if(c1.length != 2 || c2.length != 2){
            return -1;
        }

        float x1 = c1[0];
        float x2 = c2[0];
        float y1 = c1[1];
        float y2 = c2[1];

        return (float) Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));
    }
    public static String getLineFromIndex(int Index) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("data/smallerListOfRestaurants.txt"));
        String Line = reader.readLine();
        int count = 0;
        while(Line != null){
            if(count == Index){
                reader.close();
                return Line;
            }
            count++;
            Line = reader.readLine();
        }
        reader.close();
        return "";

        
    }
    
    public static String getCategories(String input){
        String outString = "";
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == 'c' & input.charAt(i+1) == 'a' & input.charAt(i+2) =='t' & input.charAt(i+3) =='e' & input.charAt(i+6) == 'r'){
                i += 13;

                while(input.charAt(i) != '\"'){
                    outString += input.charAt(i);
                }
                i += input.length();
            }
        }
        return outString;
    }

    public static void main(String[] args) throws Exception{

        String test = "St Honore Pastries";
        String inLine = getLineFromName(test);
        String path = "data/smallerListOfRestaurants.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String Line = reader.readLine();
        float[] distArray = new float[10002];

        System.out.println(getCategories(Line));
        //Fill Distance Array - Array representing distance from chosen restaurant
        int count = 0;

        while(Line != null){
            distArray[count] = getDistance(getCoordinates(inLine), getCoordinates(Line));
            count++;
            Line = reader.readLine();
        }
        reader.close();

        reader = new BufferedReader(new FileReader(path));


        while(Line != null){}


        reader.close();
  
    }



}
