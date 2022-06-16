package shuhbam.sevenminuteworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDao():HistoryDao

    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        fun getInstance(context: Context): HistoryDatabase {
            kotlin.synchronized(this){     //multiple threads can ask for data so only once at a time so it is synchrnized
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,         //creating a database if instance is null
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE=instance                           //after creating database instance is not null
                }
                return instance
            }
        }
    }
}