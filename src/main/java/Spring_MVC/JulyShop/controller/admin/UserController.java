package Spring_MVC.JulyShop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Spring_MVC.JulyShop.doamin.User;
import Spring_MVC.JulyShop.service.UploadService;
import Spring_MVC.JulyShop.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError err : errors) {
            System.out.println(err.getField() + "--" + err.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }

        try {
            String avatar = this.uploadService.handleUploadFile(file, "avatar");
            user.setAvatar(avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String passwordHash = passwordEncoder.encode(user.getPassword());
        String roleName = user.getRole().getName();
        user.setPassword(passwordHash);
        user.setRole(this.userService.findRoleByName(roleName));
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("pages") Optional<String> page) {
        int pageNumber = 0; // Default to page 0 (the first page)

        if (page.isPresent()) {
            try {
                pageNumber = Integer.parseInt(page.get()) - 1;
                if (pageNumber < 0)
                    pageNumber = 0;
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }
        }

        Pageable pageable = PageRequest.of(pageNumber, 3);
        Page<User> userPage = this.userService.handleGetAllUser(pageable);

        model.addAttribute("users", userPage.getContent());
        int currentPage = userPage.getNumber() + 1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", userPage.getTotalPages());

        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        System.out.println("=========== " + user.getId());
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable Long id) {
        User user = new User();
        user.setId(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String handleDeleteUser(@ModelAttribute User user) {
        userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String handleUpdateUser(@ModelAttribute User user) {
        User userUpdate = this.userService.getUserById(user.getId());
        if (userUpdate != null) {
            userUpdate.setAddress(user.getAddress());
            userUpdate.setFullName(user.getFullName());
            userUpdate.setPhone(user.getPhone());

            this.userService.handleSaveUser(userUpdate);
        }
        return "redirect:/admin/user";
    }

}
