package com.thoughtworks.assembly

import akka.actor.ActorSystem
import com.thoughtworks.common.{CommandMessage, EventMessage}

object AssemblyApp extends App{
  val actorSystem = ActorSystem("ActorSystem-Assembly")
  val assemblySupervisor = actorSystem.actorOf(SupervisorAssemblyActor.props(), "Supervisor-Assembly")

  for (a <- 0 until 10) {
    if (a%2 == 0)
      assemblySupervisor ! new CommandMessage(a.toString)
    else
      assemblySupervisor ! new EventMessage(a.toString)
  }
}
