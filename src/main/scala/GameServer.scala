package com.kotancode.scalamud

import akka.actor._
import akka.routing._

import java.net._
import java.io._

import com.kotancode.scalamud.core.Player
import com.kotancode.scalamud.core.NewSocket
import com.kotancode.scalamud.core.TextMessage

case class ServerStart
case class PlayerLoggedIn(player:Player)

/*
 * The GameServer actor deals with accepting inbound socket
 * connections and delegates the I/O loop to the Player
 * actor
 */
class GameServer extends Actor {
	private var allPlayers = List[Player]()
	
	def receive = {
		case s:ServerStart => {	
			context.actorOf(Props(new Actor {
				def receive = {
					case ss:ServerStart => {
						startSocketListener
					}
				}
			})) ! s
		}
		case PlayerLoggedIn(player) => {
			handlePlayerLogin(player)
		}
	}
	
	private def handlePlayerLogin(player:Player) = {
		println("Player logged in: " + player)
		allPlayers ::= player
		for (p <- allPlayers if p.name != player.name) {
			p.self ! TextMessage(player.name + " logged in.")
	    }
	}
	
	private def startSocketListener = {
			val serverSocket = new ServerSocket(8888)

	  		while(true) {
	   				println("Waiting for next connection...")
   					val clientSocket = serverSocket.accept()
	   				val player = context.actorOf(Props(new Player()))
	   				player ! NewSocket(clientSocket)	   		
	  			}
	 }
}