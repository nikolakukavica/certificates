package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.util.Base64Utility;
import com.example.util.KeyStoreWriter;
import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Bender on 5/14/2017.
 */
@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> getAll(){
        return  repository.findAll();
    }

    public boolean addUser(User user){
        user.setApprovedByAdmin(false);
        user.setRole("user");
        if(repository.findByEmail(user.getEmail()) != null)
            return false;
        String plainTextPassword = user.getPlainPassword();
        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPassword(plainTextPassword, salt);
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);
        user.setPlainPassword("");
        repository.save(user);
        return true;

    }

    public User checkUser(String email, String plainTextPassword){
        User user = repository.findByEmail(email);
        if(user==null || !user.isApprovedByAdmin())
            return null;
        byte[] salt = user.getSalt();
        byte[] attemptedHash = hashPassword(plainTextPassword, salt);
        byte[] correctHash = user.getHashedPassword();
        boolean isMatch = Arrays.equals(correctHash, attemptedHash);
        System.out.println(user.getCountry());
        System.out.println(user.getGivenName());
        return user;
    }

    public List<User> findAll(){
        List<User> all_stats = repository.findAll();
        return ( all_stats.isEmpty() ? null : all_stats );
    }

    public void approveUser(Long user_id){
        User user = repository.findOne(user_id);
        user.setApprovedByAdmin(true);
        try {
            Files.createDirectories(Paths.get("keystores/private/" + user.getEmail()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(user);
    }

    public void deleteUser(Long user_id){
        repository.delete(user_id);
    }

    public List<User> getUnapproved(){
        return repository.findByApprovedByAdmin(false);
    }

    private byte[] hashPassword(String plainTextPassword, byte[] salt){
        byte[] passBytes = plainTextPassword.getBytes();
        byte[] hashedPassword = BCrypt.generate(passBytes, salt, 10);
        return hashedPassword;
    }

    private byte[] generateSalt(){
        final Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return salt;
    }

}
