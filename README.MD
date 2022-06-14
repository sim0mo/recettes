# Recettes
## API

`Recueil load(String s)` : Crée un recueil à partir du nom de fichier

`void recueil.printAllRecettesWith(IngredientNom n)` : Affiche toutes les recettes contenant du n

`List<Recette> searchConjunctive(IngredientNom... ingredientNom)` : Retourne toutes les recettes contenant à la fois tous les ingrédients mentionnés

`List<Recette> searchDisjunctive(List<IngredientNom> ingredients)` : Retourne toutes les recettes contenant l'un ou plusieurs des ingrédients mentionnés

`List<Recette> recettesDisponibles(List<Composant> ingredientsDisponibles)` : Retourne toutes les recettes strictement faisables à partir des ingrédients disponibles

`List<Recette> recettesPresqueDisponibles(List<Composant> ingredientsDisponibles, double pourcentage = 0.75)` : Retourne toutes les recettes dont pourcentage des ingrédients est disponible

