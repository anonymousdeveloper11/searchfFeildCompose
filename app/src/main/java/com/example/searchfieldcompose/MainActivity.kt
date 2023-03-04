package com.example.searchfieldcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.searchfieldcompose.ui.theme.SearchFieldcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchFieldcomposeTheme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val persons by viewModel.persons.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                // A surface container using the 'background' color from the theme
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    TextField(value = searchText, onValueChange = viewModel::onSearchTextChnage,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Search") })
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (isSearching) {
Box(modifier = Modifier.fillMaxSize()){
    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
}
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.weight(1f)
                    ) {

                        items(persons) { persons ->
                            Text(
                                text = "${persons.firstName} ${persons.lastName}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            )
                        }
                    }
                }
            }
    }
}

}