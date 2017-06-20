package com.example.service;

import com.example.model.CertificateInfo;
import com.example.model.CertificateSigningRequest;
import com.example.model.MyCertificate;
import com.example.model.User;
import com.example.repository.CertificateRepository;
import com.example.repository.CsrRepository;
import com.example.repository.UserRepository;
import com.example.util.CertificateGenerator;
import com.example.util.KeyGenerator;
import com.example.util.KeyStoreReader;
import com.example.util.KeyStoreWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Random;

@Service
public class CertificateService {

    private static final String publicKeyStoreFile = "keystores/public/keystore";
    private static final char[] publicKeyStorePassowrd = "password".toCharArray();

    private KeyPair mockupKeyPair = KeyGenerator.generateKeyPair();

    @Autowired
    UserRepository userRepository;
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    CsrRepository csrRepository;

    CertificateGenerator certificateGenerator = new CertificateGenerator();

    public List<MyCertificate> getAllAuthorities(){
        return certificateRepository.findByIsCa(true);
    }

    public void newSelfSigned(long user_id){
        User user = userRepository.findOne(user_id);
        KeyPair keyPair = KeyGenerator.generateKeyPair();
        Random random = new Random();
        int serialNumber = random.nextInt(1000000000);
        String serialString = String.valueOf(serialNumber);

        CertificateInfo certInfo = new CertificateInfo();
        certInfo.setIssuer(user);
        certInfo.setSubject(user);
        certInfo.setIssuerPrivateKey(keyPair.getPrivate());
        certInfo.setSubjectPublicKey(keyPair.getPublic());
        certInfo.setCa(true);
        certInfo.setSerialNumber(serialString);

        X509Certificate cert = certificateGenerator.generateCertificate(certInfo);
        MyCertificate myCert = new MyCertificate();
        myCert.setCa(true);
        myCert.setIssuer(user);
        myCert.setSubject(user);
        myCert.setSerialNumber(serialString);
        myCert.setStatus("valid");

        certificateRepository.save(myCert);
        WriteCertificateToKeystores(cert, user, keyPair.getPrivate());
    }

    private void WriteCertificateToKeystores(X509Certificate certificate, User user, PrivateKey issuerKey){
        KeyStoreWriter.getInstance().loadKeyStore(null, publicKeyStorePassowrd);
        KeyStoreWriter.getInstance().write(String.valueOf(certificate.getSerialNumber()), mockupKeyPair.getPrivate(), publicKeyStorePassowrd, certificate);
        KeyStoreWriter.getInstance().saveKeyStore(publicKeyStoreFile, publicKeyStorePassowrd);

        String keystoreName = "keystores/private/" + user.getEmail() + "/keystore";
        char[] keystorePassword = new String(user.getHashedPassword()).toCharArray();

        KeyStoreWriter.getInstance().loadKeyStore(keystoreName , keystorePassword);
        KeyStoreWriter.getInstance().write(String.valueOf(certificate.getSerialNumber()), issuerKey , keystorePassword, certificate);
        KeyStoreWriter.getInstance().saveKeyStore(keystoreName, keystorePassword);
    }

    public List<MyCertificate> findBySubject(long subject_id){
        User subject = userRepository.findOne(subject_id);
        List<MyCertificate> certificates = certificateRepository.findBySubject(subject);
        return certificates;
    }

    public void accept(long csr_id){
        CertificateSigningRequest csr = csrRepository.findOne(csr_id);
        KeyPair keyPair = KeyGenerator.deserialize(csr.getKeyPair());

        MyCertificate myCert = new MyCertificate();
        myCert.setSubject(csr.getSubject());
        myCert.setIssuer(csr.getIssuer());
        myCert.setStatus("active");
        myCert.setCa(csr.isWantsCa());
        myCert.setSerialNumber("123");
        certificateRepository.save(myCert);

        PublicKey subjectKey = keyPair.getPublic();

        String keystoreName = "keystores/private/" + csr.getIssuer().getEmail() + "/keystore";
        String keystorePassword = new String(csr.getIssuer().getHashedPassword());
        PrivateKey issuerKey = KeyStoreReader.getInstance().readPrivateKey(keystoreName, keystorePassword, csr.getForSerial(), keystorePassword);

        Random random = new Random();
        int serialNumber = random.nextInt(1000000000);
        String serialString = String.valueOf(serialNumber);

        CertificateInfo certInfo = new CertificateInfo();
        certInfo.setSerialNumber(serialString);
        certInfo.setSubjectPublicKey(subjectKey);
        certInfo.setSubject(csr.getSubject());
        certInfo.setIssuerPrivateKey(issuerKey);
        certInfo.setIssuer(csr.getIssuer());

        X509Certificate newCert = CertificateGenerator.generateCertificate(certInfo);
        WriteCertificateToKeystores(newCert, csr.getSubject(), keyPair.getPrivate());

        csrRepository.delete(csr_id);
    }

    public void decline(long csr_id){
        csrRepository.delete(csr_id);
    }

    //get, status, withdraw
    public String getCertificate(String serialNumber){
        MyCertificate myCert = certificateRepository.findBySerialNumber(serialNumber);
        if(myCert == null)
            return null;
        String keystoreName = "keystores/private/" + myCert.getIssuer().getEmail() + "/keystore";
        String keystorePassword = new String(myCert.getIssuer().getHashedPassword());
        Certificate cert = KeyStoreReader.getInstance().readCertificate(keystoreName, keystorePassword, serialNumber);
        return cert.toString();
    }

    public String getStatus(String serialNumber){
        MyCertificate myCert = certificateRepository.findBySerialNumber(serialNumber);
        if(myCert == null)
            return null;
        String status = myCert.getStatus();
        return status;
    }

    public boolean withdrawCertificate(String serialNumber){
        MyCertificate myCert = certificateRepository.findBySerialNumber(serialNumber);
        if(myCert == null || !myCert.getStatus().equals("active"))
            return false;
        myCert.setStatus("revoked");
        certificateRepository.save(myCert);
        return true;
    }

    public MyCertificate findBySerial(String serial){
        return certificateRepository.findBySerialNumber(serial);
    }

}
