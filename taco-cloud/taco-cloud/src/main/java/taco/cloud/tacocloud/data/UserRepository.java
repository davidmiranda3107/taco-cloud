package taco.cloud.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import taco.cloud.tacocloud.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    User findByUsername(String username);
}
