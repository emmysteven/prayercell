import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { IError } from '@app/core/models/error'
import { ActivatedRoute, Router } from '@angular/router'
import { AlertService, MemberService } from '@app/core/services'
import {first} from 'rxjs/operators'
import { Util } from '@app/core/Utils'


@Component({
  selector: 'app-create-member',
  template: `
    <div class="container">
      <form [formGroup]="form" (ngSubmit)="onSubmit()" autocomplete="off" class="form-block">
        <div class="form-group mb-3">
          <label for="firstName">Firstname:</label>
          <input type="text" id="firstName" formControlName="firstName"
                 class="form-control" [ngClass]="{ 'is-invalid': submitted && f.firstName.errors }" />

          <div *ngIf="submitted && f.firstName.errors" class="invalid-feedback">
            <div *ngIf="f.firstName.errors.required">First Name is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" formControlName="lastName" class="form-control"
                 [ngClass]="{ 'is-invalid': submitted && f.lastName.errors }" />

          <div *ngIf="submitted && f.lastName.errors" class="invalid-feedback">
            <div *ngIf="f.lastName.errors.required">Last Name is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label for="email">Email</label>
          <input type="text" id="email" formControlName="email" class="form-control"
                 [ngClass]="{ 'is-invalid': submitted && f.email.errors }" />

          <div *ngIf="submitted && f.email.errors" class="invalid-feedback">
            <div *ngIf="f.email.errors.required">Email is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label>Gender:</label>
          <br />
          <select class="form-control w-75">
            <option value>Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>

        <div class="form-group mb-3">
          <label>Prayer Cell</label>
          <br />
          <select v-model="fields.cell" class="form-control w-75" required>
<!--            <option :value="defaultCell">{{ defaultCell }}</option>-->
            <option value>Select Cell</option>
            <option *ngFor="let cell of cells">{{ cell }}</option>
          </select>
        </div>

        <div class="form-group mb-3">
          <label>Date of Birth:</label>
          <br />
          <input type="date" class="form-control w-75">
        </div>

        <div class="form-group mb-3">
          <label>Marriage Anniversary</label>
          <br />
          <input type="date" class="form-control w-75">
        </div>

        <button class="btn btn-primary me-2">Submit</button>
        <a class="btn btn-secondary" routerLink="/member">Cancel</a>
      </form>
    </div>
  `,
  styles: [`
    .container {
      margin-top: 2.5rem;
      width: 50%;
    }

    .w-25 {
      width: 5.4rem !important;
    }
  `]
})
export class addMemberComponent implements OnInit {

  form: FormGroup = new FormGroup({});
  loading = false;
  submitted = false;

  cells: any = Util.cells();

  Error: IError = {
    error: ''
  }

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private memberService: MemberService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;

    this.memberService.add(this.form.value).pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Registration successful', { keepAfterRouteChange: true });
          this.router.navigate(['../login'], { relativeTo: this.route });
          console.log(data);
        },
        (error) => {
          this.alertService.error(error.message);
          console.log(error.message);
          this.loading = false;
        });
  }
}
