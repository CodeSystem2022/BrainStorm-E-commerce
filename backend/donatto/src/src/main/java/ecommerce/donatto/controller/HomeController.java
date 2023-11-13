package ecommerce.donatto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.OrderDetail;
import ecommerce.donatto.model.Product;
import ecommerce.donatto.model.User;
import ecommerce.donatto.service.IOrderDetailService;
import ecommerce.donatto.service.IOrderService;
import ecommerce.donatto.service.IUserService;
import ecommerce.donatto.service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    //Para almacenar los detallesde la orden
    List<OrderDetail> detail = new ArrayList<OrderDetail>();
    
    //Datos de la orden
    Order order = new Order();

    @GetMapping("")
    public String home(Model model, HttpSession session) {

        //
        log.info("sesion del usuario: {}", session.getAttribute("iduser")); //Llamamos el metodo de user control para mantener la sesion
        
        model.addAttribute("products", productService.findAll());

        //session "parametro para que inicie la viste", get.."agregamos si esta la vista del user" 
        model.addAttribute("sesion", session.getAttribute("iduser"));

        return "user/home";
    }

    @GetMapping("producthome/{id}")
    public String productHome(@PathVariable Integer id, Model model) {
        log.info("id enviado como parametro {}",id);
        Product product = new Product();
        Optional<Product> productOptional = productService.get(id);
        product = productOptional.get();

        model.addAttribute("product", product);

        return "user/producthome";
    }

    @PostMapping("/cart") 
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {//entre (argumentos) 
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();
        double sumaTotal = 0;

        Optional<Product> optionalProduct = productService.get(id);
        log.info("Producto añadido {}", optionalProduct.get());
        log.info("cantidad: {}", cantidad);
        product=optionalProduct.get();

        orderDetail.setCantidad(cantidad);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setName(product.getName());
        orderDetail.setTotal(product.getPrice()*cantidad);
        orderDetail.setProduct(product);

        //validacion para que no se agregue un prod mas de una vez
         Integer idProduct=product.getId();
         boolean ingresado=detail.stream().anyMatch(p -> p.getProduct().getId()==idProduct);//anyMatch busca coincidencias

         if (!ingresado) {
            detail.add(orderDetail);
         }

        sumaTotal=detail.stream().mapToDouble(dt->dt.getTotal()).sum();

        order.setTotal(sumaTotal);
        model.addAttribute("cart", detail);
        model.addAttribute("order", order);

        return "user/cart";
    }

    //Quitar producto
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id, Model model) {
        //Lista nueva de detalles
        List<OrderDetail> ordersNew = new ArrayList<OrderDetail>();

        for(OrderDetail orderDetail: detail) {
            if (orderDetail.getProduct().getId()!=id) {
                ordersNew.add(orderDetail);
            }
        }
        //mostrar lista actualizada con los prod restantes
        detail=ordersNew;

        double sumaTotal=0;
        sumaTotal=detail.stream().mapToDouble(dt->dt.getTotal()).sum();

        order.setTotal(sumaTotal);
        model.addAttribute("cart", detail);
        model.addAttribute("order", order);

        return "user/cart";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {

        model.addAttribute("cart", detail);
        model.addAttribute("order", order);

        //Sesion
        model.addAttribute("sesion", session.getAttribute("iduser"));
        return "/user/cart";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        //Obtenemos el usuario guardado
        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();

        model.addAttribute("cart", detail);
        model.addAttribute("order", order);
        model.addAttribute("user", user);

        return "user/resumenorder";
    }

    //guardar orden
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        Date fechaCreacion = new Date();
        order.setFechaCreacion(fechaCreacion);
        order.setNumber(orderService.generateOrderNumber());

        //usuario
        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();
        
        order.setUser(user);
        orderService.save(order);

        //guardar detalles
        for (OrderDetail dt:detail) {
            dt.setOrder(order);
            orderDetailService.save(dt);
        }

        //Limpiar lista y orden
        order = new Order();
        detail.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String name, Model model) {
        log.info("Nombre del producto: {}", name);
        List<Product> products = productService.findAll().stream().filter(p -> p.getName().contains(name)).collect(Collectors.toList());
        model.addAttribute("products", products);

        return "user/home";
    }
    
}
