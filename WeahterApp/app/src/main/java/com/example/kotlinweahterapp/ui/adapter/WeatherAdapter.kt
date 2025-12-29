import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweahterapp.R
import com.example.kotlinweahterapp.data.model.WeatherModel

class WeatherAdapter(
    private val mContext: Context,
    private val weatherList: List<WeatherModel>
) : RecyclerView.Adapter<WeatherAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: TextView = view.findViewById(R.id.tvDay)
        val imgWeather: ImageView = view.findViewById(R.id.imgWeather)
        val tvMaxMin: TextView = view.findViewById(R.id.tvMaxMin)
        val tvHumidity: TextView = view.findViewById(R.id.tvHumidity)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.next_weahther_card, parent, false)
        return CardTasarimTutucu(view)
    }
    private fun getWeatherIcon(weather: WeatherModel): Int {

        val text = "${weather.status} ${weather.description}".lowercase()

        return when {
            text.contains("açık") -> R.drawable.sunny
            text.contains("güneş") -> R.drawable.sunny

            text.contains("parçalı") -> R.drawable.cloudy
            text.contains("az bulut") -> R.drawable.cloudy

            text.contains("bulut") -> R.drawable.cloudy

            text.contains("yağmur") -> R.drawable.rain
            text.contains("sağanak") -> R.drawable.rain

            text.contains("fırtına") -> R.drawable.rain
            text.contains("şimşek") -> R.drawable.rain

            text.contains("kar") -> R.drawable.snow

            else -> R.drawable.sunny
        }
    }


    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val weather = weatherList[position]

        holder.tvDay.text = weather.day
        holder.tvMaxMin.text = "${weather.max}° / ${weather.min}°"
        holder.tvHumidity.text = "%${weather.humidity}"

        holder.imgWeather.setImageResource(getWeatherIcon(weather))
    }


}
