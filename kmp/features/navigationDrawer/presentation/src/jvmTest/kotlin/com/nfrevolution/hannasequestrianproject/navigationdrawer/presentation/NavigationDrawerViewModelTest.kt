package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import com.nfrevolution.hannasequestrianproject.resources.Res
import com.nfrevolution.hannasequestrianproject.resources.menu_about
import com.nfrevolution.hannasequestrianproject.resources.menu_horses
import com.nfrevolution.hannasequestrianproject.resources.menu_stables
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.toLocalDateTime
import pro.respawn.flowmvi.dsl.collect
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
internal class NavigationDrawerViewModelTest {

    private lateinit var mockFeatureProvider: FeatureProvider
    private lateinit var mockHomeDestination: NavDestination<Unit>
    private lateinit var mockHorsesDestination: NavDestination<Unit>
    private lateinit var mockStablesDestination: NavDestination<Unit>
    private lateinit var mockAboutDestination: NavDestination<Unit>
    private lateinit var viewModel: NavigationDrawerViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Create mock destinations
        mockHomeDestination = mockk(relaxed = true)
        mockHorsesDestination = mockk(relaxed = true)
        mockStablesDestination = mockk(relaxed = true)
        mockAboutDestination = mockk(relaxed = true)

        // Create and configure mock FeatureProvider
        mockFeatureProvider = mockk(relaxed = true) {
            every { home } returns mockHomeDestination
            every { horses } returns mockHorsesDestination
            every { stables } returns mockStablesDestination
            every { about } returns mockAboutDestination
        }

