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
    if(this.user) {
      this.recipeService.getAllByUserId(this.user.id).subscribe(
        data => {
          console.log(data);
          this.recipes = data;
        },
        err => {
          console.log(err);
        }
      )
    }
    else {
      this.recipeService.getAll().subscribe(
        data => {
          console.log(data);
          this.recipes = data;
        },
        err => {
          console.log(err);
        }
      );
    }
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
