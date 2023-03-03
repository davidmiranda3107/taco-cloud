package taco.cloud.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import taco.cloud.tacocloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    
    Page<Taco> findAll(Pageable pageable);
}
