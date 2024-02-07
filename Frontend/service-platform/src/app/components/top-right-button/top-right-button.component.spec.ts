import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopRightButtonComponent } from './top-right-button.component';

describe('TopRightButtonComponent', () => {
  let component: TopRightButtonComponent;
  let fixture: ComponentFixture<TopRightButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopRightButtonComponent]
    });
    fixture = TestBed.createComponent(TopRightButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
