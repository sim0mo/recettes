package recettes;

import recettes.ingredients.Composant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Une Recette contient des Composants et a un nom.
 */
public final class Recette {
    private final List<Composant> ingredients;
    private final String name;

    public Recette(String name) {
        this.name = name;
        ingredients = new ArrayList<>();
    }

    public void addComposant(Composant composant){
        for (Composant c : ingredients){
            if (Objects.equals(c.getIngredient().getName(), composant.getIngredient().getName()) &&
                    c.getIngredient().getSpecification().equals(composant.getIngredient().getSpecification())
            ){
                throw new IllegalArgumentException(String.format("Déjà dans la recette %s ! (%s)", this.name,composant.getIngredient().getName()));
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
                    .append(e.getIngredient().getName())
                    //.append(e.getIngredient().getSpecification())
                    .append("\n");
        }
        return sb.toString();
    }



    public boolean contains(String ingredientNom){
        for (Composant c : ingredients){
            if (c.getIngredient().getName().equals(ingredientNom)){
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

    public List<String> getIngredientNames() {
        List<String> list = new ArrayList<>();
        for (Composant i : ingredients){
            list.add(i.getIngredient().getName());
        }
        return list;
    }
}
