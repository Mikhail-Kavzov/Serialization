package com.pack.digest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class PlaginSignature {

    private final static String signAlgorithm="SHA256WithDSA";
    private static byte[] fileData(String filePath) throws IOException {
        FileInputStream fin = new FileInputStream(filePath);
        byte[] data=fin.readAllBytes();
        fin.close();
        return data;
    }
    private static void saveKey( String filePath,Object key)
            throws IOException
    {
        if (key != null){
            FileOutputStream fos = new FileOutputStream(filePath,true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);
            oos.close();
            fos.close();

        }
    }

    private static void saveDigest(byte[] data,String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath,true);
        fos.write(data);
        fos.close();
    }
    public static boolean signPlagin(String filePath)  {

        try {

            byte[] data =fileData(filePath);

            Signature signature = Signature.getInstance(signAlgorithm);
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            signature.initSign(keyPair.getPrivate(), secureRandom);
            PublicKey publicKey=keyPair.getPublic();

            signature.update(data);

            byte[] digitalSignature = signature.sign();

            saveDigest(digitalSignature,filePath);
            saveKey(filePath,publicKey);

        } catch (Exception e) {
          return false;
        }
        return true;
    }

    public static boolean checkDigestPlagin()
    {   boolean verified=false;

        try {
           Signature signature = Signature.getInstance(signAlgorithm);

        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return verified;


    }
}
