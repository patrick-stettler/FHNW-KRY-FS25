# Programmieraufgabe: Substitutionspermultationsnetzwerk (SPN)

Gegeben sei das SPN mit folgenden Parametern:

Anzahl der Runden: r = 4  
Anzahl der Bits pro Subblock: n = 4  
Anzahl der Subblocks pro Block: m = 4  
Schlüssel: k = 0011 1010 1001 0100 1101 0110 0011 1111
Schlüssellänge in Bits: s = 32  

Rundenschlüssel:
- i = 0: die 16 aufeinanderfolgenden Bits ab Position 4 x 0 = 0
- i = 1: die 16 aufeinanderfolgenden Bits ab Position 4 x 1 = 4
- i = 2: die 16 aufeinanderfolgenden Bits ab Position 4 x 2 = 8
- i = 3: die 16 aufeinanderfolgenden Bits ab Position 4 x 3 = 12
- i = 4: die 16 aufeinanderfolgenden Bits ab Position 4 x 4 = 16

S-Box:
![S-Box](sbox.png)

Aus Darstellungsgründen ist in der Tabelle die Hexadezimalschreibweise verwendet worden.  
Der Bitstring 0010 entspricht etwa 2 und wird auf D abgebildet, was 1101 entspricht.

Bitpermutation:
![Bitpermutation](bitpermutation.png)

Alice und Bob verwenden obiges SPN im CTR-Modus mit dem Schlüssel 0011 1010 1001 0100 1101 0110 0011 1111 um sich verschlüsselte Texte zuzusenden. Dabei wird ein Text zunächst ASCII kodiert. An den zugehörigen Bitstring wird eine 1 drangehängt und dann so viele Nullen, bis die Gesamtlänge des Bitstrings durch 16 teilbar ist. Dieser resultierende Bitstring wird dann im CTR-Modus verschlüsselt.

Bob empfängt den folgenden Chiffretext - wie lautet die zugehörige Nachricht?
00000100110100100000101110111000000000101000111110001110011111110110000001010001010000111010000000010011011001110010101110110000


### Hinweise
- Schreiben Sie ein kommentiertes Java-Programm.
- Zum Testen ihrer Ver- bzw. Entschlüsselungsroutine des SPNs können Sie verwenden, dass x = 0001 0010 1000 1111 mit dem Schlüssel k = 0001 0001 0010 1000 1000 1100 0000 0000 zu y = 1010 1110 1011 0100 verschlüsselt wird (mit dem SPN).
- Beachten Sie, dass es sich um Bonuspunkte handelt. Damit können sich interessierte Studierende durch Zusatzarbeit einen kleinen Bonus verdienen. Eigentlich gehe ich davon aus, dass Sie aus Fairnessgründen diesen Studierenden gegenüber nicht versuchen, zu betrügen. Dennoch werde ich dies (auch mit Hilfe von Tools) kontrollieren. Falls dabei ein Täuschungsversuch festgestellt wird (also: (verschleierte) Kopien von Teilen existierender Programme), wird die Note des nächsten Tests auf 1.0 gesetzt.


### Abgabe
Datum: 23.03.2025  
Schicken Sie die entschlüsselte Nachricht in ihrer Mail mit!
