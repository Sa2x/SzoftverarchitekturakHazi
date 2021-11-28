import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  token: any;

  constructor(private authService : AuthService, private tokenStorageService : TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    this.token = this.tokenStorageService.getToken();
    this.authService.self().subscribe(
      data => {
        this.currentUser = data;
      },
      err => {
        console.log(err);
      }
    );
  }

  goToNewRecipe(): void {
    this.router.navigate(['/recipes/new']);
  }
}