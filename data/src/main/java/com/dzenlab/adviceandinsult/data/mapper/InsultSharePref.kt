package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.sharepref.models.InsultSharePref
import com.dzenlab.adviceandinsult.models.InsultSP

internal fun InsultSharePref.toInsultSP(): InsultSP =
    InsultSP(number = number, insult = insult, comment = comment)

internal fun InsultSharePref.Companion.toInsultSharePref(insultSP: InsultSP): InsultSharePref =
    InsultSharePref(
        number = insultSP.number,
        insult = insultSP.insult,
        comment = insultSP.comment)