package recettes.ingredients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Ingredient {
    private final String name;
    private String specification = "";

    public Ingredient(String nom){
        this.name = nom;
    }

    public Ingredient setSpecification(String spec){
        this.specification = spec;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public String getName(){
        return (specification.equals("") ?
                name :
                this.name + "(" + specification + ")");
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



    private static Set<String> allowedIngredients = new HashSet<>();
    static{
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(Composant.class.getResourceAsStream("ingredients.txt")), StandardCharsets.UTF_8));
            reader.lines().forEachOrdered(l -> allowedIngredients.addAll(Arrays.asList(l.split(","))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Ingredient fromString(String s){
        s = s
                .replaceAll("[èéê]", "e")
                .replaceAll("[àâ]", "a")
                .replaceAll(" \\(", "(")
                .replaceAll(" ", "_")
                .replaceAll("'", "_");
        s = s.toUpperCase(Locale.ROOT);
        return new Ingredient(s);
//        return s.contains("(")
//                ? new Ingredient(new IngredientNom(s.substring(0,s.indexOf("(")))).setSpecification(s.substring(s.indexOf("("), s.indexOf(")")+1))
//                : new Ingredient(new IngredientNom(s));
    }

}
