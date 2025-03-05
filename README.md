# CrackMyPass
Interactive password cracking app to demonstrate the importance of strong passwords.

## Installation
You can clone this repository onto your machine using
`git clone https://github.com/uyuyuy99/CrackMyPass.git`

Alternatively download a zip file of the repository with this link:
[CrackMyPass](https://github.com/uyuyuy99/CrackMyPass/archive/refs/heads/main.zip)

Run using Java with your preferred IDE.

You can also download an executable version of the app [here](https://github.com/uyuyuy99/CrackMyPass/releases/download/release/CrackMyPass.zip) (contains the CrackMyPass executable and manual)

## Interface
The CrackMyPass runs a dictionary attack against a hash table of passwords and displays the passwords it cracks.

You can select a new dictionary to use for the attack from your file system using the **Load Dictionary** button.

You can select a new hash table to attack from your file system using the **Load Hashes** button. All hashes must be encrypted using the MD5 cryptographic hash algorithm.

Selecting **Prepend** allows you to see and edit a list of prefixes to add to dictionary words during an attack. A range of numbers can be specified.

Similarly, selecting **Append** allows you to see and edit a list of suffixes to add to dictionary words during an attack.

Selecting **Capitalize** allows you to set capitalization options to be tested during an attack.

Selecting **Replace** allows to to set character replacement options to be tested during an attack.

Begin a new attack using the **Start** button. End an attack using the **Stop** button.
