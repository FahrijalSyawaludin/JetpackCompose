package com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.inside.home.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fahrijalsyawaludin.aplikasijetpackcompose.R
import com.fahrijalsyawaludin.aplikasijetpackcompose.ViewModel
import com.fahrijalsyawaludin.aplikasijetpackcompose.injection.Injection
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.AplikasiJetpackComposeTheme
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.materials.OrderButton
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.materials.ProductCount
import com.fahrijalsyawaludin.aplikasijetpackcompose.ui.theme.state.UiState

@Composable
fun DetailProductScreen(
    productId: Long,
    viewModel: DetailProductViewModel = viewModel(
        factory = ViewModel(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getProductById(productId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.product.image,
                    data.product.title,
                    data.product.description,
                    data.product.requiredPoint,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToStock(data.product, count)
                        navigateToCart()
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    description: String,
    basePoint: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = stringResource(R.string.required_point, basePoint),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.LightGray)
            )
        }

        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ProductCount(
                    1,
                    orderCount,
                    onProductIncreased = { orderCount++ },
                    onProductDecreased = { if (orderCount > 0) orderCount-- },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                totalPoint = basePoint * orderCount
                OrderButton(
                    text = stringResource(R.string.add_to_cart, totalPoint),
                    enabled = orderCount > 0,
                    onClick = {
                        onAddToCart(orderCount)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    AplikasiJetpackComposeTheme {
        DetailContent(
            R.drawable.t11,
            "Sensor Pelindung Kaki Merk Adidas",
            "Untuk melindungi kaki dan mencetak skor dengan otomatis dikarenakan ada sensor yang tersambung di kaki dan akan menampilkan skor di layar dengan otomatis",
            7500,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}
