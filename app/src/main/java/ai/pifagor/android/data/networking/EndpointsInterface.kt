package ai.pifagor.android.data.networking

import ai.pifagor.android.data.models.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndpointsInterface {

    @FormUrlEncoded
    @POST("/api/user")
    suspend fun auth(
        @Field("key") key: String,
        @Field("uuid") uuid: String,
        @Field("include_player_id") include_player_id: String,
        @Field("url") url: String,
        @Field("modal") modal: String,
        @Field("time") time: String
    ): Response<User>


}