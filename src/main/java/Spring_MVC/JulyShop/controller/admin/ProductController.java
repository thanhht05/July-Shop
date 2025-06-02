package Spring_MVC.JulyShop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Spring_MVC.JulyShop.doamin.Product;
import Spring_MVC.JulyShop.service.ProductService;
import Spring_MVC.JulyShop.service.UploadService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product/create")
    public String getProductCreatePage(Model model) {
        model.addAttribute("product", new Product());
        return "admin//product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        try {
            String imageProduct = uploadService.handleUploadFile(file, "product");
            product.setImg(imageProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String target = product.getTarget();
        product.setTarget(target);
        this.productService.saveProduct(product);
        return "redirect:/admin/product";

    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable Long id) {

        Product product = this.productService.getUserById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute Product product) {

        Product productUpdate = this.productService.getUserById(product.getId());
        if (productUpdate != null) {
            productUpdate.setDetailDesc(product.getDetailDesc());
            productUpdate.setFactor(product.getFactor());
            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setSold(product.getSold());
            productUpdate.setQuantity(product.getQuantity());
            productUpdate.setTarget(product.getTarget());
            this.productService.saveProduct(productUpdate);
        }

        return "redirect:/admin/product";
    }

}
