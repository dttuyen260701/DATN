package com.example.realestateapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.realestateapp.designsystem.icon.Icon
import com.example.realestateapp.navigation.RealEstateNavHost
import com.example.realestateapp.navigation.TopLevelDestination

/**
 * Created by tuyen.dang on 5/3/2023.
 */

@Composable
fun RealEstateApp(
    appState: RealEstateAppState = rememberRealEstateAppState()
) {
    Scaffold(
        backgroundColor = Color.Transparent,
        bottomBar = {
            if (appState.shouldShowBottomBar) RealEstateBottomBar(
                currentDestination = appState.currentDestination,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                tabs = appState.topLevelDestinations
            )
        }
    ) { innerPaddingModifier ->
        RealEstateNavHost(
            navController = appState.navController,
            modifier = Modifier.padding(innerPaddingModifier)
        )
    }
}

@Composable
fun RealEstateBottomBar(
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    tabs: List<TopLevelDestination>,
    modifier: Modifier = Modifier
) {
    if (currentDestination?.route in tabs.map { it.route }) {
        BottomNavigation(
            modifier = Modifier
                .windowInsetsBottomHeight(
                    WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
                )
                .then(modifier)
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = {
                        when (tab.icon) {
                            is Icon.ImageVectorIcon -> Icon(
                                imageVector = tab.icon.imageVector,
                                contentDescription = null
                            )
                            is Icon.DrawableResourceIcon -> Icon(
                                painter = painterResource(id = tab.icon.id),
                                contentDescription = null
                            )
                        }
                    },
                    label = { Text(stringResource(tab.title)) },
                    selected = currentDestination?.route == tab.route,
                    onClick = {
                        if (currentDestination?.route != tab.route)
                            onNavigateToDestination(tab)
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = Color.Green,
                    unselectedContentColor = Color.Gray,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .background(Color.White)
                )
            }
        }
    }
}
