package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(
            CredentialService credentialService,
            UserService userService,
            EncryptionService encryptionService
    ) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping()
    public String createCredential(Authentication authentication, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        Integer credentialId = credential.getCredentialId();
        Credential existingCredential = credentialService.getCredential(credentialId);
        if (credentialId == null) {
            credential.setUserId(userId);
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);
            credentialService.addCredential(credential);
        } else if (existingCredential != null) {
            if (existingCredential.getUserId().equals(userId)) {
                String encryptedPassword = encryptionService.encryptValue(
                        credential.password,
                        existingCredential.getKey()
                );
                credential.setPassword(encryptedPassword);
                credentialService.updateCredential(credential);
            }
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        Credential credential = credentialService.getCredential(credentialId);
        if (credential != null) {
            if (credential.getUserId().equals(userId)) {
                credentialService.deleteCredential(credentialId);
            } else {
                return "redirect:/result?error";
            }
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
