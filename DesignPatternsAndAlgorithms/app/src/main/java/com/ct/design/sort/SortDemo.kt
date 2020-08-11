package com.ct.design.sort

import java.util.*

private val ARRAY = intArrayOf(85, 16, 32, 1, 8, 2, 17, 33, 24, 63, 99, 44)

fun main() {


    println("排序前:${ARRAY.contentToString()}")
    //冒泡排序
    bubbleSort(ARRAY)
    println("排序后:${ARRAY.contentToString()}")




}


/**
 * 冒泡排序
 * */
private fun bubbleSort(array: IntArray) {

    for (i in array.indices) {
        for (j in 0 until array.size-1-i) {
            if (array[j + 1] > array[j]) {

                val temp = array[j + 1]
                array[j + 1] = array[j]
                array[j] = temp
            }
        }
    }

}