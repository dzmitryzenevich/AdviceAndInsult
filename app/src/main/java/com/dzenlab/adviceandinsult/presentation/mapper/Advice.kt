package com.dzenlab.adviceandinsult.presentation.mapper

import com.dzenlab.adviceandinsult.models.AdviceCreateDB
import com.dzenlab.adviceandinsult.models.AdviceNet
import com.dzenlab.adviceandinsult.models.AdviceNumberDB
import com.dzenlab.adviceandinsult.models.AdviceSP
import com.dzenlab.adviceandinsult.presentation.models.Advice

fun Advice.Companion.toAdvice(adviceNet: AdviceNet): Advice =
    Advice(number = adviceNet.number, advice = adviceNet.advice)

fun Advice.Companion.toAdvice(adviceSP: AdviceSP): Advice =
    Advice(number = adviceSP.number, advice = adviceSP.advice)

fun Advice.toAdviceSP(): AdviceSP = AdviceSP(number = number, advice = advice)

fun Advice.toAdviceNumberDB(): AdviceNumberDB = AdviceNumberDB(number = number)

fun Advice.toAdviceCreateDB(): AdviceCreateDB = AdviceCreateDB(number = number, advice = advice)