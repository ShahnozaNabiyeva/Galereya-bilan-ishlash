package com.shahnoza.galereyabilanishlash.offlinedatabase

import com.shahnoza.galereyabilanishlash.models.ImageUser
import java.util.ArrayList

interface Reja {

    fun addImage(imageUser: ImageUser)

    fun getAllImage():ArrayList<ImageUser>



}