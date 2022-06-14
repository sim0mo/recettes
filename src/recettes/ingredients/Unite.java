package recettes.ingredients;

public enum Unite {
    G(1),KG(1000),MG(0.001),L(1),DL(0.1),CL(0.01),ML(0.001),CS(1),CC(1),O(1),
    ;

    public final double coeff;

    Unite(double coeff){
        this.coeff = coeff;
    }

    @Override
    public String toString() {
        if (this == Unite.O) {
            return "";
        }
        return super.toString();
    }

    public static Unite parse(String s){
        String s1 = s.replaceAll("[.-9]| ","").toLowerCase();
        switch (s1){
            case "g":return G;
            case "kg":return KG;
            case "mg":return MG;
            case "l":return L;
            case "dl":return DL;
            case "cl":return CL;
            case "ml":return ML;
            case "cs":return CS;
            case "cc":return CC;
            case "":return O;
            default: throw new IllegalArgumentException(String.format("Erreur dans la lecture de l'unité : %s", s));
        }
    }

    public static double parseNumber(String s){
        String n = s.replaceAll("[A-z]| ","");
        return Double.parseDouble(n);
    }

}
