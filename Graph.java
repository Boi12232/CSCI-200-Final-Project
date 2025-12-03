import java.util.*;

public class Graph<T> {
  // -------------- inner edge class ------------
  class Edge {
    T destination;
    double weight;
    
    Edge(T dest, double w) {
      destination = dest;
      weight = w;
    }
  // -------------- inner edge class ------------
    @Override
    public String toString() {
      return "(" + destination + ", " + weight + ")";
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Edge other = (Edge) obj;
      return this.destination.equals(other.destination);
    }
    
    @Override
    public int hashCode() {
      return destination.hashCode();
    }
  }
  
  private Map<T, Set<Edge>> adj = new HashMap<>();
  private boolean directed;
  
  public Graph(boolean directed) {
    this.directed = directed;
  }
  
  public void addVertex(T v) {
    adj.putIfAbsent(v, new HashSet<>());
  }
  
  public void addEdge(T src, T dest, double weight) {
    addVertex(src);
    addVertex(dest);
    adj.get(src).remove(new Edge(dest, 0));
    adj.get(src).add(new Edge(dest, weight));
    if (!directed) {
      adj.get(dest).remove(new Edge(src, weight));
      adj.get(dest).add(new Edge(src, weight));
    }
  }
  
  public Set<Edge> getNeighbors(T v) {
    return adj.getOrDefault(v, new HashSet<>());
  }
  
  public boolean edgeExists(T src, T dest) {
    Set<Edge> n = adj.get(src);
    if (n == null) return false;
    return n.contains(new Edge(dest, 0));
  }
  
  public Set<T> getVertices() {
    return adj.keySet();
  }
  
  public void printGraph() {
    for (T v : adj.keySet()) {
      System.out.println(v + " → " + adj.get(v));
    }
  }
}
