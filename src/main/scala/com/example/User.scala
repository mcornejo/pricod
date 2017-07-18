package com.example

import scala.util.Random

/**
  * Created by mcornejo on 25/05/16.
  */

// User class, that takes as input:
// 1) a list of contacts, otherwhise all off them will be random,
// 2) the size of the random contacts
// 3) the size of the secret key
case class User(complementaryUsers: Option[List[String]] = None, sizeContacts: Int = 5000, keySize: Int = 12 ) {

  private val bitSize = 50   // bitsize of each entry. 2^50 is a 16 decimal digit.

  // A helper function to generate random "contact" numbers
  private def generateContacts(qty: Int): List[String] = (for(i <- 1 to qty) yield BigInt(50, Random).toString).toList

  // A variable to store the partner Key
  private var partnerKey: String = ""
  val key: String = Random.alphanumeric.take(keySize).toString //key of size "12" bytes (96 bits).

  // generate a random list of 5000 number
  // If there is initial parameter, then it will append both lists. the randomly generated and the complementaryUsers.
  private val contacts: List[String] = generateContacts(sizeContacts) ++ complementaryUsers.getOrElse(List())

  // Let's store the contact list in a hashmap: Number -> EncryptedValue
  private val contactMap: Map[String, String] = contacts.map(c => (Utils.calculateRFC2104HMAC(c, key), c)).toMap

  // The encrypted contacts will be the values of the contact list hashmap.
  val encryptedContacts: Set[String] = contactMap.keySet


  // Method to compute the identifiers of contact list using the partner key
  def computeLabels(partnerK: String): Set[String] = {
    partnerKey = partnerK
    contacts.map(c => Utils.calculateRFC2104HMAC(c.toString, partnerKey)).toSet
  }

  // Method to filter the results that was sent by the server.
  def decryptContacts(encryptedContacts: Set[String]) = {
    val decryptedValues = contactMap.filterKeys(k => encryptedContacts.contains(k)).values.toList
    decryptedValues.sorted
  }

  // Same method than decryptContacts with a println to visualize
  def decryptNPrintContacts(encryptedContacts: Set[String]) = {
    val decryptedValues = contactMap.filterKeys(k => encryptedContacts.contains(k)).values.toList
    println(decryptedValues.sorted.mkString(" "))

    decryptedValues.sorted
  }

  def printContacts(): Unit = {
    contacts.foreach(println)
  }


}





