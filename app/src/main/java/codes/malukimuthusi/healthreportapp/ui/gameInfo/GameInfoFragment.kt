package codes.malukimuthusi.healthreportapp.ui.gameInfo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.dataModels.GameImage
import codes.malukimuthusi.healthreportapp.databinding.FragmentGameInfoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.view.*

class GameInfoFragment : Fragment() {

    private val viewModel: GameInfoViewModel by viewModels()
    private lateinit var binding: FragmentGameInfoBinding

    // create an array of the images
//    lateinit var imageList: MutableList<ImageView>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingView = inflater.inflate(R.layout.fragment_game_info, container, false)
        return bindingView.root
    }

    private fun gameLogic(index: Int, image: ImageView) {

        // check for double clicking
        if (!viewModel.imageStateList[index].imageRevealed) {

            viewModel.imageStateList[index].resource = viewModel.resourcesList[index]

            // reveal the image clicked.
            revealImage(image, index)
            viewModel.imageStateList[index].imageRevealed = true

            // check if match is being waited.
            if (viewModel.currentClickedImage.imageIndex != -1) {

                // check for match
                if (viewModel.currentClickedImage.resource == viewModel.imageStateList[index].resource) {
                    showSnackBar(binding.root)

                    // change tint of matched images
                    image.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    // place this variable here to make an error disappear
                    val imageList = mutableListOf<ImageView>()
                    imageList[viewModel.currentClickedImage.imageIndex].setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    viewModel.currentClickedImage = GameImage(resource = 0, imageIndex = -1)

                    // no match found
                } else {
// place this variable here to make an error disappear
                    val imageList = mutableListOf<ImageView>()
                    closeImage(imageList[viewModel.currentClickedImage.imageIndex])
                    viewModel.imageStateList[viewModel.currentClickedImage.imageIndex].imageRevealed =
                        false
                    closeImage(image)
                    viewModel.imageStateList[index].imageRevealed = false
                    viewModel.currentClickedImage = GameImage(resource = 0, imageIndex = -1)

                }

                // no match was waited. It is a fresh new click
            } else {
                viewModel.currentClickedImage = viewModel.imageStateList[index]
                viewModel.currentClickedImage.imageIndex = index
            }

        }

    }

    private fun closeImage(image: ImageView) {
        image.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_texture_black_24dp,
                null
            )
        )
    }

    private fun revealImage(image: ImageView, index: Int) {
        image.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                viewModel.imageStateList[index].resource,
                null
            )
        )
    }

    private fun showSnackBar(xx: View) {
        Snackbar.make(xx, "Matched!!", Snackbar.LENGTH_LONG)
            .show()
    }

    inner class GameView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : View(context, attrs, defStyleAttr, defStyleRes) {
        private val imagesToDraw = mutableListOf<Drawable?>()
        private var imageWidth: Int = 0
        private var imageHeight = 0
        private lateinit var levelOnePaint: Paint
        private val level = 1


        init {
            initializeLevelOne()

//            when (viewModel.glv) {
//                1 -> {
//                    initializeLevelOne()
//                }
//            }

        }

        // set up measurements of my objects
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)

            when (level) {
                1 -> {
                    levelOneOnSizeChanged(w, h, oldw, oldh)
                }
            }
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)

            when (viewModel.glv) {
                1 -> {
                    levelOneOnMeasure(widthMeasureSpec, heightMeasureSpec)
                }

            }

            // specify how much size I need.
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            when (viewModel.glv) {
                1 -> {
                    levelOneOnDraw(canvas)
                }
            }
        }

        private fun initializeLevelOne() {
            val shuffledImages = imageList.shuffled()
            for (index in 0..levelOneSize) {
                imagesToDraw.add(index, ContextCompat.getDrawable(context, shuffledImages[index]))
            }

            levelOnePaint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.STROKE
                color = ContextCompat.getColor(context, R.color.colorPrimary)
            }
        }

        private fun levelOneOnSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            val availableWidth = w - paddingLeft - paddingRight
            val availableHeight = h - paddingTop - paddingBottom

            imageWidth = availableWidth / 2
            imageHeight = imageWidth

            var rectBound: Rect

            for ((index, drawable) in imagesToDraw.withIndex()) {
                rectBound = Rect(
                    paddingLeft,
                    paddingTop,
                    paddingLeft + imageWidth,
                    paddingTop + imageHeight
                )
                when {

                    ((index % 2) == 0) -> {
                        rectBound.offset(0, imageHeight * (index / 2))
                    }

                    else -> {
                        rectBound.offset(imageWidth, imageHeight * (index / 2))
                    }
                }

                drawable?.bounds = rectBound
            }

        }

        private fun levelOneOnMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            val desiredWidth: Int
            val desiredHeight: Int

            val specWidth = MeasureSpec.getSize(widthMeasureSpec)
            val specHeight = MeasureSpec.getSize(heightMeasureSpec)

            val contentWidth = specWidth - paddingLeft - paddingRight
            val contentHeight = specHeight - paddingTop - paddingBottom

            desiredWidth = specWidth
            desiredHeight = specHeight

            val width = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0)
            val height = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0)

            setMeasuredDimension(width, height)
        }


        private fun levelOneOnDraw(canvas: Canvas?) {

            for ((index, drawable) in imagesToDraw.withIndex()) {

                if (canvas != null) {
                    drawable?.draw(canvas)
                }

                val rect = drawable?.bounds
                rect?.let {
                    canvas?.drawRect(rect, levelOnePaint)
                }
            }
        }

    }

    companion object {

        val imageList = setOf(
            R.drawable.ic_noun_cleaning_3311960, R.drawable.ic_noun_cough_630783,
            R.drawable.ic_noun_crowded_place_3307204, R.drawable.ic_noun_flight_delay_3365080,
            R.drawable.ic_noun_hand_wash_3497, R.drawable.ic_noun_soclai_distance_3365071,
            R.drawable.ic_noun_virus_3364091, R.drawable.ic_noun_avoid_hand_shake_3365076
        )

        const val levelOneSize = 4
        const val levelTwoSize = 8
        const val imageSize = 200

    }
}
