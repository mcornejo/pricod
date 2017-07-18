package com.example

/**
  * Created by mcornejo on 25/05/16.
  */

//A server class that it is instantiated using an encrypted database
case class Server(encrytedDB: Set[String]) {

  // The method that computes the intersection of the sets.
  // Very handy that the method is called "intersect" (Scala's Set class)
  def computeMatches(labels: Set[String]): Set[String] = encrytedDB.intersect(labels)

}
