package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/note")
    public String addNote(Principal principal,Model model,
                          @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential) {
        String noteEditError = null;
        String noteUploadError = null;
        String username = principal.getName();
        User user = userService.findByName(username);
        note.setUserid(user.getUserid());
        if (note.getNoteid() != null) {
            try {
                noteService.editNote(note);
                model.addAttribute("noteUploadSuccess", "Note successfully uploaded");
            } catch (Exception e) {
                e.printStackTrace();
                noteEditError = e.toString();
                model.addAttribute("noteError", noteEditError);
            }
        } else {
            try {
                noteService.addNote(note);
                model.addAttribute("noteEditSuccess", "Note successfully updated.");
            } catch (Exception e) {
                e.printStackTrace();
                noteUploadError = e.toString();
                model.addAttribute("noteError", noteUploadError);
            }
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-notes-tab");
        return "home";
    }

    @RequestMapping("/note-delete/{noteId}")
    public String deleteFile(@PathVariable("noteId") Integer noteId,Model model,Principal principal,
                             @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential)  {
        String noteDeleteError = null;
        String username = principal.getName();
        User user = userService.findByName(username);
        try {
            noteService.deleteById(noteId);
            model.addAttribute("noteDeleteSuccess", "Note successfully deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            noteDeleteError = e.toString();
            model.addAttribute("noteError", noteDeleteError);
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-notes-tab");
        return "home";
    }

}