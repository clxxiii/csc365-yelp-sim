package main.java.types;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ExtensibleHashTable implements java.io.Serializable {
  private class KeyValue implements java.io.Serializable {
    public int key;
    public String value;

    KeyValue(int k, String v) {
      key = k;
      value = v;
    }
  }

  public class Bucket implements java.io.Serializable {
    private KeyValue[] arr;
    public int size;

    public Bucket() {
      arr = new KeyValue[32];
      size = 0;
    }

    /**
     * Adds an element to the list, returns false if the bucket if full.
     */
    boolean add(int k, String v) {
      int index = 0;
      while (index < arr.length) {
        if (arr[index] == null) {
          KeyValue cell = new KeyValue(k, v);
          arr[index] = cell;
          size++;
          return true;
        }
        index++;
      }
      return false;
    }

    String find(int k) {
      for (int i = 0; i < size; i++) {
        if (arr[i].key == k)
          return arr[i].value;
      }
      return null;
    }

    /**
     * Removes an element from the list, and replaces the empty spot with
     * the last element
     */
    String remove(int k) {
      String removed = "";
      int rIndex = -1;
      for (int i = 0; i < size; i++) {
        if (arr[i].key == k) {
          removed = arr[i].value;
          rIndex = i;
          arr[i] = null;
        }
      }

      if (rIndex != -1)
        swap(rIndex, size - 1);

      if (removed != "")
        size--;

      return removed == "" ? null : removed;
    }

    public KeyValue[] toArray() {
      return arr;
    }

    public boolean isFull() {
      return size >= arr.length;
    }

    private void swap(int i1, int i2) {
      KeyValue temp = arr[i1];
      arr[i1] = arr[i2];
      arr[i2] = temp;
    }
  }

  String bucketDir;
  int size = 2;
  int[] pBuckets; // bucket pointers
  int numOfBuckets = 1;

  public ExtensibleHashTable(String bucketDir) throws IOException {
    this.bucketDir = bucketDir;
    pBuckets = new int[2];
    pBuckets[0] = 0;
    pBuckets[1] = 0;
    Bucket bucket = new Bucket();
    FileOutputStream bFile = new FileOutputStream(getFilePath(0));
    ObjectOutputStream os = new ObjectOutputStream(bFile);
    os.writeObject(bucket);

    os.close();
    bFile.close();
  }

  public void add(String key, String value) throws IOException, ClassNotFoundException {
    int hash = key.hashCode();
    int pIndex = hash & (size - 1);
    int bIndex = pBuckets[pIndex];
    FileInputStream file = new FileInputStream(getFilePath(bIndex));
    ObjectInputStream in = new ObjectInputStream(file);
    Bucket bucket = (Bucket) in.readObject();
    bucket.add(hash, value);
    boolean isFull = bucket.isFull();

    in.close();
    file.close();

    FileOutputStream fileOut = new FileOutputStream(getFilePath(bIndex));
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(bucket);

    fileOut.close();
    out.close();

    if (isFull)
      resize(bIndex);
  }

  public String get(String e) throws IOException, ClassNotFoundException {
    int hash = e.hashCode();
    int pIndex = hash & (size - 1);
    int bIndex = pBuckets[pIndex];

    FileInputStream file = new FileInputStream(getFilePath(bIndex));
    ObjectInputStream in = new ObjectInputStream(file);
    Bucket bucket = (Bucket) in.readObject();

    String find = bucket.find(hash);

    in.close();
    file.close();
    return find;
  }

  /**
   * Handles all resizing and bucket management.
   * Takes the full bucket index as a parameter
   */
  private void resize(int lbIndex) throws IOException, ClassNotFoundException {

    // This is the check for whether or not I have to go through pain.
    ArrayList<Integer> pointers = getPointers(lbIndex);
    if (pointers.size() == 1) {
      int oldsize = size;
      size <<= 1;
      int[] newPBuckets = new int[size];

      int[] pointersToSplit = new int[2];
      for (int i = 0; i < size; i++) {
        if ((i & (oldsize - 1)) == lbIndex) {
          pointersToSplit[i / oldsize] = i & (oldsize - 1);
        }

        newPBuckets[i] = pBuckets[i & (oldsize - 1)];
      }
      pBuckets = newPBuckets;
      splitBucket(pointersToSplit[0], pointersToSplit[1]);
    } else {
      splitBucket(pointers.get(0), pointers.get(1));
    }
  }

  /**
   * Takes two indexes from pIndex which point to the
   * same bucket, and seperates them into two
   * seperate buckets.
   */
  private void splitBucket(int p1, int p2) throws ClassNotFoundException, IOException {
    if (p1 == p2)
      return;
    if (pBuckets[p1] != pBuckets[p2])
      return;
    int bIndex = pBuckets[p1];

    FileInputStream file = new FileInputStream(getFilePath(bIndex));
    ObjectInputStream in = new ObjectInputStream(file);
    Bucket latestBucket = (Bucket) in.readObject();
    Bucket newBucket = new Bucket();

    KeyValue[] arr = latestBucket.toArray();

    for (KeyValue el : arr) {
      if (el == null)
        break;

      if ((el.key & (size - 1)) == p2) {
        latestBucket.remove(el.key);
        newBucket.add(el.key, el.value);
      }
    }

    pBuckets[p2] = numOfBuckets++;

    FileOutputStream oldFile = new FileOutputStream(getFilePath(bIndex));
    FileOutputStream newFile = new FileOutputStream(getFilePath(pBuckets[p2]));
    ObjectOutputStream oldBucketOS = new ObjectOutputStream(oldFile);
    ObjectOutputStream newBucketOS = new ObjectOutputStream(newFile);
    oldBucketOS.writeObject(latestBucket);
    newBucketOS.writeObject(newBucket);

    oldBucketOS.close();
    newBucketOS.close();
    oldFile.close();
    newFile.close();
  }

  /**
   * Returns the number of ints in pBuckets that point to
   * the selected bucket.
   * If the size of this array is 1,
   */
  private ArrayList<Integer> getPointers(int bIndex) {
    ArrayList<Integer> indexes = new ArrayList<Integer>();
    for (int i = 0; i < pBuckets.length; i++) {
      if (pBuckets[i] == bIndex)
        indexes.add(i);
    }
    return indexes;
  }

  private String getFilePath(int id) {
    return bucketDir + "/bucket-" + id;
  }
}
