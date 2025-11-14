import java.util.*;
public class Graph<T> {
  //T is nodes 
  private Map<T, Set<Edge>> adj = new HashMap<>();
  //This connects the nodes to its adjacent nodes and nodes that it can reach in the graph
  //the set contains all unique edges from T to the edge + its weight
  private boolean directed;
  
  public Graph(boolean directed) {
    this.directed = directed;
  }
  
  public void addVertex(T v) {
    adj.putIfAbsent(v, new HashSet<>());
  }
  
  public void addEdge(T src, T dest, double weight) {
    addVertex(src); //add nodes in graph
    addVertex(dest); 
    // Remove existing edge if it exists (so we can update weight)
    adj.get(src).remove(new Edge(dest, 0));//weight ignored
    adj.get(src).add(new Edge(dest, weight));
    if (!directed) {
      adj.get(dest).remove(new Edge(src, weight));
      adj.get(dest).add(new Edge(src, weight));
      
      //updates weight
    }
  }
  
  public Set<Edge> getNeighbors(T v) {
// Check if this vertex exists in the map
    Set<Edge> neighbors = adj.get(v);
    if (neighbors == null) {
      neighbors = new HashSet<>();//empty set
    }
    return neighbors;
  }
  
  public void printGraph() {
    for (T v : adj.keySet()) {
      System.out.println(v + " → " + adj.get(v));
    }
  }
  
  class Edge {
  T destination;
  double weight;
  Edge(T dest, double w) {
    destination = dest;
    weight = w;
  }
  public String toString() {
    return "(" + destination + ", " + weight + ")";
  }
//  @Override
//  public boolean equals(Object obj) {
//    if (!(obj instanceof Edge)) return false;
//    Edge other = (Edge) obj;
//    return this.destination.equals(other.destination);
//  }
  @Override
  public int hashCode() {
    return destination.hashCode();
  }
}

}