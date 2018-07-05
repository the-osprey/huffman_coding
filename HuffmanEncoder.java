import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character, Integer> freqTable = new HashMap<>();
        for (char c : inputSymbols) {
//            int count = freqTable.get(c);
            if (freqTable.get(c) == null) {
                freqTable.put(c, 1);
            } else {
                freqTable.put(c, freqTable.get(c) + 1);
            }
        }
        return freqTable;
    }

    public static void main(String[] args) {
        // Read file as 8bit symbols
        char[] fileReadOutput = FileUtils.readFile(args[0]);

        // Build Frequency Tables
        Map<Character, Integer> freqTable = buildFrequencyTable(fileReadOutput);

        // Construct binary decoding trie
        BinaryTrie decodingTrie = new BinaryTrie(freqTable);

        // Write trie to .huf file
        // NOTE: The order the trie, len and seq are written in MATTERS for decode!
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(decodingTrie);

        // Write number of objects to the .huf file
        ow.writeObject(fileReadOutput.length);

        // create lookup table
        Map<Character, BitSequence> lookupTable = decodingTrie.buildLookupTable();

        // List of bitsequences
        LinkedList<BitSequence> bitSequence = new LinkedList<>();

        // for each 8 bit symbol
        for (char c : fileReadOutput) {
            BitSequence seq = lookupTable.get(c);
            bitSequence.add(seq);
        }

        //Assemble all bit sequences into one long seq
        BitSequence totalSequence = BitSequence.assemble(bitSequence);

        // write whole sequence into .huf file
        ow.writeObject(totalSequence);
    }
}
