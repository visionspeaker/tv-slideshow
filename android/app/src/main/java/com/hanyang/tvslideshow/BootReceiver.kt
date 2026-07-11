package com.hanyang.tvslideshow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** 크롬캐스트 부팅 완료 시 슬라이드쇼 자동 실행 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val launch = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launch)
        }
    }
}
