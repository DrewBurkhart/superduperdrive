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
        if (note.getNoteId() == null) {
            User user = userService.getUser(authentication.getName());
            Integer userId = user.getUserid();
            note.setUserId(userId);
            noteService.addNote(note);
        } else {
            noteService.updateNote(note);
        }
        // https://stackoverflow.com/a/57439172
        return "redirect:/result?success";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        if (noteService.getNote(noteId) != null) {
            noteService.deleteNote(noteId);
        } else {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
}
