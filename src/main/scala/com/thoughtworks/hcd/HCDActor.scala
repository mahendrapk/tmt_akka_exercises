package com.thoughtworks.hcd

import akka.actor.{Actor, ActorLogging, Props}
import com.thoughtworks.common.CommandMessage

private class HCDActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case CommandMessage(message) => println(s"HCDActor received and acted on command: $message")
  }
}

object HCDActor{
  def props() = Props(new HCDActor)
}
