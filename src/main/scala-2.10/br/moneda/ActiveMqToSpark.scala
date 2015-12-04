package br.moneda


import javax.jms._
import com.google.gson.Gson
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.command.ActiveMQBytesMessage

/**
  * Created by philippe on 02/12/15.
  */
object ActiveMqToSpark {
   val activeMqUrl: String = "tcp://192.168.1.211:61616"
   val queueName : String = "rdx_update_spark"
   val user : String = "admin"
   val password : String = "Radix@123"

   def consumeSparkMessages ( messageSpark :SparkMessage => Unit) : Unit = {
     val connectionFactory  = new ActiveMQConnectionFactory(activeMqUrl)
     val connection = connectionFactory.createConnection(user, password)
     connection.start()

     val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
     val destination = session.createQueue(queueName)
     val consumer = session.createConsumer(destination);
     while (true)
     {
       val message = consumer.receive(1000);
       if (message != null && message.isInstanceOf[ActiveMQBytesMessage]) {
         val byteMsg = message.asInstanceOf[ActiveMQBytesMessage];
         val json = new String(byteMsg.getMessage.getContent.getData,"UTF-8");
         val gson = new Gson();
         val spark = gson.fromJson(json,classOf[SparkMessage])

         messageSpark(spark)
       }
     }
     println("Closing connection");
     consumer.close();
     session.close();
     connection.close();
   }

   def main (args: Array[String]) {
      consumeSparkMessages((sparkMessage) => {
        println(sparkMessage)
      })
   }
}
