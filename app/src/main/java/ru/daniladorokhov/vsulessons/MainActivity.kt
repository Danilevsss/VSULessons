package ru.daniladorokhov.vsulessons

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.daniladorokhov.vsulessons.ui.theme.VSULessonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VSULessonsTheme {
                var navController = rememberNavController()
                var sPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(navController = navController, modifier = Modifier.padding(innerPadding), sPref)
                }
            }
        }
    }
}



@Composable
fun Main(navController: NavHostController, modifier: Modifier = Modifier, sPref: SharedPreferences?){
    NavHost(navController = navController, startDestination = "courses") {
        composable("login") { LoginScreen(modifier = modifier, navController = navController, sPref = sPref) }
        composable("courses") { CoursesScreen(navController = navController, sPref = sPref) }
        composable("course_details/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            CoursesDetailScreen(navController = navController, id = backStackEntry.arguments?.getInt("id") ?: 0)
        }
        composable("profile") { ProfileScreen(navController = navController, sPref = sPref) }
        composable("tasks") { TasksScreen(navController = navController) }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    VSULessonsTheme {
        Main(navController = rememberNavController(), sPref = null)
    }
}