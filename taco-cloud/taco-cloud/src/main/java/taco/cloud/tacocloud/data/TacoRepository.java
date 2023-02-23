package taco.cloud.tacocloud.data;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.tacocloud.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    
    //Taco save(Taco design);
}
