package br.moneda

import com.datastax.spark.connector._
import org.apache.spark._
import org.joda.time.DateTime

import scala.util.Random

object Main {

  val sc :SparkContext = configSpark()
  var count : Int = 0;
  var run : Boolean = true;
  def oncePerSecond(callback: () => Unit) {
    while (run) {
      callback();
      if(count < 1) {
        count = count + 1;
      }else {
        run = false
      }
      Thread sleep 1000
    }
  }

  def saveBlogsToCassandra (sparkMessage: SparkMessage, sc : SparkContext) : Unit = {
    var seq = scala.collection.mutable.Seq.empty[Tuple2[String,String]];

    for(a <- 0 until sparkMessage.getMessageSize){
      val blog : BlogMessage = sparkMessage.getMessages.get(a).asInstanceOf[BlogMessage]
      val t = (blog.getAuthor + " " + new Random(new DateTime().getMillisOfDay).nextInt(), blog.getBlog)
      seq = seq :+ t
    }
    val collection = sc.parallelize(seq.toSeq)
    collection.saveToCassandra("test", "blogs", SomeColumns("author", "blog"))
  }

  def runSparkRun(sc : SparkContext) : Unit = {

    for( b <- 1 until 5){
      var seq = scala.collection.mutable.Seq.empty[Tuple2[String,String]]
      for( a <- 1 until 100000){
        if(a % 10000 == 0){
           println(a)
        }
        val t = (b.toString + a.toString,"12312312321")
        seq = seq :+ t
      }
      println("INICIO: "+DateTime.now())
      val collection = sc.parallelize(seq.toSeq)
      collection.saveToCassandra("test", "blogs", SomeColumns("author", "blog"))
      println("FIM: "+DateTime.now())
    }


  }

  def configSpark() : SparkContext = {
    val conf = new SparkConf()
      .set("spark.master","local[4]")
      .set("spark.executor.memory","2g")
      .set("spark.cassandra.connection.host","127.0.0.1")
      .set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      .set("spark.eventLog.enabled","true")
      .set("spark.eventLog.dir","/var/tmp/eventLog")
      .setAppName("teste-spark")
    return new SparkContext(conf)
  }

  def main(args :Array[String]): Unit ={
    val sc = configSpark()
    ActiveMqToSpark.consumeSparkMessages((sparkMessage) => {
        if(sparkMessage.getObjectName == "blog"){
          saveBlogsToCassandra(sparkMessage,sc)
        }else{
          println("Not supported")
        }

    })
    sc.stop

  }

  def fakeRun = {
    println("Start time: "+Main.sc.startTime)
  }
}
