package des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * Created by androidboss on 2015/3/7.
 */
public class ImoocDES {

    private static String mSrc = "imooc security des";

    /**
     * JDK 自带的DES加解密API使用
     */
    public static void jdkDES() {

        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            // 指定key的size
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            // KEY的转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            // 一般用接口来表示
//            SecretKey convertSecretKey = factory.generateSecret(desKeySpec);
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");// 加解密算法/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(mSrc.getBytes());
            System.out.println("jdk des encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk des decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * bc的DES加解密API使用
     * org.bouncycastle
     */
    public static void bcDES() {

        try {
            ////////////////////////////////////////////////////////////
            // 方法一，通过addProvider的方式，比较好的方式
            Security.addProvider(new BouncyCastleProvider());
            ///////////////////////////////////////////////////////////
            // 方法二,可以替换下面的这个bc包里的类，比较麻烦，不如直接使用这写jdk里面的类

            // 生成KEY
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");// 这个情况就是SunJCE
//            keyGenerator.getProvider();// SunJCE
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", "BC");// 指定BC
            keyGenerator.getProvider();// BouncyCastleProvider
            // 指定key的size
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            // KEY的转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            // 一般用接口来表示
//            SecretKey convertSecretKey = factory.generateSecret(desKeySpec);
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");// 加解密算法/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(mSrc.getBytes());
            System.out.println("bc des encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc des decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }
}
