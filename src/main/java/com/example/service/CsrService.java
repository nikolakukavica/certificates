package com.example.service;

import com.example.model.CertificateInfo;
import com.example.model.CertificateSigningRequest;
import com.example.model.MyCertificate;
import com.example.model.User;
import com.example.repository.CertificateRepository;
import com.example.repository.CsrRepository;
import com.example.repository.UserRepository;
import com.example.util.*;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Random;

@Service
public class CsrService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    CsrRepository csrRepository;

    public void create(long subject_id, String issuer_serrial_number, boolean wantsCa) {
        User subject = userRepository.findOne(subject_id);

        MyCertificate issuerCert = certificateRepository.findBySerialNumber(issuer_serrial_number);
        User issuer = issuerCert.getIssuer();

        KeyPair keyPair = KeyGenerator.generateKeyPair();
        byte[] keyPairBytes = KeyGenerator.serialize(keyPair);

        CertificateSigningRequest myCsr = new CertificateSigningRequest(issuer_serrial_number,subject, issuer, keyPairBytes, wantsCa);

        csrRepository.save(myCsr);
    }

    public List<CertificateSigningRequest> findByIssuer(long issuer_id){
        User issuer = userRepository.findOne(issuer_id);
        return csrRepository.findByIssuer(issuer);
    }
}