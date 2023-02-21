package taco.cloud.tacocloud.data;

import taco.cloud.tacocloud.Order;

public interface OrderRepository {
    
    Order save(Order order);
}
