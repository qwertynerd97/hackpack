package hackpack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Dinic {
	static final double eps = 1e-7;
	static final long linf = (long) 1e18;
	static final int iinf = (int) 1e9;
	static final double dinf = Double.MAX_VALUE;

	ArrayList<Edge>[] g;
	ArrayList<Edge> edges;
	ArrayList<Edge> cedges;
	int[] dist;
	boolean[] blocked;
	boolean hasFlow;
	double[] demands;

	public Dinic(int n) {
		g = new ArrayList[n];
		edges = new ArrayList<>();
		cedges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
		}
		demands = new double[n];
		blocked = new boolean[n];
		dist = new int[n];
		hasFlow = false;
	}

	public Edge addEdge(int u, int v, double cap) {
		Edge f = new Edge(edges.size(), u, v, cap);
		g[u].add(f);
		edges.add(f);

		Edge b = new Edge(edges.size(), v, u, 0);
		g[v].add(b);
		edges.add(b);

		f.reverse = b;
		b.reverse = f;

		return f;
	}

	public Edge addEdge(int u, int v, double cap, double min) {
		if (min > cap && !eq(min, cap)) {
			System.err.println("addEdge: flipping max and min " + cap + " " + min);
			double tmp = cap;
			cap = min;
			min = tmp;
		}
		demands[u] += min;
		demands[v] -= min;
		Edge e = addEdge(u, v, cap - min);
		e.bonusFlow += min;
		return e;
	}

	public void addCirculationEdges(int s, int t) {
		for (int i = 0; i < demands.length; i++) {
			if (eq(demands[i], 0))
				continue;
			Edge e = null;
			if (demands[i] > 0) {
				e = addEdge(i, t, demands[i]);
			} else {
				e = addEdge(s, i, -demands[i]);
			}
			if (e != null) {
				cedges.add(e);
			}
		}
	}

	public void addDemand(int n, double demand) {
		demands[n] += demand;
	}

	public void addSupply(int n, double supply) {
		demands[n] -= supply;
	}
	
	public boolean bfs(int s, int t) {
		Arrays.fill(dist, iinf);
		ArrayDeque<Integer> bfs = new ArrayDeque<Integer>();
		bfs.offer(t);
		dist[t] = 0;
		while (!bfs.isEmpty()) {
			int c = bfs.poll();
			for (Edge e : g[c]) {
				if (dist[e.v] != iinf)
					continue;
				Edge r = e.reverse;
				if (eq(r.flow, r.cap))
					continue;
				dist[e.v] = dist[c] + 1;
				bfs.offer(e.v);
			}
		}
		return dist[s] != iinf;
	}

	public double dfs(int s, int t, int c, double cap) {
		if (c == t || cap == 0)
			return cap;
		double flow = 0;
		for (Edge e : g[c]) {
			if (dist[e.v] != dist[e.u] - 1)
				continue;
			if (blocked[e.v])
				continue;
			if (eq(e.flow, e.cap))
				continue;
			double pushed = dfs(s, t, e.v, Math.min(cap - flow, e.cap - e.flow));
			flow += pushed;
			e.flow += pushed;
			e.reverse.flow -= pushed;
			if (eq(flow, cap))
				break;
		}
		if (!eq(flow, cap))
			blocked[c] = true;
		return flow;
	}

	public double pushFlow(int s, int t) {
		resetFlow();
		hasFlow = true;
		double flow = 0;
		while (bfs(s, t)) {
			Arrays.fill(blocked, false);
			flow += dfs(s, t, s, dinf);
		}
		for (Edge e : cedges) {
			if (!eq(e.flow, e.cap)) {
				return -1;
			}
			if (e.u == s)
				flow -= e.flow;
		}

		return flow;
	}

	public void resetFlow() {
		if (hasFlow)
			for (Edge e : edges) {
				e.flow = 0;
			}
		hasFlow = false;
	}

	static boolean eq(double a, double b) {
		return Math.abs(a - b) <= eps;
	}
	
	class Edge {
		int u, v;
		double cap, flow;
		double bonusFlow;
		Edge reverse;
		
		public Edge(int id, int u, int v, double cap) {
			this.u = u;
			this.v = v;
			this.cap = cap;
			this.flow = 0;
		}

		public double getTotalFlow() {
			return this.flow + this.bonusFlow;
		}
	}
}