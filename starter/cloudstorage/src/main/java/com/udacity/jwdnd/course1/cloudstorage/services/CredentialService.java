package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    public void editCredential(Credential credential) {
        String decryptedPassword = credential.getDecryptedPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(decryptedPassword,encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.editCredential(credential);
    }

    public void addCredential(Credential credential) {
        String decryptedPassword = credential.getDecryptedPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(decryptedPassword,encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.addCredential(credential);
    }

    public List<Credential> findAllCredentials(Integer userid) {
        List<Credential> credentials = credentialMapper.findAllCredentials(userid);;
        return credentials;
    }

    public void deleteById(Integer credentialid) {
        credentialMapper.deleteById(credentialid);
    }


}