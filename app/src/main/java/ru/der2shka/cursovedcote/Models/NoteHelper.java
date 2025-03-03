package ru.der2shka.cursovedcote.Models;

import android.graphics.Path;

import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.Note;

public class NoteHelper {
    private static Optional<NoteHelper> instance = Optional.ofNullable( null );

    private Optional<Note> note = Optional.ofNullable( null );

    private NoteHelper() {}

    /**
     * Return instance of NoteHelper and create them if it not exist.
     * @return NoteHelper instance.
     * **/
    public static NoteHelper getInstance() {
        if ( !instance.isPresent() ) {
            instance = Optional.ofNullable( new NoteHelper() );
        }

        return instance.get();
    }

    /**
     * Set note optional value.
     * @param nnote new note value.
     * @return true if value was updated else false.
     * **/
    public Boolean setNoteValue(Optional<Note> nnote) {
        if (nnote.isPresent()) {
            note = nnote;

            return true;
        }

        return false;
    }

    /**
     * Return note object.
     * @return note value if persist, else Note(0, ..., 1)
     * **/
    public Note getNoteValue() {
        return note.orElse(
                new Note(
                        0,
                        "name",
                        "description",
                        0L,
                        0,
                        1
                )
        );
    }
}
