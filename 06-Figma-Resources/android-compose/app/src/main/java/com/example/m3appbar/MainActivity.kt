package com.example.m3appbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.example.m3appbar.components.M3ExtendedFAB
import com.example.m3appbar.components.M3ToggleButton
import com.example.m3appbar.components.M3ToggleButtonElevated
import com.example.m3appbar.components.M3ToggleButtonOutline
import com.example.m3appbar.components.M3ToggleButtonTonal
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import com.example.m3appbar.components.M3FABMenu
import com.example.m3appbar.components.M3FabMenuItem
import com.example.m3appbar.ui.M3IconButtonWidth
import com.example.m3appbar.components.M3FAB
import com.example.m3appbar.components.M3IconButton
import com.example.m3appbar.components.M3IconButtonOutline
import com.example.m3appbar.components.M3IconButtonStandard
import com.example.m3appbar.components.M3IconButtonTogglable
import com.example.m3appbar.components.M3IconButtonTogglableOutline
import com.example.m3appbar.components.M3IconButtonTogglableTonal
import com.example.m3appbar.components.M3IconButtonTonal
import com.example.m3appbar.ui.M3ExtendedFabSize
import com.example.m3appbar.ui.M3FabColor
import com.example.m3appbar.ui.M3FabSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.m3appbar.components.M3Carousel
import com.example.m3appbar.components.M3CarouselFullScreen
import com.example.m3appbar.components.M3Keyboard
import com.example.m3appbar.components.M3ExamplesLayoutGrid
import com.example.m3appbar.components.Configuration
import com.example.m3appbar.components.LayoutRegions
import com.example.m3appbar.components.WindowSizeClass
import com.example.m3appbar.components.M3PlainTooltip
import com.example.m3appbar.components.M3GenericAvatar
import com.example.m3appbar.components.M3ShapeSet
import com.example.m3appbar.components.Shape as M3Shape
import com.example.m3appbar.components.Style
import com.example.m3appbar.components.Type
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import com.example.m3appbar.components.M3ModalDatePicker
import com.example.m3appbar.components.M3InputDatePicker
import com.example.m3appbar.components.M3DialPicker
import com.example.m3appbar.components.M3KeyboardPicker
import com.example.m3appbar.components.M3DockedInputDatePickerDesktop
import com.example.m3appbar.components.Format
import com.example.m3appbar.components.Orientation
import com.example.m3appbar.components.M3CarouselItem
import com.example.m3appbar.ui.M3CarouselLayout
import com.example.m3appbar.components.M3Checkboxes
import com.example.m3appbar.components.M3RadioButtons
import com.example.m3appbar.components.M3AssistiveChip
import com.example.m3appbar.components.M3ChipGroups
import com.example.m3appbar.components.M3FilterChip
import com.example.m3appbar.components.M3InputChip
import com.example.m3appbar.components.M3TabItem
import com.example.m3appbar.components.M3Toolbar
import com.example.m3appbar.components.M3ToolbarOverflowItem
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection
import com.example.m3appbar.ui.M3ToolbarColor
import com.example.m3appbar.ui.M3ToolbarConfiguration
import com.example.m3appbar.ui.M3ToolbarOrientation
import com.example.m3appbar.components.M3Tabs
import com.example.m3appbar.ui.M3TabConfiguration
import com.example.m3appbar.ui.M3TabLayout
import com.example.m3appbar.ui.M3TabStyle
import com.example.m3appbar.components.M3SuggestionChip
import com.example.m3appbar.ui.M3ChipGroupLayout
import com.example.m3appbar.ui.M3ChipStyle
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.m3appbar.components.M3BasicDialog
import com.example.m3appbar.components.M3ListDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.example.m3appbar.components.M3SearchBar
import com.example.m3appbar.components.rememberM3SearchBarState
import com.example.m3appbar.components.M3SearchDockedLayout
import com.example.m3appbar.components.M3Segment
import com.example.m3appbar.components.M3SegmentedButton
import com.example.m3appbar.ui.M3SegmentedButtonDensity
import com.example.m3appbar.components.M3SnackbarHost
import com.example.m3appbar.components.showM3Snackbar
import com.example.m3appbar.components.M3ScrollableListDialog
import com.example.m3appbar.components.M3CircularDeterminateProgressIndicator
import com.example.m3appbar.components.M3CircularIndeterminateProgressIndicator
import com.example.m3appbar.components.M3LinearDeterminateProgressIndicator
import com.example.m3appbar.components.M3LinearIndeterminateProgressIndicator
import com.example.m3appbar.components.M3LoadingIndicator
import com.example.m3appbar.components.M3MenuItemData
import com.example.m3appbar.components.M3Menu
import com.example.m3appbar.components.M3MenuBaseline
import com.example.m3appbar.ui.M3MenuTheme
import com.example.m3appbar.components.M3NavItem
import com.example.m3appbar.components.M3NavigationBar
import com.example.m3appbar.components.M3NavigationDrawer
import com.example.m3appbar.components.M3NavigationRail
import com.example.m3appbar.ui.M3NavRailAlignment
import kotlinx.coroutines.launch
import com.example.m3appbar.components.M3AppBar
import com.example.m3appbar.components.M3AppBarScrollDemo
import com.example.m3appbar.components.M3ToolbarScrollDemo
import com.example.m3appbar.components.M3BottomAppBar
import com.example.m3appbar.components.AppBarConfiguration
import com.example.m3appbar.components.AppBarElevation
import com.example.m3appbar.components.TrailingAction
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Edit
import com.example.m3appbar.components.M3Badges
import com.example.m3appbar.ui.M3BadgeSize
import com.example.m3appbar.components.M3BottomSheet
import com.example.m3appbar.components.M3ConnectedButtonGroup
import com.example.m3appbar.ui.M3ButtonGroupSelection
import com.example.m3appbar.ui.M3ButtonGroupShape
import com.example.m3appbar.ui.M3ButtonGroupSize
import com.example.m3appbar.components.M3CenteredSlider
import com.example.m3appbar.components.M3RangeSlider
import com.example.m3appbar.components.M3StandardSlider
import com.example.m3appbar.ui.M3SliderSize
import com.example.m3appbar.components.M3Switch
import com.example.m3appbar.components.M3TextField
import com.example.m3appbar.ui.M3TextFieldStyle
import com.example.m3appbar.components.M3Button
import com.example.m3appbar.components.M3ButtonVisual
import com.example.m3appbar.ui.M3ButtonShape
import com.example.m3appbar.ui.M3ButtonSize
import com.example.m3appbar.ui.M3ButtonStyle
import com.example.m3appbar.ui.M3InteractionState
import com.example.m3appbar.ui.M3StateLayer
import com.example.m3appbar.ui.theme.M3KitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3KitTheme(dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MetricsBoard()
                }
            }
        }
    }
}

