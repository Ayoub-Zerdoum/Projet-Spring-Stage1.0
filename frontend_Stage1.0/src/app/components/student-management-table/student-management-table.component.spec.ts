import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentManagementTableComponent } from './student-management-table.component';

describe('StudentManagementTableComponent', () => {
  let component: StudentManagementTableComponent;
  let fixture: ComponentFixture<StudentManagementTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentManagementTableComponent]
    });
    fixture = TestBed.createComponent(StudentManagementTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
