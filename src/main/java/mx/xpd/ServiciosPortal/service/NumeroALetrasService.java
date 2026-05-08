package mx.xpd.ServiciosPortal.service;

import org.springframework.stereotype.Service;

@Service
public class NumeroALetrasService {

    private static final String[] UNIDADES = {
            "", "uno", "dos", "tres", "cuatro", "cinco", "seis",
            "siete", "ocho", "nueve", "diez", "once", "doce",
            "trece", "catorce", "quince", "dieciséis", "diecisiete",
            "dieciocho", "diecinueve", "veinte"
    };

    private static final String[] DECENAS = {
            "", "", "veinte", "treinta", "cuarenta",
            "cincuenta", "sesenta", "setenta",
            "ochenta", "noventa"
    };

    private static final String[] CENTENAS = {
            "", "ciento", "doscientos", "trescientos",
            "cuatrocientos", "quinientos", "seiscientos",
            "setecientos", "ochocientos", "novecientos"
    };

    public String convertir(long numero) {
        if (numero == 0) return "cero";
        if (numero < 0) return "menos " + convertir(-numero);
        return convertirNumero(numero).trim();
    }

    private String convertirNumero(long numero) {
        if (numero <= 20) return UNIDADES[(int) numero];

        if (numero < 100) {
            int decena = (int) (numero / 10);
            int unidad = (int) (numero % 10);

            if (numero <= 29) return "veinti" + UNIDADES[unidad];
            
            return DECENAS[decena] + (unidad > 0 ? " y " + UNIDADES[unidad] : "");
        }

        if (numero < 1000) {
            if (numero == 100) return "cien";

            int centena = (int) (numero / 100);
            long resto = numero % 100;

            return CENTENAS[centena] + (resto > 0 ? " " + convertirNumero(resto) : "");
        }

        if (numero < 1000000) {
            long miles = numero / 1000;
            long resto = numero % 1000;

            String milesTexto = (miles == 1) ? "mil" : convertirNumero(miles) + " mil";
            return milesTexto + (resto > 0 ? " " + convertirNumero(resto) : "");
        }

        long millones = numero / 1000000;
        long resto = numero % 1000000;

        String millonesTexto = (millones == 1) ? "un millón" : convertirNumero(millones) + " millones";
        return millonesTexto + (resto > 0 ? " " + convertirNumero(resto) : "");
    }
}