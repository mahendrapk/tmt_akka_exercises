package com.thoughtworks.assembly

import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorRef, ActorSelection, ActorSystem, Props}
import com.thoughtworks.common.{CommandMessage, EventMessage}

private class AssemblyActor extends Actor with ActorLogging{

  val actorSelection: ActorSelection = context.actorSelection("akka://ActorSystem-HCD/user/Supervisor-HCD")
  var hcdActor: ActorRef = _


  override def preStart(): Unit = {
    actorSelection ! identity(1)
  }

  override def receive: Receive = {
    case ActorIdentity(_,Some(actorRef)) => {
      hcdActor = actorRef
      context.become(commandReady)
    }

    case e : EventMessage => handleEvent(e)
  }

  def commandReady: Receive = {
    case command : CommandMessage => println(s"AssemblyActor received and acted on command: $command")
      hcdActor ! command

    case e : EventMessage => handleEvent(e)
  }

  private def handleEvent(eventMessage: EventMessage) = {
    println(s"AssemblyActor received and acted on event: $eventMessage")
  }
}

object AssemblyActor {
  def props() = Props(new AssemblyActor)
}