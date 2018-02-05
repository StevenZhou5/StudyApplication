package study.zhouzhenwu.com.mydemo.algorithm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import study.zhouzhenwu.com.mydemo.common.activity.BaseActivity;

/**
 * Created by Steven on 2018/1/11.
 */

public class AESTestActivity extends BaseActivity {
    private static final String KEY = "PqAzsFXl%}Mha:h-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Log.d( "ZZW", "resultBASE64：" + Base64.encodeToString( "-2415676411514173011".getBytes(), 0 ));

//        String result = encrypt( KEY, "-2415676411514173011" );
//        Log.d( "ZZW", "result：" + result );
//        String reseltB = Base64.encodeToString( result.getBytes(), 0 );
//        Log.d( "ZZW", "resultBASE64：" + reseltB );
    }


    /*
     * 加密
     */
    public static String encrypt(String key, String cleartext) {
        if (TextUtils.isEmpty( cleartext )) {
            return cleartext;
        }
        try {
            byte[] result = encrypt( key, cleartext.getBytes() );
            for (byte b : result) {
                Log.d( "ZZW", "resultB：" + (int) b );
            }
            return new String( result, "UTF-8" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 加密
    */
//    private static byte[] encrypt(String key, byte[] clear) throws Exception {
//        byte[] raw = getRawKey( key.getBytes() );
//        SecretKeySpec skeySpec = new SecretKeySpec( raw, AES );
//        Cipher cipher = Cipher.getInstance( CBC_PKCS5_PADDING );
//        cipher.init( Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec( new byte[cipher.getBlockSize()] ) );
//        byte[] encrypted = cipher.doFinal( clear );
//        return encrypted;
//    }

    private static byte[] encrypt(String key, byte[] src) throws Exception {
        Cipher cipher = Cipher.getInstance( CBC_PKCS5_PADDING );
        SecretKeySpec securekey = new SecretKeySpec( key.getBytes(), AES );
        cipher.init( Cipher.ENCRYPT_MODE, securekey, new IvParameterSpec( key.getBytes() ) );
        return cipher.doFinal( src );
    }

    private final static String HEX = "0123456789ABCDEF";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String AES = "AES";//AES 加密
    private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    // 对密钥进行处理
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance( AES );
        //for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance( SHA1PRNG, "Crypto" );
        } else {
            sr = SecureRandom.getInstance( SHA1PRNG );
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed( seed );
        kgen.init( 128, sr ); //256 bits or 128 bits,192bits
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
}
