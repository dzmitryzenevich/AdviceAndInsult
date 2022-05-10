package com.dzenlab.adviceandinsult.models

data class AdviceCreateDB(val number: Int, val advice: String)

data class AdviceIdDB(val id: Long)

data class AdviceGetDB(val id: Long, val number: Int, val advice: String)

data class AdviceCountDB(val count: Int)

data class AdviceNumberDB(val number: Int)

data class AdviceIsExistDB(val isExist: Boolean)

data class AdviceUpdateDB(val id: Long, val advice: String)

data class AdviceDeleteDB(val id: Long)