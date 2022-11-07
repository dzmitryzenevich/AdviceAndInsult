package com.dzenlab.adviceandinsult.data.sharepref.storage

import android.content.SharedPreferences
import com.dzenlab.adviceandinsult.data.constants.SHARE_PREF_ADVICE_ADVICE
import com.dzenlab.adviceandinsult.data.constants.SHARE_PREF_ADVICE_NUMBER
import com.dzenlab.adviceandinsult.data.constants.SHARE_PREF_NO_DATA
import com.dzenlab.adviceandinsult.data.sharepref.models.AdviceSharePref

class AdviceSharePrefStorage(private val sharedPreferences: SharedPreferences) {

    internal fun getAdvice(): AdviceSharePref {

        val number = sharedPreferences.getInt(SHARE_PREF_ADVICE_NUMBER, 0)

        val advice = sharedPreferences.getString(
            SHARE_PREF_ADVICE_ADVICE, SHARE_PREF_NO_DATA) ?: SHARE_PREF_NO_DATA

        return AdviceSharePref(number = number, advice = advice)
    }

    internal fun saveAdvice(adviceSharePref: AdviceSharePref) {

        sharedPreferences.edit().putInt(SHARE_PREF_ADVICE_NUMBER, adviceSharePref.number).apply()

        sharedPreferences.edit().putString(SHARE_PREF_ADVICE_ADVICE, adviceSharePref.advice).apply()
    }
}