package com.tec.appnotas

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tec.appnotas.domain.datasource.UserStore
import com.tec.appnotas.ui.global.GlobalProvider
import com.tec.appnotas.ui.screens.calendario.CalendarioViewModel
import com.tec.appnotas.ui.screens.notas.UserViewmodel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Inject
    lateinit var userVM: UserViewmodel
    @Inject
    lateinit var calendarioVM: CalendarioViewModel

    lateinit var dataStore: UserStore

    lateinit var gp: GlobalProvider

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup(){
        hiltRule.inject()
    }

    //No clue how to make UI tests with all of this stuff
    //Tests de UI no funcionan por el momento
    @Test
    fun se_agrega_nueva_nota_correctamente(){
        composeTestRule.setContent {
            val navController = rememberNavController()
            gp = GlobalProvider(
                calendarioVM = calendarioVM,
                dataStore = dataStore,
                userVM = userVM,
                nav = navController
            )
            mainScreen(gp = gp)
        }
        composeTestRule.onNodeWithTag("Add").performClick()
        val node = composeTestRule.onNodeWithTag("editor_tag")
        node.performTextInput("Test1")
        node.assertTextEquals("Test1")
    }
}