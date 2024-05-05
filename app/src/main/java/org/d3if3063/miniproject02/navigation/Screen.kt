package org.d3if3063.miniproject02.navigation

import org.d3if3063.miniproject02.ui.screen.KEY_ID_PASIEN

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_PASIEN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}