package com.kotancode.scalamud.core.cmd

import com.kotancode.scalamud.core.ServerStats
import com.kotancode.scalamud.core.TextMessage
import com.kotancode.scalamud.Game
import com.kotancode.scalamud.core.Implicits._
import akka.actor._
import akka.routing._

abstract class CommandLibMessage
case class AttachCommandLib extends CommandLibMessage

class MortalCommandLib extends Actor {
	def receive = handleMortalCommands
	
	def handleMortalCommands: Receive = {
		case cmd:EnrichedCommand if cmd.firstVerb == "who" => {
			handleWho(cmd.issuer)
		}
		
		case cmd:EnrichedCommand if cmd.firstVerb == "look" => {
			handleLook(cmd.issuer)
		}
		
		case AttachCommandLib => {
			attachToSender(sender)
		}
	}
	
	def handleWho(issuer: ActorRef) = {
		println("player "+ issuer.name + " typed who.")
		var stringOut = "Players logged in:\n"
		for (p: ActorRef <- Game.server.inventory) {
			stringOut += p.name + "\n"
		}
		issuer ! TextMessage(stringOut)
	}
	
	def handleLook(issuer:ActorRef) = {
		var stringOut = issuer.environment.description + "\n"
		stringOut += issuer.environment.inventory.map(_.name).mkString(", ")
		issuer ! TextMessage(stringOut)
	}
	
	def attachToSender(sender:ActorRef) = {
		sender ! AddCommand(Set("who"), self)
		sender ! AddCommand(Set("look", "l"), self)
	}
	
	
}