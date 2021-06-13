import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  template: `
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" routerLink="/">Restaurant</a>
      <button
        class="navbar-toggler"
        type="button" data-toggle="collapse"
        data-target="#navbarDefault"
        aria-controls="navbarDefault"
        aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarDefault">
        <ul class="navbar-nav ml-auto">
          <li *ngIf="!isLoggedIn()" class="nav-item">
            <a class="nav-link" routerLink="/login">Login</a>
          </li>
          <li *ngIf="!isLoggedIn()" class="nav-item">
            <a class="nav-link" routerLink="/register">Register</a>
          </li>
          <li *ngIf="isLoggedIn()" class="nav-item">
            <a class="nav-link" role="button" (click)="logout()">logout</a>
          </li>
        </ul>
      </div>
    </nav>
  `,
  styles: [
  ]
})

export class HeaderComponent implements OnInit {
  constructor(public authService: AuthService) {}

  isLoggedIn = ():boolean => this.authService.isLoggedIn();
  logout = ():void => this.authService.logout();

  ngOnInit(): void {}
}
