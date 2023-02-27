package taco.cloud.tacocloud.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import taco.cloud.tacocloud.Order;
import taco.cloud.tacocloud.User;
import taco.cloud.tacocloud.data.OrderRepository;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    private OrderProps props;

    public OrderController(OrderRepository orderRepo, OrderProps props) {
        this.orderRepo = orderRepo;
        this.props = props;
    }
    
    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
        @ModelAttribute Order order) {
            if (order.getName() == null) {
                order.setName(user.getFullname());
              }
              if (order.getStreet() == null) {
                order.setStreet(user.getStreet());
              }
              if (order.getCity() == null) {
                order.setCity(user.getCity());
              }
              if (order.getState() == null) {
                order.setState(user.getState());
              }
              if (order.getZip() == null) {
                order.setZip(user.getZip());
              }
        
              return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, 
        SessionStatus sessionStatus,
        @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        
        order.setUser(user);
        
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    // @GetMapping
    // public String ordersForUser(
    //     @AuthenticationPrincipal User user, Model model) {

    //    model.addAttribute("orders", 
    //       orderRepo.findByUserOrderByPlacedAtDesc(user));

    //       return "orderList";
    // }

    @GetMapping
    public String ordersForUser (
      @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0,  props.getPageSize());
        model.addAttribute("orders", 
            orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
      }

}
