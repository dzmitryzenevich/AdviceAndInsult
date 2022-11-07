package com.dzenlab.adviceandinsult.data.sharepref.storage

import android.content.SharedPreferences
import com.dzenlab.adviceandinsult.data.constants.*
import com.dzenlab.adviceandinsult.data.sharepref.models.InsultSharePref

class InsultSharePrefStorage(private val sharedPreferences: SharedPreferences) {

    internal fun getInsult(): InsultSharePref {

        val number = sharedPreferences.getInt(SHARE_PREF_INSULT_NUMBER, 0)

        val insult = sharedPreferences.getString(
            SHARE_PREF_INSULT_INSULT, SHARE_PREF_NO_DATA) ?: SHARE_PREF_NO_DATA

        val comment = sharedPreferences.getString(
            SHARE_PREF_INSULT_COMMENT, SHARE_PREF_NO_DATA) ?: SHARE_PREF_NO_DATA

        return InsultSharePref(number = number, insult = insult, comment = comment)
    }

    internal fun saveInsult(insultSharePref: InsultSharePref) {

        sharedPreferences.edit().putInt(SHARE_PREF_INSULT_NUMBER,
            insultSharePref.number).apply()

        sharedPreferences.edit().putString(SHARE_PREF_INSULT_INSULT,
            insultSharePref.insult).apply()

        sharedPreferences.edit().putString(SHARE_PREF_INSULT_COMMENT,
            insultSharePref.comment).apply()
    }
}