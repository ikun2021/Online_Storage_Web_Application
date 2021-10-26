package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;



@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @RequestMapping()
    public String homepage(Principal principal, Model model, @ModelAttribute Note note,@ModelAttribute Credential credential) {
        String username = principal.getName();
        User user = userService.findByName(username);
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        return "home";
    }
}

