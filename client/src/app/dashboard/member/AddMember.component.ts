import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-member',
  template: `
    <div class="container">
      <form class="form-block" method="POST">
        <div class="form-group">
          <label>Firstname:</label>
          <input type="text" class="form-control capitalize" required />
        </div>

        <div class="form-group">
          <label>Lastname:</label>
          <input type="text" class="form-control capitalize" required />
        </div>

        <div class="form-group">
          <label>Email:</label>
          <input type="email" class="form-control" required />
        </div>

        <div class="form-group">
          <label>Gender:</label>
          <br />
          <select class="custom-select w-75">
            <option value>Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
        </div>

        <div class="form-group">
          <label>Prayer Cell</label>
          <br />
          <select v-model="fields.cell" class="custom-select w-75" required>
            <!-- <option :value="defaultCell">{{ defaultCell }}</option> -->
            <option value>Select Cell</option>
<!--            <option v-for="(value, index) in cells" :key="index">{{ value }}</option>-->
          </select>
        </div>

        <div class="form-group">
          <label>Month and Day of Birth:</label>
          <br />
          <select v-model="fields.day" class="custom-select w-25" required>
            <option value>Select</option>
<!--            <option v-for="(value, index) in days" :key="index">{{ value }}</option>-->
          </select>

          <select v-model="fields.month" class="ml-3 custom-select w-25" required>
            <option value>Select</option>
<!--            <option v-for="(value, index) in months" :key="index">{{ value }}</option>-->
          </select>
        </div>

        <!-- <div class="form-group" v-if="fields.gender == 'Male'"> -->
<!--        <div class="form-group">-->
<!--          <label>Month and Day of Marriage:</label>-->
<!--          <br />-->
<!--          <select v-model="fields.day1" class="custom-select w-25">-->
<!--            <option value>Select</option>-->
<!--            <option v-for="(value, index) in days" :key="index">{{ value }}</option>-->
<!--          </select>-->

<!--          <select v-model="fields.month1" class="ml-3 custom-select w-25">-->
<!--            <option value>Select</option>-->
<!--            <option v-for="(value, index) in months" :key="index">{{ value }}</option>-->
<!--          </select>-->
<!--        </div>-->

        <button class="btn btn-primary mr-2">Submit</button>
        <a class="btn btn-secondary" routerLink="/admin/home">Cancel</a>
      </form>
    </div>
  `,
  styles: [
  ]
})
export class CreateMemberComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
