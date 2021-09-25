import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Entry(

    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("key") val key: String? = null
) : Serializable