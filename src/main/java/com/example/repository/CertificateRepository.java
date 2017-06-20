package com.example.repository;

import com.example.model.CertificateSigningRequest;
import com.example.model.MyCertificate;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Bender on 5/18/2017.
 */
public interface CertificateRepository extends JpaRepository<MyCertificate,Long> {

    List<MyCertificate> findBySubject(User subject);
    List<MyCertificate> findByIsCa(boolean isCa);
    MyCertificate findBySerialNumber(String serialNumber);

}
