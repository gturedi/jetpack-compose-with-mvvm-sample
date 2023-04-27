package com.gturedi.github.ui.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gturedi.github.network.model.Resource
import com.gturedi.github.network.model.UserResponse
import com.gturedi.github.theme.marginSmall
import com.gturedi.github.theme.marginXSmall
import com.gturedi.github.ui.LoadingView
import com.gturedi.github.ui.ShowErrorAlert
import com.gturedi.github.util.isNotNullOrBlank
import com.gturedi.github.util.isNullOrBlankThen
import org.koin.androidx.compose.koinViewModel
import com.gturedi.github.R

@Composable
fun UserScreen(
    name: String?,
    viewModel: UserViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    //viewModel.getUser(fullName)
    LaunchedEffect(Unit) {
        viewModel.getUser(name)
    }

    val uiState by viewModel.userResp.collectAsStateWithLifecycle()

    when (uiState) {
        Resource.Loading -> {
            LoadingView()
        }
        is Resource.Success -> {
            val user = (uiState as? Resource.Success<UserResponse>)?.data
            user?.let {
                UserView(it)
            }
        }
        is Resource.Failure -> {
            val msg = (uiState as? Resource.Failure)?.message.orEmpty()
            ShowErrorAlert(msg) {
                navigateToBack.invoke()
            }
        }
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserView(
    item: UserResponse,
) {
    val uriHandler = LocalUriHandler.current

    Card(
        Modifier
            .padding(marginSmall)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            Modifier.padding(marginSmall),
        ) {
            AsyncImage(
                item.avatarUrl,
                "",
                Modifier.size(70.dp),
                //placeholder = ImageBitmap.imageResource(R.drawable.stat_sys_upload),
                //error = ImageBitmap.imageResource(R.drawable.stat_notify_error),
            )
            Spacer(Modifier.width(marginSmall))
            Column {
                Row {
                    Column {
                        Text(
                            stringResource(R.string.userName),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            item.userName.isNullOrBlankThen("-"),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.width(marginSmall))
                    Column {
                        Text(
                            stringResource(R.string.fullName),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            item.fullName.isNullOrBlankThen("-"),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.width(marginSmall))
                    Column {
                        Text(
                            stringResource(R.string.location),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            item.location.isNullOrBlankThen("-"),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(Modifier.height(marginXSmall))
                Column(Modifier.clickable {
                    if (item.blog.isNotNullOrBlank()) {
                        uriHandler.openUri(item.blog.orEmpty())
                    }
                }) {
                    Text(
                        stringResource(R.string.blog),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        item.blog.isNullOrBlankThen("-"),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserView(UserResponse.createMock())
}