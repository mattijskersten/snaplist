package com.snaplist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.snaplist.ui.capture.CaptureScreen
import com.snaplist.ui.handoff.HandoffScreen
import com.snaplist.ui.home.HomeScreen
import com.snaplist.ui.review.ReviewScreen
import com.snaplist.ui.settings.SettingsScreen
import com.snaplist.ui.theme.SnapListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnapListTheme {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNewListing = { draftId -> nav.navigate("capture/$draftId") },
                            onOpenDraft = { draftId -> nav.navigate("review/$draftId") },
                            onSettings = { nav.navigate("settings") },
                        )
                    }
                    composable(
                        "capture/{draftId}",
                        arguments = listOf(navArgument("draftId") { type = NavType.LongType }),
                    ) { entry ->
                        val draftId = entry.arguments!!.getLong("draftId")
                        CaptureScreen(
                            draftId = draftId,
                            onDone = {
                                nav.navigate("review/$draftId") {
                                    popUpTo("home")
                                }
                            },
                            onBack = { nav.popBackStack() },
                        )
                    }
                    composable(
                        "review/{draftId}",
                        arguments = listOf(navArgument("draftId") { type = NavType.LongType }),
                    ) { entry ->
                        val draftId = entry.arguments!!.getLong("draftId")
                        ReviewScreen(
                            draftId = draftId,
                            onPost = { nav.navigate("handoff/$draftId") },
                            onAddPhotos = { nav.navigate("capture/$draftId") },
                            onBack = { nav.popBackStack("home", inclusive = false) },
                        )
                    }
                    composable(
                        "handoff/{draftId}",
                        arguments = listOf(navArgument("draftId") { type = NavType.LongType }),
                    ) { entry ->
                        val draftId = entry.arguments!!.getLong("draftId")
                        HandoffScreen(
                            draftId = draftId,
                            onDone = { nav.popBackStack("home", inclusive = false) },
                            onBack = { nav.popBackStack() },
                        )
                    }
                    composable("settings") {
                        SettingsScreen(onBack = { nav.popBackStack() })
                    }
                }
            }
        }
    }
}
