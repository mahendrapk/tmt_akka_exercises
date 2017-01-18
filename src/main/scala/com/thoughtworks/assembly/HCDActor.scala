package com.thoughtworks.assembly

import akka.actor.{Actor, ActorLogging, Props}
import com.thoughtworks.common.{CommandMessage, EventMessage}

private class AssemblyActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case CommandMessage(message) => println(s"AssemblyActor received and acted on command: $message")
    case EventMessage(message) =>  println(s"AssemblyActor received and acted on event: $message")
  }
}

object AssemblyActor {
  def props() = Props(new AssemblyActor)
}
