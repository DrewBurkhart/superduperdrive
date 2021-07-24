package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    // CREATE
    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    // READ
    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public List<Note> getUserNotes(Integer userId) {
        return noteMapper.getNotesByUser(userId);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }

    // UPDATE
    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    // DELETE
    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
