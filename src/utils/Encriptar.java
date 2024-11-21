package src.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encriptar {
    public static SecretKey generarClaveAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    // Método para encriptar la contraseña
    public static String encriptar(String texto, SecretKey clave) throws Exception {
        // Crear un objeto Cipher con el algoritmo AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        byte[] textoEncriptado = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(textoEncriptado);
    }

    // Método para desencriptar la contraseña
    public static String desencriptar(String textoEncriptado, SecretKey clave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, clave);
        byte[] textoEncriptadoBytes = Base64.getDecoder().decode(textoEncriptado);
        byte[] textoDesencriptado = cipher.doFinal(textoEncriptadoBytes);
        return new String(textoDesencriptado);
    }
}
