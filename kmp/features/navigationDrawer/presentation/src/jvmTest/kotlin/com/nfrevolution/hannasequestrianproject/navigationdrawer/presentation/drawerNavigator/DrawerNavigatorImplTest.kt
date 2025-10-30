package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.drawerNavigator

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerIntent
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerViewModel
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import com.nfrevolution.hannasequestrianproject.resources.Res
import com.nfrevolution.hannasequestrianproject.resources.menu_about
import com.nfrevolution.hannasequestrianproject.resources.menu_horses
import com.nfrevolution.hannasequestrianproject.resources.menu_stables
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import pro.respawn.flowmvi.dsl.intent
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class DrawerNavigatorImplTest {

    private lateinit var mockFeatureProvider: FeatureProvider
    private lateinit var mockNavigationDrawerViewModel: NavigationDrawerViewModel
    private lateinit var mockHomeDestination: NavDestination<Unit>
    private lateinit var mockHorsesDestination: NavDestination<Unit>
    private lateinit var mockStablesDestination: NavDestination<Unit>
    private lateinit var mockAboutDestination: NavDestination<Unit>
    private lateinit var mockOtherDestination: NavDestination<Unit>
    private lateinit var drawerNavigator: DrawerNavigatorImpl

    @BeforeTest
    fun setup() {
        // Create mock destinations
        mockHomeDestination = mockk(relaxed = true)
        mockHorsesDestination = mockk(relaxed = true)
        mockStablesDestination = mockk(relaxed = true)
        mockAboutDestination = mockk(relaxed = true)
        mockOtherDestination = mockk(relaxed = true)

        // Create and configure mock FeatureProvider
        mockFeatureProvider = mockk(relaxed = true) {
            every { home } returns mockHomeDestination
            every { horses } returns mockHorsesDestination
            every { stables } returns mockStablesDestination
            every { about } returns mockAboutDestination
        }

        // Create mock NavigationDrawerViewModel
        mockNavigationDrawerViewModel = mockk(relaxed = true) {
            every { drawerSetup.menuItems } returns listOf(
                DrawerMenuItem(
                    title = Res.string.menu_horses,
                    destination = mockHorsesDestination
                ),
                DrawerMenuItem(
                    title = Res.string.menu_stables,
                    destination = mockStablesDestination
                ),
                DrawerMenuItem(
                    title = Res.string.menu_about,
                    destination = mockAboutDestination
                )
            )
        }

        drawerNavigator = DrawerNavigatorImpl(
            navigationDrawerViewModel = mockNavigationDrawerViewModel,
            featureProvider = mockFeatureProvider
        )
    }

    @Test
    fun `should send NavigateToHome intent when navigating to home destination`() {
        // When
        drawerNavigator.navigateTo(mockHomeDestination)

        // Then
        verify {
            mockNavigationDrawerViewModel.intent(NavigationDrawerIntent.NavigateToHome)
        }
    }

    @Test
    fun `should send NavigateTo intent with horses menu item when navigating to horses destination`() {
        // When
        drawerNavigator.navigateTo(mockHorsesDestination)

        // Then
        verify {
            mockNavigationDrawerViewModel.intent(
                match { intent ->
                    intent is NavigationDrawerIntent.NavigateTo &&
                            intent.menuItem.destination == mockHorsesDestination &&
                            intent.menuItem.title == Res.string.menu_horses
                }
            )
        }
    }

    @Test
    fun `should send NavigateTo intent with stables menu item when navigating to stables destination`() {
        // When
        drawerNavigator.navigateTo(mockStablesDestination)

        // Then
        verify {
            mockNavigationDrawerViewModel.intent(
                match { intent ->
                    intent is NavigationDrawerIntent.NavigateTo &&
                            intent.menuItem.destination == mockStablesDestination &&
                            intent.menuItem.title == Res.string.menu_stables
                }
            )
        }
    }

    @Test
    fun `should send NavigateTo intent with about menu item when navigating to about destination`() {
        // When
        drawerNavigator.navigateTo(mockAboutDestination)

        // Then
        verify {
            mockNavigationDrawerViewModel.intent(
                match { intent ->
                    intent is NavigationDrawerIntent.NavigateTo &&
                            intent.menuItem.destination == mockAboutDestination &&
                            intent.menuItem.title == Res.string.menu_about
                }
            )
        }
    }

    @Test
    fun `should not send any intent when navigating to unknown destination`() {
        // When
        drawerNavigator.navigateTo(mockOtherDestination)

        // Then
        verify(exactly = 0) { mockNavigationDrawerViewModel.intent(any()) }
    }

    @Test
    fun `should handle navigation when menu items list is empty`() {
        // Given
        every { mockNavigationDrawerViewModel.drawerSetup.menuItems } returns emptyList()

        // When
        drawerNavigator.navigateTo(mockHorsesDestination)

        // Then - should not send any intent
        verify(exactly = 0) { mockNavigationDrawerViewModel.intent(any()) }
    }

    @Test
    fun `should send correct intent when navigating to same destination multiple times`() {
        // When
        drawerNavigator.navigateTo(mockHorsesDestination)
        drawerNavigator.navigateTo(mockHorsesDestination)

        // Then - should send intent twice (the ViewModel handles deduplication)
        verify(exactly = 2) {
            mockNavigationDrawerViewModel.intent(
                match { intent ->
                    intent is NavigationDrawerIntent.NavigateTo &&
                            intent.menuItem.destination == mockHorsesDestination
                }
            )
        }
    }
}
