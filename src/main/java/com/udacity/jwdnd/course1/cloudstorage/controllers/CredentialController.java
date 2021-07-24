package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping()
    public String createNote(Authentication authentication, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        if (credential.getCredentialId() == null) {
            credential.setUserId(userId);
            credentialService.addCredential(credential);
        } else if (credential.getUserId().equals(userId)) {
            credentialService.updateCredential(credential);
        } else {
            return "redirect:/result?error";
        }
        // https://stackoverflow.com/a/57439172
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
