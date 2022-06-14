package com.bigflowertiger.compopsebingwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.bigflowertiger.compopsebingwallpaper.config.BASE_URL
import com.bigflowertiger.compopsebingwallpaper.data.BingViewModel
import com.bigflowertiger.compopsebingwallpaper.data.QWeatherViewModel
import com.bigflowertiger.compopsebingwallpaper.data.model.BingItem
import com.bigflowertiger.compopsebingwallpaper.ui.screen.BigViewScreen
import com.bigflowertiger.compopsebingwallpaper.ui.theme.EasyBingWallpaperTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bingViewModel by viewModels<BingViewModel>()
    private val qwViewModel by viewModels<QWeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val bingList = bingViewModel.bingList.value
            val qw = qwViewModel.weatherState.value
            val systemUiController = rememberSystemUiController()
            val navController = rememberNavController()
            EasyBingWallpaperTheme {
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Transparent)
                }
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = qw.toString())
                }

//                BingListScreen(navController, systemUiController, bingList)
            }
        }
    }

    @Composable
    private fun BingListScreen(
        navController: NavHostController,
        systemUiController: SystemUiController,
        bingList: List<BingItem>
    ) {
        NavHost(navController = navController, startDestination = "MainScreen") {
            composable("MainScreen")
            {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(bingList) { _, item ->
                            Card(modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    // url作为参数在compose传递时需要编码，否则会作为compose参数的一部分，因为compose参数也用url格式
                                    val encodedUrl = URLEncoder.encode(
                                        item.url,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigate("BigViewScreen/${encodedUrl}")
                                }
                            ) {
                                Box {
                                    AsyncImage(
                                        model = BASE_URL + item.url,
                                        contentDescription = item.desc
                                    )
                                    Text(text = item.title)
                                }
                            }
                        }
                    }
                }
            }
            composable(
                "BigViewScreen/{imgUrl}",
                arguments = listOf(navArgument("imgUrl") {
                    type = NavType.StringType
                })
            ) {
                BigViewScreen(
                    imgUrl = it.arguments?.getString("imgUrl")!!
                )
            }
        }
    }
}