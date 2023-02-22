package main.java.com.example.yelprecommender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApplication {

  @GetMapping("/get_names")
  public String[] listBusinessNames() {
    String[] list = { "Hello World!" };
    return list;
  }

  @GetMapping("/recommend")
  public String recommendBusinees(
    @RequestParam(value = "id", defaultValue = "0") String id
  ) {
    return "Fetching two businesses related to Business ID " + id;
  }
}
