package com.example.composerotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRotationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
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
                .clip(RoundedCornerShape(10.dp))
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
                range = -200f..200f,
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
                range = -200f..200f,
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
    val xAxisRotationBlue = animateFloatAsState(targetValue = transformation.xAxisRotation)
    val yAxisRotationBlue = animateFloatAsState(targetValue = transformation.yAxisRotation)
    val zAxisRotationBlue = animateFloatAsState(targetValue = transformation.zAxisRotation)
    val xScaleBlue = animateFloatAsState(targetValue = transformation.xScale)
    val yScaleBlue = animateFloatAsState(targetValue = transformation.yScale)
    val xOffsetBlue = animateFloatAsState(targetValue = transformation.xOffset)
    val yOffsetBlue = animateFloatAsState(targetValue = transformation.yOffset)
    Card(
        backgroundColor = color,
        shape = RoundedCornerShape(10.dp),
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
