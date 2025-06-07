package Spring_MVC.JulyShop.controller.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import Spring_MVC.JulyShop.doamin.Product;
import Spring_MVC.JulyShop.doamin.User;
import Spring_MVC.JulyShop.doamin.dto.RegisterDTO;
import Spring_MVC.JulyShop.service.ProductService;
import Spring_MVC.JulyShop.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomepageControler {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomepageControler(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userRegister", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("userRegister") @Valid RegisterDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerDTOToUser(userDTO);
        String hashPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        user.setRole(this.userService.findRoleByName("USER"));
        this.userService.handleSaveUser(user);

        return "redirect:/";
    }

}
