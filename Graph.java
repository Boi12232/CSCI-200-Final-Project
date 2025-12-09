/**
 * Shamin and Mai Ke Lor 
 * W/references to Heather's notes
 * Graph class: creates and contains the methods of the Graph object.
 * 12/7/2025
 */
import java.util.*;

public class Graph<T> {
  
  // -------------- inner edge class ------------
  class Edge implements Comparable<Edge> { //Edge Class
    
    /**
     * Attributres
     */
    T destination;
    double weight;
    
    
    /**
     * Constructor
     * 
     * @param dest represents the destination node this node goes to 
     * @param w represents the weight it takes from this node to the destination node
     */
    Edge(T dest, double w) {
      destination = dest;
      weight = w;
    }
    // -------------- inner edge class ------------
    
    /**
     * This method prints the edge information 
     * @return String of the edge's destination and weight
     */
    @Override
    public String toString() {
      return "(" + destination + ", " + weight + ")";
    }
    
    /**
     * this method determins if two edges are equal
     * @param obj represents the other edge or object to be compared to
     * @return boolean true if the two are equal; else false.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Edge other = (Edge) obj;
      return this.destination.equals(other.destination);
    }
    
    /**
     * This method makes a hashCode for the edge based on its destination.
     */
    @Override
    public int hashCode() {
      return destination.hashCode();
    }
    
    /**
     * This method compared this edge with another edge based on their weights
     * @param other represents the Edge we will compare with this edge.
     * @return int representing the result of the comparison.
     */
    @Override
    public int compareTo(Edge other){
      return Double.compare(this.weight, other.weight);
      
    }
  }
  
  //Atributes
  private Map<T, Set<Edge>> adj = new HashMap<>();
  private boolean directed;
  
  
  /**
   * Constructor 
   * @param directed represents if the graph is undirected or not (True for directed, False for undirected)
   */
  public Graph(boolean directed) {
    this.directed = directed;
  }
  
  /**
   * This method adds a vertex onto the Graph 
   * @param v represents the object we want to add in our graph
   */
  public void addVertex(T v) {
    adj.putIfAbsent(v, new HashSet<>());
  }
  
  /**
   * This method adds an edge between two nodes and the weight it takes to reach it. 
   * @param src represents the first node 
   * @param dest represents the second node
   * @param weight represents the encounterChance of meeting enemies from src to dest (The higher the weight, the more likely it is to meet enemies)
   */
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
  

//  public T removeVertex(T v) {
//    if(adj.containsKey(v)){
//      adj.remove(v);
//      return v;
//    }
//    else{
//      return null;
//    }
//  }
  
  /**
   * This node updates the weights going to a specific node
   * @param chosenNode at which all weights reaching it will be updated
   * @param amount represents the amount to change the weights by
   */
  public void updateWeights(T chosenNode, int amount){
    
    Set<Edge> neighborsOfChosenNode = getNeighbors(chosenNode);
    if(chosenNode == null){
      System.out.println("Invalid Region");
    }
    else
    {
      for(Edge edge: neighborsOfChosenNode){
        double weightb4 = edge.weight;
        
        edge.weight = Math.max(Math.round((edge.weight + amount)*1000.0)/1000.0,0);
        System.out.println(edge.destination + " " + weightb4 + " + " + amount + " = " + edge.weight);
      }
      
      for(Edge edge2: neighborsOfChosenNode){
        T dest = edge2.destination;
        Set<Edge> neighborsOfChosenNode2 = getNeighbors(dest);
        for(Edge edge3: neighborsOfChosenNode2){
          if(edge3.destination == chosenNode){
          edge3.weight = Math.max(Math.round((edge3.weight + amount)*1000.0)/1000.0,0);
          }
        }
      }
      
    }
  }
  
  /**
   * Gets a list of weights at a specific node for testing
   * @param chosenNode is the node we want to get its weights from 
   * @param toNode is the node we want to get its weights of
   * @return List of Strings of the updated weights at this node
   */
  public String getWeights(T chosenNode, T toNode){
    
    String listOfWeightsAt = "";
    Set<Edge> neighborsOfChosenNode = getNeighbors(chosenNode);
    
    if(chosenNode == null)
    {
      System.out.println("Invalid Region");
      return listOfWeightsAt;
    }
    
    for(Edge edge: neighborsOfChosenNode)
    {
      if(edge.destination == toNode){
        listOfWeightsAt = Double.toString(edge.weight);
      }
    }
    
    return listOfWeightsAt;
  }

  
  
  /**
   * This method gets the Neighbors at a specific node
   * @param v represents the vertex we want to get the neighbors of 
   * @return Set of the neighbots of v based on the edges it has
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
   * Checks if an edge between two nodes exist
   * @param src represents the first node
   * @param dest represents the second node
   * @return boolean true if the edge for two nodes exits; else false.
   */
  public boolean edgeExists(T src, T dest) {
    Set<Edge> n = adj.get(src);
    if (n == null) return false;
    return n.contains(new Edge(dest, 0));
  }
  
  /**
   * This method gets all the verticies by retuning the keys of adj
   * @return Set of all the keys (nodes) in adj
   */
  public Set<T> getVertices() {
    return adj.keySet();
  }
  
