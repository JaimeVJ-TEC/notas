package com.tec.appnotas.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle

@Composable
fun DrawerHead(){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(text="HEADER",fontSize = 45.sp)
    }
}

@Composable
fun DrawerBody(items: List<DrawerItem>, modifier: Modifier,textStyle: TextStyle?, onItemClick: (DrawerItem) -> Unit){
    LazyColumn(modifier){
        items(items){ item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
                    .padding(16.dp)
            ){
                Icon(imageVector = item.icon!!, contentDescription = item.description)
                Text(text = item.title, modifier = Modifier.weight(1f))
            }

        }
    }
}

data class DrawerItem(
    val id: String = "",
    val title: String = "",
    val icon: ImageVector?,
    val description: String = ""
)