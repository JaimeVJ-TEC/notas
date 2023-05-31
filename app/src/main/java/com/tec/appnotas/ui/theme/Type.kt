package com.tec.appnotas.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tec.appnotas.R

private val Nunito = FontFamily(
    Font(R.font.nunito_font, FontWeight.W600) ,
    Font(R.font.nunito_italic, FontWeight.W600)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    h5 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp
    ),
    h6 = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    )






    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.s
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)