package com.example

/**
  * Created by mcornejo on 25/05/16.
  */
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

// Some utils
object Utils {

  // A function to compute the time taken for a function to compute
  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }


  private val HMAC_SHA1_ALGORITHM = "HmacSHA1"

  // Just map the array into a string formatter.
  private def toHexString(bytes: Array[Byte]): String = bytes.map(b => f"$b%02x").mkString

  // The PRF. For this Proof of Concept SHA1 is enough.
  def calculateRFC2104HMAC(data: String, key: String): String = {
    val signingKey = new SecretKeySpec(key.getBytes, HMAC_SHA1_ALGORITHM)
    val mac = Mac.getInstance(HMAC_SHA1_ALGORITHM)
    mac.init(signingKey)
    toHexString(mac.doFinal(data.getBytes))
  }
}


