package ai.pifagor.android.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uuid: String,
    val include_player_id: String,
    val name: String,
    val time_created: String,
    val notice_act: Boolean,
    val points: Int,
    val url: String,
    val modal: String,
    val is_subscriber: Boolean,
    val badgeCount: Int?,
    val hasBadge: Boolean?,
    val is_new: Boolean?,
    val answers_muted: Boolean,
    val version: String,
    val version_check: Boolean,
    val use_api: Boolean,
)
