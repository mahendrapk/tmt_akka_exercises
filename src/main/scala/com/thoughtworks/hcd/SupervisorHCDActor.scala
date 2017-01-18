package com.thoughtworks.hcd

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection, Props}

class SupervisorHCDActor extends Actor with ActorLogging{

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    println(s"SupervisorHCDActor::preStart")
    val hcdActorRef: ActorRef = context.actorOf(HCDActor.props(), "HCD1")
  }

  override def receive: Receive = {
    case command => {
      println(s"SupervisorHCDActor received command $command")
      val hcdActorSelection: ActorSelection = context.actorSelection("HCD1")
      hcdActorSelection ! command
    }
  }
}

object SupervisorHCDActor{
  def props() = Props(new SupervisorHCDActor)
}