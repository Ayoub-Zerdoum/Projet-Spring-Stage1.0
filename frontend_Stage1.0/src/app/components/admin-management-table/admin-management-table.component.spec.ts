import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminManagementTableComponent } from './admin-management-table.component';

describe('AdminManagementTableComponent', () => {
  let component: AdminManagementTableComponent;
  let fixture: ComponentFixture<AdminManagementTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminManagementTableComponent]
    });
    fixture = TestBed.createComponent(AdminManagementTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
