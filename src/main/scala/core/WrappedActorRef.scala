package com.kotancode.scalamud.core

import akka.actor.ActorRef
import scala.collection.mutable.{Map,
    SynchronizedMap, HashMap}

object Implicits {
	implicit def enrichActorRef(value: ActorRef) = new WrappedActorRef(value)
}

/*
 * Singleton containing a hash map keyed on the Actor Ref path
 * which contains additional hash maps.
 * There is one hash map for every actorref that needs state
 */
object StateMap {
	
   // Creates a thread-safe hash map
   def makeMap: Map[String, HashMap[String,String]] = {
       new HashMap[String, HashMap[String,String]] with
           SynchronizedMap[String, HashMap[String,String]]
   }

   private var stateMap = makeMap

   def getState(key:String):HashMap[String,String] = {
   	 if (!stateMap.contains(key))
	     stateMap.put(key, new HashMap[String,String])
	 stateMap(key)
   }

   def setState(key:String, propertyName:String, value:String) = {
		var state: HashMap[String,String] = null
		val result = stateMap.get(key)          
		result match {
			case Some(x) => state = x
			case None => state = new HashMap[String,String]
		}
		state.put(propertyName, value)
		stateMap.put(key, state)
   }
}

class WrappedActorRef(val actorRef: ActorRef) {
	
	def name:String = StateMap.getState(actorRef.path.toString)("name")
	def name_=(value:String) { StateMap.setState(actorRef.path.toString, "name", value) }
}

