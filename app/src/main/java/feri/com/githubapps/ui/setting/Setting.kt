package feri.com.githubapps.ui.setting

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.databinding.DataBindingUtil
import feri.com.githubapps.R
import feri.com.githubapps.databinding.ActivitySettingBinding
import feri.com.githubapps.shared.AlarmReceiver
import feri.com.githubapps.shared.SPManager
import feri.com.githubapps.shared.getViewModel

class Setting : AppCompatActivity() {
    private lateinit var settingBinding: ActivitySettingBinding
    private lateinit var vm:SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding=DataBindingUtil.setContentView(this,R.layout.activity_setting)
        vm=getViewModel{SettingViewModel()}
        settingBinding.vm=vm
        settingBinding.lifecycleOwner=this
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
        setAlarm()
    }

    private fun setAlarm() {
        val spManager=SPManager(this)
        settingBinding.switch2.isChecked=spManager.isAlarmSetUp()
        settingBinding.switch2.setOnCheckedChangeListener { compoundButton, b ->
            if (b||AlarmReceiver().isAlarmSet(this,"GithubApps Alarm")==false){
                AlarmReceiver().setRepeatingAlarm(this,"GithubApps Alarm","09:00","Peringatan harian pukul 09:00")
                spManager.saveSetupAlarm(true)
            }else{
                AlarmReceiver().cancelAlarm(this,"GithubApps Alarm")
                spManager.saveSetupAlarm(false)
            }
        }
        settingBinding.button.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}