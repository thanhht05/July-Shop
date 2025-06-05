package Spring_MVC.JulyShop.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String getHompage(Model model, @RequestParam("pages") Optional<String> page) {
        int pageNumber = 0;
        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get()) - 1;
                if (pageNumber < 0) {
                    pageNumber = 0;
                }

            } catch (Exception e) {
                pageNumber = 0;
            }

        }

        Pageable pageable = PageRequest.of(pageNumber, 4);
        Page<Product> products = this.productService.getAllProducts(pageable);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", products.getNumber() + 1);
        model.addAttribute("totalPages", products.getTotalPages());

        return "client/homepage/index";
    }

}
