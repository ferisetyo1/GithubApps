package feri.com.githubapps.shared

import android.content.Context
import android.content.SharedPreferences

class SPManager(ctx: Context) {
    val sp: SharedPreferences
    val SP_APP = "GITHUB APPS"
    val SP_IS_SETUP_ALARM = "spLogin"

    init {
        sp = ctx.getSharedPreferences(SP_APP, Context.MODE_PRIVATE)
    }

    fun saveSetupAlarm(value: Boolean) {
        saveSPBoolean(SP_IS_SETUP_ALARM, value)
    }

    fun saveSPBoolean(keySP: String, value: Boolean) {
        val spEditor = sp.edit()
        spEditor.putBoolean(keySP, value)
        spEditor.apply()
    }


    fun isAlarmSetUp(): Boolean {
        return sp.getBoolean(SP_IS_SETUP_ALARM, false)
    }
}