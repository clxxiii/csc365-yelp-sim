package main.java.functions;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.*;

public class guiTest {

  public static void main(String[] args) {
    String[] listOfMovies = { "Batman", "Batman 2", "Batman 3" };

    // TEST
    JFrame frame = new JFrame("HelloWorldSwing");
    frame.setPreferredSize(new Dimension(500, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(new Point(500, 150));

    JLabel label = new JLabel("Please select a movie!");

    JComboBox movieList = new JComboBox(listOfMovies);
    movieList.addActionListener(movieList);

    frame.getContentPane().add(label);
    frame.getContentPane().add(movieList);

    // Display the window.

    frame.pack();
    frame.setVisible(true);
  }
}
