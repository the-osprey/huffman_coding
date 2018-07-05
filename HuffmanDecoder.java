public class HuffmanDecoder {

    public static void main(String[] args) {
        String fileReadOutput = args[0];

        // Create the Object Reader
        ObjectReader reader = new ObjectReader(fileReadOutput);

        // read the huffman coding trie (the first in file)
        BinaryTrie readTrie = (BinaryTrie) reader.readObject();

        // Read number of symbols (2nd item)
        int numSymbols = (Integer) reader.readObject();

        // Read bit sequence (3rd item)
        BitSequence bitSeq = (BitSequence) reader.readObject();

        // Data Structure to track symbols
        char[] symbols = new char[numSymbols];

        // For loop that does the following until there are no more symbols
        for (int i = 0; i < numSymbols; i++) {
            // Get the match object returned from finding longest sequence
            Match longestPrefix = readTrie.longestPrefixMatch(bitSeq);

            // Insert that symbol
            symbols[i] = longestPrefix.getSymbol();

            // Update our bit sequence to the rest that was not in longest sequence
            bitSeq = bitSeq.allButFirstNBits(longestPrefix.getSequence().length());
        }

        // Write symbols to specified file
        String newFileName = args[1];
        FileUtils.writeCharArray(newFileName, symbols);
    }
}
