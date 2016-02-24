package com.kunyandata.nlpsuit.hotwords
import com.kunyandata.nlpsuit.hotwords.BaseHttp

import scala.collection.mutable

/**
  * Created by Administrator on 2016/1/26.
  */
object HWHttp extends BaseHttp{
  override def get(strUrl: String, parameters: mutable.HashMap[String, String], parse: String): Unit = super.get(strUrl, parameters, parse)
  def sendNew(url:String,parameters: mutable.HashMap[String, String]): Unit ={
    get(url,parameters,"prase")
  }

}
