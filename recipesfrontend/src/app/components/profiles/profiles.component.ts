import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {
  users:any
  token:any
  currentUser:any
  constructor(private authService : AuthService, private tokenStorageService:TokenStorageService) { }

  ngOnInit(): void {
    this.authService.self().subscribe(
      data => {
        this.currentUser = data;
      },
      err => {
        console.log(err);
      }
    );
    this.token = this.tokenStorageService.getToken();
    this.authService.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
      err => {
        console.log(err);
      }
    );
  }

}
