package recettes.ingredients;

import java.util.Locale;

public final class Ingredient {
    private IngredientNom nom;
    private String specification = "";

    public Ingredient(IngredientNom nom){
        this.nom = nom;
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
                nom.toString() :
                this.nom.toString() + "(" + specification + ")");
    }

    public IngredientNom getNom(){
        return nom;
    }

    public static Ingredient fromString(String s){
        s = s.replaceAll("[èéê]", "e").replaceAll("[àâ]", "a").replaceAll(" \\(", "(").replaceAll(" ", "_").replaceAll("'", "_");
        s = s.toUpperCase(Locale.ROOT);
        Ingredient i = s.contains("(")
                ? new Ingredient(IngredientNom.valueOf(s.substring(0,s.indexOf("(")))).setSpecification(s.substring(s.indexOf("("), s.indexOf(")")+1))
                : new Ingredient(IngredientNom.valueOf(s));
        i.resolve();
        return i;
    }

    private void resolve() {
        this.nom = this.nom.resolve();
    }
}
