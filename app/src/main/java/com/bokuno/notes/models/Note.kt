package com.bokuno.notes.models

data class Note(
    val title:String?=null,
    val text:String?=null,
    val createdAt:Long?=null,
    val userId:String?=null,
    val location:String?=null
)