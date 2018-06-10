package pl.sda.poznan.springmvcintro.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.sda.poznan.springmvcintro.model.Product;

@Repository
public class BaseProductRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public List<Product> getAllProducts() {
    return this.entityManager
        .createQuery("select p from Product p")
        .getResultList();
  }

  @Transactional
  public void saveProduct(Product product){
    entityManager.persist(product);
  }

}
