package com.thoughtworks.assembly

import akka.actor.{Actor, ActorRef, Props}
import com.thoughtworks.common.{CommandMessage, EventMessage}

class SupervisorAssemblyActor extends Actor {
  var assembly: ActorRef = _

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    assembly = context.actorOf(AssemblyActor.props(), "Assembly")
  }

  override def receive: Receive = {
    case command: CommandMessage => assembly ! command
    case event: EventMessage => assembly ! event
  }
}

object SupervisorAssemblyActor{
  def props() = Props(new SupervisorAssemblyActor)
}
