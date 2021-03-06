package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping()
    public String createNote(Authentication authentication, Note note) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        Integer noteId = note.getNoteId();
        Note existingNote = noteService.getNote(noteId);
        if (noteId == null) {
            note.setUserId(userId);
            noteService.addNote(note);
        } else if (existingNote != null) {
            if (existingNote.getUserId().equals(userId)) {
                noteService.updateNote(note);
            }
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserid();
        Note note = noteService.getNote(noteId);
        if (note != null) {
            if (note.getUserId().equals(userId)) {
                noteService.deleteNote(noteId);
            } else {
                return "redirect:/result?error";
            }
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
