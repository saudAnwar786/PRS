package com.sacoding.prs.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ArrayParcleable(
    val data:ArrayList<String>
): Serializable
