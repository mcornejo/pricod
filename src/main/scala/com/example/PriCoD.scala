package com.example

import scala.util.Random

object PriCoD {
  def main(args: Array[String]): Unit = {

    /*
     * This part is the small "visual testing". A small dataset, just to print
     * something on the screen, and verify that everything is working.
     *
     * The 'real' tests are in the  test folder:
     * /src/test/scala/HelloSpec
     **/


    // Create 4 random contacts of up to 16 digits.
    val commonContacts = (for (i <- 1 to 4) yield BigInt(50, Random).toString).toList

    // Create the contact list of 10 people + the common contacts just created.
    val alice = new User(Some(commonContacts), 10)
    val bob = new User(Some(commonContacts), 10)

    println("*******************************************************************")
    println("Alice's encrypted contact list")
    alice.encryptedContacts.foreach(println)

    println("*******************************************************************")
    println("Bob's encrypted contact list")
    bob.encryptedContacts.foreach(println)
    println("*******************************************************************")


    println("First Round (Alice's round):")

    val s1 = new Server(alice.encryptedContacts) // The server is seeded with Alice's encrypted contact list
    val bob_labels = bob.computeLabels(alice.key) //bob computes labels using alice's key
    val server_result = s1.computeMatches(bob_labels) //bob sends the labels to Claude.
    println("Server output:")
    server_result.foreach(println)


    println("*******************************************************************")


    println("Second Round (Bob's round):")

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)
    println("Server output:")
    s2_results.foreach(println)


    println("*******************************************************************")

    print("Alice's decryption: ")
    alice.decryptNPrintContacts(server_result) //Alice decrypts the values
    print("Bob's decryption:   ")
    bob.decryptNPrintContacts(s2_results)

    println("*******************************************************************")
  }
}