import java.util.*;

public class Graph<T> {
  // -------------- inner edge class ------------
  class Edge implements Comparable<Edge> {
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
    
    @Override
    public int compareTo(Edge other){
      
      
      return Double.compare(this.weight, other.weight);
      
      
      
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
  
  /**
   * 
   */
  public T removeVertex(T v) {
    if(adj.containsKey(v)){
      adj.remove(v);
      return v;
    }
    else{
      return null;
    }
  }
  
  /**
   * @param T node at which all weights to it will be updated
   */
  public void updateWeights(T chosenNode){
    
    Set<Edge> neighborsOfChosenNode = getNeighbors(chosenNode);
    if(chosenNode == null){
      System.out.println("Invalid Region");
    }
    else
    {
      for(Edge edge: neighborsOfChosenNode){
        System.out.println(edge.destination + " " + edge.weight);
        edge.weight = edge.weight + 1;
        System.out.println(edge.destination + " " + edge.weight);
      }
    }
  }
  
  /**
   * Gets a list of weights at a specific node for testing
   */
  public List<String> getWeights(T chosenNode){
    
    List<String> listOfWeightsAt = new ArrayList<String>();
    Set<Edge> neighborsOfChosenNode = getNeighbors(chosenNode);
    
    if(chosenNode == null)
    {
      System.out.println("Invalid Region");
      return listOfWeightsAt;
    }
    for(Edge edge: neighborsOfChosenNode)
    {
      listOfWeightsAt.add(Double.toString(edge.weight));
    }
    
    return listOfWeightsAt;
  }

  
  
  
  public Set<Edge> getNeighbors(T v) {
    //Check if this vertex exists in the map
    Set<Edge> neighbors = adj.get(v); //gets the edges that key v is connected to
    if (neighbors == null) {
      neighbors = new HashSet<>();//empty set
    }
    return neighbors;
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
  
  public List<T> traverseEntireGraph(T start){
    List<T> order = new ArrayList<T>();
    if(!adj.containsKey(start)){ //there is no such vertex
      return order;
    }
    Set<T> visited = new HashSet<T>(); //checks what was visited to prevent cycles
    Deque<T> stack = new ArrayDeque<T>(); 
    stack.push(start); //pushes the starting vertex
    while(!stack.isEmpty()){
      T v = stack.pop();
      if(!visited.contains(v)){
        visited.add(v);
        order.add(v);
        
        for(Edge e: getNeighbors(v)){//for each neighbor of vertex v, check
          if(!visited.contains(e.destination)){
            stack.push(e.destination);
          }
          
          
        }
      }
    }
    return order;
  }
  
  public List<T> traverseFrom(T start){
    List<T> orderUntilSafe = new ArrayList<T>();
    BiomeRegion startv2 = (BiomeRegion) start;
    
    // TODO: Implement BFS traversal
    if(!adj.containsKey(start) || startv2.biome.getEncounterChance() == 0 ){ //there is no such vertex
      return orderUntilSafe;
    }
    Set<T> visited = new HashSet<T>(); //checks what was visited to prevent cycles
    Deque<T> queue = new ArrayDeque<>(); 
    queue.add(start); //pushes the starting vertex
    while(!queue.isEmpty()){
      T v = queue.remove();
      BiomeRegion v2 = (BiomeRegion) v;
      if(!visited.contains(v)){
        visited.add(v);
        orderUntilSafe.add(v);
        
        for(Edge e: getNeighbors(v)){//for each neighbor of vertex v, check
          if(!visited.contains(e.destination)){
            queue.add(e.destination);
          }
          
          if(v2.biome.getEncounterChance() == 0){
            return orderUntilSafe;
          }
          
        }
      }
    }
    
    return orderUntilSafe;
  }
  
  public List<T> traverseUsingLeast(T start, T end){
    Map<T, Double> dist = new HashMap<T, Double>();
    ArrayList<T> shortestListToEnd = new ArrayList<T>();
    Map<T,T> path = new HashMap<T, T>(); //record what came previously for reference //object and object before it
    
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
      if(current.weight <= dist.get(v)){ //if the current weight is less than the one that has been applied
        for(Edge e: getNeighbors(v)){ //for each of the vertex's neighbors
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
