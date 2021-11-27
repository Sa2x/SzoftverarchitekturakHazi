import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/tokes-storage.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  isLoggedIn = false;
  token = "";

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.token = this.tokenStorageService.getToken()!;
  }
}

}
