import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.*;
import java.io.*;
import java.nio.*;
import java.security.*;
import java.security.spec.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class RSAAlgorithm {
    private static KeyPair keypair;

    public void keyPairGenerator()throws Exception{
      SecureRandom secureRandom = new SecureRandom();
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize( 512, secureRandom);
      keypair = keyPairGenerator.generateKeyPair();
    }
    public static KeyPair getKeyPair(){
        System.out.println(keypair);
        return keypair;
    }
    private PrivateKey getPrivateKey(){
      return keypair.getPrivate();
    }
    private PublicKey getPublicKey(){
        return keypair.getPublic();
    }
   // public void PrivateKeyReader {

    public static PrivateKey PrivateKeyReader()throws Exception {

        String filename = "Private.key";
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keypair = KeyFactory.getInstance("RSA");
        return keypair.generatePrivate(spec);
    }

    public static PublicKey PublicKeyReader()throws Exception {

        String filename = "Public.key";
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keypair = KeyFactory.getInstance("RSA");
        return keypair.generatePublic(spec);
    }

   //  public String encrypt(String password) throws Exception{
        
   //      PublicKey publicKey = getPublicKey();
   //      Cipher cipher = Cipher.getInstance("RSA");
   //      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
   //      byte[] encryptedbyte = cipher.doFinal(password.getBytes());
   //      return Base64.getEncoder().encodeToString(encryptedbyte);
   //  }

   //  public String decrypt(String password) throws Exception{
   //      byte passwordByte[]=Base64.getDecoder().decode(password.getBytes());
   //      PrivateKey privateKey=getPrivateKey();
   //      Cipher cipher = Cipher.getInstance("RSA");
   //      cipher.init(Cipher.DECRYPT_MODE, privateKey);
   //      String decryptedString = new String(cipher.doFinal(passwordByte));
   //      return decryptedString;
        
   //  }

    public static void main(String[] args) throws Exception{
        RSAAlgorithm rsaAlgorithm=new RSAAlgorithm();
        rsaAlgorithm.keyPairGenerator();
        // try (FileOutputStream out = new FileOutputStream("Private.key")) {
        //     out.write(rsaAlgorithm.getPrivateKey().getEncoded());
        // }

        // try (FileOutputStream out = new FileOutputStream("Public.key")) {
        //     out.write(rsaAlgorithm.getPublicKey().getEncoded());
        // }

        System.out.println(rsaAlgorithm.PublicKeyReader());
        System.out.println(rsaAlgorithm.PrivateKeyReader());
    }    
}