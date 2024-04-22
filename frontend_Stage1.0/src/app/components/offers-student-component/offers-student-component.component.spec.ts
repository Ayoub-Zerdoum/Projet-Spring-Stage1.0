import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffersStudentComponentComponent } from './offers-student-component.component';

describe('OffersStudentComponentComponent', () => {
  let component: OffersStudentComponentComponent;
  let fixture: ComponentFixture<OffersStudentComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OffersStudentComponentComponent]
    });
    fixture = TestBed.createComponent(OffersStudentComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
