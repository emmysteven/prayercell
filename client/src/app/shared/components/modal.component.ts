import { Component, ViewEncapsulation, ElementRef, Input, OnInit, OnDestroy } from '@angular/core';
import { ModalService } from '@app/core/services'


@Component({
  selector: 'app-modal',
  template: `
    <div class="app-modal">
      <div class="app-modal-body">
        <ng-content></ng-content>
      </div>
    </div>
    <div class="app-modal-background"></div>
  `,
  styles: [
    `app-modal {
        display: none;
      }

      app-modal .app-modal {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 1000;
        overflow: auto;
      }

      app-modal .app-modal .app-modal-body {
        padding: 20px;
        background: #fff;
        margin: 40px;
      }

      app-modal .app-modal-background {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: #000;
        opacity: 0.75;
        z-index: 900;
      }

      body.app-modal-open {
        overflow: hidden;
      }
    `
  ]
})


export class ModalComponent implements OnInit {
  
  @Input() id: string = '';
  private readonly element: any;

  constructor(private modalService: ModalService, private el: ElementRef) {
    this.element = el.nativeElement;
  }

  ngOnInit(): void {
    // ensure id attribute exists
    if (!this.id) {
      console.error('modal must have an id');
      return;
    }

    // move element to bottom of page (just before </body>) so it can be displayed above everything else
    document.body.appendChild(this.element);

    // close modal on background click
    this.element.addEventListener('click', (el: any) => {
      if (el.target.className === 'app-modal') {
        this.close();
      }
    });

    // add self (this modal instance) to the modal service so it's accessible from controllers
    this.modalService.add(this);
  }

  // remove self from modal service when component is destroyed
  ngOnDestroy(): void {
    this.modalService.remove(this.id);
    this.element.remove();
  }

  // open modal
  open(): void {
    this.element.style.display = 'block';
    document.body.classList.add('app-modal-open');
  }

  // close modal
  close(): void {
    this.element.style.display = 'none';
    document.body.classList.remove('app-modal-open');
  }

}
