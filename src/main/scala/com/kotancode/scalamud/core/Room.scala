package com.kotancode.scalamud.core

import akka.actor._
import akka.routing._
import com.kotancode.scalamud.core.Implicits._
import com.kotancode.scalamud.core.cmd.AddCommand
import com.kotancode.scalamud.core.cmd.RemoveCommand
import com.kotancode.scalamud.core.cmd.EnrichedCommand
import com.kotancode.scalamud.Game

case class RoomMessage(text:String)

class Room extends Container { 
	
//	protected var exits = Seq( (Set("a","b"), "c") )
	protected var exits: Seq[(Set[String], String, String)] = _
	
	override def handleMessages = super.handleMessages orElse handleRoomMessages
	
	def handleRoomMessages:Receive = {
		case RoomMessage(text) => {
			log.info("Got a room message {}", text)
		}
		
		case cmd:EnrichedCommand if exits.map( { case (dirset, actorpath, msg) => dirset }).flatten.contains(cmd.firstVerb) => {
			handleExit(cmd.firstVerb, cmd.issuer)
		}
	}
	
	override def entered_inv(ob: ActorRef, from: ActorRef) = {
		exits foreach ( { case (dirset, actorpath, msg)  => {
			ob ! AddCommand(dirset, self)
		} } )
		
		entered_room(ob, from)
	}
	
	override def left_inv(ob:ActorRef, to: ActorRef) = {
		exits foreach ( { case (dirset, actorpath, msg)  => {
			ob ! RemoveCommand(dirset.head)
		} } )
		left_room(ob, to)
	}
	
	def entered_room(ob:ActorRef, from:ActorRef) = {
		ob ! TextMessage(self.description + ".")
	}
	
	def left_room(ob:ActorRef, to:ActorRef) = {
		
	}
	
	def handleExit(exitName: String, ob: ActorRef) = {
		val target = exits.find( { case(dirset,path,msg) => dirset.contains(exitName) } ) match {
				case Some( (dirset, path, msg) ) => (path, msg)
				case None => ("", "")
			} 
		log.info("Player [{}] wanted to go in direction of akka://ScalaMUD/{}", ob.name, target._1)
		ob ! TextMessage(target._2)
		ob ! Move(Game.system.actorFor("akka://ScalaMUD/" + target._1))
	}
}