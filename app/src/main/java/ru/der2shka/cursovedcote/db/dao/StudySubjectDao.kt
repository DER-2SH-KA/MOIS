package ru.der2shka.cursovedcote.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.entity.StudySubject

@Dao
interface StudySubjectDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertStudySubject(ss: StudySubject): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertStudySubjects(vararg ss: StudySubject)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudySubject(ss: StudySubject)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudySubjects(vararg ss: StudySubject)

    @Delete
    fun deleteStudySubject(ss: StudySubject)

    @Delete
    fun deleteStudySubjects(vararg s: StudySubject)

    @Query(value = "SELECT * FROM ${TableNames.STUDY_SUBJECTSS_TABLE} AS sst WHERE( sst.id == :ssId )")
    fun findStudySubjectById(ssId: Long): StudySubject

    @Query(value = "SELECT * FROM ${TableNames.STUDY_SUBJECTSS_TABLE}")
    fun findStudySubjects(): List<StudySubject>

    @Query(value = "SELECT * FROM ${TableNames.STUDY_SUBJECTSS_TABLE} AS sst ORDER BY sst.name ASC")
    fun findStudySubjectsWithOrdering(): List<StudySubject>


    @Query(value = "SELECT * FROM ${TableNames.STUDY_SUBJECTSS_TABLE} AS sst WHERE( sst.name LIKE '%' || :name || '%' ) ORDER BY sst.name ASC")
    fun findStudySubjectsByName(name: String): List<StudySubject>
}