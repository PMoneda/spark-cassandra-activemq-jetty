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
    val hello =  Main.sc.cassandraTable[(String, String)]("test", "blogs")
    println("Total: " + hello.count)
    val responseBody : NodeSeq =
      <message>
      <body>
        <blogs>{hello.toArray().map(i => <b><a>{i._1}</a><l>{i._2}</l></b>)}</blogs>
      </body>
    </message>

    response.getWriter.write(responseBody.toString)
  }

}