package Spring_MVC.JulyShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Spring_MVC.JulyShop.doamin.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
