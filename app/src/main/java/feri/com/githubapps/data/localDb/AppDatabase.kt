package feri.com.githubapps.data.localDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Favorit::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavoritDao?
    abstract fun cursorFavDao(): CursorFavDao?

    companion object {
        var tag = "seed"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        println("Creating DB")
                        INSTANCE =
                            buildDatabase(context)
                    }
                }
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDatabase {
            println("Building, Room")
            return Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase::class.java,
                "githubapps_database"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}