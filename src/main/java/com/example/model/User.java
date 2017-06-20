package com.example.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Bender on 5/14/2017.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long user_id;

    private String userName;

    private String commonName;
    private String sureName;
    private String givenName;
    private String organisation;
    private String organisationUnit;
    private String country;
    private String email;
    private String uid;

    private String plainPassword;
    private byte[] salt;
    private byte[] hashedPassword;

    private boolean approvedByAdmin = false;
    private boolean isRoot;

    private String role;
/*
    @JsonIgnore
    @OneToMany(cascade= CascadeType.ALL, mappedBy="subject")
    private List<MyCertificate> owns;

    @JsonIgnore
    @OneToMany(cascade= CascadeType.ALL, mappedBy="issuer")
    private List<MyCertificate> issued;
*/

    public User(){}

    public User(String userName, String commonName, String sureName, String givenName, String organisation, String organisationUnit, String country, String email, String uid, String plainPassword, byte[] salt, byte[] hashedPassword, boolean approvedByAdmin, boolean isRoot, List<MyCertificate> owns, List<MyCertificate> issued, String role) {
        this.userName = userName;
        this.commonName = commonName;
        this.sureName = sureName;
        this.givenName = givenName;
        this.organisation = organisation;
        this.organisationUnit = organisationUnit;
        this.country = country;
        this.email = email;
        this.uid = uid;
        this.plainPassword = plainPassword;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.approvedByAdmin = approvedByAdmin;
        this.isRoot = isRoot;
        //this.owns = owns;
        //this.issued = issued;
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getOrganisationUnit() {
        return organisationUnit;
    }

    public void setOrganisationUnit(String organisationUnit) {
        this.organisationUnit = organisationUnit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

/*
    public List<MyCertificate> getOwns() {
        return owns;
    }

    public void setOwns(List<MyCertificate> owns) {
        this.owns = owns;
    }

    public List<MyCertificate> getIssued() {
        return issued;
    }

    public void setIssued(List<MyCertificate> issued) {
        this.issued = issued;
    }
*/

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isApprovedByAdmin() {
        return approvedByAdmin;
    }

    public void setApprovedByAdmin(boolean approvedByAdmin) {
        this.approvedByAdmin = approvedByAdmin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
