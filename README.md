# RSA encryption & decrytion

These programs all use the `java.math.BigInteger` library to store the numbers of these keys.

## Key Generator

### `rsaGenerator.java`

This file is to generate a RSA encyption key, which includes:
  - `P` & `Q`, the 2 large prime numbers
  - `n`, which equals `P * Q`
  - `z`, which equals `(P - 1) * (Q - 1)`
  - `D`, a coprime of `z`
  - `E`, the multiplicative modular inverse of `D` (where `z % (E * D) = 1`)

By default, this file will generate keys using 40 bit primes, `D` will also be 40 bits by default.  
You may edit the code below `main` to specify the size of the primes, same with `D` under `getCoprime`.  

```java
public static void main(String[] arg){
	BigInteger P = BigInteger.probablePrime(40, rand);
	BigInteger Q = BigInteger.probablePrime(40, rand);
	...
```
  
```java
public static BigInteger getCoprime(BigInteger zCopy){
        BigInteger ans = new BigInteger("2");
        ans = ans.add(BigInteger.probablePrime(40, rand));
	...
```

## Key Crackers

These program will:
  1. prompt you for a case#, input a random number, this is related to `ResultWriter.java`
  1. ask you for `n`
  1. ask you for `E`
  1. ask you for the cypher (message encypted using this key)

It should then, given enough times, output all values of this key & also decrypt the cypher.

### `rsaCracker_countFromStart.java`

This program utilizes the simple method checking if any number counting down from root(n) can divide n without leaving a remainder. There are minor optimizations, like skipping non-prime numbers (even numbers or numbers that end in "0" or "5").

### `rsaCracker_memoized.java`

Within the "primes" folder contains 4550 txt files of prime numbers up to 10 billion. This program utilizes this folder & therefore does not need to calculate prime numbers at runtime. Though this program can crack keys quite fast, it is limited to cracking keys using prime numbers no larger than 10 billion (~30 bits).

### `rsaCracker_nextProbPrime.java`

This program is inverse of rsaCracker_countFromStart.java. Rather than count down from root(n), the program counts up from 2. The program is much easier to read as it simply just utilizes the `BigInteger` library function, `nextProbablePrime()` to check for prime numbers.

### `rsaCracker_perfectSquares.java`

This program utilizes an algorithm showcased on the YouTube channel: [Numberphile](https://youtu.be/-ShwJqAalOk). Rather than checking for primes per iteration, the algorithm checks for perfect squares. This algorithm is much faster than one that check for primes. This algorithm is also especially fast for RSA keys whose prime numbers are relatively "close" to one another.

### `ResultWriter.java`

This program contains 1 function used by the key cracking program. The method is used to quickly dump the contents of a cracked key into a txt file. This program was used for a hacking contest. 

## Others

Just some files for testing or double checking my results.