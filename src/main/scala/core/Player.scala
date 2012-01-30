package com.kotancode.scalamud.core

import com.kotancode.scalamud._

import akka.actor.{Actor, PoisonPill}
import Actor._
import akka.routing.{Routing, CyclicIterator}
import Routing._

import java.net._
import java.io._

case class NewSocket(socket: Socket)
case class TextMessage(message:String)

class Player extends Actor {
	var name:String = "newbsauce"
	private var inReader: BufferedReader = null
	private var outWriter: PrintWriter = null
	
 	implicit def inputStreamWrapper(in: InputStream) =
  		new BufferedReader(new InputStreamReader(in))

 	implicit def outputStreamWrapper(out: OutputStream) =
  		new PrintWriter(new OutputStreamWriter(out))
	
	def receive = {
		case NewSocket(socket) => {
			spawn {
				setupSocket(socket.getInputStream(), socket.getOutputStream())
				}
		}
		case TextMessage(message) => {
			outWriter.println(message)
			outWriter.flush()
		}		
	}
	
	private def setupSocket(in: BufferedReader, out: PrintWriter) {
	   inReader = in
	   outWriter = out
	
	   out.println("Who be ye?")
	   out.flush()
	   name = in.readLine();
	   out.println("Welcome to the Echo Server, " + name)
	   out.flush()
	   println("Player logged in: " + self + " : " + name)
	   Game.server ! PlayerLoggedIn(this)
	   while (true) {
	       val line = in.readLine()
	       out.println(name + ": " + line)
	       out.flush()
	  }
	 }
}