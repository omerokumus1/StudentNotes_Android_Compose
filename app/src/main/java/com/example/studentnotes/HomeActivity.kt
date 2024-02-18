package com.example.studentnotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentnotes.C.lectures
import com.example.studentnotes.ui.theme.Purple30
import com.example.studentnotes.ui.theme.Purple40
import com.google.firebase.Firebase
import com.google.firebase.database.database

class HomeActivity : ComponentActivity() {

    private val searchTextState = mutableStateOf("")
    private val lectureState = mutableStateOf(lectures)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopBar(scrollBehavior)
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    SearchArea()
                    Spacer(modifier = Modifier.height(20.dp))
                    LectureList()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(scrollBehavior: TopAppBarScrollBehavior) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Home")
            },
            actions = {
                IconButton(
                    onClick = {
                        Intent(
                            this@HomeActivity,
                            ProfileActivity::class.java
                        ).also { startActivity(it) }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Localized description",
                        tint = Purple40,
                        modifier = Modifier.size(36.dp)
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    fun SearchArea() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            OutlinedTextField(
                value = searchTextState.value,
                modifier = Modifier.weight(5f),
                placeholder = { Text(text = "Search Lecture") },
                onValueChange = { searchTextState.value = it },
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    lectureState.value =
                        lectures.filter { it.toString().contains(searchTextState.value, true) }
                },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Purple40, RoundedCornerShape(8.dp)),
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search button",
                    tint = Purple40,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    @Composable
    fun LectureList() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            for (lecture in lectureState.value) {
                LectureRow(lecture)
            }
        }
    }

    @Composable
    fun LectureRow(lecture: Lecture) {
        OutlinedButton(
            onClick = {
                Intent(this@HomeActivity, NoteListActivity::class.java).also {
                    it.putExtra("lectureCode", lecture.code)
                    it.putExtra("lectureName", lecture.toString())
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
                text = lecture.toString(),
                fontSize = 16.sp,
                color = Purple30,
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
