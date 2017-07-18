# Introduction

Two users want to know which phone numbers they have in common, 
but they are very conscious that sharing their contact lists is a
very bad idea. The users actually want to compute the intersection of
private sets while neither should learn anything more that the intersection 
itself. In here we present a solution in order to achieve Private Set Intersection
(PSI) based on Symmetric Searchable Encryption (SSE) taking into consideration a mobile setup.

Private Contact Discovery A typical use case of PSI is a contact discovery
protocol. This is the case of applications with social interactions. After
creating an account, the user needs to send its contact list to the server, so
that the server can identify which users are already enrolled to linked them.
When the contact discovery is Friend-to-Friend, then it is a little trickier
since a malicious user can manipulate her list of friends by populating it
with a large amount of phone numbers in order to maximize the amount of
information learned.

# Intuition
This protocol is run by (at least) two parties Alice and Bob and
one server Claude. The main idea is to run a simplified symmetric searchable 
encryption twice: one for each user. First Alice creates the encrypted
database of her phone numbers and sends it to Claude who stores it. The
database is a hash table containing an identifier for every phone number.
Later, Bob requests a secret key from Alice in order to create one identifier
 for each phone number that he has in his contact list. Then Bob sends his
identifiers to Claude who computes the search using the encrypted database

#Code
The prototype was written in Scala using Activator (SBT) as a build
tool. Since the protocol is so simple, this could have been coded in any
language. For a real implementation a native code is preferred (C-style) to
profit of the hardware optimisations. The structure of the code is simple:
There is a class User and a class `Server` which are instantiated with different
settings in the `Test` class.
To run a small demo of the code, type:

```` bash
$ cd pricod
$ sbt run
```

For the tests:
```` bash
$ cd pricod
$ sbt test
```
