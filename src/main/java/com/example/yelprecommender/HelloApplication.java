package main.java.com.example.yelprecommender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApplication {

  @GetMapping("/get_names")
  public String[] listBusinessNames() {
    // This is just a placeholder to test the frontend
    String[] list = {"go",     "javascript", "python",   "rust", "swift",
                     "kotlin", "elixir",     "java",     "lisp", "v",
                     "zig",    "nim",        "rescript", "d",    "haskell"};
    return list;
  }

  @GetMapping("/recommend")
  public String recommendBusinees(@RequestParam(value = "id",
                                                defaultValue = "0") String id) {
    return "Fetching two businesses related to Business ID " + id;
  }
}
