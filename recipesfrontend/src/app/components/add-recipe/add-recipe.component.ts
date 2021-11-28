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
  selectedFile: any;
  form: any = {
    name: null,
    image: File,
    description: null
  };
  ingridients: string[];
  diets: string[];

  constructor(private recipeService : RecipeService,  private router: Router) {
    this.filePath = '';
    this.ingridients = new Array();
    this.diets = new Array();
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
    console.log(this.form.name);
    console.log(this.selectedFile);
    console.log(this.form.description);
    console.log(this.diets);
    console.log(this.ingridients);
    this.recipeService.create(this.form.name, this.selectedFile, this.form.description, this.diets, this.ingridients).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/recipes']);
      },
      err => {
        console.log(err);
      }
    );
  }

  addIngridient(value : string):void {
    this.ingridients.push(value)
  }

  removeIngridient(value : string):void {
    for( var i = 0; i < this.ingridients.length; i++){ 
      if ( this.ingridients[i] === value) { 
        this.ingridients.splice(i, 1); 
      }
    }
  }

  checkCheckBoxvalue(event : any){
    console.log(event.target.name, event.target.value, event.target.checked);
    if(event.target.checked)
    {
      this.diets.push(event.target.value)
      console.log(this.diets);
    }
    else {
      for( var i = 0; i < this.diets.length; i++){ 
        if ( this.diets[i] === event.target.value) { 
          this.diets.splice(i, 1); 
        }
      }
      console.log(this.diets);
    }
  }
}
