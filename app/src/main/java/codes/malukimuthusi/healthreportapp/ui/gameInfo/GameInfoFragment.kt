package codes.malukimuthusi.healthreportapp.ui.gameInfo

import android.os.Bundle
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

class GameInfoFragment : Fragment() {

    private val viewModel: GameInfoViewModel by viewModels()
    private lateinit var binding: FragmentGameInfoBinding

    // create an array of the images
    lateinit var imageList: MutableList<ImageView>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameInfoBinding.inflate(inflater, container, false)


        binding.apply {
            imageList = mutableListOf(
                imageView1r1, imageView2r1, imageView3r1,
                imageView1r2, imageView2r2, imageView3r2,
                imageView1r3, imageView2r3, imageView3r3
            )
        }

        for ((index, image) in imageList.withIndex()) {

            // [start] onclickListener
            image.setOnClickListener {

                gameLogic(index, image)

            }
            // [end] onclickListener

        }

        return binding.root
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
                    showSnackBar()

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

    private fun showSnackBar() {
        Snackbar.make(binding.imageView1r1, "Matched!!", Snackbar.LENGTH_LONG)
            .show()
    }
}
