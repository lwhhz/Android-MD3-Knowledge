package com.example.m3appbar.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.text.input.rememberTextFieldState
import kotlinx.coroutines.launch
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.m3appbar.ui.M3SearchMetrics

/**
 * Search bar（contained）。
 *
 * 官方：宽 360–720dp、高 56dp、avatar 30dp、未聚焦 24dp / 聚焦 12dp 边距。
 * 展开后使用 Material3 SearchBar 的全屏搜索态；建议列表在 suggestions 槽。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    enabled: Boolean = true,
    showLeadingIcon: Boolean = true,
    showTrailingIcon: Boolean = true,
    showAvatar: Boolean = false,
) {
    val latestQuery by rememberUpdatedState(query)
    val latestExpanded by rememberUpdatedState(expanded)
    val latestOnQueryChange by rememberUpdatedState(onQueryChange)
    val latestOnExpandedChange by rememberUpdatedState(onExpandedChange)
    val searchState = rememberSearchBarState(
        initialValue = if (expanded) SearchBarValue.Expanded else SearchBarValue.Collapsed,
    )
    val textState = rememberTextFieldState(query)

    LaunchedEffect(query) {
        if (textState.text.toString() != query) textState.setTextAndPlaceCursorAtEnd(query)
    }
    LaunchedEffect(textState) {
        snapshotFlow { textState.text.toString() }.collect { text ->
            if (text != latestQuery) latestOnQueryChange(text)
        }
    }
    LaunchedEffect(expanded) {
        val target = if (expanded) SearchBarValue.Expanded else SearchBarValue.Collapsed
        if (searchState.targetValue != target) {
            if (target == SearchBarValue.Expanded) searchState.animateToExpanded() else searchState.animateToCollapsed()
        }
    }
    LaunchedEffect(searchState) {
        snapshotFlow { searchState.currentValue }.collect { value ->
            val isExpanded = value != SearchBarValue.Collapsed
            if (isExpanded != latestExpanded) latestOnExpandedChange(isExpanded)
        }
    }

    M3SearchBar(
        state = searchState,
        textFieldState = textState,
        modifier = modifier,
        placeholder = placeholder,
        suggestions = suggestions,
        onSuggestionClick = onSuggestionClick,
        enabled = enabled,
        showLeadingIcon = showLeadingIcon,
        showTrailingIcon = showTrailingIcon,
        showAvatar = showAvatar,
    )
}

/** 新状态化 SearchBar：与 ExpandedFullScreenSearchBar 共享同一 state。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberM3SearchBarState(): SearchBarState = rememberSearchBarState()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchBar(
    state: SearchBarState,
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    enabled: Boolean = true,
    showLeadingIcon: Boolean = true,
    showTrailingIcon: Boolean = true,
    showAvatar: Boolean = false,
) {
    val scope = rememberCoroutineScope()
    BackHandler(
        enabled = state.currentValue != SearchBarValue.Collapsed || state.targetValue != SearchBarValue.Collapsed,
    ) {
        scope.launch { state.animateToCollapsed() }
    }

    val inputField: @Composable () -> Unit = {
        val expanded = state.targetValue != SearchBarValue.Collapsed
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = state,
            onSearch = { query ->
                onSearch(query)
                scope.launch { state.animateToCollapsed() }
            },
            enabled = enabled,
            placeholder = { Text(placeholder) },
            leadingIcon = if (showLeadingIcon) {
                { Icon(Icons.Filled.Search, contentDescription = null) }
            } else {
                null
            },
            trailingIcon = when {
                showAvatar -> {
                    {
                        Box(
                            modifier = Modifier
                                .size(M3SearchMetrics.avatarSize)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("A", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
                showTrailingIcon -> {
                    {
                        androidx.compose.material3.IconButton(
                            onClick = {
                                textFieldState.setTextAndPlaceCursorAtEnd("")
                                scope.launch { state.animateToCollapsed() }
                            },
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "关闭搜索")
                        }
                    }
                }
                else -> null
            },
        )
    }

    SearchBar(
        state = state,
        inputField = inputField,
        modifier = modifier.widthIn(min = M3SearchMetrics.minWidth, max = M3SearchMetrics.maxWidth).height(M3SearchMetrics.barHeight),
    )
    ExpandedFullScreenSearchBar(state = state, inputField = inputField) {
        suggestions.forEach { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        textFieldState.setTextAndPlaceCursorAtEnd(suggestion)
                        onSuggestionClick(suggestion)
                        scope.launch { state.animateToCollapsed() }
                    }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


/** Full-screen layout：展开态全屏（compact 默认）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchFullScreenLayout(
    query: String,
    onQueryChange: (String) -> Unit,
    onDismiss: () -> Unit,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
) {
    M3SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        expanded = true,
        onExpandedChange = { if (!it) onDismiss() },
        modifier = modifier,
        placeholder = placeholder,
        suggestions = suggestions,
        onSuggestionClick = onSuggestionClick,
    )
}

/** Docked layout：搜索栏停在上方，建议在其下方（medium/expanded）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchDockedLayout(
    query: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
) {
    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { onExpandedChange(false) },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(placeholder) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.widthIn(min = M3SearchMetrics.minWidth, max = M3SearchMetrics.maxWidth),
    ) {
        suggestions.forEach { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSuggestionClick(suggestion)
                        onExpandedChange(false)
                    }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

/** Baseline full-screen：方角 4dp、surfaceContainer 容器。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchFullScreenLayoutBaseline(
    query: String,
    onQueryChange: (String) -> Unit,
    onDismiss: () -> Unit,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { onDismiss() },
                expanded = true,
                onExpandedChange = { if (!it) onDismiss() },
                placeholder = { Text(placeholder) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            )
        },
        expanded = true,
        onExpandedChange = { if (!it) onDismiss() },
        modifier = modifier.widthIn(min = M3SearchMetrics.minWidth, max = M3SearchMetrics.maxWidth),
        shape = RoundedCornerShape(M3SearchMetrics.baselineCorner),
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        suggestions.forEach { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion); onDismiss() }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

/** Baseline docked：方角 4dp、surfaceContainer 容器。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchDockedLayoutBaseline(
    query: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Hinted search text",
) {
    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { onExpandedChange(false) },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(placeholder) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.widthIn(min = M3SearchMetrics.minWidth, max = M3SearchMetrics.maxWidth),
        shape = RoundedCornerShape(M3SearchMetrics.baselineCorner),
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        suggestions.forEach { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion); onExpandedChange(false) }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
