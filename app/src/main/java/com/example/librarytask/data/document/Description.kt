import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Description (
	@Expose @SerializedName("type") val type : String?=null,
	@Expose @SerializedName("value") val value : String?=null
):Serializable