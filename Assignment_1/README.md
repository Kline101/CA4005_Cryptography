
# Assignment1 - Symmetric File Encryption Using Password and Salt
 ##    Declaration of Non-Plagiarism:                                           
 A declaration that this is solely my own work (except elements that are explicitly attributed to another source). 

## Overview
Code for CA4005 Assignment 1. 
It performs several cryptographic operations, including password-based key derivation, AES encryption, and RSA encryption.
The code generates essential cryptographic parameters like random salts and initialization vectors (IVs) and reads and encrypts a Java class file ("Assignment1.class") using AES in CBC mode.

## Key Details
- **Geoff's Key (GK):** This is a constant string representing a cryptographic key.
- **AES Encryption:** The code employs the Advanced Encryption Standard with a 128-bit key, CBC mode, and no padding.
- **RSA Encryption:** It encrypts a user-defined password using RSA.
- **File Output:** The results are written to various output files, such as Salt.txt, IV.txt, Password.txt, Password_raw.txt, and Encryption.txt.

## How to Run
To compile and run the code, follow these steps:
1. **Compile:** Use the `javac` command to compile the code:
   ```shell
   javac Assignment1.java
   ```

2. **Run:** Execute the code with the following command:
   ```shell
   java Assignment1
   ```

3. **Run and Write to Encryption.txt:** To run and write the output to a file (Encryption.txt)
   ```shell
   java Assignment1 Assignment1.class > Encryption.txt
   ```

4. **Cleaning Up:** If needed, you can remove the generated files using the following command:
   ```shell
   rm Encryption.txt IV.txt Password_raw.txt Password.txt Salt.txt Assignment1.class
   ```

## References
- CA4005 Study Notes Week 3: Symmetric Cryptography
- [SimpliLearn - StringBuilder in Java](https://www.simplilearn.com/tutorials/java-tutorial/stringbuilder-in-java)
- [Java AES Cryptography Example | Java Encryption Decryption Example | JCA and JCE Example](https://www.youtube.com/watch?v=a3ZdcDvUwdY)

## Updates
- **26/10/2023:** Added the `bytesToHex` method.
- **27/10/2023:** Fixed the AES issue related to spaces.
- **28/10/2023:** Verified that the `bytesToHex` method is working.
- **30/10/2023:** Implemented code commenting, verified the output, and conducted refactoring for code enhancement.