package com.example.studentnotes

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentnotes.ui.theme.Purple30
import com.google.firebase.Firebase
import com.google.firebase.database.database

class NoteDetailActivity : ComponentActivity() {

    private var noteTitle = ""
    private var noteContent = ""
    private var lectureCode = ""
    private var noteIndex = -1

    private val contentState = mutableStateOf("")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteTitle = intent.getStringExtra("noteTitle") ?: ""
        noteContent = intent.getStringExtra("noteContent") ?: ""
        lectureCode = intent.getStringExtra("lectureCode") ?: ""
        noteIndex = intent.getIntExtra("noteIndex", -1)
        contentState.value = noteContent
        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(noteTitle)
                        },
                        navigationIcon = {
                            IconButton(onClick = { this@NoteDetailActivity.finish() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Go back"
                                )
                            }
                        },
                        actions = {
                            OutlinedButton(
                                onClick = {
                                    Network.updateNote(Note(noteTitle, contentState.value), lectureCode, noteIndex)
                                    Toast.makeText(
                                        this@NoteDetailActivity,
                                        "Note Saved",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },

                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Text(
                                    text = "Save",
                                    fontSize = 16.sp,
                                    color = Purple30,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    )
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp)
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    NoteArea()
                }
            }
        }
    }


    @Composable
    fun NoteArea() {
        BasicTextField(
            value = contentState.value,
            onValueChange = {
                contentState.value = it
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
