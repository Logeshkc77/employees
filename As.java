
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.*;
import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

// public class As {

// 	private static final String RSA= "RSA";
// 	private static Scanner sc;

// 	private static KeyPair keypair ;

// 	public static void generateRSAKkeyPair()throws Exception
// 	{
// 		SecureRandom secureRandom = new SecureRandom();
// 		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
// 		keyPairGenerator.initialize( 512, secureRandom);
// 		keypair = keyPairGenerator.generateKeyPair(); 
// 	}

// 	public static byte[] rsaEncryption(String plainText)throws Exception
// 	{
// 		PrivateKey privateKey= keypair.getPrivate();
// 		Cipher cipher= Cipher.getInstance(RSA);
// 		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
// 		return cipher.doFinal(plainText.getBytes());
// 	}

// 	public static String rsaDecryption(byte[] cipherText)throws Exception
// 	{
// 		PublicKey publicKey = keypair.getPublic();
// 		Cipher cipher = Cipher.getInstance(RSA);
// 		cipher.init(Cipher.DECRYPT_MODE,publicKey);
// 		byte[] result = cipher.doFinal(cipherText);
// 		return new String(result);
// 	}

// 	public static void main(String args[])throws Exception
// 	{
// 		 // = generateRSAKkeyPair();
// 		Scanner getInput = new Scanner(System.in);
// 		generateRSAKkeyPair();
// 		while(true){
// 			System.out.println("\nEnter Password:");
// 			String input = getInput.next();

// 			byte[] encryptedText= rsaEncryption(input);
// 			System.out.println("The encrypted text is: "+ encryptedText);

// 			String decoded = new String(encryptedText, "ISO-8859-1");
//     		System.out.println("decoded:" + decoded);

// 			// String encodedString = Base64.getEncoder().encodeToString(encryptedText);
// 	    	// System.out.print("encodedString : ");
// 	    	// System.out.println(encodedString);
// 	    	// byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
// 	    	// System.out.print("decodedBytes : ");
// 	    	// System.out.println(decodedBytes);

			
// 			System.out.println(new String(encryptedText));

// 			// System.out.println(encryptedText.getString());


// 			String decryptedText = rsaDecryption(encryptedText);

// 			System.out.println("The decrypted text is: "+ decryptedText);
// 		}
// 	}
// }

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class As {

    // private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    // private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";

 //    KeyPair keypair ;

 //    As()throws Exception{
 //      SecureRandom secureRandom = new SecureRandom();
 //      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
 //      keyPairGenerator.initialize( 512, secureRandom);
 //      keypair = keyPairGenerator.generateKeyPair();
 //   }

	// private PrivateKey getPrivateKey(){
 //      return keypair.getPrivate();
 //   }
 //   private PublicKey getPublicKey(){
 //      return keypair.getPublic();
 //   }

 //    public String encrypt(String password) throws Exception{
        
 //        PublicKey publicKey = getPublicKey();
 //        Cipher cipher = Cipher.getInstance("RSA");
 //        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
 //        byte[] encryptedbyte = cipher.doFinal(password.getBytes());
 //        return Base64.getEncoder().encodeToString(encryptedbyte);
 //    }

 //    public String decrypt(String password) throws Exception{
 //    	byte passwordByte[]=Base64.getDecoder().decode(password.getBytes());
 //        PrivateKey privateKey=getPrivateKey();
 //        Cipher cipher = Cipher.getInstance("RSA");
 //        cipher.init(Cipher.DECRYPT_MODE, privateKey);
 //        String decryptedString = new String(cipher.doFinal(passwordByte));
 //        return decryptedString;
        
 //    }

 //    // public String decrypt(String data) throws Exception{
 //    //     return decrypt(Base64.getDecoder().decode(data.getBytes()));
 //    // }

    public static void main(String[] args) throws Exception{
        As as=new As();
        Scanner getInput = new Scanner(System.in);
        try {
        	String encryptedString[]=new String[5];
        	encryptedString[0] = "25ybAuZb8M/NWVPejHIUiEGO2Tg/KPSrEf6GwH+Xhe4CgR++o3FwQMQ/sJS+XD1jqmSie+m6LG1uzlUPSmM1nA==";
        	for(int i=1;i<5;i++){
        		String input=getInput.next();
        		encryptedString[i]=as.encrypt(input);
        		System.out.println(encryptedString[i]);
        	}
            // String encryptedString = as.encrypt("Dhiraj is the author");
            // System.out.println("sd\n"+encryptedString);
            // System.out.println(encryptedString.length());
            for(int i=0;i<5;i++){
        		System.out.println(as.decrypt(encryptedString[i]));
        	}
            // String decryptedString = as.decrypt(encryptedString);
            // System.out.println("sd\n"+decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}