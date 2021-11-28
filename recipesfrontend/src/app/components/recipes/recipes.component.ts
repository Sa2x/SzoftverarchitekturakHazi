import { Component, Input, OnInit } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
})
export class RecipesComponent implements OnInit {
  @Input()
  user: any;
  recipes: any;

  constructor(private recipeService: RecipeService) {}

  ngOnInit(): void {
    this.recipeService.getAll().subscribe(
      (data) => {
        this.recipes = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  get filterByUserId() {
    console.log(this.user, this.recipes);
    return this.recipes.filter(
      (r: { user: { id: number } }) => r.user.id == this.user.id
    );
  }

  delRecipe(id: number): void {
    this.recipeService.delete(id).subscribe(
      (data) => {
        this.ngOnInit();
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
