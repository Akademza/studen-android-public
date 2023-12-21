package ai.pifagor.android.data.models

import kotlinx.serialization.Serializable

@Serializable
data class HttpError(
    val message: String?
)
