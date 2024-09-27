package org.grating.recolldroid.data.fake

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.json.Json
import org.grating.recolldroid.R
import org.grating.recolldroid.data.RecollResult

object FakeResultsDataProvider {

    @Composable
    fun getSampleResults(): List<RecollResult> {
        val context = LocalContext.current
        val json = context.resources.openRawResource(R.raw.sample_query_results)
            .bufferedReader().use { it.readText() }
        return Json.decodeFromString<List<RecollResult>>(json);
    }

    @Composable
    fun getFirstResult(): RecollResult {
        return getSampleResults().first()
    }
}

