package com.gturedi.github.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.gturedi.github.theme.CompAppTheme
import com.gturedi.github.theme.marginSmall
import com.gturedi.github.util.logI
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val titleState by viewModel.title.collectAsStateWithLifecycle()
            val showBackState by viewModel.showBack.collectAsStateWithLifecycle()
            //val errorAlertMsg by viewModel.errorAlertMsg.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            CompAppTheme {
                Surface(
                    Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    @OptIn(ExperimentalMaterial3Api::class)
                    Scaffold(
                        Modifier.fillMaxWidth(),
                        {
                            SmallTopAppBar(
                                //{ Text(stringResource(R.string.app_name)) },
                                { Text(titleState) },
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                navigationIcon = {
                                    if (showBackState) {
                                        Icon(
                                            Icons.Default.ArrowBack,
                                            "",
                                            Modifier
                                                .padding(end = marginSmall)
                                                .clickable { navController.popBackStack() },
                                            MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.background,
                        content = {
                            logI("contentPadding $it")
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(it)
                            ) {
                                /*if (errorAlertMsg.isNotNullOrBlank()) {
                                    ShowErrorAlert(errorAlertMsg)
                                }*/
                                NavigationView(navController, viewModel)
                            }
                        }
                    )
                }
            }
        }
    }
}