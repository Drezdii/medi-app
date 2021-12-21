package com.bartoszdrozd.mediapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.bartoszdrozd.mediapp.R

@SuppressLint("ResourceType")
class GradientArcView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val stroke = 40f
    var text: CharSequence = ""
    var arcValue: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val bounds = RectF()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = stroke
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.neutral_grey)
        strokeCap = Paint.Cap.ROUND
    }

    private val colors =
        intArrayOf(
            ContextCompat.getColor(context, R.color.prediction_serious),
            ContextCompat.getColor(context, R.color.prediction_warning),
            ContextCompat.getColor(context, R.color.prediction_good)
        )
    private val positions = floatArrayOf(0f, 0.4f, 1f)

    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = stroke
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 16f * resources.displayMetrics.density
        isAntiAlias = true
    }

    init {
        val attributes = intArrayOf(android.R.attr.text, R.attr.arcValue)
        val result = context.obtainStyledAttributes(attrs, attributes)

        text = result.getText(0) ?: ""
        arcValue = result.getFloat(1, 0f)

        result.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = 0
        var height = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                width = widthSize
            }
            MeasureSpec.AT_MOST -> {
                width = widthSize
            }
            MeasureSpec.UNSPECIFIED -> {
                width = widthSize
            }
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> {
                height = heightSize
            }
            MeasureSpec.AT_MOST,
            MeasureSpec.UNSPECIFIED -> {
                height = widthSize.coerceAtMost(heightSize) / 2
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val left = 0f + paddingLeft + (stroke / 2)
        val top = 0f + paddingTop + (stroke / 2)
        val right = w.toFloat() - paddingRight - (stroke / 2)
        val bottom = (h.toFloat() - paddingBottom) * 2

        paint2.shader = SweepGradient(w / 2f, h / 2f, colors, positions).apply {
            val gradientMatrix = Matrix()
            gradientMatrix.preRotate(90f, w / 2f, h / 2f)
            setLocalMatrix(gradientMatrix)
        }
        bounds.set(left, top, right, bottom)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val textWidth = textPaint.measureText(text as String?).toInt()

        val textLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, textWidth)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1f)
            .setIncludePad(false)

        val layout = textLayout.build()

        canvas.apply {
            drawArc(bounds, 180f, 180f, false, paint)
            drawArc(bounds, 180f, arcValue * 180f, false, paint2)
            canvas.save()
            canvas.translate(
                (measuredWidth / 2f) - textWidth / 2f + paddingLeft - paddingRight,
                (measuredHeight / 2f) + layout.height
            )
            layout.draw(canvas)
            canvas.restore()
        }
    }
}