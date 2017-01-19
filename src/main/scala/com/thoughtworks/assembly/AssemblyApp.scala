package com.thoughtworks.assembly

import akka.actor.{ActorSelection, ActorSystem}
import com.thoughtworks.common.{CommandMessage, EventMessage}
import com.typesafe.config.ConfigFactory

object AssemblyApp extends App{
  val actorSystem = ActorSystem("ActorSystem-Assembly",ConfigFactory.parseString("akka.remote.netty.tcp.port=2554").withFallback(ConfigFactory.load()))
  val assemblySupervisor = actorSystem.actorOf(SupervisorAssemblyActor.props(), "Supervisor-Assembly")

  for (a <- 0 until 10) {
    if (a%2 == 0)
      assemblySupervisor ! new CommandMessage(a.toString)
    else
      assemblySupervisor ! new EventMessage(a.toString)
  }
}
