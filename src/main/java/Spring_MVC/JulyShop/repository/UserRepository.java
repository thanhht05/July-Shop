package Spring_MVC.JulyShop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import Spring_MVC.JulyShop.doamin.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
