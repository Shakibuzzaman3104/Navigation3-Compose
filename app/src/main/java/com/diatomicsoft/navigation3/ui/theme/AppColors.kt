package com.diatomicsoft.navigation3.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Enhanced color palette with better contrast and accessibility
object AppColors {
    // Primary colors
    val Primary50 = Color(0xFFF3E5F5)
    val Primary100 = Color(0xFFE1BEE7)
    val Primary200 = Color(0xFFCE93D8)
    val Primary300 = Color(0xFFBA68C8)
    val Primary400 = Color(0xFFAB47BC)
    val Primary500 = Color(0xFF9C27B0)
    val Primary600 = Color(0xFF8E24AA)
    val Primary700 = Color(0xFF7B1FA2)
    val Primary800 = Color(0xFF6A1B9A)
    val Primary900 = Color(0xFF4A148C)

    // Secondary colors
    val Secondary50 = Color(0xFFF3E5F5)
    val Secondary100 = Color(0xFFE1BEE7)
    val Secondary200 = Color(0xFFCE93D8)
    val Secondary300 = Color(0xFFBA68C8)
    val Secondary400 = Color(0xFFAB47BC)
    val Secondary500 = Color(0xFF9C27B0)
    val Secondary600 = Color(0xFF8E24AA)
    val Secondary700 = Color(0xFF7B1FA2)
    val Secondary800 = Color(0xFF6A1B9A)
    val Secondary900 = Color(0xFF4A148C)

    // Tertiary colors
    val Tertiary50 = Color(0xFFFCE4EC)
    val Tertiary100 = Color(0xFFF8BBD9)
    val Tertiary200 = Color(0xFFF48FB1)
    val Tertiary300 = Color(0xFFF06292)
    val Tertiary400 = Color(0xFFEC407A)
    val Tertiary500 = Color(0xFFE91E63)
    val Tertiary600 = Color(0xFFD81B60)
    val Tertiary700 = Color(0xFFC2185B)
    val Tertiary800 = Color(0xFFAD1457)
    val Tertiary900 = Color(0xFF880E4F)

    // Error colors
    val Error50 = Color(0xFFFFEBEE)
    val Error100 = Color(0xFFFFCDD2)
    val Error200 = Color(0xFFEF9A9A)
    val Error300 = Color(0xFFE57373)
    val Error400 = Color(0xFFEF5350)
    val Error500 = Color(0xFFF44336)
    val Error600 = Color(0xFFE53935)
    val Error700 = Color(0xFFD32F2F)
    val Error800 = Color(0xFFC62828)
    val Error900 = Color(0xFFB71C1C)

    // Neutral colors
    val Neutral0 = Color(0xFFFFFFFF)
    val Neutral10 = Color(0xFFFAFAFA)
    val Neutral20 = Color(0xFFF5F5F5)
    val Neutral30 = Color(0xFFEEEEEE)
    val Neutral40 = Color(0xFFE0E0E0)
    val Neutral50 = Color(0xFFBDBDBD)
    val Neutral60 = Color(0xFF9E9E9E)
    val Neutral70 = Color(0xFF757575)
    val Neutral80 = Color(0xFF616161)
    val Neutral90 = Color(0xFF424242)
    val Neutral95 = Color(0xFF212121)
    val Neutral100 = Color(0xFF000000)
}

val EnhancedLightColorScheme = lightColorScheme(
    primary = AppColors.Primary500,
    onPrimary = AppColors.Neutral0,
    primaryContainer = AppColors.Primary100,
    onPrimaryContainer = AppColors.Primary900,
    
    secondary = AppColors.Secondary500,
    onSecondary = AppColors.Neutral0,
    secondaryContainer = AppColors.Secondary100,
    onSecondaryContainer = AppColors.Secondary900,
    
    tertiary = AppColors.Tertiary500,
    onTertiary = AppColors.Neutral0,
    tertiaryContainer = AppColors.Tertiary100,
    onTertiaryContainer = AppColors.Tertiary900,
    
    error = AppColors.Error500,
    onError = AppColors.Neutral0,
    errorContainer = AppColors.Error100,
    onErrorContainer = AppColors.Error900,
    
    background = AppColors.Neutral10,
    onBackground = AppColors.Neutral95,
    surface = AppColors.Neutral0,
    onSurface = AppColors.Neutral95,
    surfaceVariant = AppColors.Neutral20,
    onSurfaceVariant = AppColors.Neutral70,
    
    outline = AppColors.Neutral50,
    outlineVariant = AppColors.Neutral30,
    scrim = AppColors.Neutral100,
    inverseSurface = AppColors.Neutral95,
    inverseOnSurface = AppColors.Neutral10,
    inversePrimary = AppColors.Primary200
)

val EnhancedDarkColorScheme = darkColorScheme(
    primary = AppColors.Primary200,
    onPrimary = AppColors.Primary900,
    primaryContainer = AppColors.Primary700,
    onPrimaryContainer = AppColors.Primary100,
    
    secondary = AppColors.Secondary200,
    onSecondary = AppColors.Secondary900,
    secondaryContainer = AppColors.Secondary700,
    onSecondaryContainer = AppColors.Secondary100,
    
    tertiary = AppColors.Tertiary200,
    onTertiary = AppColors.Tertiary900,
    tertiaryContainer = AppColors.Tertiary700,
    onTertiaryContainer = AppColors.Tertiary100,
    
    error = AppColors.Error200,
    onError = AppColors.Error900,
    errorContainer = AppColors.Error700,
    onErrorContainer = AppColors.Error100,
    
    background = AppColors.Neutral100,
    onBackground = AppColors.Neutral10,
    surface = AppColors.Neutral95,
    onSurface = AppColors.Neutral10,
    surfaceVariant = AppColors.Neutral90,
    onSurfaceVariant = AppColors.Neutral30,
    
    outline = AppColors.Neutral60,
    outlineVariant = AppColors.Neutral80,
    scrim = AppColors.Neutral100,
    inverseSurface = AppColors.Neutral10,
    inverseOnSurface = AppColors.Neutral95,
    inversePrimary = AppColors.Primary500
)
