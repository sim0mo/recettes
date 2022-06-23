package recettes.ingredients;

import java.util.Objects;

public class IngredientNom {

    public final String name;

    IngredientNom(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientNom that = (IngredientNom) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
