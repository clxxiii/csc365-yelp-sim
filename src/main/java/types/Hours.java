package main.java.types;

public class Hours implements java.io.Serializable {

  public String Sunday;
  public String Monday;
  public String Tuesday;
  public String Wednesday;
  public String Thursday;
  public String Friday;
  public String Saturday;

  public Hours() {}

  public Hours(
    String s,
    String m,
    String t,
    String w,
    String th,
    String f,
    String st
  ) {
    setHours(s, m, t, w, th, f, st);
  }

  public void setHours(
    String s,
    String m,
    String t,
    String w,
    String th,
    String f,
    String st
  ) {
    Sunday = s;
    Monday = m;
    Tuesday = t;
    Wednesday = w;
    Thursday = th;
    Friday = f;
    Saturday = st;
  }
}
