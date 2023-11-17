import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user.model';

interface AuthResponse {
  username: string;
  jwtAuthToken: string;
  serverCurrentTime: number;
  tokenExpirationTime: number;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User | null>(null);
  timeout = new Subject<boolean>();
  private tokenExirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  login(inputFields: { username: string; password: string }) {
    return this.http
      .post<AuthResponse>(
        'http://localhost:8008/authorization/login',
        inputFields
      )
      .pipe(
        catchError(this.handleError),
        tap((response) => {
          this.handleAuthentication(
            response['username'],
            response['jwtAuthToken'],
            response['serverCurrentTime'],
            response['tokenExpirationTime']
          );
        })
      );
  }

  autoLogin() {
    const user = localStorage.getItem('userData');
    if (!user) return;

    const parsedUser: {
      username: string;
      _token: string;
      serverCurrentTime: number;
      _tokenExpirationTime: number;
    } = JSON.parse(user);
    const loadedUser = new User(
      parsedUser.username,
      parsedUser._token,
      parsedUser.serverCurrentTime,
      parsedUser._tokenExpirationTime
    );

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration =
        loadedUser.tokenExpirationTime - new Date().getTime();

      console.log(expirationDuration);

      this.autoLogout(expirationDuration);
    }
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['./auth']);
    this.removeUser();

    if (this.tokenExirationTimer) {
      clearTimeout(this.tokenExirationTimer);
    }
    this.tokenExirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExirationTimer = setTimeout(() => {
      this.timeout.next(true);
      this.logout();
    }, expirationDuration);
  }

  private handleError(errorResponse: HttpErrorResponse) {
    let errorMessage = 'An unknown error occured';

    if (!errorResponse.error || !errorResponse.error.error) {
      return throwError(errorMessage);
    }

    switch (errorResponse.error.message) {
      case 'No value present':
        errorMessage = 'Invalid Username';
        break;
      default:
        errorMessage = 'Invalid Username or Password';
    }
    return throwError(errorMessage);
  }

  private handleAuthentication(
    username: string,
    token: string,
    serverCurrentTime: number,
    tokenExpirationTime: number
  ) {
    const user = new User(
      username,
      token,
      serverCurrentTime,
      tokenExpirationTime
    );
    this.storeUser(user);
    this.autoLogout(tokenExpirationTime - serverCurrentTime);
    this.user.next(user);
  }

  private storeUser(user: User) {
    localStorage.setItem('userData', JSON.stringify(user));
  }

  private removeUser() {
    localStorage.removeItem('userData');
  }
}
