package com.example.myapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter


//@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun ProfileScreen(item: Item) {
    MyAppTheme {
        Surface(
            color = MaterialTheme.colors.surface,
        ) {
            Scaffold(
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        icon = { Icon(Icons.Outlined.Phone,"") },
                        text = { Text("Adopt me") },
                        onClick = { /*TODO*/ },
                        backgroundColor = MaterialTheme.colors.primary,
                    )
                },
                content = {
                    Column() {
                        Image(
                            painter = rememberImagePainter(data = "${ItemsViewModel.BASE_URL}${item.dogImage}"),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Column(modifier = Modifier.padding(15.dp)) {
                            Text(
                                item.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onPrimary,
                            )
                            Divider(
                                color = Color.DarkGray,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                "Sex",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.secondary
                            )
                            Text(
                                item.sex.lowercase().replaceFirstChar{ it.uppercase() },
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.onPrimary,
                            )
                            Divider(
                                color = Color.DarkGray,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                "Age",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.secondary
                            )
                            Text(
                                item.age.toString(),
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.onPrimary,
                            )
                            Divider(
                                color = Color.DarkGray,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                "Personality",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.secondary
                            )
                            Text(
                                item.description,
                                fontSize = 15.sp,
                                color = MaterialTheme.colors.onPrimary,
                            )
                        }
                    }
                }
            )
        }
    }
}
