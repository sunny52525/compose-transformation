package com.example.composerotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.composerotation.ui.theme.Blue
import com.example.composerotation.ui.theme.ComposeRotationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRotationTheme {
                HomeScreen()
            }
        }
    }

}

data class Transformations(
    val xAxisRotation: Float = 0f,
    val yAxisRotation: Float = 0f,
    val zAxisRotation: Float = 0f,
    val yScale: Float = 1f,
    val xScale: Float = 1f,
    val xOffset: Float = 1f,
    val yOffset: Float = 1f
)


@Composable
@Preview
fun HomeScreen() {
    var selected: TYPE by remember {
        mutableStateOf(TYPE.BLUE)
    }
    var blueTransformation by remember {
        mutableStateOf(Transformations())
    }
    var blackTransformation by remember {
        mutableStateOf(Transformations())
    }
    val sliderValues by derivedStateOf {
        if (selected == TYPE.BLUE)
            blueTransformation
        else
            blackTransformation
    }

    val systemUiController = rememberSystemUiController()
    val statusBarColor =
        animateColorAsState(
            targetValue = if (selected == TYPE.BLUE) Blue else Color.Black,
            animationSpec = tween(1000)
        )
    systemUiController.setStatusBarColor(statusBarColor.value, false)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
            Rotation(transformation = blackTransformation, color = Color.Black, selectAndReset = {
                selected = TYPE.BLACK
                blackTransformation = Transformations()
            })
            Rotation(transformation = blueTransformation, selectAndReset = {
                selected = TYPE.BLUE
                blueTransformation = Transformations()
            })
        }
        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            RotationSlider(
                title = "X axis rotate",
                value = sliderValues.xAxisRotation,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(xAxisRotation = it)
                    } else {
                        blackTransformation = blackTransformation.copy(xAxisRotation = it)
                    }
                })
            RotationSlider(
                title = "Y axis rotate",
                value = sliderValues.yAxisRotation,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(yAxisRotation = it)
                    } else {
                        blackTransformation = blackTransformation.copy(yAxisRotation = it)
                    }
                })
            RotationSlider(
                title = "Z axis rotate",
                value = sliderValues.zAxisRotation,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(zAxisRotation = it)
                    } else {
                        blackTransformation = blackTransformation.copy(zAxisRotation = it)
                    }
                })
            RotationSlider(
                title = "X scale",
                value = sliderValues.xScale,
                range = 0f..2f,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(xScale = it)
                    } else {
                        blackTransformation = blackTransformation.copy(xScale = it)
                    }
                })
            RotationSlider(
                title = "Y scale",
                value = sliderValues.yScale,
                range = 0f..2f,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(yScale = it)
                    } else {
                        blackTransformation = blackTransformation.copy(yScale = it)
                    }

                })
            RotationSlider(
                title = "X offset",
                value = sliderValues.xOffset,
                range = -2000f..2000f,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(xOffset = it)
                    } else {
                        blackTransformation = blackTransformation.copy(xOffset = it)
                    }

                })
            RotationSlider(
                title = "Y offset",
                value = sliderValues.yOffset,
                range = -2000f..2000f,
                onChange = {
                    if (selected == TYPE.BLUE) {
                        blueTransformation = blueTransformation.copy(yOffset = it)
                    } else {
                        blackTransformation = blackTransformation.copy(yOffset = it)
                    }

                })

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Rotation(
    color: Color = Blue,
    transformation: Transformations,
    selectAndReset: () -> Unit
) {
    val xAxisRotationBlue = animateFloatAsState(
        targetValue = transformation.xAxisRotation,
        animationSpec = if (transformation.xAxisRotation == 0f) tween(500) else spring()
    )
    val yAxisRotationBlue =
        animateFloatAsState(
            targetValue = transformation.yAxisRotation,
            animationSpec = if (transformation.yAxisRotation == 0f) tween(500) else spring()
        )
    val zAxisRotationBlue =
        animateFloatAsState(
            targetValue = transformation.zAxisRotation,
            animationSpec = if (transformation.zAxisRotation == 0f) tween(500) else spring()
        )
    val xScaleBlue =
        animateFloatAsState(
            targetValue = transformation.xScale,
            animationSpec = if (transformation.xScale == 0f) tween(500) else spring()
        )
    val yScaleBlue =
        animateFloatAsState(
            targetValue = transformation.yScale,
            animationSpec = if (transformation.yScale == 0f) tween(500) else spring()
        )
    val xOffsetBlue =
        animateFloatAsState(
            targetValue = transformation.xOffset,
            animationSpec = if (transformation.xOffset == 0f) tween(500) else spring()
        )
    val yOffsetBlue =
        animateFloatAsState(
            targetValue = transformation.yOffset,
            animationSpec = if (transformation.yOffset == 0f) tween(500) else spring()
        )
    Card(
        backgroundColor = color,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(150.dp)
            .scale(
                xScaleBlue.value,
                yScaleBlue.value
            )
            .graphicsLayer {
                rotationX = xAxisRotationBlue.value
                rotationY = yAxisRotationBlue.value
                rotationZ = zAxisRotationBlue.value
            }
            .zIndex(10f)
            .offset(
                x = xOffsetBlue.value.dp,
                y = yOffsetBlue.value.dp
            ),
        onClick = selectAndReset

    ) {}
}

@Preview
@Composable
fun RotationSlider(
    title: String = "X Axis value",
    value: Float = 0.5f,
    range: ClosedFloatingPointRange<Float> = -300f..300f,
    onChange: (Float) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = title, color = Color.DarkGray)
            Text(
                text = "${roundTo(value, 2)}",
                modifier = Modifier.align(Alignment.CenterEnd),
                color = Color.DarkGray
            )
        }
        Slider(
            value = value, onValueChange = onChange, colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Blue,
                inactiveTrackColor = Color.Gray,

                ),
            valueRange = range
        )
    }
}


fun roundTo(
    num: Float,
    numFractionDigits: Int
) = "%.${numFractionDigits}f".format(num, Locale.ENGLISH).toDouble()

enum class TYPE {
    BLUE,
    BLACK
}
