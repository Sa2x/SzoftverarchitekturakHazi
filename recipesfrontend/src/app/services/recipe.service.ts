import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/recipe/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get("assets/recipes.json");
    //return this.http.get(AUTH_API, httpOptions);
  }

  create(name: string):Observable<any> {
    return this.http.post(AUTH_API, {
      name
    }, httpOptions)
  }
}
