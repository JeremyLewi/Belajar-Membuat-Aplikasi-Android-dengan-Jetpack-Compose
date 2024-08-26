package com.example.jetgarage.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetgarage.di.Injection
import com.example.jetgarage.model.OrderCar
import com.example.jetgarage.ui.ViewModelFactory
import com.example.jetgarage.ui.common.UiState
import com.example.jetgarage.ui.components.CarItem
import com.example.jetgarage.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {


    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCars()
            }
            is UiState.Success -> {
                HomeContent(
                    orderCar = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderCar: List<OrderCar>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {

    val query by viewModel.query

    Column(modifier = modifier) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("CarList")

        ) {

            items(orderCar) { data ->

                CarItem(
                    image = data.car.image,
                    title = data.car.title,
                    requiredPrice = data.car.requiredPrice,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.car.id)
                    }
                )
            }
        }

    }


}