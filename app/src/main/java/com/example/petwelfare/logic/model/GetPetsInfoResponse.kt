package com.example.petwelfare.logic.model

data class GetPetsInfoResponse(
    val code: Int,
    val msg: String,
    val data: Pets
)

data class Pets(
    val pets: MutableList<Pet>
) {
    constructor() : this(mutableListOf())
}

data class Pet(
    var birthday: String,

    /**
     * 其他描述
     */
    var description: String,
    var head_image: String,

    var name: String,

    /**
     * 在该用户的宠物列表中的id
     */
    var pet_id: Int,

    /**
     * 照片墙照片
     */
    var photos: ArrayList<String>,

    var sex: String,
    var type: String
) {
    constructor() : this(
        "aa", "aa", "aa", "aa",
        0, arrayListOf(), "aa", "aa"
    )
}