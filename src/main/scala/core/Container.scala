package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._

case class EnterInventory

class Container extends GameObject {
	override def handleMessages = {
		super.handleMessages orElse handleContainerMessages
	}
	
	def handleContainerMessages:Receive = {
		case EnterInventory => {
			println(sender + " entered my inventory.")
		}
	}
}