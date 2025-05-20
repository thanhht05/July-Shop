package Spring_MVC.JulyShop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Spring_MVC.JulyShop.doamin.User;
import Spring_MVC.JulyShop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleSaveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> handleGetAllUser() {
        List<User> users = this.userRepository.findAll();
        return users;
    }

}
