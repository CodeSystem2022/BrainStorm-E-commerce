package ecommerce.donatto.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.Product;
import ecommerce.donatto.service.IOrderService;
import ecommerce.donatto.service.IUserService;
import ecommerce.donatto.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    private Logger logg = LoggerFactory.getLogger(AdminController.class); //Para imprimir por consola

    @GetMapping("")
    public String home(Model model){

        List<Product> products=productService.findAll();
        model.addAttribute("products", products);

        return "administrador/home";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("orders")
    public String orders(Model model) {
        model.addAttribute("ordenes", orderService.findAll());
        return "administrador/orders";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        logg.info("Id de la orde: {}",id);
        Order order = orderService.findById(id).get();

        model.addAttribute("detail", order.getDetail());

        return "administrador/detalleorden";
    }

}
