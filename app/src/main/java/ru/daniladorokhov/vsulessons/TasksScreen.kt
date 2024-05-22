package ru.daniladorokhov.vsulessons

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.daniladorokhov.vsulessons.ui.theme.VSULessonsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavHostController){
    var courses = arrayListOf(
        Course("Введение в программирование", stringResource(id = R.string.lorem_ipsum), "Дорохов Д. И.", arrayListOf("Программирование", "IT", "Java"), true),
        Course("1С", stringResource(id = R.string.lorem_ipsum), "Иванов И. И.", arrayListOf("Программирование", "Бухгалтерия", "Боль", "Страдания"), false),
        Course("C++ для начинающих", stringResource(id = R.string.lorem_ipsum), "Петров П. С.", arrayListOf("Программирование", "IT", "Смерть"), true),
        Course("Kotlin. Основы", stringResource(id = R.string.lorem_ipsum), "Дорохов Д. И.", arrayListOf("Программирование", "IT"), true),
        Course("Тестирование мобильных приложений", stringResource(id = R.string.lorem_ipsum), "Тест Е. Р.", arrayListOf("Тестирование", "IT"), false),
    )
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(id = R.string.app_name))
            }
        )
    }){ paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item{
                TaskItem(courseName = courses[0].name, arrayListOf(Task("Задание 1", "Изучить переменные в Java"), Task("Задание 2", "Написать калькулятор"))) {
                    navController.navigate("course_details/0")
                }
            }
            item{
                TaskItem(courseName = courses[3].name, arrayListOf(Task("Контрольная", "Подключиться на контрольную в 14:00 в Zoom"))) {
                    navController.navigate("course_details/3")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(courseName: String, tasks: List<Task>, onClick: () -> Unit){
    Card(onClick = { onClick() }, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background
    ), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = courseName, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
            for (task in tasks){
                Text(text = task.taskName, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                Text(text = task.taskText)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TasksScreenPreview() {
    VSULessonsTheme {
        TasksScreen(rememberNavController())
    }
}

data class Task(var taskName: String, var taskText: String)