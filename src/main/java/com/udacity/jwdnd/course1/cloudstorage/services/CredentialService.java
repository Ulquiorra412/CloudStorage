package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(Integer userId) {
        List<Credential> credentials = credentialMapper.getUserCredentials(userId);
        credentials.forEach(credential -> {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getCredentialKey());
            credential.setDecryptedPassword(decryptedPassword);
        });
        return credentials;
    }

    public Integer saveCredential(Credential credential, Integer userId) {
        String newUrl = credential.getUrl();

        Credential existedCredential = credentialMapper.getCredentialByUserAndUrl(userId, newUrl);

        if (existedCredential == null) {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

            credential.setUserId(userId);
            credential.setCredentialKey(encodedKey);
            credential.setPassword(encryptedPassword);
            return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(),
                    credential.getCredentialKey(), credential.getPassword(), credential.getUserId()));
        }

        else if (userId.equals(existedCredential.getUserId())) {
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), existedCredential.getCredentialKey());
            existedCredential.setPassword(encryptedPassword);
            return credentialMapper.update(existedCredential);
        }

        return -1;
    }

    public Integer deleteCredential(Integer credentialId, Integer userId) {
        Credential existedCredential = credentialMapper.getCredential(credentialId);
        if (existedCredential != null && userId.equals(existedCredential.getUserId()))
            return credentialMapper.delete(existedCredential.getCredentialId());
        return -1;
    }
}
