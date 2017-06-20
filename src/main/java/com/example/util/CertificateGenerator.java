package com.example.util;

import com.example.model.CertificateInfo;
import com.example.model.User;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class CertificateGenerator {

	static{
		Security.addProvider(new BouncyCastleProvider());
	}

	public CertificateGenerator() {}

	public static X509Certificate generateCertificate(CertificateInfo certInfo) {
		X500Name subjectName = generateX500Name(certInfo.getSubject());
		X500Name issuerName = generateX500Name(certInfo.getIssuer());

		try {
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			builder = builder.setProvider("BC");

			ContentSigner contentSigner = builder.build(certInfo.getIssuerPrivateKey());

			Calendar calendar = Calendar.getInstance();
			Date startDate = calendar.getTime();
			calendar.add(Calendar.YEAR, 1);
			Date endDate = calendar.getTime();

			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerName,
					new BigInteger(certInfo.getSerialNumber()),
					startDate,
					endDate,
					subjectName,
					certInfo.getSubjectPublicKey());
		//byte[] isCaByte = new byte[1];
		//isCaByte[0] = (byte)(certInfo.isCa()?1:0);
		//certGen.addExtension(new Extension(Extension.basicConstraints, true, isCaByte));

			X509CertificateHolder certHolder = certGen.build(contentSigner);


			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");
			return certConverter.getCertificate(certHolder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static X500Name generateX500Name(User user){
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, user.getCommonName());
		builder.addRDN(BCStyle.SURNAME, user.getSureName());
		builder.addRDN(BCStyle.GIVENNAME, user.getGivenName());
		builder.addRDN(BCStyle.O, user.getOrganisation());
		builder.addRDN(BCStyle.OU, user.getOrganisationUnit());
		builder.addRDN(BCStyle.C, user.getCountry());
		builder.addRDN(BCStyle.E, user.getEmail());
		builder.addRDN(BCStyle.UID, user.getUid());
		System.out.println(user.getCommonName());
		System.out.println(user.getSureName());
		System.out.println(user.getGivenName());
		System.out.println(user.getOrganisation());
		return builder.build();
	}

}
