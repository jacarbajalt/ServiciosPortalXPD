package mx.xpd.ServiciosPortal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class NumeroMonedaInglesService {
	private final NumeroALetraInglesService numeroALetraInglesService;

	public NumeroMonedaInglesService(NumeroALetraInglesService numeroALetraInglesService) {
        this.numeroALetraInglesService = numeroALetraInglesService;
    }
	
	public String convertirMoneda(BigDecimal cantidad, String moneda) {
        cantidad = cantidad.setScale(2, RoundingMode.HALF_UP);
        long parteEntera = cantidad.longValue();
        int centavos = cantidad.remainder(BigDecimal.ONE).movePointRight(2).intValue();
        String texto = numeroALetraInglesService.convertir(parteEntera).toUpperCase();

        String monedaSingular;
        String monedaPlural;
        String monedaTexto = "";
        if (moneda != null && !moneda.isBlank()) {
        	switch (moneda.toUpperCase()) {
	            case "USD":
	                monedaSingular = "DOLLAR";
	                monedaPlural = "DOLLARS";
	                break;
	
	            case "MXN":
	            default:
	                monedaSingular = "PESO";
	                monedaPlural = "PESOS";
	                break;
	        }
	
	        monedaTexto = (parteEntera == 1) ? monedaSingular : monedaPlural;
        }

        return String.format("%s %s %02d/100", texto, monedaTexto, centavos);
    }
}
