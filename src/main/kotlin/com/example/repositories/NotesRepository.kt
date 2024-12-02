package com.example.repositories

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.AppDatabase
import com.example.database.DbNote
import com.example.models.Note
import com.example.models.Note.Type.*
import java.io.File

private const val DATABASE_NAME = "database.db"

object NotesRepository {

    private val notesDb = JdbcSqliteDriver(url = "jdbc:sqlite:$DATABASE_NAME").let {
        if (!File("database.db").exists()){
            AppDatabase.Schema.create(it)
        }
        AppDatabase(it)
    }.noteQueries

    fun save(note: Note): Note {
        notesDb.insert(note.title,note.description,note.type.name)
        return notesDb.selectLastInsertedNote().executeAsOne().toNote()
    }

    fun getAll(): List<Note> = notesDb.select().executeAsList().map { it.toNote() }

    fun getById(id: Long): Note? = notesDb.select().executeAsOneOrNull()?.toNote()

    fun update(note: Note): Boolean {
        if (getById(note.id) == null) return false
        notesDb.update(note.title,note.description,note.type.name, note.id)
        return true
    }

    fun delete(id: Long): Boolean {
        if (getById(id) == null) return false
        notesDb.delete(id)
        return true
    }

}

private fun DbNote.toNote(): Note = Note (
    id,
    title,
    description,
    Note.Type.valueOf(type)
)