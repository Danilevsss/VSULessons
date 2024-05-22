package ru.daniladorokhov.vsulessons

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.daniladorokhov.vsulessons.ui.theme.VSULessonsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(navController: NavHostController, sPref: SharedPreferences? = null){
    var loggedIn by remember { mutableStateOf(sPref?.getBoolean("loggedIn", false) ?: true) }
    var courses = arrayListOf(
        Course("Введение в программирование", stringResource(id = R.string.lorem_ipsum), "Дорохов Д. И.", arrayListOf("Программирование", "IT", "Java"), true && loggedIn),
        Course("1С", stringResource(id = R.string.lorem_ipsum), "Иванов И. И.", arrayListOf("Программирование", "Бухгалтерия", "Боль", "Страдания"), false && loggedIn),
        Course("C++ для начинающих", stringResource(id = R.string.lorem_ipsum), "Петров П. С.", arrayListOf("Программирование", "IT", "Ужас"), true && loggedIn),
        Course("Kotlin. Основы", stringResource(id = R.string.lorem_ipsum), "Дорохов Д. И.", arrayListOf("Программирование", "IT"), true && loggedIn),
        Course("Тестирование мобильных приложений", stringResource(id = R.string.lorem_ipsum), "Тест Е. Р.", arrayListOf("Тестирование", "IT"), false && loggedIn),
    )
    val searchText = remember { mutableStateOf("") }
    var showSubscribed by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(id = R.string.app_name))
            },
            actions = {
                if(!loggedIn) {
                    Icon(
                        Icons.AutoMirrored.Filled.Login,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigate("login")
                            }
                    )
                }else{
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigate("profile")
                            }
                    )
                    Icon(
                        if(showSubscribed) Icons.Default.Bookmarks else Icons.Outlined.Bookmarks,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .clickable {
                                showSubscribed = !showSubscribed
                            }
                    )
                }
                Icon(
                    Icons.Default.FilterAlt,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable {

                        }
                )
                if(loggedIn) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigate("tasks")
                            }
                    )
                }
            }
        )
    }) { paddingValues->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchView(text = searchText)
            LazyColumn{
                var filteredCourses = if (searchText.value.equals("")) {
                    if(showSubscribed && loggedIn){
                        courses.filter { it.subscribed }
                    }else {
                        courses
                    }
                } else courses.filter {
                    it.name.lowercase()
                        .contains(searchText.value.lowercase()) || it.description.lowercase()
                        .contains(searchText.value.lowercase()) || it.teacherName.lowercase()
                        .contains(searchText.value.lowercase()) || it.tags.any {
                        it.lowercase().contains(searchText.value.lowercase())
                    }
                }
                items(filteredCourses) { lesson ->
                    CourseItem(course = lesson, onClick = {
                        navController.navigate("course_details/${courses.indexOf(lesson)}")
                    })
                }
            }
        }
    }
}

@Composable
fun SearchView(text: MutableState<String>) {
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (text.value != "") {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            text.value = ""
                        }
                )
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseItem(course: Course, onClick: () -> Unit){
    Card(onClick = { onClick() }, modifier = Modifier.padding(8.dp).fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background
    ), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = course.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                if(course.subscribed) {
                    Icon(
                        Icons.Default.Bookmark,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                            .clickable {

                            }
                    )
                }
            }
            Text(text = course.teacherName)
            LazyRow {
                items(course.tags){
                    Text(text = it, modifier = Modifier
                        .padding(4.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                    )
                }
            }
            Text(text = course.description)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoursesScreenPreview() {
    VSULessonsTheme {
        CoursesScreen(rememberNavController())
    }
}

data class Course(var name: String, var description: String, var teacherName: String, var tags: List<String>, var subscribed: Boolean)