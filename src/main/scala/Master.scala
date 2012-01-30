package com.kotancode.scalamud

import akka.actor.{Actor, PoisonPill}
import Actor._

object Game extends App {
	val server = actorOf(new GameServer).start()
	server ! ServerStart	
}