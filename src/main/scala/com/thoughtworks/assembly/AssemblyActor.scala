package com.thoughtworks.assembly

import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorRef, ActorSelection, Stash, Identify, Props}
import com.thoughtworks.common.{CommandMessage, EventMessage}

private class AssemblyActor extends Actor with ActorLogging with Stash{

  val actorSelection: ActorSelection = context.actorSelection("akka.tcp://ActorSystem-HCD@127.0.0.1:2552/user/Supervisor-HCD")
  println(actorSelection)

  var hcdActor: ActorRef = _

  override def preStart(): Unit = {
    actorSelection ! Identify(1)
  }

  override def receive: Receive =  {

    case ActorIdentity(_,Some(actorRef)) => {
      println("HCD Actor Identified")
      hcdActor = actorRef
      context.become(commandReady)
      unstashAll()
    }

    case command : CommandMessage =>
      println(s"AssemblyActor received and stashed command: $command")
      stash()

    case e : EventMessage =>
      println("Not yet command ready")
      handleEvent(e)
  }

  def commandReady: Receive = {

    case command : CommandMessage => println(s"AssemblyActor received and acted on command: $command")
      hcdActor ! command

    case e : EventMessage => println("command ready")
      handleEvent(e)
  }

  private def handleEvent(eventMessage: EventMessage) = {
    println(s"AssemblyActor received and acted on event: $eventMessage")
  }
}

object AssemblyActor {
  def props() = Props(new AssemblyActor)
}