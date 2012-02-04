package com.kotancode.scalamud.core

import com.kotancode.scalamud._

import akka.actor._
import akka.routing._

case class Move(to:ActorRef)

abstract class GameObject extends Actor {
	
	def receive = handleMessages
	
	def handleMessages: Receive = {
		case Move(to) => {
			println("Moving to " + to)
		}
	}
}