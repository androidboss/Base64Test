package aes;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Created by androidboss on 2015/3/7.
 */
public class ImoocAES {

    public static void main(String[] args) {
        jdkAES();
        bcAES();
    }

    private static String mSrc = "imooc security aes";

    public static void jdkAES() {

        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128);// 生成KEY的长度
            keyGenerator.init(new SecureRandom());// 可以生成默认的长度
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(mSrc.getBytes());
            System.out.println("jdk aes encrypt : " + Base64.encodeBase64String(result));


            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcAES() {

        try {
            Security.addProvider(new BouncyCastleProvider());

            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "BC");
//            keyGenerator.getProvider();// 为了debug可以看出来Provider设置成功与否
            keyGenerator.init(128);// 生成KEY的长度
            // java.security.InvalidKeyException: Illegal key size or default parameters
            // 看来这个不行了
//            keyGenerator.init(new SecureRandom());// 可以生成默认的长度

            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // KEY转换
            Key key = new SecretKeySpec(keyBytes, "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(mSrc.getBytes());
            System.out.println("jdk aes encrypt : " + Base64.encodeBase64String(result));


            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
