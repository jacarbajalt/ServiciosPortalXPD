package mx.xpd.ServiciosPortal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
/**
*
* Servicio puente para la transformacion de numeros a letras
* 
* @author  Juan Antonio <desarrollo19@xpd.mx>
* @version 3.3 y 4.0
*
*/

@Service
public class NumeroMonedaService {
	private final NumeroALetrasService numeroALetrasService;

    public NumeroMonedaService(NumeroALetrasService numeroALetrasService) {
        this.numeroALetrasService = numeroALetrasService;
    }

    public String convertirMoneda(BigDecimal cantidad, String moneda) {
    	cantidad = cantidad.setScale(2, RoundingMode.HALF_UP);
        long parteEntera = cantidad.longValue();
        int centavos = cantidad.remainder(BigDecimal.ONE).movePointRight(2).intValue();
        String texto = numeroALetrasService.convertir(parteEntera).toUpperCase();
        String nombreMoneda = "MXN".equals(moneda) ? "pesos" : moneda;
        String abreviacionMoneda = "MXN".equals(moneda) ? "M.N." : moneda;
        
        return String.format("%s %s %02d/100 %s", texto, nombreMoneda, centavos, abreviacionMoneda);
    }
}
