package Spring_MVC.JulyShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> products = this.productRepository.findAll(pageable);
        return products;
    }

    public Product getUserById(Long id) {
        Optional<Product> prdOptional = this.productRepository.findById(id);
        if (prdOptional.isPresent()) {
            return prdOptional.get();
        }
        return null;
    }

    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }
}
