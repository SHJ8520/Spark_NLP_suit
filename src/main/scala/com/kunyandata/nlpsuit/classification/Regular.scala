package com.kunyandata.nlpsuit.classification

import scala.collection.{mutable, Map}

/**
  * Created by QQ on 2016/2/18.
  * @author QQ
  */
object Regular {

  private def grep(textString: String, categoryKeywords: Map[String, Array[String]],
                   categoryList: mutable.MutableList[String]): Unit = {
    /**
      * grep(textString, categoryKeywords, categoryList)
      *
      * args:
      * textString: 文本标题字符串
      * categoryKeywords: 分类词典（行业，股票，概念板块）
      * categoryList: 类别List（行业，股票，概念板块）
      *
      * return:
      * Unit
      *
      */
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

  /**
    * 基于规则的分类
    * @param textString 输入的文本标题字符串
    * @param stockDict 股票分类词典
    * @param indusDict 行业分类词典
    * @param sectDict 板块分类词典
    * @return 返回（股票: String，行业: String，版块: String）
    */
  def predict(textString: String, stockDict: Map[String, Array[String]],
              indusDict: Map[String, Array[String]],
              sectDict: Map[String, Array[String]]) = {
    /**
      * predict(textString, stockDict, indusDict, sectDict)
      *
      * args:
      * textString: 文本标题字符串
      * stockDict: 股票关键词词典
      * indusDict: 行业关键词词典
      * sectDict: 概念板块关键词词典
      *
      * return:
      * 返回值的顺序为
      * (股票，行业，版块)
      *
      */
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
