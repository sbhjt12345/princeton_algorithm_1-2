
public class Outcast {
	private final WordNet wordnet;

	public Outcast(WordNet wordnet){
		// constructor takes a WordNet object
		if (wordnet==null) throw new IllegalArgumentException();
		this.wordnet = wordnet;
	}

	public String outcast(String[] nouns){
		// given an array of WordNet nouns, return an outcast
		if(nouns==null || nouns.length<2) throw new IllegalArgumentException();
		int maxDis = Integer.MIN_VALUE;
		String outcastStr = null;
		for (int i=0;i<nouns.length;i++){
			int curDis = 0;
			for (int j=0;j<nouns.length;j++){
				if (i==j) continue;
				int tmpDis = wordnet.distance(nouns[i], nouns[j]);
				curDis += tmpDis;
			}
			if (curDis > maxDis){
				maxDis = curDis;
				outcastStr = nouns[i];
			}
		}
		return outcastStr;
	}


	public static void main(String[] args){
		// see test client below
	}

}
