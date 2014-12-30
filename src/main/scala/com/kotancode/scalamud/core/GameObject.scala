package com.kotancode.scalamud.core

import com.kotancode.scalamud._

import akka.actor._
import akka.routing._
import com.kotancode.scalamud.core.Implicits._

sealed abstract class GameObjectMessage
case class Move(to:ActorRef) extends GameObjectMessage
case class Say(issuer:ActorRef, text:String) extends GameObjectMessage

abstract class GameObject extends Actor {
	
	def receive = handleMessages
	
	def handleMessages: Receive = {
		case Move(to) => {
			println("Moving to " + to)
			to ! EnterInventory(self.environment)
			self.environment = to
		}
		case Say(issuer, text) => {
			issuer.environment.inventory.filter( ob => ob.path != issuer.path ).foreach( ob => {
				ob ! TextMessage(issuer.name + " says: " + text)
			})
		}
	}
}