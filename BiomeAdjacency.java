import java.util.*;

public class BiomeAdjacency {

    private static final Map<String, Set<String>> adjacencyRules = new HashMap<>();

    static
    {
        adjacencyRules.put("Forest", new HashSet<>(Arrays.asList("Plains", "Jungle", "Coast", "Town", "Village")));
        adjacencyRules.put("Scorched Forest", new HashSet<>(Arrays.asList("Plains", "Jungle", "Coast", "Town", "Village")));
        adjacencyRules.put("Plains", new HashSet<>(Arrays.asList("Forest", "Desert", "Coast")));
        adjacencyRules.put("Desert", new HashSet<>(Arrays.asList("Plains", "Coast", "Oasis")));
        adjacencyRules.put("Oasis", new HashSet<>(Arrays.asList("Desert")));
        adjacencyRules.put("Jungle", new HashSet<>(Arrays.asList("Forest")));
        adjacencyRules.put("Tundra", new HashSet<>(Arrays.asList("Mountains"));
        adjacencyRules.put("Coast", new HashSet<>(Arrays.asList("Forest", "Plains", "Desert")));
        adjacencyRules.put("Mountains", new HashSet<>(Arrays.asList("Tundra")));
        adjacencyRules.put("Town", new HashSet<>(Arrays.asList("Forest", "Scorched Forest", "Village")));
        adjacencyRules.put("Village", new HashSet<>(Arrays.asList("Forest", "Scorched Forest", "Town")));
    }

    //Check if two biomes can be neighbors
    public static boolean canNeighbor(String a, String b) 
    {
        return adjacencyRules.getOrDefault(a, new HashSet<>()).contains(b);
    }

    //Get neighbors for a given biome
    public static Set<String> getNeighbors(String biome) 
    {
        return adjacencyRules.getOrDefault(biome, new HashSet<>());
    }
}
