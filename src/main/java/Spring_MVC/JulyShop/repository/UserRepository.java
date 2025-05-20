package Spring_MVC.JulyShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Spring_MVC.JulyShop.doamin.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
