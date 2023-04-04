package main.java.functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Locality {

  public static String getLineFromName(String name) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("data/business_list.txt"));
    String Line = reader.readLine();

    while (Line != null) {
      if (Parser.getName(Line).equals(name)) {
        reader.close();
        return Line;
      }
      Line = reader.readLine();
    }
    reader.close();
    return "";
  }

  public static String getLineFromIndex(int Index) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("data/business_list.txt"));
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

  public static float[] getDistArr(String input) throws IOException {
    String path = "data/business_list.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();
    int count = 0;
    float[] distArray = new float[10010];
    while (Line != null) {
      distArray[count] = 1 / Parser.getDistance(Parser.getCoordinates(input),
          Parser.getCoordinates(Line));
      count++;
      Line = reader.readLine();
    }

    reader.close();
    return distArray;
  }

  public static float TFIDF(String[] commonElements, FreqTable freqTable) {
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

  public static float[] getTFIDFArr(String input, FreqTable freqTable)
      throws IOException {
    String path = "data/business_list.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();
    int count = 0;
    float[] IFIDFArr = new float[10010];

    while (Line != null) {
      if (Line.equals(input)) {
        IFIDFArr[count] = -1;
      } else {
        IFIDFArr[count] = TFIDF(Parser.commonElements(Parser.getCategories(input),
            Parser.getCategories(Line)),
            freqTable);
      }
      count++;
      Line = reader.readLine();
    }

    reader.close();
    return IFIDFArr;
  }

  public static FreqTable getFreqTable() throws IOException {
    String path = "data/business_list.txt";
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String Line = reader.readLine();
    FreqTable freqTable = new FreqTable();

    while (Line != null) {
      for (int i = 0; i < Parser.getCategories(Line).length; i++) {
        String ithCategory = Parser.getCategories(Line)[i];
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
    float[] output = new float[10000];
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] < min) {
        min = arr[i];
      }
      if (arr[i] > max) {
        max = arr[i];
      }
    }

    for (int i = 0; i < arr.length; i++) {
      output[i] = (arr[i] - min) / (max - min);
    }
    return output;
  }

  public static float[] getSimiliarityArr(String inLine) throws IOException {
    float[] output = new float[10000];
    FreqTable freqTable = getFreqTable();
    float[] TFIDFArr = getTFIDFArr(inLine, freqTable);
    float[] distArr = getDistArr(inLine);

    // Is this doing anything?

    // float[] normIFIDFArr = normalizeArr(IFIDFArr);
    // float[] normDistArr = normalizeArr(distArr);

    if (distArr.length != 10000 || TFIDFArr.length != 10000) {
      return new float[10];
    }

    for (int i = 0; i < output.length; i++) {
      float similarity = 0;

      similarity -= distArr[i];
      similarity += TFIDFArr[i];

      output[i] = similarity;
    }

    return output;
  }

  /*
   * public static void serializeHT(FrequencyTable ht, String fileName)
   * throws FileNotFoundException, IOException {
   * ObjectOutputStream out = new ObjectOutputStream(new
   * FileOutputStream(fileName));
   * out.writeObject(ht);
   * out.close();
   * }
   * 
   * public static FrequencyTable deserializeHT(String fileName)
   * throws ClassNotFoundException, IOException {
   * ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
   * FrequencyTable ht = (FrequencyTable) in.readObject();
   * in.close();
   * return ht;
   * }
   */

  public static String tooString(String[] list) {
    String out = "[";
    for (int i = 0; i < list.length; i++) {
      if (i >= list.length - 1)
        out += list[i];
      else
        out += list[i] + ",";
    }
    return out + "]";
  }

  public static void main(String[] args) throws IOException {

    // BufferedWriter bw = new BufferedWriter(new
    // FileWriter("data/businessList.txt"));

    // BufferedReader br = new BufferedReader(new
    // FileReader("data/smallerListOfRestaurants.txt"));

    // String Line = br.readLine();
    // while(Line!=null){

    // }

    // bw.close;

    // HT ht = getP2Hash();
    // System.out.println(ht.getValue("White Horse Tavern and Wine Bar"));
    // Scanner in = new Scanner(System.in);
    // String fileName = ht.getValue(in.nextLine());

    // BufferedReader reader = new BufferedReader(new
    // FileReader("data/businesses/" + fileName));

    // System.out.println(reader.readLine().split(";")[1]);

    // reader.close();

    ///////////// Code to create file system /////////////

    // BufferedReader reader = new BufferedReader(
    // new FileReader("data/smallerListOfRestaurants.txt")
    // );White Horse Tavern and Wine Bar

    // String Line = reader.readLine();
    // String outString = "";
    // int count = 0;
    // while(Line != null){
    // count += 1;

    // outString = "";
    // outString += getName(Line) + ";" + tooString(getCategories(Line));

    // PrintWriter out = new PrintWriter(new BufferedWriter(new
    // FileWriter("data/businesses/" + getName(Line) + ".txt")));
    // out.write(outString);
    // out.close();

    // Line = reader.readLine();
    // }
    // System.out.println(count);

    // reader.close();

    /////////////////// Algorithmic Test Code ///////////////////

    // String path = "data/smallerListOfRestaurants.txt";
    // BufferedReader reader = new BufferedReader(new FileReader(path));
    // String Line = reader.readLine();

    // String inLine = getLineFromName("St Honore Pastries");

    // float[] similiarityArr = getSimiliarityArr(inLine);

    // float mostSimilar = 100000;
    // int index = 0;

    // for(int i = 0; i < similiarityArr.length; i++){

    // if(similiarityArr[i] < mostSimilar){
    // mostSimilar = similiarityArr[i];
    // index = i;
    // }
    // }

    // System.out.println(getLineFromIndex(index));
  }
}
