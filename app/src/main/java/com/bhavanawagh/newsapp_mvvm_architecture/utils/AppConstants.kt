package com.bhavanawagh.newsapp_mvvm_architecture.utils

import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Country
import com.bhavanawagh.newsapp_mvvm_architecture.data.model.Language

object AppConstants {

   // 59268db289864425bc1ca98d11669422
    const val EXTRAS_COUNTRY = "us"
    const val DEBOUNCE_TIMEOUT = 300L
    const val MIN_SEARCH_CHAR = 3
    const val APP_NAME = "NewsApp"
    const val INITIAL_PAGE = 1
    const val PAGE_SIZE = 10

    val COUNTRY_LIST: List<Country> = listOf(
        Country("United Arab Emirates", "ae"),
        Country("Argentina", "ar"),
        Country("Austria", "at"),
        Country("Australia", "au"),
        Country("Belgium", "be"),
        Country("Bulgaria", "bg"),
        Country("Brazil", "br"),
        Country("Canada", "ca"),
        Country("Switzerland", "ch"),
        Country("China", "cn"),
        Country("Colombia", "co"),
        Country("Cuba", "cu"),
        Country("Czech Republic", "cz"),
        Country("Germany", "de"),
        Country("Egypt", "eg"),
        Country("France", "fr"),
        Country("United Kingdom", "gb"),
        Country("Greece", "gr"),
        Country("Hong Kong", "hk"),
        Country("Hungary", "hu"),
        Country("Indonesia", "id"),
        Country("Ireland", "ie"),
        Country("Israel", "il"),
        Country("India", "in"),
        Country("Italy", "it"),
        Country("Japan", "jp"),
        Country("South Korea", "kr"),
        Country("Lithuania", "lt"),
        Country("Latvia", "lv"),
        Country("Morocco", "ma"),
        Country("Mexico", "mx"),
        Country("Malaysia", "my"),
        Country("Nigeria", "ng"),
        Country("Netherlands", "nl"),
        Country("Norway", "no"),
        Country("New Zealand", "nz"),
        Country("Philippines", "ph"),
        Country("Poland", "pl"),
        Country("Portugal", "pt"),
        Country("Romania", "ro"),
        Country("Serbia", "rs"),
        Country("Russia", "ru"),
        Country("Saudi Arabia", "sa"),
        Country("Sweden", "se"),
        Country("Singapore", "sg"),
        Country("Slovakia", "sk"),
        Country("Thailand", "th"),
        Country("Turkey", "tr"),
        Country("Taiwan", "tw"),
        Country("Ukraine", "ua"),
        Country("United States", "us"),
        Country("Venezuela", "ve"),
        Country("South Africa", "za")
    )

    val LANGUAGE_LIST = listOf(
        Language("Arabic", "ar"),
        Language("German", "de"),
        Language("English", "en"),
        Language("Spanish", "es"),
        Language("French", "fr"),
        Language("Hebrew", "he"),
        Language("Italian", "it"),
        Language("Dutch", "nl"),
        Language("Norwegian", "no"),
        Language("Portuguese", "pt"),
        Language("Russian", "ru"),
        Language("Swedish", "sv"),
        Language("Chinese", "zh")
    )
}