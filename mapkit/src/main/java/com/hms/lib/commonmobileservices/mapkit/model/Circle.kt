package com.hms.lib.commonmobileservices.mapkit.model

import java.lang.Exception



class Circle(private val circleImpl: Any) {
    fun hide(){
        when(circleImpl){
            is HmsCircle -> circleImpl.isVisible=false
            is GmsCircle -> circleImpl.isVisible=false
        }
    }

    fun show(){
        when(circleImpl){
            is HmsCircle -> circleImpl.isVisible=true
            is GmsCircle -> circleImpl.isVisible=true
        }
    }

    fun remove():Boolean{
        return try{
            when (circleImpl){
                is HmsCircle -> circleImpl.remove()
                is GmsCircle -> circleImpl.remove()
            }
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}