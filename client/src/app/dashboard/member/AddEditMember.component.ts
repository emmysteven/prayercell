import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms'
import {Util} from '@app/core/Utils'
import {IError} from '@app/core/models/error'
import {ActivatedRoute, Router} from '@angular/router'
import {AlertService, MemberService} from '@app/core/services'
import {first} from 'rxjs/operators'

@Component({
  selector: 'app-update-member',
  template: `
    <div class="container">
      <form [formGroup]="form" (ngSubmit)="onSubmit()" autocomplete="off" class="form-block">
        <div class="form-group mb-3">
          <label for="firstName">Firstname:</label>
          <input type="text" id="firstName" formControlName="firstname" class="form-control"
                 [ngClass]="{ 'is-invalid': submitted && f.firstname.errors }" />

          <div *ngIf="submitted && f.firstname.errors" class="invalid-feedback">
            <div *ngIf="f.firstname.errors.required">First Name is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" formControlName="lastname" class="form-control"
                 [ngClass]="{ 'is-invalid': submitted && f.lastname.errors }" />

          <div *ngIf="submitted && f.lastname.errors" class="invalid-feedback">
            <div *ngIf="f.lastname.errors.required">Last Name is required</div>
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
          <label for="telephone">Phone Number</label>
          <input type="text" id="telephone" formControlName="telephone" class="form-control"
                 [ngClass]="{ 'is-invalid': submitted && f.telephone.errors }" />

          <div *ngIf="submitted && f.telephone.errors" class="invalid-feedback">
            <div *ngIf="f.telephone.errors.required">Phone Number is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label>Gender:</label>
          <br />
          <select formControlName="gender" class="form-control w-75">
            <option value>Select Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>

        <div class="form-group mb-3">
          <label>Prayer Cell</label>
          <br />
          <select formControlName="cell" class="form-control w-75" required>
            <option value>Prayer Cell</option>
            <option *ngFor="let cell of cells">{{ cell }}</option>
          </select>
        </div>

        <div class="form-group mb-3">
          <label for="birthDate">Date of Birth:</label>
          <br />
          <input type="text" formControlName="birthDate" class="form-control w-75" placeholder="dd-mm-yyyy">

          <div *ngIf="submitted && f.birthDate.errors" class="invalid-feedback">
            <div *ngIf="f.birthDate.errors.required">Date of Birth is required</div>
          </div>
        </div>

        <div class="form-group mb-3">
          <label>Marriage Anniversary</label>
          <br />
          <input type="text" formControlName="marriageDate" class="form-control w-75" placeholder="dd-mm-yyyy">
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
export class AddEditMemberComponent implements OnInit {

  form: FormGroup = new FormGroup({
    firstname: new FormControl<string | null>(''),
    lastname: new FormControl<string | null>(''),
    gender: new FormControl<string | null>(''),
    cell: new FormControl<string | null>(''),
    email: new FormControl<string | null>(''),
    telephone: new FormControl<number | null>(null),
    birthDate: new FormControl<string | null>(''),
    marriageDate: new FormControl<string | null>('')
  });
  id: string = '';
  isAddMode: boolean = false;
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

    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;

    this.form = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      gender: ['', Validators.required],
      cell: ['', Validators.required],
      email: ['', Validators.required],
      telephone: [null, Validators.required],
      birthDate: [''],
      marriageDate: [''],
    });

    console.log("Add Mode is " + this.isAddMode)
    if (!this.isAddMode){
      this.memberService.getById(this.id)
        .pipe(first())
        .subscribe(result => this.form.patchValue(result));
    }
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      console.log(this.form.invalid)
      return;
    }

    this.loading = true;
    if (this.isAddMode) {
      this.addMember();
    } else {
      this.editMember();
    }
  }

  private addMember() {
    this.memberService.add(this.form.value).pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Member added', { keepAfterRouteChange: true });
          this.router.navigate(['./member']);
          console.log(data);
        },
        (error) => {
          this.alertService.error(error.message);
          console.log(error.message);
          this.loading = false;
        });
  }

  private editMember() {
    this.memberService.update(this.id, this.form.value).pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Member Updated', { keepAfterRouteChange: true });
          this.router.navigate(['./member']);
          console.log(data);
        },
        (error) => {
          this.alertService.error(error.message);
          console.log(error.message);
          this.loading = false;
        });
  }

}
