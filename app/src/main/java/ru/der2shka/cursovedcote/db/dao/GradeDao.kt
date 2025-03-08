package ru.der2shka.cursovedcote.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.Grade
import ru.der2shka.cursovedcote.db.entity.Homework

@Dao
interface GradeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGrade(g: Grade): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGrades(vararg g: Grade)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGrade(g: Grade)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGrades(vararg g: Grade)

    @Delete
    fun deleteGrade(g: Grade)

    @Delete
    fun deleteGrades(vararg g: Grade)

    @Query(value = "SELECT * FROM ${TableNames.GRADES_TABLE} AS grt WHERE( grt.id == :hId )")
    fun findGradeById(hId: Long): Grade

    @Query(value = "SELECT * FROM ${TableNames.GRADES_TABLE}")
    fun findGrades(): List<Grade>

    @Query(value = "SELECT * FROM ${TableNames.GRADES_TABLE} AS grt ORDER BY grt.date DESC")
    fun findGradesWithOrderingByDate(): List<Grade>
}