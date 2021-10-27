package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.security.Principal;

@Controller
class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    private final long fileMaxSize = 10485760;

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Model model, Principal principal,
                             @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential){
        User user = null;
        try {
            String fileUploadError = null;
            String username = principal.getName();
            user = userService.findByName(username);
            if(!fileUpload.isEmpty()){
                String fileName = fileUpload.getOriginalFilename();
                if(fileService.isFilenameAvailable(fileName)){
                    long size = fileUpload.getSize();
                    if(size<fileMaxSize){
                        String fileSize = size + " byte";
                        String contentType = fileUpload.getContentType();
                        byte[] fileData = new byte[0];
                        fileData = fileUpload.getBytes();
                        File file = new File(null,fileName,contentType,fileSize,user.getUserid(),fileData);
                        fileService.addFile(file);
                        model.addAttribute("fileUploadSuccess", "File successfully uploaded.");
                    }else{
                        model.addAttribute("fileError", "Can't upload a file that exceeds the maximum file size(10M)");
                    }
                }else{
                    model.addAttribute("fileError", "Can't upload files with duplicate names.");
                }
            }else{
                model.addAttribute("fileError", "Can't upload without choosing any files.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("fileError", "error when uploading file, try again.");
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-files-tab");
        return "home";
    }

    @GetMapping("/file-download/{fileid}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer fileid) throws IOException {
        File file = fileService.findFileById(fileid);
        String fileName =file.getFileName();
        String contentType = file.getContentType();
        byte[] fileData = file.getFileData();
        InputStream inputStream = new ByteArrayInputStream(fileData);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attchament;filename=" + fileName)
                .contentType(MediaType.parseMediaType(contentType))
                .body(inputStreamResource);
    }


    @GetMapping("/file-delete/{fileid}")
    public String deleteFile(@PathVariable Integer fileid,Model model,Principal principal,
    @ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential){
        String username = principal.getName();
        User user = userService.findByName(username);
        String fileDeleteError = null;
        try {
            fileService.deleteById(fileid);
            model.addAttribute("fileDeleteSuccess", "File successfully deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            fileDeleteError = e.toString();
            model.addAttribute("fileError", fileDeleteError);
        }
        model.addAttribute("files", fileService.findAllFiles(user.getUserid()));
        model.addAttribute("notes", noteService.findAllNotes(user.getUserid()));
        model.addAttribute("credentials",credentialService.findAllCredentials(user.getUserid()));
        model.addAttribute("tab", "nav-files-tab");
        return "home";
    }

}
