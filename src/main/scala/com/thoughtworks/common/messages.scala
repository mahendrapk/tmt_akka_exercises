package com.thoughtworks.common

trait Command
trait Event

final case class CommandMessage(val message: String) extends Command;
final case class EventMessage(val message: String) extends Event;