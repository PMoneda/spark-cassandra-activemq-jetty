package br.moneda

import org.apache.spark.SparkContext
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder


object JettyServer {

  def startJettyServer = {
    val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    val server = new Server(8089)
    server.setHandler(context)
    context.addServlet(new ServletHolder(new HelloServlet), "/*");
    def start() = server.start
    def stop() = server.stop
    start()
  }

  def main(args: Array[String]) : Unit = {
    Main.fakeRun
    startJettyServer
  }
}
