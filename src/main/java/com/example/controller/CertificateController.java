package com.example.controller;

import com.example.model.CertificateInfo;
import com.example.model.MyCertificate;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.List;


@RestController
@RequestMapping(value = "/certificates")
public class CertificateController {

    @Autowired
    CertificateService service;

    @RequestMapping(value = "/newSelfSigned/{user_id}", method = RequestMethod.POST)
    public void newSelfSigned(@PathVariable long user_id){
        System.out.print("Cuvam ga!");
        service.newSelfSigned(user_id);
    }

    @RequestMapping(value = "/allAuthorities")
    public List<MyCertificate> getAllAuthorities(){
        return service.getAllAuthorities();
    }

    @RequestMapping(value = "/bySubject/{subject_id}")
    public List<MyCertificate> findBySubject(@PathVariable long subject_id){
        return service.findBySubject(subject_id);
    }

    @RequestMapping(value = "/getCertificate/{serial}")
    public String getCertificate(@PathVariable String serial){
        System.out.println(service.getCertificate(serial));
        return service.getCertificate(serial);
    }

    @RequestMapping(value = "/getStatus/{serial}", produces={"application/json"})
    public String getStatus(@PathVariable String serial){
        System.out.println(service.getStatus(serial));
        String retVal = service.getStatus(serial);
        return retVal;
    }

    @RequestMapping(value = "/withdraw/{serial}")
    public boolean withdraw(@PathVariable String serial){
        return service.withdrawCertificate(serial);
    }



}
