package recettes;

import recettes.ingredients.Composant;
import recettes.ingredients.Ingredient;
import recettes.ingredients.Unite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class Main {
    private static final Scanner keyb = new Scanner(System.in);

    private static final List<Composant> basic = parseFrigo("/frigo.txt");

    private static List<Composant> parseFrigo(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(fileName);
        List<Composant> list = new ArrayList<>();
        try {
            assert inputStream != null;
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(
                                         inputStream, StandardCharsets.UTF_8))) {
                reader.lines()
                        .map(x -> Composant.parse("1000kg " + x))
                        .forEachOrdered(list::add);
            }
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
        return list;
    }

    public static void main(String[] args) {
        Recueil gastronogeek = load("/gastronogeek.txt");
        Recueil catherine1 = load("/catherine1.txt");
//        gastronogeek.printAllRecettesWith("vodka" );

        //gastronoGeek.recettesDisponibles(promptIngredientsIllimited())).forEach(System.out::println);
        catherine1.printAllRecettesWith("LAIT");
        System.out.println();

//        catherine1.recettesPresqueDisponibles(promptIngredientsIllimited()).forEach(System.out::println);
//        System.out.println();

        catherine1.searchDisjunctive(List.of("SAFRAN","CANNELLE")).forEach(System.out::println);
        System.out.println();

        catherine1.recettesDisponibles(List.of(
                new Composant(new Ingredient("OLIVE"), 300, Unite.G),
                new Composant(new Ingredient("ANCHOIS"), 100, Unite.G),
                new Composant(new Ingredient("THON"), 101, Unite.G),
                new Composant(new Ingredient("AIL"), 5),
                new Composant(new Ingredient("FARINE"), 300),
                new Composant(new Ingredient("OEUF"), 10),
                new Composant(new Ingredient("SUCRE"), 300, Unite.G),
                new Composant(new Ingredient("BEURRE"), 500),
                new Composant(new Ingredient("LAIT"), 5, Unite.DL),
                new Composant(new Ingredient("CHOCOLAT_NOIR"), 600, Unite.G)
        )).forEach(System.out::println);
        //gastronoGeek.recettesContenant(EAU)).forEach(x -> System.out.println(x.getName()));

        // TODO: 18.04.20 gui 
    }

    private static Recueil load(String s) {
        return new Recueil.Loader().loadFrom(Main.class.getResourceAsStream(s)).build();
    }

    private static List<String> promptIngredientNames() {
        List<String> list = new ArrayList<>();
        String input = keyb.nextLine();
        while (!input.isEmpty()){
            list.add(Ingredient.fromString(input).getName());
            input = keyb.nextLine();
        }
        return list;
    }

    private static List<Composant> promptIngredientsIllimited() {
        List<Composant> list = new ArrayList<>(basic);
        String input = keyb.nextLine();
        while (!input.isEmpty()){
            list.add(Composant.parse("1000kg " + input));
            input = keyb.nextLine();
        }
        return list;
    }

    public static String cleanString(String s){
        s = s
                .replaceAll("[èéê]", "e")
                .replaceAll("[àâ]", "a")
                .replaceAll(" \\(", "(")
                .replaceAll(" ", "_")
                .replaceAll("'", "_");
        s = s.toUpperCase(Locale.ROOT);
        s = s.trim();
        return s;
    }


}
