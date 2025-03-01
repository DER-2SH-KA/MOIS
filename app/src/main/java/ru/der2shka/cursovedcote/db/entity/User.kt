package ru.der2shka.cursovedcote.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.der2shka.cursovedcote.db.TableNames

@Entity(
    tableName = TableNames.USERS_TABLE,
    indices = [ Index(value = ["id", "login", "email"]) ]
)
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = "id" )
    var id: Long,

    @ColumnInfo( name = "name" )
    var name: String?,

    @ColumnInfo( name = "login" )
    var login: String?,

    @ColumnInfo( name = "password" )
    var password: String?,

    @ColumnInfo( name = "password" )
    var email: String?,

    @ColumnInfo( name = "global_id" )
    var glovalId: Long?
)