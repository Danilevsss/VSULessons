package ru.daniladorokhov.vsulessons

import android.content.SharedPreferences
import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.daniladorokhov.vsulessons.ui.theme.VSULessonsTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController, sPref: SharedPreferences? = null){
    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }
    var passwordsEquals by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.Center, modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {
        TextField(value = login, onValueChange = { login = it}, label = {Text(text = "Логин")}, modifier = Modifier.fillMaxWidth())
        AnimatedVisibility(visible = !isLogin) {
            TextField(value = email, onValueChange = { email = it }, label = { Text(text = "Email") }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
            )
        }
        AnimatedVisibility(visible = !isLogin) {
            TextField(value = phone, onValueChange = { phone = it }, label = { Text(text = "Телефон") }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
            )
        }
        TextField(value = password, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), visualTransformation = PasswordVisualTransformation(), onValueChange = {password = it; passwordsEquals = true}, label = {Text(text = "Пароль")}, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp))

        AnimatedVisibility(visible = !isLogin) {
            Row(verticalAlignment = Alignment.CenterVertically){
                TextField(value = password2,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { password2 = it; passwordsEquals = true },
                    label = { Text(text = "Повторить пароль") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp)
                )
                AnimatedVisibility(visible = !passwordsEquals) {
                    Text(text = "Пароли\nне совпадают", color = Color.Red)
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
            Button(onClick = {
                if(isLogin){
                    sPref?.edit()?.putBoolean("loggedIn", true)?.apply()
                    navController.popBackStack()
                } else {
                    isLogin = true
                }
            }, modifier = Modifier
                .weight(if(isLogin) 3f else 2f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isLogin) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(text = "Вход")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if(!isLogin){
                    if(password.equals(password2)) {
                        sPref?.edit()?.putBoolean("loggedIn", true)?.apply()
                        navController.popBackStack()
                    } else {
                        passwordsEquals = false
                    }
                } else {
                    isLogin = false
                }
            }, modifier = Modifier
                .weight(if(isLogin) 2f else 3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(!isLogin) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                )
            )
            {
                Text(text = "Регистрация")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    VSULessonsTheme {
        LoginScreen(navController = rememberNavController())
    }
}