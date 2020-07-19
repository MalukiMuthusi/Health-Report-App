package codes.malukimuthusi.healthreportapp.ui.gameInfo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import codes.malukimuthusi.healthreportapp.R
import codes.malukimuthusi.healthreportapp.dataModels.GameImage
import codes.malukimuthusi.healthreportapp.databinding.FragmentGameInfoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameInfoFragment : Fragment() {

    private val viewModel: GameInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentGameInfoBinding
    private var matchedCounter = 0
    private var level = 0
    private var points = 0

    private lateinit var imageList: List<ImageView>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        gameSetUp()
        imageClickActions()

        checkCounter()
        return binding.root
    }

    private fun gameSetUp() {
        binding.apply {
            imageList = listOf(
                imageView1,
                imageView2,
                imageView3,
                imageView4,
                imageView5,
                imageView6,
                imageView7,
                imageView8,
                imageView9,
                imageView10,
                imageView11,
                imageView12
            )
        }

        // get saved points
        points = sharedPreferences.getInt(getString(R.string.point_scores), points)
        level = sharedPreferences.getInt(getString(R.string.level_count), level)

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

    private fun imageClickActions() {
        for ((index, image) in imageList.withIndex()) {

            // [start] onclickListener
            image.setOnClickListener {

                gameLogic(index, image)

            }
            // [end] onclickListener
        }


    }

    private fun trackMatchedImages() {

        ++matchedCounter

        if (matchedCounter == imageList.size) {
            // TODO: change Level

            // TODO: reset matched
        }
    }

    private fun rewardPoints() {

        points += 10
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.point_scores), points)
            apply()
        }

    }

    private fun changeLevel() {
        level += 1
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.level_count), level)
            apply()
        }
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

                    // match found
                    showSnackBar()
                    trackMatchedImages()

                    // change tint of matched images
                    image.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    imageList[viewModel.currentClickedImage.imageIndex].setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorPrimary
                        ), android.graphics.PorterDuff.Mode.SRC_IN
                    )

                    viewModel.currentClickedImage = GameImage(resource = 0, imageIndex = -1)

                    // no match found
                } else {

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

    private fun checkCounter() {
        lifecycleScope.launch {
            repeat(10) {
                binding.counter.text = getString(R.string.counter, it)
                delay(1000)
            }
            Snackbar.make(binding.card, "Game Over", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showSnackBar() {
        Snackbar.make(binding.card, "CARD TOAST", Snackbar.LENGTH_LONG)
    }
}
