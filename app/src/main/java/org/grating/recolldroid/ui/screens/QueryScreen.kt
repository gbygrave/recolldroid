package org.grating.recolldroid.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.grating.recolldroid.R
import org.grating.recolldroid.ui.theme.RecollDroidTheme

@Composable
fun QueryScreen(
    viewModel: RecollDroidViewModel = viewModel(),
    onQueryChanged: (query: String) -> Unit,
    onQueryExecuteRequest: () -> Unit
) {
    QueryBar(viewModel, onQueryChanged, onQueryExecuteRequest)
}

@Composable
fun QueryBar(
    viewModel: RecollDroidViewModel = viewModel(),
    onQueryChanged: (query: String) -> Unit,
    onQueryExecuteRequest: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    OutlinedTextField(
        value = uiState.currentQuery,
        onValueChange = {
            onQueryChanged(it)
        },
        placeholder = { Text(stringResource(R.string.enter_recoll_query)) },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.recoll),
                 contentDescription = stringResource(R.string.recoll_icon),
                 tint = Color.Unspecified,
                 modifier = Modifier.size(32.dp))
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp).fillMaxWidth(),
        enabled = true,
        singleLine = true,
        trailingIcon = {
            OutlinedIconButton(
                onClick = { onQueryExecuteRequest() }
            ) {
                Icon(imageVector = Icons.Outlined.Search,
                     contentDescription = stringResource(R.string.search_action))
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onQueryExecuteRequest() }),
    )
}

@Preview(showBackground = true)
@Composable
fun QueryScreenPreview() {
    RecollDroidTheme {
        QueryScreen(onQueryChanged = {},
                    onQueryExecuteRequest = {})
    }
}