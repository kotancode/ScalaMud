package com.kotancode.scalamud.areas.root.rooms

import com.kotancode.scalamud.core._
import akka.actor._
import akka.routing._
import com.kotancode.scalamud.core.Implicits._

class TheVoid extends Room {

	self.description = "You are in the void"
	
	self.name = "The Void"
}