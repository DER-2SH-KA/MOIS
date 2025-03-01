package ru.der2shka.cursovedcote.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.Note

@Dao
interface NoteDao {
    /** Insert new Note into table. If note is already exist make a abort.
     * @param note new note object**/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNote(note: Note): Long

    /** Insert new Notes into table. If notes is already exist make a abort.
     * @param notes new notes objects**/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNotes(vararg notes: Note): List<Long>

    /** Update note row. On conflict make a replace.
     * @param note note object**/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    /** Update note rows. On conflict make a replace.
     * @param notes note objects**/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotes(vararg notes: Note)

    /** Delete note row.
     * @param note note object**/
    @Delete
    fun deleteNote(note: Note)

    /** Delete note rows.
     * @param notes note objects**/
    @Delete
    fun deleteNotes(vararg notes: Note)

    /** Return one note where note id equals id from param.
     * @param noteId Long note id for searching.
     * @return Note entity**/
    @Query(value = "SELECT * FROM ${TableNames.NOTES_TABLE} AS n WHERE( n.id == :noteId)")
    fun findNoteById(noteId: Long): Note

    /** Return all notes. **/
    @Query(value = "SELECT * FROM ${TableNames.NOTES_TABLE} AS n ORDER BY n.id ASC")
    fun findNotes(): List<Note>

    /** Return all notes with ordering by date. **/
    @Query(value = "SELECT * FROM ${TableNames.NOTES_TABLE} AS n ORDER BY n.date DESC")
    fun findNotesWithOrdering(): List<Note>

    /**  Return all notes by filters **/
    @Query(
        "SELECT * FROM ${TableNames.NOTES_TABLE} AS n " +
        "WHERE( " +
                "n.name LIKE '%' || :name || '%' AND " +
                "n.date BETWEEN :dateBeginInMills AND :dateEndInMills " +
            ") ORDER BY n.date DESC"
    )
    fun findNotesByFiltersWithoutStatus(
        name: String,
        dateBeginInMills: Long,
        dateEndInMills: Long
    ): List<Note>
}