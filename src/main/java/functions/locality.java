package main.java.functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class locality {

  public static int wordIndex(String key, String inLine) {
    String temp = "";
    for (int i = 0; i < inLine.length(); i++) {
      int count = 0;
      while (inLine.charAt(i) == key.charAt(count)) {
        temp += inLine.charAt(i);
        count++;
      }
      if (temp.equals(key)) {
        return i;
      }
    }
    return -1;
  }

  public static String getName(String input) {
    String name = "";
    for (int i = 0; i < input.length(); i++) {
      if (
        input.charAt(i) == 'n' &
        input.charAt(i + 1) == 'a' &
        input.charAt(i + 2) == 'm' &
        input.charAt(i + 3) == 'e'
      ) {
        i += 7;
        while (input.charAt(i) != '\"') {
          name += input.charAt(i);
          i++;
        }
        i += input.length();
      }
    }
    return name;
  }

  public static String getID(String input) {
    String ID = "";
    for (int i = 0; i < input.length(); i++) {
      if (
        input.charAt(i) == 'b' &
        input.charAt(i + 1) == 'u' &
        input.charAt(i + 2) == 's' &
        input.charAt(i + 3) == 'i'
      ) {
        i += 14;
        while (input.charAt(i) != '\"') {
          ID += input.charAt(i);
          i++;
        }
        i += input.length();
      }
    }
    return ID;
  }

  public static String getLineFromName(String name) throws Exception {
    BufferedReader reader = new BufferedReader(
      new FileReader("data/smallerListOfRestaurants.txt")
    );
    String Line = reader.readLine();

    while (Line != null) {
      if (getName(Line).equals(name)) {
        reader.close();
        return Line;
      }
      Line = reader.readLine();
    }
    reader.close();
    return "";
  }

  public static float[] getCoordinates(String input) {
    String out1 = "", out2 = "";
    for (int i = 0; i < input.length() - 1; i++) {
      if (
        i + 13 < input.length() &
        input.charAt(i) == 'l' &
        input.charAt(i + 1) == 'a' &
        input.charAt(i + 2) == 't' &
        input.charAt(i + 4) == 't' &
        input.charAt(i + 7) == 'e'
      ) {
        i += 10;

        while (input.charAt(i) != ',') {
          out1 += input.charAt(i);
          i++;
        }

        i += 13;
        while (input.charAt(i) != ',') {
          out2 += input.charAt(i);
          i++;
        }
        i = i + input.length();
      }
    }

    float[] output = { Float.parseFloat(out1), Float.parseFloat(out2) };
    return output;
  }

  public static float getDistance(float[] c1, float[] c2) {
    if (c1.length != 2 || c2.length != 2) {
      return -1;
    }

    float x1 = c1[0];
    float x2 = c2[0];
    float y1 = c1[1];
    float y2 = c2[1];

    return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
  }

  public static String getLineFromIndex(int Index) throws Exception {
    BufferedReader reader = new BufferedReader(
      new FileReader("data/smallerListOfRestaurants.txt")
    );
    String Line = reader.readLine();
    int count = 0;
    while (Line != null) {
      if (count == Index) {
        reader.close();
        return Line;
      }
      count++;
      Line = reader.readLine();
    }
    reader.close();
    return "";
  }

  public static String[] getCategories(String input) {
    String outString = "";
    for (int i = 0; i < input.length(); i++) {
      if (
        input.charAt(i) == 'c' &
        input.charAt(i + 1) == 'a' &
        input.charAt(i + 2) == 't' &
        input.charAt(i + 3) == 'e' &
        input.charAt(i + 6) == 'r'
      ) {
        i += 13;

        while (input.charAt(i) != '\"') {
          outString += input.charAt(i);
          i++;
        }
        i += input.length();
      }
    }
    return outString.split(",\s");
  }

  public static String[] commonElements(String[] l1, String[] l2) {
    ArrayList<String> temp = new ArrayList<String>();

    for (int i = 0; i < l1.length; i++) {
      for (int j = 0; j < l2.length; j++) {
        if (l1[i].equals(l2[j])) {
          temp.add(l1[i]);
        }
      }
    }

    String[] output = new String[temp.size()];
    for (int i = 0; i < output.length; i++) {
      output[i] = temp.get(i);
    }
    return output;
  }

  public static float[] getDistArr(String input) throws Exception {
    String path = "data/smallerListOfRestaurants.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();
    int count = 0;
    float[] distArray = new float[10000];
    while (Line != null) {
      distArray[count] =
        1 / getDistance(getCoordinates(input), getCoordinates(Line));
      count++;
      Line = reader.readLine();
    }

    reader.close();
    return distArray;
  }

  public static float IFIDF(String[] commonElements, FreqTable freqTable) {
    float output = 0;

    for (int i = 0; i < commonElements.length; i++) {
      if (freqTable.contains(commonElements[i])) {
        float temp = 10000 / freqTable.getCount(commonElements[i]);
        output += (Math.log(temp));
      } else {
        throw new IllegalArgumentException();
      }
    }

    return output;
  }

  public static float[] getIFIDFArr(String input, FreqTable freqTable)
    throws Exception {
    String path = "data/smallerListOfRestaurants.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();
    int count = 0;
    float[] IFIDFArr = new float[10000];

    while (Line != null) {
      if (Line.equals(input)) {
        IFIDFArr[count] = -1;
      } else {
        IFIDFArr[count] =
          IFIDF(
            commonElements(getCategories(input), getCategories(Line)),
            freqTable
          );
      }
      count++;
      Line = reader.readLine();
    }

    reader.close();
    return IFIDFArr;
  }

  public static FreqTable getFreqTable() throws Exception {
    String path = "data/smallerListOfRestaurants.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();

    FreqTable freqTable = new FreqTable();

    while (Line != null) {
      for (int i = 0; i < getCategories(Line).length; i++) {
        String ithCategory = getCategories(Line)[i];

        freqTable.add(ithCategory);
      }
      Line = reader.readLine();
    }
    reader.close();
    return freqTable;
  }

  public static float[] normalizeArr(float[] arr) {
    float min = 1000000;
    float max = 0;
    float minIndex, maxIndex;
    float[] output = new float[10000];
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] < min) {
        min = arr[i];
        minIndex = i;
      }
      if (arr[i] > max) {
        max = arr[i];
        maxIndex = i;
      }
    }

    for (int i = 0; i < arr.length; i++) {
      output[i] = (arr[i] - min) / (max - min);
    }
    return output;
  }

  public static float[] getSimiliarityArr(String inLine) throws Exception {
    float[] output = new float[10000];
    FreqTable freqTable = getFreqTable();
    float[] IFIDFArr = getIFIDFArr(inLine, freqTable);
    float[] distArr = getDistArr(inLine);

    //Normalize Arrays

    float[] normIFIDFArr = normalizeArr(IFIDFArr);
    float[] normDistArr = normalizeArr(distArr);

    if (distArr.length != 10000 || IFIDFArr.length != 10000) {
      return new float[10];
    }

    for (int i = 0; i < output.length; i++) {
      float similarity = 0;

      similarity -= distArr[i];
      similarity += IFIDFArr[i];

      output[i] = similarity;
    }

    return output;
  }

  public static String getState(String inLine) {
    String state = "";
    for (int i = 0; i < inLine.length(); i++) {
      if (
        i + 5 < inLine.length() &
        inLine.charAt(i) == 's' &
        inLine.charAt(i + 1) == 't' &
        inLine.charAt(i + 2) == 'a' &
        inLine.charAt(i + 3) == 't' &
        inLine.charAt(i + 4) == 'e'
      ) {
        i += 8;
        while (inLine.charAt(i) != '\"') {
          state += inLine.charAt(i);
          i++;
        }
        return state;
      }
    }
    return "";
  }

  public static void main(String[] args) throws Exception {
    // ThrowawayHash ht = new ThrowawayHash();
    // for(int i = 0; i < 100; i++){
    //     Integer x = i;
    //     ht.add("Test" + i, i + "");
    // }
    // ht.printAll();

    //System.out.println(wordIndex("Test", "aTest"));

    // String path = "data/smallerListOfRestaurants.txt";
    // BufferedReader reader = new BufferedReader(new FileReader(path));
    // String Line = reader.readLine();

    // String inLine = getLineFromName("St Honore Pastries");

    // float[] similiarityArr = getSimiliarityArr(inLine);

    // float mostSimilar = 100000;
    // int index = 0;

    // for(int i = 0; i < similiarityArr.length; i++){

    //     if(similiarityArr[i] < mostSimilar){
    //         mostSimilar = similiarityArr[i];
    //         index = i;
    //     }
    // }

    // System.out.println(getLineFromIndex(index));

  }
}
