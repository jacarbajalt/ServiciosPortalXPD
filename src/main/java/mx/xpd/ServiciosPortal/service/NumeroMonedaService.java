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

    public String convertirMoneda(BigDecimal cantidad) {
    	cantidad = cantidad.setScale(2, RoundingMode.HALF_UP);
        long parteEntera = cantidad.longValue();
        int centavos = cantidad.remainder(BigDecimal.ONE).movePointRight(2).intValue();
        String texto = numeroALetrasService.convertir(parteEntera).toUpperCase();

        //if (parteEntera == 1) 
            //texto = "un";
        
        //return String.format("%s %s %02d/100 M.N.", texto, parteEntera == 1 ? "peso" : "pesos", centavos);
        return String.format("%s %s %02d/100 M.N.", texto, "pesos", centavos);
    }
}
