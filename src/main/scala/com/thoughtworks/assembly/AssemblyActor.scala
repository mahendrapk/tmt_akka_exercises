package com.thoughtworks.assembly

import java.util.concurrent

import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorRef, ActorSelection, Identify, Props, Stash}
import com.thoughtworks.common.{CommandMessage, EventMessage, ResolveHCDReference}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global;

private class AssemblyActor extends Actor with ActorLogging with Stash{

  val actorSelection: ActorSelection = context.actorSelection("akka.tcp://ActorSystem-HCD@127.0.0.1:2552/user/Supervisor-HCD")
  println(actorSelection)

  var hcdActor: ActorRef = _

  override def preStart(): Unit = {
    context.system.scheduler.scheduleOnce(500 milliseconds, self, ResolveHCDReference)
    println("message scheduled")
    actorSelection ! Identify(1)
  }

  override def receive: Receive =  {

    case ActorIdentity(_,Some(actorRef)) => {
      println("HCD Actor Identified")
      hcdActor = actorRef
      context.become(commandReady)
      unstashAll()
    }

    case ResolveHCDReference => {
      actorSelection ! Identify(1)
      context.system.scheduler.scheduleOnce(500 milliseconds, self, ResolveHCDReference)
      println("message scheduled again")
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