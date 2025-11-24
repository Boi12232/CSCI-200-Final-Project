/**
 * 
 *
 */

import java.util.*;
public class Graph<T>{
  
//----------------------- Inner edge class -----------------------
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
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
      if (this == obj)
      {
        return true;
      }
      if (obj == null || getClass() != obj.getClass())
      {
        return false;
      }
      Edge other = (Edge) obj;
      return this.destination.equals(other.destination);
    }
    @Override
    public int hashCode() {
      return destination.hashCode();
    }
  }
//----------------------------------------------------------------
  
  //T is nodes 
  private Map<T, Set<Edge>> adj = new HashMap<>();
  //This connects the nodes to its adjacent nodes and nodes that it can reach in the graph
  //the set contains all unique edges from T to the edge + its weight
  private boolean directed;
  
  //Graph constructor
  public Graph(boolean directed) {
    this.directed = directed;
  }
  
  /**
   * Method to add vertex
   */
  public void addVertex(T v) {
    adj.putIfAbsent(v, new HashSet<>());
  }
  
  /**
   * Method to add an edge
   */
  public void addEdge(T src, T dest, double weight) {
    addVertex(src); //add nodes in graph
    addVertex(dest); 
    // Remove existing edge if it exists (so we can update weight)
    adj.get(src).remove(new Edge(dest, 0));//weight ignored
    adj.get(src).add(new Edge(dest, weight));
    if (!directed) {
      adj.get(dest).remove(new Edge(src, weight));
      adj.get(dest).add(new Edge(src, weight));
    }
  }
  
  /**
   * Removes an an edge to a specific Location
   * @param loc represents the location that has an edge to the location we want to remove
   * @param removedLoc represents the location that will be removed
   */
  public void removeEdge(T loc , T removedLoc){
    Set<Edge> setOfEdgesInLoc= adj.get(loc);
    
    for(Edge edge : setOfEdgesInLoc)
    {
      
      if(edge.destination.equals(removedLoc)){
        setOfEdgesInLoc.remove(edge);
      }
      
    }
  }
  
  /**
   * Removes a vertex from graph
   * @param loc 
   */
  public void removeVertex(T removedVertex){
    adj.remove(removedVertex);
    System.out.println("1, " + adj);
  }
  
  
  
  /**
   * Method to get node neighbors
   */
  public Set<Edge> getNeighbors(T v) {
    //Check if this vertex exists in the map
    Set<Edge> neighbors = adj.get(v); //gets the edges that key v is connected to
    if (neighbors == null) {
      neighbors = new HashSet<>();//empty set
    }
    return neighbors;
  }
  
  /**
   * Method to print the graph
   */
  public void printGraph() {
    for (T v : adj.keySet()) {
      System.out.println(v + " → " + adj.get(v));
    }
  }
}
