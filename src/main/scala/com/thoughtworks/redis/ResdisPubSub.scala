package com.thoughtworks.redis

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import redis.RedisClient
import redis.actors.RedisSubscriberActor
import redis.api.pubsub.{Message, PMessage}

class RedisPublisher(implicit val actorSystem:ActorSystem) {
  val redis = RedisClient("127.0.0.1", 6379)
  def publish(channel:String, value:String): Unit = {
    redis.publish(channel, value)
  }
}

class RedisSubscriber(channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
  extends RedisSubscriberActor(
    new InetSocketAddress("localhost", 6379),
    channels,
    patterns,
    onConnectStatus = connected => {
      println(s"connected: $connected")
    }
  ) {

  def onMessage(message: Message) {
    println(s"Redis message received: $message")
  }

  def onPMessage(pmessage: PMessage) {
    println(s"pattern message received: $pmessage")
  }
}
