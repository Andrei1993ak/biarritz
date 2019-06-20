package pl.biarritz.android.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import pl.biarritz.android.R
import kotlin.concurrent.thread


class HomeFragment : Fragment() {

    private val images: ArrayList<Bitmap> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (images.isEmpty()) {
            loadImages()
        } else {
            onHomeImageLoaded(images[0])
            onImagesLoaded(images.subList(1, images.size))
        }
    }

    private fun loadImages() {
        thread {
            try {
                activity?.assets?.let {
                    for (x in 0..7) {

                        val inputStream = it.open("images/$x.jpeg")

                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        inputStream.close()
                        images.add(bitmap)

                        if (x == 0) {
                            onHomeImageLoaded(bitmap)
                        }
                    }

                    onImagesLoaded(images.subList(1, images.size))
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", e.message, e)
            }
        }
    }

    private fun onHomeImageLoaded(bitmap: Bitmap) {
        activity?.runOnUiThread {
            homeImageView?.setImageBitmap(bitmap)
        }
    }

    private fun onImagesLoaded(images: MutableList<Bitmap>) {
        activity?.runOnUiThread {
            homeImageSlider?.run {
                this.sliderAdapter = SliderAdapter(images)
                this.startAutoCycle()
            }
        }
    }

    inner class SliderAdapter(private val images: MutableList<Bitmap>) :
        SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

        override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
            return SliderAdapterVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.image_slider_layout_item,
                    null
                ) as ImageView
            )
        }

        override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
            viewHolder.itemView.setImageBitmap(images[position])
        }

        override fun getCount() = images.size

        inner class SliderAdapterVH(val itemView: ImageView) : SliderViewAdapter.ViewHolder(itemView)
    }
}