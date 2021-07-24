package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(Credential credential) {
        credentialMapper.addCredential(credential);
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public List<Credential> getUserCredentials(Integer userId) {
        return credentialMapper.getCredentialsByUser(userId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
