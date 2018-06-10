package pl.sda.poznan.springmvcintro.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.poznan.springmvcintro.model.Product;
import pl.sda.poznan.springmvcintro.repository.BaseProductRepository;

@RestController
public class ProductController {

  private BaseProductRepository baseProductRepository;

  @Autowired
  public ProductController(
      BaseProductRepository baseProductRepository) {
    this.baseProductRepository = baseProductRepository;
  }

  @GetMapping("/product")
  public List<Product> products() {
    List<Product> allProducts = this.baseProductRepository.getAllProducts();
    return allProducts;
  }

  @GetMapping("/product/{id}")
  public Product getProductById(@PathVariable Long id) {
    Product product = new Product();
    product.setId(id);
    product.setName("Produkt z fajnym id");
    return product;
  }

  @PostMapping("/product")
  public String createProduct(@RequestBody Product product) {
    this.baseProductRepository.saveProduct(product);

    //todo: set HTTP status to 201
    return "CREATED";
  }


}
