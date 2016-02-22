package com.kunyandata.nlpsuit.classification

import scala.collection.{mutable, Map}

/**
  * Created by QQ on 2016/2/18.
  */
object regular {

  private def grep(textString: String, categoryKeywords: Map[String, Array[String]],
                   categoryList: mutable.MutableList[String]): Unit = {

    for (indus: String <- categoryKeywords.keys) {
      var i_control = true
      for (keyword: String <- categoryKeywords(indus) if i_control) {
        val exists = textString.contains(keyword)
        if (exists) {
          categoryList.+=(indus)
          i_control = false
        }
      }
    }
  }

  def predict(textString: String, stockDict: Map[String, Array[String]],
              indusDict: Map[String, Array[String]],
              sectDict: Map[String, Array[String]]) = {

    val industryList = new mutable.MutableList[String]
    val stockList = new mutable.MutableList[String]
    val sectionList = new mutable.MutableList[String]

    //    行业分类
    grep(textString, indusDict, industryList)
    //    概念板块分类
    grep(textString, sectDict, sectionList)
    //    股票分类
    grep(textString, stockDict, stockList)

    industryList.mkString(",")
    sectionList.mkString(",")
    stockList.mkString(",")

    //    返回值的顺序为股票，行业，版块
    (stockList.mkString(","), industryList.mkString(","), sectionList.mkString(","))
  }
}