package Spring_MVC.JulyShop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Spring_MVC.JulyShop.doamin.Role;
import Spring_MVC.JulyShop.doamin.User;
import Spring_MVC.JulyShop.doamin.dto.RegisterDTO;
import Spring_MVC.JulyShop.repository.RoleRepository;
import Spring_MVC.JulyShop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository repository;

    public UserService(UserRepository userRepository, RoleRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    public void handleSaveUser(User user) {
        this.userRepository.save(user);
    }

    public Page<User> handleGetAllUser(Pageable pageable) {
        Page<User> users = this.userRepository.findAll(pageable);
        return users;
    }

    public User getUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            User userReal = user.get();
            return userReal;
        }

        return null;
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public Role findRoleByName(String roleName) {
        Role role = this.repository.findByName(roleName);

        return role;
    }

    public User registerDTOToUser(RegisterDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFirstName() + " " + userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public boolean checkExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
