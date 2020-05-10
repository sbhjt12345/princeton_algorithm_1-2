import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	private final In synset, hypernum;
	private ST<String, LinkedBag<Integer>> nounSets;
	private ST<Integer, String> synsetIDs;
	private Digraph digraph;




	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms){
		synset = new In(synsets);
		hypernum = new In(hypernyms);
		nounSets = new ST<>();
		synsetIDs = new ST<>();
		while (synset.hasNextLine()){
			String[] line = synset.readLine().split(",");
			for (String noun : line[1].split(" ")){
				LinkedBag<Integer> ids = new LinkedBag<>();
				if (nounSets.contains(noun)){
					ids = nounSets.get(noun);
					ids.add(Integer.parseInt(line[0]));
				}
				nounSets.put(noun, ids);
			}
			synsetIDs.put(Integer.parseInt(line[0]), line[1]);
		}
		
		



	}

	// returns all WordNet nouns
	public Iterable<String> nouns(){

	}

	// is the word a WordNet noun?
	public boolean isNoun(String word){

	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){

	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		
	}

	// do unit testing of this class
	public static void main(String[] args)
}