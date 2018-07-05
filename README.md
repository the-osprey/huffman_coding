# huffman_coding

In this project, I built a huffman encoder and decoder. This involved implementing a special binary trie also known as a huffman trie as well. 

This is used to compress files into a saveable format which saves space. BinaryTrie.java does most of the lifting in this program. The constructor builds a huffman trie from a frequency table. HuffmanDecoder.java and HuffmanEncoder.java allow us to take in a text file and either compress a file into a filename.txt.huf file, or take this file and encode it back into a .txt file. 

To run, try the following:

java HuffmanDecoder watermelonsugar.txt.huf originalwatermelon.txt

this takes an encoded file, and then decodes it to regular text. More generally:

running "java HuffmanEncoder FILENAME.txt" returns an encoded FILENAME.txt.huf file

running "java HuffmanDecoder FILENAME.txt.huf NEWNAME.txt" decodes it. 
