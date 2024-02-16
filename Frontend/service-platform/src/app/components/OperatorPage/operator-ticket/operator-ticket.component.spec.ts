import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorTicketComponent } from './operator-ticket.component';

describe('OperatorTicketComponent', () => {
  let component: OperatorTicketComponent;
  let fixture: ComponentFixture<OperatorTicketComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperatorTicketComponent]
    });
    fixture = TestBed.createComponent(OperatorTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
