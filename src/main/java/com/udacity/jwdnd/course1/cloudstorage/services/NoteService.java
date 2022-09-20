package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getUserNotes(Integer userId) {
        return noteMapper.getUserNotes(userId);
    }

    public Integer saveNote(Note note, Integer userId) {
        if (note.getNoteId() == null) {
            note.setUserId(userId);
            return noteMapper.insert(note);
        } else {
            Note existedNote = noteMapper.getNote(note.getNoteId());
            if (existedNote != null && existedNote.getUserId().equals(userId)) {
                return noteMapper.update(note);
            } else {
                return -1;
            }
        }
    }

    public Integer deleteNote(Integer noteId, Integer userId) {
        Note existedNote = noteMapper.getNote(noteId);
        if (existedNote != null && existedNote.getUserId().equals(userId)) {
            return noteMapper.delete(noteId);
        }
        return -1;
    }
}
