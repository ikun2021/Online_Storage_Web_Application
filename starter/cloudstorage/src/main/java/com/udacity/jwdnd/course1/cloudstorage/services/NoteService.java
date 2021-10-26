package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;


    public List<Note> findAllNotes(Integer userid) {
        return noteMapper.findAllNotes(userid);
    }

    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    public void editNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteById(Integer noteid) {
        noteMapper.deleteById(noteid);
    }
}
