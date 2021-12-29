package com.example.myapp2


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.myapp2.ProfileScreen
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable

import androidx.compose.runtime.*
import coil.compose.rememberImagePainter
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

import kotlinx.serialization.Serializable


@Serializable
data class Item(
    val id: Int,
    val title: String,
    val sex: String,
    val age: Int,
    val description: String,
    val dogImage: String,
)

enum class Sex{ MALE, FEMALE }


class ItemsViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    init {
        getAll()
    }
    private fun getAll() {
        viewModelScope.launch {
            try {
                val response = client.get<List<Item>>("$BASE_URL/dogs")
                _items.update { response }
            } catch (e: Exception) {
                _items.update { emptyList() }
            }
        }
    }
    fun getById(id: Int) = items.value.first { it.id == id }

    companion object {
        const val BASE_URL = "https://usersdogs-ip4weamexa-ew.a.run.app"
    }
}


@Composable
fun ItemRow(item: Item, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)
        .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(5.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = "${ItemsViewModel.BASE_URL}${item.dogImage}"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .absolutePadding(right = 10.dp, left = 10.dp, top = 10.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(15.dp)),
            )
            Column(
                modifier = Modifier.absolutePadding(left = 5.dp)
            ) {
                Text(
                    item.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary)
                Text(
                    item.sex.lowercase().replaceFirstChar{ it.uppercase() },
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onPrimary)
            }
        }
    }

}


@Composable
fun ItemsComponent(itemsFlow: Flow<List<Item>>, onSelected: (Item) -> Unit) {
    val items by itemsFlow.collectAsState(initial = emptyList())
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ){
        items(items) {
                ItemRow(item = it, onClick = { onSelected(it) })
        }
    }
}


@Composable
fun MainScreen() {
    MyAppTheme{
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colors.surface
        ) {
            var selectedId by rememberSaveable { mutableStateOf<Int?>(null) }
            val viewModel = ItemsViewModel()
            Crossfade(targetState = selectedId) { id ->
                if (id == null) {
                    ItemsComponent(
                        itemsFlow = viewModel.items,
                        onSelected = { selectedId = it.id }
                    )
                } else {
                    ProfileScreen(item = viewModel.getById(id))
                    BackHandler {
                        selectedId = null
                    }
                }
            }
        }
    }
}

