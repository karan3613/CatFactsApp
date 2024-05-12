package com.example.catfacts

import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.Data.CatFacts
import com.example.catfacts.ui.theme.CATFACTSTheme
import com.example.catfacts.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CATFACTSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context =  LocalContext.current
                    var facts by remember{
                       mutableStateOf(CatFacts())
                   }
                    val scope  = rememberCoroutineScope()
                    LaunchedEffect(key1 = true){
                        scope.launch(Dispatchers.IO){
                            val response = try{
                                RetrofitInstance.api.getRandomFact()

                            }catch (e:HttpException){
                                Toast.makeText(context , "http error"+e.message , Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                            catch (e: IOException){
                                Toast.makeText(context , "http error"+e.message , Toast.LENGTH_SHORT).show()
                                return@launch
                            }
                            if(response.isSuccessful && response.body() != null){
                                withContext(Dispatchers.Main){}
                                facts = response.body()!!
                            }
                        }
                    }
                    MainScreen(facts)

                }
            }
        }
    }
}

@Composable
fun MainScreen(facts: CatFacts) {


    Box(modifier = Modifier.fillMaxSize().background(Color.Cyan) ,
        contentAlignment = Alignment.Center
        ){
        Text(
            text =  facts.fact ,
            fontWeight = FontWeight.SemiBold ,
            color =  Color.Black ,
            fontSize = 25.sp ,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )
    }
}



