package org.grating.recolldroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.grating.recolldroid.R
import org.grating.recolldroid.data.RecollResult
import org.grating.recolldroid.data.fake.FakeResultsDataProvider
import org.grating.recolldroid.ui.readableFileSize
import org.grating.recolldroid.ui.theme.RecollDroidTheme


@Composable
fun ResultsScreen(
    viewModel: RecollDroidViewModel,
    onQueryChanged: (query: String) -> Unit,
    onQueryExecuteRequest: () -> Unit,
    results: List<RecollResult>,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column {
        QueryBar(viewModel = viewModel,
                 onQueryChanged = onQueryChanged,
                 onQueryExecuteRequest = onQueryExecuteRequest)
        Results(viewModel = viewModel,
                results = results,
                modifier = Modifier)
    }
}

@Composable
fun Results(viewModel: RecollDroidViewModel,
            results: List<RecollResult>,
            modifier: Modifier = Modifier,
            contentPadding: PaddingValues = PaddingValues(0.dp)) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier
            .fillMaxSize()
        //.background(color = MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        items(results) { result ->
            ResultCard(result, modifier)
        }
    }
}

/**
 * UI composable for displaying a single search result.
 */
@Composable
fun ResultCard(
    result: RecollResult,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
    ) {
        Row(modifier = modifier) {
            Image(
                painter = painterResource(result.mType.typeIcon),
                contentDescription = "HTML Icon",
                modifier = Modifier
                    .size(64.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = result.title, style = MaterialTheme.typography.titleMedium)
                Text(text = result.fBytes.readableFileSize())
                Text(text = result.abstract)
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text(text = stringResource(R.string.preview_btn),
                     maxLines = 1)
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text(text = stringResource(R.string.open_btn),
                     maxLines = 1)
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text(text = stringResource(R.string.snippets_btn),
                     maxLines = 1)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResultCardPreview() {
    RecollDroidTheme {
        ResultCard(
            result = FakeResultsDataProvider.getFirstResult(),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    RecollDroidTheme {
        ResultsScreen(RecollDroidViewModel(),
                      {},
                      {},
                      FakeResultsDataProvider.getSampleResults())
    }
}