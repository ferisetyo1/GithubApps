package feri.com.githubapps.data.localDb

import android.content.ContentValues
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import feri.com.githubapps.model.User
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

    fun toUser(): User {
        return User(
            null,
            null,
            null,
            null,
            this.login,
            null,
            null,
            null,
            null,
            null,
            this.avatarUrl,
            null,
            null,
            null,
            null,
            null, null, null
        )
    }

    companion object {
        fun fromContentValue(contentValues: ContentValues): Favorit {
            return Favorit(
                contentValues.getAsString("login"),
                contentValues.getAsString("avatarUrl")
            )
        }
    }
}