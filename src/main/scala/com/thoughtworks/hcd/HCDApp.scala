package com.thoughtworks.hcd

import akka.actor.{ActorRef, ActorSystem}
import com.thoughtworks.common.CommandMessage
import com.typesafe.config.ConfigFactory

object HCDApp extends App{
//  val config = ConfigFactory
//    .parseString("akka.remote.netty.tcp.port=2554")

  private val actorSystem = ActorSystem("ActorSystem-HCD",ConfigFactory.load())

  val hcdSupervisorActorRef: ActorRef = actorSystem.actorOf(SupervisorHCDActor.props(), "Supervisor-HCD")

  println(hcdSupervisorActorRef)

  for(a <- 0 until 10){
    hcdSupervisorActorRef ! new CommandMessage(a.toString)
  }
}
