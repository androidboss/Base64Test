import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by androidboss on 2015/3/3.
 */
public class Main {
    public static String mSrc = "imooc security base64";

    public static void jdkBase64() {
        BASE64Encoder encoder = new BASE64Encoder();
        String result = encoder.encode(mSrc.getBytes());
        System.out.println(result);

        BASE64Decoder decoder = new BASE64Decoder();
        String rlt = null;
        try {
            rlt = new String(decoder.decodeBuffer(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(rlt);
    }

    public static void commonsCodesBase64() {
        byte[] result = Base64.encodeBase64(mSrc.getBytes());
        System.out.println("encode:" + new String(result));

        byte[] rlt = Base64.decodeBase64(result);
        System.out.println("decode:" + new String(rlt));
    }

    public static void bouncyCastleBase64() {
        byte[] result = org.bouncycastle.util.encoders.Base64.encode(mSrc.getBytes());
        System.out.println("encode:" + new String(result));

        byte[] rlt = org.bouncycastle.util.encoders.Base64.decode(result);
        System.out.println("decode:" + new String(rlt));
    }

    public static void main(String[] args) {
//        jdkBase64();
//        commonsCodesBase64();
        bouncyCastleBase64();
    }
}
