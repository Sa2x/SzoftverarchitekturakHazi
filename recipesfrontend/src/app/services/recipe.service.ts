import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const RECIPE_API = 'http://localhost:8080/api/recipe/';
const USER_API = 'http://localhost:8080/api/user/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' }),
};

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(RECIPE_API);
  }

  create(
    name: string,
    image: File,
    description: string,
    diets: string[],
    ingridients: string[]
  ): Observable<any> {
    const formData = new FormData();
    formData.append('file', image);
    formData.append('name', name);
    formData.append('description', description);
    diets.forEach((diet) => {
      formData.append('diets', diet);
    });
    ingridients.forEach((ingridient) => {
      formData.append('ingredients', ingridient);
    });
    return this.http.post(RECIPE_API, formData, {});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(RECIPE_API + id);
  }

  getById(id: number): Observable<any> {
    return this.http.get(RECIPE_API + id);
  }

  getAllByUserId(userId: number): Observable<any> {
    return this.http.get(USER_API + userId + '/uploaded'
    );
  }

  getRecipeLikers(id: number): Observable<any> {
    return this.http.get(RECIPE_API + id + '/likes')
  }

  likeRecipe(id:number): Observable<any> {
    return this.http.post(RECIPE_API + id + '/like', "",);
  }

  unLikeRecipe(id:number): Observable<any> {
    return this.http.post(RECIPE_API + id + '/unlike', "",);
  }
}
