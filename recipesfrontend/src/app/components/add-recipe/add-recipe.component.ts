import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {
  filePath: string;
  selectedFile: any
  form: any = {
    name: null,
    image: File
  };
  constructor(private recipeService : RecipeService,  private router: Router) {
    this.filePath = '';
   }

  onFileChanged(event: any ) {
    this.selectedFile = event.target.files[0]
    const reader = new FileReader();
    reader.onload = () => {
      this.filePath = reader.result as string;
    }
    reader.readAsDataURL(this.selectedFile)
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.recipeService.create(this.form.name, this.selectedFile).subscribe(
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
