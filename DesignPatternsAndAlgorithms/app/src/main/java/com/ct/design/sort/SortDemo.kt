package com.ct.design.sort

import android.R.attr


private var ARRAY = intArrayOf(85, 16, 32, 1, 8, 2, 17, 33, 24, 63, 99, 44)

//https://www.cnblogs.com/guoyaohua/p/8600214.html
fun main() {


    println("排序前:${ARRAY.contentToString()}")
    //冒泡排序
    //bubbleSort(ARRAY)

    //选择排序
    //selectionSort(ARRAY)

    //插入排序
    //insertSort(ARRAY)

    //希尔排序
    shellSort(ARRAY)

    //归并排序
    //ARRAY = mergeSort(ARRAY)

    //快速排序
    quickSort(ARRAY, 0, ARRAY.size-1)
    println("排序后:${ARRAY.contentToString()}")


}


/**
 * 冒泡排序
 * */
private fun bubbleSort(array: IntArray) {

    for (i in array.indices) {
        for (j in 0 until array.size - 1 - i) {
            if (array[j + 1] < array[j]) {

                val temp = array[j + 1]
                array[j + 1] = array[j]
                array[j] = temp
            }
        }
    }

}

/**
 * 选择排序
 *  首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
 *  然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾
 * */
private fun selectionSort(array: IntArray) {
    for (i in array.indices) {
        var minIndex = i
        for (j in i + 1 until array.size) {
            if (array[minIndex] > array[j])
                minIndex = j
        }
        val temp = array[i]
        array[i] = array[minIndex]
        array[minIndex] = temp
    }
}

/**
 * 插入排序
 *1.从第一个元素开始，该元素可以认为已经被排序；
 *2.取出下一个元素，在已经排序的元素序列中从后向前扫描；
 *3.如果该元素（已排序）大于新元素，将该元素移到下一位置；
 *4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 *5.将新元素插入到该位置后；
 * 重复步骤2~5。
 *
 * */
private fun insertSort(array: IntArray) {
    println(array.size)
    for (i in 0 until array.size - 1) {
        val value = array[i + 1]
        var preIndex = i
        while (preIndex >= 0 && array[preIndex] > value) {
            array[preIndex + 1] = array[preIndex]
            preIndex--
        }
        array[preIndex + 1] = value
    }
}


/**
 * 希尔排序
 * */
private fun shellSort(array: IntArray) {
    val len = array.size
    var gap = len / 2
    while (gap > 0) {
        for (i in gap until len) {
            val temp = array[i]
            var preIndex = i - gap
            while (preIndex >= 0 && array[preIndex] > temp) {
                array[preIndex + gap] = array[preIndex]
                preIndex -= gap
            }
            array[preIndex + gap] = temp
        }
        gap = gap shr 1
    }
}

/**
 * 归并排序
 * */
private fun mergeSort(array: IntArray): IntArray {
    if (array.size < 2)
        return array
    val mid = array.size / 2
    val left = array.copyOfRange(0, mid)
    val right = array.copyOfRange(mid, array.size)
    return merge(mergeSort(left), mergeSort(right))

}

private fun merge(left: IntArray, right: IntArray): IntArray {
    val newArray = IntArray(left.size + right.size)

    var index = 0
    var i = 0
    var j = 0
    while (index < newArray.size) {
        when {
            i >= left.size -> newArray[index] = right[j++]
            j >= right.size -> newArray[index] = left[i++]
            left[i] > right[j] -> newArray[index] = right[j++]
            else -> newArray[index] = left[i++]
        }
        index++
    }

    return newArray
}


/**
 * 快速排序
 *
 * 1．先从数列中取出一个数作为基准数。
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左右区间重复第二步，直到各区间只有一个数。
 * */
private fun quickSort(array: IntArray, start: Int, end: Int) {
    if (start >= end)
        return

    var low = start
    var high = end

    val index = array[low]

    while (low < high) {
        while (index <= array[high] && low < high)
            high--
        if(low < high){
            array[low] = array[high]
            low++

        }

        while (index > array[low] && low < high)
            low++

        if(low < high){
            array[high] = array[low]
            high--
        }

    }

    array[low] = index

    quickSort(array, start, low-1)
    quickSort(array, low+1, end)

}

//private fun partition(array: IntArray, start: Int, end: Int): Int {
//
//}