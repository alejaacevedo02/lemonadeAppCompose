package com.example.lemonadeappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadeappcompose.ui.theme.LemonadeAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}


@Composable
fun TapImageWithText() {

    var currentStep by remember {
        mutableStateOf(1)
    }


    var lemonSize by remember { mutableStateOf(0) }

    when (currentStep) {
        1 -> LemonTextWithImage(lemonState = LemonState.SELECT, onClick = {
            currentStep = 2
            lemonSize = (2..4).random()
        })
        2 -> LemonTextWithImage(lemonState = LemonState.SQUEEZE, onClick = {
            lemonSize--
            if (lemonSize == 0) {
                currentStep = 3
            }
        })
        3 -> LemonTextWithImage(lemonState = LemonState.DRINK, onClick = { currentStep = 4 })
        else -> LemonTextWithImage(lemonState = LemonState.RESTART, onClick = { currentStep = 1 })
    }

}


@Composable
fun LemonTextWithImage(
    lemonState: LemonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentSize()
    ) {

        Text(
            stringResource(id = lemonState.textId),
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = onClick,
            border = BorderStroke(1.dp, Color(105, 205, 216)),
            colors = ButtonDefaults.outlinedButtonColors()
        ) {

            Image(
                painterResource(id = lemonState.imageId),
                contentDescription = stringResource(
                    id = lemonState.contentDescriptionId
                )
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LemonadeApp() {
    LemonadeAppComposeTheme {
        TapImageWithText()
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}

enum class LemonState(
    @DrawableRes val imageId: Int,
    @StringRes val textId: Int,
    @StringRes val contentDescriptionId: Int
) {
    SELECT(R.drawable.lemon_tree, R.string.lemonade_tree, R.string.lemon_tree_content_description),
    SQUEEZE(R.drawable.lemon_squeeze, R.string.lemon_squeeze, R.string.lemon_content_description),
    DRINK(
        R.drawable.lemon_drink,
        R.string.lemon_drink,
        R.string.Glass_of_lemonade_content_description
    ),
    RESTART(
        R.drawable.lemon_restart,
        R.string.lemon_try_again,
        R.string.empty_glass_content_description
    )
}