package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.main.mine.pet.PetActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class PetsAdapter (private val list: MutableList<Pet>, private val lifecycleOwner: LifecycleOwner, val type: Int) : RecyclerView.Adapter<PetsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val petItem = binding.petItem
        val deleteBtn = binding.deletePetBtn
        val headImage = binding.petHeadImage
        val name = binding.petName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // 传递给详情页
        holder.petItem.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, PetActivity::class.java)
            intent.putExtra("pet_id", item.pet_id)
            intent.putExtra("birthday", item.birthday)
            intent.putExtra("head_image", item.head_image)
            intent.putExtra("name", item.name)
            intent.putExtra("sex", item.sex)
            intent.putExtra("type", item.type)
            intent.putExtra("description", item.description)
            intent.putStringArrayListExtra("photos", item.photos)
            // 检查context是否是Activity的Context，如果不是，则添加FLAG_ACTIVITY_NEW_TASK标志
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)
        }

        // 看是否为管理页的列表，来决定是否要有删除键
        holder.deleteBtn.visibility = if(type == 1) View.GONE else View.VISIBLE

        // 删除
        holder.deleteBtn.setOnClickListener {
            val _delPetResponse = MutableLiveData<BaseResponse>()
            val delPetResponse : LiveData<BaseResponse> = _delPetResponse
            val a = CoroutineScope(Dispatchers.Main)
            CoroutineScope(Dispatchers.Main).launch {
                _delPetResponse.value = PetWelfareNetwork.delPet(item.pet_id.toString(), Repository.Authorization)
            }
            delPetResponse.observe(lifecycleOwner) { result->
                if (result.code == 200) {
                    Toast.makeText(PetWelfareApplication.context, "删除成功", Toast.LENGTH_SHORT).show()
                } else if (result.code != 0) {
                    Toast.makeText(PetWelfareApplication.context, "删除失败", Toast.LENGTH_SHORT).show()
                }
                _delPetResponse.value = BaseResponse(0, "") // 以便下一次再进行删除的网络请求
            }
        }

        //...进行数据的处理与呈现
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = item.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(PetWelfareApplication.context).load(headImageGlideUrl).into(it) }
        holder.name.text = item.name
    }
}