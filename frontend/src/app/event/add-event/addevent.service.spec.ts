import { TestBed } from '@angular/core/testing';

import { AddeventService } from './addevent.service';

describe('AddeventService', () => {
  let service: AddeventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddeventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
