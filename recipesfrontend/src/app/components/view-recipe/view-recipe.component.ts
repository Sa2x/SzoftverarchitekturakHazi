import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-view-recipe',
  templateUrl: './view-recipe.component.html',
  styleUrls: ['./view-recipe.component.css']
})
export class ViewRecipeComponent implements OnInit {
  recipe : any
  isLiked : any
  likers: any
  currentUser : any
  recipeId : any
  token:any
  constructor(private recipeService : RecipeService, private route: ActivatedRoute, private authService : AuthService, private tokenStorageService:TokenStorageService ) { }

  ngOnInit(): void {
    this.token = this.tokenStorageService.getToken();
    const routeParams = this.route.snapshot.paramMap;
    this.recipeId = Number(routeParams.get('recipeId'));
    this.authService.self().subscribe(
      data => {
        this.currentUser = data;
      },
      err => {
        console.log(err);
      }
    );
    this.recipeService.getById(this.recipeId).subscribe(
      data => {
        console.log(data);
        this.recipe = data;
      },
      err => {
        console.log(err);
      }
    );

    this.recipeService.getRecipeLikers(this.recipeId).subscribe(
      data => {
        this.likers = data;
        if (this.likers.some((u: { id: number; }) => u.id === this.currentUser.id)) {
          this.isLiked = true;
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  likeRecipe(): void {
    this.isLiked = !this.isLiked;
    if(this.isLiked) {
      this.recipeService.likeRecipe(this.recipeId).subscribe(
        data => {
          this.ngOnInit();
        },
        err => {
          console.log(err);
        }
      );
    }
    else {
      this.recipeService.unLikeRecipe(this.recipeId).subscribe(
        data => {
          this.ngOnInit();
        },
        err => {
          console.log(err);
        }
      );
    }
    
  }

}
