package com.thoughtworks.app

import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}

import com.thoughtworks.zmq.ZMQPublisher

object HardwareSimulator extends App{

  val scheduleTimer = new ScheduledThreadPoolExecutor(1)
  val publisher = new ZMQPublisher()

  val task = new Runnable() {
    override def run(): Unit = {
      println("Publishing message")
      publisher.publish("Hello");
    }
  }

  val f = scheduleTimer.scheduleAtFixedRate(task,5,2,TimeUnit.SECONDS)
}


