package criptografia;

// Metodos Usado --> http://juanlopez.com.ar/encriptar-informacion-con-java/
// Info --> https://es.stackoverflow.com/questions/209193/encriptaci%C3%B3n-de-datos-en-java-con-javax-crypto
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.codec.binary.Base64.decodeBase64;

public class EncriptDescript{
	// Definición del tipo de algoritmo a utilizar (AES, DES, RSA)
    private final static String alg = "AES";
    // Definición del modo de cifrado a utilizar
    private final static String cI = "AES/CBC/PKCS5Padding";
    // KEY
    private final static String key = "6AS5D4C5R5D5E6D6";
    // vector de inicialización usado en iv
    private final static String iv = "0123456789ABCDEF";

    /* Función que encripta la contraseña dada */
	public static String encrypt(String cleartext) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return new String(encodeBase64(encrypted));
    }
	
	/* Función que desencripta la contraseña encriptada dada */
	public static String decrypt(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(cI);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        byte[] enc = decodeBase64(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }
	
}
