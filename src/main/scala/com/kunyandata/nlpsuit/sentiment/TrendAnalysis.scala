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

  def search_senti(title_cut:Array[String], dict_p:Array[String], dict_n:Array[String], dict_f:Array[String]): Int ={
    /* this will search the sentiment words in the sentence
     * count the sentence emotional tendency according to the sentiment word
     */
    // Interrogative Sentences
    //    if (title_cut(title_cut.length-1) == "?"){
    //      return 0
    //    }
    var p = 0
    var n = 0
    //    var s = ""
    // traverse every word in sentence
    for (i <- title_cut.indices) {
      val t_c = title_cut(i)
      // if word in positive dictionary
      if(dict_p.contains(t_c)){
        if(count_senti(i, title_cut, dict_f)>0){
          p = p + 1
        }
        else{
          n = n + 1
        }
        //        s = s + t_c + " "
      }
      // if word in negative dictionary
      else if (dict_n.contains(t_c)){
        if(count_senti(i, title_cut, dict_f)>0){
          n = n + 1
        }
        else{
          p = p + 1
        }
        //        s = s + t_c + " "
      }
    }
    // positive
    if (p > n){
      //      writer.write("，" + s + "，" + "positive" + "\n")
      1
    }
    // negative
    else if (p < n){
      //      writer.write("，" + s + "，" + "negative" + "\n")
      -1
    }
    // neutral
    else{
      //      writer.write("，" + "NULL" + "，" + "neutral" + "\n")
      0
    }
  }


}
