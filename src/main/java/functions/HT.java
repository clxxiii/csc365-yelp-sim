package main.java.functions;

import java.io.*;
import java.util.*;
import java.io.Serializable;

import javax.swing.text.html.HTMLDocument;

public class HT implements java.io.Serializable {

  public HT(){}

  static final class Node implements java.io.Serializable{


    //Where keys are business names and values are file names
    String key;
    String value; 
    Node next;

    // Object value;

    // Node(String k, Node n) {
    //   key = k;
    //   next = n;
    // }

    Node(String k, Node n, String v) {
      key = k;
      next = n;
      value = v;
    }
  }

  Node[] table = new Node[8]; //always a power of 2
  int size = 0;

  String getValue(String key){
    int h = key.hashCode();
    int i = h & (table.length - 1);

    for (Node e = table[i]; e != null; e = e.next) {
      if (key.equals(e.key))
        return e.value;
    }
    return null;
  }

  boolean contains(String key) {
    int h = key.hashCode();
    int i = h & (table.length - 1);
    for (Node e = table[i]; e != null; e = e.next) {
      if (key.equals(e.key))
        return true;
    }
    return false;
  }

  void add(String key, String value) {
    int h = key.hashCode();
    int i = h & (table.length - 1);
    for (Node e = table[i]; e != null; e = e.next) {
        if (key.equals(e.key))
      return;
    }
    table[i] = new Node(key, table[i], value);
    ++size;
    if ((float)size/table.length >= 0.75f)
        resize();
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
        newTable[j] = new Node(e.key, newTable[j], e.value);
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
        System.out.println(e.key + " - " + e.value);
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


	//Test of Extendible Hash
	public static void main(String[] args){

		
		HT ht = new HT();
		for(int i = 0; i < 100; i++){
			String fileName = i + "";
			String businessName = "Name" + i;
			ht.add(businessName, fileName);
		}

		System.out.println("Business Name - File Name\n---------------------");
		ht.printAll();	
	}
	
}	
