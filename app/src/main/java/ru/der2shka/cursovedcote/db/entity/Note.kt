package ru.der2shka.cursovedcote.db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames

@Entity(
    tableName = TableNames.NOTES_TABLE,
    indices = [ Index(value = ["id", "date", "status"]) ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = "id" )
    var id: Long
)
