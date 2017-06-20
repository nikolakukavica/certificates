package com.example.controller;

import com.example.model.CertificateSigningRequest;
import com.example.service.CertificateService;
import com.example.service.CsrService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/csrs")
public class CsrController {

    @Autowired
    CsrService service;
    @Autowired
    CertificateService certificateService;


    @RequestMapping(value = "/create/{subject_id}/{serial}/{isCa}", method = RequestMethod.POST)
    public void create(@PathVariable long subject_id, @PathVariable String serial, @PathVariable boolean isCa){
        System.out.println("Eo brt pravim!" + subject_id + "    " + serial + "   " + isCa);
        service.create(subject_id, serial, isCa);
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public List<CertificateSigningRequest> findByIssuer(@PathVariable long user_id){
        return service.findByIssuer(user_id);
    }

    @RequestMapping(value = "/accept/{csr_id}", method = RequestMethod.POST)
    public void accept(@PathVariable long csr_id){
        certificateService.accept(csr_id);
    }

    @RequestMapping(value = "/decline/{csr_id}", method = RequestMethod.POST)
    public void decline(@PathVariable long csr_id){
        certificateService.decline(csr_id);
    }

}
