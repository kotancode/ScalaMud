package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._

case class RoomMessage(text:String)

class Room extends Container { 
	
	override def handleMessages = super.handleMessages orElse handleRoomMessages
	
	def handleRoomMessages:Receive = {
		case RoomMessage(text) => {
			println("Got a room message: " + text)
		}
	}
}