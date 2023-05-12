package main.java.functions;

import main.java.types.Restaurant;
import main.java.types.Street;

import java.util.*;

public class Dijkstra {
    public static ArrayList<Street> getShortestPath(Restaurant[] restaurants, Restaurant start, Restaurant end) {
        ArrayList<Street> path = new ArrayList<>();
        Map<Restaurant, Float> dist = new HashMap<>();
        Map<Restaurant, Restaurant> prev = new HashMap<>();
        Set<Restaurant> visited = new HashSet<>();

        // Initialization
        for (Restaurant r : restaurants) {
            if (r.equals(start)) {
                dist.put(r, (float) 0);
            } else {
                dist.put(r, Float.POSITIVE_INFINITY);
            }
            prev.put(r, null);
        }

        // Use a priority queue to select the unvisited restaurant with the smallest tentative distance
        PriorityQueue<Restaurant> unvisited = new PriorityQueue<>(Comparator.comparing(dist::get));
        unvisited.addAll(Arrays.asList(restaurants));

        while (!unvisited.isEmpty()) {
            // Find the unvisited restaurant with the smallest tentative distance
            Restaurant curr = null;
            float shortestDist = Float.POSITIVE_INFINITY;
            for (Restaurant r : unvisited) {
                if (dist.get(r) < shortestDist && r.streets.length > 0) { // only consider restaurants with streets
                    shortestDist = dist.get(r);
                    curr = r;
                }
            }

    if (curr == null) {
        // no unvisited restaurants have streets connected to them, so we're done
        break;
    }

            // If we've reached the end, we're done
            if (curr.equals(end)) {
                // Build the path from end to start
                while (prev.get(curr) != null) {
                    for (Street s : curr.streets) {
                        if (s.Restaurants[0].equals(curr) && s.Restaurants[1].equals(prev.get(curr)) ||
                                s.Restaurants[0].equals(prev.get(curr)) && s.Restaurants[1].equals(curr)) {
                            path.add(s);
                            break;
                        }
                    }
                    curr = prev.get(curr);
                }
                // Reverse the path so it goes from start to end
                Collections.reverse(path);
                return path;
            }

            visited.add(curr);

            // Update distances to neighbors
            for (Street s : curr.streets) {
              Restaurant neighbor = s.Restaurants[0].equals(curr) ? s.Restaurants[1] : s.Restaurants[0];
              if (!dist.containsKey(neighbor)) continue; // skip if neighbor distance not initialized
              float altDist = dist.get(curr) + s.length;
              if (altDist < dist.get(neighbor)) {
                dist.put(neighbor, altDist);
                prev.put(neighbor, curr);
              }
            }
            
        }

        // No path found
        return null;
    }
}
