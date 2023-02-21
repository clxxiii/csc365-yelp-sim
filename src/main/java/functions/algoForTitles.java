package main.java.functions;

public class algoForTitles {

  static int diffBetweenTitles(String title1, String title2) {
    // Ensure that title1 stores the shorter title for algorithm

    if (title1.length() > title2.length()) {
      String temp = title1;
      title1 = title2;
      title2 = temp;
    }

    // Separate difference between a change or an add, not necessary but could come
    // in handy.
    int change = 0;
    int add = 0;

    // Point at the 0th index and compare the character between both titles. Stops
    // at end of shorter string(title 1).
    for (int i = 0; i < title1.length(); i++) {
      if (title1.charAt(i) != title2.charAt(i)) {
        change++;
      }
    }

    // Now the only differences left are additions, you would need to add number of
    // characters = to the difference in lengths of titles.

    add = Math.abs(title2.length() - title1.length());

    System.out.println(
        "Took: " + change + " changes and " + add + " additions");

    return (add + change);
  }

  public static void main(String[] args) {
    String w2 = "Ringmaster";
    String w1 = "rongmaster";

    diffBetweenTitles(w1, w2);
  }
}
