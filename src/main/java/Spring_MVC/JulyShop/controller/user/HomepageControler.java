package Spring_MVC.JulyShop.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Spring_MVC.JulyShop.doamin.Product;
import Spring_MVC.JulyShop.service.ProductService;

@Controller
public class HomepageControler {
    private final ProductService productService;

    public HomepageControler(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHompage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "client/homepage/index";
    }

}
