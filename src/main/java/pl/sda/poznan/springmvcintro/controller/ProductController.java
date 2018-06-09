package pl.sda.poznan.springmvcintro.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.poznan.springmvcintro.model.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

  @GetMapping(value = "")
  public List<Product> products() {
    Product p1 = new Product();
    p1.setName("Dell Latitude");
    p1.setDescription("Laptop DELL");
    p1.setPrice(2999D);

    Product p2 = new Product();
    p2.setName("LG G6");
    p2.setDescription("Smatfon lg");
    p2.setPrice(1500D);

    return Arrays.asList(p1, p2);
  }

  @GetMapping("{id}")
  public Product getProductById(@PathVariable Long id) {
    Product product = new Product();
    product.setId(id);
    product.setName("Produkt z fajnym id");
    return product;
  }

  @PostMapping("")
  public String createProduct(@RequestBody Product product) {
    System.out.println("Otrzymalem request POST - utworz produkt");

    return "OK";
  }


}
