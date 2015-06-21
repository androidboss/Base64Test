package des;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created by androidboss on 2015/3/7.
 */
public class Imooc3DES {
    public static void main(String[] args) {
        jdk3DES();
    }

    private static String mSrc = "imooc security 3des";


    private static void jdk3DES() {

        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");// 换成三重DES
            // 指定key的size
//            keyGenerator.init(56);
            keyGenerator.init(168);// 换成三重DES, KEY size必须是112或者168位
            // 换成三重DES,或者可以这样也行
            keyGenerator.init(new SecureRandom());// 生成默认长度的KEY
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();

            // KEY的转换
//            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);// 换成三重DES
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");// 换成三重DES
            // 一般用接口来表示
//            SecretKey convertSecretKey = factory.generateSecret(desKeySpec);
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密                                                     // 换成三重DES
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");// 加解密算法/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(mSrc.getBytes());
            System.out.println("jdk 3des encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk 3des decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
