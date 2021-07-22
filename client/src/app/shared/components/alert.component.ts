import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Alert, AlertType } from '@app/core/models/alert'
import { Subscription } from 'rxjs'
import { NavigationStart, Router } from '@angular/router'
import { AlertService } from '@app/core/services'

@Component({
  selector: 'app-alert',
  template: `
    <div class="mt-5 mb-5" *ngFor="let alert of alerts" class="{{cssClass(alert)}}">
      <a class="close" (click)="removeAlert(alert)">&times;</a>
      <span [innerHTML]="alert.message"></span>
    </div>
  `,

  styles: [`
    .mt-5 { margin-top: 60px !important;}
    /*.mb-5 { margin-top: -60px !important;}*/
  `]
})

export class AlertComponent implements OnInit, OnDestroy {

  @Input() id = 'default-alert';
  @Input() fade = true;

  alerts: Alert[] = [];
  alertSubscription!: Subscription;
  routeSubscription!: Subscription;

  constructor(private router: Router, private alertService: AlertService ) { }

  ngOnInit(): void {
    // subscribe to new alert notifications
    this.alertSubscription = this.alertService.onAlert(this.id)
      .subscribe(alert => {
        // clear alerts when an empty alert is received
        if (!alert.message){
          // filter out alerts without 'keepAfterRouteChange' flag
          this.alerts = this.alerts.filter(x => x.keepAfterRouteChange);

          // reset 'keepAfterRouteChange' flag on the rest
          this.alerts.forEach(x => x.keepAfterRouteChange = false);
          return;
        }

        // add alert to array
        this.alerts.push(alert);

        // auto close alert if required
        if(alert.autoClose){
          setTimeout(() => this.removeAlert(alert), 3000);
        }
      });

    // clear alerts on location change
    this.routeSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart){
        this.alertService.clear(this.id);
      }
    });
  }

  ngOnDestroy() {
    // unsubscribe to avoid memory leaks
    this.alertSubscription.unsubscribe();
    this.routeSubscription.unsubscribe();
  }

  removeAlert(alert: Alert){
    // check if already removed to prevent error on auto close
    if (!this.alerts.includes(alert)) return;
    if (this.fade) {
      // fade out alert
      alert.fade = true;

      // remove alert after faded out
      setTimeout(() => {
        this.alerts = this.alerts.filter(x => x !== alert);
      }, 250);
    } else {
      // remove alert
      this.alerts = this.alerts.filter(x => x !== alert);
    }
  }

  cssClass(alert: Alert){
    if (alert?.type === undefined) return;

    const classes = ['alert', 'alert-dismissible', 'mt-4', 'container'];

    const alertTypeClass = {
      [AlertType.Success]: 'alert-success',
      [AlertType.Error]: 'alert-danger',
      [AlertType.Info]: 'alert-info',
      [AlertType.Warning]: 'alert-warning'
    }

    classes.push(alertTypeClass[alert.type]);

    if (alert.fade){
      classes.push('fade');
    }

    return classes.join(' ');
  }

}
