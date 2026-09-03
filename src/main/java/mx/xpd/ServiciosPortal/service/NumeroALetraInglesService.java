package mx.xpd.ServiciosPortal.service;

import org.springframework.stereotype.Service;

@Service
public class NumeroALetraInglesService {
	private static final String COMA = ",";
    private static final String MENOS = "-";

    private static final String[] UNIDADES = {
    		"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", 
    		"ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN", 
    		"TWENTY", "TWENTY-ONE", "TWENTY-TWO", "TWENTY-THREE", "TWENTY-FOUR", "TWENTY-FIVE", "TWENTY-SIX", 
    		"TWENTY-SEVEN", "TWENTY-EIGHT", "TWENTY-NINE"
    };
    
    private static final String[] DECENAS = {
    		"", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"
    };
    
    private static final String[] CENTENAS = {
    		"", "ONE HUNDRED", "TWO HUNDRED", "THREE HUNDRED", "FOUR HUNDRED", "FIVE HUNDRED", 
    		"SIX HUNDRED", "SEVEN HUNDRED", "EIGHT HUNDRED", "NINE HUNDRED"
    };
    
    private static final String[] MILLON_SINGULAR = {
    		"", "MILLION", "BILLION", "TRILLION", "QUADILLION", "QUINTILLION", "SEXTILLION", "SEPTILLION", "OCTILLION", "NONILLION", 
    		"DECILLION", "UNDECILLION", "DUODECILLION", "TRIDECILLION", "QUADRIDEDILLION", "QUIDECILLON", "SIX-DECILLION", 
    		"SEPTIDECILLION", "OCTODECILLION", "NONIDECILLION", "VIGILLON"
    };
    
    private static final String[] MILLON_PLURAL = {
    		"", "MILLIONS", "BILLIONS", "TRILLIONS", "QUADILLIONS", "QUINTILLIONS", "SEXTILLIONS", 
    		"SEPTILLIONS", "OCTILLIONS", "NONMILLIONS", "DECILLIONS", "UNDECILLIONS", "DUODECILLIONS",
    		"TRIDECILLIONS", "QUADRIDILLION", "QUIDILLIONS", "SIX-DECILLIONS", "SEPTIDILLIONS", "OCTODECILLIONS", 
    		"NONIDILLIONS", "VIGILLIONS"
    };
    
    public String convertir(long numero) {
        if (numero == 0 || numero <= 0) return "ZERO";
        return convertirNumero(numero).trim();
    }
    
    private String convertirNumero(long numero) {
        if (numero < 30) return UNIDADES[(int) numero];
        
        if (numero < 100) {
            int decena = (int) (numero / 10);
            int unidad = (int) (numero % 10);

            if (unidad == 0) return DECENAS[decena];
            
            return DECENAS[decena] + "-" + UNIDADES[unidad];
        }

        if (numero < 1000) {
            int centena = (int) (numero / 100);
            int resto = (int) (numero % 100);

            if (resto == 0) return CENTENAS[centena];

            return CENTENAS[centena] + " " + convertirNumero(resto);
        }

        if (numero < 1_000_000) {
            long miles = numero / 1000;
            long resto = numero % 1000;

            String resultado;

            if (miles == 1) 
                resultado = "ONE THOUSAND";
            else 
                resultado = convertirNumero(miles) + " THOUSAND";

            if (resto > 0) resultado += " " + convertirNumero(resto);

            return resultado;
        }

        int indiceMillon = 0;
        long divisor = 1;

        while (numero / divisor >= 1_000_000 && indiceMillon < MILLON_SINGULAR.length - 1) {
            divisor *= 1_000_000;
            indiceMillon++;
        }

        long parteAlta = numero / divisor;
        long resto = numero % divisor;

        String nombreMillon;

        if (parteAlta == 1) 
            nombreMillon = MILLON_SINGULAR[indiceMillon];
        else 
            nombreMillon = MILLON_PLURAL[indiceMillon];

        String resultado = convertirNumero(parteAlta) + " " + nombreMillon;

        if (resto > 0) resultado += " " + convertirNumero(resto);

        return resultado;
    }
}
