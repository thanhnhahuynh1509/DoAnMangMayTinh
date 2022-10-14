package com.htn.mmt.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class DESAlgorithm {
    private static final DESAlgorithm desAlgorithm = new DESAlgorithm();
    private static final String SECRET_KEY = "12345678";
    private static Cipher eCipher;
    private static Cipher dCipher;

    private DESAlgorithm() {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");

            eCipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            dCipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");

            eCipher.init(Cipher.ENCRYPT_MODE, key);
            dCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static DESAlgorithm getInstance() {
        return  desAlgorithm;
    }

    public String encrypt(String txt) {
        try {
            byte[] utf8Text = txt.getBytes(StandardCharsets.UTF_8);
            byte[] encryptText = eCipher.doFinal(utf8Text);
            return Base64.getEncoder().encodeToString(encryptText);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String txt) {
        try {
            byte[] textDecrypt = Base64.getDecoder().decode(txt);
            byte[] byteDecrypt = dCipher.doFinal(textDecrypt);
            return new String(byteDecrypt);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
