package com.example.petwelfare.logic.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

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

    // 其他描述
    var description: String,

    var head_image: String,
    var name: String,

    // 在宠物列表中的id
    var pet_id: Int,

    // 照片墙照片
    var photos: ArrayList<String>,

    var sex: String,
    var type: String

) : Serializable {

    constructor() : this(
        "", "", "", "",
        0, arrayListOf(), "", ""
    )
}