/**
 * 0.1/0.2 的可见验收面：把官方数值直接画出来，方便对照 KB。
 * 不依赖任何实验性 API，也不引用历史转写组件（0.4 才接入）。
 */
@Composable
fun MetricsBoard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "M3 Kit · 0.1 / 0.2",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = "official values · material3 1.5.0-rc01",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SectionTitle("M3Button · 5 styles")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            M3ButtonStyle.entries.forEach { style ->
                M3Button(
                    label = style.name.lowercase().replaceFirstChar { it.uppercase() },
                    onClick = {},
                    style = style,
                )
            }
        }

        SectionTitle("M3Button · states")
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            M3InteractionState.entries.forEach { state ->
                M3ButtonVisual(
                    label = "State " + state.name.lowercase(),
                    state = state,
                    showFocusIndicator = true,
                )
            }
        }

        SectionTitle("M3Button · sizes")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3Button(label = "XS", onClick = {}, size = M3ButtonSize.XS)
            M3Button(label = "S", onClick = {}, size = M3ButtonSize.S)
            M3Button(label = "M", onClick = {}, size = M3ButtonSize.M)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3Button(label = "Large", onClick = {}, size = M3ButtonSize.L)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3Button(label = "X-Large", onClick = {}, size = M3ButtonSize.XL)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3Button(label = "Square", onClick = {}, shape = M3ButtonShape.SQUARE)
            M3Button(label = "Disabled", onClick = {}, enabled = false)
            M3Button(
                label = "Icon",
                onClick = {},
                showIcon = true,
                icon = rememberVectorPainter(Icons.Filled.Add),
            )
        }


        SectionTitle("M3IconButton · 4 styles")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            M3IconButton(Icons.Filled.Add, "添加", {})
            M3IconButtonTonal(Icons.Filled.Add, "添加", {})
            M3IconButtonOutline(Icons.Filled.Add, "添加", {})
            M3IconButtonStandard(Icons.Filled.Add, "添加", {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            M3IconButtonTogglable(Icons.Filled.Check, "收藏", selected = false, onClick = {})
            M3IconButtonTogglable(Icons.Filled.Check, "收藏", selected = true, onClick = {})
            M3IconButtonTogglableTonal(Icons.Filled.Check, "收藏", selected = true, onClick = {})
            M3IconButtonTogglableOutline(Icons.Filled.Check, "收藏", selected = true, onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            M3IconButton(Icons.Filled.Add, "narrow", {}, width = M3IconButtonWidth.NARROW)
            M3IconButton(Icons.Filled.Add, "default", {}, width = M3IconButtonWidth.DEFAULT)
            M3IconButton(Icons.Filled.Add, "wide", {}, width = M3IconButtonWidth.WIDE)
        }


        SectionTitle("M3ToggleButton · 4 styles")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3ToggleButton(label = "off", selected = false, onClick = {})
            M3ToggleButton(label = "on", selected = true, onClick = {})
            M3ToggleButtonTonal(label = "off", selected = false, onClick = {})
            M3ToggleButtonTonal(label = "on", selected = true, onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3ToggleButtonElevated(label = "off", selected = false, onClick = {})
            M3ToggleButtonElevated(label = "on", selected = true, onClick = {})
            M3ToggleButtonOutline(label = "off", selected = false, onClick = {})
            M3ToggleButtonOutline(label = "on", selected = true, onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            M3ToggleButton(
                label = "icon on",
                selected = true,
                showIcon = true,
                icon = rememberVectorPainter(Icons.Filled.Add),
                selectedIcon = rememberVectorPainter(Icons.Filled.Check),
                onClick = {},
            )
        }

        SectionTitle("M3FAB · sizes / colors")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.SMALL)
            M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.DEFAULT)
            M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.MEDIUM)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            M3FAB(Icons.Filled.Add, "添加", {}, size = M3FabSize.LARGE)
            M3FAB(Icons.Filled.Add, "添加", {}, color = M3FabColor.TERTIARY_CONTAINER)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            M3ExtendedFAB(label = "Compose", onClick = {}, imageVector = Icons.Filled.Add, contentDescription = "写新消息")
            M3ExtendedFAB(label = "Checkout", onClick = {}, size = M3ExtendedFabSize.MEDIUM)
        }
        M3FABMenu(
            imageVector = Icons.Filled.Add,
            items = listOf(
                M3FabMenuItem("Search", Icons.Filled.Search, {}),
                M3FabMenuItem("Add", Icons.Filled.Add, {}),
                M3FabMenuItem("Done", Icons.Filled.Check, {}),
            ),
            initiallyExpanded = true,
        )


        SectionTitle("M3TextField · filled / outlined / error")
        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("Ada") }
        M3TextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "name@example.com",
            supportingText = "用于登录和找回密码",
            leadingIcon = Icons.Filled.Person,
            modifier = Modifier.fillMaxWidth(),
        )
        M3TextField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
            style = M3TextFieldStyle.OUTLINED,
            supportingText = "必填 *",
            trailingIcon = Icons.Filled.Search,
            trailingIconDescription = "搜索",
            modifier = Modifier.fillMaxWidth(),
        )
        M3TextField(
            value = "not-an-email",
            onValueChange = {},
            label = "Email",
            isError = true,
            supportingText = "用于登录和找回密码",
            errorText = "请输入有效的邮箱地址",
            modifier = Modifier.fillMaxWidth(),
        )
        M3TextField(
            value = "",
            onValueChange = {},
            label = "Disabled",
            enabled = false,
            supportingText = "不可编辑",
            modifier = Modifier.fillMaxWidth(),
        )


        SectionTitle("M3Checkbox / M3Radio / M3Switch")
        var checkA by remember { mutableStateOf(true) }
        var checkB by remember { mutableStateOf(false) }
        var radioIndex by remember { mutableStateOf(0) }
        var switchA by remember { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            M3Checkboxes(checked = checkA, onCheckedChange = { checkA = it })
            M3Checkboxes(checked = false, indeterminate = true, onCheckedChange = {})
            M3Checkboxes(checked = checkB, isError = true, onCheckedChange = { checkB = it })
            M3Checkboxes(checked = false, enabled = false, onCheckedChange = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            M3RadioButtons(selected = radioIndex == 0, onClick = { radioIndex = 0 })
            M3RadioButtons(selected = radioIndex == 1, onClick = { radioIndex = 1 })
            M3RadioButtons(selected = true, isError = true, onClick = {})
            M3RadioButtons(selected = false, enabled = false, onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            M3Switch(checked = switchA, onCheckedChange = { switchA = it })
            M3Switch(checked = true, onCheckedChange = {})
            M3Switch(checked = true, isError = true, onCheckedChange = {})
            M3Switch(checked = false, enabled = false, onCheckedChange = {})
        }


        SectionTitle("M3Slider · standard / centered / range")
        var sliderA by remember { mutableStateOf(50f) }
        var sliderB by remember { mutableStateOf(0f) }
        var sliderRange by remember { mutableStateOf(20f..80f) }
        M3StandardSlider(
            value = sliderA,
            onValueChange = { sliderA = it },
            size = M3SliderSize.M,
            steps = 1,
            showStops = true,
            showIcon = true,
            icon = Icons.Filled.Search,
        )
        M3CenteredSlider(
            value = sliderB,
            onValueChange = { sliderB = it },
            size = M3SliderSize.S,
        )
        M3RangeSlider(
            values = sliderRange,
            onValueChange = { sliderRange = it },
            size = M3SliderSize.M,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            M3StandardSlider(
                value = sliderA,
                onValueChange = { sliderA = it },
                size = M3SliderSize.XS,
                modifier = Modifier.weight(1f),
            )
            M3StandardSlider(
                value = sliderA,
                onValueChange = { sliderA = it },
                size = M3SliderSize.L,
                modifier = Modifier.weight(1f),
            )
        }


        SectionTitle("M3Chip · assist / filter / input / suggestion")
        var filterOn by remember { mutableStateOf(true) }
        var inputChips by remember { mutableStateOf(listOf("Alice", "Bob")) }
        M3ChipGroups(layout = M3ChipGroupLayout.WRAP) {
            M3AssistiveChip(label = "Add", onClick = {}, leadingIcon = Icons.Filled.Add)
            M3AssistiveChip(label = "Saved", onClick = {}, style = M3ChipStyle.ELEVATED, leadingIcon = Icons.Filled.Check)
            M3FilterChip(
                label = "Unread",
                selected = filterOn,
                onClick = { filterOn = !filterOn },
                showTrailingIcon = true,
                trailingIcon = Icons.Filled.Close,
            )
            M3FilterChip(label = "Images", selected = false, onClick = {})
            M3SuggestionChip(label = "Try dark mode", onClick = {}, leadingIcon = Icons.Filled.Search, style = M3ChipStyle.ELEVATED)
            M3SuggestionChip(label = "Nearby", onClick = {}, leadingIcon = Icons.Filled.Search)
        }


        M3ChipGroups(layout = M3ChipGroupLayout.WRAP) {
            inputChips.forEach { name ->
                M3InputChip(
                    label = name,
                    selected = false,
                    onClick = {},
                    onRemove = { inputChips = inputChips - name },
                )
            }
        }

        SectionTitle("M3Tabs · primary / secondary / scrollable")
        var tabPrimary by remember { mutableStateOf(0) }
        var tabIcon by remember { mutableStateOf(0) }
        var tabSecondary by remember { mutableStateOf(0) }
        var tabScroll by remember { mutableStateOf(0) }
        M3Tabs(
            tabs = listOf(M3TabItem("Overview"), M3TabItem("Specs"), M3TabItem("Usage", showBadge = true)),
            selectedIndex = tabPrimary,
            onSelect = { tabPrimary = it },
        )
        M3Tabs(
            tabs = listOf(
                M3TabItem("Home", Icons.Filled.Home),
                M3TabItem("Search", Icons.Filled.Search),
                M3TabItem("Settings", Icons.Filled.Settings, showBadge = true),
            ),
            selectedIndex = tabIcon,
            onSelect = { tabIcon = it },
            layout = M3TabLayout.ICON_AND_LABEL,
        )
        M3Tabs(
            tabs = listOf(M3TabItem("Inbox"), M3TabItem("Archive")),
            selectedIndex = tabSecondary,
            onSelect = { tabSecondary = it },
            style = M3TabStyle.SECONDARY,
        )
        M3Tabs(
            tabs = listOf("One","Two","Three","Four","Five","Six").map { M3TabItem(it) },
            selectedIndex = tabScroll,
            onSelect = { tabScroll = it },
            configuration = M3TabConfiguration.SCROLLABLE,
        )


        SectionTitle("M3Badges · small / large / count / label")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            M3Badges(size = M3BadgeSize.SMALL)
            M3Badges(size = M3BadgeSize.LARGE, count = 3)
            M3Badges(size = M3BadgeSize.LARGE, count = 120)
            M3Badges(size = M3BadgeSize.LARGE, label = "NEW")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
            BadgedBox(badge = { M3Badges(size = M3BadgeSize.SMALL) }) {
                Icon(Icons.Filled.Settings, contentDescription = null, modifier = Modifier.size(24.dp))
            }
            BadgedBox(badge = { M3Badges(size = M3BadgeSize.LARGE, count = 12) }) {
                Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(24.dp))
            }
        }


        SectionTitle("M3Navigation · bar / rail / drawer")
        var navBarIndex by remember { mutableStateOf(0) }
        var navRailIndex by remember { mutableStateOf(0) }
        var navDrawerIndex by remember { mutableStateOf(0) }
        val navItems = listOf(
            M3NavItem("Home", Icons.Filled.Home),
            M3NavItem("Search", Icons.Filled.Search, badge = "3"),
            M3NavItem("Settings", Icons.Filled.Settings),
            M3NavItem("Saved", Icons.Filled.Check, badge = "99+"),
        )
        M3NavigationBar(items = navItems, selectedIndex = navBarIndex, onSelect = { navBarIndex = it })
        M3NavigationBar(
            items = navItems.take(3),
            selectedIndex = navBarIndex.coerceAtMost(2),
            onSelect = { navBarIndex = it },
            layout = com.example.m3appbar.ui.M3NavBarItemLayout.HORIZONTAL,
        )
        Box(Modifier.height(320.dp)) {
            M3NavigationRail(
                items = navItems,
                selectedIndex = navRailIndex,
                onSelect = { navRailIndex = it },
                alignment = M3NavRailAlignment.CENTER,
                header = { Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(24.dp)) },
            )
        }
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        M3NavigationDrawer(
            items = navItems,
            selectedIndex = navDrawerIndex,
            onSelect = { navDrawerIndex = it },
            drawerState = drawerState,
            modifier = Modifier.height(200.dp),
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = { scope.launch { drawerState.open() } }) { Text("Open drawer") }
            }
        }


        SectionTitle("M3Menu · standard / vibrant / baseline")
        var standardMenuOpen by remember { mutableStateOf(false) }
        var vibrantMenuOpen by remember { mutableStateOf(false) }
        var baselineMenuOpen by remember { mutableStateOf(false) }
        var longMenuOpen by remember { mutableStateOf(false) }
        val menuItems = listOf(
            M3MenuItemData("Cut", leadingIcon = Icons.Filled.Close, trailingText = "⌘X"),
            M3MenuItemData("Copy", leadingIcon = Icons.Filled.Check, selected = true),
            M3MenuItemData("Paste", leadingIcon = Icons.Filled.Add, enabled = false),
            M3MenuItemData("More", supportingText = "Additional actions"),
        )
        Box {
            Button(onClick = { standardMenuOpen = true }) { Text("Standard menu") }
            M3Menu(
                expanded = standardMenuOpen,
                onDismissRequest = { standardMenuOpen = false },
                items = menuItems,
                onItemClick = { standardMenuOpen = false },
                groups = 2,
                showSectionLabel = true,
                sectionLabel = "Edit",
            )
        }
        Box {
            Button(onClick = { vibrantMenuOpen = true }) { Text("Vibrant menu") }
            M3Menu(
                expanded = vibrantMenuOpen,
                onDismissRequest = { vibrantMenuOpen = false },
                items = menuItems.take(2),
                onItemClick = { vibrantMenuOpen = false },
                theme = M3MenuTheme.VIBRANT,
            )
        }

        Box {
            Button(onClick = { longMenuOpen = true }) { Text("Long menu") }
            M3Menu(
                expanded = longMenuOpen,
                onDismissRequest = { longMenuOpen = false },
                items = (1..20).map { M3MenuItemData("Action " + it) },
                onItemClick = { longMenuOpen = false },
                showScrollbar = true,
                showFocusIndicator = true,
            )
        }
        Box {
            Button(onClick = { baselineMenuOpen = true }) { Text("Baseline menu") }
            M3MenuBaseline(
                expanded = baselineMenuOpen,
                onDismissRequest = { baselineMenuOpen = false },
                items = listOf("Cut", "Copy", "Paste"),
                onItemClick = { baselineMenuOpen = false },
            )
        }


        SectionTitle("M3Dialog · basic / list / scrollable")
        var basicDialogOpen by remember { mutableStateOf(false) }
        var listDialogOpen by remember { mutableStateOf(false) }
        var scrollDialogOpen by remember { mutableStateOf(false) }
        Button(onClick = { basicDialogOpen = true }) { Text("Basic dialog") }
        Button(onClick = { listDialogOpen = true }) { Text("List dialog") }
        Button(onClick = { scrollDialogOpen = true }) { Text("Scrollable list dialog") }
        if (basicDialogOpen) {
            M3BasicDialog(
                title = "Delete this note?",
                supportingText = "This action cannot be undone. The note and its attachments will be permanently removed.",
                confirmLabel = "Delete",
                dismissLabel = "Cancel",
                onConfirm = { basicDialogOpen = false },
                onDismiss = { basicDialogOpen = false },
                onDismissRequest = { basicDialogOpen = false },
                icon = Icons.Filled.Close,
                showDivider = true,
            )
        }
        if (listDialogOpen) {
            M3ListDialog(
                title = "Pick a folder",
                items = listOf("Inbox", "Archive", "Trash"),
                onItemClick = { listDialogOpen = false },
                onDismissRequest = { listDialogOpen = false },
            )
        }
        if (scrollDialogOpen) {
            M3ScrollableListDialog(
                title = "Pick a tag",
                items = (1..12).map { "Tag " + it },
                onItemClick = { scrollDialogOpen = false },
                onDismissRequest = { scrollDialogOpen = false },
            )
        }


        SectionTitle("M3Snackbar · auto-dismiss / action / FAB avoidance")
        val snackbarHostState = remember { SnackbarHostState() }
        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = {
                    M3SnackbarHost(
                        hostState = snackbarHostState,
                        maxLines = 2,
                        showCloseAffordance = true,
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {}) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                },
            ) { padding ->
                Column(
                    modifier = Modifier.padding(padding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showM3Snackbar(
                                    message = "Message archived. Auto-dismiss in 4 seconds.",
                                )
                            }
                        },
                    ) { Text("Auto-dismiss") }
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showM3Snackbar(
                                    message = "Note archived. You can undo this action from the snackbar.",
                                    actionLabel = "Undo",
                                    withDismissAction = true,
                                )
                            }
                        },
                    ) { Text("With action") }
                }
            }
        }


        SectionTitle("M3Search · bar / docked / full-screen")
        var searchQuery by remember { mutableStateOf("") }
        val searchBarState = rememberM3SearchBarState()
        val searchTextFieldState = rememberTextFieldState()
        M3SearchBar(
            state = searchBarState,
            textFieldState = searchTextFieldState,
            suggestions = listOf("Compose", "Material 3", "Kotlin", "Figma"),
            onSuggestionClick = { searchQuery = it },
        )
        var dockedExpanded by remember { mutableStateOf(false) }
        M3SearchDockedLayout(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            expanded = dockedExpanded,
            onExpandedChange = { dockedExpanded = it },
            suggestions = listOf("Docked result A", "Docked result B"),
            onSuggestionClick = { searchQuery = it },
        )
        var legacyQuery by remember { mutableStateOf("") }
        var legacyExpanded by remember { mutableStateOf(false) }
        M3SearchBar(
            query = legacyQuery,
            onQueryChange = { legacyQuery = it },
            expanded = legacyExpanded,
            onExpandedChange = { legacyExpanded = it },
            suggestions = listOf("Legacy result A", "Legacy result B"),
            onSuggestionClick = { legacyQuery = it },
        )


        SectionTitle("M3Progress / Loading · determinate / indeterminate")
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            M3LinearDeterminateProgressIndicator(progress = 0.6f)
            M3LinearIndeterminateProgressIndicator()
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
                M3CircularDeterminateProgressIndicator(progress = 0.7f)
                M3CircularIndeterminateProgressIndicator()
                M3LoadingIndicator()
                M3LoadingIndicator(showContainer = true)
            }
        }


        SectionTitle("M3Toolbar · docked / floating / vibrant")
        M3Toolbar(
            configuration = M3ToolbarConfiguration.DOCKED,
            overflowItems = listOf(
                M3ToolbarOverflowItem("Delete"),
                M3ToolbarOverflowItem("Archive"),
            ),
        ) {
            IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "Add") }
            IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = "Search") }
            IconButton(onClick = {}) { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
        }
        M3Toolbar(
            configuration = M3ToolbarConfiguration.FLOATING,
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Filled.Add, contentDescription = "FAB")
                }
            },
        ) {
            IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = "Search") }
            IconButton(onClick = {}) { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
        }
        M3Toolbar(
            configuration = M3ToolbarConfiguration.FLOATING,
            color = M3ToolbarColor.VIBRANT,
            scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(exitDirection = FloatingToolbarExitDirection.Bottom),
        ) {
            IconButton(onClick = {}) { Icon(Icons.Filled.Check, contentDescription = "Done") }
            IconButton(onClick = {}) { Icon(Icons.Filled.Close, contentDescription = "Close") }
        }
        Box(modifier = Modifier.fillMaxWidth().height(280.dp), contentAlignment = Alignment.Center) {
            M3Toolbar(
                configuration = M3ToolbarConfiguration.FLOATING,
                orientation = M3ToolbarOrientation.VERTICAL,
                color = M3ToolbarColor.VIBRANT,
            ) {
                IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "Add") }
                IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = "Search") }
                IconButton(onClick = {}) { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
            }
        }


        val toolbarScrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
            exitDirection = FloatingToolbarExitDirection.Bottom,
        )
        Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().nestedScroll(toolbarScrollBehavior),
            ) {
                items(20) { index ->
                    Text(
                        text = "Scroll item " + (index + 1),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    )
                }
            }
            M3Toolbar(
                configuration = M3ToolbarConfiguration.FLOATING,
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                scrollBehavior = toolbarScrollBehavior,
            ) {
                IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "Add") }
                IconButton(onClick = {}) { Icon(Icons.Filled.Search, contentDescription = "Search") }
            }
        }



        SectionTitle("M3AppBar / M3BottomAppBar · configurations")
        val appBarMenu = rememberVectorPainter(Icons.Filled.Menu)
        val appBarSearch = rememberVectorPainter(Icons.Filled.Search)
        val appBarMore = rememberVectorPainter(Icons.Filled.MoreVert)
        val appBarAdd = rememberVectorPainter(Icons.Filled.Add)
        M3AppBar(
            headline = "Centered",
            configuration = AppBarConfiguration.SMALL_CENTERED,
            leadingIcon = appBarMenu,
            trailingActions = listOf(TrailingAction(appBarSearch, "Search", {})),
        )
        M3AppBar(
            headline = "Medium",
            supportingText = "Supporting text",
            configuration = AppBarConfiguration.MEDIUM_FLEXIBLE,
            elevation = AppBarElevation.ON_SCROLL,
            leadingIcon = appBarMenu,
            trailingActions = listOf(
                TrailingAction(appBarMore, "More", {}),
                TrailingAction(appBarSearch, "Search", {}),
            ),
            showTrailingAction1 = true,
            showTrailingAction2 = true,
        )
        M3BottomAppBar(
            iconsCount = 2,
            showFab = true,
            actions = listOf(
                TrailingAction(appBarSearch, "Search", {}),
                TrailingAction(appBarMore, "More", {}),
            ),
            fab = appBarAdd,
        )


        var showAppBarScroll by remember { mutableStateOf(false) }
        var showToolbarScroll by remember { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showAppBarScroll = true }) { Text("App bar scroll") }
            Button(onClick = { showToolbarScroll = true }) { Text("Toolbar scroll") }
        }
        if (showAppBarScroll) {
            M3AppBarScrollDemo(onDismissRequest = { showAppBarScroll = false })
        }
        if (showToolbarScroll) {
            M3ToolbarScrollDemo(onDismissRequest = { showToolbarScroll = false })
        }

        SectionTitle("M3BottomSheet · modal / standard")
        var sheetOpen by remember { mutableStateOf(false) }
        var tallSheetOpen by remember { mutableStateOf(false) }
        Button(onClick = { sheetOpen = true }) { Text("Open modal sheet") }
        if (sheetOpen) {
            M3BottomSheet(
                show = true,
                onDismissRequest = { sheetOpen = false },
                modal = true,
                showDragHandle = true,
            ) {
                Text("Share this note", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))
                Text(
                    "Modal bottom sheets show secondary content above a scrim and block interaction until dismissed.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { sheetOpen = false }) { Text("Cancel") }
                    TextButton(onClick = { sheetOpen = false }) { Text("Share") }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
        Button(onClick = { tallSheetOpen = true }) { Text("Open tall sheet") }
        if (tallSheetOpen) {
            M3BottomSheet(
                show = true,
                onDismissRequest = { tallSheetOpen = false },
                modal = true,
                showDragHandle = true,
            ) {
                Text("Tall sheet", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))
                repeat(20) { index ->
                    Text("Sheet item " + (index + 1), modifier = Modifier.padding(vertical = 6.dp))
                }
                Spacer(Modifier.height(24.dp))
            }
        }
        M3BottomSheet(
            show = true,
            modal = false,
            showDragHandle = true,
        ) {
            Text("Standard bottom sheet", style = MaterialTheme.typography.titleMedium)
            Text("Co-exists with main content; no scrim.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
        }


        SectionTitle("M3SegmentedButton · single / multi / density")
        var segmentIndex by remember { mutableStateOf(0) }
        var multiSegments by remember { mutableStateOf(setOf(0)) }
        M3SegmentedButton(
            segments = listOf(M3Segment("Day"), M3Segment("Week"), M3Segment("Month")),
            selectedIndex = segmentIndex,
            onSelect = { segmentIndex = it },
        )
        M3SegmentedButton(
            segments = listOf(M3Segment("Small"), M3Segment("Medium"), M3Segment("Large")),
            selectedIndex = segmentIndex,
            onSelect = { segmentIndex = it },
            density = M3SegmentedButtonDensity.D2,
        )
        M3SegmentedButton(
            segments = listOf(M3Segment("Wi-Fi"), M3Segment("Bluetooth"), M3Segment("NFC")),
            selectedIndex = 0,
            onSelect = {},
            multiSelect = true,
            selectedIndices = multiSegments,
            onSelectionChange = { multiSegments = it },
        )


        SectionTitle("M3ConnectedButtonGroup · single / multi / required")
        var connectedSingle by remember { mutableStateOf(setOf(0)) }
        var connectedMulti by remember { mutableStateOf(setOf(0, 1)) }
        var connectedRequired by remember { mutableStateOf(setOf(1)) }
        M3ConnectedButtonGroup(
            labels = listOf("Day", "Week", "Month"),
            selected = connectedSingle,
            onSelectionChange = { connectedSingle = it },
            selection = M3ButtonGroupSelection.SINGLE,
        )
        M3ConnectedButtonGroup(
            labels = listOf("Wi-Fi", "BT", "NFC"),
            selected = connectedMulti,
            onSelectionChange = { connectedMulti = it },
            selection = M3ButtonGroupSelection.MULTI,
            size = M3ButtonGroupSize.S,
        )
        M3ConnectedButtonGroup(
            labels = listOf("Low", "Med", "High"),
            selected = connectedRequired,
            onSelectionChange = { connectedRequired = it },
            selection = M3ButtonGroupSelection.REQUIRED,
            size = M3ButtonGroupSize.M,
        )

        M3ConnectedButtonGroup(
            labels = listOf("A", "B", "C"),
            selected = connectedRequired,
            onSelectionChange = { connectedRequired = it },
            selection = M3ButtonGroupSelection.SINGLE,
            shape = M3ButtonGroupShape.SQUARE,
            size = M3ButtonGroupSize.S,
        )


        SectionTitle("M3Carousel · multi-browse / hero / full-screen")
        val carouselItems = listOf(
            M3CarouselItem("Mountain", image = painterResource(R.drawable.carousel_mountain), contentDescription = "Mountain landscape"),
            M3CarouselItem("Coast", image = painterResource(R.drawable.carousel_coast), contentDescription = "Coastline"),
            M3CarouselItem("Forest", image = painterResource(R.drawable.carousel_forest), contentDescription = "Forest"),
            M3CarouselItem("Mountain at dusk", image = painterResource(R.drawable.carousel_mountain), contentDescription = "Mountain at dusk"),
            M3CarouselItem("Coast at dawn", image = painterResource(R.drawable.carousel_coast), contentDescription = "Coast at dawn"),
        )
        M3Carousel(
            items = carouselItems,
            layout = M3CarouselLayout.MULTI_BROWSE,
            onShowAll = {},
        )
        M3Carousel(
            items = carouselItems,
            layout = M3CarouselLayout.HERO,
        )
        M3CarouselFullScreen(items = carouselItems.take(3))
        M3Carousel(
            items = carouselItems,
            layout = M3CarouselLayout.MULTI_ASPECT,
        )


        SectionTitle("M3Date/Time pickers · modal / input / dial / keyboard / docked")
        var showModalDate by remember { mutableStateOf(false) }
        var showInputDate by remember { mutableStateOf(false) }
        var showMonthYear by remember { mutableStateOf(false) }
        var showFullRange by remember { mutableStateOf(false) }
        var showDialPicker by remember { mutableStateOf(false) }
        var showKeyboardPicker by remember { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showModalDate = true }) { Text("Open modal date") }
            Button(onClick = { showInputDate = true }) { Text("Open input date") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showMonthYear = true }) { Text("Open month/year") }
            Button(onClick = { showFullRange = true }) { Text("Open full range") }
        }
        if (showModalDate) {
            M3ModalDatePicker(
                show = true,
                onDismissRequest = { showModalDate = false },
            )
        }
        if (showInputDate) {
            M3InputDatePicker(
                show = true,
                onDismissRequest = { showInputDate = false },
            )
        }
        if (showMonthYear) {
            M3ModalDatePicker(
                show = true,
                onDismissRequest = { showMonthYear = false },
                type = Type.MONTH,
            )
        }
        if (showFullRange) {
            M3ModalDatePicker(
                show = true,
                onDismissRequest = { showFullRange = false },
                type = Type.FULL_SCREEN_RANGE,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showDialPicker = true }) { Text("Open dial") }
            Button(onClick = { showKeyboardPicker = true }) { Text("Open keyboard") }
        }
        if (showDialPicker) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showDialPicker = false }) {
                M3DialPicker(format = Format.N12_HOUR, orientation = Orientation.VERTICAL)
            }
        }
        if (showKeyboardPicker) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showKeyboardPicker = false }) {
                M3KeyboardPicker(format = Format.N24_HOUR)
            }
        }


        SectionTitle("M3PlainTooltip / M3GenericAvatar / M3ShapeSet")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            M3PlainTooltip(text = "Add item", type = Type.SINGLE_LINE) {
                IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "Add") }
            }
            M3PlainTooltip(text = "Delete item permanently", type = Type.MULTI_LINE) {
                IconButton(onClick = {}) { Icon(Icons.Filled.Delete, contentDescription = "Delete") }
            }
            M3GenericAvatar(style = Style.AVATAR, letter = "A")
            M3GenericAvatar(style = Style.MONOGRAM, letter = "B")
            M3GenericAvatar(style = Style.PORTRAIT)
        }
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            listOf(
                M3Shape.CIRCLE, M3Shape.SQUARE, M3Shape.PILL, M3Shape.DIAMOND, M3Shape.HEXAGON,
                M3Shape.PENTAGON, M3Shape.TRIANGLE, M3Shape.ARCH, M3Shape.GEM, M3Shape.SLANTED,
            ).forEach { shape ->
                M3ShapeSet(shape = shape)
            }
        }


        SectionTitle("M3Keyboard / M3ExamplesLayoutGrid")
        M3Keyboard(configuration = Configuration.BASE_KEYBOARD, style = Style.PORTRAIT)
        M3Keyboard(configuration = Configuration.KEYPAD, style = Style.FLOATING)
        M3ExamplesLayoutGrid(
            windowSizeClass = WindowSizeClass.N3_EXPANDED_840MINUS_1199P,
            layoutRegions = LayoutRegions.NONE,
        )


        SectionTitle("RTL smoke · layout direction")
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = {}) { Text("RTL 按钮") }
                OutlinedTextField(
                    value = "مرحبا",
                    onValueChange = {},
                    label = { Text("RTL field") },
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = {}) { Icon(Icons.Filled.Add, contentDescription = "إضافة") }
            }
        }

        SectionTitle("State layer (8 / 10 / 10 / 16 / 38)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            M3InteractionState.entries
                .filter { it != M3InteractionState.ENABLED }
                .forEach { state ->
                    StateLayerSwatch(state)
                }
        }
        Text(
            text = "layer " + M3StateLayer.layerSize.value.toInt() + "dp · target " +
                M3StateLayer.targetSize.value.toInt() + "dp",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SectionTitle("Button sizes")
        HorizontalDivider()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            HeaderCell("size", 0.8f)
            HeaderCell("height", 1f)
            HeaderCell("padding", 1f)
            HeaderCell("icon", 0.9f)
            HeaderCell("radius", 1f)
            HeaderCell("pressed", 1f)
        }
        M3ButtonSize.entries.forEach { size ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RowCell(size.name, 0.8f, bold = true)
                RowCell(dpText(size.height), 1f)
                RowCell(dpText(size.horizontalPadding), 1f)
                RowCell(dpText(size.iconSize), 0.9f)
                RowCell(dpText(size.cornerRadius), 1f)
                RowCell(dpText(size.pressedCornerRadius), 1f)
            }
        }


        Spacer(Modifier.height(8.dp))
        Text(
            text = "Base: Material 3 Design Kit © Google, CC BY 4.0. " +
                "This is an unofficial Compose transcription; state layers follow current official values.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private fun dpText(value: androidx.compose.ui.unit.Dp): String =
    value.value.toInt().toString() + "dp"

@Composable
private fun SectionTitle(text: String) {
    Spacer(Modifier.height(20.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun StateLayerSwatch(state: M3InteractionState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(M3StateLayer.layerSize)
                .clip(CircleShape)
                .background(M3StateLayer.colorFor(MaterialTheme.colorScheme.onSurfaceVariant, state)),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = state.name.lowercase(),
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = (M3StateLayer.alphaFor(state) * 100).toInt().toString() + "%",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun RowScope.HeaderCell(text: String, weight: Float) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun RowScope.RowCell(text: String, weight: Float, bold: Boolean = false) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.bodySmall,
        fontWeight = if (bold) FontWeight.SemiBold else null,
    )
}

@Preview(showBackground = true, widthDp = 420, heightDp = 900)
@Composable
private fun MetricsBoardPreview() {
    M3KitTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MetricsBoard()
        }
    }
}