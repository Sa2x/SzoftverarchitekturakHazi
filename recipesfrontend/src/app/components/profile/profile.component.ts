import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  userId: any;

  constructor(private authService : AuthService, private tokenStorageService : TokenStorageService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.userId = Number(routeParams.get('userId'));
    this.token = this.tokenStorageService.getToken();
    if(this.userId) {
      this.authService.getUserById(this.userId).subscribe(
        data => {
          this.currentUser=data;
        },
        err => {
          console.log(err);
        }
      );
    }
    else {
      this.authService.self().subscribe(
        data => {
          this.currentUser = data;
        },
        err => {
          console.log(err);
        }
      );
    }
  }

  goToNewRecipe(): void {
    this.router.navigate(['/recipes/new']);
  }
}