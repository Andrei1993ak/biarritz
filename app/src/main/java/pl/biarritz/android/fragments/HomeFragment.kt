package pl.biarritz.android.fragments

import android.content.res.AssetManager
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
import java.util.*
import kotlin.concurrent.thread


class HomeFragment : Fragment() {

    private var images: ArrayList<Bitmap>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.assets?.let {
            homeImageSlider?.sliderAdapter = SliderAdapter(it)

            try {
                val inputStream = it.open("images/home_image.jpeg")
                homeImageView.setImageBitmap(BitmapFactory.decodeStream(inputStream))
                inputStream.close()
            } catch (e: Exception) {
                Log.d("test", e.message, e)
            }

        }
    }

    inner class SliderAdapter(private val assetManager: AssetManager) :
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
            thread {
                val bitmap = BitmapFactory.decodeStream(assetManager.open("images/$position.jpeg"))
                activity?.runOnUiThread {
                    viewHolder.itemView.setImageBitmap(bitmap)
                }
            }
        }

        override fun getCount() = 7

        inner class SliderAdapterVH(val itemView: ImageView) : SliderViewAdapter.ViewHolder(itemView)
    }
}