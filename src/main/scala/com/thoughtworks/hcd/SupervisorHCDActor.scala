package com.thoughtworks.hcd

import akka.actor.Actor.Receive
import akka.actor.SupervisorStrategy.{Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, OneForOneStrategy, Props, SupervisorStrategy}
import com.thoughtworks.common.CommandMessage
import com.thoughtworks.hcd.HCDActor.{FatalError, MajorError, MinorError}

class SupervisorHCDActor extends Actor with ActorLogging{

  var hcdActorRef: ActorRef = _

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case _: MinorError =>
      println("Minor Error occurred. Resuming HCD")
      Resume
    case _: MajorError =>
      println("Major Error occurred. Restarting HCD")
      Restart
    case _ =>
      println("Fatal Error occurred. Stopping HCD")
      Stop
  }

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    println(s"SupervisorHCDActor::preStart")
    hcdActorRef = context.actorOf(HCDActor.props(), "HCD1")
  }

  override def receive: Receive = {
    case command: CommandMessage => {
      println(s"SupervisorHCDActor received command $command")
      hcdActorRef ! command
    }

    case _ => println(s"Invalid command")
  }
}

object SupervisorHCDActor{
  def props() = Props(new SupervisorHCDActor)
}