package br.moneda

import javax.servlet.http.HttpServlet
import com.datastax.spark.connector._
import org.apache.spark._
class HelloServlet extends HttpServlet {

  import scala.xml.NodeSeq
  import javax.servlet.http.HttpServletRequest
  import javax.servlet.http.HttpServletResponse

  override def service(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("text/xml")
    response.setCharacterEncoding("UTF-8")
    response.addHeader("token","873812738171badasdgasdgag13617")

    val sc = Main.configSpark()
    val hello = sc.cassandraTable[(String, String)]("test", "hello")
    val first = hello.first
    println(first)

    //val hello : String = "Hello World"
    val responseBody : NodeSeq =
      <message>
      <header>
        <source>Spark 1.2.3</source>
        <query>SELECT * FROM hello</query>
      </header>
      <body>
        <blogs>
          <author>{first._1}</author>
          <blog>{first._2}</blog>
        </blogs>
      </body>
    </message>
    response.getWriter.write(responseBody.toString)
  }

}