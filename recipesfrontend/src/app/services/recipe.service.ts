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
    return this.http.get(AUTH_API);
  }

  create(name: string, image: File, description: string, diet: string[], ingridients:string[]): Observable<any> {
    const formData = new FormData();
    formData.append('file', image);
    formData.append('name', name);
    formData.append('description', description);
    formData.append('diets', new Blob([JSON.stringify(diet)]));
    formData.append('ingredients', new Blob([JSON.stringify(ingridients)]));
    return this.http.post(AUTH_API, formData, {});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(AUTH_API + id);
  }

  getById(id:number):Observable<any> {
    return this.http.get(AUTH_API + id);
  }

  getAllByUserId(userId:number):Observable<any> {
    return this.http.get("http://localhost:8080/api/user/" + userId + "/uploaded") 
  }
}