  /**
   * This method prints the graph of the keys and its connected destinations and weights
   */
  public void printGraph() {
    for (T v : adj.keySet()) {
      System.out.println(v + " → " + adj.get(v));
    }
  }
  
  /**
   * This method implements depth first Search Traversals and returns a List of the nodes it traversed through
   * @param start represents the object we started at
   * @param biomeName represents the biome we want to reach
   * @return List representing the order after the DFS traversal.
   */
  public List<T> traverseEntireGraph(T start, String biomeName){
    List<T> order = new ArrayList<T>();
    BiomeRegion startv2 = (BiomeRegion) start;
    if(!adj.containsKey(start) || startv2.getBiome().name == biomeName){
      return order;
    }
    
    Set<T> visited = new HashSet<T>(); //keeps track of visited nodes to prevent revisiting nodes
    Deque<T> stack = new ArrayDeque<T>(); 
    stack.push(start); //pushes the starting vertex
    while(!stack.isEmpty()){
      
      T v = stack.pop(); //while stack is not empty and object hasn't been visited, grab it's top and put it in order   
      BiomeRegion v2 = (BiomeRegion) v;
      if(!visited.contains(v)){
        visited.add(v);
        order.add(v);
        
        for(Edge e: getNeighbors(v)){//for each neighbor of vertex v, check if its been visited, it it hasn't push it to the stack
          if(!visited.contains(e.destination)){
            stack.push(e.destination);
          }
          
          if(v2.getBiome().name == biomeName){ //if the node we've reached is safe, then return bst order it takes to reach this safe node.
            return order;
          }
          
          
        }
      }
    }
    return order;
  }
  
  
    /**
   * This method implements depth Breath First Search traversal and stops once a safe node has been reached. BFS finds the closest safest region from our starting region.
   * @param start represents the object we started at
   * @return List representing the order after the BFS traversal to a safe node (indicated if the encounter chance is 0).
   */
  public List<T> traverseFrom(T start){
    List<T> orderUntilSafe = new ArrayList<T>();
    BiomeRegion startv2 = (BiomeRegion) start; //reference to BiomeRegion object
    
    // if start is not a valid object or we are already at a safe node, return an empty list
    if(!adj.containsKey(start) || startv2.getBiome().name == "Village" || startv2.getBiome().name == "Shelter"){ 
      return orderUntilSafe;
    }
    
    Set<T> visited = new HashSet<T>(); //keeps track of nodes visited
    Deque<T> queue = new ArrayDeque<>(); //bfs uses queues
    
    queue.add(start);
    
    while(!queue.isEmpty()){
      T v = queue.remove();
      BiomeRegion v2 = (BiomeRegion) v;
      if(!visited.contains(v)){
        visited.add(v);
        orderUntilSafe.add(v);
        
        for(Edge e: getNeighbors(v)){//for each neighbor of vertex v, check if its been visited, if it has not, add it to the queue.
          if(!visited.contains(e.destination)){
            queue.add(e.destination);
          }
          
          if(v2.biome.getEncounterChance() == 0){ //if the node we've reached is safe, then return bst order it takes to reach this safe node.
            return orderUntilSafe;
          }
          
        }
      }
    }
    
    return orderUntilSafe;
  }
  
   /**
   * This method implements Dijkstra traversals and returns a list that represents the safest path to a specified node from the user's current node. Prioritizes pathways with the least encounter chances!
   * @param start represents the object we started at
   * @param end represents the object we end at
   * @return List representing the smallest weighted path it takes to get from the start to the end, ensuring the user reaches a location with the least amount of encounter chances
   */
  public List<T> traverseUsingLeast(T start, T end){
    Map<T, Double> dist = new HashMap<T, Double>();
    ArrayList<T> shortestListToEnd = new ArrayList<T>();
    Map<T,T> path = new HashMap<T, T>(); //record what came previously for reference //<object and object before it>
    
    for(T v: getVertices()){
      dist.put(v,Double.POSITIVE_INFINITY);
      path.put(v,null);
    }
    dist.put(start,0.0);
    path.put(start,null); //the item before start is -1
    
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>(); //if you use a PriorityQueue you need a comparable method to compare
    pq.add(new Edge(start,0.0));
    
    while(!pq.isEmpty()){
      Edge current = pq.poll();
      T v = current.destination;
      if(current.weight <= dist.get(v)){ //if the current weight is less than the one that has been applied...
        for(Edge e: getNeighbors(v)){ //for each of the vertex's neighbors...
          double newDist = dist.get(v) + e.weight;
          if(newDist < dist.get(e.destination)){ //if the new destination of vertex and edge is less than the destination recorded to get to e, replace it with a new value.
            path.put(e.destination,v); //find the node right before e.destination
            dist.put(e.destination,newDist);
            pq.add(new Edge(e.destination, newDist));
          }
        }
      }
    }
    
    //This part makes a list of the quickest past to the desired end node after Dikj traversal
    List<T> toEnd = new ArrayList<T>();
    T found = end;
    while(found != null){
      toEnd.add(found);
      found = path.get(found);
      
    }
    
    Collections.reverse(toEnd);
    
    //System.out.println("1. " + path);
    return toEnd; //returns a map
  }
  
}
