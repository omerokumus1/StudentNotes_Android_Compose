package com.example.studentnotes

data class Lecture(
    val code: String,
    val name: String,
    val notes: MutableList<Note>? = mutableListOf()
) {
    override fun toString(): String {
        return "$code - $name"
    }
}
