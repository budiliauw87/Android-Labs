package dev.liau.kmppractice.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.liau.kmppractice.Greeting
import kmppractice.composeapp.generated.resources.Res
import kmppractice.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun ScreenComponent( modifier: Modifier) {
        var showContent by remember { mutableStateOf(false) }
        // Creates and remembers the scroll state across recompositions
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState),
        ) {

            Text(
                "Button Material",
                )
            HorizontalDivider(modifier= Modifier.padding(vertical = 8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

            ){
                Button(onClick = { /* todo */ }) {
                    Text("Filled")
                }
                FilledTonalButton(onClick = { /* todo */ }) {
                    Text("Tonal")
                }
                OutlinedButton(onClick = { /* todo */ }) {
                    Text("Outlined")
                }
                ElevatedButton(onClick = { /* todo */ }) {
                    Text("Elevated")
                }
                TextButton(onClick = {  }) {
                    Text("Text Button")
                }
            }

            Text("Image Loading")
            HorizontalDivider(modifier= Modifier.padding(vertical = 8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                repeat(6) { index ->
                    AsyncImage(
                        model = "https://picsum.photos/id/${1000 + index}/200/300",
                        contentDescription = "example image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                            .clip(shape = if (index % 2 == 0) CircleShape else RoundedCornerShape(8.dp))
                    )

                }
            }
            Text("Card ",modifier = Modifier.padding(top = 8.dp))
            HorizontalDivider(modifier= Modifier.padding(vertical = 8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .size( 100.dp)
            ) {
                Text(
                    text = "Filled",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .size(100.dp)
            ) {
                Text(
                    text = "Elevated",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .size( 100.dp)
            ) {
                Text(
                    text = "Outlined",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Text("Animation ",modifier = Modifier.padding(top = 8.dp))
            HorizontalDivider(modifier= Modifier.padding(vertical = 8.dp))

            Button(onClick = {
                showContent = !showContent
                coroutineScope.launch {
                    if( showContent){
                        delay(200)
                        // 3. Animate to a specific pixel value
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                }

            }) {
                Text("Toggle Content")
            }


            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }





        }
}