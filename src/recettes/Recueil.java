package recettes;

import recettes.ingredients.Composant;
import recettes.ingredients.Ingredient;
import recettes.ingredients.IngredientNom;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Recueil {
    private final List<Recette> recettes;

    private Recueil(List<Recette> recettes) {
        this.recettes = List.copyOf(recettes);
    }

    @Override
    public String toString(){
        StringBuilder allRecettes = new StringBuilder();
        for (Recette r: recettes)
            allRecettes.append(r).append("\n");
        return allRecettes.toString();
    }

    public List<Recette> searchConjunctive(IngredientNom ingredientNom){
        List<Recette> recettesFaisables = new ArrayList<>();
        for (Recette r : recettes){
            if (r.contains(ingredientNom))
                recettesFaisables.add(r);
        }
        return recettesFaisables;
    }

    public List<Recette> searchConjunctive(List<IngredientNom> ingredients){
        List<Recette> recettesFaisables = new ArrayList<>();
        for (Recette r : recettes){
            if (r.contains(ingredients.get(0))) {
                recettesFaisables.add(r);
            }
        }
        for (IngredientNom i : ingredients.subList(1, ingredients.size())){
            recettesFaisables.removeIf(r -> !r.contains(i));
        }
        return recettesFaisables;
    }

    public List<Recette> searchDisjunctive(List<IngredientNom> ingredients) {
        List<Recette> recettesFaisables = new ArrayList<>();
        for (IngredientNom i : ingredients){
            for (Recette r : recettes){
                if(r.contains(i) && !recettesFaisables.contains(r)){
                    recettesFaisables.add(r);
                }
            }
        }
        return  recettesFaisables;
    }


    public List<Recette> recettesDisponibles(List<Composant> ingredientsDisponibles){

        List<Recette> recettesFaisables = new ArrayList<>();
        for (Recette r : recettes){
            if ( //détermine si tous les ingrédients de la recette r sont disponibles
                    r.getIngredientNames().stream()
                            .allMatch(
                                    ingredientInRecipe -> ingredientsDisponibles.stream()
                                            .map(composant -> composant.getIngredient().getNom())
                                            .anyMatch(ingredientInRecipe::equals))

            ){ //détermine si leur nombre est suffisant
                boolean faisable = true;
                for (Composant required : r.getIngredients()){
                    for (Composant disposable : ingredientsDisponibles){
                        if (required.getIngredient().getNom() == disposable.getIngredient().getNom()){
                            //System.out.println(required.quantiteFondamentale() + "   " + disposable.quantiteFondamentale());
                            faisable &= (required.quantiteFondamentale() <= disposable.quantiteFondamentale());
                        }
                    }
                }
                if (faisable)
                    recettesFaisables.add(r);
            }
        }

        return recettesFaisables;
    }

    public List<Recette> recettesPresqueDisponibles(List<Composant> ingredientsDisponibles){
        return recettesPresqueDisponibles(ingredientsDisponibles, 0.75);
    }

    public List<Recette> recettesPresqueDisponibles(List<Composant> ingredientsDisponibles, double pourcentage) {
        List<Recette> recettesFaisables = new ArrayList<>();
        for (Recette r : recettes){
            int nIngredients = r.getIngredients().size();
            int nMatches = 0;
            for(Composant c : r.getIngredients()){
                for(Composant d : ingredientsDisponibles){
                    if(c.getIngredient().getNom() == d.getIngredient().getNom() && c.quantiteFondamentale() <= d.quantiteFondamentale()){
                        nMatches++;
                    }
                }
            }
            if(nMatches*1.0/nIngredients >= pourcentage){
                recettesFaisables.add(r);
            }
        }
        return recettesFaisables;
    }

    public static class Loader{
        private final List<Recette> recettes = new ArrayList<>();
        public Loader(){}
        public Loader loadFrom(InputStream inputStream){
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(// TODO: 18.08.20 change charset 
                            inputStream, StandardCharsets.UTF_8))) {
                reader.lines()
                        .map(s -> s.split(", *"))
                        .map(strings -> {
                            Recette recette = new Recette((strings[0]));
                            for (int i = 1; i < strings.length; i++){
                                recette.addComposant(Composant.parse(strings[i]));
                            }
                            return recette;
                        })
                        .forEachOrdered(recettes::add);
                return this;
            } catch (IOException e){
                throw new UncheckedIOException(e);
            }
        }
        public Recueil build(){
            return new Recueil(recettes);
        }
    }

    public void printAllRecettesWith(IngredientNom i1, IngredientNom... i){
        System.out.printf("Recettes contenant : %s %s\n", i1, Arrays.toString(i));
        List<IngredientNom> l = new ArrayList<>(List.of(i));
        l.add(0, i1);
        for (Recette r : searchConjunctive(l)){
            System.out.println(r.getName());
        }
    }
}
