package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.impl.Nameable

@Entity(
    tableName = TableNames.HOMEWORKS_TABLE,
    indices = [
        Index( value = [
            "id",
            "date_of_write",
            "date_begin",
            "date_end",
            "status",
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
data class Homework(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    var id: Long,

    @ColumnInfo( name = "name" )
    override var name: String,

    @ColumnInfo(
        name = "description",
        typeAffinity = ColumnInfo.TEXT
    )
    var description: String?,

    @ColumnInfo( name = "date_of_write" )
    var dateOfWrite: Long,

    @ColumnInfo( name = "date_begin" )
    var dateBegin: Long,

    @ColumnInfo( name = "date_end" )
    var dateEnd: Long,

    @ColumnInfo( name = "status" )
    var status: Int,

    @ColumnInfo( name = "study_subject_id" )
    var studySubjectId: Long
) : Nameable
