package com.remote.project

import android.app.Application
import com.remote.hklibrary.helper.INetHelper
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class App:Application(){
    companion object{
        private  var instance:App by SingleValue()
        private  var helper: INetHelper by SingleValue()
        fun instance() = instance!!
        fun helper() = helper!!
    }
    override fun onCreate() {
        super.onCreate()
        print(" this is onCreate is running")
        instance = this
        helper = INetHelper()

    }
   private class SingleValue<T>:ReadWriteProperty<Any?,T>{
         private var value:T? = null
       override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
           this.value = if (this.value == null) value
           else throw IllegalStateException("application already initialized")

       }

       override fun getValue(thisRef: Any?, property: KProperty<*>): T {
           return value ?: throw IllegalStateException("application not initialized")
       }

   }


}