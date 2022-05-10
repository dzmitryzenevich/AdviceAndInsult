package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.database.models.*
import com.dzenlab.adviceandinsult.data.database.models.InsultCreate
import com.dzenlab.adviceandinsult.models.*

internal fun InsultCreate.Companion.toInsultCreate(insultCreateDB: InsultCreateDB): InsultCreate =
    InsultCreate(
        number = insultCreateDB.number,
        insult = insultCreateDB.insult,
        comment = insultCreateDB.comment)

internal fun InsultGet.toInsultGetDB(): InsultGetDB =
    InsultGetDB(id = id, number = number, insult = insult, comment = comment)

internal fun InsultUpdate.Companion.toInsultUpdate(insultUpdateDB: InsultUpdateDB): InsultUpdate =
    InsultUpdate(id= insultUpdateDB.id, insult = insultUpdateDB.insult)

internal fun InsultDelete.Companion.toInsultDelete(insultDeleteDB: InsultDeleteDB): InsultDelete =
    InsultDelete(id = insultDeleteDB.id)