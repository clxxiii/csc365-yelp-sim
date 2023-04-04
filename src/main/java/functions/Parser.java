package main.java.functions;

import java.util.ArrayList;

public class Parser {

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
      if (input.charAt(i) == 'n' & input.charAt(i + 1) == 'a' &
          input.charAt(i + 2) == 'm' & input.charAt(i + 3) == 'e') {
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
      if (input.charAt(i) == 'b' & input.charAt(i + 1) == 'u' &
          input.charAt(i + 2) == 's' & input.charAt(i + 3) == 'i') {
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

  public static float[] getCoordinates(String input) {
    String out1 = "", out2 = "";
    for (int i = 0; i < input.length() - 1; i++) {
      if (i + 13 < input.length() & input.charAt(i) == 'l' &
          input.charAt(i + 1) == 'a' & input.charAt(i + 2) == 't' &
          input.charAt(i + 4) == 't' & input.charAt(i + 7) == 'e') {
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

  public static String[] getCategories(String input) {
    String outString = "";
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == 'c' & input.charAt(i + 1) == 'a' &
          input.charAt(i + 2) == 't' & input.charAt(i + 3) == 'e' &
          input.charAt(i + 6) == 'r') {
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

  public static String getState(String inLine) {
    String state = "";
    for (int i = 0; i < inLine.length(); i++) {
      if (i + 5 < inLine.length() & inLine.charAt(i) == 's' &
          inLine.charAt(i + 1) == 't' & inLine.charAt(i + 2) == 'a' &
          inLine.charAt(i + 3) == 't' & inLine.charAt(i + 4) == 'e') {
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
}
