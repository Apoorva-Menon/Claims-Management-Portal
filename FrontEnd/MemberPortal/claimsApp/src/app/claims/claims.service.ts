import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

interface BillsResponse {
  dueAmount: number;
  dueDate: string;
  lastPaidDate: string;
  lateCharge: number;
  memberId: number;
  policyId: number;
}

interface ClaimStatusResponse {
  claimId: string;
  claimStatus: string;
  claimDescription: string;
}

@Injectable({
  providedIn: 'root',
})
export class ClaimsService {
  billResponse: BillsResponse | null = null;
  claimStatusResponse: ClaimStatusResponse | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  viewBills(inputFields: { memberID: string; policyID: string }) {
    return this.http
      .get<BillsResponse>(
        'http://localhost:8099/memberModule/viewBills/' +
          inputFields.memberID +
          '/' +
          inputFields.policyID
      )
      .pipe(
        catchError(this.handleError),
        tap((response) => {
          this.billResponse = response;
          this.claimStatusResponse = null;
          console.log(response);
        })
      );
  }

  viewStatus(inputFields: {
    memberID: string;
    policyID: string;
    claimID: string;
  }) {
    console.log(inputFields);

    return this.http
      .get<ClaimStatusResponse>(
        'http://localhost:8099/memberModule/getClaimStatus/' +
          inputFields.claimID +
          '/' +
          inputFields.policyID +
          '/' +
          inputFields.memberID
      )
      .pipe(
        catchError(this.handleError),
        tap((response) => {
          console.log(response);

          this.claimStatusResponse = response;
          this.billResponse = null;
        })
      );
  }

  submitClaim(inputFields: {
    memberId: string;
    policyId: string;
    hospitalId: string;
    benefitId: string;
    remarks: number;
    claimAmount: number;
  }) {
    console.log(inputFields);
    return this.http
      .post<ClaimStatusResponse>(
        'http://localhost:8099/memberModule/submitClaim',
        inputFields
      )
      .pipe(
        catchError(this.handleError),
        tap((response) => {
          this.claimStatusResponse = response;
          this.billResponse = null;
        })
      );
  }

  handleError(errorResponse: HttpErrorResponse) {
    console.log(errorResponse);
    return throwError(errorResponse.error.message);
  }
}
