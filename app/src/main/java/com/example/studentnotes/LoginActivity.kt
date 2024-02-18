package com.example.studentnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database

class LoginActivity : ComponentActivity() {
    private val studentNumberState = mutableStateOf("20191308013")
    private val passwordState = mutableStateOf("12345678")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WelcomeText()
                StudentNumberInput(studentNumberState)
                Spacer(modifier = Modifier.height(20.dp))
                PasswordInput(passwordState)
                Spacer(modifier = Modifier.height(20.dp))
                LoginButton()
            }
        }
    }

    @Composable
    fun WelcomeText() {
        Text(
            text = "Welcome to Student Notes",
            style = TextStyle(fontSize = 24.sp, color = Color(0xFF7359B0)),
            modifier = Modifier.offset(0.dp, (-100).dp)
        )
    }

    @Composable
    fun StudentNumberInput(state: MutableState<String>) {
        OutlinedTextField(
            value = state.value,
            placeholder = {
                Text(text = "Student Number")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                state.value = it
            })
    }

    @Composable
    fun PasswordInput(state: MutableState<String>) {
        OutlinedTextField(
            value = state.value,
            placeholder = {
                Text(text = "Password")
            },
            visualTransformation =  PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = {
                state.value = it
            }
        )
    }

    @Composable
    fun LoginButton() {
        val context = this
        Button(
            onClick = {
                if (isCredentialsValid()) {
                    C.loggedInStudent = studentNumberState.value
                    Intent(context, HomeActivity::class.java).also {
                        context.startActivity(it)
                    }
                } else {
                    Toast.makeText(context, "Wrong Credentials!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = "Login")
        }
    }

    private fun isCredentialsValid(): Boolean {
        for (s in C.registeredStudents) {
            if (s.studentNo == studentNumberState.value && s.password == passwordState.value) {
                return true
            }
        }
        return false
    }

}


