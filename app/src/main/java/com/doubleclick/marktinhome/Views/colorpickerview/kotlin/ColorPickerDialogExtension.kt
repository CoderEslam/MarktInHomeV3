package com.skydoves.colorpickerview.kotlin

import android.content.Context
import com.doubleclick.marktinhome.Views.colorpickerview.ColorPickerDialog

@DslMarker
internal annotation class ColorPickerDsl

/**
 * Creates a lambda scope for implementing [ColorPickerDialog] using its [ColorPickerDialog.Builder].
 *
 * @param block lambda scope for receiving [ColorPickerDialog.Builder].
 * @return new instance of [ColorPickerDialog].
 */
@JvmSynthetic
@ColorPickerDsl
inline fun Context.colorPickerDialog(block: ColorPickerDialog.Builder.() -> Unit) =
  ColorPickerDialog.Builder(this).apply(block)
