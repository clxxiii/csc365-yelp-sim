package main.java.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.java.types.ExtensibleHashTable;
import main.java.types.Restaurant;

/**
 * This class holds deals with the initial
 * creation of the filesystem, and the
 * Data files.
 */
public class Serializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("Building ExtensibleHashTable to and writing to disk...");
        BufferedReader br = new BufferedReader(new FileReader("data/business_list.txt"));
        String line = br.readLine();

        ExtensibleHashTable table = new ExtensibleHashTable("data/buckets");

        while (line != null) {
            float[] coords = Parser.getCoordinates(line);
            String[] categories = Parser.getCategories(line);
            Restaurant res = new Restaurant(
                    Parser.getID(line),
                    Parser.getName(line),
                    coords[0],
                    coords[1],
                    Parser.getState(line),
                    categories);

            FileOutputStream file = new FileOutputStream("data/restaurants/" +
                    res.business_id.trim());
            ObjectOutputStream out = new ObjectOutputStream(file);
            table.add(res.name, res.business_id.trim());
            out.writeObject(res);

            out.close();
            file.close();
            line = br.readLine();
        }
        br.close();

        FileOutputStream fOut = new FileOutputStream("data/hashtable");
        ObjectOutputStream out = new ObjectOutputStream(fOut);

        out.writeObject(table);

        out.close();
        fOut.close();

        // Test that the hashtable is functioning:
        System.out.println("Checking that Hashtable was created properly...");
        FileInputStream file = new FileInputStream("data/hashtable");
        ObjectInputStream in = new ObjectInputStream(file);
        ExtensibleHashTable inTable = (ExtensibleHashTable) in.readObject();

        String id = inTable.get("Spirit Bistro");
        if (id.equalsIgnoreCase("I6S2XzdA0vpodHk5ZoA2yg")) {
            System.out.println("Hashtable Success!");
        } else {
            System.out.println("Something has gone horribly wrong with the hash table...");
        }

        in.close();
        file.close();

        System.exit(0);
    }
}
