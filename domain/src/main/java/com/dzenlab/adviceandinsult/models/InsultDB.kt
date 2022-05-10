package com.dzenlab.adviceandinsult.models

data class InsultCreateDB(val number: Int, val insult: String, val comment: String)

data class InsultIdDB(val id: Long)

data class InsultGetDB(val id: Long, val number: Int, val insult: String, val comment: String)

data class InsultCountDB(val count: Int)

data class InsultNumberDB(val number: Int)

data class InsultIsExistDB(val isExist: Boolean)

data class InsultUpdateDB(val id: Long, val insult: String)

data class InsultDeleteDB(val id: Long)