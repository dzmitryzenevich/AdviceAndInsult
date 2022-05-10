package com.dzenlab.adviceandinsult.data.mapper

import com.dzenlab.adviceandinsult.data.sharepref.models.AdviceSharePref
import com.dzenlab.adviceandinsult.models.AdviceSP

internal fun AdviceSharePref.toAdviceSP(): AdviceSP = AdviceSP(number = number, advice = advice)

internal fun AdviceSharePref.Companion.toAdviceSharePref(adviceSP: AdviceSP): AdviceSharePref =
    AdviceSharePref(number = adviceSP.number, advice = adviceSP.advice)