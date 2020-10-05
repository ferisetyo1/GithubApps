package feri.com.githubapps.data.localDb

import android.content.ContentValues
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorit")
@Parcelize
class Favorit(
    @PrimaryKey
    @NonNull
    val login: String,
    @NonNull
    val avatarUrl: String
) : Parcelable {
    companion object {
        fun fromContentValue(contentValues: ContentValues): Favorit {
            return Favorit(
                contentValues.getAsString("login"),
                contentValues.getAsString("avatarUrl")
            )
        }
    }
}