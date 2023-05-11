package main.java.functions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import main.java.types.ExtensibleHashTable;
import main.java.types.Restaurant;

/**
 * This class holds deals with the initial
 * creation of the filesystem, and the
 * Data files.
 */
public class Serializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ArrayList<String> restaurantNames = new ArrayList<String>();

        System.out.println("Building ExtensibleHashTable to and writing to disk...");
        BufferedReader br = new BufferedReader(new FileReader("data/business_list.txt"));
        String line = br.readLine();
        System.out.println("Test1");
        ExtensibleHashTable table = new ExtensibleHashTable("data/buckets");
        int count = 0;
        while (line != null) {
            count += 1;
            if (line.equals("")) {
                line = br.readLine();
                continue;
            }

            float[] coords = Parser.getCoordinates(line);
            String[] categories = Parser.getCategories(line);
            restaurantNames.add(Parser.getName(line));
            System.out.println(Parser.getName(line) + ' ' + count);
            Restaurant res = new Restaurant(
                    Parser.getID(line),
                    Parser.getName(line),
                    coords[0],
                    coords[1],
                    Parser.getState(line),
                    categories);
            res.setStreets();
            FileOutputStream file = new FileOutputStream("data/restaurants/" +
                    res.business_id.trim());
            ObjectOutputStream out = new ObjectOutputStream(file);
            table.add(res.name, res.business_id);
            out.writeObject(res);

            out.close();
            file.close();
            line = br.readLine();
        }
        br.close();
        System.out.println("Test2");
        FileOutputStream fOut = new FileOutputStream("data/hashtable");
        ObjectOutputStream out = new ObjectOutputStream(fOut);

        out.writeObject(table);

        out.close();
        fOut.close();

        FileOutputStream namesFile = new FileOutputStream("data/business_names");
        ObjectOutputStream namesOut = new ObjectOutputStream(namesFile);

        String[] namesArray = new String[restaurantNames.size()];
        for (int i = 0; i < restaurantNames.size(); i++) {
            namesArray[i] = (String) restaurantNames.get(i);
        }

        namesOut.writeObject(namesArray);

        namesOut.close();
        fOut.close();

        // Test that the hashtable is functioning:
        System.out.println("Checking that Hashtable was created properly...");
        FileInputStream file = new FileInputStream("data/hashtable");
        ObjectInputStream in = new ObjectInputStream(file);
        ExtensibleHashTable inTable = (ExtensibleHashTable) in.readObject();

        String id = inTable.get("Pomme Cafe & Wine Bar");
        if (id != null && id.equalsIgnoreCase("cljHYek6vHtqUgUsnJBqmA")) {
            System.out.println("Hashtable Success!");
        } else {
            System.out.println("Something has gone horribly wrong with the hash table...");
        }

        in.close();
        file.close();

        System.exit(0);
    }
}
