package Spring_MVC.JulyShop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DahsboardController {
    @GetMapping("/admin")
    public String getDashboardPage() {
        return "admin/dashboard/index";
    }

}
