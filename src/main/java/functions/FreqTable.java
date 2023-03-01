package main.java.functions;

import java.io.*;
import java.util.*;

public class FreqTable implements java.io.Serializable {

  public FreqTable(){}

  static final class Node {

    String key;
    Node next;
    int count;

    // Object value;
    Node(String k, Node n) {
      key = k;
      next = n;
      count = 0;
    }

    Node(String k, Node n, int c) {
      key = k;
      next = n;
      count = c;
    }
  }

  Node[] table = new Node[8]; //always a power of 2
  int size = 0;

  boolean contains(String key) {
    int h = key.hashCode();
    int i = h & (table.length - 1);
    for (Node e = table[i]; e != null; e = e.next) {
      if (key.equals(e.key))
        return true;
    }
    return false;
  }

  void add(String key) {
    int h = key.hashCode();
    int i = h & (table.length - 1);
    for (Node e = table[i]; e != null; e = e.next) {
      if (key.equals(e.key)) {
        e.count++;
        return;
      }
    }
    table[i] = new Node(key, table[i]);
    ++size;
    if ((float) size / table.length >= 0.75f)
      resize();
  }

  int getCount(String key) {
    int h = key.hashCode();
    int i = h & (table.length - 1);

    for (Node e = table[i]; e != null; e = e.next) {
      if (key.equals(e.key)) {
        return e.count;
      }
    }

    return 0;
  }

  void resize() {
    Node[] oldTable = table;
    int oldCapacity = oldTable.length;
    int newCapacity = oldCapacity << 1;
    Node[] newTable = new Node[newCapacity];
    for (int i = 0; i < oldCapacity; ++i) {
      for (Node e = oldTable[i]; e != null; e = e.next) {
        int h = e.key.hashCode();
        int j = h & (newTable.length - 1);
        newTable[j] = new Node(e.key, newTable[j], e.count);
      }
    }
    table = newTable;
  }

  void remove(String key) {
    int h = key.hashCode();
    int i = h & (table.length - 1);
    Node e = table[i], p = null;
    while (e != null) {
      if (key.equals(e.key)) {
        if (p == null)
          table[i] = e.next;
        else
          p.next = e.next;
        break;
      }
      p = e;
      e = e.next;
    }
  }

  void printAll() {
    for (int i = 0; i < table.length; ++i)
      for (Node e = table[i]; e != null; e = e.next)
        System.out.println(e.key);
  }

  // private void writeObject(ObjectOutputStream s) throws Exception {
  //   s.defaultWriteObject();
  //   s.writeInt(size);
  //   for (int i = 0; i < table.length; ++i) {
  //     for (Node e = table[i]; e != null; e = e.next) {
  //       s.writeObject(e.key);
  //     }
  //   }
  // }

  // private void readObject(ObjectInputStream s) throws Exception {
  //   s.defaultReadObject();
  //   int n = s.readInt();
  //   for (int i = 0; i < n; ++i)
  //     add(s.readObject());
  // }
}
