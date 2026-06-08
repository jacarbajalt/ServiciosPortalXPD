package mx.xpd.ServiciosPortal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Aplicación ServiciosPortal-Timbrado funcionando correctamente con Java 25 y WildFly 40";
    }
}