## RSA encryption & decrytion

### Key Generator

#### rsaGenerator.java

This file is to generate a RSA encyption key, which includes:
  - P & Q, the 2 large prime numbers
  - n, which equals P x Q
  - z, which equals (P - 1) x (Q - 1)
  - D, a coprime of z
  - E, the multiplicative modular inverse of D (where z % (E x D) = 1)

By default, this file will generate keys using 40 bit primes. You may edit the code & specify the size of primes you'd like.  

### Key Crackers

#### rsaCracker_countFromStart.java

This program utilizes the simple method checking if any number counting down from root(n) can divide n without leaving a remainder. There are minor optimizations, such as skipping even numbers & numbers that end in "0" or "5".

#### rsaCracker_memoized.java

Within the "primes" folder contains 4550 txt files of prime numbers up to 10 billion. This program utilizes this folder & therefore does not need to calculate prime numbers at runtime. Though this program can crack keys at lightning pace, it is limited to cracking keys using prime numbers no larger than 10 billion.

#### rsaCracker_nextProbPrime.java

This program is inverse of rsaCracker_countFromStart.java. Rather than count down from root(n), the program counts up from 2. The program is much easier to read as it simply just utilizes the BigInteger library function "nextProbablePrime()".

#### rsaCracker_perfectSquares.java

This program utilizes an algorithm showcased in an Numberphiles (youtube channel) video on Break RSA Encryption. Rather than checking for primes per iteration, the algorithm checks for perfect squares. This algorithm is much faster than one that check for primes. This algorithm is also especially fast for RSA keys whose prime numbers are relatively "close" to one another.

#### ResultWriter.java

this program was used to quickly dump a cracked key into a txt file. This program was used for a hacking contest. 

