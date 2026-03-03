package dev.liau.kmppractice.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
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
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.liau.kmppractice.Greeting
import kmppractice.composeapp.generated.resources.Res
import kmppractice.composeapp.generated.resources.broken_image_24dp
import kmppractice.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable

fun ScreenComponent(modifier: Modifier) {
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
            "Button ",
            style = MaterialTheme.typography.titleLarge,
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
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
            TextButton(onClick = { }) {
                Text("Text Button")
            }
        }
        /**
         * Image Section
         */
        Text(
            text = "Image Loading",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Network Image using Coil Library compose ",
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            repeat(5) { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://picsum.photos/id/${30 + index}/200/300")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "example image",
                    error = painterResource(Res.drawable.broken_image_24dp),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )

            }
            repeat(4) { index ->
                SubcomposeAsyncImage(
                    model =  ImageRequest.Builder(LocalPlatformContext.current)
                        .data("https://picsum.photos/id/${40 + index}/200/300")
                        .crossfade(true)
                        .build(),
                    contentDescription = "example image",

                ) {
                    val state by painter.state.collectAsState()
                    if (state is AsyncImagePainter.State.Success) {
                        SubcomposeAsyncImageContent(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(shape = MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop,
                        )
                    } else if(state is AsyncImagePainter.State.Error){
                        Image(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(shape = MaterialTheme.shapes.medium),
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = "error image"
                        )
                    }else {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(shape = MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center

                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }
        Text(
            "Card ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(100.dp)
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
                .size(100.dp)
        ) {
            Text(
                text = "Outlined",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }

        Text(
            "Animation ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Button(onClick = {
            showContent = !showContent
            coroutineScope.launch {
                if (showContent) {
                    delay(500)
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

@Preview(
    showBackground = true
)

@Composable
fun ScreenComponentPreview() {
    // Provide sample data for the preview
    ScreenComponent(modifier = Modifier)
}