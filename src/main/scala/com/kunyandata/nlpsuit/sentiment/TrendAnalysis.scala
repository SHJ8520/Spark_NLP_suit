package com.kunyandata.nlpsuit.sentiment

/**
  * Created by QQ on 2016/2/23.
  * 情感倾向分析
  */
object TrendAnalysis {

  /**
    *
    * @param i
    * @param sentence
    * @param dic
    * @return
    */
  private def count_senti(i:Int, sentence:Array[String], dic:Array[String]): Int ={
    /* this will search neg word nearby the sentiment word
     * if find a neg word, reverse the sentiment word's emotional tendency
     */
    // find neg word before sentiment word
    if (i-1 > 0){
      if (dic.contains(sentence(i-1))){
        return -1
      }
      else if (i-2 >0){
        if (dic.contains(sentence(i-2))){
          return  -1
        }
      }
    }
    // fine neg word behind sentiment word
    if (i+1 < sentence.length){
      if(dic.contains(sentence(i+1))){
        return -1
      }
      else if(i+2 < sentence.length){
        if (dic.contains(sentence(i+2))){
          return -1
        }
      }
    }
    // with no neg word return 1
    1
  }


}
