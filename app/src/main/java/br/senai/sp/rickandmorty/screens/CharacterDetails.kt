package br.senai.sp.rickandmorty.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.model.Episode
import br.senai.sp.rickandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getEpisodeIds(character: Character): List<Int> {
    val episodeIds = mutableListOf<Int>()
    character.episodes.forEach { episodeUrl->
        val episodeId = episodeUrl.substringAfterLast("/").toIntOrNull()
        if (episodeId != null) {
            episodeIds.add(episodeId)
        }
    }
    return episodeIds
}

@Composable
fun CharacterDetails(characterId: Int) {
    var character by remember {
        mutableStateOf(Character())
    }
    val callCharacter = RetrofitFactory()
        .getCharacterService()
        .getCharacterById(characterId)

    callCharacter.enqueue(object : Callback<Character> {
        override fun onResponse(p0: Call<Character>, response: Response<Character>) {
            if (response.code() == 404) {
                character = Character()
            } else {
                character = response.body()!!
            }
        }

        override fun onFailure(p0: Call<Character>, p1: Throwable) {
        }
    })
    var episodes = getEpisodeIds(character)

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFF72BCDD)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .size(120.dp),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = ""
                )
            }
            Text(text = "Name: " + character.name)
            Text(text = "Specie: " + character.species)

            Text(text = "EPISODES")

            LazyColumn {
                items(episodes) { id ->
                    var episode = remember {
                        mutableStateOf(Episode())
                    }

                    val callEpisodeById = RetrofitFactory()
                        .getCharacterService()
                        .getEpisodeById(id) //

                    callEpisodeById.enqueue(object : Callback<Episode> {
                        override fun onResponse(p0: Call<Episode>, response: Response<Episode>) {
                            Log.i("ENQUEUE", episode.value.toString())
                            episode.value = response.body()!!
                        }

                        override fun onFailure(p0: Call<Episode>, p1: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                    Text(text = episode.value.name)
                }
            }
        }
    }
}