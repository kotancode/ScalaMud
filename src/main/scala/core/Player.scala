package com.kotancode.scalamud.core

import com.kotancode.scalamud._

import akka.actor._
import akka.routing._

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
		case s:NewSocket => {
			context.actorOf(Props(new Actor {
				def receive = {
					case NewSocket(socket) => {
						setupSocket(socket.getInputStream(), socket.getOutputStream())						
					}
				}})) ! s
		}
		case TextMessage(message) => {
			outWriter.println(message)
			outWriter.flush()
		}		
	}
	
	private def setupSocket(in: BufferedReader, out: PrintWriter) {
	   inReader = in
	   outWriter = out
	
	   val is: InputStream = classOf[Player].getResourceAsStream("/welcome.txt")
	   val source = scala.io.Source.fromInputStream(is)

	   out.println(source.mkString)
	   out.print("Login: ")
	   out.flush()
	   name = in.readLine();
	   out.println("Welcome to ScalaMUD, " + name)
	   out.flush()
	   println("Player logged in: "+ self)
	   Game.server ! PlayerLoggedIn(this)
	   while (true) {
		   val line = inReader.readLine()
		   outWriter.println(name + ": " + line)
		   outWriter.flush()
	   }	
	 }
}