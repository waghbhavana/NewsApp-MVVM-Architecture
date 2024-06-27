package com.bhavanawagh.newsapp_mvvm_architecture.ui.base.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.theme.Purple40
import com.bhavanawagh.newsapp_mvvm_architecture.ui.base.theme.PurpleGrey40


@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit
) {
    NavigationBar() {
        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = { onClick(navigationItem) },
                icon = {
                    if (currentRoute == navigationItem.route) {
                        Icon(
                            imageVector = navigationItem.icon,
                            contentDescription = navigationItem.title,
                            tint = Purple40
                        )
                    } else {
                        Icon(
                            imageVector = navigationItem.icon,
                            contentDescription = navigationItem.title,
                            tint = PurpleGrey40
                        )
                    }
                },
                label = {
                    if (currentRoute == navigationItem.route) {
                        Text(navigationItem.title, color = Purple40)
                    } else{
                        Text(navigationItem.title, color = PurpleGrey40)
                        }
                }
            )
        }
    }

}