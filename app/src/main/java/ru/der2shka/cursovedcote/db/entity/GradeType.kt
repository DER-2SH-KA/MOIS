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
class GradeType(
    @PrimaryKey( autoGenerate = true )
    @ColumnInfo( name = "id" )
    var id: Long,

    @ColumnInfo( name = "name" )
    var name: String,

    @ColumnInfo( name = "multiplier" )
    var mulltiplier: Long,

    @ColumnInfo( name = "user_local_id" )
    var userLocalId: Long
)
