package com.example.esgithub.models.network

import com.example.esgithub.models.user.UserDataModel

data class UserNetwork(
    val userId: String,
    val followers: List<FollowerRelationship>,
    val followings: List<FollowingRelationship>
)

data class FollowerRelationship(
    val follower: UserDataModel,
    val relationId: String
)

data class FollowingRelationship(
    val following: UserDataModel,
    val relationId: String
)
