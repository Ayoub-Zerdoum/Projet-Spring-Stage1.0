import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessorManagementTableComponent } from './professor-management-table.component';

describe('ProfessorManagementTableComponent', () => {
  let component: ProfessorManagementTableComponent;
  let fixture: ComponentFixture<ProfessorManagementTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfessorManagementTableComponent]
    });
    fixture = TestBed.createComponent(ProfessorManagementTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
