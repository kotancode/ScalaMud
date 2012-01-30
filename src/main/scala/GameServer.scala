package com.kotancode.scalamud

import akka.actor.{Actor, PoisonPill,ActorRef}
import Actor._

import java.net._
import java.io._

import com.kotancode.scalamud.core.Player
import com.kotancode.scalamud.core.NewSocket
import com.kotancode.scalamud.core.TextMessage

case object ServerStart
case class PlayerLoggedIn(player:Player)

class GameServer extends Actor {
	private var allPlayers = List[Player]()
	
	def receive = {
		case ServerStart => {
			startSocketListener
		}
		case PlayerLoggedIn(player) => {
			handlePlayerLogin(player)
		}
	}
	
	private def handlePlayerLogin(player:Player) = {
		println("Player logged in: " + player)
		allPlayers ::= player
		allPlayers.foreach( p=> {
			if (p.name != player.name) {
				p.self ! TextMessage(player.name + " logged in.")
			}
		})
	}
	
	private def startSocketListener = {
		spawn {
			val serverSocket = new ServerSocket(8888)

	  		while(true) {
	   				println("Awaiting connection...")
   					val clientSocket = serverSocket.accept()
	   				val player = actorOf(new Player()).start()
	   				player ! NewSocket(clientSocket)
	   				println("Spun off echo handler for player")
	  			}
		}
	 }
}