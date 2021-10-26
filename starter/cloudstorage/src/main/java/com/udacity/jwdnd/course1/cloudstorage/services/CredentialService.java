package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    public void editCredential(Credential credential) {
        credentialMapper.editCredential(credential);
    }

    public void addCredential(Credential credential) {
        credentialMapper.addCredential(credential);
    }

    public List<Credential> findAllCredentials(Integer userid) {
        List<Credential> credentials = credentialMapper.findAllCredentials(userid);;
        for(Credential cre:credentials) {
            String encryptedPassword = cre.getPassword();
            String encodedKey = cre.getKey();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
            cre.setPassword(decryptedPassword);
        }
        return credentials;
    }

    public void deleteById(Integer credentialid) {
        credentialMapper.deleteById(credentialid);
    }


}