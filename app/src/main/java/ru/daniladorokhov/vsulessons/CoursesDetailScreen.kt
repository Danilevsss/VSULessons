package ru.daniladorokhov.vsulessons

import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
fun CoursesDetailScreen(navController: NavHostController, id: Int = 0){
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
        var course = courses[id]
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = course.name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = course.teacherName,
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                LazyRow(modifier = Modifier.padding(8.dp)) {
                    items(course.tags) {
                        Text(
                            text = it, modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(4.dp)
                        )
                    }
                }
                Text(text = course.description, modifier = Modifier.padding(8.dp))
            }
            items(3){
                CoursePart(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursePart(id: Int){
    Card(onClick = { }, modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 6.dp)
        .fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background
    ), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Тема $id")
            Row {
                LinearProgressIndicator(
                    progress = 0.1f * id, modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, end = 8.dp)
                        .height(8.dp)
                )
                Text(text = "${10*id} %")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoursePartPreview() {
    VSULessonsTheme {
        CoursePart(0)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoursesDetailScreenPreview() {
    VSULessonsTheme {
        CoursesDetailScreen(rememberNavController())
    }
}