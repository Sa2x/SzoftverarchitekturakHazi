import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {
  
  form: any = {
    name: null
  };
  constructor(private recipeService : RecipeService,  private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { name } = this.form;

    this.recipeService.create(name).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/recipes']);
      },
      err => {
        console.log(err);
      }
    );
  }

}
