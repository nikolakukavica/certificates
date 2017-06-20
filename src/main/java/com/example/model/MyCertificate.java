package com.example.model;

import javax.persistence.*;

/**
 * Created by Bender on 5/15/2017.
 */
@Entity
public class MyCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long certificate_id;
    private String serialNumber;
    private String status;
    private boolean isCa;

    @ManyToOne
    private User subject;

    @ManyToOne
    private User issuer;

    public MyCertificate(){}

    public MyCertificate(long certificate_id, String serialNumber, String status, boolean isCa, User subject, User issuer) {
        this.certificate_id = certificate_id;
        this.serialNumber = serialNumber;
        this.status = status;
        this.isCa = isCa;
        this.subject = subject;
        this.issuer = issuer;
    }

    public long getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(long certificate_id) {
        this.certificate_id = certificate_id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCa() {
        return isCa;
    }

    public void setCa(boolean ca) {
        isCa = ca;
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
}
