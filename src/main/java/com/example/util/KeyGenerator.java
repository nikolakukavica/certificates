package com.example.util;

import java.io.*;
import java.security.*;

/**
 * Created by Bender on 5/29/2017.
 */
public class KeyGenerator {

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] serialize(KeyPair keyPair){
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(keyPair);
            byte[] keyPairBytes = b.toByteArray();
            o.close();
            b.close();
            return keyPairBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair deserialize(byte[] keyPairBytes){

        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(keyPairBytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            KeyPair keyPair = (KeyPair) oi.readObject();

            oi.close();
            bi.close();

            return keyPair;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        KeyPair gen = generateKeyPair();
        byte[] bytes = serialize(gen);
        KeyPair ret = deserialize(bytes);
        System.out.println(gen.getPrivate().equals(ret.getPrivate()));
        System.out.println(gen.getPublic().equals(ret.getPublic()));
        System.out.println(gen.equals(ret));
    }

}
