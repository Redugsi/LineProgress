package com.redugsi.lineprogress

import android.content.Context
import android.graphics.*
import android.icu.util.Measure
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class LineProgress @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    var loadingBounds: RectF = RectF()
    var elapsedBounds: RectF = RectF()
    var textBounds: Rect = Rect()

    var loadingPaint: Paint = Paint()
    var elapsedPaint: Paint = Paint()

    var stroke: Float = 1F

    var text: String = "%0"
    var textPaint: TextPaint = TextPaint()

    var loadingColor: Int = Color.BLUE
    var elapsedColor: Int = Color.LTGRAY
    var textColor: Int = Color.BLUE
    var textFontSize: Float = 20F
    var textPadding: Float  = 8F


    init {
        readParams(attrs)
        setPaints()
    }

    fun readParams(attrs: AttributeSet?){
        context.obtainStyledAttributes(attrs, R.styleable.LineProgress).apply {
            try {
                loadingColor = getColor(R.styleable.LineProgress_loadingColor, Color.BLUE)
                elapsedColor = getColor(R.styleable.LineProgress_elapsedColor, Color.BLACK)
                textColor = getColor(R.styleable.LineProgress_textColor, Color.BLUE)
                textFontSize = getDimension(R.styleable.LineProgress_textSize, 60F)
                textPadding = getDimension(R.styleable.LineProgress_textPadding, 0F)
                stroke = getDimension(R.styleable.LineProgress_stroke, 10F)
            }finally {
                recycle()
            }
        }
    }

    fun setPaints(){
        loadingPaint.apply {
            color = loadingColor
            strokeWidth = stroke
        }

        elapsedPaint.apply {
            color = elapsedColor
            strokeWidth = stroke
        }

        textPaint.apply {
            color = textColor
            textSize = textFontSize
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var widthW = resolveSize(widthSize, widthMeasureSpec)

        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val textHeight = textBounds.height()
        val heightW = Math.max(textHeight, stroke.toInt())
        setMeasuredDimension(widthW, heightW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBounds(100F)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    fun drawLines(canvas: Canvas?){
        canvas?.drawRect(elapsedBounds, elapsedPaint)
        canvas?.drawRect(loadingBounds, loadingPaint)
    }

    fun drawText(canvas: Canvas?){
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        val textHeight = textBounds.height()
        val textLeft = Math.min(loadingBounds.right + textPadding, width - textPaint.measureText(text) - textPadding)
        canvas?.drawText(text, textLeft, loadingBounds.centerY() + (textHeight * 0.5F), textPaint)
    }

    fun setBounds(percent: Float){
        if (percent < 0 || percent > 100) return
        var progressDot = width * percent / 100

        val loadingRight = Math.min(progressDot, width - textPaint.measureText(text) - textPadding * 2)
        loadingBounds.set(0F, 0F, loadingRight, stroke)
        elapsedBounds.set(progressDot + textPaint.measureText(text) + textPadding * 2, 0F, width.toFloat(), stroke)
    }


    fun setProgressValue(percent: Float){
        setBounds(percent)
        invalidate()
    }


















}