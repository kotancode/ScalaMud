package com.kotancode.scalamud.areas.root

import com.kotancode.scalamud.core.Room
import com.kotancode.scalamud.core._
import com.kotancode.scalamud.areas.root.rooms._
import akka.actor._
import akka.routing._

case object AreaStart

class RootArea extends Actor {
  var theVoid:ActorRef = null
  var thePit:ActorRef = null
	
	def receive = {
		case AreaStart => {
			println("Root Area Started...")
			theVoid = context.actorOf(Props(new TheVoid()), "thevoid")			
      thePit = context.actorOf(Props(new ThePit()), "thepit")      
			
			theVoid ! RoomMessage("this is a room message")
			
		}
	}
}