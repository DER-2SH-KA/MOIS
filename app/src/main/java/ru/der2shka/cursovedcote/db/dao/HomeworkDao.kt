package ru.der2shka.cursovedcote.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.Homework
import ru.der2shka.cursovedcote.db.entity.StudySubject

@Dao
interface HomeworkDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertHomework(h: Homework): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertHomeworks(vararg h: Homework)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHomework(h: Homework)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHomeworks(vararg h: Homework)

    @Delete
    fun deleteHomework(h: Homework)

    @Delete
    fun deleteHomeworks(vararg h: Homework)

    @Query(value = "SELECT * FROM ${TableNames.HOMEWORKS_TABLE} AS ht WHERE( ht.id == :hId )")
    fun findHomeworkById(hId: Long): Homework

    @Query(value = "SELECT * FROM ${TableNames.HOMEWORKS_TABLE}")
    fun findHomeworks(): List<Homework>

    @Query(value = "SELECT * FROM ${TableNames.HOMEWORKS_TABLE} AS ht ORDER BY ht.date_end DESC")
    fun findHomeworksWithOrderingByDateEndDesc(): List<Homework>

    @Query(value = "SELECT * FROM ${TableNames.HOMEWORKS_TABLE} AS ht ORDER BY ht.date_of_write DESC")
    fun findHomeworksWithOrderingByDateOfWriteDesc(): List<Homework>
}