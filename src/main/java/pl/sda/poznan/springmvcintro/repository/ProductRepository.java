package pl.sda.poznan.springmvcintro.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import pl.sda.poznan.springmvcintro.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

  Product findByNameIgnoringCase(String s);

  List<Product> findByNameAndPriceAfterOrderByPriceAsc(String n, Double p);
}
