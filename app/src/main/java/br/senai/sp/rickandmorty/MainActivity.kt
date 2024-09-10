package br.senai.sp.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.rickandmorty.screens.CharacterDetails
import br.senai.sp.rickandmorty.screens.ListAllCharacters
import br.senai.sp.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val controleDeNavegacao = rememberNavController()
                    
                    NavHost(
                        navController = controleDeNavegacao,
                        startDestination = "all") {

                        composable("all"){ ListAllCharacters(controleDeNavegacao)}
                        composable("characterDetails/{characterId}",
                            arguments = listOf(navArgument("characterId") {type = NavType.IntType})
                        ){ backStackEntry ->
                            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                            CharacterDetails(characterId)
                        }
                    }
                }
            }
        }
    }
}
