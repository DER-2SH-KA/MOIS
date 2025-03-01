package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames

@Entity(
    tableName = TableNames.GRADES_TABLE,
    indices = [
        Index( value = [
            "id",
            "grade_type_id",
            "study_subject_id",
            "date"
        ] )
    ],
    foreignKeys = [
        ForeignKey(
            entity = GradeType::class,
            parentColumns = ["id"],
            childColumns = ["grade_type_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StudySubject::class,
            parentColumns = ["id"],
            childColumns = ["study_subject_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Grade(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "grade_value")
    var gradeValue: Int,

    @ColumnInfo(name = "date")
    var date: Long,

    @ColumnInfo(name = "grade_type_id")
    var gradeTypeId: Long,

    @ColumnInfo(name = "study_subject_id")
    var subjectStudyId: Long
)