package Spring_MVC.JulyShop.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageControler {
    @GetMapping("/")
    public String getHompage() {
        return "admin/index";
    }

}
