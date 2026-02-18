package com.example.feature.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Cart
import com.example.feature.R
import com.example.feature.presentation.preview.CartSampleData

@Composable
fun CartCard(
    cart: Cart,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.padding(dimensionResource(R.dimen.spacing_large))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                CartHeader(
                    cartId = cart.id,
                    productCount = cart.products.size,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.delete_button_offset))
                )
                
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))
                
                ProductList(products = cart.products)
            }
            
            IconButton(
                onClick = { onDeleteClick(cart.id) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.cart_item_remove_button),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun CartHeader(
    cartId: String,
    productCount: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.cart_item_title, cartId),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))
        Text(
            text = stringResource(R.string.cart_item_products_count, productCount),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CartCardPreview() {
    MaterialTheme {
        CartCard(
            cart = CartSampleData.cart,
            onDeleteClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
