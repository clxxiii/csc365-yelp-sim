package main.java.com.example.yelprecommender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApplication {

  @GetMapping("/")
  public String index() {
    return "Hello World!";
  }
}
