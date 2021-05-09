package com.example.jakirespons.data

@Database(entities = [Movie::class, Tv::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object {

        const val DATABASE_NAME = "db_moviecatalog"

    }
}