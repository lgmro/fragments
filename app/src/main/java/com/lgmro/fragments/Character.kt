package com.lgmro.fragments

import java.io.Serializable

data class Character(
    val name: String,
    val description: String,
    val imageResid: Int
) : Serializable