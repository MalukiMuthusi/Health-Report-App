package codes.malukimuthusi.healthreportapp

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.compound_view.view.*


/**
 * Compound view that shows single statistic with icon and title
 * Used in Global and Local Summary screens
 */
class SingleStatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.compound_view, this, true)
        orientation = VERTICAL

        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CompundView, 0, 0
        ).apply {
            try {
                stat_title.text =
                    getText(R.styleable.CompundView_title) ?: context.getString(R.string.title)
                stat_value.text =
                    getText(R.styleable.CompundView_statValue) ?: context.getString(
                        R.string.value
                    )
                stat_icon.setImageDrawable(getDrawable(R.styleable.CompundView_icon))
                val iconColor = getColor(
                    R.styleable.CompundView_iconColor,
                    ContextCompat.getColor(context, R.color.colorPrimary)
                )
                stat_icon.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
            } finally {
                recycle()
            }
        }
    }
}