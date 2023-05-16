import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pethelper.R
import com.example.pethelper.ui.theme.Bisque1
import com.example.pethelper.ui.theme.Bisque4

@Composable
fun TopNav() {
    TopAppBar(
        title = { Text(text = "PETHELPER", textAlign = TextAlign.Center) },
        actions = {
            IconButton(onClick = {}) {
                Icon(modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search"
                )
            }
        }, elevation = 8.dp,
        backgroundColor = Bisque1
    )
}
