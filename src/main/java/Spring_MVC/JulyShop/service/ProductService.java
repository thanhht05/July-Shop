package Spring_MVC.JulyShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Spring_MVC.JulyShop.doamin.Product;
import Spring_MVC.JulyShop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return products;
    }
}
