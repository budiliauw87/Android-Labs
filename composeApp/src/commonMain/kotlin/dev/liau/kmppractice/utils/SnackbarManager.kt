import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

data class CustomSnackbarVisuals(
    override val message: String,
    override val actionLabel: String?,
    override val withDismissAction: Boolean,
    override val duration: SnackbarDuration,
) : SnackbarVisuals

object SnackbarManager{

    data class SnackbarEvent(
        val visuals: SnackbarVisuals,
        val result: CompletableDeferred<SnackbarResult>
    )

    private val events = Channel<SnackbarEvent>(Channel.BUFFERED)
    val snackbarEvents: Flow<SnackbarEvent> = events.receiveAsFlow()
    suspend fun showSnackBar(snackbarVisuals: SnackbarVisuals) : SnackbarResult{
        val deferred = CompletableDeferred<SnackbarResult>()
        events.send(SnackbarEvent(snackbarVisuals, deferred))
        return deferred.await()
    }
}
