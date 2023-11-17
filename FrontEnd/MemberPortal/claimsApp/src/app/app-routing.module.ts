import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { AuthGuard } from './auth/auth.guard';
import { ClaimsFormComponent } from './claims/claims-form/claims-form.component';
import { ClaimsResultComponent } from './claims/claims-result/claims-result.component';
import { ClaimsComponent } from './claims/claims.component';
import { ClaimsGuard } from './claims/claims.guard';
import { SubmitClaimFormComponent } from './claims/submit-claim-form/submit-claim-form.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'auth', component: AuthComponent },
  {
    path: ':username',
    component: ClaimsComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'viewClaims',
        component: ClaimsFormComponent,
        data: { mode: 'billMode' },
      },
      {
        path: 'viewClaims/result',
        component: ClaimsResultComponent,
        canActivate: [ClaimsGuard],
      },
      {
        path: 'getClaimStatus',
        component: ClaimsFormComponent,
        data: { mode: 'claimStatusMode' },
      },
      {
        path: 'getClaimStatus/result',
        component: ClaimsResultComponent,
        canActivate: [ClaimsGuard],
      },
      {
        path: 'submitClaim',
        component: SubmitClaimFormComponent,
      },
      {
        path: 'submitClaim/result',
        component: ClaimsResultComponent,
        canActivate: [ClaimsGuard],
      },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
