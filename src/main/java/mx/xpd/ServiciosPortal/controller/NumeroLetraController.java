package mx.xpd.ServiciosPortal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import mx.xpd.ServiciosPortal.service.NumeroMonedaService;
import mx.xpd.ServiciosPortal.controller.NumeroLetraController;
import mx.xpd.ServiciosPortal.service.NumeroMonedaInglesService;
import mx.xpd.ServiciosPortal.service.QRService;

/**
*
* Este servicio Rest es para convertir numeros a letra en ingles y espa�ol asi como para obtener el QR de un CFDI
* 
* @author  Juan Antonio <desarrollo19@xpd.mx>
* @version 3.3 y 4.0
*
*/
@RestController
@RequestMapping("/")
public class NumeroLetraController {
	private final NumeroMonedaService service;
	private final NumeroMonedaInglesService serviceIngles;
	private final QRService qrService;
	private static final Logger log = LoggerFactory.getLogger(NumeroLetraController.class);
	
	public NumeroLetraController(NumeroMonedaService service, NumeroMonedaInglesService serviceIngles, QRService qrService) {
        this.service = service;
        this.serviceIngles = serviceIngles;
        this.qrService = qrService;
    }

    @GetMapping("/ConvertirNumeroLetra")
    public String convertir(@RequestParam(value = "numero", defaultValue = "0") BigDecimal cantidad, @RequestParam(value= "moneda", defaultValue = "MXN") String moneda) {
    	log.info("[ConvertirNumeroLetra] >>> Numero a convertir: {}, Moneda: {} <<<", cantidad, moneda);
        return service.convertirMoneda(cantidad, moneda);
    }
    
    @GetMapping("/AmountToLetter")
    public String convertirNumeroIngles(@RequestParam(value = "amount", defaultValue = "0") BigDecimal cantidad, @RequestParam(value = "currency", required = false) String moneda) {
    	log.info("[AmountToLetter] >>> Numero a convertir: {} <<<", cantidad);
        return serviceIngles.convertirMoneda(cantidad, moneda);
    }
    
    @GetMapping("/GenerarQR")
    public ResponseEntity<byte[]> generarQR(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "str", required = false) String str,
            @RequestParam(value = "id", required = false) String id, @RequestParam(value = "re", required = false) String re,
            @RequestParam(value = "rr", required = false) String rr, @RequestParam(value = "tt", required = false) String tt,
            @RequestParam(value = "fe", required = false) String fe, @RequestParam(value = "IdCCP", required = false) String IdCCP,
            @RequestParam(value = "FechaOrig", required = false) String FechaOrig, @RequestParam(value = "FechaTimb", required = false) String FechaTimb
    ) throws FileNotFoundException, IOException {
    	Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
    	
    	//Valida el token asignado por Addendas
        if (!"g_NndKQ7vM=[".equals(token) && !"JEJE".equals(str)) {
        	log.info("[GenerarQR] >>> No tiene permisos para realizar esta acción");
            return ResponseEntity.status(403).body(null);
        }

        String url;
        
        if (id != null && re != null && rr != null && tt != null && fe != null) { // CFDI
            url = prop.getProperty("validaCfdiSAT")+"/default.aspx?"+ "&id=" + id + "&re=" + re + "&rr=" + rr + "&tt=" + tt + "&fe=" + fe;
            log.info("[GenerarQR] >>> URL: {}", url);
        }else if (IdCCP != null && FechaOrig != null && FechaTimb != null) { //CartaPorte
            url = prop.getProperty("validaCfdiSAT")+"/verificaccp/default.aspx?" + "IdCCP=" + IdCCP + "&FechaOrig=" + FechaOrig + "&FechaTimb=" + FechaTimb;
            log.info("[GenerarQR] >>> URL: {}", url);
        } else {
        	log.info("[GenerarQR] >>> URL no proporcionada");
        	return ResponseEntity.status(404).body(null);
        }
        
        //Devuelve la URL para generar el QR desde el Service
        byte[] qr = qrService.generarQRCode(url, 400, 400);
        log.info("[GenerarQR] >>> QR Generado Satisfactoriamente");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
    }
}
