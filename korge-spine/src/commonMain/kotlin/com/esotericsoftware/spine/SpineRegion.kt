package com.esotericsoftware.spine

import com.soywiz.korim.atlas.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.ImageOrientation
import com.soywiz.korma.geom.slice.*

class SpineRegion(val entry: Atlas.Entry) {
    val bmpSlice: BmpSlice = entry.slice
    val bmp: Bitmap = bmpSlice.base
    val coords: RectCoords = bmpSlice.coords
    val texture: Bitmap = bmp
    var rotate: Boolean = entry.info.imageOrientation != ImageOrientation.ORIGINAL
    val u: Float = coords.tlX
    val u2: Float = coords.brX
    val v: Float = if (rotate) bmpSlice.brY else coords.tlY
    val v2: Float = if (rotate) bmpSlice.tlY else coords.brY
    var offsetX: Float = (entry.info.virtFrame?.x ?: 0).toFloat()
    var offsetY: Float = (entry.info.virtFrame?.y ?: 0).toFloat()
    var originalWidth: Float = (entry.info.virtFrame?.w ?: entry.info.frame.w).toFloat()
    var originalHeight: Float = (entry.info.virtFrame?.h ?: entry.info.frame.h).toFloat()
    var packedWidth: Float = entry.info.frame.w.toFloat()
    var packedHeight: Float = entry.info.frame.h.toFloat()
    var degrees: Int = if (rotate) 90 else 0
}
