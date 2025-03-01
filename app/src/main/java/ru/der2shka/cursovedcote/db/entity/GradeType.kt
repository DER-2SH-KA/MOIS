package ru.der2shka.cursovedcote.db.entity;

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames

@Entity(
    tableName = TableNames.GRADE_TYPE_TABLE,
    indices = [
        Index( value = ["id"] )
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
data class GradeType(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    var id: Long = 0,

    @ColumnInfo( name = "name" )
    var name: String = "",

    @ColumnInfo( name = "multiplier" )
    var mulltiplier: Long = 0,

    @ColumnInfo( name = "user_local_id" )
    var userLocalId: Long = 0
)
