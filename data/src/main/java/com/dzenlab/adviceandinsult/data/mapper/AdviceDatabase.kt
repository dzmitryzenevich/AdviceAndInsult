package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.database.models.AdviceCreate
import com.dzenlab.adviceandinsult.data.database.models.AdviceDelete
import com.dzenlab.adviceandinsult.data.database.models.AdviceGet
import com.dzenlab.adviceandinsult.data.database.models.AdviceUpdate
import com.dzenlab.adviceandinsult.models.AdviceCreateDB
import com.dzenlab.adviceandinsult.models.AdviceDeleteDB
import com.dzenlab.adviceandinsult.models.AdviceGetDB
import com.dzenlab.adviceandinsult.models.AdviceUpdateDB

internal fun AdviceCreate.Companion.toAdviceCreate(adviceCreateDB: AdviceCreateDB): AdviceCreate =
    AdviceCreate(number = adviceCreateDB.number, advice = adviceCreateDB.advice)

internal fun AdviceGet.toAdviceGetDB(): AdviceGetDB = AdviceGetDB(id = id, number = number, advice = advice)

internal fun AdviceUpdate.Companion.toAdviceUpdate(adviceUpdateDB: AdviceUpdateDB): AdviceUpdate =
    AdviceUpdate(id= adviceUpdateDB.id, advice = adviceUpdateDB.advice)

internal fun AdviceDelete.Companion.toAdviceDelete(adviceDeleteDB: AdviceDeleteDB): AdviceDelete =
    AdviceDelete(id = adviceDeleteDB.id)