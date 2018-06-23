package pl.sda.poznan.springmvcintro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.poznan.springmvcintro.model.Product;
import pl.sda.poznan.springmvcintro.repository.ProductRepository;

@RestController
public class ProductController {

  private ProductRepository productRepository;

  public ProductController(
      ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping("/product")
  public List<Product> products() {
    Iterable<Product> all = this.productRepository.findAll();
    List<Product> products = new ArrayList<>();
    all.forEach(products::add);

    return products;
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Optional<Product> optional = this.productRepository.findById(id);
    if (optional.isPresent()) {
      return ResponseEntity.ok(optional.get());
    } else {
      return ResponseEntity.badRequest().build();
    }

    // to samo w bardziej funkcyjny sposób:
    /*
    return optional.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().build());

    */
  }

  @GetMapping("/product/name/{name}")
  public Product getByName(@PathVariable String name) {
    return this.productRepository.findByNameIgnoringCase(name);
  }

  @PostMapping("/product")
  public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product,
      BindingResult bindingResult) {
    // walidujemy encję i jeżeli występują błędy to od razu zwracamy staus BAD_REQUEST - 400
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    // jeśli błędów nie było kontynuujemy przetwarzenie - sprawdzamy, czy w bazie danych jest taki produkt
    Product productFromDb = this.productRepository.findByNameIgnoringCase(product.getName());
    // jeśli repozytorium coś zwróci, to mamy juz taki produkt w bazie
    // wysyłamy status 409 -> CONFLICT
    if (productFromDb != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    // jeśli takiego obiektu nie ma w bazie to zapisujemy do DB
    //zwracamy staus 201 -> CREATED
    this.productRepository.save(product);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  //todo: napisać aktualizację encji produkt
  //todo:  sprawdzić czy taki produkt istnieje - zastanowić sie nad statusem jesli nie istnieje
  //todo:  zwrócić status odpowiedzi

  @PutMapping("/product")
  public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product,
      BindingResult bindingResult) {
    // sprawdzamy, czy uaktualniona wersja produktu jest zgodna z zasadami walidacyjnymi
    if (bindingResult.hasErrors()) {
      // jesli mamy błędy walidacji to zwracamy 400 - BAD REQUEST
      return ResponseEntity.badRequest().body(product);
    }

    if (product.getId() == null) {
      return ResponseEntity.badRequest().body(product);
    }

    //jesli nie było błędów: pobieramy z bazy danych produkt
    Optional<Product> optionalProduct = productRepository.findById(product.getId());

    if (optionalProduct.isPresent()) {
      //metoda save zadziała jak metoda merge entityManagera (jesli jest nadane id to wykona update, jesli bez id to wykona save)
      productRepository.save(product);
      return ResponseEntity.ok(product);
    } else {
      // nie ma produktu o takim id -> probujemy aktualizowac nie istniejacy produkt - zwracamy blad
      return ResponseEntity.badRequest().body(product);
    }
  }


  //todo: zaimplementować usuwanie produktu i zwrocic poprawny status
  //todo: sprawdź czy istnieje produkt o takim id
  @DeleteMapping("/product/{id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    if (this.productRepository.existsById(id)) {
      this.productRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }
}
