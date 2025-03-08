package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.impl.Nameable

@Entity(
    tableName = TableNames.SUM_COUNT_GRADES_FOR_ALL_TIME_TABLE,
    indices = [
        Index( value = [
            "id",
            "study_subject_id"
        ] )
    ],
    foreignKeys = [
        ForeignKey(
            entity = StudySubject::class,
            parentColumns = ["id"],
            childColumns = ["study_subject_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data  class SumCountGradesForAllTime(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    var id: Long = 0,

    @ColumnInfo( name = "sum" )
    var sum: Long,

    @ColumnInfo( name = "count_of_grades" )
    var count_of_grades: Long,

    @ColumnInfo( name = "study_subject_id" )
    var studySubjectId: Long,
) : Nameable {
    @Ignore
    override  var name: String = "None Name"
}
