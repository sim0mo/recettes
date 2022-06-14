package recettes;

import recettes.ingredients.Composant;
import recettes.ingredients.IngredientNom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Recette {
    //private final Map<Ingredient, Double> ingredients;
    private final List<Composant> ingredients;
    private final String name;

    public Recette(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
    }
//    public void addIngredient(Ingredient ingredient, double quantite){
//        addIngredient(ingredient, quantite, null);
//    }
//    public void addIngredient(Ingredient ingredient, double quantite, Unite unite){
//        for (Composant c : ingredients){
//            // TODO: 18.08.20 utiliser .equals () ?
//            if (c.getIngredient() == ingredient) {
//                throw new IllegalArgumentException("Déjà dans la recette !");
//            }
//        }
//        ingredients.add(new Composant(ingredient, quantite, unite));
//    }
    public void addComposant(Composant composant){
        for (Composant c : ingredients){
            if (c.getIngredient().getNom()==composant.getIngredient().getNom() && c.getIngredient().getSpecification().equals(composant.getIngredient().getSpecification())){
                throw new IllegalArgumentException(String.format("Déjà dans la recette ! (%s)", composant.getIngredient().getNom()));
            }
        }
        ingredients.add(composant);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder().append(name).append("\n---------------\n");
        for (Composant e : ingredients){
            if (e.getQuantite()!=0)
                if (Math.floor(e.getQuantite())==e.getQuantite())
                    sb.append((int)e.getQuantite());
                else sb.append(e.getQuantite());
            if (e.getUnite()!=null)
                sb.append(String.valueOf(e.getUnite()).toLowerCase());
            sb.append(" ")
                    .append(e.getIngredient().getNom())
                    .append(e.getIngredient().getSpecification())
                    .append("\n");
        }
        return sb.toString();
    }



    public boolean contains(IngredientNom ingredientNom){
        for (Composant c : ingredients){
            if (c.getIngredient().getNom()==ingredientNom){
                return true;
            }
        }
        return false;
    }

    public List<Composant> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public String getName() {
        return name;
    }

    public List<IngredientNom> getIngredientNames() {
        List<IngredientNom> list = new ArrayList<>();
        for (Composant i : ingredients){
            list.add(i.getIngredient().getNom());
        }
        return list;
    }
}
