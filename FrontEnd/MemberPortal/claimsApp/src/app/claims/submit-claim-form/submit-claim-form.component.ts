import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectDetailService } from 'src/app/shared/select-detail/select-detail.service';
import { ClaimsService } from '../claims.service';

@Component({
  selector: 'app-submit-claim-form',
  templateUrl: './submit-claim-form.component.html',
  styleUrls: ['./submit-claim-form.component.css'],
})
export class SubmitClaimFormComponent {
  isValidating: boolean = false;
  error: string | null = null;

  constructor(
    private claimsService: ClaimsService,
    private router: Router,
    private route: ActivatedRoute,
    public selectDetailsService: SelectDetailService
  ) {}

  submitClaim(
    memberId: string,
    policyId: string,
    hospitalId: string,
    benefitId: string,
    remarks: number,
    claimAmount: number
  ) {
    this.isValidating = true;
    this.claimsService
      .submitClaim({
        memberId,
        policyId,
        hospitalId,
        benefitId,
        remarks,
        claimAmount,
      })
      .subscribe(
        (response) => {
          console.log(response);
          this.error = null;
          this.isValidating = false;
          this.router.navigate(['./result'], { relativeTo: this.route });
        },
        (errorMessage) => {
          this.isValidating = false;
          this.error = 'Invalid Details. Please check your details again';
          console.log(errorMessage);
        }
      );
  }

  onSubmit(form: NgForm) {
    this.submitClaim(
      form.value.memberId,
      form.value.policyId,
      form.value.hospitalId,
      form.value.benefitId,
      form.value.remarks,
      form.value.claimAmount
    );

    form.reset();
  }

  handleError() {
    this.error = null;
  }
}
