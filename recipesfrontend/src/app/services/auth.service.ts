import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

const AUTH_API = 'http://localhost:8080/api/user/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'login', {
      email,
      password
    }, httpOptions);
  }

  register(userName: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'register', {
      userName,
      email,
      password
    }, httpOptions);
  }

  self(): Observable<any> {
    return this.http.get(AUTH_API + "me", httpOptions)
  }

  getAllUsers():Observable<any> {
    return this.http.get(AUTH_API);
  }

  getUserById(id:number):Observable<any> {
    return this.http.get(AUTH_API + id);
  }

  subscribe(id:number):Observable<any> {
    return this.http.post(AUTH_API + id + '/follow', "");
  }

  unsubscribe(id:number):Observable<any> {
    return this.http.post(AUTH_API + id + '/unfollow' ,"");
  }

  getFollowers(id:number):Observable<any> {
    return this.http.get(AUTH_API + id + '/followers');
  }
}