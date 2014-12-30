package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._
import com.kotancode.scalamud.core.cmd._

case object MobileMessage1

class Mobile extends Container with Commandable { 
	
	override def handleMessages = super.handleMessages orElse handleCommandMessages orElse handleMobileMessages
	
	def handleMobileMessages:Receive = {
		case MobileMessage1 => {
			println("NOOP")
		}
	}
	
	override def entered_inv(ob: ActorRef, from: ActorRef) = {
		
	}
	
	override def left_inv(ob:ActorRef, to: ActorRef) = {
		
	}
}