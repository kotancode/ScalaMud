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
	
	def description:String = StateStore.getState(actorRef.path.toString)("description").asInstanceOf[String]
	def description_=(value:String) { StateStore.setState(actorRef.path.toString, "description", value) }
	
	def environment:ActorRef = StateStore.getState(actorRef.path.toString)("environment").asInstanceOf[ActorRef]
	def environment_=(value:ActorRef) { StateStore.setState(actorRef.path.toString, "environment", value) }
	
	def inventory_=(value:HashSet[ActorRef]) { StateStore.setState(actorRef.path.toString, "inventory", value)}
	def inventory:HashSet[ActorRef] = {
		val stateMap = StateStore.getState(actorRef.path.toString)
		if (!stateMap.contains("inventory")) {
			val inv = new HashSet[ActorRef]
			stateMap.put("inventory", inv)
		}
		stateMap("inventory").asInstanceOf[HashSet[ActorRef]]
	}
}

