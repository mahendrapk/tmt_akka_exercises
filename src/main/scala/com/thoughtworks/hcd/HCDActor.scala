package com.thoughtworks.hcd

import akka.actor.{Actor, ActorLogging, Props}
import com.thoughtworks.common.CommandMessage
import com.thoughtworks.hcd.HCDActor.{FatalError, MajorError, MinorError}

private class HCDActor extends Actor with ActorLogging{

  var history: String = ""

  override def receive: Receive = {
    case CommandMessage(counter) => {
      println(s"HCDActor received command: $counter")
      println(s"History: $history")
      history += counter + ", "

      if(counter == 3.toString) {
        println(s"Throwing MinorError")
        throw new MinorError
      }
      else if(counter == 5.toString) {
        println(s"Throwing MajorError")
        throw new MajorError
      }
      else if(counter == 9.toString) {
        println(s"Throwing FatalError")
        throw new FatalError
      }
      else
        println(s"Handled message: $counter")
    }
  }
}

object HCDActor{
  def props() = Props(new HCDActor)
  class MinorError extends Exception("MajorError")
  class MajorError extends Exception("MinorError")
  class FatalError extends Exception("FatalError")
}
