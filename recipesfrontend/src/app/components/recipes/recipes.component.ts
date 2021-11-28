import { Component, Input, OnInit } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {
  @Input()
  user:any
  recipes: any

  constructor(private recipeService : RecipeService) { }

  ngOnInit(): void {
    this.recipeService.getAll().subscribe(
      data => {
        this.recipes = data;
      },
      err => {
        console.log(err);
      }
    );
  }

  get filterByUserId() {
    return this.recipes.filter( (r: { user: number; }) => r.user == this.user.id);
  }

}
