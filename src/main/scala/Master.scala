package com.kotancode.scalamud

import akka.actor._
import akka.routing._

/*
 * Root-level Game singleton
 * Creates the Akka Actor System for all subsequent game objects
 */
object Game extends App {
	val system = ActorSystem("ScalaMUD")
	val server = system.actorOf(Props(new GameServer()), "server")
	println("System Starting")
	server ! ServerStart()	
}