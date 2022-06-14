package recettes.ingredients;

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
     *
     * La quantité à comparer en unité fondamentale (g/l/cs/cc)
     * @return
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

    public static Composant parse(String s){
        String one = s.substring(s.indexOf(" ")+1);
        String zero = s.substring(0, s.indexOf(" "));

        //System.out.println(zero);
        Ingredient in;
        try {
            in = Ingredient.fromString(one);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(String.format("Aucun ingrédient correspondant à <<%s>> n'est répertorié.", one));
        }
        Unite un = Unite.parse(zero);
        double qu = Unite.parseNumber(zero);
        return new Composant(in, qu, un);
    }
}
