package dev.techmess.gym_mem.presentation.app.fragment.home.event

sealed class HomeFragmentEvent {


    object FetchUser : HomeFragmentEvent()

    object FetchContents: HomeFragmentEvent()

}