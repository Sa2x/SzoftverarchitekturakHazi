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
  myuserId:any
  token: any;
  userId: any;
  isFollowed: any;
  followerCount: any;
  followedCount: any;

  constructor(private authService : AuthService, private tokenStorageService : TokenStorageService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.userId = Number(routeParams.get('userId'));
    this.token = this.tokenStorageService.getToken();
    this.authService.self().subscribe(
      data => {
        this.myuserId = data.id;
        this.currentUser = data;
      },
      err => {
        console.log(err);
      }
    );
    if(this.userId) {
      this.authService.getUserById(this.userId).subscribe(
        data => {
          this.currentUser=data;
        },
        err => {
          console.log(err);
        }
      );
      this.authService.getFollowers(this.userId).subscribe(
        data => {
          this.followerCount = data.length;
          if (data.some((u: { id: number; }) => u.id === this.myuserId)) {
            this.isFollowed = true;
          }
          else {
            this.isFollowed = false;
          }
        },
        err => {
          console.log(err);
        }
      );
      this.authService.getFollowed(this.userId).subscribe(
        data => {
          this.followedCount = data.length;
        },
        err => {
          console.log(err);
        }
      )
    }
    else {
      this.authService.self().subscribe(
        data => {
          this.currentUser = data;
          this.authService.getFollowers(this.currentUser.id).subscribe(
            data => {
              this.followerCount = data.length;
            },
            err => {
              console.log(err);
            }
          );
          this.authService.getFollowed(this.currentUser.id).subscribe(
            data => {
              this.followedCount = data.length;
            },
            err => {
              console.log(err);
            }
          );
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

  subscribeToRecipes(): void {
    this.authService.subscribe(this.userId).subscribe(
      data => {
        this.ngOnInit();
      },
      err => {
        console.log(err);
      }
    );
  }

  UnSubscribeFromRecipes():void {
    this.authService.unsubscribe(this.userId).subscribe(
      data => {
        console.log(data);
        this.ngOnInit();
      },
      err => {
        console.log(err);
      }
    );
  }
}