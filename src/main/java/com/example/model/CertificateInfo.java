package com.example.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * Created by Bender on 5/18/2017.
 */
public class CertificateInfo {

    private User subject;
    private PublicKey subjectPublicKey;

    private User issuer;
    private PrivateKey issuerPrivateKey;


    private String serialNumber;
    private boolean isCa;


    public CertificateInfo(){}

    public CertificateInfo(User subject, PublicKey subjectPublicKey, User issuer, PrivateKey issuerPrivateKey, String serialNumber, boolean isCa) {
        this.subject = subject;
        this.subjectPublicKey = subjectPublicKey;
        this.issuer = issuer;
        this.issuerPrivateKey = issuerPrivateKey;
        this.serialNumber = serialNumber;
        this.isCa = isCa;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public PublicKey getSubjectPublicKey() {
        return subjectPublicKey;
    }

    public void setSubjectPublicKey(PublicKey subjectPublicKey) {
        this.subjectPublicKey = subjectPublicKey;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public PrivateKey getIssuerPrivateKey() {
        return issuerPrivateKey;
    }

    public void setIssuerPrivateKey(PrivateKey issuerPrivateKey) {
        this.issuerPrivateKey = issuerPrivateKey;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isCa() {
        return isCa;
    }

    public void setCa(boolean ca) {
        isCa = ca;
    }
}
