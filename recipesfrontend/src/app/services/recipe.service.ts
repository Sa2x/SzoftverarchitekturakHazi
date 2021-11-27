import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from './tokes-storage.service';

const API_URL = 'http://localhost:8080/api/recipe/';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  isLoggedIn = false;
  token = "";

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.token = this.tokenStorageService.getToken()!;
    }
  }

  getAll(): Observable<any> {
    return this.http.get(API_URL);
  }

  create(name : string): Observable<any> {
    return this.http.post(API_URL, {
      name
    })
  }
}
