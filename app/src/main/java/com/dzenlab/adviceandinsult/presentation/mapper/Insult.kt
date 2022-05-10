package com.dzenlab.adviceandinsult.presentation.mapper

import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.presentation.models.Insult
import com.dzenlab.adviceandinsult.presentation.models.InsultAndComment

fun Insult.Companion.toInsult(insultNet: InsultNet): Insult =
    Insult(number = insultNet.number, insult = insultNet.insult, comment = insultNet.comment)

fun Insult.Companion.toInsult(insultSP: InsultSP): Insult =
    Insult(number = insultSP.number, insult = insultSP.insult, comment = insultSP.comment)

fun Insult.toInsultSP(): InsultSP = InsultSP(number = number, insult = insult, comment = comment)

fun Insult.toInsultNumberDB(): InsultNumberDB = InsultNumberDB(number = number)

fun Insult.toInsultCreateDB(): InsultCreateDB =
    InsultCreateDB(number = number, insult = insult, comment = comment)

fun Insult.toInsultAndComment(): InsultAndComment =
    InsultAndComment(insult = insult, comment = comment)