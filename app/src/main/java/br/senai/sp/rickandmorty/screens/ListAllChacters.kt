package br.senai.sp.rickandmorty.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.model.Result
import br.senai.sp.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListAllCharacters(controleDeNavegacao: NavHostController) {
    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    //Executar a requisição para a API
    val callCharacterList = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    callCharacterList.enqueue(
        object : Callback<Result>{
            override fun onResponse(p0: Call<Result>, response: Response<Result>) {
                characterList = response.body()!!.results
            }

            override fun onFailure(p0: Call<Result>, p1: Throwable) {
            }
        }
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color =  Color(0xFF0E2844)
    ) {
        Column (
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Text(text ="Ricky and Morty API",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
                )
            LazyColumn {
               items(characterList){
                   CharacterCard(controleDeNavegacao ,it)
               }
            }
        }
    }
}

@Composable
fun CharacterCard(controleDeNavegacao: NavHostController, character: Character){
    Card(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable(
                onClick = {
                    controleDeNavegacao.navigate("characterDetails/${character.id}")
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF72BCDD)
        ),

    ) {
        Row {
          Card (
              modifier = Modifier.size((100.dp)),
              colors = CardDefaults.cardColors(
                  containerColor = Color.Magenta
              )
          ) {
              AsyncImage(
                  model = character.image ,
                  contentDescription = "")
          }
            Column (
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = character.name,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                    )
                Text(text = character.species,
                    color = Color(0xFFE2EBEE)
                    )
            }
        }
    }
}

@Preview
@Composable
private fun ListAllCharactersPreview(){
    //ListAllCharacters(controleDeNavegacao)
}