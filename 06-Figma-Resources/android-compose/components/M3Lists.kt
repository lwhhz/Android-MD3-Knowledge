package com.example.m3appbar.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.m3appbar.M3Typography

/** Lists 页面组件。参数来自 Figma componentPropertyDefinitions（真实默认值/选项）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3ListItemListItem4DensityBaseline(
    modifier: Modifier = Modifier,
    vCondition: Condition = Condition.N3_LINEPLUS,
    vLeading: Leading = Leading.NONE,
    vTrailing: Trailing = Trailing.NONE,
    vShowoverline: ShowOverline = ShowOverline.FALSE,
    vShowsupportingtext: ShowSupportingText = ShowSupportingText.TRUE,
    bShowtrailingsupportingtext: Boolean = false,
    bShowdivider: Boolean = false,
    bShowleadingavatar: Boolean = true,
    bShowleadingicon: Boolean = true,
    bShowimage: Boolean = true,
    bShowvideothumbnail: Boolean = true,
    bShowcheckbox: Boolean = true,
    bShowradiobutton: Boolean = true,
    bShowswitch: Boolean = true,
    tOverline: String = "Overline",
    tHeadline: String = "List item",
    tTrailingsupportingtext: String = "100+",
    tSupportingtext: String = "Supporting line text lorem ipsum dolor sit amet, consectetur.",
    iStatelayer: Painter? = null,
    iSwapdividertype: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3ListItemListItem2DensityBaseline(
    modifier: Modifier = Modifier,
    vCondition: Condition = Condition.N3_LINEPLUS,
    vLeading: Leading = Leading.NONE,
    vTrailing: Trailing = Trailing.NONE,
    vShowoverline: ShowOverline = ShowOverline.FALSE,
    vShowsupportingtext: ShowSupportingText = ShowSupportingText.TRUE,
    bShowtrailingsupportingtext: Boolean = false,
    bShowdivider: Boolean = false,
    bShowleadingavatar: Boolean = true,
    bShowleadingicon: Boolean = true,
    bShowimage: Boolean = true,
    bShowvideothumbnail: Boolean = true,
    bShowcheckbox: Boolean = true,
    bShowradiobutton: Boolean = true,
    bShowswitch: Boolean = true,
    tHeadline: String = "List item",
    tOverline: String = "Overline",
    tTrailingsupportingtext: String = "100+",
    tSupportingtext: String = "Supporting line text lorem ipsum dolor sit amet, consectetur.",
    iStatelayer: Painter? = null,
    iSwapdividertype: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3ListItemListItem0DensityBaseline(
    modifier: Modifier = Modifier,
    vCondition: Condition = Condition.N3_LINEPLUS,
    vLeading: Leading = Leading.NONE,
    vTrailing: Trailing = Trailing.NONE,
    vShowoverline: ShowOverline = ShowOverline.FALSE,
    vShowsupportingtext: ShowSupportingText = ShowSupportingText.TRUE,
    bShowtrailingsupportingtext: Boolean = false,
    bShowdivider: Boolean = false,
    bShowleadingavatar: Boolean = true,
    bShowleadingicon: Boolean = true,
    bShowimage: Boolean = true,
    bShowvideothumbnail: Boolean = true,
    bShowcheckbox: Boolean = true,
    bShowradiobutton: Boolean = true,
    bShowswitch: Boolean = true,
    tHeadline: String = "List item",
    tOverline: String = "Overline",
    tTrailingsupportingtext: String = "100+",
    tSupportingtext: String = "Supporting line text lorem ipsum dolor sit amet, consectetur.",
    iStatelayer: Painter? = null,
    iSwapdividertype: Painter? = null,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3List(
    modifier: Modifier = Modifier,
    vType: Type = Type.STANDARD,
    vMultiline: MultiLine = MultiLine.FALSE,
    bShowdivider: Boolean = false,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3ListItem(
    modifier: Modifier = Modifier,
    vAlignment: M3Alignment = M3Alignment.MIDDLE_ALIGNED,
    vState: State = State.ENABLED,
    vSelected: Selected = Selected.FALSE,
    bShowtrailingelement: Boolean = true,
    bShowleadingelement: Boolean = true,
    bShowfocusindicator: Boolean = false,
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3ListItemAccordion(
    modifier: Modifier = Modifier,
    vAccordion: Accordion = Accordion.COLLAPSED,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3ListItemSwipe(
    modifier: Modifier = Modifier,
    vSwipestate: SwipeState = SwipeState.DEFAULT,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3BuildingBlocksLeadingElement(
    modifier: Modifier = Modifier,
    vType: Type = Type.ICON,
    bSelected: Boolean = true,
    iIcon: Painter? = null,
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3BuildingBlocksTrailingElement(
    modifier: Modifier = Modifier,
    vType: Type = Type.ICON,
    bShowtrailingtext: Boolean = true,
    tTrailingtext: String = "⌘C",
    iIcon: Painter? = null,
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3BuildingBlocksContent(
    modifier: Modifier = Modifier,
    vType: Type = Type.TEXT,
    bShowoverlinetext: Boolean = true,
    bShowsupportingtext: Boolean = true,
    tOverlinetext: String = "Overline",
    tLabeltext: String = "Label text",
    tSupportingtext: String = "Supporting line text, lorem ipsum dolor",
    sContent: @Composable () -> Unit = {},
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3BuildingBlocksRevealElement(
    modifier: Modifier = Modifier,
    vActions: Actions = Actions.N1,
    vType: Type = Type.INITIATE_REVEAL,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}

@Composable
fun M3BuildingBlocksListItemAccordionButtton(
    modifier: Modifier = Modifier,
    vType: Type = Type.EXPAND,
    vState: State = State.ENABLED,
    tLabel: String = "Label",
    onClick: () -> Unit = {}
) {
    // 布局骨架：完整规格见 COMPONENTS_SPEC.md
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) { Text(tLabel) }
}
