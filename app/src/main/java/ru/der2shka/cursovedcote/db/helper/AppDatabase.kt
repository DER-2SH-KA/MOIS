package ru.der2shka.cursovedcote.db.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.der2shka.cursovedcote.db.dao.GradeTypeDao
import ru.der2shka.cursovedcote.db.dao.NoteDao
import ru.der2shka.cursovedcote.db.dao.StudySubjectDao
import ru.der2shka.cursovedcote.db.dao.UserDao
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.GradeType
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.Note
import ru.der2shka.cursovedcote.db.entity.StudySubject
import ru.der2shka.cursovedcote.db.entity.SumCountGradesForAllTime
import ru.der2shka.cursovedcote.db.entity.User

@Database(
    entities = [
        User::class,
        GradeType::class,
        Note::class,
        StudySubject::class,
        Grade::class,
        Homework::class,
        SumCountGradesForAllTime::class
   ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    /**  Abstract function of UserDao **/
    abstract fun userDao(): UserDao

    /**  Abstract function of NoteDao **/
    abstract fun noteDao(): NoteDao

    /**  Abstract function of GradeTypeDao **/
    abstract fun gradeTypeDao(): GradeTypeDao


    /**  Abstract function of StudySubjectDao **/
    abstract fun studySubjectDao(): StudySubjectDao

    /*
    /**  Abstract function of UserDao **/
    abstract fun userDao(): UserDao

    /**  Abstract function of UserDao **/
    abstract fun userDao(): UserDao

    /**  Abstract function of UserDao **/
    abstract fun userDao(): UserDao
    */
}