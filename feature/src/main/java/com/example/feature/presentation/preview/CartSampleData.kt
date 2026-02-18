package com.example.feature.presentation.preview

import com.example.domain.model.Cart
import com.example.domain.model.Product

/**
 * Sample data for Cart previews and tests
 * Based on carts.json from data module
 */
object CartSampleData {
    
    val products = listOf(
        Product(
            id = "prod-beer-001",
            name = "Budweiser Lata 350ml",
            price = 3.99,
            imageUrl = "https://images.unsplash.com/photo-1608270586620-248524c67de9?w=400",
            quantity = 12
        ),
        Product(
            id = "prod-beer-002",
            name = "Corona Extra Long Neck 330ml",
            price = 5.49,
            imageUrl = "https://images.unsplash.com/photo-1618885472179-5e474019f2a9?w=400",
            quantity = 6
        ),
        Product(
            id = "prod-beer-003",
            name = "Stella Artois Garrafa 550ml",
            price = 7.99,
            imageUrl = "https://images.unsplash.com/photo-1535958636474-b021ee887b13?w=400",
            quantity = 24
        ),
        Product(
            id = "prod-snack-001",
            name = "Amendoim Japonês 500g",
            price = 12.90,
            imageUrl = "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?w=400",
            quantity = 3
        ),
        Product(
            id = "prod-beer-004",
            name = "Heineken Lata 269ml",
            price = 4.29,
            imageUrl = "https://images.unsplash.com/photo-1612528443702-f6741f70a049?w=400",
            quantity = 48
        ),
        Product(
            id = "prod-beer-005",
            name = "Brahma Duplo Malte 350ml",
            price = 3.49,
            imageUrl = "https://images.unsplash.com/photo-1536935338788-846bb9981813?w=400",
            quantity = 18
        ),
        Product(
            id = "prod-water-001",
            name = "Água Mineral 500ml",
            price = 1.99,
            imageUrl = "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=400",
            quantity = 36
        ),
        Product(
            id = "prod-soda-001",
            name = "Coca-Cola Lata 350ml",
            price = 2.99,
            imageUrl = "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400",
            quantity = 24
        ),
        Product(
            id = "prod-snack-002",
            name = "Batata Chips 150g",
            price = 8.49,
            imageUrl = "https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=400",
            quantity = 10
        ),
        Product(
            id = "prod-snack-003",
            name = "Pipoca Gourmet 100g",
            price = 6.99,
            imageUrl = "https://images.unsplash.com/photo-1578849278619-e73505e9610f?w=400",
            quantity = 8
        )
    )
    
    val carts = listOf(
        Cart(
            id = "cart-001",
            products = listOf(
                products[0], // Budweiser
                products[1], // Corona
                products[2], // Stella
                products[3]  // Amendoim
            )
        ),
        Cart(
            id = "cart-002",
            products = listOf(
                products[4], // Heineken
                products[5], // Brahma
                products[6], // Água
                products[7]  // Coca-Cola
            )
        ),
        Cart(
            id = "cart-003",
            products = listOf(
                products[0], // Budweiser
                products[4], // Heineken
                products[8], // Batata Chips
                products[9]  // Pipoca
            )
        )
    )
    
    val product get() = products[0]
    val cart get() = carts[0]
    val emptyCart get() = Cart(id = "cart-empty", products = emptyList())
}
