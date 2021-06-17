# SecureVoting
In this project, we implemented the Secure Election Protocol with two central facilities. The implementation provides a secure way for people to vote electronically, which eliminates the hassle of physically being present at designated election locations. Since computerized voting will not replace general elections unless there is a protocol that both maintains individual privacy and prevents cheating, the ideal protocol must meet these
requirements:
\n
● Only authorized voters can vote before the election ends.
● No one can vote more than once.
● No one can determine for whom anyone else voted.
● No one can duplicate anyone else's votes.
● Every voter can make sure that his vote has been taken into account in the final tabulation.
● Everyone knows who voted and who didn't
This project design uses two central facilities: Central Tabulating Facility (CTF) and Central
Legitimization Agency (CLA). CLA's main function is to certify the voters. Each voter will send a
message to the CLA asking for a validation number, and CLA will return a random validation
number to the user. The CLA retains a list of validation numbers as well as a list of validation
numbers' recipients to prevent a voter from voting twice. Then, the CLA sends the same number
the CTF. After a voter gets the validation number from CLA, the voter sends his/her vote and the
validation number to CTF. CTF's main function is to count votes. CTF checks the validation
number against a list of numbers received from the CLA. If the validation number is there, the CTF
crosses it out (to prevent someone from voting twice). The CTF adds the identification number to
the list of people who voted for a particular candidate and adds one to the tally. After the election
ends, the CTF publishes the outcome.
All the data sent between a voter and CLA, between CLA and CTF, and between a voter and CTF is
encrypted. For simplicity, RSA is used for encryption and decryption. Suppose each party involved
in the protocol has a public key/private key pair. All voters know the public keys of CTF and CLA.
CTF keeps a list of eligible users and their public keys in a text file. CTF and CLA know the public
key of each other.
