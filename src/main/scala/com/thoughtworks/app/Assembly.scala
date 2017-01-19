package com.thoughtworks.app

import akka.actor.{ActorSystem, Props}
import com.thoughtworks.assembly.SupervisorAssemblyActor
import com.thoughtworks.common.{CommandMessage, EventMessage}
import com.thoughtworks.redis.RedisSubscriber
import com.typesafe.config.ConfigFactory

object Assembly extends App{
  val actorSystem = ActorSystem("ActorSystem-Assembly",ConfigFactory.parseString("akka.remote.netty.tcp.port=2554").withFallback(ConfigFactory.load()))
  val assemblySupervisor = actorSystem.actorOf(SupervisorAssemblyActor.props(), "Supervisor-Assembly")

  for (a <- 0 until 10) {
    if (a%2 == 0)
      assemblySupervisor ! new CommandMessage(a.toString)
    else
      assemblySupervisor ! new EventMessage(a.toString)
  }

  actorSystem.actorOf(Props(classOf[RedisSubscriber], Seq("hcd-status"), Seq("")).withDispatcher("rediscala.rediscala-client-worker-dispatcher"))
}
