package com.example.studentnotes

import androidx.compose.runtime.MutableState
import com.google.firebase.Firebase
import com.google.firebase.database.database

object Network {
    private val db = Firebase.database

    fun fetchComments(commentsState: MutableState<String>) {
        db.getReference("comments").get().addOnSuccessListener {
            for (ds in it.children) {
                if (ds.child("studentNo").value == C.loggedInStudent) {
                    commentsState.value = ds.child("comment").value as String
                    break
                }
            }
        }
    }

    fun updateComments(comment: String) {
        db.getReference("comments").get().addOnSuccessListener {
            for (ds in it.children) {
                if (ds.child("studentNo").value == C.loggedInStudent) {
                    ds.child("comment").ref.setValue(comment)
                    break
                }
            }
        }
    }

    fun fetchNotes(lectureCode: String, notesState: MutableState<MutableList<Note>>) {
        db.getReference("lectures").get().addOnSuccessListener {
            for (ds in it.children) {
                if (ds.child("code").value == lectureCode) {
                    (ds.child("notes").value as? List<*>)?.let {
                        val notes = mutableListOf<Note>()
                        for (note in it) {
                            val noteMap = note as Map<*, *>
                            notes.add(
                                Note(
                                    noteMap["title"] as String,
                                    noteMap["content"] as String
                                )
                            )
                        }
                        notesState.value = notes
                    }
                    break
                }
            }
        }
    }

    fun postNewNote(note: Note, lectureCode: String) {
        db.getReference("lectures").get().addOnSuccessListener {
            for (ds in it.children) {
                if (ds.child("code").value == lectureCode) {
                    val notes = ds.child("notes").value as? MutableList<Note> ?: mutableListOf()
                    notes.add(note)
                    ds.child("notes").ref.setValue(notes)
                    break
                }
            }
        }
    }

    fun updateNote(note: Note, lectureCode: String, noteIndex: Int) {
        db.getReference("lectures").get().addOnSuccessListener {
            for (ds in it.children) {
                if (ds.child("code").value == lectureCode) {
                    (ds.child("notes").value as? List<*>)?.let {
                        val updatedNotes = it.toMutableList()
                        updatedNotes[noteIndex] = hashMapOf(
                            "title" to note.title,
                            "content" to note.content
                        )
                        ds.child("notes").ref.setValue(updatedNotes)
                    }

                    break
                }
            }
        }
    }
}