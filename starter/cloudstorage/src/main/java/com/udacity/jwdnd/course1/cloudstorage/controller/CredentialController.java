package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/credential")
    public String addCredential(Principal principal, Model model
    ,@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential){
        String credentialEditError = null;
        String credentialUploadError = null;
        String credentialError =null;
        String username = principal.getName();
        User user = userService.findByName(username);
        credential.setUserid(user.getUserid());
        if (credential.getCredentialid()== null){
            try {
                credentialService.addCredential(credential);
                model.addAttribute("credentialUploadSuccess", "Credential successfully uploaded.");
            } catch (Exception e) {
                e.printStackTrace();
                credentialUploadError = e.toString();
                model.addAttribute("credentialError", credentialUploadError);
            }
        }else{
            try {
                credentialService.editCredential(credential);
                model.addAttribute("credentialEditSuccess", "Credential successfully updated.");
            } catch (Exception e) {
                e.printStackTrace();
                credentialEditError = e.toString();
                model.addAttribute("credentialError", credentialEditError);
            }
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-credentials-tab");
        return "home";
    }

    @GetMapping("/credential-delete/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid, Model model, Principal principal,
                                   @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential){
        String credentialDeleteError = null;
        String username = principal.getName();
        User user = userService.findByName(username);
        try {
            credentialService.deleteById(credentialid);
            model.addAttribute("credentialDeleteSuccess", "Credential successfully deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            credentialDeleteError = e.toString();
            model.addAttribute("credentialError", credentialDeleteError);
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-credentials-tab");
        return "home";
    }
}

