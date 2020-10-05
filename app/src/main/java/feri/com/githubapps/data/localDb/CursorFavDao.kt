package feri.com.githubapps.data.localDb

import android.database.Cursor
import androidx.room.*


@Dao
interface CursorFavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorit: Favorit?): Long

    @Query("SELECT * FROM favorit")
    fun findAll(): Cursor?

    @Query("SELECT * FROM favorit WHERE login = :login")
    fun find(login: String): Cursor?

    @Query("DELETE FROM favorit WHERE login = :login ")
    fun delete(login: String): Int

    @Update
    fun update(favorit: Favorit?): Int
}
