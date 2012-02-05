package com.kotancode.scalamud

import akka.actor._
import akka.routing._

import java.net._
import java.io._

import com.kotancode.scalamud.core.Implicits._
import com.kotancode.scalamud.core.Player
import com.kotancode.scalamud.core.NewSocket
import com.kotancode.scalamud.core.TextMessage

import com.kotancode.scalamud.areas.root._

case class ServerStart
case class PlayerLoggedIn

/*
 * The GameServer actor deals with accepting inbound socket
 * connections and delegates the I/O loop to the Player
 * actor
 */
class GameServer extends Actor {
	private var allPlayers = List[ActorRef]()
	private val rootArea = context.actorOf(Props(new RootArea), "areas-root")
	
	
	def receive = {
		case s:ServerStart => {	
			context.actorOf(Props(new Actor {
				def receive = {
					case ss:ServerStart => {						
						rootArea ! AreaStart
						startSocketListener
					}
				}
			})) ! s
		}
		case PlayerLoggedIn => {
			handlePlayerLogin(sender)
		}
	}
	
	private def handlePlayerLogin(player:ActorRef) = {
		println("Player logged in: " + player)
		allPlayers ::= player
		// TODO: Used to be able to do this by 'cheating'
		// need to do this without cheating
		// 		for (p <- allPlayers if p.name != player.name) {
		for (p: ActorRef <- allPlayers) {
			p ! TextMessage(player.name + " logged in.")
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