package ecommerce.donatto.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ecommerce.donatto.model.Order;
import ecommerce.donatto.model.User;
import ecommerce.donatto.service.IOrderService;
import ecommerce.donatto.service.IUserService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    //usuario-registro
    @GetMapping("/register")
    public String create() {
        return "user/registro";
    }
    
    @GetMapping("/contactos")
    public String contacto() {
        return "user/contacto";
    }
    
    @PostMapping("/save")
    public String save(User user) {
        logger.info("Usuario registro: {}", user);
        user.setType("USER");
        userService.save(user);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/inicioSesion";
    }

    @GetMapping("/ayuda")
    public String ayuda() {
        return "user/ayuda";
    }

    @GetMapping("/ofertas")
    public String ofertas() {
        return "user/ofertas";
    }

    @PostMapping("/acceder")
    public String acceder(User user, HttpSession session) { //user user parametro
        logger.info("Accesos : {}", user);//imprimimos por consola

        //pasamos el atributo que es username
        Optional<User> usuario=userService.findByUsername(user.getUsername());
        //logger.info("Usuario obtenido de db: {}", usuario.get());
        
        if (usuario.isPresent()) { //Optional nos permite hacer la validacion de existencia del user
            session.setAttribute("iduser", usuario.get().getId());//Si esta presente añadimo un objeto http session
            //Mantendremos la session mientras exista el usuario
            if (usuario.get().getType().equals("ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            logger.info("Usuario no existe");
        }

        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session) {
        //implementamos la vista para que nos de error en la variacin
        model.addAttribute("sesion", session.getAttribute("iduser"));
        
        User user = userService.findById( Integer.parseInt(session.getAttribute("iduser").toString())).get();
        List<Order> ordenes=orderService.findByUser(user);

        model.addAttribute("ordenes", ordenes);
        
        return "user/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) { //model para enviar vista
        logger.info("Id de la orden: {}", id);
        //Buscar orden
        Optional<Order> order = orderService.findById(id);
        model.addAttribute("detalles", order.get().getDetail());

        //sesion
        model.addAttribute("sesion", session.getAttribute("iduser"));
        return "user/detallecompra";
    }

    

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session) {
        session.removeAttribute("iduser");
        return "redirect:/";
    }

}
