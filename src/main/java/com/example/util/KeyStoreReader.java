package com.example.util;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class KeyStoreReader {

	private KeyStore keyStore;
	private static KeyStoreReader instance = null;

	private KeyStoreReader() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyStoreReader getInstance(){
		if(instance == null)
			instance = new KeyStoreReader();
		return instance;
	}


    public void listContent(String keyStoreFile, char[] password){

        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            Enumeration<String> aliases = keyStore.aliases();

            if(!aliases.hasMoreElements()){
                System.out.println("Selected keystore is empty.");
            }else{
                System.out.println("Selected keystore contains following entries: ");
                while(aliases.hasMoreElements())
                    System.out.println(aliases.nextElement());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }




    }

    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if(ks.isKeyEntry(alias)) {
				Certificate cert = ks.getCertificate(alias);
				return cert;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {

			KeyStore ks = KeyStore.getInstance("JKS", "SUN");

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if(ks.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
