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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
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

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class, FlowPreview::class)
internal class NavigationDrawerViewModelTest {

    private lateinit var mockFeatureProvider: FeatureProvider
    private lateinit var mockHomeDestination: NavDestination<Unit>
    private lateinit var mockHorsesDestination: NavDestination<Unit>
    private lateinit var mockStablesDestination: NavDestination<Unit>
    private lateinit var mockAboutDestination: NavDestination<Unit>
    private lateinit var viewModel: NavigationDrawerViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

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
    fun `should provide drawer setup with correct menu items and destinations`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then
        assertEquals(3, drawerSetup.menuItems.size)

        val horsesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_horses }
        assertNotNull(horsesItem)
        assertEquals(mockHorsesDestination, horsesItem.destination)

        val stablesItem = drawerSetup.menuItems.find { it.title == Res.string.menu_stables }
        assertNotNull(stablesItem)
        assertEquals(mockStablesDestination, stablesItem.destination)

        val aboutItem = drawerSetup.menuItems.find { it.title == Res.string.menu_about }
        assertNotNull(aboutItem)
        assertEquals(mockAboutDestination, aboutItem.destination)
    }

    @Test
    fun `should provide drawer setup with social items`() {
        // When
        val drawerSetup = viewModel.drawerSetup

        // Then
        assertTrue(drawerSetup.socialItems.isNotEmpty())
        drawerSetup.socialItems.forEach { socialItem ->
            assertNotNull(socialItem.name)
            assertNotNull(socialItem.link)
            assertNotNull(socialItem.icon)
            assertTrue(socialItem.link.isNotBlank())
        }
    }

    @Test
    fun `should provide drawer setup with copyright containing current year`() {
        // When
        val drawerSetup = viewModel.drawerSetup
        val copyright = drawerSetup.copyright

        // Then
        assertNotNull(copyright)
        assertNotNull(copyright.titleRes)
        assertNotNull(copyright.year)

        val currentYear = Clock.System.now()
            .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
            .year

        assertEquals(currentYear, copyright.year)
        assertTrue(copyright.year > 2020)
    }

    @Test
    fun `should update selected item and emit NavigateTo action when navigating to menu item`() =
        runTest {
            // Given
            val menuItem = DrawerMenuItem(
                title = Res.string.menu_horses,
                destination = mockHorsesDestination
            )
            val results = mutableListOf<NavigationDrawerUiState>()
            val actions = mutableListOf<NavigationDrawerAction>()

            val stateJob = launch(testDispatcher) {
                viewModel.store.collect {
                    states.toList(results)
                }
            }

            val actionJob = launch(testDispatcher) {
                viewModel.store.collect {
                    this.actions.toList(actions)
                }
            }

            // When
            viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
            advanceUntilIdle()

            // Then
            assertEquals(menuItem, results.last().selectedItem)
            assertTrue(actions.isNotEmpty())
            val action = actions.first()
            assertTrue(action is NavigationDrawerAction.NavigateTo)
            assertEquals(mockHorsesDestination, action.destination)
            stateJob.cancel()
            actionJob.cancel()
        }

    @Test
    fun `should clear selected item and emit NavigateTo home action when navigating to home`() =
        runTest {
            // Given
            val menuItem = DrawerMenuItem(
                title = Res.string.menu_horses,
                destination = mockHorsesDestination
            )
            val results = mutableListOf<NavigationDrawerUiState>()
            val actions = mutableListOf<NavigationDrawerAction>()

            val stateJob = launch(testDispatcher) {
                viewModel.store.collect {
                    states.toList(results)
                }
            }

            val actionJob = launch(testDispatcher) {
                viewModel.store.collect {
                    this.actions.toList(actions)
                }
            }

            // When
            viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
            advanceUntilIdle()
            viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
            advanceUntilIdle()

            // Then
            assertNull(results.last().selectedItem)
            assertTrue(actions.size >= 2)
            val homeAction = actions.last()
            assertTrue(homeAction is NavigationDrawerAction.NavigateTo)
            assertEquals(mockHomeDestination, homeAction.destination)
            stateJob.cancel()
            actionJob.cancel()
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
        val actions = mutableListOf<NavigationDrawerAction>()

        val stateJob = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        val actionJob = launch(testDispatcher) {
            viewModel.store.collect {
                this.actions.toList(actions)
            }
        }

        // When & Then
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()
        assertEquals(horsesItem, results.last().selectedItem)
        assertTrue(actions.isNotEmpty())
        assertEquals(
            mockHorsesDestination,
            (actions.last() as NavigationDrawerAction.NavigateTo).destination
        )

        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(stablesItem))
        advanceUntilIdle()
        assertEquals(stablesItem, results.last().selectedItem)
        assertEquals(
            mockStablesDestination,
            (actions.last() as NavigationDrawerAction.NavigateTo).destination
        )

        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()
        assertNull(results.last().selectedItem)
        assertEquals(
            mockHomeDestination,
            (actions.last() as NavigationDrawerAction.NavigateTo).destination
        )

        stateJob.cancel()
        actionJob.cancel()
    }

    @Test
    fun `should not emit NavigateTo action when navigating to the same menu item`() = runTest {
        // Given
        val menuItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val actions = mutableListOf<NavigationDrawerAction>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                this.actions.toList(actions)
            }
        }

        // When - navigate to the same item twice
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()
        val actionsAfterFirstNav = actions.size

        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()

        // Then - no new action should be emitted
        assertEquals(actionsAfterFirstNav, actions.size)
        job.cancel()
    }

    @Test
    fun `should not emit NavigateTo action when navigating to home while already at home`() =
        runTest {
            // Given
            val actions = mutableListOf<NavigationDrawerAction>()

            val job = launch(testDispatcher) {
                viewModel.store.collect {
                    this.actions.toList(actions)
                }
            }

            // When - navigate to home when already at home (selectedItem is null initially)
            viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
            advanceUntilIdle()

            // Then - no action should be emitted
            assertTrue(actions.isEmpty())
            job.cancel()
        }

    @Test
    fun `should not update state when navigating to the same menu item`() = runTest {
        // Given
        val menuItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When - navigate to the same item twice
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()
        val statesAfterFirstNav = results.size

        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(menuItem))
        advanceUntilIdle()

        // Then - no new state should be emitted
        assertEquals(statesAfterFirstNav, results.size)
        job.cancel()
    }

    @Test
    fun `should not update state when navigating to home while already at home`() = runTest {
        // Given
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        advanceUntilIdle()
        val initialStateCount = results.size

        // When - navigate to home when already at home
        viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        advanceUntilIdle()

        // Then - no new state should be emitted
        assertEquals(initialStateCount, results.size)
        job.cancel()
    }

    @Test
    fun `should sync selected item to null when current destination is home`() = runTest {
        // Given
        val horsesItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // Set initial state with a selected item
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()
        assertEquals(horsesItem, results.last().selectedItem)

        // When - sync with home destination
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockHomeDestination))
        advanceUntilIdle()

        // Then
        assertNull(results.last().selectedItem)
        job.cancel()
    }

    @Test
    fun `should sync selected item to matching menu item when current destination matches`() =
        runTest {
            // Given
            val results = mutableListOf<NavigationDrawerUiState>()

            val job = launch(testDispatcher) {
                viewModel.store.collect {
                    states.toList(results)
                }
            }

            advanceUntilIdle()
            assertNull(results.last().selectedItem)

            // When - sync with horses destination
            viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockHorsesDestination))
            advanceUntilIdle()

            // Then
            assertNotNull(results.last().selectedItem)
            assertEquals(Res.string.menu_horses, results.last().selectedItem?.title)
            assertEquals(mockHorsesDestination, results.last().selectedItem?.destination)
            job.cancel()
        }

    @Test
    fun `should sync selected item to null when current destination does not match any drawer item`() =
        runTest {
            // Given
            val horsesItem = DrawerMenuItem(
                title = Res.string.menu_horses,
                destination = mockHorsesDestination
            )
            val unknownDestination = mockk<NavDestination<Unit>>(relaxed = true)
            val results = mutableListOf<NavigationDrawerUiState>()

            val job = launch(testDispatcher) {
                viewModel.store.collect {
                    states.toList(results)
                }
            }

            // Set initial state with a selected item
            viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
            advanceUntilIdle()
            assertEquals(horsesItem, results.last().selectedItem)

            // When - sync with unknown destination
            viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(unknownDestination))
            advanceUntilIdle()

            // Then
            assertNull(results.last().selectedItem)
            job.cancel()
        }

    @Test
    fun `should not update state when syncing to the same selected item`() = runTest {
        // Given
        val horsesItem = DrawerMenuItem(
            title = Res.string.menu_horses,
            destination = mockHorsesDestination
        )
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // Set initial state with horses selected
        viewModel.store.intent(NavigationDrawerIntent.NavigateTo(horsesItem))
        advanceUntilIdle()
        val statesAfterNav = results.size

        // When - sync with the same destination
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockHorsesDestination))
        advanceUntilIdle()

        // Then - no new state should be emitted
        assertEquals(statesAfterNav, results.size)
        assertEquals(horsesItem.title, results.last().selectedItem?.title)
        job.cancel()
    }

    @Test
    fun `should sync through multiple destination changes correctly`() = runTest {
        // Given
        val results = mutableListOf<NavigationDrawerUiState>()

        val job = launch(testDispatcher) {
            viewModel.store.collect {
                states.toList(results)
            }
        }

        // When & Then
        // Start at home
        advanceUntilIdle()
        assertNull(results.last().selectedItem)

        // Sync to horses
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockHorsesDestination))
        advanceUntilIdle()
        assertEquals(Res.string.menu_horses, results.last().selectedItem?.title)

        // Sync to stables
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockStablesDestination))
        advanceUntilIdle()
        assertEquals(Res.string.menu_stables, results.last().selectedItem?.title)

        // Sync to about
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockAboutDestination))
        advanceUntilIdle()
        assertEquals(Res.string.menu_about, results.last().selectedItem?.title)

        // Sync back to home
        viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(mockHomeDestination))
        advanceUntilIdle()
        assertNull(results.last().selectedItem)

        job.cancel()
    }
}
