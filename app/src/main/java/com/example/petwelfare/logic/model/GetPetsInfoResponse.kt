package com.example.petwelfare.logic.model

data class GetPetsInfoResponse(
    val code: Int,
    val msg: String,
    val data: Pets
)

data class Pets(
    val pets: List<Pet>
)

data class Pet(
    val birthday: String,

    /**
     * 其他描述
     */
    val description: String,
    val head_image: String,

    val name: String,

    /**
     * 在该用户的宠物列表中的id
     */
    val pet_id: Int,

    /**
     * 照片墙照片
     */
    val photos: List<String>,

    val sex: String,
    val type: String
)