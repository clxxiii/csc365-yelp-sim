package main.java.functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import main.java.types.ExtensibleHashTable;

public class SeralizeTester {

  public static ExtensibleHashTable readTable() throws IOException, ClassNotFoundException {
    FileInputStream tableFile = new FileInputStream("data/hashtable");
    ObjectInputStream tableIn = new ObjectInputStream(tableFile);
    ExtensibleHashTable table = (ExtensibleHashTable) tableIn.readObject();

    
    
    tableIn.close();
    tableFile.close();
    return table;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    readTable();
    System.exit(0);
  }
}
