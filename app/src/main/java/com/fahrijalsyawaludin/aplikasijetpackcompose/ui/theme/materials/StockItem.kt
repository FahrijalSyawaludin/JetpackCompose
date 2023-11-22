package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.materials

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fahrijalsyawaludin.aplikasijetpackcompose.R
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.AplikasiJetpackComposeTheme
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.Shapes

@Composable
fun StockItem(
    productId: Long,
    image: Int,
    title: String,
    totalPoint: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(Shapes.medium)
            .clickable { }
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.medium)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text(
                text = stringResource(
                    R.string.required_point,
                    totalPoint
                ),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        ProductCount(
            orderId = productId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(productId, count + 1) },
            onProductDecreased = { onProductCountChanged(productId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StockItemPreview() {
    AplikasiJetpackComposeTheme {
        StockItem(
            4, R.drawable.t11, "Sensor Pelindung Kaki Merk Adidas", 4000, 0,
            onProductCountChanged = { productId, count -> },
        )
    }
}