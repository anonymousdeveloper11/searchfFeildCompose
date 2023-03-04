package com.example.searchfieldcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class MainViewModel: ViewModel() {

    private  val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private  val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val  persons = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_persons){
        text, persons ->
        if(text.isBlank()){
            persons
        }else{
            persons.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _persons.value)
    fun onSearchTextChnage(text: String){
        _searchText.value = text
    }
}
data class Person(
    val firstName: String,
    val lastName: String
){
    fun doesMatchSearchQuery(query:String): Boolean{
        val matchingCombinations = listOf("$firstName$lastName",
        "$firstName $lastName",
        "${firstName.first()}${lastName.first()}",)
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }

}

private val allPersons = listOf(

    Person(
        firstName = "Bhim",
        lastName = "Dangi"
    ),
    Person(
        firstName = "Doron",
        lastName = "Bell"
    ),
    Person(
        firstName = "Jackie",
        lastName = "Bol"
    ),
    Person(
        firstName = "Lolo",
        lastName = "Let"
    ),
    Person(
        firstName = "Loki",
        lastName = "Let"
    ),

)