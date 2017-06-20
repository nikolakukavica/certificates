package com.example.repository;

import com.example.model.CertificateSigningRequest;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Bender on 5/29/2017.
 */
public interface CsrRepository extends JpaRepository<CertificateSigningRequest, Long>{

    List<CertificateSigningRequest> findBySubject(User subject);
    List<CertificateSigningRequest> findByIssuer(User issuer);

}
