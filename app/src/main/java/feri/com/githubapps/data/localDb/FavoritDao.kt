package feri.com.githubapps.data.localDb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritDao {
    @Query("SELECT * from favorit Where login = :login")
    fun get(login: String?): LiveData<Favorit?>?

    @Query("SELECT * from favorit")
    fun getAll(): LiveData<List<Favorit>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorit: Favorit);

    @Delete
    fun delete(model: Favorit)

    @Query("DELETE FROM favorit WHERE login = :login ")
    fun delete(login: String): Int
}