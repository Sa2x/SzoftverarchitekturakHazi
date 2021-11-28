import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/recipe/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' }),
};

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    // return this.http.get('assets/recipes.json');
    return this.http.get(AUTH_API);
  }

  create(name: string, image: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', image);
    formData.append('name', name);
    return this.http.post(AUTH_API, formData, {});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(AUTH_API + id);
  }

  getById(id: number): Observable<any> {
    return this.http.get('assets/recipe.json');
  }
}
