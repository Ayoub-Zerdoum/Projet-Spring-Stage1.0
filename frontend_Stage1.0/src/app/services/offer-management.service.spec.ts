import { TestBed } from '@angular/core/testing';

import { OfferManagementService } from './offer-management.service';

describe('OfferManagementService', () => {
  let service: OfferManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
