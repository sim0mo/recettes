package recettes.ingredients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Composant {
    private final Ingredient ingredient;
    private final double quantite;
    private final Unite unite;

    public Composant(Ingredient ingredient, double quantite, Unite unite) {
        this.ingredient = ingredient;
        this.quantite = quantite;
        this.unite = unite;
    }

    public Composant(Ingredient ingredient, double quantite){
        this(ingredient, quantite, null);
    }

    public Composant(Ingredient ingredient) {
        this(ingredient, 1, null);
    }

    /**
     * La quantité à comparer en unité fondamentale (g/l/cs/cc)
     */
    public double quantiteFondamentale(){
        if (unite==null)
            return quantite;
        else return quantite*unite.coeff;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getQuantite() {
        return quantite;
    }

    public Unite getUnite() {
        return unite;
    }


    private static Map<String, String> synonyms = new HashMap<>();
    static {
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(Composant.class.getResourceAsStream("synonyms.txt")), StandardCharsets.UTF_8));
            reader.lines().forEachOrdered(l -> {
                List<String> line = List.of(l.split(","));
                for(String synonym : line.subList(1, line.size())){
                    synonyms.put(line.get(0), synonym);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Composant parse(String s){
        String second = s.substring(s.indexOf(" ")+1);
        String first = s.substring(0, s.indexOf(" "));

        //System.out.println(zero);
        Ingredient in;
        try {
            in = Ingredient.fromString(second);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(String.format("Aucun ingrédient correspondant à <<%s>> n'est répertorié.", second));
        }
        Unite un = Unite.parse(first);
        double qu = Unite.parseNumber(first);
        return new Composant(in, qu, un);
    }
}
