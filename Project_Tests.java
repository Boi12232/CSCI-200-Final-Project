/**
 * Mai Ke Lor
 * 12/7/2025
 * Contains the tests for this project
 */
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Project_Tests{
  
  private Graph testWorld = new Graph(false);
  
  /**
   * -----------------------------------------------------------------------
   * Adding, removing, and modifying nodes and edges. 
   * Test that graph and graph methods are working is functional are working
   * -----------------------------------------------------------------------
   */
  
  @Test
  @SuppressWarnings("unchecked")
  public void addLocationTest(){
    Graph thisGraph = testWorld;
    
    thisGraph.addVertex("A");
    
    thisGraph.addVertex("B");
    
    thisGraph.addVertex("C");
    
    thisGraph.addVertex("D");
    Set<String> vertices = thisGraph.getVertices();
    
    assertEquals(4,vertices.size());
    
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void emptyGraphTest(){
    Graph thisGraph = testWorld;
    
    Set<String> vertices = thisGraph.getVertices();
    
    assertEquals(0,vertices.size());
    
  }
  
  /**
   * Tests if two nodes share the same weights in undirected Graph
   */
  @Test
  @SuppressWarnings("unchecked")
  public void undirectedEdgeTest(){
    Graph thisGraph = testWorld;
    
    thisGraph.addEdge("A","B",1.5);

    String expectedString1 = thisGraph.getWeights("A","B");
    
    String expectedString2 = thisGraph.getWeights("B","A");
    
    
    //System.out.println(expectedString1 +" " + expectedString2);
    assertEquals("Sees if edge connecting A and B are equal",expectedString1,expectedString2);
    
    thisGraph.addEdge("A", "C", 2.5);
    
   String expectedString3 = thisGraph.getWeights("A","C");
    
   String expectedString4 = thisGraph.getWeights("C","A");
    
   
    //System.out.println(expectedString3 +" " + expectedString4);
    assertEquals("Sees if edge connecting A and C are equal",expectedString3,expectedString4);
    
  }
  
  
  
  
  
  
  /**
   * Correctness of DFS, BFS, and Dijkstra’s results on small, known graphs.
   * Used with samples online
   */
  
  @Test
  @SuppressWarnings("unchecked")
  public void dfsTraversalTest(){
    Graph thisGraph = testWorld;
    Biome biome = new Biome("Villiage", 0, 0);
    
    
    BiomeRegion biome0 = new BiomeRegion("biome0",biome,false) ;
    BiomeRegion biome1 = new BiomeRegion("biome1",biome,false) ;
    BiomeRegion biome2 = new BiomeRegion("biome2",biome,false) ;
    BiomeRegion biome3 = new BiomeRegion("biome3",biome,false) ;
    BiomeRegion biome4 = new BiomeRegion("biome4",biome,false) ;
    BiomeRegion biome5 = new BiomeRegion("biome5",biome,false) ;
    
    thisGraph.addEdge(biome1, biome2,1.0);
    thisGraph.addEdge(biome1,biome3,1.0);
    thisGraph.addEdge(biome2,biome4,1.0);
    thisGraph.addEdge(biome2,biome5,1.0);
    
    List<BiomeRegion> expectedList = Arrays.asList(biome1,biome2,biome4,biome5,biome3);
    assertEquals("DFS traversals should be in the same order as expectedList",expectedList,thisGraph.traverseEntireGraph(biome1,""));
    //leaving second parameter as blank will just run through the entire dfs program as is
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void dfsTraversalThroughSingleGraphTest(){
    Graph thisGraph = testWorld;
    Biome biome = new Biome("Villiage", 0, 0);
    
    BiomeRegion biome0 = new BiomeRegion("biome0",biome,false);
    thisGraph.addVertex(biome0);
    
    List<BiomeRegion> expectedList = Arrays.asList(biome0);
    assertEquals("DFS traversals should be in the same order as expectedList",expectedList,thisGraph.traverseEntireGraph(biome0,""));
  }
  
  
  @Test
  @SuppressWarnings("unchecked")
  public void bfsTraversalTest(){
    Graph thisGraph = testWorld;
    Biome biome = new Biome("Villiage", 4, 4);
    
    
    BiomeRegion biome0 = new BiomeRegion("biome0",biome,false) ;
    BiomeRegion biome1 = new BiomeRegion("biome1",biome,false) ;
    BiomeRegion biome2 = new BiomeRegion("biome2",biome,false) ;
    BiomeRegion biome3 = new BiomeRegion("biome3",biome,false) ;
    BiomeRegion biome4 = new BiomeRegion("biome4",biome,false) ;
    BiomeRegion biome5 = new BiomeRegion("biome5",biome,false) ;
    BiomeRegion biome6 = new BiomeRegion("biome6",biome,false) ;
    BiomeRegion biome7 = new BiomeRegion("biome7",biome,false) ;
    
    
    thisGraph.addEdge(biome0,biome1,1.0);
    thisGraph.addEdge(biome0,biome2,1.0);
    thisGraph.addEdge(biome1, biome3,1.0);
    thisGraph.addEdge(biome1,biome4,1.0);
    thisGraph.addEdge(biome2,biome5,1.0);
    thisGraph.addEdge(biome3,biome6,1.0);
    thisGraph.addEdge(biome5,biome7,1.0);
    
    
    List<BiomeRegion> expectedList = Arrays.asList(biome0,biome1,biome2,biome3,biome4,biome5,biome6,biome7);
    
    assertEquals("BFS traversals should be in the same order as expectedList", expectedList,thisGraph.traverseFrom(biome0));
    
    
  }
  
  
  /**
   * Simple Dkj traversal:
   */
  @Test
  @SuppressWarnings("unchecked")
  public void dkjTraversalTest(){
    Graph thisGraph = testWorld;
    Biome biome = new Biome("Villiage", 4, 4);
    
    
    BiomeRegion biome0 = new BiomeRegion("biome0",biome,false) ;
    BiomeRegion biome1 = new BiomeRegion("biome1",biome,false) ;
    BiomeRegion biome2 = new BiomeRegion("biome2",biome,false) ;
    BiomeRegion biome3 = new BiomeRegion("biome3",biome,false) ;
    BiomeRegion biome4 = new BiomeRegion("biome4",biome,false) ;
    BiomeRegion biome5 = new BiomeRegion("biome5",biome,false) ;
    BiomeRegion biome6 = new BiomeRegion("biome6",biome,false) ;
    BiomeRegion biome7 = new BiomeRegion("biome7",biome,false) ;
    
    thisGraph.addEdge(biome0, biome1,1.0);
    thisGraph.addEdge(biome0,biome2,2.0);
    thisGraph.addEdge(biome0,biome4,3.0);
    thisGraph.addEdge(biome4,biome5,2.0);
    
    List<BiomeRegion> expectedList = Arrays.asList(biome0,biome4,biome5);
    assertEquals("DKJ traversals should be in the same order as expectedList", expectedList,thisGraph.traverseUsingLeast(biome0,biome5));
    //System.out.println(thisGraph.traverseUsingLeast(biome0,biome5));
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void dkjTraversalBigger(){
    Graph thisGraph = testWorld;
    Biome biome = new Biome("Villiage", 4, 4);
    
    
    BiomeRegion biome0 = new BiomeRegion("biome0",biome,false) ;
    BiomeRegion biome1 = new BiomeRegion("biome1",biome,false) ;
    BiomeRegion biome2 = new BiomeRegion("biome2",biome,false) ;
    BiomeRegion biome3 = new BiomeRegion("biome3",biome,false) ;
    BiomeRegion biome4 = new BiomeRegion("biome4",biome,false) ;
    BiomeRegion biome5 = new BiomeRegion("biome5",biome,false) ;
    BiomeRegion biome6 = new BiomeRegion("biome6",biome,false) ;
    BiomeRegion biome7 = new BiomeRegion("biome7",biome,false) ;
    BiomeRegion biome8 = new BiomeRegion("biome8",biome,false) ;
    
    thisGraph.addEdge(biome0, biome1,8.0);
    thisGraph.addEdge(biome1,biome2,2.0);
    thisGraph.addEdge(biome1,biome3,2.0);
    thisGraph.addEdge(biome2,biome4,2.0);
    thisGraph.addEdge(biome2, biome5,3.0);
    thisGraph.addEdge(biome1,biome5,6.0);
    thisGraph.addEdge(biome3,biome5,4.0);
    thisGraph.addEdge(biome4,biome6,4.0);
    thisGraph.addEdge(biome5, biome6,8.0);
    thisGraph.addEdge(biome3,biome7,7.0);
    thisGraph.addEdge(biome4,biome7,1.0);
    
    List<BiomeRegion> expectedList = Arrays.asList(biome5,biome2,biome4); //the path it'll take to get to biome4 with the least weights
    assertEquals("DKJ traversals should be in the same order as expectedList", expectedList,thisGraph.traverseUsingLeast(biome5,biome4));
    //System.out.println(thisGraph.traverseUsingLeast(biome5,biome4));
    
    
  }
  
  
  /**
   *  Correct updates after user-driven and event-triggered changes.
   */
  
  /**
   * Tests if event-driven weights are properly updated. All edges connected to a certain node will increase by one
   * 
   * This is the same method used for user driven event
   */
  @Test
  @SuppressWarnings("unchecked")
  public void updateWeightsTest(){
    Graph thisGraph = testWorld;
    
    thisGraph.addEdge("A", "B",1.0);
    thisGraph.addEdge("A","C",2.0);
    thisGraph.addEdge("A","D",3.0);
    thisGraph.addEdge("A","E",4.0);
    
    thisGraph.updateWeights("A",1);
    
    assertEquals("2.0",thisGraph.getWeights("A","B"));
    assertEquals("3.0",thisGraph.getWeights("A","C"));
    assertEquals("4.0",thisGraph.getWeights("A","D"));
    assertEquals("5.0",thisGraph.getWeights("A","E"));
    
    
  }
  
}
