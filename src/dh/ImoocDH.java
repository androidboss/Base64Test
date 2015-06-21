package dh;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * Created by androidboss on 2015/3/14.
 */
public class ImoocDH {

    public static void main(String[] args) {
        jdkDH();
    }

    private static String src = "imooc security dh";

    public static void jdkDH() {
        try {
            // 1.初始化发送方的密钥：
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");// 可以设置Provider
            senderKeyPairGenerator.initialize(512);
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            // 发送方的公钥，发送给接收方(网络，文件，U盘等方式传送)
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

            // 2.初始化接收方的密钥,实际上1和2是分开的，两个不同的地方的，现在只是为了演示放到了一块
            KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);// 使用了发送方的公钥
            PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
            // 接收方要使用和发送方一样的public key 所以需要一样的参数来初始化KeyPair
            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);// 从发送方的公钥解析出来的参数
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivatekey = receiverKeyPair.getPrivate();
            byte[] receiverPulicKeyEnc = receiverKeyPair.getPublic().getEncoded();

            // 3.密钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivatekey);// 使用接收方私钥初始化
            receiverKeyAgreement.doPhase(receiverPublicKey, true);
            // 生成接收方密钥
            SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPulicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey, true);
            // 生成发送方密钥
            SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");

            if (Objects.equals(receiverDesKey, senderDesKey)) {
                System.out.println("双方密钥相同");
            }

            // 4.加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk dh encrypt : " + Base64.encodeBase64String(result));

            // 5.解密
            cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
            result = cipher.doFinal(result);
            System.out.println("jdk dh decrypt : " + new String(result));


        } catch (Exception e) {// 用于演示，实际是NoSuchAlgorithmException
            e.printStackTrace();
        }
    }
}
