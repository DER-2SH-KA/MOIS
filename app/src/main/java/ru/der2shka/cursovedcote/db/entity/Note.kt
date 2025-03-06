package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.impl.Nameable
import java.time.LocalDate

@Entity(
    tableName = TableNames.NOTES_TABLE,
    indices = [
        Index( value = ["id", "date", "status"] )
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
data class Note(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    var id: Long = 0,

    @ColumnInfo( name = "name" )
    override var name: String,

    @ColumnInfo(
        name = "description",
        typeAffinity = ColumnInfo.TEXT
    )
    var description: String = "",

    @ColumnInfo( name = "date" )
    var date: Long,

    @ColumnInfo( name = "status" )
    var status: Int,

    @ColumnInfo( name = "user_local_id" )
    var userId: Long
) : Nameable
