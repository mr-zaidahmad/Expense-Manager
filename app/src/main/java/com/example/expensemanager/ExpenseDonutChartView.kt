package com.example.expensemanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class ExpenseDonutChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var values = emptyList<Double>()

    private val colors = listOf(
        0xFF08A6A6.toInt(),
        0xFF91460F.toInt(),
        0xFFFFB000.toInt(),
        0xFF8E4DE8.toInt(),
        0xFF778899.toInt(),
        0xFF4CAF50.toInt(),
        0xFFE91E63.toInt(),
        0xFF607D8B.toInt()
    )


    fun setValues(
        newValues: List<Double>
    ) {
        values = newValues
        invalidate()
    }


    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        if (values.isEmpty()) {
            return
        }

        val total = values.sum()

        if (total <= 0) {
            return
        }


        val size = min(
            width,
            height
        )


        val strokeWidth =
            size * 0.18f


        paint.style =
            Paint.Style.STROKE

        paint.strokeWidth =
            strokeWidth

        paint.strokeCap =
            Paint.Cap.BUTT


        val padding =
            strokeWidth / 2f + 5f


        val rect =
            RectF(
                padding,
                padding,
                width - padding,
                height - padding
            )


        var startAngle = -90f


        values.forEachIndexed { index, value ->

            val sweepAngle =
                (value / total * 360f).toFloat()


            paint.color =
                colors[index % colors.size]


            canvas.drawArc(
                rect,
                startAngle,
                sweepAngle,
                false,
                paint
            )


            startAngle += sweepAngle
        }
    }
}