package taco.cloud.security.user;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.security.core.userdetails.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    User findByUsername(String username);
}
