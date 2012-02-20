package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._
import com.kotancode.scalamud.core.Implicits._

case class EnterInventory(from:ActorRef)
case class LeaveInventory(to:ActorRef)

class Container extends GameObject {
	override def handleMessages = {
		super.handleMessages orElse handleContainerMessages
	}
	
	def handleContainerMessages:Receive = {
		case EnterInventory(from) => {
			println(sender + " entered my inventory from " + from)
			self.inventory += sender
		}
		
		case LeaveInventory(to) => {
			println(sender + " left my inventory, heading to " + to)
			self.inventory -= sender
		}
	}
}