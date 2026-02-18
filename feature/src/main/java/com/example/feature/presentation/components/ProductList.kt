package com.example.feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Product
import com.example.feature.R
import com.example.feature.presentation.preview.CartSampleData

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        products.forEach { product ->
            ProductCard(product = product)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductListPreview() {
    MaterialTheme {
        ProductList(
            products = CartSampleData.products.take(3),
            modifier = Modifier.padding(16.dp)
        )
    }
}
