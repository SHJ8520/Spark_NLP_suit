package com.kunyandata.nlpsuit.hotwords

import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.Date

import _root_.com.kunyandata.nlpsuit.hotwords.HWHttp
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Scan, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos
import org.apache.hadoop.hbase.util.{Base64, Bytes}
import org.wltea.analyzer.IKSegmentation

import scala.collection.mutable

/**
  * Created by QQ on 2016/2/24.
  */
class TriSortHotWords {

  val conf = HBaseConfiguration.create()
  //分词
  def SplitWords(tuple: (ImmutableBytesWritable, Result)): mutable.MutableList[String] = {
    var list = new mutable.MutableList[String]
    val result = tuple._2
    val title = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("title")))
    val reader: StringReader = new StringReader(title)
    val ik = new IKSegmentation(reader, true)
    var lexeme = ik.next()
    while (lexeme != null) {
      val word = lexeme.getLexemeText()
      list += word.toString
      lexeme = ik.next()
    }
    list
  }

  //获取时间戳
  def getTime(): Unit ={
    val scan = new Scan()
    val date = new Date(new Date().getTime - 60 * 60 * 1000)
    val format = new SimpleDateFormat("yyyy-MM-dd HH")
    val time = format.format(date)
    val time1 = format.format(new Date().getTime)
    val starttime = time + "-00-00"
    val stoptime = time1 + "-00-00"
    val sdf1: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
    val startrow: Long = sdf1.parse(starttime).getTime
    val stoprow: Long = sdf1.parse(stoptime).getTime
    println(startrow)
    println(stoprow)
    scan.setTimeRange(startrow, stoprow)
    val proto: ClientProtos.Scan = ProtobufUtil.toScan(scan)
    val scanToString = Base64.encodeBytes(proto.toByteArray)
    conf.set(TableInputFormat.SCAN, scanToString)
  }

  //前后两小时的排序差
  def obtainHbaseRdd(): Unit = {
    val mapBefore = new mutable.HashMap[String, Int]()
    val mapDiff = new mutable.HashMap[String, Int]()
    var difference = 0
    var value: Int = 0
    var key = ""
    if (mapBefore.contains(key)) {
      difference = value - mapBefore.get(key).get
      mapDiff.+=((key, difference))
    } else {

      val differences = value - mapBefore.size
      mapDiff.+=((key, differences))
    }
  }

  //调用HTTP接口
  def compare: Unit = {
    //paramaMap处理后的数据集
    var paramaMap = new mutable.HashMap[String, String]
    HWHttp.sendNew("http://120.55.189.211/cgi-bin/northsea/prsim/subscribe/1/hot_words_notice.fcgi", paramaMap)
  }

}
