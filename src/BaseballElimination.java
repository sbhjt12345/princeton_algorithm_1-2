import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	private final int teamNum;
	private final List<String> teams;
	private final Map<String, Integer> teamMap;
	private final Map<Integer, String> teamMapRev;
	private final int[] w, l, r;
	private final int[][] g;
	public BaseballElimination(String filename){
		// create a baseball division from given filename in format specified below
		In in  = new In(filename);
		String[] input = in.readAllLines();
		teamNum = Integer.parseInt(input[0].trim());
		teams = new ArrayList<>();
		teamMap = new HashMap<>();
		teamMapRev = new HashMap<>();
		w = new int[teamNum];
		l = new int[teamNum];
		r = new int[teamNum];
		g = new int[teamNum][teamNum];
		for (int i=1; i<input.length;i++){
			String[] teamStat = input[i].trim().split(" ");
			int j = 0;
			for (String stat : teamStat){
				if (!stat.equals("")){
					if (j==0) {
						teams.add(stat);
						teamMap.put(stat,i-1);
						teamMapRev.put(i-1, stat);
					}
					else if (j==1) w[i-1] = Integer.parseInt(stat);
					else if (j==2) l[i-1] = Integer.parseInt(stat);
					else if (j==3) r[i-1] = Integer.parseInt(stat);
					else {
						g[i-1][j-4] = Integer.parseInt(stat);
					}
					j++;
				}
			}

		}		
	}
	public int numberOfTeams(){
		// number of teams
		return teamNum;
	}

	public Iterable<String> teams(){
		// all teams
		return teams;
	}
	public int wins(String team){
		// number of wins for given team
		this.validate(team);
		return w[this.teamMap.get(team)];
	}

	public int losses(String team){
		// number of losses for given team
		this.validate(team);
		return l[this.teamMap.get(team)];
	}
	public int remaining(String team){
		// number of remaining games for given team
		this.validate(team);
		return r[this.teamMap.get(team)];
	}
	public int against(String team1, String team2){
		// number of remaining games between team1 and team2
		this.validate(team1);
		this.validate(team2);
		return g[this.teamMap.get(team1)][this.teamMap.get(team2)];
	}
	public boolean isEliminated(String team){
		// is given team eliminated?
		this.validate(team);
		return this.certificateOfElimination(team) != null;
	}


	public Iterable<String> certificateOfElimination(String team){
		// subset R of teams that eliminates given team; null if not eliminated
		this.validate(team);
		Set<String> res = new HashSet<>();
		int v = this.numberOfTeams() - 2;
		int numG = (v+1)*v/2;
		int index = this.teamMap.get(team);
		int[] gToV = new int[this.numberOfTeams()];   // the order of team vertices
		for (int i=0;i<this.numberOfTeams();i++){
			if (i < index){
				gToV[i] = numG + i + 1;
			}
			if (i > index){
				gToV[i]= numG + i;
			}
		}
		FlowNetwork fn = new FlowNetwork(numG+this.numberOfTeams()+1);
		int countGm = 1;
		// first buildGameE
		for (int i=0;i<this.numberOfTeams();i++){
			for (int j=i+1;j<this.numberOfTeams();j++){
				if (i==index) continue;
				if (j==index) continue;
				int capacity = g[i][j];
				FlowEdge from = new FlowEdge(0,countGm,capacity);
				fn.addEdge(from);
				FlowEdge to1 = new FlowEdge(countGm, gToV[i], Integer.MAX_VALUE);
				FlowEdge to2 = new FlowEdge(countGm, gToV[j], Integer.MAX_VALUE);
				fn.addEdge(to1);
				fn.addEdge(to2);
				countGm++;
			}
		}
		// next build teamE
		int teamMaxWin = this.wins(team) + this.remaining(team); 
		for (int i=0;i<this.numberOfTeams()-1;i++){
			if (i==index) continue;
			int capacity = teamMaxWin - this.w[i];
			FlowEdge fe = new FlowEdge(gToV[i], numG+this.numberOfTeams(), capacity>0?capacity:0);
			fn.addEdge(fe);
		}
		FordFulkerson ff = new FordFulkerson(fn, 0, numG+this.numberOfTeams());
		int vCounter = 1;
		for (int i=0;i<this.numberOfTeams();i++){
			for (int j=i+1;j<this.numberOfTeams();j++){
				if (i==index) continue;
				if (j==index) continue;
				if (ff.inCut(vCounter)){
					res.add(this.teamMapRev.get(i));
				}
				vCounter++;
			}
		}
		return res.isEmpty()?null:res;
	}

	private void validate(Object o){
		if (o==null) throw new IllegalArgumentException();
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			}
			else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}
