package com.gturedi.github.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gturedi.github.R
import com.gturedi.github.network.model.Resource
import com.gturedi.github.network.model.SearchUserResponse
import com.gturedi.github.theme.CompAppTheme
import com.gturedi.github.theme.marginMedium
import com.gturedi.github.theme.marginSmall
import com.gturedi.github.ui.LoadingView
import com.gturedi.github.ui.ShowErrorAlert
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    navigateToUser: (String) -> Unit,
) {
    //val navController = rememberNavController()
    var searchText by remember { mutableStateOf("") }
    var searchBtnEnabled by remember { mutableStateOf(false) }
    val uiState by viewModel.searchResp.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .padding(
                vertical = marginSmall,
                horizontal = marginMedium,
            )
    ) {
        Row(
            Modifier.padding(bottom = marginSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                searchText,
                {
                    searchText = it
                    searchBtnEnabled = it.isBlank().not()
                },
                Modifier.weight(1f),
                singleLine = true,
                label = { Text(stringResource(R.string.search_hint)) }
            )
            Spacer(Modifier.width(marginSmall))
            Button(
                {
                    keyboard?.hide()
                    viewModel.search(searchText)
                },
                Modifier
                    .height(55.dp)
                    .padding(horizontal = marginSmall),
                searchBtnEnabled,
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.search))
            }
        }

        when (uiState) {
            Resource.Loading -> {
                searchBtnEnabled = false
                LoadingView()
            }
            is Resource.Success<SearchUserResponse> -> {
                searchBtnEnabled = true
                val users = (uiState as? Resource.Success<SearchUserResponse>)?.data?.items
                UserListView(users) {
                    navigateToUser.invoke(it.userName.orEmpty())
                }
            }
            is Resource.Failure -> {
                val msg = (uiState as? Resource.Failure)?.message.orEmpty()
                //Text(msg)
                ShowErrorAlert(msg)
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CompAppTheme {
        SearchScreen{}
    }
}