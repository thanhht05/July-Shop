package Spring_MVC.JulyShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Spring_MVC.JulyShop.doamin.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
