package week6;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	private final ST<String, LinkedBag<Integer>> nounSets;
	private final ST<Integer, String> synsetIDs;
	private Digraph digraph;
	private final SAP sap;



	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms){
		if (synsets==null || hypernyms==null) throw new IllegalArgumentException();
		In synset = new In(synsets);
		In hypernym = new In(hypernyms);
		nounSets = new ST<>();
		synsetIDs = new ST<>();
		
		while (synset.hasNextLine()){
			String[] line = synset.readLine().split(",");
			for (String noun : line[1].split(" ")){
				LinkedBag<Integer> ids = new LinkedBag<>();
				if (nounSets.contains(noun)){
					ids = nounSets.get(noun);	
				}
				ids.add(Integer.parseInt(line[0]));
				nounSets.put(noun, ids);
			}
			synsetIDs.put(Integer.parseInt(line[0]), line[1]);
		}
		
		digraph = new Digraph(synsetIDs.size());
		while (hypernym.hasNextLine()){
			String[] line = hypernym.readLine().split(",");
//			if (line.length<=1) throw new IllegalArgumentException();
			for (int i=1;i<line.length;i++){
				digraph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
			}
		}
		if (!isDAG()) throw new IllegalArgumentException();
		sap = new SAP(digraph);
	}
	
	private boolean isDAG(){
		int rootCount = 0;
		for (int v = 0; v < digraph.V(); v++){
			if (!digraph.adj(v).iterator().hasNext()) rootCount++;
		}
		if (rootCount > 1) return false;
		DirectedCycle cd = new DirectedCycle(digraph);
		if (cd.hasCycle()) return false;
		return true;
	}
	

	// returns all WordNet nouns
	public Iterable<String> nouns(){
		return nounSets;

	}

	// is the word a WordNet noun?
	public boolean isNoun(String word){
		if (word==null) throw new IllegalArgumentException();
		return nounSets.contains(word);

	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new IllegalArgumentException();
		return sap.length(nounSets.get(nounA), nounSets.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		if (nounA == null || nounB == null) throw new IllegalArgumentException();
		return synsetIDs.get(sap.ancestor(nounSets.get(nounA), nounSets.get(nounB)));
	}

	// do unit testing of this class
	public static void main(String[] args){
//		String sy = args[0], hy = args[1];
//		WordNet wn = new WordNet(sy, hy);
//		System.out.println(wn.distance("a","b"));
//		System.out.println("shabi");
	}
}