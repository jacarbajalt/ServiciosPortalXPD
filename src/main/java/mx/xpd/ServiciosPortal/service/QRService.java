package mx.xpd.ServiciosPortal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import java.io.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
public class QRService {
	public byte[] generarQRCode(String contenido, int width, int height) {
		try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el codigo QR", e);
        }
	}
}