        viewModel = NavigationDrawerViewModel(mockFeatureProvider)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize with null selected item`() = runTest {
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        advanceUntilIdle()

        assertNull(results.first().selectedItem)
        job.cancel()
    }

    @Test
    fun `should provide drawer setup with three menu items`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then
        assertEquals(3, drawerSetup.menuItems.size)
    }

    @Test
    fun `should provide drawer setup with horses menu item`() {
        // When
        val drawerSetup = viewModel.drawerSetup
        val horsesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_horses }

        // Then
        assertNotNull(horsesItem)
        assertEquals(mockHorsesDestination, horsesItem.destination)
    }

    @Test
    fun `should provide drawer setup with stables menu item`() {
        // When
        val drawerSetup = viewModel.drawerSetup
        val stablesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_stables }

        // Then
        assertNotNull(stablesItem)
        assertEquals(mockStablesDestination, stablesItem.destination)
    }

    @Test
    fun `should provide drawer setup with about menu item`() {
        // When
        val drawerSetup = viewModel.drawerSetup
        val aboutItem = drawerSetup.menuItems.find { it.title == Res.string.menu_about }

        // Then
        assertNotNull(aboutItem)
        assertEquals(mockAboutDestination, aboutItem.destination)
    }

    @Test
    fun `should provide drawer setup with social items`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then
        assertTrue(drawerSetup.socialItems.isNotEmpty())
    }

    @Test
    fun `should provide drawer setup with copyright containing current year`() {
        // When
        val drawerSetup = viewModel.drawerSetup
        val copyright = drawerSetup.copyright

        // Then
        assertNotNull(copyright)
        assertNotNull(copyright.year)

        val currentYear = Clock.System.now()
            .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
            .year

        assertEquals(currentYear, copyright.year)
    }

    @Test
    fun `should update selected item when navigating to menu item`() = runTest {
        // Given
        val menuItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()

        // Then
        assertEquals(menuItem, results.last().selectedItem)
        job.cancel()
    }

    @Test
    fun `should emit NavigateTo action when navigating to menu item`() = runTest {
        // Given
        val menuItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val actions = mutableListOf<NavigationDrawerAction>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                this.actions.toList(actions)
            }
        }

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()

        // Then
        assertTrue(actions.isNotEmpty())
        val action = actions.first()
        assertTrue(action is NavigationDrawerAction.NavigateTo)
        assertEquals(mockHorsesDestination, action.destination)
        job.cancel()
    }

    @Test
    fun `should clear selected item when navigating to home`() = runTest {
        // Given
        val menuItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()

        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()

        // Then
        assertNull(results.last().selectedItem)
        job.cancel()
    }

    @Test
    fun `should emit NavigateTo home action when navigating to home`() = runTest {
        val actions = mutableListOf<NavigationDrawerAction>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                this.actions.toList(actions)
            }
        }

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()

        // Then
        assertTrue(actions.isNotEmpty())
        val action = actions.first()
        assertTrue(action is NavigationDrawerAction.NavigateTo)
        assertEquals(mockHomeDestination, action.destination)
        job.cancel()
    }

    @Test
    fun `should handle multiple navigation intents sequentially`() = runTest {
        // Given
        val horsesItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val stablesItem = DrawerMenuItem(
            title = Res.string.menu_stables,
            destination = mockStablesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When & Then
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()
        assertEquals(horsesItem, results.last().selectedItem)

        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(stablesItem))
        advanceUntilIdle()
        assertEquals(stablesItem, results.last().selectedItem)

        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()
        assertNull(results.last().selectedItem)

        job.cancel()
    }

    @Test
    fun `should emit correct sequence of actions for multiple navigations`() = runTest {
        // Given
        val horsesItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val actions = mutableListOf<NavigationDrawerAction>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                this.actions.toList(actions)
            }
        }

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()

        // Then
        val firstAction = actions.first() as NavigationDrawerAction.NavigateTo
        assertEquals(mockHorsesDestination, firstAction.destination)

        // When
        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()

        // Then
        val secondAction = actions.last() as NavigationDrawerAction.NavigateTo
        assertEquals(mockHomeDestination, secondAction.destination)
        job.cancel()
    }

    @Test
    fun `should maintain state consistency across different menu item selections`() = runTest {
        // Given
        val horsesItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val aboutItem = DrawerMenuItem(
            title = Res.string.menu_about,
            destination = mockAboutDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testScheduler) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When & Then
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()
        assertEquals(horsesItem, results.last().selectedItem)

        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(aboutItem))
        advanceUntilIdle()
        assertEquals(aboutItem, results.last().selectedItem)

        job.cancel()
    }

    @Test
    fun `should correctly structure drawer setup with all components`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then - Verify menu items structure
        assertTrue(drawerSetup.menuItems.isNotEmpty())
        assertEquals(3, drawerSetup.menuItems.size)

        // Verify each menu item has proper structure
        drawerSetup.menuItems.forEach { menuItem ->
            assertNotNull(menuItem.title)
            assertNotNull(menuItem.destination)
        }

        // Verify copyright structure
        assertNotNull(drawerSetup.copyright)
        assertNotNull(drawerSetup.copyright.titleRes)
        assertTrue(drawerSetup.copyright.year > 2020) // Sanity check for year

        // Verify social items structure
        drawerSetup.socialItems.forEach { socialItem ->
            assertNotNull(socialItem.name)
            assertNotNull(socialItem.link)
            assertNotNull(socialItem.icon)
            assertTrue(socialItem.link.isNotBlank())
        }
    }

    @Test
    fun `should use correct destinations from FeatureProvider`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then - Verify that the correct destinations are used
        val horsesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_horses }
        val stablesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_stables }
        val aboutItem = drawerSetup.menuItems.find { it.title == Res.string.menu_about }

        assertEquals(mockHorsesDestination, horsesItem?.destination)
        assertEquals(mockStablesDestination, stablesItem?.destination)
        assertEquals(mockAboutDestination, aboutItem?.destination)
    }

    @Test
    fun `should emit action with correct destination from FeatureProvider when navigating home`() =
        runTest {
            val actions = mutableListOf<NavigationDrawerAction>()

            val job = launch(testScheduler) {
                viewModel.store.collect {
                    this.actions.toList(actions)
                }
            }

            // When
            viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
            advanceUntilIdle()

            // Then
            assertTrue(actions.isNotEmpty())
            val action = actions.first() as NavigationDrawerAction.NavigateTo

            // Verify it uses the home destination from FeatureProvider
            assertEquals(mockHomeDestination, action.destination)
            job.cancel()
        }
}
