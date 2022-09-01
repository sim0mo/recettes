package recettes.ingredients;

import recettes.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Ingrédient : a un nom qui doit être contenu dans ingredients.txt
 * Peut avoir une spécification qui n'a pas d'influence sur l'égalité
 */
public final class Ingredient {
    private final String name;
    private String specification = "";

    public Ingredient(String nom){
        this.name = nom;
    }

    public Ingredient(String nom, String specification){
        this.name = nom;
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    public String getName(){
//        return name;
        return (specification.equals("") ?
                name :
                this.name + specification);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }



    private static final Set<String> allowedIngredients = new HashSet<>();

    static{
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(Ingredient.class.getClassLoader().getResourceAsStream("ingredients.txt")), StandardCharsets.UTF_8));
            reader.lines().forEachOrdered(l -> allowedIngredients.addAll(Arrays.asList(l.split(","))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Ingredient in first position of each line will be considered as standard form
    public static Map<String, String> synonyms = new HashMap<>();
    static {
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(Composant.class.getClassLoader().getResourceAsStream("synonyms.txt")),
                            StandardCharsets.UTF_8));
            reader.lines().forEachOrdered(l -> {
                List<String> line = List.of(l.split(","));
                for(String synonym : line.subList(1, line.size())){
                    synonyms.put(synonym, line.get(0));
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Ingredient fromString(String s){
        s = Main.cleanString(s);

        Ingredient result;
        if (s.contains("(")){
            result = new Ingredient(s.substring(0,s.indexOf("(")), s.substring(s.indexOf("("), s.indexOf(")")+1));
        } else{
            result = new Ingredient(s);
        }
        if (!allowedIngredients.contains(result.name)){
            throw new IllegalArgumentException(String.format("Aucun ingrédient correspondant à <<%s>> n'est répertorié.", result.name));
        }
        return result;

    }

}
