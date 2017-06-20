package com.example.util;

import com.example.model.User;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.*;

/**
 * Created by Bender on 5/19/2017.
 */
public class CsrGenerator {


    public static PKCS10CertificationRequest generateCsr(User subject, KeyPair keyPair){
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(generateX500Principal(subject), keyPair.getPublic());
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = null;
        try {
            signer = csBuilder.build(keyPair.getPrivate());
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }
        PKCS10CertificationRequest csr = p10Builder.build(signer);
        /*
        byte[] bytes = null;;
        try {
            bytes = csr.getEncoded();
            System.out.println(Base64Utility.encode(csr.getEncoded()));
            PKCS10CertificationRequest cs = new PKCS10CertificationRequest(bytes);
            RDN ct = cs.getSubject().getRDNs(BCStyle.CN)[0];
            //System.out.println("eo ga  " + ct.getFirst().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return csr;
    }

    private static X500Principal generateX500Principal(User user){

        StringBuilder userString = new StringBuilder();
        userString.append("CN" + user.getCommonName());
        userString.append("GN" + user.getGivenName());
        userString.append("CN" + user.getSureName());
        userString.append("SN" + user.getCountry());
        userString.append("CN" + user.getEmail());
        userString.append("CN" + user.getUid());
        userString.append("O" + user.getOrganisation());
        userString.append("OU" + user.getOrganisationUnit());
        return new X500Principal(userString.toString());
    }

    public static void main(String[] args){

        //generateCsr(null);
    }

}
