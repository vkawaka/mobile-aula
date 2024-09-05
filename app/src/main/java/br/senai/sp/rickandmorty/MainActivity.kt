package br.senai.sp.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import br.senai.sp.rickandmorty.model.Character
import br.senai.sp.rickandmorty.service.RetrofitFactory
import br.senai.sp.rickandmorty.ui.theme.RickAndMortyTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                CharacterDetails()
            }
        }
    }
}

@Composable
fun CharacterDetails(modifier: Modifier = Modifier) {
    var id by remember {
        mutableStateOf("")
    }
    var character by remember {
        mutableStateOf(Character())
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFF52DD6F)
    ) {
        Column (
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = id,
                onValueChange = {id=it},
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        val callCharacter = RetrofitFactory()
                            .getCharacterService()
                            .getCharacterById(id.toInt())

                        callCharacter.enqueue(object : Callback<Character>{
                            override fun onResponse(p0: Call<Character>, response: Response<Character>) {
                                if(response.code() == 404){
                                    character = Character()
                                }else{
                                    character = response.body()!!
                                }
                            }

                            override fun onFailure(p0: Call<Character>, p1: Throwable) {
                                TODO("Not yet implemented")
                            }
                        })
                    }) {

                    }
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
                )
            Spacer(modifier = Modifier.height(32.dp))
            Card (
                modifier = Modifier
                    .size(120.dp),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = "")
            }
            Text(text = "Name: " + character.name)
            Text(text = "Specie: " + character.species)
        }
    }
}

@Preview
@Composable
private fun Preview(){
    CharacterDetails()
}