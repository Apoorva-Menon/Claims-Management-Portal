import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  isValidating: boolean = false;
  isLoading: boolean = false;
  error = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  login(username: string, password: string) {
    this.isValidating = true;

    this.authService.login({ username, password }).subscribe(
      (response) => {
        this.isValidating = false;
        this.error = null;
        this.router.navigate(['./home']);
      },
      (errorMessage) => {
        this.isValidating = false;
        this.error = errorMessage;
      }
    );
  }

  onSubmit(form: NgForm) {
    const username = form.value.username;
    const password = form.value.password;

    this.login(username, password);

    form.reset();
  }

  handleError() {
    this.error = null;
  }
}
