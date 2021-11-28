import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-view-recipe',
  templateUrl: './view-recipe.component.html',
  styleUrls: ['./view-recipe.component.css']
})
export class ViewRecipeComponent implements OnInit {
  recipe : any
  isLiked : any
  constructor(private recipeService : RecipeService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const recipeIdFromRoute = Number(routeParams.get('recipeId'));

    this.recipeService.getById(recipeIdFromRoute).subscribe(
      data => {
        console.log(data);
        this.recipe = data;
        this.isLiked = false;
      },
      err => {
        console.log(err);
      }
    );
  }

  likeRecipe(): void {
    this.isLiked = !this.isLiked;
    this.ngOnInit();
  }

}
