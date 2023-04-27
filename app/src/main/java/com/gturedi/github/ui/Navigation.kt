package com.gturedi.github.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gturedi.github.R
import com.gturedi.github.ui.search.SearchScreen
import com.gturedi.github.ui.user.UserScreen
import com.gturedi.github.util.AppConst
import org.koin.androidx.compose.koinViewModel

//https://developer.android.com/jetpack/compose/navigation#nav-from-composable
//https://vtsen.hashnode.dev/simple-jetpack-compose-navigation-example

sealed class NavRoutes(val route: String) {
    object search : NavRoutes("search")
    object user : NavRoutes("user/{name}")
}

@Composable
fun NavigationView(
    navController: NavHostController,
    viewModel: MainViewModel = koinViewModel()
) {

    NavHost(
        navController,
        NavRoutes.search.route
    ) {
        composable(NavRoutes.search.route) {
            viewModel.changeTitle(stringResource(R.string.search))
            viewModel.showBack(false)

            SearchScreen {
                navController.navigate(
                    NavRoutes.user.route.replace("{${AppConst.PARAM_NAME}}", it)
                )
            }
        }
        composable(
            NavRoutes.user.route,
            arguments = listOf(
                navArgument(AppConst.PARAM_NAME) { type = NavType.StringType }
            )
        ) {
            viewModel.changeTitle(stringResource(R.string.detail))
            viewModel.showBack(true)

            UserScreen(it.arguments?.getString(AppConst.PARAM_NAME)) {
                navController.popBackStack()
            }
        }
    }
}