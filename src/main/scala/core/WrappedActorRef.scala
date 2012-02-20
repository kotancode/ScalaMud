package com.kotancode.scalamud.core

import akka.actor.ActorRef
import scala.collection.mutable.{Map,
    SynchronizedMap, HashMap,HashSet}

object Implicits {
	implicit def enrichActorRef(value: ActorRef) = new WrappedActorRef(value)
}


class WrappedActorRef(val actorRef: ActorRef) {
	
	def name:String = StateStore.getState(actorRef.path.toString)("name").asInstanceOf[String]
	def name_=(value:String) { StateStore.setState(actorRef.path.toString, "name", value) }
	
	def inventory_=(value:HashSet[ActorRef]) { StateStore.setState(actorRef.path.toString, "inventory", value)}
	def inventory:HashSet[ActorRef] = {
		val stateMap = StateStore.getState(actorRef.path.toString)
		if (!stateMap.contains("inventory"))
			stateMap.put("inventory", new HashSet[ActorRef])
		stateMap("inventory").asInstanceOf[HashSet[ActorRef]]
	}
}

