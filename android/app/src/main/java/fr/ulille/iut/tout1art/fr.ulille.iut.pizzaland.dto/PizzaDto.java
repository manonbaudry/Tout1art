package fr.ulille.iut.pizzaland.dto;


import java.util.List;

public class PizzaDto extends PizzaShortDto {
	private List<IngredientDto> ingredients;

	public List<IngredientDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDto> ingredients) {
		this.ingredients = ingredients;
	}
}