package Spring_MVC.JulyShop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
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
    public String getProductPage(Model model, @RequestParam("pages") Optional<String> page) {
        int pageNumber = 0;
        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get()) - 1;
                if (pageNumber < 0) {
                    pageNumber = 0;
                }

            } catch (Exception e) {
                pageNumber = 0;
                e.printStackTrace();
            }
        }

        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<Product> products = this.productService.getAllProducts(pageable);
        model.addAttribute("products", products.getContent());
        int currentPage = products.getNumber() + 1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable Long id) {

        Product product = this.productService.getUserById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getMethodName(Model model, @PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        model.addAttribute("product", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String handleDeleteProduct(@ModelAttribute Product product) {
        this.productService.deleteProductById(product.getId());

        return "redirect:/admin/product";
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
