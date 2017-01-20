package com.thoughtworks.app

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer
import com.thoughtworks.assembly.SupervisorAssemblyActor
import com.thoughtworks.common.{CommandMessage, EventMessage}
import com.thoughtworks.redis.RedisSubscriber
import com.typesafe.config.ConfigFactory

object Assembly extends App{
  implicit val actorSystem = ActorSystem("ActorSystem-Assembly",ConfigFactory.parseString("akka.remote.netty.tcp.port=2554").withFallback(ConfigFactory.load()))
  implicit val materializer = ActorMaterializer()

  val assemblySupervisor = actorSystem.actorOf(SupervisorAssemblyActor.props(), "Supervisor-Assembly")

  startHttpServer

  private def invokeAssembly = {
    for (a <- 0 until 10) {
      if (a % 2 == 0)
        assemblySupervisor ! new CommandMessage(a.toString)
      else
        assemblySupervisor ! new EventMessage(a.toString)
    }
  }

  actorSystem.actorOf(Props(classOf[RedisSubscriber], Seq("hcd-status"), Seq("")).withDispatcher("rediscala.rediscala-client-worker-dispatcher"))

  private def startHttpServer() = {

    val requestHandler: HttpRequest => HttpResponse = {
      case HttpRequest(HttpMethods.POST, Uri.Path("/start-assembly"), _, _, _) =>
        assemblySupervisor ! invokeAssembly
        HttpResponse(entity = "Assembly started!")

      case r: HttpRequest =>
        r.discardEntityBytes() // important to drain incoming HTTP Entity stream
        HttpResponse(404, entity = "Unknown resource!")
    }

    val bindingFuture = Http().bindAndHandleSync(requestHandler, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  }
}

