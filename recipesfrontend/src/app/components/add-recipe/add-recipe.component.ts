import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  recipeId: any
  isUpdate: boolean

  constructor(private recipeService : RecipeService,  private router: Router, private route: ActivatedRoute) {
    this.filePath = '/assets/imageplaceholder.png';
    this.ingridients = new Array();
    this.diets = new Array();
    this.isUpdate = false;
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
    const routeParams = this.route.snapshot.paramMap;
    this.recipeId = Number(routeParams.get('recipeId'));
    if(this.recipeId) {
      this.isUpdate = true;
      this.recipeService.getById(this.recipeId).subscribe(
        data => {
          this.ingridients = data.ingredients;
          this.diets = data.diets;
          this.form.name = data.name;
          this.form.description = data.description;
          this.filePath = data.imageURL;
        },
        err => {
          console.log(err);
        }
      );
    }
  }

  onSubmit(): void {
    console.log("button pressed");
    if(!this.isUpdate)
    {
      console.log("calling create");
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
    else {
      console.log("calling update");
      this.recipeService.update(this.recipeId, this.form.name, this.selectedFile, this.form.description, this.diets, this.ingridients).subscribe(
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
