package com.koen.gosexam.extension

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.koen.gosexam.R


/**
 * Refer to nav_graph NavController
 */
fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
    return topLevelHost.navController
}

fun Fragment.findTopNavController(id : Int): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(id) as NavHostFragment
    return topLevelHost.navController
}