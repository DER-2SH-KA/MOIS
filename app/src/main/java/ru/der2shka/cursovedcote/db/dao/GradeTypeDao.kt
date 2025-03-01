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

@Dao
interface GradeTypeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGradeType(gt: GradeType)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGradeTypes(vararg gt: GradeType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGradeType(gt: GradeType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGradeTypes(vararg gt: GradeType)

    @Delete
    fun deleteGradeType(gt: GradeType)

    @Delete
    fun deleteGradeTypes(vararg gt: GradeType)

    @Query(value = "SELECT * FROM ${TableNames.GRADE_TYPE_TABLE} AS gt WHERE( gt.id == :gtId )")
    fun findGradeTypeById(gtId: Long): Note

    @Query(value = "SELECT * FROM ${TableNames.GRADE_TYPE_TABLE} AS gt ORDER BY gt.name ASC")
    fun findGradeTypes(): List<Note>


    @Query(value = "SELECT * FROM ${TableNames.GRADE_TYPE_TABLE} AS gt WHERE( gt.name LIKE '%' || :name || '%' ) ORDER BY gt.name ASC")
    fun findGradeTypesByName(name: String): List<Note>
}