import com.example.{Server, User}
import org.scalatest._

import scala.util.Random

class HelloSpec extends FlatSpec with Matchers {
  "Small data set (10 contacts)" should "work" in {

    val commonContacts = (for (i <- 1 to 4) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts), 10)
    val bob = new User(Some(commonContacts), 10)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }

  "Average data set (5,000 contacts) with no contacts in common" should "work" in {


    val alice = new User(None,5000)
    val bob = new User(None,5000)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (0)
    bob.decryptContacts(s2_results).size should === (0)
  }

  "Average data set (5,000 contacts) with a very small common list (4 contacts)" should "work" in {

    val commonContacts = (for (i <- 1 to 4) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts),5000)
    val bob = new User(Some(commonContacts),5000)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (4)
    bob.decryptContacts(s2_results).size should === (4)
    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }


  "Average data set (5,000 contacts) with a high common list (4900 contacts)" should "also work" in {

    val commonContacts = (for (i <- 1 to 4900) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts),100)
    val bob = new User(Some(commonContacts),100)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (4900)
    bob.decryptContacts(s2_results).size should === (4900)
    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }

  "Large data set (5,0000 contacts) with a small common list (3 contacts)" should "also work" in {

    val commonContacts = (for (i <- 1 to 3) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts),50000)
    val bob = new User(Some(commonContacts),50000)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (3)
    bob.decryptContacts(s2_results).size should === (3)
    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }


  "Large data set (5,0000 contacts) with a high common list (40000 contacts)" should "also work" in {

    val commonContacts = (for (i <- 1 to 40000) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts),10000)
    val bob = new User(Some(commonContacts),10000)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (40000)
    bob.decryptContacts(s2_results).size should === (40000)
    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }

  /*

   // The half-a-million test is commented because it takes some time in execute. In my computer it takes around 1 minute, 2 seconds.


  "XXL data set (half million of contacts) with a high common list (400,000 contacts)" should "also work" in {

    val commonContacts = (for (i <- 1 to 400000) yield BigInt(50, Random).toString).toList
    val alice = new User(Some(commonContacts),100000)
    val bob = new User(Some(commonContacts),100000)

    val s1 = new Server(alice.encryptedContacts)
    val bob_labels = bob.computeLabels(alice.key)
    val server_result = s1.computeMatches(bob_labels)

    val s2 = new Server(bob.encryptedContacts)
    val alice_labels = alice.computeLabels(bob.key)
    val s2_results = s2.computeMatches(alice_labels)

    alice.decryptContacts(server_result).size should === (400000)
    bob.decryptContacts(s2_results).size should === (400000)
    alice.decryptContacts(server_result) should === (bob.decryptContacts(s2_results))
  }

*/

}
