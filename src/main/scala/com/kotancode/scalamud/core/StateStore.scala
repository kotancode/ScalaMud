package com.kotancode.scalamud.core

import akka.actor.ActorRef
import scala.collection.mutable.{Map,
    SynchronizedMap, HashMap,HashSet}

/*
 * Singleton containing a hash map keyed on the Actor Ref path
 * which contains additional hash maps.
 * There is one hash map for every actorref that needs state
 */
object StateStore {
	
   // Creates a thread-safe hash map
   def makeMap: Map[String, HashMap[String,Any]] = {
       new HashMap[String, HashMap[String,Any]] with
           SynchronizedMap[String, HashMap[String,Any]]
   }

   private var stateMap = makeMap

   def getState(key:String):HashMap[String,Any] = {
    	var state:HashMap[String, Any] = null
		val result = stateMap.get(key)
		result match {
			case Some(x) => x
			case None => {
				val newMap = new HashMap[String, Any]
				stateMap.put(key, newMap)
				newMap
			}
		}
   }

   def setState(key:String, propertyName:String, value:Any) = {
		var state: HashMap[String,Any] = null
		val result = stateMap.get(key)          
		result match {
			case Some(x) => state = x
			case None => state = new HashMap[String,Any]
		}
		state.put(propertyName, value)
		stateMap.put(key, state)
   }
}
