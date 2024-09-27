package org.grating.recolldroid.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import org.grating.recolldroid.R
import org.grating.recolldroid.ui.screens.RecollDroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.grating.recolldroid.data.fake.FakeResultsDataProvider
import org.grating.recolldroid.ui.screens.QueryScreen
import org.grating.recolldroid.ui.screens.ResultsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecollDroidApp(
    viewModel: RecollDroidViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val currentScreen =
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                RecollDroidTopAppBar(
                    currentScreen = RecollDroidScreen.valueOf(
                        backStackEntry?.destination?.route ?: RecollDroidScreen.Query.name
                    ),
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    scrollBehavior = scrollBehavior,
                )
            }
        ) { contentPadding ->
            NavHost(
                navController = navController,
                startDestination = RecollDroidScreen.Query.name,
                modifier = Modifier.padding(contentPadding)
            ) {
                composable(route = RecollDroidScreen.Query.name) {
                    QueryScreen(viewModel = viewModel,
                                onQueryChanged = viewModel::updateCurrentQuery,
                                onQueryExecuteRequest = {
                                    viewModel.executeCurrentQuery()
                                    navController.navigate(RecollDroidScreen.Results.name)
                                })
                }
                composable(route = RecollDroidScreen.Results.name) {
                    ResultsScreen(viewModel = viewModel,
                                  onQueryChanged = viewModel::updateCurrentQuery,
                                  onQueryExecuteRequest = {
                                      viewModel.executeCurrentQuery()
                                      navController.navigate(RecollDroidScreen.Results.name)
                                  },
                                  results = FakeResultsDataProvider.getSampleResults(),
                                  contentPadding = contentPadding)
                }
            }
        }
}

enum class RecollDroidScreen(@StringRes val title: Int) {
    Query(title = R.string.query_screen),
    Results(title = R.string.results_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecollDroidTopAppBar(
    currentScreen: RecollDroidScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }

    )
}