package ru.der2shka.cursovedcote.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.der2shka.cursovedcote.db.TableNames
import ru.der2shka.cursovedcote.db.entity.User

/**
 * Data Access Object interface for User entities.
 * **/
@Dao
interface UserDao {
    /** Insert new User into table. If user is already exist make a abort.
     * @param user new user object**/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(user: User)

    /** Insert new Users into table. If users is already exist make a abort.
     * @param users new user objects**/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUsers(vararg users: User)

    /** Update user row. On conflict make a replace.
     * @param user user object**/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    /** Update user rows. On conflict make a replace.
     * @param users user objects**/
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg users: User)

    /** Delete user row.
     * @param user user object**/
    @Delete
    fun deleteUser(user: User)

    /** Delete user rows.
     * @param users user objects**/
    @Delete
    fun deleteUsers(vararg user: User)

    /** Return one user where user id equals id from param.
     * @param userId Long user id for searching.
     * @return User entity**/
    @Query(value = "SELECT * FROM ${TableNames.USERS_TABLE} AS u WHERE( u.id == :userId)")
    fun findUserById(userId: Long): User

    /** Return all users with ordering by ID. **/
    @Query(value = "SELECT * FROM ${TableNames.USERS_TABLE} as u ORDER BY u.id")
    fun findUsers(): List<User>
}