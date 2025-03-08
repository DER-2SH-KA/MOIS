package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.impl.Nameable

@Entity(
    tableName = TableNames.STUDY_SUBJECTSS_TABLE,
    indices = [
        Index( value = ["id", "name"] )
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_local_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StudySubject(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "name")
    override var name: String,

    @ColumnInfo(name = "user_local_id")
    var userLocalId: Long
) : Nameable
