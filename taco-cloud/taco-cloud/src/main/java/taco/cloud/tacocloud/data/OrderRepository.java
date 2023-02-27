package taco.cloud.tacocloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

import taco.cloud.tacocloud.Order;
import taco.cloud.tacocloud.User;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
   // Order save(Order order);

   List<Order> findByZip(String deliveryZip);

   List<Order> readOrdersByZipAndPlacedAtBetween(
        String deliveryZip, Date startDate, Date endDate);

   List<Order> findByNameAndCityAllIgnoreCase(
       String deliveryTo, String deliveryCity);
   
   List<Order> findByCityOrderByName(String city);

   @Query("select o from Order o where o.city='Seattle'")
   List<Order> readOrdersDeliveredInSeattle();

   List<Order> findByUserOrderByPlacedAtDesc(User user);

   List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
