package com.thoughtworks.hcd

import akka.actor.{ActorRef, ActorSystem}
import com.sun.org.apache.xml.internal.security.Init
import com.thoughtworks.common.CommandMessage

object HCDApp extends App{
  private val actorSystem = ActorSystem("ActorSystem-HCD")
  val hcdSupervisorActorRef: ActorRef = actorSystem.actorOf(SupervisorHCDActor.props(), "Supervisor-HCD")

  for(a <- 0 until 10){
    hcdSupervisorActorRef ! new CommandMessage(a.toString)
  }
}
