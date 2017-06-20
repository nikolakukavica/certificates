package com.example.model;

import com.example.service.CertificateService;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class CertificateSigningRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long csr_id;

    private String forSerial;

    @ManyToOne
    private User subject;

    @ManyToOne
    private User issuer;

    @Type(type = "org.hibernate.type.MaterializedBlobType")
    private byte[] keyPair;

    private boolean wantsCa;

    public CertificateSigningRequest(){}

    public CertificateSigningRequest(String forSerial, User subject, User issuer, byte[] keyPair, boolean wantsCa) {
        this.forSerial = forSerial;
        this.subject = subject;
        this.issuer = issuer;
        this.keyPair = keyPair;
        this.wantsCa = wantsCa;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public byte[] getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(byte[] keyPair) {
        this.keyPair = keyPair;
    }

    public long getCsr_id() {
        return csr_id;
    }

    public void setCsr_id(long csr_id) {
        this.csr_id = csr_id;
    }

    public boolean isWantsCa() {
        return wantsCa;
    }

    public void setWantsCa(boolean wantsCa) {
        this.wantsCa = wantsCa;
    }

    public String getForSerial() {
        return forSerial;
    }

    public void setForSerial(String forSerial) {
        this.forSerial = forSerial;
    }
}
