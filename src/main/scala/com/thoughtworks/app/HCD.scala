package com.thoughtworks.app

import java.util.concurrent.Executors

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import com.thoughtworks.hcd.SupervisorHCDActor
import com.thoughtworks.redis.RedisPublisher
import com.thoughtworks.zmq.ZMQSubscriber
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

object HCD extends App{

  private implicit val actorSystem = ActorSystem("ActorSystem-HCD",ConfigFactory.load())
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher

  val hcdSupervisorActorRef: ActorRef = actorSystem.actorOf(SupervisorHCDActor.props(), "Supervisor-HCD")

  startHardwareEventSubscriber

  private def startHardwareEventSubscriber() = {
    val ec = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())
    Future {
      val redisPublisher = new RedisPublisher()
      val zmqSubscriber = new ZMQSubscriber()
      zmqSubscriber.stream
        .runForeach { message =>
          redisPublisher.publish("hcd-status", message)
          println(s"Publishing $message to Redis")
        }.onComplete { x =>
        zmqSubscriber.shutdown()
      }
    }(ec)
  }
}
