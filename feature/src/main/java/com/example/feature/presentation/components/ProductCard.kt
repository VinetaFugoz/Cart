package com.example.feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.SingletonImageLoader
import com.example.domain.model.Product
import com.example.feature.R
import com.example.feature.presentation.preview.CartSampleData
import java.util.Locale

private const val TAG = "ProductImage"

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_elevation)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImage(
                imageUrl = product.imageUrl,
                contentDescription = product.name,
                imageLoader = SingletonImageLoader.get(LocalContext.current)
            )

            ProductInfo(
                name = product.name,
                quantity = product.quantity,
                modifier = Modifier.weight(1f)
            )

            ProductPrice(price = product.price)
        }
    }
}

@Composable
private fun ProductImage(
    imageUrl: String,
    contentDescription: String,
    imageLoader: ImageLoader
) {
    AsyncImage(
        model = imageUrl.ifBlank { null },
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = Modifier
            .size(dimensionResource(R.dimen.product_image_size))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.product_image_corner_radius)))
            .background(MaterialTheme.colorScheme.surface),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_placeholder),
        error = painterResource(R.drawable.ic_placeholder),
        onSuccess = { Log.d(TAG, "Image loaded: $imageUrl") },
        onError = { state ->
            Log.e(TAG, "Image failed: $imageUrl", state.result.throwable)
        }
    )
}

@Composable
private fun ProductInfo(
    name: String,
    quantity: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.product_quantity, quantity),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProductPrice(price: Double) {
    Text(
        text = formatPrice(price),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

private fun formatPrice(price: Double): String {
    return "$${String.format(Locale.getDefault(), "%.2f", price)}"
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    MaterialTheme {
        ProductCard(product = CartSampleData.product)
    }
}
