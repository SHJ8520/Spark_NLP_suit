package com.kunyandata.nlpsuit.util

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by QQ on 2016/2/23.
  */
class TimeUtil {

  def get_date(timeString: String, formatString: String): String ={

    var dateFormat = new SimpleDateFormat(formatString)
    //    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH")
    val out = dateFormat.format(timeString)
    out
  }
}
