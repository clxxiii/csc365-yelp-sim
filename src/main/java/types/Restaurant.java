package main.java.types;

public class Restaurant implements java.io.Serializable {

  public String business_id;
  public String name;
  public String address;
  public String city;
  public String state;
  public String postal_code;

  public double latitude;
  public double longitude;

  public int stars;
  public int review_count;
  public int is_open;

  public RestaurantAttributes attributes;

  public String categories;
  public Hours hours;

  public Restaurant(
    String id,
    String name,
    String addr,
    String city,
    String state,
    String postal_code,
    double latitude,
    double longitude,
    int stars,
    int review_count,
    int is_open,
    String categories
  ) {
    business_id = id;
    this.name = name;
    address = addr;
    this.city = city;
    this.state = state;
    this.postal_code = postal_code;
    this.latitude = latitude;
    this.longitude = longitude;
    this.stars = stars;
    this.review_count = review_count;
    this.is_open = is_open;
    this.categories = categories;
    attributes = new RestaurantAttributes();
    hours = new Hours();
  }

  public Restaurant(
    String id,
    String name,
    String addr,
    String city,
    String state,
    String postal_code,
    double latitude,
    double longitude,
    int stars,
    int review_count,
    int is_open,
    String categories,
    String delivery,
    String outdoor,
    String credit_cards,
    String parking,
    String bike_parking,
    String price_range2,
    String take_out,
    String appointment_only,
    String wifi,
    String alcohol,
    String caters
  ) {
    business_id = id;
    this.name = name;
    address = addr;
    this.city = city;
    this.state = state;
    this.postal_code = postal_code;
    this.latitude = latitude;
    this.longitude = longitude;
    this.stars = stars;
    this.review_count = review_count;
    this.is_open = is_open;
    this.categories = categories;
    attributes = new RestaurantAttributes();
    setAttributes(
      delivery,
      outdoor,
      credit_cards,
      parking,
      bike_parking,
      price_range2,
      take_out,
      appointment_only,
      wifi,
      alcohol,
      caters
    );
    hours = new Hours();
  }

  public void setAttributes(
    String delivery,
    String outdoor,
    String credit_cards,
    String parking,
    String bike_parking,
    String price_range2,
    String take_out,
    String appointment_only,
    String wifi,
    String alcohol,
    String caters
  ) {
    attributes.RestaurantsDelivery = delivery;
    attributes.OutdoorSearing = outdoor;
    attributes.BusinessAcceptsCreditCards = credit_cards;
    attributes.BusinessParking = parking;
    attributes.BikeParking = bike_parking;
    attributes.RestaurantsPriceRange2 = price_range2;
    attributes.RestaurantsTakeOut = take_out;
    attributes.ByAppointmentOnly = appointment_only;
    attributes.WiFi = wifi;
    attributes.Alcohol = alcohol;
    attributes.Caters = caters;
  }
}
