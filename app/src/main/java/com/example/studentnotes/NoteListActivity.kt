package com.example.studentnotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studentnotes.ui.theme.Purple30
import com.example.studentnotes.ui.theme.Purple40

class NoteListActivity : ComponentActivity() {

    private val notes = mutableListOf<Note>()

    private val notesState = mutableStateOf(notes)
    private val showDialogState = mutableStateOf(false)

    private var lectureName = ""
    private var lectureCode = ""

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lectureName = intent.getStringExtra("lectureName") ?: ""
        lectureCode = intent.getStringExtra("lectureCode") ?: ""
        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.safeContentPadding(),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                lectureName,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { this@NoteListActivity.finish() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Go back"
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    showDialogState.value = true
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add new note",
                                    tint = Purple40,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        },
                    )
                }) {
                if (showDialogState.value) {
                    NewNoteDialog(showDialogState, notesState)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    NoteList()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Network.fetchNotes(lectureCode, notesState)
    }


    @Composable
    fun NoteList() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            for (i in notesState.value.indices) {
                NoteRow(notesState.value[i], i)
            }

        }

    }

    @Composable
    fun NoteRow(note: Note, noteIndex: Int) {
        OutlinedButton(
            onClick = {
                Intent(this, NoteDetailActivity::class.java).also {
                    it.putExtra("noteTitle", note.title)
                    it.putExtra("noteContent", note.content)
                    it.putExtra("lectureCode", lectureCode)
                    it.putExtra("noteIndex", noteIndex)
                    startActivity(it)
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .border(1.dp, Purple40, RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = note.title,
                fontSize = 16.sp,
                color = Purple30,
                modifier = Modifier.padding(12.dp),
            )
        }
    }

    @Composable
    fun NewNoteDialog(
        showDialogState: MutableState<Boolean>,
        notesState: MutableState<MutableList<Note>>
    ) {
        val enteredTextState = remember {
            mutableStateOf("")
        }
        Dialog(
            onDismissRequest = { showDialogState.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(16.dp, 8.dp, 16.dp, 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { showDialogState.value = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Enter Note Name", textAlign = TextAlign.Center, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = enteredTextState.value,
                            placeholder = {
                                Text(text = "Max 16 characters")
                            },
                            maxLines = 1,
                            onValueChange = {
                                if (it.length <= 16) enteredTextState.value = it
                                else Toast.makeText(
                                    this@NoteListActivity,
                                    "Max 16 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                showDialogState.value = false
                                notesState.value = notesState.value.toMutableList().also {
                                    var noteName = "New Note"
                                    if (enteredTextState.value.isNotEmpty()) {
                                        noteName = enteredTextState.value
                                    }
                                    val newNote = Note(noteName, "")
                                    it.add(0, newNote)
                                    Network.postNewNote(newNote, lectureCode)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}
