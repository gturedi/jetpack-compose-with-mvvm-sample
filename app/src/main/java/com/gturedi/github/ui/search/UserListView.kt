package com.gturedi.github.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gturedi.github.network.model.UserModel

@Composable
fun UserListView(
    users: List<UserModel>?,
    itemClickListener: (UserModel) -> Unit,
) {
    if (users.isNullOrEmpty().not()) {
        LazyColumn {
            items(users!!) {
                UserCardView(it, itemClickListener)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCardView(
    item: UserModel,
    itemClickListener: (UserModel) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                itemClickListener.invoke(item)
            },
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                item.avatarUrl,
                "",
                Modifier.size(50.dp),
                //placeholder = ImageBitmap.imageResource(R.drawable.stat_sys_upload),
                //error = ImageBitmap.imageResource(R.drawable.stat_notify_error),
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    item.userName.orEmpty(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    item.url.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListViewPreview() {
    UserListView(
        listOf(
            UserModel.createMock(),
            UserModel.createMock(),
            UserModel.createMock(),
        )
    ) {}
}