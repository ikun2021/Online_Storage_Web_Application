package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public void addFile(File file){
        fileMapper.addFile(file);
    }

    public File findFileById(Integer fileid) {
        return fileMapper.findFileById(fileid);
    }

    public List<File> findAllFiles(Integer userid){
        return fileMapper.findAllFiles(userid);
    }

    public void deleteById(Integer fileid) {
        fileMapper.deleteById(fileid);
    }

    public boolean isFilenameAvailable(String fileName){

        return (fileMapper.findByName(fileName)==null);
    }
}
