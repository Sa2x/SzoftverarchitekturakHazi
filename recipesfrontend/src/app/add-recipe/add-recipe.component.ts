import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/tokes-storage.service';
import { RecipeService } from '../services/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {
  currentUser: any
  form: any = {
    name: null
  };
  constructor(private token: TokenStorageService, private recipeService : RecipeService) { }

  ngOnInit(): void {
    //this.currentUser = this.token.getUser();
  }

  onSubmit(): void {
    const { name } = this.form;
    console.log(name);
    /*
    this.recipeService.create(name, 1)
    .subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      });
      */
  }

}
