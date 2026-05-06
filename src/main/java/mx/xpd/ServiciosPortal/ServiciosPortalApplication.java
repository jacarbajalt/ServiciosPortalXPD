package mx.xpd.ServiciosPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ServiciosPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciosPortalApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ready() {
		System.out.println(">>> Aplicación lista y funcionando <<<");
	}
}
