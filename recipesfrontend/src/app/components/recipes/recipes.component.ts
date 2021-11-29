import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
})
export class RecipesComponent implements OnInit {
  @Input()
  user: any;
  recipes: any;
  token: any;
  currentUser:any

  constructor(private recipeService: RecipeService, private tokenStorageService: TokenStorageService, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.self().subscribe(
      data => {
        this.currentUser = data;
      },
      err => {
        console.log(err);
      }
    );
    this.token = this.tokenStorageService.getToken();
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
