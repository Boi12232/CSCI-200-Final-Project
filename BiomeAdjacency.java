import java.util.*;

public class BiomeAdjacency {

    private static final Map<String, Set<String>> adjacencyRules = new HashMap<>();

    static
    {
        adjacencyRules.put("Forest", new HashSet<>(Arrays.asList("Plains", "Jungle", "Coast", "Mountains")));
        adjacencyRules.put("Plains", new HashSet<>(Arrays.asList("Forest", "Desert", "Coast", "Tundra")));
        adjacencyRules.put("Desert", new HashSet<>(Arrays.asList("Plains", "Coast", "Oasis")));
        adjacencyRules.put("Jungle", new HashSet<>(Arrays.asList("Forest", "Ruins")));
        adjacencyRules.put("Coast", new HashSet<>(Arrays.asList("Forest", "Plains", "Desert", "Village")));
        adjacencyRules.put("Tundra", new HashSet<>(Arrays.asList("Mountains", "Plains", "Ruins")));
        adjacencyRules.put("Mountains", new HashSet<>(Arrays.asList("Forest", "Tundra", "Ruins")));
        adjacencyRules.put("Ruins", new HashSet<>(Arrays.asList("Jungle", "Mountains", "Tundra")));
        adjacencyRules.put("Oasis", new HashSet<>(Arrays.asList("Desert")));
        adjacencyRules.put("Village", new HashSet<>(Arrays.asList("Coast", "Forest")));
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
