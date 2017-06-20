package com.example.util;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class KeyStoreWriter {

	private KeyStore keyStore;
	private static KeyStoreWriter instance = null;

	private KeyStoreWriter() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyStoreWriter getInstance(){
		if(instance == null)
			instance = new KeyStoreWriter();
		return instance;
	}

	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null)
				keyStore.load(new FileInputStream(fileName), password);
			else
				keyStore.load(null, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
		} catch (KeyStoreException e) {
			e.printStackTrace();
			PKCS10CertificationRequest req = null;

		}
	}
}
